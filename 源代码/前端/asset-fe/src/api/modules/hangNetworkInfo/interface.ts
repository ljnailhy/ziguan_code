import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 挂网信息表入参
 *
 * @author wangshuai
 * @since 2024-07-09 15:15:21
 */
export interface HangNetworkInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 转让id/反担保id */
  doId?: number;
  /** 数据对象类型 */
  doType?: string;
  /** 类型 */
  hangNetworkType?: number;
  /** 挂网时间 */
  hangNetworkDate?: number;
  /** 挂网价格 */
  hangNetworkMoney?: number;
  /** 挂网报价 */
  hangNetworkQuotation?: number;
  /** 状态 */
  hangNetworkState?: number;
  /** 备注 */
  remark?: string;
}

/**
 * 挂网信息表分页入参
 *
 * @author wangshuai
 * @since 2024-07-09 15:15:21
 */
export interface HangNetworkInfoPageRequest extends HangNetworkInfoRequest {
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
 * 挂网信息表出参
 *
 * @author wangshuai
 * @since 2024-07-09 15:15:21
 */
export interface HangNetworkInfoDTO extends HangNetworkInfoRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
