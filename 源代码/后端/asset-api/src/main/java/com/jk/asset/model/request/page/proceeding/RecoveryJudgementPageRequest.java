package com.jk.asset.model.request.page.proceeding;

import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.enums.LitigationTypeEnum;
import com.jk.common.enums.OrderEnum;
import com.jk.asset.model.request.proceeding.RecoveryJudgementRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 审判信息（立案一审二审再审）分页入参
 *
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "审判信息（立案一审二审再审）分页入参")
public class RecoveryJudgementPageRequest extends RecoveryJudgementRequest {

  @ApiModelProperty("创建时间范围")
  private List<Date> createStampRange;

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

  @ApiModelProperty("立案时间范围")
  private List<Date> fillingDateRange;

  @ApiModelProperty("类型")
  private List<LitigationTypeEnum> litigationTypeList;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
