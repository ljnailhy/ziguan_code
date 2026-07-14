package com.jk.asset.model.request.proceeding;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 诉讼入参
 *
 * @author wangshuai
 * @since 2024-07-01 16:38:08
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "诉讼入参")
public class RecoveryLitigationRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("是否存量 是1 否0")
  private Boolean isStock;

  @ApiModelProperty("是否提交 是1 否0")
  private Boolean isSubmit;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensationAmount;

  @ApiModelProperty("利息（万元）")
  private BigDecimal interest;

  @ApiModelProperty("违约金（万元）")
  private BigDecimal liquidatedDamages;

  @ApiModelProperty("其他费用")
  private BigDecimal otherFees;

  @ApiModelProperty(value = "项目名称", required = true)
  private Long projectId;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("项目状态 一审 二审 再审 保全 撤诉 调节 审判 执行 恢复执行 终本 结案 其他 登记项目工作")
  private Long projectStatus;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-01 16:38:08
   */
  // @Override
  public boolean unverified() {
    // String校验：空org.apache.commons.lang3.StringUtils.isBlank 非空isNotBlank 批量-是否有空isAnyBlank 批量-都不空isNoneBlank
    // List校验：空org.apache.commons.collections4.CollectionUtils.isEmpty 非空isNotEmpty
    // 其他Object(或List)：空org.apache.commons.lang3.ObjectUtils.isEmpty 非空isNotEmpty 批量-都不空allNotNull 批量-都空allNull 批量-是否有不空anyNotNull 批量-是否有空anyNull
    // return super.unverified();
    return false;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
