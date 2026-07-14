import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 年度回款目标入参
 *
 * @author wangshuai
 * @since 2024-06-27 10:25:18
 */
export interface PaymentCollectionTargetRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 年度 */
  year?: number;
  /** 回款目标金额 */
  targetMoney?: number;
  /** 备注 */
  remark?: string;
  paymentCollectionTargetDetailRequests: any[];
  entrustMoney?: number;
}

/**
 * 年度回款目标分页入参
 *
 * @author wangshuai
 * @since 2024-06-27 10:25:18
 */
export interface PaymentCollectionTargetPageRequest extends PaymentCollectionTargetRequest {
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
 * 年度回款目标出参
 *
 * @author wangshuai
 * @since 2024-06-27 10:25:18
 */
export interface PaymentCollectionTargetDTO extends PaymentCollectionTargetRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
