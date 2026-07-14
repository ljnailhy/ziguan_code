import { ReqPage } from "@/api/interface";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 项目信息详细信息扩展表入参
 *
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export interface ProjectInfoExtRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 大类 */
  type?: number;
  /** 产品名称 */
  productName?: number;
  /** 合作银行 */
  cooperateBank?: number;
  /** 合作银行（支行） */
  cooperateBankBranch?: string;
  /** 行业分类 */
  industryType?: number;
  /** 企业划型 DXQY:大型企业 ZXQY:中型企业 XXQY:小型企业 WXQY:微型企业 QT:其他 */
  bigSmall?: BigSmallEnum;
  /** 政策扶持领域类别 */
  goverType?: number;
  /** 债权起始日 */
  debtBeginDate?: number;
  /** 债权到期日 */
  debtEndDate?: number;
  /** 银行放款金额 */
  loanMoney?: number;
  /** 委保合同号 */
  pactCode?: string;
  /** 借据号 */
  loanCode?: string;
  /** 担保费率（年） */
  guaranteeRate?: number;
  /** 风补基金 */
  riskCompensation?: number;
  /** A角 */
  acode?: string;
  /** B角 */
  bcode?: string;
  /** A角 */
  aid?: number;
  /** B角 */
  bid?: number;
  /** 借款合同号 */
  loanPactCode?: string;
  /** 保证合同号 */
  pledPactCode?: string;
}

/**
 * 项目信息详细信息扩展表分页入参
 *
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export interface ProjectInfoExtPageRequest extends ProjectInfoExtRequest {
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
 * 项目信息详细信息扩展表出参
 *
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export interface ProjectInfoExtDTO extends ProjectInfoExtRequest {
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
 * 企业划型 DXQY:大型企业 ZXQY:中型企业 XXQY:小型企业 WXQY:微型企业 QT:其他枚举
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export enum BigSmallEnum {
  DXQY = "DXQY",
  ZXQY = "ZXQY",
  XXQY = "XXQY",
  WXQY = "WXQY",
  QT = "QT"
}
/**
 * 企业划型 DXQY:大型企业 ZXQY:中型企业 XXQY:小型企业 WXQY:微型企业 QT:其他下拉选择项
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export const bigSmallOptions = [
  { label: "大型企业", value: "DXQY" },
  { label: "中型企业", value: "ZXQY" },
  { label: "小型企业", value: "XXQY" },
  { label: "微型企业", value: "WXQY" },
  { label: "其他", value: "QT" }
];
export enum IndustryPolicySupportEnum {
  CYCX = "CYCX",
  SN = "SN",
  ZLXXCY = "ZLXXCY",
  WXQY = "XWQY",
  W = "W"
}
export const industryPolicySupportOptions = [
  { label: "创业创新", value: "CYCX" },
  { label: "三农", value: "SN" },
  { label: "战略新兴产业", value: "ZLXXCY" },
  { label: "小微企业", value: "XWQY" },
  { label: "无", value: "W" }
];
export enum subjectNatureEnum {
  E = "E",
  NE = "NE",
  FM = "FM",
  IB = "IB",
  SME = "SME",
  OTHER = "OTHER"
}
export const subjectNatureOptions = [
  { label: "非企业经济组织", value: "NE" },
  { label: "农户", value: "FM" },
  { label: "个体工商户", value: "IB" },
  { label: "小微企业", value: "SME" },
  { label: "其他", value: "OTHER" },
  { label: "企业", value: "E" }
];
