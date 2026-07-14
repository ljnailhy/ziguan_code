import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import {
  RecoveryPaymentCollectionRequest,
  RecoveryPaymentCollectionPageRequest,
  RecoveryPaymentCollectionDTO
} from "./interface";

/**
 *  回款表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangtao
 * @since 2024-07-08 10:09:46
 */
export function recoveryPaymentCollection() {
  const CONTEXT = "/recovery/payment/collection";
  return {
    /**
     * 新增
     *
     * @param params 回款表入参
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 10:09:46
     */
    add: (params: Partial<RecoveryPaymentCollectionRequest>) => {
      return http.post<RecoveryPaymentCollectionDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 10:09:46
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 回款表入参
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 10:09:46
     */
    update: (params: Partial<RecoveryPaymentCollectionRequest>) => {
      return http.put<RecoveryPaymentCollectionDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 10:09:46
     */
    findById: (id: string | number) => {
      return http.get<RecoveryPaymentCollectionDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`, {}, { cancel: false });
    },
    /**
     * 提交
     *
     * 回款表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<RecoveryPaymentCollectionRequest>) => {
      return http.put<RecoveryPaymentCollectionDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
    },
    /**
     * 回款导入
     * @param params
     */
    importData: (params: any) => {
      return http.post(`${VITE_API_ASSET_URL}${CONTEXT}/import/recovery/paymentCollection`, params);
    },
    /**
     * 分页查找
     *
     * @param params 回款表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-07-08 10:09:46
     */
    findAll: (params: RecoveryPaymentCollectionPageRequest) => {
      return http.post<Array<RecoveryPaymentCollectionDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },
    batch: (params: any) => {
      return http.post<any>(`${VITE_API_ASSET_URL}${CONTEXT}/batch/projectIds`, params);
    }
  };
}
