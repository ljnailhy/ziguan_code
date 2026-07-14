package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.enums.SubjectTypeEnum;
import com.jk.asset.model.request.WriteOffRequest;
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
public class ProjectLedgerDTO {

    @ApiModelProperty("项目id")
    private Long id;

    @ApiModelProperty("债务人id")
    private Long debtorId;

    @ApiModelProperty("项目状态")
    private Long projectState;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("行业分类")
    private String industryType;

    @ApiModelProperty("所属区域_省")
    private String belongProvince;

    @ApiModelProperty("所属区域_市")
    private String belongCity;

    @ApiModelProperty("所属区域_区")
    private String belongDistrict;

    @ApiModelProperty("代偿时间")
    private Date compensationDate;

    @ApiModelProperty("代偿金额(元)")
    private BigDecimal compensationMoney;

    @ApiModelProperty("移交至保全部日期")
    private Date transferDate;

    @ApiModelProperty("反担保处置情况/其他财产线索")
    List<RevePropertyInfoDTO> reveInfoList;

    @ApiModelProperty("反担保人名称")
    private String reveName;
    @ApiModelProperty("反担保措施/财产线索")
    private String reveMeasure;
    @ApiModelProperty("处置金额(元)")
    private BigDecimal disposeMoney;
    @ApiModelProperty("裁定抵债金额(元)")
    private BigDecimal debtRepaymentMoney;

    @ApiModelProperty("保全经理")
    private String manage;

    @ApiModelProperty("代理律所")
    private String name;

    @ApiModelProperty("代理律师")
    private String lawyerName;

    @ApiModelProperty("律师电话")
    private String lawyerPhone;

    @ApiModelProperty("代理合同到期时间")
    private Date endDate;

    @ApiModelProperty("A角")
    private String aname;

    @ApiModelProperty("B角")
    private String bname;

    @ApiModelProperty("被告人(被执行人)")
    private String defendant;

    @ApiModelProperty("诉讼时效")
    private Date proceedingAgeingDate;

    @ApiModelProperty("立案日期")
    private Date fillingDate;

    @ApiModelProperty("立案案号")
    private String fillingCode;

    @ApiModelProperty("保全日期")
    private Date preservationDate;

    @ApiModelProperty("保全案号")
    private String preservationCode;

    @ApiModelProperty("开庭日期")
    private Date courtSessionDate;

    @ApiModelProperty("判决案号")
    private String adjustCode;

    @ApiModelProperty("执行案号")
    private String executeCode;

    @ApiModelProperty("执行时效（调解审批判决日期）")
    private Date adjustTrialDate;

    @ApiModelProperty("判决日期")
    private Date judgeDate;

    @ApiModelProperty("项目回款金额(元)")
    private BigDecimal recoveryCollectionAmount;

    @ApiModelProperty("现金")
    private BigDecimal cashAmount = BigDecimal.ZERO;

    @ApiModelProperty("再担保")
    private BigDecimal reGuaranteeAmount  = BigDecimal.ZERO;

    @ApiModelProperty("抵债")
    private BigDecimal mortgageAmount  = BigDecimal.ZERO;

    @ApiModelProperty("银票")
    private BigDecimal silverBillAmount  = BigDecimal.ZERO;

    @ApiModelProperty("代偿余额(元)")
    private BigDecimal residueRecoveryAmount;

    @ApiModelProperty("诉讼杂费(元) 诉讼费")
    private BigDecimal payType01;

    @ApiModelProperty("诉讼杂费(元) 保全费")
    private BigDecimal payType02;

    @ApiModelProperty("诉讼杂费(元) 保险费")
    private BigDecimal payType03;

    @ApiModelProperty("诉讼杂费(元) 其他费用")
    private BigDecimal payType04;

    @ApiModelProperty("诉讼杂费(元) 律师费用")
    private BigDecimal payType05;

    @ApiModelProperty("核销标签")
    private Boolean isWriteOff;

    @ApiModelProperty("业务类型")
    private SubjectTypeEnum subjectType;

    @ApiModelProperty("证件号码")
    private String documentNumber;

    @ApiModelProperty(value = "核销时间", required = true)
    private Date writeOffDate;

    @ApiModelProperty("核销金额")
    private BigDecimal writeDffAmount;

    @ApiModelProperty("核销项目分类 A:A B:B C:C D:D")
    private WriteOffRequest.WriteOffClassificationEnum writeOffClassification;

}
