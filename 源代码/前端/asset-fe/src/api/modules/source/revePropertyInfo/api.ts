import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { RevePropertyInfoRequest, RevePropertyInfoPageRequest, RevePropertyInfoDTO } from "./interface";

/**
 *  反担保信息接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangtao
 * @since 2024-07-09 10:56:02
 */
export function revePropertyInfo() {
  const CONTEXT = "/reve/property/info";
  return {
    /**
     * 新增
     *
     * @param params 反担保/其他财产线索信息入参
     * @returns Result
     * @author wangtao
     * @since 2024-07-09 10:56:02
     */
    add: (params: Partial<RevePropertyInfoRequest>) => {
      return http.post<RevePropertyInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-07-09 10:56:02
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 反担保/其他财产线索信息入参
     * @returns Result
     * @author wangtao
     * @since 2024-07-09 10:56:02
     */
    update: (params: Partial<RevePropertyInfoRequest>) => {
      return http.put<RevePropertyInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangtao
     * @since 2024-07-09 10:56:02
     */
    findById: (id: string | number) => {
      return http.get<RevePropertyInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 反担保/其他财产线索信息分页入参(含页码)
     * @returns Result
     * @author wangtao
     * @since 2024-07-09 10:56:02
     */
    findAll: (params: RevePropertyInfoPageRequest) => {
      return http.post<Array<RevePropertyInfoDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    }
  };
}
