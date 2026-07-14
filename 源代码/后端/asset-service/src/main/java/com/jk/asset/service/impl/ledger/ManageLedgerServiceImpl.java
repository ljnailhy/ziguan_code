package com.jk.asset.service.impl.ledger;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jk.asset.constant.AssertConstants;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.CollectionAscriptionEnum;
import com.jk.asset.enums.CollectionSignEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.mapper.ProjectInfoMapper;
import com.jk.asset.model.dto.CompensatoryCountDTO;
import com.jk.asset.model.dto.CompensatoryCountExportDTO;
import com.jk.asset.model.dto.ManageLedgerDTO;
import com.jk.asset.model.dto.ManageLedgerDetailDTO;
import com.jk.asset.model.dto.ManageLedgerProjectDTO;
import com.jk.asset.model.dto.PaymentCollectionPageDTO;
import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.asset.model.dto.ProjectLedgerDTO;
import com.jk.asset.model.dto.export.CollectionLedgerExportDTO;
import com.jk.asset.model.dto.export.ProjectLedgerExportDTO;
import com.jk.asset.model.entity.LawFirmInfo;
import com.jk.asset.model.entity.PaymentCollectionTarget;
import com.jk.asset.model.entity.PaymentCollectionTargetDetail;
import com.jk.asset.model.entity.ProjectBusinessInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RecoveryPayment;
import com.jk.asset.model.entity.RecoveryPaymentCollection;
import com.jk.asset.model.entity.RecoveryPaymentCollectionDetail;
import com.jk.asset.model.entity.RecoveryPaymentDetail;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.entity.SubjectInfo;
import com.jk.asset.model.entity.WriteOff;
import com.jk.asset.model.entity.proceeding.RecoveryAdjustTrial;
import com.jk.asset.model.entity.proceeding.RecoveryExecute;
import com.jk.asset.model.entity.proceeding.RecoveryJudgement;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationDetails;
import com.jk.asset.model.request.ExportCompensatoryRequest;
import com.jk.asset.model.request.ProjectInfoRequest;
import com.jk.asset.model.request.page.ManagePageRequest;
import com.jk.asset.model.request.page.PaymentCollectionLedgerPageRequest;
import com.jk.asset.model.request.page.ProjectInfoPageRequest;
import com.jk.asset.model.request.proceeding.RecoveryAdjustTrialRequest;
import com.jk.asset.service.handler.LawFirmInfoHandler;
import com.jk.asset.service.handler.PaymentCollectionTargetDetailHandler;
import com.jk.asset.service.handler.PaymentCollectionTargetHandler;
import com.jk.asset.service.handler.ProjectBusinessInfoHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionDetailHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionHandler;
import com.jk.asset.service.handler.RecoveryPaymentDetailHandler;
import com.jk.asset.service.handler.RecoveryPaymentHandler;
import com.jk.asset.service.handler.RevePropertyInfoHandler;
import com.jk.asset.service.handler.SubjectInfoHandler;
import com.jk.asset.service.handler.WriteOffHandler;
import com.jk.asset.service.handler.proceeding.RecoveryAdjustTrialHandler;
import com.jk.asset.service.handler.proceeding.RecoveryExecuteHandler;
import com.jk.asset.service.handler.proceeding.RecoveryJudgementHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationDetailsHandler;
import com.jk.asset.service.ledger.ManageLedgerService;
import com.jk.asset.utils.excel.ExcelUtil;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.model.entity.BaseDO;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.utils.PlatformFieldUtils;
import com.jk.service.utils.PlatformUserUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author wangTao
 * date2024/7/16 10:13
 **/
