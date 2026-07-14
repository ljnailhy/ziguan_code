/**
 * 路由列表
 * @methods setRoutesList 设置路由数据
 * @methods setColumnsMenuHover 设置分栏布局菜单鼠标移入 boolean
 * @methods setColumnsNavHover 设置分栏布局最左侧导航鼠标移入 boolean
 */
export declare const useRoutesList: import("pinia").StoreDefinition<"routesList", RoutesListState<any>, {}, {
    setRoutesList(data: Array<string>): Promise<void>;
    setColumnsMenuHover(bool: Boolean): Promise<void>;
    setColumnsNavHover(bool: Boolean): Promise<void>;
}>;
