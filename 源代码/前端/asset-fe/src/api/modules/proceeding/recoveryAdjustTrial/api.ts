import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { RecoveryAdjustTrialRequest, RecoveryAdjustTrialPageRequest, RecoveryAdjustTrialDTO } from "./interface";

/**
 *  调解或审判信息	接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
export function recoveryAdjustTrial() {
  const CONTEXT = "/recovery/adjust/trial";
  return {
    /**
     * 新增
     *
     * @param params 调解或审判信息	入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-03 10:13:14
     */
    add: (params: Partial<RecoveryAdjustTrialRequest>) => {
      return http.post<RecoveryAdjustTrialDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-03 10:13:14
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 调解或审判信息	入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-03 10:13:14
     */
    update: (params: Partial<RecoveryAdjustTrialRequest>) => {
      return http.put<RecoveryAdjustTrialDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-03 10:13:14
     */
    findById: (id: string | number) => {
      return http.get<RecoveryAdjustTrialDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 调解或审判信息	分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-03 10:13:14
     */
    findAll: (params: RecoveryAdjustTrialPageRequest) => {
      return http.post<Array<RecoveryAdjustTrialDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },
    /**
     * 根据诉讼id 查询审判信息
     *
     * @param id 诉讼id
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    findByLitigationId: (id: string | number) => {
      return http.get<RecoveryAdjustTrialDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/litigation/${id}`);
    },

    /**
     * 提交
     *
     * @param params 调解或审判信息	入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<RecoveryAdjustTrialRequest>) => {
      return http.put<RecoveryAdjustTrialDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
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
      return http.get<RecoveryAdjustTrialDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/writeBack/${id}`);
    }
  };
}
