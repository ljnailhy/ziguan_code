package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目信息表入参
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "项目信息表入参")
public class ProjectInfoRequest extends ProjectInfoExtRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("第三方标识")
  private String thirdPartyId;

  @ApiModelProperty("原所属组织")
  private Long primaryAffiliatedOrg;

  @ApiModelProperty("所属组织")
  private Long affiliatedOrg;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("律所名称")
  private Long lawFirmName;

  @Deprecated
  @ApiModelProperty("律师")
  private Long lawyerId;

  @ApiModelProperty("关联合同")
  private Long contractId;

  @ApiModelProperty("保全经理")
  private String manage;

  @ApiModelProperty("是否移交")
  private Boolean isTransfer;

  @ApiModelProperty("是否核销")
  private Boolean isWriteOff;

  @ApiModelProperty("项目状态")
  private Long projectState;

  @ApiModelProperty("执行时效（调解审批判决日期）")
  private Date adjustTrialDate;

  @ApiModelProperty("债务人id")
  private Long debtorId;

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("所属区域_省")
  private Long province;

  @ApiModelProperty("所属区域_市")
  private Long city;

  @ApiModelProperty("所属区域_区")
  private Long district;

  @ApiModelProperty("详细地址")
  private String address;

  @ApiModelProperty("代偿金额(元)")
  private BigDecimal compensationMoney;

  @ApiModelProperty("代偿时间")
  private Date compensationDate;

  @ApiModelProperty("诉讼时效")
  private Date proceedingAgeingDate;

  @ApiModelProperty("移交至保全部日期")
  private Date transferDate;

  @ApiModelProperty("代偿方案")
  private String compensationPlan;

  @ApiModelProperty("分险比例（债权人）")
  private BigDecimal dividedInsuranceDebtor;

  @ApiModelProperty("分险比例（原担保）")
  private BigDecimal dividedInsuranceSecurity;

  @ApiModelProperty("分险比例（再担保）")
  private BigDecimal dividedInsuranceAgainSecurity;

  @ApiModelProperty("分险比例（其他）")
  private BigDecimal dividedInsuranceOther;

  @ApiModelProperty("是否删除")
  private Boolean isDeleted;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("是否历史代偿")
  private Boolean projectIsHistory;

//  @ApiModelProperty("项目信息详细信息扩展表入参")
//  public  ProjectInfoExtRequest projectInfoExtRequest;

   @ApiModelProperty("反担保/其他财产线索信息入参")
   private List<RevePropertyInfoRequest> revePropertyInfoRequest;

  @ApiModelProperty("主体信息入参")
  private SubjectInfoRequest subjectInfoRequest;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileRequests;

  @ApiModelProperty("证件号")
  private String documentNumber;

  @ApiModelProperty("业务信息")
  List<ProjectBusinessInfoRequest> businessInfos;

  @ApiModelProperty("借款信息")
  List<ProjectLoanInfoRequest> projectLoanInfos;

  @ApiModelProperty("项目状态")
  private List<Long> projectStateList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  // @Override
  public boolean unverified() {
    // String校验：空org.apache.commons.lang3.StringUtils.isBlank 非空isNotBlank 批量-是否有空isAnyBlank 批量-都不空isNoneBlank
    // List校验：空org.apache.commons.collections4.CollectionUtils.isEmpty 非空isNotEmpty
    // 其他Object(或List)：空org.apache.commons.lang3.ObjectUtils.isEmpty 非空isNotEmpty 批量-都不空allNotNull 批量-都空allNull 批量-是否有不空anyNotNull 批量-是否有空anyNull
    // return super.unverified();
    if (ObjectUtils.isEmpty(debtorId)  || ObjectUtils.isEmpty(compensationMoney)
//            || ObjectUtils.isEmpty(compensationDate)
    ){
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
