package com.jk.asset.model.request.proceeding;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.RevePropertyInfoRequest;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行信息	入参
 *
 * @author wangshuai
 * @since 2024-07-05 09:52:54
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "执行信息	入参")
public class RecoveryExecuteRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("执行状态 apply:已申请 unapply:未申请")
  private ExecuteTypeEnum executeType;

  @ApiModelProperty("是否存量 是1 否0")
  private Boolean isStock;

  @ApiModelProperty(value = "诉讼id", required = true)
  private Long litigationId;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("律所")
  private Long lawFirmId;

  @ApiModelProperty("执行案号")
  private String executeCode;

  @ApiModelProperty("执行法院")
  private String executeCourt;

  @ApiModelProperty("申请执行时间")
  private Date applyExecuteDate;

  @ApiModelProperty("执行员")
  private String executer;

  @ApiModelProperty("联系电话")
  private String executerTelphone;

  @ApiModelProperty("执行裁定下达时间")
  private String executeRulingIssuanceTime;

  @ApiModelProperty("备注")
  private String remarks;

  @ApiModelProperty("流程状态")
  private ProcessStatus flowState;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  @ApiModelProperty("反担保信息入参")
  private List<RevePropertyInfoRequest> reveInfoRequest;

  @ApiModelProperty("其他财产线索信息入参")
  private List<RevePropertyInfoRequest> propertyInfoRequest;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-05 09:52:54
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
   * 执行状态 apply:已申请 unapply:未申请枚举
   * @author wangshuai
   * @since 2024-07-05 09:52:54
   */
  @Getter
  public enum ExecuteTypeEnum {
    APPLY("APPLY","APPLY"),
    UNAPPLY("UNAPPLY","UNAPPLY"),
    REVOKE("REVOKE","REVOKE"),
    RECONCILIATE("RECONCILIATE","RECONCILIATE")
    ;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    ExecuteTypeEnum(String key, String value) {
      this.key = key;
      this.value = value;
    }

    /** 保存key value对的map */
    private static final Map<String, String> MAP = new HashMap<>();

    static {
      ExecuteTypeEnum[] enums = ExecuteTypeEnum.values();
      for (ExecuteTypeEnum objEnum : enums) {
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
