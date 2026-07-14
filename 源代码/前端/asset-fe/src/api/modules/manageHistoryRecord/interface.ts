import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 保全经理历史变更记录入参
 *
 * @author wangshuai
 * @since 2024-07-24 14:24:35
 */
export interface ManageHistoryRecordRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 项目id */
  projectId?: number;
  /** 保全经理 */
  manage?: number;
  /** 开始时间 */
  startDate?: number;
  /** 结束时间 */
  endDate?: number;
}

/**
 * 保全经理历史变更记录分页入参
 *
 * @author wangshuai
 * @since 2024-07-24 14:24:35
 */
export interface ManageHistoryRecordPageRequest extends ManageHistoryRecordRequest {
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
 * 保全经理历史变更记录出参
 *
 * @author wangshuai
 * @since 2024-07-24 14:24:35
 */
export interface ManageHistoryRecordDTO extends ManageHistoryRecordRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
