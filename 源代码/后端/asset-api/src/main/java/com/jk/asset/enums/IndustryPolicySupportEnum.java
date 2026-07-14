package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/** 政策扶持领域类别枚举
 * @author wangTao
 * date2024/6/25 18:21
 **/
@Getter
public enum IndustryPolicySupportEnum {
    INDUSTRY_POLICY_SUPPORT("INDUSTRY_POLICY_SUPPORT","政策扶持领域类别"),
    INDUSTRY_POLICY_SUPPORT_CYCX("CYCX","创业创新"),
    INDUSTRY_POLICY_SUPPORT_SN("SN","三农"),
    INDUSTRY_POLICY_SUPPORT_ZLXXCY("ZLXXCY","战略新兴产业"),
    INDUSTRY_POLICY_SUPPORT_XWQY("XWQY","小微企业"),
    INDUSTRY_POLICY_SUPPORT_W("W","无");

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    IndustryPolicySupportEnum(String key, String value) {
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
