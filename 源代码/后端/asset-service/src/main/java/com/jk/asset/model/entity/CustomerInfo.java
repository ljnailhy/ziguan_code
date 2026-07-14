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

/**
 * 客户信息表对象
 *
 * @author wangtao
 * @since 2024-06-20 11:38:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("customer_info")
@ApiModel(description = "客户信息表对象")
public class CustomerInfo extends BaseTenantDO {

  @ApiModelProperty("客户名称")
  private String customerName;

  @ApiModelProperty("证件类型")
  private Long documentType;

  @ApiModelProperty("证件号")
  private String documentCode;

  @ApiModelProperty("联系人")
  private String contacts;

  @ApiModelProperty("联系电话")
  private String contactsPhone;

  @ApiModelProperty("来源中介")
  private String mediumSource;

  @ApiModelProperty("客户来源'INTRODUCE'：介绍,'NATURAL_PERSON'：自然人")
  private String customerSource;

  @ApiModelProperty("意向资产")
  private String intentionalAssets;

  @ApiModelProperty("备注")
  private String remark;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-06-20 11:38:52
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
