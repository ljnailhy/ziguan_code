package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 回款明细入参
 *
 * @author wangtao
 * @since 2024-06-20 18:53:02
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "回款明细入参")
public class RecoveryPaymentCollectionDetailRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("回款主表Id")
  private Long paymentCollectionId;

  @ApiModelProperty("回款类型 代偿金额:COMPENSATORY_CASH 利息:INTEREST 其他费用:OTHER_FEE 违约金:DEFAULT_CASH")
  private String collectionDetailType;

  @ApiModelProperty("反担保措施")
  private String reveMeasure;

  @ApiModelProperty("反担保Id")
  private String reveId;

  @ApiModelProperty("回款金额")
  private BigDecimal collectionDetailAmount;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileRequests;

  @ApiModelProperty("分录状态：新增ADD，修改UPDATE，删除DELETE")
  private OperationTypeEnum operateType;
  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  // @Override
  public boolean unverified() {
    // String校验：空org.apache.commons.lang3.StringUtils.isBlank 非空isNotBlank 批量-是否有空isAnyBlank 批量-都不空isNoneBlank
    // List校验：空org.apache.commons.collections4.CollectionUtils.isEmpty 非空isNotEmpty
    // 其他Object(或List)：空org.apache.commons.lang3.ObjectUtils.isEmpty 非空isNotEmpty 批量-都不空allNotNull 批量-都空allNull 批量-是否有不空anyNotNull 批量-是否有空anyNull
    // return super.unverified();
    if (collectionDetailType == null || collectionDetailAmount == null){
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
