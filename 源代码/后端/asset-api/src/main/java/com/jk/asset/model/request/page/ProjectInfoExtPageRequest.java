package com.jk.asset.model.request.page;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.ProjectInfoExtRequest;
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
import java.util.Set;

/**
 * 项目信息详细信息扩展表分页入参
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
@ApiModel(description = "项目信息详细信息扩展表分页入参")
public class ProjectInfoExtPageRequest extends ProjectInfoExtRequest {

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

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("项目状态集合")
  private List<Long> projectStateList;

  @ApiModelProperty("代偿时间范围")
  private List<Date> compensationDateRange;

  @ApiModelProperty("用户权限")
  private Set<Long> orgUserIds;

  @ApiModelProperty("项目状态")
  private Long projectState;

  @ApiModelProperty("保全经理")
  private String manage;

  @ApiModelProperty("用户列表")
  private List<Long> userIds;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
