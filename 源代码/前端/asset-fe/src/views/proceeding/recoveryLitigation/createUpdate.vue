<template>
  <div>
    <vz-card title="历史记录" :collapse="false">
      <recoveryLitigationIndex
        v-if="drawerProps.projectId"
        @open-drawer="openDrawer"
        :selected-id="selectedId"
        :filter-data="{ projectId: drawerProps.projectId }"
      ></recoveryLitigationIndex>
    </vz-card>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="100px"
        label-suffix=" :"
        scroll-to-error
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="项目名称" prop="projectId">
                <vzProjectInfo :default-value="form!.projectId" @handle-ok="projectHandleOk"></vzProjectInfo>
              </el-form-item>
            </el-col> -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="代偿金额" prop="compensationAmount" :inline-message="true">
              <vz-input-unit
                v-model="form!.compensationAmount"
                :value="form!.compensationAmount"
                placeholder="请输入代偿金额"
                text="元"
                :max="999999999999"
                :disabled="true"
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
            <el-form-item label="违约金" prop="liquidatedDamages" :inline-message="true">
              <vz-input-unit
                v-model="form!.liquidatedDamages"
                :value="form!.liquidatedDamages"
                placeholder="请输入违约金"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="其他费用" prop="otherFees" :inline-message="true">
              <vz-input-unit
                v-model="form!.otherFees"
                :value="form!.otherFees"
                placeholder="请输入其他费用"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remarks">
              <el-input
                type="textarea"
                :rows="5"
                v-model="form!.remarks"
                placeholder="请输入备注"
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

<script setup lang="ts" name="recoveryLitigationDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance, ElMessageBox } from "element-plus";
import { RecoveryLitigationRequest } from "@/api/modules/proceeding/recoveryLitigation/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { recoveryLitigation } from "@/api/modules/proceeding/recoveryLitigation/api"; // api
// import { recoveryJudgement } from "@/api/modules/proceeding/recoveryJudgement/api";
// import { recoveryAdjustTrial } from "@/api/modules/proceeding/recoveryAdjustTrial/api";
import { useDialogStore } from "@/stores/modules/dialogParams";

// import vzProjectInfo from "@/components/source/vzProjectInfo.vue";
import recoveryLitigationIndex from "@/views/proceeding/recoveryLitigation/index.vue";
//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  projectId: [{ required: !drawerProps.value.isView, message: "请输入项目名称", trigger: "change" }],
  interest: [{ required: !drawerProps.value.isView, message: "请输入利息", trigger: "change" }],
  liquidatedDamages: [{ required: !drawerProps.value.isView, message: "请输入违约金", trigger: "change" }],
  otherFees: [{ required: !drawerProps.value.isView, message: "请输入其他费用", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});
// const recoveryJudgementList = ref({
//   data: [],
//   header: [
//     {
//       prop: "litigationType",
//       label: "类型",
//       width: "150",
//       type: "enum",
//       enum: [
//         { label: "立案", value: "REGISTER" },
//         { label: "一审", value: "FIRST_INSTANCE" },
//         { label: "二审", value: "SECOND_INSTANCE" },
//         { label: "再审", value: "RETRIAL" }
//       ]
//     },
//     {
//       prop: "filingCourtName",
//       label: "法院",
//       width: "110",
//       type: "text"
//     },
//     {
//       prop: "fillingDate",
//       label: "立案时间",
//       width: "150",
//       isRequired: true,
//       type: "date"
//     },
//     {
//       prop: "fillingCode",
//       label: "案号",
//       width: "110",
//       type: "text"
//     }
//   ]
// });

