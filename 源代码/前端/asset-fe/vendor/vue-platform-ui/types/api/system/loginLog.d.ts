/**
 *
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 * @method getSysUsers 获取字典
 * @method addSysDics 新增字典
 */
export declare function useLoginLogApi(): {
    findAll: (data: object, params: object) => Promise<import("axios").AxiosResponse<any, any>>;
    add: (data: object) => Promise<import("axios").AxiosResponse<any, any>>;
    update: (data: object) => Promise<import("axios").AxiosResponse<any, any>>;
    deleteById: (id: string | number) => Promise<import("axios").AxiosResponse<any, any>>;
    findById: (id: string | number) => Promise<import("axios").AxiosResponse<any, any>>;
};
