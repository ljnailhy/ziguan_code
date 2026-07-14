<template>
  <div>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="100px"
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="对象类型" prop="doType">
            <el-input v-model="form!.doType" placeholder="请输入对象类型" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="对象id" prop="doId">
            <el-input v-model="form!.doId" placeholder="请输入对象id" clearable></el-input>
          </el-form-item>
        </el-col> -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="类型" prop="type">
              <vz-select dict-type="WORK_TYPE" v-model="form!.type" :dict-value="form!.type" style="width: 100%"></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="工作时间" prop="workDate">
              <el-date-picker v-model="form!.workDate" type="date" placeholder="请选择工作时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="工作内容" prop="workContent">
              <el-input
                type="textarea"
                :rows="5"
                v-model="form!.workContent"
                placeholder="请输入工作内容"
                maxlength="2000"
                show-word-limit
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="是否删除" prop="isDelete">
            <el-switch v-model="form!.isDelete" />
          </el-form-item>
        </el-col> -->
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="drawerProps.isView" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="workRegisterDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { WorkRegisterRequest } from "@/api/modules/compensatory/workRegister/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { workRegister } from "@/api/modules/compensatory/workRegister/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const initParamsJson = localStorage.getItem("initParams");
const initParam: any = ref(initParamsJson ? JSON.parse(initParamsJson) : {});
const rules = reactive({
  type: [{ required: true, message: "请选择工作类型", trigger: "change" }],
  workDate: [{ required: true, message: "请选择工作时间", trigger: "blur" }],
  workContent: [{ required: true, message: "请选择工作内容", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<WorkRegisterRequest>>({
  /** 是否删除 */
  isDelete: false,
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await workRegister().findById(drawerProps.value.id);
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const handleSave = async () => {
  try {
    await ruleFormRef.value!.validate();
    if (fileRef.value?.tableRulesRef) {
      await fileRef.value?.tableRulesRef.validate();
    }
    form.value.doId = initParam.value?.doId;
    form.value.doType = initParam.value?.doType;
    form.value.fileInfoList = fileRef.value.submitForm;
    await drawerProps.value.api!(form.value);
    ElMessage.success({ message: `保存工作登记成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

// 提交数据（流程）
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}工作登记成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

onMounted(() => {
  findById();
});
defineExpose({
  handleSave,
  handleSubmit
});
</script>
