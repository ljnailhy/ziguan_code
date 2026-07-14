package com.jk.asset.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jk.asset.constant.AssertConstants;
import com.jk.asset.enums.BigSmallEnum;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.DocumentTypeEnum;
import com.jk.asset.enums.IndustryPolicySupportEnum;
import com.jk.asset.enums.LitigationTypeEnum;
import com.jk.asset.enums.ProjectStateEnum;
import com.jk.asset.enums.RiskFundEnum;
import com.jk.asset.enums.SecurityTypeEnum;
import com.jk.asset.enums.SecurityWayEnum;
import com.jk.asset.enums.SubjectNatureEnum;
import com.jk.asset.enums.SubjectTypeEnum;
import com.jk.asset.mapper.ProjectInfoMapper;
import com.jk.asset.mapper.RecoveryPaymentMapper;
import com.jk.asset.mapper.SubjectInfoMapper;
import com.jk.asset.model.dto.ContractInfoDTO;
import com.jk.asset.model.dto.PayFeeDTO;
import com.jk.asset.model.dto.ProductNameDTO;
import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.asset.model.dto.ProjectWorkflowProcess;
import com.jk.asset.model.entity.AllocationInfoDetail;
import com.jk.asset.model.entity.BillContract;
import com.jk.asset.model.entity.BillLawyer;
import com.jk.asset.model.entity.CompensatorySync;
import com.jk.asset.model.entity.ContractInfo;
import com.jk.asset.model.entity.LawFirmInfo;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.entity.ProjectBusinessInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.ProjectInfoExt;
import com.jk.asset.model.entity.ProjectLoanInfo;
import com.jk.asset.model.entity.ProjectTransferDetailed;
import com.jk.asset.model.entity.RecoveryPayment;
import com.jk.asset.model.entity.RecoveryPaymentCollection;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.entity.SubjectInfo;
import com.jk.asset.model.entity.WriteOff;
import com.jk.asset.model.entity.proceeding.RecoveryAdjustTrial;
import com.jk.asset.model.entity.proceeding.RecoveryExecute;
import com.jk.asset.model.entity.proceeding.RecoveryJudgement;
import com.jk.asset.model.entity.proceeding.RecoveryLitigation;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationDetails;
import com.jk.asset.model.request.AntiRemarkRequest;
import com.jk.asset.model.request.ImportProjectRequest;
import com.jk.asset.model.request.ManageHistoryRecordRequest;
import com.jk.asset.model.request.ProjectBusinessInfoRequest;
import com.jk.asset.model.request.ProjectInfoRequest;
import com.jk.asset.model.request.ProjectLoanInfoRequest;
import com.jk.asset.model.request.RevePropertyInfoRequest;
import com.jk.asset.model.request.SubjectInfoRequest;
import com.jk.asset.model.request.SyncCompensatoryRequest;
import com.jk.asset.model.request.page.ProjectInfoPageRequest;
import com.jk.asset.service.ManageHistoryRecordService;
import com.jk.asset.service.ProjectInfoService;
import com.jk.asset.service.handler.AllocationInfoDetailHandler;
import com.jk.asset.service.handler.BillContractHandler;
import com.jk.asset.service.handler.BillLawyerHandler;
import com.jk.asset.service.handler.CompensatorySyncHandler;
import com.jk.asset.service.handler.ContractInfoHandler;
import com.jk.asset.service.handler.LawFirmInfoHandler;
import com.jk.asset.service.handler.LawyerInfoHandler;
import com.jk.asset.service.handler.ProjectBusinessInfoHandler;
import com.jk.asset.service.handler.ProjectInfoExtHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.ProjectLoanInfoHandler;
import com.jk.asset.service.handler.ProjectTransferDetailedHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionHandler;
import com.jk.asset.service.handler.RecoveryPaymentHandler;
import com.jk.asset.service.handler.RevePropertyInfoHandler;
import com.jk.asset.service.handler.SubjectInfoHandler;
import com.jk.asset.service.handler.WriteOffHandler;
import com.jk.asset.service.handler.proceeding.RecoveryAdjustTrialHandler;
import com.jk.asset.service.handler.proceeding.RecoveryExecuteHandler;
import com.jk.asset.service.handler.proceeding.RecoveryJudgementHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationDetailsHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationHandler;
import com.jk.asset.tasks.AdventEarlyWarning;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.model.entity.BaseDO;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysOrgDTO;
import com.jk.infrastructure.model.dto.SysRegionDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.infrastructure.model.request.sys.org.SysOrgPageRequest;
import com.jk.infrastructure.model.request.sys.region.SysRegionPageRequest;
import com.jk.infrastructure.model.request.sys.user.SysUserRequest;
import com.jk.service.client.ProcessClient;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.client.SysOrgClient;
import com.jk.service.client.SysRegionClient;
import com.jk.service.client.SysUserClient;
import com.jk.service.utils.PlatformFieldUtils;
import com.jk.service.utils.PlatformFileUtils;
import com.jk.service.utils.PlatformUserUtils;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 项目信息表接口实现类
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
@RestController
@Slf4j
@Api(tags = "项目信息表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectInfoServiceImpl implements ProjectInfoService {

    private final ProjectInfoMapper mapper;
    private final PlatformFieldUtils platformFieldUtils;
    private final ProjectInfoExtHandler projectInfoExtHandler;
    private final RevePropertyInfoHandler revePropertyInfoHandler;
    private final SubjectInfoMapper subjectInfoMapper;
    private final SubjectInfoHandler subjectInfoHandler;
    private final PlatformFileUtils platformFileUtils;
    private final SysDictionaryClient sysDictionaryClient;
    private final SysUserClient sysUserClient;
    private final SysRegionClient sysRegionClient;
    private final RecoveryPaymentCollectionHandler recoveryPaymentCollectionHandler;
    private final WriteOffHandler writeOffHandler;
    private final RecoveryPaymentHandler recoveryPaymentHandler;
    private final AllocationInfoDetailHandler allocationInfoDetailHandler;
    private final RecoveryLitigationHandler recoveryLitigationHandler;
    private final ProjectInfoHandler projectInfoHandler;
    private final RecoveryExecuteHandler recoveryExecuteHandler;
    private final RecoveryAdjustTrialHandler recoveryAdjustTrialHandler;
    private final ProjectTransferDetailedHandler projectTransferDetailedHandler;
    private final RecoveryJudgementHandler recoveryJudgementHandler;
    private final RecoveryLitigationDetailsHandler recoveryLitigationDetailsHandler;
    private final ProcessClient processClient;
    private final SysOrgClient sysOrgClient;
    private final CompensatorySyncHandler  compensatorySynchandler;
    private final ManageHistoryRecordService manageHistoryRecordService;
    private final ProjectLoanInfoHandler projectLoanInfoHandler;
    private final ProjectBusinessInfoHandler projectBusinessInfoHandler;
    private final BillLawyerHandler billLawyerHandler;
    private final AssetUserLimitsUtils assetUserLimitsUtils;
    private final RecoveryPaymentMapper recoveryPaymentMapper;
    private final ProjectInfoMapper projectInfoMapper;
    private final PlatformUserUtils platformUserUtils;
    private final LawyerInfoHandler lawyerInfoHandler;
    private final BillContractHandler billContractHandler;
    private final ContractInfoHandler contractInfoHandler;
    private final AdventEarlyWarning adventEarlyWarning;
    private final LawFirmInfoHandler lawFirmInfoHandler;

    @Override
    public Result<ProjectInfoDTO> add(ProjectInfoRequest request) {
        if (null == request || request.unverified()) {
            String message = "项目信息表新增 请求参数异常";
            log.info("{} request {}", message, request);
            return Result.error(message);
        }

        // 对象拷贝：request->DO
        ProjectInfo projectInfo = PlatformMapUtils.getInstance().map(request, ProjectInfo.class);
        SysUserDTO currentUser = platformUserUtils.getCurrentUser();
        projectInfo.setManage(currentUser.getId().toString());
        Result<List<SysOrgDTO>> all = findOrgId();
        if (all.succeed()){
            Long id = all.getData().get(0).getId();
            projectInfo.setAffiliatedOrg(id);
        }
//        projectInfo.setProjectName(request.getSubjectInfoRequest().getSubjectName());
        projectInfo.setDebtorId(request.getDebtorId());
        Optional<SysDictionaryItemDTO> state = sysDictionaryClient.findByCode(ProjectStateEnum.PROJECT_STATE.getKey())
                .getData()
                .getItems()
                .stream()
                .filter(item -> item.getItemCode().equals(DictEnum.PROJECT_STATE_01.getKey()))
                .findAny();
        state.ifPresent(sysDictionaryItemDTO -> projectInfo.setProjectState(sysDictionaryItemDTO.getId()));
        projectInfo.setId(null);
        projectInfo.setIsDeleted(false);
        projectInfo.setBelongProvince(request.getSubjectInfoRequest().getBelongProvince());
        projectInfo.setBelongCity(request.getSubjectInfoRequest().getBelongCity());
        projectInfo.setBelongDistrict(request.getSubjectInfoRequest().getBelongDistrict());
        Result<String> stringResult = checkDividedInsurance(request);
        if (!stringResult.succeed()) {
            return Result.error(stringResult.getErrorMessage());
        }
        projectInfoHandler.save(projectInfo);
        ProjectInfoDTO dto = new ProjectInfoDTO();
        dto.setId(projectInfo.getId());
        ProjectInfoExt projectInfoExt = PlatformMapUtils.getInstance().map(request, ProjectInfoExt.class);
        projectInfoExt.setId(dto.getId());
        projectInfo.setIsDeleted(false);
        projectInfoExtHandler.save(projectInfoExt);
        if (!request.getRevePropertyInfoRequest().isEmpty()) {
            List<RevePropertyInfoRequest> collect1 = request.getRevePropertyInfoRequest().stream().distinct().map(a -> a.setIsDeleted(false)).collect(Collectors.toList());
            List<RevePropertyInfo> collect = PlatformMapUtils.getInstance()
                    .mapAsList(collect1, RevePropertyInfo.class)
                    .stream()
                    .map(a -> a.setDoType(BillTypeEnum.PROJECT_INFO.getKey()).setDoId(projectInfo.getId()))
                    .collect(Collectors.toList());
            revePropertyInfoHandler.saveBatch(collect);
        }
        if (!ObjectUtils.isEmpty(request.getFileRequests())) {
            platformFileUtils.batchAddFile(request.getFileRequests(), dto.getId(), BillTypeEnum.PROJECT_INFO.getKey());
        }
        // 业务信息 借款信息

        List<ProjectLoanInfoRequest> projectLoanInfos = request.getProjectLoanInfos();
        List<ProjectBusinessInfoRequest> businessInfos = request.getBusinessInfos();
        if (!ObjectUtils.isEmpty(projectLoanInfos)){
            projectLoanInfoHandler.saveBatch(PlatformMapUtils.getInstance().mapAsList(projectLoanInfos, ProjectLoanInfo.class));
        }
        List<Long> userIds = new ArrayList<>();
        if (!ObjectUtils.isEmpty(businessInfos)){
            request.getBusinessInfos().forEach(a ->{
                a.setProjectId(dto.getId());
                if (ObjectUtils.isEmpty(a.getAid())){
                    userIds.add(a.getAid());
                }
                if (ObjectUtils.isEmpty(a.getBid())){
                    userIds.add(a.getBid());
                }
            });
            if (!userIds.isEmpty()) {
                Result<List<SysUserDTO>> byIds = sysUserClient.findByIds(userIds, false);
                List<SysUserDTO> data = byIds.getData();
                for (ProjectBusinessInfoRequest businessInfo : businessInfos) {
                    Long aid = businessInfo.getAid();
                    Long bid = businessInfo.getBid();
                    for (SysUserDTO datum : data) {
                        if (!ObjectUtils.isEmpty(aid)) {
                            if (datum.getId().equals(aid)) {
                                businessInfo.setAcode(datum.getAccount());
                                businessInfo.setAname(datum.getUsername());
                            }
                        }
                        if (!ObjectUtils.isEmpty(bid)) {
                            businessInfo.setAcode(datum.getAccount());
                            businessInfo.setAname(datum.getUsername());
                        }
                    }
                    getBusinessType(businessInfo);
                }
            }
            projectBusinessInfoHandler.saveBatch(PlatformMapUtils.getInstance().mapAsList(businessInfos, ProjectBusinessInfo.class));
        }
        // 发送提醒消息
        this.sendMessage(projectInfo.getId(),projectInfo.getProjectName());
        return Result.success(dto);
    }

    private void sendMessage(long projectId,String projectName){
        SysUserDTO currentUser = platformUserUtils.getCurrentUser();
        // 保存成功同时写入系统消息提醒保全部部长进行项目分配
        Result<SysOrgDTO> sysOrgDTOResult = sysOrgClient.findById(currentUser.getDeptId());
        String doType = DictEnum.MESSAGE_TYPE_005.getKey();
        String title = "【"+DictEnum.MESSAGE_TYPE_005.getValue()+"】"+projectName;
        String content = currentUser.getUsername()+"新增【"+projectName+"】追偿项目入库，请移步至未分配项目列表进行分配保全经理和律所。";
        String receiveUser = sysOrgDTOResult.getData().getDeptLeader()+"";
        if(org.apache.commons.lang3.ObjectUtils.isNotEmpty(projectId)){
            adventEarlyWarning.addMessage(projectId,doType,title,content,receiveUser,"MESSAGE_TYPE_005","");
        }
    }

    private Result<List<SysOrgDTO>> findOrgId() {
        SysOrgPageRequest sysOrgPageRequest = new SysOrgPageRequest();
        sysOrgPageRequest.setOrgName("资产保全部");
        sysOrgPageRequest.setOrgCode("ZXD_ZCBQB");
        return sysOrgClient.findAll(1, 1, sysOrgPageRequest);
    }

    @Override
    public Result<?> delete(Long id) {
        LambdaQueryWrapper<ProjectBusinessInfo>  businessInfoQuery = new LambdaQueryWrapper<>();
        businessInfoQuery.eq(ProjectBusinessInfo::getProjectId, id);
        List<ProjectBusinessInfo> businessInfos = projectBusinessInfoHandler.list(businessInfoQuery);
        if (!ObjectUtils.isEmpty(businessInfos)){
            List<Long> collect = businessInfos.stream().map(ProjectBusinessInfo::getId).collect(Collectors.toList());
            List<String> relateBusinessNoList = businessInfos.stream().map(ProjectBusinessInfo::getRelatedBusNo).collect(Collectors.toList());
            projectBusinessInfoHandler.removeByIds(collect);
            LambdaQueryWrapper<ProjectLoanInfo>  loanInfoQuery = new LambdaQueryWrapper<>();
            loanInfoQuery.in(ProjectLoanInfo::getRelatedBusNo,relateBusinessNoList);
            List<ProjectLoanInfo> loanList = projectLoanInfoHandler.list(loanInfoQuery);
            if (!ObjectUtils.isEmpty(loanList)){
                projectLoanInfoHandler.removeByIds(loanList.stream().map(ProjectLoanInfo::getId).collect(Collectors.toList()));
            }
            LambdaQueryWrapper<CompensatorySync>  syncLambdaQueryWrapper = new LambdaQueryWrapper<>();
            syncLambdaQueryWrapper.in(CompensatorySync::getRelatedBusNo,relateBusinessNoList);
            List<CompensatorySync> list = compensatorySynchandler.list(syncLambdaQueryWrapper);
            List<CompensatorySync> collect1 = list.stream().map(item -> item.setSyncStatus(false)).collect(Collectors.toList());
            compensatorySynchandler.saveOrUpdateBatch(collect1);
        }
        projectInfoHandler.removeById(id);
        return Result.success();
    }

    @Override
    public Result<ProjectInfoDTO> update(ProjectInfoRequest request) {
        if (null == request || null == request.getId()) {
            String message = "项目信息表修改 请求参数异常";
            log.info("{} request {}", message, request);
            return Result.error(message);
        }
        Result<String> stringResult = checkDividedInsurance(request);
        if (!stringResult.succeed()) {
            return Result.error(stringResult.getErrorMessage());
        }
        ProjectInfo projectInfo = PlatformMapUtils.getInstance().map(request, ProjectInfo.class);
        projectInfoMapper.updateById(projectInfo);
//        boolean b = projectInfoHandler.updateById(projectInfo);
        ProjectInfoExt projectInfoExt = PlatformMapUtils.getInstance().map(request, ProjectInfoExt.class);
        projectInfoExtHandler.updateById(projectInfoExt);
        ProjectInfoDTO dto = new ProjectInfoDTO();
        dto.setId(projectInfo.getId());
        List<RevePropertyInfoRequest> infoRequestList = request.getRevePropertyInfoRequest();
        projectInfo.setBelongProvince(request.getSubjectInfoRequest().getBelongProvince());
        projectInfo.setBelongCity(request.getSubjectInfoRequest().getBelongCity());
        projectInfo.setBelongDistrict(request.getSubjectInfoRequest().getBelongDistrict());

        List<Long> deleteList = new ArrayList<>();
        List<RevePropertyInfoRequest> updateList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(infoRequestList)) {
            infoRequestList.forEach(a -> a.setDoId(request.getId()));
            for (RevePropertyInfoRequest revePropertyInfoRequest : infoRequestList) {
                if (Objects.nonNull(revePropertyInfoRequest.getOperateType()) && revePropertyInfoRequest.getOperateType() == OperationTypeEnum.DELETE){
                    Long id = revePropertyInfoRequest.getId();
                    deleteList.add(id);
                }else {
                    revePropertyInfoRequest.setDoId(request.getId());
                    revePropertyInfoRequest.setDoType(BillTypeEnum.PROJECT_INFO.getKey());
                    updateList.add(revePropertyInfoRequest);
                }
            }
            if (!ObjectUtils.isEmpty(deleteList)){
                revePropertyInfoHandler.removeByIds(deleteList);
            }
            if (!ObjectUtils.isEmpty(updateList)){
                revePropertyInfoHandler.saveOrUpdateBatch(PlatformMapUtils.getInstance().mapAsList(updateList, RevePropertyInfo.class));

            }
        }
        List<ProjectBusinessInfoRequest> businessInfos = request.getBusinessInfos();
        List<Long> delList = new ArrayList<>();
        List<ProjectBusinessInfoRequest> updateList1 = new ArrayList<>();
        if (!ObjectUtils.isEmpty(request.getBusinessInfos())){
            for (ProjectBusinessInfoRequest businessInfo : businessInfos) {
                if (Objects.nonNull(businessInfo.getOperateType()) && businessInfo.getOperateType() == OperationTypeEnum.DELETE) {
                    delList.add(businessInfo.getId());
                } else {
                    updateList1.add(businessInfo);
                }
            }
            if (!ObjectUtils.isEmpty(delList)){
                projectBusinessInfoHandler.removeByIds(delList);
            }
            if (!ObjectUtils.isEmpty(updateList1)){
                List<Long> userIds = new ArrayList<>();
                updateList1.forEach(businessInfo ->{
                    businessInfo.setProjectId(request.getId());
                    if (ObjectUtils.isEmpty(businessInfo.getAid())){
                        userIds.add(businessInfo.getAid());
                    }
                    if (ObjectUtils.isEmpty(businessInfo.getBid())){
                        userIds.add(businessInfo.getBid());
                    }
                    getBusinessType(businessInfo);
                });
                if (!userIds.isEmpty()) {
                    Result<List<SysUserDTO>> byIds = sysUserClient.findByIds(userIds, false);
                    List<SysUserDTO> data = byIds.getData();
                    for (ProjectBusinessInfoRequest businessInfo : request.getBusinessInfos()) {
                        businessInfo.setProjectId(request.getId());
                        Long aid = businessInfo.getAid();
                        Long bid = businessInfo.getBid();
                        for (SysUserDTO datum : data) {
                            if (!ObjectUtils.isEmpty(aid)) {
                                if (datum.getId().equals(aid)) {
                                    businessInfo.setAcode(datum.getAccount());
                                    businessInfo.setAname(datum.getUsername());
                                }
                            }
                            if (!ObjectUtils.isEmpty(bid)) {
                                businessInfo.setAcode(datum.getAccount());
                                businessInfo.setAname(datum.getUsername());
                            }
                        }
                    }
                }
                projectBusinessInfoHandler.saveOrUpdateBatch(PlatformMapUtils.getInstance().mapAsList(updateList1, ProjectBusinessInfo.class));
            }
        }
        List<ProjectLoanInfoRequest> loanInfos = request.getProjectLoanInfos();
        List<Long> deleteLoanInfos = new ArrayList<>();
        if (!ObjectUtils.isEmpty(loanInfos)){
            for (ProjectLoanInfoRequest loan : loanInfos) {
                if (Objects.nonNull(loan.getOperateType()) && loan.getOperateType() == OperationTypeEnum.DELETE) {
                    deleteLoanInfos.add(loan.getId());
                }
            }
            projectLoanInfoHandler.saveOrUpdateBatch(PlatformMapUtils.getInstance().mapAsList(loanInfos, ProjectLoanInfo.class));
            if (!ObjectUtils.isEmpty(deleteLoanInfos)){
                projectLoanInfoHandler.removeByIds(deleteLoanInfos);
            }
        }
        if (!ObjectUtils.isEmpty(request.getFileRequests())) {
            platformFileUtils.batchUpdateFile(request.getFileRequests(), projectInfo.getId(), BillTypeEnum.PROJECT_INFO.getKey());
        }
        // 如果项目名称改变 核销 回款 付款的项目名称也要变
        LambdaQueryWrapper<WriteOff> writeOffLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<RecoveryPaymentCollection> collectionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<RecoveryPayment> paymentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        writeOffLambdaQueryWrapper.eq(WriteOff::getProjectId, request.getId());
        collectionLambdaQueryWrapper.eq(RecoveryPaymentCollection::getProjectId, request.getId());
        paymentLambdaQueryWrapper.eq(RecoveryPayment::getProjectId, request.getId());
        List<WriteOff> writeOffs = writeOffHandler.list(writeOffLambdaQueryWrapper);
        List<RecoveryPaymentCollection> recoveryPaymentCollections = recoveryPaymentCollectionHandler.list(collectionLambdaQueryWrapper);
        List<RecoveryPayment> paymentList = recoveryPaymentHandler.list(paymentLambdaQueryWrapper);
        if (!ObjectUtils.isEmpty(writeOffs)) {
            writeOffs.forEach(a -> a.setProjectName(request.getProjectName()));
            writeOffHandler.updateBatchById(writeOffs);
        }
        if (!ObjectUtils.isEmpty(recoveryPaymentCollections)) {
            recoveryPaymentCollections.forEach(a -> a.setProjectName(request.getProjectName()));
            recoveryPaymentCollectionHandler.updateBatchById(recoveryPaymentCollections);
        }
        if (!ObjectUtils.isEmpty(paymentList)) {
            paymentList.forEach(a -> a.setProjectName(request.getProjectName()));
            recoveryPaymentHandler.updateBatchById(paymentList);
        }
        return Result.success(dto);
    }

    private void getBusinessType(ProjectBusinessInfoRequest businessInfo) {
        Long productName = businessInfo.getProductName();
        List<SysDictionaryItemDTO> type = sysDictionaryClient.findByCode(DictEnum.TYPE.getKey())
                .getData().getItems();
        List<SysDictionaryItemDTO> onLine = sysDictionaryClient.findByCode(DictEnum.ON_LINE.getKey())
                .getData().getItems();
        onLine.forEach(a -> {
            if (a.getId().equals(productName)) {
                Long id = type.stream().filter(b -> b.getItemCode().equals(DictEnum.ON_LINE.getKey())).findAny().get().getId();
                businessInfo.setType(id);
            }
        });
        List<SysDictionaryItemDTO> offLine = sysDictionaryClient.findByCode(DictEnum.OFF_LINE.getKey())
                .getData().getItems();
        offLine.forEach(a -> {
            if (a.getId().equals(productName)) {
                businessInfo.setType(type.stream().filter(b -> b.getItemCode().equals(DictEnum.OFF_LINE.getKey())).findAny().get().getId());
            }
        });
        List<SysDictionaryItemDTO> traditional = sysDictionaryClient.findByCode(DictEnum.TRADITIONAL_PRODUCT.getKey())
                .getData().getItems();
        traditional.forEach(a -> {
            if (a.getId().equals(productName)) {
                businessInfo.setType(type.stream().filter(b -> b.getItemCode().equals(DictEnum.TRADITIONAL_PRODUCT.getKey())).findAny().get().getId());
            }
        });
    }

    private Result<String> checkDividedInsurance(ProjectInfoRequest request) {
        BigDecimal dividedInsuranceDebtor = request.getDividedInsuranceDebtor();
        BigDecimal dividedInsuranceOther = request.getDividedInsuranceOther();
        // 再担保
        BigDecimal dividedInsuranceAgainSecurity = request.getDividedInsuranceAgainSecurity();
        BigDecimal dividedInsuranceSecurity = request.getDividedInsuranceSecurity();
        if (ObjectUtils.isEmpty(dividedInsuranceDebtor) && ObjectUtils.isEmpty(dividedInsuranceOther)
                && ObjectUtils.isEmpty(dividedInsuranceSecurity) && ObjectUtils.isEmpty(dividedInsuranceAgainSecurity)) {
            return Result.success();
        }
        if (ObjectUtils.isEmpty(dividedInsuranceDebtor) || ObjectUtils.isEmpty(dividedInsuranceOther) || ObjectUtils.isEmpty(dividedInsuranceSecurity)) {
            return Result.error("分险比例信息未填写完整");
        }
        if (ObjectUtils.isEmpty(dividedInsuranceAgainSecurity)) {
            if (!ObjectUtils.isEmpty(dividedInsuranceDebtor) && !ObjectUtils.isEmpty(dividedInsuranceOther) && !ObjectUtils.isEmpty(dividedInsuranceSecurity)) {
                if (dividedInsuranceDebtor.add(dividedInsuranceOther).add(dividedInsuranceSecurity).compareTo(new BigDecimal(AssertConstants.CONSTANT_STR_ONE_HUNDRED)) != 0) {
                    return Result.error("分险比例（再担保)为空时,其余分险比例相加需等于100%!");
                }
            } else {
                return Result.error("分险比例信息未填写完整");
            }
        } else {
            if (dividedInsuranceDebtor.add(dividedInsuranceOther).add(dividedInsuranceSecurity).add(dividedInsuranceAgainSecurity).compareTo(new BigDecimal(AssertConstants.CONSTANT_STR_ONE_HUNDRED)) != 0) {
                return Result.error("分险比例（再担保)为空时,其余分险比例相加需等于100%!");
            }
        }
        return Result.success();
    }

    @Override
    public Result<ProjectInfoDTO> findById(Long id) {
        ProjectInfoDTO dto = mapper.findInfoById(id);
        if (null == dto) {
            String message = "项目信息表单个查找 该记录不存在";
            log.info("{} id {}", message, id);
            return Result.error(message);
        }
        // 对象拷贝：DO->DTO
//    ProjectInfoDTO dto = PlatformMapUtils.getInstance().map(projectInfo, ProjectInfoDTO.class);

        SubjectInfo subjectInfo = subjectInfoMapper.selectById(dto.getDebtorId());
        LambdaQueryWrapper<RevePropertyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RevePropertyInfo::getDoType, BillTypeEnum.PROJECT_INFO.getKey())
                .eq(RevePropertyInfo::getDoId, dto.getId())
                .eq(RevePropertyInfo::getIsDeleted, AssertConstants.CONSTANT_ZERO);
        List<RevePropertyInfo> list = revePropertyInfoHandler.list(wrapper);
        dto
                .setSubjectInfoRequest(PlatformMapUtils.getInstance().map(subjectInfo, SubjectInfoRequest.class))
                .setRevePropertyInfoRequest(PlatformMapUtils.getInstance().mapAsList(list, RevePropertyInfoRequest.class));
        if (!ObjectUtils.isEmpty(subjectInfo.getNature())) {
            dto.setNature(subjectInfo.getNature());
        }
        SysDictionaryItemDTO state = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
                .getData()
                .getItems()
                .stream()
                .filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey()))
                .findAny().get();
        LambdaQueryWrapper<RecoveryPaymentCollection> query = new LambdaQueryWrapper<>();
        query.eq(RecoveryPaymentCollection::getProjectId, dto.getId())
                .eq(RecoveryPaymentCollection::getCollectionStatus,state.getId());
        List<RecoveryPaymentCollection> list1 = recoveryPaymentCollectionHandler.list(query);
        BigDecimal amount = BigDecimal.ZERO;
        if (!ObjectUtils.isEmpty(list1)) {
            for (RecoveryPaymentCollection collection : list1) {
                BigDecimal collectionAmount = collection.getCollectionAmount();
                if (!ObjectUtils.isEmpty(collectionAmount)) {
                    amount = amount.add(collectionAmount);
                }
            }
            dto.setTotalCollectionAmount(amount);
            BigDecimal compensationMoney = dto.getCompensationMoney();
            if (compensationMoney.compareTo(amount) <= 0){
                dto.setWriteDffAmount(BigDecimal.ZERO);
            }else {
                BigDecimal subtract = compensationMoney.subtract(amount);
                dto.setWriteDffAmount(subtract);
            }
        }else {
            // 没有 回款记录 默认核销=代偿
            dto.setWriteDffAmount(dto.getCompensationMoney());
        }
        LambdaQueryWrapper<ProjectBusinessInfo> query2 = new LambdaQueryWrapper<>();
        query2.eq(ProjectBusinessInfo::getProjectId,id);
        List<ProjectBusinessInfo> businessInfos = projectBusinessInfoHandler.list(query2);
        query2.eq(ProjectBusinessInfo::getRelatedBusNo, id);
        if (!ObjectUtils.isEmpty(businessInfos)){
            dto.setBusinessInfos(PlatformMapUtils.getInstance().mapAsList(businessInfos, ProjectBusinessInfoRequest.class));
            LambdaQueryWrapper<ProjectLoanInfo> query1 = new LambdaQueryWrapper<>();
            query1.in(ProjectLoanInfo::getRelatedBusNo, businessInfos.stream().map(ProjectBusinessInfo::getRelatedBusNo).collect(Collectors.toList()));
            List<ProjectLoanInfo> loanInfos = projectLoanInfoHandler.list(query1);
            if (!ObjectUtils.isEmpty(loanInfos)){
                dto.setProjectLoanInfos(PlatformMapUtils.getInstance().mapAsList(loanInfos, ProjectLoanInfoRequest.class));
            }
        }
        LambdaQueryWrapper<BillLawyer> billQuery = new LambdaQueryWrapper<>();
        billQuery.eq(BillLawyer::getDoType,BillTypeEnum.PROJECT_INFO)
                .eq(BillLawyer::getDoId,id);
        List<BillLawyer> billList = billLawyerHandler.list(billQuery);
        if (!ObjectUtils.isEmpty(billList)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (BillLawyer billLawyer : billList) {
                if (ObjectUtils.isEmpty(stringBuilder)) {
                    stringBuilder.append(billLawyer.getLawyer());
                } else {
                    stringBuilder.append(",").append(billLawyer.getLawyer());
                }
            }
            dto.setLawyer(stringBuilder.toString());
        }

        return Result.success(dto);
    }

    @Override
    public Result<ProjectInfoDTO> changeManage(ProjectInfoRequest request) {
        if (null == request || null == request.getId() || StringUtils.isEmpty(request.getManage())) {
            String message = "修改保全经理失败 请求参数异常";
            log.info("{} request {}", message, request);
            return Result.error(message);
        }
        projectInfoHandler.update(new LambdaUpdateWrapper<ProjectInfo>()
            .eq(ProjectInfo::getId,request.getId())
            .set(ProjectInfo::getManage,request.getManage()));

        ManageHistoryRecordRequest manageHistoryRecordRequest = new ManageHistoryRecordRequest()
            .setProjectId(request.getId())
            .setManageStr(request.getManage());
        manageHistoryRecordService.add(manageHistoryRecordRequest);

        return Result.success();
    }

    @Override
    public Result<List<ProjectInfoDTO>> findByIds(List<Long> idList) {
        if (ObjectUtils.isEmpty(idList)) {
            return Result.success(new ArrayList<>());
        }

        List<ProjectInfo> list = projectInfoHandler.list(new LambdaQueryWrapper<ProjectInfo>()
                .in(ProjectInfo::getId, idList));

        if (ObjectUtils.isEmpty(list)) {
            return Result.success(new ArrayList<>());
        }

        List<ProjectInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, ProjectInfoDTO.class);

        return Result.success(dtoList);
    }

    @Override
    public Result<List<ProjectInfoDTO>> findAll(Integer current, Integer size, ProjectInfoPageRequest request) {
        if (PlatformStringUtils.isNotBlank(request.getField())) {
            request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
        }

        //增加权限过滤
        Set<Long> userLimitsOrg = request.getOrgUserIds();
        if (org.apache.commons.lang3.ObjectUtils.isEmpty(userLimitsOrg)) {
            request.setOrgUserIds(assetUserLimitsUtils.getUserLimitsOrg());
        }
        if (request.getIsLawyer() != null) {
            if (request.getIsLawyer()) {
                request.setOrgUserIds(null);
            }
        }
        // Step1：创建一个 Page 对象
        IPage<ProjectInfoDTO> page = new Page<>(current, size);
        // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
        List<ProjectInfoDTO> doList = mapper.findAll(page, request);
        // 空则直接返回
        if (CollectionUtils.isEmpty(doList)) {
            return Result.success(Lists.newArrayList(), PageFactory.page(page));
        }
        List<Long> collect2 = doList.stream().map(ProjectInfoRequest::getId).collect(Collectors.toList());
        LambdaQueryWrapper<BillLawyer> billQuery = new LambdaQueryWrapper<>();
        billQuery.eq(BillLawyer::getDoType,BillTypeEnum.PROJECT_INFO)
                .eq(BillLawyer::getIsEffective,Boolean.TRUE)
                .in(BillLawyer::getDoId,collect2);
        List<BillLawyer> billList = billLawyerHandler.list(billQuery);
        if (!ObjectUtils.isEmpty(billList)){
            Map<Long, List<BillLawyer>> collect = billList.stream().collect(Collectors.groupingBy(BillLawyer::getDoId));
            for (ProjectInfoDTO infoDTO : doList) {
                Long projectId = infoDTO.getId();
                List<BillLawyer> billLawyers = collect.get(projectId);
                StringBuilder stringBuilder = new StringBuilder();
                if (!ObjectUtils.isEmpty(billLawyers)) {
                    for (BillLawyer billLawyer : billLawyers) {
                        if (ObjectUtils.isEmpty(stringBuilder)) {
                            stringBuilder.append(billLawyer.getLawyer());
                        } else {
                            if (!stringBuilder.toString().contains(billLawyer.getLawyer().toString())) {
                                stringBuilder.append(",").append(billLawyer.getLawyer());
                            }
                        }
                    }
                }
                infoDTO.setLawyer(stringBuilder.toString());
            }
        }
        // Step3：获取分页数据
        List<ProjectInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, ProjectInfoDTO.class);
        if (dtoList.isEmpty()) {
            return Result.success(dtoList, PageFactory.page(page));
        }
        List<SubjectInfo> subjectInfos = subjectInfoHandler.listByIds(dtoList.stream().map(ProjectInfoRequest::getDebtorId).collect(Collectors.toList()));
        LambdaQueryWrapper<RevePropertyInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RevePropertyInfo::getDoType, BillTypeEnum.PROJECT_INFO.getKey())
                .in(RevePropertyInfo::getDoId, dtoList.stream().map(ProjectInfoRequest::getId).collect(Collectors.toList()))
                .eq(RevePropertyInfo::getIsDeleted, AssertConstants.CONSTANT_ZERO);
        List<RevePropertyInfo> propertyInfoList = revePropertyInfoHandler.list(wrapper);
        if (!subjectInfos.isEmpty()) {
            Map<Long, SubjectInfo> collect = subjectInfos.stream().collect(Collectors.toMap(SubjectInfo::getId, subjectInfo -> subjectInfo));
            dtoList.forEach(a -> {
                SubjectInfo subjectInfo = collect.get(a.getDebtorId());
                if (subjectInfo != null) {
                    SubjectInfoRequest subjectInfoRequest = PlatformMapUtils.getInstance().map(subjectInfo, SubjectInfoRequest.class);
                    if (subjectInfoRequest != null) {
                        a.setNature(subjectInfo.getNature());
                        a.setSubjectInfoRequest(subjectInfoRequest);
                    }
                }
            });
        }
        if (!propertyInfoList.isEmpty()) {
            Map<Long, List<RevePropertyInfo>> collect = propertyInfoList.stream().collect(Collectors.groupingBy(RevePropertyInfo::getDoId));
            for (ProjectInfoDTO dto : dtoList) {
                List<RevePropertyInfo> revePropertyInfos = collect.get(dto.getId());
                if (!ObjectUtils.isEmpty(revePropertyInfos)) {
                    List<RevePropertyInfoRequest> infoRequestList = PlatformMapUtils.getInstance().mapAsList(revePropertyInfos, RevePropertyInfoRequest.class);
                    dto.setRevePropertyInfoRequest(infoRequestList);
                }
            }
        }
        List<ProjectInfoDTO> resList = new ArrayList<>();
        for (int i = 0; i < dtoList.size(); i++) {
            Long productName = dtoList.get(i).getProductName();
            if (!ObjectUtils.isEmpty(productName)) {
                int finalI = i;
                Optional<SysDictionaryItemDTO> onLine = sysDictionaryClient.findByCode(DictEnum.ON_LINE.getKey()).getData().getItems().stream().filter(item -> item.getId().equals(dtoList.get(finalI).getProductName())).findAny();
                Optional<SysDictionaryItemDTO> offLine = sysDictionaryClient.findByCode(DictEnum.OFF_LINE.getKey()).getData().getItems().stream().filter(item -> item.getId().equals(dtoList.get(finalI).getProductName())).findAny();
                Optional<SysDictionaryItemDTO> traditional = sysDictionaryClient.findByCode(DictEnum.TRADITIONAL_PRODUCT.getKey()).getData().getItems().stream().filter(item -> item.getId().equals(dtoList.get(finalI).getProductName())).findAny();
                onLine.ifPresent(sysDictionaryItemDTO -> dtoList.get(finalI).setProduct(sysDictionaryItemDTO.getItemName()));
                offLine.ifPresent(sysDictionaryItemDTO -> dtoList.get(finalI).setProduct(sysDictionaryItemDTO.getItemName()));
                traditional.ifPresent(sysDictionaryItemDTO -> dtoList.get(finalI).setProduct(sysDictionaryItemDTO.getItemName()));
            }
            resList.add(dtoList.get(i));
        }
           // 借款信息 业务信息
        List<Long> collect = resList.stream().map(ProjectInfoRequest::getId).collect(Collectors.toList());
        LambdaQueryWrapper<ProjectBusinessInfo> businessInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        businessInfoLambdaQueryWrapper.in(ProjectBusinessInfo::getProjectId, collect);
        List<ProjectBusinessInfo> list = projectBusinessInfoHandler.list(businessInfoLambdaQueryWrapper);
        if (!ObjectUtils.isEmpty(list)) {
            LambdaQueryWrapper<ProjectLoanInfo> loanWrapper = new LambdaQueryWrapper<>();
            List<String> collect1 = list.stream().map(ProjectBusinessInfo::getRelatedBusNo).collect(Collectors.toList());
            loanWrapper.in(ProjectLoanInfo::getRelatedBusNo, collect1);
            List<ProjectLoanInfo> loanInfos = projectLoanInfoHandler.list(loanWrapper);
            for (ProjectInfoDTO infoDTO : resList) {
                List<ProjectLoanInfo> loanList = new ArrayList<>();
                List<ProjectBusinessInfo> busList = new ArrayList<>();
                if (!ObjectUtils.isEmpty(busList)){
                    infoDTO.setBusinessInfos(PlatformMapUtils.getInstance().mapAsList(busList, ProjectBusinessInfoRequest.class));
                }
                if (!ObjectUtils.isEmpty(loanInfos)){
                    for (ProjectBusinessInfo businessInfo : list) {
                        if (infoDTO.getId().equals(businessInfo.getProjectId())){
                            String relatedBusNo = businessInfo.getRelatedBusNo();
                            for (ProjectLoanInfo loanInfo : loanInfos) {
                                if (loanInfo.getRelatedBusNo().equals(relatedBusNo)){
                                    loanList.add(loanInfo);
                                }
                            }
                        }
                    }
                }
                infoDTO.setProjectLoanInfos(PlatformMapUtils.getInstance().mapAsList(loanList, ProjectLoanInfoRequest.class));
            }
        }
        // 剩余反担保措施 律师费 剩余代偿余额 累计回款
        // 律所Id
        List<Long> collect1 = resList.stream().map(ProjectInfoRequest::getLawFirmId).collect(Collectors.toList());
        SysDictionaryItemDTO payStatus = sysDictionaryClient.findByCode(DictEnum.PAY_STATUS.getKey())
                .getData()
                .getItems()
                .stream()
                .filter(item -> item.getItemCode().equals(DictEnum.PAY_STATUS_002.getKey()))
                .findAny().get();
        SysDictionaryItemDTO payType = sysDictionaryClient.findByCode(DictEnum.PAY_TYPE.getKey())
                .getData()
                .getItems()
                .stream()
                .filter(item -> "PAY_TYPE_005".equals(item.getItemCode()))
                .findAny().get();
         List<PayFeeDTO> payFeeList= recoveryPaymentMapper.findFeeByLawFirmId(payStatus.getId(), collect1,payType.getId());
         if (!ObjectUtils.isEmpty(payFeeList)){
             Map<Long, List<PayFeeDTO>> collect3 = payFeeList.stream().collect(Collectors.groupingBy(PayFeeDTO::getLawyerId));
             for (ProjectInfoDTO infoDTO : resList) {
                 Long id = infoDTO.getId();
                 Long lawFirmId = infoDTO.getLawFirmId();
                 BigDecimal totalPayAmount = BigDecimal.ZERO;
                 List<PayFeeDTO> payFeeDTOS = collect3.get(lawFirmId);
                 if (!ObjectUtils.isEmpty(payFeeDTOS)){
                     for (PayFeeDTO payFeeDTO : payFeeDTOS) {
                         if (payFeeDTO.getProjectId().equals(id)){
                             BigDecimal payAmount = payFeeDTO.getPayAmount();
                             if (!ObjectUtils.isEmpty(payAmount)){
                                 totalPayAmount = totalPayAmount.add(payAmount);
                             }
                         }
                     }
                 }
                 infoDTO.setLawFee(totalPayAmount);
             }
         }
        SysDictionaryItemDTO state = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
                .getData()
                .getItems()
                .stream()
                .filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey()))
                .findAny().get();
        LambdaQueryWrapper<RecoveryPaymentCollection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RecoveryPaymentCollection::getCollectionStatus, state.getId())
                .in(RecoveryPaymentCollection::getLawyerId,collect1);
        List<RecoveryPaymentCollection> recoveryPaymentCollections = recoveryPaymentCollectionHandler.list(queryWrapper);
        if (!ObjectUtils.isEmpty(recoveryPaymentCollections)){
            Map<Long, List<RecoveryPaymentCollection>> collect3 = recoveryPaymentCollections.stream().collect(Collectors.groupingBy(RecoveryPaymentCollection::getLawyerId));
            for (ProjectInfoDTO infoDTO : resList) {
                Long lawFirmId = infoDTO.getLawFirmId();
                Long id = infoDTO.getId();
                BigDecimal totalCollectionAmount = BigDecimal.ZERO;
                if (!ObjectUtils.isEmpty(lawFirmId)){
                    List<RecoveryPaymentCollection> paymentCollectionList = collect3.get(lawFirmId);
                    if (!ObjectUtils.isEmpty(paymentCollectionList)){
                        for (RecoveryPaymentCollection collection : paymentCollectionList) {
                            Long projectId = collection.getProjectId();
                            if (Objects.equals(projectId, id)){
                                totalCollectionAmount = totalCollectionAmount.add(collection.getCollectionAmount());
                            }
                        }
                    }
                }
                infoDTO.setTotalCollectionAmount(totalCollectionAmount);
                BigDecimal compensationMoney = infoDTO.getCompensationMoney();
                infoDTO.setResidueRecoveryAmount(compensationMoney.subtract(totalCollectionAmount));
            }
        }
        for (ProjectInfoDTO infoDTO : resList) {
            List<RevePropertyInfoRequest> revePropertyInfoRequest = infoDTO.getRevePropertyInfoRequest();
            if (ObjectUtils.isEmpty(revePropertyInfoRequest)){
                infoDTO.setResidueReveMeasureNum(0);
            }else {
                //infoDTO.setResidueReveMeasureNum((int) revePropertyInfoRequest.stream().filter(item -> item.getIsDispose() != null).filter(RevePropertyInfoRequest::getIsDispose).count());
                infoDTO.setResidueReveMeasureNum((int) revePropertyInfoRequest.stream().filter(item -> item.getIsDispose() != null).filter(item -> !item.getIsDispose()).count());
            }
//            BigDecimal residueRecoveryAmount = infoDTO.getResidueRecoveryAmount();
//            BigDecimal totalCollectionAmount = infoDTO.getTotalCollectionAmount();
//            BigDecimal lawFee = infoDTO.getLawFee();
//            if (residueRecoveryAmount != null){
//                infoDTO.setResidueRecoveryAmount(infoDTO.getResidueRecoveryAmount().divide(BigDecimal.valueOf(10000),6, RoundingMode.HALF_UP));
//            }
//            if (totalCollectionAmount != null){
//                infoDTO.setTotalCollectionAmount(infoDTO.getTotalCollectionAmount().divide(BigDecimal.valueOf(10000),6, RoundingMode.HALF_UP));
//            }
//            if (lawFee != null){
//                infoDTO.setLawFee(infoDTO.getLawFee().divide(BigDecimal.valueOf(10000),6, RoundingMode.HALF_UP));
//            }
        }
        return Result.success(resList, PageFactory.page(page));
    }

    @Override
    public Result<List<ProjectInfoDTO>> findLimitsAll(Integer current, Integer size, ProjectInfoPageRequest request) {
        Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
        request.setOrgUserIds(userLimitsOrg);
        SysUserDTO currentUser = platformUserUtils.getCurrentUser();
        List<LawyerInfo> lawyerInfos = lawyerInfoHandler.list(new LambdaQueryWrapper<LawyerInfo>()
                .eq(LawyerInfo::getUserId,currentUser.getId()));
        if (CollectionUtils.isNotEmpty(lawyerInfos)){
            // 不为空 说明 要分配律师权限
            request.setOrgUserIds(null);
            Long lawFirmId = lawyerInfos.get(0).getLawFirmId();
            request.setLawFirmId(lawFirmId);
            request.setIsLawyer(true);
        }
        return findAll(current,size,request);
    }

    @Override
    public Result<String> syncCompensatory(SyncCompensatoryRequest syncCompensatoryRequest) {
        LambdaQueryWrapper<CompensatorySync> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CompensatorySync::getCredentialNo, syncCompensatoryRequest.getParams())
                .eq(CompensatorySync::getSyncStatus,false);
        List<CompensatorySync> compensatoryList = compensatorySynchandler.list(queryWrapper);
        if (ObjectUtils.isEmpty(compensatoryList)){
            return Result.success();
        }
        boolean present = compensatoryList.stream().anyMatch(CompensatorySync::getSyncStatus);
        if (present){
            return Result.error("数据已同步，请刷新页面");
        }
        // 证件号
        List<String> documentCodeList = compensatoryList.stream().map(CompensatorySync::getCredentialNo).distinct().collect(Collectors.toList());
        List<String> repeatDocumentCode = subjectInfoMapper.findRepeatDocumentCode(documentCodeList);
        if (CollectionUtils.isNotEmpty(repeatDocumentCode) && syncCompensatoryRequest.getIsSync() == null){
            return Result.error(0,"证件号:" + String.join(",", repeatDocumentCode) + "已存在项目,是否继续同步");
        }
        if (syncCompensatoryRequest.getIsSync() != null && !syncCompensatoryRequest.getIsSync()){
            compensatoryList = compensatoryList.stream().filter(item -> !repeatDocumentCode.contains(item.getCredentialNo())).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(compensatoryList)){
            return Result.success();
        }
        Result<List<SysOrgDTO>> all = findOrgId();
        Optional<SysDictionaryItemDTO> state = sysDictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey())
                .getData()
                .getItems()
                .stream()
                .filter(item -> item.getItemCode().equals(DictEnum.PROJECT_STATE_01.getKey()))
                .findAny();
        SysUserDTO currentUser = platformUserUtils.getCurrentUser();
        Map<String, List<CompensatorySync>> collect = compensatoryList.stream().collect(Collectors.groupingBy(CompensatorySync::getCredentialNo));

        LambdaQueryWrapper<SubjectInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(SubjectInfo::getDocumentNumber);
        List<SubjectInfo> oldSubjectInfoList = subjectInfoHandler.list();
        List<String>  subjectInfoCollect = null;
        if (!ObjectUtils.isEmpty(oldSubjectInfoList)){
            subjectInfoCollect = oldSubjectInfoList.stream().map(SubjectInfo::getDocumentNumber).collect(Collectors.toList());
        }

        List<SysDictionaryItemDTO> projectOnLine = sysDictionaryClient.findByCode(DictEnum.OFF_LINE.getKey()).getData().getItems();
        List<SysDictionaryItemDTO> projectOffLine = sysDictionaryClient.findByCode(DictEnum.ON_LINE.getKey()).getData().getItems();
        List<SysDictionaryItemDTO> projectTraditional = sysDictionaryClient.findByCode(DictEnum.TRADITIONAL_PRODUCT.getKey()).getData().getItems();
        List<SysDictionaryItemDTO> rist = sysDictionaryClient.findByCode(BillTypeEnum.RIST_TYPE_AFTER_GUARANTEE.getKey())
                .getData()
                .getItems();
        List<SysDictionaryItemDTO> riskFund = sysDictionaryClient.findByCode(RiskFundEnum.RISK_FUND.getKey())
                .getData()
                .getItems();
        List<SysDictionaryItemDTO> bank = sysDictionaryClient.findByCode(DictEnum.COOPERATE_BANK.getKey())
                .getData()
                .getItems();
        List<SysDictionaryItemDTO> items1 = sysDictionaryClient.findByCode(DictEnum.TYPE.getKey()).getData().getItems();
        List<SysDictionaryItemDTO> gxb = sysDictionaryClient.findByCode(DictEnum.INDUSTRY_GXB.getKey()).getData().getItems();
        List<ProjectInfoRequest> projectInfoList = new ArrayList<>();
        List<SubjectInfo> subjectInfoList = new ArrayList<>();
        // 借款信息
        List<ProjectLoanInfoRequest> loanInfos = new ArrayList<>();
        for (Map.Entry<String, List<CompensatorySync>> stringListEntry : collect.entrySet()) {
            CompensatorySync value = stringListEntry.getValue().get(0);
            List<CompensatorySync> valueList = stringListEntry.getValue();
            // 身份证号
            ProjectInfoRequest ext = new ProjectInfoRequest();
            SubjectInfo subjectInfo = new SubjectInfo();
            // 行业分类
            // 关联企业 关联社会信用统一代码
            ext.setRelationEnterpriseNo(value.getRelationEnterpriseNo());
            ext.setRelationEnterprise(value.getRelationEnterprise());
            ext.setDocumentNumber(value.getCredentialNo());
            ext.setTransferDate(new Date());

            String support = value.getIndustryPolicySupport();
            List<SysDictionaryItemDTO> items = sysDictionaryClient.findByCode(IndustryPolicySupportEnum.INDUSTRY_POLICY_SUPPORT.getKey())
                    .getData()
                    .getItems();
            if (!ObjectUtils.isEmpty(support)){
                for (SysDictionaryItemDTO item : items) {
                    String[] split = support.split(",");
                    for (String s : split) {
                        if (item.getItemCode().equals(s)){
                            if (ObjectUtils.isEmpty(ext.getIndustryType())){
                                ext.setGoverType(item.getId().toString());
                            }else {
                                ext.setGoverType(ext.getGoverType() == null ? "" : ext.getGoverType() + "," + item.getId().toString());
                            }
                        }
                    }
                }
            }
            String industryGxb = value.getIndustryGxb();
            for (SysDictionaryItemDTO sysDictionaryItemDTO : gxb) {
                if (industryGxb.equals(sysDictionaryItemDTO.getItemCode())){
                    ext.setIndustryType(sysDictionaryItemDTO.getId());
                }
            }
            // 企业划型
            if (!value.getEnterpriseSize().isEmpty()){
                SysDictionaryItemDTO bigSmall = sysDictionaryClient.findByCode(BigSmallEnum.BIG_SMALL.getKey()).getData().getItems().stream().filter(item -> item.getItemCode().equals(value.getEnterpriseSize())).findAny().get();
                ext.setBigSmall(bigSmall.getId());
            }
            if (all.succeed()){
                // 部门组织
                ext.setAffiliatedOrg(all.getData().get(0).getId());
            }
            // 项目状态
            state.ifPresent(sysDictionaryItemDTO -> ext.setProjectState(sysDictionaryItemDTO.getId()));
            // 省市区
//            ext.setBelongProvince(value.getProvince());
//            ext.setBelongCity(value.getCity());
//            ext.setBelongDistrict(value.getArea());
            ext.setProjectName(value.getCustomerName());
            // 默认保全经理 创建人
            ext.setManage(currentUser.getId().toString());
            // 统计总的代偿金额 最早代偿时间
            BigDecimal amount = BigDecimal.ZERO;
            for (CompensatorySync compensatorySync : valueList) {
                if (!ObjectUtils.isEmpty(compensatorySync.getRepaymentAmount())){
                    amount = amount.add(compensatorySync.getRepaymentAmount());
                }
            }
            ext.setCompensationMoney(amount);
            Optional<CompensatorySync> earliestRepayment = valueList.stream()
                    .min(Comparator.comparing(CompensatorySync::getRepaymentDate));
            earliestRepayment.ifPresent(sync -> ext.setCompensationDate(sync.getRepaymentDate()));
            Date compensationDate = ext.getCompensationDate();
            if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(compensationDate)){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(compensationDate);
                calendar.add(Calendar.YEAR, 3);
                ext.setProceedingAgeingDate(calendar.getTime());
            }
            List<RevePropertyInfoRequest> revePropertyInfoRequest = new ArrayList<>();
            // 业务信息
            List<ProjectBusinessInfoRequest> businessInfos = new ArrayList<>();
            Map<String, List<CompensatorySync>> relatedBusList = valueList.stream().collect(Collectors.groupingBy(CompensatorySync::getRelatedBusNo));
            if (!ObjectUtils.isEmpty(relatedBusList)){
                relatedBusList.forEach((key, data) -> {
                    CompensatorySync compensatorySync = data.get(0);
                    ProjectBusinessInfoRequest businessInfoRequest = new ProjectBusinessInfoRequest();
                    businessInfoRequest.setRelatedBusNo(compensatorySync.getRelatedBusNo());
                    businessInfoRequest.setBusinessType(ProjectBusinessInfoRequest.BusinessTypeEnum.valueOf(compensatorySync.getBusinessType()));
                        for (SysDictionaryItemDTO item : projectOnLine) {
                            if (item.getItemCode().equals(compensatorySync.getProductName())){
                                businessInfoRequest.setProductName(item.getId());
                                for (SysDictionaryItemDTO sysDictionaryItemDTO : items1) {
                                    if (sysDictionaryItemDTO.getItemCode().equals(DictEnum.ON_LINE.getKey())){
                                        businessInfoRequest.setType(sysDictionaryItemDTO.getId());
                                        break;
                                    }
                                }
                            }
                        }
                    if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(compensatorySync.getProductName())){
                        for (SysDictionaryItemDTO item : projectOffLine) {
                            if (item.getItemCode().equals(compensatorySync.getProductName())){
                                businessInfoRequest.setProductName(item.getId());
                                for (SysDictionaryItemDTO sysDictionaryItemDTO : items1) {
                                    if (sysDictionaryItemDTO.getItemCode().equals(DictEnum.OFF_LINE.getKey())){
                                        businessInfoRequest.setType(sysDictionaryItemDTO.getId());
                                    }
                                }
                            }
                        }
                        for (SysDictionaryItemDTO item : projectTraditional) {
                            if (item.getItemCode().equals(compensatorySync.getProductName())){
                                businessInfoRequest.setProductName(item.getId());
                                for (SysDictionaryItemDTO sysDictionaryItemDTO : items1) {
                                    if (sysDictionaryItemDTO.getItemCode().equals(DictEnum.TRADITIONAL_PRODUCT.getKey())){
                                        businessInfoRequest.setType(sysDictionaryItemDTO.getId());
                                    }
                                }
                            }
                        }
                    }
                    rist.forEach(a -> {if (a.getItemCode().equals(compensatorySync.getRistTypeAfterGuarantee())) {businessInfoRequest.setRistTypeAfterGuarantee(a.getId());}});
                    businessInfoRequest.setDividedInsuranceOther(compensatorySync.getRiskRateOther());
                    businessInfoRequest.setDividedInsuranceDebtor(compensatorySync.getRiskRateBank());
                    businessInfoRequest.setDividedInsuranceSecurity(compensatorySync.getRiskRateOrigin());
                    businessInfoRequest.setDividedInsuranceAgainSecurity(compensatorySync.getRiskRateAgain());

                    riskFund.forEach(a ->{if (a.getItemCode().equals(compensatorySync.getRiskFund())){businessInfoRequest.setRiskCompensation(a.getId());}});
                    String aName = compensatorySync.getAname();
                    String aCode = compensatorySync.getAcode();
                    String bName = compensatorySync.getBname();
                    String bCode = compensatorySync.getBcode();
                    businessInfoRequest.setAname(aName);
                    businessInfoRequest.setAcode(aCode);
                    businessInfoRequest.setBname(bName);
                    businessInfoRequest.setBcode(bCode);
                    Result<SysUserDTO> userA = sysUserClient.findByAccount(aCode);
                    if (!ObjectUtils.isEmpty(aCode)) {
                        if (ObjectUtils.isEmpty(userA.getData())) {
                            SysUserRequest sysUserRequest = new SysUserRequest();
                            sysUserRequest.setAccount(aCode).setUsername(aName).setDeptId(all.getData().get(0).getId()).setCompanyId(currentUser.getCompanyId());
                            Result<SysUserDTO> added = sysUserClient.add(sysUserRequest);
                            businessInfoRequest.setAid(added.getData().getId());
                        } else {
                            businessInfoRequest.setAid(userA.getData().getId());
                        }
                    }
                    if (!ObjectUtils.isEmpty(bCode)) {
                        Result<SysUserDTO> userB = sysUserClient.findByAccount(bCode);
                        if (ObjectUtils.isEmpty(userB.getData())) {
                            SysUserRequest sysUserRequest = new SysUserRequest();
                            sysUserRequest.setAccount(bCode).setUsername(bName).setDeptId(all.getData().get(0).getId()).setCompanyId(currentUser.getCompanyId());
                            Result<SysUserDTO> added = sysUserClient.add(sysUserRequest);
                            businessInfoRequest.setBid(added.getData().getId());
                        } else {
                            businessInfoRequest.setBid(userB.getData().getId());
                        }
                    }
                    businessInfos.add(businessInfoRequest);
                    data.forEach(loan -> {
                        ProjectLoanInfoRequest loanInfoRequest = new ProjectLoanInfoRequest();
                        loanInfoRequest.setLoanCode(loan.getDebtBillNo());
                        loanInfoRequest.setDebtBeginDate(loan.getDebtBillStartDay());
                        loanInfoRequest.setDebtEndDate(loan.getDebtBillDueDay());
                        loanInfoRequest.setLoanMoney(loan.getDebtAmount());
                        loanInfoRequest.setLoanRate(loan.getLoanRate());
                        loanInfoRequest.setLoanPactCode(loan.getPrincipleClaimContractNo());
                        loanInfoRequest.setPledPactCode(loan.getWarrantyContractNo());
                        loanInfoRequest.setPactCode(loan.getGuaranteeContractNo());
                        loanInfoRequest.setGuaranteeRate(loan.getGuaranteeFeeRate());
                        loanInfoRequest.setRelatedBusNo(loan.getRelatedBusNo());
                        loanInfoRequest.setCompensationMoney(loan.getRepaymentAmount());
                        loanInfoRequest.setCompensationDate(loan.getRepaymentDate());
                        String cooperativeBank = loan.getCooperativeBank();
                        if (!ObjectUtils.isEmpty(cooperativeBank)){
                            bank.forEach(a -> {
                                if (a.getItemCode().equals(cooperativeBank)){
                                    loanInfoRequest.setCooperateBank(a.getId());
                                }
                            });
                        }
                        loanInfoRequest.setCooperateBankBranch(loan.getCooperativeBankThird());
                        loanInfoRequest.setIsFirstLoanAccount(loan.getIsFirstLoanAccount());
                        loanInfos.add(loanInfoRequest);
                    });
                });
            }
            ext.setBusinessInfos(businessInfos);
            for (CompensatorySync comSync : valueList) {
                if (!ObjectUtils.isEmpty(comSync.getAntiRemark()) && !"[]".equals(comSync.getAntiRemark())) {
                    //反担保措施有值
                    String antiRemark = comSync.getAntiRemark();
                    List<AntiRemarkRequest> antiRemarkRequests = JSON.parseArray(antiRemark, AntiRemarkRequest.class);
                    for (AntiRemarkRequest antiRemarkRequest : antiRemarkRequests) {
                        RevePropertyInfoRequest revePropertyInfo = new RevePropertyInfoRequest();
                        revePropertyInfo.setDoType(BillTypeEnum.PROJECT_INFO.getKey());
                        revePropertyInfo.setBillType(BillTypeEnum.REVE.getKey());
                        revePropertyInfo.setReveMeasure(antiRemarkRequest.getREMARK());
                        revePropertyInfo.setSecurityType(antiRemarkRequest.getGuarantee_type());
                        revePropertyInfo.setReveName(antiRemarkRequest.getGuarantee_name());
                        if (antiRemarkRequest.getSort() != null) {
                            Optional<SysDictionaryItemDTO> way = sysDictionaryClient.findByCode(SecurityWayEnum.SECURITY_WAY.getKey())
                                    .getData()
                                    .getItems()
                                    .stream()
                                    .filter(item -> item.getItemCode().equals(String.valueOf(antiRemarkRequest.getSort())))
                                    .findAny();
                            way.ifPresent(sysDictionaryItemDTO -> revePropertyInfo.setSecurityWay(sysDictionaryItemDTO.getId()));
                        }
                        revePropertyInfo.setSourceId(Long.valueOf(antiRemarkRequest.getBusiness_id()));
                        revePropertyInfoRequest.add(revePropertyInfo);
                    }
                }

            }
            // 主体信息表
            subjectInfo.setSubjectName(value.getCustomerName());
            if (!value.getCustomerProperty().isEmpty()){
                subjectInfo.setNature(SubjectNatureEnum.valueOf(value.getCustomerProperty()));
            }
            subjectInfo.setSubjectType(SubjectTypeEnum.valueOf(SubjectTypeEnum.DEBTOR.getKey()));
            String credentialType = value.getCredentialType();
            if (!ObjectUtils.isEmpty(credentialType)){
                subjectInfo.setDocumentType(credentialType);
            }
            subjectInfo.setDocumentNumber(value.getCredentialNo());
            subjectInfo.setContacts(value.getContact());
            subjectInfo.setPhone(value.getTel());
            subjectInfo.setBelongProvince(value.getProvince());
            subjectInfo.setBelongCity(value.getCity());
            subjectInfo.setBelongDistrict(value.getArea());
            subjectInfo.setAddress(value.getRegisteredAddress());
            if (!ObjectUtils.isEmpty(subjectInfoCollect)) {
                if (!subjectInfoCollect.contains(subjectInfo.getDocumentNumber())) {
                    subjectInfoList.add(subjectInfo);
                }
            }
            if (subjectInfoCollect == null){
                subjectInfoList.add(subjectInfo);
            }
            ext.setRevePropertyInfoRequest(revePropertyInfoRequest);
            projectInfoList.add(ext);
        }
        if (!ObjectUtils.isEmpty(subjectInfoList)){
            subjectInfoHandler.saveBatch(subjectInfoList);
        }
        LambdaQueryWrapper<SubjectInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SubjectInfo::getDocumentNumber, BaseDO::getId);
        List<SubjectInfo> newSubjectInfoList = subjectInfoHandler.list(wrapper);

        Map<String, SubjectInfo> documentNumberToSubjectInfo = new HashMap<>();
        for (SubjectInfo subjectInfo : newSubjectInfoList) {
            documentNumberToSubjectInfo.put(subjectInfo.getDocumentNumber(), subjectInfo);
        }
        for (ProjectInfoRequest projectInfoRequest : projectInfoList) {
            SubjectInfo subjectInfo = documentNumberToSubjectInfo.get(projectInfoRequest.getDocumentNumber());
            if (subjectInfo != null) {
                projectInfoRequest.setDebtorId(subjectInfo.getId());
            }
        }
        List<ProjectInfoRequest> projectInfoList2 = new ArrayList<>(projectInfoList);
        List<ProjectInfo> projectInfos = PlatformMapUtils.getInstance().mapAsList(projectInfoList, ProjectInfo.class);
        SysOrgPageRequest sysOrgPageRequest = new SysOrgPageRequest();
        sysOrgPageRequest.setOrgName("中小担");
        sysOrgPageRequest.setOrgCode("ZXD");
        Result<List<SysOrgDTO>> all1 = sysOrgClient.findAll(1, 1, sysOrgPageRequest);
        if (all1.succeed()){
            projectInfos.forEach(a -> a.setPrimaryAffiliatedOrg(all1.getData().get(0).getId()));
        }
        projectInfoHandler.saveBatch(projectInfos);
