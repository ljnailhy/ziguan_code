import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息入参
 *
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
export interface RecoveryLitigationDetailsRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 是否存量 是1 否0 */
  isStock?: boolean;
  /** 诉讼id */
  litigationId: number;
  title: string;
  preservationCode: string;
  /** 时间 */
  detailsDate?: Date;
  /** 说明 */
  detailsDescription?: string;
  /** 诉讼类型 final:终本 other:其他 drop_lawsuit:撤诉 close_case:结案 preservation:保全 */
  litigationType: LitigationTypeEnum;
  /** 备注 */
  remarks?: string;
  /** 项目名称 */
  projectId: number;
  /** 律所 */
  lawFirmId: number;
  /** 立案 */
  registerId: number;
  /** 附件信息 */
  fileInfoList: [];
  /** 反担保信息入参 */
  reveInfoRequest: any[];
  /** 其他财产线索信息入参 */
  propertyInfoRequest: any[];
  insureMoney?: number;
  preserveType?: string;
  preserveRuleDate?: number;
  preserveApplyDate?: number;
}

/**
 * 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息分页入参
 *
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
export interface RecoveryLitigationDetailsPageRequest extends RecoveryLitigationDetailsRequest {
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
 * 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息出参
 *
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
export interface RecoveryLitigationDetailsDTO extends RecoveryLitigationDetailsRequest {
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
 * 诉讼类型 final:终本 other:其他 drop_lawsuit:撤诉 close_case:结案 preservation:保全枚举
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
export enum LitigationTypeEnum {
  FINAL = "FINAL",
  OTHER = "OTHER",
  DROP_LAWSUIT = "DROP_LAWSUIT",
  CLOSE_CASE = "CLOSE_CASE",
  PRESERVATION = "PRESERVATION"
}
/**
 * 诉讼类型 final:终本 other:其他 drop_lawsuit:撤诉 close_case:结案 preservation:保全下拉选择项
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
export const litigationTypeOptions = [
  { label: "终本", value: "FINAL" },
  { label: "其他", value: "OTHER" },
  { label: "撤诉", value: "DROP_LAWSUIT" },
  { label: "结案", value: "CLOSE_CASE" },
  { label: "保全", value: "PRESERVATION" }
];
