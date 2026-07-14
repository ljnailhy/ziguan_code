import { Login } from "../../interface";
import {
  VITE_API_INFRASTRUCTURE_URL,
  VITE_API_OAUTH2_URL,
} from "../../config/servicePort";

import http from "../../index";

/**
 * @name 登录模块
 */

export const loginApi = (params: Login.ReqLoginForm) => {
  return http.post<Login.ResLogin>(`${VITE_API_OAUTH2_URL}/login`, params, {
    loading: false,
  });
};

//生成验证码图片

export const generateCaptcha = () => {
  return http.get<Login.ReqLoginForm>(
    `${VITE_API_OAUTH2_URL}/captcha/image`,
    {},
    {
      loading: false,
    }
  );
};

//登陆日志
export const loginLog = () => {
  return http.post<any>(
    `${VITE_API_INFRASTRUCTURE_URL}/sys/loginlog`,
    {},
    { loading: false }
  );
};

// 获取菜单列表
export const queryMenu = () => {
  return http.post<any>(
    `${VITE_API_INFRASTRUCTURE_URL}/sys/authoritys/current`,
    {},
    { loading: false }
  );
};

//获取当前用户信息
export function getCurrentUserInfo() {
  return http.get(`${VITE_API_INFRASTRUCTURE_URL}/sys/user/current`);
}
