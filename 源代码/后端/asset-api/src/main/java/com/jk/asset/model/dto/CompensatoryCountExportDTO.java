package com.jk.asset.model.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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

/**
 * @author wangTao
 * date2024/9/2 14:27
 * 追偿情况统计出参
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "追偿情况统计")
@ExcelIgnoreUnannotated
@ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER, horizontalAlignment = HorizontalAlignmentEnum.CENTER,wrapped = BooleanEnum.TRUE)
public class CompensatoryCountExportDTO {

    @ExcelMerge(isPrimaryKey = true)
    private String order;

    @ApiModelProperty("季度")
    @ExcelProperty({"时间", "季度"})
    private String season;

    @ApiModelProperty("月份")
    @ExcelProperty({"时间", "月份"})
    private String month;

    @ApiModelProperty("上年末追偿金额")
    @ExcelProperty({"追偿", "上年末追偿金额"})
    private BigDecimal lastYearCompensatoryMoney;

    @ApiModelProperty("本年新增功能追偿金额")
    @ExcelProperty({"追偿", "本年新增功能追偿金额"})
    private BigDecimal thisYearCompensatoryMoney;

    @ApiModelProperty("累计追偿金额-查所有项目")
    @ExcelProperty({"追偿", "累计追偿金额"})
    private BigDecimal totalCompensatoryMoney;

    @ApiModelProperty("历史风险项目")
    @ExcelProperty({"追偿", "其中：历史风险项目（2017年4月28日之前介入项目）"})
    private BigDecimal historyCompensatoryMoney;

    @ApiModelProperty("新增风险项目")
    @ExcelProperty({"追偿", "其中：新增项目（2017年4月28日之后介入项目）"})
    private BigDecimal addCompensatoryMoney;

    @ApiModelProperty("上年末回款金额")
    @ExcelProperty({"回款", "上年末回款金额"})
    private BigDecimal lastYearCollectionMoney;

    @ApiModelProperty("本年新增回款金额")
    @ExcelProperty({"回款", "本年新增回款金额"})
    private BigDecimal thisYearCollectionMoney;

    @ApiModelProperty("累计回款金额")
    @ExcelProperty({"回款", "累计回款金额"})
    private BigDecimal totalCollectionMoney;

    @ApiModelProperty("历史风险项目追偿回款")
    @ExcelProperty({"回款", "其中：历史风险项目追偿回款（2017年4月28日之前介入项目）"})
    private BigDecimal historyCollectionMoney;

    @ApiModelProperty("新增项目追偿回款")
    @ExcelProperty({"回款", "其中：新增项目追偿回款（2017年4月28日之后介入项目）"})
    private BigDecimal addCollectionMoney;

    @ApiModelProperty("上年末追偿余额")
    @ExcelProperty({"余额", "上年末追偿余额"})
    private BigDecimal lastYearBalance;

    @ApiModelProperty("追偿余额")
    @ExcelProperty({"余额", "追偿余额"})
    private BigDecimal totalBalance;
}
