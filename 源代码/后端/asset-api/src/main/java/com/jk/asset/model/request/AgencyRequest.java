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
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代理机构入参
 *
 * @author wangtao
 * @since 2024-06-19 18:14:46
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "代理机构入参")
public class AgencyRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("机构名称")
  private String agencyName;

  @ApiModelProperty("机构类型")
  private Long agencyType;

  @ApiModelProperty("代理性质 租赁:LEASE ASSESS:评估 LEASE_TRANSFER 租赁转让")
  private String agencyNature;

  @ApiModelProperty("联系人")
  private String contacts;

  @ApiModelProperty("联系电话")
  private String contactsPhone;

  @ApiModelProperty("机构地址")
  private String agencyAddress;

  @ApiModelProperty("代理费")
  private BigDecimal agencyFee;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("代理机构ids")
  private String agencyIds;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileRequests;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-06-19 18:14:46
   */
  // @Override
  public boolean unverified() {
    // String校验：空org.apache.commons.lang3.StringUtils.isBlank 非空isNotBlank 批量-是否有空isAnyBlank 批量-都不空isNoneBlank
    // List校验：空org.apache.commons.collections4.CollectionUtils.isEmpty 非空isNotEmpty
    // 其他Object(或List)：空org.apache.commons.lang3.ObjectUtils.isEmpty 非空isNotEmpty 批量-都不空allNotNull 批量-都空allNull 批量-是否有不空anyNotNull 批量-是否有空anyNull
    // return super.unverified();
      return StringUtils.isEmpty(agencyName);
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
