import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { RecoveryLitigationPageRequest, RecoveryLitigationDTO } from "./interface";

/**
 *  诉讼接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-01 16:38:08
 */
export function recoveryLitigation() {
  const CONTEXT = "/recovery/litigation";
  return {
    /**
     * 新增
     *
     * @param params 诉讼入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-01 16:38:08
     */
    add: (params: any) => {
      return http.post<RecoveryLitigationDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-01 16:38:08
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 诉讼入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-01 16:38:08
     */
    update: (params: any) => {
      return http.put<RecoveryLitigationDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-01 16:38:08
     */
    findById: (id: string | number) => {
      return http.get<RecoveryLitigationDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 诉讼分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-01 16:38:08
     */
    findAll: (params: RecoveryLitigationPageRequest) => {
      return http.post<Array<RecoveryLitigationDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params,
        { cancel: false }
      );
    },

    /**
     * 提交
     *
     * @param params 执行信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<RecoveryLitigationPageRequest>) => {
      return http.put<RecoveryLitigationDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-01 16:38:08
     */
    findByProjectId: (id: string | number) => {
      return http.get<RecoveryLitigationDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/project/${id}`);
    }
  };
}
