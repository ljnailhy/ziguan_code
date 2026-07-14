package com.jk.asset.model.dto.export;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 回款台账导出出参
 *
 * @author Yuqiang Wu
 * @since 2024/8/9 009 15:38
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "回款台账导出出参")
@ExcelIgnoreUnannotated
public class CollectionLedgerExportDTO {

    @ApiModelProperty("项目名称")
    @ExcelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("回款标记 CASH:现金 RE_GUARANTEE:再担保 MORTGAGE:抵押 SILVER_BILL银票")
    private String collectionSign;

    @ApiModelProperty("回款标记中文 CASH:现金 RE_GUARANTEE:再担保 MORTGAGE:抵押 SILVER_BILL银票")
    @ExcelProperty("回款标记")
    private String collectionSignName;

    @ApiModelProperty("回款金额(元)")
    @ExcelProperty("回款金额(元)")
    private BigDecimal collectionAmount;

    @ApiModelProperty("回款时间")
    @ExcelProperty("回款时间")
    @DateTimeFormat("yyyy-MM-dd")
    private Date collectionDate;

    @ApiModelProperty("是否历史代偿 1 是 0 否")
    @ExcelProperty("是否历史代偿 TRUE 是 FALSE 否")
    private Boolean isCollectionHistorical;

    @ApiModelProperty("项目回款归属 	GROUP集团	CULTURAL_TOURISM文旅 	SMALL_MEDIUM_DAN中小担 	VER_SMALL_MEDIUM 核销中小担 	VER_GROUP 核销-集团	VER_CULTURAL_TOURISM 核销-文旅")
    private String collectionAscription;

    @ApiModelProperty("项目回款归属中文 	GROUP集团	CULTURAL_TOURISM文旅 	SMALL_MEDIUM_DAN中小担 	VER_SMALL_MEDIUM 核销中小担 	VER_GROUP 核销-集团	VER_CULTURAL_TOURISM 核销-文旅")
    @ExcelProperty("项目回款归属")
    private String collectionAscriptionName;

    @ApiModelProperty("回款律所名字")
    @ExcelProperty("回款律所名字")
    private String lawyerName;

    @ApiModelProperty("回款摘要")
    @ExcelProperty("回款摘要")
    private String collectionSummary;

    @ApiModelProperty("利息(元)")
    @ExcelProperty("利息(元)")
    private BigDecimal interest;

    @ApiModelProperty("代偿金额(元)")
    @ExcelProperty("代偿金额(元)")
    private BigDecimal compensatoryCash;

    @ApiModelProperty("其他费用(元)")
    @ExcelProperty("其他费用(元)")
    private BigDecimal otherFee;

    @ApiModelProperty("违约金(元)")
    @ExcelProperty("违约金(元)")
    private BigDecimal defaultCash;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
