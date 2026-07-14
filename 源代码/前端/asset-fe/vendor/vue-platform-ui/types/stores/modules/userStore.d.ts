import { UserState } from "../interface/index";
export declare const useCommonUserStore: import("pinia").StoreDefinition<"userStore", UserState, {}, {
    setMenuList(menuList: any): void;
    setToken(token: string): void;
    setUserInfo(userInfo: UserState["userInfo"]): void;
    setTenantInfo(tenantInfo: UserState["tenantInfo"]): void;
    clearUserData(): void;
}>;
