package com.jk.asset.tasks;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.mapper.ContractInfoMapper;
import com.jk.asset.mapper.ProjectInfoMapper;
import com.jk.asset.mapper.property.PropertyBillMapper;
import com.jk.asset.mapper.property.PropertyInfoMapper;
import com.jk.asset.model.dto.LawFirmAgreementDTO;
import com.jk.asset.model.dto.OperationInfoRemindDTO;
import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.asset.model.dto.PropertyInfoRemindDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.entity.WorkRegister;
import com.jk.asset.model.entity.property.LeasePaymentCycle;
import com.jk.asset.model.entity.property.PropertyBill;
import com.jk.asset.model.entity.property.PropertyInfo;
import com.jk.asset.model.request.ProjectBusinessInfoRequest;
import com.jk.asset.model.request.page.ProjectInfoPageRequest;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RevePropertyInfoHandler;
import com.jk.asset.service.handler.WorkRegisterHandler;
import com.jk.asset.service.handler.property.LeasePaymentCycleHandler;
import com.jk.asset.service.handler.property.PropertyBillHandler;
import com.jk.asset.service.handler.property.PropertyInfoHandler;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformDateUtils;
import com.jk.infrastructure.enums.HandleStateEnum;
import com.jk.infrastructure.enums.MessageStateEnum;
import com.jk.infrastructure.enums.MessageTypeEnum;
import com.jk.infrastructure.model.dto.SysDictionaryDTO;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysMessageDTO;
import com.jk.infrastructure.model.dto.SysOrgDTO;
import com.jk.infrastructure.model.request.sys.message.SysMessagePageRequest;
import com.jk.infrastructure.model.request.sys.message.SysMessageRequest;
import com.jk.infrastructure.model.request.sys.org.SysOrgPageRequest;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.client.SysMessageClient;
import com.jk.service.client.SysOrgClient;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 临期预警  弹层提醒和发系统消息提示，提醒跟进人处理（每隔5天提醒一次）
 *
 * @author WangShuai
 * @since 2024/7/22 11:34
 **/
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdventEarlyWarning {


  private final ProjectInfoHandler projectInfoHandler;
  private final ProjectInfoMapper projectInfoMapper;
  private final RevePropertyInfoHandler revePropertyInfoHandler;
  private final SysDictionaryClient dictionaryClient;
  private final SysMessageClient messageClient;
  private final SysDictionaryClient sysDictionaryClient;
  private final WorkRegisterHandler workRegisterHandler;
  private final LeasePaymentCycleHandler leasePaymentCycleHandler;
  private final PropertyBillHandler propertyBillHandler;
  private final PropertyInfoHandler propertyInfoHandler;
  private final PropertyInfoMapper propertyInfoMapper;
  private final PropertyBillMapper propertyBillMapper;
  private final ContractInfoMapper contractInfoMapper;
  private final SysOrgClient sysOrgClient;
  /**
   * 保全预警  保全到期日小于90天
   *
   * @param
   * @return void
   * @author wangshuai
   * @since 2024/7/22 11:36
   **/
  @XxlJob("preservationEarlyWarning")
  public void preservationEarlyWarning() {
    log.info("定时 保全预警 开始");

    //  线下项目，项目工作日期超90天未登记且项目状态不为“已结案”的项目，系统自动提醒保全
    //  查询项目状态不为已结案的项目
    List<ProjectInfo> projectInfoList = getProjectList();

    if (ObjectUtils.isEmpty(projectInfoList)) {
      return;
    }
    List<Long> projectIds = projectInfoList.stream().map(ProjectInfo::getId).collect(Collectors.toList());

    //  查询保全到期日小于九十天的项目
    LocalDate ninetyDaysAgo = LocalDate.now().minusDays(90);
    // 创建 LambdaQueryWrapper 实例
    LambdaQueryWrapper<RevePropertyInfo> queryWrapper = new LambdaQueryWrapper<>();
    // 添加日期范围条件
    queryWrapper.between(RevePropertyInfo::getPreserveDate, ninetyDaysAgo, new Date());
    queryWrapper.in(RevePropertyInfo::getDoId, projectIds);
    queryWrapper.eq(RevePropertyInfo::getDoType, BillTypeEnum.PROJECT_INFO.getKey());
    List<RevePropertyInfo> revePropertyInfoList = revePropertyInfoHandler.list(queryWrapper);

    if (ObjectUtils.isEmpty(revePropertyInfoList)) {
      return;
    }

    //  如果是反担保查询担保名称
//    List<Long> reveIds = revePropertyInfoList
//        .stream()
//        .filter(item -> BillTypeEnum.REVE.getKey().equals(item.getBillType()))
//        .map(item -> Long.valueOf(item.getReveName()))
//        .collect(Collectors.toList());
//    Map<Long, String> subjectNameMap = Maps.newHashMap();
//    if (ObjectUtils.isNotEmpty(reveIds)) {
//      subjectNameMap = subjectInfoHandler.list(new LambdaQueryWrapper<SubjectInfo>().in(SubjectInfo::getId, reveIds))
//          .stream().collect(Collectors.toMap(SubjectInfo::getId, SubjectInfo::getSubjectName));
//    }
    //  保全经理id和项目Map
    Map<Long, String> projectManageMap = projectInfoList
            .stream()
            .filter(item -> StringUtils.isNotBlank(item.getManage()))
            .collect(Collectors.toMap(ProjectInfo::getId, ProjectInfo::getManage));
    String content = "该保全日期距离当前日期小于90天请处理";
    //  五天转分钟
    int day = 7200;
    for (RevePropertyInfo revePropertyInfo : revePropertyInfoList) {
      String title = "保全预警 ";
      if (BillTypeEnum.REVE.getKey().equals(revePropertyInfo.getBillType())) {
//        title = title + subjectNameMap.get(Long.valueOf(revePropertyInfo.getReveName()));
        title = title + revePropertyInfo.getReveName();
      } else {
        title = title + revePropertyInfo.getReveMeasure();
      }
      //  判断这条数据五天内有没有发送过
      SysMessagePageRequest sysMessagePageRequest = new SysMessagePageRequest();
      sysMessagePageRequest.setDoId(revePropertyInfo.getId());
      sysMessagePageRequest.setDoType(BillTypeEnum.REVE.getKey());
      Result<List<SysMessageDTO>> allCommon = messageClient.findAllCommon(1, 1, sysMessagePageRequest);
      if (ObjectUtils.isNotEmpty(allCommon) && ObjectUtils.isNotEmpty(allCommon.getData())) {
        SysMessageDTO sysMessageDTO = allCommon.getData().get(0);
        int intervalMins = PlatformDateUtils.getIntervalMins(sysMessageDTO.getCreateStamp(), new Date());
        //  如果二个日期小于五天不再发送
        log.info("该记录五天内已发送过提醒消息 startday {} endday {} intervalMins {}", sysMessageDTO.getCreateStamp(), new Date(), intervalMins);
        if (intervalMins < day) {
          continue;
        }
      }
      addMessage(revePropertyInfo.getId(), BillTypeEnum.REVE.getKey(), title, content, projectManageMap.get(revePropertyInfo.getDoId()), DictEnum.MESSAGE_TYPE_001.getKey(), "");
    }
    log.info("定时 保全预警 结束");
  }

  /**
   * 诉讼预警 诉讼到期日小于90天
   *
   * @param
   * @return void
   * @author wangshuai
   * @since 2024/7/23 10:19
   **/
  @XxlJob("proceedingEarlyWarning")
  public void proceedingEarlyWarning() {
    List<Long> projectStatus = sysDictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey())
            .getData().getItems().stream().filter(item ->
                    item.getItemCode().equals(DictEnum.PROJECT_STATE_03.getKey()) ||
                            item.getItemCode().equals(DictEnum.PROJECT_STATE_04.getKey()) ||
                            item.getItemCode().equals(DictEnum.PROJECT_STATE_05.getKey()))
            .map(SysDictionaryItemDTO::getId).collect(Collectors.toList());
    //  诉讼时效
    List<ProjectInfo> projectList = projectInfoHandler.list(new LambdaUpdateWrapper<ProjectInfo>().in(ProjectInfo::getProjectState, projectStatus));
    if (CollectionUtils.isEmpty(projectList)) {
      return;
    }
    int day = 7200;
    //  诉讼时效小于90天
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTimeInMillis(new Date().getTime());
    gc.add(Calendar.YEAR, 90);
    List<ProjectInfo> projectInfoList = projectList.stream()
            .filter(item -> item.getProceedingAgeingDate().after(gc.getTime())).collect(Collectors.toList());
    //  未立案 无和解，调解
    Map<Long, String> projectManageMap = projectInfoList
            .stream()
            .filter(item -> StringUtils.isNotBlank(item.getManage()))
            .collect(Collectors.toMap(ProjectInfo::getId, ProjectInfo::getManage));
    if (CollectionUtils.isNotEmpty(projectList)) {
      for (ProjectInfo projectInfo : projectInfoList) {
        String content = "项目: " + projectInfo.getProjectName() + "该诉讼时效距离当前日期小于90天请及时处理";
        String title = "诉讼时效提醒";
        //  判断这条数据五天内有没有发送过
        SysMessagePageRequest sysMessagePageRequest = new SysMessagePageRequest();
        sysMessagePageRequest.setDoId(projectInfo.getId());
        sysMessagePageRequest.setDoType(BillTypeEnum.PROJECT_INFO.getKey());
        Result<List<SysMessageDTO>> allCommon = messageClient.findAllCommon(1, 1, sysMessagePageRequest);
        if (ObjectUtils.isNotEmpty(allCommon) && ObjectUtils.isNotEmpty(allCommon.getData())) {
          SysMessageDTO sysMessageDTO = allCommon.getData().get(0);
          int intervalMins = PlatformDateUtils.getIntervalMins(sysMessageDTO.getCreateStamp(), new Date());
          //  如果二个日期小于五天不再发送
          if (intervalMins < day) {
            return;
          }
        }
        addMessage(projectInfo.getId(), BillTypeEnum.PROJECT_INFO.getKey(), title, content, projectManageMap.get(projectInfo.getId()), DictEnum.MESSAGE_TYPE_001.getKey(), "");
      }
    }
  }

  /**
   * 线下项目，项目工作日期超90天未登记且项目状态不为“已结案”的项目，系统自动提醒保全经理进行跟踪
   *
   * @param
   * @return void
   * @author wangshuai
   * @since 2024/7/23 10:19
   **/
  @XxlJob("offlineProjectWorkRegisterWarning")
  public void offlineProjectWorkRegisterWarning() {
    List<Long> projectStatus = sysDictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey())
            .getData().getItems().stream().filter(item ->
                    !item.getItemCode().equals(DictEnum.PROJECT_STATE_15.getKey()))
            .map(SysDictionaryItemDTO::getId).collect(Collectors.toList());

    ProjectInfoPageRequest projectInfoRequest = new ProjectInfoPageRequest();
    projectInfoRequest.setProjectStateList(projectStatus);
    IPage<ProjectInfoDTO> page = new Page<>(1, 10000);
    List<ProjectInfoDTO> projectInfos = projectInfoMapper.findAll(page, projectInfoRequest);
    if (CollectionUtils.isEmpty(projectInfos)) {
      return;
    }
    // 线下产品字典
    List<Long> offLineItems = dictionaryClient.findByCode(DictEnum.OFF_LINE.getKey()).getData().getItems()
            .stream().map(SysDictionaryItemDTO::getId).collect(Collectors.toList());
    // 线下产品对应的项目
    List<ProjectInfoDTO> projects = projectInfos.stream()
            .filter(projectInfo -> {
              List<ProjectBusinessInfoRequest> businessInfos = projectInfo.getBusinessInfos();
              return CollectionUtils.isNotEmpty(businessInfos) &&
                      businessInfos.stream()
                              .anyMatch(businessInfo ->
                                      businessInfo.getProductName() != null &&
                                              offLineItems.contains(businessInfo.getProductName())
                              );
            })
            .collect(Collectors.toList());
    if (CollectionUtils.isEmpty(projects)) {
      return;
    }
    // 项目工作日期超90天未登记
    List<WorkRegister> workRegisterList = workRegisterHandler.list(new LambdaQueryWrapper<WorkRegister>()
            .eq(WorkRegister::getDoType, BillTypeEnum.PROJECT_INFO.getKey())
            .in(WorkRegister::getDoId, projects.stream().map(ProjectInfoDTO::getId).collect(Collectors.toList())));
    if (CollectionUtils.isNotEmpty(workRegisterList)) {
      projects = projects.stream().filter(item -> workRegisterList.stream().map(WorkRegister::getDoId).collect(Collectors.toList()).contains(item.getId())).collect(Collectors.toList());
    }
    if (CollectionUtils.isEmpty(projects)) {
      return;
    }
    // todo 项目工作日期?筛选
    for (ProjectInfoDTO project : projects) {
      addMessage(project.getId(), BillTypeEnum.PROJECT_INFO.getKey(),
              "项目工作日期超90天未登记", "项目: " + project.getProjectName() + "项目工作日期超90天未登记，请及时登记",
              project.getManage(), DictEnum.MESSAGE_TYPE_004.getKey(), "");
    }
  }


  /**
   * 项目状态不为“已结案”的项目
   * @param
   * @return java.util.List<com.jk.asset.model.entity.ProjectInfo>
   * @author wangshuai
   * @since 2024/7/23 9:45
   **/
  public List<ProjectInfo> getProjectList(){
    Long state = null;
    try {
      Result<SysDictionaryDTO> byCode = dictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey());
      if (byCode.getCode().equals(0)) {
        Optional<Long> first = byCode.getData().getItems()
            .stream()
            .filter(item -> DictEnum.PROJECT_STATE_15.getKey().equals(item.getItemCode()))
            .map(SysDictionaryItemDTO::getId)
            .findFirst();
        state = first.get();
      }
    }catch (Exception e){
      String message = "保全预警，查询项目状态失败";
      log.error("{} code {}", message, DictEnum.PROJECT_STATE_15.getKey());
      return Lists.newArrayList();
    }
    return projectInfoHandler.list(new LambdaUpdateWrapper<ProjectInfo>().ne(ProjectInfo::getProjectState, state));
  }


  /**
   * 发送消息
   * @param doId  数据对象id
   * @param doType  数据对象类型
   * @param title 标题
   * @param content 内容
   * @param receiveUser 接收人
   * @param messageType 消息类型
   * @param url
   * @return void
   * @author wangshuai
   * @since 2024/7/23 9:41
   **/
  public void addMessage(Long doId, String doType, String title, String content, String receiveUser, String messageType, String url){
    //  查询保全预警类型id
    SysDictionaryItemDTO messageStateItemSto = sysDictionaryClient.findByCode(DictEnum.MESSAGE_TYPE.getKey())
        .getData()
        .getItems()
        .stream()
        .filter(item -> item.getItemCode().equals(messageType))
        .findAny()
        .get();
    SysMessageRequest sysMessageRequest = new SysMessageRequest();
    sysMessageRequest.setDoId(doId);
    sysMessageRequest.setDoType(doType);
    sysMessageRequest.setTitle(title);
    sysMessageRequest.setContent(content);
    sysMessageRequest.setMessageType(MessageTypeEnum.UNDONE);
    sysMessageRequest.setMessageState(MessageStateEnum.SEND);
    sysMessageRequest.setHandleState(HandleStateEnum.UNDONE);
    sysMessageRequest.setReceiveUser(receiveUser);
    if(ObjectUtils.isNotEmpty(messageStateItemSto)) {
      sysMessageRequest.setMessageCategory(messageStateItemSto.getId());
    }
    sysMessageRequest.setUrl(url);
    messageClient.add(sysMessageRequest);
    log.info("发送消息成功 sysMessageRequest：{}", JSON.toJSONString(sysMessageRequest));
  }

  /**
   * 	租赁缴费通知提示，提前一个月提醒资产跟进人
   * @param
   * @return void
   * @author wangshuai
   * @since 2024/7/23 10:19
   **/
  @XxlJob("projectLeaseWarning")
  public void projectLeaseWarning() {
    Long payStatus = sysDictionaryClient.findByCode("PAYMENT_CYCLE_STATUS")
            .getData().getItems().stream()
            .filter(item -> "PAYMENT_CYCLE_STATUS_002".equals(item.getItemCode())).findAny().get().getId();
    List<LeasePaymentCycle> cycleList = leasePaymentCycleHandler.list(new LambdaUpdateWrapper<LeasePaymentCycle>()
            .eq(LeasePaymentCycle::getState, payStatus)
            .eq(LeasePaymentCycle::getDoType, BillTypeEnum.LEASE_INFO.getKey()));
    if (CollectionUtils.isEmpty(cycleList)){
      return;
    }
      Map<Long,String> idsMap =  new HashedMap<>();
    for (LeasePaymentCycle leasePaymentCycle : cycleList) {
      Date remindDate = leasePaymentCycle.getRemindDate();
      if (ObjectUtils.isEmpty(remindDate)) {
        continue;
      }
      Date oneMonthday = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date endDay = Date.from(LocalDate.now().plusMonths(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
      String formattedDate = sdf.format(endDay);
      String formattedRemindDate = sdf.format(remindDate);
      String formatDay = sdf.format(oneMonthday);
      if (!formattedRemindDate.equals(formatDay)) {
        continue;
      }
        // 发送消息
        idsMap.put(leasePaymentCycle.getDoId(), formattedRemindDate);
        List<PropertyBill> billList = propertyBillHandler.list(new LambdaUpdateWrapper<PropertyBill>().in(PropertyBill::getDoId, idsMap.keySet())
                .eq(PropertyBill::getDoType, BillTypeEnum.LEASE_INFO.getKey()));
        List<Long> collect = billList.stream().map(PropertyBill::getPropertyId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
          continue;
        }
          List<PropertyInfo> propertyInfos = propertyInfoHandler.listByIds(collect);
          if (CollectionUtils.isEmpty(propertyInfos)) {
            continue;
          }
            for (PropertyInfo propertyInfo : propertyInfos) {
              String followUpPerson = propertyInfo.getFollowUpPerson();
              if (followUpPerson == null) {
                continue;
              }
              addMessage(leasePaymentCycle.getDoId(), BillTypeEnum.LEASE_INFO.getKey(),
                      "收租提醒", propertyInfo.getPropertyName() + "租赁资产于" + formattedDate + "到期，请及时跟进收租或续签事宜！",
                      followUpPerson, DictEnum.MESSAGE_TYPE_008.getKey(), ""
              );
            }
    }
  }

  /**
   * 		运营提醒 没做运营 每隔5天 提醒一次 2个月没做运营->提醒
   * @param
   * @return void
   * @author wangshuai
   * @since 2024/7/23 10:19
   **/
  @XxlJob("operationRegisterWarning")
  public void operationRegisterWarning() {
    // 查询出未做运营的
    List<PropertyInfoRemindDTO> propertyInfoList = propertyInfoMapper.findUnOperation();
    if (CollectionUtils.isEmpty(propertyInfoList)) {
      return;
    }
    for (PropertyInfoRemindDTO propertyInfo : propertyInfoList) {
      String title = "运营提醒";
      String content = propertyInfo.getPropertyName() + "资产还未进行运营登记，请及时跟进！";
      if (ObjectUtils.isEmpty(propertyInfo.getFollowUpPerson())) {
        continue;
      }
      //  判断这条数据五天内有没有发送过
      SysMessagePageRequest sysMessagePageRequest = new SysMessagePageRequest();
      sysMessagePageRequest.setDoId(propertyInfo.getDoId());
      sysMessagePageRequest.setDoType(BillTypeEnum.OPERATION_INFO.getKey());
      Result<List<SysMessageDTO>> allCommon = messageClient.findAllCommon(1, 1, sysMessagePageRequest);
      if (ObjectUtils.isNotEmpty(allCommon) && ObjectUtils.isNotEmpty(allCommon.getData())) {
        SysMessageDTO sysMessageDTO = allCommon.getData().get(0);
        int intervalMins = PlatformDateUtils.getIntervalMins(sysMessageDTO.getCreateStamp(), new Date());
        //  如果二个日期小于五天不再发送
        if (intervalMins < 5 * 24 * 60) {
          continue;
        }
        addMessage(propertyInfo.getId(), BillTypeEnum.OPERATION_INFO.getKey(), title, content, propertyInfo.getFollowUpPerson(), DictEnum.MESSAGE_TYPE_007.getKey(), "");
      }else {
        addMessage(propertyInfo.getId(), BillTypeEnum.OPERATION_INFO.getKey(), title, content, propertyInfo.getFollowUpPerson(), DictEnum.MESSAGE_TYPE_007.getKey(), "");
      }
    }
    // 提醒一次 2个月没做运营->提醒
    List<OperationInfoRemindDTO> propertyInfos = propertyBillMapper.findOperation();
    if (CollectionUtils.isEmpty(propertyInfos)) {
      return;
    }
    Map<Long, OperationInfoRemindDTO> propertyOption = propertyInfos.stream()
            .filter(item -> item.getOperationDate() != null)
            .collect(Collectors.toMap(
                    OperationInfoRemindDTO::getPropertyId,
                    o -> o,
                    (o1, o2) -> o1.getOperationDate().compareTo(o2.getOperationDate()) > 0 ? o1 : o2
            ));
    if (propertyOption.isEmpty()) {
      return;
    }
    for (Map.Entry<Long, OperationInfoRemindDTO> remind : propertyOption.entrySet()) {
      OperationInfoRemindDTO value = remind.getValue();
      //  判断这条数据有没有发送过
      SysMessagePageRequest sysMessagePageRequest = new SysMessagePageRequest();
      sysMessagePageRequest.setDoId(value.getDoId());
      sysMessagePageRequest.setDoType(BillTypeEnum.OPERATION_INFO.getKey());
      Result<List<SysMessageDTO>> allCommon = messageClient.findAllCommon(1, 1, sysMessagePageRequest);
      if (ObjectUtils.isNotEmpty(allCommon) && ObjectUtils.isNotEmpty(allCommon.getData())) {
        continue;
      }
      if (value.getFollowUpPerson() != null) {
        String title = "资产运营提醒";
        String content = "资产为 " + value.getPropertyName() + "已2个月未运营，请及时跟进！";
        addMessage(value.getDoId(), BillTypeEnum.OPERATION_INFO.getKey(), title, content, value.getFollowUpPerson(), DictEnum.MESSAGE_TYPE_007.getKey(), "");
      }

    }
  }



  /**
   * 		律所协议到期提醒，协议到期前3个月提醒一次，提醒保全部部长与保全经理
   * @param
   * @return void
   * @author wangtao
   * @since 2024/7/23 10:19
   **/
  @XxlJob("lawFirmAgreementEndWarning")
  public void lawFirmAgreementEndWarning() {
    //
   List<LawFirmAgreementDTO> dtoList = contractInfoMapper.lawFirmAgreementEndAll();
    SysOrgPageRequest sysOrgRequest = new SysOrgPageRequest();
    sysOrgRequest.setOrgName("资产保全部");
    Result<List<SysOrgDTO>> all = sysOrgClient.findAll(1, 10000,sysOrgRequest);
    Set<Long> followUp = new HashSet<>();
    if (CollectionUtils.isEmpty(dtoList)){
     return;
   }
   for (LawFirmAgreementDTO dto : dtoList) {
     if (all.succeedWithData()){
       followUp = all.getData().stream().map(SysOrgDTO::getDeptLeader).collect(Collectors.toSet());
     }
     String contractFollowUp = dto.getFollowUp();
     if (StringUtils.isNotEmpty(contractFollowUp)){
       String[] split = contractFollowUp.split(",");
       for (String s : split) {
         followUp.add(Long.valueOf(s));
       }
     }
     String title = "律所协议到期提醒";
     String content = "合同《"+dto.getContractName() + "》与律所:" +dto.getLawFirmName()+"协议90天后到期,请及时跟进！";
     addMessage(dto.getId(), BillTypeEnum.CONTRACT_INFO.getKey(),
             title, content,
             followUp.stream()
                     .map(String::valueOf)
                     .collect(Collectors.joining(",")), DictEnum.MESSAGE_TYPE_009.getKey(), ""
     );
     followUp.clear();
   }
  }
}
