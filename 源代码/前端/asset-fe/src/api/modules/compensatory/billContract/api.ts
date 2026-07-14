import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { BillContractRequest, BillContractPageRequest, BillContractDTO } from "./interface";

/**
 *  单据合同关联表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:32
 */
export function billContract() {
  const CONTEXT = "/bill/contract";
  return {
    /**
     * 新增
     *
     * @param params 单据合同关联表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:32
     */
    add: (params: BillContractRequest) => {
      return http.post<BillContractDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:32
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 单据合同关联表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:32
     */
    update: (params: BillContractRequest) => {
      return http.put<BillContractDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:32
     */
    findById: (id: string | number) => {
      return http.get<BillContractDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 单据合同关联表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:32
     */
    findAll: (params: BillContractPageRequest) => {
      return http.post<BillContractDTO>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    },
    /**
     * 根据doType和doId查询合同
     *
     * @param doId 对象iD
     * @param doType 对象iD
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:45
     */
    findByDoId: (doId: string | number, doType: string) => {
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
