import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 入参
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
export interface ProjectTransferDetailedRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 项目移交id */
  transferId?: number;
  /** 项目id */
  projectId?: number;
  /** 代偿金额 */
  compensationMoney?: number;
  /** 代偿时间 */
  compensationDate?: number;
  /** 保全经理 */
  manage?: string;
  /** 律所 */
  lawFirmId?: number;
  /** 关联合同 */
  contractId?: number;
  /** 回款金额 */
  collectionAmount?: number;
  /** 剩余代偿金额 */
  residueCompensation?: number;
}

/**
 * 分页入参
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
export interface ProjectTransferDetailedPageRequest extends ProjectTransferDetailedRequest {
  /** 创建时间范围 */
  createStampRange?: number[];
  /** 租户id */
  tenantId?: number;
  /** 是否删除 */
  isDeleted: boolean;
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
 * 出参
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
export interface ProjectTransferDetailedDTO extends ProjectTransferDetailedRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
