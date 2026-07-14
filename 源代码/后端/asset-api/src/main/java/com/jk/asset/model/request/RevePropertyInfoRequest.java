package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 反担保/其他财产线索信息入参
 *
 * @author wangtao
 * @since 2024-06-24 09:42:39
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "反担保/其他财产线索信息入参")
public class RevePropertyInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("来源id")
  private Long sourceId;

  @ApiModelProperty("单据类型（其他财产，反担保）")
  private String billType;

  @ApiModelProperty("对象Id")
  private Long doId;

  @ApiModelProperty("对象类型")
  private String doType;

  @ApiModelProperty("担保方式")
  private Long securityWay;

  @ApiModelProperty("反担保人名称")
  private String reveName;

  @ApiModelProperty("担保类型")
  private String securityType;

  @ApiModelProperty("反担保措施/其他财产")
  private String reveMeasure;

  @ApiModelProperty("保全日期")
  private Date preserveDate;

  @ApiModelProperty("备注")
  private String remark;

  @ApiModelProperty("裁定以资抵债日期")
  private Date debtRepaymentDate;

  @ApiModelProperty("裁定抵债金额")
  private BigDecimal debtRepaymentMoney;

  @ApiModelProperty("是否已处置")
  private Boolean isDispose;

  @ApiModelProperty("处置回款金额")
  private BigDecimal disposeMoney;

  @ApiModelProperty("保全类型")
  private PreserveTypeEnum preserveType;

  @ApiModelProperty("保全申请提交时间")
  private Date preserveApplyDate;

  @ApiModelProperty("保全裁定送达时间")
  private Date preserveRuleDate;

  @ApiModelProperty("保险费")
  private BigDecimal insureMoney;

  @ApiModelProperty("是否删除")
  private Boolean isDeleted;

  @ApiModelProperty("项目唯一标识")
  private String businessNo;

  @ApiModelProperty("分录状态：新增ADD，修改UPDATE，删除DELETE")
  private OperationTypeEnum operateType;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  @ApiModelProperty("法拍过程")
  private List<HangNetworkInfoRequest> hangNetworkInfoRequestList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-06-24 09:42:39
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

  @Getter
  public enum PreserveTypeEnum {
    BEFORE_LITIGATION_PRESERVE("BEFORE_LITIGATION_PRESERVE","诉前保全"),
    MIDDLE_LITIGATION_PRESERVE("MIDDLE_LITIGATION_PRESERVE","诉中保全"),

    ;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    PreserveTypeEnum(String key, String value) {
      this.key = key;
      this.value = value;
    }

    /** 保存key value对的map */
    private static final Map<String, String> MAP = new HashMap<>();

    static {
      RevePropertyInfoRequest.PreserveTypeEnum[] enums = RevePropertyInfoRequest.PreserveTypeEnum.values();
      for (RevePropertyInfoRequest.PreserveTypeEnum objEnum : enums) {
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
