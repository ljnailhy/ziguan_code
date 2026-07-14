import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { PropertyBillRequest, PropertyBillPageRequest, PropertyBillDTO } from "./interface";

/**
 *  资产和单据关联表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-11 18:03:58
 */
export function propertyBill() {
  const CONTEXT = "/property/bill";
  return {
    /**
     * 新增
     *
     * @param params 资产和单据关联表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:03:58
     */
    add: (params: Partial<PropertyBillRequest>) => {
      return http.post<PropertyBillDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:03:58
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 资产和单据关联表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:03:58
     */
    update: (params: Partial<PropertyBillRequest>) => {
      return http.put<PropertyBillDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:03:58
     */
    findById: (id: string | number) => {
      return http.get<PropertyBillDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 资产和单据关联表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-11 18:03:58
     */
    findAll: (params: PropertyBillPageRequest) => {
      return http.post<Array<PropertyBillDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    }
  };
}
