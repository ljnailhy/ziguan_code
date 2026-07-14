<template>
  <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" size="large">
    <el-form-item prop="account">
      <el-input v-model="loginForm.account" placeholder="用户名">
        <template #prefix>
          <el-icon class="el-input__icon">
            <user />
          </el-icon>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input v-model="loginForm.password" type="password" placeholder="密码" show-password>
        <template #prefix>
          <el-icon class="el-input__icon">
            <lock />
          </el-icon>
        </template>
      </el-input>
    </el-form-item>
    <div data-v-b1dc51ac="" class="font12 mt30 login-animation4 login-msg">
      * 温馨提示：建议使用谷歌、Microsoft Edge、版本 79.0.1072.62 及以上浏览器、360浏览器，并关闭兼容模式。
    </div>
  </el-form>
  <div class="login-btn">
    <el-button :icon="CircleClose" round size="large" @click="resetForm(loginFormRef)"> 重置 </el-button>
    <el-button :icon="UserFilled" round size="large" type="primary" :loading="loading" @click="login(loginFormRef)">
      登录
    </el-button>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
import { HOME_URL } from "@/config";
import { getTimeState } from "@/utils";
import { Login } from "@/api/interface";
import { ElNotification } from "element-plus";
import { loginApi, loginlog } from "@/api/modules/login";
import { useUserStore } from "@/stores/modules/user";
import { useTabsStore } from "@/stores/modules/tabs";
import { useKeepAliveStore } from "@/stores/modules/keepAlive";
import { initDynamicRouter } from "@/routers/modules/dynamicRouter";
import { CircleClose, UserFilled } from "@element-plus/icons-vue";
import type { ElForm } from "element-plus";
import { Session } from "@platform/vue-platform-ui";
import { queryMenu } from "@/api/modules/login";

const router = useRouter();
const userStore = useUserStore();
const tabsStore = useTabsStore();
const keepAliveStore = useKeepAliveStore();

type FormInstance = InstanceType<typeof ElForm>;
const loginFormRef = ref<FormInstance>();
const loginRules = reactive({
  account: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }]
});

const loading = ref(false);
const loginForm = reactive<Login.ReqLoginForm>({
  account: "",
  password: "",
  type: "login"
});
const queryMenuFun = async () => {
  const { data } = await queryMenu();
  userStore.setMenuList(data);
};

// login
const login = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async valid => {
    if (!valid) return;
    loading.value = true;
    try {
      // 1.执行登录接口
      const { data } = await loginApi(loginForm);
      // 获取当前时间
      let now = new Date();
      // 将当前时间增加8小时
      let time = now.getTime();
      time += 8 * 60 * 60 * 1000; // 8小时的毫秒数
      now.setTime(time);
      Session.set("token", `bearer ${data.access_token}`, now);

      // 存储 token 到浏览器缓存
      localStorage.setItem("Authorization", `bearer ${data.access_token}`);
      userStore.setToken(data.access_token);
      userStore.setUserInfo(data.loginUser);

      // 2.添加动态路由
      await loginlog();
      await initDynamicRouter();
      await queryMenuFun();

      // 3.清空 tabs、keepAlive 数据
      tabsStore.setTabs([]);
      keepAliveStore.setKeepAliveName([]);

      // 4.跳转到首页

      router.push(HOME_URL);
      window.location.reload();

      ElNotification({
        title: getTimeState(),
        message: "欢迎登录，湖南担保集团资产管理系统",
        type: "success",
        duration: 3000
      });
    } finally {
      loading.value = false;
    }
  });
};

// resetForm
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};

onMounted(() => {
  // 监听 enter 事件（调用登录）
  document.onkeydown = (e: KeyboardEvent) => {
    e = (window.event as KeyboardEvent) || e;
    if (e.code === "Enter" || e.code === "enter" || e.code === "NumpadEnter") {
      if (loading.value) return;
      login(loginFormRef.value);
    }
  };
});
</script>

<style scoped lang="scss">
@import "../index.scss";
.login-msg {
  font-size: 12px;
  color: var(--el-text-color-placeholder);
}
</style>
