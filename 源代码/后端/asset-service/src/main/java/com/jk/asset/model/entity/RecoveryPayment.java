package com.jk.asset.model.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * 付款表对象
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("recovery_payment")
@ApiModel(description = "付款表对象")
public class RecoveryPayment extends BaseTenantDO {

  @ApiModelProperty("付款金额")
  private BigDecimal payAmount;

  @ApiModelProperty("付款时间")
  private Date payDate;

  @ApiModelProperty("律所名称")
   private String lawyerName;

  @ApiModelProperty("律所id")
  private Long lawyerId;

  @ApiModelProperty("收款方")
  private String payee;

  @ApiModelProperty("收款账号")
  private String receivingAccount;

  @ApiModelProperty("收款银行")
  private String receivingBank;

  @ApiModelProperty("状态 1 已审批 0 审批中")
  private Long payStatus;

  @ApiModelProperty(value = "项目名称")
  private String projectName;

  @ApiModelProperty(value = "项目Id")
  private Long projectId;

  @ApiModelProperty("付款说明")
  private String payExplain;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

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
