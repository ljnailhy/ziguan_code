import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 分配/变更主表入参
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export interface AllocationInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 分配变更说明 */
  allocationIllustrate: string;
  /** 是否删除 */
  isDelete: boolean;
  allocationInfoDetailRequestList: any[];
  fileInfoList: [];
}

/**
 * 分配/变更主表分页入参
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export interface AllocationInfoPageRequest extends AllocationInfoRequest {
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
 * 分配/变更主表出参
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export interface AllocationInfoDTO extends AllocationInfoRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
