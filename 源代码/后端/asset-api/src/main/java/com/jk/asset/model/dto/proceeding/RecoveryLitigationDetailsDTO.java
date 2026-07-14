package com.jk.asset.model.dto.proceeding;

import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.proceeding.RecoveryLitigationDetailsRequest;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息出参
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
@ApiModel(description = "终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息出参")
public class RecoveryLitigationDetailsDTO extends RecoveryLitigationDetailsRequest {

  @ApiModelProperty("创建人")
  private Long creator;

  @ApiModelProperty("创建时间")
  private Date createStamp;

  @ApiModelProperty("最后修改人")
  private Long lastEditor;

  @ApiModelProperty("最后修改时间")
  private Date lastEditStamp;

  @ApiModelProperty("流程状态")
  private ProcessStatus processStatus;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
