import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { LawFirmInfoRequest, LawFirmInfoPageRequest, LawFirmInfoDTO } from "./interface";

/**
 *  律所信息接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-26 16:49:08
 */
export function lawFirmInfo() {
  const CONTEXT = "/law/firm/info";
  return {
    /**
     * 新增
     *
     * @param params 律所信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 16:49:08
     */
    add: (params: LawFirmInfoRequest) => {
      return http.post<LawFirmInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 16:49:08
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 律所信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 16:49:08
     */
    update: (params: LawFirmInfoRequest) => {
      return http.put<LawFirmInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 16:49:08
     */
    findById: (id: string | number) => {
      return http.get<LawFirmInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`, {}, { cancel: false });
    },

    /**
     * 分页查找
     *
     * @param params 律所信息分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 16:49:08
     */
    findAll: (params: LawFirmInfoPageRequest) => {
      return http.post<LawFirmInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    },

    /**
     * 组件分页查找带统计
     *
     * @param params 律所信息分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 16:49:08
     */
    findAssemblyList: (params: LawFirmInfoPageRequest) => {
      return http.post<LawFirmInfoDTO>(
        `${VITE_API_ASSET_URL}${CONTEXT}/assembly?current=${params.current}&size=${params.size}`,
        params
      );
    },
    isEnable: (id: string | number, enabled: boolean | number) => {
      return http.get<LawFirmInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/enable/${id}/${enabled}`);
    }
  };
}
