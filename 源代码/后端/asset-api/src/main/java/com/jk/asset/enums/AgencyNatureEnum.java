package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 代理性质枚举
 *
 * @author wangtao
 * @since 2024-06-19 18:14:46
 */
@Getter
public enum AgencyNatureEnum {
  LEASE("LEASE","租赁"),
  ASSESS("ASSESS","评估"),
  LEASE_TRANSFER("LEASE_TRANSFER","租赁转让")
  ;

  /** 键 */
  private final String key;
  /** 值 */
  private final String value;

  AgencyNatureEnum(String key, String value) {
    this.key = key;
    this.value = value;
  }

  /** 保存key value对的map */
  private static final Map<String, String> MAP = new HashMap<>();

  static {
    OrderEnum[] enums = OrderEnum.values();
    for (OrderEnum objEnum : enums) {
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
