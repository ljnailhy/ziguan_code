package com.jk.asset.model.request.property;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.BillContractRequest;
import com.jk.asset.model.request.HangNetworkInfoRequest;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资产转让入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:13
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "资产转让入参")
public class AssetTransferRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("受让人")
  private Long customId;

  @ApiModelProperty("评估机构")
  private Long estimateId;

  @ApiModelProperty("评估价")
  private BigDecimal estimateMoney;

  @ApiModelProperty("评估有效期开始时间")
  private Date estimateDate;

  @ApiModelProperty("评估有效期截止时间")
  private Date estimateEndDate;

  @ApiModelProperty("应付代理费用")
  private BigDecimal agencyFee;

  @ApiModelProperty("实际支付代理费用")
  private BigDecimal realityAgencyFee;

  @ApiModelProperty("成交价")
  private BigDecimal dealMoney;

  @ApiModelProperty("成交时间")
  private Date dealDate;

  @ApiModelProperty("税费")
  private BigDecimal taxation;

  @ApiModelProperty("过户时间")
  private Date transferOwnershipDate;

  @ApiModelProperty("联系人")
  private String contacts;

  @ApiModelProperty("联系电话")
  private String contactsPhone;

  @ApiModelProperty("处置方案")
  private String programme;

  @ApiModelProperty("是否二次挂网")
  private Boolean isAgainAuction;

  @ApiModelProperty("中介机构")
  private Long intermediaryId;

  @ApiModelProperty("中介费用")
  private Long intermediaryFee;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("资产信息")
  private List<PropertyBillRequest> propertyBillRequestList;

  @ApiModelProperty("中介关联信息")
  private List<DocumentIntermediaryRequest> documentIntermediaryRequestList;

  @ApiModelProperty("中介客户线索关联表入参")
  private List<IntermediaryCustomerLeadRequest> intermediaryCustomerLeadRequestList;

  @ApiModelProperty("合同关联")
  private List<BillContractRequest> billContractRequestList;

  @ApiModelProperty("法拍过程")
  private List<HangNetworkInfoRequest> hangNetworkInfoRequestList;

  @ApiModelProperty("资产收入分配入参")
  List<AssetIncomeDistributionRequest> assetIncomeDistributionRequestList;

  @ApiModelProperty("转让产权信息")
  List<PropertyRightInfoRequest> propertyRightInfoRequests;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-13 09:53:13
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
