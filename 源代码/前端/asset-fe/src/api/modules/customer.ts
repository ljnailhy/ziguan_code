import { Customer } from "@/api/interface/index";
import { VITE_API_AUTO_URL } from "@/api/config/servicePort";
import http from "@/api";

/**
 * @name 客户管理模块
 */
export function useCustomApi() {
  return {
    findAll: (params: Customer.CustomerPageRequest) => {
      return http.post<Array<Customer.ResCustomerList>>(
        `${VITE_API_AUTO_URL}/customer/infos?current=${params.current}&size=${params.size}`,
        params
      );
    },
    add: (params: Customer.ReqCustomerParams) => {
      return http.post(`${VITE_API_AUTO_URL}/customer/info`, params);
    },
    update: (params: Customer.ReqCustomerParams) => {
      return http.put(`${VITE_API_AUTO_URL}/customer/info/`, params);
    },
    delete: (id: number) => {
      return http.delete(`${VITE_API_AUTO_URL}/customer/info/${id}`);
    },
    findById: (id: number) => {
      return http.get<Customer.ResCustomerList>(`${VITE_API_AUTO_URL}/customer/info/${id}`);
    }
  };
}
