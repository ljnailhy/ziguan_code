import { ReqPage } from "@/api/interface";

/**
 * 律所台账出参
 *
 * @author wangtao
 * @since 2024-06-28 10:13:53
 */
export interface LawyerLedgerDTO {
  /** 律所 */
  lawyerName: string;
  /** 在管项目数 */
  proNum: number;
  /** 累计代偿金额 */
  proMoney: number;
  /** 累计回款金额 */
  proAmount: number;
  /** 回款率(%) */
  collectionRatio: number;
}
