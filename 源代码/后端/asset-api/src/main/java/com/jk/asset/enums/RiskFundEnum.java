package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/** 风补基金枚举
 * @author wangTao
 * date2024/6/25 18:33
 **/
@Getter
public enum RiskFundEnum {

    RISK_FUND("RISK_FUND","风补基金"),
    RISK_FUND_0("0","无风补机构"),
    RISK_FUND_A("A","慈利县人民政府"),
    RISK_FUND_B("B","湘西经济开发区"),
    RISK_FUND_C("C","洪江市人民政府"),
    RISK_FUND_D("D","平江县人民政府"),
    RISK_FUND_E("E","绥宁县人民政府"),
    RISK_FUND_F("F","吉首经开区（腾达）"),
    RISK_FUND_G("G","洞口县经开区"),
    RISK_FUND_H("H","双牌县"),
    RISK_FUND_I("I","马栏山管委会园区"),
    RISK_FUND_J("J","衡山县人民政府"),
    RISK_FUND_K("K","娄星区风补基金"),
    RISK_FUND_90("90","长沙政银担平台"),
    RISK_FUND_91("91","株洲政银担平台"),
    RISK_FUND_92("92","马栏山政银担平台"),
    RISK_FUND_L("L","吉首经开区-产业转移担"),
    RISK_FUND_M("M","隆平数据科技有限公司"),
    RISK_FUND_N("N","政银担-旅游风补"),
    RISK_FUND_SZSCQ("SZSCQ","省知识产权风补"),
    RISK_FUND_XHGLQFB("XHGLQFB","西湖管理区风险补偿基金"),
    RISK_FUND_ZJTZFB("ZJTZFB","芷江侗族风补");

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    RiskFundEnum(String key, String value) {
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
