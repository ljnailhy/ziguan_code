package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.BillContractMapper;
import com.jk.asset.model.dto.BillContractDTO;
import com.jk.asset.model.entity.BillContract;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.request.BillContractRequest;
import com.jk.asset.model.request.page.BillContractPageRequest;
import com.jk.asset.service.BillContractService;
import com.jk.asset.service.handler.BillContractHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 单据合同关联表接口实现类
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:32
 */
@RestController
@Slf4j
@Api(tags = "单据合同关联表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BillContractServiceImpl implements BillContractService {

  private final BillContractHandler handler;
  private final BillContractMapper mapper;
  private final ProjectInfoHandler projectInfoHandler;

  @Override
  public Result<BillContractDTO> add(BillContractRequest request) {
    if (null == request || request.unverified()) {
      String message = "单据合同关联表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    BillContract billContract = PlatformMapUtils.getInstance().map(request, BillContract.class);
    handler.save(billContract);

    //  如果是类型是项目，同步项目表
    if (request.getDoType().equals(BillTypeEnum.PROJECT_INFO.getKey())){
      LambdaUpdateWrapper<ProjectInfo> wrapper = Wrappers.lambdaUpdate();
      wrapper.eq(ProjectInfo::getId, request.getDoId())
          .set(ProjectInfo::getContractId, request.getContractId());
      projectInfoHandler.update(wrapper);
    }

    BillContractDTO dto = new BillContractDTO();
    dto.setId(billContract.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<BillContractDTO> update(BillContractRequest request) {
    if (null == request || null == request.getId()) {
      String message = "单据合同关联表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    BillContract billContract = PlatformMapUtils.getInstance().map(request, BillContract.class);
    handler.updateById(billContract);

    return Result.success();
  }

  @Override
  public Result<BillContractDTO> findById(Long id) {
    BillContract billContract = handler.getById(id);
    if (null == billContract) {
      String message = "单据合同关联表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    BillContractDTO dto = PlatformMapUtils.getInstance().map(billContract, BillContractDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<BillContractDTO>> findHistoryByDoId(Long doId, String doType) {
    List<BillContract> list = handler.list(new LambdaUpdateWrapper<BillContract>()
        .eq(BillContract::getDoId, doId)
        .eq(BillContract::getDoType, doType)
        .orderByDesc(BillContract::getCreateStamp));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(Lists.newArrayList());
    }
    //  最新一条正在使用，历史记录不查询
    if (!doType.equals(BillTypeEnum.LEASE_INFO.getKey()) && !doType.equals(BillTypeEnum.ASSET_TRANSFER.getKey())){
      list.remove(0);
    }

    List<BillContractDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, BillContractDTO.class);
    return Result.success(dtoList);
  }

  @Override
  public Result<List<BillContractDTO>> findAll(Integer current, Integer size, BillContractPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<BillContract> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<BillContract> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<BillContractDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, BillContractDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}