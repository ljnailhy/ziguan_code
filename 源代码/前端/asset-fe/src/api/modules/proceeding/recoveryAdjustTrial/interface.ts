import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 调解或审判信息	入参
 *
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
export interface RecoveryAdjustTrialRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 调解或审判类型 before_ligation_mediation:诉前调解 middle_litigation_mediation：诉中调解 juge:判决 */
  adjustTrialType?: AdjustTrialTypeEnum;
  /** 诉讼id */
  litigationId: number;
  /** 律所 */
  lawFirmId: number;
  /** 项目名称 */
  projectId: number;
  /** 是否存量 是1 否0 */
  isStock?: boolean;
  /** 调解或审判日期 */
  adjustTrialDate?: number;
  /** 判决案号 */
  adjustCode?: string;
  /** 代偿金额 */
  compensatoryAmount: number;
  /** 利息 */
  interest: number;
  /** 违约金 */
  backOutAmount: number;
  /** 其他费用 */
  otherAmount: number;
  /** 特殊情况说明 */
  specialRemarks: string;
  /** 备注 */
  remarks?: string;
  fileInfoList: [];
}

/**
 * 调解或审判信息	分页入参
 *
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
export interface RecoveryAdjustTrialPageRequest extends RecoveryAdjustTrialRequest {
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
 * 调解或审判信息	出参
 *
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
export interface RecoveryAdjustTrialDTO extends RecoveryAdjustTrialRequest {
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
 * 调解或审判类型 before_ligation_mediation:诉前调解 middle_litigation_mediation：诉中调解 juge:判决枚举
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
export enum AdjustTrialTypeEnum {
  BEFORE_LITIGATION_MEDIATION = "BEFORE_LITIGATION_MEDIATION",
  MIDDLE_LITIGATION_MEDIATION = "MIDDLE_LITIGATION_MEDIATION",
  JUDG = "JUDG"
}
/**
 * 调解或审判类型 before_ligation_mediation:诉前调解 middle_litigation_mediation：诉中调解 juge:判决下拉选择项
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
export const adjustTrialTypeOptions = [
  { label: "诉前调解", value: "BEFORE_LITIGATION_MEDIATION" },
  { label: "诉中调解", value: "MIDDLE_LITIGATION_MEDIATION" },
  { label: "判决", value: "JUDG" }
];
