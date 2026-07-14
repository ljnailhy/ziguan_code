package com.jk.asset.model.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.ProjectBusinessInfoRequest.BusinessTypeEnum;
import com.jk.common.model.entity.BaseTenantDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 业务信息表对象
 *
 * @author wangtao
 * @since 2024-07-26 18:51:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("project_business_info")
@ApiModel(description = "业务信息表对象")
public class ProjectBusinessInfo extends BaseTenantDO {

  @ApiModelProperty("项目Id")
  private Long projectId;

  @ApiModelProperty("业务编号")
  private String relatedBusNo;

  @ApiModelProperty("业务类型 线上审批：YBXZCN;上会审批：YBXZCW;见贷即保：JDJB；")
  private BusinessTypeEnum businessType;

  @ApiModelProperty("产品名称")
  private Long productName;

  @ApiModelProperty("大类")
  private Long type;

  @ApiModelProperty("风险归类")
  private Long ristTypeAfterGuarantee;

  @ApiModelProperty("分险比例（债权人）")
  private BigDecimal dividedInsuranceDebtor;

  @ApiModelProperty("分险比例（原担保）")
  private BigDecimal dividedInsuranceSecurity;

  @ApiModelProperty("分险比例（再担保）")
  private BigDecimal dividedInsuranceAgainSecurity;

  @ApiModelProperty("分险比例（其他）")
  private BigDecimal dividedInsuranceOther;

  @ApiModelProperty("风补基金")
  private Long riskCompensation;

  @ApiModelProperty("a角")
  private Long aid;

  @ApiModelProperty("A角名称")
  private String aname;

  @ApiModelProperty("A角账号")
  private String acode;

  @ApiModelProperty("b角")
  private Long bid;

  @ApiModelProperty("B角账号")
  private String bcode;

  @ApiModelProperty("B角名称")
  private String bname;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-07-26 18:51:12
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
