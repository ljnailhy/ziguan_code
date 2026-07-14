package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 年度回款目标入参
 *
 * @author wangshuai
 * @since 2024-06-25 10:24:50
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "年度回款目标入参")
public class PaymentCollectionTargetRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("年度")
  private Date year;

  @ApiModelProperty("自主追偿回款目标")
  private BigDecimal targetMoney;

  @ApiModelProperty("委托追偿回款目标")
  private BigDecimal entrustMoney;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("回款目标明细")
  private List<PaymentCollectionTargetDetailRequest> paymentCollectionTargetDetailRequests;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-25 10:24:50
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
