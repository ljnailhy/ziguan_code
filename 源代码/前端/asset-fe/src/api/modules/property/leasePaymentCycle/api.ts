import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { LeasePaymentCycleRequest, LeasePaymentCyclePageRequest, LeasePaymentCycleDTO } from "./interface";

/**
 *  租赁缴费周期表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-13 09:46:33
 */
export function leasePaymentCycle() {
  const CONTEXT = "/lease/payment/cycle";
  return {
    /**
     * 新增
     *
     * @param params 租赁缴费周期表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:46:33
     */
    add: (params: Partial<LeasePaymentCycleRequest>) => {
      return http.post<LeasePaymentCycleDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:46:33
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 租赁缴费周期表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:46:33
     */
    update: (params: Partial<LeasePaymentCycleRequest>) => {
      return http.put<LeasePaymentCycleDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:46:33
     */
    findById: (id: string | number) => {
      return http.get<LeasePaymentCycleDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 租赁缴费周期表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:46:33
     */
    findAll: (params: LeasePaymentCyclePageRequest) => {
      return http.post<Array<LeasePaymentCycleDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },
    findByDoId: (doType: string, doId: number) => {
      return http.get<any>(
        `${VITE_API_ASSET_URL}${CONTEXT}/${doType}/${doId}`,
        {},
        {
          cancel: false,
          loading: false
        }
      );
    }
  };
}
