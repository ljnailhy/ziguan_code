import '../theme/loading.scss';
/**
 * 页面全局 Loading
 * @method start 创建 loading
 * @method done 移除 loading
 */
export declare const NextLoading: {
    start: () => void;
    done: (time?: number) => void;
};
