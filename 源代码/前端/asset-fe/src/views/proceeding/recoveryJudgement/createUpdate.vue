<template>
  <div>
    <vz-card title="历史信息" v-if="drawerProps.projectId" :collapse="false">
      <recoveryJudgementIndex
        :selected-id="selectedId"
        @open-drawer="openDrawer"
        :filter-data="{ projectId: drawerProps.projectId, litigationTypeList: drawerProps.litigationTypeList }"
      ></recoveryJudgementIndex>
    </vz-card>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="120px"
        label-suffix=" :"
        scroll-to-error
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.litigationType != 'REGISTER'">
            <el-form-item label="立案案号" prop="registerId">
              <vzRegister
                :default-value="form!.registerId"
                :filter-data="{
                  projectId: drawerProps.projectId || form.projectId,
                  litigationType: 'REGISTER',
                  registerType: form.litigationType,
                  flowState: 'completed'
                }"
                @handle-ok="registerOk"
              ></vzRegister>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="立案法院" prop="filingCourtName">
              <el-input v-model="form!.filingCourtName" placeholder="请输入立案法院" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.litigationType != 'REGISTER'">
            <el-form-item label="类型" prop="litigationType">
              <el-select v-model="form!.litigationType" clearable class="w100">
                <!-- <el-option label="立案" value="REGISTER"></el-option> -->
                <el-option label="一审" value="FIRST_INSTANCE"></el-option>
                <el-option label="二审" value="SECOND_INSTANCE"></el-option>
                <el-option label="再审" value="RETRIAL"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.litigationType == 'REGISTER'">
            <el-form-item label="立案类型" prop="registerType">
              <el-select v-model="form!.registerType" clearable class="w100">
                <el-option label="一审立案" value="FIRST_INSTANCE"></el-option>
                <el-option label="二审立案" value="SECOND_INSTANCE"></el-option>
                <el-option label="再审立案" value="RETRIAL"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="法官" prop="judgeName">
              <el-input v-model="form!.judgeName" placeholder="请输入法官" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="法官联系方式" prop="judgePhone">
              <el-input v-model="form!.judgePhone" placeholder="请输入法官联系方式" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="立案时间" prop="fillingDate">
              <el-date-picker v-model="form!.fillingDate" type="date" placeholder="请选择立案时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.litigationType == 'REGISTER'">
            <el-form-item label="案号" prop="fillingCode">
              <el-input v-model="form!.fillingCode" placeholder="请输入案号" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="8"
            class="mb20"
            v-if="form!.litigationType != LitigationTypeEnum.REGISTER"
          >
            <el-form-item label="判决日期" prop="judgeDate">
              <el-date-picker v-model="form!.judgeDate" type="date" placeholder="请选择判决日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="8"
            class="mb20"
            v-if="form!.litigationType != LitigationTypeEnum.REGISTER"
          >
            <el-form-item label="开庭时间" prop="courtSessionDate">
              <el-date-picker v-model="form!.courtSessionDate" type="date" placeholder="请选择开庭时间" class="w100" />
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="诉讼id" prop="litigationId">
                <el-input v-model="form!.litigationId" placeholder="请输入诉讼id" clearable></el-input>
              </el-form-item>
            </el-col> -->
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

<script setup lang="ts" name="recoveryJudgementDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { RecoveryJudgementRequest, LitigationTypeEnum } from "@/api/modules/proceeding/recoveryJudgement/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { recoveryJudgement } from "@/api/modules/proceeding/recoveryJudgement/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";

import recoveryJudgementIndex from "@/views/proceeding/recoveryJudgement/index.vue";
import vzRegister from "@/components/source/vzRegister.vue";
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  isStock: [{ required: true, message: "请选择是否存量 是1 否0", trigger: "change" }],
  filingCourtName: [{ required: true, message: "请输入立案法院", trigger: "blur" }],
  registerType: [{ required: true, message: "请输入立案类型", trigger: "blur" }],
  fillingDate: [{ required: true, message: "请输入立案时间", trigger: "blur" }],
  fillingCode: [{ required: true, message: "请输入立案案号", trigger: "blur" }],
  registerId: [{ required: true, message: "请输入立案法院", trigger: "blur" }],
  litigationType: [{ required: true, message: "请选择类型", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<RecoveryJudgementRequest>>({
  /** 是否存量 是1 否0 */
  isStock: false,
  /** 类型 first_instance:一审 second_instance 二审 retrial:再审 */
  litigationType: LitigationTypeEnum.FIRST_INSTANCE,
  id: undefined // 这只是一个后面没逗号的坑位
});
const registerOk = (row: EmptyObjectType) => {
  form!.value.registerId = row.id;
  form!.value.filingCourtName = row.filingCourtName;
  form!.value.fillingDate = row.fillingDate;
  form!.value.fillingCode = row.fillingCode;
  form!.value.judgePhone = row.judgePhone;
  form!.value.judgeName = row.judgeName;
};
// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await recoveryJudgement().findById(drawerProps.value.id);
  drawerProps.value.litigationId = data.litigationId;
  form.value = data;
};

const selectedId = ref();
const ruleFormRef = ref<FormInstance>();
const openDrawer = (title: string, row: RecoveryJudgementRequest) => {
  drawerProps.value.isView = false;
  selectedId.value = row.id;
  if ("取消" == title) {
    if (form.value.id == row.id) {
      form.value = {};
      if (drawerProps.value.litigationType && "REGISTER" == drawerProps.value.litigationType) {
        form!.value.litigationType = drawerProps.value.litigationType;
      }
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
  form.value.litigationId = drawerProps.value.litigationId;
  form.value.projectId = drawerProps.value.projectId;
  form.value.lawFirmId = drawerProps.value.lawFirmId;
  form.value.fileInfoList = fileRef.value.submitForm;
  return true;
};

const handleSave = async () => {
  const result = await saveFun();
  if (!result) return false;
  try {
    drawerProps.value.title == "新增"
      ? await recoveryJudgement().add!(form.value)
      : await recoveryJudgement().update!(form.value);
    //  判断是不是查询页调用保存，如果是，则调用反写接口
    // if (drawerProps.value.isView) {
    //   recoveryJudgement().writeBackProject(form.value.id);
    // }
    ElMessage.success({ message: `保存成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

// 提交数据（流程）
const handleSubmit = async () => {
  const result = await saveFun();
  if (!result) return false;
  try {
    await recoveryJudgement().submit!(form.value);
    //  判断是不是查询页调用保存，如果是，则调用反写接口
    // if (drawerProps.value.isView) {
    //   recoveryJudgement().writeBackProject(form.value.id);
    // }
    ElMessage.success({ message: `提交成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

onMounted(() => {
  if (drawerProps.value.litigationType && "REGISTER" == drawerProps.value.litigationType) {
    form!.value.litigationType = drawerProps.value.litigationType;
  }
  findById();
});
defineExpose({
  handleSave,
  handleSubmit
});
</script>
