package com.jk.asset.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 回款标记枚举
 * @author wangTao
 * date2024/6/25 18:12
 **/
@Getter
public enum CollectionSignEnum {
    CASH("CASH","现金"),
    RE_GUARANTEE("RE_GUARANTEE","再担保"),
    MORTGAGE("MORTGAGE","抵债"),
    SILVER_BILL("SILVER_BILL","银票"),

    ;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    CollectionSignEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /** 保存key value对的map */
    private static final Map<String, String> MAP = new HashMap<>();

    static {
        CollectionSignEnum[] enums = CollectionSignEnum.values();
        for (CollectionSignEnum objEnum : enums) {
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
