import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 资产收入分配入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:48:45
 */
export interface AssetIncomeDistributionRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 对象Id */
  doId?: number;
  /** 对象类型 */
  doType?: string;
  /** 资产id */
  propertyId?: number;
  /** 类型（收入/支出） 1 收入 0 支出 */
  propertyType?: boolean;
  /** 收入金额 */
  propertyMoney?: number;
  /** 备注 */
  remark?: string;
}

/**
 * 资产收入分配分页入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:48:45
 */
export interface AssetIncomeDistributionPageRequest extends AssetIncomeDistributionRequest {
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
 * 资产收入分配出参
 *
 * @author wangshuai
 * @since 2024-07-13 09:48:45
 */
export interface AssetIncomeDistributionDTO extends AssetIncomeDistributionRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
