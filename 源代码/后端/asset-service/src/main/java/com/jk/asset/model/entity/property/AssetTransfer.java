package com.jk.asset.model.entity.property;

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
 * 资产转让对象
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("asset_transfer")
@ApiModel(description = "资产转让对象")
public class AssetTransfer extends BaseTenantDO {

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

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("是否二次挂网")
  private Boolean isAgainAuction;

  @ApiModelProperty("中介机构")
  private Long intermediaryId;

  @ApiModelProperty("中介费用")
  private Long intermediaryFee;

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

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
