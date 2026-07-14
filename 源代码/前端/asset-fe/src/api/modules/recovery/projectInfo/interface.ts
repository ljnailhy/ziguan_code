import { ReqPage } from "@/api/interface";
import { OrderEnum } from "@/enums/commonEnums";
import { DocumentTypeEnum, NatureEnum, SubjectTypeEnum } from "@/api/modules/source/subjectInfo/interface";

/**
 * 项目信息表入参
 *
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export interface ProjectInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 第三方标识 */
  thirdPartyId?: string;
  /** 原所属组织 */
  primaryAffiliatedOrg?: number;
  /** 所属组织 */
  affiliatedOrg?: number;
  /** 律所 */
  lawFirmId?: number;
  /** 律师 */
  lawyerId?: number;
  /** 关联合同 */
  contractId?: number;
  /** 保全经理 */
  manage?: string;
  /** 是否移交 */
  isTransfer?: boolean;
  /** 是否核销 */
  isWriteOff?: boolean;
  /** 项目状态 */
  projectState?: number;
  /** 执行时效（调解审批判决日期） */
  adjustTrialDate?: number;
  /** 债务人id */
  debtorId?: number;
  /** 项目名称 */
  projectName?: string;
  /** 所属区域_省 */
  belongProvince?: number;
  /** 所属区域_市 */
  belongCity?: number;
  /** 所属区域_区 */
  belongDistrict?: number;
  /** 详细地址 */
  address?: string;
  /** 代偿金额 */
  compensationMoney?: number;
  /** 代偿时间 */
  compensationDate?: number;
  /** 诉讼时效 */
  proceedingAgeingDate?: number;
  /** 移交至保全部日期 */
  transferDate?: number;
  /** 代偿方案 */
  compensationPlan?: string;
  /** 分险比例（债权人） */
  dividedInsuranceDebtor?: number;
  /** 分险比例（原担保） */
  dividedInsuranceSecurity?: number;
  /** 分险比例（再担保） */
  dividedInsuranceAgainSecurity?: number;
  /** 分险比例（其他） */
  dividedInsuranceOther?: number;
  projectInfoExtRequest: ProjectInfoExtRequestType;
  revePropertyInfoRequest: RevePropertyInfoRequestType[];
  subjectInfoRequest: SubjectInfoRequest;
  businessInfos: any[];
  projectLoanInfos: any[];
  fileRequests: any[];
  /** 业务信息 */
  projectBusinessInfoRequest: ProjectBusinessInfoRequest[];
  /** 借款信息 */
  projectLoanInfoRequest: ProjectLoanInfoRequest[];
  /** 是否删除 */
  isDelete: boolean;
  nature: NatureEnum;
  /** 证件类型 SFZ身份证 YYZZ 营业执照 JGZ 军官证 SBZ 士兵证 HZ 护照 JSZ 驾驶证 ZZZ 暂住证 */
  documentType?: DocumentTypeEnum;
  /** 证件号码 */
  documentNumber?: string;
  /** 法定代表人 */
  legalRepresentative?: string;
  /** 联系人 */
  contacts?: string;
  /** 联系人电话 */
  phone?: string;
  /** 是否历史代偿项目 **/
  projectIsHistory: boolean;
  /**
   * 项目扩展表
   */
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
  bigSmall?: number;
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
  aid?: number;
  /** B角 */
  bid?: number;
  /** 借款合同号 */
  loanPactCode?: string;
  /** 保证合同号 */
  pledPactCode?: string;
  // 累计回款金额
  collectionAmount?: number;
}
export interface RevePropertyInfoRequestType {
  /** 主键 */
  id: number;
  /** 来源id */
  sourceId?: number;
  /** 单据类型（其他财产，反担保） PROPERTY:其他财产 REVE:反担保 */
  billType?: BillTypeEnum;
  /** 对象Id */
  doId?: number;
  /** 对象类型 */
  doType?: string;
  /** 担保方式 */
  securityWay?: number;
  /** 担保类型 QYBZ:企业保证 GRBZ:自然人保证 BDCDY:不动产抵押 DCDY:动产抵押YSZKZY:应收账款质押 QLZY:权力质押 CHZY:存货质押 QTLX:其他类型 NO:无 */
  securityType?: SecurityTypeEnum;
  /** 反担保人名称 */
  reveName?: number;
  /** 反担保措施/其他财产 */
  reveMeasure?: string;
  /** 是否已处置 */
  isDispose?: boolean;
  /** 处置回款金额 */
  disposeMoney?: number;
  /** 保全日期 */
  preserveDate?: number;
  /** 备注 */
  remark?: string;
  /** 裁定以资抵债日期 */
  debtRepaymentDate?: number;
  /** 裁定抵债金额 */
  debtRepaymentMoney?: number;
}
export interface ProjectInfoExtRequestType {
  /** 主键 */
  id?: number;
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
  bigSmall?: number;
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
  /** 借款合同号 */
  loanPactCode?: string;
  /** 保证合同号 */
  pledPactCode?: string;
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
 * 项目信息表分页入参
 *
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export interface ProjectInfoPageRequest extends ProjectInfoRequest {
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

  lawFirmId?: number;
  idList?: [];
}

/**
 * 项目信息表出参
 *
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export interface ProjectInfoDTO extends ProjectInfoRequest {
  /** 创建人 */
  creator: number;
  /** 创建时间 */
  createStamp: number;
  /** 最后修改人 */
  lastEditor: number;
  /** 最后修改时间 */
  lastEditStamp: number;
  nature: NatureEnum;
  /** 证件类型 SFZ身份证 YYZZ 营业执照 JGZ 军官证 SBZ 士兵证 HZ 护照 JSZ 驾驶证 ZZZ 暂住证 */
  documentType?: DocumentTypeEnum;
  /** 证件号码 */
  documentNumber?: string;
  /** 律师 */
  lawyer: string;
  /** 律所 */
  name: string;
  /** 核销金额 */
  writeDffAmount: number;
  /** 累计回款金额 */
  totalCollectionAmount: number;
}
export interface ProductNameDTO {
  /** 产品Id */
  productId: number;
  /** 产品名 */
  productName: string;
}
/**
 * 同步分页入参
 *
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export interface SyncRequest extends ReqPage {
  /** 代偿时间范围  */
  repaymentDateRange?: number[];
  /** 债务人名称 */
  customerName: string;
  /** 债务人性质 */
  customerProperty: NatureEnum;
  /** 代偿金额 */
  repaymentAmount: number;
  /** 代偿时间 */
  repaymentDate: number;
  /** 证件类型 */
  credentialType: DocumentTypeEnum;
  /** 证件号码 */
  credentialNo: string;
  /** 联系人 */
  contact: string;
  /** 联系人电话 */
  tel: string;
  /** 省 */
  province: number;
  /** 市 */
  city: number;
  /** 区 */
  area: number;
  /** 行业分类 */
  industryGxb: number;
  /** 企业划型 */
  enterpriseSize: number;
  /** 政策扶持领域 */
  industryPolicySupport: string;
  /** 项目来源 */
  projectFrom: string;
}
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
  fileInfoList: any[];
}
/**
 * 同步代偿项目
 * @author wangtao
 * @since 2024-06-28 16:34:44
 */
