import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 项目分配/变更明细入参
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export interface AllocationInfoDetailRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 分配Id */
  allocationId: number;
  /** 项目id */
  projectId?: number;
  /** 保全经理 */
  manage?: string;
  /** 律所 */
  lawFirm?: number;
  /** 关联合同 */
  relatedContracts?: number;
}

/**
 * 项目分配/变更明细分页入参
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export interface AllocationInfoDetailPageRequest extends AllocationInfoDetailRequest {
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
 * 项目分配/变更明细出参
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export interface AllocationInfoDetailDTO extends AllocationInfoDetailRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
