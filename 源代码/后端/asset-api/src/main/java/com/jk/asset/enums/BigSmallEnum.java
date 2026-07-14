package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 企业划型枚举
 * @author wangTao
 * date2024/6/25 18:12
 **/
@Getter
public enum BigSmallEnum {
    BIG_SMALL("BIG_SMALL","企业划型"),
    BIG_SMALL_DXQY("DXQY","大型企业"),
    BIG_SMALL_ZXQY("ZXQY","中型企业"),
    BIG_SMALL_XXQY("XXQY","小型企业"),
    BIG_SMALL_WXQY("WXQY","微型企业"),
    BIG_SMALL_QT("QT","其他"),;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    BigSmallEnum(String key, String value) {
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
