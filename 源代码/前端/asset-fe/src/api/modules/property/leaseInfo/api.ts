import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { LeaseInfoRequest, LeaseInfoPageRequest, LeaseInfoDTO } from "./interface";

/**
 *  租赁信息表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
export function leaseInfo() {
  const CONTEXT = "/lease/info";
  return {
    /**
     * 新增
     *
     * @param params 租赁信息表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:04:22
     */
    add: (params: Partial<LeaseInfoRequest>) => {
      return http.post<LeaseInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:04:22
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 租赁信息表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:04:22
     */
    update: (params: Partial<LeaseInfoRequest>) => {
      return http.put<LeaseInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:04:22
     */
    findById: (id: string | number) => {
      return http.get<LeaseInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 租赁信息表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:04:22
     */
    findAll: (params: LeaseInfoPageRequest) => {
      return http.post<Array<LeaseInfoDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },

    /**
     * 提交
     *
     * @param params 租赁信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<LeaseInfoPageRequest>) => {
      return http.put<LeaseInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
    }
  };
}
