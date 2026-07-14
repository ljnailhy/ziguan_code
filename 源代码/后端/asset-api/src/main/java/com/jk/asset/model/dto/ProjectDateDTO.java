package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wangTao
 * date2024/7/15 19:08
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "临期预警")
public class ProjectDateDTO {

    @ApiModelProperty("保全日到期日期")
    private List<Long> transferDueDate;

    @ApiModelProperty("诉讼到期日期")
    private List<Long> proceedingAgeingDueDate;

    @ApiModelProperty("执行到期日期")
    private List<Long> adjustTrialDueDate;
}
