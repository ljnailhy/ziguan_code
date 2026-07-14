import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { RecoveryPaymentRequest, RecoveryPaymentPageRequest, RecoveryPaymentDTO } from "./interface";

/**
 *  付款表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
export function recoveryPayment() {
  const CONTEXT = "/recovery/payment";
  return {
    /**
     * 新增
     *
     * @param params 付款表入参
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 11:35:36
     */
    add: (params: Partial<RecoveryPaymentRequest>) => {
      return http.post<RecoveryPaymentDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 提交
     *
     * 付款表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<RecoveryPaymentRequest>) => {
      return http.put<RecoveryPaymentDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
    },
    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 11:35:36
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 付款表入参
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 11:35:36
     */
    update: (params: Partial<RecoveryPaymentRequest>) => {
      return http.put<RecoveryPaymentDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 11:35:36
     */
    findById: (id: string | number) => {
      return http.get<RecoveryPaymentDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 付款表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 11:35:36
     */
    findAll: (params: RecoveryPaymentPageRequest) => {
      return http.post<Array<RecoveryPaymentDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    }
  };
}
