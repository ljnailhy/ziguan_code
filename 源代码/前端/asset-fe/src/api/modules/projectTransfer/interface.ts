import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 项目移交入参
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:55
 */
export interface ProjectTransferRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 移交说明 */
  transferIllustrate?: any;
  title?: string;
  projectTransferDetailedRequestList: [];
  fileInfoList: [];
}

/**
 * 项目移交分页入参
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:55
 */
export interface ProjectTransferPageRequest extends ProjectTransferRequest {
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
 * 项目移交出参
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:55
 */
export interface ProjectTransferDTO extends ProjectTransferRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
