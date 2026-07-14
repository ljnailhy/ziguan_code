/**
 *
 * （不建议写成 request.post(xxx)，因为这样 post 时，无法 params 与 data 同时传参）
 *
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 * @method getAll 获取部门(admin)
 * @method getTestMenu 获取后端动态路由菜单(test)
 */
export declare function useUsersApi(): {
    findAll: (data: object, params: object) => Promise<import("axios").AxiosResponse<any, any>>;
    add: (data: object) => Promise<import("axios").AxiosResponse<any, any>>;
    update: (data: object) => Promise<import("axios").AxiosResponse<any, any>>;
    delete: (id: string | number) => Promise<import("axios").AxiosResponse<any, any>>;
    findById: (id: string | number) => Promise<import("axios").AxiosResponse<any, any>>;
    findByOrg: (data: object, params: object) => Promise<import("axios").AxiosResponse<any, any>>;
    findByIds: (data: Array<string | number>) => Promise<import("axios").AxiosResponse<any, any>>;
};
