import { ReqPage } from "@/api/interface/index";
import { OrderEnum } from "@/enums/commonEnums";
import { PropertyRightInfoRequest } from "@/api/modules/property/propertyRightInfo/interface";

/**
 * 资产信息入参
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
export interface PropertyInfoRequest extends ReqPage {
  /** 主键 */
  id: number;
  /** 资产状态'IDLE'：空闲,'OCCUPY'：占用,'LEASED'：已出租,'TRANSFERRED'：已转让,'NOT_TRANSFERRED'：未转让,'NOT_LEASED'：未出租 */
  propertyState?: PropertyStateEnum;
  /** 资产名称 */
  propertyName?: string;
  /** 大类 */
  type?: number;
  /** 资产分类 */
  propertyType?: number;
  /** 资产类型 */
  sourceType?: number;
  /** 项目id */
  projectId?: number;
  /** 反担保id */
  reveId?: any;
  /** 资产地址_省 */
  province?: number;
  /** 资产地址_市 */
  city?: number;
  /** 资产地址_区 */
  district?: number;
  /** 详细地址 */
  address?: string;
  /** 资产标签 */
  label?: string;
  /** 资产取得方式 */
  accessWay?: number;
  /** 资产描述 */
  propertyDescribe?: string;
  /** 面积 */
  area?: number;
  /** 资产用途 */
  assetUse?: string;
  /** 产权证号 */
  propertyCode?: string;
  /** 权证到期日 */
  propertyEndDate?: number;
  /** 资产过户日期 */
  propertyTransferOwnership?: number;
  /** 资产过户备注 */
  transferOwnershipRemark?: string;
  /** 资产抵债价格 */
  debtRepaymentMoney?: number;
  /** 资产原值 */
  originalValue?: number;
  /** 资产净值 */
  netWorth?: number;
  /** 资产处置价格 */
  disposalPrice?: number;
  /** 税费 */
  taxeFee?: number;
  /** 原债务人费用 */
  originalObligorFee?: number;
  projectName?: string;
  /** 跟进人 **/
  followUpPerson: string;
  propertyOwner: string;
  assertTime: number;
  /** 资产盈亏 */
  disposeFee?: number;
  profitAndLoss?: number;
  propertyTag?: string;
  fileInfoList: [];
  propertyPictureList: any[];
  separable?: boolean;
  totalArea?: number;
  affiliatedUnit?: number;
  landUseNature?: number;
  propertyRightInfoList: PropertyRightInfoRequest[];
  propertyRightInfoRequests: PropertyRightInfoRequest[];
}

/**
 * 资产信息分页入参
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
export interface PropertyInfoPageRequest extends PropertyInfoRequest {
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
 * 资产信息出参
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
export interface PropertyInfoDTO extends PropertyInfoRequest {
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
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
export enum PropertyStateEnum {
  IDLE = "IDLE",
  PART_IDLE = "PART_IDLE",
  OCCUPY = "OCCUPY",
  // PART_OCCUPY = "PART_OCCUPY",
  LEASED = "LEASED",
  PART_TRANSFER = "PART_TRANSFER",
  TRANSFERRED = "TRANSFERRED",
  SELF_USE = "SELF_USE",
  PART_LEASE = "PART_LEASE"
  // IN_TRANSIT = "IN_TRANSIT"
}
/**
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
export const propertyStateOptions = [
  { label: "闲置", value: "IDLE" },
  { label: "自用", value: "SELF_USE" },
  { label: "已出租", value: "LEASED" },
  { label: "已转让", value: "TRANSFERRED" },
  { label: "待办证", value: "PART_IDLE" },
  { label: "被占用", value: "OCCUPY" },
  // { label: "部分占用", value: "PART_OCCUPY" },
  { label: "部分租赁", value: "PART_LEASE" },
  { label: "部分转让", value: "PART_TRANSFER" }
  // ,
  // { label: "在途", value: "IN_TRANSIT" }
];

export const leasePaymentOptions = [
  { label: "已缴费", value: "PAID" },
  { label: "未缴费", value: "NOTPAID" }
];
