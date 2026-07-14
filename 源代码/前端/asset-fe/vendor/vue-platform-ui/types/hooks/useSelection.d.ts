/**
 * @description 表格多选数据操作
 * @param {String} rowKey 当表格可以多选时，所指定的 id
 * */
export declare const useSelection: (rowKey?: string) => {
    isSelected: import("vue").Ref<boolean>;
    selectedList: import("vue").Ref<{
        [key: string]: any;
    }[]>;
    selectedListIds: import("vue").ComputedRef<string[]>;
    selectionChange: (rowArr: {
        [key: string]: any;
    }[]) => void;
};
