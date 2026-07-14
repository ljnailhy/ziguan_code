import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { ProjectInfoExtRequest, ProjectInfoExtPageRequest, ProjectInfoExtDTO } from "./interface";

/**
 *  项目信息详细信息扩展表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export function projectInfoExt() {
  const CONTEXT = "/project/info/ext";
  return {
    /**
     * 新增
     *
     * @param params 项目信息详细信息扩展表入参
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    add: (params: ProjectInfoExtRequest) => {
      return http.post<ProjectInfoExtDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 项目信息详细信息扩展表入参
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    update: (params: ProjectInfoExtRequest) => {
      return http.put<ProjectInfoExtDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    findById: (id: string | number) => {
      return http.get<ProjectInfoExtDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 项目信息详细信息扩展表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    findAll: (params: ProjectInfoExtPageRequest) => {
      return http.post<ProjectInfoExtDTO>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },
    /**
     * 分页查找
     *
     * @param params 项目信息详细信息扩展表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    litigationLedger: (params: ProjectInfoExtPageRequest) => {
      return http.post<ProjectInfoExtDTO>(
        `${VITE_API_ASSET_URL}${CONTEXT}/litigation/ledger?current=${params.current}&size=${params.size}`,
        params
      );
    },
    /**
     * 导出
     * @author Yuqiang Wu
     * @since 2024-08-10 09:47:37
     */
    exportLitigationLedgers: (params: any) => {
      return http.download(`${VITE_API_ASSET_URL}${CONTEXT}/litigation/ledger/export`, params);
    }
  };
}
