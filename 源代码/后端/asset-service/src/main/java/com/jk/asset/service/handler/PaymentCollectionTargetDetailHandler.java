package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.PaymentCollectionTargetDetailMapper;
import com.jk.asset.model.entity.PaymentCollectionTargetDetail;
import com.jk.asset.model.request.PaymentCollectionTargetDetailRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 回款目标明细接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-06-25 10:24:51
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentCollectionTargetDetailHandler extends ServiceImpl<PaymentCollectionTargetDetailMapper, PaymentCollectionTargetDetail> {

  /**
   * 回款目标没逻辑直接先删后插
   * @param
   * @return void
   * @author wangshuai
   * @since 2024/6/25 10:30
   **/
  public void batchAddPaymentCollectionTargetDetail(List<PaymentCollectionTargetDetailRequest> paymentCollectionTargetDetailRequests,Long targetId ){

    if (ObjectUtils.isEmpty(paymentCollectionTargetDetailRequests)) {
      return;
    }

//    this.remove(new LambdaQueryWrapper<PaymentCollectionTargetDetail>().eq(PaymentCollectionTargetDetail::getTargetId,targetId));

    List<PaymentCollectionTargetDetailRequest> list = paymentCollectionTargetDetailRequests
            .stream()
            .map(item -> item.setTargetId(targetId))
            .collect(Collectors.toList());
    List<PaymentCollectionTargetDetailRequest> removeList = paymentCollectionTargetDetailRequests.stream()
            .filter(item -> item.getOperateType() != null && item.getOperateType().getKey().equals(OperationTypeEnum.DELETE.getKey()))
            .collect(Collectors.toList());
    List<PaymentCollectionTargetDetail> doList = PlatformMapUtils.getInstance().mapAsList(list, PaymentCollectionTargetDetail.class);
    this.saveOrUpdateBatch(doList);
    this.removeByIds(removeList.stream().map(PaymentCollectionTargetDetailRequest::getId).collect(Collectors.toList()));
  }

}