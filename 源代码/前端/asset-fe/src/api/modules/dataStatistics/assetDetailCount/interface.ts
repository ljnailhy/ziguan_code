/**
 * 律所台账出参
 *
 * @author wangtao
 * @since 2024-06-28 10:13:53
 */
export interface AssetDetailLedgerDTO {
  /** 所属单位 */
  affiliatedUnit: number;
  /** 资产名称 */
  propertyName: string;
  /** 项目名称 */
  projectName: string;
  /** 用地性质（国有出让地，国有划拨地） */
  landUseNature: number;
  /** 资产过户备注 */
  transferOwnershipRemark: string;
  /** 面积 */
  area: number;
  /** 详细地址 */
  address: string;
  /** 产权人名称 */
  propertyOwner: string;
  /** 权证到期日 */
  propertyEndDate: number;
  /** 产权证号 */
  propertyCode: string;
  /** 原值 */
  originalValue: number;
  /** 资产单元状态 (在途，占用，闲置，已租赁，已转让)*/
  assetUnitState: number;
  /** 当年盘活回收资金 */
  propertyMoney: number;
}
