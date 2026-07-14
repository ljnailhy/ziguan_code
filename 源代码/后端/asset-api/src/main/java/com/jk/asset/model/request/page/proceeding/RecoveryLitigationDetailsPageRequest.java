package com.jk.asset.model.request.page.proceeding;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.proceeding.RecoveryLitigationDetailsRequest;
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
 * 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息分页入参
 *
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息分页入参")
public class RecoveryLitigationDetailsPageRequest extends RecoveryLitigationDetailsRequest {

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

  @ApiModelProperty("时间范围")
  private List<Date> detailsDateRange;

  @ApiModelProperty("保全申请提交日期")
  private List<Date> preserveApplyDateRange;

  @ApiModelProperty("保全裁定送达日期")
  private List<Date> preserveRuleDateRange;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
