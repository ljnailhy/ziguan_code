import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 租赁缴费周期表入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:46:33
 */
export interface LeasePaymentCycleRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 对象Id */
  doId?: number;
  /** 对象类型 */
  doType?: string;
  /** 开始时间 */
  startDate?: number;
  /** 结束时间 */
  endDate?: number;
  /** 提醒收租时间 */
  remindDate?: number;
  /** 缴费金额 */
  paymentAmount?: number;
  /** 状态 */
  state?: number;
  /** 备注 */
  remark?: string;
}

/**
 * 租赁缴费周期表分页入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:46:33
 */
export interface LeasePaymentCyclePageRequest extends LeasePaymentCycleRequest {
  /** 创建时间范围 */
  createStampRange?: number[];
  /** 租户id */
  tenantId?: number;
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
 * 租赁缴费周期表出参
 *
 * @author wangshuai
 * @since 2024-07-13 09:46:33
 */
export interface LeasePaymentCycleDTO extends LeasePaymentCycleRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
