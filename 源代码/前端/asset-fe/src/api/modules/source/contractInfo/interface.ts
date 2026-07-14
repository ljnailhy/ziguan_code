import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 合同信息入参
 *
 * @author wangshuai
 * @since 2024-06-26 18:17:34
 */
export interface ContractInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 合同名称 */
  contractName: string;
  /** 合同编号 */
  contractCode: string;
  /** 合同类型 */
  contractType: number;
  /** 合同金额 */
  contractMoney?: number;
  /** 签约日期 */
  signingDate: number;
  /** 开始时间 */
  startDate?: number;
  /** 截止时间 */
  endDate?: number;
  /** 合同摘要 */
  contractAbstract?: string;
  /** 代理方式 */
  agentWay: number;
  /** 律所 */
  lawFirmId?: number;
  /** 代理费收费比例 */
  agencyFeeRatio?: number;
  fileInfoList?: any[];
  signatureInfoList?: any[];
  projectInfoList?: any[];
}

/**
 * 合同信息分页入参
 *
 * @author wangshuai
 * @since 2024-06-26 18:17:34
 */
export interface ContractInfoPageRequest extends ContractInfoRequest {
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
 * 合同信息出参
 *
 * @author wangshuai
 * @since 2024-06-26 18:17:34
 */
export interface ContractInfoDTO extends ContractInfoRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
