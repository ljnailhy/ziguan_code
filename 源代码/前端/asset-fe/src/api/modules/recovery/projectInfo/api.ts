import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { ProjectInfoRequest, ProjectInfoDTO, SyncCompensatoryRequest } from "./interface";

/**
 *  项目信息表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangtao
 * @since 2024-06-26 17:32:58
 */
export function projectInfo() {
  const CONTEXT = "/project/info";
  // 同步代偿项目分页路径
  const CONTEXT1 = "/compensatory/sync";
  return {
    /**
     * 分页查找
     *
     * @param params 项目信息表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    findCompensatorySyncAll: (params: any) => {
      return http.post<any>(`${VITE_API_ASSET_URL}${CONTEXT1}s?current=${params.current}&size=${params.size}`, params);
    },
    /**
     * 新增
     *
     * @param params 项目信息表入参
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    add: (params: ProjectInfoRequest) => {
      return http.post<ProjectInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
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
     * @param params 项目信息表入参
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    update: (params: ProjectInfoRequest) => {
      return http.put<ProjectInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
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
      return http.get<ProjectInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`, {}, { cancel: false });
    },
    findProductName: () => {
      return http.get<any>(`${VITE_API_ASSET_URL}${CONTEXT}/productName`);
    },
    findByIds: (ids: any) => {
      return http.post<ProjectInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/batch`, ids, { cancel: false });
    },
    /**
     * 分页查找
     *
     * @param params 项目信息表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    findAll: (params: any) => {
      return http.post<any>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    },
    /**
     * 分页带权限查找
     *
     * @param params 项目信息表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    findLimitsAll: (params: any) => {
      return http.post<any>(`${VITE_API_ASSET_URL}${CONTEXT}/limits?current=${params.current}&size=${params.size}`, params);
    },
    /**
     * 分页查找
     *
     * @param params 代偿项目api数据
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 17:32:58
     */
    getCompensatoryData: (params: SyncCompensatoryRequest) => {
      return http.post<SyncCompensatoryRequest>(
        `${VITE_API_ASSET_URL}${CONTEXT}/compensatory/project/data?current=${params.current}&size=${params.size}`,
        params
      );
    },
    syncCompensatory: (params: any) => {
      return http.post<SyncCompensatoryRequest>(`${VITE_API_ASSET_URL}${CONTEXT}/compensatory/project/sync`, params);
    },

    /**
     * 分页查找
     *
     */
    importData: (params: any) => {
      return http.post(`${VITE_API_ASSET_URL}${CONTEXT}/import/compensatory`, params);
    },

    findFlowAll: (params: any) => {
      return http.get(`${VITE_API_ASSET_URL}${CONTEXT}/flow`, params);
    },
    changeManage: (params: ProjectInfoRequest) => {
      return http.post<ProjectInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/change/manage`, params);
    }
  };

  /**
   * const CONTEXT = "/project/info";
   */
}
