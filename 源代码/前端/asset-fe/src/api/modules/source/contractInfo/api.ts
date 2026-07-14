import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { ContractInfoRequest, ContractInfoPageRequest, ContractInfoDTO } from "./interface";

/**
 *  合同信息接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-26 18:17:34
 */
export function contractInfo() {
  const CONTEXT = "/contract/info";
  return {
    /**
     * 新增
     *
     * @param params 合同信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 18:17:34
     */
    add: (params: ContractInfoRequest) => {
      return http.post<ContractInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 18:17:34
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 合同信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 18:17:34
     */
    update: (params: ContractInfoRequest) => {
      return http.put<ContractInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 18:17:34
     */
    findById: (id: string | number) => {
      return http.get<ContractInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`, {}, { cancel: false });
    },

    /**
     * 分页查找
     *
     * @param params 合同信息分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 18:17:34
     */
    findAll: (params: ContractInfoPageRequest) => {
      return http.post<ContractInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    }
  };
}
