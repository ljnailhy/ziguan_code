import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { AgencyRequest, AgencyPageRequest, AgencyDTO } from "./interface";

/**
 *  代理机构接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangtao
 * @since 2024-06-25 19:28:45
 */
export function agency() {
  const CONTEXT = "/agency";
  return {
    /**
     * 新增
     *
     * @param params 代理机构入参
     * @returns Result
     * @author wangtao
     * @since 2024-06-25 19:28:45
     */
    add: (params: AgencyRequest) => {
      return http.post<AgencyDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-06-25 19:28:45
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 代理机构入参
     * @returns Result
     * @author wangtao
     * @since 2024-06-25 19:28:45
     */
    update: (params: AgencyRequest) => {
      return http.put<AgencyDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-06-25 19:28:45
     */
    findById: (id: string | number) => {
      return http.get<AgencyDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`, {}, { cancel: false });
    },

    /**
     * 分页查找
     *
     * @param params 代理机构分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-06-25 19:28:45
     */
    findAll: (params: AgencyPageRequest) => {
      return http.post<AgencyDTO>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    }
  };
}