//        projectInfoList =  PlatformMapUtils.getInstance().mapAsList(projectInfos, ProjectInfoRequest.class);
            for (ProjectInfoRequest projectInfoRequest : projectInfoList) {
                Long debtorId1 = projectInfoRequest.getDebtorId();
                for (ProjectInfo projectInfo : projectInfos) {
                    Long projectInfoId = projectInfo.getId();
                    Long debtorId = projectInfo.getDebtorId();
                    if (debtorId.equals(debtorId1)){
                        projectInfoRequest.setId(projectInfoId);
                    }
                }
            }

        List<ProjectInfoExt> projectInfoExts = PlatformMapUtils.getInstance().mapAsList(projectInfoList, ProjectInfoExt.class);
        projectInfoExtHandler.saveBatch(projectInfoExts);

        Map<String, ProjectInfoRequest> documentToProjectInfoRequestMap = new HashMap<>();
        for (ProjectInfoRequest projectInfoRequest : projectInfoList2) {
            documentToProjectInfoRequestMap.put(projectInfoRequest.getDocumentNumber(), projectInfoRequest);
        }

        for (SubjectInfo subjectInfo : newSubjectInfoList) {
            Long id = subjectInfo.getId();
            // 重要修改：在每次内层循环前重新获取 projectInfoRequest
            ProjectInfoRequest projectInfoRequest = documentToProjectInfoRequestMap.get(subjectInfo.getDocumentNumber());

            for (ProjectInfo projectInfo : projectInfos) {
                if (projectInfo.getDebtorId().equals(id)) {
                    if (projectInfoRequest != null) {
                        projectInfoRequest.setId(projectInfo.getId());
                    }
                }
            }
        }
        for (ProjectInfoRequest projectInfoRequest : projectInfoList2) {
            Long projectId = projectInfoRequest.getId();
            List<ProjectBusinessInfoRequest> businessInfos = projectInfoRequest.getBusinessInfos();
            List<RevePropertyInfoRequest> revePropertyInfoRequest = projectInfoRequest.getRevePropertyInfoRequest();
            if (!ObjectUtils.isEmpty(businessInfos)){
                businessInfos.forEach(a -> a.setProjectId(projectId));
            }
            if (!ObjectUtils.isEmpty(revePropertyInfoRequest)){
                revePropertyInfoRequest.forEach(a -> a.setDoId(projectId));
            }
        }
        List<ProjectBusinessInfoRequest> collect2 = projectInfoList2.stream().filter(a -> !ObjectUtils.isEmpty(a.getBusinessInfos())).flatMap(projectInfo -> projectInfo.getBusinessInfos().stream()).collect(Collectors.toList());
        List<RevePropertyInfoRequest> collect3 = projectInfoList2.stream().filter(a -> !ObjectUtils.isEmpty(a.getRevePropertyInfoRequest())).flatMap(projectInfo -> projectInfo.getRevePropertyInfoRequest().stream()).collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(loanInfos)){
            projectLoanInfoHandler.saveBatch(PlatformMapUtils.getInstance().mapAsList(loanInfos, ProjectLoanInfo.class));
        }
        if (!ObjectUtils.isEmpty(collect2)){
            projectBusinessInfoHandler.saveBatch(PlatformMapUtils.getInstance().mapAsList(collect2, ProjectBusinessInfo.class));
        }
        if (!ObjectUtils.isEmpty(collect3)){
            revePropertyInfoHandler.saveBatch(PlatformMapUtils.getInstance().mapAsList(collect3, RevePropertyInfo.class));
        }
        compensatoryList.forEach(a -> a.setSyncStatus(true));
        compensatorySynchandler.saveOrUpdateBatch(compensatoryList);
        // 发送提醒消息
        for (ProjectInfoRequest projectInfo : projectInfoList2) {
            this.sendMessage(projectInfo.getId(), projectInfo.getProjectName());
        }
        return Result.success();
    }



    @Override
    public Result<String> importData(MultipartFile file, String isImport) {
        List<ImportProjectRequest> importProjectRequests;
        try {
          importProjectRequests =   EasyExcel.read(file.getInputStream()).head(ImportProjectRequest.class).autoCloseStream(false).sheet().headRowNumber(2).doReadSync();
        } catch (IOException e) {
            log.info("导入异常 ->{}", e.getMessage());
            throw new RuntimeException("导入数据格式错误");
        }
        if (importProjectRequests.size() < 3) {
            return Result.error("导入数据为空");
        }
        String originalFilename = file.getOriginalFilename();
        if("反担保措施导入模板.xlsx".equals(originalFilename) || originalFilename.contains("反担保措施导入模板")){
            List<ImportProjectRequest> reveList = importProjectRequests.stream().skip(2).collect(Collectors.toList());
            return addReveByProjectName(reveList);
        }
        String string = importProjectRequests.get(0).toString();
        if (!AssertConstants.IMPORT_TEMPLATE.equals(string) && !AssertConstants.IMPORT_TEMPLATE1.equals(string)){
            return Result.error("请使用标准模板导入");
        }
        List<ImportProjectRequest> filteredList = importProjectRequests.stream().skip(2).collect(Collectors.toList());
        // 数据校验
        if (!checkImportData(filteredList).isEmpty()) {
            return Result.error(checkImportData(filteredList));
        }
        List<ProjectInfo> projectList = projectInfoHandler.list();
        if (!projectList.isEmpty()){
            List<Long> debtorIds = projectList.stream().map(ProjectInfo::getDebtorId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
            if (!debtorIds.isEmpty()){
                List<String> code = filteredList.stream().map(ImportProjectRequest::getDocumentCode).distinct().collect(Collectors.toList());
                List<SubjectInfo> subjectInfos = subjectInfoHandler.list(new LambdaQueryWrapper<SubjectInfo>().in(SubjectInfo::getDocumentNumber, code));
                if (!subjectInfos.isEmpty()) {
                    List<String> existSubject = new ArrayList<>();
                    for (SubjectInfo subjectInfo : subjectInfos) {
                            if (debtorIds.contains(subjectInfo.getId())){
                                existSubject.add(subjectInfo.getDocumentNumber());
                            }
                    }
                    if (!existSubject.isEmpty()) {
                        StringBuilder stringBuilder = new StringBuilder("身份证：");
                        for (String s : existSubject) {
                            stringBuilder.append(s).append(" ");
                        }
                        if (!"".equals(isImport)) {
                            if (isImport.contains("false")) {
                                // 筛选 将相同身份证号的数据筛选出去
                                filteredList = filteredList.stream().filter(a -> !existSubject.contains(a.getDocumentCode())).collect(Collectors.toList());
                            }
                        } else {
                            return Result.error(201, stringBuilder.append("在项目中已存在").toString());
                        }
                    }
                }
            }
            if (filteredList.isEmpty()) {
                return Result.success("导入成功");
            }
        }

        log.info("filteredList ->{}", filteredList);
        // 按照身份证分组 身份证相同视为同一条数据(除担保信息 其他以第一条数据为准) -> 有多条反担保信息
        Map<String, List<ImportProjectRequest>> collect = filteredList.stream().collect(Collectors.groupingBy(ImportProjectRequest::getDocumentCode));
        // 获取 信息表
        return saveProjectInfoList(collect);
    }

    private Result<String> addReveByProjectName(List<ImportProjectRequest> filteredList) {
        Map<String, List<ImportProjectRequest>> projectList = filteredList.stream().collect(Collectors.groupingBy(ImportProjectRequest::getProjectName));
        Set<String> projectNames = projectList.keySet();
        List<ProjectInfo> projectInfoList = projectInfoHandler.list(new LambdaQueryWrapper<ProjectInfo>().in(ProjectInfo::getProjectName, projectNames));
        if (CollectionUtils.isEmpty(projectInfoList)){
            return Result.success();
        }
        Map<String, List<ProjectInfo>> collect = projectInfoList.stream().collect(Collectors.groupingBy(ProjectInfo::getProjectName));
        // 项目里查询到只有一个的
        Map<String, List<ProjectInfo>> singleItemProjects = collect.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (ObjectUtils.isEmpty(singleItemProjects)){
            return Result.success();
        }
        List<RevePropertyInfo> revePropertyInfos = new ArrayList<>();
        List<SysDictionaryItemDTO> securityWayItems = sysDictionaryClient.findByCode(SecurityWayEnum.SECURITY_WAY.getKey()).getData().getItems();
        for (Map.Entry<String, List<ImportProjectRequest>> stringListEntry : projectList.entrySet()) {
            String key = stringListEntry.getKey();
            if (singleItemProjects.containsKey(key)){
                Long projectId = singleItemProjects.get(key).get(0).getId();
                for (ImportProjectRequest importProjectRequest : stringListEntry.getValue()) {
                    RevePropertyInfo revePropertyInfo = new RevePropertyInfo();
                    revePropertyInfo.setDoId(projectId);
                    revePropertyInfo.setDoType(BillTypeEnum.PROJECT_INFO.getKey());
                    String securityType = importProjectRequest.getSecurityType();
                    String reveMeasure = importProjectRequest.getReveMeasure();
                    String reveName = importProjectRequest.getReveName();
                    String securityWay = importProjectRequest.getSecurityWay();
                    if (importProjectRequest.getIsDispose() == null){
                        continue;
                    }
                    for (SysDictionaryItemDTO securityWayItem : securityWayItems) {
                            if (PlatformStringUtils.isNotBlank(securityWay) && securityWay.equals(securityWayItem.getItemName())){
                                revePropertyInfo.setSecurityWay(securityWayItem.getId());
                            }
                    }
                    String disposeMoney = importProjectRequest.getDisposeMoney();
                    if (!ObjectUtils.isEmpty(disposeMoney)){
                        revePropertyInfo.setDisposeMoney(new BigDecimal(disposeMoney));
                    }
                    if (!ObjectUtils.isEmpty(securityType)){
                        revePropertyInfo.setSecurityType(SecurityTypeEnum.getStrKey(securityType));
                    }
                    revePropertyInfo.setReveMeasure(reveMeasure);
                    revePropertyInfo.setReveName(reveName);
                    if (importProjectRequest.getIsDispose() != null && "是".equals(importProjectRequest.getIsDispose())){
                        revePropertyInfo.setIsDispose(true);
                    }
//                    if (!ObjectUtils.isEmpty(importProjectRequest.getDisposeMoney())){
//                        revePropertyInfo.setDebtRepaymentMoney(new BigDecimal(importProjectRequest.getde()));
//                    }
                    String doType = importProjectRequest.getDoType();
                    if ("其他财产线索".equals(doType)){
                        revePropertyInfo.setBillType(BillTypeEnum.PROPERTY.getKey());
                    }else {
                        revePropertyInfo.setBillType(BillTypeEnum.REVE.getKey());
                    }
                    revePropertyInfos.add(revePropertyInfo);
                }
            }
        }
        if (revePropertyInfos.isEmpty()){
            return  Result.success();
        }
        revePropertyInfoHandler.saveBatch(revePropertyInfos);
        return Result.success();
    }

    @Override
    public Result<List<Long>> getProjectFile(Long id, String type) {
        List<Long> fileIdList = new ArrayList<>();
        // 项目入库 项目分配 项目移交 申请诉讼 付款管理 回款管理

        if (type.equals(BillTypeEnum.PROJECT_INFO.getKey())) {
            LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProjectInfo::getId, id);
            List<ProjectInfo> list = projectInfoHandler.list(queryWrapper);
            if (!ObjectUtils.isEmpty(list)) {
                fileIdList.addAll(list.stream().map(BaseDO::getId).collect(Collectors.toList()));
            }
        }

        if (type.equals(BillTypeEnum.ALLOCATION_INFO.getKey())) {
            LambdaQueryWrapper<AllocationInfoDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(AllocationInfoDetail::getProjectId, id);
            List<AllocationInfoDetail> list = allocationInfoDetailHandler.list(queryWrapper);
            if (!ObjectUtils.isEmpty(list)) {
                fileIdList.addAll(list.stream().map(AllocationInfoDetail::getAllocationId).collect(Collectors.toList()));
            }
        }
        if (BillTypeEnum.PROJECT_TRANSFER.getKey().equals(type)) {
            LambdaQueryWrapper<ProjectTransferDetailed> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ProjectTransferDetailed::getProjectId, id);
            List<ProjectTransferDetailed> list = projectTransferDetailedHandler.list(queryWrapper);
            if (!ObjectUtils.isEmpty(list)) {
                fileIdList.addAll(list.stream().map(ProjectTransferDetailed::getTransferId).collect(Collectors.toList()));
            }
        }
        if (type.equals(BillTypeEnum.RECOVERY_LITIGATION.getKey())) {
            //  RECOVERY_LITIGATION 诉状
            List<RecoveryLitigation> litigationList = recoveryLitigationHandler.list( new LambdaQueryWrapper<RecoveryLitigation>().eq(RecoveryLitigation::getProjectId, id));
            // REGISTER 立案 // 立案一审 RECOVERY_JUDGEMENT
            List<RecoveryJudgement> judgementList = recoveryJudgementHandler.list(new LambdaQueryWrapper<RecoveryJudgement>().eq(RecoveryJudgement::getProjectId, id));
            // DROP_LAWSUIT 撤诉 PRESERVATION 保全  FINAL 终本 OTHER 其他 CLOSE_CASE 结案
            List<RecoveryLitigationDetails> litigationDetails = recoveryLitigationDetailsHandler.list(new LambdaQueryWrapper<RecoveryLitigationDetails>().eq(RecoveryLitigationDetails::getProjectId, id));
            //   RECOVERY_ADJUST_TRIAL 调解审判
            List<RecoveryAdjustTrial> trialList = recoveryAdjustTrialHandler.list(new LambdaQueryWrapper<RecoveryAdjustTrial>().eq(RecoveryAdjustTrial::getProjectId, id));
            //   RECOVERY_EXECUTE 执行
            List<RecoveryExecute> executeList = recoveryExecuteHandler.list(new LambdaQueryWrapper<RecoveryExecute>().eq(RecoveryExecute::getProjectId, id));
            if (!ObjectUtils.isEmpty(litigationList)) {
                fileIdList.addAll(litigationList.stream().map(BaseDO::getId).collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(judgementList)) {
                fileIdList.addAll(judgementList.stream().map(BaseDO::getId).collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(litigationDetails)) {
                fileIdList.addAll(litigationDetails.stream().map(BaseDO::getId).collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(trialList)) {
                fileIdList.addAll(trialList.stream().map(BaseDO::getId).collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(executeList)) {
                fileIdList.addAll(executeList.stream().map(BaseDO::getId).collect(Collectors.toList()));
            }
        }
        if (type.equals(BillTypeEnum.RECOVERY_PAYMENT.getKey())) {
            LambdaQueryWrapper<RecoveryPayment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RecoveryPayment::getProjectId, id);
            List<RecoveryPayment> list = recoveryPaymentHandler.list(queryWrapper);
            if (!ObjectUtils.isEmpty(list)) {
                fileIdList.addAll(list.stream().map(BaseDO::getId).collect(Collectors.toList()));
            }
        }
        if (type.equals(BillTypeEnum.RECOVERY_PAYMENT_COLLECTION.getKey())) {
            LambdaQueryWrapper<RecoveryPaymentCollection> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RecoveryPaymentCollection::getProjectId, id);
            List<RecoveryPaymentCollection> list = recoveryPaymentCollectionHandler.list(queryWrapper);
            if (!ObjectUtils.isEmpty(list)) {
                fileIdList.addAll(list.stream().map(BaseDO::getId).collect(Collectors.toList()));
            }
        }

        if (type.equals(BillTypeEnum.WRITE_OFF.getKey())) {
            LambdaQueryWrapper<WriteOff> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WriteOff::getProjectId, id);
            List<WriteOff> list = writeOffHandler.list(queryWrapper);
            if (!ObjectUtils.isEmpty(list)) {
                fileIdList.addAll(list.stream().map(BaseDO::getId).collect(Collectors.toList()));
            }
        }

        return Result.success(fileIdList);
    }

    @Override
    public Result<List<ProjectWorkflowProcess>> getProjectFlow(Long id) {

        List<ProjectWorkflowProcess> list = Lists.newArrayList();

        //  查询项目过程流程
        //  项目和单据一对一相关流程（调解审批，执行，付款，回款，核销）

        Map<BillTypeEnum, ServiceImpl<?, ?>> map = new HashMap<>();
        map.put(BillTypeEnum.RECOVERY_ADJUST_TRIAL, recoveryAdjustTrialHandler);
        map.put(BillTypeEnum.WRITE_OFF, writeOffHandler);
        map.put(BillTypeEnum.RECOVERY_PAYMENT_COLLECTION, recoveryPaymentCollectionHandler);
        map.put(BillTypeEnum.RECOVERY_PAYMENT, recoveryPaymentHandler);
        map.put(BillTypeEnum.RECOVERY_EXECUTE, recoveryExecuteHandler);

        //  共同属性 设置过滤条件
        String projectId = "project_id";
        Map<String, Object> queryWrapper = Maps.newHashMap();
        queryWrapper.put(projectId, id);
        for (Map.Entry<BillTypeEnum, ServiceImpl<?, ?>> billTypeEnumServiceEntry : map.entrySet()) {
            BillTypeEnum key = billTypeEnumServiceEntry.getKey();
            ServiceImpl<?, ?> serviceHandler = billTypeEnumServiceEntry.getValue();
            List<?> objects = serviceHandler.listByMap(queryWrapper);
            for (Object obj : objects) {
                try {
                    ProjectWorkflowProcess projectWorkflowProcess = new ProjectWorkflowProcess();
                    Field idField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField("id");
                    idField.setAccessible(true); // 使私有字段可以访问
                    Field creatorField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField("creator");
                    creatorField.setAccessible(true); // 使私有字段可以访问
                    Field createStampField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField("createStamp");
                    createStampField.setAccessible(true); // 使私有字段可以访问
                    projectWorkflowProcess.setBillId(Long.valueOf(idField.get(obj).toString()));
                    projectWorkflowProcess.setCreator(Long.valueOf(creatorField.get(obj).toString()));
                    projectWorkflowProcess.setCreateStamp((Date) createStampField.get(obj));
                    projectWorkflowProcess.setBillTypeCode(key.getKey());
                    projectWorkflowProcess.setBillTypeName(key.getValue());
                    list.add(projectWorkflowProcess);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // 如果对象没有id字段或无法访问，处理异常
                    System.err.println("反射id失败找不到id属性: " + e.getMessage());
                }
            }
        }

        //  下面处理，项目和单据是一对多或者一表表示多种类型（分配，移交，立案，审理，保全，撤诉，终本，结案，其他，）
        //  立案，审理处理 这里单据类型doType和billType没用一个这里只能这样处理
        List<RecoveryJudgement> recoveryJudgementList = recoveryJudgementHandler.list(new LambdaQueryWrapper<RecoveryJudgement>()
                .eq(RecoveryJudgement::getProjectId, id));
        //  立案
        for (RecoveryJudgement recoveryJudgement : recoveryJudgementList) {
            ProjectWorkflowProcess projectWorkflowProcess = new ProjectWorkflowProcess();
            projectWorkflowProcess.setBillId(recoveryJudgement.getId());
            projectWorkflowProcess.setCreator(recoveryJudgement.getCreator());
            projectWorkflowProcess.setCreateStamp(recoveryJudgement.getCreateStamp());
            if (LitigationTypeEnum.REGISTER.getKey().equals(recoveryJudgement.getLitigationType())) {
                projectWorkflowProcess.setBillTypeCode(BillTypeEnum.RECOVERY_JUDGEMENT.getKey());
                projectWorkflowProcess.setBillTypeName(BillTypeEnum.RECOVERY_JUDGEMENT.getValue());
            } else {
                projectWorkflowProcess.setBillTypeCode(BillTypeEnum.RECOVERY_JUDGEMENT_REGISTER.getKey());
                projectWorkflowProcess.setBillTypeName(BillTypeEnum.RECOVERY_JUDGEMENT_REGISTER.getValue());
            }
            list.add(projectWorkflowProcess);
        }

        //  保全，撤诉，终本，结案，其他
        List<RecoveryLitigationDetails> recoveryLitigationDetailsList = recoveryLitigationDetailsHandler.list(new LambdaQueryWrapper<RecoveryLitigationDetails>()
                .eq(RecoveryLitigationDetails::getProjectId, id));
        for (RecoveryLitigationDetails recoveryLitigationDetails : recoveryLitigationDetailsList) {
            ProjectWorkflowProcess projectWorkflowProcess = new ProjectWorkflowProcess();
            projectWorkflowProcess.setBillId(recoveryLitigationDetails.getId());
            projectWorkflowProcess.setCreator(recoveryLitigationDetails.getCreator());
            projectWorkflowProcess.setCreateStamp(recoveryLitigationDetails.getCreateStamp());
            projectWorkflowProcess.setBillTypeCode(recoveryLitigationDetails.getLitigationType().getKey());
            projectWorkflowProcess.setBillTypeName(recoveryLitigationDetails.getLitigationType().getValue());
            list.add(projectWorkflowProcess);
        }

        //  项目移交，项目分配处理
        List<AllocationInfoDetail> allocationInfoDetailList = allocationInfoDetailHandler.list(new LambdaQueryWrapper<AllocationInfoDetail>()
                .eq(AllocationInfoDetail::getProjectId, id));
        for (AllocationInfoDetail allocationInfoDetail : allocationInfoDetailList) {
            ProjectWorkflowProcess projectWorkflowProcess = new ProjectWorkflowProcess();
            projectWorkflowProcess.setBillId(allocationInfoDetail.getAllocationId());
            projectWorkflowProcess.setCreator(allocationInfoDetail.getCreator());
            projectWorkflowProcess.setCreateStamp(allocationInfoDetail.getCreateStamp());
            projectWorkflowProcess.setBillTypeCode(BillTypeEnum.ALLOCATION_INFO.getKey());
            projectWorkflowProcess.setBillTypeName(BillTypeEnum.ALLOCATION_INFO.getValue());
            list.add(projectWorkflowProcess);
        }

        List<ProjectTransferDetailed> projectTransferDetailedList = projectTransferDetailedHandler.list(new LambdaQueryWrapper<ProjectTransferDetailed>()
                .eq(ProjectTransferDetailed::getProjectId, id));
        for (ProjectTransferDetailed projectTransferDetailed : projectTransferDetailedList) {
            ProjectWorkflowProcess projectWorkflowProcess = new ProjectWorkflowProcess();
            projectWorkflowProcess.setBillId(projectTransferDetailed.getTransferId());
            projectWorkflowProcess.setCreator(projectTransferDetailed.getCreator());
            projectWorkflowProcess.setCreateStamp(projectTransferDetailed.getCreateStamp());
            projectWorkflowProcess.setBillTypeCode(BillTypeEnum.PROJECT_TRANSFER.getKey());
            projectWorkflowProcess.setBillTypeName(BillTypeEnum.PROJECT_TRANSFER.getValue());
            list.add(projectWorkflowProcess);
        }

        //  查询流程状态和流程实例id
        List<ProjectWorkflowProcess> flowBillInfo = Lists.newArrayList();
        for (ProjectWorkflowProcess projectWorkflowProcess : list) {
            try {
                Result<ProcessStatus> statusByDoId = processClient.getStatusByDoId(projectWorkflowProcess.getBillId());
                if (null != statusByDoId && statusByDoId.succeedWithData()) {
                    Result<String> processInstanceIdByDoId = processClient.getProcessInstanceIdByDoId(projectWorkflowProcess.getBillId());
                    projectWorkflowProcess.setFlowId(processInstanceIdByDoId.getData());
                    projectWorkflowProcess.setProcessStatus(statusByDoId.getData());
                    flowBillInfo.add(projectWorkflowProcess);
                }
            } catch (Exception e) {
                log.error("查询流程引擎报错 doId：{}", projectWorkflowProcess.getBillId());
            }
        }

        return Result.success(flowBillInfo);
    }

    @Override
    public Result<List<ContractInfoDTO>> findContractByProjectId(Long id) {
        List<BillContract> billContracts = billContractHandler.list(new LambdaQueryWrapper<BillContract>()
                .eq(BillContract::getDoType, BillTypeEnum.PROJECT_INFO)
                .eq(BillContract::getDoId, id)
        );
        if (CollectionUtils.isEmpty(billContracts)){
            return Result.success(new ArrayList<>());
        }
        List<Long>  contractIds = billContracts.stream().map(BillContract::getContractId).collect(Collectors.toList());
        List<ContractInfo> contractInfos = contractInfoHandler.listByIds(contractIds);
        if(CollectionUtils.isEmpty(contractInfos)){
            return Result.success(new ArrayList<>());
        }
        return Result.success(PlatformMapUtils.getInstance().mapAsList(contractInfos, ContractInfoDTO.class));
    }

    @Override
    public Result<List<ProductNameDTO>> findProductName() {
        List<ProductNameDTO> dtoList = new ArrayList<>();
        List<SysDictionaryItemDTO> list = new ArrayList<>();
        List<SysDictionaryItemDTO> onLine = sysDictionaryClient.findByCode(DictEnum.ON_LINE.getKey())
                .getData().getItems();
        List<SysDictionaryItemDTO> offLine = sysDictionaryClient.findByCode(DictEnum.OFF_LINE.getKey())
                .getData().getItems();
        List<SysDictionaryItemDTO> traditional = sysDictionaryClient.findByCode(DictEnum.TRADITIONAL_PRODUCT.getKey())
                .getData().getItems();
        list.addAll(onLine);
        list.addAll(offLine);
        list.addAll(traditional);
        list.forEach(dictionary -> {
            ProductNameDTO dto = new ProductNameDTO();
            dto.setProductId(dictionary.getId());
            dto.setProductName(dictionary.getItemName());
            dtoList.add(dto);
        });
        return Result.success(dtoList);
    }

    private Result<String> saveProjectInfoList(Map<String, List<ImportProjectRequest>> collect) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Result<List<SysOrgDTO>> orgId = findOrgId();
        Calendar calendar = Calendar.getInstance();
        SysUserDTO currentUser = platformUserUtils.getCurrentUser();
        // 未分配
        List<SysDictionaryItemDTO> projectStates = sysDictionaryClient.findByCode(ProjectStateEnum.PROJECT_STATE.getKey()).getData().getItems();
        // 查询出省市区
        Map<String, Long> locationMap = new HashedMap<>();
        SysRegionPageRequest regionPageRequest = new SysRegionPageRequest();
        regionPageRequest.setName("湖南省");
        Result<List<SysRegionDTO>> all = sysRegionClient.findAll(1, 1, regionPageRequest);
        Long provinceId = all.getData().get(0).getId();
        List<Long> cityList = new ArrayList<>();
        cityList.add(provinceId);
        Map<String, Long> cityMap = platformFieldUtils.getRegionIdByParentIds(cityList);
        // 湖南省全部市
        List<Long> districtList = cityMap.values().stream().distinct().collect(Collectors.toList());
        Map<String, Long> districtMap = platformFieldUtils.getRegionIdByParentIds(districtList);
        locationMap.put("湖南省", provinceId);
        locationMap.putAll(cityMap);
        locationMap.putAll(districtMap);
        // 查询律所
        List<LawFirmInfo> lawFirmInfoList = lawFirmInfoHandler.list(new LambdaQueryWrapper<LawFirmInfo>()
                .eq(LawFirmInfo::getEnabled, true));
        Map<String, Long> lawFirmMap = new HashedMap<>();
        if (CollectionUtils.isNotEmpty(lawFirmInfoList)) {
            lawFirmMap = lawFirmInfoList.stream().collect(Collectors.toMap(LawFirmInfo::getName, BaseDO::getId));
        }
        List<ProjectBusinessInfo> projectBusinessInfoList = new ArrayList<>();
        List<ProjectLoanInfo> projectLoanInfoList = new ArrayList<>();
        for (Map.Entry<String, List<ImportProjectRequest>> stringListEntry : collect.entrySet()) {
            List<RevePropertyInfo> reveList = new ArrayList<>();
            String key = stringListEntry.getKey();
            List<ImportProjectRequest> value = stringListEntry.getValue();
            log.info("=========证件号 ->{}=========信息==========->{}", key, value);
            ImportProjectRequest request = value.get(0);
            ProjectInfo projectInfo = new ProjectInfo();
            ProjectInfoExt ext = new ProjectInfoExt();
            SubjectInfo subjectInfo = new SubjectInfo();
            LambdaQueryWrapper<SubjectInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SubjectInfo::getDocumentNumber, request.getDocumentCode())
