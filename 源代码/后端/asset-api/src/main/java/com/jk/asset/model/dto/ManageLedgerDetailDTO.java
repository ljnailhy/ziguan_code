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
 * date2024/7/17 10:09
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "保全经理详情 出参")
public class ManageLedgerDetailDTO {

    @ApiModelProperty("保全经理名称")
    private String  manageName;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("在管项目数")
    private Integer manageProjectNum;

    @ApiModelProperty("代偿金额(万元)")
    private BigDecimal compensationMoney;

    @ApiModelProperty("回款金额(万元)")
    private BigDecimal recoveryCollectionAmount;

    @ApiModelProperty("剩余代偿金额(万元)")
    private BigDecimal residueRecoveryAmount;

    @ApiModelProperty("累计回款率")
    private BigDecimal totalCollectionRate;

    @ApiModelProperty("核销项目数")
    private Integer writeOffNum;

    @ApiModelProperty("核销项目金额")
    private BigDecimal totalWriteOffAmount;

    @ApiModelProperty("保全日到期小于90天")
    private Integer transferDueDateNum;

    @ApiModelProperty("诉讼到期小于90天")
    private Integer proceedingAgeingDueDateNum;

    @ApiModelProperty("执行到期小于90天")
    private Integer adjustTrialDueDateNum;

//    @ApiModelProperty("在管项目")
//    Result<List<ManageLedgerProjectDTO>> manageProjectList;
//
//    @ApiModelProperty("已核销项目")
//    Result<List<ManageLedgerProjectDTO>> writeOffProjectList;
//
//    @ApiModelProperty("已结案项目")
//    Result<List<ManageLedgerProjectDTO>> closeCaseProjectList;


}
