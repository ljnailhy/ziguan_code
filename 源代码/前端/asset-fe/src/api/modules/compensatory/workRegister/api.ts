import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { WorkRegisterRequest, WorkRegisterPageRequest, WorkRegisterDTO } from "./interface";

/**
 *  工作登记接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-28 17:23:16
 */
export function workRegister() {
  const CONTEXT = "/work/register";
  return {
    /**
     * 新增
     *
     * @param params 工作登记入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 17:23:16
     */
    add: (params: WorkRegisterRequest) => {
      return http.post<WorkRegisterDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 17:23:16
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 工作登记入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 17:23:16
     */
    update: (params: WorkRegisterRequest) => {
      return http.put<WorkRegisterDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 17:23:16
     */
    findById: (id: string | number) => {
      return http.get<WorkRegisterDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 工作登记分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 17:23:16
     */
    findAll: (params: WorkRegisterPageRequest) => {
      return http.post<WorkRegisterDTO>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    }
  };
}
