package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/** 字典枚举
 * @author wangTao
 * date2024/6/25 16:22
 **/
@Getter
public enum DictEnum {
    /**
     * 产品名称
     */
    OFF_LINE("OFF_LINE","线下标准化产品"),
    ON_LINE("ON_LINE","线上批量产品"),
    TYPE("TYPE","大类"),
    TRADITIONAL_PRODUCT("TRADITIONAL_PRODUCT","传统产品"),
    PRODUCT_NAME_XHD("XHD","湘汇担"),
    PRODUCT_NAME_ZSCQD("ZSCQD","知识产权担"),
    PRODUCT_NAME_ZYD("ZYD","政银担"),
    PRODUCT_NAME_SYD("SYD","税银担"),
    PRODUCT_NAME_EYD("EYD","E银-抵押贷（建行）"),
    PRODUCT_NAME_SED("SED","E银-云税贷（建行）"),
    PRODUCT_NAME_YBD("YBD","E银-税e贷（光大）"),
    PRODUCT_NAME_SJD("SJD","E银-税金贷（平安）"),
    PRODUCT_NAME_SRD("SRD","E银-税融贷（中行）"),
    PRODUCT_NAME_SDD("SDD","E银-税担贷（农行）"),
    PRODUCT_NAME_E_XLGYL("E-XLGYL","E银-湘隆供应链担"),
    PRODUCT_NAME_E_SED("E-SED","E银-税e贷（长行）"),
    PRODUCT_NAME_E_JJD("E-JJD","E银-经营贷（工行）"),
    PRODUCT_NAME_XJD("XJD","E银-湘西产业转移"),
    PRODUCT_NAME_CTDB("CTDB","传统产品"),
    PRODUCT_NAME_XLGYL("XLGYL","湘隆供应链政银担"),
    PRODUCT_NAME_MXD("MXD","民宿担"),
    PRODUCT_NAME_MLSZYD("MLSZYD","文创担"),
    PRODUCT_NAME_XCD("XCD","湘茶担"),
    PRODUCT_NAME_YDED_NH("YDED-NH","银担E贷-农行"),
    PRODUCT_NAME_KED("KED","科e担"),
    PRODUCT_NAME_YDED_JH("YDED-JH","银担E贷-建行（线上）"),
    PRODUCT_NAME_YDED_JT("YDED-JT","银担E贷-交行"),
    PRODUCT_NAME_YDED_GH("YDED-GH","银担E贷-工行（线上）"),
    PRODUCT_NAME_CXD("CXD","长湘贷（长沙银行）"),
    PRODUCT_NAME_KLD("KLD","快乐E贷（长沙银行）"),
    PRODUCT_NAME_GJHR("GJHR","共建互认（建设银行）"),
    PRODUCT_NAME_SEDCS("SEDCS","税e贷（长沙银行）"),
    PRODUCT_NAME_YDED_PA("YDED-PA","银担E贷-平安（线上）"),
    PRODUCT_NAME_YDED_JHXX("YDED-JHXX","银担E贷-建行（线下）"),
    PRODUCT_NAME_YDED_KCZH("YDED-KCZH","银担E贷-科创贷（中行）"),
    PRODUCT_NAME_YDED_ZH("YDED-ZH","银担E贷-中行（线下）"),
    PRODUCT_NAME_YDED_YC("YDED-YC","银担E贷-邮储"),
    PRODUCT_NAME_PAYHXX("PAYHXX","银担E贷-平安（线下）"),
    PRODUCT_NAME_YDED_GHXX("YDED-GHXX","银担E贷-工行（线下）"),
    PRODUCT_NAME_YDED_PAD("YDED-PAD","平安贷"),
    PRODUCT_NAME_SBGXD("SBGXD","设备更新担"),
    PRODUCT_NAME_E_SRDJG("E-SRDJG","E银-税融贷（交行）"),
    PRODUCT_NAME_DYD("DYD","抵押担"),
    PRODUCT_NAME_GXD("GXD","高新担"),
    PRODUCT_NAME_JFRD("JFRD","金芙蓉担"),
    PRODUCT_NAME_ECYZYD("ECYZYD","E银-产业转移担"),
    PRODUCT_NAME_JH_JDD("JH-JDD","建担贷"),
    PRODUCT_NAME_JH_JDDXC("JH-JDDXC","建担贷（乡村振兴）"),
    PRODUCT_NAME_GYLD_XWF("GYLD-XWF","供应链担-新五丰"),
    PRODUCT_NAME_XNYD("XNYD","湘农易担"),
    PRODUCT_NAME_JED("JED","交e担"),
    PRODUCT_NAME_XDED_YC("XDED-YC","湘担E贷-邮储"),
    PRODUCT_NAME_XDED_ZH("XDED-ZH","湘担E贷-中行"),
    PRODUCT_NAME_GDKD2("GDKD2","GDKD2 国担快贷2.0"),
    PRODUCT_NAME_GYYD("GYYD","工银易担"),
    PRODUCT_NAME_YDPHD_CSNH("YDPHD-CSNH","银担普惠贷-长沙农商"),
    PRODUCT_NAME_WZD("WZD","微众担"),
    PRODUCT_NAME_HNED("HNED","惠农E贷"),
    PRODUCT_NAME_JDD_ZX("JDD-ZX","建担贷（中小）"),
    PRODUCT_NAME_JDD_WL("JDD-WL","建担贷（文旅）"),
    PRODUCT_NAME_YDED_ZHXS("YDED-ZHXS","银担E贷-中行（线上）"),
    PRODUCT_NAME_YDPHD_HNYH("YDPHD-HNYH","银担普惠贷-湖南银行"),
    PRODUCT_NAME_YDPHD_CSYH("YDPHD-CSYH","银担普惠贷-长沙银行"),
    PRODUCT_NAME_YDPHD_XSNH("YDPHD-XSNH","银担普惠贷-星沙农商"),
    PRODUCT_NAME_WSD("WSD","网商贷"),
    PRODUCT_NAME_DYYD("DYYD","抵押云担"),
    PRODUCT_NAME_YQD("YQD","园区担"),
    PRODUCT_NAME_PHED_JTYH("PHED-JTYH","普惠E贷-交通银行"),
    PRODUCT_NAME_XDED_YCXCZX("XDED-YCXCZX","湘担E贷-邮储（乡村振兴）"),
    PRODUCT_NAME_ZJTSFB("ZJTSFB","芷江侗族风补"),
    PRODUCT_NAME_LYD_LSJD("LYD-LSJD","旅游担-连锁酒店"),
    PRODUCT_NAME_XHGLQFB("XHGLQFB","西湖管理区风险补偿基金"),
    PRODUCT_NAME_XXYHXSD("XXYHXSD","兴业银行湘速担"),

