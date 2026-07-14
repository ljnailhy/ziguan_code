import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 律师团队入参
 *
 * @author wangshuai
 * @since 2024-06-26 17:26:54
 */
export interface LawyerInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 律所id */
  lawFirmId: number;
  /** 律师姓名 */
  lawyerName: string;
  /** 联系电话 */
  phone?: string;
  /** 执业证号 */
  credentialNumber?: string;
  /** 登录账号 */
  userId?: number;
  /** 备注 */
  remark?: string;
}

/**
 * 律师团队分页入参
 *
 * @author wangshuai
 * @since 2024-06-26 17:26:54
 */
export interface LawyerInfoPageRequest extends LawyerInfoRequest {
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
 * 律师团队出参
 *
 * @author wangshuai
 * @since 2024-06-26 17:26:54
 */
export interface LawyerInfoDTO extends LawyerInfoRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
}
