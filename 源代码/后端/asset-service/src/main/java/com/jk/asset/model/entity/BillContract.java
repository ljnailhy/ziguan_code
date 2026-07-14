package com.jk.asset.model.entity;

import com.alibaba.fastjson.JSON;
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

/**
 * 单据合同关联表对象
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("bill_contract")
@ApiModel(description = "单据合同关联表对象")
public class BillContract extends BaseTenantDO {

  @ApiModelProperty("对象类型")
  private String doType;

  @ApiModelProperty("对象id")
  private Long doId;

  @ApiModelProperty("合同id")
  private Long contractId;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-28 11:49:32
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
