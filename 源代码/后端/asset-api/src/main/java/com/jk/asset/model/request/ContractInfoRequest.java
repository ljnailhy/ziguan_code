package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
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
 * 合同信息入参
 *
 * @author wangshuai
 * @since 2024-06-20 17:46:10
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "合同信息入参")
public class ContractInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("合同名称")
  private String contractName;

  @ApiModelProperty("合同编号")
  private String contractCode;

  @ApiModelProperty("合同类型")
  private Long contractType;

  @ApiModelProperty("合同金额")
  private BigDecimal contractMoney;

  @ApiModelProperty("签约日期")
  private Date signingDate;

  @ApiModelProperty("开始时间")
  private Date startDate;

  @ApiModelProperty("截止时间")
  private Date endDate;

  @ApiModelProperty("合同摘要")
  private String contractAbstract;

  @ApiModelProperty("代理方式")
  private Long agentWay;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("代理费收费比例")
  private BigDecimal agencyFeeRatio;

  @ApiModelProperty("跟进人")
  private String followUp;

  @ApiModelProperty("是否删除")
  private Boolean isDeleted;

  @ApiModelProperty("签约方信息")
  private List<SignatureInfoRequest> signatureInfoList;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  @ApiModelProperty("关联项目")
  private List<BillContractRequest> projectInfoList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-20 17:46:10
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
