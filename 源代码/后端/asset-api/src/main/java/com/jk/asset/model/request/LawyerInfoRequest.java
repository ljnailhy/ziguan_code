package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 律师团队入参
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:49
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "律师团队入参")
public class LawyerInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("律所id")
  private Long lawFirmId;

  @ApiModelProperty("律师姓名")
  private String lawyerName;

  @ApiModelProperty("联系电话")
  private String phone;

  @ApiModelProperty("执业证号")
  private String credentialNumber;

  @ApiModelProperty("登录账号")
  private Long userId;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  @ApiModelProperty("分录状态：新增ADD，修改UPDATE，删除DELETE")
  private OperationTypeEnum operateType;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-24 11:09:49
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
