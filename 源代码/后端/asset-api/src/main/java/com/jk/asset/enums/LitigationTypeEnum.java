package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 审判类型
 *
 * @author WangShuai
 * @since 2024/7/2 11:13
 **/
@Getter
public enum LitigationTypeEnum {
  FIRST_INSTANCE("FIRST_INSTANCE","一审"),
  SECOND_INSTANCE("SECOND_INSTANCE","二审"),
  RETRIAL("RETRIAL","再审"),
  REGISTER("REGISTER","立案"),
  ;

  /** 键 */
  private final String key;
  /** 值 */
  private final String value;

  LitigationTypeEnum(String key, String value) {
    this.key = key;
    this.value = value;
  }

  /** 保存key value对的map */
  private static final Map<String, String> MAP = new HashMap<>();

  static {
    LitigationTypeEnum[] enums = LitigationTypeEnum.values();
    for (LitigationTypeEnum objEnum : enums) {
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
