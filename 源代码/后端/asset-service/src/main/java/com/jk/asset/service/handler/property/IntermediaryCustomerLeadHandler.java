package com.jk.asset.service.handler.property;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.property.IntermediaryCustomerLeadMapper;
import com.jk.asset.model.entity.property.IntermediaryCustomerLead;
import com.jk.asset.model.request.property.IntermediaryCustomerLeadRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 中介客户线索关联表接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-12 16:12:05
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IntermediaryCustomerLeadHandler extends ServiceImpl<IntermediaryCustomerLeadMapper, IntermediaryCustomerLead> {

  public void batchAddOrUpdateIntermediaryCustomerLead(List<IntermediaryCustomerLeadRequest> intermediaryCustomerLeadHandler, Long doId,String doType){

    if (ObjectUtils.isEmpty(intermediaryCustomerLeadHandler)) {
      return;
    }

    for (IntermediaryCustomerLeadRequest intermediaryCustomerLeadRequest : intermediaryCustomerLeadHandler) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(intermediaryCustomerLeadRequest.getOperateType()) && intermediaryCustomerLeadRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(intermediaryCustomerLeadRequest.getId());
        continue;
      }

      //  关联单据
      intermediaryCustomerLeadRequest.setDoId(doId);
      intermediaryCustomerLeadRequest.setDoType(doType);
      IntermediaryCustomerLead intermediaryCustomerLead = PlatformMapUtils.getInstance().map(intermediaryCustomerLeadRequest, IntermediaryCustomerLead.class);
      //  新增或更新
      this.saveOrUpdate(intermediaryCustomerLead);
    }

  }

}