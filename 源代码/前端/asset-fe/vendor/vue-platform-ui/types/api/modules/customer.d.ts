import { ResPage, Customer, ResultData } from "../interface/index";
/**
 * @name 客户管理模块
 */
export declare function useCustomApi(): {
    findAll: (params: Customer.ReqCustomerParams) => Promise<ResultData<ResPage<Customer.ResCustomerList>>>;
    findAllProject: (params: Customer.ReqCustomerParams) => Promise<ResultData<ResPage<Customer.ResCustomerList>>>;
    delete: (id: number) => Promise<ResultData<ResultData<any>>>;
    update: (id: number) => Promise<ResultData<ResultData<any>>>;
};
