import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 中介客户线索关联表入参
 *
 * @author wangshuai
 * @since 2024-07-12 16:12:05
 */
export interface IntermediaryCustomerLeadRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 对象Id */
  doId?: number;
  /** 对象类型 */
  doType?: string;
  /** 单据与中介关联id */
  propertyId?: number;
  /** 客户id */
  customId?: number;
  endDate?: number;
  /** 开始时间 */
  startDate?: number;
  /** 备注 */
  remark?: string;
}

/**
 * 中介客户线索关联表分页入参
 *
 * @author wangshuai
 * @since 2024-07-12 16:12:05
 */
export interface IntermediaryCustomerLeadPageRequest extends IntermediaryCustomerLeadRequest {
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
 * 中介客户线索关联表出参
 *
 * @author wangshuai
 * @since 2024-07-12 16:12:05
 */
export interface IntermediaryCustomerLeadDTO extends IntermediaryCustomerLeadRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
