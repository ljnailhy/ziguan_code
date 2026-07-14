package com.jk.asset.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.constant.AssertConstants;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.CollectionAscriptionEnum;
import com.jk.asset.enums.CollectionSignEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.FlowBillEnum;
import com.jk.asset.mapper.RecoveryPaymentCollectionMapper;
import com.jk.asset.model.dto.RecoveryPaymentCollectionDTO;
import com.jk.asset.model.entity.LawFirmInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RecoveryPaymentCollection;
import com.jk.asset.model.entity.RecoveryPaymentCollectionDetail;
import com.jk.asset.model.request.ImportPaymentCollectionRequest;
import com.jk.asset.model.request.RecoveryPaymentCollectionDetailRequest;
import com.jk.asset.model.request.RecoveryPaymentCollectionRequest;
import com.jk.asset.model.request.page.RecoveryPaymentCollectionPageRequest;
import com.jk.asset.service.RecoveryPaymentCollectionService;
import com.jk.asset.service.handler.LawFirmInfoHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionDetailHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionHandler;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.constant.PlatformConstant;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 回款表接口实现类
 *
 * @author wangtao
 * @since 2024-06-20 18:53:02
 */
@RestController
@Slf4j
@Api(tags = "回款表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryPaymentCollectionServiceImpl implements RecoveryPaymentCollectionService {

  private final RecoveryPaymentCollectionHandler handler;
  private final RecoveryPaymentCollectionMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final RecoveryPaymentCollectionDetailHandler detailHandler;
  private final ProjectInfoHandler projectInfoHandler;
  private final LawFirmInfoHandler lawFirmInfoHandler;
  private final SysDictionaryClient sysDictionaryClient;
  private final ProcessClient processClient;
  private final AssetUserLimitsUtils assetUserLimitsUtils;
  private final RecoveryPaymentCollectionHandler recoveryPaymentCollectionHandler;

  @Override
  public Result<RecoveryPaymentCollectionDTO> add(RecoveryPaymentCollectionRequest request) {
    if (null == request || request.unverified()) {
      String message = "回款表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    RecoveryPaymentCollection collection = PlatformMapUtils.getInstance().map(request, RecoveryPaymentCollection.class);
    String projectName = projectInfoHandler.getById(collection.getProjectId()).getProjectName();
    collection.setProjectName(projectName);
    if (ObjectUtils.isEmpty(collection.getCollectionStatus())){
      SysDictionaryItemDTO state = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
              .getData()
              .getItems()
              .stream()
              .filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_001.getKey()))
              .findAny().get();
      collection.setCollectionStatus(state.getId());
    }
    // 明细数据
    List<RecoveryPaymentCollectionDetailRequest> detailRequests = request.getPaymentCollectionDetailRequests();
    Long lawyerId = collection.getLawyerId();
    if (!ObjectUtils.isEmpty(lawyerId)) {
      LawFirmInfo byId = lawFirmInfoHandler.getById(lawyerId);
      if (!ObjectUtils.isEmpty(byId)) {
        collection.setLawyerName(byId.getName());
      }
    }
    handler.save(collection);
    RecoveryPaymentCollectionDTO dto = new RecoveryPaymentCollectionDTO();
    dto.setId(collection.getId());
    if (!ObjectUtils.isEmpty(request.getFileRequests())){
      platformFileUtils.batchAddFile(request.getFileRequests(), dto.getId(), BillTypeEnum.RECOVERY_PAYMENT_COLLECTION.getKey());
    }
    if (ObjectUtils.isEmpty(detailRequests)){
      return Result.success(dto);
    }
    // 新增明细表
    detailRequests.forEach(item ->
            {
              item.setPaymentCollectionId(dto.getId());
              RecoveryPaymentCollectionDetail detail = PlatformMapUtils.getInstance().map(item, RecoveryPaymentCollectionDetail.class);
              detailHandler.save(detail);
              List<SysFileRequest> fileRequests = item.getFileRequests();
              platformFileUtils.batchAddFile(fileRequests,detail.getId(), BillTypeEnum.RECOVERY_PAYMENT_COLLECTION_DETAIL.getKey());
            }
    );
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryPaymentCollectionDTO> update(RecoveryPaymentCollectionRequest request) {
    if (null == request || null == request.getId()) {
      String message = "回款表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }
    RecoveryPaymentCollection collection = PlatformMapUtils.getInstance().map(request, RecoveryPaymentCollection.class);
    String projectName = projectInfoHandler.getById(collection.getProjectId()).getProjectName();
    collection.setProjectName(projectName);
    // 明细数据
    List<RecoveryPaymentCollectionDetailRequest> detailRequests = request.getPaymentCollectionDetailRequests();

      Long lawyerId = collection.getLawyerId();
    if (!ObjectUtils.isEmpty(lawyerId)) {
      LawFirmInfo byId = lawFirmInfoHandler.getById(lawyerId);
      if (!ObjectUtils.isEmpty(byId)) {
        collection.setLawyerName(byId.getName());
      }
    }
    handler.updateById(collection);
    RecoveryPaymentCollectionDTO recoveryPaymentCollectionDTO = new RecoveryPaymentCollectionDTO();
    recoveryPaymentCollectionDTO.setId(request.getId());

    if (!ObjectUtils.isEmpty(detailRequests)) {
      List<Long> removeIds = new ArrayList<>();
      for (RecoveryPaymentCollectionDetailRequest detailRequest : detailRequests) {
        if (Objects.nonNull(detailRequest.getOperateType()) && detailRequest.getOperateType().getKey().equals(OperationTypeEnum.DELETE.getKey())){
          removeIds.add(detailRequest.getId());
        }else {
          RecoveryPaymentCollectionDetail map = PlatformMapUtils.getInstance().map(detailRequest, RecoveryPaymentCollectionDetail.class);
          map.setPaymentCollectionId(request.getId());
          detailHandler.saveOrUpdate(map);
          platformFileUtils.batchUpdateFile(detailRequest.getFileRequests(),map.getId(), BillTypeEnum.RECOVERY_PAYMENT_COLLECTION_DETAIL.getKey());
        }
      }
      if (!removeIds.isEmpty()){
        this.detailHandler.removeByIds(removeIds);
        platformFileUtils.deleteFileByDoIds(removeIds, BillTypeEnum.RECOVERY_PAYMENT_COLLECTION_DETAIL.getKey());
      }
    }
    if (!ObjectUtils.isEmpty(request.getFileRequests())) {
      platformFileUtils.batchAddFile(request.getFileRequests(), request.getId(), BillTypeEnum.RECOVERY_PAYMENT_COLLECTION.getKey());
    }
    return Result.success(recoveryPaymentCollectionDTO);
  }

  @Override
  public Result<RecoveryPaymentCollectionDTO> submit(RecoveryPaymentCollectionRequest request) {
    request.setFlowState(ProcessStatus.running);
    Result<RecoveryPaymentCollectionDTO> result;
    if (ObjectUtils.isEmpty(request.getId())){
      result= add(request);
    }else {
      result = update(request);
    }
    if (null == result || !result.succeedWithData()) {
      return Result.error("提交失败");
    }
    ProjectInfo projectInfo = projectInfoHandler.getById(request.getProjectId());

    //  【回款登记】+项目名称+于+回款时间+提交回款登记，
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
    String flowTitle = "【回款登记】%s于%s提交回款登记";

    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, result.getData().getId());
    variables.put(ProcessConstants.VARIABLE_OBJECT, String.format(flowTitle,projectInfo.getProjectName(),formatter.format(request.getCollectionDate())));
    processClient.startProcess(FlowBillEnum.RECOVERY_PAYMENT_COLLECTION.getKey(), variables);

    return Result.success();
  }

  @Override
  public Result<RecoveryPaymentCollectionDTO> findById(Long id) {
    RecoveryPaymentCollection recoveryPaymentCollection = handler.getById(id);
    if (null == recoveryPaymentCollection) {
      String message = "回款表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryPaymentCollectionDTO dto = PlatformMapUtils.getInstance().map(recoveryPaymentCollection, RecoveryPaymentCollectionDTO.class);
    if (ObjectUtils.isEmpty(dto)){
      return Result.success(dto);
    }
    // 查询明细
    LambdaQueryWrapper<RecoveryPaymentCollectionDetail> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(RecoveryPaymentCollectionDetail::getPaymentCollectionId, dto.getId());
    List<RecoveryPaymentCollectionDetail> list = detailHandler.list(wrapper);
   if (!list.isEmpty()){
     dto.setPaymentCollectionDetailRequests(PlatformMapUtils.getInstance().mapAsList(list, RecoveryPaymentCollectionDetailRequest.class));
   }
    return Result.success(dto);
  }

  @Override
  public Result<Map<Long, BigDecimal>> findByProjectId(List<Long> projectIds) {
    List<RecoveryPaymentCollection> list = handler.list(new LambdaQueryWrapper<RecoveryPaymentCollection>()
        .in(RecoveryPaymentCollection::getProjectId, projectIds));

    List<RecoveryPaymentCollection> recoveryPaymentCollectionList = Lists.newArrayList();
    for (RecoveryPaymentCollection recoveryPaymentCollection : list) {
      Result<ProcessStatus> statusResult = processClient.getStatusByDoId(recoveryPaymentCollection.getId());
      if (null != statusResult && statusResult.succeedWithData() && statusResult.getData().getKey().equals(ProcessStatus.completed.getKey())) {
        recoveryPaymentCollectionList.add(recoveryPaymentCollection);
      }
    }

    Map<Long, BigDecimal> map = recoveryPaymentCollectionList.stream()
        .collect(Collectors.groupingBy(
            RecoveryPaymentCollection::getProjectId,
            Collectors.reducing(BigDecimal.ZERO, RecoveryPaymentCollection::getCollectionAmount, BigDecimal::add)
        ));

    return Result.success(map);
  }

  @Override
  public Result<List<RecoveryPaymentCollectionDTO>> findAll(Integer current, Integer size, RecoveryPaymentCollectionPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RecoveryPaymentCollection> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
    request.setOrgUserIds(userLimitsOrg);
    List<RecoveryPaymentCollection> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }

    // Step3：获取分页数据
    List<RecoveryPaymentCollectionDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryPaymentCollectionDTO.class);
    if (dtoList.isEmpty()){
      return Result.success(dtoList, PageFactory.page(page));
    }
    // 明细
    LambdaQueryWrapper<RecoveryPaymentCollectionDetail> wrapper = new LambdaQueryWrapper<>();
    wrapper.in(RecoveryPaymentCollectionDetail::getPaymentCollectionId, dtoList.stream().map(RecoveryPaymentCollectionRequest::getId).collect(Collectors.toList()));
    List<RecoveryPaymentCollectionDetail> list = detailHandler.list(wrapper);
    if (!list.isEmpty()) {
      Map<Long, List<RecoveryPaymentCollectionDetail>> collect = list.stream().collect(Collectors.groupingBy(RecoveryPaymentCollectionDetail::getPaymentCollectionId));
      dtoList.forEach(a -> {
                if (!ObjectUtils.isEmpty(collect.get(a.getId()))) {
                  a.setPaymentCollectionDetailRequests(PlatformMapUtils.getInstance().mapAsList(collect.get(a.getId()), RecoveryPaymentCollectionDetailRequest.class));
                }
              }
      );
    }


    List<RecoveryPaymentCollectionDTO> resList = new ArrayList<>();
    ProcessStatus processStatus = request.getProcessStatus();
    for (RecoveryPaymentCollectionDTO dto : dtoList) {
      Long id = dto.getId();
      // 流程状态
      Result<ProcessStatus> statusResult = processClient.getStatusByDoId(id);
      if (null != statusResult && statusResult.succeedWithData()) {
        dto.setProcessStatus(statusResult.getData());
        if (!ObjectUtils.isEmpty(processStatus) && statusResult.getData().getKey().equals(processStatus.getKey())){
          resList.add(dto);
        }
      }
    }
    if (!ObjectUtils.isEmpty(processStatus)){
      return Result.success(resList, PageFactory.page(page));
    }
    return Result.success(dtoList, PageFactory.page(page));
  }

  @Override
  public Result<RecoveryPaymentCollectionDTO> writeBackProject(Long id) {
    RecoveryPaymentCollection collection = handler.getById(id);
    if (null == collection) {
      String message = "付款	单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    Long state = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
            .getData()
            .getItems()
            .stream()
            .filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey()))
            .findAny().get().getId();
    collection.setCollectionStatus(state);
    mapper.updateById(collection);
    return Result.success(PlatformMapUtils.getInstance().map(collection, RecoveryPaymentCollectionDTO.class));
  }

  @Override
  public Result<String> importData(MultipartFile file) throws ParseException {
    List<ImportPaymentCollectionRequest> importPaymentCollectionRequests;
    try {
      importPaymentCollectionRequests =   EasyExcel.read(file.getInputStream()).head(ImportPaymentCollectionRequest.class).autoCloseStream(false).sheet().headRowNumber(2).doReadSync();
    } catch (IOException e) {
      log.info("导入异常 ->{}", e.getMessage());
      throw new RuntimeException("导入数据格式错误");
    }
    if (importPaymentCollectionRequests.size() < 3) {
      return Result.error("导入数据为空");
    }
    if (!AssertConstants.IMPORT_COLLECTION.equals(importPaymentCollectionRequests.get(0).toString())){
      return Result.error("请使用标准模板导入");
    }
    List<ImportPaymentCollectionRequest> filteredList = importPaymentCollectionRequests.stream().skip(2).collect(Collectors.toList());
    if (!checkImportData(filteredList).isEmpty()) {
      return Result.error(checkImportData(filteredList));
    }
    List<RecoveryPaymentCollection> recoveryList = getCollectionData(filteredList);
    List<RecoveryPaymentCollection> sucessList = recoveryList.stream().filter(item -> item.getProjectId() != null).collect(Collectors.toList());
    List<RecoveryPaymentCollection> failList = recoveryList.stream().filter(item -> item.getProjectId() == null).collect(Collectors.toList());
    List<ImportPaymentCollectionRequest> failedImportList = filteredList.stream()
            .filter(request -> sucessList.stream()
                    .noneMatch(recovery -> recovery.getProjectName().equals(request.getProjectName())))
            .collect(Collectors.toList());
    Set<String> failedSet = failList.stream().map(RecoveryPaymentCollection::getProjectName).collect(Collectors.toSet());
    failedSet.addAll(failedImportList.stream().map(ImportPaymentCollectionRequest::getProjectName).collect(Collectors.toSet()));
    if (recoveryList.isEmpty()){
      return Result.success("导入成功");
    }
    recoveryPaymentCollectionHandler.saveBatch(sucessList);
    if (!failedSet.isEmpty()){
      return Result.error(202, String.join("\n", failedSet) + "\n导入失败");
    }
    return Result.success("导入成功");
  }

  private List<RecoveryPaymentCollection> getCollectionData(List<ImportPaymentCollectionRequest> filteredList) throws ParseException {
    List<RecoveryPaymentCollection> resultList = new ArrayList<>();
    CollectionSignEnum[] signEnums = CollectionSignEnum.values();
    CollectionAscriptionEnum[] ascriptionEnums = CollectionAscriptionEnum.values();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    SysDictionaryItemDTO collectionStatus002 = sysDictionaryClient.findByCode(DictEnum.COLLECTION_STATUS.getKey())
            .getData().getItems().stream().filter(item -> item.getItemCode().equals(DictEnum.COLLECTION_STATUS_002.getKey())).findAny().get();
    List<String> projectNameList = filteredList.stream().map(ImportPaymentCollectionRequest::getProjectName).distinct().collect(Collectors.toList());
    List<String> lawFirmNameList = filteredList.stream().map(ImportPaymentCollectionRequest::getLawyerName).distinct().collect(Collectors.toList());
    List<ProjectInfo> projectInfos = projectInfoHandler.list(new LambdaQueryWrapper<ProjectInfo>().in(ProjectInfo::getProjectName, projectNameList));
    List<LawFirmInfo> lawFirmInfos = lawFirmInfoHandler.list(new LambdaQueryWrapper<LawFirmInfo>().in(LawFirmInfo::getName, lawFirmNameList));
    // 只要查到size 为1的
    if (CollectionUtils.isEmpty(projectInfos)){
      return new ArrayList<>();
    }
    if (CollectionUtils.isEmpty(lawFirmInfos)){
      return new ArrayList<>();
    }
    Map<String, List<LawFirmInfo>> passLawFirm = lawFirmInfos.stream().collect(Collectors.groupingBy(LawFirmInfo::getName));
    Map<String, List<ProjectInfo>> passProject = projectInfos.stream()
            .collect(Collectors.groupingBy(ProjectInfo::getProjectName)).entrySet().stream()
            .filter(entry -> entry.getValue().size() == 1)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    if (ObjectUtils.isEmpty(passProject)){
      return new ArrayList<>();
    }
    for (ImportPaymentCollectionRequest request : filteredList) {
      RecoveryPaymentCollection recovery = new RecoveryPaymentCollection();
      recovery.setCollectionAmount(new BigDecimal(request.getCollectionAmount()));
      recovery.setCollectionDate(sdf.parse(request.getCollectionDate()));
      for (CollectionSignEnum value : signEnums) {
        if (value.getValue().equals(request.getCollectionSign())) {
          recovery.setCollectionSign(value.getKey());
        }
      }

      for (CollectionAscriptionEnum ascriptionEnum : ascriptionEnums) {
        if (ascriptionEnum.getValue().equals(request.getCollectionAscription())) {
          recovery.setCollectionAscription(ascriptionEnum.getKey());
        }
      }
      recovery.setIsCollectionHistorical("是".equals(request.getIsCollectionHistorical()));
      recovery.setCollectionType("自主追偿".equals(request.getCollectionDetailType()) ? "OWN" : "ENTRUST");
      recovery.setCollectionSummary(request.getCollectionSummary());
      recovery.setCollectionStatus(collectionStatus002.getId());

      if (passLawFirm.containsKey(request.getLawyerName())){
        recovery.setLawyerId(passLawFirm.get(request.getLawyerName()).get(0).getId());
        recovery.setLawyerName(passLawFirm.get(request.getLawyerName()).get(0).getName());
      }
      if (passProject.containsKey(request.getProjectName())){
        recovery.setProjectId(passProject.get(request.getProjectName()).get(0).getId());
        recovery.setProjectName(passProject.get(request.getProjectName()).get(0).getProjectName());
        resultList.add(recovery);
      }

    }
      return resultList;
    }

  private String checkImportData(List<ImportPaymentCollectionRequest> filteredList) {
    // 校验必填参数
    int lineNumber = 5;
      for (ImportPaymentCollectionRequest request : filteredList) {
        if (ObjectUtils.isEmpty(request.getProjectName())) {
          return "第" + lineNumber + "行必填项 项目名称名称 为空";
        }
        if (ObjectUtils.isEmpty(request.getCollectionAmount())) {
          return "第" + lineNumber + "行必填项 回款金额(元) 为空";
        }
        if (ObjectUtils.isEmpty(request.getCollectionDate())) {
          return "第" + lineNumber + "行必填项 回款时间 为空";
        }
        if (ObjectUtils.isEmpty(request.getCollectionSign())) {
          return "第" + lineNumber + "行必填项 回款标记 为空";
        }
        if (ObjectUtils.isEmpty(request.getCollectionAscription())) {
          return "第" + lineNumber + "行必填项 回款归属 为空";
        }
        if (ObjectUtils.isEmpty(request.getCollectionDetailType())) {
          return "第" + lineNumber + "行必填项 回款类型 为空";
        }
        if (ObjectUtils.isEmpty(request.getIsCollectionHistorical())) {
          return "第" + lineNumber + "行必填项 是否历史代偿 为空";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
          sdf.parse(request.getCollectionDate());
        } catch (ParseException e) {
          return "第" + lineNumber + "行必填项 代偿日期格式异常";
        }
        lineNumber++;
      }
   return "";
  }
}