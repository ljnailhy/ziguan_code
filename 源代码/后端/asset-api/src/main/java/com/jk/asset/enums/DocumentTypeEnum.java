package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/** 证件类型枚举
 * @author wangTao
 * date2024/6/25 14:30
 **/
@Getter
public enum DocumentTypeEnum {
    TYPE_SFZ("SFZ","身份证"),
    TYPE_YYZZ("YYZZ","营业执照"),
    TYPE_JGZ("JGZ","军官证"),
    TYPE_SBZ("SBZ","士兵证"),
    TYPE_HZ("HZ","护照"),
    TYPE_JSZ("JSZ","驾驶证"),
    TYPE_ZZZ("ZZZ","暂住证");

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    DocumentTypeEnum(String key, String value) {
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
        MAP.put("SFZ","身份证");
        MAP.put("YYZZ","营业执照");
        MAP.put("JGZ","军官证");
        MAP.put("SBZ","士兵证");
        MAP.put("HZ","护照");
        MAP.put("JSZ","驾驶证");
        MAP.put("ZZZ","暂住证");

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

    public static String getStrKey(String value) {
        for (Map.Entry<String, String> stringStringEntry : MAP.entrySet()) {
            if (stringStringEntry.getValue().equals(value)){
                return stringStringEntry.getKey();
            }
        }
    return StringUtils.EMPTY;
    }
}
