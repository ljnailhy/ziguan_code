/**
 * @description: 判断值是否未某个类型
 */
export declare function is(val: unknown, type: string): boolean;
/**
 * @description:  是否为函数
 */
export declare function isFunction<T = Function>(val: unknown): val is T;
/**
 * @description: 是否已定义
 */
export declare const isDef: <T = unknown>(val?: T) => val is T;
/**
 * @description: 是否未定义
 */
export declare const isUnDef: <T = unknown>(val?: T) => val is T;
/**
 * @description: 是否为对象
 */
export declare const isObject: (val: any) => val is Record<any, any>;
/**
 * @description:  是否为时间
 */
export declare function isDate(val: unknown): val is Date;
/**
 * @description:  是否为数值
 */
export declare function isNumber(val: unknown): val is number;
/**
 * @description:  是否为AsyncFunction
 */
export declare function isAsyncFunction<T = any>(val: unknown): val is Promise<T>;
/**
 * @description:  是否为promise
 */
export declare function isPromise<T = any>(val: unknown): val is Promise<T>;
/**
 * @description:  是否为字符串
 */
export declare function isString(val: unknown): val is string;
/**
 * @description:  是否为boolean类型
 */
export declare function isBoolean(val: unknown): val is boolean;
/**
 * @description:  是否为数组
 */
export declare function isArray(val: any): val is Array<any>;
/**
 * @description: 是否客户端
 */
export declare const isClient: () => boolean;
/**
 * @description: 是否为浏览器
 */
export declare const isWindow: (val: any) => val is Window;
/**
 * @description: 是否为 element 元素
 */
export declare const isElement: (val: unknown) => val is Element;
/**
 * @description: 是否为 null
 */
export declare function isNull(val: unknown): val is null;
/**
 * @description: 是否为 null || undefined
 */
export declare function isNullOrUnDef(val: unknown): val is null | undefined;
/**
 * @description: 是否为 16 进制颜色
 */
export declare const isHexColor: (str: string) => boolean;
