package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.FlowBillEnum;
import com.jk.asset.mapper.AllocationInfoMapper;
import com.jk.asset.model.dto.AllocationInfoDTO;
import com.jk.asset.model.entity.AllocationInfo;
import com.jk.asset.model.entity.AllocationInfoDetail;
import com.jk.asset.model.entity.BillContract;
import com.jk.asset.model.entity.BillLawyer;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.request.AllocationInfoDetailRequest;
import com.jk.asset.model.request.AllocationInfoRequest;
import com.jk.asset.model.request.ManageHistoryRecordRequest;
import com.jk.asset.model.request.page.AllocationInfoPageRequest;
import com.jk.asset.service.AllocationInfoService;
import com.jk.asset.service.ManageHistoryRecordService;
import com.jk.asset.service.handler.AllocationInfoDetailHandler;
import com.jk.asset.service.handler.AllocationInfoHandler;
import com.jk.asset.service.handler.BillContractHandler;
import com.jk.asset.service.handler.BillLawyerHandler;
import com.jk.asset.service.handler.LawyerInfoHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.tasks.AdventEarlyWarning;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.constant.PlatformConstant;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryDTO;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.service.client.ProcessClient;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.utils.PlatformFileUtils;
import com.jk.workflow.constant.ProcessConstants;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 分配/变更主表接口实现类
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
@RestController
@Slf4j
@Api(tags = "分配/变更主表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AllocationInfoServiceImpl implements AllocationInfoService {

  private final AllocationInfoHandler handler;
  private final AllocationInfoMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final AllocationInfoDetailHandler allocationInfoDetailHandler;
  private final ProcessClient processClient;
  private final ProjectInfoHandler projectInfoHandler;
  private final SysDictionaryClient dictionaryClient;
  private final BillLawyerHandler billLawyerHandler;
  private final BillContractHandler billContractHandler;
  private final ManageHistoryRecordService manageHistoryRecordService;
  private final SysDictionaryClient sysDictionaryClient;
  private final LawyerInfoHandler lawyerInfoHandler;
  private final AdventEarlyWarning adventEarlyWarning;

  private final AssetUserLimitsUtils assetUserLimitsUtils;

  @Override
  public Result<AllocationInfoDTO> add(AllocationInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "分配/变更主表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    AllocationInfo allocationInfo = PlatformMapUtils.getInstance().map(request, AllocationInfo.class);
    handler.save(allocationInfo);

    //  分配明细处理
    allocationInfoDetailHandler.batchAddOrUpdateAllocationDetail(request.getAllocationInfoDetailRequestList(),allocationInfo.getId());

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),allocationInfo.getId(), BillTypeEnum.ALLOCATION_INFO.getKey());

    AllocationInfoDTO dto = new AllocationInfoDTO();
    dto.setId(allocationInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<AllocationInfoDTO> update(AllocationInfoRequest request) {
    if (null == request) {
      String message = "分配/变更主表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(500,message);
    }
    // 判断同一个项目不能同时进行分配
    List<AllocationInfoDetailRequest> detailRequestList = request.getAllocationInfoDetailRequestList();
    if (ObjectUtils.isEmpty(detailRequestList)) {
      String message = "分配明细不能为空！";
      log.info("{} request {}", message, request);
      return Result.error(500,message);
    }
    AllocationInfo allocationInfo = PlatformMapUtils.getInstance().map(request, AllocationInfo.class);

//    List<Long> projectIds = detailRequestList.stream().map(item -> item.getProjectId()).collect(Collectors.toList());
//    List<Long> allocationIds = allocationInfoDetailHandler.list(new LambdaQueryWrapper<AllocationInfoDetail>().in(AllocationInfoDetail::getProjectId,projectIds)).stream().map(item -> item.getAllocationId()).collect(Collectors.toList());
//    List<AllocationInfo> infoList = handler.list(new LambdaQueryWrapper<AllocationInfo>().in(AllocationInfo::getId,allocationIds).eq(AllocationInfo::getFlowState,ProcessStatus.running.getKey()).notIn(AllocationInfo::getId,allocationInfo.getId()));
//    if(org.apache.commons.lang3.ObjectUtils.isNotEmpty(infoList)){
//      String message = "项目分配明细中存在分配审批中状态的项目，请核查！";
//      log.info("{} request {}", message, request);
//      return Result.error(500,message);
//    }

    handler.saveOrUpdate(allocationInfo);

    //  分配明细处理
    allocationInfoDetailHandler.batchAddOrUpdateAllocationDetail(request.getAllocationInfoDetailRequestList(),allocationInfo.getId());

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),allocationInfo.getId(), BillTypeEnum.ALLOCATION_INFO.getKey());

    AllocationInfoDTO dto = new AllocationInfoDTO();
    dto.setId(allocationInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<AllocationInfoDTO> submit(AllocationInfoRequest request) {
    request.setFlowState(ProcessStatus.running);
    Result<AllocationInfoDTO> updateResult = update(request);
    if (null == updateResult || !updateResult.succeedWithData()) {
      if(updateResult.getCode().equals(500)){
        return Result.error(updateResult.getErrorMessage());
      }else{
        return Result.error("提交失败");
      }
    }
    Long id = updateResult.getData().getId();
    Result<String> instanceIdResult = processClient.getProcessInstanceIdByDoId(id);
    if (null != instanceIdResult && instanceIdResult.succeedWithData()) {
      String errorMessage = "该单据已提交流程";
      log.warn("{} {}", errorMessage, instanceIdResult.getData());
      return Result.error(errorMessage);
    }

    //  【项目分配】+标题名称
    String flowTitle = "【项目分配】%s";

    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, id);
    variables.put(ProcessConstants.VARIABLE_OBJECT, String.format(flowTitle,request.getTitle()));
    processClient.startProcess(FlowBillEnum.ALLOCATION_INFO.getKey(), variables);

    // 把项目状态改为“分配中”
    SysDictionaryItemDTO itemDTO = sysDictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey())
            .getData()
            .getItems()
            .stream()
            .filter(item -> item.getItemCode().equals(DictEnum.PROJECT_STATE_17.getKey()))
            .findAny().get();

    List<Long> projectIds = request.getAllocationInfoDetailRequestList().stream().map(AllocationInfoDetailRequest::getProjectId).collect(Collectors.toList());
    List<ProjectInfo> projectInfoList = projectInfoHandler.listByIds(projectIds);
    projectInfoList.forEach(item ->{
      item.setProjectState(itemDTO.getId());
    });
    projectInfoHandler.updateBatchById(projectInfoList);
    return Result.success();
  }

  @Override
  public Result<AllocationInfoDTO> writeBackProject(Long id) {
    AllocationInfo allocationInfo = handler.getById(id);
    if (null == allocationInfo) {
      String message = "分配/变更主表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    Long state = null;
    try {
      Result<SysDictionaryDTO> byCode = dictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey());
      if (byCode.getCode().equals(0)) {
        Optional<Long> first = byCode.getData().getItems()
            .stream()
            .filter(item -> DictEnum.PROJECT_STATE_02.getKey().equals(item.getItemCode()))
            .map(SysDictionaryItemDTO::getId)
            .findFirst();
        state = first.get();
      }
    }catch (Exception e){
      String message = "状态失败，请联系管理员";
      log.info("{} code {}", message, DictEnum.PROJECT_STATE_02.getKey());
      return Result.error(message);
    }

    List<AllocationInfoDetail> list = allocationInfoDetailHandler.list(new LambdaUpdateWrapper<AllocationInfoDetail>()
        .eq(AllocationInfoDetail::getAllocationId, id));

    for (AllocationInfoDetail allocationInfoDetail : list) {
      //  修改项目状态
      projectInfoHandler.update(new LambdaUpdateWrapper<ProjectInfo>()
          .set(ProjectInfo::getProjectState, state)
          .set(ProjectInfo::getManage, allocationInfoDetail.getManage())
          .set(ProjectInfo::getLawFirmId, allocationInfoDetail.getLawFirm())
          .set(ProjectInfo::getContractId, allocationInfoDetail.getRelatedContracts())
          .eq(ProjectInfo::getId, allocationInfoDetail.getProjectId()));

      //  修改项目律师
      List<Long> lawyerIds = billLawyerHandler.list(new LambdaUpdateWrapper<BillLawyer>()
              .eq(BillLawyer::getDoType, BillTypeEnum.ALLOCATION_INFO_DETAIL.getKey())
              .eq(BillLawyer::getDoId, allocationInfoDetail.getId()))
          .stream()
          .map(item -> item.getLawyer())
          .collect(Collectors.toList());

      billLawyerHandler.batchAddBillLawyer(allocationInfoDetail.getProjectId(),BillTypeEnum.PROJECT_INFO.getKey(),lawyerIds);

      //  往合同与单据关联表插入一条数据
      BillContract billContract = new BillContract()
          .setContractId(allocationInfoDetail.getRelatedContracts())
          .setDoId(allocationInfoDetail.getProjectId())
          .setDoType(BillTypeEnum.PROJECT_INFO.getKey());
      billContractHandler.save(billContract);
      //  保全经理变更
      ManageHistoryRecordRequest manageHistoryRecordRequest = new ManageHistoryRecordRequest()
          .setProjectId(allocationInfoDetail.getProjectId())
          .setManageStr(allocationInfoDetail.getManage());
      manageHistoryRecordService.add(manageHistoryRecordRequest);

      // 分配流程结束消息提醒保全经理与律师进行及时跟进
      ProjectInfo projectInfo = projectInfoHandler.getById(allocationInfoDetail.getProjectId());
      String doType = DictEnum.MESSAGE_TYPE_006.getKey();
      String title = "【"+DictEnum.MESSAGE_TYPE_006.getValue()+"】"+projectInfo.getProjectName();
      String content = "【"+projectInfo.getProjectName()+"】追偿项目已分配到您账号下，请及时跟进相关工作。";
      String receiveUser = allocationInfoDetail.getManage();
      adventEarlyWarning.addMessage(projectInfo.getId(),doType,title,content,receiveUser,"MESSAGE_TYPE_006","");
      // 提醒律所
      LambdaQueryWrapper<LawyerInfo> lawyerInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
      lawyerInfoLambdaQueryWrapper.eq(LawyerInfo::getLawFirmId,allocationInfoDetail.getLawFirm());
      lawyerInfoLambdaQueryWrapper.isNotNull(LawyerInfo::getUserId);
      List<LawyerInfo> lawyerInfoList = lawyerInfoHandler.list(lawyerInfoLambdaQueryWrapper);
      Optional<Long> userId = lawyerInfoList.stream().map(LawyerInfo::getUserId).filter(Objects::nonNull).findFirst();
        userId.ifPresent(aLong -> adventEarlyWarning.addMessage(projectInfo.getId(), doType, title, content, aLong + "", "MESSAGE_TYPE_006", ""));
    }

    return Result.success();
  }

  @Override
  public Result<AllocationInfoDTO> findById(Long id) {
    AllocationInfo allocationInfo = handler.getById(id);
    if (null == allocationInfo) {
      String message = "分配/变更主表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    AllocationInfoDTO dto = PlatformMapUtils.getInstance().map(allocationInfo, AllocationInfoDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<AllocationInfoDTO>> findAll(Integer current, Integer size, AllocationInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<AllocationInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    //  分配暂时不控制权限
//    Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
//    request.setOrgUserIds(userLimitsOrg);
    List<AllocationInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<AllocationInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, AllocationInfoDTO.class);
//    for (AllocationInfoDTO dto : dtoList) {
//      Long id = dto.getId();
//      // 流程状态
//      Result<ProcessStatus> statusResult = processClient.getStatusByDoId(id);
//      if (null != statusResult && statusResult.succeedWithData()) {
//        dto.setProcessStatus(statusResult.getData());
//      }
//    }
    return Result.success(dtoList, PageFactory.page(page));
  }

}