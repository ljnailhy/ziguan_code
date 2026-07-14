// import { Result } from "@/api/interface/index";
import { VITE_API_INFRASTRUCTURE_URL } from "@/api/config/servicePort";
import http from "@/api";

/**
 * @name 字典模块
 */
export function useBaseInfoApi() {
  return {
    findDicByCode: (code: string | number) => {
      return http.get<any>(`${VITE_API_INFRASTRUCTURE_URL}/sys/dictionary/code/${code}`, {}, { cancel: false });
    },
    findMessages: (params: any) => {
      return http.post<any>(`${VITE_API_INFRASTRUCTURE_URL}/sys/messages?current=${params.current}&size=${params.size}`, params, {
        cancel: false
      });
    },
    readMessage: (id: number) => {
      return http.get<any>(`${VITE_API_INFRASTRUCTURE_URL}/sys/message/read/${id}`, {}, { cancel: false });
    }
  };
}
