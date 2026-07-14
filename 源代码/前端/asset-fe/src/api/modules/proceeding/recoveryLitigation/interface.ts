import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 诉讼入参
 *
 * @author wangshuai
 * @since 2024-07-01 16:38:08
 */
export interface RecoveryLitigationRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 是否存量 是1 否0 */
  isStock?: boolean;
  /** 代偿金额 */
  compensationAmount?: number;
  /** 利息（万元） */
  interest?: number;
  /** 违约金（万元） */
  liquidatedDamages?: number;
  /** 其他费用 */
  otherFees?: number;
  /** 项目名称 */
  projectId: number;
  /** 律所 */
  lawFirmId: number;
  /** 项目状态 一审 二审 再审 保全 撤诉 调节 审判 执行 恢复执行 终本 结案 其他 登记项目工作 */
  projectStatus?: number;
  /** 备注 */
  remarks?: string;
  fileInfoList: [];
}

/**
 * 诉讼分页入参
 *
 * @author wangshuai
 * @since 2024-07-01 16:38:08
 */
export interface RecoveryLitigationPageRequest extends RecoveryLitigationRequest {
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
 * 诉讼出参
 *
 * @author wangshuai
 * @since 2024-07-01 16:38:08
 */
export interface RecoveryLitigationDTO extends RecoveryLitigationRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
