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

import java.math.BigDecimal;

/**
 * 回款目标明细对象
 *
 * @author wangshuai
 * @since 2024-06-25 10:24:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("payment_collection_target_detail")
@ApiModel(description = "回款目标明细对象")
public class PaymentCollectionTargetDetail extends BaseTenantDO {

  @ApiModelProperty("目标id")
  private Long targetId;

  @ApiModelProperty("公司")
  private Long companyId;

  @ApiModelProperty("部门")
  private Long deptId;

  @ApiModelProperty("人员")
  private Long userId;

  @ApiModelProperty("自主追偿回款目标")
  private BigDecimal targetDetailMoney;

  @ApiModelProperty("委托追偿回款目标")
  private BigDecimal entrustDetailMoney;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-25 10:24:51
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
