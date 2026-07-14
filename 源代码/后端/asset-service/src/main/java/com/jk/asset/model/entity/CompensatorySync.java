package com.jk.asset.model.entity;

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

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目同步接收表对象
 *
 * @author wangtao
 * @since 2024-07-25 16:40:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("compensatory_sync")
@ApiModel(description = "项目同步接收表对象")
public class CompensatorySync extends BaseTenantDO {

  @ApiModelProperty("项目代码 项目唯一标识")
  private String businessNo;

  @ApiModelProperty("是否已同步")
  private Boolean syncStatus;

  @ApiModelProperty("业务类型  线上审批：YBXZCN；	上会审批：YBXZCW；	见贷即保：JDJB；")
  private String businessType;

  @ApiModelProperty("产品名称")
  private String productName;

  @ApiModelProperty("债务人名称")
  private String customerName;

  @ApiModelProperty("债务人性质")
  private String customerProperty;

  @ApiModelProperty("证件类型")
  private String credentialType;

  @ApiModelProperty("证件号码")
  private String credentialNo;

  @ApiModelProperty("是否首次银行贷款  是：Y；	否：N;")
  private String isFirstLoanAccount;

  @ApiModelProperty("项目来源")
  private String projectFrom;

  @ApiModelProperty("区域(省)")
  private Long province;

  @ApiModelProperty("区域(市)")
  private Long city;

  @ApiModelProperty("区域(县|区)")
  private Long area;

  @ApiModelProperty("合作银行")
  private String cooperativeBank;

  @ApiModelProperty("合作银行(支行)")
  private String cooperativeBankThird;

  @ApiModelProperty("行业分类（工信部）")
  private String industryGxb;

  @ApiModelProperty("行业分类（金融局）")
  private String industryJrj;

  @ApiModelProperty("行业分类四级（国标）")
  private String industryGxw;

  @ApiModelProperty("从业人数")
  private Integer employedPopulation;

  @ApiModelProperty("资产总额[元]")
  private BigDecimal totalAssets;

  @ApiModelProperty("营业收入[元]")
  private BigDecimal businessRevenue;

  @ApiModelProperty("缴纳税收（元）")
  private BigDecimal taxAmount;

  @ApiModelProperty("企业划型")
  private String enterpriseSize;

  @ApiModelProperty("政策扶持领域")
  private String industryPolicySupport;

  @ApiModelProperty("债权起始日期")
  private Date debtBillStartDay;

  @ApiModelProperty("债权到期日期")
  private Date debtBillDueDay;

  @ApiModelProperty("银行放款金额")
  private BigDecimal debtAmount;

  @ApiModelProperty("贷款利率[%]")
  private BigDecimal loanRate;

  @ApiModelProperty("借款合同号")
  private String principleClaimContractNo;

  @ApiModelProperty("保证合同号")
  private String warrantyContractNo;

  @ApiModelProperty("委保合同号")
  private String guaranteeContractNo;

  @ApiModelProperty("借据号（或其他唯一标识号）")
  private String debtBillNo;

  @ApiModelProperty("担保费率（年）[%]")
  private BigDecimal guaranteeFeeRate;

  @ApiModelProperty("反担保方式")
  private String counterGuaranteeType;

  @ApiModelProperty("风险归类")
  private String ristTypeAfterGuarantee;

  @ApiModelProperty("分险比例（债权人）")
  private BigDecimal riskRateBank;

  @ApiModelProperty("分险比例（原担保）")
  private BigDecimal riskRateOrigin;

  @ApiModelProperty("分险比例（再担保）")
  private BigDecimal riskRateAgain;

  @ApiModelProperty("分险比例（其他）")
  private BigDecimal riskRateOther;

  @ApiModelProperty("备注信息")
  private String remark;

  @ApiModelProperty("关联企业")
  private String relationEnterprise;

  @ApiModelProperty("关联企业社会信用统一代码")
  private String relationEnterpriseNo;

  @ApiModelProperty("联系人（或法人）")
  private String contact;

  @ApiModelProperty("联系人电话")
  private String tel;

  @ApiModelProperty("担保公司A角")
  private String businessManagerA;

  @ApiModelProperty("A角账号")
  private String acode;

  @ApiModelProperty("A角名称")
  private String aname;

  @ApiModelProperty("担保公司B角")
  private String assistB;

  @ApiModelProperty("B角账号")
  private String bcode;

  @ApiModelProperty("B角名称")
  private String bname;

  @ApiModelProperty("代偿日期")
  private Date repaymentDate;

  @ApiModelProperty("还款银行")
  private String repaymentBank;

  @ApiModelProperty("代偿金额")
  private BigDecimal repaymentAmount;

  @ApiModelProperty("注册地址/户籍所在地")
  private String registeredAddress;

  @ApiModelProperty("办公地址/现居住地址")
  private String currentAddress;

  @ApiModelProperty("风补基金")
  private String riskFund;

  @ApiModelProperty("反担保措施")
  private String antiRemark;

  @ApiModelProperty("业务编号")
  private String relatedBusNo;

  @ApiModelProperty("核保编号")
  private String relatedGuaranteeNo;

  @ApiModelProperty("经办部门")
  private String createdDeptIds;

  @ApiModelProperty("经办公司")
  private String createdCompanyId;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-07-25 16:40:16
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
