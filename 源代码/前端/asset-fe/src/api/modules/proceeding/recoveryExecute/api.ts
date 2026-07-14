import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { RecoveryExecuteRequest, RecoveryExecutePageRequest, RecoveryExecuteDTO } from "./interface";

/**
 *  执行信息	接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-05 09:52:54
 */
export function recoveryExecute() {
  const CONTEXT = "/recovery/execute";
  return {
    /**
     * 新增
     *
     * @param params 执行信息	入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-05 09:52:54
     */
    add: (params: Partial<RecoveryExecuteRequest>) => {
      return http.post<RecoveryExecuteDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-05 09:52:54
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 执行信息	入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-05 09:52:54
     */
    update: (params: Partial<RecoveryExecuteRequest>) => {
      return http.put<RecoveryExecuteDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-05 09:52:54
     */
    findById: (id: string | number) => {
      return http.get<RecoveryExecuteDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 执行信息	分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-05 09:52:54
     */
    findAll: (params: RecoveryExecutePageRequest) => {
      return http.post<Array<RecoveryExecuteDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },

    /**
     * 提交
     *
     * @param params 执行信息入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<RecoveryExecutePageRequest>) => {
      return http.put<RecoveryExecuteDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
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
      return http.get<RecoveryExecuteDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/writeBack/${id}`);
    }
  };
}
