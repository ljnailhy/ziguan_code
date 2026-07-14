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
 * 付款明细入参
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "付款明细入参")
public class RecoveryPaymentDetailRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("付款主表Id")
  private Long paymentId;

  @ApiModelProperty("付款类型	1 诉讼费	2 保全费	3 保险费	4 其他费用	5 律师费用")
  private Long payType;

  @ApiModelProperty(value = "付款金额", required = true)
  private BigDecimal payAmount;

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
   * @since 2024-07-08 11:35:36
   */
  // @Override
  public boolean unverified() {
    // String校验：空org.apache.commons.lang3.StringUtils.isBlank 非空isNotBlank 批量-是否有空isAnyBlank 批量-都不空isNoneBlank
    // List校验：空org.apache.commons.collections4.CollectionUtils.isEmpty 非空isNotEmpty
    // 其他Object(或List)：空org.apache.commons.lang3.ObjectUtils.isEmpty 非空isNotEmpty 批量-都不空allNotNull 批量-都空allNull 批量-是否有不空anyNotNull 批量-是否有空anyNull
    // return super.unverified();
    return false;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
