import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { ManageHistoryRecordRequest, ManageHistoryRecordPageRequest, ManageHistoryRecordDTO } from "./interface";

/**
 *  保全经理历史变更记录接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-24 14:24:35
 */
export function manageHistoryRecord() {
  const CONTEXT = "/manage/history/record";
  return {
    /**
     * 新增
     *
     * @param params 保全经理历史变更记录入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-24 14:24:35
     */
    add: (params: Partial<ManageHistoryRecordRequest>) => {
      return http.post<ManageHistoryRecordDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-24 14:24:35
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 保全经理历史变更记录入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-24 14:24:35
     */
    update: (params: Partial<ManageHistoryRecordRequest>) => {
      return http.put<ManageHistoryRecordDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-24 14:24:35
     */
    findById: (id: string | number) => {
      return http.get<ManageHistoryRecordDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 保全经理历史变更记录分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-24 14:24:35
     */
    findAll: (params: ManageHistoryRecordPageRequest) => {
      return http.post<Array<ManageHistoryRecordDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },

    /**
     * 根据项目查询历史记录
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-24 14:24:35
     */
    findByProjectId: (id: string | number) => {
      return http.get<ManageHistoryRecordDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/project/${id}`);
    }
  };
}
