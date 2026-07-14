import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 单据与律师关系表入参
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
export interface BillLawyerRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 对象类型 */
  doType: string;
  /** 对象id */
  doId: number;
  /** 律师 */
  lawyer: number;
  /** 备注 */
  remarks?: string;
}

/**
 * 单据与律师关系表分页入参
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
export interface BillLawyerPageRequest extends BillLawyerRequest {
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
 * 单据与律师关系表出参
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
export interface BillLawyerDTO extends BillLawyerRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
