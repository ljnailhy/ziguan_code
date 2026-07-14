package com.jk.asset.service.handler.property;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.property.AssetIncomeDistributionMapper;
import com.jk.asset.model.entity.property.AssetIncomeDistribution;
import com.jk.asset.model.entity.property.LeasePaymentCycle;
import com.jk.asset.model.request.property.AssetIncomeDistributionRequest;
import com.jk.asset.model.request.property.LeasePaymentCycleRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 资产收入分配接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-13 09:48:45
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssetIncomeDistributionHandler extends ServiceImpl<AssetIncomeDistributionMapper, AssetIncomeDistribution> {

  public void batchAddOrUpdateDocumentIntermediary(List<AssetIncomeDistributionRequest> assetIncomeDistributionRequestList, Long doId, String doType){

    if (ObjectUtils.isEmpty(assetIncomeDistributionRequestList)) {
      return;
    }

    for (AssetIncomeDistributionRequest assetIncomeDistributionRequest : assetIncomeDistributionRequestList) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(assetIncomeDistributionRequest.getOperateType()) && assetIncomeDistributionRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(assetIncomeDistributionRequest.getId());
        continue;
      }

      //  关联合同ID
      assetIncomeDistributionRequest.setDoId(doId);
      assetIncomeDistributionRequest.setDoType(doType);
      AssetIncomeDistribution assetIncomeDistribution = PlatformMapUtils.getInstance().map(assetIncomeDistributionRequest, AssetIncomeDistribution.class);
      //  新增或更新
      this.saveOrUpdate(assetIncomeDistribution);
    }

  }

}