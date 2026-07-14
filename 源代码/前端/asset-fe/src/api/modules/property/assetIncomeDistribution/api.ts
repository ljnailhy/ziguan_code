import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { AssetIncomeDistributionRequest, AssetIncomeDistributionPageRequest, AssetIncomeDistributionDTO } from "./interface";

/**
 *  资产收入分配接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-13 09:48:45
 */
export function assetIncomeDistribution() {
  const CONTEXT = "/asset/income/distribution";
  return {
    /**
     * 新增
     *
     * @param params 资产收入分配入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:48:45
     */
    add: (params: Partial<AssetIncomeDistributionRequest>) => {
      return http.post<AssetIncomeDistributionDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:48:45
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 资产收入分配入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:48:45
     */
    update: (params: Partial<AssetIncomeDistributionRequest>) => {
      return http.put<AssetIncomeDistributionDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:48:45
     */
    findById: (id: string | number) => {
      return http.get<AssetIncomeDistributionDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 资产收入分配分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:48:45
     */
    findAll: (params: AssetIncomeDistributionPageRequest) => {
      return http.post<Array<AssetIncomeDistributionDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },
    findByDoId: (doType: string, doId: number) => {
      return http.get<any>(
        `${VITE_API_ASSET_URL}${CONTEXT}/${doType}/${doId}`,
        {},
        {
          cancel: false,
          loading: false
        }
      );
    }
  };
}
