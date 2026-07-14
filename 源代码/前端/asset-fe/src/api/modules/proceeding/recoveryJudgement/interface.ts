import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 审判信息（立案一审二审再审）入参
 *
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
export interface RecoveryJudgementRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 是否存量 是1 否0 */
  isStock: boolean;
  /** 立案法院 */
  filingCourtName: string;
  /** 法官 */
  judgeName?: string;
  /** 法官联系方式 */
  judgePhone?: string;
  /** 立案时间 */
  fillingDate?: number;
  /** 类型 first_instance:一审 second_instance 二审 retrial:再审 */
  litigationType?: LitigationTypeEnum;
  /** 类型 first_instance:一审 second_instance 二审 retrial:再审 */
  registerType?: LitigationTypeEnum;
  /** 立案id */
  registerId: number;
  /** 案号 */
  fillingCode?: string;
  /** 判决日期 */
  judgeDate?: number;
  /** 开庭时间 */
  courtSessionDate?: number;
  /** 诉讼id */
  litigationId: number;
  /** 律所 */
  lawFirmId: number;
  /** 项目名称 */
  projectId: number;
  /** 备注 */
  remarks?: string;
  fileInfoList: [];
}

/**
 * 审判信息（立案一审二审再审）分页入参
 *
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
export interface RecoveryJudgementPageRequest extends RecoveryJudgementRequest {
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
 * 审判信息（立案一审二审再审）出参
 *
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
export interface RecoveryJudgementDTO extends RecoveryJudgementRequest {
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
 * 类型 first_instance:一审 second_instance 二审 retrial:再审枚举
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
export enum LitigationTypeEnum {
  REGISTER = "REGISTER",
  FIRST_INSTANCE = "FIRST_INSTANCE",
  SECOND_INSTANCE = "SECOND_INSTANCE",
  RETRIAL = "RETRIAL"
}
/**
 * 类型 first_instance:一审 second_instance 二审 retrial:再审下拉选择项
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
export const litigationTypeOptions = [
  { label: "立案", value: "REGISTER" },
  { label: "一审", value: "FIRST_INSTANCE" },
  { label: "二审", value: "SECOND_INSTANCE" },
  { label: "再审", value: "RETRIAL" }
];

export const registerTypeOptions = [
  { label: "一审立案", value: "FIRST_INSTANCE" },
  { label: "二审立案", value: "SECOND_INSTANCE" },
  { label: "再审立案", value: "RETRIAL" }
];
