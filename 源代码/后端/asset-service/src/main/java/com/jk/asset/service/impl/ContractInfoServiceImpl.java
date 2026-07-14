package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.ContractInfoMapper;
import com.jk.asset.model.dto.ContractInfoDTO;
import com.jk.asset.model.entity.AllocationInfo;
import com.jk.asset.model.entity.AllocationInfoDetail;
import com.jk.asset.model.entity.BillContract;
import com.jk.asset.model.entity.ContractInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.request.BillContractRequest;
import com.jk.asset.model.request.ContractInfoRequest;
import com.jk.asset.model.request.page.ContractInfoPageRequest;
import com.jk.asset.service.ContractInfoService;
import com.jk.asset.service.handler.AllocationInfoDetailHandler;
import com.jk.asset.service.handler.AllocationInfoHandler;
import com.jk.asset.service.handler.BillContractHandler;
import com.jk.asset.service.handler.ContractInfoHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.SignatureInfoHandler;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.enums.FlowingWaterEnum;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.service.client.FlowingWaterClient;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.utils.PlatformFileUtils;
import com.jk.service.utils.PlatformUserUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 合同信息接口实现类
 *
 * @author wangshuai
 * @since 2024-06-20 17:46:10
 */
@RestController
@Slf4j
@Api(tags = "合同信息接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractInfoServiceImpl implements ContractInfoService {

  private final ContractInfoHandler handler;
  private final ContractInfoMapper mapper;
  private final SignatureInfoHandler signatureInfoHandler;
  private final PlatformFileUtils platformFileUtils;
  private final FlowingWaterClient flowingWaterClient;
  private final ProjectInfoHandler projectInfoHandler;
  private final AllocationInfoDetailHandler allocationInfoDetailHandler;
  private final BillContractHandler billContractHandler;
  private final AssetUserLimitsUtils assetUserLimitsUtils;
  private final PlatformUserUtils platformUserUtils;
  private final AllocationInfoHandler allocationInfoHandler;
  private final SysDictionaryClient sysDictionaryClient;
  @Override
  public Result<ContractInfoDTO> add(ContractInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "合同信息新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }
    Result<String> flowingWaterDTOResult = flowingWaterClient.add(FlowingWaterEnum.CONTRACT_INFO);

    if (flowingWaterDTOResult.getCode() != 0) {
      return Result.error(flowingWaterDTOResult.getErrorMessage());
    }
    // 对象拷贝：request->DO
    ContractInfo contractInfo = PlatformMapUtils.getInstance().map(request, ContractInfo.class);
    contractInfo.setContractCode(flowingWaterDTOResult.getData());
    //  当前人
    SysUserDTO currentUser = platformUserUtils.getCurrentUser();
    if (StringUtils.isBlank(contractInfo.getFollowUp())){
      contractInfo.setFollowUp(currentUser.getId().toString());
    }
    handler.save(contractInfo);
    SysDictionaryItemDTO contractTypeItem = sysDictionaryClient.findByCode("CONTRACT_TYPE").getData().getItems()
            .stream().filter(item -> "CONTRACT_TYPE_001".equals(item.getItemCode())).findAny().get();

    List<BillContractRequest> projectInfoList = request.getProjectInfoList();
    if (CollectionUtils.isNotEmpty(projectInfoList)) {
      List<BillContract> billContractList = PlatformMapUtils.getInstance().mapAsList(projectInfoList, BillContract.class);
      billContractList.forEach(item -> {
        item.setDoType(BillTypeEnum.PROJECT_INFO.getKey());
        item.setContractId(contractInfo.getId());
      });
      billContractHandler.saveBatch(billContractList);
      if (contractTypeItem.getId().equals(request.getContractType())) {
        List<Long> projectIds = billContractList.stream().map(BillContract::getDoId).collect(Collectors.toList());
        List<ProjectInfo> projectInfos = projectInfoHandler.listByIds(projectIds);
        projectInfos.forEach(item -> item.setContractId(contractInfo.getId()));
        projectInfoHandler.updateBatchById(projectInfos);
      }
    }

    //  签约方信息新增
    signatureInfoHandler.batchAddOrUpdateSignatureInfo(request.getSignatureInfoList(),contractInfo.getId());

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),contractInfo.getId(), BillTypeEnum.CONTRACT_INFO.getKey());

    ContractInfoDTO dto = new ContractInfoDTO();
    dto.setId(contractInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {

    //  验证合同是否被引用
    List<ProjectInfo> projectInfoList = projectInfoHandler.list(new LambdaQueryWrapper<ProjectInfo>().eq(ProjectInfo::getContractId, id));
    List<AllocationInfoDetail> allocationInfoDetailList = allocationInfoDetailHandler.list(new LambdaQueryWrapper<AllocationInfoDetail>()
        .eq(AllocationInfoDetail::getRelatedContracts, id));
    List<BillContract> billContractList = billContractHandler.list(new LambdaQueryWrapper<BillContract>().eq(BillContract::getContractId, id));
    List<Long> allocationIds = allocationInfoDetailList.stream().map(item -> item.getAllocationId()).collect(Collectors.toList());
    List<AllocationInfo> allocationInfos = null;
    if (ObjectUtils.isNotEmpty(allocationIds)) {
      allocationInfos = allocationInfoHandler.listByIds(allocationIds);
    }
    if (ObjectUtils.isNotEmpty(projectInfoList) || ObjectUtils.isNotEmpty(allocationInfos) || ObjectUtils.isNotEmpty(billContractList)) {
      String message = "合同信息删除失败 该合同已被引用";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }

    handler.removeById(id);

    //  通用附件删除
    platformFileUtils.deleteFileByDoId(id,BillTypeEnum.CONTRACT_INFO.getKey());
    signatureInfoHandler.deleteSignatureInfoByContractId(id);
    return Result.success();
  }

  @Override
  public Result<ContractInfoDTO> update(ContractInfoRequest request) {
    if (null == request || null == request.getId()) {
      String message = "合同信息修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    ContractInfo contractInfo = PlatformMapUtils.getInstance().map(request, ContractInfo.class);
    //  当前人
    SysUserDTO currentUser = platformUserUtils.getCurrentUser();
    if (StringUtils.isBlank(contractInfo.getFollowUp())){
      contractInfo.setFollowUp(currentUser.getId().toString());
    }
    handler.updateById(contractInfo);
    //  签约方信息更新
    signatureInfoHandler.batchAddOrUpdateSignatureInfo(request.getSignatureInfoList(),contractInfo.getId());
    //  通用附件更新
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),contractInfo.getId(), BillTypeEnum.CONTRACT_INFO.getKey());

    List<BillContractRequest> projectInfoList = request.getProjectInfoList();
    SysDictionaryItemDTO contractTypeItem = sysDictionaryClient.findByCode("CONTRACT_TYPE").getData().getItems()
            .stream().filter(item -> "CONTRACT_TYPE_001".equals(item.getItemCode())).findAny().get();
    List<Long> updateProjectIds = new ArrayList<>();
    List<Long> deleteProjectIds = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(projectInfoList)){
      if (contractTypeItem.getId().equals(request.getContractType())) {
        for (BillContractRequest billContractRequest : projectInfoList) {
          if (billContractRequest.getOperateType() != null && billContractRequest.getOperateType().getKey().equals(OperationTypeEnum.DELETE.getKey())){
            // 反写为空
            deleteProjectIds.add(billContractRequest.getDoId());
          }else {
            updateProjectIds.add(billContractRequest.getDoId());
          }
        }
      }
      List<BillContract> billContractList = PlatformMapUtils.getInstance().mapAsList(projectInfoList,BillContract.class);
      billContractList.forEach(item -> {
        item.setDoType(BillTypeEnum.PROJECT_INFO.getKey());
        item.setContractId(contractInfo.getId());
      });
      billContractHandler.saveOrUpdateBatch(billContractList);
      List<BillContractRequest> deleteItems = projectInfoList.stream().filter(item -> item.getOperateType() != null).filter(item -> item.getOperateType().getKey().equals(OperationTypeEnum.DELETE.getKey())).collect(Collectors.toList());
      if (CollectionUtils.isNotEmpty(deleteItems)){
        billContractHandler.removeByIds(deleteItems.stream().map(BillContractRequest::getId).collect(Collectors.toList()));
      }
    }
    if (CollectionUtils.isNotEmpty(updateProjectIds)){
      List<ProjectInfo> projectInfos = projectInfoHandler.listByIds(updateProjectIds);
      projectInfos.forEach(item -> item.setContractId(contractInfo.getId()));
      projectInfoHandler.updateBatchById(projectInfos);
    }
    if (CollectionUtils.isNotEmpty(deleteProjectIds)){
      List<ProjectInfo> projectInfos = projectInfoHandler.listByIds(updateProjectIds);
      projectInfos.forEach(item -> item.setContractId(null));
      projectInfoHandler.updateBatchById(projectInfos);
    }
    return Result.success();
  }

  @Override
  public Result<ContractInfoDTO> findById(Long id) {
    ContractInfo contractInfo = handler.getById(id);
    if (null == contractInfo) {
      String message = "合同信息单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    ContractInfoDTO dto = PlatformMapUtils.getInstance().map(contractInfo, ContractInfoDTO.class);
    if (ObjectUtils.isNotEmpty(dto)){
      List<BillContract> billContracts = billContractHandler.list(new LambdaUpdateWrapper<BillContract>()
              .eq(BillContract::getDoType, BillTypeEnum.PROJECT_INFO.getKey())
              .eq(BillContract::getContractId, id)
      );
      if (CollectionUtils.isNotEmpty(billContracts)){
        dto.setProjectInfoList(PlatformMapUtils.getInstance().mapAsList(billContracts, BillContractRequest.class));
      }
    }
    return Result.success(dto);
  }

  @Override
  public Result<List<ContractInfoDTO>> findAll(Integer current, Integer size, ContractInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<ContractInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
    request.setOrgUserIds(userLimitsOrg);
    List<ContractInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    Long projectId = request.getProjectId();
    // Step3：获取分页数据
    List<ContractInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, ContractInfoDTO.class);
    List<BillContract> billContracts = billContractHandler.list(new LambdaUpdateWrapper<BillContract>()
            .eq(BillContract::getDoType, BillTypeEnum.PROJECT_INFO.getKey())
            .in(BillContract::getContractId, dtoList.stream().map(ContractInfoRequest::getId).collect(Collectors.toList()))
            .eq(ObjectUtils.isNotEmpty(projectId),BillContract::getDoId, projectId)
    );
    if (CollectionUtils.isNotEmpty(billContracts)){
      Map<Long, List<BillContract>> billcontractMap = billContracts.stream().collect(Collectors.groupingBy(BillContract::getContractId));
      for (ContractInfoDTO contractInfoDTO : dtoList) {
        List<BillContract> billContracts1 = billcontractMap.get(contractInfoDTO.getId());
        if (CollectionUtils.isNotEmpty(billContracts1)){
          contractInfoDTO.setProjectInfoList(PlatformMapUtils.getInstance().mapAsList(billContracts1, BillContractRequest.class));
        }
      }
    }
    List<Long> contractIds = billContracts.stream().map(BillContract::getContractId).collect(Collectors.toList());
    List<ContractInfoDTO> collect = dtoList.stream().filter(item -> contractIds.contains(item.getId())).collect(Collectors.toList());
    page.setTotal(collect.size());
    page.setCurrent(current);
    page.setSize(size);
    int start = (current - 1) * size;
    int end = Math.min(start + size, collect.size());
    return Result.success(collect.subList(start, end), PageFactory.page(page));
  }

}