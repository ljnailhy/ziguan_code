import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";
import { processStatusEnum } from "@/enums/commonEnums";

/**
 * 回款表入参
 *
 * @author wangtao
 * @since 2024-07-08 10:09:46
 */
export interface RecoveryPaymentCollectionRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 项目id */
  projectId: number;
  /** 项目名称 */
  projectName: string;
  /** 合计回款金额 */
  collectionAmount: number;
  /** 回款时间 */
  collectionDate: number;
  /** 回款标记 CASH:现金 RE_GUARANTEE:再担保 MORTGAGE:抵债 SILVER_BILL银票 */
  collectionSign: CollectionSignEnum;
  /** 回款归属 	GROUP集团	CULTURAL_TOURISM文旅 	SMALL_MEDIUM_DAN中小担 	VER_SMALL_MEDIUM 核销-中小担 	VER_GROUP 核销-集团	VER_CULTURAL_TOURISM 核销-文旅 */
  collectionAscription: CollectionAscriptionEnum;
  /** 是否历史代偿 1 是 0 否 */
  isCollectionHistorical?: boolean;
  /** 回款摘要 */
  collectionSummary?: string;
  flowState?: processStatusEnum;
  collectionStatus?: number;
  /** 备注 */
  remarks?: string;
  /** 代偿金额 */
  compensatoryCash?: number;
  /** 违约金 */
  defaultCash?: number;
  /** 利息 */
  interest?: number;
  /** 其他费用 */
  otherFee?: number;
  /** 律所名称 */
  lawyerName: string;
  /** 律所id */
  lawyerId: number;
  fileRequests: any[];
  paymentCollectionDetailRequests: RecoveryPaymentCollectionDetailRequest[];
  collectionType?: string;
}
/**
 * 回款明细入参
 *
 * @author wangtao
 * @since 2024-07-08 14:24:03
 */
export interface RecoveryPaymentCollectionDetailRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 回款主表Id */
  paymentCollectionId?: number;
  /** 回款类型 代偿金额:COMPENSATORY_CASH 利息:INTEREST 其他费用:OTHER_FEE 违约金:DEFAULT_CASH */
  collectionDetailType?: CollectionDetailTypeEnum;
  /** 反担保措施 */
  reveMeasure?: string;
  /** 反担保措施Id */
  reveId?: number;
  /** 回款金额 */
  collectionDetailAmount: number;
  /** 备注 */
  remarks?: string;
}
export enum CollectionDetailTypeEnum {
  INTEREST = "利息",
  COMPENSATORY_CASH = "代偿金额",
  OTHER_FEE = "其他费用",
  DEFAULT_CASH = "违约金"
}

/**
 * 回款表分页入参
 *
 * @author wangtao
 * @since 2024-07-08 10:09:46
 */
export interface RecoveryPaymentCollectionPageRequest extends RecoveryPaymentCollectionRequest {
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
 * 回款表出参
 *
 * @author wangtao
 * @since 2024-07-08 10:09:46
 */
export interface RecoveryPaymentCollectionDTO extends RecoveryPaymentCollectionRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
export const collectionDetailTypeOptions = [
  { label: "代偿金额", value: "COMPENSATORY_CASH" },
  { label: "利息", value: "INTEREST" },
  { label: "其他费用", value: "OTHER_FEE" },
  { label: "违约金", value: "DEFAULT_CASH" }
];
/**
 * 回款标记 CASH:现金 RE_GUARANTEE:再担保 MORTGAGE:抵债 SILVER_BILL银票枚举
 * @author wangtao
 * @since 2024-07-08 10:09:46
 */
export enum CollectionSignEnum {
  CASH = "现金",
  RE_GUARANTEE = "再担保",
  MORTGAGE = "抵债",
  SILVER_BILL = "银票"
}
/**
 * 回款标记 CASH:现金 RE_GUARANTEE:再担保 MORTGAGE:抵债 SILVER_BILL银票下拉选择项
 * @author wangtao
 * @since 2024-07-08 10:09:46
 */
export const collectionSignOptions = [
  { label: "现金", value: "CASH" },
  { label: "再担保", value: "RE_GUARANTEE" },
  { label: "抵债", value: "MORTGAGE" },
  { label: "银票", value: "SILVER_BILL" }
];

/**
 * 回款归属 	GROUP集团	CULTURAL_TOURISM文旅 	SMALL_MEDIUM_DAN中小担 	VER_SMALL_MEDIUM 核销-中小担 	VER_GROUP 核销-集团	VER_CULTURAL_TOURISM 核销-文旅枚举
 * @author wangtao
 * @since 2024-07-08 10:09:46
 */
export enum CollectionAscriptionEnum {
  SMALL_MEDIUM_DAN = "中小担",
  CULTURAL_TOURISM = "文旅",
  GROUP = "集团",
  VER_SMALL_MEDIUM = "核销-中小担",
  VER_GROUP = "核销-集团",
  VER_CULTURAL_TOURISM = "核销-文旅"
}
/**
 * 回款归属 	GROUP集团	CULTURAL_TOURISM文旅 	SMALL_MEDIUM_DAN中小担 	VER_SMALL_MEDIUM 核销-中小担 	VER_GROUP 核销-集团	VER_CULTURAL_TOURISM 核销-文旅下拉选择项
 * @author wangtao
 * @since 2024-07-08 10:09:46
 */
export const collectionAscriptionOptions = [
  { label: "中小担", value: "SMALL_MEDIUM_DAN" },
  { label: "文旅", value: "CULTURAL_TOURISM" },
  { label: "集团", value: "GROUP" },
  { label: "核销-中小担", value: "VER_SMALL_MEDIUM" },
  { label: "核销-集团", value: "VER_GROUP" },
  { label: "核销-文旅", value: "VER_CULTURAL_TOURISM" }
];
