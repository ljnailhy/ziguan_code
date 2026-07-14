package com.jk.asset.service.impl.property;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.mapper.property.PropertyRightInfoMapper;
import com.jk.asset.model.dto.property.PropertyRightInfoDTO;
import com.jk.asset.model.entity.property.PropertyRightInfo;
import com.jk.asset.model.request.page.property.PropertyRightInfoPageRequest;
import com.jk.asset.model.request.property.PropertyRightInfoRequest;
import com.jk.asset.service.handler.property.PropertyRightInfoHandler;
import com.jk.asset.service.property.PropertyRightInfoService;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.service.client.SysDictionaryClient;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 产权信息表接口实现类
 *
 * @author wangtao
 * @since 2024-08-17 14:44:27
 */
@RestController
@Slf4j
@Api(tags = "产权信息表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyRightInfoServiceImpl implements PropertyRightInfoService {

  private final PropertyRightInfoHandler handler;
  private final PropertyRightInfoMapper mapper;
  private final SysDictionaryClient sysDictionaryClient;

  @Override
  public Result<PropertyRightInfoDTO> add(PropertyRightInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "产权信息表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    PropertyRightInfo propertyRightInfo = PlatformMapUtils.getInstance().map(request, PropertyRightInfo.class);
    handler.save(propertyRightInfo);

    PropertyRightInfoDTO dto = new PropertyRightInfoDTO();
    dto.setId(propertyRightInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<PropertyRightInfoDTO> update(PropertyRightInfoRequest request) {
    if (null == request || null == request.getId()) {
      String message = "产权信息表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    PropertyRightInfo propertyRightInfo = PlatformMapUtils.getInstance().map(request, PropertyRightInfo.class);
    handler.updateById(propertyRightInfo);

    return Result.success();
  }

  @Override
  public Result<PropertyRightInfoDTO> findById(Long id) {
    PropertyRightInfo propertyRightInfo = handler.getById(id);
    if (null == propertyRightInfo) {
      String message = "产权信息表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    PropertyRightInfoDTO dto = PlatformMapUtils.getInstance().map(propertyRightInfo, PropertyRightInfoDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<PropertyRightInfoDTO>> findAll(Integer current, Integer size, PropertyRightInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    List<SysDictionaryItemDTO> statesItem = sysDictionaryClient.findByCode("ASSET_UNIT_STATE").getData().getItems();
    if (request.getIsTransfer() != null && !request.getIsTransfer()){
      List<SysDictionaryItemDTO> collect = statesItem.stream().filter(item -> !"已转让".equals(item.getItemName())).collect(Collectors.toList());
      request.setAssetUnitStates(collect.stream().map(SysDictionaryItemDTO::getId).collect(Collectors.toList()));
    }
    // Step1：创建一个 Page 对象
    IPage<PropertyRightInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<PropertyRightInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<PropertyRightInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, PropertyRightInfoDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}