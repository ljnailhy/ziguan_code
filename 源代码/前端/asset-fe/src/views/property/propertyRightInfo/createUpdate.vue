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
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb20">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form!.remark" placeholder="请输入备注" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb20">
          <el-form-item label="产权证号" prop="propertyCode">
            <el-input v-model="form!.propertyCode" placeholder="请输入产权证号" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb20">
          <el-form-item label="权证到期日" prop="propertyEndDate">
            <el-date-picker v-model="form!.propertyEndDate" type="date" placeholder="请选择权证到期日" class="w100" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb20">
          <el-form-item label="产权人名称" prop="propertyOwner">
            <el-input v-model="form!.propertyOwner" placeholder="请输入产权人名称" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb20">
          <el-form-item label="面积" prop="area">
            <vz-input-unit v-model:value="form!.area" placeholder="请输入面积" text="元" clearable></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb20">
          <el-form-item label="资产用途" prop="assetUse">
            <el-input v-model="form!.assetUse" placeholder="请输入资产用途" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb20">
          <el-form-item label="详细地址" prop="address">
            <el-input v-model="form!.address" placeholder="请输入详细地址" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" class="mb20">
          <el-form-item label="资产过户日期" prop="propertyTransferOwnership">
            <el-date-picker v-model="form!.propertyTransferOwnership" type="date" placeholder="请选择资产过户日期" class="w100" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="propertyRightInfoDrawer">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { PropertyRightInfoRequest, PropertyRightInfoDTO } from "@/api/modules/property/propertyRightInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { propertyRightInfo } from "@/api/modules/property/propertyRightInfo/api"; // api

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
  api?: (params: Partial<PropertyRightInfoRequest>) => Promise<PropertyRightInfoDTO>;
  getTableList?: () => void;
}

const drawerProps = ref<DrawerProps>({
  isView: false,
  showbtn: false,
  title: ""
});

const form = ref<Partial<PropertyRightInfoRequest>>({
  id: undefined // 这只是一个后面没逗号的坑位
});

const handleAccept = (params: DrawerProps) => {
  drawerProps.value = params;
  findById();
};

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await propertyRightInfo().findById(drawerProps.value.id);
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSave = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}产权信息表成功！` });
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
      ElMessage.success({ message: `${drawerProps.value.title}产权信息表成功！` });
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
