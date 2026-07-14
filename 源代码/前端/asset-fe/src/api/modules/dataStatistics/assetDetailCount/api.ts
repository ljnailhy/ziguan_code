import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { AssetDetailLedgerDTO } from "./interface";
import { PropertyRightInfoRequest } from "@/api/modules/property/propertyRightInfo/interface";

/**
 *  律所台账接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangTao
 * @since 2024-06-28 10:13:53
 */
export function assetDetailLedger() {
  const CONTEXT = "/asset/detail/ledger";
  return {
    /**
     * 资产明细台账分页查找
     *
     * @param params 资产明细台账分页入参(含页码)
     * @returns Result
     * @author wangTao
     * @since 2024-06-28 10:13:53
     */
    findAll: (params: PropertyRightInfoRequest) => {
      return http.post<Array<AssetDetailLedgerDTO>>(`${VITE_API_ASSET_URL}${CONTEXT}s`, params);
    },
    /**
     * 导出
     * @author Yuqiang Wu
     * @since 2024-08-10 09:47:37
     */
    export: (params: any) => {
      return http.download(`${VITE_API_ASSET_URL}${CONTEXT}/export`, params);
    }
  };
}
