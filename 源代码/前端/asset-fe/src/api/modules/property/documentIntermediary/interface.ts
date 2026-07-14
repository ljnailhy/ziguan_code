import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 单据和中介关联表入参
 *
 * @author wangshuai
 * @since 2024-07-12 16:11:38
 */
export interface DocumentIntermediaryRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 对象Id */
  doId?: number;
  /** 对象类型 */
  doType?: string;
  /** 中介名称 */
  intermediaryId?: number;
  /** 是否成交 */
  isDeal?: boolean;
  /** 佣金 */
  commission?: number;
  /** 备注 */
  remark?: string;
}

/**
 * 单据和中介关联表分页入参
 *
 * @author wangshuai
 * @since 2024-07-12 16:11:38
 */
export interface DocumentIntermediaryPageRequest extends DocumentIntermediaryRequest {
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
 * 单据和中介关联表出参
 *
 * @author wangshuai
 * @since 2024-07-12 16:11:38
 */
export interface DocumentIntermediaryDTO extends DocumentIntermediaryRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
