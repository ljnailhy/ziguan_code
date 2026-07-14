/**
 * post请求 不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参
 *
 * @method queryMenu
 *
 */
export declare function useCommonApi(): {
    findDicByCode: (code: String | Number) => Promise<import("axios").AxiosResponse<any, any>>;
    queryMenu: (data: object) => Promise<import("axios").AxiosResponse<any, any>>;
    findDictByIds: (data: Array<string | number>) => Promise<import("axios").AxiosResponse<any, any>>;
    findUserByIds: (data: Array<string>) => Promise<import("axios").AxiosResponse<any, any>>;
    findUserById: (userId: number) => Promise<import("axios").AxiosResponse<any, any>>;
    findRegionsByAll: (data: object) => Promise<import("axios").AxiosResponse<any, any>>;
    getFiles: (data: object) => Promise<import("axios").AxiosResponse<any, any>>;
    getFilesPage: (data: object, params: object) => Promise<import("axios").AxiosResponse<any, any>>;
    addFiles: (data: Array<object>) => Promise<import("axios").AxiosResponse<any, any>>;
    getAccount: () => Promise<import("axios").AxiosResponse<any, any>>;
    getConfigures: (data: EmptyObjectType) => Promise<import("axios").AxiosResponse<any, any>>;
};
export declare function useRegionApi(): {
    findRegionsByAll: (data: object) => Promise<import("axios").AxiosResponse<any, any>>;
    findRegionsByIds: (data: Array<string | number>) => Promise<import("axios").AxiosResponse<any, any>>;
};
export declare function dictionaryCommonApi(): {
    getDictionaryList: (dictionaryCode: String, itemId?: String) => Promise<import("axios").AxiosResponse<any, any>>;
    getDictionaryItemList: (id: String) => Promise<import("axios").AxiosResponse<any, any>>;
};
/**
 * 附件公共接口
 * @returns
 */
export declare function fileInfoCommonApi(): {
    getFileInfoList: (doId: number, doType?: String) => Promise<import("axios").AxiosResponse<any, any>>;
};
