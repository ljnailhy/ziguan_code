/**
 * @description 获取本地时间
 */
export declare const useTime: () => {
    year: import("vue").Ref<number>;
    month: import("vue").Ref<number>;
    day: import("vue").Ref<number>;
    hour: import("vue").Ref<string | number>;
    minute: import("vue").Ref<string | number>;
    second: import("vue").Ref<string | number>;
    week: import("vue").Ref<string>;
    nowTime: import("vue").Ref<string>;
};
