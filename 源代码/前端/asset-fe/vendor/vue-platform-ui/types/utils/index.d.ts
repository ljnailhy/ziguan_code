import { FieldNamesProps } from "../components/VzTable/interface";
/**
 * @description 获取localStorage
 * @param {String} key Storage名称
 * @returns {String}
 */
export declare function localGet(key: string): any;
/**
 * @description 存储localStorage
 * @param {String} key Storage名称
 * @param {*} value Storage值
 * @returns {void}
 */
export declare function localSet(key: string, value: any): void;
/**
 * @description 清除localStorage
 * @param {String} key Storage名称
 * @returns {void}
 */
export declare function localRemove(key: string): void;
/**
 * @description 清除所有localStorage
 * @returns {void}
 */
export declare function localClear(): void;
/**
 * @description 判断数据类型
 * @param {*} val 需要判断类型的数据
 * @returns {String}
 */
export declare function isType(val: any): string;
/**
 * @description 生成唯一 uuid
 * @returns {String}
 */
export declare function generateUUID(): string;
/**
 * 判断两个对象是否相同
 * @param {Object} a 要比较的对象一
 * @param {Object} b 要比较的对象二
 * @returns {Boolean} 相同返回 true，反之 false
 */
export declare function isObjectValueEqual(a: {
    [key: string]: any;
}, b: {
    [key: string]: any;
}): boolean;
/**
 * @description 生成随机数
 * @param {Number} min 最小值
 * @param {Number} max 最大值
 * @returns {Number}
 */
export declare function randomNum(min: number, max: number): number;
/**
 * @description 获取当前时间对应的提示语
 * @returns {String}
 */
export declare function getTimeState(): "" | "早上好 ⛅，新的一天就要开始啦，起来后来杯温水或者咖啡，动力满满喔 ~" | "中午好 🌞，饭前喝口水，然后去吃最爱吃的饭，接着适当休息放松喔 ~" | "下午好 🌞，繁忙的下午也不要忘记喝水、休息喔 ~" | "晚上好 🌛，休息啦，先吃晚饭，然后出去散散步，或者锻炼身体喔 ~" | "凌晨好 🌛，没想到你那么努力，未来的美好悄然走向你，不过还是希望你早点休息，放下手上的事情，美滋滋的睡个好觉喔 ~";
/**
 * @description 获取浏览器默认语言
 * @returns {String}
 */
/**
 * @description 获取不同路由模式所对应的 url + params
 * @returns {String}
 */
export declare function getUrlWithParams(): any;
/**
 * @description 使用递归扁平化菜单，方便添加动态路由
 * @param {Array} menuList 菜单列表
 * @returns {Array}
 */
/**
 * @description 使用递归过滤出需要渲染在左侧菜单的列表 (需剔除 isHide == true 的菜单)
 * @param {Array} menuList 菜单列表
 * @returns {Array}
 * */
/**
 * @description 使用递归找出所有面包屑存储到 pinia/vuex 中
 * @param {Array} menuList 菜单列表
 * @param {Array} parent 父级菜单
 * @param {Object} result 处理后的结果
 * @returns {Object}
 */
/**
 * @description 使用递归处理路由菜单 path，生成一维数组 (第一版本地路由鉴权会用到，该函数暂未使用)
 * @param {Array} menuList 所有菜单列表
 * @param {Array} menuPathArr 菜单地址的一维数组 ['**','**']
 * @returns {Array}
 */
/**
 * @description 递归查询当前 path 所对应的菜单对象 (该函数暂未使用)
 * @param {Array} menuList 菜单列表
 * @param {String} path 当前访问地址
 * @returns {Object | null}
 */
/**
 * @description 使用递归过滤需要缓存的菜单 name (该函数暂未使用)
 * @param {Array} menuList 所有菜单列表
 * @param {Array} keepAliveNameArr 缓存的菜单 name ['**','**']
 * @returns {Array}
 * */
/**
 * @description 格式化表格单元格默认值 (el-table-column)
 * @param {Number} row 行
 * @param {Number} col 列
 * @param {*} callValue 当前单元格值
 * @returns {String}
 * */
export declare function formatTableColumn(row: number, col: number, callValue: any): any;
/**
 * @description 处理 VzTable 值为数组 || 无数据
 * @param {*} callValue 需要处理的值
 * @returns {String}
 * */
export declare function formatValue(callValue: any): any;
/**
 * @description 处理 prop 为多级嵌套的情况，返回的数据 (列如: prop: user.name)
 * @param {Object} row 当前行数据
 * @param {String} prop 当前 prop
 * @returns {*}
 * */
export declare function handleRowAccordingToProp(row: {
    [key: string]: any;
}, prop: string): any;
/**
 * @description 处理 prop，当 prop 为多级嵌套时 ==> 返回最后一级 prop
 * @param {String} prop 当前 prop
 * @returns {String}
 * */
export declare function handleProp(prop: string): string;
/**
 * @description 根据枚举列表查询当需要的数据（如果指定了 label 和 value 的 key值，会自动识别格式化）
 * @param {String} callValue 当前单元格值
 * @param {Array} enumData 字典列表
 * @param {Array} fieldNames label && value && children 的 key 值
 * @param {String} type 过滤类型（目前只有 tag）
 * @returns {String}
 * */
export declare function filterEnum(callValue: any, enumData?: any, fieldNames?: FieldNamesProps, type?: "tag"): any;
/**
 * @description 递归查找 callValue 对应的 enum 值
 * */
export declare function findItemNested(enumData: any, callValue: any, value: string, children: string): any;
/**
 * @description 后端数据动态渲染路由
 * */
export declare function parseMetaRecursively(item: any): void;
