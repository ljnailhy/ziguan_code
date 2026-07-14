import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { PropertyInfoRequest, PropertyInfoPageRequest, PropertyInfoDTO } from "./interface";

/**
 *  资产信息接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
export function propertyInfo() {
  const CONTEXT = "/property/info";
  return {
    /**
     * 新增
     *
     * @param params 资产信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-10 09:47:37
     */
    add: (params: Partial<PropertyInfoRequest>) => {
      return http.post<PropertyInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-10 09:47:37
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 资产信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-10 09:47:37
     */
    update: (params: Partial<PropertyInfoRequest>) => {
      return http.put<PropertyInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-10 09:47:37
     */
    findById: (id: string | number) => {
      return http.get<PropertyInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`, {}, { cancel: false });
    },

    /**
     * 分页查找
     *
     * @param params 资产信息分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-10 09:47:37
     */
    findAll: (params: PropertyInfoPageRequest) => {
      return http.post<Array<PropertyInfoDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },
    /**
     * 导入
     * @author wangshuai
     * @since 2024-07-10 09:47:37
     */
    importData: (params: any) => {
      return http.post(`${VITE_API_ASSET_URL}${CONTEXT}/import`, params);
    },
    /**
     * 导出
     * @author Yuqiang Wu
     * @since 2024-08-10 09:47:37
     */
    export: (params: any) => {
      return http.download(`${VITE_API_ASSET_URL}${CONTEXT}s/export`, params);
    }
  };
}
