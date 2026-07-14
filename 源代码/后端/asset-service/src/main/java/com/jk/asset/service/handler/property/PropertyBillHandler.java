package com.jk.asset.service.handler.property;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.property.PropertyBillMapper;
import com.jk.asset.model.entity.property.PropertyBill;
import com.jk.asset.model.request.property.PropertyBillRequest;
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
 * 资产和单据关联表接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-11 18:03:58
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyBillHandler extends ServiceImpl<PropertyBillMapper, PropertyBill> {

  @Autowired
  private PlatformFileUtils platformFileUtils;

  public void batchAddOrUpdatePropertyBillHandler(List<PropertyBillRequest> propertyBillRequestList, Long doId, String doType){

    if (ObjectUtils.isEmpty(propertyBillRequestList)) {
      return;
    }

    for (PropertyBillRequest propertyBillRequest : propertyBillRequestList) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(propertyBillRequest.getOperateType()) && propertyBillRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(propertyBillRequest.getId());
        //  删除附件信息
        platformFileUtils.deleteFileByDoId(propertyBillRequest.getId(), BillTypeEnum.PROPERTY_BILL.getKey());
        continue;
      }


      //  关联合同ID
      propertyBillRequest.setDoId(doId);
      propertyBillRequest.setDoType(doType);
      PropertyBill propertyBill = PlatformMapUtils.getInstance().map(propertyBillRequest, PropertyBill.class);
      //  新增或更新
      this.saveOrUpdate(propertyBill);

      //  处理附件信息
      platformFileUtils.batchUpdateFile(propertyBillRequest.getFileInfoList(),propertyBill.getId(),BillTypeEnum.PROPERTY_BILL.getKey());
    }

  }

}