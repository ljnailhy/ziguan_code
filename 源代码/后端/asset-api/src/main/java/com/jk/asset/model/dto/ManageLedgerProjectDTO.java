package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author wangTao
 * date2024/7/17 10:25
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "保全经理详情 出参")
public class ManageLedgerProjectDTO {


    @ApiModelProperty("是否核销")
    @JsonIgnore
    private Boolean isWriteOff;

    @ApiModelProperty("移交至保全部日期")
    @JsonIgnore
    private Date transferDate;

    @ApiModelProperty("项目Id")
    private Long projectId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目状态")
    private Long projectState;

    @ApiModelProperty("代偿金额(元)")
    private BigDecimal compensationMoney;

    @ApiModelProperty("代偿时间")
    private Date compensationDate;

    @ApiModelProperty("律所名称")
    private String name;

    @Deprecated
    @ApiModelProperty("律师姓名")
    private String lawyerName;

    @Deprecated
    @ApiModelProperty("律师电话")
    private String phone;

    @ApiModelProperty("诉讼时效")
    private Date proceedingAgeingDate;

    @ApiModelProperty("执行时效（调解审批判决日期）")
    private Date adjustTrialDate;

    @ApiModelProperty("剩余反担保措施")
    private Integer residueReveMeasureNum;

    @ApiModelProperty("累计回款(元)")
    private BigDecimal totalCollectionAmount;

    @ApiModelProperty("剩余代偿金额(元)")
    private BigDecimal residueRecoveryAmount;


}
