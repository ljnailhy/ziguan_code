import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { SubjectInfoRequest, SubjectInfoPageRequest, SubjectInfoDTO } from "./interface";

/**
 *  主体信息接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-25 17:52:58
 */
export function subjectInfo() {
  const CONTEXT = "/subject/info";
  return {
    /**
     * 新增
     *
     * @param params 主体信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-25 17:52:58
     */
    add: (params: SubjectInfoRequest) => {
      return http.post<SubjectInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-25 17:52:58
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 主体信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-25 17:52:58
     */
    update: (params: SubjectInfoRequest) => {
      return http.put<SubjectInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-25 17:52:58
     */
    findById: (id: any) => {
      return http.get<SubjectInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 主体信息分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-25 17:52:58
     */
    findAll: (params: SubjectInfoPageRequest) => {
      return http.post<SubjectInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    }
  };
}
