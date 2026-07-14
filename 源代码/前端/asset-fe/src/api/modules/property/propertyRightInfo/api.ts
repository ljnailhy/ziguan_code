import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { PropertyRightInfoRequest, PropertyRightInfoPageRequest, PropertyRightInfoDTO } from "./interface";

/**
 *  产权信息表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangtao
 * @since 2024-08-17 14:44:27
 */
export function propertyRightInfo() {
  const CONTEXT = "/property/right/info";
  return {
    /**
     * 新增
     *
     * @param params 产权信息表入参
     * @returns Result
     * @author wangtao
     * @since 2024-08-17 14:44:27
     */
    add: (params: Partial<PropertyRightInfoRequest>) => {
      return http.post<PropertyRightInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-08-17 14:44:27
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 产权信息表入参
     * @returns Result
     * @author wangtao
     * @since 2024-08-17 14:44:27
     */
    update: (params: Partial<PropertyRightInfoRequest>) => {
      return http.put<PropertyRightInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-08-17 14:44:27
     */
    findById: (id: string | number) => {
      return http.get<PropertyRightInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 产权信息表分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-08-17 14:44:27
     */
    findAll: (params: PropertyRightInfoPageRequest) => {
      return http.post<Array<PropertyRightInfoDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    }
  };
}
