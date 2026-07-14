/**
 * 布局配置
 * 修改配置时：
 * 1、需要每次都清理 `window.localStorage` 浏览器永久缓存
 * 2、或者点击布局配置最底部 `一键恢复默认` 按钮即可看到效果
 */
export declare const useThemeConfig: import("pinia").StoreDefinition<"themeConfig", ThemeConfigState, {}, {
    setThemeConfig(data: ThemeConfigState): void;
}>;
