package com.jk.asset.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangTao
 * date2024/9/2 14:27
 * 追偿情况统计出参
 **/
@Data
public class CompensatoryCountDTO {

    @ApiModelProperty("月份")
    private String month;


    @ApiModelProperty("季度")
    private String season;

    @ApiModelProperty("上年末追偿金额")
    private BigDecimal lastYearCompensatoryMoney = BigDecimal.ZERO;

    @ApiModelProperty("本年新增功能追偿金额")
    private BigDecimal thisYearCompensatoryMoney = BigDecimal.ZERO;

    @ApiModelProperty("累计追偿金额-查所有项目")
    private BigDecimal totalCompensatoryMoney = BigDecimal.ZERO;

    @ApiModelProperty("历史风险项目")
    private BigDecimal historyCompensatoryMoney = BigDecimal.ZERO;

    @ApiModelProperty("新增风险项目")
    private BigDecimal addCompensatoryMoney = BigDecimal.ZERO;

    @ApiModelProperty("上年末回款金额")
    private BigDecimal lastYearCollectionMoney = BigDecimal.ZERO;

    @ApiModelProperty("本年新增回款金额")
    private BigDecimal thisYearCollectionMoney = BigDecimal.ZERO;

    @ApiModelProperty("累计回款金额")
    private BigDecimal totalCollectionMoney = BigDecimal.ZERO;

    @ApiModelProperty("历史风险项目追偿回款")
    private BigDecimal historyCollectionMoney = BigDecimal.ZERO;

    @ApiModelProperty("新增项目追偿回款")
    private BigDecimal addCollectionMoney = BigDecimal.ZERO;

    @ApiModelProperty("上年末追偿余额")
    private BigDecimal lastYearBalance = BigDecimal.ZERO;

    @ApiModelProperty("追偿余额")
    private BigDecimal totalBalance = BigDecimal.ZERO;
}
