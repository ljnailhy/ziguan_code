package com.jk.asset.model.entity.property;

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
 * 资产收入分配对象
 *
 * @author wangshuai
 * @since 2024-07-13 09:48:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("asset_income_distribution")
@ApiModel(description = "资产收入分配对象")
public class AssetIncomeDistribution extends BaseTenantDO {

  @ApiModelProperty("对象Id")
  private Long doId;

  @ApiModelProperty("对象类型")
  private String doType;

  @ApiModelProperty("资产id")
  private Long propertyId;

  @ApiModelProperty("资产产权ID")
  private Long propertyRightInfoId;

  @ApiModelProperty("类型（收入/支出） 1 收入 0 支出")
  private Boolean propertyType;

  @ApiModelProperty("费用类型")
  private Long costType;

  @ApiModelProperty("收入/支出日期")
  private Date propertyDate;

  @ApiModelProperty("收入金额")
  private BigDecimal propertyMoney;

  @ApiModelProperty("备注")
  private String remark;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-13 09:48:45
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
