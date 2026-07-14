package com.jk.asset.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wangTao
 * date2024/6/24 18:42
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "同步代偿项目 反担保信息 入参")
public class AntiRemarkRequest {

    @ApiModelProperty("担保方式 行业分类(工信部) 保证担保：1 抵押担保：2 质押担保：3 其他担保：4")
    private Integer sort;

    @ApiModelProperty("反担保措施描述")
    private String REMARK;

    @ApiModelProperty("业务id")
    private String business_id;

    @ApiModelProperty("反担保人名称")
    private String guarantee_name;

    @ApiModelProperty("担保类型")
    private String guarantee_type;

}
