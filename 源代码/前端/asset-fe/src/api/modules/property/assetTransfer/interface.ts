import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 资产转让入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:13
 */
export interface AssetTransferRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 受让人 */
  customId?: number;
  /** 评估机构 */
  estimateId?: number;
  /** 评估价 */
  estimateMoney?: number;
  /** 评估日期 */
  estimateDate?: number;
  /** 成交价 */
  dealMoney?: number;
  /** 成交时间 */
  dealDate?: number;
  /** 税费 */
  taxation?: number;
  /** 过户时间 */
  transferOwnershipDate?: number;
  /** 联系人 */
  contacts?: string;
  /** 联系电话 */
  contactsPhone?: string;
  /** 处置方案 */
  programme?: string;
  /** 备注 */
  remark?: string;
  /** 资产和单据关联表 */
  propertyBillRequestList: any[];
  /** 中介关联信息 */
  documentIntermediaryRequestList: any[];
  /** 中介关联信息 */
  hangNetworkInfoRequestList: any[];
  /** 合同关联 */
  billContractRequestList: any[];
  /** 附件信息 */
  fileInfoList: any[];
  estimateEndDate: string;
  intermediaryCustomerLeadRequestList: any[];
  propertyRightInfoRequests: any[];
}

/**
 * 资产转让分页入参
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:13
 */
export interface AssetTransferPageRequest extends AssetTransferRequest {
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
 * 资产转让出参
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:13
 */
export interface AssetTransferDTO extends AssetTransferRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
