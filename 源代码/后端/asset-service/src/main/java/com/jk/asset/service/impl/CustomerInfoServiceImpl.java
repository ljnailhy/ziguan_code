package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.CustomerInfoMapper;
import com.jk.asset.model.dto.CustomerInfoDTO;
import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.asset.model.entity.CustomerInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.request.CustomerInfoRequest;
import com.jk.asset.model.request.page.CustomerInfoPageRequest;
import com.jk.asset.service.CustomerInfoService;
import com.jk.asset.service.handler.CustomerInfoHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.service.utils.PlatformFileUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户信息表接口实现类
 *
 * @author wangtao
 * @since 2024-06-20 11:38:52
 */
@RestController
@Slf4j
@Api(tags = "客户信息表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerInfoServiceImpl implements CustomerInfoService {

  private final CustomerInfoHandler handler;
  private final CustomerInfoMapper mapper;
  private final PlatformFileUtils platformFileUtils;

  @Override
  public Result<CustomerInfoDTO> add(CustomerInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "客户信息表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    CustomerInfo customerInfo = PlatformMapUtils.getInstance().map(request, CustomerInfo.class);
    handler.save(customerInfo);
    platformFileUtils.batchAddFile(request.getFileRequests(), customerInfo.getId(), BillTypeEnum.CUSTOMER_INFO.getKey());

    CustomerInfoDTO dto = new CustomerInfoDTO();
    dto.setId(customerInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<CustomerInfoDTO> update(CustomerInfoRequest request) {
    if (null == request || null == request.getId()) {
      String message = "客户信息表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }
    CustomerInfo customerInfo = PlatformMapUtils.getInstance().map(request, CustomerInfo.class);
    handler.updateById(customerInfo);
    platformFileUtils.batchUpdateFile(request.getFileRequests(), customerInfo.getId(), BillTypeEnum.CUSTOMER_INFO.getKey());
    return Result.success();
  }

  @Override
  public Result<CustomerInfoDTO> findById(Long id) {
    CustomerInfo customerInfo = handler.getById(id);
    if (null == customerInfo) {
      String message = "客户信息表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    CustomerInfoDTO dto = PlatformMapUtils.getInstance().map(customerInfo, CustomerInfoDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<CustomerInfoDTO>> findByIds(List<Long> idList) {
    if (ObjectUtils.isEmpty(idList)) {
      return Result.success(new ArrayList<>());
    }

    List<CustomerInfo> list = handler.list(new LambdaQueryWrapper<CustomerInfo>()
        .in(CustomerInfo::getId, idList));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(new ArrayList<>());
    }

    List<CustomerInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, CustomerInfoDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<List<CustomerInfoDTO>> findAll(Integer current, Integer size, CustomerInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<CustomerInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<CustomerInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<CustomerInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, CustomerInfoDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}