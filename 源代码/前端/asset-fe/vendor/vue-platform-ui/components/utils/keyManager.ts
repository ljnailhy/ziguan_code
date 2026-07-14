import { CustomAxiosRequestConfig } from "../api";

interface CryptoKeys {
  aesKey: string;
  iv: string;
}

class RequestKeyManager {
  private keyMap = new Map<string, CryptoKeys>();

  // 生成请求的唯一标识
  private generateRequestId(config: CustomAxiosRequestConfig): string {
    return `${config.method}_${config.url}_${JSON.stringify(
      config.params
    )}_${JSON.stringify(config.data)}`;
  }

  // 设置密钥
  setKeys(config: CustomAxiosRequestConfig, keys: CryptoKeys) {
    const requestId = this.generateRequestId(config);
    this.keyMap.set(requestId, keys);
  }

  // 获取密钥
  getKeys(config: CustomAxiosRequestConfig): CryptoKeys | undefined {
    const requestId = this.generateRequestId(config);
    return this.keyMap.get(requestId);
  }

  // 删除密钥
  removeKeys(config: CustomAxiosRequestConfig) {
    const requestId = this.generateRequestId(config);
    this.keyMap.delete(requestId);
  }
}

export const keyManager = new RequestKeyManager();
