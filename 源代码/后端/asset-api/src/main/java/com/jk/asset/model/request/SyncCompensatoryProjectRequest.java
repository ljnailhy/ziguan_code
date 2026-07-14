package com.jk.asset.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * date2024/6/24 17:51
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "同步代偿项目入参")
public class SyncCompensatoryProjectRequest {

    @ApiModelProperty("法定代表人  联系人(或法人)")
    private String CONTACT;

    @ApiModelProperty("风补基金")
    private String RiskFund;

    @ApiModelProperty("债务人性质")
    private String Customer_Property;

    @ApiModelProperty("项目唯一标识")
    private String business_no;

    @ApiModelProperty("证件类型")
    private String CREDENTIAL_TYPE;

    @ApiModelProperty("行业分类 行业分类(工信部)")
    private String INDUSTRY_GXB;

    @ApiModelProperty("缴纳税收")
    private BigDecimal TaxAmount;

    @ApiModelProperty("证件号码")
    private String CREDENTIAL_NO;

    @ApiModelProperty("区域(省)")
    private String province;

    @ApiModelProperty("保证合同号")
    private String Warranty_contract_no;

    @ApiModelProperty("营业收入")
    private BigDecimal BUSINESS_REVENUE;

    @ApiModelProperty("区域(县|区)")
    private String area;

    @ApiModelProperty("债权起始日期")
    private Date DEBT_BILL_START_DAY;

    @ApiModelProperty("是否首次银行贷款 是：Y 否：N;")
    private String IS_FIRST_LOAN_ACCOUNT;

    @ApiModelProperty("产品名称")
    private String PRODUCT_NAME;

    @ApiModelProperty("企业划型")
    private String ENTERPRISE_SIZE;

    @ApiModelProperty("银行放款金额")
    private BigDecimal debt_amount;

    @ApiModelProperty("从业人数")
    private Integer EMPLOYED_POPULATION;

    @ApiModelProperty("反担保措施")
    private List<String> anti_remark;

    @ApiModelProperty("合作银行(支行)")
    private String cooperative_bank_third;

    @ApiModelProperty("代偿日期")
    private Date repayment_date;

    @ApiModelProperty("办公地址/现居住地址")
    private String CURRENT_ADDRESS;

    @ApiModelProperty("风险归类")
    private String RIST_TYPE_AFTER_GUARANTEE;

    @ApiModelProperty("代偿金额")
    private BigDecimal repayment_amount;

    @ApiModelProperty("行业分类四级（国标）")
    private List<String> INDUSTRY_GXW;

    @ApiModelProperty("资产总额[元]")
    private BigDecimal TOTAL_ASSETS;

    @ApiModelProperty("行业分类(金融局)")
    private String INDUSTRY_JRJ;

    @ApiModelProperty("债权到期日期")
    private Date DEBT_BILL_DUE_DAY;

    @ApiModelProperty("区域(市)")
    private String city;

    @ApiModelProperty("还款银行")
    private String repayment_bank;

    @ApiModelProperty("政策扶持领域")
    private String INDUSTRY_POLICY_SUPPORT;

    @ApiModelProperty("委保合同号")
    private String GUARANTEE_CONTRACT_NO;

    @ApiModelProperty("借据号(或其他唯一标识号)")
    private String DEBT_BILL_NO;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("分险比例（债权人）")
    private BigDecimal risk_rate_bank;

    @ApiModelProperty("借款合同号")
    private String Principle_claim_contract_no;

    @ApiModelProperty("担保公司A角")
    private String BUSINESS_MANAGER_A;

    @ApiModelProperty("未知------------")
    private String MANAGER_A;

    @ApiModelProperty("业务类型 线上审批：YBXZCN；上会审批：YBXZCW；见贷即保：JDJB；")
    private String business_type;

    @ApiModelProperty("担保费率（年）[%]")
    private BigDecimal GUARANTEE_FEE_RATE;

    @ApiModelProperty("反担保方式")
    private String COUNTER_GUARANTEE_TYPE;

    @ApiModelProperty("注册地址/户籍所在地")
    private String REGISTERED_ADDRESS;

    @ApiModelProperty("项目来源")
    private String PROJECT_FROM;

    @ApiModelProperty("分险比例（原担保）")
    private BigDecimal risk_rate_origin;

    @ApiModelProperty("关联企业")
    private String relation_enterprise;

    @ApiModelProperty("关联企业社会信用统一代码")
    private String relation_enterprise_no;

    @ApiModelProperty("贷款利率[%]")
    private BigDecimal LOAN_RATE;

    @ApiModelProperty("担保公司B角")
    private String ASSIST_B;

    @ApiModelProperty("债务人名称")
    private String CUSTOMER_NAME;

    @ApiModelProperty("分险比例(其他)")
    private BigDecimal risk_rate_other;

    @ApiModelProperty("合作银行")
    private String COOPERATIVE_BANK;

    @ApiModelProperty("分险比例（再担保）")
    private BigDecimal risk_rate_again;
    @ApiModelProperty("联系人电话")
    private String TEL;

    @ApiModelProperty("a角账号")
    private String a_code;

    @ApiModelProperty("a角名称")
    private String a_name;

    @ApiModelProperty("b角账号")
    private String b_code;

    @ApiModelProperty("b角名称")
    private String b_name;
    @ApiModelProperty("业务编号")
    private String related_bus_no;
    @ApiModelProperty("核保编号")
    private String related_guarantee_no;
    @ApiModelProperty("经办部门")
    private String CREATED_DEPT_IDS;
    @ApiModelProperty("经办公司")
    private String CREATED_COMPANY_ID;


}
