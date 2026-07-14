import { CustomAxiosRequestConfig } from "../api";
interface CryptoKeys {
    aesKey: string;
    iv: string;
}
declare class RequestKeyManager {
    private keyMap;
    private generateRequestId;
    setKeys(config: CustomAxiosRequestConfig, keys: CryptoKeys): void;
    getKeys(config: CustomAxiosRequestConfig): CryptoKeys | undefined;
    removeKeys(config: CustomAxiosRequestConfig): void;
}
export declare const keyManager: RequestKeyManager;
export {};
