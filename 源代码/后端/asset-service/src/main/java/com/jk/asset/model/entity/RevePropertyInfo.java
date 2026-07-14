package com.jk.asset.model.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.RevePropertyInfoRequest;
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
 * 反担保/其他财产线索信息对象
 *
 * @author wangtao
 * @since 2024-06-24 09:42:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("reve_property_info")
@ApiModel(description = "反担保/其他财产线索信息对象")
public class RevePropertyInfo extends BaseTenantDO {

  @ApiModelProperty("来源id")
  private Long sourceId;

  @ApiModelProperty("单据类型（其他财产，反担保）")
  private String billType;

  @ApiModelProperty("对象Id")
  private Long doId;

  @ApiModelProperty("对象类型")
  private String doType;

  @ApiModelProperty("担保方式")
  private Long securityWay;

  @ApiModelProperty("担保类型")
  private String securityType;

  @ApiModelProperty("反担保人名称")
  private String reveName;

  @ApiModelProperty("反担保措施/其他财产")
  private String reveMeasure;

  @ApiModelProperty("是否已处置")
  private Boolean isDispose;

  @ApiModelProperty("处置回款金额")
  private BigDecimal disposeMoney;

  @ApiModelProperty("保全日期")
  private Date preserveDate;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("裁定以资抵债日期")
  private Date debtRepaymentDate;

  @ApiModelProperty("裁定抵债金额")
  private BigDecimal debtRepaymentMoney;

  @ApiModelProperty("保全类型")
  private RevePropertyInfoRequest.PreserveTypeEnum preserveType;

  @ApiModelProperty("保全申请提交时间")
  private Date preserveApplyDate;

  @ApiModelProperty("保全裁定送达时间")
  private Date preserveRuleDate;

  @ApiModelProperty("保险费")
  private BigDecimal insureMoney;

  @ApiModelProperty("是否删除")
  private Boolean isDeleted;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-06-24 09:42:39
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
