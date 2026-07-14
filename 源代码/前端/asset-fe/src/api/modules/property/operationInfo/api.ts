import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { OperationInfoRequest, OperationInfoPageRequest, OperationInfoDTO } from "./interface";

/**
 *  运营信息表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:55
 */
export function operationInfo() {
  const CONTEXT = "/operation/info";
  return {
    /**
     * 新增
     *
     * @param params 运营信息表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:55
     */
    add: (params: Partial<OperationInfoRequest>) => {
      return http.post<OperationInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:55
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 运营信息表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:55
     */
    update: (params: Partial<OperationInfoRequest>) => {
      return http.put<OperationInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:55
     */
    findById: (id: string | number) => {
      return http.get<OperationInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 运营信息表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:55
     */
    findAll: (params: OperationInfoPageRequest) => {
      return http.post<Array<OperationInfoDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },

    /**
     * 提交
     *
     * @param params 运营信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<OperationInfoPageRequest>) => {
      return http.put<OperationInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
    }
  };
}
