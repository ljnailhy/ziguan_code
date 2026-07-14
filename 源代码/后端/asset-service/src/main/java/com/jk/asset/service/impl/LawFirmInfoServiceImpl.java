package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.ProjectStateEnum;
import com.jk.asset.mapper.LawFirmInfoMapper;
import com.jk.asset.mapper.RecoveryPaymentMapper;
import com.jk.asset.model.dto.LawFirmInfoDTO;
import com.jk.asset.model.dto.LawInfoDTO;
import com.jk.asset.model.entity.AllocationInfo;
import com.jk.asset.model.entity.AllocationInfoDetail;
import com.jk.asset.model.entity.ContractInfo;
import com.jk.asset.model.entity.LawFirmInfo;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RecoveryPayment;
import com.jk.asset.model.entity.RecoveryPaymentCollection;
import com.jk.asset.model.entity.RecoveryPaymentDetail;
import com.jk.asset.model.request.LawFirmInfoRequest;
import com.jk.asset.model.request.LawyerInfoRequest;
import com.jk.asset.model.request.page.LawFirmInfoPageRequest;
import com.jk.asset.service.LawFirmInfoService;
import com.jk.asset.service.handler.AllocationInfoDetailHandler;
import com.jk.asset.service.handler.AllocationInfoHandler;
import com.jk.asset.service.handler.ContractInfoHandler;
import com.jk.asset.service.handler.LawFirmInfoHandler;
import com.jk.asset.service.handler.LawyerInfoHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionHandler;
import com.jk.asset.service.handler.RecoveryPaymentDetailHandler;
import com.jk.asset.service.handler.RecoveryPaymentHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.model.entity.BaseDO;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.service.client.ProcessClient;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.utils.PlatformFileUtils;
import com.jk.service.utils.PlatformUserUtils;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 律所信息接口实现类
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:30
 */
