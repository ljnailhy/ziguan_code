package com.jk.asset.model.entity.property;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.property.LeaseInfoRequest.PaymentCycleEnum;
import com.jk.common.model.entity.BaseTenantDO;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 租赁信息表对象
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("lease_info")
@ApiModel(description = "租赁信息表对象")
public class LeaseInfo extends BaseTenantDO {

  @ApiModelProperty("评估机构")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private Long estimateId;

  @ApiModelProperty("评估价")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private BigDecimal estimateMoney;

  @ApiModelProperty("评估有效期开始时间")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private Date estimateDate;

  @ApiModelProperty("评估有效期截止时间")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private Date estimateEndDate;

  @ApiModelProperty("代理费用")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private BigDecimal agencyFee;

  @ApiModelProperty("联系人")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private String contacts;

  @ApiModelProperty("联系电话")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private String contactsPhone;

  @ApiModelProperty("处置方案")
  private String disposeProgramme;

  @ApiModelProperty("月租金")
  private BigDecimal monthRent;

  @ApiModelProperty("年租金")
  private BigDecimal yearRent;

  @ApiModelProperty("保证金")
  private BigDecimal margin;

  @ApiModelProperty("租赁期限开始日期")
  private Date leaseTermStart;

  @ApiModelProperty("租赁期限结束日期")
  private Date leaseTermEnd;

  @ApiModelProperty("缴纳周期'YEAR'：年,'HALF_YEAR'：半年,'SEASON'：季,'MONTH'：月,'DISPOSABLE'：一次性,'OTHER'：其他")
  private PaymentCycleEnum paymentCycle;

  @ApiModelProperty("承租人")
  private Long lessee;

  @ApiModelProperty("租赁用途")
  private String leasePurpose;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("是否评估")
  private Boolean isEstimate;

  @ApiModelProperty("选择中介机构")
  private Long intermediaryId;

  @ApiModelProperty("中介费用")
  private Long intermediaryFee;



  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-11 18:04:22
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
