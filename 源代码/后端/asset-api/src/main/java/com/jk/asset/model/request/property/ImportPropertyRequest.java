package com.jk.asset.model.request.property;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资产导入入参
 *
 * @author WangShuai
 * @since 2024/7/15 9:28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "模板资产导入入参")
public class ImportPropertyRequest {

  @ApiModelProperty("资产名称")
  @ExcelProperty("资产名称")
  private String propertyName;

  @ApiModelProperty("大类")
  @ExcelProperty("大类")
  private String type;

  @ApiModelProperty("资产分类")
  @ExcelProperty("资产分类")
  private String propertyType;

  @ApiModelProperty("来源类型")
  @ExcelProperty("来源方式")
  private String sourceType;

  @ApiModelProperty("项目id")
  @ExcelProperty("来源项目")
  private String projectId;

  @ApiModelProperty("资产地址_省")
  @ExcelProperty("资产地址(省)")
  private String province;

  @ApiModelProperty("资产地址_市")
  @ExcelProperty("资产地址(市)")
  private String city;

  @ApiModelProperty("资产地址_区")
  @ExcelProperty("资产地址(区)")
  private String district;

  @ApiModelProperty("详细地址")
  @ExcelProperty("详细地址")
  private String address = "";

  @ApiModelProperty("资产取得方式")
  @ExcelProperty("资产取得方式")
  private String accessWay;

  @ApiModelProperty("资产跟进人")
  @ExcelProperty("资产跟进人")
  private String followUpPerson;

  @ApiModelProperty("资产跟进人账号")
  @ExcelProperty("资产跟进人账号")
  private String followUpPersonAccount = "";

  @ApiModelProperty("用地性质（国有出让地，国有划拨地）")
  @ExcelProperty("用地性质")
  private String landUseNature;

  @ApiModelProperty("资产获得时间")
  @ExcelProperty("资产获得时间")
  private String assertTime;

  @ApiModelProperty("所属单位")
  @ExcelProperty("所属单位")
  private String affiliatedUnit = "";

  @ApiModelProperty("资产状态")
  @ExcelProperty("资产状态")
  private String propertyState = PropertyInfoRequest.PropertyStateEnum.IDLE.getValue();


  @ApiModelProperty("资产描述")
  @ExcelProperty("资产描述")
  private String propertyDescribe = "";

  @ApiModelProperty("资产备注")
  @ExcelProperty("资产备注")
  private String transferOwnershipRemark = "";

//  @ApiModelProperty("资产用途")
//  @ExcelProperty("资产用途")
//  private String assetUse;

  @ApiModelProperty("资产抵债价格")
  @ExcelProperty("资产抵债价格(元)")
  private String debtRepaymentMoney;

  @ApiModelProperty("资产原值")
  @ExcelProperty("资产原值(元)")
  private String originalValue;

  @ApiModelProperty("资产净值")
  @ExcelProperty("资产净值(元)")
  private String netWorth;

  @ApiModelProperty("资产处置价格")
  @ExcelProperty("资产处置价格(元)")
  private String disposalPrice;

  @ApiModelProperty("获得资产支付税费")
  @ExcelProperty("获得资产支付税费(元)")
  private String taxeFee;

  @ApiModelProperty("处置资产支付税费(元)")
  @ExcelProperty("处置资产支付税费(元)")
  private String disposeFee;

  @ApiModelProperty("获得资产支付的其他费用")
  @ExcelProperty("获得资产支付的其他费用(元)")
  private String originalObligorFee;

  @ApiModelProperty("资产盈亏")
  @ExcelProperty("资产盈亏(元)")
  private String profitAndLoss;
  /** 分录  产权信息 **/

  @ApiModelProperty("权证号/编号")
  @ExcelProperty("权证号/编号")
  private String propertyCode = "";

  @ApiModelProperty("资产单元状态 (在途，占用，闲置，已租赁，已转让)")
  @ExcelProperty("资产单元状态")
  private String assetUnitState;

  @ApiModelProperty("资产原值")
  @ExcelProperty("资产原值（元）")
  private String originalValueEntries;

  @ApiModelProperty("面积")
  @ExcelProperty("面积(㎡)")
  private String area = "0.00";

  @ApiModelProperty("产权人名称")
  @ExcelProperty("产权人名称")
  private String propertyOwner;

  @ApiModelProperty("权证到期日")
  @ExcelProperty("权证到期日")
  private String propertyEndDate;

  @ApiModelProperty("资产过户日期")
  @ExcelProperty("资产登记日期")
  private String propertyTransferOwnership;

  @ApiModelProperty("资产用途")
  @ExcelProperty("资产用途")
  private String assetUse;

  @ApiModelProperty("坐落")
  @ExcelProperty("坐落")
  private String addressEntries;

  @ApiModelProperty("备注")
  @ExcelProperty("备注")
  private String remark;
}
