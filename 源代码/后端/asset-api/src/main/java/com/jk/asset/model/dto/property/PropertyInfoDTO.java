package com.jk.asset.model.dto.property;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.property.PropertyInfoRequest;
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
 * 资产信息出参
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "资产信息出参")
public class PropertyInfoDTO extends PropertyInfoRequest {

  @ApiModelProperty("创建人")
  private Long creator;

  @ApiModelProperty("创建时间")
  private Date createStamp;

  @ApiModelProperty("最后修改人")
  private Long lastEditor;

  @ApiModelProperty("最后修改时间")
  private Date lastEditStamp;

  @ApiModelProperty("关联反担保措施")
  private String reveName;

  @ApiModelProperty("转让收入")
  private BigDecimal transferenceIncome;

  @ApiModelProperty("租赁收入")
  private BigDecimal leaseIncome;

  @ApiModelProperty("运营费用")
  private BigDecimal operateIncome;

  @ApiModelProperty("总面积")
  private BigDecimal totalArea;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
