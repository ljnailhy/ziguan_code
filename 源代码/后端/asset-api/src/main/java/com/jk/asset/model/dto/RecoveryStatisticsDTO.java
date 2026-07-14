package com.jk.asset.model.dto;

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
 * date2024/7/15 10:00
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "首页统计入参")
public class RecoveryStatisticsDTO {

    @ApiModelProperty("追偿项目数")
    private Integer recoveryAccount;

    @ApiModelProperty("本年累计回款金额")
    private BigDecimal yearTotalRecoveryAmount;

    @ApiModelProperty("剩余代偿金额")
    private BigDecimal residueRecoveryAmount;

    @ApiModelProperty("回款率")
    private BigDecimal recoveryRate;

    @ApiModelProperty("本年回款目标")
    private BigDecimal yearRecoveryCollectionTarget;

    @ApiModelProperty("完成率")
    private BigDecimal doneRate;

    @ApiModelProperty("本年新增追偿项目")
    private Integer yearAddRecovery;

    @ApiModelProperty("本年新增资产数")
    private Integer yearAddAssetAccount;

    @ApiModelProperty("本年新增追偿金额")
    private BigDecimal yearAddRecoveryAmount;

    @ApiModelProperty("本年新增资产净值")
    private BigDecimal yearAddAssetValue;

}
