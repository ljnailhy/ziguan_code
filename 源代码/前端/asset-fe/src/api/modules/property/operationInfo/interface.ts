import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 运营信息表入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:55
 */
export interface OperationInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 运营类型 */
  operationType?: number;
  operationTitle?: string;
  /** 运营日期 */
  operationDate?: number;
  /** 费用类型 */
  costType?: number;
  /** 费用金额 */
  costMoney?: number;
  /** 运营内容 */
  operationContent?: string;
  /** 备注 */
  remark?: string;
  /** 运营信息 */
  assetIncomeDistributionRequestList?: any[];
  /** 资产信息 */
  propertyBillRequestList?: any[];
  /** 附件信息 */
  fileInfoList?: any[];
}

/**
 * 运营信息表分页入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:55
 */
export interface OperationInfoPageRequest extends OperationInfoRequest {
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
 * 运营信息表出参
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:55
 */
export interface OperationInfoDTO extends OperationInfoRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
