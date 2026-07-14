package com.jk.asset.model.request;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
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
 * 核销入参
 *
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "核销入参")
public class WriteOffRequest {

  @ApiModelProperty("核销时间范围")
  private List<Date> writeOffDateRange;

  @ApiModelProperty("创建时间范围")
  private List<Date> createStampRange;

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("代偿金额")
  private BigDecimal compensationAmount;

  @ApiModelProperty("代偿时间")
  private Date compensationDate;

  @ApiModelProperty("核销金额")
  private BigDecimal writeDffAmount;

  @ApiModelProperty(value = "核销时间", required = true)
  private Date writeOffDate;

  @ApiModelProperty("是否提交")
  private Boolean writeOffStatus;

  @ApiModelProperty("核销项目分类 A:A B:B C:C D:D")
  private WriteOffClassificationEnum writeOffClassification;

  @ApiModelProperty("累计回款金额")
  private BigDecimal totalCollectionAmount;

  @ApiModelProperty("回款情况说明")
  private String remarks;

  @ApiModelProperty("反担保/其他财产线索信息入参")
  private List<RevePropertyInfoRequest> revePropertyInfoRequest;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileRequests;

  @ApiModelProperty("项目状态")
  private List<Long> projectStateList;

  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangtao
   * @since 2024-07-04 14:51:01
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
   * 核销项目分类 A:A B:B C:C D:D枚举
   * @author wangtao
   * @since 2024-07-04 14:51:01
   */
  @Getter
  public enum WriteOffClassificationEnum {
    A("A","A"),
    B("B","B"),
    C("C","C"),
    D("D","D"),

    ;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    WriteOffClassificationEnum(String key, String value) {
      this.key = key;
      this.value = value;
    }

    /** 保存key value对的map */
    private static final Map<String, String> MAP = new HashMap<>();

    static {
      WriteOffClassificationEnum[] enums = WriteOffClassificationEnum.values();
      for (WriteOffClassificationEnum objEnum : enums) {
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
