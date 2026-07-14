package com.jk.asset.model.request.proceeding;

import java.util.Date;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.enums.LitigationTypeEnum;
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
 * 审判信息（立案一审二审再审）入参
 *
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "审判信息（立案一审二审再审）入参")
public class RecoveryJudgementRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty(value = "是否存量 是1 否0", required = true)
  private Boolean isStock;

  @ApiModelProperty(value = "立案法院", required = true)
  private String filingCourtName;

  @ApiModelProperty("法官")
  private String judgeName;

  @ApiModelProperty("法官联系方式")
  private String judgePhone;

  @ApiModelProperty("立案时间")
  private Date fillingDate;

  @ApiModelProperty("类型 first_instance:一审 second_instance 二审 retrial:再审")
  private LitigationTypeEnum litigationType;

  @ApiModelProperty("立案类型 first_instance:一审 second_instance 二审 retrial:再审")
  private LitigationTypeEnum registerType;

  @ApiModelProperty("立案id")
  private Long registerId;

  @ApiModelProperty("案号")
  private String fillingCode;

  @ApiModelProperty("判决日期")
  private Date judgeDate;

  @ApiModelProperty("开庭时间")
  private Date courtSessionDate;

  @ApiModelProperty(value = "诉讼id", required = true)
  private Long litigationId;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("律所")
  private Long lawFirmId;

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
   * @since 2024-07-02 09:56:48
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
   * 类型 first_instance:一审 second_instance 二审 retrial:再审枚举
   * @author wangshuai
   * @since 2024-07-02 09:56:48
   */
//  @Getter
//  public enum LitigationTypeEnum {
//    first_instance("first_instance","first_instance"),
//    second_instance("second_instance","second_instance"),
//    retrial("retrial","retrial"),
//
//    ;
//
//    /** 键 */
//    private final String key;
//    /** 值 */
//    private final String value;
//
//    LitigationTypeEnum(String key, String value) {
//      this.key = key;
//      this.value = value;
//    }

    /** 保存key value对的map */
//    private static final Map<String, String> MAP = new HashMap<>();
//
//    static {
//      LitigationTypeEnum[] enums = LitigationTypeEnum.values();
//      for (LitigationTypeEnum objEnum : enums) {
//        MAP.put(objEnum.getKey(), objEnum.getValue());
//      }
//    }
//
//    /**
//     * 根据key获得name
//     *
//     * @param key 键
//     * @return 值
//     */
//    public static String getValue(String key) {
//      return PlatformStringUtils.isNotBlank(key) ? MAP.get(key) : PlatformStringUtils.EMPTY;
//    }
//  }
}