// const recoveryAdjustTrialList = ref({
//   data: [],
//   header: [
//     {
//       prop: "adjustTrialType",
//       label: "调解或审判类型",
//       width: "150",
//       type: "enum",
//       enum: [
//         { label: "诉前调解", value: "BEFORE_LITIGATION_MEDIATION" },
//         { label: "诉中调解", value: "MIDDLE_LITIGATION_MEDIATION" },
//         { label: "判决", value: "JUDG" }
//       ]
//     },
//     {
//       prop: "adjustTrialDate",
//       label: "调解或审判日期",
//       width: "150",
//       isRequired: true,
//       type: "date"
//     },
//     {
//       prop: "adjustCode",
//       label: "判决案号",
//       width: "110",
//       type: "text"
//     }
//   ]
// });

// const projectHandleOk = (row: EmptyObjectType) => {
//   if (row) {
//     if (Array.isArray(row)) {
//       if (row.length == 1) {
//         form!.value.projectId = row[0].id;
//         form!.value.compensationAmount = row[0].compensationMoney;
//       }
//     } else {
//       form!.value.projectId = row.id;
//       form!.value.compensationAmount = row.compensationMoney;
//     }
//   }
// };
const form = ref<Partial<RecoveryLitigationRequest>>({
  /** 是否存量 是1 否0 */
  isStock: false,
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await recoveryLitigation().findById(drawerProps.value.id);
  form.value = data;

  //  查看时查询
  // if (drawerProps.value.isView) {
  //   recoveryJudgement()
  //     .findByLitigationId(drawerProps.value.id)
  //     .then((res: EmptyObjectType) => {
  //       if (res.code == 0) {
  //         recoveryJudgementList.value.data = res.data;
  //       }
  //     });
  //   recoveryAdjustTrial()
  //     .findByLitigationId(drawerProps.value.id)
  //     .then((res: EmptyObjectType) => {
  //       if (res.code == 0) {
  //         recoveryAdjustTrialList.value.data = res.data;
  //       }
  //     });
  // }
};
const selectedId = ref();
const ruleFormRef = ref<FormInstance>();
const openDrawer = (title: string, row: RecoveryLitigationRequest) => {
  drawerProps.value.isView = false;
  selectedId.value = row.id;
  if ("取消" == title) {
    if (form.value.id == row.id) {
      form.value = {};
      console.log(form!.value.id);
      form!.value.compensationAmount = drawerProps.value.compensationMoney;
      selectedId.value = 0;
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
const saveFun = async () => {
  try {
    await ruleFormRef.value!.validate();
    if (fileRef.value.tableRulesRef) {
      await fileRef.value.tableRulesRef.validate();
    }
  } catch (error) {
    return false;
  }
  form.value.projectId = drawerProps.value.projectId;
  form.value.lawFirmId = drawerProps.value.lawFirmId;
  form.value.fileInfoList = fileRef.value.submitForm;
  form!.value.compensationAmount = drawerProps.value.compensationMoney;
  return true;
};
const handleSave = async () => {
  const result = await saveFun();
  if (!result) return false;
  try {
    drawerProps.value.title == "新增"
      ? await recoveryLitigation().add!(form.value)
      : await recoveryLitigation().update!(form.value);
    ElMessage.success({ message: `保存诉状成功！` });
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
    ElMessageBox.confirm(`提交后不允许编辑或删除，确定要继续提交吗?`, "温馨提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
      draggable: true
    })
      .then(async () => {
        try {
          form.value.projectId = drawerProps.value.projectId;
          form.value.lawFirmId = drawerProps.value.lawFirmId;
          form.value.fileInfoList = fileRef.value.submitForm;
          form!.value.compensationAmount = drawerProps.value.compensationMoney;
          recoveryLitigation().submit(form.value);
          ElMessage.success({ message: `提交诉状成功！` });
          drawerProps.value.getTableList!();
          emit("closeDrawer");
        } catch (error) {
          console.log(error);
        }
      })
      .catch(() => {
        // cancel operation
      });
  });
};

onMounted(() => {
  form!.value.compensationAmount = drawerProps.value.compensationMoney;
  findById();
});
defineExpose({
  handleSave,
  handleSubmit
});
</script>
