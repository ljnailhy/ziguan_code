package com.jk.asset.model.request.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author wangTao
 * date2024/7/19 17:25
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagePageRequest {

    @ApiModelProperty("创建时间范围")
    private List<Date> createStampRange;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("保全经理")
    private String manage;

    @ApiModelProperty("是否核销")
    private Boolean isWriteOff;

    @ApiModelProperty("已结案")
    private Boolean isCloseCase;
}
