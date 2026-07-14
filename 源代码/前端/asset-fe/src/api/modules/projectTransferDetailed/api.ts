import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { ProjectTransferDetailedRequest, ProjectTransferDetailedPageRequest, ProjectTransferDetailedDTO } from "./interface";

/**
 *  接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
export function projectTransferDetailed() {
  const CONTEXT = "/project/transfer/detailed";
  return {
    /**
     * 新增
     *
     * @param params 入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-17 18:27:56
     */
    add: (params: Partial<ProjectTransferDetailedRequest>) => {
      return http.post<ProjectTransferDetailedDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-17 18:27:56
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-17 18:27:56
     */
    update: (params: Partial<ProjectTransferDetailedRequest>) => {
      return http.put<ProjectTransferDetailedDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-17 18:27:56
     */
    findById: (id: string | number) => {
      return http.get<ProjectTransferDetailedDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-17 18:27:56
     */
    findAll: (params: ProjectTransferDetailedPageRequest) => {
      return http.post<Array<ProjectTransferDetailedDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-17 18:27:56
     */
    findByTransferId: (id: string | number) => {
      return http.get<ProjectTransferDetailedDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/transferId/${id}`);
    }
  };
}
