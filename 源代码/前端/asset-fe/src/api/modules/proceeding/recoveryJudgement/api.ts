import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { RecoveryJudgementRequest, RecoveryJudgementPageRequest, RecoveryJudgementDTO } from "./interface";

/**
 *  审判信息（立案一审二审再审）接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
export function recoveryJudgement() {
  const CONTEXT = "/recovery/judgement";
  return {
    /**
     * 新增
     *
     * @param params 审判信息（立案一审二审再审）入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    add: (params: Partial<RecoveryJudgementRequest>) => {
      return http.post<RecoveryJudgementDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 审判信息（立案一审二审再审）入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    update: (params: Partial<RecoveryJudgementRequest>) => {
      return http.put<RecoveryJudgementDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    findById: (id: string | number) => {
      return http.get<RecoveryJudgementDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 审判信息（立案一审二审再审）分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    findAll: (params: RecoveryJudgementPageRequest) => {
      return http.post<Array<RecoveryJudgementDTO>>(
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
      return http.get<RecoveryJudgementDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/litigation/${id}`);
    },

    /**
     * 提交
     *
     * @param params 审判信息（立案一审二审再审）入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<RecoveryJudgementRequest>) => {
      return http.put<RecoveryJudgementDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
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
      return http.get<RecoveryJudgementDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/writeBack/${id}`);
    }
  };
}
