package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jk.asset.constant.AssertConstants;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.ProjectStateEnum;
import com.jk.asset.mapper.property.PropertyInfoMapper;
import com.jk.asset.model.dto.AssetAnalyseDTO;
import com.jk.asset.model.dto.OverviewPastProjectsDTO;
import com.jk.asset.model.dto.ProjectDateDTO;
import com.jk.asset.model.dto.ProjectStatusDTO;
import com.jk.asset.model.dto.RecoveryStatisticsDTO;
import com.jk.asset.model.dto.WriteOffClassificationDTO;
import com.jk.asset.model.dto.property.AnalysePropertyInfoDTO;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.entity.PaymentCollectionTarget;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RecoveryPaymentCollection;
import com.jk.asset.model.entity.WriteOff;
import com.jk.asset.model.entity.property.PropertyInfo;
import com.jk.asset.model.request.WriteOffRequest;
import com.jk.asset.service.HomePageStatisticsService;
import com.jk.asset.service.handler.LawyerInfoHandler;
import com.jk.asset.service.handler.PaymentCollectionTargetHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionHandler;
import com.jk.asset.service.handler.WriteOffHandler;
import com.jk.asset.service.handler.property.PropertyInfoHandler;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.model.Result;
import com.jk.common.model.entity.BaseDO;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.utils.PlatformUserUtils;
import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangTao
 * date2024/7/15 11:17
 **/
@RestController
@Slf4j
@Api(tags = "首页接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomePageStatisticsServiceImpl implements HomePageStatisticsService {

    private final ProjectInfoHandler ProjectInfoHandler;
    private final RecoveryPaymentCollectionHandler recoveryPaymentCollectionHandler;
    private final SysDictionaryClient sysDictionaryClient;
    private final WriteOffHandler writeOffHandler;
    private final PaymentCollectionTargetHandler paymentCollectionTargetHandler;
    private final PropertyInfoHandler propertyInfoHandler;
    private final AssetUserLimitsUtils assetUserLimitsUtils;
    private final PropertyInfoMapper propertyInfoMapper;
    private final PlatformUserUtils platformUserUtils;
    private final LawyerInfoHandler lawyerInfoHandler;

    @Override
    public Result<RecoveryStatisticsDTO> statistics() {
        Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
        RecoveryStatisticsDTO dto = new RecoveryStatisticsDTO();
        LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProjectInfo::getCompensationMoney, ProjectInfo::getId, ProjectInfo::getCompensationDate);
        if (!ObjectUtils.isEmpty(userLimitsOrg)){
            queryWrapper.in(ProjectInfo::getAffiliatedOrg,userLimitsOrg).or();
            for (Long org : userLimitsOrg) {
                queryWrapper.or(i -> i.like(ProjectInfo::getManage, String.valueOf(org)));
            }
        }
        List<ProjectInfo> list = ProjectInfoHandler.list(queryWrapper);
        if (ObjectUtils.isEmpty(list)) {
            return Result.success(dto);
        }
        LocalDate today = LocalDate.now();
        // 获取当前年份
        int currentYear = today.getYear();
        LambdaQueryWrapper<PaymentCollectionTarget> queryTarget = new LambdaQueryWrapper<>();
        List<PaymentCollectionTarget> targetList = paymentCollectionTargetHandler.list(queryTarget);
        if (ObjectUtils.isEmpty(targetList)) {
            dto.setYearRecoveryCollectionTarget(BigDecimal.ZERO);
        }
        BigDecimal targetMoney = BigDecimal.ZERO;
        for (PaymentCollectionTarget target : targetList) {
            int year = findThisYear(target.getYear());
            if (!ObjectUtils.isEmpty(target)) {
                if (currentYear == year) {
                    double tm = org.apache.commons.lang3.ObjectUtils.isNotEmpty(target.getTargetMoney()) ? target.getTargetMoney().doubleValue() : 0;
                    double em = org.apache.commons.lang3.ObjectUtils.isNotEmpty(target.getEntrustMoney()) ? target.getEntrustMoney().doubleValue() : 0;
                    targetMoney = BigDecimal.valueOf(tm+em);
//                    targetMoney = target.getTargetMoney();
                }
            }
        }
        // 本年回款目标
        dto.setYearRecoveryCollectionTarget(targetMoney);
        // 追偿项目数
        dto.setRecoveryAccount(list.size());
        // 代偿金额
        BigDecimal recoveryAmount = BigDecimal.ZERO;
        // 本年新增代偿金额
        BigDecimal yearAddRecoveryAmount = BigDecimal.ZERO;
        int yearAddRecovery = 0;
        List<Long> projectIds = new ArrayList<>(list.size());
        for (ProjectInfo projectInfo : list) {
            BigDecimal compensationMoney = projectInfo.getCompensationMoney();
            if (!ObjectUtils.isEmpty(compensationMoney)) {
                recoveryAmount = recoveryAmount.add(compensationMoney);
            }
            if (!ObjectUtils.isEmpty(projectInfo.getCompensationDate())){
                int thisYear = findThisYear(projectInfo.getCompensationDate());
                if (thisYear == currentYear) {
                    yearAddRecovery++;
                    if (!ObjectUtils.isEmpty(compensationMoney)) {
                        yearAddRecoveryAmount = yearAddRecoveryAmount.add(compensationMoney);
                    }
                }
            }
            projectIds.add(projectInfo.getId());
        }
        SysDictionaryItemDTO collectionStatus002 = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
                .getData().getItems().stream().filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey())).findAny().get();
        dto.setYearAddRecovery(yearAddRecovery);
        dto.setYearAddRecoveryAmount(yearAddRecoveryAmount);
        LambdaQueryWrapper<RecoveryPaymentCollection> query = new LambdaQueryWrapper<>();
        query.select(RecoveryPaymentCollection::getProjectId, RecoveryPaymentCollection::getCollectionAmount, RecoveryPaymentCollection::getCollectionDate,BaseDO::getId)
                .in(RecoveryPaymentCollection::getProjectId, projectIds)
