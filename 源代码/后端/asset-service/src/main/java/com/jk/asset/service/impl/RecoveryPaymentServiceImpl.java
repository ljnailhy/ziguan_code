package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.FlowBillEnum;
import com.jk.asset.mapper.RecoveryPaymentMapper;
import com.jk.asset.model.dto.RecoveryPaymentDTO;
import com.jk.asset.model.entity.LawFirmInfo;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RecoveryPayment;
import com.jk.asset.model.entity.RecoveryPaymentDetail;
import com.jk.asset.model.request.RecoveryPaymentDetailRequest;
import com.jk.asset.model.request.RecoveryPaymentRequest;
import com.jk.asset.model.request.page.RecoveryPaymentPageRequest;
import com.jk.asset.service.RecoveryPaymentService;
import com.jk.asset.service.handler.LawFirmInfoHandler;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RecoveryPaymentDetailHandler;
import com.jk.asset.service.handler.RecoveryPaymentHandler;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.constant.PlatformConstant;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 付款表接口实现类
 *
 * @author wangtao
 * @since 2024-07-08 14:24:03
 */
@RestController
@Slf4j
@Api(tags = "付款表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryPaymentServiceImpl implements RecoveryPaymentService {

  private final RecoveryPaymentHandler handler;
  private final RecoveryPaymentMapper mapper;
  private final RecoveryPaymentDetailHandler detailHandler;
  private final PlatformFileUtils platformFileUtils;
  private final SysDictionaryClient sysDictionaryClient;
  private final LawFirmInfoHandler lawFirmInfoHandler;
  private final ProcessClient processClient;
  private final AssetUserLimitsUtils assetUserLimitsUtils;
  private final ProjectInfoHandler projectInfoHandler;
  @Override
  public Result<RecoveryPaymentDTO> add(RecoveryPaymentRequest request) {
    if (null == request || request.unverified()) {
      String message = "付款表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }
    // 对象拷贝：request->DO
    RecoveryPayment recoveryPayment = PlatformMapUtils.getInstance().map(request, RecoveryPayment.class);
    recoveryPayment.setId(null);
   if (ObjectUtils.isEmpty(request.getPayStatus())){
     Optional<SysDictionaryItemDTO> state = sysDictionaryClient.findByCode(DictEnum.PAY_STATUS.getKey())
             .getData()
             .getItems()
             .stream()
             .filter(item -> item.getItemCode().equals(DictEnum.PAY_STATUS_001.getKey()))
             .findAny();
     state.ifPresent(sysDictionaryItemDTO -> recoveryPayment.setPayStatus(sysDictionaryItemDTO.getId()));
   }
    Long lawyerId = recoveryPayment.getLawyerId();
    if (!ObjectUtils.isEmpty(lawyerId)) {
      LawFirmInfo byId = lawFirmInfoHandler.getById(lawyerId);
      if (!ObjectUtils.isEmpty(byId)) {
        recoveryPayment.setLawyerName(byId.getName());
      }
    }
    handler.save(recoveryPayment);
    RecoveryPaymentDTO dto = new RecoveryPaymentDTO();
    dto.setId(recoveryPayment.getId());
    List<RecoveryPaymentDetailRequest> recoveryPaymentDetailRequests = request.getPaymentDetailRequests();
    if (!ObjectUtils.isEmpty(recoveryPaymentDetailRequests)) {
      recoveryPaymentDetailRequests.forEach(item -> {
        item.setPaymentId(recoveryPayment.getId());
        RecoveryPaymentDetail detail = PlatformMapUtils.getInstance().map(item, RecoveryPaymentDetail.class);
        detailHandler.save(detail);
        platformFileUtils.batchAddFile(item.getFileRequests(),detail.getId(), BillTypeEnum.RECOVERY_PAYMENT_DETAIL.getKey());
      });
    }
      //  通用附件新增
      platformFileUtils.batchAddFile(request.getFileRequests(),recoveryPayment.getId(), BillTypeEnum.RECOVERY_PAYMENT.getKey());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryPaymentDTO> update(RecoveryPaymentRequest request) {
    if (null == request || null == request.getId()) {
      String message = "付款表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryPayment recoveryPayment = PlatformMapUtils.getInstance().map(request, RecoveryPayment.class);
    Long lawyerId = recoveryPayment.getLawyerId();
    if (!ObjectUtils.isEmpty(lawyerId)) {
      LawFirmInfo byId = lawFirmInfoHandler.getById(lawyerId);
      if (!ObjectUtils.isEmpty(byId)) {
        recoveryPayment.setLawyerName(byId.getName());
      }
    }
    handler.updateById(recoveryPayment);
    RecoveryPaymentDTO recoveryPaymentDTO = new RecoveryPaymentDTO();
    recoveryPaymentDTO.setId(recoveryPayment.getId());

    List<RecoveryPaymentDetailRequest> detailRequests = request.getPaymentDetailRequests();
    if (!ObjectUtils.isEmpty(detailRequests)) {
      List<Long> removeIds = new ArrayList<>();
      for (RecoveryPaymentDetailRequest detailRequest : detailRequests) {
        if (Objects.nonNull(detailRequest.getOperateType()) && detailRequest.getOperateType().getKey().equals(OperationTypeEnum.DELETE.getKey())){
          removeIds.add(detailRequest.getId());
        }else {
          RecoveryPaymentDetail map = PlatformMapUtils.getInstance().map(detailRequest, RecoveryPaymentDetail.class);
          map.setPaymentId(request.getId());
          detailHandler.saveOrUpdate(map);
          platformFileUtils.batchUpdateFile(detailRequest.getFileRequests(),map.getId(), BillTypeEnum.RECOVERY_PAYMENT_DETAIL.getKey());
        }
      }
      if (!removeIds.isEmpty()){
        this.detailHandler.removeByIds(removeIds);
        platformFileUtils.deleteFileByDoIds(removeIds, BillTypeEnum.RECOVERY_PAYMENT_DETAIL.getKey());
      }
    }
    if (!ObjectUtils.isEmpty(request.getFileRequests())) {
      platformFileUtils.batchAddFile(request.getFileRequests(), request.getId(), BillTypeEnum.RECOVERY_PAYMENT.getKey());
    }
    return Result.success(recoveryPaymentDTO);
  }

  @Override
  public Result<RecoveryPaymentDTO> submit(RecoveryPaymentRequest request) {
    request.setFlowState(ProcessStatus.running);
    Result<RecoveryPaymentDTO> result;
    if (ObjectUtils.isEmpty(request.getId())){
      result= add(request);
    }else {
      result = update(request);
    }
    if (null == result || !result.succeedWithData()) {
      return Result.error("提交失败");
    }
    ProjectInfo projectInfo = projectInfoHandler.getById(request.getProjectId());

    //  【付款申请】+项目名称+于+付款时间+提交付款申请，
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
    String flowTitle = "【付款申请】%s于%s提交付款申请";

    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, result.getData().getId());
    variables.put(ProcessConstants.VARIABLE_OBJECT, String.format(flowTitle,projectInfo.getProjectName(),formatter.format(request.getPayDate())));
    processClient.startProcess(FlowBillEnum.RECOVERY_PAYMENT.getKey(), variables);

    return Result.success();
  }

  @Override
  public Result<RecoveryPaymentDTO> findById(Long id) {
    RecoveryPayment recoveryPayment = handler.getById(id);
    if (null == recoveryPayment) {
      String message = "付款表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryPaymentDTO dto = PlatformMapUtils.getInstance().map(recoveryPayment, RecoveryPaymentDTO.class);
    LambdaQueryWrapper<RecoveryPaymentDetail> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(RecoveryPaymentDetail::getPaymentId,recoveryPayment.getId());
    List<RecoveryPaymentDetail> list = detailHandler.list(queryWrapper);
    dto.setPaymentDetailRequests(PlatformMapUtils.getInstance().mapAsList(list, RecoveryPaymentDetailRequest.class));
    return Result.success(dto);
  }

  @Override
  public Result<List<RecoveryPaymentDTO>> findAll(Integer current, Integer size, RecoveryPaymentPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }

    // Step1：创建一个 Page 对象
    IPage<RecoveryPayment> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
    request.setOrgUserIds(userLimitsOrg);
    List<RecoveryPayment> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }

    // Step3：获取分页数据
    List<RecoveryPaymentDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryPaymentDTO.class);
    LambdaQueryWrapper<RecoveryPaymentDetail> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(RecoveryPaymentDetail::getPaymentId,dtoList.stream().map(RecoveryPaymentRequest::getId).collect(Collectors.toList()));
    List<RecoveryPaymentDetail> list = detailHandler.list(queryWrapper);
    if (!ObjectUtils.isEmpty(list)) {
      Map<Long, List<RecoveryPaymentDetail>> collect = list.stream().collect(Collectors.groupingBy(RecoveryPaymentDetail::getPaymentId));
      dtoList.forEach(a -> {
        for (RecoveryPaymentDTO dto : dtoList) {
          if (!ObjectUtils.isEmpty(collect.get(dto.getProjectId()))) {
            dto.setPaymentDetailRequests(PlatformMapUtils.getInstance().mapAsList(collect.get(dto.getId()), RecoveryPaymentDetailRequest.class));
          }
        }
      });
    }
    ProcessStatus processStatus = request.getProcessStatus();
    for (RecoveryPaymentDTO dto : dtoList) {
      Long id = dto.getId();
      // 流程状态
      Result<ProcessStatus> statusResult = processClient.getStatusByDoId(id);
      if (null != statusResult && statusResult.succeedWithData()) {
        dto.setProcessStatus(statusResult.getData());
      }
    }
    return Result.success(dtoList, PageFactory.page(page));
  }

  @Override
  public Result<RecoveryPaymentDTO> writeBackProject(Long id) {
    RecoveryPayment recoveryPayment = handler.getById(id);
    if (null == recoveryPayment) {
      String message = "付款	单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    Long state = sysDictionaryClient.findByCode(DictEnum.PAY_STATUS.getKey())
            .getData()
            .getItems()
            .stream()
            .filter(item -> item.getItemCode().equals(DictEnum.PAY_STATUS_002.getKey()))
            .findAny().get().getId();
    recoveryPayment.setPayStatus(state);
    mapper.updateById(recoveryPayment);
    return Result.success(PlatformMapUtils.getInstance().map(recoveryPayment, RecoveryPaymentDTO.class));
  }
}