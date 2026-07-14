package com.jk.asset.model.dto.export;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.proceeding.RecoveryAdjustTrialRequest.AdjustTrialTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 诉讼台账导出出参
 *
 * @author Yuqiang Wu
 * @since 2024/8/9 009 19:41
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "诉讼台账导出出参")
@ExcelIgnoreUnannotated
public class LitigationLedgerExportDTO {

  @ApiModelProperty("项目名称")
  @ExcelProperty({"代偿项目基本情况", "项目名称"})
  private String projectName;

  @ApiModelProperty("行业分类")
  private Long industryType;

  @ApiModelProperty("行业分类名称")
  @ExcelProperty({"代偿项目基本情况", "行业分类"})
  private String industryTypeName;

  @ApiModelProperty("所属区域_省")
  private Long belongProvince;

  @ApiModelProperty("所属区域_市")
  private Long belongCity;

  @ApiModelProperty("所属区域_区")
  private Long belongDistrict;

  @ApiModelProperty("所属区域名称")
  @ExcelProperty({"代偿项目基本情况", "所属区域"})
  private String belongName;

  @ApiModelProperty("代偿时间")
  @ExcelProperty({"代偿项目基本情况", "代偿时间"})
  @DateTimeFormat("yyyy-MM-dd")
  private Date compensationDate;

  @ApiModelProperty("代偿金额(元)")
  @ExcelProperty({"代偿项目基本情况", "代偿金额(元)"})
  private BigDecimal compensationMoney;

  @ApiModelProperty("移交至保全部日期")
  @ExcelProperty({"代偿项目基本情况", "移交至保全部日期"})
  @DateTimeFormat("yyyy-MM-dd")
  private Date transferDate;

  @ApiModelProperty("诉讼利息(元)")
  @ExcelProperty({"诉讼请求", "利息(元)"})
  private BigDecimal interestLitigation;

  @ApiModelProperty("诉讼违约金(元)")
  @ExcelProperty({"诉讼请求", "违约金(元)"})
  private BigDecimal liquidatedDamagesLitigation;

  @ApiModelProperty("诉讼其他费用(元)")
  @ExcelProperty({"诉讼请求", "其他费用(元)"})
  private BigDecimal otherFeesLitigation;

  @ApiModelProperty("调解或审判类型")
  private AdjustTrialTypeEnum adjustTrialType;

  @ApiModelProperty("调解或审判类型名称")
  @ExcelProperty({"审批结果", "调解或审判类型"})
  private String adjustTrialTypeName;

  @ApiModelProperty("审判代偿金额(元)")
  @ExcelProperty({"审批结果", "代偿金额(元)"})
  private BigDecimal compensationAmountTrial;

  @ApiModelProperty("审判利息(元)")
  @ExcelProperty({"审批结果", "利息(元)"})
  private BigDecimal interestTrial;

  @ApiModelProperty("审判违约金(元)")
  @ExcelProperty({"审批结果", "违约金(元)"})
  private BigDecimal liquidatedDamagesTrial;

  @ApiModelProperty("审判其他费用(元)")
  @ExcelProperty({"审批结果", "其他费用(元)"})
  private BigDecimal otherFeesTrial;

  @ApiModelProperty("CASH:现金(元)")
  @ExcelProperty({"回款信息", "现金(元)"})
  private BigDecimal cashMoney;

  @ApiModelProperty("RE_GUARANTEE:再担保(元)")
  @ExcelProperty({"回款信息", "再担保(元)"})
  private BigDecimal reGuaranteeMoney;

  @ApiModelProperty("MORTGAGE:抵押(元)")
  @ExcelProperty({"回款信息", "抵押(元)"})
  private BigDecimal mortgageMoney;

  @ApiModelProperty("SILVER_BILL银票(元)")
  @ExcelProperty({"回款信息", "银票(元)"})
  private BigDecimal silverBillMoney;

}
