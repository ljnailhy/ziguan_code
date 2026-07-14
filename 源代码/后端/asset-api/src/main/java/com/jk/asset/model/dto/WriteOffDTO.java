package com.jk.asset.model.dto;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.WriteOffRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 核销出参
 *
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "核销出参")
public class WriteOffDTO extends WriteOffRequest {

  @ApiModelProperty("创建人")
  private Long creator;

  @ApiModelProperty("创建时间")
  private Date createStamp;

  @ApiModelProperty("最后修改人")
  private Long lastEditor;

  @ApiModelProperty("最后修改时间")
  private Date lastEditStamp;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