//                .eq(RecoveryPaymentCollection::getFlowState,ProcessStatus.completed.getKey());
                .eq(RecoveryPaymentCollection::getCollectionStatus,collectionStatus002.getId());
        List<RecoveryPaymentCollection> paymentCollectionList = recoveryPaymentCollectionHandler.list(query);

        BigDecimal yearTotalRecoveryAmount = BigDecimal.ZERO;
        BigDecimal totalRecoveryAmount = BigDecimal.ZERO;
        if (ObjectUtils.isEmpty(paymentCollectionList)) {
            dto.setYearTotalRecoveryAmount(BigDecimal.ZERO);
            // 剩余代偿金额
            dto.setResidueRecoveryAmount(recoveryAmount);
            // 回款率
            dto.setDoneRate(BigDecimal.ZERO);
            dto.setRecoveryRate(BigDecimal.ZERO);
            // 完成率
        } else {
            for (RecoveryPaymentCollection collection : paymentCollectionList) {
                Date collectionDate = collection.getCollectionDate();
                if (!ObjectUtils.isEmpty(collectionDate)) {
                    BigDecimal collectionAmount = collection.getCollectionAmount();
                    if (!ObjectUtils.isEmpty(collectionAmount)) {
                        int year = findThisYear(collectionDate);
                        if (year == currentYear) {
                            yearTotalRecoveryAmount = yearTotalRecoveryAmount.add(collectionAmount);
                        }
                        totalRecoveryAmount = totalRecoveryAmount.add(collectionAmount);
                    }
                }
            }
        }
        // 剩余代偿金额
        dto.setResidueRecoveryAmount(recoveryAmount.subtract(totalRecoveryAmount));
        //累计回款
        dto.setYearTotalRecoveryAmount(yearTotalRecoveryAmount);
        // 回款率 = 回款金额/代偿金额

        if (recoveryAmount.compareTo(BigDecimal.ZERO) > 0){
            BigDecimal divide = totalRecoveryAmount.divide(recoveryAmount, 4, RoundingMode.HALF_UP);
            dto.setRecoveryRate(divide);
        }
        if (targetMoney.compareTo(BigDecimal.ZERO) > 0){
            BigDecimal divide1 = yearTotalRecoveryAmount.divide(targetMoney, 4, RoundingMode.HALF_UP);
            // 完成率 = 年度回款金额/本年回款金额
            dto.setDoneRate(divide1);
        }

        // 获取今年的第一天
        LocalDate firstDayOfYear = LocalDate.now().with(java.time.temporal.TemporalAdjusters.firstDayOfYear());
        LocalDateTime startOfDay = firstDayOfYear.atStartOfDay();
        ZonedDateTime zdtStart = startOfDay.atZone(ZoneId.systemDefault());
        Date startDate = Date.from(zdtStart.toInstant());
        // 获取今年的最后一天
        LocalDate lastDayOfYear = LocalDate.now().with(java.time.temporal.TemporalAdjusters.lastDayOfYear());
        LocalDateTime endOfDay = lastDayOfYear.atTime(23, 59, 59);
        ZonedDateTime zdtEnd = endOfDay.atZone(ZoneId.systemDefault());
        Date endDate = Date.from(zdtEnd.toInstant());
        LambdaQueryWrapper<PropertyInfo> propertyInfoQuery = new LambdaQueryWrapper<>();
        propertyInfoQuery.select(PropertyInfo::getId, PropertyInfo::getNetWorth)
                .between(BaseDO::getCreateStamp, startDate, endDate);
        List<PropertyInfo> propertyInfos = propertyInfoHandler.list(propertyInfoQuery);
        BigDecimal yearAddAssetValue = BigDecimal.ZERO;
        if (ObjectUtils.isEmpty(propertyInfos)) {
            dto.setYearAddAssetAccount(AssertConstants.CONSTANT_ZERO);
        } else {
            dto.setYearAddAssetAccount(propertyInfos.size());
            for (PropertyInfo propertyInfo : propertyInfos) {
                BigDecimal netWorth = propertyInfo.getNetWorth();
                if (!ObjectUtils.isEmpty(netWorth)) {
                    yearAddAssetValue = yearAddAssetValue.add(netWorth);
                }
            }
        }
        dto.setYearAddAssetValue(yearAddAssetValue);
        // 转成万元

        BigDecimal target = new BigDecimal("10000");
        BigDecimal percent = new BigDecimal("100");
        dto.setYearTotalRecoveryAmount(dto.getYearTotalRecoveryAmount() == null ? null : dto.getYearTotalRecoveryAmount().divide(target,6,RoundingMode.HALF_UP));
        dto.setResidueRecoveryAmount(dto.getResidueRecoveryAmount() == null ? null : dto.getResidueRecoveryAmount().divide(target,6,RoundingMode.HALF_UP));
        dto.setRecoveryRate(dto.getRecoveryRate() == null ? null : dto.getRecoveryRate().multiply(percent));
        dto.setDoneRate(dto.getDoneRate() == null ? null : dto.getDoneRate().multiply(percent));
        dto.setYearRecoveryCollectionTarget(dto.getYearRecoveryCollectionTarget() == null ? null : dto.getYearRecoveryCollectionTarget().divide(target,6,RoundingMode.HALF_UP));
        dto.setYearAddRecoveryAmount(dto.getYearAddRecoveryAmount() == null ? null : dto.getYearAddRecoveryAmount().divide(target,6,RoundingMode.HALF_UP));
        dto.setYearAddAssetValue(dto.getYearAddAssetValue() == null ? null : dto.getYearAddAssetValue().divide(target,6,RoundingMode.HALF_UP));
    return Result.success(dto);
    }

    private int findThisYear(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getYear();
    }

    @Override
    public Result<List<OverviewPastProjectsDTO>> overviewPastProjects() {
        // 将金额 设置成 万元
        BigDecimal tenThousand  = new BigDecimal("10000");
//        Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
        LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProjectInfo::getId,ProjectInfo::getCompensationMoney, ProjectInfo::getCompensationDate);
