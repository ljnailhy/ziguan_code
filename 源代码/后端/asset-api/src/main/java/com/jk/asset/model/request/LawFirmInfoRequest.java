package com.jk.asset.model.request;

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
 * 律所信息入参
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:30
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "律所信息入参")
public class LawFirmInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("组织id")
  private Long orgId;

  @ApiModelProperty("律所名称")
  private String name;

  @ApiModelProperty("联系人")
  private String contacts;

  @ApiModelProperty("联系电话")
  private String phone;

  @ApiModelProperty("律所地址")
  private String address;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("是否有效")
  private Boolean enabled;

  @ApiModelProperty("是否删除")
  private Boolean isDeleted;

  @ApiModelProperty("律师团队信息")
  private List<LawyerInfoRequest> lawyerInfoRequestList;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  @ApiModelProperty("收款账号")
  private String dueNumber;

  @ApiModelProperty("收款银行")
  private String dueBank;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-24 11:09:30
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
