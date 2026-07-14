import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 主体信息入参
 *
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export interface SubjectInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 主体名称 */
  subjectName: string;
  /** 主体性质 E:企业 NE:非企业经济组织 FM:农户 IB:个体工商户 SME:小伟企业主 OTHER：其他 */
  nature: NatureEnum;
  /** 主体类型COUNTER_GUARANTOR:反担保人，DEBTOR：债务人 */
  subjectType: SubjectTypeEnum;
  /** 证件类型 SFZ身份证 YYZZ 营业执照 JGZ 军官证 SBZ 士兵证 HZ 护照 JSZ 驾驶证 ZZZ 暂住证 */
  documentType?: DocumentTypeEnum;
  // documentType?: any;
  /** 证件号码 */
  documentNumber?: string;
  /** 法定代表人 */
  legalRepresentative?: string;
  /** 联系人 */
  contacts?: string;
  /** 联系人电话 */
  phone?: string;
  /** 区域_省 */
  belongProvince?: string;
  /** 区域_市 */
  belongCity?: string;
  /** 区域_区 */
  belongDistrict?: string;
  /** 详细地址 */
  address?: string;
  /** 备注 */
  remark?: string;
  /** 是否删除 */
  isDelete: boolean;
  /** 附件 */
  fileInfoList?: any[];
}

/**
 * 主体信息分页入参
 *
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export interface SubjectInfoPageRequest extends SubjectInfoRequest {
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
 * 主体信息出参
 *
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export interface SubjectInfoDTO extends SubjectInfoRequest {
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
 * 主体性质 E:企业 NE:非企业经济组织 FM:农户 IB:个体工商户 SME:小伟企业主 OTHER：其他枚举
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export enum NatureEnum {
  E = "E",
  NE = "NE",
  FM = "FM",
  IB = "IB",
  SME = "SME",
  OTHER = "OTHER"
}
/**
 * 主体性质 E:企业 NE:非企业经济组织 FM:农户 IB: SME:小伟企业主 OTHER：其他下拉选择项
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export const natureOptions = [
  { label: "企业", value: "E" },
  { label: "非企业经济组织", value: "NE" },
  { label: "农户", value: "FM" },
  { label: "个体工商户", value: "IB" },
  { label: "小微企业主", value: "SME" },
  { label: "其他", value: "OTHER" }
];

/**
 * 主体类型COUNTER_GUARANTOR:反担保人，DEBTOR：债务人枚举
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export enum SubjectTypeEnum {
  DEBTOR = "DEBTOR",
  COUNTER_GUARANTOR = "COUNTER_GUARANTOR"
}
/**
 * 主体类型COUNTER_GUARANTOR:反担保人，DEBTOR：债务人下拉选择项
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export const subjectTypeOptions = [
  { label: "债务人", value: "DEBTOR" },
  { label: "反担保人", value: "COUNTER_GUARANTOR" }
];

/**
 * 证件类型 SFZ身份证 YYZZ 营业执照 JGZ 军官证 SBZ 士兵证 HZ 护照 JSZ 驾驶证 ZZZ 暂住证枚举
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export enum DocumentTypeEnum {
  SFZ = "SFZ",
  YYZZ = "YYZZ",
  JGZ = "JGZ",
  SBZ = "SBZ",
  HZ = "HZ",
  JSZ = "JSZ",
  ZZZ = "ZZZ"
}
/**
 * 证件类型 SFZ身份证 YYZZ 营业执照 JGZ 军官证 SBZ 士兵证 HZ 护照 JSZ 驾驶证 ZZZ 暂住证下拉选择项
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export const documentTypeOptions = [
  { label: "身份证", value: "SFZ" },
  { label: "营业执照", value: "YYZZ" },
  { label: "军官证", value: "JGZ" },
  { label: "士兵证", value: "SBZ" },
  { label: "护照", value: "HZ" },
  { label: "驾驶证", value: "JSZ" },
  { label: "暂住证", value: "ZZZ" }
];
