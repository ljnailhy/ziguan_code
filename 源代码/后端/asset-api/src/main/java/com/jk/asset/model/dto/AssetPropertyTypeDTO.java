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
 * date2024/7/15 19:55
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "资产类型")
public class AssetPropertyTypeDTO {

    @ApiModelProperty("资产类型数量")
    private Integer propertyTypeNum;

    @ApiModelProperty("资产类型")
    private Long  propertyType;

    @ApiModelProperty("资产净值")
    private BigDecimal netWorth;
}
