package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformStringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务信息表入参
 *
 * @author wangtao
 * @since 2024-07-26 18:51:12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "业务信息表入参")
public class ProjectBusinessInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty(value = "项目Id", required = true)
  private Long projectId;

  @ApiModelProperty("业务编号")
  private String relatedBusNo;

  @ApiModelProperty("业务类型 线上审批：YBXZCN；	上会审批：YBXZCW；	见贷即保：JDJB；")
  private BusinessTypeEnum businessType;

  @ApiModelProperty("产品名称")
  private Long productName;

  @ApiModelProperty("大类")
  private Long type;

  @ApiModelProperty("风险归类")
  private Long ristTypeAfterGuarantee;

  @ApiModelProperty("分险比例（债权人）")
  private BigDecimal dividedInsuranceDebtor;

  @ApiModelProperty("分险比例（原担保）")
  private BigDecimal dividedInsuranceSecurity;

  @ApiModelProperty("分险比例（再担保）")
  private BigDecimal dividedInsuranceAgainSecurity;

  @ApiModelProperty("分险比例（其他）")
  private BigDecimal dividedInsuranceOther;

  @ApiModelProperty("风补基金")
  private Long riskCompensation;

  @ApiModelProperty("a角")
  private Long aid;

  @ApiModelProperty("A角名称")
  private String aname;

  @ApiModelProperty("A角账号")
  private String acode;

  @ApiModelProperty("b角")
  private Long bid;

  @ApiModelProperty("B角账号")
  private String bcode;

  @ApiModelProperty("B角名称")
  private String bname;

  @ApiModelProperty("分录状态：新增ADD，修改UPDATE，删除DELETE")
  private OperationTypeEnum operateType;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-07-26 18:51:12
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
   * 业务类型 线上审批：YBXZCN；	上会审批：YBXZCW；	见贷即保：JDJB；枚举
   * @author wangtao
   * @since 2024-07-26 18:51:12
   */
  @Getter
  public enum BusinessTypeEnum {
    YBXZCN("YBXZCN","线上审批"),
    YBXZCW("YBXZCW","上会审批"),
    JDJB("JDJB","见贷即保"),

    ;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    BusinessTypeEnum(String key, String value) {
      this.key = key;
      this.value = value;
    }

    /** 保存key value对的map */
    private static final Map<String, String> MAP = new HashMap<>();

    static {
      BusinessTypeEnum[] enums = BusinessTypeEnum.values();
      for (BusinessTypeEnum objEnum : enums) {
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
