import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 签署方信息入参
 *
 * @author wangshuai
 * @since 2024-06-26 18:21:01
 */
export interface SignatureInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 备注 */
  contractRemark?: string;
  /** 签约方名称 */
  contractPartyName?: string;
  /** 签署方类型 */
  contractPartyType?: number;
  /** 合同id */
  contractId?: number;
}

/**
 * 签署方信息分页入参
 *
 * @author wangshuai
 * @since 2024-06-26 18:21:01
 */
export interface SignatureInfoPageRequest extends SignatureInfoRequest {
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
 * 签署方信息出参
 *
 * @author wangshuai
 * @since 2024-06-26 18:21:01
 */
export interface SignatureInfoDTO extends SignatureInfoRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
