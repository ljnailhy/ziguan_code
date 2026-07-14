package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.WriteOffRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wangTao
 * date2024/7/15 15:58
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "核销项目分类")
public class WriteOffClassificationDTO {

    @ApiModelProperty("核销数")
    private Integer writeOffNumber;

    @ApiModelProperty("核销分类")
    private WriteOffRequest.WriteOffClassificationEnum writeOffClassification;
}
