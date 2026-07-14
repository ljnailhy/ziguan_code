import { ReqPage } from "@/api/interface";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 客户信息表入参
 *
 * @author wangtao
 * @since 2024-06-26 10:05:41
 */
export interface CustomerInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 客户名称 */
  customerName: string;
  /** 证件类型 */
  documentType?: number;
  /** 证件号 */
  documentCode?: string;
  /** 联系人 */
  contacts?: string;
  /** 联系电话 */
  contactsPhone?: string;
  /** 来源中介 */
  mediumSource?: string;
  /** 客户来源'INTRODUCE'：介绍,'NATURAL_PERSON'：自然人 */
  customerSource?: CustomerSourceEnum;
  /** 意向资产 */
  intentionalAssets?: string;
  /** 备注 */
  remark?: string;
  fileRequests: any[];
}

/**
 * 客户信息表分页入参
 *
 * @author wangtao
 * @since 2024-06-26 10:05:41
 */
export interface CustomerInfoPageRequest extends CustomerInfoRequest {
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
 * 客户信息表出参
 *
 * @author wangtao
 * @since 2024-06-26 10:05:41
 */
export interface CustomerInfoDTO extends CustomerInfoRequest {
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
 * 客户来源'INTRODUCE'：介绍,'NATURAL_PERSON'：自然人 枚举
 * @author wangtao
 * @since 2024-06-26 10:05:41
 */
export enum CustomerSourceEnum {
  INTRODUCE = "INTRODUCE",
  NATURAL_PERSON = "NATURAL_PERSON"
}
/**
 * 客户来源'INTRODUCE'：介绍,'NATURAL_PERSON'：自然人下拉选择项
 * @author wangtao
 * @since 2024-06-26 10:05:41
 */
export const customerSourceOptions = [
  { label: "介绍", value: "INTRODUCE" },
  { label: "自然人", value: "NATURAL_PERSON" }
];
