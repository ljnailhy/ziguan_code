import http from "@/api";
import { VITE_API_ASSET_URL } from "@/api/config/servicePort";
import { BillLawyerRequest, BillLawyerPageRequest, BillLawyerDTO } from "./interface";

/**
 *  单据与律师关系表接口
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
export function billLawyer() {
  const CONTEXT = "/bill/lawyer";
  return {
    /**
     * 新增
     *
     * @param params 单据与律师关系表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:45
     */
    add: (params: BillLawyerRequest) => {
      return http.post<BillLawyerDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 删除
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:45
     */
    delete: (id: string | number) => {
      return http.delete(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 修改
     *
     * @param params 单据与律师关系表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:45
     */
    update: (params: BillLawyerRequest) => {
      return http.put<BillLawyerDTO>(`${VITE_API_ASSET_URL}${CONTEXT}`, params);
    },

    /**
     * 单个查找
     *
     * @param id 主键
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:45
     */
    findById: (id: string | number) => {
      return http.get<BillLawyerDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/${id}`);
    },

    /**
     * 分页查找
     *
     * @param params 单据与律师关系表分页入参(含页码)
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:45
     */
    findAll: (params: BillLawyerPageRequest) => {
      return http.post<BillLawyerDTO>(`${VITE_API_ASSET_URL}${CONTEXT}s?current=${params.current}&size=${params.size}`, params);
    },

    /**
     * 根据doType和doId查询律师
     *
     * @param doId 对象iD
     * @param doType 对象iD
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:45
     */
    findByDoId: (doId: string | number, doType: string) => {
      return http.get<BillLawyerDTO>(
        `${VITE_API_ASSET_URL}${CONTEXT}/${doType}/${doId}`,
        {},
        {
          cancel: false,
          loading: false
        }
      );
    },
    /**
     * 批量新增修改律师与单据关系
     *
     * @param params 单据与律师关系表入参
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:45
     */
    batchUpdata: (doId: string | number, doType: string, params: any) => {
      return http.post<BillLawyerDTO>(`${VITE_API_ASSET_URL}${CONTEXT}/batchUpdate/${doType}/${doId}`, params);
    },

    /**
     * 根据doType和doId查询历史变更律师
     *
     * @param doId 对象iD
     * @param doType 对象iD
     * @returns Result
     * @author wangshuai
     * @since 2024-06-28 11:49:45
     */
    findHistoryByDoId: (doId: string | number, doType: string) => {
      return http.get<BillLawyerDTO>(
        `${VITE_API_ASSET_URL}${CONTEXT}/history/${doType}/${doId}`,
        {},
        {
          cancel: false,
          loading: false
        }
      );
    }
  };
}
