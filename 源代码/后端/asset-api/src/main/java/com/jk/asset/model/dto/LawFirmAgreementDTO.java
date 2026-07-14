package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangTao
 * date2024/9/12 15:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LawFirmAgreementDTO {

    @ApiModelProperty("合同id")
    private Long id;

    @ApiModelProperty("律所名称")
    private String lawFirmName;

    @ApiModelProperty("合同名称")
    private String contractName;

    @ApiModelProperty("合同名称")
    private String endDate;

    @ApiModelProperty("跟进人")
    private String followUp;
}
