package com.jk.asset.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目回款归属 枚举
 * @author wangTao
 * date2024/6/25 18:12
 **/
@Getter
public enum CollectionAscriptionEnum {
    GROUP("GROUP","集团"),
    CULTURAL_TOURISM("CULTURAL_TOURISM","文旅"),
    SMALL_MEDIUM_DAN("SMALL_MEDIUM_DAN","中小担"),
    VER_SMALL_MEDIUM("VER_SMALL_MEDIUM","核销-中小担"),
    VER_GROUP("VER_GROUP","核销-集团"),
    VER_CULTURAL_TOURISM("VER_CULTURAL_TOURISM","核销-文旅"),

    ;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    CollectionAscriptionEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /** 保存key value对的map */
    private static final Map<String, String> MAP = new HashMap<>();

    static {
        CollectionAscriptionEnum[] enums = CollectionAscriptionEnum.values();
        for (CollectionAscriptionEnum objEnum : enums) {
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
