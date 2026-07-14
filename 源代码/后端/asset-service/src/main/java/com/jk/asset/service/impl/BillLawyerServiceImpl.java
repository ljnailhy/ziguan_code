package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.model.dto.LawyerInfoDTO;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.BillLawyerMapper;
import com.jk.asset.model.dto.BillLawyerDTO;
import com.jk.asset.model.entity.BillLawyer;
import com.jk.asset.model.request.BillLawyerRequest;
import com.jk.asset.model.request.page.BillLawyerPageRequest;
import com.jk.asset.service.BillLawyerService;
import com.jk.asset.service.handler.BillLawyerHandler;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单据与律师关系表接口实现类
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
@RestController
@Slf4j
@Api(tags = "单据与律师关系表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BillLawyerServiceImpl implements BillLawyerService {

  private final BillLawyerHandler handler;
  private final BillLawyerMapper mapper;

  @Override
  public Result<BillLawyerDTO> add(BillLawyerRequest request) {
    if (null == request || request.unverified()) {
      String message = "单据与律师关系表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    BillLawyer billLawyer = PlatformMapUtils.getInstance().map(request, BillLawyer.class);
    handler.save(billLawyer);

    BillLawyerDTO dto = new BillLawyerDTO();
    dto.setId(billLawyer.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<BillLawyerDTO> update(BillLawyerRequest request) {
    if (null == request || null == request.getId()) {
      String message = "单据与律师关系表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    BillLawyer billLawyer = PlatformMapUtils.getInstance().map(request, BillLawyer.class);
    handler.updateById(billLawyer);

    return Result.success();
  }

  @Override
  public Result<BillLawyerDTO> findById(Long id) {
    BillLawyer billLawyer = handler.getById(id);
    if (null == billLawyer) {
      String message = "单据与律师关系表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    BillLawyerDTO dto = PlatformMapUtils.getInstance().map(billLawyer, BillLawyerDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<Long>> findByDoId(Long doId, String doType) {

    List<BillLawyer> list = handler.list(new LambdaUpdateWrapper<BillLawyer>()
        .eq(BillLawyer::getDoId, doId)
        .eq(BillLawyer::getIsEffective, Boolean.TRUE)
        .eq(BillLawyer::getDoType, doType));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(Lists.newArrayList());
    }

    List<Long> lawyerIds = list.stream().map(item -> item.getLawyer()).collect(Collectors.toList());
    return Result.success(lawyerIds);
  }

  @Override
  public Result<List<BillLawyerDTO>> findHistoryByDoId(Long doId, String doType) {
    List<BillLawyer> list = handler.list(new LambdaUpdateWrapper<BillLawyer>()
        .eq(BillLawyer::getDoId, doId)
        .eq(BillLawyer::getIsEffective, Boolean.FALSE)
        .eq(BillLawyer::getDoType, doType).orderByDesc(BillLawyer::getCreateStamp));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(Lists.newArrayList());
    }
    List<BillLawyerDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, BillLawyerDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<?> batchUpdata(Long doId, String doType, List<Long> idList) {
    handler.batchAddBillLawyer(doId,doType,idList);
    return Result.success();
  }

  @Override
  public Result<List<BillLawyerDTO>> findAll(Integer current, Integer size, BillLawyerPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<BillLawyer> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<BillLawyer> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<BillLawyerDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, BillLawyerDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}