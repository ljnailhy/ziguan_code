import { Login } from "../../interface";
/**
 * @name 登录模块
 */
export declare const loginApi: (params: Login.ReqLoginForm) => Promise<import("../../interface").ResultData<Login.ResLogin>>;
export declare const generateCaptcha: () => Promise<import("../../interface").ResultData<Login.ReqLoginForm>>;
export declare const loginLog: () => Promise<import("../../interface").ResultData<any>>;
export declare const queryMenu: () => Promise<import("../../interface").ResultData<any>>;
export declare function getCurrentUserInfo(): Promise<import("../../interface").ResultData<unknown>>;
