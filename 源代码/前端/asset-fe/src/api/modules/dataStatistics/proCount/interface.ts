import { ReqPage } from "@/api/interface";
import { RevePropertyInfoDTO } from "@/api/modules/source/revePropertyInfo/interface";
import { SubjectTypeEnum } from "@/api/modules/source/subjectInfo/interface";
import { WriteOffClassificationEnum } from "@/api/modules/recovery/writeOff/interface";

/**
 * 保全经理出参
 *
 * @author wangtao
 * @since 2024-06-28 10:13:53
 */
export interface ProjectLedgerDTO extends ReqPage {
  /** 项目id */
  id: number;
  /** 项目名称 */
  projectName: string;
  /** 项目状态 */
  projectState: string;
  /** 行业分类 */
  industryType: string;
  /** 所属区域_市 */
  belongCity: string;
  /** 代偿时间 */
  compensationDate: number;
  /** 代偿金额(万元) */
  compensationMoney: number;
  /** 移交至保全部日期 */
  transferDate: number;
  /** 反担保处置情况/其他财产线索 */
  reveInfoList: RevePropertyInfoDTO[];
  /** 保全经理 */
  manage: string;
  /** 律所名称 */
  name: string;
  /** 律师姓名 */
  lawyerName: string;
  /** 律师电话 */
  lawyerPhone: string;
  /** 合同到期时间 */
  endDate: number;
  /** a角 */
  aname: string;
  /** b角 */
  bname: string;
  /** 被告(被执行人) **/
  defendant: string;
  /** 诉讼时效 */
  proceedingAgeingDate: number;
  /** 开庭时间 */
  courtSessionDate: number;
  /** 立案日期 */
  fillingDate: number;
  /** 判决案号 */
  adjustCode: string;
  /** 执行案号 */
  executeCode: string;
  /**  保全案号**/
  preservationCode: string;
  /** 保全日期 */
  preservationDate: number;
  /** 执行时效（调解审批判决日期） */
  judgeDate: number;
  /** 回款金额 */
  recoveryCollectionAmount: number;
  /** 剩余代偿金额 */
  residueRecoveryAmount: number;
  /** 诉讼费 */
  payType01: number;
  /** 保全费 */
  payType02: number;
  /** 保险费 */
  payType03: number;
  /** 其他费用 */
  payType04: number;
  /** 律师费用 */
  payType05: number;
  /** 核销标签 */
  isWriteOff: boolean;
  /** 业务类型 */
  subjectType: SubjectTypeEnum;
  /** 证件号码 */
  documentNumber: string;
  /** 现金 */
  cashAmount: number;
  /** 再担保 */
  reGuaranteeAmount: number;
  /** 抵债 */
  mortgageAmount: number;
  /** 银票 */
  silverBillAmount: number;
  /** 核销时间 */
  writeOffDate: number;
  /** 核销金额 */
  writeDffAmount: number;
  /** 核销项目分类 A:A B:B C:C D:D */
  writeOffClassification: WriteOffClassificationEnum;
}
