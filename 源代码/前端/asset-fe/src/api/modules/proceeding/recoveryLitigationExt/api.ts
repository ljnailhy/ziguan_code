import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { RecoveryLitigationExtRequest, RecoveryLitigationExtPageRequest, RecoveryLitigationExtDTO } from "./interface";

/**
 *  诉讼反写信息扩展表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
export function recoveryLitigationExt() {
  const CONTEXT = "/recovery/litigation/ext";
  return {
    /**
     * 新增
     *
     * @param params 诉讼反写信息扩展表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-04 10:35:25
     */
    add: (params: Partial<RecoveryLitigationExtRequest>) => {
      return http.post<RecoveryLitigationExtDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-04 10:35:25
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 诉讼反写信息扩展表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-04 10:35:25
     */
    update: (params: Partial<RecoveryLitigationExtRequest>) => {
      return http.put<RecoveryLitigationExtDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-04 10:35:25
     */
    findById: (id: string | number) => {
      return http.get<RecoveryLitigationExtDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 诉讼反写信息扩展表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-04 10:35:25
     */
    findAll: (params: RecoveryLitigationExtPageRequest) => {
      return http.post<Array<RecoveryLitigationExtDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    }
  };
}
