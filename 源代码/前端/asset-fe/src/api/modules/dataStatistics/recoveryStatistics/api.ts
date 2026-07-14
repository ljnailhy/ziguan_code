import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";

/**
 *  保全经理台账接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export function recoveryLedger() {
  const CONTEXT = "/ledger";
  return {
    /**
     * 保全经理台账分页查找
     *
     * @param params 保全经理台账分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 10:13:53
     */
    findAll: (params: any) => {
      return http.get<Array<any>>(`${VITE_API_ASSET_URL}${CONTEXT}/project/count`, params);
    },
    exportCompensatoryCount: (params: any) => {
      return http.download(`${VITE_API_ASSET_URL}${CONTEXT}/compensatory/count/export`, params);
    }
  };
}
