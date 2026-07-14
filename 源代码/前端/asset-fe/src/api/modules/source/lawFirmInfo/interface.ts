import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 律所信息入参
 *
 * @author wangshuai
 * @since 2024-06-26 16:49:08
 */
export interface LawFirmInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 组织id */
  orgId?: number;
  /** 律所名称 */
  name?: string;
  /** 联系人 */
  contacts?: string;
  /** 联系电话 */
  phone?: string;
  /** 律所地址 */
  address?: string;
  /** 备注 */
  remark?: string;
  lawInfoDTO?: LawInfoDTOType;
  fileInfoList?: any[];
  lawyerInfoRequestList?: any[];
  /** 收款账号 */
  dueNumber?: string;
  /** 收款银行 */
  dueBank?: string;
}

/**
 * 律所信息分页入参
 *
 * @author wangshuai
 * @since 2024-06-26 16:49:08
 */
export interface LawFirmInfoPageRequest extends LawFirmInfoRequest {
  /** 创建时间范围 */
  createStampRange?: number[];
  /** 租户id */
  tenantId?: number;
  /** 是否有效 */
  enabled: boolean;
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
 * 律所信息出参
 *
 * @author wangshuai
 * @since 2024-06-26 16:49:08
 */
export interface LawFirmInfoDTO extends LawFirmInfoRequest {
  /** 是否有效 */
  enabled: boolean;
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}

export interface LawInfoDTOType {
  /** 在管项目数" */
  manageProjectNum?: string;
  /** 代偿金额 */
  totalCompensationMoney?: number;
  /** 回款总金额 */
  totalPaymentCollection?: number;
  /** 回款率 */
  collectionRate?: number;
  /** 本年回款率 */
  collectionRateYear?: string;
  /** 律师费 */
  lawFee?: string;
  /** 核销率 */
  write0ffRate?: string;
}
