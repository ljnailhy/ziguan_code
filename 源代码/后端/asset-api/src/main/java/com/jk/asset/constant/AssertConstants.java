package com.jk.asset.constant;

/** 常量
 * @author wangTao
 * date2024/6/24 11:39
 **/
public  class AssertConstants {
    public final static int CONSTANT_ZERO = 0;

    public final static int CONSTANT_ONE = 1;

    public final static int CONSTANT_TWO = 2;

    public final static int CONSTANT_THREE = 3;

    public final static int CONSTANT_FOUR = 4;

    public final static int ERROR_CODE = 999;
    /**
    *  代偿同步项目请求参数
    * */
    public final static String API_SERVICE = "longer.fmp.statistics.business.common.list";
    public final static String API_VERSION = "1.0";
    public final static String API_SESSION_ID = "LS_asdfghjkl741";
    public final static String API_STATISICS_CDOE = "compensation_total";
    // 省市区
    public final static String  PROVINCE= "PROVINCE";
    public final static String CITY = "CITY";
    public final static String AREA = "AREA";

    public final static String CONSTANT_STR_ONE = "1";

    public final static String CONSTANT_STR_ONE_HUNDRED = "100";
    public final static String IMPORT_TEMPLATE = "ImportProjectRequest(projectName=必填项\n" +
            "文本格式, nature=必填项\n" +
            "（下拉框）, compensationMoney=必填项\n" +
            "（文本格式）, compensationDate=必填项\n" +
            "格式：2018/05/25, compensationPlan=null, transferDate=必填项\n" +
            "格式：2018/05/25, documentType=必填项\n" +
            "（下拉框）, documentCode=必填项\n" +
            "文本格式, belongProvince=选填\n" +
            "（下拉框）, belongCity=选填\n" +
            "（下拉框）, belongDistrict=选填\n" +
            "（下拉框）, address=选填, industryType=选填\n" +
            "（下拉框）, bigSmall=选填\n" +
            "（下拉框）, goverType=选填\n" +
            "（下拉框）, proceedingageingdate=必填项\n" +
            "格式：2020/01/01, adjustTrialDate=选填\n" +
            "格式：2020/01/01, importRemark=选填, manage=选填\n" +
            "（文本格式）, manageAccount=选填(若有保全经理，此项必填)\n" +
            "（文本格式）, lawFirmName=选填\n" +
            "（文本格式）, projectState=选填\n" +
            "（文本格式）, relationEnterprise=选填\n" +
            "担保人为个人时，此项必填, relationEnterpriseNo=选填\n" +
            "担保人为个人时，此项必填, legalRepresentative=选填\n" +
            "格式：文本格式, contacts=选填\n" +
            "（文本格式）, phone=选填\n" +
            "（文本格式）, securityWay=选填, securityType=选填, reveName=选填, reveMeasure=选填\n" +
            "文本格式, isDispose=必填\n" +
            "（文本格式）, disposeMoney=选填\n" +
            "（文本格式）, doType=选填\n" +
            "（文本格式）)";

    public final static String IMPORT_TEMPLATE1 = "ImportProjectRequest(projectName=必填项\n" +
            "文本格式, nature=必填项\n" +
            "（下拉框）, compensationMoney=必填项\n" +
            "（文本格式）, compensationDate=必填项\n" +
            "格式：2018/05/25, compensationPlan=null, transferDate=必填项\n" +
            "格式：2018/05/25, documentType=必填项\n" +
            "（下拉框）, documentCode=必填项\n" +
            "文本格式, belongProvince=选填\n" +
            "（下拉框）, belongCity=选填\n" +
            "（下拉框）, belongDistrict=选填\n" +
            "（下拉框）, address=选填, industryType=选填\n" +
            "（下拉框）, bigSmall=选填\n" +
            "（下拉框）, goverType=选填\n" +
            "（下拉框）, proceedingageingdate=必填项\n" +
            "格式：2020/01/01, adjustTrialDate=null, importRemark=选填, manage=选填\n" +
            "（文本格式）, manageAccount=选填(若有保全经理，此项必填)\n" +
            "（文本格式）, lawFirmName=选填\n" +
            "格式：2020/01/01, projectState=选填\n" +
            "（文本格式）, relationEnterprise=选填\n" +
            "担保人为个人时，此项必填, relationEnterpriseNo=选填\n" +
            "担保人为个人时，此项必填, legalRepresentative=选填\n" +
            "格式：文本格式, contacts=选填\n" +
            "（文本格式）, phone=选填\n" +
            "（文本格式）, securityWay=选填, securityType=选填, reveName=选填, reveMeasure=选填\n" +
            "文本格式, isDispose=必填\n" +
            "（文本格式）, disposeMoney=选填\n" +
            "（文本格式）, doType=选填\n" +
            "（文本格式）)";
    public static final String IMPORT_ASSET = "ImportPropertyRequest(propertyName=必填项\n" +
            "文本格式, type=必填项\n" +
            "（下拉框）, propertyType=必填项\n" +
            "（下拉框）, sourceType=必填项\n" +
            "（下拉框）, projectId=选填项\n" +
            "文本格式, province=选填项\n" +
            "（下拉框）, city=选填项\n" +
            "（下拉框）, district=选填项\n" +
            "（下拉框）, address=选填, accessWay=选填\n" +
            "（下拉框）, followUpPerson=选填\n" +
            "文本格式\n" +
            "多个跟进人用逗号（,）隔开, followUpPersonAccount=选填\n" +
            "文本格式\n" +
            "多个跟进人用逗号（,）隔开, landUseNature=选填\n" +
            "（下拉框）, assertTime=选填\n" +
            "格式：2020/01/01, affiliatedUnit=选填\n" +
            "（下拉框）, propertyState=选填\n" +
            "（下拉框）, propertyDescribe=必填项\n" +
            "文本格式, transferOwnershipRemark=选填, debtRepaymentMoney=选填项\n" +
            "数值，保留二位小数, originalValue=必填项\n" +
            "数值，保留二位小数, netWorth=必填项\n" +
            "数值，保留二位小数, disposalPrice=选填项\n" +
            "数值，保留二位小数, taxeFee=选填项\n" +
            "数值，保留二位小数, disposeFee=选填项\n" +
            "数值，保保留二位小数, originalObligorFee=选填项\n" +
            "数值，保留二位小数, profitAndLoss=选填项\n" +
            "数值，保留二位小数, propertyCode=必填项(若存在产权信息)\n" +
            "文本格式, assetUnitState=必填项(若存在产权信息)\n" +
            "文本格式, originalValueEntries=选填项\n" +
            "数值，保留二位小数, area=选填项\n" +
            "数值，保留二位小数, propertyOwner=选填, propertyEndDate=选填\n" +
            "格式：2020/01/01, propertyTransferOwnership=选填\n" +
            "格式：2020/01/01, assetUse=选填, addressEntries=选填, remark=选填)";
    public static final String IMPORT_COLLECTION = "ImportPaymentCollectionRequest(projectName=必填项\n" +
            "文本格式, collectionAmount=必填项, collectionDate=必填项\n" +
            "格式：2018/05/25, collectionSign=必填项\n" +
            "文本格式, collectionAscription=必填项\n" +
            "文本格式, isCollectionHistorical=必填项\n" +
            "文本格式, collectionDetailType=必填项\n" +
            "文本格式, lawyerName=选填项\n" +
            "文本格式, collectionSummary=选填项\n" +
            "文本格式)";
}
