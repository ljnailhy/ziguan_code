package com.jk.asset.model.dto;

import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 项目审批过程流程
 *
 * @author WangShuai
 * @since 2024/7/18 19:09
 **/
@ApiModel(description = "回款明细出参")
@Data
public class ProjectWorkflowProcess {

  @ApiModelProperty("流程ID")
  private String flowId;

  @ApiModelProperty("单据ID")
  private Long billId;

  @ApiModelProperty("业务单据名称")
  private String billTypeName;

  @ApiModelProperty("业务单据code")
  private String billTypeCode;

  @ApiModelProperty("流程状态")
  private ProcessStatus processStatus;

  @ApiModelProperty("创建人")
  private Long creator;

  @ApiModelProperty("创建时间")
  private Date createStamp;

}
