package com.jk.asset.model.dto.property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 资产分析出参
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "资产分析出参")
public class AnalysePropertyInfoDTO {

  @ApiModelProperty("维度ID") // 资产单元状态、资产分类
  private Long typeId;

  @ApiModelProperty("维度名称")
  private String typeName;

  @ApiModelProperty("资产原值")
  private BigDecimal totalMoney;
}
