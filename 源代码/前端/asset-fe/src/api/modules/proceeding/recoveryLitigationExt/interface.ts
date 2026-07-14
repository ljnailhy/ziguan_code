import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 诉讼反写信息扩展表入参
 *
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
export interface RecoveryLitigationExtRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 备注 */
  remarks?: string;
  /** 代偿金额 */
  backCompensationAmount?: number;
  /** 利息（万元） */
  backInterest?: number;
  /** 违约金（万元） */
  backLiquidatedDamages?: number;
  /** 其他费用 */
  backOtherFees?: number;
  /** 调解或审判日期 */
  adjustTrialDate?: number;
  /** 判决案号 */
  adjustCode?: string;
  /** 立案法院 */
  filingCourtName?: string;
  /** 法官 */
  judgeName?: string;
  /** 立案时间 */
  fillingDate?: number;
  /** 类型 first_instance:一审 second_instance 二审 retrial:再审 register:立案 */
  litigationType?: LitigationTypeEnum;
  /** 案号 */
  fillingCode?: string;
  /** 判决日期 */
  judgeDate?: number;
  /** 开庭时间 */
  courtSessionDate?: number;
}

/**
 * 诉讼反写信息扩展表分页入参
 *
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
export interface RecoveryLitigationExtPageRequest extends RecoveryLitigationExtRequest {
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
 * 诉讼反写信息扩展表出参
 *
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
export interface RecoveryLitigationExtDTO extends RecoveryLitigationExtRequest {
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
 * 类型 first_instance:一审 second_instance 二审 retrial:再审 register:立案枚举
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
export enum LitigationTypeEnum {
  FIRST_INSTANCE = "FIRST_INSTANCE",
  SECOND_INSTANCE = "SECOND_INSTANCE",
  RETRIAL = "RETRIAL",
  REGISTER = "REGISTER"
}
/**
 * 类型 first_instance:一审 second_instance 二审 retrial:再审 register:立案下拉选择项
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
export const litigationTypeOptions = [
  { label: "FIRST_INSTANCE", value: "FIRST_INSTANCE" },
  { label: "SECOND_INSTANCE", value: "SECOND_INSTANCE" },
  { label: "RETRIAL", value: "RETRIAL" },
  { label: "REGISTER", value: "REGISTER" }
];
