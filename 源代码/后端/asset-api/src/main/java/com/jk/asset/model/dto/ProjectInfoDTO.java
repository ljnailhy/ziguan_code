package com.jk.asset.model.dto;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.enums.SubjectNatureEnum;
import com.jk.asset.model.request.ProjectInfoRequest;
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
 * 项目信息表出参
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "项目信息表出参")
public class ProjectInfoDTO extends ProjectInfoRequest {

  @ApiModelProperty("创建人")
  private Long creator;

  @ApiModelProperty("创建时间")
  private Date createStamp;

  @ApiModelProperty("最后修改人")
  private Long lastEditor;

  @ApiModelProperty("最后修改时间")
  private Date lastEditStamp;

  @ApiModelProperty("主体性质'ENTERPRISE'：企业,'NON_ENTERPRISE'：非企业经济组织,'INDIVIDUALITY'：个体工商户,'XIAOWEI'：小微企业主,'PEASANT_HOUSEHOLD'：农户")
  private SubjectNatureEnum nature;

  @ApiModelProperty("律师名称")
  private String lawyer;

  @ApiModelProperty("累计回款金额")
  private BigDecimal totalCollectionAmount;

  @ApiModelProperty("核销金额")
  private BigDecimal writeDffAmount;

  @ApiModelProperty("产品名")
  private String product;

  @ApiModelProperty("剩余反担保措施")
  private Integer residueReveMeasureNum;

  @ApiModelProperty("剩余代偿金额")
  private BigDecimal residueRecoveryAmount;

  @ApiModelProperty("律师费")
  private BigDecimal lawFee;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
