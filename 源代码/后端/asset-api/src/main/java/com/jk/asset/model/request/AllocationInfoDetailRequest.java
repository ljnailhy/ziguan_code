package com.jk.asset.model.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.model.entity.BaseTenantDO;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 项目分配/变更明细入参
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "项目分配/变更明细入参")
public class AllocationInfoDetailRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty(value = "分配Id", required = true)
  private Long allocationId;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("保全经理")
  private String manage;

  @ApiModelProperty("律所")
  private Long lawFirm;

  @ApiModelProperty("关联合同")
  private Long relatedContracts;

  @ApiModelProperty("律师")
  private String lawyers;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  @ApiModelProperty("分录状态：新增ADD，修改UPDATE，删除DELETE")
  private OperationTypeEnum operateType;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-28 10:13:53
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
