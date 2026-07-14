import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { LawyerInfoRequest, LawyerInfoPageRequest, LawyerInfoDTO } from "./interface";

/**
 *  律师团队接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-26 17:26:54
 */
export function lawyerInfo() {
  const CONTEXT = "/lawyer/info";
  return {
    /**
     * 新增
     *
     * @param params 律师团队入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 17:26:54
     */
    add: (params: LawyerInfoRequest) => {
      return http.post<LawyerInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 17:26:54
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 律师团队入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 17:26:54
     */
    update: (params: LawyerInfoRequest) => {
      return http.put<LawyerInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 17:26:54
     */
    findById: (id: string | number) => {
      return http.get<LawyerInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 律师团队分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 17:26:54
     */
    findAll: (params: LawyerInfoPageRequest) => {
      return http.post<LawyerInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    },
    /**
     * 根据律所id查询律师团队
     *
     * @param id 律所id
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 17:26:54
     */
    findByLawFirmId: (id: string | number) => {
      return http.get<LawyerInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/LawFirm/${id}`);
    },

    /**
     * 批量查询律师信息
     *
     * @param ids 律师ids
     * @returns Result
     * @author wangshuai
     * @since 2024-06-26 17:26:54
     */
    findByIds: (ids: EmptyArrayType) => {
      return http.post<Array<LawyerInfoDTO>>(`${VITE_API_ASSET_URL}${CONTEXT}/batch`, ids, { cancel: false });
    }
  };
}