export interface SyncCompensatoryRequest extends ReqPage {
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
  /** 法定代表人  联系人(或法人) */
  CONTACT?: string;
  /** 风补基金 */
  RiskFund?: string;
  /** 债务人性质 */
  Customer_Property?: string;
  /** 项目唯一标识 */
  business_no?: string;
  /** 证件类型 */
  CREDENTIAL_TYPE?: string;
  /** 行业分类 行业分类(工信部) */
  INDUSTRY_GXB?: string;
  /** 行缴纳税收 */
  TaxAmount?: number;
  /** 证件号码 */
  CREDENTIAL_NO?: string;
  /** 区域(省) */
  province?: string;
  /** 区域(省) */
  Warranty_contract_no?: string;
  /** 营业收入 */
  BUSINESS_REVENUE?: number;
  /**  区域(县|区) */
  area?: string;
  /**  债权起始日期 */
  DEBT_BILL_START_DAY?: number;
  /**  否首次银行贷款 是：Y 否：N; */
  IS_FIRST_LOAN_ACCOUNT?: string;
  /**  否产品名称 */
  PRODUCT_NAME?: string;
  /**  企业划型 */
  ENTERPRISE_SIZE?: string;
  /**  银行放款金额 */
  debt_amount?: number;
  /**  从业人数 */
  EMPLOYED_POPULATION?: number;
  /**  反担保措施 */
  anti_remark?: string[];
  /**  合作银行(支行) */
  cooperative_bank_third?: string;
  /**  代偿日期 */
  repayment_date?: number;
  /**  办公地址/现居住地址 */
  CURRENT_ADDRESS?: string;
  /**  风险归类 */
  RIST_TYPE_AFTER_GUARANTEE?: string;
  /**  代偿金额 */
  repayment_amount?: number;
  /**  行业分类四级（国标） */
  INDUSTRY_GXW?: string[];
  /**  资产总额[元] */
  TOTAL_ASSETS?: number;
  /**  行业分类(金融局) */
  INDUSTRY_JRJ?: string;
  /**  债权到期日期] */
  DEBT_BILL_DUE_DAY?: number;
  /**  区域(市) */
  city?: string;
  /**  还款银行 */
  repayment_bank?: string;
  /**  政策扶持领域 */
  INDUSTRY_POLICY_SUPPORT?: string;
  /**  委保合同号 */
  GUARANTEE_CONTRACT_NO?: string;
  /**  借据号(或其他唯一标识号) */
  DEBT_BILL_NO?: string;
  /**  备注信息 */
  remark?: string;
  /**  分险比例（债权人） */
  risk_rate_bank?: number;
  /**  借款合同号 */
  Principle_claim_contract_no?: string;
  /**  担保公司A角 */
  BUSINESS_MANAGER_A?: string;
  /**  未知ziduan */
  MANAGER_A?: string;
  /**  业务类型 线上审批：YBXZCN；上会审批：YBXZCW；见贷即保：JDJB； */
  business_type?: string;
  /**  担保费率（年）[%] */
  GUARANTEE_FEE_RATE?: number;
  /**  反担保方式 */
  COUNTER_GUARANTEE_TYPE?: string;
  /**  注册地址/户籍所在地 */
  REGISTERED_ADDRESS?: string;
  /**  项目来源 */
  PROJECT_FROM?: string;
  /**  分险比例（原担保) */
  risk_rate_origin?: number;
  /**  关联企业 */
  relation_enterprise?: string;
  /**  关联企业社会信用统一代码 */
  relation_enterprise_no?: string;
  /**  贷款利率[%] */
  LOAN_RATE?: number;
  /**  担保公司B角 */
  ASSIST_B?: string;
  /**  债务人名称 */
  CUSTOMER_NAME?: string;
  /**  分险比例(其他) */
  risk_rate_other?: number;
  /**  合作银行 */
  COOPERATIVE_BANK?: number;
  /**  分险比例（再担保） */
  risk_rate_again?: number;
  /**  联系人电话 */
  TEL?: number;
  /**  a角账号 */
  a_code?: string;
  /**  a角名称 */
  a_name?: string;
  /**  b角账号 */
  b_code?: string;
  /**  b角名称 */
  b_name?: string;
}

