package com.jk.asset.model.entity.property;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.property.PropertyInfoRequest.PropertyStateEnum;
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
 * 资产信息对象
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("property_info")
@ApiModel(description = "资产信息对象")
public class PropertyInfo extends BaseTenantDO {

  @ApiModelProperty("资产状态")
  private PropertyStateEnum propertyState;

  @ApiModelProperty("资产名称")
  private String propertyName;

  @ApiModelProperty("资产标签")
  private String propertyTag;

  @ApiModelProperty("大类")
  private Long type;

  @ApiModelProperty("资产分类")
  private Long propertyType;

  @ApiModelProperty("资产类型")
  private Long sourceType;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("反担保id")
  private Long reveId;

  @ApiModelProperty("资产地址_省")
  private Long province;

  @ApiModelProperty("资产地址_市")
  private Long city;

  @ApiModelProperty("资产地址_区")
  private Long district;

  @ApiModelProperty("详细地址")
  private String address;

  @ApiModelProperty("资产标签")
  private String label;

  @ApiModelProperty("资产取得方式")
  private Long accessWay;

  @ApiModelProperty("资产描述")
  private String propertyDescribe;

  @ApiModelProperty("面积")
  private BigDecimal area;

  @ApiModelProperty("资产用途")
  private String assetUse;

  @ApiModelProperty("产权证号")
  private String propertyCode;

  @ApiModelProperty("权证到期日")
  private Date propertyEndDate;

  @ApiModelProperty("资产过户日期")
  private Date propertyTransferOwnership;

  @ApiModelProperty("资产过户备注")
  private String transferOwnershipRemark;

  @ApiModelProperty("资产抵债价格")
  private BigDecimal debtRepaymentMoney;

  @ApiModelProperty("资产原值")
  private BigDecimal originalValue;

  @ApiModelProperty("资产净值")
  private BigDecimal netWorth;

  @ApiModelProperty("资产处置价格")
  private BigDecimal disposalPrice;

  @ApiModelProperty("税费")
  private BigDecimal taxeFee;

  @ApiModelProperty("处置资产支付税费")
  private BigDecimal disposeFee;

  @ApiModelProperty("原债务人费用")
  private BigDecimal originalObligorFee;

  @ApiModelProperty("资产盈亏")
  private BigDecimal profitAndLoss;

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

  @ApiModelProperty("资产跟进人")
  private String followUpPerson;

  @ApiModelProperty("产权人名称")
  private String propertyOwner;

  @ApiModelProperty("资产获得时间")
  private Date assertTime;

  @ApiModelProperty("是否拆分")
  private boolean separable;

  @TableField(exist = false)
  @ApiModelProperty("运营费用")
  private BigDecimal yyMoney;
  @TableField(exist = false)
  @ApiModelProperty("转让收入")
  private BigDecimal zrMoney;
  @TableField(exist = false)
  @ApiModelProperty("租赁收入")
  private BigDecimal zlMoney;

  @ApiModelProperty("所属单位")
  private Long affiliatedUnit;

  @ApiModelProperty("用地性质（国有出让地，国有划拨地）")
  private Long landUseNature;


  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-10 09:47:37
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
