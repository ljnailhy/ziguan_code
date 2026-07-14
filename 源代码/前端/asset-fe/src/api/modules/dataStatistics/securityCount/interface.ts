/**
 * 保全经理出参
 *
 * @author wangtao
 * @since 2024-06-28 10:13:53
 */
export interface ManageLedgerDTO {
  /** 保全经理 */
  manage: string;
  /** 在管项目数 */
  manageNum: number;
  /** 累计追偿金额(万元) */
  totalCompensationMoney: number;
  /** 累计回款金额 */
  totalPaymentCollection: number;
  /** 回款率(%) */
  paymentCollectionRate: number;
  /** 移交项目数 */
  transferNum: number;
  /** 核销项目数 */
  writeOffNum: number;
  /** 自主回款目标(万元) */
  ownTarget: number;
  /** 自主回款完成率 */
  ownDoneRate: number;
  /** 自主回款金额(万元) */
  ownPaymentCollectionAmount: number;
  /** 委托回款目标(万元) */
  entrustMoneyTarget: number;
  /** 委托回款金额(万元) */
  entrustMoneyAmount: number;
  /** 委托回款完成率 */
  entrustDoneRate: number;
  /** 累计回款目标(万元) */
  totalPaymentCollectionTarget: number;
}
