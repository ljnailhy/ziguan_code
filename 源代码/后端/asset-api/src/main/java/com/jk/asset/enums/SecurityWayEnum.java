package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangTao
 * date2024/6/25 11:50
 **/
@Getter
public enum SecurityWayEnum {
    SECURITY_WAY("SECURITY_WAY","担保方式"),
    SECURITY_WAY_ONE("1","保证担保"),
    SECURITY_WAY_TWO("2","抵押担保"),
    SECURITY_WAY_THREE("3","质押担保"),
    SECURITY_WAY_FOUR("4","其他担保");

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    SecurityWayEnum(String key, String value) {
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