export const securityTypeNatureOptions = [
  { label: "企业保证", value: "QYBZ" },
  { label: "自然人保证", value: "GRBZ" },
  { label: "不动产抵押", value: "BDCDY" },
  { label: "动产抵押", value: "DCDY" },
  { label: "应收账款质押", value: "YSZKZY" },
  { label: "权力质押", value: "QLZY" },
  { label: "存货质押", value: "CHZY" },
  { label: "其他类型", value: "QTLX" },
  { label: "无", value: "NO" }
];

// export enum IsWriteOffEnum {
//   true = true,
//   false = false
// }
export const IsWriteOffOptions = [
  { label: "是", value: true },
  { label: "否", value: false }
];
/**
 * 担保类型 QYBZ:企业保证 GRBZ:自然人保证 BDCDY:不动产抵押 DCDY:动产抵押YSZKZY:应收账款质押 QLZY:权力质押 CHZY:存货质押 QTLX:其他类型 NO:无枚举
 * @author wangtao
 * @since 2024-06-28 16:34:44
 */
export enum SecurityTypeEnum {
  QYBZ = "QYBZ",
  GRBZ = "GRBZ",
  BDCDY = "BDCDY",
  DCDY = "DCDY",
  YSZKZY = "YSZKZY",
  QLZY = "QLZY",
  CHZY = "CHZY",
  QTLX = "QTLX",
  NO = "NO"
}
/**
 * 担保类型 QYBZ:企业保证 GRBZ:自然人保证 BDCDY:不动产抵押 DCDY:动产抵押YSZKZY:应收账款质押 QLZY:权力质押 CHZY:存货质押 QTLX:其他类型 NO:无下拉选择项
 * @author wangtao
 * @since 2024-06-28 16:34:44
 */
