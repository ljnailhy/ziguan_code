import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 租赁信息表入参
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
export interface LeaseInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 评估机构 */
  estimateId?: number;
  /** 评估价 */
  estimateMoney?: number;
  /** 评估日期 */
  estimateDate?: string | number;
  /** 代理费用 */
  agencyFee?: number;
  /** 联系人 */
  contacts?: string;
  /** 联系电话 */
  contactsPhone?: string;
  /** 处置方案 */
  disposeProgramme?: string;
  /** 月租金 */
  monthRent?: number;
  /** 年租金 */
  yearRent?: number;
  /** 保证金 */
  margin?: number;
  /** 租赁期限开始日期 */
  leaseTermStart?: number;
  /** 租赁期限结束日期 */
  leaseTermEnd?: number;
  /** 缴纳周期'YEAR'：年,'HALF_YEAR'：半年,'SEASON'：季,'MONTH'：月,'DISPOSABLE'：一次性,'OTHER'：其他 */
  paymentCycle?: PaymentCycleEnum;
  /** 承租人 */
  lessee?: number;
  remark?: string;
  estimateEndDate?: string | number;
  isEstimate?: boolean;
  /** 租赁用途 */
  leasePurpose?: string;
  /** 选择中介机构 **/
  intermediaryId?: number;
  /** 中介费用 **/
  intermediaryFee?: number;
  propertyBillRequestList?: any[];
  documentIntermediaryRequestList: any[];
  billContractRequestList: any[];
  fileInfoList: any[];
  leasePaymentCycleRequestList: any[];
  hangNetworkInfoRequestList: any[];
  intermediaryCustomerLeadRequestList: any[];
  propertyRightInfoRequests: any[];
}

/**
 * 租赁信息表分页入参
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
export interface LeaseInfoPageRequest extends LeaseInfoRequest {
  /** 创建时间范围 */
  createStampRange?: number[];
  /** 租户id */
  tenantId?: number;
  /** 是否删除 */
  isDeleted: boolean;
  /** 创建人 */
  creator?: number;
  /** 创建时间 */
  createStamp?: number;
  /** 最后修改人 */
  lastEditor?: number;
  /** 最后修改时间 */
  lastEditStamp?: number;
  /** 排序字段名 */
  field?: string;
  /** 排序类型 */
  order?: OrderEnum;
}

/**
 * 租赁信息表出参
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
export interface LeaseInfoDTO extends LeaseInfoRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}

/**
 * 缴纳周期'YEAR'：年,'HALF_YEAR'：半年,'SEASON'：季,'MONTH'：月,'DISPOSABLE'：一次性,'OTHER'：其他枚举
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
export enum PaymentCycleEnum {
  YEAR = "YEAR",
  HALF_YEAR = "HALF_YEAR",
  SEASON = "SEASON",
  MONTH = "MONTH",
  DISPOSABLE = "DISPOSABLE",
  OTHER = "OTHER"
}
/**
 * 缴纳周期'YEAR'：年,'HALF_YEAR'：半年,'SEASON'：季,'MONTH'：月,'DISPOSABLE'：一次性,'OTHER'：其他下拉选择项
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
export const paymentCycleOptions = [
  { label: "年", value: "YEAR" },
  { label: "半年", value: "HALF_YEAR" },
  { label: "季", value: "SEASON" },
  { label: "月", value: "MONTH" },
  { label: "一次性", value: "DISPOSABLE" },
  { label: "其他", value: "OTHER" }
];
