package com.jk.asset.model.dto;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.LawFirmInfoRequest;
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
 * 律所信息出参
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "律所信息出参")
public class LawFirmInfoDTO extends LawFirmInfoRequest {

  @ApiModelProperty("创建人")
  private Long creator;

  @ApiModelProperty("创建时间")
  private Date createStamp;

  @ApiModelProperty("最后修改人")
  private Long lastEditor;

  @ApiModelProperty("最后修改时间")
  private Date lastEditStamp;

  @ApiModelProperty("在管项目数")
  private int projectNumber;

  @ApiModelProperty("累计追偿金额")
  private BigDecimal compensationMoneySum;

  @ApiModelProperty("累计回款金额(万)")
  private BigDecimal collectionAmountSum;

  @ApiModelProperty("回款率")
  private BigDecimal collectionRate;

  @ApiModelProperty("案件信息")
  private LawInfoDTO lawInfoDTO;
  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