//        if (!ObjectUtils.isEmpty(userLimitsOrg)){
//            queryWrapper.in(ProjectInfo::getAffiliatedOrg,userLimitsOrg).or();
//        }
//        if (!ObjectUtils.isEmpty(userLimitsOrg)) {
//            for (Long org : userLimitsOrg) {
//                queryWrapper.or(i -> i.like(ProjectInfo::getManage, String.valueOf(org)));
//            }
//        }

        List<ProjectInfo> projectList = ProjectInfoHandler.list(queryWrapper);
        if (ObjectUtils.isEmpty(projectList)){
            return Result.success(new ArrayList<>());
        }

        Map<Integer, List<ProjectInfo>> collect = projectList.stream().filter(a -> !ObjectUtils.isEmpty(a.getCompensationDate()))
                .collect(Collectors.groupingBy(
                        projectInfo -> {
                            // 将Date转换为Instant，然后转换为Year，最后提取年份的整数值
                            Instant instant = projectInfo.getCompensationDate().toInstant();
                            Year year = Year.from(instant.atZone(ZoneId.systemDefault()));
                            return year.getValue(); // 这里返回的是int类型的年份
                        }
                ));
        List<OverviewPastProjectsDTO> yearList = new ArrayList<>(collect.size());

        for (Map.Entry<Integer, List<ProjectInfo>> yearListEntry : collect.entrySet()) {
            OverviewPastProjectsDTO dto = new OverviewPastProjectsDTO();
            Integer key = yearListEntry.getKey();
            dto.setProjectYear(key);
            yearList.add(dto);
        }
        List<OverviewPastProjectsDTO> resList = yearList.stream().distinct().collect(Collectors.toList());
        resList.forEach(a -> {
            List<ProjectInfo> projectInfoList = collect.get(a.getProjectYear());
            if (!projectInfoList.isEmpty()) {
                int recovery = projectInfoList.size();
                BigDecimal totalRecovery = BigDecimal.ZERO;
                for (ProjectInfo b : projectInfoList) {
                    totalRecovery = totalRecovery.add(b.getCompensationMoney());
                }
                a.setRecoveryAmount(totalRecovery);
                a.setRecoveryAccount(recovery);
            }
        });
        SysDictionaryItemDTO collectionStatus002 = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
                .getData().getItems().stream().filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey())).findAny().get();
        LambdaQueryWrapper<RecoveryPaymentCollection> query = new LambdaQueryWrapper<>();
        query.select(RecoveryPaymentCollection::getProjectId,RecoveryPaymentCollection::getCollectionAmount,RecoveryPaymentCollection::getCollectionDate,BaseDO::getId);
