import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 产权信息表入参
 *
 * @author wangtao
 * @since 2024-08-17 14:44:27
 */
export interface PropertyRightInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 备注 */
  remark?: string;
  /** 产权证号 */
  propertyCode?: string;
  /** 权证到期日 */
  propertyEndDate?: number;
  /** 产权人名称 */
  propertyOwner?: string;
  /** 面积 */
  area?: number;
  /** 资产用途 */
  assetUse?: string;
  /** 详细地址 */
  address?: string;
  /** 资产过户日期 */
  propertyTransferOwnership?: number;
  propertyInfoId?: number;
  assetUnitState?: number;
  landUseNature?: number;
  original_value?: number;
  do_id?: number;
  do_type?: string;
  parentId?: number;
  propertyMoney?: number;
  propertyEndDateRange?: number[];
}

/**
 * 产权信息表分页入参
 *
 * @author wangtao
 * @since 2024-08-17 14:44:27
 */
export interface PropertyRightInfoPageRequest extends PropertyRightInfoRequest {
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
  doIds?: number[];
}

/**
 * 产权信息表出参
 *
 * @author wangtao
 * @since 2024-08-17 14:44:27
 */
export interface PropertyRightInfoDTO extends PropertyRightInfoRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
