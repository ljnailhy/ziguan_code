package com.jk.asset.model.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * 项目移交明细对象
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("project_transfer_detailed")
@ApiModel(description = "对象")
public class ProjectTransferDetailed extends BaseTenantDO {

  @ApiModelProperty("项目移交id")
  private Long transferId;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensationMoney;

  @ApiModelProperty("代偿时间")
  private Date compensationDate;

  @ApiModelProperty("保全经理")
  private String manage;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("律师")
  private String lawyers;

  @ApiModelProperty("关联合同")
  private Long contractId;

  @ApiModelProperty("回款金额")
  private BigDecimal collectionAmount;

  @ApiModelProperty("剩余代偿金额")
  private BigDecimal residueCompensation;

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

  @ApiModelProperty("原项目归属方")
  private Long primaryAffiliatedOrg;

  @ApiModelProperty("现在项目归属方")
  private Long affiliatedOrg;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-17 18:27:56
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
