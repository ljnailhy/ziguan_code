package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.BillContractMapper;
import com.jk.asset.model.entity.BillContract;
import com.jk.asset.model.request.BillContractRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 单据合同关联表接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:32
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BillContractHandler extends ServiceImpl<BillContractMapper, BillContract> {

  public void batchAddOrUpdateDocumentIntermediary(List<BillContractRequest> billContractRequests, Long doId, String doType){
    if (ObjectUtils.isEmpty(billContractRequests)) {
      return;
    }
    List<Long> billContractIds = new ArrayList<>();
    List<BillContract> billContractList = new ArrayList<>();
    for (BillContractRequest billContractRequest : billContractRequests) {
      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(billContractRequest.getOperateType()) && billContractRequest.getOperateType() == OperationTypeEnum.DELETE){
        billContractIds.add(billContractRequest.getId());
      }
      //  关联合同ID
      billContractRequest.setDoId(doId);
      billContractRequest.setDoType(doType);
      BillContract billContract = PlatformMapUtils.getInstance().map(billContractRequest, BillContract.class);
      //  新增或更新
      billContractList.add(billContract);
    }
    // 批量处理新增、更新
    this.saveOrUpdateBatch(billContractList);
    // 批量处理删除
    this.removeByIds(billContractIds);
  }
}