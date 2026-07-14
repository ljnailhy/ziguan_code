package com.jk.asset.model.request;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ImportProjectRequest {


    @ApiModelProperty("项目名称")
    @ExcelProperty("债务人名称")
    private String projectName;

    @ExcelProperty("债务人性质")
    @ApiModelProperty("主体性质'ENTERPRISE'：企业,'NON_ENTERPRISE'：非企业经济组织,'INDIVIDUALITY'：个体工商户,'XIAOWEI'：小微企业主,'PEASANT_HOUSEHOLD'：农户")
    private String nature;

    @ApiModelProperty("代偿金额")
    @ExcelProperty("代偿金额（元）")
    private String compensationMoney;

    @ApiModelProperty("代偿时间")
    @ExcelProperty("代偿日期")
    private String compensationDate;

    @ApiModelProperty("代偿方案")
    @ExcelProperty("代偿方案说明")
    private String compensationPlan;

    @ApiModelProperty("移交至保全部日期")
    @ExcelProperty("移交至保全部日期")
    private String transferDate;

    @ApiModelProperty("证件类型")
    @ExcelProperty("证件类型")
    private String documentType;

    @ApiModelProperty("证件号")
    @ExcelProperty("证件号码")
    private String documentCode;

    @ApiModelProperty("所属区域_省")
    @ExcelProperty("区域(省)")
    private String belongProvince;

    @ApiModelProperty("区域(市)")
    @ExcelProperty("区域(市)")
    private String belongCity;

    @ApiModelProperty("区域(县|区)")
    @ExcelProperty("区域(县|区)")
    private String belongDistrict;

    @ApiModelProperty("详细地址")
    @ExcelProperty("详细地址")
    private String address;


    @ApiModelProperty("行业分类")
    @ExcelProperty("行业分类")
    private String industryType;

    @ApiModelProperty("企业划型 DXQY:大型企业 ZXQY:中型企业 XXQY:小型企业 WXQY:微型企业 QT:其他")
    @ExcelProperty("企业划型")
    private String bigSmall;

    @ExcelProperty("政策扶持领域")
    @ApiModelProperty("政策扶持领域类别")
    private String goverType;

    @ExcelProperty("诉讼时效")
    @ApiModelProperty("诉讼时效")
    private String proceedingageingdate;

    @ExcelProperty("执行时效")
    @ApiModelProperty("执行时效")
    private String adjustTrialDate;

    @ExcelProperty("备注")
    @ApiModelProperty("备注")
    private String importRemark;

    @ExcelProperty("保全经理名称")
    @ApiModelProperty("保全经理名称")
    private String manage;

    @ExcelProperty("保全经理账号")
    @ApiModelProperty("保全经理账号")
    private String manageAccount;

    @ApiModelProperty("律所名称")
    @ExcelProperty("律所名称")
    private String lawFirmName;

    @ApiModelProperty("项目状态")
    @ExcelProperty("项目状态")
    private String projectState;

    @ApiModelProperty("关联企业")
    @ExcelProperty("关联企业")
    private String relationEnterprise;

    @ApiModelProperty("关联企业社会信用统一代码")
    @ExcelProperty("关联企业社会信用统一代码")
    private String relationEnterpriseNo;

    @ApiModelProperty("法定代表人")
    @ExcelProperty("法定代表人")
    private String legalRepresentative;

    @ApiModelProperty("联系人")
    @ExcelProperty("联系人")
    private String contacts;

    @ApiModelProperty("联系人电话")
    @ExcelProperty("联系电话")
    private String phone;

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

    @ApiModelProperty("是否已处置")
    @ExcelProperty("是否已处置")
    private String isDispose;
    @ApiModelProperty("处置金额(万元)")
    @ExcelProperty("处置金额(元)")
    private String disposeMoney;
    @ApiModelProperty("doType")
    @ExcelProperty("doType")
    private String doType;


}
