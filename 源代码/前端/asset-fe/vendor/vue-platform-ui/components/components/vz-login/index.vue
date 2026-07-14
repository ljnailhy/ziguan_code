<template>
  <div>
    <el-form
      ref="loginFormRef"
      :model="loginForm"
      :rules="loginRules"
      v-if="isShowForm"
      size="large"
    >
      <el-form-item prop="account">
        <el-input
          v-model="loginForm.account"
          :prefix-icon="User"
          placeholder="请输入用户名"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          :prefix-icon="Lock"
          type="password"
          placeholder="请输入密码"
          show-password
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="captcha">
        <el-input
          v-model="loginForm.captcha"
          :prefix-icon="WarnTriangleFilled"
          placeholder="请输入验证码"
        >
          <template #append>
            <el-image
              style="cursor: pointer; height: 38px; width: 100%"
              :src="captcha?.captchaImage"
              @click="getCaptcha()"
            ></el-image>
          </template>
        </el-input>
      </el-form-item>
      <div class="font12 mt30 login-msg">
        * 温馨提示：建议使用谷歌、Microsoft Edge、版本 79.0.1072.62
        及以上浏览器、360浏览器，并关闭兼容模式。
      </div>
    </el-form>
    <div class="login-btn" v-if="isShowForm">
      <el-button
        :icon="CircleClose"
        round
        size="large"
        @click="resetForm(loginFormRef)"
      >
        重置
      </el-button>
      <el-button
        :icon="UserFilled"
        round
        size="large"
        type="primary"
        :loading="loading"
        @click="login(loginFormRef)"
      >
        登录
      </el-button>
    </div>
    <el-card shadow="never" v-else>
      <template #header>
        <div class="flex-between">
          请选租户进入 <el-link type="primary" @click="back">返回</el-link>
        </div>
      </template>
      <el-radio-group v-model="chooseModel" @change="confirmLogin">
        <el-radio
          size="default"
          style="margin-bottom: 10px"
          :value="item.id"
          :label="item.id"
          v-for="item in chooseData"
          :key="item.id"
          border
        >
          {{ item.tenantName }}
        </el-radio>
      </el-radio-group>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import CryptoJS, { enc, PBKDF2 } from "crypto-js";
import { Login } from "../../api/interface";
import { loginApi, generateCaptcha } from "../../api/modules/login/api";
import {
  CircleClose,
  UserFilled,
  User,
  Lock,
  WarnTriangleFilled,
} from "@element-plus/icons-vue";
import type { ElForm } from "element-plus";
import { useAuthLogin } from "../../hooks/useAuthLogin";
import { generateAesKey } from "../../utils/crypto";
type Props = {
  initDynamicRouter: () => void;
};

const props = withDefaults(defineProps<Props>(), {});

const { loading, isShowForm, chooseData, handleLoginSuccess } = useAuthLogin();

// const router = useRouter();

type FormInstance = InstanceType<typeof ElForm>;
const loginFormRef = ref<FormInstance>();
const loginRules = reactive({
  account: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  captcha: [{ required: true, message: "请输入验证码", trigger: "blur" }],
});

const chooseModel = ref<number>(0);
const captcha = ref();
const loginForm = reactive<Login.ReqLoginForm>({
  account: "",
  password: "",
  captcha: "",
  captchaKey: "",
  terminal: "pc",
  type: "login",
});

const back = () => {
  isShowForm.value = true;
};

// login
const login = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (!valid) return;
    await loginFun();
  });
};

const loginFun = async (tenantId?: any) => {
  if (tenantId) {
    localStorage.setItem("tenant", JSON.stringify({ id: tenantId }));
  }

  loading.value = true;
  try {
    // 1.执行登录接口
    const { data } = await loginApi(loginForm);
    isShowForm.value = true;
    await handleLoginSuccess(data, props.initDynamicRouter, true);
  } catch (error: any) {
    if (error.code === 100 && error.data) {
      isShowForm.value = false;
      chooseData.value = error.data;
    }
    if (error.code === 101) {
      getCaptcha();
    }
  } finally {
    loading.value = false;
  }
};

const confirmLogin = (val) => {
  const tenantId = val ? val.toString() : "";
  loginFun(tenantId);
};

// resetForm
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};

//生成derivedKey
const STATIC_SALT =
  import.meta.env.VITE_CRYPTO_SALT || "8710FB89B0ECD98F5A98E45D3BFB549F";
const getDerivedKey = async () => {
  const aesKey = generateAesKey();
  // 模拟后端的PBKDF2密钥派生
  const salt = enc.Utf8.parse(STATIC_SALT); // 需要与后端保持一致
  const iterations = 65536; // 需要与后端保持一致
  const keySize = 256 / 32; // 256位 → 8 (32位 x 8 = 256位)

  const derivedKey = PBKDF2(aesKey, salt, {
    keySize: keySize,
    iterations: iterations,
    hasher: CryptoJS.algo.SHA256,
  });
  localStorage.setItem("aesKey", aesKey);
  localStorage.setItem("derivedKey", JSON.stringify(derivedKey));
};

const getCaptcha = async () => {
  try {
    const { data } = await generateCaptcha();
    captcha.value = data;
    loginForm.captchaKey = data.captchaKey;
  } catch (error: any) {
    console.log(error);
  }
};

onMounted(async () => {
  await getDerivedKey();
  await getCaptcha();
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

<style scope lang="scss">
@import "index.scss";
</style>
