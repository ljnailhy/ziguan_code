package com.jk.asset.model.request.proceeding;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.jk.common.utils.PlatformStringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * 调解或审判信息	入参
 *
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "调解或审判信息	入参")
public class RecoveryAdjustTrialRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("调解或审判类型 before_ligation_mediation:诉前调解 middle_litigation_mediation：诉中调解 juge:判决")
  private AdjustTrialTypeEnum adjustTrialType;

  @ApiModelProperty("诉讼id")
  private Long litigationId;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("是否存量 是1 否0")
  private Boolean isStock;

  @ApiModelProperty("调解或审判日期")
  private Date adjustTrialDate;

  @ApiModelProperty("判决案号")
  private String adjustCode;

  @ApiModelProperty(value = "代偿金额", required = true)
  private BigDecimal compensatoryAmount;

  @ApiModelProperty(value = "利息", required = true)
  private BigDecimal interest;

  @ApiModelProperty(value = "违约金", required = true)
  private BigDecimal backOutAmount;

  @ApiModelProperty(value = "其他费用", required = true)
  private BigDecimal otherAmount;

  @ApiModelProperty(value = "特殊情况说明", required = true)
  private String specialRemarks;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-03 10:13:14
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
   * 调解或审判类型 before_ligation_mediation:诉前调解 middle_litigation_mediation：诉中调解 juge:判决枚举
   * @author wangshuai
   * @since 2024-07-03 10:13:14
   */
  @Getter
  public enum AdjustTrialTypeEnum {
    BEFORE_LITIGATION_MEDIATION("BEFORE_LITIGATION_MEDIATION","诉前调解"),
    MIDDLE_LITIGATION_MEDIATION("MIDDLE_LITIGATION_MEDIATION","诉中调解"),
    JUDG("JUDG","判决"),

    ;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    AdjustTrialTypeEnum(String key, String value) {
      this.key = key;
      this.value = value;
    }

    /** 保存key value对的map */
    private static final Map<String, String> MAP = new HashMap<>();

    static {
      AdjustTrialTypeEnum[] enums = AdjustTrialTypeEnum.values();
      for (AdjustTrialTypeEnum objEnum : enums) {
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
