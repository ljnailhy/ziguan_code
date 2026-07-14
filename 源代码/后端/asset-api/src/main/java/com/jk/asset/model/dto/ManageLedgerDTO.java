package com.jk.asset.model.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangTao
 * date2024/7/16 9:58
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "保全经理台账出参")
@ExcelIgnoreUnannotated
public class ManageLedgerDTO {

    @ApiModelProperty("项目Id")
    private List<ProjectInfoDTO> projectInfos;

    @ApiModelProperty("保全经理")
    @ExcelProperty("保全经理")
    private String manage;

    @ApiModelProperty("在管项目数")
    @ExcelProperty("在管项目数")
    private Integer manageNum;

    @ApiModelProperty("累计追偿金额(万元)")
    @ExcelProperty("累计追偿金额(万元)")
    private BigDecimal totalCompensationMoney = BigDecimal.ZERO;

    @ApiModelProperty("核销项目数")
    @ExcelProperty("核销项目数")
    private Integer writeOffNum;

    @ApiModelProperty("移交项目数")
    @ExcelProperty("移交项目数")
    private Integer transferNum;

    @ApiModelProperty("自主回款目标(万元)")
    @ExcelProperty("自主回款目标(万元)")
    private BigDecimal ownTarget = BigDecimal.ZERO;

    @ApiModelProperty("自主回款完成率")
    @ExcelProperty("自主回款完成率")
    private BigDecimal ownDoneRate;

    @ApiModelProperty("自主回款金额(万元)")
    @ExcelProperty("自主回款金额(万元)")
    private BigDecimal ownPaymentCollectionAmount = BigDecimal.ZERO;

    @ApiModelProperty("委托回款目标(万元)")
    @ExcelProperty("委托回款目标(万元)")
    private BigDecimal entrustMoneyTarget = BigDecimal.ZERO;

    @ApiModelProperty("委托回款金额(万元)")
    @ExcelProperty("委托回款金额(万元)")
    private BigDecimal entrustMoneyAmount = BigDecimal.ZERO;

    @ApiModelProperty("委托回款完成率")
    @ExcelProperty("委托回款完成率")
    private BigDecimal entrustDoneRate;

    @ApiModelProperty("累计回款目标(万元)")
    @ExcelProperty("累计回款目标(万元)")
    private BigDecimal totalPaymentCollectionTarget = BigDecimal.ZERO;

    @ApiModelProperty("累计回款金额(万元)")
    @ExcelProperty("累计回款金额(万元)")
    private BigDecimal totalPaymentCollection = BigDecimal.ZERO;

    @ApiModelProperty("累计回款完成率回款率(%)")
    @ExcelProperty("回款率(%)")
    private BigDecimal paymentCollectionRate;

}

