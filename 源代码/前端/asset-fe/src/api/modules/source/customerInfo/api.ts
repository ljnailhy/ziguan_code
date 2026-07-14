import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { CustomerInfoRequest, CustomerInfoPageRequest, CustomerInfoDTO } from "./interface";

/**
 *  客户信息表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangtao
 * @since 2024-06-26 10:05:41
 */
export function customerInfo() {
  const CONTEXT = "/customer/info";
  return {
    /**
     * 新增
     *
     * @param params 客户信息表入参
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 10:05:41
     */
    add: (params: CustomerInfoRequest) => {
      return http.post<CustomerInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 10:05:41
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 客户信息表入参
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 10:05:41
     */
    update: (params: CustomerInfoRequest) => {
      return http.put<CustomerInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 10:05:41
     */
    findById: (id: string | number) => {
      return http.get<CustomerInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`, {}, { cancel: false });
    },

    /**
     * 分页查找
     *
     * @param params 客户信息表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 10:05:41
     */
    findAll: (params: CustomerInfoPageRequest) => {
      return http.post<CustomerInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    },

    /**
     * 分页查找
     *
     * @param params 客户信息表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-06-26 10:05:41
     */
    batch: (params: any) => {
      return http.post<any>(`${VITE_API_ASSET_URL}${CONTEXT}/batch`, params, {
        cancel: false,
        loading: false
      });
    }
  };
}
