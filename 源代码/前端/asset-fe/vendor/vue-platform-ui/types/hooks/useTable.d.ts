/**
 * @description table 页面操作方法封装
 * @param {Function} api 获取表格数据 api 方法 (必传)
 * @param {Object} initParam 获取数据初始化参数 (非必传，默认为{})
 * @param {Boolean} isPageable 是否有分页 (非必传，默认为true)
 * @param {Function} dataCallBack 对后台返回的数据进行处理的方法 (非必传)
 * */
export declare const useTable: (api?: (params: any) => Promise<any>, initParam?: object, isPageable?: boolean, dataCallBack?: (data: any) => any, requestError?: (error: any) => void) => {
    getTableList: () => Promise<void>;
    search: () => void;
    reset: () => void;
    handleSizeChange: (val: number) => void;
    handleCurrentChange: (val: number) => void;
    updatedTotalParam: () => void;
    tableData: import("vue").Ref<any[]>;
    pageable: import("vue").Ref<{
        current: number;
        size: number;
        total: number;
    }>;
    searchParam: import("vue").Ref<{
        [key: string]: any;
    }>;
    searchInitParam: import("vue").Ref<{
        [key: string]: any;
    }>;
    totalParam: import("vue").Ref<{
        [key: string]: any;
    }>;
    icon?: import("vue").Ref<{
        [key: string]: any;
    } | undefined> | undefined;
};
