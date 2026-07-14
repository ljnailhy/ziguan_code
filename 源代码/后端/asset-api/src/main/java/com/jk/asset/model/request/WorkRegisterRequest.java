package com.jk.asset.model.request;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.common.model.entity.BaseTenantDO;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 工作登记入参
 *
 * @author wangshuai
 * @since 2024-06-28 17:23:16
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "工作登记入参")
public class WorkRegisterRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("对象类型")
  private BillTypeEnum doType;

  @ApiModelProperty(value = "对象id", required = true)
  private Long doId;

  @ApiModelProperty("类型")
  private Long type;

  @ApiModelProperty("工作时间")
  private Date workDate;

  @ApiModelProperty("工作内容")
  private String workContent;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-28 17:23:16
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
