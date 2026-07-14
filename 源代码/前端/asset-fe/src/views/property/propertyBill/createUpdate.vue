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
          <el-form-item label="资产id" prop="propertyId">
            <el-input v-model="form!.propertyId" placeholder="请输入资产id" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="资产状态" prop="propertyState">
            <el-input v-model="form!.propertyState" placeholder="请输入资产状态" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="资产标签" prop="propertyTag">
            <el-input v-model="form!.propertyTag" placeholder="请输入资产标签" clearable></el-input>
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

<script setup lang="ts" name="propertyBillDrawer">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { PropertyBillRequest, PropertyBillDTO } from "@/api/modules/property/propertyBill/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { propertyBill } from "@/api/modules/property/propertyBill/api"; // api

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
  api?: (params: Partial<PropertyBillRequest>) => Promise<PropertyBillDTO>;
  getTableList?: () => void;
}

const drawerProps = ref<DrawerProps>({
  isView: false,
  showbtn: false,
  title: ""
});

const form = ref<Partial<PropertyBillRequest>>({
  id: undefined // 这只是一个后面没逗号的坑位
});

const handleAccept = (params: DrawerProps) => {
  drawerProps.value = params;
  findById();
};

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await propertyBill().findById(drawerProps.value.id);
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSave = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}资产和单据关联表成功！` });
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
      ElMessage.success({ message: `${drawerProps.value.title}资产和单据关联表成功！` });
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
