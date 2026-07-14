package com.jk.asset.model.request.property;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.BillContractRequest;
import com.jk.asset.model.request.HangNetworkInfoRequest;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 租赁信息表入参
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "租赁信息表入参")
public class LeaseInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("评估机构")
  private Long estimateId;

  @ApiModelProperty("评估价")
  private BigDecimal estimateMoney;

  @ApiModelProperty("评估有效期开始时间")
  private Date estimateDate;

  @ApiModelProperty("评估有效期截止时间")
  private Date estimateEndDate;

  @ApiModelProperty("代理费用")
  private BigDecimal agencyFee;

  @ApiModelProperty("联系人")
  private String contacts;

  @ApiModelProperty("联系电话")
  private String contactsPhone;

  @ApiModelProperty("处置方案")
  private String disposeProgramme;

  @ApiModelProperty("月租金")
  private BigDecimal monthRent;

  @ApiModelProperty("年租金")
  private BigDecimal yearRent;

  @ApiModelProperty("保证金")
  private BigDecimal margin;

  @ApiModelProperty("租赁期限开始日期")
  private Date leaseTermStart;

  @ApiModelProperty("租赁期限结束日期")
  private Date leaseTermEnd;

  @ApiModelProperty("缴纳周期")
  private PaymentCycleEnum paymentCycle;

  @ApiModelProperty("承租人")
  private Long lessee;

  @ApiModelProperty("租赁用途")
  private String leasePurpose;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("是否评估")
  private Boolean isEstimate;

  @ApiModelProperty("选择中介机构")
  private Long intermediaryId;

  @ApiModelProperty("中介费用")
  private Long intermediaryFee;

  @ApiModelProperty("资产信息")
  private List<PropertyBillRequest> propertyBillRequestList;

  @ApiModelProperty("租赁产权信息")
  private List<PropertyRightInfoRequest> propertyRightInfoRequests;

  @ApiModelProperty("中介关联信息")
  private List<DocumentIntermediaryRequest> documentIntermediaryRequestList;

  @ApiModelProperty("法拍过程")
  private List<HangNetworkInfoRequest> hangNetworkInfoRequestList;

  @ApiModelProperty("缴费周期")
  private List<LeasePaymentCycleRequest> leasePaymentCycleRequestList;

  @ApiModelProperty("合同关联")
  private List<BillContractRequest> billContractRequestList;

  @ApiModelProperty("中介客户线索关联表入参")
  private List<IntermediaryCustomerLeadRequest> intermediaryCustomerLeadRequestList;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-11 18:04:22
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

  /**
   * 缴纳周期'YEAR'：年,'HALF_YEAR'：半年,'SEASON'：季,'MONTH'：月,'DISPOSABLE'：一次性,'OTHER'：其他枚举
   * @author wangshuai
   * @since 2024-07-11 18:04:22
   */
  @Getter
  public enum PaymentCycleEnum {
    YEAR("YEAR","YEAR"),
    HALF_YEAR("HALF_YEAR","HALF_YEAR"),
    SEASON("SEASON","SEASON"),
    MONTH("MONTH","MONTH"),
    DISPOSABLE("DISPOSABLE","DISPOSABLE"),
    OTHER("OTHER","OTHER"),

    ;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    PaymentCycleEnum(String key, String value) {
      this.key = key;
      this.value = value;
    }

    /** 保存key value对的map */
    private static final Map<String, String> MAP = new HashMap<>();

    static {
      PaymentCycleEnum[] enums = PaymentCycleEnum.values();
      for (PaymentCycleEnum objEnum : enums) {
        MAP.put(objEnum.getKey(), objEnum.getValue());
      }
    }

    /**
     * 根据key获得name
     *
     * @param key 键
     * @return 值
     */
    public static String getValue(String key) {
      return PlatformStringUtils.isNotBlank(key) ? MAP.get(key) : PlatformStringUtils.EMPTY;
    }
  }
}
