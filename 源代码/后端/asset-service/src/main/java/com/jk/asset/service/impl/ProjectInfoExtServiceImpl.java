package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jk.asset.enums.CollectionSignEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.mapper.ProjectInfoExtMapper;
import com.jk.asset.model.dto.ProjectInfoExtDTO;
import com.jk.asset.model.dto.export.LitigationLedgerExportDTO;
import com.jk.asset.model.dto.proceeding.RecoveryAdjustTrialDTO;
import com.jk.asset.model.dto.proceeding.RecoveryLitigationDTO;
import com.jk.asset.model.entity.ProjectInfoExt;
import com.jk.asset.model.entity.RecoveryPaymentCollection;
import com.jk.asset.model.entity.proceeding.RecoveryAdjustTrial;
import com.jk.asset.model.entity.proceeding.RecoveryLitigation;
import com.jk.asset.model.request.ProjectInfoExtRequest;
import com.jk.asset.model.request.page.ProjectInfoExtPageRequest;
import com.jk.asset.service.ProjectInfoExtService;
import com.jk.asset.service.handler.ProjectInfoExtHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionHandler;
import com.jk.asset.service.handler.proceeding.RecoveryAdjustTrialHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationHandler;
import com.jk.asset.utils.excel.ExcelUtil;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryDTO;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.service.client.ProcessClient;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.client.SysUserClient;
import com.jk.service.utils.PlatformFieldUtils;
import com.jk.service.utils.PlatformUserUtils;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 项目信息详细信息扩展表接口实现类
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
@RestController
@Slf4j
@Api(tags = "项目信息详细信息扩展表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectInfoExtServiceImpl implements ProjectInfoExtService {

  private final ProjectInfoExtHandler handler;
  private final ProjectInfoExtMapper mapper;
  private final RecoveryLitigationHandler recoveryLitigationHandler;
  private final RecoveryAdjustTrialHandler recoveryAdjustTrialHandler;
  private final RecoveryPaymentCollectionHandler recoveryPaymentCollectionHandler;
  private final SysDictionaryClient sysDictionaryClient;
  private final ProcessClient processClient;
  private final AssetUserLimitsUtils assetUserLimitsUtils;
  private final PlatformFieldUtils platformFieldUtils;
  private final PlatformUserUtils platformUserUtils;
  private final SysUserClient sysUserClient;

  @Override
  public Result<ProjectInfoExtDTO> add(ProjectInfoExtRequest request) {
    if (null == request || request.unverified()) {
      String message = "项目信息详细信息扩展表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    ProjectInfoExt projectInfoExt = PlatformMapUtils.getInstance().map(request, ProjectInfoExt.class);
    handler.save(projectInfoExt);

    ProjectInfoExtDTO dto = new ProjectInfoExtDTO();
    dto.setId(projectInfoExt.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<ProjectInfoExtDTO> update(ProjectInfoExtRequest request) {
    if (null == request || null == request.getId()) {
      String message = "项目信息详细信息扩展表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    ProjectInfoExt projectInfoExt = PlatformMapUtils.getInstance().map(request, ProjectInfoExt.class);
    handler.updateById(projectInfoExt);

    return Result.success();
  }

  @Override
  public Result<ProjectInfoExtDTO> findById(Long id) {
    ProjectInfoExt projectInfoExt = handler.getById(id);
    if (null == projectInfoExt) {
      String message = "项目信息详细信息扩展表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    ProjectInfoExtDTO dto = PlatformMapUtils.getInstance().map(projectInfoExt, ProjectInfoExtDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<ProjectInfoExtDTO>> findAll(Integer current, Integer size, ProjectInfoExtPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<ProjectInfoExt> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<ProjectInfoExt> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<ProjectInfoExtDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, ProjectInfoExtDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

  @Override
  public Result<List<ProjectInfoExtDTO>> litigationLedger(Integer current, Integer size, ProjectInfoExtPageRequest request) {
    // Step1：创建一个 Page 对象
    IPage<ProjectInfoExt> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    //  固定不查待分配的项目
    Result<SysDictionaryDTO> projectState = sysDictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey());
    if (ObjectUtils.isNotEmpty(projectState) && ObjectUtils.isNotEmpty(projectState.getData()) && ObjectUtils.isNotEmpty(projectState.getData().getItems())) {
      List<Long> projectStateIds = projectState.getData().getItems()
          .stream()
          .filter(item -> !DictEnum.PROJECT_STATE_01.getKey().equals(item.getItemCode()))
          .map(SysDictionaryItemDTO::getId)
          .collect(Collectors.toList());
      request.setProjectStateList(projectStateIds);
    }
    Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
    request.setOrgUserIds(userLimitsOrg);
    String manageSearch = request.getManage();
//    if (ObjectUtils.isNotEmpty(manageSearch)) {
//      SysUserPageRequest sysUserPageRequest = new SysUserPageRequest();
//      sysUserPageRequest.setUsername(manageSearch);
//      Result<List<SysUserDTO>> manageResult = sysUserClient.findAll(1, 10000, sysUserPageRequest);
//      if (!manageResult.succeedWithData()) {
//        return Result.success(new ArrayList<>());
//      }
//      List<SysUserDTO> data = manageResult.getData();
//      if (CollectionUtils.isNotEmpty(data)) {
//        request.setUserIds(data.stream().map(SysUserDTO::getId).collect(Collectors.toList()));
//      }
//    }
    List<ProjectInfoExtDTO> dtoList = mapper.litigationLedger(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(dtoList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
//    List<SysDictionaryItemDTO> projectStateItems = sysDictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey()).getData().getItems();
    for (ProjectInfoExtDTO projectInfoExtDTO : dtoList) {
//        Optional<SysDictionaryItemDTO> first = projectStateItems.stream().filter(item -> item.getId().toString().equals(projectInfoExtDTO.getProjectState())).findFirst();
//        first.ifPresent(sysDictionaryItemDTO -> projectInfoExtDTO.setProjectState(sysDictionaryItemDTO.getItemName()));
      String manage = projectInfoExtDTO.getManage();
      if (StringUtils.isNotBlank(manage)) {
        List<Long> manageList = Arrays.stream(manage.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());;
        List<SysUserDTO> userByIds = platformUserUtils.getUserByIds(manageList, false);
        projectInfoExtDTO.setManage(userByIds.stream().map(SysUserDTO::getUsername).collect(Collectors.joining(",")));
      }
    }
    List<Long> projectIds = dtoList.stream().map(ProjectInfoExtRequest::getId).collect(Collectors.toList());

    //  查询诉讼信息
    Map<Long, RecoveryLitigationDTO> litigation = getLitigation(projectIds);
    //  查询审批信息
    Map<Long, RecoveryAdjustTrialDTO> judgement = getJudgement(projectIds);
    //  查询回款信息
    List<RecoveryPaymentCollection> list = recoveryPaymentCollectionHandler.list(new LambdaQueryWrapper<RecoveryPaymentCollection>()
        .in(RecoveryPaymentCollection::getProjectId, projectIds)
        .eq(RecoveryPaymentCollection::getFlowState, ProcessStatus.completed));

//    List<RecoveryPaymentCollection> flowList = Lists.newArrayList();
//    for (RecoveryPaymentCollection recoveryPaymentCollection : list) {
//      // 流程状态
//      Result<ProcessStatus> statusResult = processClient.getStatusByDoId(recoveryPaymentCollection.getId());
//      if (null != statusResult && statusResult.succeedWithData()) {
//        flowList.add(recoveryPaymentCollection);
//      }
//    }
    Map<Long, List<RecoveryPaymentCollection>> collect = list.stream().collect(Collectors.groupingBy(RecoveryPaymentCollection::getProjectId));

    for (ProjectInfoExtDTO projectInfoExtDTO : dtoList) {
      RecoveryLitigationDTO recoveryLitigation = litigation.get(projectInfoExtDTO.getId());
      if (ObjectUtils.isNotEmpty(recoveryLitigation)) {
        projectInfoExtDTO.setCompensationAmountLitigation(recoveryLitigation.getCompensationAmount());
        projectInfoExtDTO.setInterestLitigation(recoveryLitigation.getInterest());
        projectInfoExtDTO.setLiquidatedDamagesLitigation(recoveryLitigation.getLiquidatedDamages());
        projectInfoExtDTO.setOtherFeesLitigation(recoveryLitigation.getOtherFees());
      }
      RecoveryAdjustTrialDTO recoveryAdjustTrialDTO = judgement.get(projectInfoExtDTO.getId());
      if (ObjectUtils.isNotEmpty(recoveryAdjustTrialDTO)) {
        projectInfoExtDTO.setCompensationAmountTrial(recoveryAdjustTrialDTO.getCompensatoryAmount());
        projectInfoExtDTO.setInterestTrial(recoveryAdjustTrialDTO.getInterest());
        projectInfoExtDTO.setLiquidatedDamagesTrial(recoveryAdjustTrialDTO.getBackOutAmount());
        projectInfoExtDTO.setOtherFeesTrial(recoveryAdjustTrialDTO.getOtherAmount());
        projectInfoExtDTO.setAdjustTrialType(recoveryAdjustTrialDTO.getAdjustTrialType());
      }
      List<RecoveryPaymentCollection> recoveryPaymentCollections = collect.get(projectInfoExtDTO.getId());
      if (ObjectUtils.isNotEmpty(recoveryPaymentCollections)) {

        Map<String, BigDecimal> collectionsMap = recoveryPaymentCollections.stream()
            .collect(Collectors.toMap(
                    RecoveryPaymentCollection::getCollectionSign,
                    RecoveryPaymentCollection::getCollectionAmount,
                    BigDecimal::add
                )
            );
        projectInfoExtDTO.setCashMoney(collectionsMap.get(CollectionSignEnum.CASH.getKey()));
        projectInfoExtDTO.setReGuaranteeMoney(collectionsMap.get(CollectionSignEnum.RE_GUARANTEE.getKey()));
        projectInfoExtDTO.setMortgageMoney(collectionsMap.get(CollectionSignEnum.MORTGAGE.getKey()));
        projectInfoExtDTO.setSilverBillMoney(collectionsMap.get(CollectionSignEnum.SILVER_BILL.getKey()));
      }
      Long productName = projectInfoExtDTO.getProductName();
      if (!org.springframework.util.ObjectUtils.isEmpty(productName)){
        Optional<SysDictionaryItemDTO> onLine = sysDictionaryClient.findByCode(DictEnum.ON_LINE.getKey()).getData().getItems().stream().filter(item -> item.getId().equals(projectInfoExtDTO.getProductName())).findAny();
        Optional<SysDictionaryItemDTO> offLine = sysDictionaryClient.findByCode(DictEnum.OFF_LINE.getKey()).getData().getItems().stream().filter(item -> item.getId().equals(projectInfoExtDTO.getProductName())).findAny();
        Optional<SysDictionaryItemDTO> traditional = sysDictionaryClient.findByCode(DictEnum.TRADITIONAL_PRODUCT.getKey()).getData().getItems().stream().filter(item -> item.getId().equals(projectInfoExtDTO.getProductName())).findAny();
        onLine.ifPresent(sysDictionaryItemDTO -> projectInfoExtDTO.setProduct(sysDictionaryItemDTO.getItemName()));
        offLine.ifPresent(sysDictionaryItemDTO -> projectInfoExtDTO.setProduct(sysDictionaryItemDTO.getItemName()));
        traditional.ifPresent(sysDictionaryItemDTO -> projectInfoExtDTO.setProduct(sysDictionaryItemDTO.getItemName()));
      }
    }

    // Step3：获取分页数据
//    List<ProjectInfoExtDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, ProjectInfoExtDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

  @Override
  public void exportLitigationLedgers(ProjectInfoExtPageRequest request, HttpServletResponse response) {
    Result<List<ProjectInfoExtDTO>> result = litigationLedger(1, 10000, request);
    if (null == result || !result.succeedWithData()) {
      return;
    }

    List<LitigationLedgerExportDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(result.getData(), LitigationLedgerExportDTO.class);

    List<String> industryTypeIds = dtoList.stream().filter(e -> null != e.getIndustryType()).map(e -> String.valueOf(e.getIndustryType())).distinct().collect(Collectors.toList());
    List<String> dictionaryItemIds = Lists.newArrayList();
    dictionaryItemIds.addAll(industryTypeIds);

    List<Long> provinceIds = dtoList.stream().map(LitigationLedgerExportDTO::getBelongProvince).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    List<Long> cityIds = dtoList.stream().map(LitigationLedgerExportDTO::getBelongCity).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    List<Long> districtIds = dtoList.stream().map(LitigationLedgerExportDTO::getBelongDistrict).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    List<Long> regionIds = Lists.newArrayList();
    regionIds.addAll(provinceIds);
    regionIds.addAll(cityIds);
    regionIds.addAll(districtIds);

    Map<Long, SysDictionaryItemDTO> dictionaryItemById = platformFieldUtils.getDictionaryItemNameById(dictionaryItemIds);
    Map<Long, String> regionNameById = platformFieldUtils.getRegionNameById(regionIds);

    dtoList.forEach(e -> {
      if (MapUtils.isNotEmpty(dictionaryItemById)) {
        e.setIndustryTypeName(Optional.ofNullable(dictionaryItemById.get(e.getIndustryType())).orElse(new SysDictionaryItemDTO()).getItemName());
      }

      if (MapUtils.isNotEmpty(regionNameById)) {
        e.setBelongName(Lists.newArrayList(regionNameById.get(e.getBelongProvince()), regionNameById.get(e.getBelongCity()), regionNameById.get(e.getBelongDistrict()))
            .stream()
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.joining(" - ")));
      }

      if (ObjectUtils.isNotEmpty(e.getAdjustTrialType())) {
        e.setAdjustTrialTypeName(e.getAdjustTrialType().getValue());
      }
    });

    String fileName = "诉讼台账";
    ExcelUtil.exportExcel(dtoList, fileName, LitigationLedgerExportDTO.class, response);
  }

  /**
   * 根据项目ids查询每个项目下面最新的诉讼信息
   * @param projectIds  项目id集合
   * @return java.util.Map<java.lang.Long,com.jk.asset.model.entity.proceeding.RecoveryLitigation>
   * @author wangshuai
   * @since 2024/7/16 15:38
   **/
  public Map<Long,RecoveryLitigationDTO> getLitigation(List<Long> projectIds){

    List<RecoveryLitigation> list = recoveryLitigationHandler.list(new LambdaQueryWrapper<RecoveryLitigation>()
        .in(RecoveryLitigation::getProjectId, projectIds)
        .orderByDesc(RecoveryLitigation::getCreateStamp));

    if (ObjectUtils.isEmpty(list)){
      return Maps.newHashMap();
    }
    List<RecoveryLitigationDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, RecoveryLitigationDTO.class);
    //  获取最新一条诉讼信息
    Map<Long, RecoveryLitigationDTO> latestMap = dtoList.stream()
        .filter(item -> item.getIsSubmit())
        .collect(Collectors.toMap(
            RecoveryLitigationDTO::getProjectId,
            b -> b,
            (existing, replacement) -> existing.getCreateStamp().after(replacement.getCreateStamp()) ? existing : replacement
        ));

    return latestMap;
  }

  /**
   * 根据项目ids查询每个项目下面最新的审批信息
   * @param projectIds  项目id集合
   * @return java.util.Map<java.lang.Long,com.jk.asset.model.entity.proceeding.RecoveryLitigation>
   * @author wangshuai
   * @since 2024/7/16 15:38
   **/
  public Map<Long,RecoveryAdjustTrialDTO> getJudgement(List<Long> projectIds){

    List<RecoveryAdjustTrial> list = recoveryAdjustTrialHandler.list(new LambdaQueryWrapper<RecoveryAdjustTrial>()
        .in(RecoveryAdjustTrial::getProjectId, projectIds)
        .orderByDesc(RecoveryAdjustTrial::getCreateStamp));

    if (ObjectUtils.isEmpty(list)){
      return Maps.newHashMap();
    }
    List<RecoveryAdjustTrial> flowList = Lists.newArrayList();
    for (RecoveryAdjustTrial recoveryAdjustTrial : list) {
      // 流程状态
      Result<ProcessStatus> statusResult = processClient.getStatusByDoId(recoveryAdjustTrial.getId());
      if (null != statusResult && statusResult.succeedWithData()) {
        flowList.add(recoveryAdjustTrial);
      }
    }
    List<RecoveryAdjustTrialDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(flowList, RecoveryAdjustTrialDTO.class);
    //  获取最新一条诉讼信息
    Map<Long, RecoveryAdjustTrialDTO> latestMap = dtoList.stream()
        .collect(Collectors.toMap(
            RecoveryAdjustTrialDTO::getProjectId,
            b -> b,
            (existing, replacement) -> existing.getCreateStamp().after(replacement.getCreateStamp()) ? existing : replacement
        ));

    return latestMap;
  }

}