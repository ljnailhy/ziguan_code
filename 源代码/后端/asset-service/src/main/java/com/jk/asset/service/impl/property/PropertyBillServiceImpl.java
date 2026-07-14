package com.jk.asset.service.impl.property;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.property.PropertyBillMapper;
import com.jk.asset.model.dto.property.PropertyBillDTO;
import com.jk.asset.model.entity.property.PropertyBill;
import com.jk.asset.model.request.property.PropertyBillRequest;
import com.jk.asset.model.request.page.property.PropertyBillPageRequest;
import com.jk.asset.service.property.PropertyBillService;
import com.jk.asset.service.handler.property.PropertyBillHandler;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资产和单据关联表接口实现类
 *
 * @author wangshuai
 * @since 2024-07-11 18:03:58
 */
@RestController
@Slf4j
@Api(tags = "资产和单据关联表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyBillServiceImpl implements PropertyBillService {

  private final PropertyBillHandler handler;
  private final PropertyBillMapper mapper;

  @Override
  public Result<PropertyBillDTO> add(PropertyBillRequest request) {
    if (null == request || request.unverified()) {
      String message = "资产和单据关联表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    PropertyBill propertyBill = PlatformMapUtils.getInstance().map(request, PropertyBill.class);
    handler.save(propertyBill);

    PropertyBillDTO dto = new PropertyBillDTO();
    dto.setId(propertyBill.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<PropertyBillDTO> update(PropertyBillRequest request) {
    if (null == request || null == request.getId()) {
      String message = "资产和单据关联表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    PropertyBill propertyBill = PlatformMapUtils.getInstance().map(request, PropertyBill.class);
    handler.updateById(propertyBill);

    return Result.success();
  }

  @Override
  public Result<PropertyBillDTO> findById(Long id) {
    PropertyBill propertyBill = handler.getById(id);
    if (null == propertyBill) {
      String message = "资产和单据关联表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    PropertyBillDTO dto = PlatformMapUtils.getInstance().map(propertyBill, PropertyBillDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<PropertyBillDTO>> findPropertyBillByDoId(Long doId, String doType) {
    List<PropertyBill> list = handler.list(new LambdaUpdateWrapper<PropertyBill>()
        .eq(PropertyBill::getDoId, doId)
        .eq(PropertyBill::getDoType, doType));

    if (ObjectUtils.isEmpty(list)){
      return Result.success(Lists.newArrayList());
    }

    List<PropertyBillDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, PropertyBillDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<List<PropertyBillDTO>> findAll(Integer current, Integer size, PropertyBillPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<PropertyBill> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<PropertyBill> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<PropertyBillDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, PropertyBillDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}