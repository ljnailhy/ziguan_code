import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 资产和单据关联表入参
 *
 * @author wangshuai
 * @since 2024-07-11 18:03:58
 */
export interface PropertyBillRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 对象Id */
  doId?: number;
  /** 对象类型 */
  doType?: string;
  /** 资产id */
  propertyId?: number;
  /** 资产状态 */
  propertyState?: number;
  /** 资产标签 */
  propertyTag?: string;
  /** 备注 */
  remark?: string;
}

/**
 * 资产和单据关联表分页入参
 *
 * @author wangshuai
 * @since 2024-07-11 18:03:58
 */
export interface PropertyBillPageRequest extends PropertyBillRequest {
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
 * 资产和单据关联表出参
 *
 * @author wangshuai
 * @since 2024-07-11 18:03:58
 */
export interface PropertyBillDTO extends PropertyBillRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
