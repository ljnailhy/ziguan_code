package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.enums.SubjectNatureEnum;
import com.jk.asset.enums.SubjectTypeEnum;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 主体信息入参
 *
 * @author wangshuai
 * @since 2024-06-20 14:25:14
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "主体信息入参")
public class SubjectInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("主体名称")
  private String subjectName;

  @ApiModelProperty("主体logo")
  private String subjectLogo;

  @ApiModelProperty("主体性质'ENTERPRISE'：企业,'NON_ENTERPRISE'：非企业经济组织,'INDIVIDUALITY'：个体工商户,'XIAOWEI'：小微企业主,'PEASANT_HOUSEHOLD'：农户")
  private SubjectNatureEnum nature;

  @ApiModelProperty("主体类型COUNTER_GUARANTOR:反担保人，DEBTOR：债务人")
  private SubjectTypeEnum subjectType;

  @ApiModelProperty("证件类型")
  private String documentType;

  @ApiModelProperty("证件号码")
  private String documentNumber;

  @ApiModelProperty("法定代表人")
  private String legalRepresentative;

  @ApiModelProperty("联系人")
  private String contacts;

  @ApiModelProperty("联系人电话")
  private String phone;

  @ApiModelProperty("区域_省")
  private Long belongProvince;

  @ApiModelProperty("区域_市")
  private Long belongCity;

  @ApiModelProperty("区域_区")
  private Long belongDistrict;

  @ApiModelProperty("详细地址")
  private String address;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("是否删除")
  private Boolean isDeleted;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  @ApiModelProperty("项目唯一标识")
  private String businessNo;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-06-20 14:25:14
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
