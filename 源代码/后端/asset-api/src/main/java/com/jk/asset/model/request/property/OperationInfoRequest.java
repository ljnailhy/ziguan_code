package com.jk.asset.model.request.property;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import com.jk.workflow.enums.ProcessStatus;
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
 * 运营信息表入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:55
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "运营信息表入参")
public class OperationInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("运营标题")
  private String operationTitle;

  @ApiModelProperty("运营类型")
  private Long operationType;

  @ApiModelProperty("运营日期")
  private Date operationDate;

  @ApiModelProperty("费用类型")
  private Long costType;

  @ApiModelProperty("费用金额")
  private BigDecimal costMoney;

  @ApiModelProperty("运营内容")
  private String operationContent;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("资产信息")
  private List<PropertyBillRequest> propertyBillRequestList;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  @ApiModelProperty("资产收入分配入参")
  List<AssetIncomeDistributionRequest> assetIncomeDistributionRequestList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-13 09:53:55
   */
  // @Override
  public boolean unverified() {
    // String校验：空org.apache.commons.lang3.StringUtils.isBlank 非空isNotBlank 批量-是否有空isAnyBlank 批量-都不空isNoneBlank
    // List校验：空org.apache.commons.collections4.CollectionUtils.isEmpty 非空isNotEmpty
    // 其他Object(或List)：空org.apache.commons.lang3.ObjectUtils.isEmpty 非空isNotEmpty 批量-都不空allNotNull 批量-都空allNull 批量-是否有不空anyNotNull 批量-是否有空anyNull
    // return super.unverified();
    return false;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
