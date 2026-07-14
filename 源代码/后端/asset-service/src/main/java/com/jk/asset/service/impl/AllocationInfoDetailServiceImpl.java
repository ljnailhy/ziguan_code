package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.model.dto.LawyerInfoDTO;
import com.jk.asset.model.entity.BillLawyer;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.service.handler.BillLawyerHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.AllocationInfoDetailMapper;
import com.jk.asset.model.dto.AllocationInfoDetailDTO;
import com.jk.asset.model.entity.AllocationInfoDetail;
import com.jk.asset.model.request.AllocationInfoDetailRequest;
import com.jk.asset.model.request.page.AllocationInfoDetailPageRequest;
import com.jk.asset.service.AllocationInfoDetailService;
import com.jk.asset.service.handler.AllocationInfoDetailHandler;
import com.sun.xml.bind.v2.TODO;
import io.swagger.annotations.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目分配/变更明细接口实现类
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
@RestController
@Slf4j
@Api(tags = "项目分配/变更明细接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AllocationInfoDetailServiceImpl implements AllocationInfoDetailService {

  private final AllocationInfoDetailHandler handler;
  private final AllocationInfoDetailMapper mapper;
  private final ProjectInfoHandler projectInfoHandler;
  private final BillLawyerHandler billLawyerHandler;

  @Override
  public Result<AllocationInfoDetailDTO> add(AllocationInfoDetailRequest request) {
    if (null == request || request.unverified()) {
      String message = "项目分配/变更明细新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    AllocationInfoDetail allocationInfoDetail = PlatformMapUtils.getInstance().map(request, AllocationInfoDetail.class);
    handler.save(allocationInfoDetail);

    AllocationInfoDetailDTO dto = new AllocationInfoDetailDTO();
    dto.setId(allocationInfoDetail.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<AllocationInfoDetailDTO> update(AllocationInfoDetailRequest request) {
    if (null == request || null == request.getId()) {
      String message = "项目分配/变更明细修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    AllocationInfoDetail allocationInfoDetail = PlatformMapUtils.getInstance().map(request, AllocationInfoDetail.class);
    handler.updateById(allocationInfoDetail);

    return Result.success();
  }

  @Override
  public Result<AllocationInfoDetailDTO> findById(Long id) {
    AllocationInfoDetail allocationInfoDetail = handler.getById(id);
    if (null == allocationInfoDetail) {
      String message = "项目分配/变更明细单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    AllocationInfoDetailDTO dto = PlatformMapUtils.getInstance().map(allocationInfoDetail, AllocationInfoDetailDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<AllocationInfoDetailDTO>> findByAllocationId(Long allocationId) {

    List<AllocationInfoDetail> list = handler.list(new LambdaQueryWrapper<AllocationInfoDetail>()
        .eq(AllocationInfoDetail::getAllocationId, allocationId));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(new ArrayList<>());
    }

    List<AllocationInfoDetailDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, AllocationInfoDetailDTO.class);

    for (AllocationInfoDetailDTO allocationInfoDetailDTO : dtoList) {
      ProjectInfo projectInfo = projectInfoHandler.getById(allocationInfoDetailDTO.getProjectId());
      if (ObjectUtils.isEmpty(projectInfo)) {
        continue;
      }
      allocationInfoDetailDTO.setCompensationMoney(projectInfo.getCompensationMoney());
      allocationInfoDetailDTO.setCompensationDate(projectInfo.getCompensationDate());
      //  TODO  律师是多个
      List<BillLawyer> billLawyerList = billLawyerHandler.list(new LambdaUpdateWrapper<BillLawyer>()
          .eq(BillLawyer::getDoId, allocationInfoDetailDTO.getId())
          .eq(BillLawyer::getDoType, BillTypeEnum.ALLOCATION_INFO_DETAIL.getKey()));
      if (!ObjectUtils.isEmpty(billLawyerList)) {
        String lawyers = billLawyerList.stream().map(item -> "" + item.getLawyer()).collect(Collectors.joining(","));
        allocationInfoDetailDTO.setLawyers(lawyers);
      }
    }

    return Result.success(dtoList);
  }

  @Override
  public Result<List<AllocationInfoDetailDTO>> findAll(Integer current, Integer size, AllocationInfoDetailPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<AllocationInfoDetail> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<AllocationInfoDetail> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<AllocationInfoDetailDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, AllocationInfoDetailDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}