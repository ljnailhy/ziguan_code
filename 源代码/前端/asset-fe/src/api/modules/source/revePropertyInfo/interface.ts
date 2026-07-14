import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 反担保/其他财产线索信息入参
 *
 * @author wangtao
 * @since 2024-07-09 10:56:02
 */
export interface RevePropertyInfoRequest extends ReqPage {
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
  reveName?: string;
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

/**
 * 反担保/其他财产线索信息分页入参
 *
 * @author wangtao
 * @since 2024-07-09 10:56:02
 */
export interface RevePropertyInfoPageRequest extends RevePropertyInfoRequest {
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
 * 反担保/其他财产线索信息出参
 *
 * @author wangtao
 * @since 2024-07-09 10:56:02
 */
export interface RevePropertyInfoDTO extends RevePropertyInfoRequest {
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
 * 单据类型（其他财产，反担保） PROPERTY:其他财产 REVE:反担保
 * @author wangtao
 * @since 2024-07-09 10:56:02
 */
export enum BillTypeEnum {
  REVE = "REVE",
  PROPERTY = "PROPERTY"
}
/**
 * 单据类型（其他财产，反担保） PROPERTY:其他财产 REVE:反担保下拉选择项
 * @author wangtao
 * @since 2024-07-09 10:56:02
 */
export const billTypeOptions = [
  { label: "REVE", value: "反担保" },
  { label: "PROPERTY", value: "其他财产" }
];

/**
 * 担保类型 QYBZ:企业保证 GRBZ:自然人保证 BDCDY:不动产抵押 DCDY:动产抵押YSZKZY:应收账款质押 QLZY:权力质押 CHZY:存货质押 QTLX:其他类型 NO:无
 * @author wangtao
 * @since 2024-07-09 10:56:02
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
 * @since 2024-07-09 10:56:02
 */
export const securityTypeOptions = [
  { label: "QYBZ", value: "企业保证" },
  { label: "GRBZ", value: "自然人保证" },
  { label: "BDCDY", value: "不动产抵押" },
  { label: "DCDY", value: "动产抵押" },
  { label: "YSZKZY", value: "应收账款质押" },
  { label: "QLZY", value: "权力质押" },
  { label: "CHZY", value: "存货质押" },
  { label: "QTLX", value: "其他类型" },
  { label: "NO", value: "无" }
];
