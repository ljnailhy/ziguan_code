import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import {
  RecoveryLitigationDetailsRequest,
  RecoveryLitigationDetailsPageRequest,
  RecoveryLitigationDetailsDTO
} from "./interface";

/**
 *  终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
export function recoveryLitigationDetails() {
  const CONTEXT = "/recovery/litigation/details";
  return {
    /**
     * 新增
     *
     * @param params 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 15:44:58
     */
    add: (params: Partial<RecoveryLitigationDetailsRequest>) => {
      return http.post<RecoveryLitigationDetailsDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 15:44:58
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 15:44:58
     */
    update: (params: Partial<RecoveryLitigationDetailsRequest>) => {
      return http.put<RecoveryLitigationDetailsDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 15:44:58
     */
    findById: (id: string | number) => {
      return http.get<RecoveryLitigationDetailsDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 15:44:58
     */
    findAll: (params: RecoveryLitigationDetailsPageRequest) => {
      return http.post<Array<RecoveryLitigationDetailsDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },

    /**
     * 提交
     *
     * @param params 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<RecoveryLitigationDetailsRequest>) => {
      return http.put<RecoveryLitigationDetailsDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
    },

    /**
     * 反写相关信息
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-03 10:13:14
     */
    writeBackProject: (id: string | number | undefined) => {
      return http.get<RecoveryLitigationDetailsDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/writeBack/${id}`);
    }
  };
}
