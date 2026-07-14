<template>
  <div>
    <vz-card title="历史信息" v-if="drawerProps.projectId" :collapse="false">
      <recoveryAdjustTrialIndex
        :selected-id="selectedId"
        @open-drawer="openDrawer"
        :filter-data="{ projectId: drawerProps.projectId }"
      ></recoveryAdjustTrialIndex>
    </vz-card>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="140px"
        label-suffix=" :"
        scroll-to-error
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="调解/判决" prop="adjustTrialType">
              <el-select v-model="form!.adjustTrialType" clearable class="w100">
                <el-option label="调解" value="BEFORE_LITIGATION_MEDIATION"></el-option>
                <!-- <el-option label="诉中调解" value="MIDDLE_LITIGATION_MEDIATION"></el-option> -->
                <el-option label="判决" value="JUDG"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="调解/判决日期" prop="adjustTrialDate">
              <el-date-picker v-model="form!.adjustTrialDate" type="date" placeholder="请选择调解/判决日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="判决案号" prop="adjustCode">
              <el-input v-model="form!.adjustCode" placeholder="请输入判决案号" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="代偿金额" prop="compensatoryAmount" :inline-message="true">
              <vz-input-unit
                v-model="form!.compensatoryAmount"
                :value="form!.compensatoryAmount"
                placeholder="请输入代偿金额"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="利息" prop="interest" :inline-message="true">
              <vz-input-unit
                v-model="form!.interest"
                :value="form!.interest"
                placeholder="请输入利息"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="违约金" prop="backOutAmount" :inline-message="true">
              <vz-input-unit
                v-model="form!.backOutAmount"
                :value="form!.backOutAmount"
                placeholder="请输入违约金"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="其他费用" prop="otherAmount" :inline-message="true">
              <vz-input-unit
                v-model="form!.otherAmount"
                :value="form!.otherAmount"
                placeholder="请输入其他费用"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="特殊情况说明" prop="specialRemarks">
              <el-input
                type="textarea"
                :rows="5"
                v-model="form!.specialRemarks"
                placeholder="请输入特殊情况说明"
                clearable
                maxlength="2000"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="drawerProps.isView" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="recoveryAdjustTrialDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { RecoveryAdjustTrialRequest, AdjustTrialTypeEnum } from "@/api/modules/proceeding/recoveryAdjustTrial/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { recoveryAdjustTrial } from "@/api/modules/proceeding/recoveryAdjustTrial/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";
import { recoveryLitigation } from "@/api/modules/proceeding/recoveryLitigation/api";

import recoveryAdjustTrialIndex from "@/views/proceeding/recoveryAdjustTrial/index.vue";
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  adjustTrialType: [
    {
      required: true,
      message: "请选择调解/判决类型",
      trigger: "change"
    }
  ],
  litigationId: [{ required: true, message: "请输入诉讼id", trigger: "blur" }],
  compensatoryAmount: [{ required: true, message: "请输入代偿金额", trigger: "blur" }],
  interest: [{ required: true, message: "请输入利息", trigger: "blur" }],
  backOutAmount: [{ required: true, message: "请输入违约金", trigger: "blur" }],
  otherAmount: [{ required: true, message: "请输入其他费用", trigger: "blur" }],
  adjustTrialDate: [{ required: true, message: "请选择执行或调解日期", trigger: "blur" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<RecoveryAdjustTrialRequest>>({
  /** 调解或审判类型 before_ligation_mediation:诉前调解 middle_litigation_mediation：诉中调解 juge:判决 */
  adjustTrialType: AdjustTrialTypeEnum.BEFORE_LITIGATION_MEDIATION,
  /** 是否存量 是1 否0 */
  isStock: false,
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await recoveryAdjustTrial().findById(drawerProps.value.id);
  drawerProps.value.litigationId = data.litigationId;
  form.value = data;
};
//  查询诉讼记录带出最新一条记录中的四个金额
const getPetitionInfo = () => {
  if (drawerProps.value.projectId) {
    recoveryLitigation()
      .findByProjectId(drawerProps.value.projectId)
      .then((info: any) => {
        if (info && info.data) {
          form.value.compensatoryAmount = info.data.compensationAmount;
          form.value.interest = info.data.interest;
          form.value.backOutAmount = info.data.liquidatedDamages;
          form.value.otherAmount = info.data.otherFees;
        }
      });
  }
};
const selectedId = ref();
const ruleFormRef = ref<FormInstance>();
const openDrawer = (title: string, row: RecoveryAdjustTrialRequest) => {
  drawerProps.value.isView = false;
  selectedId.value = row.id;
  if ("取消" == title) {
    if (form.value.id == row.id) {
      form.value = {};
      selectedId.value = 0;
      getPetitionInfo();
    }
  } else {
    if ("查看" == title) {
      ruleFormRef.value?.clearValidate();
      drawerProps.value.isView = true;
    }
    form.value = row;
  }
};

// 保存数据（新增/编辑）

const fileRef = ref();
const handleSave = async () => {
  try {
    await ruleFormRef.value!.validate();
    if (fileRef.value.tableRulesRef) {
      await fileRef.value.tableRulesRef.validate();
    }
    form.value.litigationId = drawerProps.value.litigationId;
    form.value.projectId = drawerProps.value.projectId;
    form.value.lawFirmId = drawerProps.value.lawFirmId;
    form.value.fileInfoList = fileRef.value.submitForm;

    await recoveryAdjustTrial().add!(form.value);
    //  判断是不是查询页调用保存，如果是，则调用反写接口
    // if (drawerProps.value.isView) {
    //   recoveryAdjustTrial().writeBackProject(form.value.id);
    // }
    ElMessage.success({ message: `保存成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
  // ruleFormRef.value!.validate(async valid => {
  //   if (!valid) return;
  //   try {
  //     form.value.litigationId = drawerProps.value.litigationId;
  //     await drawerProps.value.api!(form.value);
  //     //  判断是不是查询页调用保存，如果是，则调用反写接口
  //     if (drawerProps.value.isView) {
  //       recoveryAdjustTrial().writeBackProject(form.value.id);
  //     }
  //     ElMessage.success({ message: `${drawerProps.value.title}调解或审判信息	成功！` });
  //     drawerProps.value.getTableList!();
  //     emit("closeDrawer");
  //   } catch (error) {
  //     console.log(error);
  //   }
  // });
};

// 提交数据（流程）
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      form.value.litigationId = drawerProps.value.litigationId;
      form.value.projectId = drawerProps.value.projectId;
      form.value.lawFirmId = drawerProps.value.lawFirmId;
      await recoveryAdjustTrial().submit!(form.value);
      ElMessage.success({ message: `提交成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

onMounted(() => {
  findById();
  getPetitionInfo();
});
defineExpose({
  handleSave,
  handleSubmit
});
</script>
