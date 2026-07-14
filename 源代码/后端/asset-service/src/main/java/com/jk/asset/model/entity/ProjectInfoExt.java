package com.jk.asset.model.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目信息详细信息扩展表对象
 *
 * @author wangtao
 * @since 2024-06-29 15:28:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("project_info_ext")
@ApiModel(description = "项目信息详细信息扩展表对象")
public class ProjectInfoExt extends BaseTenantDO {

  @ApiModelProperty("大类")
  private Long type;

  @ApiModelProperty("合作银行")
  private Long cooperateBank;

  @ApiModelProperty("合作银行（支行）")
  private String cooperateBankBranch;

  @ApiModelProperty("行业分类")
  private Long industryType;

  @ApiModelProperty("产品名称")
  private Long productName;

  @ApiModelProperty("企业划型 DXQY:大型企业 ZXQY:中型企业 XXQY:小型企业 WXQY:微型企业 QT:其他")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private Long bigSmall;

  @ApiModelProperty("政策扶持领域类别")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private String goverType;

  @ApiModelProperty("债权起始日")
  private Date debtBeginDate;

  @ApiModelProperty("债权到期日")
  private Date debtEndDate;

  @ApiModelProperty("银行放款金额")
  private BigDecimal loanMoney;

  @ApiModelProperty("委保合同号")
  private String pactCode;

  @ApiModelProperty("借据号")
  private String loanCode;

  @ApiModelProperty("担保费率（年）")
  private BigDecimal guaranteeRate;

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

  @ApiModelProperty("借款合同号")
  private String loanPactCode;

  @ApiModelProperty("关联企业")
  private String relationEnterprise;

  @ApiModelProperty("关联企业社会信用统一代码")
  private String relationEnterpriseNo;

  @ApiModelProperty("保证合同号")
  private String pledPactCode;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-06-29 15:28:07
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
