import { defineStore } from "pinia";
import { UserState } from "../interface/index";
import piniaPersistConfig from "../helper/persist";

export const useCommonUserStore = defineStore({
  id: "userStore",
  state: (): UserState => ({
    token: "",
    userInfo: { name: "" },
    tenantInfo: {},
    menuList: [],
  }),
  getters: {},
  actions: {
    // Set menuList
    setMenuList(menuList: any) {
      this.menuList = menuList;
    },
    // Set Token
    setToken(token: string) {
      this.token = token;
    },
    // Set setUserInfo
    setUserInfo(userInfo: UserState["userInfo"]) {
      this.userInfo = userInfo;
    },
    // Set setTenantInfo
    setTenantInfo(tenantInfo: UserState["tenantInfo"]) {
      this.tenantInfo = tenantInfo;
    },
    clearUserData() {
      this.token = "";
      this.userInfo = { name: "" };
      this.tenantInfo = {};
      this.menuList = [];
    },
  },
  persist: piniaPersistConfig("userStore"),
});
