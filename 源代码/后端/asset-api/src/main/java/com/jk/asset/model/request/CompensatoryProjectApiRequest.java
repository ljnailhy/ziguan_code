package com.jk.asset.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author wangTao
 * date2024/6/29 18:59
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "代偿项目api 入参")
public class CompensatoryProjectApiRequest {
    @ApiModelProperty("代偿开始时间")
//    @JsonProperty("BEGIN_check_date")
    private Date begin_check_date;

    @ApiModelProperty("代偿截至时间")
//    @JsonProperty("END_check_date")
    private Date end_check_date;

    @ApiModelProperty("产品名称")
//    @JsonProperty("PRODUCT_NAME")
    private String product_name;

//    @JsonProperty("CUSTOMER_NAME")
    @ApiModelProperty("债务人名称")
    private String customer_name;

//    @JsonProperty("CREDENTIAL_NO")
    @ApiModelProperty("证件号")
    private String credential_no;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String area;

//    @JsonProperty("COOPERATIVE_BANK")
    @ApiModelProperty("合作银行")
    private String cooperative_bank;

//    @JsonProperty("INDUSTRY_GXB")
    @ApiModelProperty("行业分类（工信部）")
    private String industry_gxb;

//    @JsonProperty("Principle_claim_contract_no")
    @ApiModelProperty("借款合同号")
    private String principle_claim_contract_no;

//    @JsonProperty("Warranty_contract_no")
    @ApiModelProperty("保证合同号")
    private String warranty_contract_no;

//    @JsonProperty("GUARANTEE_CONTRACT_NO")
    @ApiModelProperty("委保合同号")
    private String guarantee_contract_no;

//    @JsonProperty("DEBT_BILL_NO")
    @ApiModelProperty("借据号")
    private String debt_bill_no;

//    @JsonProperty("COUNTER_GUARANTEE_TYPE")
    @ApiModelProperty("反担保方式")
    private String counter_guarantee_type;

    @ApiModelProperty("项目唯一标识")
    private List<String> business_no;
}
