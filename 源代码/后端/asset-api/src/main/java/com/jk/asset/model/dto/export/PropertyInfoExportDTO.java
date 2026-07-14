package com.jk.asset.model.dto.export;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.annotation.ExcelMerge;
import com.jk.asset.model.request.property.PropertyInfoRequest.PropertyStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产台账导出出参
 *
 * @author Yuqiang Wu
 * @since 2024/8/9 009 19:41
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "资产台账导出出参")
@ExcelIgnoreUnannotated
@ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER, horizontalAlignment = HorizontalAlignmentEnum.CENTER,wrapped = BooleanEnum.TRUE)
public class PropertyInfoExportDTO {


  @ApiModelProperty("资产id")
  @ExcelMerge(isPrimaryKey = true)
  private Long id;

  @ApiModelProperty("资产名称")
  @ExcelProperty({"资产信息", "资产名称"})
  @ExcelMerge
  private String propertyName;

  @ApiModelProperty("大类")
  private Long type;

  @ApiModelProperty("大类名称")
  @ExcelProperty({"资产信息", "大类"})
  @ExcelMerge
  private String typeName;

  @ApiModelProperty("资产分类")
  private Long propertyType;

  @ApiModelProperty("资产分类名称")
  @ExcelProperty({"资产信息", "资产分类"})
  @ExcelMerge
  private String propertyTypeName;

  @ApiModelProperty("来源方式")
  private Long sourceType;

  @ApiModelProperty("来源方式名称")
  @ExcelProperty({"资产信息", "来源方式"})
  @ExcelMerge
  private String sourceTypeName;

  @ApiModelProperty("来源项目")
  @ExcelProperty({"资产信息", "来源项目"})
  @ExcelMerge
  private String projectName;

  @ApiModelProperty("关联反担保措施")
  @ExcelProperty({"资产信息", "关联反担保措施"})
  @ExcelMerge
  private String reveName;

  @ApiModelProperty("资产地址_省")
  private Long province;

  @ApiModelProperty("资产地址_市")
  private Long city;

  @ApiModelProperty("资产地址_区")
  private Long district;

  @ApiModelProperty("所在区域")
  @ExcelProperty({"资产信息", "所在区域"})
  @ExcelMerge
  private String regionName;

  @ApiModelProperty("详细地址")
  @ExcelProperty({"资产信息", "详细地址"})
  @ExcelMerge
  private String address;

  @ApiModelProperty("资产标签")
  private String propertyTag;

  @ApiModelProperty("资产标签名称")
  @ExcelProperty({"资产信息", "资产标签"})
  @ExcelMerge
  private String propertyTagName;

  @ApiModelProperty("资产取得方式")
  private Long accessWay;

  @ApiModelProperty("资产取得方式名称")
  @ExcelProperty({"资产信息", "资产取得方式"})
  @ExcelMerge
  private String accessWayName;

  @ApiModelProperty("面积")
  @ExcelProperty({"资产信息", "面积(m²)"})
  @ExcelMerge
  private BigDecimal area;

  @ApiModelProperty("资产跟进人")
  @ExcelProperty({"资产信息", "资产跟进人"})
  @ExcelMerge
  private String followUpPerson;

  @ApiModelProperty("用地性质（国有出让地，国有划拨地）")
  private Long landUseNature;

  @ApiModelProperty("用地性质（国有出让地，国有划拨地）")
  @ExcelProperty({"资产信息", "用地性质"})
  @ExcelMerge
  private String landUseNatureName;

  @ApiModelProperty("资产获得时间")
  @ExcelProperty({"资产信息", "资产获得时间"})
  @DateTimeFormat("yyyy-MM-dd")
  @ExcelMerge
  private Date assertTime;

  @ApiModelProperty("所属单位")
  private Long affiliatedUnit;

  @ApiModelProperty("所属单位")
  @ExcelProperty({"资产信息", "所属单位"})
  @ExcelMerge
  private String affiliatedUnitName;

  @ApiModelProperty("资产状态")
  private PropertyStateEnum propertyState;

