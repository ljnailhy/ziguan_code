package com.jk.asset.model.request.page;

import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.enums.OrderEnum;
import com.jk.asset.model.request.RevePropertyInfoRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 反担保/其他财产线索信息分页入参
 *
 * @author wangtao
 * @since 2024-06-24 09:42:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "反担保/其他财产线索信息分页入参")
public class RevePropertyInfoPageRequest extends RevePropertyInfoRequest {

  @ApiModelProperty("创建时间范围")
  private List<Date> createStampRange;

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
