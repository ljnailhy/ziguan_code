import { ResPage, Customer, ResultData } from "../interface/index";
import {
  VITE_API_AUTO_URL,
  VITE_API_INFRASTRUCTURE_URL,
  VITE_API_HQX_ASSET_URL,
} from "../config/servicePort";
import http from "../index";

/**
 * @name 客户管理模块
 */
export function useCustomApi() {
  return {
    findAll: (params: Customer.ReqCustomerParams) => {
      return http.post<ResPage<Customer.ResCustomerList>>(
        `${VITE_API_AUTO_URL}/vehicle/infos?current=${
          params.current
        }&size=${50}`,
        params
      );
    },
    findAllProject: (params: Customer.ReqCustomerParams) => {
      return http.post<ResPage<Customer.ResCustomerList>>(
        `${VITE_API_HQX_ASSET_URL}/project/infos?current=${
          params.current
        }&size=${50}`,
        params
      );
    },
    delete: (id: number) => {
      return http.get<ResultData>(`${VITE_API_AUTO_URL}/vehicle/info/${id}`);
    },
    update: (id: number) => {
      return http.put<ResultData>(`${VITE_API_AUTO_URL}/customer/info/${id}`);
    },
  };
}
