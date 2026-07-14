import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { IntermediaryCustomerLeadRequest, IntermediaryCustomerLeadPageRequest, IntermediaryCustomerLeadDTO } from "./interface";

/**
 *  中介客户线索关联表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-12 16:12:05
 */
export function intermediaryCustomerLead() {
  const CONTEXT = "/intermediary/customer/lead";
  return {
    /**
     * 新增
     *
     * @param params 中介客户线索关联表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:12:05
     */
    add: (params: Partial<IntermediaryCustomerLeadRequest>) => {
      return http.post<IntermediaryCustomerLeadDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:12:05
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 中介客户线索关联表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:12:05
     */
    update: (params: Partial<IntermediaryCustomerLeadRequest>) => {
      return http.put<IntermediaryCustomerLeadDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:12:05
     */
    findById: (id: string | number) => {
      return http.get<IntermediaryCustomerLeadDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 中介客户线索关联表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:12:05
     */
    findAll: (params: IntermediaryCustomerLeadPageRequest) => {
      return http.post<Array<IntermediaryCustomerLeadDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },
    findByDoId: (doId: number, doType: string) => {
      return http.get<Array<IntermediaryCustomerLeadDTO>>(
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
