package com.jk.asset.model.entity.property;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.model.entity.BaseTenantDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 中介客户线索关联表对象
 *
 * @author wangshuai
 * @since 2024-07-12 16:12:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("intermediary_customer_lead")
@ApiModel(description = "中介客户线索关联表对象")
public class IntermediaryCustomerLead extends BaseTenantDO {

  @ApiModelProperty("对象Id")
  private Long doId;

  @ApiModelProperty("对象类型")
  private String doType;

  @ApiModelProperty("单据与中介关联id")
  private Long propertyId;

  @ApiModelProperty("客户id")
  private Long customId;

  @ApiModelProperty("客户电话")
  private String customPhone;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("推荐客户保护期 开始时间")
  private Date startDate;

  @ApiModelProperty("推荐客户保护期 结束时间")
  private Date endDate;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-12 16:12:05
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