export const securityTypeOptions = [
  { label: "企业保证", value: "QYBZ" },
  { label: "自然人保证", value: "GRBZ" },
  { label: "不动产抵押", value: "BDCDY" },
  { label: "动产抵押", value: "DCDY" },
  { label: "应收账款质押", value: "YSZKZY" },
  { label: "权力质押", value: "QLZY" },
  { label: "存货质押", value: "CHZY" },
  { label: "其他类型", value: "QTLX" },
  { label: "无", value: "NO" }
];
/**
 * 单据类型（其他财产，反担保） PROPERTY:其他财产 REVE:反担保枚举
 * @author wangtao
 * @since 2024-06-28 16:34:44
 */
export enum BillTypeEnum {
  REVE = "REVE",
  PROPERTY = "PROPERTY"
}

/**
 * 借款信息表入参
 *
 * @author wangtao
 * @since 2024-07-26 18:51:24
 */
export interface ProjectLoanInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 项目id */
  projectId: number;
  /** 借据号（或其他唯一标识号） */
  loanCode?: string;
  /** 债权起始日 */
  debtBeginDate?: number;
  /** 债权到期日 */
  debtEndDate?: number;
  /** 银行放款金额 */
  loanMoney?: number;
  /** 代偿金额 */
  compensationMoney?: number;
  /** 代偿时间 */
  compensationDate?: number;
  /** 合作银行 */
  cooperateBank?: number;
  /** 合作银行（支行） */
  cooperateBankBranch?: string;
  /** 是否首次银行贷款  是：Y；	否：N; */
  isFirstLoanAccount?: string;
  /** 贷款利率[%] */
  loanRate?: number;
  /** 借款合同号 */
  loanPactCode?: string;
  /** 保证合同号 */
  pledPactCode?: string;
  /** 委保合同号 */
  pactCode?: string;
  /** 担保费率（年） */
  guaranteeRate?: number;
}
/**
 * 借款信息表出参
 *
 * @author wangtao
 * @since 2024-07-26 18:51:24
 */
export interface ProjectLoanInfoDTO extends ProjectLoanInfoRequest {}

/**
 * 业务信息表入参
 *
 * @author wangtao
 * @since 2024-07-26 18:51:12
 */
/**
 * 业务信息表出参
 *
 * @author wangtao
 * @since 2024-07-26 18:51:12
 */
export interface ProjectBusinessInfoDTO extends ProjectBusinessInfoRequest {}
export interface ProjectBusinessInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 项目Id */
  projectId: number;
  /** 业务编号 */
  relatedBusNo?: string;
  /** 业务类型 线上审批：YBXZCN；	上会审批：YBXZCW；	见贷即保：JDJB； */
  businessType?: BusinessTypeEnum;
  /** 产品名称 */
  productName?: number;
  /** 风险归类 */
  ristTypeAfterGuarantee?: number;
  /** 分险比例（债权人） */
  dividedInsuranceDebtor?: number;
  /** 分险比例（原担保） */
  dividedInsuranceSecurity?: number;
  /** 分险比例（再担保） */
  dividedInsuranceAgainSecurity?: number;
  /** 分险比例（其他） */
  dividedInsuranceOther?: number;
  /** 风补基金 */
  riskCompensation?: number;
  /** a角 */
  aid?: number;
  /** A角名称 */
  aname?: string;
  /** A角账号 */
  acode?: string;
  /** b角 */
  bid?: number;
  /** B角账号 */
  bcode?: string;
  /** B角名称 */
  bname?: string;
}
/**
 * 业务类型 线上审批：YBXZCN；	上会审批：YBXZCW；	见贷即保：JDJB；枚举
 * @author wangtao
 * @since 2024-07-26 18:51:12
 */
export enum BusinessTypeEnum {
  YBXZCN = "线上审批",
  YBXZCW = "上会审批",
  JDJB = "见贷即保"
}
/**
 * 业务类型 线上审批：YBXZCN；	上会审批：YBXZCW；	见贷即保：JDJB；下拉选择项
 * @author wangtao
 * @since 2024-07-26 18:51:12
 */
export const businessTypeOptions = [
  { label: "线上审批", value: "YBXZCN" },
  { label: "上会审批", value: "YBXZCW" },
  { label: "见贷即保", value: "JDJB" }
];