  @ApiModelProperty("资产状态名称 'IDLE'：空闲,'OCCUPY'：占用,'LEASED'：已出租,'TRANSFERRED'：已转让,'NOT_TRANSFERRED'：未转让,'NOT_LEASED'：未出租")
  @ExcelProperty({"资产信息", "资产状态"})
  @ExcelMerge
  private String propertyStateName;

  @ApiModelProperty("资产描述")
  @ExcelProperty({"资产信息", "资产描述"})
  @ExcelMerge
  private String propertyDescribe;

  @ApiModelProperty("资产备注")
  @ExcelProperty({"资产信息", "资产备注"})
  @ExcelMerge
  private String transferOwnershipRemark;

  @ApiModelProperty("资产抵债价格(元)")
  @ExcelProperty({"资产价值", "资产抵债价格(元)"})
  @ExcelMerge
  private BigDecimal debtRepaymentMoney;

  @ApiModelProperty("资产原值(元)")
  @ExcelProperty({"资产价值", "资产原值(元)"})
  @ExcelMerge
  private BigDecimal originalValue;

  @ApiModelProperty("资产净值(元)")
  @ExcelProperty({"资产价值", "资产净值(元)"})
  @ExcelMerge
  private BigDecimal netWorth;

  @ApiModelProperty("资产处置价格(元)")
  @ExcelProperty({"资产价值", "资产处置价格(元)"})
  @ExcelMerge
  private BigDecimal disposalPrice;

  @ApiModelProperty("获得资产支付税费(元)")
  @ExcelProperty({"资产价值", "获得资产支付税费(元)"})
  @ExcelMerge
  private BigDecimal taxeFee;

  @ApiModelProperty("处置资产支付税费(元)")
  @ExcelProperty({"资产价值", "处置资产支付税费(元)"})
  @ExcelMerge
  private BigDecimal disposeFee;

  @ApiModelProperty("获得资产支付的其他费用(元)")
  @ExcelProperty({"资产价值", "获得资产支付的其他费用(元)"})
  @ExcelMerge
  private BigDecimal originalObligorFee;

  @ApiModelProperty("资产盈亏(元)")
  @ExcelProperty({"资产价值", "资产盈亏(元)"})
  @ExcelMerge
  private BigDecimal profitAndLoss;

  @ApiModelProperty("权证号/编号")
  @ExcelProperty({"产权信息", "权证号/编号"})
  private String propertyCode;

  @ApiModelProperty("资产单元状态 (在途，占用，闲置，已租赁，已转让)")
  private Long assetUnitState;

  @ApiModelProperty("资产单元状态 (在途，占用，闲置，已租赁，已转让)")
  @ExcelProperty({"产权信息", "资产单元状态"})
  private String assetUnitStateStr;

  @ApiModelProperty("资产原值")
  @ExcelProperty({"产权信息", "资产原值（元）"})
  private BigDecimal originalValueEntries;

  @ApiModelProperty("面积(m²)")
  @ExcelProperty({"产权信息", "面积(m²)"})
  private BigDecimal areaEntries;

  @ApiModelProperty("产权人名称")
  @ExcelProperty({"产权信息", "产权人名称"})
  private String propertyOwner;

  @ApiModelProperty("权证到期日")
  @ExcelProperty({"产权信息", "权证到期日"})
  @DateTimeFormat("yyyy-MM-dd")
  private Date propertyEndDate;

  @ApiModelProperty("资产登记日期")
  @ExcelProperty({"产权信息", "资产登记日期"})
  @DateTimeFormat("yyyy-MM-dd")
  private Date propertyTransferOwnership;

  @ApiModelProperty("资产用途")
  @ExcelProperty({"产权信息", "资产用途"})
  private String assetUse;

  @ApiModelProperty("详细地址")
  @ExcelProperty({"产权信息", "详细地址"})
  private String addressEntries;

  @ApiModelProperty("备注")
  @ExcelProperty({"产权信息", "备注"})
  private String remark;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

}
