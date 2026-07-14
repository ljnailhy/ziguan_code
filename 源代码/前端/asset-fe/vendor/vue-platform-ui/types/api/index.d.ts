import { AxiosInstance, AxiosRequestConfig, InternalAxiosRequestConfig } from "axios";
import { ResultData } from "../api/interface/index";
export interface CustomAxiosRequestConfig extends InternalAxiosRequestConfig {
    loading?: boolean;
    cancel?: boolean;
}
declare class RequestHttp {
    service: AxiosInstance;
    constructor(config: AxiosRequestConfig);
    /**
     * @description 常用请求方法封装
     */
    get<T>(url: string, params?: object, _object?: {}): Promise<ResultData<T>>;
    post<T>(url: string, params?: object | string, _object?: {}): Promise<ResultData<T>>;
    put<T>(url: string, params?: object, _object?: {}): Promise<ResultData<T>>;
    delete<T>(url: string, params?: any, _object?: {}): Promise<ResultData<T>>;
    download(url: string, params?: object, _object?: {}): Promise<BlobPart>;
}
declare const _default: RequestHttp;
export default _default;
