import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 回款目标明细入参
 *
 * @author wangshuai
 * @since 2024-06-27 10:25:18
 */
export interface PaymentCollectionTargetDetailRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 目标id */
  targetId?: number;
  /** 公司 */
  companyId?: number;
  /** 部门 */
  deptId?: number;
  /** 人员 */
  userId?: number;
  /** 回款目标金额 */
  targetDetailMoney?: number;
}

/**
 * 回款目标明细分页入参
 *
 * @author wangshuai
 * @since 2024-06-27 10:25:18
 */
export interface PaymentCollectionTargetDetailPageRequest extends PaymentCollectionTargetDetailRequest {
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
 * 回款目标明细出参
 *
 * @author wangshuai
 * @since 2024-06-27 10:25:18
 */
export interface PaymentCollectionTargetDetailDTO extends PaymentCollectionTargetDetailRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
