package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author wangTao
 * date2024/8/26 15:47
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "资产明细台账")
public class AssetDetailLedgerDTO  {

    @ApiModelProperty("所属单位")
    private Long affiliatedUnit;
    @ApiModelProperty("资产名称")
    private String propertyName;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("用地性质（国有出让地，国有划拨地）")
    private Long landUseNature;
    @ApiModelProperty("资产过户备注")
    private String transferOwnershipRemark;
    @ApiModelProperty("面积")
    private BigDecimal area;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("产权人名称")
    private String propertyOwner;
    @ApiModelProperty("权证到期日")
    private LocalDate propertyEndDate;
    @ApiModelProperty("产权证号")
    private String propertyCode;
    @ApiModelProperty("原值")
    private BigDecimal originalValue;
    @ApiModelProperty("资产单元状态 (在途，占用，闲置，已租赁，已转让)")
    private Long assetUnitState;
    @ApiModelProperty("当年盘活回收资金")
    private BigDecimal propertyMoney;

}
