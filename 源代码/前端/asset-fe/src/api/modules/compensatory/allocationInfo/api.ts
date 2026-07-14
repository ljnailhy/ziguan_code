import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { AllocationInfoRequest, AllocationInfoPageRequest, AllocationInfoDTO } from "./interface";

/**
 *  分配/变更主表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
export function allocationInfo() {
  const CONTEXT = "/allocation/info";
  return {
    /**
     * 新增
     *
     * @param params 分配/变更主表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 10:13:53
     */
    add: (params: Partial<AllocationInfoRequest>) => {
      return http.post<AllocationInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 10:13:53
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 分配/变更主表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 10:13:53
     */
    update: (params: Partial<AllocationInfoRequest>) => {
      return http.put<AllocationInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 提交
     *
     * @param params 分配/变更主表入参
     * @returns Result
     * @author Yuqiang Wu
     * @since 2024/6/29 029 17:52
     */
    submit: (params: Partial<AllocationInfoRequest>) => {
      return http.put<AllocationInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 10:13:53
     */
    findById: (id: string | number) => {
      return http.get<AllocationInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 分配/变更主表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 10:13:53
     */
    findAll: (params: AllocationInfoPageRequest) => {
      return http.post<Array<AllocationInfoDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },

    /**
     * 反写相关信息
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-03 10:13:14
     */
    writeBackProject: (id: string | number | undefined) => {
      return http.get<AllocationInfoDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/writeBack/${id}`);
    }
  };
}
