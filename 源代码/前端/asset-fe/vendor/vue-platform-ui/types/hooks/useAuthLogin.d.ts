export declare function useAuthLogin(): {
    loading: import("vue").Ref<boolean>;
    isShowForm: import("vue").Ref<boolean>;
    chooseData: import("vue").Ref<any>;
    handleLoginSuccess: (data: any, initDynamicRouter: Function, refresh?: boolean) => Promise<void>;
};
