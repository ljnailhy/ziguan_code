package com.jk.asset.model.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.model.entity.BaseTenantDO;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * 单据合同关联表入参
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:32
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "单据合同关联表入参")
public class BillContractRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("对象类型")
  private String doType;

  @ApiModelProperty("对象id")
  private Long doId;

  @ApiModelProperty("合同id")
  private Long contractId;

  @ApiModelProperty("分录状态：新增ADD，修改UPDATE，删除DELETE")
  private OperationTypeEnum operateType;

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
    return StringUtils.isBlank(doType) || null == doId || null == contractId;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
