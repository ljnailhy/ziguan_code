import axios, {
  AxiosInstance,
  AxiosError,
  AxiosRequestConfig,
  InternalAxiosRequestConfig,
  AxiosResponse,
} from "axios";
import {
  showFullScreenLoading,
  tryHideFullScreenLoading,
} from "../components/Loading/fullScreen";
// import { LOGIN_URL } from "@/config";
import { ElMessage } from "element-plus";
import { ResultData } from "../api/interface/index";
import { ResultEnum } from "../enums/httpEnum";
import { checkStatus } from "./helper/checkStatus";
import { AxiosCanceler } from "./helper/axiosCancel";
// import { useUserStore } from "@/stores/modules/user";
import {
  rsaEncryptToBase64,
  aesDecrypt,
  generateIV,
  aesEncrypt,
} from "../utils/crypto";
import { keyManager } from "../utils/keyManager";

export interface CustomAxiosRequestConfig extends InternalAxiosRequestConfig {
  loading?: boolean;
  cancel?: boolean;
}

const deepDecrypt = (
  obj: any,
  keys: { aesKey: string; iv: string },
  maxDepth = 3,
  currentDepth = 1
) => {
  if (currentDepth > maxDepth) return obj; // 超过最大层数直接返回

  if (Array.isArray(obj)) {
    return obj.map((item) =>
      deepDecrypt(item, keys, maxDepth, currentDepth + 1)
    );
  } else if (obj && typeof obj === "object") {
    Object.keys(obj).forEach((key) => {
      const val = obj[key];
      console.log(key.toLowerCase().includes("phone"));
      if (
        key.toLowerCase().includes("phone") ||
        key.toLowerCase().includes("account")
      ) {
        obj[key] = aesDecrypt(val, keys.aesKey, keys.iv);
      } else if (val && (typeof val === "object" || Array.isArray(val))) {
        obj[key] = deepDecrypt(val, keys, maxDepth, currentDepth + 1);
      }
    });
    return obj;
  }

  return obj;
};

const config = {
  // 默认地址请求地址，可在 .env.** 文件中修改
  baseURL: import.meta.env.VITE_API_URL as string,
  // 设置超时时间
  timeout: ResultEnum.TIMEOUT as number,
  // 跨域时候允许携带凭证
  withCredentials: true,
};

const axiosCanceler = new AxiosCanceler();

