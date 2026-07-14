import { VITE_API_INFRASTRUCTURE_URL } from "@/api/config/servicePort";
import http from "@/api";

export const changePsw = (params: object) => {
  return http.put(`${VITE_API_INFRASTRUCTURE_URL}/sys/user/password`, params, {
    cancel: false
  });
};

export const changeUserInfo = (params: object) => {
  return http.put(`${VITE_API_INFRASTRUCTURE_URL}/sys/user`, params, {
    cancel: false
  });
};

export function getUserInfoById(id: number) {
  return http.get(`${VITE_API_INFRASTRUCTURE_URL}/sys/user/${id}`);
}
