import { CustomAxiosRequestConfig } from "../index";
export declare const getPendingUrl: (config: CustomAxiosRequestConfig) => string;
export declare class AxiosCanceler {
    /**
     * @description: 添加请求
     * @param {Object} config
     * @return void
     */
    addPending(config: CustomAxiosRequestConfig): void;
    /**
     * @description: 移除请求
     * @param {Object} config
     */
    removePending(config: CustomAxiosRequestConfig): void;
    /**
     * @description: 清空所有pending
     */
    removeAllPending(): void;
}
