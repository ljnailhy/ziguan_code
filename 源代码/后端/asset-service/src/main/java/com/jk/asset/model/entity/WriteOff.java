package com.jk.asset.model.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.WriteOffRequest.WriteOffClassificationEnum;
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
 * 核销对象
 *
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("write_off")
@ApiModel(description = "核销对象")
public class WriteOff extends BaseTenantDO {

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensationAmount;

  @ApiModelProperty("代偿时间")
  private Date compensationDate;

  @ApiModelProperty("核销金额")
  private BigDecimal writeDffAmount;

  @ApiModelProperty("核销时间")
  private Date writeOffDate;

  @ApiModelProperty("核销项目分类 A:A B:B C:C D:D")
  private WriteOffClassificationEnum writeOffClassification;

  @ApiModelProperty("累计回款金额")
  private BigDecimal totalCollectionAmount;

  @ApiModelProperty("回款情况说明")
  private String remarks;

  @ApiModelProperty("是否删除")
  @TableLogic
  private Boolean isDeleted;

  @ApiModelProperty("是否提交")
  private Boolean writeOffStatus;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-07-04 14:51:01
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