class RequestHttp {
  service: AxiosInstance;
  public constructor(config: AxiosRequestConfig) {
    // instantiation
    this.service = axios.create(config);

    /**
     * @description 请求拦截器
     * 客户端发送请求 -> [请求拦截器] -> 服务器
     * token校验(JWT) : 接受服务器返回的 token,存储到 vuex/pinia/本地储存当中
     */
    this.service.interceptors.request.use(
      (config: CustomAxiosRequestConfig) => {
        if (
          localStorage.getItem("Authorization") &&
          config.url?.indexOf("oauth2/login") == -1
        ) {
          config.headers!["Authorization"] = `${localStorage.getItem(
            "Authorization"
          )}`;
        }

        const tenantValue = localStorage.getItem("tenant");
        if (tenantValue && tenantValue !== "undefined") {
          config.headers!["TenantId"] = JSON.parse(<string>tenantValue).id;
        }

        // const userStore = useUserStore();

        try {
          // 为每个新请求生成AES密钥和IV
          // const aesKey = generateAesKey();
          const aesKey = localStorage.getItem("aesKey");
          const iv = generateIV();
          if (aesKey) {
            // 存储本次请求的密钥信息
            keyManager.setKeys(config, { aesKey, iv });
            // 将AES密钥用RSA加密后发送给后端
            config.headers!["platform-encrypt-key"] = rsaEncryptToBase64(
              aesKey.trim()
            );
            config.headers!["platform-encrypt-iv"] = iv;
            // 若请求中有数据
            if (config.data) {
              // 若是对象类型
              if (typeof config.data === "object") {
                Object.keys(config.data).forEach((key) => {
                  // 若数据中存在需要加密的字段
                  if (
                    key.toLowerCase().includes("phone") ||
                    key.toLowerCase().includes("account") ||
                    key.toLowerCase().includes("password")
                  ) {
                    // 对该字段进行AES加密
                    config.data[key] = aesEncrypt(config.data[key], aesKey, iv);
                  }
                });
              }
              // 其他类型视情况处理
            }
          }
        } catch (error) {
          console.error("加密失败:", error);
        }
        // 重复请求不需要取消，在 api 服务中通过指定的第三个参数: { cancel: false } 来控制
        config.cancel ??= true;
        config.cancel && axiosCanceler.addPending(config);
        // 当前请求不需要显示 loading，在 api 服务中通过指定的第三个参数: { loading: false } 来控制
        config.loading ??= true;
        config.loading && showFullScreenLoading();
        if (config.headers && typeof config.headers.set === "function") {
          // config.headers.set("x-access-token", userStore.token);
        }
        return config;
      },
      (error: AxiosError) => {
        return Promise.reject(error);
      }
    );

    /**
     * @description 响应拦截器
     *  服务器换返回信息 -> [拦截统一处理] -> 客户端JS获取到信息
     */
    this.service.interceptors.response.use(
      (response: AxiosResponse & { config: CustomAxiosRequestConfig }) => {
        const { data, config } = response;

        // const userStore = useUserStore();
        axiosCanceler.removePending(config);
        config.loading && tryHideFullScreenLoading();
        // 登录失效
        if (data.code == ResultEnum.OVERDUE) {
          // userStore.setToken("");
          // router.replace(LOGIN_URL);
          ElMessage.error(data.msg);
          return Promise.reject(data);
        }
        // 全局错误信息拦截（防止下载文件的时候返回数据流，没有 code 直接报错）
        if (data.code && data.code !== ResultEnum.SUCCESS) {
          ElMessage.error(data.errorMessage);
          return Promise.reject(data);
        }
        // 成功请求（在页面上除非特殊情况，否则不用处理失败逻辑）
        try {
          // 获取请求时存储的密钥和IV
          const keys = keyManager.getKeys(config);

          // 若响应中有数据
          if (data.data && keys) {
            data.data = deepDecrypt(data.data, keys, 2);

            // 若是对象类型
            // if (typeof data.data === "object") {
            //   Object.keys(data.data).forEach(key => {
            //     // 若数据中存在需要解密的字段
            //     if (key.toLowerCase().includes("phone") || key.toLowerCase().includes("account")) {
            //       // 使用存储的密钥和IV进行解密
            //       data.data[key] = aesDecrypt(data.data[key], keys.aesKey, keys.iv);
            //     }
            //   });
            // }
            // 若是数组类型 TODO 数组有性能问题
            // else if (Array.isArray(data.data)) {
            //   data.data.forEach(item => {
            //     if (typeof item === "object") {
            //       Object.keys(item).forEach(key => {
            //         // 若数据中存在需要解密的字段
            //         if (key.toLowerCase().includes("phone") || key.toLowerCase().includes("account")) {
            //           // 使用存储的密钥和IV进行解密
            //           item[key] = aesDecrypt(item[key], keys.aesKey, keys.iv);
            //         }
            //       });
            //     }
            //   });
            // }
            // 其他类型视情况处理
          }
        } catch (error) {
          console.error("解密失败:", error);
        } finally {
          // 使用完后删除密钥信息
          keyManager.removeKeys(response.config);
        }
        // 成功请求（在页面上除非特殊情况，否则不用处理失败逻辑）
        return data;
      },
      async (error: AxiosError) => {
        const { response } = error;
        tryHideFullScreenLoading();
        // 请求超时 && 网络错误单独判断，没有 response
        if (error.message.indexOf("timeout") !== -1)
          ElMessage.error("请求超时！请您稍后重试");
        if (error.message.indexOf("Network Error") !== -1)
          ElMessage.error("网络错误！请您稍后重试");
        // 根据服务器响应的错误状态码，做不同的处理
        if (response) checkStatus(response.status);
        // 服务器结果都没有返回(可能服务器错误可能客户端断网)，断网处理:跳转到断网页面
        // if (!window.navigator.onLine) router.replace("/500");
        return Promise.reject(error);
      }
    );
  }

  /**
   * @description 常用请求方法封装
   */
  get<T>(url: string, params?: object, _object = {}): Promise<ResultData<T>> {
    return this.service.get(url, { params, ..._object });
  }
  post<T>(
    url: string,
    params?: object | string,
    _object = {}
  ): Promise<ResultData<T>> {
    return this.service.post(url, params, _object);
  }
  put<T>(url: string, params?: object, _object = {}): Promise<ResultData<T>> {
    return this.service.put(url, params, _object);
  }
  delete<T>(url: string, params?: any, _object = {}): Promise<ResultData<T>> {
    return this.service.delete(url, { params, ..._object });
  }
  download(url: string, params?: object, _object = {}): Promise<BlobPart> {
    return this.service.post(url, params, { ..._object, responseType: "blob" });
  }
}

export default new RequestHttp(config);
