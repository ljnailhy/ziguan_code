package com.jk.asset.service.impl.property;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.FlowBillEnum;
import com.jk.asset.mapper.property.OperationInfoMapper;
import com.jk.asset.model.dto.property.OperationInfoDTO;
import com.jk.asset.model.entity.property.OperationInfo;
import com.jk.asset.model.entity.property.PropertyBill;
import com.jk.asset.model.entity.property.PropertyInfo;
import com.jk.asset.model.request.page.property.OperationInfoPageRequest;
import com.jk.asset.model.request.property.OperationInfoRequest;
import com.jk.asset.service.handler.property.AssetIncomeDistributionHandler;
import com.jk.asset.service.handler.property.OperationInfoHandler;
import com.jk.asset.service.handler.property.PropertyBillHandler;
import com.jk.asset.service.handler.property.PropertyInfoHandler;
import com.jk.asset.service.property.OperationInfoService;
import com.jk.common.constant.PlatformConstant;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运营信息表接口实现类
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:55
 */
@RestController
@Slf4j
@Api(tags = "运营信息表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OperationInfoServiceImpl implements OperationInfoService {

  private final OperationInfoHandler handler;
  private final OperationInfoMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final PropertyBillHandler propertyBillHandler;
  private final AssetIncomeDistributionHandler assetIncomeDistributionHandler;
  private final ProcessClient processClient;
  private final PropertyInfoHandler propertyInfoHandler;
  private final PlatformUserUtils platformUserUtils;
  private final SysDictionaryClient sysDictionaryClient;

  @Override
  public Result<OperationInfoDTO> add(OperationInfoRequest request) {
    if (null == request || request.unverified() || ObjectUtils.isEmpty(request.getPropertyBillRequestList())) {
      String message = "运营信息表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    OperationInfo operationInfo = PlatformMapUtils.getInstance().map(request, OperationInfo.class);
    handler.save(operationInfo);

    //  资产和单据关联处理
    propertyBillHandler.batchAddOrUpdatePropertyBillHandler(request.getPropertyBillRequestList(),operationInfo.getId(), BillTypeEnum.OPERATION_INFO.getKey());

    //  处理资产分摊
    assetIncomeDistributionHandler.batchAddOrUpdateDocumentIntermediary(request.getAssetIncomeDistributionRequestList(),operationInfo.getId(),BillTypeEnum.OPERATION_INFO.getKey());

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),operationInfo.getId(), BillTypeEnum.OPERATION_INFO.getKey());

    OperationInfoDTO dto = new OperationInfoDTO();
    dto.setId(operationInfo.getId());
    dto.setOperationType(operationInfo.getOperationType());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    Result<ProcessStatus> statusResult = processClient.getStatusByDoId(id);
    if (null != statusResult && statusResult.succeedWithData()) {
      String message = "运营信息删除失败 已提交数据不能删除";
      log.info("{} processStatus {}", message, statusResult.getData().getValue());
      return Result.error(message);
    }
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<OperationInfoDTO> update(OperationInfoRequest request) {
    if (null == request || ObjectUtils.isEmpty(request.getPropertyBillRequestList())) {
      String message = "运营信息表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    OperationInfo operationInfo = PlatformMapUtils.getInstance().map(request, OperationInfo.class);
    handler.saveOrUpdate(operationInfo);

    //  资产和单据关联处理
    propertyBillHandler.batchAddOrUpdatePropertyBillHandler(request.getPropertyBillRequestList(),operationInfo.getId(), BillTypeEnum.OPERATION_INFO.getKey());

    //  处理资产分摊
    assetIncomeDistributionHandler.batchAddOrUpdateDocumentIntermediary(request.getAssetIncomeDistributionRequestList(),operationInfo.getId(),BillTypeEnum.OPERATION_INFO.getKey());

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),operationInfo.getId(), BillTypeEnum.OPERATION_INFO.getKey());

    OperationInfoDTO dto = new OperationInfoDTO();
    dto.setId(operationInfo.getId());
    dto.setOperationType(operationInfo.getOperationType());
    return Result.success(dto);
  }

  @Override
  public Result<OperationInfoDTO> findById(Long id) {
    OperationInfo operationInfo = handler.getById(id);
    if (null == operationInfo) {
      String message = "运营信息表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    OperationInfoDTO dto = PlatformMapUtils.getInstance().map(operationInfo, OperationInfoDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<OperationInfoDTO> submit(OperationInfoRequest request) {
    request.setFlowState(ProcessStatus.running);
    Result<OperationInfoDTO> updateResult = update(request);
    if (null == updateResult || !updateResult.succeedWithData()) {
      return Result.error("提交失败");
    }
    //  【资产运营】+申请人+于+运营日期+提交资产巡视工作登记，
    String flowTitle = "【资产运营】%s于%s提交资产巡视工作登记";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");

    SysDictionaryItemDTO operationType = sysDictionaryClient.findByCode(DictEnum.OPERATION_TYPE.getKey())
            .getData()
            .getItems()
            .stream()
            .filter(item -> item.getItemCode().equals(DictEnum.OPERATION_TYPE_001.getKey()))
            .findAny().get();

    //  当前人
    SysUserDTO currentUser = platformUserUtils.getCurrentUser();
    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, updateResult.getData().getId());
    variables.put(ProcessConstants.VARIABLE_OBJECT,String.format(flowTitle,currentUser.getUsername(),formatter.format(request.getOperationDate())));
    processClient.startProcess(FlowBillEnum.OPERATION_INFO.getKey(), variables);
    return Result.success();
  }

  @Override
  public Result<OperationInfoDTO> writeBackProject(Long id) {

    List<PropertyBill> list = propertyBillHandler.list(new LambdaUpdateWrapper<PropertyBill>()
        .eq(PropertyBill::getDoId, id)
        .eq(PropertyBill::getDoType, BillTypeEnum.OPERATION_INFO.getKey()));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success();
    }

    for (PropertyBill propertyBill : list) {
      LambdaUpdateWrapper<PropertyInfo> updateWrapper = new LambdaUpdateWrapper<PropertyInfo>()
              .set(PropertyInfo::getPropertyState, propertyBill.getPropertyState().getKey())
              .set(PropertyInfo::getPropertyTag, propertyBill.getPropertyTag())
              .eq(PropertyInfo::getId, propertyBill.getPropertyId());
      propertyInfoHandler.update(updateWrapper);
    }
    return Result.success();
  }

  @Override
  public Result<Map<String, Object>> getFlowVariables(Long id) {
    SysDictionaryItemDTO operationType = sysDictionaryClient.findByCode(DictEnum.OPERATION_TYPE.getKey())
            .getData()
            .getItems()
            .stream()
            .filter(item -> item.getItemCode().equals(DictEnum.OPERATION_TYPE_001.getKey()))
            .findAny().get();
    Map<String, Object> map = Maps.newHashMap();
    OperationInfo operationInfo = handler.getById(id);

    if (!operationInfo.getOperationType().equals(operationType.getId())) {
      // 不为日常巡逻
      map.put("dailyPatrol", false);
    }else {
      map.put("dailyPatrol", true);
    }
    return Result.success(map);
  }

  @Override
  public Result<List<OperationInfoDTO>> findAll(Integer current, Integer size, OperationInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<OperationInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<OperationInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<OperationInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, OperationInfoDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}