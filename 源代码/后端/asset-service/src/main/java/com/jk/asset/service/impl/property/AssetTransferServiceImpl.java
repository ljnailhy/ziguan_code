package com.jk.asset.service.impl.property;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.FlowBillEnum;
import com.jk.asset.mapper.property.AssetTransferMapper;
import com.jk.asset.model.dto.property.AssetTransferDTO;
import com.jk.asset.model.entity.property.AssetIncomeDistribution;
import com.jk.asset.model.entity.property.AssetTransfer;
import com.jk.asset.model.entity.property.IntermediaryCustomerLead;
import com.jk.asset.model.entity.property.PropertyInfo;
import com.jk.asset.model.entity.property.PropertyRightInfo;
import com.jk.asset.model.request.page.property.AssetTransferPageRequest;
import com.jk.asset.model.request.property.AssetIncomeDistributionRequest;
import com.jk.asset.model.request.property.AssetTransferRequest;
import com.jk.asset.model.request.property.IntermediaryCustomerLeadRequest;
import com.jk.asset.model.request.property.PropertyBillRequest;
import com.jk.asset.model.request.property.PropertyInfoRequest;
import com.jk.asset.model.request.property.PropertyRightInfoRequest;
import com.jk.asset.service.handler.BillContractHandler;
import com.jk.asset.service.handler.HangNetworkInfoHandler;
import com.jk.asset.service.handler.property.AssetIncomeDistributionHandler;
import com.jk.asset.service.handler.property.AssetTransferHandler;
import com.jk.asset.service.handler.property.DocumentIntermediaryHandler;
import com.jk.asset.service.handler.property.IntermediaryCustomerLeadHandler;
import com.jk.asset.service.handler.property.PropertyBillHandler;
import com.jk.asset.service.handler.property.PropertyInfoHandler;
import com.jk.asset.service.handler.property.PropertyRightInfoHandler;
import com.jk.asset.service.property.AssetTransferService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 资产转让接口实现类
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:13
 */
