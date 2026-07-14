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
 * @author wangTao 案件状态出餐
 * date2024/7/10 9:53
 **/

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "律所案件状态")
public class LawInfoDTO {

    @ApiModelProperty("未分配")
    private Integer projectState01;
    @ApiModelProperty("已分配")
    private Integer projectState02;
    @ApiModelProperty("待立案")
    private Integer projectState03;
    @ApiModelProperty("已立案")
    private Integer projectState04;
    @ApiModelProperty("保全")
    private Integer projectState05;
    @ApiModelProperty("一审")
    private Integer projectState06;
    @ApiModelProperty("二审")
    private Integer projectState07;
    @ApiModelProperty("再审")
    private Integer projectState08;
    @ApiModelProperty("已撤诉")
    private Integer projectState09;
    @ApiModelProperty("调解")
    private Integer projectState10;
    @ApiModelProperty("诉中调解")
    private Integer projectState11;
    @ApiModelProperty("判决")
    private Integer projectState12;
    @ApiModelProperty("执行中")
    private Integer projectState13;
    @ApiModelProperty("终本")
    private Integer projectState14;
    @ApiModelProperty("已结案")
    private Integer projectState15;
    @ApiModelProperty("其他")
    private Integer projectState16;
    @ApiModelProperty("分配中")
    private Integer projectState17;

    @ApiModelProperty("在管项目数")
    private Integer manageProjectNum;

    @ApiModelProperty("代偿总金额")
    private BigDecimal totalCompensationMoney;

    @ApiModelProperty("回款总金额")
    private BigDecimal totalPaymentCollection;

    @ApiModelProperty("回款率")
    private BigDecimal collectionRate;

    @ApiModelProperty("本年回款率")
    private BigDecimal collectionRateYear;

    @ApiModelProperty("律师费")
    private BigDecimal lawFee;

    @ApiModelProperty("核销率")
    private BigDecimal writeOffRate;

}
