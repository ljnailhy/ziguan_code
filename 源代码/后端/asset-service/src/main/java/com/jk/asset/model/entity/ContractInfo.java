package com.jk.asset.model.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.model.entity.BaseTenantDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同信息对象
 *
 * @author wangshuai
 * @since 2024-06-20 17:46:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("contract_info")
@ApiModel(description = "合同信息对象")
public class ContractInfo extends BaseTenantDO {

  @ApiModelProperty("合同名称")
  private String contractName;

  @ApiModelProperty("合同编号")
  private String contractCode;

  @ApiModelProperty("合同类型")
  private Long contractType;

  @ApiModelProperty("合同金额")
  private BigDecimal contractMoney;

  @ApiModelProperty("签约日期")
  private Date signingDate;

  @ApiModelProperty("开始时间")
  private Date startDate;

  @ApiModelProperty("截止时间")
  private Date endDate;

  @ApiModelProperty("合同摘要")
  private String contractAbstract;

  @ApiModelProperty("代理方式")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private Long agentWay;

  @ApiModelProperty("律所")
  @TableField(updateStrategy = FieldStrategy.IGNORED)
  private Long lawFirmId;

  @ApiModelProperty("代理费收费比例")
  private BigDecimal agencyFeeRatio;

  @ApiModelProperty("跟进人")
  private String followUp;

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-20 17:46:10
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
