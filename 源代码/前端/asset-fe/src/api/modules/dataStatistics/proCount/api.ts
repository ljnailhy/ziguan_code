import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { ProjectLedgerDTO } from "./interface";
import { ProjectInfoPageRequest } from "@/api/modules/recovery/projectInfo/interface";

/**
 *  项目台账接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export function projectLedger() {
  const CONTEXT = "/ledger";
  return {
    /**
     * 项目台账
     *
     * @param params 项目台账入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 10:13:53
     */
    findProjectLedgerAll: (params: ProjectInfoPageRequest) => {
      return http.post<Array<ProjectLedgerDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}/project?current=${params.current}&size=${params.size}`,
        params
      );
    },
    /**
     * 导出
     * @author Yuqiang Wu
     * @since 2024-08-10 09:47:37
     */
    exportProjectLedger: (params: any) => {
      return http.download(`${VITE_API_ASSET_URL}${CONTEXT}/project/export`, params);
    }
  };
}
