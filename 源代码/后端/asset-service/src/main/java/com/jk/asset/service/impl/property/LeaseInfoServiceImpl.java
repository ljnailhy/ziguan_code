package com.jk.asset.service.impl.property;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.FlowBillEnum;
import com.jk.asset.mapper.property.LeaseInfoMapper;
import com.jk.asset.model.dto.property.LeaseInfoDTO;
import com.jk.asset.model.entity.property.IntermediaryCustomerLead;
import com.jk.asset.model.entity.property.LeaseInfo;
import com.jk.asset.model.entity.property.PropertyInfo;
import com.jk.asset.model.entity.property.PropertyRightInfo;
import com.jk.asset.model.request.page.property.LeaseInfoPageRequest;
import com.jk.asset.model.request.property.IntermediaryCustomerLeadRequest;
import com.jk.asset.model.request.property.LeaseInfoRequest;
import com.jk.asset.model.request.property.PropertyBillRequest;
import com.jk.asset.model.request.property.PropertyInfoRequest;
import com.jk.asset.model.request.property.PropertyRightInfoRequest;
import com.jk.asset.service.handler.BillContractHandler;
import com.jk.asset.service.handler.HangNetworkInfoHandler;
import com.jk.asset.service.handler.property.DocumentIntermediaryHandler;
import com.jk.asset.service.handler.property.IntermediaryCustomerLeadHandler;
import com.jk.asset.service.handler.property.LeaseInfoHandler;
import com.jk.asset.service.handler.property.LeasePaymentCycleHandler;
import com.jk.asset.service.handler.property.PropertyBillHandler;
import com.jk.asset.service.handler.property.PropertyInfoHandler;
import com.jk.asset.service.handler.property.PropertyRightInfoHandler;
import com.jk.asset.service.property.LeaseInfoService;
import com.jk.common.constant.PlatformConstant;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryDTO;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.service.client.ProcessClient;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.utils.PlatformFileUtils;
import com.jk.service.utils.PlatformUserUtils;
import com.jk.workflow.constant.ProcessConstants;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 租赁信息表接口实现类
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
@RestController
@Slf4j
@Api(tags = "租赁信息表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LeaseInfoServiceImpl implements LeaseInfoService {

  private final LeaseInfoHandler handler;
  private final LeaseInfoMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final PropertyBillHandler propertyBillHandler;
  private final DocumentIntermediaryHandler documentIntermediaryHandler;
  private final LeasePaymentCycleHandler leasePaymentCycleHandler;
  private final BillContractHandler billContractHandler;
  private final ProcessClient processClient;
  private final SysDictionaryClient dictionaryClient;
  private final PropertyInfoHandler propertyInfoHandler;
  private final PlatformUserUtils platformUserUtils;
  private final HangNetworkInfoHandler hangNetworkInfoHandler;
  private final IntermediaryCustomerLeadHandler intermediaryCustomerLeadHandler;
  private final PropertyRightInfoHandler propertyRightInfoHandler;

  @Override
  public Result<LeaseInfoDTO> add(LeaseInfoRequest request) {
    if (null == request || request.unverified() || ObjectUtils.isEmpty(request.getPropertyBillRequestList())) {
      String message = "租赁信息表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    LeaseInfo leaseInfo = PlatformMapUtils.getInstance().map(request, LeaseInfo.class);
    handler.save(leaseInfo);
    // 产权信息
    List<PropertyRightInfoRequest> propertyRightInfoRequests = request.getPropertyRightInfoRequests();
    if (CollectionUtils.isNotEmpty(propertyRightInfoRequests)){
      propertyRightInfoRequests.forEach(item -> {
        item.setDoId(leaseInfo.getId());
        item.setDoType(BillTypeEnum.LEASE_INFO.getKey());
      });
      List<Long> parentId = propertyRightInfoRequests.stream().map(PropertyRightInfoRequest::getParentId).collect(Collectors.toList());
      List<PropertyRightInfo> propertyRightInfos = propertyRightInfoHandler.listByIds(parentId);
      List<PropertyInfo> propertyInfos = propertyInfoHandler.listByIds(propertyRightInfos.stream().map(PropertyRightInfo::getPropertyInfoId).collect(Collectors.toList()));
      propertyInfos.forEach(item -> item.setPropertyState(PropertyInfoRequest.PropertyStateEnum.LEASED));
      propertyRightInfoHandler.saveBatch(PlatformMapUtils.getInstance().mapAsList(propertyRightInfoRequests, PropertyRightInfo.class));
    }
    //  资产和单据关联处理
    propertyBillHandler.batchAddOrUpdatePropertyBillHandler(request.getPropertyBillRequestList(),leaseInfo.getId(),BillTypeEnum.LEASE_INFO.getKey());

    //  中介信息
    documentIntermediaryHandler.batchAddOrUpdateDocumentIntermediary(request.getDocumentIntermediaryRequestList(),leaseInfo.getId(),BillTypeEnum.LEASE_INFO.getKey());

    //  缴费周期
    leasePaymentCycleHandler.batchAddOrUpdateDocumentIntermediary(request.getLeasePaymentCycleRequestList(),leaseInfo.getId(),BillTypeEnum.LEASE_INFO.getKey());

    //  合同关联
    billContractHandler.batchAddOrUpdateDocumentIntermediary(request.getBillContractRequestList(),leaseInfo.getId(),BillTypeEnum.LEASE_INFO.getKey());

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),leaseInfo.getId(), BillTypeEnum.LEASE_INFO.getKey());

    LeaseInfoDTO dto = new LeaseInfoDTO();
    dto.setId(leaseInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<LeaseInfoDTO> update(LeaseInfoRequest request) {
    if (null == request || ObjectUtils.isEmpty(request.getPropertyBillRequestList())) {
      String message = "租赁信息表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    LeaseInfo leaseInfo = PlatformMapUtils.getInstance().map(request, LeaseInfo.class);
    handler.saveOrUpdate(leaseInfo);

    if (ObjectUtils.isNotEmpty(request.getLessee())) {
      List<PropertyInfo> list = Lists.newArrayList();
      for (PropertyBillRequest propertyBill : request.getPropertyBillRequestList()) {
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setPropertyState(PropertyInfoRequest.PropertyStateEnum.LEASED);
        propertyInfo.setId(propertyBill.getPropertyId());
        list.add(propertyInfo);
      }
      propertyInfoHandler.saveOrUpdateBatch(list);
    }
    List<IntermediaryCustomerLeadRequest> intermediaryCustomerLeadRequestList = request.getIntermediaryCustomerLeadRequestList();
    if (CollectionUtils.isNotEmpty(intermediaryCustomerLeadRequestList)) {
      intermediaryCustomerLeadRequestList.forEach(item -> {
        item.setDoId(leaseInfo.getId());
        item.setDoType(BillTypeEnum.LEASE_INFO.getKey());
      });
      intermediaryCustomerLeadHandler.saveOrUpdateBatch(PlatformMapUtils.getInstance().mapAsList(intermediaryCustomerLeadRequestList, IntermediaryCustomerLead.class));
      List<Long> deleteIdList = intermediaryCustomerLeadRequestList.stream()
              .filter(item -> ObjectUtils.isNotEmpty(item.getOperateType()))
              .filter(item -> item.getOperateType().getKey().equals(OperationTypeEnum.DELETE.getKey()))
              .map(IntermediaryCustomerLeadRequest::getId).collect(Collectors.toList());
      if (CollectionUtils.isNotEmpty(deleteIdList)){
        intermediaryCustomerLeadHandler.removeByIds(deleteIdList);
      }
    }
    //  租赁产权
    List<PropertyRightInfoRequest> propertyRightInfoRequests = request.getPropertyRightInfoRequests();
    if (CollectionUtils.isNotEmpty(propertyRightInfoRequests)){
      propertyRightInfoRequests.forEach(item -> {
        item.setDoId(leaseInfo.getId());
        item.setDoType(BillTypeEnum.LEASE_INFO.getKey());
      });
      List<Long> parentId = propertyRightInfoRequests.stream().map(PropertyRightInfoRequest::getParentId).collect(Collectors.toList());
      Map<Long, List<PropertyRightInfoRequest>> collect = propertyRightInfoRequests.stream().collect(Collectors.groupingBy(PropertyRightInfoRequest::getParentId));
      List<PropertyRightInfo> propertyRightInfos = propertyRightInfoHandler.listByIds(parentId);
      List<PropertyInfo> propertyInfos = propertyInfoHandler.listByIds(propertyRightInfos.stream().map(PropertyRightInfo::getPropertyInfoId).collect(Collectors.toList()));
//      propertyInfos.forEach(item -> item.setPropertyState(PropertyInfoRequest.PropertyStateEnum.LEASED));
      propertyInfos.forEach(item -> item.setPropertyState(PropertyInfoRequest.PropertyStateEnum.PART_LEASE));
      propertyRightInfoHandler.saveOrUpdateBatch(PlatformMapUtils.getInstance().mapAsList(propertyRightInfoRequests, PropertyRightInfo.class));
      for (PropertyRightInfo propertyRightInfo : propertyRightInfos) {
        if (collect.containsKey(propertyRightInfo.getId())) {
          propertyRightInfo.setAssetUnitState(collect.get(propertyRightInfo.getId()).get(0).getAssetUnitState());
        }
      }
      propertyRightInfoHandler.updateBatchById(propertyRightInfos);
      List<Long> deleteIdList = propertyRightInfoRequests.stream()
              .filter(item -> ObjectUtils.isNotEmpty(item.getOperateType()))
              .filter(item -> item.getOperateType().getKey().equals(OperationTypeEnum.DELETE.getKey()))
              .map(PropertyRightInfoRequest::getId).collect(Collectors.toList());
      if (CollectionUtils.isNotEmpty(deleteIdList)){
        propertyRightInfoHandler.removeByIds(deleteIdList);
      }
    }
    //  资产和单据关联处理
    propertyBillHandler.batchAddOrUpdatePropertyBillHandler(request.getPropertyBillRequestList(),leaseInfo.getId(),BillTypeEnum.LEASE_INFO.getKey());

    //  中介信息
    documentIntermediaryHandler.batchAddOrUpdateDocumentIntermediary(request.getDocumentIntermediaryRequestList(),leaseInfo.getId(),BillTypeEnum.LEASE_INFO.getKey());

    //  处理法拍过程
    hangNetworkInfoHandler.batchAddOrUpdateHangNetworkInfo(request.getHangNetworkInfoRequestList(),leaseInfo.getId(),BillTypeEnum.LEASE_INFO.getKey());

    //  缴费周期
    leasePaymentCycleHandler.batchAddOrUpdateDocumentIntermediary(request.getLeasePaymentCycleRequestList(),leaseInfo.getId(),BillTypeEnum.LEASE_INFO.getKey());

    //  合同关联
    billContractHandler.batchAddOrUpdateDocumentIntermediary(request.getBillContractRequestList(),leaseInfo.getId(),BillTypeEnum.LEASE_INFO.getKey());

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),leaseInfo.getId(), BillTypeEnum.LEASE_INFO.getKey());

    LeaseInfoDTO dto = new LeaseInfoDTO();
    dto.setId(leaseInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<LeaseInfoDTO> submit(LeaseInfoRequest request) {
    request.setFlowState(ProcessStatus.running);
    Result<LeaseInfoDTO> updateResult = update(request);
    if (null == updateResult || !updateResult.succeedWithData()) {
      return Result.error("提交失败");
    }

    //  提交流程资产标签新增租赁中
    Long state = null;
    try {
      Result<SysDictionaryDTO> byCode = dictionaryClient.findByCode(DictEnum.PROPERTY_TAG.getKey());
      if (byCode.getCode().equals(0)) {
        Optional<Long> first = byCode.getData().getItems()
            .stream()
            .filter(item -> DictEnum.PROPERTY_TAG_001.getKey().equals(item.getItemCode()))
            .map(SysDictionaryItemDTO::getId)
            .findFirst();
        state = first.get();
      }
    }catch (Exception e){
      String message = "查询资产标签失败，请联系管理员";
      log.info("{} code {}", message, DictEnum.PROPERTY_TAG_001.getKey());
      return Result.error(message);
    }

    List<Long> propertyIds = request.getPropertyBillRequestList()
        .stream()
        .map(item -> item.getPropertyId())
        .collect(Collectors.toList());

    List<PropertyInfo> list = propertyInfoHandler.list(new LambdaUpdateWrapper<PropertyInfo>().in(PropertyInfo::getId, propertyIds));

    List<PropertyInfo> updatePropertyInfoList = Lists.newArrayList();
    for (PropertyInfo propertyInfo : list) {
      if (StringUtils.isBlank(propertyInfo.getPropertyTag()) || propertyInfo.getPropertyTag().indexOf(state.toString()) == -1){
        propertyInfo.setPropertyTag(propertyInfo.getPropertyTag()+","+state.toString());
        updatePropertyInfoList.add(propertyInfo);
      }
    }

    propertyInfoHandler.updateBatchById(updatePropertyInfoList);


    //  【资产租赁】+申请人+提交资产租赁方案申请，
    String flowTitle = "【资产租赁】%s提交资产租赁方案申请";
    //  当前人
    SysUserDTO currentUser = platformUserUtils.getCurrentUser();
    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, updateResult.getData().getId());
    variables.put(ProcessConstants.VARIABLE_OBJECT,String.format(flowTitle,currentUser.getUsername()));
    processClient.startProcess(FlowBillEnum.LEASE_INFO.getKey(), variables);

    return Result.success();
  }

  @Override
  public Result<LeaseInfoDTO> writeBackProject(Long id) {

//    List<PropertyBill> list = propertyBillHandler.list(new LambdaUpdateWrapper<PropertyBill>()
//        .eq(PropertyBill::getDoId, id)
//        .eq(PropertyBill::getDoType, BillTypeEnum.OPERATION_INFO.getKey()));
//
//    for (PropertyBill propertyBill : list) {
//      propertyInfoHandler.update(new LambdaUpdateWrapper<PropertyInfo>()
//          .set(PropertyInfo::getPropertyState, PropertyInfoRequest.PropertyStateEnum.LEASED.getKey())
//          .eq(PropertyInfo::getId,propertyBill.getPropertyId()));
//    }
    return Result.success();
  }

  @Override
  public Result<LeaseInfoDTO> findById(Long id) {
    LeaseInfo leaseInfo = handler.getById(id);
    if (null == leaseInfo) {
      String message = "租赁信息表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    LeaseInfoDTO dto = PlatformMapUtils.getInstance().map(leaseInfo, LeaseInfoDTO.class);
    List<IntermediaryCustomerLead> intermediaryCustomerLeadList = intermediaryCustomerLeadHandler
            .list(new LambdaQueryWrapper<IntermediaryCustomerLead>()
        .eq(IntermediaryCustomerLead::getDoId, id)
            .eq(IntermediaryCustomerLead::getDoType, BillTypeEnum.LEASE_INFO.getKey()));
    if (CollectionUtils.isNotEmpty(intermediaryCustomerLeadList)){
      dto.setIntermediaryCustomerLeadRequestList(PlatformMapUtils.getInstance().mapAsList(intermediaryCustomerLeadList, IntermediaryCustomerLeadRequest.class));
    }
    List<PropertyRightInfo> propertyRightInfoList = propertyRightInfoHandler.list(new LambdaQueryWrapper<PropertyRightInfo>()
            .eq(PropertyRightInfo::getDoId, id).eq(PropertyRightInfo::getDoType,BillTypeEnum.LEASE_INFO.getKey()));
    if (CollectionUtils.isNotEmpty(propertyRightInfoList)){
      dto.setPropertyRightInfoRequests(PlatformMapUtils.getInstance().mapAsList(propertyRightInfoList, PropertyRightInfoRequest.class));
    }
    return Result.success(dto);
  }

  @Override
  public Result<List<LeaseInfoDTO>> findAll(Integer current, Integer size, LeaseInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<LeaseInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<LeaseInfoDTO> dtoList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(dtoList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
//    List<LeaseInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, LeaseInfoDTO.class);
//    for (LeaseInfoDTO dto : dtoList) {
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