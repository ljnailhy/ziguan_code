package com.jk.asset.model.dto.export;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.annotation.ExcelMerge;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目台账导出出参
 *
 * @author Yuqiang Wu
 * @since 2024/8/11 011 15:42
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "项目台账导出出参")
@ExcelIgnoreUnannotated
@ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER, horizontalAlignment = HorizontalAlignmentEnum.CENTER,wrapped = BooleanEnum.TRUE)
public class ProjectLedgerExportDTO {

    @ApiModelProperty("项目id")
    @ExcelMerge(isPrimaryKey = true)
    private Long id;

    @ApiModelProperty("项目名称")
    @ExcelProperty({"代偿项目基本情况", "项目名称"})
    @ExcelMerge
    private String projectName;

    @ApiModelProperty("行业分类")
    @ExcelProperty({"代偿项目基本情况", "行业分类"})
    @ExcelMerge
    private String industryType;

    @ApiModelProperty("所属区域_省市区")
    @ExcelProperty({"代偿项目基本情况", "所属区域"})
    @ExcelMerge
    private String belongCity;

    @ApiModelProperty("代偿时间")
    @ExcelProperty({"代偿项目基本情况", "代偿时间"})
    @ExcelMerge
    @DateTimeFormat("yyyy-MM-dd")
    private Date compensationDate;

    @ApiModelProperty("代偿金额(元)")
    @ExcelProperty({"代偿项目基本情况", "代偿金额(元)"})
    @ExcelMerge
    private BigDecimal compensationMoney;

    @ApiModelProperty("移交至保全部日期")
    @ExcelProperty({"代偿项目基本情况", "移交至保全部日期"})
    @ExcelMerge
    @DateTimeFormat("yyyy-MM-dd")
    private Date transferDate;

    /******************** 反担保处置情况/其他财产线索start *************************/

    @ApiModelProperty("担保方式")
//    @ExcelProperty({"反担保处置情况/其他财产线索", "担保方式"})
    private String securityWayStr;

    @ApiModelProperty("反担保人名称")
//    @ExcelProperty({"反担保处置情况/其他财产线索", "反担保人名称"})
    private String reveName;

    @ApiModelProperty("担保类型")
//    @ExcelProperty({"反担保处置情况/其他财产线索", "担保类型"})
    private String securityType;

    @ApiModelProperty("反担保措施/其他财产")
    @ExcelProperty({"反担保处置情况/其他财产线索", "反担保措施/财产线索"})
    private String reveMeasure;

    @ApiModelProperty("保全日期")
//    @ExcelProperty({"反担保处置情况/其他财产线索", "保全日期"})
    private Date preserveDate;

    @ApiModelProperty("裁定以资抵债日期")
    @ExcelProperty({"反担保处置情况/其他财产线索", "裁定以资抵债日期"})
    @DateTimeFormat("yyyy-MM-dd")
    private Date debtRepaymentDate;

    @ApiModelProperty("裁定抵债金额")
    @ExcelProperty({"反担保处置情况/其他财产线索", "裁定抵债金额"})
    private BigDecimal debtRepaymentMoney;

    @ApiModelProperty("是否已处置")
    @ExcelProperty({"反担保处置情况/其他财产线索", "是否已处置"})
    private Boolean isDispose;

    @ApiModelProperty("处置回款金额")
    @ExcelProperty({"反担保处置情况/其他财产线索", "处置回款金额"})
    private BigDecimal disposeMoney;

    /******************** 反担保处置情况/其他财产线索end *************************/

    @ApiModelProperty("保全经理")
    @ExcelProperty({"追偿工作责任人", "保全经理"})
    @ExcelMerge
    private String manage;

    @ApiModelProperty("代理律所")
    @ExcelProperty({"追偿工作责任人", "代理律所"})
    @ExcelMerge
    private String name;

    @ApiModelProperty("代理律师")
    @ExcelProperty({"追偿工作责任人", "代理律师"})
    @ExcelMerge
    private String lawyerName;