//        query.eq(RecoveryPaymentCollection::getFlowState,ProcessStatus.completed.getKey());
        query.eq(RecoveryPaymentCollection::getCollectionStatus,collectionStatus002.getId());
        List<RecoveryPaymentCollection> paymentCollectionList = recoveryPaymentCollectionHandler.list(query);
        if (Collections.isEmpty(paymentCollectionList)){
            resList.forEach(item -> item.setRecoveryCollectionAmount(BigDecimal.ZERO));
            for (OverviewPastProjectsDTO data : resList) {
                BigDecimal recoveryAmount = data.getRecoveryAmount();
                BigDecimal recoveryCollectionAmount = data.getRecoveryCollectionAmount();
                data.setRecoveryAmount(recoveryAmount == null ? BigDecimal.ZERO : recoveryAmount.divide(tenThousand, 6, RoundingMode.HALF_UP));
                data.setRecoveryCollectionAmount(recoveryCollectionAmount == null ? BigDecimal.ZERO : recoveryCollectionAmount.divide(tenThousand, 6, RoundingMode.HALF_UP));
            }
            return Result.success(resList);
        }
        paymentCollectionList = paymentCollectionList.stream()
                .filter(item -> projectList.stream().map(BaseDO::getId).collect(Collectors.toList())
                        .contains(item.getProjectId())).collect(Collectors.toList());
        // 分组 每年的回款数据
        Map<Integer, List<RecoveryPaymentCollection>> collect1 = paymentCollectionList.stream().collect(Collectors.groupingBy(payment -> {
            // 将 Date 转换为 LocalDate，然后获取年份
            ZonedDateTime zdt = payment.getCollectionDate().toInstant().atZone(ZoneId.systemDefault());
            LocalDate date = zdt.toLocalDate();
            return date.getYear();
        } ));

        if (!ObjectUtils.isEmpty(collect1.isEmpty())){
            for (OverviewPastProjectsDTO overview : resList) {
                BigDecimal money = BigDecimal.ZERO;
                Integer projectYear = overview.getProjectYear();
                List<RecoveryPaymentCollection> yearCollection = collect1.get(projectYear);
                if (!ObjectUtils.isEmpty(yearCollection)){
                    for (RecoveryPaymentCollection collection : yearCollection) {
                        BigDecimal collectionAmount = collection.getCollectionAmount();
                        if (!ObjectUtils.isEmpty(collectionAmount)){
                            money = money.add(collectionAmount);
                        }
                    }
                }
                overview.setRecoveryCollectionAmount(money);
            }
        }
        for (OverviewPastProjectsDTO data : resList) {
            BigDecimal recoveryAmount = data.getRecoveryAmount();
            BigDecimal recoveryCollectionAmount = data.getRecoveryCollectionAmount();
            data.setRecoveryAmount(recoveryAmount == null ? BigDecimal.ZERO : recoveryAmount.divide(tenThousand, 6, RoundingMode.HALF_UP));
            data.setRecoveryCollectionAmount(recoveryCollectionAmount == null ? BigDecimal.ZERO : recoveryCollectionAmount.divide(tenThousand, 6, RoundingMode.HALF_UP));
        }
        return Result.success(resList);
    }

    @Override
    public Result<ProjectDateDTO> deadlineWarning() {
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
        ProjectDateDTO projectDateDTO = new ProjectDateDTO();
        LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(ProjectInfo::getTransferDate,startDate,endDate);
        // 保全到期日期
        Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
        if (!ObjectUtils.isEmpty(userLimitsOrg)){
            queryWrapper.in(ProjectInfo::getAffiliatedOrg,userLimitsOrg).or();
            for (Long org : userLimitsOrg) {
                queryWrapper.or(i -> i.like(ProjectInfo::getManage, String.valueOf(org)));
            }
        }
//        List<ProjectInfo> transferList = ProjectInfoHandler.list(queryWrapper);
//        if (!ObjectUtils.isEmpty(transferList)){
//            projectDateDTO.setTransferDueDate(transferList.stream().map(ProjectInfo::getId).collect(Collectors.toList()));
//        }else {
//            projectDateDTO.setTransferDueDate(new ArrayList<>());
//        }
        queryWrapper.clear();
        queryWrapper.between(ProjectInfo::getProceedingAgeingDate,startDate,endDate);
        List<ProjectInfo> proceedingAgeingList = ProjectInfoHandler.list(queryWrapper);
        if (!ObjectUtils.isEmpty(proceedingAgeingList)){
            projectDateDTO.setProceedingAgeingDueDate(proceedingAgeingList.stream().map(ProjectInfo::getId).collect(Collectors.toList()));
        }else {
            projectDateDTO.setProceedingAgeingDueDate(new ArrayList<>());
        }
        if (!ObjectUtils.isEmpty(userLimitsOrg)){
            queryWrapper.in(ProjectInfo::getAffiliatedOrg,userLimitsOrg).or();
            for (Long org : userLimitsOrg) {
                queryWrapper.or(i -> i.like(ProjectInfo::getManage, String.valueOf(org)));
            }
        }
        queryWrapper.clear();
        queryWrapper.between(ProjectInfo::getAdjustTrialDate,startDate,endDate);
        if (!ObjectUtils.isEmpty(userLimitsOrg)){
            queryWrapper.in(ProjectInfo::getAffiliatedOrg,userLimitsOrg).or();
            for (Long org : userLimitsOrg) {
                queryWrapper.or(i -> i.like(ProjectInfo::getManage, String.valueOf(org)));
            }
        }
        List<ProjectInfo> adjustTrialList = ProjectInfoHandler.list(queryWrapper);
        if (!ObjectUtils.isEmpty(adjustTrialList)){
            projectDateDTO.setAdjustTrialDueDate(adjustTrialList.stream().map(ProjectInfo::getId).collect(Collectors.toList()));
        }else {
            projectDateDTO.setAdjustTrialDueDate(new ArrayList<>());
        }
        return Result.success(projectDateDTO);
    }

    @Override
    public Result<List<WriteOffClassificationDTO>> writeOffClassification() {
        Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
        LambdaQueryWrapper<ProjectInfo> projectQuery = new LambdaQueryWrapper<>();
        if (!ObjectUtils.isEmpty(userLimitsOrg)){
            projectQuery.in(ProjectInfo::getAffiliatedOrg,userLimitsOrg).or();
            for (Long org : userLimitsOrg) {
                projectQuery.or(i -> i.like(ProjectInfo::getManage, String.valueOf(org)));
            }
        }
        List<ProjectInfo> projectInfoList = ProjectInfoHandler.list(projectQuery);
        List<WriteOffClassificationDTO> dtoList = new ArrayList<>();
        LambdaQueryWrapper<WriteOff> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(WriteOff::getWriteOffClassification);
        queryWrapper.eq(WriteOff::getWriteOffStatus,true);
        if (!Collections.isEmpty(projectInfoList)){
            queryWrapper.in(WriteOff::getProjectId,projectInfoList.stream().map(BaseDO::getId).collect(Collectors.toList()));
        }
        List<WriteOff> list = writeOffHandler.list(queryWrapper);

        if (ObjectUtils.isEmpty(list)){
            return Result.success(new ArrayList<>());
        }
        Map<WriteOffRequest.WriteOffClassificationEnum, List<WriteOff>> collect = list.stream().collect(Collectors.groupingBy(WriteOff::getWriteOffClassification));
        for (Map.Entry<WriteOffRequest.WriteOffClassificationEnum, List<WriteOff>> entry : collect.entrySet()) {
            WriteOffClassificationDTO classificationDTO = new WriteOffClassificationDTO();
            WriteOffRequest.WriteOffClassificationEnum key = entry.getKey();
            int value = entry.getValue().size();
            classificationDTO.setWriteOffClassification(key);
            classificationDTO.setWriteOffNumber(value);
            dtoList.add(classificationDTO);
        }
        return Result.success(dtoList);
    }

    @Override
    public Result<AssetAnalyseDTO> assetAnalyse() {
        AssetAnalyseDTO res = new AssetAnalyseDTO();
        // 饼图（资产单元状态）
        List<SysDictionaryItemDTO> items1 = sysDictionaryClient.findByCode("ASSET_UNIT_STATE").getData().getItems();
        List<AnalysePropertyInfoDTO> analysePie = propertyInfoMapper.analysePie(null);
        List<AnalysePropertyInfoDTO> analysePieList = new ArrayList<>();
        items1.forEach(item -> {
            AnalysePropertyInfoDTO dto = new AnalysePropertyInfoDTO();
            dto.setTypeId(item.getId());
            dto.setTypeName(item.getItemName());
            dto.setTotalMoney(BigDecimal.ZERO);
            analysePie.forEach(his -> {
                if(item.getId().equals(his.getTypeId())){
                    dto.setTotalMoney(his.getTotalMoney());
                }
            });
            analysePieList.add(dto);
        });
        res.setAnalysePie(analysePieList);
        // 柱状图（资产分类）
        List<SysDictionaryItemDTO> items2 = sysDictionaryClient.findByCode("PROPERTY_TYPE").getData().getItems();
        List<AnalysePropertyInfoDTO> analyseHistogram = propertyInfoMapper.analyseHistogram(null);
        List<AnalysePropertyInfoDTO> analyseHistogramList = new ArrayList<>();
        items2.forEach(item -> {
            AnalysePropertyInfoDTO dto = new AnalysePropertyInfoDTO();
            dto.setTypeId(item.getId());
            dto.setTypeName(item.getItemName());
            dto.setTotalMoney(BigDecimal.ZERO);
            analyseHistogram.forEach(his -> {
                if(item.getId().equals(his.getTypeId())){
                    dto.setTotalMoney(his.getTotalMoney());
                }
            });
            analyseHistogramList.add(dto);
        });
        res.setAnalyseHistogram(analyseHistogramList);



//        LambdaQueryWrapper<PropertyInfo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.select(PropertyInfo::getId,PropertyInfo::getNetWorth,PropertyInfo::getPropertyType,PropertyInfo::getPropertyState);
//        List<PropertyInfo> propertyInfoList = propertyInfoHandler.list(queryWrapper);
//        if (ObjectUtils.isEmpty(propertyInfoList)){
//            return Result.success(res);
//        }
//        res.setAssetNum(propertyInfoList.size());
//        List<AssetPropertyTypeDTO> typeList = new ArrayList<>();
//        List<AssetStatusDTO> statusList = new ArrayList<>();
//        Map<PropertyInfoRequest.PropertyStateEnum, List<PropertyInfo>> collect = propertyInfoList.stream().collect(Collectors.groupingBy(PropertyInfo::getPropertyState));
//        for (Map.Entry<PropertyInfoRequest.PropertyStateEnum, List<PropertyInfo>> entry : collect.entrySet()) {
//            PropertyInfoRequest.PropertyStateEnum key = entry.getKey();
//            AssetStatusDTO assetStatusDTO = new AssetStatusDTO();
//            assetStatusDTO.setPropertyType(key);
//            assetStatusDTO.setPropertyNum(entry.getValue().size());
//            statusList.add(assetStatusDTO);
//        }
//
//        Map<Long, List<PropertyInfo>> collect1 = propertyInfoList.stream().filter(a -> !ObjectUtils.isEmpty(a.getPropertyType())).collect(Collectors.groupingBy(PropertyInfo::getPropertyType));
//        List<SysDictionaryItemDTO> items = sysDictionaryClient.findByCode("PROPERTY_TYPE")
//                .getData().getItems();
//        for (SysDictionaryItemDTO item : items) {
//            Long typeId = item.getId();
//            List<PropertyInfo> propertyInfos = collect1.get(typeId);
//            if (!ObjectUtils.isEmpty(propertyInfos)){
//                AssetPropertyTypeDTO assetPropertyTypeDTO = new AssetPropertyTypeDTO();
//                assetPropertyTypeDTO.setPropertyType(typeId);
//                assetPropertyTypeDTO.setPropertyTypeNum(propertyInfos.size());
//                BigDecimal netWorthAmount = BigDecimal.ZERO;
//                for (PropertyInfo propertyInfo : propertyInfos) {
//                    BigDecimal netWorth = propertyInfo.getNetWorth();
//                    if (!ObjectUtils.isEmpty(netWorth)){
//                        netWorthAmount = netWorthAmount.add(netWorth);
//                    }
//                }
//                assetPropertyTypeDTO.setNetWorth(netWorthAmount.divide(new BigDecimal("10000"),6,RoundingMode.HALF_UP));
//                typeList.add(assetPropertyTypeDTO);
//            }
//        }
//        res.setAssetPropertyTypeDTO(typeList);
//        res.setAssetStatusDTO(statusList);
        return Result.success(res);
    }
    private void applyUserLimitsOrgQuery(LambdaQueryWrapper<ProjectInfo> queryWrapper, Set<Long> userLimitsOrg) {
        if (!ObjectUtils.isEmpty(userLimitsOrg)) {
            queryWrapper.in(ProjectInfo::getAffiliatedOrg, userLimitsOrg).or();
            for (Long org : userLimitsOrg) {
                queryWrapper.or(i -> i.like(ProjectInfo::getManage, String.valueOf(org)));
            }
        }
    }
    @Override
    public Result<ProjectStatusDTO> proceedStatistics() {
        ProjectStatusDTO projectStatusDTO = new ProjectStatusDTO();
        LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
        queryWrapper.select(ProjectInfo::getId, ProjectInfo::getProjectState);
        SysUserDTO currentUser = platformUserUtils.getCurrentUser();
        if (CollectionUtils.isNotEmpty(currentUser.getOrgList()) &&
                currentUser.getOrgList().stream().filter(org -> org.getOrgCode() != null).anyMatch(org -> "LAWYER".equals(org.getOrgCode()))) {
            Long userId = currentUser.getId();
            List<LawyerInfo> lawyerInfoList = lawyerInfoHandler.list(new LambdaQueryWrapper<LawyerInfo>()
                    .eq(LawyerInfo::getUserId, userId).isNotNull(LawyerInfo::getLawFirmId));
            // 律师律所
            if (CollectionUtils.isNotEmpty(lawyerInfoList)) {
                queryWrapper.eq(ProjectInfo::getLawFirmId, lawyerInfoList.get(0).getLawFirmId());
            } else {
                applyUserLimitsOrgQuery(queryWrapper, userLimitsOrg);
            }
        } else {
            applyUserLimitsOrgQuery(queryWrapper, userLimitsOrg);
        }

        List<ProjectInfo> list = ProjectInfoHandler.list(queryWrapper);
        List<SysDictionaryItemDTO> items = sysDictionaryClient.findByCode(ProjectStateEnum.PROJECT_STATE.getKey())
                .getData()
                .getItems();
        Map<Long, List<ProjectInfo>> collect = list.stream().collect(Collectors.groupingBy(ProjectInfo::getProjectState));
        items.forEach(a -> {
            List<ProjectInfo> projectInfoList = collect.get(a.getId());
            if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(projectInfoList)){
                int size = projectInfoList.size();
                switch (a.getItemCode()){
                    case "PROJECT_STATE_01":
                        projectStatusDTO.setProjectState01(size);
                        break;
                    case "PROJECT_STATE_02":
                        projectStatusDTO.setProjectState02(size);
                        break;
                    case "PROJECT_STATE_03":
                        projectStatusDTO.setProjectState03(size);
                        break;
                    case "PROJECT_STATE_04":
                        projectStatusDTO.setProjectState04(size);
                        break;
                    case "PROJECT_STATE_05":
                        projectStatusDTO.setProjectState05(size);
                        break;
                    case "PROJECT_STATE_06":
                        projectStatusDTO.setProjectState06(size);
                        break;
                    case "PROJECT_STATE_07":
                        projectStatusDTO.setProjectState07(size);
                        break;
                    case "PROJECT_STATE_08":
                        projectStatusDTO.setProjectState08(size);
                        break;
                    case "PROJECT_STATE_09":
                        projectStatusDTO.setProjectState09(size);
                        break;
                    case "PROJECT_STATE_10":
                        projectStatusDTO.setProjectState10(size);
                        break;
                    case "PROJECT_STATE_11":
                        projectStatusDTO.setProjectState11(size);
                        break;
                    case "PROJECT_STATE_12":
                        projectStatusDTO.setProjectState12(size);
                        break;
                    case "PROJECT_STATE_13":
                        projectStatusDTO.setProjectState13(size);
                        break;
                    case "PROJECT_STATE_14":
                        projectStatusDTO.setProjectState14(size);
                        break;
                    case "PROJECT_STATE_15":
                        projectStatusDTO.setProjectState15(size);
                        break;
                    case "PROJECT_STATE_16":
                        projectStatusDTO.setProjectState16(size);
                        break;
                    case "PROJECT_STATE_17":
                        projectStatusDTO.setProjectState17(size);
                        break;
                    default:
                        break;
                }
            }
        });
        return Result.success(projectStatusDTO);
    }
}