@RestController
@Slf4j
@Api(tags = "资产转让接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssetTransferServiceImpl implements AssetTransferService {

  private final AssetTransferHandler handler;
  private final AssetTransferMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final PropertyBillHandler propertyBillHandler;
  private final DocumentIntermediaryHandler documentIntermediaryHandler;
  private final BillContractHandler billContractHandler;
  private final HangNetworkInfoHandler hangNetworkInfoHandler;
  private final ProcessClient processClient;
  private final SysDictionaryClient dictionaryClient;
  private final PropertyInfoHandler propertyInfoHandler;
  private final AssetIncomeDistributionHandler assetIncomeDistributionHandler;
  private final PlatformUserUtils platformUserUtils;
  private final PropertyRightInfoHandler propertyRightInfoHandler;
  @Autowired
  private IntermediaryCustomerLeadHandler intermediaryCustomerLeadHandler;

  @Override
  public Result<AssetTransferDTO> add(AssetTransferRequest request) {
    if (null == request || request.unverified() || ObjectUtils.isEmpty(request.getPropertyBillRequestList())) {
      String message = "资产转让新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    AssetTransfer assetTransfer = PlatformMapUtils.getInstance().map(request, AssetTransfer.class);
    handler.save(assetTransfer);

    //  资产和单据关联处理
    propertyBillHandler.batchAddOrUpdatePropertyBillHandler(request.getPropertyBillRequestList(),assetTransfer.getId(), BillTypeEnum.ASSET_TRANSFER.getKey());

    //  中介信息
    documentIntermediaryHandler.batchAddOrUpdateDocumentIntermediary(request.getDocumentIntermediaryRequestList(),assetTransfer.getId(),BillTypeEnum.ASSET_TRANSFER.getKey());

    //  处理客户与中介关系
    intermediaryCustomerLeadHandler.batchAddOrUpdateIntermediaryCustomerLead(
            request.getIntermediaryCustomerLeadRequestList(),
            assetTransfer.getId(),
            BillTypeEnum.ASSET_TRANSFER.getKey()
    );

    //  处理法拍过程
    hangNetworkInfoHandler.batchAddOrUpdateHangNetworkInfo(request.getHangNetworkInfoRequestList(),assetTransfer.getId(),BillTypeEnum.ASSET_TRANSFER.getKey());

    //  合同关联
    billContractHandler.batchAddOrUpdateDocumentIntermediary(request.getBillContractRequestList(),assetTransfer.getId(),BillTypeEnum.ASSET_TRANSFER.getKey());

    //  处理资产分摊
    assetIncomeDistributionHandler.batchAddOrUpdateDocumentIntermediary(request.getAssetIncomeDistributionRequestList(),assetTransfer.getId(),BillTypeEnum.ASSET_TRANSFER.getKey());

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),assetTransfer.getId(), BillTypeEnum.ASSET_TRANSFER.getKey());

    AssetTransferDTO dto = new AssetTransferDTO();
    dto.setId(assetTransfer.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    Result<ProcessStatus> statusResult = processClient.getStatusByDoId(id);
    if (null != statusResult && statusResult.succeedWithData()) {
      String message = "转让信息删除失败 已提交数据不能删除";
      log.info("{} processStatus {}", message, statusResult.getData().getValue());
      return Result.error(message);
    }
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<AssetTransferDTO> update(AssetTransferRequest request) {
    if (null == request || ObjectUtils.isEmpty(request.getPropertyBillRequestList())) {
      String message = "资产转让修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }
    // 检验多个中介不能推荐相关客户（暂不控制）
//    List<DocumentIntermediaryRequest> documentIntermediaryRequestList = request.getDocumentIntermediaryRequestList();
//    List<Long> customerIds = new ArrayList<>();
//    for(DocumentIntermediaryRequest documentIntermediaryRequest : documentIntermediaryRequestList){
//      List<IntermediaryCustomerLeadRequest> requests = documentIntermediaryRequest.getIntermediaryCustomerLeadRequestList();
//      for (IntermediaryCustomerLeadRequest customerLeadRequest : requests){
//        customerIds.add(customerLeadRequest.getCustomId());
//      }
//    }
//    if(ObjectUtils.isNotEmpty(customerIds) && customerIds.stream().distinct().count() != customerIds.size()){
//      String message = "存在多个中介推荐同一客户，请核实后在提交！";
//      log.info("{} request {}", message, request);
//      return Result.error(message);
//    }

    AssetTransfer assetTransfer = PlatformMapUtils.getInstance().map(request, AssetTransfer.class);
    handler.saveOrUpdate(assetTransfer);

    // 反写资产状态
    if (ObjectUtils.isNotEmpty(request.getCustomId())) {
      List<PropertyInfo> list = Lists.newArrayList();
      for (PropertyBillRequest propertyBill : request.getPropertyBillRequestList()) {
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setPropertyState(PropertyInfoRequest.PropertyStateEnum.PART_TRANSFER);
        propertyInfo.setId(propertyBill.getPropertyId());
        list.add(propertyInfo);
      }
      propertyInfoHandler.saveOrUpdateBatch(list);
    }

    //  资产和单据关联处理
    propertyBillHandler.batchAddOrUpdatePropertyBillHandler(request.getPropertyBillRequestList(),assetTransfer.getId(), BillTypeEnum.ASSET_TRANSFER.getKey());

    //  中介信息
    documentIntermediaryHandler.batchAddOrUpdateDocumentIntermediary(request.getDocumentIntermediaryRequestList(),assetTransfer.getId(),BillTypeEnum.ASSET_TRANSFER.getKey());

    //  处理客户与中介关系
    intermediaryCustomerLeadHandler.batchAddOrUpdateIntermediaryCustomerLead(
            request.getIntermediaryCustomerLeadRequestList(),
            assetTransfer.getId(),
            BillTypeEnum.ASSET_TRANSFER.getKey()
    );

    // 处理转让产权信息
    List<PropertyRightInfoRequest> propertyRightInfoRequestList1 = request.getPropertyRightInfoRequests();
    if (ObjectUtils.isNotEmpty(propertyRightInfoRequestList1)) {
      List<Long> ids = new ArrayList<>();
      List<PropertyRightInfo> propertyRightInfoList = new ArrayList<>();
      propertyRightInfoRequestList1.forEach(item -> {
        item.setDoId(assetTransfer.getId());
        item.setDoType(BillTypeEnum.ASSET_TRANSFER.getKey());
        PropertyRightInfo propertyRightInfo = PlatformMapUtils.getInstance().map(item, PropertyRightInfo.class);
        propertyRightInfoList.add(propertyRightInfo);
        if (Objects.nonNull(item.getOperateType()) && item.getOperateType() == OperationTypeEnum.DELETE){
          ids.add(item.getId());
        }
      });
      if (ObjectUtils.isNotEmpty(propertyRightInfoList)) {
        propertyRightInfoHandler.saveOrUpdateBatch(propertyRightInfoList);
      }
      if (ObjectUtils.isNotEmpty(ids)) {
        propertyRightInfoHandler.removeByIds(ids);
      }
    }
    // 在产权收入分摊时，先删除历史收入
    LambdaQueryWrapper<AssetIncomeDistribution> distributionLambdaQueryWrapper = new LambdaQueryWrapper<>();
    distributionLambdaQueryWrapper.eq(AssetIncomeDistribution::getDoId,assetTransfer.getId());
    distributionLambdaQueryWrapper.eq(AssetIncomeDistribution::getDoType,BillTypeEnum.ASSET_TRANSFER.getKey());
    assetIncomeDistributionHandler.remove(distributionLambdaQueryWrapper);
    // 反写资产单元状态
    // 查询转让产权信息
    LambdaQueryWrapper<PropertyRightInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(PropertyRightInfo::getDoId,assetTransfer.getId());
    queryWrapper.eq(PropertyRightInfo::getDoType,BillTypeEnum.ASSET_TRANSFER.getKey());
    List<PropertyRightInfo> list = propertyRightInfoHandler.list(queryWrapper);
    if (ObjectUtils.isNotEmpty(request.getCustomId()) && ObjectUtils.isNotEmpty(list)) {
      List<PropertyRightInfo> prilist = new ArrayList<>();
      list.forEach(item -> {
        if(ObjectUtils.isNotEmpty(item.getParentId()) && ObjectUtils.isNotEmpty(item.getAssetUnitState())){
          PropertyRightInfo info = new PropertyRightInfo();
          info.setId(item.getParentId());
          info.setAssetUnitState(item.getAssetUnitState());
          prilist.add(info);
        }
      });
      if(ObjectUtils.isNotEmpty(prilist)){
        propertyRightInfoHandler.saveOrUpdateBatch(prilist);
      }
      //  处理资产分摊
      List<AssetIncomeDistributionRequest> requestList = new ArrayList<>();
      list.forEach(item -> {
        AssetIncomeDistributionRequest incomeDistributionRequest = new AssetIncomeDistributionRequest();
        incomeDistributionRequest.setPropertyId(item.getPropertyInfoId());
        incomeDistributionRequest.setPropertyRightInfoId(item.getParentId());
        incomeDistributionRequest.setPropertyType(true);
        incomeDistributionRequest.setPropertyDate(assetTransfer.getTransferOwnershipDate());
        incomeDistributionRequest.setPropertyMoney(item.getPropertyMoney());
        requestList.add(incomeDistributionRequest);
      });
      assetIncomeDistributionHandler.batchAddOrUpdateDocumentIntermediary(requestList,assetTransfer.getId(),BillTypeEnum.ASSET_TRANSFER.getKey());
    }

    //  处理法拍过程
    hangNetworkInfoHandler.batchAddOrUpdateHangNetworkInfo(request.getHangNetworkInfoRequestList(),assetTransfer.getId(),BillTypeEnum.ASSET_TRANSFER.getKey());

    //  合同关联
    billContractHandler.batchAddOrUpdateDocumentIntermediary(request.getBillContractRequestList(),assetTransfer.getId(),BillTypeEnum.ASSET_TRANSFER.getKey());

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),assetTransfer.getId(), BillTypeEnum.ASSET_TRANSFER.getKey());

    AssetTransferDTO dto = new AssetTransferDTO();
    dto.setId(assetTransfer.getId());
    return Result.success(dto);
  }

  @Override
  public Result<AssetTransferDTO> submit(AssetTransferRequest request) {
    request.setFlowState(ProcessStatus.running);
    Result<AssetTransferDTO> updateResult = update(request);
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
            .filter(item -> DictEnum.PROPERTY_TAG_002.getKey().equals(item.getItemCode()))
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

    //  【资产转让】+申请人+提交资产转让方案申请，
    String flowTitle = "【资产转让】%s提交资产转让方案申请";
    //  当前人
    SysUserDTO currentUser = platformUserUtils.getCurrentUser();

    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, updateResult.getData().getId());
    variables.put(ProcessConstants.VARIABLE_OBJECT,String.format(flowTitle,currentUser.getUsername()));
    processClient.startProcess(FlowBillEnum.ASSET_TRANSFER.getKey(), variables);

    return Result.success();
  }

  @Override
  public Result<AssetTransferDTO> writeBackProject(Long id) {

//    List<PropertyBill> list = propertyBillHandler.list(new LambdaUpdateWrapper<PropertyBill>()
//        .eq(PropertyBill::getDoId, id)
//        .eq(PropertyBill::getDoType, BillTypeEnum.OPERATION_INFO.getKey()));
//
//    for (PropertyBill propertyBill : list) {
//      propertyInfoHandler.update(new LambdaUpdateWrapper<PropertyInfo>()
//          .set(PropertyInfo::getPropertyState, PropertyInfoRequest.PropertyStateEnum.TRANSFERRED.getKey())
//          .eq(PropertyInfo::getId,propertyBill.getPropertyId()));
//    }
    return Result.success();
  }

  @Override
  public Result<Map<String, Object>> getFlowVariables(Long id) {
    Map<String, Object> map = Maps.newHashMap();
    AssetTransfer assetTransfer = handler.getById(id);

    if (ObjectUtils.isNotEmpty(assetTransfer)) {
      map.put("isAgainAuction", assetTransfer.getIsAgainAuction());
    }

    return Result.success(map);
  }

  @Override
  public Result<AssetTransferDTO> findById(Long id) {
    AssetTransfer assetTransfer = handler.getById(id);
    if (null == assetTransfer) {
      String message = "资产转让单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    AssetTransferDTO dto = PlatformMapUtils.getInstance().map(assetTransfer, AssetTransferDTO.class);
    // 查看准客户
    LambdaQueryWrapper<IntermediaryCustomerLead> customerLeadLambdaQueryWrapper = new LambdaQueryWrapper<>();
    customerLeadLambdaQueryWrapper.eq(IntermediaryCustomerLead::getDoId,dto.getId());
    customerLeadLambdaQueryWrapper.eq(IntermediaryCustomerLead::getDoType,BillTypeEnum.ASSET_TRANSFER.getKey());
    List<IntermediaryCustomerLead> intermediaryCustomerLeads = intermediaryCustomerLeadHandler.list(customerLeadLambdaQueryWrapper);
    List<IntermediaryCustomerLeadRequest> customers = PlatformMapUtils.getInstance().mapAsList(intermediaryCustomerLeads, IntermediaryCustomerLeadRequest.class);
    dto.setIntermediaryCustomerLeadRequestList(customers);
    // 查询转让产权信息
    LambdaQueryWrapper<PropertyRightInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(PropertyRightInfo::getDoId,dto.getId());
    queryWrapper.eq(PropertyRightInfo::getDoType,BillTypeEnum.ASSET_TRANSFER.getKey());
    List<PropertyRightInfo> list = propertyRightInfoHandler.list(queryWrapper);
    List<PropertyRightInfoRequest> propertyRightInfoRequestList = PlatformMapUtils.getInstance().mapAsList(list, PropertyRightInfoRequest.class);
    dto.setPropertyRightInfoRequests(propertyRightInfoRequestList);
    return Result.success(dto);
  }

  @Override
  public Result<List<AssetTransferDTO>> findAll(Integer current, Integer size, AssetTransferPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<AssetTransfer> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<AssetTransfer> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<AssetTransferDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, AssetTransferDTO.class);
//    for (AssetTransferDTO dto : dtoList) {
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