package com.jk.asset.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.dto.RevePropertyInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangTao
 * date2024/7/18 9:48
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "项目台账出参")
public class ProjectLedgerRequest {

    @ApiModelProperty("项目id")
    private Long id;

    @ApiModelProperty("项目状态")
    private Long projectState;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("大类")
    private Long type;

    @ApiModelProperty("产品")
    private String productName;

    @ApiModelProperty("行业分类")
    private Long industryType;

    @ApiModelProperty("所属区域_市")
    private Long belongCity;

    @ApiModelProperty("代偿时间")
    private Date compensationDate;

    @ApiModelProperty("代偿金额(万元)")
    private BigDecimal compensationMoney;

    @ApiModelProperty("移交至保全部日期")
    private Date transferDate;

    @ApiModelProperty("反担保处置情况")
    List<RevePropertyInfoDTO> reveInfoList;

    @ApiModelProperty("其他财产线索")
    List<RevePropertyInfoDTO> propertyInfoList;

    @ApiModelProperty("保全经理")
    private String manage;

    @ApiModelProperty("律所名称")
    private String name;

    @ApiModelProperty("律师姓名")
    private String lawyerName;

    @ApiModelProperty("律师电话")
    private String lawyerPhone;

    @ApiModelProperty("合同到期时间")
    private Date endDate;

    @ApiModelProperty("a角")
    private Long aid;

    @ApiModelProperty("b角")
    private Long bid;

    @ApiModelProperty("诉讼时效")
    private Date proceedingAgeingDate;
    @ApiModelProperty("开庭时间")
    private Date courtSessionDate;

    @ApiModelProperty("立案日期")
    private Date fillingDate;
    @ApiModelProperty("判决案号")
    private String adjustCode;
    @ApiModelProperty("执行案号")
    private String executeCode;


    @ApiModelProperty("保全日期")
    private Date detailsDate;
    @ApiModelProperty("执行时效（调解审批判决日期）")
    private Date adjustTrialDate;
    @ApiModelProperty("回款金额")
    private BigDecimal recoveryCollectionAmount;
    @ApiModelProperty("剩余代偿金额")
    private BigDecimal residueRecoveryAmount;
    @ApiModelProperty("付款类型	1 诉讼费	2 保全费	3 保险费	4 其他费用	5 律师费用")
    private BigDecimal payType01;
    private BigDecimal payType02;
    private BigDecimal payType03;
    private BigDecimal payType04;
    private BigDecimal payType05;



}
