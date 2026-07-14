import { defineStore } from "@platform/vue-platform-ui";
import { UserState } from "@/stores/interface";
import piniaPersistConfig from "@/stores/helper/persist";

export const useUserStore = defineStore({
  id: "vz-userInfo",
  state: (): UserState => ({
    token: "",
    userInfo: { name: "" },
    menuList: []
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
    }
  },
  persist: piniaPersistConfig("vz-userInfo")
});
