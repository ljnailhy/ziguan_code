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
          <el-form-item label="项目移交id" prop="transferId">
            <el-input v-model="form!.transferId" placeholder="请输入项目移交id" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="项目id" prop="projectId">
            <el-input v-model="form!.projectId" placeholder="请输入项目id" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="代偿金额" prop="compensationMoney">
            <vz-input-unit
              v-model:value="form!.compensationMoney"
              placeholder="请输入代偿金额"
              text="元"
              clearable
            ></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="代偿时间" prop="compensationDate">
            <el-date-picker v-model="form!.compensationDate" type="date" placeholder="请选择代偿时间" class="w100" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="保全经理" prop="manage">
            <el-input v-model="form!.manage" placeholder="请输入保全经理" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="律所" prop="lawFirmId">
            <el-input v-model="form!.lawFirmId" placeholder="请输入律所" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="关联合同" prop="contractId">
            <el-input v-model="form!.contractId" placeholder="请输入关联合同" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="回款金额" prop="collectionAmount">
            <vz-input-unit
              v-model:value="form!.collectionAmount"
              placeholder="请输入回款金额"
              text="元"
              clearable
            ></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="剩余代偿金额" prop="residueCompensation">
            <vz-input-unit
              v-model:value="form!.residueCompensation"
              placeholder="请输入剩余代偿金额"
              text="元"
              clearable
            ></vz-input-unit>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="projectTransferDetailedDrawer">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { ProjectTransferDetailedRequest, ProjectTransferDetailedDTO } from "@/api/modules/projectTransferDetailed/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { projectTransferDetailed } from "@/api/modules/projectTransferDetailed/api"; // api

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
  api?: (params: Partial<ProjectTransferDetailedRequest>) => Promise<ProjectTransferDetailedDTO>;
  getTableList?: () => void;
}

const drawerProps = ref<DrawerProps>({
  isView: false,
  showbtn: false,
  title: ""
});

const form = ref<Partial<ProjectTransferDetailedRequest>>({
  id: undefined // 这只是一个后面没逗号的坑位
});

const handleAccept = (params: DrawerProps) => {
  drawerProps.value = params;
  findById();
};

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await projectTransferDetailed().findById(drawerProps.value.id);
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSave = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}成功！` });
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
      ElMessage.success({ message: `${drawerProps.value.title}成功！` });
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
