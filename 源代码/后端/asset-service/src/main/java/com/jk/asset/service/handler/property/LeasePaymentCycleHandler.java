package com.jk.asset.service.handler.property;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.property.LeasePaymentCycleMapper;
import com.jk.asset.model.entity.property.LeasePaymentCycle;
import com.jk.asset.model.request.property.AssetIncomeDistributionRequest;
import com.jk.asset.model.request.property.LeasePaymentCycleRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.service.utils.PlatformFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 租赁缴费周期表接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-13 09:46:33
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LeasePaymentCycleHandler extends ServiceImpl<LeasePaymentCycleMapper, LeasePaymentCycle> {
  @Autowired
  private PlatformFileUtils platformFileUtils;
  @Autowired
  private AssetIncomeDistributionHandler assetIncomeDistributionHandler;


  public void batchAddOrUpdateDocumentIntermediary(List<LeasePaymentCycleRequest> leasePaymentCycleRequestList, Long doId, String doType){

    if (ObjectUtils.isEmpty(leasePaymentCycleRequestList)) {
      return;
    }

    for (LeasePaymentCycleRequest leasePaymentCycleRequest : leasePaymentCycleRequestList) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(leasePaymentCycleRequest.getOperateType()) && leasePaymentCycleRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(leasePaymentCycleRequest.getId());
        List<AssetIncomeDistributionRequest> assetIncomeDistributionRequestList = leasePaymentCycleRequest.getAssetIncomeDistributionRequestList();
        if (!ObjectUtils.isEmpty(assetIncomeDistributionRequestList)){
          assetIncomeDistributionRequestList.forEach(item -> item.setOperateType(OperationTypeEnum.DELETE));
          assetIncomeDistributionHandler.batchAddOrUpdateDocumentIntermediary(leasePaymentCycleRequest.getAssetIncomeDistributionRequestList(),leasePaymentCycleRequest.getId(),BillTypeEnum.LEASE_PAYMENT_CYCLE.getKey());
        }
        continue;
      }

      //  关联合同ID
      leasePaymentCycleRequest.setDoId(doId);
      leasePaymentCycleRequest.setDoType(doType);
      LeasePaymentCycle leasePaymentCycle = PlatformMapUtils.getInstance().map(leasePaymentCycleRequest, LeasePaymentCycle.class);
      //  新增或更新
      this.saveOrUpdate(leasePaymentCycle);

      //  处理资产分配
      assetIncomeDistributionHandler.batchAddOrUpdateDocumentIntermediary(leasePaymentCycleRequest.getAssetIncomeDistributionRequestList(),leasePaymentCycle.getId(),BillTypeEnum.LEASE_PAYMENT_CYCLE.getKey());

      //  处理附件信息
      platformFileUtils.batchUpdateFile(leasePaymentCycleRequest.getFileInfoList(),leasePaymentCycle.getId(),BillTypeEnum.LEASE_PAYMENT_CYCLE.getKey());
    }

  }

}