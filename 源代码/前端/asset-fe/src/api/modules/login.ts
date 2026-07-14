import { Login } from "@/api/interface/index";
import { VITE_API_OAUTH2_URL, VITE_API_INFRASTRUCTURE_URL } from "@/api/config/servicePort";
import authMenuList from "@/assets/json/authMenuList.json";
import authButtonList from "@/assets/json/authButtonList.json";
import http from "@/api";

/**
 * @name 登录模块
 */
// 用户登录
// return http.post<Login.ResLogin>(`${VITE_API_OAUTH2_URL}/login`, params, { loading: false }); // 正常 post json 请求  ==>  application/json
// return http.post<Login.ResLogin>(`${VITE_API_OAUTH2_URL}/login`, params, { loading: false }); // 控制当前请求不显示 loading
// return http.post<Login.ResLogin>(`${VITE_API_OAUTH2_URL}/login`, {}, { params }); // post 请求携带 query 参数  ==>  ?username=admin&password=123456
// return http.post<Login.ResLogin>(`${VITE_API_OAUTH2_URL}/login`, qs.stringify(params)); // post 请求携带表单参数  ==>  application/x-www-form-urlencoded
// return http.get<Login.ResLogin>(`${VITE_API_OAUTH2_URL}/login?${qs.stringify(params, { arrayFormat: "repeat" })}`); // get 请求可以携带数组等复杂参数
export const loginApi = (params: Login.ReqLoginForm) => {
  return http.post<Login.ResLogin>(`${VITE_API_OAUTH2_URL}/login`, params, { loading: false });
};

// 获取菜单列表
export const getAuthMenuListApi = (menuCode: string) => {
  return http.get<any>(`${VITE_API_INFRASTRUCTURE_URL}/sys/menu/current/${menuCode}`, {}, { loading: false });
  // 如果想让菜单变为本地数据，注释上一行代码，并引入本地 authMenuList.json 数据
  return authMenuList;
};

// 获取按钮权限
export const getAuthButtonListApi = () => {
  // return http.get<Login.ResAuthButtons>(VITE_API_OAUTH2_URL + `/auth/buttons`, {}, { loading: false });
  // 如果想让按钮权限变为本地数据，注释上一行代码，并引入本地 authButtonList.json 数据
  return authButtonList;
};

// 获取菜单列表
export const queryMenu = () => {
  return http.post<any>(`${VITE_API_INFRASTRUCTURE_URL}/sys/authoritys/current`, {}, { loading: false });
};

//登陆日志

export const loginlog = () => {
  return http.post<any>(`${VITE_API_INFRASTRUCTURE_URL}/sys/loginlog`, {}, { loading: false });
};
