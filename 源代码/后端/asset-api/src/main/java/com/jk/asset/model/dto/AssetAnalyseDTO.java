package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.dto.property.AnalysePropertyInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wangTao
 * date2024/7/15 20:01
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "资产分析")
public class AssetAnalyseDTO {

    @ApiModelProperty("资产类型数量")
    private List<AssetPropertyTypeDTO> assetPropertyTypeDTO;

    @ApiModelProperty("资产状态")
    private List<AssetStatusDTO> assetStatusDTO;

    @ApiModelProperty("资产数量")
    private Integer assetNum;

    @ApiModelProperty("饼图")
    private List<AnalysePropertyInfoDTO> analysePie;

    @ApiModelProperty("柱状图")
    private List<AnalysePropertyInfoDTO> analyseHistogram;
}
