package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.enums.OperationTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目移交明细入参
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "入参")
public class ProjectTransferDetailedRequest {

  @ApiModelProperty("主键")
  private Long id;

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

  @ApiModelProperty("原项目归属方")
  private Long primaryAffiliatedOrg;

  @ApiModelProperty("现在项目归属方")
  private Long affiliatedOrg;

  @ApiModelProperty("分录状态：新增ADD，修改UPDATE，删除DELETE")
  private OperationTypeEnum operateType;

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
