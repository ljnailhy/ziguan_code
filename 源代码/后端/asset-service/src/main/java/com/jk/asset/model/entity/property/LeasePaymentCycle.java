package com.jk.asset.model.entity.property;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.model.entity.BaseTenantDO;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 租赁缴费周期表对象
 *
 * @author wangshuai
 * @since 2024-07-13 09:46:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("lease_payment_cycle")
@ApiModel(description = "租赁缴费周期表对象")
public class LeasePaymentCycle extends BaseTenantDO {

  @ApiModelProperty("对象Id")
  private Long doId;

  @ApiModelProperty("对象类型")
  private String doType;

  @ApiModelProperty("开始时间")
  private Date startDate;

  @ApiModelProperty("结束时间")
  private Date endDate;

  @ApiModelProperty("提醒收租时间")
  private Date remindDate;

  @ApiModelProperty("缴费金额")
  private BigDecimal paymentAmount;

  @ApiModelProperty("状态")
  private Long state;

  @ApiModelProperty("备注")
  private String remark;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-13 09:46:33
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
