package com.jk.asset.model.dto.property;

import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.property.LeaseInfoRequest;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 租赁信息表出参
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "租赁信息表出参")
public class LeaseInfoDTO extends LeaseInfoRequest {

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

  @ApiModelProperty("资产名称")
  private String propertyName;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
