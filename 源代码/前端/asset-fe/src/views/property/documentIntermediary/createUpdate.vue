<template>
  <div>
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
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="对象Id" prop="doId">
            <el-input v-model="form!.doId" placeholder="请输入对象Id" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="对象类型" prop="doType">
            <el-input v-model="form!.doType" placeholder="请输入对象类型" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="中介名称" prop="intermediaryId">
            <el-input v-model="form!.intermediaryId" placeholder="请输入中介名称" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="是否成交" prop="isDeal">
            <el-switch v-model="form!.isDeal" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="佣金" prop="commission" :inline-message="true">
            <vz-input-unit v-model:value="form!.commission" placeholder="请输入佣金" text="元" clearable></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form!.remark" placeholder="请输入备注" clearable></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="documentIntermediaryDrawer">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { DocumentIntermediaryRequest, DocumentIntermediaryDTO } from "@/api/modules/property/documentIntermediary/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { documentIntermediary } from "@/api/modules/property/documentIntermediary/api"; // api

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

interface DrawerProps {
  title: string;
  isView: boolean;
  showbtn?: boolean;
  id?: number;
  api?: (params: Partial<DocumentIntermediaryRequest>) => Promise<DocumentIntermediaryDTO>;
  getTableList?: () => void;
}

const drawerProps = ref<DrawerProps>({
  isView: false,
  showbtn: false,
  title: ""
});

const form = ref<Partial<DocumentIntermediaryRequest>>({
  /** 是否成交 */
  isDeal: false,
  id: undefined // 这只是一个后面没逗号的坑位
});

const handleAccept = (params: DrawerProps) => {
  drawerProps.value = params;
  findById();
};

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await documentIntermediary().findById(drawerProps.value.id);
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSave = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}单据和中介关联表成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

// 提交数据（流程）
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}单据和中介关联表成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};
defineExpose({
  handleSave,
  handleSubmit,
  handleAccept
});
</script>
