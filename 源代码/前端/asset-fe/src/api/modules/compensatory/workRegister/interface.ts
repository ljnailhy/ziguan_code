import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 工作登记入参
 *
 * @author wangshuai
 * @since 2024-06-28 17:23:16
 */
export interface WorkRegisterRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 对象类型 */
  doType?: string;
  /** 对象id */
  doId: number;
  /** 类型 */
  type?: number;
  /** 工作时间 */
  workDate?: number;
  /** 工作内容 */
  workContent?: string;
  /** 是否删除 */
  isDelete: boolean;
  fileInfoList: [];
}

/**
 * 工作登记分页入参
 *
 * @author wangshuai
 * @since 2024-06-28 17:23:16
 */
export interface WorkRegisterPageRequest extends WorkRegisterRequest {
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
 * 工作登记出参
 *
 * @author wangshuai
 * @since 2024-06-28 17:23:16
 */
export interface WorkRegisterDTO extends WorkRegisterRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
