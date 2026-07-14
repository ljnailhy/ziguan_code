package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/** 担保类型枚举
 * @author wangTao
 * date2024/7/3 15:05
 **/
@Getter
public enum SecurityTypeEnum {
    SECURITY_TYPE_QYBZ("QYBZ","企业保证"),
    SECURITY_TYPE_GRBZ("GRBZ","自然人保证"),
    SECURITY_TYPE_BDCDY("BDCDY","不动产抵押"),
    SECURITY_TYPE_DCDY("DCDY","动产抵押"),
    SECURITY_TYPE_YSZKZY("YSZKZY","应收账款质押"),
    SECURITY_TYPE_QLZY("QLZY","权力质押"),
    SECURITY_TYPE_CHZY("CHZY","存货质押"),
    SECURITY_TYPE_QTLX("QTLX","其他类型"),
    SECURITY_TYPE_NO("NO","无");

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    SecurityTypeEnum(String key, String value) {
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
        MAP.put("QYBZ", "企业保证");
        MAP.put("GRBZ", "自然人保证");
        MAP.put("BDCDY", "不动产抵押");
        MAP.put("DCDY", "动产抵押");
        MAP.put("YSZKZY", "应收账款质押");
        MAP.put("QLZY", "权力质押");
        MAP.put("CHZY", "存货质押");
        MAP.put("QTLX", "其他类型");
        MAP.put("NO", "无");
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
