package com.jk.asset.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 主体类型枚举
 *
 * @author WangShuai
 * @since 2024/6/20 14:45
 **/
@Getter
public enum SubjectTypeEnum {
  COUNTER_GUARANTOR("COUNTER_GUARANTOR","反担保人"),
  DEBTOR("DEBTOR","债务人")
  ;

  /** 键 */
  private final String key;
  /** 值 */
  private final String value;

  SubjectTypeEnum(String key, String value) {
    this.key = key;
    this.value = value;
  }

  /** 保存key value对的map */
  private static final Map<String, String> MAP = new HashMap<>();

  static {
    SubjectTypeEnum[] enums = SubjectTypeEnum.values();
    for (SubjectTypeEnum objEnum : enums) {
      MAP.put(objEnum.getKey(), objEnum.getValue());
    }
  }

  /**
   * 根据key获得name
   * @param key
   * @return java.lang.String
   * @author wangshuai
   * @since 2024/3/9 16:44
   **/
  public static String getValue(String key) {
    return StringUtils.isNotBlank(key) ? MAP.get(key) : StringUtils.EMPTY;
  }
}
