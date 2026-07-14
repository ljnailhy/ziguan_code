package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * 回款表入参
 *
 * @author wangtao
 * @since 2024-06-20 18:53:02
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "回款表入参")
public class RecoveryPaymentCollectionRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("备注")
  private String remarks;
  @ApiModelProperty("律所名称")
  private String lawyerName;

  @ApiModelProperty("律所id")
  private Long lawyerId;

  @ApiModelProperty("回款状态")
  private Long collectionStatus;

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("合计回款金额(元)")
  private BigDecimal collectionAmount;

  @ApiModelProperty("回款时间")
  private Date collectionDate;

  @ApiModelProperty("回款标记 CASH:现金 RE_GUARANTEE:再担保 MORTGAGE:抵押 SILVER_BILL银票")
  private String collectionSign;

  @ApiModelProperty("项目回款归属 	GROUP集团	CULTURAL_TOURISM文旅 	SMALL_MEDIUM_DAN中小担 	VER_SMALL_MEDIUM 核销中小担 	VER_GROUP 核销-集团	VER_CULTURAL_TOURISM 核销-文旅")
  private String collectionAscription;

  @ApiModelProperty("回款类型：OWN自主追偿、ENTRUST委托追偿")
  private String collectionType;

  @ApiModelProperty("是否历史代偿 1 是 0 否")
  private Boolean isCollectionHistorical;

  @ApiModelProperty("回款摘要")
  private String collectionSummary;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensatoryCash;

  @ApiModelProperty("违约金")
  private BigDecimal defaultCash;

  @ApiModelProperty("利息")
  private BigDecimal interest;

  @ApiModelProperty("其他费用")
  private BigDecimal otherFee;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileRequests;

  @ApiModelProperty("回款明细")
  private List<RecoveryPaymentCollectionDetailRequest> paymentCollectionDetailRequests;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  // @Override
  public boolean unverified() {
    // String校验：空org.apache.commons.lang3.StringUtils.isBlank 非空isNotBlank 批量-是否有空isAnyBlank 批量-都不空isNoneBlank
    // List校验：空org.apache.commons.collections4.CollectionUtils.isEmpty 非空isNotEmpty
    // 其他Object(或List)：空org.apache.commons.lang3.ObjectUtils.isEmpty 非空isNotEmpty 批量-都不空allNotNull 批量-都空allNull 批量-是否有不空anyNotNull 批量-是否有空anyNull
    // return super.unverified();
    if (projectName == null || collectionDate == null || collectionAscription == null || collectionSign == null){
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
