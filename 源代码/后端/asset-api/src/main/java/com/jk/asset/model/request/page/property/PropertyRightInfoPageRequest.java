package com.jk.asset.model.request.page.property;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.property.PropertyRightInfoRequest;
import com.jk.common.enums.OrderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 产权信息表分页入参
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
@ApiModel(description = "产权信息表分页入参")
public class PropertyRightInfoPageRequest extends PropertyRightInfoRequest {

  @ApiModelProperty("创建时间范围")
  private List<Date> createStampRange;

  @ApiModelProperty("产权证日期范围")
  private List<Date> propertyEndDateRange;

  @ApiModelProperty("资产名称")
  private String propertyName;

  @ApiModelProperty("资产跟进人")
  private String followUpPerson;

  @ApiModelProperty("资产来源")
  private String projectName;

  @ApiModelProperty("租户id")
  private Long tenantId;

  @ApiModelProperty("创建人")
  private Long creator;

  @ApiModelProperty("创建时间")
  private Date createStamp;

  @ApiModelProperty("最后修改人")
  private Long lastEditor;

  @ApiModelProperty("最后修改时间")
  private Date lastEditStamp;

  @ApiModelProperty("排序字段名")
  private String field;

  @ApiModelProperty("排序类型")
  private OrderEnum order;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