    @ApiModelProperty("代理合同到期时间")
    @ExcelProperty({"追偿工作责任人", "代理合同到期时间"})
    @ExcelMerge
    @DateTimeFormat("yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty("A角")
    @ExcelProperty({"业务主要责任人", "A角"})
    @ExcelMerge
    private String aname;

    @ApiModelProperty("B角")
    @ExcelProperty({"业务主要责任人", "B角"})
    @ExcelMerge
    private String bname;

    @ApiModelProperty("被告人(被执行人)")
    @ExcelProperty("被告人(被执行人)")
    @ExcelMerge
    private String defendant;

    @ApiModelProperty("诉讼时效")
    @ExcelProperty("诉讼时效")
    @ExcelMerge
    @DateTimeFormat("yyyy-MM-dd")
    private Date proceedingAgeingDate;

    @ApiModelProperty("立案日期")
    @ExcelProperty("立案日期")
    @ExcelMerge
    @DateTimeFormat("yyyy-MM-dd")
    private Date fillingDate;

    @ApiModelProperty("保全日期")
    @ExcelProperty("保全日期")
    @ExcelMerge
    @DateTimeFormat("yyyy-MM-dd")
    private Date detailsDate;

    @ApiModelProperty("开庭日期")
    @ExcelProperty("开庭日期")
    @ExcelMerge
    @DateTimeFormat("yyyy-MM-dd")
    private Date courtSessionDate;

    @ApiModelProperty("判决日期")
    @ExcelProperty("判决日期")
    @ExcelMerge
    @DateTimeFormat("yyyy-MM-dd")
    private Date judgeDate;

    @ApiModelProperty("保全案号")
    @ExcelProperty({"案号", "保全案号"})
    @ExcelMerge
    private String preservationCode;

    @ApiModelProperty("判决案号")
    @ExcelProperty({"案号", "判决案号"})
    @ExcelMerge
    private String adjustCode;

    @ApiModelProperty("执行案号")
    @ExcelProperty({"案号", "执行案号"})
    @ExcelMerge
    private String executeCode;

    @ApiModelProperty("执行时效（调解审批判决日期）")
    @ExcelProperty("执行时效")
    @ExcelMerge
    @DateTimeFormat("yyyy-MM-dd")
    private Date adjustTrialDate;

    @ApiModelProperty("现金")
    @ExcelProperty({"回款金额", "现金"})
    @ExcelMerge
    private BigDecimal cashAmount;

    @ApiModelProperty("再担保")
    @ExcelProperty({"回款金额", "再担保"})
    @ExcelMerge
    private BigDecimal reGuaranteeAmount;

    @ApiModelProperty("抵债")
    @ExcelProperty({"回款金额", "抵债"})
    @ExcelMerge
    private BigDecimal mortgageAmount;

    @ApiModelProperty("银票")
    @ExcelProperty({"回款金额", "银票"})
    @ExcelMerge
    private BigDecimal silverBillAmount;

    @ApiModelProperty("合计回款金额")
    @ExcelProperty("合计回款金额")
    @ExcelMerge
    private BigDecimal recoveryCollectionAmount;

    @ApiModelProperty("代偿余额(元)")
    @ExcelProperty("代偿余额(元)")
    @ExcelMerge
    private BigDecimal residueRecoveryAmount;

    @ApiModelProperty("诉讼杂费(元) 诉讼费")
    @ExcelProperty({"诉讼杂费(元)", "诉讼费"})
    @ExcelMerge
    private BigDecimal payType01;

    @ApiModelProperty("诉讼杂费(元) 保全费")
    @ExcelProperty({"诉讼杂费(元)", "保全费"})
    @ExcelMerge
    private BigDecimal payType02;

    @ApiModelProperty("诉讼杂费(元) 保险费")
    @ExcelProperty({"诉讼杂费(元)", "保险费"})
    @ExcelMerge
    private BigDecimal payType03;

    @ApiModelProperty("诉讼杂费(元) 其他费用")
    @ExcelProperty({"诉讼杂费(元)", "其他费用"})
    @ExcelMerge
    private BigDecimal payType04;

    @ApiModelProperty("诉讼杂费(元) 律师费用")
    @ExcelProperty({"诉讼杂费(元)", "律师费用"})
    @ExcelMerge
    private BigDecimal payType05;

    @ApiModelProperty(value = "核销时间", required = true)
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty({"核销项目信息", "核销时间"})
    @ExcelMerge
    private Date writeOffDate;

    @ApiModelProperty("核销金额")
    @ExcelProperty({"核销项目信息", "核销金额(元)"})
    @ExcelMerge
    private BigDecimal writeDffAmount;

    @ApiModelProperty("核销项目分类 A:A B:B C:C D:D")
    @ExcelProperty({"核销项目信息", "核销项目分类"})
    @ExcelMerge
    private String writeOffClassification;

}
