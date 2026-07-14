package com.jk.asset.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.annotation.ExcelMerge;
import com.jk.asset.annotation.SupplierDateConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 反担保/其他财产线索信息入参
 *
 * @author wangtao
 * @since 2024-06-24 09:42:39
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "反担保/其他财产线索信息入参")
public class RevePropertyInfoExportDTO {

  @ApiModelProperty("担保方式")
  @ExcelProperty(value = "担保方式")
  @ExcelMerge(merge = true)
  private Long securityWay;

  @ApiModelProperty("担保类型")
  @ExcelMerge(merge = true)
  @ExcelProperty(value = "担保类型")
  private String securityType;

  @ApiModelProperty("反担保人名称")
  @ExcelMerge(merge = true)
  @ExcelProperty(value = "反担保人名称")
  private String reveName;

  @ApiModelProperty("反担保措施")
  @ExcelMerge(merge = true)
  @ExcelProperty(value = "反担保措施")
  private String reveMeasure;

  @ApiModelProperty("是否已处置")
  @ExcelMerge(merge = true)
  @ExcelProperty(value = "是否已处置")
  private Boolean isDispose;

  @ApiModelProperty("处置回款金额")
  @ExcelMerge(merge = true)
  @ExcelProperty(value = "处置回款金额")
  private BigDecimal disposeMoney;

  @ApiModelProperty("保全日期")
  @ExcelMerge(merge = true)
  @ExcelProperty(value = "保全日期",converter = SupplierDateConverter.class)
  private Date preserveDate;

  @ApiModelProperty("裁定以资抵债日期")
  @ExcelMerge(merge = true)
  @ExcelProperty(value = "裁定以资抵债日期",converter = SupplierDateConverter.class)
  private Date debtRepaymentDate;

  @ApiModelProperty("裁定抵债金额")
  @ExcelMerge(merge = true)
  @ExcelProperty(value = "裁定抵债金额")
  private BigDecimal debtRepaymentMoney;

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
