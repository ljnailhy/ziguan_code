package com.jk.asset.model.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.model.entity.BaseTenantDO;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.AllocationInfoDetailRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 项目分配/变更明细出参
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "项目分配/变更明细出参")
public class AllocationInfoDetailDTO extends AllocationInfoDetailRequest {

  @ApiModelProperty("创建人")
  private Long creator;

  @ApiModelProperty("创建时间")
  private Date createStamp;

  @ApiModelProperty("最后修改人")
  private Long lastEditor;

  @ApiModelProperty("最后修改时间")
  private Date lastEditStamp;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensationMoney;

  @ApiModelProperty("代偿时间")
  private Date compensationDate;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
