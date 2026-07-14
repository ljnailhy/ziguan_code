package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.mapper.LawyerInfoMapper;
import com.jk.asset.model.dto.LawyerInfoDTO;
import com.jk.asset.model.entity.BillLawyer;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RecoveryPaymentCollection;
import com.jk.asset.model.request.LawyerInfoRequest;
import com.jk.asset.model.request.page.LawyerInfoPageRequest;
import com.jk.asset.service.LawyerInfoService;
import com.jk.asset.service.handler.BillLawyerHandler;
import com.jk.asset.service.handler.LawyerInfoHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.service.client.ProcessClient;
import com.jk.service.client.SysDictionaryClient;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 律师团队接口实现类
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:49
 */
@RestController
@Slf4j
@Api(tags = "律师团队接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LawyerInfoServiceImpl implements LawyerInfoService {

    private final LawyerInfoHandler handler;
    private final LawyerInfoMapper mapper;
    private final BillLawyerHandler billLawyerHandler;
    private final ProjectInfoHandler projectInfoHandler;
    private final RecoveryPaymentCollectionHandler recoveryPaymentCollectionHandler;
    private final SysDictionaryClient sysDictionaryClient;

    private final ProcessClient processClient;

    @Override
    public Result<LawyerInfoDTO> add(LawyerInfoRequest request) {
        if (null == request || request.unverified()) {
            String message = "律师团队新增 请求参数异常";
            log.info("{} request {}", message, request);
            return Result.error(message);
        }

        // 对象拷贝：request->DO
        LawyerInfo lawyerInfo = PlatformMapUtils.getInstance().map(request, LawyerInfo.class);
        handler.save(lawyerInfo);

        LawyerInfoDTO dto = new LawyerInfoDTO();
        dto.setId(lawyerInfo.getId());
        return Result.success(dto);
    }

    @Override
    public Result<?> delete(Long id) {
        handler.removeById(id);
        return Result.success();
    }

    @Override
    public Result<LawyerInfoDTO> update(LawyerInfoRequest request) {
        if (null == request || null == request.getId()) {
            String message = "律师团队修改 请求参数异常";
            log.info("{} request {}", message, request);
            return Result.error(message);
        }

        LawyerInfo lawyerInfo = PlatformMapUtils.getInstance().map(request, LawyerInfo.class);
        handler.updateById(lawyerInfo);

        return Result.success();
    }

    @Override
    public Result<LawyerInfoDTO> findById(Long id) {
        LawyerInfo lawyerInfo = handler.getById(id);
        if (null == lawyerInfo) {
            String message = "律师团队单个查找 该记录不存在";
            log.info("{} id {}", message, id);
            return Result.error(message);
        }
        // 对象拷贝：DO->DTO
        LawyerInfoDTO dto = PlatformMapUtils.getInstance().map(lawyerInfo, LawyerInfoDTO.class);
        return Result.success(dto);
    }

    @Override
    public Result<List<LawyerInfoDTO>> findByIds(List<Long> idList) {

        if (ObjectUtils.isEmpty(idList)) {
            return Result.success(new ArrayList<>());
        }

        List<LawyerInfo> list = handler.list(new LambdaQueryWrapper<LawyerInfo>()
                .in(LawyerInfo::getId, idList));

        if (ObjectUtils.isEmpty(list)) {
            return Result.success(new ArrayList<>());
        }

        List<LawyerInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, LawyerInfoDTO.class);

        return Result.success(dtoList);
    }

    @Override
    public Result<List<LawyerInfoDTO>> findByLawFirmId(Long lawFirmId) {

        List<LawyerInfo> list = handler.list(new LambdaQueryWrapper<LawyerInfo>()
                .eq(LawyerInfo::getLawFirmId, lawFirmId));

        if (ObjectUtils.isEmpty(list)) {
            return Result.success(new ArrayList<>());
        }

        List<LawyerInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, LawyerInfoDTO.class);
        //  统计
        setProject(dtoList);
        return Result.success(dtoList);
    }

    public void setProject(List<LawyerInfoDTO> dtoList) {
        //  获取律师项目信息
        for (LawyerInfoDTO lawyerInfoDTO : dtoList) {
            List<BillLawyer> list = billLawyerHandler.list(new LambdaUpdateWrapper<BillLawyer>()
                    .eq(BillLawyer::getDoType, BillTypeEnum.PROJECT_INFO.getKey())
                    .eq(BillLawyer::getLawyer, lawyerInfoDTO.getId())
                    .eq(BillLawyer::getIsEffective, Boolean.TRUE));

            if (ObjectUtils.isEmpty(list)) {
                lawyerInfoDTO.setProjectNumber(0);
                lawyerInfoDTO.setCompensationMoneySum(BigDecimal.ZERO);
                lawyerInfoDTO.setCollectionAmountSum(BigDecimal.ZERO);
                lawyerInfoDTO.setCollectionRate(null);
                continue;
            }

            List<Long> projectIds = list.stream().map(BillLawyer::getDoId).collect(Collectors.toList());
            List<ProjectInfo> projectInfoList = projectInfoHandler.list(new LambdaUpdateWrapper<ProjectInfo>()
                    .in(ProjectInfo::getId, projectIds));

            //  查询这些项目的代偿金额
            BigDecimal compensationMoneySum = projectInfoList.stream()
                    .filter(item -> null != item.getCompensationMoney())
                    .map(item -> item.getCompensationMoney())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            //  查询这些项目的回款记录
            SysDictionaryItemDTO sysDictionaryItemDTO = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey()).getData().getItems().stream()
                    .filter(item -> DictEnum.COLLECTION_STATUS_002.getKey().equals(item.getItemCode())).findFirst().get();
            List<RecoveryPaymentCollection> recoveryList = recoveryPaymentCollectionHandler
                    .list(new LambdaUpdateWrapper<RecoveryPaymentCollection>()
                            .in(RecoveryPaymentCollection::getProjectId, projectIds)
                            .eq(RecoveryPaymentCollection::getCollectionStatus, sysDictionaryItemDTO.getId()));

//      List<RecoveryPaymentCollection> newRecoveryList = Lists.newArrayList();
//      for (RecoveryPaymentCollection recoveryPaymentCollection : recoveryList) {F
//        // 流程状态
//        Result<ProcessStatus> statusResult = processClient.getStatusByDoId(recoveryPaymentCollection.getId());
//        if (null != statusResult && statusResult.succeedWithData() && statusResult.getData().getKey().equals(ProcessStatus.completed.getKey())) {
//          newRecoveryList.add(recoveryPaymentCollection);
//        }
//      }
            BigDecimal collectionAmountSum = recoveryList.stream()
                    .filter(item -> null != item.getCollectionAmount())
                    .map(item -> item.getCollectionAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            lawyerInfoDTO.setProjectNumber(list.size());
            lawyerInfoDTO.setCompensationMoneySum(compensationMoneySum);
            if (null != collectionAmountSum) {
                lawyerInfoDTO.setCollectionAmountSum(collectionAmountSum);
            }

            //   累计回款金额 / 累计代偿金额 * 100
            if (compensationMoneySum.compareTo(BigDecimal.ZERO) == 0) {
                lawyerInfoDTO.setCollectionRate(BigDecimal.ZERO);
            } else {
                lawyerInfoDTO.setCollectionRate(collectionAmountSum.divide(compensationMoneySum, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100")));
            }
        }
    }

    @Override
    public Result<List<LawyerInfoDTO>> findAll(Integer current, Integer size, LawyerInfoPageRequest request) {
        if (PlatformStringUtils.isNotBlank(request.getField())) {
            request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
        }
        // Step1：创建一个 Page 对象
        IPage<LawyerInfo> page = new Page<>(current, size);
        // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
        List<LawyerInfo> doList = mapper.findAll(page, request);
        // 空则直接返回
        if (CollectionUtils.isEmpty(doList)) {
            return Result.success(Lists.newArrayList(), PageFactory.page(page));
        }
        // Step3：获取分页数据
        List<LawyerInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, LawyerInfoDTO.class);

        //  统计
        setProject(dtoList);

        return Result.success(dtoList, PageFactory.page(page));
    }

}