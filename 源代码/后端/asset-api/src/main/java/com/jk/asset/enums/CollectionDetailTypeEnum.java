package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  回款明细类型 枚举
 * @author wangTao
 * date2024/6/21 10:13
 **/
@Getter
public enum CollectionDetailTypeEnum {
    COMPENSATORY_CASH("COMPENSATORY_CASH","代偿金额"),
    INTEREST("INTEREST","利息"),
    OTHER_FEE("OTHER_FEE","其他费用"),
    DEFAULT_CASH("DEFAULT_CASH","违约金");
    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    CollectionDetailTypeEnum(String key, String value) {
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
