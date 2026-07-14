import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { DocumentIntermediaryRequest, DocumentIntermediaryPageRequest, DocumentIntermediaryDTO } from "./interface";

/**
 *  单据和中介关联表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-12 16:11:38
 */
export function documentIntermediary() {
  const CONTEXT = "/document/intermediary";
  return {
    /**
     * 新增
     *
     * @param params 单据和中介关联表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:11:38
     */
    add: (params: Partial<DocumentIntermediaryRequest>) => {
      return http.post<DocumentIntermediaryDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:11:38
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 单据和中介关联表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:11:38
     */
    update: (params: Partial<DocumentIntermediaryRequest>) => {
      return http.put<DocumentIntermediaryDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:11:38
     */
    findById: (id: string | number) => {
      return http.get<DocumentIntermediaryDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 单据和中介关联表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-12 16:11:38
     */
    findAll: (params: DocumentIntermediaryPageRequest) => {
      return http.post<Array<DocumentIntermediaryDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    }
  };
}
