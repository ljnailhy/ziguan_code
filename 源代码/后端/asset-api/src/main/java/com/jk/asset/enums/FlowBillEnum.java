package com.jk.asset.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程和单据对应枚举
 *
 * @author WangShuai
 * @since 2024/7/3 14:40
 **/
@Getter
public enum FlowBillEnum {
  ALLOCATION_INFO("Process_1719651753163", "项目分配/变更"),
  RECOVERY_JUDGEMENT("Process_1719987769368", "立案一审"),
  RECOVERY_ADJUST_TRIAL("Process_1719998138810", "调解审判"),
  RECOVERY_PAYMENT_COLLECTION("Process_1720689427663", "回款"),
  RECOVERY_PAYMENT("Process_1720748681086", "付款"),
  PRESERVATION("Process_1719997849910", "诉讼保全"),
  RECOVERY_EXECUTE("Process_1720515062973", "执行登记"),
  LEASE_INFO("Process_1720853749554", "租赁登记"),
  ASSET_TRANSFER("Process_1720857653111", "转让登记"),
  OPERATION_INFO("Process_1720864610987", "运营登记"),
  PROJECT_TRANSFER("Process_1721288798134", "项目移交")


      ;

  /**
   * 键
   */
  private final String key;
  /**
   * 值
   */
  private final String value;

  FlowBillEnum(String key, String value) {
    this.key = key;
    this.value = value;
  }

  /**
   * 保存key value对的map
   */
  private static final Map<String, String> MAP = new HashMap<>();

  static {
    FlowBillEnum[] enums = FlowBillEnum.values();
    for (FlowBillEnum objEnum : enums) {
      MAP.put(objEnum.getKey(), objEnum.getValue());
    }
  }

  /**
   * 根据key获得name
   *
   * @param key
   * @return java.lang.String
   * @author wangshuai
   * @since 2024/3/9 16:44
   **/
  public static String getValue(String key) {
    return StringUtils.isNotBlank(key) ? MAP.get(key) : StringUtils.EMPTY;
  }
}