@RestController
@Slf4j
@Api(tags = "律所信息接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LawFirmInfoServiceImpl implements LawFirmInfoService {

  private final LawFirmInfoHandler handler;
  private final LawFirmInfoMapper mapper;
  private final LawyerInfoHandler lawyerInfoHandler;
  private final PlatformFileUtils platformFileUtils;
  private final ProjectInfoHandler projectInfoHandler;
  private final RecoveryPaymentCollectionHandler recoveryPaymentCollectionHandler;
  private final SysDictionaryClient sysDictionaryClient;
  private final RecoveryPaymentHandler recoveryPaymentHandler;
  private final RecoveryPaymentMapper recoveryPaymentMapper;
  private final ContractInfoHandler contractInfoHandler;
  private final ProcessClient processClient;
  private final AllocationInfoDetailHandler allocationInfoDetailHandler;
  private final RecoveryPaymentDetailHandler recoveryPaymentDetailHandler;
  private final AllocationInfoHandler allocationInfoHandler;
  private final PlatformUserUtils platformUserUtils;
  @Override
  public Result<LawFirmInfoDTO> add(LawFirmInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "律所信息新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }
    List<LawyerInfoRequest> lawyerInfoRequestList = request.getLawyerInfoRequestList();
    List<Long> lawyers = lawyerInfoRequestList.stream().map(LawyerInfoRequest::getUserId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(lawyers)){
      // 律师Id
      List<LawyerInfo> lawyerInfo = lawyerInfoHandler.list(new LambdaQueryWrapper<LawyerInfo>().in(LawyerInfo::getUserId, lawyers));
      if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(lawyerInfo)){
      List<Long> userId =  lawyerInfo.stream().map(LawyerInfo::getUserId).collect(Collectors.toList());
        if (!userId.isEmpty()){
          List<SysUserDTO> userByIds = platformUserUtils.getUserByIds(userId, false);
          String usernames = userByIds.stream()
                  .map(SysUserDTO::getUsername).distinct()
                  .collect(Collectors.joining(","));
          return Result.error("登录账号: "+ usernames + "已分配律所");
        }
      }
    }
    // 对象拷贝：request->DO
    LawFirmInfo lawFirmInfo = PlatformMapUtils.getInstance().map(request, LawFirmInfo.class);
    handler.save(lawFirmInfo);
    //  新增修改律师团队
    lawyerInfoHandler.batchAddOrUpdateLawyerInfo(request.getLawyerInfoRequestList(),lawFirmInfo.getId());

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),lawFirmInfo.getId(), BillTypeEnum.LAW_FIRM_INFO.getKey());

    LawFirmInfoDTO dto = new LawFirmInfoDTO();
    dto.setId(lawFirmInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {


    //  判断合同，分配是否应用律所
    List<ContractInfo> contractInfoList = contractInfoHandler
        .list(new LambdaUpdateWrapper<ContractInfo>().eq(ContractInfo::getLawFirmId, id));
    List<AllocationInfoDetail> allocationInfoDetailList = allocationInfoDetailHandler
        .list(new LambdaUpdateWrapper<AllocationInfoDetail>().eq(AllocationInfoDetail::getLawFirm, id));
    //  判断合同，分配是否应用付款回款
    List<RecoveryPayment> recoveryPaymentList = recoveryPaymentHandler
        .list(new LambdaUpdateWrapper<RecoveryPayment>().eq(RecoveryPayment::getLawyerId, id));
    List<RecoveryPaymentCollection> recoveryPaymentCollectionList = recoveryPaymentCollectionHandler
        .list(new LambdaUpdateWrapper<RecoveryPaymentCollection>().eq(RecoveryPaymentCollection::getLawyerId,id));
    if (ObjectUtils.isNotEmpty(contractInfoList)){
      String message = "律所信息删除失败! 该律所已被合同管理引用不能删除";
      return Result.error(message);
    }
    if (ObjectUtils.isNotEmpty(allocationInfoDetailList)){
      List<Long> allocationIdList = allocationInfoDetailList.stream().map(AllocationInfoDetail::getAllocationId).collect(Collectors.toList());
      List<AllocationInfo> allocationInfos = allocationInfoHandler.listByIds(allocationIdList);
      if (ObjectUtils.isNotEmpty(allocationInfos)){
        String message = "律所信息删除失败 该律所已被项目分配引用不能删除";
        return Result.error(message);
      }
    }
    if (ObjectUtils.isNotEmpty(recoveryPaymentList)){
      String message = "律所信息删除失败 该律所已被付款管理引用不能删除";
      return Result.error(message);
    }
    if (ObjectUtils.isNotEmpty(recoveryPaymentCollectionList)){
      String message = "律所信息删除失败 该律所已被回款管理引用不能删除";
      return Result.error(message);
    }
    handler.removeById(id);

    //  通用附件删除
    platformFileUtils.deleteFileByDoId(id,BillTypeEnum.LAW_FIRM_INFO.getKey());
    //  律师团队删除
    lawyerInfoHandler.deleteLawyerInfoByContractId(id);
    return Result.success();
  }

  @Override
  public Result<LawFirmInfoDTO> update(LawFirmInfoRequest request) {
    if (null == request || null == request.getId()) {
      String message = "律所信息修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    LawFirmInfo lawFirmInfo = PlatformMapUtils.getInstance().map(request, LawFirmInfo.class);
    handler.updateById(lawFirmInfo);
    Long lawFirmId =  lawFirmInfo.getId();
    List<LawyerInfoRequest> lawyerInfoRequestList = request.getLawyerInfoRequestList();
    List<Long> lawyers = lawyerInfoRequestList.stream().map(LawyerInfoRequest::getUserId).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(lawyers)){
      // 律师Id
      List<LawyerInfo> lawyerInfo = lawyerInfoHandler.list(new LambdaQueryWrapper<LawyerInfo>().in(LawyerInfo::getUserId, lawyers));
      if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(lawyerInfo)){
        List<Long> userId = lawyerInfo.stream()
                .filter(info -> !lawFirmId.equals(info.getLawFirmId()))
                .map(LawyerInfo::getUserId)
                .collect(Collectors.toList());
        if (!userId.isEmpty()){
          List<SysUserDTO> userByIds = platformUserUtils.getUserByIds(userId, false);
          String usernames = userByIds.stream().distinct()
                  .map(SysUserDTO::getUsername)
                  .collect(Collectors.joining(","));
          return Result.error("登录账号: "+ usernames + "已分配律所");
        }
      }
    }
    //  新增修改律师团队
    lawyerInfoHandler.batchAddOrUpdateLawyerInfo(request.getLawyerInfoRequestList(),lawFirmInfo.getId());

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),lawFirmInfo.getId(), BillTypeEnum.LAW_FIRM_INFO.getKey());

    return Result.success();
  }

  @Override
  public Result<LawFirmInfoDTO> findById(Long id) {
    LawFirmInfo lawFirmInfo = handler.getById(id);
    if (null == lawFirmInfo) {
      String message = "律所信息单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    BigDecimal tenThousand = new BigDecimal("10000");
    // 对象拷贝：DO->DTO
    LawFirmInfoDTO dto = PlatformMapUtils.getInstance().map(lawFirmInfo, LawFirmInfoDTO.class);

    // 案件状态
    // 查询律所所有项目信息
    LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ProjectInfo::getLawFirmId,id);
    List<ProjectInfo> list = projectInfoHandler.list(queryWrapper);
    LawInfoDTO lawInfoDTO = new LawInfoDTO();
    if (ObjectUtils.isEmpty(list)) {
      dto.setLawInfoDTO(lawInfoDTO);
      return Result.success(dto);
    }
    List<SysDictionaryItemDTO> items = sysDictionaryClient.findByCode(ProjectStateEnum.PROJECT_STATE.getKey())
            .getData()
            .getItems();
    Map<Long, List<ProjectInfo>> collect = list.stream().collect(Collectors.groupingBy(ProjectInfo::getProjectState));
    BigDecimal hundred = new BigDecimal("100");
    items.forEach(a -> {
      List<ProjectInfo> projectInfoList = collect.get(a.getId());
      if (ObjectUtils.isNotEmpty(projectInfoList)){
        int size = projectInfoList.size();
        switch (a.getItemCode()){
          case "PROJECT_STATE_01":
            lawInfoDTO.setProjectState01(size);
            break;
          case "PROJECT_STATE_02":
            lawInfoDTO.setProjectState02(size);
            break;
          case "PROJECT_STATE_03":
            lawInfoDTO.setProjectState03(size);
            break;
          case "PROJECT_STATE_04":
            lawInfoDTO.setProjectState04(size);
            break;
          case "PROJECT_STATE_05":
            lawInfoDTO.setProjectState05(size);
            break;
          case "PROJECT_STATE_06":
            lawInfoDTO.setProjectState06(size);
            break;
          case "PROJECT_STATE_07":
            lawInfoDTO.setProjectState07(size);
            break;
          case "PROJECT_STATE_08":
            lawInfoDTO.setProjectState08(size);
            break;
          case "PROJECT_STATE_09":
            lawInfoDTO.setProjectState09(size);
            break;
          case "PROJECT_STATE_10":
            lawInfoDTO.setProjectState10(size);
            break;
          case "PROJECT_STATE_11":
            lawInfoDTO.setProjectState11(size);
            break;
          case "PROJECT_STATE_12":
            lawInfoDTO.setProjectState12(size);
            break;
          case "PROJECT_STATE_13":
            lawInfoDTO.setProjectState13(size);
            break;
          case "PROJECT_STATE_14":
            lawInfoDTO.setProjectState14(size);
            break;
            case "PROJECT_STATE_15":
              lawInfoDTO.setProjectState15(size);
            break;
          case "PROJECT_STATE_16":
            lawInfoDTO.setProjectState16(size);
            break;
          case "PROJECT_STATE_17":
            lawInfoDTO.setProjectState17(size);
            break;
          default:
            break;

        }
      }
    });
    lawInfoDTO.setManageProjectNum(list.size());
    // 代偿金额
     BigDecimal totalCompensationMoney = BigDecimal.ZERO;
     // 回款金额
    BigDecimal totalCollectionAmount = BigDecimal.ZERO;
    List<BigDecimal> collect1 = list.stream().map(ProjectInfo::getCompensationMoney).collect(Collectors.toList());
    for (BigDecimal decimal : collect1) {
      if (ObjectUtils.isNotEmpty(decimal)){
        totalCompensationMoney = totalCompensationMoney.add(decimal);
      }
    }
    SysDictionaryItemDTO any1 = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
            .getData()
            .getItems()
            .stream()
            .filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey()))
            .findAny().get();
    lawInfoDTO.setTotalCompensationMoney(totalCompensationMoney);
    LambdaQueryWrapper<RecoveryPaymentCollection> query = new LambdaQueryWrapper<>();
    query.eq(RecoveryPaymentCollection::getLawyerId,id)
            .eq(RecoveryPaymentCollection::getCollectionStatus,any1.getId());
    List<RecoveryPaymentCollection> paymentCollectionList = recoveryPaymentCollectionHandler.list(query);
    if (ObjectUtils.isNotEmpty(paymentCollectionList)){
      for (RecoveryPaymentCollection collection : paymentCollectionList) {
        BigDecimal collectionAmount = collection.getCollectionAmount();
        if (ObjectUtils.isNotEmpty(collectionAmount)){
          totalCollectionAmount = totalCollectionAmount.add(collectionAmount);
        }
      }
      if (totalCompensationMoney.compareTo(BigDecimal.ZERO) == 0){
        lawInfoDTO.setCollectionRate(BigDecimal.ZERO);
      }else {
        lawInfoDTO.setCollectionRate(totalCollectionAmount.divide(totalCompensationMoney, 4, RoundingMode.HALF_UP).multiply(hundred));
      }
    }
    lawInfoDTO.setTotalPaymentCollection(totalCollectionAmount);
    //本年回款率 本年代偿金额/本年回款金额
    // 本年代偿金额
    BigDecimal yearCompensationMoney = BigDecimal.ZERO;
    List<ProjectInfo> collect2 = list.stream().filter(a -> isThisYear(a.getCompensationDate())).collect(Collectors.toList());
    for (ProjectInfo projectInfo : collect2) {
      BigDecimal compensationMoney = projectInfo.getCompensationMoney();
      if (compensationMoney != null){
        yearCompensationMoney = yearCompensationMoney.add(compensationMoney);
      }
    }
    // 本年回款金额
    BigDecimal yearCollection = BigDecimal.ZERO;
    List<RecoveryPaymentCollection> collect3 = paymentCollectionList.stream().filter(a -> isThisYear(a.getCollectionDate())).collect(Collectors.toList());
    for (RecoveryPaymentCollection collection : collect3) {
      BigDecimal collectionAmount = collection.getCollectionAmount();
      if (collection.getCollectionAmount() != null){
        yearCollection = yearCollection.add(collectionAmount);
      }
    }
    if (yearCompensationMoney.compareTo(BigDecimal.ZERO) == 0){
      lawInfoDTO.setCollectionRateYear(BigDecimal.ZERO);
    }else {
      lawInfoDTO.setCollectionRateYear(yearCollection.divide(yearCompensationMoney, 4, RoundingMode.HALF_UP).multiply(hundred));
    }
    // 律师费 付款金额
   SysDictionaryItemDTO any = sysDictionaryClient.findByCode(DictEnum.PAY_STATUS.getKey())
            .getData()
            .getItems()
            .stream()
            .filter(item -> item.getItemCode().equals(DictEnum.PAY_STATUS_002.getKey()))
            .findAny().get();

    SysDictionaryItemDTO lawFeeDictionaryId = sysDictionaryClient.findByCode(DictEnum.PAY_TYPE.getKey())
            .getData()
            .getItems()
            .stream()
            .filter(item -> "PAY_TYPE_005".equals(item.getItemCode()))
            .findAny().get();
    LambdaQueryWrapper<RecoveryPayment> paymentQuery = new LambdaQueryWrapper<>();
    paymentQuery.eq(RecoveryPayment::getLawyerId, id)
            .eq(RecoveryPayment::getPayStatus, any.getId());
    List<RecoveryPayment> paymentList = recoveryPaymentHandler.list(paymentQuery);
    BigDecimal fee = BigDecimal.ZERO;
    if (ObjectUtils.isNotEmpty(paymentList)){
      List<RecoveryPaymentDetail> detailList = recoveryPaymentDetailHandler
              .list(new LambdaQueryWrapper<RecoveryPaymentDetail>()
              .in(RecoveryPaymentDetail::getPaymentId, paymentList.stream().map(BaseDO::getId).collect(Collectors.toList())));
      if (!detailList.isEmpty()){
        for (RecoveryPaymentDetail recoveryPaymentDetail : detailList) {
          Long payType = recoveryPaymentDetail.getPayType();
          if (lawFeeDictionaryId.getId().equals(payType)) {
            fee = fee.add(recoveryPaymentDetail.getPayAmount() == null ? BigDecimal.ZERO : recoveryPaymentDetail.getPayAmount());
          }
        }
      }
        lawInfoDTO.setLawFee(fee);
    }
    long count = list.stream().filter(ProjectInfo::getIsWriteOff).count();
    if (list.isEmpty()){
      lawInfoDTO.setWriteOffRate(BigDecimal.ZERO);
    }else {
      lawInfoDTO.setWriteOffRate(new BigDecimal(String.valueOf(count)).divide(BigDecimal.valueOf(list.size()), 4, RoundingMode.HALF_UP).multiply(hundred));
    }
    if (tenThousand.compareTo(BigDecimal.ZERO) == 0){
      lawInfoDTO.setLawFee(BigDecimal.ZERO);
      lawInfoDTO.setTotalCompensationMoney(BigDecimal.ZERO);
      lawInfoDTO.setTotalPaymentCollection(BigDecimal.ZERO);
    }else {
      lawInfoDTO.setLawFee(lawInfoDTO.getLawFee() == null ? BigDecimal.ZERO : lawInfoDTO.getLawFee().divide(tenThousand, 6, RoundingMode.HALF_UP));
      lawInfoDTO.setTotalCompensationMoney(lawInfoDTO.getTotalCompensationMoney() == null?BigDecimal.ZERO:lawInfoDTO.getTotalCompensationMoney().divide(tenThousand,6,RoundingMode.HALF_UP));
      lawInfoDTO.setTotalPaymentCollection(lawInfoDTO.getTotalPaymentCollection() == null?BigDecimal.ZERO:lawInfoDTO.getTotalPaymentCollection().divide(tenThousand,6,RoundingMode.HALF_UP));
    }
    dto.setLawInfoDTO(lawInfoDTO);
    return Result.success(dto);
  }
  public static boolean isThisYear(Date date) {
    // 获取当前日期
    Calendar calendar = Calendar.getInstance();
    // 获取当前年份
    int currentYear = calendar.get(Calendar.YEAR);

    // 将Date对象转换为Calendar对象以便获取年份
    Calendar dateCalendar = Calendar.getInstance();
    dateCalendar.setTime(date);
    // 获取Date对象的年份
    int dateYear = dateCalendar.get(Calendar.YEAR);

    // 比较年份
    return dateYear == currentYear;
  }
  @Override
  public Result<?> isEnable(Long id, Boolean enabled) {

    handler.update(new LambdaUpdateWrapper<LawFirmInfo>()
        .set(LawFirmInfo::getEnabled,enabled)
        .eq(LawFirmInfo::getId,id));

    return Result.success();
  }

  @Override
  public Result<List<LawFirmInfoDTO>> findAll(Integer current, Integer size, LawFirmInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<LawFirmInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<LawFirmInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<LawFirmInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, LawFirmInfoDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

  @Override
  public Result<List<LawFirmInfoDTO>> findAssemblyList(Integer current, Integer size, LawFirmInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    request.setEnabled(Boolean.TRUE);
    // Step1：创建一个 Page 对象
    IPage<LawFirmInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<LawFirmInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<LawFirmInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, LawFirmInfoDTO.class);
    //  获取这些律所项目统计
    for (LawFirmInfoDTO lawFirmInfoDTO : dtoList) {
      //  查询该律所管理多少项目
      List<ProjectInfo> list = projectInfoHandler.list(new LambdaUpdateWrapper<ProjectInfo>().eq(ProjectInfo::getLawFirmId, lawFirmInfoDTO.getId()));

      if (ObjectUtils.isEmpty(list)) {
        lawFirmInfoDTO.setProjectNumber(0);
        lawFirmInfoDTO.setCompensationMoneySum(BigDecimal.ZERO);
        lawFirmInfoDTO.setCollectionAmountSum(BigDecimal.ZERO);
        lawFirmInfoDTO.setCollectionRate(null);
        continue;
      }

      //  查询这些项目的代偿金额
      BigDecimal compensationMoneySum = list.stream()
            .filter(item -> null != item.getCompensationMoney())
            .map(item -> item.getCompensationMoney())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

      List<Long> projectIds = list.stream().map(item -> item.getId()).collect(Collectors.toList());

      //  查询这些项目的回款记录
      List<RecoveryPaymentCollection> recoveryList = recoveryPaymentCollectionHandler.list(new LambdaUpdateWrapper<RecoveryPaymentCollection>()
          .in(RecoveryPaymentCollection::getProjectId, projectIds));

      List<RecoveryPaymentCollection> newRecoveryList = Lists.newArrayList();
      for (RecoveryPaymentCollection recoveryPaymentCollection : recoveryList) {
        // 流程状态
        Result<ProcessStatus> statusResult = processClient.getStatusByDoId(recoveryPaymentCollection.getId());
        if (null != statusResult && statusResult.succeedWithData() && statusResult.getData().getKey().equals(ProcessStatus.completed.getKey())) {
          newRecoveryList.add(recoveryPaymentCollection);
        }
      }

      BigDecimal collectionAmountSum = newRecoveryList.stream()
          .filter(item -> null != item.getCollectionAmount())
          .map(item -> item.getCollectionAmount())
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      lawFirmInfoDTO.setProjectNumber(list.size());
      lawFirmInfoDTO.setCompensationMoneySum(compensationMoneySum);
      lawFirmInfoDTO.setCollectionAmountSum(collectionAmountSum);
      //  累计代偿金额 /  累计回款金额 * 100
      if (compensationMoneySum.compareTo(BigDecimal.ZERO) == 0){
        lawFirmInfoDTO.setCollectionRate(BigDecimal.ZERO);
      }else{
        lawFirmInfoDTO.setCollectionRate(collectionAmountSum.divide(compensationMoneySum,4,RoundingMode.HALF_UP).multiply(new BigDecimal("100")));
      }
    }
    return Result.success(dtoList, PageFactory.page(page));
  }

}