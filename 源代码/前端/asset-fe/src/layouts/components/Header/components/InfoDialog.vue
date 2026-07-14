<template>
  <el-dialog v-model="dialogVisible" title="个人信息" width="600px" draggable>
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :hide-required-asterisk="userDisabled"
      :disabled="userDisabled"
      :model="row"
      :destroy-on-close="true"
    >
      <el-form-item label="用户头像" prop="avatar">
        <UploadImg v-model:image-url="row!.avatar" width="135px" height="135px" :file-size="10">
          <template #empty>
            <el-icon><Avatar /></el-icon>
            <span>请上传头像</span>
          </template>
          <template #tip> 头像大小不能超过 10M </template>
        </UploadImg>
      </el-form-item>
      <el-form-item label="用户昵称" prop="nickname">
        <el-input v-model="row!.nickname" placeholder="请填写用户昵称" clearable></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="row!.sex" placeholder="请选择性别" clearable>
          <el-option label="男" value="MALE" />
          <el-option label="女" value="FEMALE" />
          <el-option label="未知" value="UNKNOW" />
        </el-select>
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-date-picker v-model="row!.birthday" type="date" placeholder="请选择生日" value-format="x" />
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input v-model="row!.phone" placeholder="请填写手机号码" clearable></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="row!.email" placeholder="请填写邮箱" clearable></el-input>
      </el-form-item>

      <el-form-item label="个性签名" prop="signature">
        <el-input
          v-model="row!.signature"
          placeholder="编辑个签名，展示我的独特态度"
          type="textarea"
          :rows="5"
          maxlength="500"
          show-word-limit
          clearable
        ></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">{{ userDisabled ? "修改" : "保存" }}</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { changeUserInfo, getUserInfoById } from "@/api/modules/user/user";
import UploadImg from "@/components/Upload/Img.vue";
import { useUserStore } from "@/stores/modules/user";

const rules = reactive({
  // avatar: [{ required: true, message: "请上传用户头像" }],
  nickname: [{ required: true, message: "请填写用户昵称" }],
  // birthday: [{ required: true, message: "请填写生日" }],
  sex: [{ required: true, message: "请选择性别" }]
  // phone: [{ required: true, message: "请填写手机号码" }],
  // email: [{ required: true, message: "请填写邮箱" }],
  // signature: [{ required: true, message: "请填写个性签名" }]
});

const userDisabled = ref(true);
const row = ref<any>({
  avatar: "", //头像
  nickname: "", //昵称
  sex: "",
  birthday: "",
  phone: "",
  email: "",
  signature: ""
});
const dialogVisible = ref(false);
const openDialog = () => {
  getCurrentUser();
  dialogVisible.value = true;
};
const userStore: any = useUserStore();
const getCurrentUser = () => {
  getUserInfoById(userStore.userInfo.id).then(res => {
    row.value = res.data;
  });
};
const ruleFormRef = ref<FormInstance>();
const handleSubmit = () => {
  if (userDisabled.value) {
    userDisabled.value = false;
    return;
  }
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await changeUserInfo(row.value);
      ElMessage.success("修改成功");
      userDisabled.value = true;
    } catch (error) {
      console.log(error);
    }
  });
};
defineExpose({ openDialog });
</script>