    /**
     * 合作银行
     */
    COOPERATE_BANK("COOPERATE_BANK","合作银行"),
    COOPERATE_BANK_XYYH("XYYH","兴业银行股份有限公司"),
    COOPERATE_BANK_ZGYH("ZGYH","中国银行股份有限公司"),
    COOPERATE_BANK_YCYH("YCYH","中国邮政储蓄银行股份有限公司"),
    COOPERATE_BANK_NYYH("NYYH","中国农业银行股份有限公司"),
    COOPERATE_BANK_CCB("CCB","中国建设银行股份有限公司"),
    COOPERATE_BANK_JTYH("JTYH","交通银行股份有限公司"),
    COOPERATE_BANK_GSYH("GSYH","中国工商银行股份有限公司"),
    COOPERATE_BANK_GDYH("GDYH","中国光大银行股份有限公司"),
    COOPERATE_BANK_MSYH("MSYH","中国民生银行股份有限公司"),
    COOPERATE_BANK_NFH("NFH","中国农业发展银行"),
    COOPERATE_BANK_HFYH("HFYH","恒丰银行股份有限公司"),
    COOPERATE_BANK_CCBX("CCBX","中国人民财产保险股份有限公司"),
    COOPERATE_BANK_HRXJ("HRXJ","湖南银行股份有限公司"),
    COOPERATE_BANK_PFYH("PFYH","上海浦东发展银行股份有限公司"),
    COOPERATE_BANK_BHYH("BHYH","渤海银行股份有限公司"),
    COOPERATE_BANK_SXYH("SXYH","湖南三湘银行股份有限公司"),
    COOPERATE_BANK_CSYH("CSYH","长沙银行股份有限公司"),
    COOPERATE_BANK_BJYH("BJYH","北京银行股份有限公司"),
    COOPERATE_BANK_RJYH("RJYH","沅江浦发村镇银行股份有限公司"),
    COOPERATE_BANK_HNNXS("HNNXS","湖南省农村信用社联合社"),
    COOPERATE_BANK_PAYH("PAYH","平安银行股份有限公司"),
    COOPERATE_BANK_JCK("JCK","中国进出口银行"),
    COOPERATE_BANK_HXYH("HXYH","华夏银行股份有限公司"),
    COOPERATE_BANK_ZXYH("ZXYH","中信银行股份有限公司"),
    COOPERATE_BANK_ZSYH("ZSYH","浙商银行股份有限公司"),
    COOPERATE_BANK_DGYH("DGYH","东莞银行股份有限公司"),
    COOPERATE_BANK_GFYH("GFYH","广发银行股份有限公司"),
    COOPERATE_BANK_KFYH("KFYH","国家开发银行"),
    COOPERATE_BANK_CLHNSH("CLHNSH","慈利沪农商村镇银行股份有限公司"),
    COOPERATE_BANK_SCXXD("SCXXD","湖南省财信小额贷款有限公司"),
    COOPERATE_BANK_HNJJZX("HNJJZX","湖南金交中心"),
    COOPERATE_BANK_KLTBXD("KLTBXD","湖南快乐通宝小额贷款有限公司"),
    COOPERATE_BANK_CSGXLGXD("CSGXLGXD","长沙高新开发区麓谷小额贷款有限公司"),
    COOPERATE_BANK_CZTXD("CZTXD","湖南省长株潭试验区小额贷款有限公司"),
    COOPERATE_BANK_YXN("YXN","易喜农"),
    COOPERATE_BANK_CSNCSY("CSNCSY","长沙农村商业银行股份有限公司"),
    COOPERATE_BANK_ZZNCSY("ZZNCSY","株洲农村商业银行股份有限公司"),
    COOPERATE_BANK_GDNYYH("GDNYYH","广东南粤银行股份有限公司"),
    COOPERATE_BANK_ZWQD("ZWQD","暂未确定"),
    COOPERATE_BANK_JSNCSY("JSNCSY","湖南吉首农村商业银行股份有限公司"),
    COOPERATE_BANK_XXCHCZ("XXCHCZ","湘西长行村镇银行股份有限公司"),
    COOPERATE_BANK_XJXQNCSY("XJXQNCSY","湖南湘江新区农村商业银行股份有限公司"),
    COOPERATE_BANK_MLNCSY("MLNCSY","湖南汨罗农村商业银行股份有限公司"),
    COOPERATE_BANK_QT("QT","其它机构"),
    COOPERATE_BANK_NXNCSY("NXNCSY","湖南宁乡农村商业银行股份有限公司"),
    COOPERATE_BANK_YYNCSY("YYNCSY","益阳农村商业银行股份有限公司"),
    COOPERATE_BANK_XSNCSY("XSNCSY","湖南新邵农村商业银行股份有限公司"),
    COOPERATE_BANK_HNXSNCSY("HNXSNCSY","湖南星沙农村商业银行股份有限公司"),
    COOPERATE_BANK_ZJJNSH("ZJJNSH","张家界农村商业银行股份有限公司"),
    COOPERATE_BANK_SZQHWZYH("SZQHWZYH","深圳前海微众银行股份有限公司"),
    COOPERATE_BANK_ZJWSYH("ZJWSYH","浙江网商银行股份有限公司"),

