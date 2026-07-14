/**
 * 时间日期转换
 * @param date 当前时间，new Date() 格式
 * @param format 需要转换的时间格式字符串
 * @description format 字符串随意，如 `YYYY-mm、YYYY-MM-DD`
 * @description format 季度："YYYY-MM-DD HH:mm:ss QQQQ"
 * @description format 星期："YYYY-MM-DD HH:mm:ss WWW"
 * @description format 几周："YYYY-MM-DD HH:mm:ss ZZZ"
 * @description format 季度 + 星期 + 几周："YYYY-MM-DD HH:mm:ss WWW QQQQ ZZZ"
 * @returns 返回拼接后的时间字符串
 */
export declare function formatDate(date: Date, format: string): string;
/**
 * 获取当前日期是第几周
 * @param dateTime 当前传入的日期值
 * @returns 返回第几周数字值
 */
export declare function getWeek(dateTime: Date): number;
/**
 * 将时间转换为 `几秒前`、`几分钟前`、`几小时前`、`几天前`
 * @param param 当前时间，new Date() 格式或者字符串时间格式
 * @param format 需要转换的时间格式字符串
 * @description param 10秒：  10 * 1000
 * @description param 1分：   60 * 1000
 * @description param 1小时： 60 * 60 * 1000
 * @description param 24小时：60 * 60 * 24 * 1000
 * @description param 3天：   60 * 60* 24 * 1000 * 3
 * @returns 返回拼接后的时间字符串
 */
export declare function formatPast(param: string | Date, format?: string): string;
/**
 * 时间问候语
 * @param param 当前时间，new Date() 格式
 * @description param 调用 `formatAxis(new Date())` 输出 `上午好`
 * @returns 返回拼接后的时间字符串
 */
export declare function formatAxis(param: Date): string;
