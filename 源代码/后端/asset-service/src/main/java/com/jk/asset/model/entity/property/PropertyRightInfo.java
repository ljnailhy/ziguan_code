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
 * 产权信息表对象
 *
 * @author wangtao
 * @since 2024-08-17 14:44:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("property_right_info")
@ApiModel(description = "产权信息表对象")
public class PropertyRightInfo extends BaseTenantDO {

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("产权证号")
  private String propertyCode;

  @ApiModelProperty("权证到期日")
  private Date propertyEndDate;

  @ApiModelProperty("产权人名称")
  private String propertyOwner;

  @ApiModelProperty("面积")
  private BigDecimal area;

  @ApiModelProperty("资产用途")
  private String assetUse;

  @ApiModelProperty("详细地址")
  private String address;

  @ApiModelProperty("资产过户日期")
  private Date propertyTransferOwnership;

  @ApiModelProperty("资产id")
  private Long propertyInfoId;

  @ApiModelProperty("资产单元状态 (在途，占用，闲置，已租赁，已转让)")
  private Long assetUnitState;
  @ApiModelProperty("用地性质（国有出让地，国有划拨地）")
  private Long landUseNature;
  @ApiModelProperty("原值")
  private BigDecimal originalValue;

  @ApiModelProperty("对象Id")
  private Long doId;

  @ApiModelProperty("对象类型")
  private String doType;

  @ApiModelProperty("来源权产ID")
  private Long parentId;

  @ApiModelProperty("转让金额")
  private BigDecimal propertyMoney;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-08-17 14:44:27
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
