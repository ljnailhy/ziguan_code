import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { HangNetworkInfoRequest, HangNetworkInfoPageRequest, HangNetworkInfoDTO } from "./interface";

/**
 *  挂网信息表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-09 15:15:21
 */
export function hangNetworkInfo() {
  const CONTEXT = "/hang/network/info";
  return {
    /**
     * 新增
     *
     * @param params 挂网信息表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-09 15:15:21
     */
    add: (params: Partial<HangNetworkInfoRequest>) => {
      return http.post<HangNetworkInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-09 15:15:21
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 挂网信息表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-09 15:15:21
     */
    update: (params: Partial<HangNetworkInfoRequest>) => {
      return http.put<HangNetworkInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-09 15:15:21
     */
    findById: (id: string | number) => {
      return http.get<HangNetworkInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 挂网信息表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-09 15:15:21
     */
    findAll: (params: HangNetworkInfoPageRequest) => {
      return http.post<Array<HangNetworkInfoDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },

    /**
     * 根据doid和dotype查询法拍记录
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-09 15:15:21
     */
    findByDoId: (doId: string | number, doType: string) => {
      return http.get<HangNetworkInfoDTO>(
        `${VITE_API_ASSET_URL}${CONTEXT}/${doId}/${doType}`,
        {},
        {
          cancel: false,
          loading: false
        }
      );
    }
  };
}
