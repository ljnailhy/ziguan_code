import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 执行信息	入参
 *
 * @author wangshuai
 * @since 2024-07-05 09:52:54
 */
export interface RecoveryExecuteRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 执行状态 apply:已申请 unapply:未申请 */
  executeType?: ExecuteTypeEnum;
  /** 是否存量 是1 否0 */
  isStock?: boolean;
  /** 诉讼id */
  litigationId: number;
  /** 律所 */
  lawFirmId: number;
  /** 项目名称 */
  projectId: number;
  /** 执行案号 */
  executeCode?: string;
  /** 执行法院 */
  executeCourt?: string;
  /** 申请执行时间 */
  applyExecuteDate?: number;
  /** 执行员 */
  executer?: string;
  /** 联系电话 */
  executerTelphone?: string;
  /** 执行裁定下达时间 */
  executeRulingIssuanceTime?: any;
  /** 备注 */
  remarks?: string;
  fileInfoList: any[];
  propertyInfoRequest: any[];
  reveInfoRequest: any[];
}

/**
 * 执行信息	分页入参
 *
 * @author wangshuai
 * @since 2024-07-05 09:52:54
 */
export interface RecoveryExecutePageRequest extends RecoveryExecuteRequest {
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
 * 执行信息	出参
 *
 * @author wangshuai
 * @since 2024-07-05 09:52:54
 */
export interface RecoveryExecuteDTO extends RecoveryExecuteRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}

/**
 * 执行状态 apply:已申请 unapply:未申请枚举
 * @author wangshuai
 * @since 2024-07-05 09:52:54
 */
export enum ExecuteTypeEnum {
  APPLY = "APPLY",
  UNAPPLY = "UNAPPLY",
  REVOKE = "REVOKE",
  RECONCILIATE = "RECONCILIATE"
}
/**
 * 执行状态 apply:已申请 unapply:未申请下拉选择项
 * @author wangshuai
 * @since 2024-07-05 09:52:54
 */
export const executeTypeOptions = [
  { label: "已申请", value: "APPLY" },
  { label: "未申请", value: "UNAPPLY" },
  { label: "撤销执行", value: "REVOKE" },
  { label: "执行和解", value: "RECONCILIATE" }
];
