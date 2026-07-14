package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wangTao
 * date2024/7/19 16:21
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "诉讼阶段统计")
public class ProjectStatusDTO {

    @ApiModelProperty("未分配")
    private Integer projectState01 = 0;
    @ApiModelProperty("已分配")
    private Integer projectState02 = 0;
    @ApiModelProperty("代立案")
    private Integer projectState03 = 0;
    @ApiModelProperty("已立案")
    private Integer projectState04 = 0;
    @ApiModelProperty("保全")
    private Integer projectState05 = 0;
    @ApiModelProperty("一审")
    private Integer projectState06 = 0;
    @ApiModelProperty("二审")
    private Integer projectState07 = 0;
    @ApiModelProperty("再审")
    private Integer projectState08 = 0;
    @ApiModelProperty("已撤诉")
    private Integer projectState09 = 0;
    @ApiModelProperty("诉前调解")
    private Integer projectState10 = 0;
    @ApiModelProperty("诉中调解")
    private Integer projectState11 = 0;
    @ApiModelProperty("判决")
    private Integer projectState12 = 0;
    @ApiModelProperty("执行中")
    private Integer projectState13 = 0;
    @ApiModelProperty("终本")
    private Integer projectState14 = 0;
    @ApiModelProperty("已结案")
    private Integer projectState15 = 0;
    @ApiModelProperty("其他")
    private Integer projectState16 = 0;
    @ApiModelProperty("分配中")
    private Integer projectState17 = 0;
}