    /**
     * 行业分类(工信部)
     */
    INDUSTRY_GXB("INDUSTRY_GXB","行业分类(工信部)"),
    INDUSTRY_A("A","农、林、牧、渔业"),
    INDUSTRY_BC("BC","工业"),
    INDUSTRY_E("E","建筑业"),
    INDUSTRY_F51("F51","批发业"),
    INDUSTRY_F52("F52","零售业"),
    INDUSTRY_G53_G58("G53-G58","交通运输业"),
    INDUSTRY_G59("G59","仓储业"),
    INDUSTRY_G60("G60","邮政业"),
    INDUSTRY_H61("H61","住宿业"),
    INDUSTRY_H62("H62","餐饮业"),
    INDUSTRY_I63_I64("I63-I64","信息传输业"),
    INDUSTRY_I65("I65","软件和信息技术服务业"),
    INDUSTRY_K701("K701","房地产开发经营"),
    INDUSTRY_K702("K702","物业管理"),
    INDUSTRY_L("L","租赁和商务服务业"),
    INDUSTRY_Z("Z","其他未列明行业"),

    /**
     * 证件类型
     */
    ID_TYPE("ID_TYPE","证件类型"),

    PAY_STATUS("PAY_STATUS","付款状态"),
    PAY_STATUS_001("PAY_STATUS_001","确认中"),
    PAY_STATUS_002("PAY_STATUS_002","已付款"),

    COLLECTION_STATUS("COLLECTION_STATUS","回款状态"),
    COLLECTION_STATUS_001("COLLECTION_STATUS_001","确认中"),
    COLLECTION_STATUS_002("COLLECTION_STATUS_002","已回款"),
    PAY_TYPE("PAY_TYPE","付款类型"),


