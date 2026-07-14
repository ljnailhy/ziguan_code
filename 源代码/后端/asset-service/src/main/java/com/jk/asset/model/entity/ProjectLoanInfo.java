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
 * 借款信息表对象
 *
 * @author wangtao
 * @since 2024-07-26 18:51:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("project_loan_info")
@ApiModel(description = "借款信息表对象")
public class ProjectLoanInfo extends BaseTenantDO {

  @ApiModelProperty("业务编号")
  private String relatedBusNo;


  @ApiModelProperty("借据号（或其他唯一标识号）")
  private String loanCode;

  @ApiModelProperty("债权起始日")
  private Date debtBeginDate;

  @ApiModelProperty("债权到期日")
  private Date debtEndDate;

  @ApiModelProperty("银行放款金额")
  private BigDecimal loanMoney;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensationMoney;

  @ApiModelProperty("代偿时间")
  private Date compensationDate;

  @ApiModelProperty("合作银行")
  private Long cooperateBank;

  @ApiModelProperty("合作银行（支行）")
  private String cooperateBankBranch;

  @ApiModelProperty("是否首次银行贷款  是：Y；	否：N;")
  private String isFirstLoanAccount;

  @ApiModelProperty("贷款利率[%]")
  private BigDecimal loanRate;

  @ApiModelProperty("借款合同号")
  private String loanPactCode;

  @ApiModelProperty("保证合同号")
  private String pledPactCode;

  @ApiModelProperty("委保合同号")
  private String pactCode;

  @ApiModelProperty("担保费率（年）")
  private BigDecimal guaranteeRate;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-07-26 18:51:24
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
