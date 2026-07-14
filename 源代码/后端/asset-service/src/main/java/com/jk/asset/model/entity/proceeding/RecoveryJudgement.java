package com.jk.asset.model.entity.proceeding;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.asset.enums.LitigationTypeEnum;
import com.jk.common.model.entity.BaseTenantDO;
import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 审判信息（立案一审二审再审）对象
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
@TableName("recovery_judgement")
@ApiModel(description = "审判信息（立案一审二审再审）对象")
public class RecoveryJudgement extends BaseTenantDO {

  @ApiModelProperty("是否存量 是1 否0")
  private Boolean isStock;

  @ApiModelProperty("立案法院")
  private String filingCourtName;

  @ApiModelProperty("法官")
  private String judgeName;

  @ApiModelProperty("法官联系方式")
  private String judgePhone;

  @ApiModelProperty("立案时间")
  private Date fillingDate;

  @ApiModelProperty("类型 first_instance:一审 second_instance 二审 retrial:再审")
  private LitigationTypeEnum litigationType;

  @ApiModelProperty("立案类型 first_instance:一审 second_instance 二审 retrial:再审")
  private LitigationTypeEnum registerType;

  @ApiModelProperty("立案id")
  private Long registerId;

  @ApiModelProperty("案号")
  private String fillingCode;

  @ApiModelProperty("判决日期")
  private Date judgeDate;

  @ApiModelProperty("开庭时间")
  private Date courtSessionDate;

  @ApiModelProperty("诉讼id")
  private Long litigationId;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-02 09:56:48
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
