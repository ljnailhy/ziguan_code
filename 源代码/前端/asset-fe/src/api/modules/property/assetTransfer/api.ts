import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { AssetTransferRequest, AssetTransferPageRequest, AssetTransferDTO } from "./interface";

/**
 *  资产转让接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:13
 */
export function assetTransfer() {
  const CONTEXT = "/asset/transfer";
  return {
    /**
     * 新增
     *
     * @param params 资产转让入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:13
     */
    add: (params: Partial<AssetTransferRequest>) => {
      return http.post<AssetTransferDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:13
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 资产转让入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:13
     */
    update: (params: Partial<AssetTransferRequest>) => {
      return http.put<AssetTransferDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:13
     */
    findById: (id: string | number) => {
      return http.get<AssetTransferDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 资产转让分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-07-13 09:53:13
     */
    findAll: (params: AssetTransferPageRequest) => {
      return http.post<Array<AssetTransferDTO>>(
        `${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`,
        params
      );
    },
    findPropertyBill: (doType: string, doId: number) => {
      return http.get<any>(
        `${VITE_API_ASSET_URL}/property/bill/${doType}/${doId}`,
        {},
        {
          cancel: false,
          loading: false
        }
      );
    },
    findIntermediary: (doType: string, doId: number) => {
      return http.get<any>(
        `${VITE_API_ASSET_URL}/document/intermediary/${doType}/${doId}`,
        {},
        {
          cancel: false,
          loading: false
        }
      );
    },
    findHangNetwork: (doType: string, doId: number) => {
      return http.get<any>(
        `${VITE_API_ASSET_URL}/hang/network/info/${doId}/${doType}`,
        {},
        {
          cancel: false,
          loading: false
        }
      );
    },

    /**
     * 提交
     *
     * @param params 资产转让入参
     * @returns Result
     * @author wangshuai
     * @since 2024-07-02 09:56:48
     */
    submit: (params: Partial<AssetTransferPageRequest>) => {
      return http.put<AssetTransferDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/submit`, params);
    }
  };
}
