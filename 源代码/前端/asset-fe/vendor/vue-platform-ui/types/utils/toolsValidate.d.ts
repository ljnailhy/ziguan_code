/**
 * 2020.11.29 lyt 整理
 * 工具类集合，适用于平时开发
 * 新增多行注释信息，鼠标放到方法名即可查看
 */
/**
 * 验证百分比（不可以小数）
 * @param val 当前值字符串
 * @returns 返回处理后的字符串
 */
export declare function verifyNumberPercentage(val: string): string;
/**
 * 验证百分比（可以小数）
 * @param val 当前值字符串
 * @returns 返回处理后的字符串
 */
export declare function verifyNumberPercentageFloat(val: string): string;
/**
 * 小数或整数(不可以负数)
 * @param val 当前值字符串
 * @returns 返回处理后的字符串
 */
export declare function verifyNumberIntegerAndFloat(val: string): string;
/**
 * 正整数验证
 * @param val 当前值字符串
 * @returns 返回处理后的字符串
 */
export declare function verifiyNumberInteger(val: string): string;
/**
 * 去掉中文及空格
 * @param val 当前值字符串
 * @returns 返回处理后的字符串
 */
export declare function verifyCnAndSpace(val: string): string;
/**
 * 去掉英文及空格
 * @param val 当前值字符串
 * @returns 返回处理后的字符串
 */
export declare function verifyEnAndSpace(val: string): string;
/**
 * 禁止输入空格
 * @param val 当前值字符串
 * @returns 返回处理后的字符串
 */
export declare function verifyAndSpace(val: string): string;
/**
 * 金额用 `,` 区分开
 * @param val 当前值字符串
 * @returns 返回处理后的字符串
 */
export declare function verifyNumberComma(val: string): any;
/**
 * 匹配文字变色（搜索时）
 * @param val 当前值字符串
 * @param text 要处理的字符串值
 * @param color 搜索到时字体高亮颜色
 * @returns 返回处理后的字符串
 */
export declare function verifyTextColor(val: string, text?: string, color?: string): string;
/**
 * 数字转中文大写
 * @param val 当前值字符串
 * @param unit 默认：仟佰拾亿仟佰拾万仟佰拾元角分
 * @returns 返回处理后的字符串
 */
export declare function verifyNumberCnUppercase(val: any, unit?: string, v?: string): string;
/**
 * 手机号码
 * @param val 当前值字符串
 * @returns 返回 true: 手机号码正确
 */
export declare function verifyPhone(val: string): boolean;
/**
 * 国内电话号码
 * @param val 当前值字符串
 * @returns 返回 true: 国内电话号码正确
 */
export declare function verifyTelPhone(val: string): boolean;
/**
 * 登录账号 (字母开头，允许5-16字节，允许字母数字下划线)
 * @param val 当前值字符串
 * @returns 返回 true: 登录账号正确
 */
export declare function verifyAccount(val: string): boolean;
/**
 * 密码 (以字母开头，长度在6~16之间，只能包含字母、数字和下划线)
 * @param val 当前值字符串
 * @returns 返回 true: 密码正确
 */
export declare function verifyPassword(val: string): boolean;
/**
 * 强密码 (字母+数字+特殊字符，长度在6-16之间)
 * @param val 当前值字符串
 * @returns 返回 true: 强密码正确
 */
export declare function verifyPasswordPowerful(val: string): boolean;
/**
 * 密码强度
 * @param val 当前值字符串
 * @description 弱：纯数字，纯字母，纯特殊字符
 * @description 中：字母+数字，字母+特殊字符，数字+特殊字符
 * @description 强：字母+数字+特殊字符
 * @returns 返回处理后的字符串：弱、中、强
 */
export declare function verifyPasswordStrength(val: string): string;
/**
 * IP地址
 * @param val 当前值字符串
 * @returns 返回 true: IP地址正确
 */
export declare function verifyIPAddress(val: string): boolean;
/**
 * 邮箱
 * @param val 当前值字符串
 * @returns 返回 true: 邮箱正确
 */
export declare function verifyEmail(val: string): boolean;
/**
 * 身份证
 * @param val 当前值字符串
 * @returns 返回 true: 身份证正确
 */
export declare function verifyIdCard(val: string): boolean;
/**
 * 姓名
 * @param val 当前值字符串
 * @returns 返回 true: 姓名正确
 */
export declare function verifyFullName(val: string): boolean;
/**
 * 邮政编码
 * @param val 当前值字符串
 * @returns 返回 true: 邮政编码正确
 */
export declare function verifyPostalCode(val: string): boolean;
/**
 * url 处理
 * @param val 当前值字符串
 * @returns 返回 true: url 正确
 */
export declare function verifyUrl(val: string): boolean;
/**
 * 车牌号
 * @param val 当前值字符串
 * @returns 返回 true：车牌号正确
 */
export declare function verifyCarNum(val: string): boolean;
