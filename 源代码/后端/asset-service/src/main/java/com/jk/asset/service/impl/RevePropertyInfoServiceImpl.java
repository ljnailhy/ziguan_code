package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.service.RevePropertyInfoService;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.RevePropertyInfoMapper;
import com.jk.asset.model.dto.RevePropertyInfoDTO;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.request.RevePropertyInfoRequest;
import com.jk.asset.model.request.page.RevePropertyInfoPageRequest;
import com.jk.asset.service.handler.RevePropertyInfoHandler;
import com.jk.service.utils.PlatformFileUtils;
import io.swagger.annotations.Api;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 反担保/其他财产线索信息接口实现类
 *
 * @author wangtao
 * @since 2024-06-24 09:42:39
 */
@RestController
@Slf4j
@Api(tags = "反担保/其他财产线索信息接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RevePropertyInfoServiceImpl implements RevePropertyInfoService {

  private final RevePropertyInfoHandler handler;
  private final RevePropertyInfoMapper mapper;
  private final PlatformFileUtils platformFileUtils;

  @Override
  public Result<RevePropertyInfoDTO> add(RevePropertyInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "反担保/其他财产线索信息新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    RevePropertyInfo revePropertyInfo = PlatformMapUtils.getInstance().map(request, RevePropertyInfo.class);
    handler.save(revePropertyInfo);

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),revePropertyInfo.getId(), request.getBillType());

    RevePropertyInfoDTO dto = new RevePropertyInfoDTO();
    dto.setId(revePropertyInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<List<RevePropertyInfoDTO>> findByIds(List<Long> idList) {
    if (ObjectUtils.isEmpty(idList)) {
      return Result.success(new ArrayList<>());
    }

    List<RevePropertyInfo> list = handler.list(new LambdaQueryWrapper<RevePropertyInfo>()
        .in(RevePropertyInfo::getId, idList));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(new ArrayList<>());
    }

    List<RevePropertyInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, RevePropertyInfoDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<RevePropertyInfoDTO> update(RevePropertyInfoRequest request) {
    if (null == request || null == request.getId()) {
      String message = "反担保/其他财产线索信息修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RevePropertyInfo revePropertyInfo = PlatformMapUtils.getInstance().map(request, RevePropertyInfo.class);
    handler.updateById(revePropertyInfo);

    //  通用附件修改
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),revePropertyInfo.getId(), request.getBillType());

    return Result.success();
  }

  @Override
  public Result<RevePropertyInfoDTO> findById(Long id) {
    RevePropertyInfo revePropertyInfo = handler.getById(id);
    if (null == revePropertyInfo) {
      String message = "反担保/其他财产线索信息单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RevePropertyInfoDTO dto = PlatformMapUtils.getInstance().map(revePropertyInfo, RevePropertyInfoDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<RevePropertyInfoDTO>> findByDoId(Long doId, String doType, String billType) {

    List<RevePropertyInfo> list = handler.list(new LambdaUpdateWrapper<RevePropertyInfo>()
        .eq(RevePropertyInfo::getDoId, doId)
        .eq(RevePropertyInfo::getDoType, doType)
        .eq(RevePropertyInfo::getBillType, billType));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(Lists.newArrayList());
    }

    List<RevePropertyInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, RevePropertyInfoDTO.class);
    return Result.success(dtoList);
  }

  @Override
  public Result<List<RevePropertyInfoDTO>> findAll(Integer current, Integer size, RevePropertyInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RevePropertyInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<RevePropertyInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<RevePropertyInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RevePropertyInfoDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}