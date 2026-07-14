package com.jk.asset.model.dto;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.ProjectInfoExtRequest;
import com.jk.asset.model.request.proceeding.RecoveryAdjustTrialRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目信息详细信息扩展表出参
 *
 * @author wangtao
 * @since 2024-06-29 15:28:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "项目信息详细信息扩展表出参")
public class ProjectInfoExtDTO extends ProjectInfoExtRequest {

  @ApiModelProperty("创建人")
  private Long creator;

  @ApiModelProperty("创建时间")
  private Date createStamp;

  @ApiModelProperty("最后修改人")
  private Long lastEditor;

  @ApiModelProperty("最后修改时间")
  private Date lastEditStamp;

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("产品名")
  private String product;

  @ApiModelProperty("所属区域_省")
  private Long belongProvince;

  @ApiModelProperty("所属区域_市")
  private Long belongCity;

  @ApiModelProperty("所属区域_区")
  private Long belongDistrict;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensationMoney;

  @ApiModelProperty("代偿时间")
  private Date compensationDate;

  @ApiModelProperty("移交至保全部日期")
  private Date transferDate;

  @ApiModelProperty("诉讼代偿金额(元)")
  private BigDecimal compensationAmountLitigation;

  @ApiModelProperty("诉讼利息(元)")
  private BigDecimal interestLitigation;

  @ApiModelProperty("诉讼违约金(元)")
  private BigDecimal liquidatedDamagesLitigation;

  @ApiModelProperty("诉讼其他费用(元)")
  private BigDecimal otherFeesLitigation;

  @ApiModelProperty("审判代偿金额(元)")
  private BigDecimal compensationAmountTrial;

  @ApiModelProperty("审判利息(元)")
  private BigDecimal interestTrial;

  @ApiModelProperty("审判违约金(元)")
  private BigDecimal liquidatedDamagesTrial;

  @ApiModelProperty("审判其他费用(元)")
  private BigDecimal otherFeesTrial;

  @ApiModelProperty("调解或审判类型")
  private RecoveryAdjustTrialRequest.AdjustTrialTypeEnum adjustTrialType;

  @ApiModelProperty("CASH:现金(元)")
  private BigDecimal cashMoney;

  @ApiModelProperty("RE_GUARANTEE:再担保(元)")
  private BigDecimal reGuaranteeMoney;

  @ApiModelProperty("MORTGAGE:抵押(元)")
  private BigDecimal mortgageMoney;

  @ApiModelProperty("SILVER_BILL银票(元)")
  private BigDecimal silverBillMoney;

  @ApiModelProperty("保全经理")
  private String manage;

  @ApiModelProperty("律所")
  private String lawFirmName;

  @ApiModelProperty("项目状态")
  private Long projectState;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
