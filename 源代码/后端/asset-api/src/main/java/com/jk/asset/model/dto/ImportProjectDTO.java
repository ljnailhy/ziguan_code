package com.jk.asset.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangTao
 * date2024/7/2 14:41
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
//@Builder(toBuilder = true)
@ApiModel(description = "模板导入 入参")
public class ImportProjectDTO {

    @ApiModelProperty("产品名称")
    @ExcelProperty("产品名称")
    private Long productName;

    @ApiModelProperty("项目名称")
    @ExcelProperty("债务人名称")
    private String projectName;

    @ExcelProperty("债务人性质")
    @ApiModelProperty("主体性质'ENTERPRISE'：企业,'NON_ENTERPRISE'：非企业经济组织,'INDIVIDUALITY'：个体工商户,'XIAOWEI'：小微企业主,'PEASANT_HOUSEHOLD'：农户")
    private String nature;

    @ApiModelProperty("代偿金额")
    @ExcelProperty("代偿金额")
    private BigDecimal compensationMoney;

    @ApiModelProperty("代偿时间")
    @ExcelProperty("代偿日期")
    private Date compensationDate;

    @ApiModelProperty("代偿方案")
    @ExcelProperty("代偿方案说明")
    private String compensationPlan;

    @ApiModelProperty("移交至保全部日期")
    @ExcelProperty("移交至保全部日期")
    private Date transferDate;

    @ApiModelProperty("证件类型")
    @ExcelProperty("证件类型")
    private Long documentType;

    @ApiModelProperty("证件号")
    @ExcelProperty("证件号码")
    private String documentCode;

    @ApiModelProperty("所属区域_省")
    @ExcelProperty("区域(省)")
    private Long belongProvince;

    @ApiModelProperty("区域(市)")
    @ExcelProperty("区域(市)")
    private Long belongCity;

    @ApiModelProperty("区域(县|区)")
    @ExcelProperty("区域(县|区)")
    private Long belongDistrict;

    @ApiModelProperty("详细地址")
    @ExcelProperty("详细地址")
    private String address;

    @ApiModelProperty("合作银行")
    @ExcelProperty("合作银行")
    private Long cooperateBank;

    @ApiModelProperty("合作银行(支行)")
    @ExcelProperty("合作银行(支行)")
    private String cooperateBankBranch;

    @ApiModelProperty("行业分类")
    @ExcelProperty("行业分类(工信部)")
    private Long industryType;

    @ApiModelProperty("企业划型 DXQY:大型企业 ZXQY:中型企业 XXQY:小型企业 WXQY:微型企业 QT:其他")
    @ExcelProperty("企业划型")
    private Long bigSmall;

    @ExcelProperty("政策扶持领域")
    @ApiModelProperty("政策扶持领域类别")
    private Long goverType;

    @ApiModelProperty("债权起始日")
    @ExcelProperty("债权起始日期")
    private Date debtBeginDate;

    @ApiModelProperty("债权到期日")
    @ExcelProperty("债权到期日期")
    private Date debtEndDate;

    @ApiModelProperty("银行放款金额")
    @ExcelProperty("银行放款金额")
    private BigDecimal loanMoney;

    @ApiModelProperty("借款合同号")
    @ExcelProperty("借款合同号")
    private String loanPactCode;

    @ApiModelProperty("保证合同号")
    @ExcelProperty("保证合同号")
    private String pledPactCode;

    @ApiModelProperty("委保合同号")
    @ExcelProperty("委保合同号")
    private String pactCode;

    @ApiModelProperty("借据号")
    @ExcelProperty("借据号")
    private String loanCode;

    @ApiModelProperty("担保费率(年)")
    @ExcelProperty("担保费率(年)[%]")
    private String guaranteeRate;

    @ApiModelProperty("分险比例(债权人)")
    @ExcelProperty("分险比例(债权人)")
    private String dividedInsuranceDebtor;

    @ApiModelProperty("分险比例(原担保)")
    @ExcelProperty("分险比例(原担保)")
    private String dividedInsuranceSecurity;

    @ApiModelProperty("分险比例(再担保)")
    @ExcelProperty("分险比例(再担保)")
    private String dividedInsuranceAgainSecurity;

    @ApiModelProperty("分险比例(其他)")
    @ExcelProperty("险比例(其他)")
    private String dividedInsuranceOther;

    @ApiModelProperty("关联企业")
    @ExcelProperty("关联企业")
    private String relationEnterprise;

    @ApiModelProperty("关联企业社会信用统一代码")
    @ExcelProperty("关联企业社会信用统一代码")
    private String relationEnterpriseNo;

    @ApiModelProperty("法定代表人")
    @ExcelProperty("联系人(或法人)")
    private String legalRepresentative;

    @ApiModelProperty("联系人电话")
    @ExcelProperty("联系人电话")
    private String phone;

    @ApiModelProperty("A角系统登陆账号")
    @ExcelProperty("A角系统登陆账号")
    private String aAccount;

    @ApiModelProperty("担保公司A角名称")
    @ExcelProperty("担保公司A角名称")
    private String aName;

    @ApiModelProperty("B角系统登陆账号")
    @ExcelProperty("B角系统登陆账号")
    private String bAccount;

    @ApiModelProperty("担保公司B角名称")
    @ExcelProperty("担保公司B角名称")
    private String bName;

    @ApiModelProperty("风补基金")
    @ExcelProperty("风补基金")
    private String riskCompensation;

    @ApiModelProperty("担保方式")
    @ExcelProperty("担保方式")
    private String securityWay;

    @ApiModelProperty("反担保类型")
    @ExcelProperty("反担保类型")
    private String securityType;

    @ApiModelProperty("反担保人名称")
    @ExcelProperty("反担保人名称")
    private String reveName;

    @ApiModelProperty("反担保措施")
    @ExcelProperty("反担保措施")
    private String reveMeasure;

}
