import { ReqPage } from "@/api/interface";
import { CollectionAscriptionEnum, CollectionSignEnum } from "@/api/modules/recovery/recoveryPaymentCollection/interface";
import { OrderEnum } from "@/enums/commonEnums";

/**
 * 保全经理出参
 *
 * @author wangtao
 * @since 2024-06-28 10:13:53
 */
export interface PaymentCollectionPageDTO {
  /** 项目名称 */
  projectName: string;
  /** 回款标记 */
  collectionSign: CollectionSignEnum;
  /** 回款时间 */
  collectionAmount: number;
  /** 是否历史代偿 */
  isCollectionHistorical: boolean;
  /** 回款归属 */
  collectionAscription: CollectionAscriptionEnum;
  /** 回款律所 */
  lawyerName: string;
  /** 回款摘要 */
  collectionSummary: string;
  /** 利息 */
  interest: number;
  /** 代偿金额 */
  compensatoryCash: number;
  /** 其他费用 */
  otherFee: number;
  /** 违约金 */
  defaultCash: number;
}
export interface PaymentCollectionLedgerPageRequest extends ReqPage {
  /** 回款律所 */
  lawyerName: string;
  /** 项目名称 */
  projectName: string;
  /** 创建时间范围 */
  createStampRange?: number[];
  /** 排序字段名 */
  field?: string;
  /** 排序类型 */
  order?: OrderEnum;
}
