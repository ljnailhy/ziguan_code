package com.jk.asset.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.model.entity.BaseTenantDO;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 单据与律师关系表对象
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("bill_lawyer")
@ApiModel(description = "单据与律师关系表对象")
public class BillLawyer extends BaseTenantDO {

  @ApiModelProperty("对象类型")
  private String doType;

  @ApiModelProperty("对象id")
  private Long doId;

  @ApiModelProperty("律师")
  private Long lawyer;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty(value = "截止时间")
  private Date endDate;

  @ApiModelProperty(value = "是否有效", required = true)
  private Boolean isEffective;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-28 11:49:45
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
