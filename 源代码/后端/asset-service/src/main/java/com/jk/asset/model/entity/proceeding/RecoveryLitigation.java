package com.jk.asset.model.entity.proceeding;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.model.entity.BaseTenantDO;
import java.math.BigDecimal;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 诉讼对象
 *
 * @author wangshuai
 * @since 2024-07-01 16:38:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("recovery_litigation")
@ApiModel(description = "诉讼对象")
public class RecoveryLitigation extends BaseTenantDO {

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

  @ApiModelProperty("项目名称")
  private Long projectId;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("项目状态 一审 二审 再审 保全 撤诉 调节 审判 执行 恢复执行 终本 结案 其他 登记项目工作")
  private Long projectStatus;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

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
