import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 核销入参
 *
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
export interface WriteOffRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 项目名称 */
  projectName: string;
  /** 项目Id */
  projectId: number;
  /** 代偿金额 */
  compensationAmount?: number;
  /** 代偿时间 */
  compensationDate?: number;
  /** 核销金额 */
  writeDffAmount?: number;
  /** 核销时间 */
  writeOffDate: number;
  /** 是否核销 */
  writeOffStatus: boolean;
  /** 核销项目分类 A:A B:B C:C D:D */
  writeOffClassification?: WriteOffClassificationEnum;
  /** 累计回款金额 */
  totalCollectionAmount?: number;
  /** 回款情况说明 */
  remarks?: string;
  revePropertyInfoRequest: any[];
  /** 附件信息 */
  fileRequests: any[];
  projectStateList: any[];
}

/**
 * 核销分页入参
 *
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
export interface WriteOffPageRequest extends WriteOffRequest {
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
 * 核销出参
 *
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
export interface WriteOffDTO extends WriteOffRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
  /** 附件信息 */
  fileRequests: any[];
}

/**
 * 核销项目分类 A:A B:B C:C D:D枚举
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
export enum WriteOffClassificationEnum {
  A = "A",
  B = "B",
  C = "C",
  D = "D"
}
/**
 * 核销项目分类 A:A B:B C:C D:D下拉选择项
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
export const writeOffClassificationOptions = [
  { label: "A", value: "A" },
  { label: "B", value: "B" },
  { label: "C", value: "C" },
  { label: "D", value: "D" }
];
