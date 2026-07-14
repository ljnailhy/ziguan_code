/**
 * 获取字体图标 `document.styleSheets`
 * @method ali 获取阿里字体图标 `<i class="iconfont 图标类名"></i>`
 * @method ele 获取 element plus 自带图标 `<i class="图标类名"></i>`
 * @method ali 获取 fontawesome 的图标 `<i class="fa 图标类名"></i>`
 */
declare const initIconfont: {
    ali: () => Promise<unknown>;
    ele: () => Promise<unknown>;
    awe: () => Promise<unknown>;
};
export default initIconfont;
