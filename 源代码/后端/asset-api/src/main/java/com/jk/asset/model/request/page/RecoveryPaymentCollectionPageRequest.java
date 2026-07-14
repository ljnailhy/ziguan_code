package com.jk.asset.model.request.page;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.RecoveryPaymentCollectionRequest;
import com.jk.common.enums.OrderEnum;
import com.jk.workflow.enums.ProcessStatus;
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
 * 回款表分页入参
 *
 * @author wangtao
 * @since 2024-06-20 18:53:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "回款表分页入参")
public class RecoveryPaymentCollectionPageRequest extends RecoveryPaymentCollectionRequest {

  @ApiModelProperty("创建时间范围")
  private List<Date> createStampRange;

  @ApiModelProperty("回款时间范围")
  private List<Date> collectionDateRange;

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

  @ApiModelProperty("项目id")
  private List<Long> projectInfoList;

  @ApiModelProperty("流程状态")
  private ProcessStatus processStatus;

  @ApiModelProperty("用户权限")
  private Set<Long> orgUserIds;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
