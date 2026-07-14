package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.service.WorkflowCommonService;
import com.jk.asset.service.handler.AllocationInfoHandler;
import com.jk.asset.service.handler.ProjectTransferHandler;
import com.jk.asset.service.handler.RecoveryPaymentCollectionHandler;
import com.jk.asset.service.handler.RecoveryPaymentHandler;
import com.jk.asset.service.handler.proceeding.RecoveryAdjustTrialHandler;
import com.jk.asset.service.handler.proceeding.RecoveryExecuteHandler;
import com.jk.asset.service.handler.proceeding.RecoveryJudgementHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationDetailsHandler;
import com.jk.asset.service.handler.property.AssetTransferHandler;
import com.jk.asset.service.handler.property.LeaseInfoHandler;
import com.jk.asset.service.handler.property.OperationInfoHandler;
import com.jk.common.model.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程引擎公共调用接口
 *
 * @author WangShuai
 * @since 2024/8/5 16:22
 **/
@RestController
@Slf4j
@Api(tags = "流程引擎公共调用接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkflowCommonServiceImpl implements WorkflowCommonService {

  /**
   * 项目分配
   */
  private final AllocationInfoHandler allocationInfoHandler;
  /**
   * 资产转让
   */
  private final AssetTransferHandler assetTransferHandler;
  /**
   * 资产租赁
   */
  private final LeaseInfoHandler leaseInfoHandler;
  /**
   * 资产运营
   */
  private final OperationInfoHandler operationInfoHandler;
  /**
   * 项目移交
   */
  private final ProjectTransferHandler projectTransferHandler;
  /**
   * 调解审批
   */
  private final RecoveryAdjustTrialHandler recoveryAdjustTrialHandler;
  /**
   * 诉讼执行
   */
  private final RecoveryExecuteHandler recoveryExecuteHandler;
  /**
   * 立案审理
   */
  private final RecoveryJudgementHandler recoveryJudgementHandler;
  /**
   * 保全终本
   */
  private final RecoveryLitigationDetailsHandler recoveryLitigationDetailsHandler;
  /**
   * 付款管理
   */
  private final RecoveryPaymentHandler recoveryPaymentHandler;
  /**
   * 回款管理
   */
  private final RecoveryPaymentCollectionHandler recoveryPaymentCollectionHandler;

  @Override
  public Result<?> writeBackState(Long billId, String billType, String state) {

    //  根据类型判断 handler
    ServiceImpl<?, ?> handler = null;
    if (BillTypeEnum.ALLOCATION_INFO.getKey().equals(billType)) {
      handler = allocationInfoHandler;
    }else if (BillTypeEnum.ASSET_TRANSFER.getKey().equals(billType)){
      handler = assetTransferHandler;
    }else if (BillTypeEnum.LEASE_INFO.getKey().equals(billType)){
      handler = leaseInfoHandler;
    }else if (BillTypeEnum.OPERATION_INFO.getKey().equals(billType)){
      handler = operationInfoHandler;
    }else if (BillTypeEnum.PROJECT_TRANSFER.getKey().equals(billType)){
      handler = projectTransferHandler;
    }else if (BillTypeEnum.RECOVERY_ADJUST_TRIAL.getKey().equals(billType)){
      handler = recoveryAdjustTrialHandler;
    }else if (BillTypeEnum.RECOVERY_EXECUTE.getKey().equals(billType)){
      handler = recoveryExecuteHandler;
    }else if (BillTypeEnum.RECOVERY_JUDGEMENT.getKey().equals(billType)){
      handler = recoveryJudgementHandler;
    }else if (BillTypeEnum.FINAL.getKey().equals(billType)){
      handler = recoveryLitigationDetailsHandler;
    }else if (BillTypeEnum.RECOVERY_PAYMENT.getKey().equals(billType)){
      handler = recoveryPaymentHandler;
    }else if (BillTypeEnum.RECOVERY_PAYMENT_COLLECTION.getKey().equals(billType)){
      handler = recoveryPaymentCollectionHandler;
    }else{
      String message = "反写流程状态失败";
      log.error("{} billId {} billType {} state {}", message, billId,billType,state);
      return Result.error(message);
    }

    UpdateWrapper updateWrapper = new UpdateWrapper();
    updateWrapper.eq("id", billId);
    updateWrapper.set("flow_state", state);
    handler.update(updateWrapper);
    return Result.success();
  }
}
