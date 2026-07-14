package com.jk.asset.model.dto.property;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.property.PropertyRightInfoRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 产权信息表出参
 *
 * @author wangtao
 * @since 2024-08-17 14:44:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "产权信息表出参")
public class PropertyRightInfoDTO extends PropertyRightInfoRequest {

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
