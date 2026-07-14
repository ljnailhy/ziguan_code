package com.jk.asset.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 主体新增枚举类
 *
 * @author WangShuai
 * @since 2024/6/20 14:35
 **/
@Getter
public enum SubjectNatureEnum {
  E("E","企业"),
  NE("NE","非企业经济组织"),
  FM("FM","农户"),
  IB("IB","个体工商户"),
  SME("SME","小微企业主"),
  OTHER("OTHER","其他"),
  ;

  /** 键 */
  private final String key;
  /** 值 */
  private final String value;

  SubjectNatureEnum(String key, String value) {
    this.key = key;
    this.value = value;
  }

  /** 保存key value对的map */
  private static final Map<String, String> MAP = new HashMap<>();

  static {
    SubjectNatureEnum[] enums = SubjectNatureEnum.values();
    for (SubjectNatureEnum objEnum : enums) {
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
  /**
   * 根据 name获得 key
   *
   * @param key
   * @return java.lang.String
   * @author wangshuai
   * @since 2024/3/9 16:44
   **/
  public static SubjectNatureEnum getKey(String value) {
    return MAP.entrySet().stream()
            .filter(entry -> entry.getValue().equals(value))
            .map(entry -> SubjectNatureEnum.valueOf(entry.getKey()))
            .findFirst()
            .orElse(null);
  }
}
