package com.jk.asset.model.entity.proceeding;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.model.entity.BaseTenantDO;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.proceeding.RecoveryAdjustTrialRequest.AdjustTrialTypeEnum;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 调解或审判信息	对象
 *
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("recovery_adjust_trial")
@ApiModel(description = "调解或审判信息	对象")
public class RecoveryAdjustTrial extends BaseTenantDO {

  @ApiModelProperty("调解或审判类型 before_ligation_mediation:诉前调解 middle_litigation_mediation：诉中调解 juge:判决")
  private AdjustTrialTypeEnum adjustTrialType;

  @ApiModelProperty("诉讼id")
  private Long litigationId;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("是否存量 是1 否0")
  private Boolean isStock;

  @ApiModelProperty("调解或审判日期")
  private Date adjustTrialDate;

  @ApiModelProperty("判决案号")
  private String adjustCode;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensatoryAmount;

  @ApiModelProperty("利息")
  private BigDecimal interest;

  @ApiModelProperty("违约金")
  private BigDecimal backOutAmount;

  @ApiModelProperty("其他费用")
  private BigDecimal otherAmount;

  @ApiModelProperty("特殊情况说明")
  private String specialRemarks;

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
   * @author wangshuai
   * @since 2024-07-03 10:13:14
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
