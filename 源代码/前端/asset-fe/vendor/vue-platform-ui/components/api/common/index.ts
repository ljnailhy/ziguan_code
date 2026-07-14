import request from "../../utils/request";
import { VITE_API_INFRASTRUCTURE_URL } from "../../config/global";

/**
 * post请求 不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参
 *
 * @method queryMenu
 *
 */
export function useCommonApi() {
  return {
    findDicByCode: (code: String | Number) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionary/code/${code}`,
        method: "get",
      });
    },
    queryMenu: (data: object) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/authoritys/current`,
        method: "post",
        data,
      });
    },
    findDictByIds: (data: Array<string | number>) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionarys/ids`,
        method: "post",
        data,
      });
    },
    findUserByIds: (data: Array<string>) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/users/ids?hadDetail=false`,
        method: "post",
        data,
      });
    },
    findUserById: (userId: number) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/user/${userId}`,
        method: "get",
      });
    },
    findRegionsByAll: (data: object) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/regions`,
        method: "post",
        data,
      });
    },
    getFiles: (data: object) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/files?size=10000`,
        method: "post",
        data,
      });
    },
    getFilesPage: (data: object, params: object) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/files`,
        method: "post",
        data,
        params,
      });
    },
    addFiles: (data: Array<object>) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/file/batch`,
        method: "post",
        data,
      });
    },
    getAccount: () => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/user/current`,
        method: "get",
      });
    },
    getConfigures: (data: EmptyObjectType) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/file/configures?size=1000`,
        method: "post",
        data,
      });
    },
  };
}

//省市区
export function useRegionApi() {
  return {
    findRegionsByAll: (data: object) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/regions`,
        method: "post",
        data,
      });
    },
    findRegionsByIds: (data: Array<string | number>) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/regions/ids`,
        method: "post",
        data,
      });
    },
  };
}

export function dictionaryCommonApi() {
  return {
    getDictionaryList: (dictionaryCode: String, itemId?: String) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionary/code/${dictionaryCode}`,
        method: "get",
      });
    },
    getDictionaryItemList: (id: String) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/dictionaryitem/${id}`,
        method: "get",
      });
    },
  };
}

/**
 * 附件公共接口
 * @returns
 */
export function fileInfoCommonApi() {
  return {
    //	根据doId查询附件数据
    getFileInfoList: (doId: number, doType?: String) => {
      return request({
        url: `${VITE_API_INFRASTRUCTURE_URL}/sys/files?size=10000`,
        method: "post",
        data: { doId, doType },
      });
    },
  };
}