    PROJECT_STATE("PROJECT_STATE","项目状态"),
    PROJECT_STATE_01("PROJECT_STATE_01","未分配"),
    PROJECT_STATE_17("PROJECT_STATE_17","分配中"),
    PROJECT_STATE_02("PROJECT_STATE_02","已分配"),
    PROJECT_STATE_03("PROJECT_STATE_03","待立案"),
    PROJECT_STATE_04("PROJECT_STATE_04","已立案"),
    PROJECT_STATE_05("PROJECT_STATE_05","保全"),
    PROJECT_STATE_06("PROJECT_STATE_06","一审"),
    PROJECT_STATE_07("PROJECT_STATE_07","二审"),
    PROJECT_STATE_08("PROJECT_STATE_08","再审"),
    PROJECT_STATE_09("PROJECT_STATE_09","已撤诉"),
    PROJECT_STATE_10("PROJECT_STATE_10","诉前调解"),
    PROJECT_STATE_11("PROJECT_STATE_11","诉中调解"),
    PROJECT_STATE_12("PROJECT_STATE_12","判决"),
    PROJECT_STATE_13("PROJECT_STATE_13","执行中"),
    PROJECT_STATE_14("PROJECT_STATE_14","终本"),
    PROJECT_STATE_15("PROJECT_STATE_15","已结案"),
    PROJECT_STATE_16("PROJECT_STATE_16","其他"),


    PROPERTY_TAG("PROPERTY_TAG","资产标签"),
    PROPERTY_TAG_001("PROPERTY_TAG_001","租赁中"),
    PROPERTY_TAG_002("PROPERTY_TAG_002","转让中"),
    PROPERTY_TAG_003("PROPERTY_TAG_003","正常"),
    PROPERTY_TAG_004("PROPERTY_TAG_004","待维修保养"),


    PROPERTY_LARGE_CATEGORY("PROPERTY_LARGE_CATEGORY","资产大类"),
    PROPERTY_LARGE_CATEGORY_001("PROPERTY_LARGE_CATEGORY_001","动产"),
    PROPERTY_LARGE_CATEGORY_002("PROPERTY_LARGE_CATEGORY_002","不动产"),


    PROPERTY_TYPE("PROPERTY_TYPE","资产分类"),
    PROPERTY_TYPE_001("PROPERTY_TYPE_001","房产"),
    PROPERTY_TYPE_002("PROPERTY_TYPE_002","土地"),
    PROPERTY_TYPE_003("PROPERTY_TYPE_003","船舶"),
    PROPERTY_TYPE_004("PROPERTY_TYPE_004","设备"),
    PROPERTY_TYPE_005("PROPERTY_TYPE_005","汽车"),
    PROPERTY_TYPE_006("PROPERTY_TYPE_006","其他"),


    SOURCE_TYPE("SOURCE_TYPE","资产来源"),
    SOURCE_TYPE_001("SOURCE_TYPE_001","以物抵债"),
    SOURCE_TYPE_002("SOURCE_TYPE_002","其他资产"),
    SOURCE_TYPE_003("SOURCE_TYPE_003","自购资产"),


    ACCESS_WAY("ACCESS_WAY","资产获取方式"),
    ACCESS_WAY_001("ACCESS_WAY_001","诉讼"),
    ACCESS_WAY_002("ACCESS_WAY_002","调解"),
    ACCESS_WAY_003("ACCESS_WAY_003","执行"),
    ACCESS_WAY_004("ACCESS_WAY_004","自购"),
    ACCESS_WAY_005("ACCESS_WAY_005","其他"),


    MESSAGE_TYPE("MESSAGE_TYPE","消息提醒"),
    MESSAGE_TYPE_001("MESSAGE_TYPE_001","保全预警"),
    MESSAGE_TYPE_002("MESSAGE_TYPE_002","诉讼预警"),
    MESSAGE_TYPE_003("MESSAGE_TYPE_003","执行预警"),
    MESSAGE_TYPE_004("MESSAGE_TYPE_004","工作登记提醒"),
    MESSAGE_TYPE_005("MESSAGE_TYPE_005","未分配项目提醒"),
    MESSAGE_TYPE_006("MESSAGE_TYPE_006","项目已分配提醒"),
    MESSAGE_TYPE_007("MESSAGE_TYPE_007","资产运营提醒"),
    OPERATION_TYPE("OPERATION_TYPE","运营类型"),
    MESSAGE_TYPE_008("MESSAGE_TYPE_008","资产租赁提醒"),
    MESSAGE_TYPE_009("MESSAGE_TYPE_009","律所协议到期提醒"),
    OPERATION_TYPE_001("OPERATION_TYPE_001","日常巡逻"),

    LAND_USE_NATURE("LAND_USE_NATURE","用地性质"),
    LAND_USE_NATURE_001("LAND_USE_NATURE_001","国有出让地"),
    LAND_USE_NATURE_002("LAND_USE_NATURE_002","国有划拨地"),


    ASSET_UNIT_STATE("ASSET_UNIT_STATE","资产单元状态"),




    ;
    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    DictEnum(String key, String value) {
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
