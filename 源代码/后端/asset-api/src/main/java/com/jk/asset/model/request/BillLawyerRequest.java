package com.jk.asset.model.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.model.entity.BaseTenantDO;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 单据与律师关系表入参
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "单据与律师关系表入参")
public class BillLawyerRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty(value = "对象类型", required = true)
  private String doType;

  @ApiModelProperty(value = "对象id", required = true)
  private Long doId;

  @ApiModelProperty(value = "律师", required = true)
  private Long lawyer;

  @ApiModelProperty(value = "截止时间")
  private Date endDate;

  @ApiModelProperty(value = "是否有效", required = true)
  private Boolean isEffective;

  @ApiModelProperty("备注")
  private String remarks;

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
