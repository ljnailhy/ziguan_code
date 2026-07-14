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
 * date2024/7/15 11:02
 **/

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "历年项目概况")
public class OverviewPastProjectsDTO {

    @ApiModelProperty("代偿金额")
    private BigDecimal recoveryAmount;

    @ApiModelProperty("回款金额")
    private BigDecimal recoveryCollectionAmount;

    @ApiModelProperty("代偿项目数")
    private Integer recoveryAccount;

    @ApiModelProperty("年份")
    private Integer projectYear;

}
