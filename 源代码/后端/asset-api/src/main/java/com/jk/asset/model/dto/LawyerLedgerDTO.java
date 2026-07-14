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

/**
 * @author wangTao
 * date2024/7/16 9:58
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "律所台账出参")
@ExcelIgnoreUnannotated
public class LawyerLedgerDTO {

    @ApiModelProperty("律所Id")
    private Long id;

    @ApiModelProperty("律所名称")
    @ExcelProperty("律所名称")
    private String name;

    @ApiModelProperty("代理项目数")
    @ExcelProperty("代理项目数")
    private Integer proNum;

    @ApiModelProperty("代理项目金额(元)")
    @ExcelProperty("累计回款金额(元)")
    private BigDecimal proMoney;

    @ApiModelProperty("回款金额(元)")
    @ExcelProperty("回款金额(元)")
    private BigDecimal proAmount;

    @ApiModelProperty("回款率(%)")
    @ExcelProperty("回款率(%)")
    private BigDecimal collectionRatio;
}

