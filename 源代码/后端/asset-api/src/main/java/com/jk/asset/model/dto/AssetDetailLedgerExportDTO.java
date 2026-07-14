package com.jk.asset.model.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
public class AssetDetailLedgerExportDTO {

    @ApiModelProperty("所属单位")
    @ExcelIgnore
    private Long affiliatedUnit;
    @ApiModelProperty("所属单位")
    @ExcelProperty("所属单位")
    private String affiliatedUnitStr;
    @ExcelProperty("资产名称")
    @ApiModelProperty("资产名称")
    private String propertyName;
    @ApiModelProperty("项目名称")
    @ExcelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("用地性质（国有出让地，国有划拨地）")
    @ExcelIgnore
    private Long landUseNature;
    @ApiModelProperty("用地性质")
    @ExcelProperty("用地性质")
    private String landUseNatureStr;
    @ApiModelProperty("资产过户备注")
    @ExcelProperty("资产过户备注")
    private String transferOwnershipRemark;
    @ExcelProperty("面积")
    @ApiModelProperty("面积")
    private BigDecimal area;
    @ExcelProperty("详细地址")
    @ApiModelProperty("详细地址")
    private String address;
    @ExcelProperty("产权人名称")
    @ApiModelProperty("产权人名称")
    private String propertyOwner;
    @ExcelProperty("权证到期日")
    @ApiModelProperty("权证到期日")
    private LocalDate propertyEndDate;
    @ExcelProperty("产权证号")
    @ApiModelProperty("产权证号")
    private String propertyCode;
    @ExcelProperty("原值")
    @ApiModelProperty("原值")
    private BigDecimal originalValue;
    @ApiModelProperty("资产单元状态 (在途，占用，闲置，已租赁，已转让)")
    @ExcelIgnore
    private Long assetUnitState;
    @ApiModelProperty("资产单元状态")
    @ExcelProperty("资产单元状态")
    private String assetUnitStateStr;
    @ApiModelProperty("当年盘活回收资金")
    @ExcelProperty("当年盘活回收资金")
    private BigDecimal propertyMoney;

}
