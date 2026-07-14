import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";
import { processStatusEnum } from "@/enums/commonEnums";

/**
 * 付款表入参
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
export interface RecoveryPaymentRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 付款金额 */
  payAmount?: number;
  /** 付款时间 */
  payDate?: number;
  /** 收款方 */
  payee?: string;
  /** 收款账号 */
  receivingAccount?: string;
  /** 收款银行 */
  receivingBank?: string;
  /** 状态 1 已审批 0 审批中 */
  flowState?: processStatusEnum;
  payStatus?: number;
  /** 项目Id */
  projectId: number;
  /** 项目名称 */
  projectName: string;
  /** 付款说明 */
  payExplain?: string;
  /** 律所名称 */
  lawyerName: string;
  /** 律所id */
  lawyerId: number;
  /** 备注 */
  remarks?: string;
  fileRequests: any[];
  paymentDetailRequests: RecoveryPaymentDetailRequest[];
}
/**
 * 付款明细入参
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
export interface RecoveryPaymentDetailRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 付款主表Id */
  paymentId?: number;
  /** 付款类型	1 诉讼费	2 保全费	3 保险费	4 其他费用	5 律师费用 */
  payType?: number;
  /** 付款金额 */
  payAmount: number;
  /** 备注 */
  remarks?: string;
}

/**
 * 付款明细分页入参
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
export interface RecoveryPaymentDetailPageRequest extends RecoveryPaymentDetailRequest {
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
 * 付款明细出参
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
export interface RecoveryPaymentDetailDTO extends RecoveryPaymentDetailRequest {
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
 * 付款表分页入参
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
export interface RecoveryPaymentPageRequest extends RecoveryPaymentRequest {
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
 * 付款表出参
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
export interface RecoveryPaymentDTO extends RecoveryPaymentRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
