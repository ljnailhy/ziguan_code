import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 代理机构入参
 *
 * @author wangtao
 * @since 2024-06-25 19:28:45
 */
export interface AgencyRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 机构名称 */
  agencyName?: string;
  /** 机构类型 */
  agencyType?: number;
  /** 代理性质 租赁:LEASE ASSESS:评估 LEASE_TRANSFER 租赁转让 */
  agencyNature: AgencyNatureEnum;
  /** 联系人 */
  contacts?: string;
  /** 联系电话 */
  contactsPhone?: string;
  /** 机构地址 */
  agencyAddress?: string;
  /** 代理费 */
  agencyFee?: number;
  /** 备注 */
  remark?: string;
  /** 附件 */
  fileRequests: any[];
}

/**
 * 代理机构分页入参
 *
 * @author wangtao
 * @since 2024-06-25 19:28:45
 */
export interface AgencyPageRequest extends AgencyRequest {
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
 * 代理机构出参
 *
 * @author wangtao
 * @since 2024-06-25 19:28:45
 */
export interface AgencyDTO extends AgencyRequest {
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
 * 代理性质 租赁:LEASE ASSESS:评估 LEASE_TRANSFER 租赁转让枚举
 * @author wangtao
 * @since 2024-06-25 19:28:45
 */
export enum AgencyNatureEnum {
  LEASE = "LEASE",
  ASSESS = "ASSESS",
  LEASE_TRANSFER = "LEASE_TRANSFER"
}
/**
 * 代理性质 租赁:LEASE ASSESS:评估 LEASE_TRANSFER 租赁转让下拉选择项
 * @author wangtao
 * @since 2024-06-25 19:28:45
 */
export const agencyNatureOptions = [
  { label: "租赁", value: "LEASE" },
  { label: "评估", value: "ASSESS" },
  { label: "租赁转让", value: "LEASE_TRANSFER" }
];
