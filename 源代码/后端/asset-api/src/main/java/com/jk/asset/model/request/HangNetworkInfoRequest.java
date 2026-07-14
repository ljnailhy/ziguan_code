package com.jk.asset.model.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.model.entity.BaseTenantDO;
import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 挂网信息表入参
 *
 * @author wangshuai
 * @since 2024-07-09 15:15:21
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "挂网信息表入参")
public class HangNetworkInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("转让id/反担保id")
  private Long doId;

  @ApiModelProperty("数据对象类型")
  private String doType;

  @ApiModelProperty("类型")
  private Long hangNetworkType;

  @ApiModelProperty("挂网时间")
  private Date hangNetworkDate;

  @ApiModelProperty("挂网价格")
  private BigDecimal hangNetworkMoney;

  @ApiModelProperty("挂网报价")
  private BigDecimal hangNetworkQuotation;

  @ApiModelProperty("状态")
  private Long hangNetworkState;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("分录状态：新增ADD，修改UPDATE，删除DELETE")
  private OperationTypeEnum operateType;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-09 15:15:21
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
