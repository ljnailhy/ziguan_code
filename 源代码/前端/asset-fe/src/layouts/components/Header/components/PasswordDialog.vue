<template>
  <div>
    <el-dialog v-model="dialogVisible" title="修改密码" width="500px" draggable>
      <el-form :model="form" ref="ruleFormRef" label-width="100" style="padding: 20px" :rules="rules" status-icon @submit.prevent>
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="form.oldPassword" type="password" placeholder="旧密码" show-password autocomplete="new-password">
          </el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            v-model="form.newPassword"
            type="password"
            placeholder="请输入6-16位包含大写字母、数字、特殊符号的新密码"
            show-password
            autocomplete="new-password"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input
            v-model="form.confirmPassword"
            type="password"
            placeholder="确认新密码"
            show-password
            autocomplete="new-password"
          >
          </el-input>
        </el-form-item>
        <!-- <el-form-item>
        <el-button type="primary" @click="onSubmit(ruleFormRef)" native-type="submit">确认</el-button>
        <el-button @click="cancelForm()">取消</el-button>
      </el-form-item> -->
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="onSubmit(ruleFormRef)">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { ElMessage } from "element-plus";
import { changePsw } from "@/api/modules/user/user";

const dialogVisible = ref(false);
const ruleFormRef = ref();

const form = reactive({
  oldPassword: "",
  newPassword: "",
  confirmPassword: ""
});
const openDialog = () => {
  dialogVisible.value = true;
};
const validatePass = (rule: any, value: any, callback: any) => {
  if (value !== form.newPassword) {
    callback(new Error("两次密码不一致!"));
  } else {
    callback();
  }
};
const validatePsw = (rule: any, value: any, callback: any) => {
  const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&.])[A-Za-z\d@$!%*?&.]{6,16}$/;

  if (!passwordRegex.test(value)) {
    callback(new Error("密码必须是6-16位包含大写字母、数字、特殊符号"));
  } else {
    callback();
  }
};
const rules = reactive<FormRules<typeof form>>({
  oldPassword: [{ required: true, message: "旧密码不能为空", trigger: "blur" }],
  newPassword: [
    { required: true, message: "新密码不能为空", trigger: "blur" },
    { min: 6, max: 16, message: "新密码不能小于6位", trigger: "blur" },
    { validator: validatePsw, trigger: "blur" }
  ],
  confirmPassword: [
    { required: true, message: "确认密码不能为空", trigger: "blur" },
    { min: 6, max: 16, message: "确认密码不能小于6位", trigger: "blur" },
    { validator: validatePsw, trigger: "blur" },
    { validator: validatePass, trigger: "blur" }
  ]
});
//修改密码
const onSubmit = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async valid => {
    if (valid) {
      const { code } = await changePsw({
        newPassword: form.newPassword,
        oldPassword: form.oldPassword
      });

      if (code == "0") {
        ElMessage.success("修改成功");
        // 清除缓存/token等
        localStorage.removeItem("Authorization");
        // 使用 reload 时，不需要调用 resetRoute() 重置路由
        window.location.reload();
      } else {
        ElMessage.error("修改失败，请重试");
      }
    }
  });
};
defineExpose({ openDialog });
</script>
<style lang="scss" scoped>
:deep(.el-dialog .el-dialog__body) {
  max-height: calc(90vh - 111px) !important;
  height: auto !important;
}
</style>
