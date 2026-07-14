package com.jk.asset.model.entity.proceeding;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.asset.enums.LitigationTypeEnum;
import com.jk.asset.model.request.proceeding.RecoveryExecuteRequest;
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
 * 诉讼反写信息扩展表对象
 *
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("recovery_litigation_ext")
@ApiModel(description = "诉讼反写信息扩展表对象")
public class RecoveryLitigationExt extends BaseTenantDO {

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("代偿金额")
  private BigDecimal backCompensationAmount;

  @ApiModelProperty("利息（万元）")
  private BigDecimal backInterest;

  @ApiModelProperty("违约金（万元）")
  private BigDecimal backLiquidatedDamages;

  @ApiModelProperty("其他费用")
  private BigDecimal backOtherFees;

  @ApiModelProperty("调解或审判日期")
  private Date adjustTrialDate;

  @ApiModelProperty("判决案号")
  private String adjustCode;

  @ApiModelProperty("立案法院")
  private String filingCourtName;

  @ApiModelProperty("法官")
  private String judgeName;

  @ApiModelProperty("立案时间")
  private Date fillingDate;

  @ApiModelProperty("类型 first_instance:一审 second_instance 二审 retrial:再审 register:立案")
  private LitigationTypeEnum litigationType;

  @ApiModelProperty("案号")
  private String fillingCode;

  @ApiModelProperty("判决日期")
  private Date judgeDate;

  @ApiModelProperty("开庭时间")
  private Date courtSessionDate;

  @ApiModelProperty("撤诉时间")
  private Date withdrawLawsuitDate;

  @ApiModelProperty("撤诉说明")
  private String withdrawLawsuitDescription;

  @ApiModelProperty("终本时间")
  private Date finalVersionDate;

  @ApiModelProperty("终本说明")
  private String finalVersionDescription;

  @ApiModelProperty("结案时间")
  private Date closeCaseDate;

  @ApiModelProperty("结案说明")
  private String closeCaseDescription;

  @ApiModelProperty("其他时间")
  private Date otherDate;

  @ApiModelProperty("其他说明")
  private String otherDescription;

  @ApiModelProperty("执行状态 apply:已申请 unapply:未申请")
  private RecoveryExecuteRequest.ExecuteTypeEnum executeType;

  @ApiModelProperty("执行案号")
  private String executeCode;

  @ApiModelProperty("执行法院")
  private String executeCourt;

  @ApiModelProperty("申请执行时间")
  private Date applyExecuteDate;

  @ApiModelProperty("执行员")
  private String executer;

  @ApiModelProperty("联系电话")
  private String executerTelphone;

  @ApiModelProperty("执行裁定下达时间")
  private String executeRulingIssuanceTime;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-04 10:35:25
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
