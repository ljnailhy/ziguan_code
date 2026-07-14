package com.jk.asset.enums;

import com.jk.common.enums.OrderEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 单据枚举
 *
 * @author WangShuai
 * @since 2024/3/13 11:11
 **/
@Getter
public enum BillTypeEnum {
  AGENCY("AGENCY","代理机构"),
  CUSTOMER_INFO("CUSTOMER_INFO","客户信息"),
//  PAYMENT_COLLECTION("PAYMENT_COLLECTION","回款管理"),
//  PAYMENT_COLLECTION_DETAIL("PAYMENT_COLLECTION_DETAIL","回款明细"),
//  RETAIL("CUSTOMER_INFO","客户信息"),
  SUBJECT_INFO("SUBJECT_INFO","主体信息管理"),
  SIGNATURE_INFO("SIGNATURE_INFO","签约方信息"),
  CONTRACT_INFO("CONTRACT_INFO","合同信息管理"),
  REVE("REVE","反担保"),
  PROPERTY("PROPERTY","其他财产"),
  PROJECT_INFO("PROJECT_INFO","项目信息"),
  LAWYER_INFO("LAWYER_INFO","律师团队"),
  LAW_FIRM_INFO("LAW_FIRM_INFO","律所信息"),
  ALLOCATION_INFO("ALLOCATION_INFO","项目分配"),
  ALLOCATION_INFO_DETAIL("ALLOCATION_INFO_DETAIL","项目分配明细"),
  RECOVERY_LITIGATION("RECOVERY_LITIGATION","诉讼管理"),
  RECOVERY_JUDGEMENT("RECOVERY_JUDGEMENT","立案一审"),
  RECOVERY_JUDGEMENT_REGISTER("RECOVERY_JUDGEMENT_REGISTER","立案审理申请单"),
  FINAL("FINAL","终本"),
  OTHER("OTHER","其他"),
  DROP_LAWSUIT("DROP_LAWSUIT","撤诉"),
  CLOSE_CASE("CLOSE_CASE","结案"),
  PRESERVATION("PRESERVATION","保全"),
  WORK_REGISTER("WORK_REGISTER","工作登记"),
  RECOVERY_ADJUST_TRIAL("RECOVERY_ADJUST_TRIAL","调解审判"),
  WRITE_OFF("WRITE_OFF","项目核销"),
  RECOVERY_PAYMENT_COLLECTION("RECOVERY_PAYMENT_COLLECTION","项目回款"),
  RECOVERY_PAYMENT_COLLECTION_DETAIL("RECOVERY_PAYMENT_COLLECTION_DETAIL","项目回款明细"),
  RECOVERY_PAYMENT("RECOVERY_PAYMENT","项目付款"),
  RECOVERY_PAYMENT_DETAIL("RECOVERY_PAYMENT_DETAIL","项目付款明细"),
  RECOVERY_EXECUTE("RECOVERY_EXECUTE","执行登记"),
  PROPERTY_INFO("PROPERTY_INFO","资产入库"),
  LEASE_INFO("LEASE_INFO","租赁登记"),
  PROPERTY_BILL("PROPERTY_BILL","资产和单据关联表"),
  DOCUMENT_INTERMEDIARY("DOCUMENT_INTERMEDIARY","单据和中介关联表对象"),
  LEASE_PAYMENT_CYCLE("LEASE_PAYMENT_CYCLE","缴费周期"),
  ASSET_TRANSFER("ASSET_TRANSFER","资产转让"),
  OPERATION_INFO("OPERATION_INFO","资产运营"),
  PROJECT_TRANSFER("PROJECT_TRANSFER","项目移交"),
  RIST_TYPE_AFTER_GUARANTEE("RIST_TYPE_AFTER_GUARANTEE","风险归类"),



  ;

  /** 键 */
  private final String key;
  /** 值 */
  private final String value;

  BillTypeEnum(String key, String value) {
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