//                    .eq(SubjectInfo::getSubjectName, request.getProjectName())
                    .eq(SubjectInfo::getSubjectType, SubjectTypeEnum.DEBTOR);
            SubjectInfo one = subjectInfoHandler.getOne(queryWrapper);
            if (one != null) {
                projectInfo.setDebtorId(one.getId());
            }
            projectInfo.setManage(currentUser.getId().toString());
            projectInfo.setProjectName(request.getProjectName());
            projectInfo.setRemark(request.getImportRemark());
            // 省市区
            String belongProvince = request.getBelongProvince();
            if (PlatformStringUtils.isNotEmpty(belongProvince)) {
                if (locationMap.containsKey(belongProvince)) {
                    projectInfo.setBelongProvince(locationMap.get(belongProvince));
                }
            }
            String city = request.getBelongCity();
            if (PlatformStringUtils.isNotEmpty(city)) {
                if (locationMap.containsKey(city)) {
                    projectInfo.setBelongCity(locationMap.get(city));
                }
            }
            String belongDistrict = request.getBelongDistrict();
            if (PlatformStringUtils.isNotEmpty(belongDistrict)) {
                if (locationMap.containsKey(belongDistrict)) {
                    projectInfo.setBelongDistrict(locationMap.get(belongDistrict));
                }
            }
            projectInfo.setAddress(request.getAddress());
            if (one == null) {
                subjectInfo.setSubjectName(request.getProjectName());
                subjectInfo.setNature(SubjectNatureEnum.getKey(request.getNature()));
                subjectInfo.setSubjectType(SubjectTypeEnum.DEBTOR);
                subjectInfo.setDocumentType(DocumentTypeEnum.getStrKey(request.getDocumentType()));
                subjectInfo.setDocumentNumber(request.getDocumentCode());
                subjectInfo.setBelongProvince(provinceId);
                subjectInfo.setBelongCity(projectInfo.getBelongCity());
                subjectInfo.setBelongDistrict(projectInfo.getBelongDistrict());
                subjectInfo.setAddress(request.getAddress());
                subjectInfo.setPhone(request.getPhone());
                subjectInfo.setLegalRepresentative(request.getLegalRepresentative());
                subjectInfo.setContacts(request.getContacts());
                subjectInfoHandler.save(subjectInfo);
                projectInfo.setDebtorId(subjectInfo.getId());
            }
            try {
                projectInfo.setCompensationDate(sdf.parse(request.getCompensationDate()));
                // 诉讼时效 代偿时间+3年
                calendar.setTime(sdf.parse(request.getCompensationDate()));
                calendar.add(Calendar.YEAR, 3);
                projectInfo.setProceedingAgeingDate(calendar.getTime());
                if (request.getTransferDate() != null){
                    projectInfo.setTransferDate(sdf.parse(request.getTransferDate()));
                }
                if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(request.getProceedingageingdate())) {
                    projectInfo.setProceedingAgeingDate(sdf.parse(request.getProceedingageingdate()));
                }
                if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(request.getAdjustTrialDate())) {
                    // 执行时效
                    projectInfo.setAdjustTrialDate(sdf.parse(request.getAdjustTrialDate()));
                }
            } catch (ParseException e) {
                log.info("时间转换错误 ->{}", e.getMessage());
                throw new RuntimeException("");
            }
            projectInfo.setCompensationMoney(new BigDecimal(request.getCompensationMoney()));
            if (orgId.succeed()) {
                projectInfo.setAffiliatedOrg(orgId.getData().get(0).getId());
            }
            // 设置状态
            if (PlatformStringUtils.isEmpty(request.getProjectState())) {
                projectInfo.setProjectState(projectStates.stream().filter(item -> item.getItemCode().equals(DictEnum.PROJECT_STATE_01.getKey())).map(SysDictionaryItemDTO::getId).findAny().get());
            } else {
                Optional<Long> anyState = projectStates.stream().filter(item -> item.getItemName().equals(request.getProjectState())).map(SysDictionaryItemDTO::getId).findAny();
                anyState.ifPresent(projectInfo::setProjectState);
            }
            // 律所
            String lawFirmName = request.getLawFirmName();
            if (!ObjectUtils.isEmpty(lawFirmName) && !lawFirmMap.isEmpty()) {
                if (lawFirmMap.containsKey(lawFirmName)) {
                    projectInfo.setLawFirmId(lawFirmMap.get(lawFirmName));
                }
            }
            String manageAccount = request.getManageAccount();
            if (PlatformStringUtils.isNotBlank(manageAccount)) {
                Result<SysUserDTO> byAccount = sysUserClient.findByAccount(manageAccount);
                if (byAccount.succeedWithData()) {
                    Long userId = byAccount.getData().getId();
                    projectInfo.setManage(userId.toString());
                } else {
                    return Result.error("保全经理账号:" + manageAccount + "不存在");
                }
            }
            projectInfoHandler.save(projectInfo);
            try {
                for (ImportProjectRequest importProjectRequest : value) {
                    String securityType = importProjectRequest.getSecurityType();
                    String reveMeasure = importProjectRequest.getReveMeasure();
                    String reveName = importProjectRequest.getReveName();
                    String securityWay = importProjectRequest.getSecurityWay();
                    String isDispose = importProjectRequest.getIsDispose();
                    if (PlatformStringUtils.isEmpty(isDispose)) {
                        continue;
                    }
                    RevePropertyInfo revePropertyInfo = new RevePropertyInfo();
                    revePropertyInfo.setBillType(BillTypeEnum.REVE.getKey());
                    revePropertyInfo.setDoType(BillTypeEnum.PROJECT_INFO.getKey());
                    revePropertyInfo.setDoId(projectInfo.getId());
                    sysDictionaryClient.findByCode(SecurityWayEnum.SECURITY_WAY.getKey()).getData().getItems().stream().filter(item -> item.getItemName().equals(securityWay)).findAny()
                            .ifPresent(sysDictionaryItemDTO -> revePropertyInfo.setSecurityWay(sysDictionaryItemDTO.getId()));
                    if (!ObjectUtils.isEmpty(securityType)){
                        revePropertyInfo.setSecurityType(SecurityTypeEnum.getStrKey(securityType));
                    }
                    revePropertyInfo.setReveMeasure(reveMeasure);
                    revePropertyInfo.setReveName(reveName);
                    if (importProjectRequest.getIsDispose() != null && "是".equals(importProjectRequest.getIsDispose())) {
                        revePropertyInfo.setIsDispose(true);
                    }
                    String disposeMoney = importProjectRequest.getDisposeMoney();
                    if (PlatformStringUtils.isNotBlank(disposeMoney)){
                        revePropertyInfo.setDisposeMoney(new BigDecimal(disposeMoney));
                    }
                    String doType = importProjectRequest.getDoType();
                    if ("其他财产线索".equals(doType)) {
                        revePropertyInfo.setBillType(BillTypeEnum.PROPERTY.getKey());
                    } else {
                        revePropertyInfo.setBillType(BillTypeEnum.REVE.getKey());
                    }
                    reveList.add(revePropertyInfo);
                }
            } catch (RuntimeException exception) {
                log.info("反担保措施信息异常 ->{}", exception.getMessage());
                throw new RuntimeException("反担保措施信息填写异常");
            }
            // 保存反担保信息
            revePropertyInfoHandler.saveBatch(reveList);
            ext.setId(projectInfo.getId());
            ProjectBusinessInfo projectBusinessInfo = new ProjectBusinessInfo();
            String relatedBusNo = "A" + new Date().getTime();
            projectBusinessInfo.setProjectId(projectInfo.getId());
            projectBusinessInfo.setRelatedBusNo(relatedBusNo);
            projectBusinessInfo.setBusinessType(ProjectBusinessInfoRequest.BusinessTypeEnum.JDJB);
            projectBusinessInfoList.add(projectBusinessInfo);
            ProjectLoanInfo projectLoanInfo = new ProjectLoanInfo();
            projectLoanInfo.setRelatedBusNo(relatedBusNo);
            projectLoanInfo.setCompensationDate(projectInfo.getCompensationDate());
            projectLoanInfo.setCompensationMoney(projectInfo.getCompensationMoney());
            projectLoanInfoList.add(projectLoanInfo);
            // 合作银行
            sysDictionaryClient.findByCode(DictEnum.INDUSTRY_GXB.getKey()).getData().getItems().stream().filter(item -> item.getItemName().equals(request.getIndustryType())).findAny().ifPresent(sysDictionaryItemDTO -> ext.setIndustryType(sysDictionaryItemDTO.getId()));
            sysDictionaryClient.findByCode(BigSmallEnum.BIG_SMALL.getKey()).getData().getItems().stream().filter(item -> item.getItemName().equals(request.getBigSmall())).findAny().ifPresent(sysDictionaryItemDTO -> ext.setBigSmall(sysDictionaryItemDTO.getId()));
            sysDictionaryClient.findByCode(IndustryPolicySupportEnum.INDUSTRY_POLICY_SUPPORT.getKey()).getData().getItems().stream().filter(item -> item.getItemName().equals(request.getGoverType())).findAny().ifPresent(sysDictionaryItemDTO -> ext.setGoverType(sysDictionaryItemDTO.getId().toString()));
            ext.setRelationEnterprise(request.getRelationEnterprise());
            ext.setRelationEnterpriseNo(request.getRelationEnterpriseNo());
            projectInfoExtHandler.save(ext);
            // 发送提醒消息
            this.sendMessage(projectInfo.getId(), projectInfo.getProjectName());
        }
        projectBusinessInfoHandler.saveBatch(projectBusinessInfoList);
        projectLoanInfoHandler.saveBatch(projectLoanInfoList);
        return  Result.success("导入成功");
    }

    private String checkImportData(List<ImportProjectRequest> filteredList) {
      // 校验必填参数
      int i = 5;
      for (ImportProjectRequest importProjectRequest : filteredList) {

        if (ObjectUtils.isEmpty(importProjectRequest.getProjectName())) {
          return "第" + i + "行必填项 债务人名称为空";
        }
        if (ObjectUtils.isEmpty(importProjectRequest.getNature())) {
          return "第" + i + "行必填项 债务人性质为空";
        }
        if (ObjectUtils.isEmpty(importProjectRequest.getCompensationMoney())) {
          return "第" + i + "行必填项 代偿金额为空";
        }
        if (ObjectUtils.isEmpty(importProjectRequest.getCompensationDate())) {
          return "第" + i + "行必填项 代偿日期为空";
        }
//        if (ObjectUtils.isEmpty(importProjectRequest.getTransferDate())) {
//          return "第" + i + "行必填项 移交至保全部日期为空";
//        }
        if (ObjectUtils.isEmpty(importProjectRequest.getDocumentType())) {
          return "第" + i + "行必填项 证件类型为空";
        }
        if (ObjectUtils.isEmpty(importProjectRequest.getDocumentCode())) {
          return "第" + i + "行必填项 证件号码为空";
        }

          if (ObjectUtils.isEmpty(importProjectRequest.getProceedingageingdate())) {
              return "第" + i + "行必填项 诉讼时效为空";
          }
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
          try {
              sdf.parse(importProjectRequest.getCompensationDate());
          } catch (ParseException e) {
              return "第" + i + "行必填项 代偿日期格式异常";
          }
          try {
              sdf.parse(importProjectRequest.getProceedingageingdate());
          } catch (ParseException e) {
              return "第" + i + "行必填项 诉讼时效格式异常";
          }
          if (!ObjectUtils.isEmpty(importProjectRequest.getAdjustTrialDate())){
              try {
                  sdf.parse(importProjectRequest.getAdjustTrialDate());
              } catch (ParseException e) {
                  return "第" + i + "行必填项 执行时效格式异常";
              }
          }
          // 诉讼时效 代偿时间+3年
          Calendar calendar = Calendar.getInstance();
          try {
              calendar.setTime(sdf.parse(importProjectRequest.getCompensationDate()));
          } catch (ParseException e) {
              return "第" + i + "行必填项 代偿日期格式异常";
          }
          calendar.add(Calendar.YEAR, 3);
          calendar.getTime();
          String transferDate = importProjectRequest.getTransferDate();
          if (!ObjectUtils.isEmpty(transferDate)){
              try {
                  sdf.parse(transferDate);
              } catch (ParseException e) {
                  return "第" + i + "行必填项 移交至保全部日期格式异常";
              }
          }
          i++;
      }
        return "";
    }
}