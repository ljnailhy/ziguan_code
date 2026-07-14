import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import {
  PaymentCollectionTargetDetailRequest,
  PaymentCollectionTargetDetailPageRequest,
  PaymentCollectionTargetDetailDTO
} from "./interface";

/**
 *  回款目标明细接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-27 10:25:18
 */
export function paymentCollectionTargetDetail() {
  const CONTEXT = "/payment/collection/target/detail";
  return {
    /**
     * 新增
     *
     * @param params 回款目标明细入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-27 10:25:18
     */
    add: (params: PaymentCollectionTargetDetailRequest) => {
      return http.post<PaymentCollectionTargetDetailDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-27 10:25:18
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 回款目标明细入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-27 10:25:18
     */
    update: (params: PaymentCollectionTargetDetailRequest) => {
      return http.put<PaymentCollectionTargetDetailDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-27 10:25:18
     */
    findById: (id: string | number) => {
      return http.get<PaymentCollectionTargetDetailDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 回款目标明细分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-27 10:25:18
     */
    findAll: (params: PaymentCollectionTargetDetailPageRequest) => {
      return http.post<PaymentCollectionTargetDetailDTO>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },
    /**
     * 根据回款目标id查询回款目标明细
     *
     * @param id 目标id
     * @returns Result
     * @author wangshuai
     * @since 2024-06-27 10:25:18
     */
    findByTargetId: (id: string | number) => {
      return http.get<PaymentCollectionTargetDetailDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/target/${id}`);
    }
  };
}