@RestController
@Slf4j
@Api(tags = "保全经理台账接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ManageLedgerServiceImpl implements ManageLedgerService {

    public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000");
    private final ProjectInfoHandler projectInfoHandler;
    private final PaymentCollectionTargetDetailHandler targetDetailHandler;
    private final PaymentCollectionTargetHandler targetHandler;
    private final ProjectInfoMapper projectInfoMapper;
    private final SysDictionaryClient sysDictionaryClient;
    private final PlatformUserUtils platformUserUtils;
    private final LawFirmInfoHandler firmInfoHandler;
    private final RecoveryPaymentCollectionHandler recoveryPaymentCollectionHandler;
    private final RevePropertyInfoHandler revePropertyInfoHandler;
    private final WriteOffHandler writeOffHandler;
    private final RecoveryLitigationDetailsHandler recoveryLitigationDetailsHandler;
    private final RecoveryExecuteHandler recoveryExecuteHandler;
    private final RecoveryAdjustTrialHandler recoveryAdjustTrialHandler;
    private final RecoveryPaymentHandler recoveryPaymentHandler;
    private final RecoveryPaymentDetailHandler recoveryPaymentDetailHandler;
    private final AssetUserLimitsUtils assetUserLimitsUtils;
    private final RecoveryPaymentCollectionDetailHandler recoveryPaymentCollectionDetailHandler;
    private final SubjectInfoHandler subjectInfoHandler;
    private final PlatformFieldUtils platformFieldUtils;
    private final ProjectBusinessInfoHandler projectBusinessInfoHandler;
    private final RecoveryJudgementHandler recoveryJudgementHandler;

    @Override
    public Result<List<ManageLedgerDTO>> findManageAll(Integer current, Integer size, ProjectInfoPageRequest request) {
        // Step1：创建一个 Page 对象
        IPage<ProjectInfoDTO> page = new Page<>(current, size);
        String manage = request.getManage();
        List<Date> createStampRange = request.getCreateStampRange();
        LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProjectInfo::getId,ProjectInfo::getManage,ProjectInfo::getCompensationMoney,ProjectInfo::getIsWriteOff,ProjectInfo::getIsTransfer);
        if (ObjectUtils.isNotEmpty(manage)){
            queryWrapper.like(ProjectInfo::getManage, manage);
        }
        if (!ObjectUtils.isEmpty(createStampRange) && createStampRange.size() == 2) {
            queryWrapper.between(ProjectInfo::getCreateStamp, createStampRange.get(0), createStampRange.get(1));
        }
        queryWrapper.orderByDesc(ProjectInfo::getCreateStamp);
        List<ProjectInfo> projectInfoList = projectInfoHandler.list(queryWrapper);
        // 空则直接返回
        if (CollectionUtils.isEmpty(projectInfoList)) {
            return Result.success(Lists.newArrayList(), PageFactory.page(page));
        }
        List<ManageLedgerDTO> dtoList = new ArrayList<>();
        Map<String, List<ProjectInfo>> collect4 = projectInfoList.stream().filter(a -> !ObjectUtils.isEmpty(a.getManage())).collect(Collectors.groupingBy(ProjectInfo::getManage));
        Map<String, List<ProjectInfo>> collect = new HashedMap<>();

        for (Map.Entry<String, List<ProjectInfo>> entry : collect4.entrySet()) {
            String[] key = entry.getKey().split(",");
            if (key.length > 1) {
                // 一个项目多个保全经理
                for (String s : key) {
                    if (StringUtils.isNotBlank(manage) && !manage.equals(s)) {
                        continue;
                    }
                    List<ProjectInfo> value = entry.getValue();
                    List<ProjectInfo> projectInfoList1 = collect.get(s);
                    if (!ObjectUtils.isEmpty(projectInfoList1)) {
                        List<ProjectInfo> projectInfoList2 = collect.get(s);
                        projectInfoList2.addAll(value);
                        collect.put(s, projectInfoList2);
                    } else {
                        collect.put(s, value);
                    }
                }
            }else {
                collect.put(entry.getKey(), entry.getValue());
            }
        }
        List<Long> projectIds = new ArrayList<>();
        List<Long> manageIds = new ArrayList<>();
        for (Map.Entry<String, List<ProjectInfo>> entry : collect.entrySet()) {
            ManageLedgerDTO manageLedgerDTO = new ManageLedgerDTO();
            BigDecimal totalCompensationMoney = BigDecimal.ZERO;
            String key = entry.getKey();
            // 保全经理
            manageLedgerDTO.setManage(key);
            // 在管项目数
            List<ProjectInfoDTO> value = PlatformMapUtils.getInstance().mapAsList(entry.getValue(), ProjectInfoDTO.class);
            if (!ObjectUtils.isEmpty(key)){
                String[] split = key.split(",");
                for (String s : split) {
                    manageIds.add(Long.valueOf(s));
                }
            }
            projectIds.addAll(value.stream().map(ProjectInfoRequest::getId).collect(Collectors.toList()));
            // 在管项目
            manageLedgerDTO.setManageNum(value.size());
            int transferNum = 0;
            int writeOffNum = 0;
            for (ProjectInfoDTO projectInfo : value) {
                BigDecimal compensationMoney = projectInfo.getCompensationMoney();
                Boolean isTransfer = projectInfo.getIsTransfer();
                Boolean isWriteOff = projectInfo.getIsWriteOff();
                if (!ObjectUtils.isEmpty(compensationMoney)){
                    totalCompensationMoney = totalCompensationMoney.add(compensationMoney);
                }
                if (isTransfer){
                    transferNum++;
                }
                if (isWriteOff){
                    writeOffNum++;
                }
            }
            // 累计追偿金额
            manageLedgerDTO.setTotalCompensationMoney(totalCompensationMoney);
            // 项目核销数 移交数
            manageLedgerDTO.setTransferNum(transferNum);
            manageLedgerDTO.setWriteOffNum(writeOffNum);
            manageLedgerDTO.setProjectInfos(value);
            dtoList.add(manageLedgerDTO);
        }
        if (manageIds.isEmpty()){
            return Result.success(Lists.newArrayList(), PageFactory.page(page));
        }
        LocalDate today = LocalDate.now();
        // 获取当前年份
        int currentYear = today.getYear();
        LambdaQueryWrapper<PaymentCollectionTarget> queryTarget = new LambdaQueryWrapper<>();
        List<PaymentCollectionTarget> targetList = targetHandler.list(queryTarget);
        Long targetId = null;
        for (PaymentCollectionTarget target : targetList) {
            Date year = target.getYear();
            LocalDate localDate = year.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int targetYear = localDate.getYear();
            if (targetYear == currentYear){
                targetId = target.getId();
                break;
            }
        }
        if (ObjectUtils.isNotEmpty(targetId)) {
            LambdaQueryWrapper<PaymentCollectionTargetDetail> targetDetailQuery = new LambdaQueryWrapper<>();
            targetDetailQuery.select(PaymentCollectionTargetDetail::getUserId, PaymentCollectionTargetDetail::getTargetDetailMoney,PaymentCollectionTargetDetail::getEntrustDetailMoney)
                .in(PaymentCollectionTargetDetail::getUserId, manageIds)
                .eq(PaymentCollectionTargetDetail::getTargetId, targetId);
            // 年度回款目标
            List<PaymentCollectionTargetDetail> paymentCollectionTargetDetailList = targetDetailHandler.list(targetDetailQuery);
            Map<Long, List<PaymentCollectionTargetDetail>> manageTargetMap = paymentCollectionTargetDetailList.stream().collect(Collectors.groupingBy(PaymentCollectionTargetDetail::getUserId));
            for (ManageLedgerDTO manageLedgerDTO : dtoList) {
                Long manageId = Long.valueOf(manageLedgerDTO.getManage());
                if (manageTargetMap.containsKey(manageId)){
                    // 自主回款目标 委托回款目标 累计回款目标
                    List<PaymentCollectionTargetDetail> paymentCollectionTargetDetails = manageTargetMap.get(manageId);
                    BigDecimal ownMoneyTarget = paymentCollectionTargetDetails.stream().map(PaymentCollectionTargetDetail::getTargetDetailMoney).filter(targetDetailMoney -> !ObjectUtils.isEmpty(targetDetailMoney)).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal entrustMoney = paymentCollectionTargetDetails.stream().map(PaymentCollectionTargetDetail::getEntrustDetailMoney).filter(entrustDetailMoney -> !ObjectUtils.isEmpty(entrustDetailMoney)).reduce(BigDecimal.ZERO, BigDecimal::add);
                    manageLedgerDTO.setOwnTarget(ownMoneyTarget);
                    manageLedgerDTO.setEntrustMoneyTarget(entrustMoney);
                    manageLedgerDTO.setTotalPaymentCollectionTarget(ownMoneyTarget.add(entrustMoney));
                }
            }

        }
        SysDictionaryItemDTO collectionStatus002 = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
                .getData().getItems().stream().filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey())).findAny().get();
        projectIds = projectIds.stream().distinct().collect(Collectors.toList());
        LambdaQueryWrapper<RecoveryPaymentCollection> collectionQuery = new LambdaQueryWrapper<>();
        collectionQuery.select(RecoveryPaymentCollection::getProjectId, RecoveryPaymentCollection::getCollectionAmount, BaseDO::getId, RecoveryPaymentCollection::getCollectionDate,RecoveryPaymentCollection::getCollectionType)
                .in(RecoveryPaymentCollection::getProjectId, projectIds)
                .isNotNull(RecoveryPaymentCollection::getCollectionType)
                .eq(RecoveryPaymentCollection::getCollectionStatus, collectionStatus002.getId());
        List<RecoveryPaymentCollection> recoveryPaymentCollectionList = recoveryPaymentCollectionHandler.list(collectionQuery);
        if (!ObjectUtils.isEmpty(recoveryPaymentCollectionList)){
            Map<Long, List<RecoveryPaymentCollection>> collectionsByProjectId = recoveryPaymentCollectionList.stream().collect(Collectors.groupingBy(RecoveryPaymentCollection::getProjectId));
            for (ManageLedgerDTO manageLedgerDTO : dtoList) {
                // 本年回款
                    gePaymentCollectionData(collectionsByProjectId,manageLedgerDTO,currentYear);
            }
        }
        // 将所有金额转换为万元
        dtoList.forEach(dto -> {
            dto.setOwnTarget(dto.getOwnTarget().divide(TEN_THOUSAND, 6, RoundingMode.HALF_UP));
            dto.setTotalCompensationMoney(dto.getTotalCompensationMoney().divide(TEN_THOUSAND, 6, RoundingMode.HALF_UP));
            dto.setTotalPaymentCollectionTarget(dto.getTotalPaymentCollectionTarget().divide(TEN_THOUSAND, 6, RoundingMode.HALF_UP));
            dto.setTotalPaymentCollection(dto.getTotalPaymentCollection().divide(TEN_THOUSAND, 6, RoundingMode.HALF_UP));
            dto.setOwnPaymentCollectionAmount(dto.getOwnPaymentCollectionAmount().divide(TEN_THOUSAND, 6, RoundingMode.HALF_UP));
            dto.setEntrustMoneyTarget(dto.getEntrustMoneyTarget().divide(TEN_THOUSAND, 6, RoundingMode.HALF_UP));
            dto.setEntrustMoneyAmount(dto.getEntrustMoneyAmount().divide(TEN_THOUSAND, 6, RoundingMode.HALF_UP));
        });
        return Result.success(dtoList,PageFactory.page(page));
    }

    private void gePaymentCollectionData(Map<Long, List<RecoveryPaymentCollection>> collectionsByProjectId,ManageLedgerDTO manageLedgerDTO,int currentYear) {
        List<Long> projectInfos = manageLedgerDTO.getProjectInfos().stream().map(ProjectInfoRequest::getId).collect(Collectors.toList());
        List<RecoveryPaymentCollection> entrustCollections = new ArrayList<>();
        List<RecoveryPaymentCollection> ownCollections = new ArrayList<>();
        for (Long projectInfo : projectInfos) {
            List<RecoveryPaymentCollection> collect = Optional.ofNullable(collectionsByProjectId.get(projectInfo)).orElse(Lists.newArrayList()).stream().filter(e -> {
                if (ObjectUtils.isEmpty(e.getCollectionDate())) {
                    return false;
                }
                return currentYear == e.getCollectionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
            }).filter(e -> ObjectUtils.isNotEmpty(e.getCollectionAmount()) && "ENTRUST".equals(e.getCollectionType())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)){
                entrustCollections.addAll(collect);
            }
            List<RecoveryPaymentCollection> collect1 = Optional.ofNullable(collectionsByProjectId.get(projectInfo)).orElse(Lists.newArrayList()).stream().filter(e -> {
                if (ObjectUtils.isEmpty(e.getCollectionDate())) {
                    return false;
                }
                return currentYear == e.getCollectionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
            }).filter(e -> ObjectUtils.isNotEmpty(e.getCollectionAmount()) && "OWN".equals(e.getCollectionType())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect1)){
                ownCollections.addAll(collect1);
            }
        }
        if (CollectionUtils.isNotEmpty(entrustCollections)) {
            BigDecimal tempCollectionAmountTotal = entrustCollections.stream().map(RecoveryPaymentCollection::getCollectionAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            // 委托回款金额
            manageLedgerDTO.setEntrustMoneyAmount(tempCollectionAmountTotal);
        }
        if (CollectionUtils.isNotEmpty(ownCollections)){
            // 委托回款金额
            BigDecimal tempCollectionAmountTotal = ownCollections.stream().map(RecoveryPaymentCollection::getCollectionAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            manageLedgerDTO.setOwnPaymentCollectionAmount(tempCollectionAmountTotal);
        }
        // 累计回款金额
        manageLedgerDTO.setTotalPaymentCollection(manageLedgerDTO.getOwnPaymentCollectionAmount().add(manageLedgerDTO.getEntrustMoneyAmount()));
        // 自主回款完成率
        if (manageLedgerDTO.getOwnTarget().compareTo(BigDecimal.ZERO) > 0){
            manageLedgerDTO.setOwnDoneRate(manageLedgerDTO.getOwnPaymentCollectionAmount().divide(manageLedgerDTO.getOwnTarget(),4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
        }
        if (manageLedgerDTO.getEntrustMoneyTarget().compareTo(BigDecimal.ZERO) > 0){
            // 委托回款完成率
            manageLedgerDTO.setEntrustDoneRate(manageLedgerDTO.getEntrustMoneyAmount().divide(manageLedgerDTO.getEntrustMoneyTarget(),4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
        }
        if (manageLedgerDTO.getTotalPaymentCollectionTarget().compareTo(BigDecimal.ZERO) > 0){
            // 累计回款完成率
            manageLedgerDTO.setPaymentCollectionRate(manageLedgerDTO.getTotalPaymentCollection().divide(manageLedgerDTO.getTotalPaymentCollectionTarget(),4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));
        }
    }

    @Override
    public void exportManage(ProjectInfoPageRequest request, HttpServletResponse response) {
        Result<List<ManageLedgerDTO>> result = findManageAll(1, 10000, request);
        if (null == result || !result.succeedWithData()) {
            return;
        }

        List<ManageLedgerDTO> manageLedgerDTOList = result.getData();
        List<Long> managerIds = manageLedgerDTOList.stream()
            .map(e -> Long.valueOf(e.getManage()))
            .collect(Collectors.toList());
        Map<String, String> usernameById = platformUserUtils.getUserByIds(managerIds, false)
            .stream()
            .collect(Collectors.toMap(e -> String.valueOf(e.getId()), SysUserDTO::getUsername));

        manageLedgerDTOList.forEach(e -> {
            if (usernameById.containsKey(e.getManage())) {
                e.setManage(usernameById.get(e.getManage()));
            }
        });

        String fileName = "保全经理台账";
        ExcelUtil.exportExcel(manageLedgerDTOList, fileName, ManageLedgerDTO.class, response);
    }

    @Override
    public Result<ManageLedgerDetailDTO> findByManageId(Integer current, Integer size, ManagePageRequest request) {
        String manage = request.getManage();
        String projectName = request.getProjectName();
//        List<Date> createStampRange = request.getCreateStampRange();
        ManageLedgerDetailDTO result = new ManageLedgerDetailDTO();
        List<Long> manageId = Lists.newArrayList(Long.valueOf(manage));
        List<SysUserDTO> sysUserDTOList = platformUserUtils.getUserByIds(manageId, false);
        if (CollectionUtils.isEmpty(sysUserDTOList) || null == sysUserDTOList.get(0)){
            return Result.success(result);
        }
        String phone = sysUserDTOList.get(0).getPhone();
        // 保全经理名称
        result.setManageName(manage);
        result.setPhone(phone);
        LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ProjectInfo::getManage,manage)
                .eq(!ObjectUtils.isEmpty(projectName),ProjectInfo::getProjectName,projectName);
//
//        if (!ObjectUtils.isEmpty(createStampRange)){
//            queryWrapper.between(ProjectInfo::getCreateStamp,createStampRange.get(0),createStampRange.get(1));
//        }
        List<ProjectInfo> projectList = projectInfoHandler.list(queryWrapper);
        if (ObjectUtils.isEmpty(projectList)){
            return Result.success(result);
        }
        // 在管项目
        List<ManageLedgerProjectDTO> manageProjectList = findManageProjectList(projectList);
        result.setManageProjectNum(manageProjectList.size());
        BigDecimal compensationMoney = BigDecimal.ZERO;
        BigDecimal recoveryCollectionAmount = BigDecimal.ZERO;
        for (ManageLedgerProjectDTO manageLedgerProjectDTO : manageProjectList) {
            if (!ObjectUtils.isEmpty(manageLedgerProjectDTO)){
                compensationMoney = compensationMoney.add(manageLedgerProjectDTO.getCompensationMoney());
                recoveryCollectionAmount = recoveryCollectionAmount.add(manageLedgerProjectDTO.getTotalCollectionAmount());
            }
        }
        result.setCompensationMoney(compensationMoney);
        result.setRecoveryCollectionAmount(recoveryCollectionAmount);
        result.setResidueRecoveryAmount(compensationMoney.subtract(recoveryCollectionAmount));
        // 已核销
        List<ManageLedgerProjectDTO> collect1 = manageProjectList.stream().filter(ManageLedgerProjectDTO::getIsWriteOff).collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(collect1)){
            BigDecimal writeOffAmount = BigDecimal.ZERO;
            result.setWriteOffNum(collect1.size());
            List<Long> collect = collect1.stream().map(ManageLedgerProjectDTO::getProjectId).collect(Collectors.toList());
            LambdaQueryWrapper<WriteOff> writeOffQuery = new LambdaQueryWrapper<>();
            writeOffQuery.in(WriteOff::getProjectId,collect);
            List<WriteOff> list = writeOffHandler.list(writeOffQuery);
            for (WriteOff writeOff : list) {
                BigDecimal writeOffAmount1 = writeOff.getWriteDffAmount();
                if (!ObjectUtils.isEmpty(writeOffAmount1)){
                    writeOffAmount =   writeOffAmount.add(writeOffAmount1);
                }
            }
            result.setTotalWriteOffAmount(writeOffAmount);
        }else {
            result.setWriteOffNum(0);
            result.setTotalWriteOffAmount(BigDecimal.ZERO);
        }
        LocalDate today = LocalDate.now();
        LocalDateTime startTime = today.atStartOfDay();
        ZonedDateTime zdtStart = startTime.atZone(ZoneId.systemDefault());
        // 获取当前日期（今天）的最开始时间
        Date startDate = Date.from(zdtStart.toInstant());
        LocalDate futureDate = today.plusDays(90);
        LocalDateTime endTime = futureDate.atTime(LocalTime.MAX);
        ZonedDateTime zdtEnd = endTime.atZone(ZoneId.systemDefault());
        // 90天后
        Date endDate = Date.from(zdtEnd.toInstant());
        int target = 0;
        int proceedTarget = 0;
        int transferTarget = 0;
        for (ManageLedgerProjectDTO manageLedgerProjectDTO : manageProjectList) {
            Date adjustTrialDate = manageLedgerProjectDTO.getAdjustTrialDate();
            Date proceedingAgeingDate = manageLedgerProjectDTO.getProceedingAgeingDate();
            Date transferDate = manageLedgerProjectDTO.getTransferDate();
            if (!ObjectUtils.isEmpty(adjustTrialDate) && adjustTrialDate.after(startDate) && adjustTrialDate.before(endDate)){
                target++;
            }
            if (!ObjectUtils.isEmpty(proceedingAgeingDate) && proceedingAgeingDate.after(startDate) && proceedingAgeingDate.before(endDate)){
                proceedTarget++;
            }
            if (!ObjectUtils.isEmpty(transferDate) && transferDate.after(startDate) && transferDate.before(endDate)){
                transferTarget++;
            }
        }
        result.setAdjustTrialDueDateNum(target);
        result.setProceedingAgeingDueDateNum(proceedTarget);
        result.setTransferDueDateNum(transferTarget);
        // 已结案
        SysDictionaryItemDTO status = sysDictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey())
                .getData().getItems().stream().filter(item -> item.getItemCode().equals(DictEnum.PROJECT_STATE_15.getKey())).findAny().get();
        List<ManageLedgerProjectDTO> collect2 = manageProjectList.stream().filter(a -> Objects.equals(a.getProjectState(), status.getId())).collect(Collectors.toList());
        // 将金额 设置成 万元
        result.setCompensationMoney(result.getCompensationMoney() == null ? BigDecimal.ZERO : result.getCompensationMoney().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
        result.setRecoveryCollectionAmount(result.getRecoveryCollectionAmount() == null? BigDecimal.ZERO : result.getRecoveryCollectionAmount().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
        result.setResidueRecoveryAmount(result.getResidueRecoveryAmount() == null? BigDecimal.ZERO : result.getResidueRecoveryAmount().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
        result.setTotalWriteOffAmount(result.getTotalWriteOffAmount() == null? BigDecimal.ZERO : result.getTotalWriteOffAmount().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
        result.setTotalCollectionRate(result.getRecoveryCollectionAmount().divide(result.getCompensationMoney(),4,RoundingMode.HALF_UP).multiply(new BigDecimal("100")));
//        List<ManageLedgerProjectDTO> manageProjectList1 = result.getManageProjectList().getData();
//        if (!ObjectUtils.isEmpty(manageProjectList1)){
//            for (ManageLedgerProjectDTO data : manageProjectList1) {
//                data.setCompensationMoney(data.getCompensationMoney() == null ? BigDecimal.ZERO : data.getCompensationMoney().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
//                data.setTotalCollectionAmount(data.getTotalCollectionAmount() == null ? BigDecimal.ZERO : data.getTotalCollectionAmount().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
//                data.setResidueRecoveryAmount(data.getResidueRecoveryAmount() == null ? BigDecimal.ZERO : data.getResidueRecoveryAmount().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
//            }
//        }
//        List<ManageLedgerProjectDTO> writeOffProjectList = result.getWriteOffProjectList().getData();
//        if (!ObjectUtils.isEmpty(writeOffProjectList)){
//            for (ManageLedgerProjectDTO data : writeOffProjectList) {
//                data.setCompensationMoney(data.getCompensationMoney() == null ? BigDecimal.ZERO : data.getCompensationMoney().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
//                data.setTotalCollectionAmount(data.getTotalCollectionAmount() == null ? BigDecimal.ZERO : data.getTotalCollectionAmount().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
//                data.setResidueRecoveryAmount(data.getResidueRecoveryAmount() == null ? BigDecimal.ZERO : data.getResidueRecoveryAmount().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
//            }
//        }
//        List<ManageLedgerProjectDTO> closeCaseProjectList = result.getCloseCaseProjectList().getData();
//        if (!ObjectUtils.isEmpty(closeCaseProjectList)){
//            for (ManageLedgerProjectDTO data : closeCaseProjectList) {
//                data.setCompensationMoney(data.getCompensationMoney() == null ? BigDecimal.ZERO : data.getCompensationMoney().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
//                data.setTotalCollectionAmount(data.getTotalCollectionAmount() == null ? BigDecimal.ZERO : data.getTotalCollectionAmount().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
//                data.setResidueRecoveryAmount(data.getResidueRecoveryAmount() == null ? BigDecimal.ZERO : data.getResidueRecoveryAmount().divide(TEN_THOUSAND,4,RoundingMode.HALF_UP));
//            }
//        }
        return Result.success(result);
    }


    private List<ManageLedgerProjectDTO> findManageProjectList(List<ProjectInfo> projectList) {
        List<ManageLedgerProjectDTO> resList = new ArrayList<>();
        List<Long> longList = new ArrayList<>();
        for (ProjectInfo projectInfo : projectList) {
            ManageLedgerProjectDTO manageLedger = new ManageLedgerProjectDTO();
            manageLedger.setProjectName(projectInfo.getProjectName());
            manageLedger.setProjectState(projectInfo.getProjectState());
            manageLedger.setCompensationMoney(projectInfo.getCompensationMoney());
            manageLedger.setCompensationDate(projectInfo.getCompensationDate());
            manageLedger.setAdjustTrialDate(projectInfo.getAdjustTrialDate());
            manageLedger.setProceedingAgeingDate(projectInfo.getProceedingAgeingDate());
            manageLedger.setProjectId(projectInfo.getId());
            manageLedger.setIsWriteOff(projectInfo.getIsWriteOff());
            manageLedger.setTransferDate(projectInfo.getTransferDate());
            longList.add(projectInfo.getId());
            resList.add(manageLedger);
        }
        Map<Long, Long> lawFirmIdByProjectId = projectList.stream().distinct()
            .filter(a -> ObjectUtils.isNotEmpty(a.getLawFirmId()))
            .collect(Collectors.toMap(ProjectInfo::getId, ProjectInfo::getLawFirmId));
        List<Long> lawFirmIds = lawFirmIdByProjectId.values().stream().distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(lawFirmIds)) {
            List<LawFirmInfo> lawFirmInfos = firmInfoHandler.listByIds(lawFirmIds);
            Map<Long, String> lawFirmNameById = lawFirmInfos.stream()
                .collect(Collectors.toMap(LawFirmInfo::getId, LawFirmInfo::getName));

            for (ManageLedgerProjectDTO res : resList) {
                if (!lawFirmIdByProjectId.containsKey(res.getProjectId())) {
                    continue;
                }
                Long lawFirmId = lawFirmIdByProjectId.get(res.getProjectId());
                if (!lawFirmNameById.containsKey(lawFirmId)) {
                    continue;
                }
                res.setName(lawFirmNameById.get(lawFirmId));
            }
        }
//        List<ProjectInfo> collect2 = projectList.stream().filter(a -> !ObjectUtils.isEmpty(a.getLawyerId())).collect(Collectors.toList());
//        if (!ObjectUtils.isEmpty(collect2)){
//            List<Long> collect = hadLawFirmProjects.stream().map(ProjectInfo::getLawyerId).collect(Collectors.toList());
//            List<LawyerInfo> lawyerInfos = lawyerInfoHandler.listByIds(collect);
//            if (!ObjectUtils.isEmpty(lawyerInfos)){
//                Map<Long, List<LawyerInfo>> collect3 = lawyerInfos.stream().collect(Collectors.groupingBy(LawyerInfo::getId));
//                for (ManageLedgerProjectDTO res : resList) {
//                    List<LawyerInfo> lawyerInfos1 = collect3.get(res.getProjectId());
//                    if (!ObjectUtils.isEmpty(lawyerInfos1)){
//                        res.setLawyerName(lawyerInfos1.get(0).getLawyerName());
//                        res.setPhone(lawyerInfos1.get(0).getPhone());
//                    }
//                }
//
//            }
//        }

        SysDictionaryItemDTO sysDictionaryItemDTO = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
                .getData()
                .getItems()
                .stream()
                .filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey())).findAny().get();

        LambdaQueryWrapper<RecoveryPaymentCollection> collectionQuery = new LambdaQueryWrapper<>();
        collectionQuery.in(RecoveryPaymentCollection::getProjectId,longList)
                .eq(RecoveryPaymentCollection::getCollectionStatus,sysDictionaryItemDTO.getId());
        List<RecoveryPaymentCollection> list = recoveryPaymentCollectionHandler.list(collectionQuery);
        if (ObjectUtils.isEmpty(list)) {
            for (ManageLedgerProjectDTO manageLedgerProjectDTO : resList) {
                manageLedgerProjectDTO.setTotalCollectionAmount(BigDecimal.ZERO);
                manageLedgerProjectDTO.setResidueRecoveryAmount(manageLedgerProjectDTO.getCompensationMoney());
            }
        } else {
            Map<Long, List<RecoveryPaymentCollection>> collect = list.stream().collect(Collectors.groupingBy(RecoveryPaymentCollection::getProjectId));
            for (ManageLedgerProjectDTO manageLedgerProjectDTO : resList) {
                BigDecimal total = BigDecimal.ZERO;
                Long projectId = manageLedgerProjectDTO.getProjectId();
                List<RecoveryPaymentCollection> paymentCollectionList = collect.get(projectId);
                if (!ObjectUtils.isEmpty(paymentCollectionList)) {
                    for (RecoveryPaymentCollection collection : paymentCollectionList) {
                        BigDecimal collectionAmount = collection.getCollectionAmount();
                        if (!ObjectUtils.isEmpty(collectionAmount)) {
                            total = total.add(collectionAmount);
                        }
                    }
                }
                manageLedgerProjectDTO.setTotalCollectionAmount(total);
                manageLedgerProjectDTO.setResidueRecoveryAmount((manageLedgerProjectDTO.getCompensationMoney().subtract(total)));
            }
        }
        // 查询未处置的反担保
        LambdaQueryWrapper<RevePropertyInfo> reveInfoQuery = new LambdaQueryWrapper<>();
        reveInfoQuery
                .eq(RevePropertyInfo::getIsDispose,false)
                .in(RevePropertyInfo::getDoId,longList)
                .eq(RevePropertyInfo::getDoType, BillTypeEnum.PROJECT_INFO);
//                .eq(RevePropertyInfo::getBillType,BillTypeEnum.REVE);
        List<RevePropertyInfo> list1 = revePropertyInfoHandler.list(reveInfoQuery);
        if (ObjectUtils.isEmpty(list1)){
            resList.forEach(a ->{a.setResidueReveMeasureNum(AssertConstants.CONSTANT_ZERO);});
        }else {
            Map<Long, List<RevePropertyInfo>> collect = list1.stream().collect(Collectors.groupingBy(RevePropertyInfo::getDoId));
            for (ManageLedgerProjectDTO manageLedgerProjectDTO : resList) {
                Long projectId = manageLedgerProjectDTO.getProjectId();
                List<RevePropertyInfo> revePropertyInfos = collect.get(projectId);
                if (!ObjectUtils.isEmpty(revePropertyInfos)){
                    manageLedgerProjectDTO.setResidueReveMeasureNum(revePropertyInfos.size());
                }else {
                    manageLedgerProjectDTO.setResidueReveMeasureNum(AssertConstants.CONSTANT_ZERO);
                }
            }
        }
        return resList;
    }

    @Override
    public Result<List<PaymentCollectionPageDTO>> findPaymentCollectionAll(Integer current, Integer size, PaymentCollectionLedgerPageRequest request) {
        IPage<PaymentCollectionPageDTO> page = new Page<>(current, size);
        Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
        request.setOrgUserIds(userLimitsOrg);
        Long state = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
                .getData()
                .getItems()
                .stream()
                .filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey()))
                .findAny().get().getId();
        request.setStatus(state);
        List<PaymentCollectionPageDTO> dtoList =  projectInfoMapper.findPaymentCollectionAll(page,request);
        if (dtoList.isEmpty()){
            return Result.success(dtoList,PageFactory.page(page));
        }
        List<Long> collect1 = dtoList.stream().map(PaymentCollectionPageDTO::getCollectionId).collect(Collectors.toList());
        LambdaQueryWrapper<RecoveryPaymentCollectionDetail>  queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RecoveryPaymentCollectionDetail::getPaymentCollectionId,collect1);
        List<RecoveryPaymentCollectionDetail> list = recoveryPaymentCollectionDetailHandler.list(queryWrapper);
        if (ObjectUtils.isEmpty(list)){
            return Result.success(dtoList,PageFactory.page(page));
        }
        List<Long> provinceIds = dtoList.stream().map(PaymentCollectionPageDTO::getBelongProvince).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<Long> cityIds = dtoList.stream()
                .map(PaymentCollectionPageDTO::getBelongCity)
                .filter(Objects::nonNull)
                .map(Long::parseLong)
                .distinct()
                .collect(Collectors.toList());
        List<Long> districtIds = dtoList.stream().map(PaymentCollectionPageDTO::getBelongDistrict).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<Long> regionIds = Lists.newArrayList();
        regionIds.addAll(provinceIds);
        regionIds.addAll(cityIds);
        regionIds.addAll(districtIds);
        Map<Long, String> regionNameById = platformFieldUtils.getRegionNameById(regionIds);
        Map<Long, List<RecoveryPaymentCollectionDetail>> collect = list.stream().collect(Collectors.groupingBy(RecoveryPaymentCollectionDetail::getPaymentCollectionId));
        for (PaymentCollectionPageDTO collectionPageDTO : dtoList) {
            Long collectionId = collectionPageDTO.getCollectionId();
            List<RecoveryPaymentCollectionDetail> recoveryPaymentCollectionDetails = collect.get(collectionId);
            BigDecimal interest = BigDecimal.ZERO;
            BigDecimal compensatoryCash = BigDecimal.ZERO;
            BigDecimal otherFee = BigDecimal.ZERO;
            BigDecimal defaultCash = BigDecimal.ZERO;
            if (CollectionUtils.isNotEmpty(recoveryPaymentCollectionDetails)){
                for (RecoveryPaymentCollectionDetail detail : recoveryPaymentCollectionDetails) {
                    String collectionDetailType = detail.getCollectionDetailType();
                    BigDecimal collectionDetailAmount = detail.getCollectionDetailAmount();
                    if (!ObjectUtils.isEmpty(collectionDetailAmount)) {
                        if ("COMPENSATORY_CASH".equals(collectionDetailType)) {
                            compensatoryCash = compensatoryCash.add(collectionDetailAmount);
                        }
                        if ("INTEREST".equals(collectionDetailType)) {
                            interest = interest.add(collectionDetailAmount);
                        }
                        if ("OTHER_FEE".equals(collectionDetailType)) {
                            otherFee = otherFee.add(collectionDetailAmount);
                        }
                        if ("DEFAULT_CASH".equals(collectionDetailType)) {
                            defaultCash = defaultCash.add(collectionDetailAmount);
                        }
                    }
                }
            }
            collectionPageDTO.setInterest(interest);
            collectionPageDTO.setCompensatoryCash(compensatoryCash);
            collectionPageDTO.setDefaultCash(defaultCash);
            collectionPageDTO.setOtherFee(otherFee);
            String belongCity = collectionPageDTO.getBelongCity();
            Long belongDistrict = collectionPageDTO.getBelongDistrict();
            StringBuilder stringBuilder = new StringBuilder();
            Long belongProvince = collectionPageDTO.getBelongProvince();
            if (ObjectUtils.isNotEmpty(belongProvince)) {
                String province = regionNameById.get(belongProvince);
                if (PlatformStringUtils.isNotBlank(province)) {
                    stringBuilder.append(province);
                }
            }
            if (PlatformStringUtils.isNotBlank(belongCity)) {
                String city = regionNameById.get(Long.valueOf(belongCity));
                if (PlatformStringUtils.isNotBlank(city)) {
                    if (PlatformStringUtils.isNotBlank(stringBuilder.toString())) {
                        stringBuilder.append("-").append(city);
                    } else {
                        stringBuilder.append(city);
                    }
                }
            }
            if (ObjectUtils.isNotEmpty(belongDistrict)) {
                String district = regionNameById.get(belongDistrict);
                    if (PlatformStringUtils.isNotBlank(stringBuilder.toString())) {
                        stringBuilder.append("-").append(district);
                    } else {
                        stringBuilder.append(district);
                    }
            }
            collectionPageDTO.setBelongCity(stringBuilder.toString());
        }

        return Result.success(dtoList,PageFactory.page(page));
    }

    @Override
    public void exportPaymentCollection(PaymentCollectionLedgerPageRequest request, HttpServletResponse response) {
        Result<List<PaymentCollectionPageDTO>> result = findPaymentCollectionAll(1, 10000, request);
        if (null == result || !result.succeedWithData()) {
            return;
        }

        List<CollectionLedgerExportDTO> ledgerDTOList = PlatformMapUtils.getInstance().mapAsList(result.getData(), CollectionLedgerExportDTO.class);

        ledgerDTOList.forEach(e -> {
            if (ObjectUtils.isNotEmpty(e.getCollectionSign())) {
                e.setCollectionSignName(CollectionSignEnum.getValue(e.getCollectionSign()));
            }
            if (ObjectUtils.isNotEmpty(e.getCollectionAscription())) {
                e.setCollectionAscriptionName(CollectionAscriptionEnum.getValue(e.getCollectionAscription()));
            }
        });

        String fileName = "回款台账";
        ExcelUtil.exportExcel(ledgerDTOList, fileName, CollectionLedgerExportDTO.class, response);
    }

    @Override
    public Result<List<ProjectLedgerDTO>> findProjectLedgerAll(Integer current, Integer size, ProjectInfoPageRequest request) {
        IPage<ProjectLedgerDTO> page = new Page<>(current, size);
        Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
        request.setOrgUserIds(userLimitsOrg);
        List<ProjectLedgerDTO> projectInfoList = projectInfoMapper.findProjectLedgerAll(page, request);
        if (ObjectUtils.isEmpty(projectInfoList)){
            return Result.success(new ArrayList<>());
        }
        List<RecoveryJudgement> judgementList = recoveryJudgementHandler.list(new LambdaQueryWrapper<RecoveryJudgement>()
                .in(RecoveryJudgement::getProjectId, projectInfoList.stream().map(ProjectLedgerDTO::getId).collect(Collectors.toList()))
                .eq(RecoveryJudgement::getFlowState, "completed"));
        Map<Long, RecoveryJudgement> newJudgementMap = new HashedMap<>();
        if (CollectionUtils.isNotEmpty(judgementList)){
            Map<Long, List<RecoveryJudgement>> judgementMap = judgementList.stream().collect(Collectors.groupingBy(RecoveryJudgement::getProjectId));
            judgementMap.forEach((key,value)->{
                for (RecoveryJudgement recoveryJudgement : value) {
                    if (newJudgementMap.isEmpty()){
                        newJudgementMap.put(key,recoveryJudgement);
                    }else {
                        Date fillingDate = recoveryJudgement.getFillingDate();
                        if (newJudgementMap.get(key) != null){
                            if (newJudgementMap.get(key).getFillingDate().compareTo(fillingDate) < 0 ){
                                newJudgementMap.put(key,recoveryJudgement);
                            }
                        }
                    }
                }
            });
        }
        List<ProjectLedgerDTO> projectLedger = PlatformMapUtils.getInstance().mapAsList(projectInfoList, ProjectLedgerDTO.class);
        Map<Long, List<SysDictionaryItemDTO>> industryTypeItems = sysDictionaryClient.findByCode(DictEnum.INDUSTRY_GXB.getKey())
                .getData().getItems().stream().collect(Collectors.groupingBy(SysDictionaryItemDTO::getId));
        List<Long> projectIds = projectLedger.stream().map(ProjectLedgerDTO::getId).collect(Collectors.toList());
        List<ProjectLedgerDTO> lawyerInfos = projectInfoMapper.findLawyerInfo(projectIds);
        List<ProjectBusinessInfo> businessInfos = projectBusinessInfoHandler.list(new LambdaQueryWrapper<ProjectBusinessInfo>().in(ProjectBusinessInfo::getProjectId, projectIds));
        Map<Long, List<ProjectLedgerDTO>> lawyers = null;
        List<Long> userIds = null;
        Map<Long, List<ProjectBusinessInfo>> businessInfosMap = null;
        if (ObjectUtils.isNotEmpty(lawyerInfos)){
            lawyers = lawyerInfos.stream().collect(Collectors.groupingBy(ProjectLedgerDTO::getId));
        }
        if (ObjectUtils.isNotEmpty(businessInfos)){
            userIds = businessInfos.stream()
                    .flatMap(info -> Stream.of(info.getAid(), info.getBid())).distinct()
                    .collect(Collectors.toList());
            businessInfosMap = businessInfos.stream().collect(Collectors.groupingBy(ProjectBusinessInfo::getProjectId));
        }
        Map<String, String> abid = platformUserUtils.getUserByIds(userIds, false)
                .stream()
                .collect(Collectors.toMap(e -> String.valueOf(e.getId()), SysUserDTO::getUsername));
        // 查询 主体主体信息 获取 省市区
        List<SubjectInfo> subjectInfos = subjectInfoHandler.listByIds(projectLedger.stream().map(ProjectLedgerDTO::getDebtorId).collect(Collectors.toList()));
        Map<Long, List<SubjectInfo>> subjectList = subjectInfos.stream().collect(Collectors.groupingBy(SubjectInfo::getId));
        List<Long> provinceIds = subjectInfos.stream().map(SubjectInfo::getBelongProvince).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<Long> cityIds = subjectInfos.stream().map(SubjectInfo::getBelongCity).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<Long> districtIds = subjectInfos.stream().map(SubjectInfo::getBelongDistrict).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<Long> regionIds = Lists.newArrayList();
        regionIds.addAll(provinceIds);
        regionIds.addAll(cityIds);
        regionIds.addAll(districtIds);
        regionIds.addAll(provinceIds);
        regionIds.addAll(cityIds);
        regionIds.addAll(districtIds);
        Map<Long, String> regionNameById = platformFieldUtils.getRegionNameById(regionIds);
        // 反担保，其他线索措施
        LambdaQueryWrapper<RevePropertyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RevePropertyInfo::getDoType,BillTypeEnum.PROJECT_INFO)
                .in(RevePropertyInfo::getDoId,projectIds);
        List<RevePropertyInfo> revePropertyInfos = revePropertyInfoHandler.list(queryWrapper);
        if (!ObjectUtils.isEmpty(revePropertyInfos)) {
            for (ProjectLedgerDTO project : projectLedger) {
                // 反担保/其他线索（重组）
                String reveName = revePropertyInfos.stream().filter(item -> project.getId().equals(item.getDoId()) && null != item.getReveName() && !"".equals(item.getReveName())).map(item -> item.getReveName()).collect(Collectors.joining("、"));
                String reveMeasure = revePropertyInfos.stream().filter(item -> project.getId().equals(item.getDoId()) && null != item.getReveMeasure() && !"".equals(item.getReveMeasure())).map(item -> item.getReveMeasure()).collect(Collectors.joining("；"));
                BigDecimal disposeMoneySum = revePropertyInfos.stream()
                        .filter(item -> project.getId().equals(item.getDoId()) && null != item.getDisposeMoney())
                        .map(RevePropertyInfo::getDisposeMoney)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal debtRepaymentMoneySum = revePropertyInfos.stream()
                        .filter(item -> project.getId().equals(item.getDoId()) && null != item.getDebtRepaymentMoney())
                        .map(RevePropertyInfo::getDebtRepaymentMoney)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                project.setReveName(reveName);
                project.setDefendant(reveName);
                project.setReveMeasure(reveMeasure);
                project.setDisposeMoney(disposeMoneySum);
                project.setDebtRepaymentMoney(debtRepaymentMoneySum);
                // 反担保/其他线索
//                List<RevePropertyInfoDTO> reveInfoList = new ArrayList<>();
//                for (RevePropertyInfo revePropertyInfo : revePropertyInfos) {
//                    if (project.getId().equals(revePropertyInfo.getDoId())) {
//                        RevePropertyInfoDTO map = PlatformMapUtils.getInstance().map(revePropertyInfo, RevePropertyInfoDTO.class);
//                        Long securityWay = map.getSecurityWay();
//                        if (CollectionUtils.isNotEmpty(securityWayItemMap.get(securityWay))){
//                            map.setSecurityWayStr(securityWayItemMap.get(securityWay).get(0).getItemName());
//                        }
//                        reveInfoList.add(map);
//                    }
//                }
//                if (!reveInfoList.isEmpty()){
//                    project.setReveInfoList(reveInfoList);
//                }
            }

        }
        LambdaQueryWrapper<RecoveryLitigationDetails> litigationQuery = new LambdaQueryWrapper<>();
        litigationQuery.eq(RecoveryLitigationDetails::getLitigationType,BillTypeEnum.PRESERVATION)
                .in(RecoveryLitigationDetails::getProjectId,projectIds);
        List<RecoveryLitigationDetails> list = recoveryLitigationDetailsHandler.list(litigationQuery);
        for (ProjectLedgerDTO project : projectLedger) {
            if (!ObjectUtils.isEmpty(list)) {
                for (RecoveryLitigationDetails litigation : list) {
                    if (project.getId().compareTo(litigation.getProjectId()) == 0) {
                        Date detailsDate = litigation.getDetailsDate();
                        String preservationCode = litigation.getPreservationCode();
                        // 保全日期
                            project.setPreservationDate(detailsDate);
                            project.setPreservationCode(preservationCode);

                    }
                }
            }
            String industryType = project.getIndustryType();
            if (PlatformStringUtils.isNotBlank(industryType)) {
                List<SysDictionaryItemDTO> industry = industryTypeItems.get(Long.valueOf(industryType));
                if (CollectionUtils.isNotEmpty(industry)) {
                    project.setIndustryType(industry.get(0).getItemName());
                }
            }
//            String projectState = project.getProjectState();
//            if (PlatformStringUtils.isNotBlank(projectState)) {
//                List<SysDictionaryItemDTO> projectStateItems = projectStatus.get(Long.valueOf(projectState));
//                if (CollectionUtils.isNotEmpty(projectStateItems)) {
//                    project.setProjectState(projectStateItems.get(0).getItemName());
//                }
//            }
            // 省市区
            Long debtorId = project.getDebtorId();
            if (ObjectUtils.isNotEmpty(debtorId)) {
                SubjectInfo subjectInfo = subjectInfos.stream().filter(item -> item.getId().compareTo(debtorId) == 0).findFirst().orElse(null);
                if (ObjectUtils.isNotEmpty(subjectInfo)) {
                    Long province = subjectInfo.getBelongProvince();
                    Long belongCity = subjectInfo.getBelongCity();
                    Long belongDistrict = subjectInfo.getBelongDistrict();
                    if (ObjectUtils.isNotEmpty(province)) {
                        project.setBelongProvince(regionNameById.get(province));
                    }
                    if (ObjectUtils.isNotEmpty(belongCity)) {
                        project.setBelongCity(regionNameById.get(belongCity));
                    }
                    if (ObjectUtils.isNotEmpty(belongDistrict)) {
                        project.setBelongDistrict(regionNameById.get(belongDistrict));
                    }
                    project.setDocumentNumber(subjectInfo.getDocumentNumber());
                    project.setSubjectType(subjectInfo.getSubjectType());
                }

            }
            // 保全经理 转中文
            String manager = project.getManage();
            if (StringUtils.isNotEmpty(manager)){
                String[] split = manager.split(",");
                List<Long> managerIds = Arrays.stream(split).map(Long::valueOf).collect(Collectors.toList());
                Map<String, String> usernameById = platformUserUtils.getUserByIds(managerIds, false)
                        .stream()
                        .collect(Collectors.toMap(e -> String.valueOf(e.getId()), SysUserDTO::getUsername));
                project.setManage(String.join(",", usernameById.values()));
            }
            if (ObjectUtils.isNotEmpty(lawyers)){
                Long id = project.getId();
                List<ProjectLedgerDTO> lawInfo = lawyers.get(id);
                if (CollectionUtils.isNotEmpty(lawInfo)){
                    String lawyerInfo = lawInfo.stream().map(ProjectLedgerDTO::getLawyerName).distinct().collect(Collectors.joining(","));
                    project.setLawyerName(lawyerInfo);
                    project.setLawyerPhone(lawInfo.stream().map(ProjectLedgerDTO::getLawyerPhone).distinct().collect(Collectors.joining(",")));
                }
            }
            // a角b角
            if (ObjectUtils.isNotEmpty(businessInfosMap)){
                List<ProjectBusinessInfo> infoList = businessInfosMap.get(project.getId());
                if (CollectionUtils.isNotEmpty(infoList)){
                    StringBuilder aidStr = new StringBuilder();
                    StringBuilder bidStr = new StringBuilder();
                    List<Long> aids = infoList.stream().map(ProjectBusinessInfo::getAid).filter(Objects::nonNull).distinct().collect(Collectors.toList());
                    List<Long> bids = infoList.stream().map(ProjectBusinessInfo::getBid).filter(Objects::nonNull).distinct().collect(Collectors.toList());
                    if (!aids.isEmpty()){
                        for (Long aid : aids) {
                            String s = abid.get(aid.toString());
                            if (StringUtils.isEmpty(aidStr.toString())){
                                aidStr.append(s);
                            }else {
                                aidStr.append(",").append(s);
                            }
                        }
                    }
                    if (!bids.isEmpty()){
                        for (Long bid : bids) {
                            String s = abid.get(bid.toString());
                            if (StringUtils.isEmpty(bidStr.toString())){
                                bidStr.append(s);
                            }else {
                                bidStr.append(",").append(s);
                            }

                        }
                    }
                    project.setAname(aidStr.toString());
                    project.setBname(bidStr.toString());
                }

            }
            if (CollectionUtils.isNotEmpty(subjectList.get(debtorId))) {
//              project.setDefendant(subjectList.get(debtorId).get(0).getSubjectName());
                if (ObjectUtils.isEmpty(project.getDefendant())) {
                    project.setDefendant(subjectList.get(debtorId).get(0).getSubjectName());
                } else {
                    project.setDefendant(subjectList.get(debtorId).get(0).getSubjectName() + "、" + project.getDefendant());
                }
            }
            RecoveryJudgement recoveryJudgement = newJudgementMap.get(project.getId());
            if (ObjectUtils.isNotEmpty(recoveryJudgement)){
                //立案案号
                project.setFillingCode(recoveryJudgement.getFillingCode());
                project.setFillingDate(recoveryJudgement.getFillingDate());
                //开庭时间
                project.setCourtSessionDate(recoveryJudgement.getCourtSessionDate());
                //判决日期
                project.setJudgeDate(recoveryJudgement.getJudgeDate());
            }
        }

        LambdaQueryWrapper<RecoveryExecute> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .in(RecoveryExecute::getProjectId,projectIds);
        List<RecoveryExecute> recoveryExecutes = recoveryExecuteHandler.list(lambdaQueryWrapper);
        if (!ObjectUtils.isEmpty(recoveryExecutes)){
            for (ProjectLedgerDTO project : projectLedger) {
                for (RecoveryExecute execute : recoveryExecutes) {
                    if (project.getId().equals(execute.getProjectId())){
                        String executeCode = execute.getExecuteCode();
                        if (ObjectUtils.isEmpty(executeCode)){
                            // 执行案号
                            project.setExecuteCode(executeCode);
                        }
                    }
                }
            }
        }
        LambdaQueryWrapper<RecoveryAdjustTrial> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(RecoveryAdjustTrial::getAdjustTrialType, RecoveryAdjustTrialRequest.AdjustTrialTypeEnum.JUDG)
                .in(RecoveryAdjustTrial::getProjectId,projectIds);
        List<RecoveryAdjustTrial> list2 = recoveryAdjustTrialHandler.list(queryWrapper1);
        if (!ObjectUtils.isEmpty(list2)){
            for (ProjectLedgerDTO project : projectLedger) {
                for (RecoveryAdjustTrial trial : list2) {
                    if (project.getId().compareTo(trial.getProjectId()) == 0){
                        String adjustCode = trial.getAdjustCode();
                        if (ObjectUtils.isEmpty(adjustCode)){
                            // 判决案号
                            project.setAdjustCode(adjustCode);
                        }
                    }
                }
            }
        }
        SysDictionaryItemDTO status = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
                .getData().getItems().stream().filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey())).findAny().get();
        List<Long> collect1 = projectLedger.stream().map(ProjectLedgerDTO::getId).distinct().collect(Collectors.toList());
        LambdaQueryWrapper<RecoveryPaymentCollection> collectionQuery = new LambdaQueryWrapper<>();
        collectionQuery.select(RecoveryPaymentCollection::getProjectId,RecoveryPaymentCollection::getCollectionAmount,RecoveryPaymentCollection::getCollectionSign)
                .in(RecoveryPaymentCollection::getProjectId,collect1)
                .eq(RecoveryPaymentCollection::getCollectionStatus,status.getId());
        List<RecoveryPaymentCollection> collectionList = recoveryPaymentCollectionHandler.list(collectionQuery);
        if (!ObjectUtils.isEmpty(collectionList)) {
            Map<Long, List<RecoveryPaymentCollection>> collect = collectionList.stream().collect(Collectors.groupingBy(RecoveryPaymentCollection::getProjectId));
            for (ProjectLedgerDTO project : projectLedger) {
                List<RecoveryPaymentCollection> paymentCollectionList = collect.get(project.getId());
                if (!ObjectUtils.isEmpty(paymentCollectionList)) {
                    BigDecimal payment = BigDecimal.ZERO;
                    for (RecoveryPaymentCollection collection : paymentCollectionList) {
                        BigDecimal collectionAmount = collection.getCollectionAmount();
                        if (!ObjectUtils.isEmpty(collectionAmount)) {
                            payment = payment.add(collectionAmount);
                        }
                    }
                    Map<String, List<RecoveryPaymentCollection>> signList = paymentCollectionList.stream().collect(Collectors.groupingBy(RecoveryPaymentCollection::getCollectionSign));
                    if (ObjectUtils.isNotEmpty(signList)){
                        CollectionSignEnum[] enumList = CollectionSignEnum.values();
                        for (CollectionSignEnum key : enumList) {
                            if (signList.containsKey(key.getKey())) {
                                BigDecimal totalAmount = signList.get(key.getKey()).stream()
                                        .map(RecoveryPaymentCollection::getCollectionAmount)
                                        .reduce(BigDecimal.ZERO, (a, b) -> a.add(Optional.ofNullable(b).orElse(BigDecimal.ZERO)));
                                switch (key.getKey()) {
                                    case "CASH": project.setCashAmount(totalAmount);break;
                                    case "RE_GUARANTEE": project.setReGuaranteeAmount(totalAmount);break;
                                    case "MORTGAGE": project.setMortgageAmount(totalAmount);break;
                                    case "SILVER_BILL":project.setSilverBillAmount(totalAmount);break;
                                    default: break;
                                }
                            }
                        }
                    }
                    project.setRecoveryCollectionAmount(payment);
                    project.setResidueRecoveryAmount(project.getCompensationMoney().subtract(payment));
                }
            }
        } else {
            projectLedger.forEach(a -> {
                a.setRecoveryCollectionAmount(BigDecimal.ZERO);
                a.setResidueRecoveryAmount(a.getCompensationMoney());
            });
        }

        SysDictionaryItemDTO status1 = sysDictionaryClient.findByCode(DictEnum.PAY_STATUS.getKey())
                .getData().getItems().stream().filter(item -> item.getItemCode().equals(DictEnum.PAY_STATUS_002.getKey())).findAny().get();
        LambdaQueryWrapper<RecoveryPayment>  lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.in(RecoveryPayment::getProjectId,collect1)
                .eq(RecoveryPayment::getPayStatus,status1.getId());
        List<RecoveryPayment> recoveryPayments = recoveryPaymentHandler.list(lambdaQueryWrapper1);
        if (!ObjectUtils.isEmpty(recoveryPayments)){
            List<Long> collect2 = recoveryPayments.stream().map(BaseDO::getId).collect(Collectors.toList());
            LambdaQueryWrapper<RecoveryPaymentDetail>  detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            detailLambdaQueryWrapper.in(RecoveryPaymentDetail::getPaymentId,collect2);
            List<RecoveryPaymentDetail> recoveryPaymentDetails = recoveryPaymentDetailHandler.list(detailLambdaQueryWrapper);
            if (!ObjectUtils.isEmpty(recoveryPaymentDetails)){
                Map<Long, List<RecoveryPaymentDetail>> collect = recoveryPaymentDetails.stream().collect(Collectors.groupingBy(RecoveryPaymentDetail::getPaymentId));
                Map<Long, List<RecoveryPayment>> collect3 = recoveryPayments.stream().collect(Collectors.groupingBy(RecoveryPayment::getProjectId));
                List<SysDictionaryItemDTO> items = sysDictionaryClient.findByCode(DictEnum.PAY_TYPE.getKey())
                        .getData().getItems();
                for (ProjectLedgerDTO project : projectLedger) {
                    Long projectId = project.getId();
                    List<RecoveryPayment> paymentList = collect3.get(projectId);
                    BigDecimal payType01 = BigDecimal.ZERO;
                    BigDecimal payType02 = BigDecimal.ZERO;
                    BigDecimal payType03 = BigDecimal.ZERO;
                    BigDecimal payType04 = BigDecimal.ZERO;
                    BigDecimal payType05 = BigDecimal.ZERO;
                    if (!ObjectUtils.isEmpty(paymentList)){
                        for (RecoveryPayment recoveryPayment : paymentList) {
                            Long paymentId = recoveryPayment.getId();
                            List<RecoveryPaymentDetail> detailList = collect.get(paymentId);
                            if (!ObjectUtils.isEmpty(detailList)){
                                for (RecoveryPaymentDetail recoveryPaymentDetail : detailList) {
                                    BigDecimal payAmount = recoveryPaymentDetail.getPayAmount();
                                    Long payType = recoveryPaymentDetail.getPayType();
                                    for (SysDictionaryItemDTO item : items) {
                                        if (item.getId().equals(payType)){
                                        if ("诉讼费".equals(item.getItemName())){
                                            payType01 = payType01.add(payAmount);
                                        }
                                        if ("保全费".equals(item.getItemName())){
                                            payType02 = payType02.add(payAmount);
                                        }
                                        if ("保险费".equals(item.getItemName())){
                                            payType03 = payType03.add(payAmount);
                                        }
                                        if ("其他费用".equals(item.getItemName())){
                                            payType04 = payType04.add(payAmount);
                                        }
                                        if ("律师费".equals(item.getItemName())) {
                                            payType05 = payType05.add(payAmount);
                                        }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    project.setPayType01(payType01);
                    project.setPayType02(payType02);
                    project.setPayType03(payType03);
                    project.setPayType04(payType04);
                    project.setPayType05(payType05);
                }
            }
        }
        List<Long> writeOffIds = projectLedger.stream().filter(ProjectLedgerDTO::getIsWriteOff).map(ProjectLedgerDTO::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(writeOffIds)){
            return Result.success(projectLedger,PageFactory.page(page));
        }
        List<WriteOff> writeOffs = writeOffHandler.list(new LambdaQueryWrapper<WriteOff>().in(WriteOff::getProjectId, writeOffIds).eq(WriteOff::getWriteOffStatus,true));
        if (!CollectionUtils.isEmpty(writeOffs)){
            Map<Long, List<WriteOff>> collect = writeOffs.stream().collect(Collectors.groupingBy(WriteOff::getProjectId));
            for (ProjectLedgerDTO project : projectLedger) {
                if (collect.containsKey(project.getId())){
                    project.setWriteOffDate(collect.get(project.getId()).get(0).getWriteOffDate());
                    project.setWriteDffAmount(collect.get(project.getId()).get(0).getWriteDffAmount());
                    project.setWriteOffClassification(collect.get(project.getId()).get(0).getWriteOffClassification());
                }
            }
        }
        return Result.success(projectLedger,PageFactory.page(page));
    }

    @Override
    public void exportProjectLedger(ProjectInfoPageRequest request, HttpServletResponse response) {
        Result<List<ProjectLedgerDTO>> result = findProjectLedgerAll(1, 10000, request);
        if (null == result || !result.succeedWithData()) {
            return;
        }

        List<ProjectLedgerExportDTO> ledgerDTOList = result.getData()
            .stream()
            .flatMap(projectLedgerDTO -> {
                if (CollectionUtils.isEmpty(projectLedgerDTO.getReveInfoList())) {
                    // 如果为空或 null，直接创建一个对象
                    ProjectLedgerExportDTO exportDTO = PlatformMapUtils.getInstance().map(projectLedgerDTO, ProjectLedgerExportDTO.class);
                    return Lists.newArrayList(exportDTO).stream();
                } else {
                    // 如果非空，平铺每个 SubData
                    List<ProjectLedgerExportDTO> tempExportDTO = PlatformMapUtils.getInstance()
                        .mapAsList(projectLedgerDTO.getReveInfoList(), ProjectLedgerExportDTO.class);
                    return tempExportDTO.stream()
                        .peek(reveInfo -> PlatformMapUtils.getInstance().map(projectLedgerDTO, reveInfo));
                }
            })
            .collect(Collectors.toList());
//        System.out.println(1/0);
        String fileName = "项目台账";
        ExcelUtil.exportExcel(ledgerDTOList, fileName, ProjectLedgerExportDTO.class, true, response);
    }

    @Override
    public Result<List<ManageLedgerProjectDTO>> findManageProject(Integer current, Integer size, ManagePageRequest request) {
        SysDictionaryItemDTO projectState = sysDictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey())
                .getData().getItems().stream()
                .filter(item -> DictEnum.PROJECT_STATE_15.getKey().equals(item.getItemCode())).findAny().get();
        String manage = request.getManage();
        String projectName = request.getProjectName();
        IPage<ManageLedgerProjectDTO> page = new Page<>(current, size);
        page.setCurrent(current);
        page.setSize(size);
        List<Long> manageId = Lists.newArrayList(Long.valueOf(manage));
        List<SysUserDTO> sysUserDTOList = platformUserUtils.getUserByIds(manageId, false);
        if (CollectionUtils.isEmpty(sysUserDTOList) || null == sysUserDTOList.get(0)){
            return Result.success();
        }
        // 保全经理名称
        LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ProjectInfo::getManage,manage)
                .eq(ObjectUtils.isNotEmpty(projectName),ProjectInfo::getProjectName,projectName)
                .eq(ObjectUtils.isNotEmpty(request.getIsWriteOff()),ProjectInfo::getIsWriteOff,request.getIsWriteOff())
                .eq(ObjectUtils.isNotEmpty(request.getIsCloseCase()),ProjectInfo::getProjectState,projectState.getId());
        List<ProjectInfo> projectList = projectInfoHandler.list(queryWrapper);
        if (ObjectUtils.isEmpty(projectList)){
            return Result.success();
        }
        // 在管项目
        List<ManageLedgerProjectDTO> manageProjectList = findManageProjectList(projectList);
        if (CollectionUtils.isEmpty(manageProjectList)){
            return Result.success(manageProjectList);
        }
//        if (!ObjectUtils.isEmpty(request.getIsCloseCase())){
//            // 查询结案
//            List<ManageLedgerProjectDTO> closeList = manageProjectList.stream()
//                    .filter(item -> item.getProjectState().equals(projectState.getId())).collect(Collectors.toList());
//            page.setTotal(closeList.size());
//            int start = (current - 1) * size;
//            int end = Math.min(start + size, closeList.size());
//            return Result.success(closeList.subList(start, end), PageFactory.page(page));
//        }
//        if (!ObjectUtils.isEmpty(request.getIsWriteOff())){
//            // 查询核销
//            List<ManageLedgerProjectDTO> isWriteOffList = manageProjectList.stream().filter(ManageLedgerProjectDTO::getIsWriteOff).collect(Collectors.toList());
//            page.setTotal(isWriteOffList.size());
//            int start = (current - 1) * size;
//            int end = Math.min(start + size, isWriteOffList.size());
//            return Result.success(isWriteOffList.subList(start, end), PageFactory.page(page));
//        }
        // 在管项目
        page.setTotal(manageProjectList.size());
        int start = (current - 1) * size;
        int end = Math.min(start + size, manageProjectList.size());
        return Result.success(manageProjectList.subList(start, end), PageFactory.page(page));
    }

    @Override
    public Result<List<CompensatoryCountDTO>> findCompensatoryCountByYear(Integer request) {
       List<ProjectInfoDTO> projectInfoList = projectInfoMapper.findCompensatoryCountByYear();
        SysDictionaryItemDTO payStatus = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey()).getData().getItems().stream().filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey())).findAny().get();
        List<RecoveryPaymentCollection> paymentCollectionList = recoveryPaymentCollectionHandler
                .list(new LambdaQueryWrapper<RecoveryPaymentCollection>()
                        .eq(RecoveryPaymentCollection::getCollectionStatus,payStatus.getId()).isNotNull(RecoveryPaymentCollection::getCollectionDate));
       if (CollectionUtils.isEmpty(projectInfoList)){
           return Result.success(new ArrayList<>(16));
       }
        List<CompensatoryCountDTO> dtoList = new ArrayList<>(16);
        // 2017/4/28 之前的数据 是否历史代偿为 true
        Map<Integer, List<ProjectInfoDTO>> beforeMap = filterByDateComparisonAndMonth(projectInfoList,true);
        // 2017/4/28 之后的数据 是否历史代偿为 false
        Map<Integer, List<ProjectInfoDTO>> afterMap = filterByDateComparisonAndMonth(projectInfoList,false);
        Map<Integer, List<ProjectInfoDTO>> totalCompensatoryMap = projectInfoList.stream().collect(Collectors.groupingBy(item -> {
            Date compensationDate = item.getCompensationDate();
            if (compensationDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(compensationDate);
                return calendar.get(Calendar.MONTH) + 1;
            }
            return -1;
        }));
        List<RecoveryPaymentCollection> paymentHistoryList = new ArrayList<>();
        List<RecoveryPaymentCollection> paymentList = new ArrayList<>();
        for (RecoveryPaymentCollection collection : paymentCollectionList) {
            for (ProjectInfoDTO projectInfo : projectInfoList) {
                if (projectInfo.getId().equals(collection.getProjectId()) && !projectInfo.getProjectIsHistory()){
                    paymentHistoryList.add(collection);
                }
                if (projectInfo.getId().equals(collection.getProjectId()) && projectInfo.getProjectIsHistory()){
                    paymentList.add(collection);
                }
            }
        }
        Map<Integer, List<RecoveryPaymentCollection>> paymentBefore = filterPaymentByDateComparisonAndMonth(paymentList);
        Map<Integer, List<RecoveryPaymentCollection>> paymentAfter = filterPaymentByDateComparisonAndMonth(paymentHistoryList);
        Map<Integer, List<RecoveryPaymentCollection>> totalPayment = paymentCollectionList.stream().collect(Collectors.groupingBy(item -> {
            Date compensationDate = item.getCollectionDate();
            if (compensationDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(compensationDate);
                return calendar.get(Calendar.MONTH) + 1;
            }
            return -1;
        }));
        for (int month = 1; month <= 12; month++) {
            CompensatoryCountDTO compensatoryCount = new CompensatoryCountDTO();
            // 上年
            List<ProjectInfoDTO> lastYearData = filterByYearAndMonth(projectInfoList, request - 1, month);
            BigDecimal lastYearMoney = lastYearData.stream().map(ProjectInfoDTO::getCompensationMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
            compensatoryCount.setLastYearCompensatoryMoney(lastYearMoney);
            // 今年
            List<ProjectInfoDTO> thisYearData = filterByYearAndMonth(projectInfoList, request, month);
            BigDecimal thisYearMoney = thisYearData.stream().map(ProjectInfoDTO::getCompensationMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
            compensatoryCount.setThisYearCompensatoryMoney(thisYearMoney);
            if (beforeMap.containsKey(month)){
                List<ProjectInfoDTO> beforeInfo = beforeMap.get(month);
                BigDecimal beforeInfoMoney = beforeInfo.stream().map(ProjectInfoDTO::getCompensationMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
                compensatoryCount.setHistoryCompensatoryMoney(beforeInfoMoney);
            }
            if (afterMap.containsKey(month)){
                List<ProjectInfoDTO> afterInfo = afterMap.get(month);
                BigDecimal afterInfoMoney = afterInfo.stream().map(ProjectInfoDTO::getCompensationMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
                compensatoryCount.setAddCompensatoryMoney(afterInfoMoney);
            }
            if (totalCompensatoryMap.containsKey(month)){
                List<ProjectInfoDTO> projectInfos = totalCompensatoryMap.get(month);
                BigDecimal totalCompensatoryMoney = projectInfos.stream().map(ProjectInfoDTO::getCompensationMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
                compensatoryCount.setTotalCompensatoryMoney(totalCompensatoryMoney);
            }
            if (paymentBefore.containsKey(month)){
                List<RecoveryPaymentCollection> paymentCollection = paymentBefore.get(month);
                BigDecimal paymentCollectionMoney = paymentCollection.stream().map(RecoveryPaymentCollection::getCollectionAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                compensatoryCount.setHistoryCollectionMoney(paymentCollectionMoney);
            }
            if (paymentAfter.containsKey(month)){
                List<RecoveryPaymentCollection> paymentCollection = paymentAfter.get(month);
                BigDecimal  paymentCollectionMoney = paymentCollection.stream().map(RecoveryPaymentCollection::getCollectionAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                compensatoryCount.setAddCollectionMoney(paymentCollectionMoney);
            }
            if (totalPayment.containsKey(month)){
                List<RecoveryPaymentCollection> total = totalPayment.get(month);
                compensatoryCount.setTotalCollectionMoney(total.stream().map(RecoveryPaymentCollection::getCollectionAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            }
            // 上一年回款
            List<RecoveryPaymentCollection> paymentLastYearData = filterPaymentByYearAndMonth(paymentCollectionList, request - 1, month);
            compensatoryCount.setLastYearCollectionMoney(paymentLastYearData.stream().map(RecoveryPaymentCollection::getCollectionAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            // 今年回款
            List<RecoveryPaymentCollection> paymentThisYearData = filterPaymentByYearAndMonth(paymentCollectionList, request, month);
            compensatoryCount.setThisYearCollectionMoney(paymentThisYearData.stream().map(RecoveryPaymentCollection::getCollectionAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            // 上年末追偿余额
            compensatoryCount.setLastYearBalance(compensatoryCount.getLastYearCompensatoryMoney().subtract(compensatoryCount.getLastYearCollectionMoney()));
            // 追偿余额
            compensatoryCount.setTotalBalance(compensatoryCount.getTotalCompensatoryMoney().subtract(compensatoryCount.getTotalCollectionMoney()));
            compensatoryCount.setMonth(month + "月");
            if (month <= 3) {
                compensatoryCount.setSeason("第一季度");
            }
            if (month > 3 && month <= 6) {
                compensatoryCount.setSeason("第二季度");
            }
            if (month > 6 && month <= 9) {
                compensatoryCount.setSeason("第三季度");
            }
            if (month > 9) {
                compensatoryCount.setSeason("第四季度");
            }
            dtoList.add(compensatoryCount);
            if (month % 3 == 0) {
                quarterlyTotal(dtoList);
            }
        }
        return Result.success(dtoList);
    }

    @Override
    public void exportCompensatoryCount(ExportCompensatoryRequest request, HttpServletResponse response) {
        Result<List<CompensatoryCountDTO>> result = findCompensatoryCountByYear(request.getRequest());
        if (null == result || !result.succeedWithData()) {
            return;
        }
        List<CompensatoryCountExportDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(result.getData(), CompensatoryCountExportDTO.class);
        IntStream.rangeClosed(1, dtoList.size())
                .forEach(i -> dtoList.get(i - 1).setOrder(String.valueOf(i)));
        String fileName = "追偿情况统计";
        ExcelUtil.exportExcel(dtoList, fileName, CompensatoryCountExportDTO.class, true, response);
    }

    private void quarterlyTotal(List<CompensatoryCountDTO> dtoList) {
        CompensatoryCountDTO compensatoryCount = new CompensatoryCountDTO();
        for (int i = 0; i < dtoList.size(); i++) {
            CompensatoryCountDTO dto = dtoList.get(i);
            // 根据不同的大小进行累加
            if (dtoList.size() == 3 ) {
                getCompensatoryCountDTO(compensatoryCount, dto);
                compensatoryCount.setSeason("第一季度");
            } else if (dtoList.size() == 7 && (i == 4 || i == 5 || i == 6)) {
                getCompensatoryCountDTO(compensatoryCount, dto);
                compensatoryCount.setSeason("第二季度");
            } else if (dtoList.size() == 11 && (i == 8 || i == 9 || i == 10)) {
                getCompensatoryCountDTO(compensatoryCount, dto);
                compensatoryCount.setSeason("第三季度");
            } else if (dtoList.size() == 15 && (i == 12 || i == 13 || i == 14)) {
                getCompensatoryCountDTO(compensatoryCount, dto);
                compensatoryCount.setSeason("第四季度");
            }
        }
        dtoList.add(compensatoryCount);
    }

    private void getCompensatoryCountDTO(CompensatoryCountDTO compensatoryCount, CompensatoryCountDTO dto) {
        compensatoryCount.setLastYearCompensatoryMoney(dto.getLastYearCompensatoryMoney().add(compensatoryCount.getLastYearCompensatoryMoney()));
        compensatoryCount.setThisYearCompensatoryMoney(dto.getThisYearCompensatoryMoney().add(compensatoryCount.getThisYearCompensatoryMoney()));
        compensatoryCount.setThisYearCollectionMoney(dto.getThisYearCollectionMoney().add(compensatoryCount.getThisYearCollectionMoney()));
        compensatoryCount.setTotalCompensatoryMoney(dto.getTotalCompensatoryMoney().add(compensatoryCount.getTotalCompensatoryMoney()));
        compensatoryCount.setHistoryCompensatoryMoney(dto.getHistoryCompensatoryMoney().add(compensatoryCount.getHistoryCompensatoryMoney()));
        compensatoryCount.setAddCompensatoryMoney(dto.getAddCompensatoryMoney().add(compensatoryCount.getAddCompensatoryMoney()));
        compensatoryCount.setLastYearCollectionMoney(dto.getLastYearCollectionMoney().add(compensatoryCount.getLastYearCollectionMoney()));
        compensatoryCount.setAddCollectionMoney(dto.getAddCollectionMoney().add(compensatoryCount.getAddCollectionMoney()));
        compensatoryCount.setTotalCollectionMoney(dto.getTotalCollectionMoney().add(compensatoryCount.getTotalCollectionMoney()));
        compensatoryCount.setHistoryCollectionMoney(dto.getHistoryCollectionMoney().add(compensatoryCount.getHistoryCollectionMoney()));
        compensatoryCount.setLastYearBalance(dto.getLastYearCompensatoryMoney().subtract(compensatoryCount.getLastYearCollectionMoney()));
        compensatoryCount.setTotalBalance(compensatoryCount.getTotalCompensatoryMoney().subtract(compensatoryCount.getTotalCollectionMoney()));
        compensatoryCount.setMonth("季度合计");
    }

    public List<RecoveryPaymentCollection> filterPaymentByYearAndMonth(List<RecoveryPaymentCollection> paymentCollectionList, int year, int month) {
        return paymentCollectionList.stream()
                .filter(item -> {
                    Date compensationDate = item.getCollectionDate();
                    if (compensationDate!= null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(compensationDate);
                        int itemYear = calendar.get(Calendar.YEAR);
                        int itemMonth = calendar.get(Calendar.MONTH) + 1;
                        return itemYear == year && itemMonth == month;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
    public List<ProjectInfoDTO> filterByYearAndMonth(List<ProjectInfoDTO> projectInfoList, int year, int month) {
        return projectInfoList.stream()
                .filter(item -> {
                    Date compensationDate = item.getCompensationDate();
                    if (compensationDate!= null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(compensationDate);
                        int itemYear = calendar.get(Calendar.YEAR);
                        int itemMonth = calendar.get(Calendar.MONTH) + 1;
                        return itemYear == year && itemMonth == month;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
    public  Map<Integer, List<ProjectInfoDTO>> filterByDateComparisonAndMonth(List<ProjectInfoDTO> projectInfoList,boolean projectIsHistory) {
        return projectInfoList.stream()
                .filter(item -> item.getProjectIsHistory()!= null && item.getProjectIsHistory() == projectIsHistory)
                .collect(Collectors.groupingBy(item -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(item.getCompensationDate());
                    return calendar.get(Calendar.MONTH) + 1;
                }));
    }

    public  Map<Integer, List<RecoveryPaymentCollection>> filterPaymentByDateComparisonAndMonth(List<RecoveryPaymentCollection> collectionList) {
        return collectionList.stream()
                .collect(Collectors.groupingBy(item -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(item.getCollectionDate());
                    return calendar.get(Calendar.MONTH) + 1;
                }));
    }

}
