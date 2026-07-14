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
@ApiModel(description = "回款模板导入 入参")
public class ImportPaymentCollectionRequest {

    @ApiModelProperty("项目名称")
    @ExcelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("回款金额(元)")
    @ExcelProperty("回款金额(元)")
    private String collectionAmount;

    @ExcelProperty("回款时间")
    @ApiModelProperty("回款时间")
    private String collectionDate;

    @ApiModelProperty("回款标记")
    @ExcelProperty("回款标记")
    private String collectionSign;

    @ApiModelProperty("回款归属")
    @ExcelProperty("回款归属")
    private String collectionAscription;

    @ApiModelProperty("是否历史代偿")
    @ExcelProperty("是否历史代偿")
    private String isCollectionHistorical;

    @ApiModelProperty("回款类型")
    @ExcelProperty("回款类型")
    private String collectionDetailType;

    @ApiModelProperty("回款律所")
    @ExcelProperty("回款律所")
    private String lawyerName;

    @ApiModelProperty("回款摘要/备注")
    @ExcelProperty("回款摘要/备注")
    private String collectionSummary;;

}
