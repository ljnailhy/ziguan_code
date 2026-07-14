import { ref } from "vue";
import { useRouter } from "vue-router";
import { ElNotification } from "element-plus";
import { useCommonUserStore } from "../stores/modules/userStore";
// import { useTabsStore } from "@/stores/modules/tabs";
import { useKeepAliveStore } from "../stores/modules/keepAlive";
// import { HOME_URL } from "@/config";
import { useTenantApi } from "../api/system/tenant";
import {
  loginLog,
  queryMenu,
  getCurrentUserInfo,
} from "../api/modules/login/api";
import { getTimeState } from "../utils/index";
// import { initDynamicRouter } from "@/routers/modules/dynamicRouter";

export function useAuthLogin() {
  const router = useRouter();
  const userStore = useCommonUserStore();
  // const tabsStore = useTabsStore();
  const keepAliveStore = useKeepAliveStore();
  const loading = ref(false);
  const isShowForm = ref(true);
  const chooseData = ref<any>(null);

  //获取菜单
  const queryMenuFun = async () => {
    try {
      const { data } = await queryMenu();
      userStore.setMenuList(data);
    } catch (error) {
      console.error("获取菜单失败:", error);
    }
  };

  //获取用户信息
  const getUserInfo = async () => {
    try {
      const { data } = await getCurrentUserInfo();
      userStore.setUserInfo(data);
    } catch (error) {
      console.error("获取用户信息失败:", error);
    }
  };
  //获取租户
  const getTenant = async (tenantId: number) => {
    try {
      const res = await useTenantApi().findById(tenantId);
      localStorage.setItem("tenant", JSON.stringify(res.data));
      userStore.setTenantInfo(res.data);
    } catch (error) {
      console.error("获取租户信息失败:", error);
    }
  };
  /**
   * 处理登录成功后的逻辑
   * @param data 登录返回的数据
   */
  const handleLoginSuccess = async (
    data: any,
    initDynamicRouter: Function,
    refresh?: boolean
  ) => {
    if (!data.access_token) {
      loading.value = false;
      isShowForm.value = false;
      chooseData.value = data;
      return;
    }

    // 存储 token 和 tenantId
    localStorage.setItem("Authorization", `bearer ${data.access_token}`);
    if (data.loginUser?.tenantId) {
      localStorage.setItem(
        "tenant",
        JSON.stringify({ id: data.loginUser.tenantId })
      );
      // 获取租户信息
      getTenant(data.loginUser?.tenantId);
    }

    userStore.setToken(data.access_token);
    await getUserInfo();

    // 初始化动态路由

    await initDynamicRouter();
    await queryMenuFun();

    // 清空 tabs 和 keepAlive 缓存
    // tabsStore.setTabs([]);
    keepAliveStore.setKeepAliveName([]);
    if (refresh) {
      router.push("/home");
      window.location.reload();
    }
    // 显示欢迎通知
    setTimeout(() => {
      ElNotification({
        title: getTimeState(),
        message: `${data.loginUser?.username} 您好！欢迎登录!`,
        type: "success",
        duration: 3000,
      });
      loginLog();
    }, 1000);
  };

  return {
    loading,
    isShowForm,
    chooseData,
    handleLoginSuccess,
  };
}
