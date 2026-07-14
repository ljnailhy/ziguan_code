package com.jk.asset.model.request.page.property;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.property.PropertyInfoRequest;
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
 * 资产信息分页入参
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
@ApiModel(description = "资产信息分页入参")
public class PropertyInfoPageRequest extends PropertyInfoRequest {

  @ApiModelProperty("创建时间范围")
  private List<Date> createStampRange;

  @ApiModelProperty("收入时间范围")
  private List<Date> propertyDateRange;

  @ApiModelProperty("租户id")
  private Long tenantId;

  @ApiModelProperty("是否删除")
  private Boolean isDeleted = false;

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

    @ApiModelProperty("资产状态")
  private List<PropertyStateEnum> propertyStateList;

  @ApiModelProperty("权证到期日范围")
  private List<Date> propertyEndDateRange;

  @ApiModelProperty("资产id集合")
  private List<Long> ids;

  @ApiModelProperty("资产id集合不查询这个id")
  private List<Long> noIds;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
