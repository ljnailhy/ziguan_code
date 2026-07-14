package com.jk.asset.model.request.proceeding;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.model.request.RevePropertyInfoRequest;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息入参
 *
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息入参")
public class RecoveryLitigationDetailsRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("是否存量 是1 否0")
  private Boolean isStock;

  @ApiModelProperty(value = "诉讼id", required = true)
  private Long litigationId;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("立案id")
  private Long registerId;

  @ApiModelProperty("标题")
  private String title;

  @ApiModelProperty("保全案号")
  private String preservationCode;

  @ApiModelProperty("保全类型")
  private String preserveType;

  @ApiModelProperty("保全申请提交时间")
  private Date preserveApplyDate;

  @ApiModelProperty("保全裁定送达时间")
  private Date preserveRuleDate;

  @ApiModelProperty("保险费")
  private BigDecimal insureMoney;

  @ApiModelProperty("时间")
  private Date detailsDate;

  @ApiModelProperty("说明")
  private String detailsDescription;

  @ApiModelProperty(value = "诉讼类型 final:终本 other:其他 drop_lawsuit:撤诉 close_case:结案 preservation:保全", required = true)
  private BillTypeEnum litigationType;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("反担保信息入参")
  private List<RevePropertyInfoRequest> reveInfoRequest;

  @ApiModelProperty("其他财产线索信息入参")
  private List<RevePropertyInfoRequest> propertyInfoRequest;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-02 15:44:58
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
