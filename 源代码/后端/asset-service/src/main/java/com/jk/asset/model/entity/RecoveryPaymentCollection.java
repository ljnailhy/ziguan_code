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
 * 回款表对象
 *
 * @author wangtao
 * @since 2024-06-21 10:00:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("recovery_payment_collection")
@ApiModel(description = "回款表对象")
public class RecoveryPaymentCollection extends BaseTenantDO {

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("律所名称")
  private String lawyerName;

  @ApiModelProperty("律所id")
  private Long lawyerId;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("合计回款金额(元)")
  private BigDecimal collectionAmount;

  @ApiModelProperty("回款时间")
  private Date collectionDate;

  @ApiModelProperty("回款状态 COLLECTION_STATUS")
  private Long collectionStatus;

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

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensatoryCash;

  @ApiModelProperty("违约金")
  private BigDecimal defaultCash;

  @ApiModelProperty("利息")
  private BigDecimal interest;

  @ApiModelProperty("其他费用")
  private BigDecimal otherFee;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-06-21 10:00:28
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
