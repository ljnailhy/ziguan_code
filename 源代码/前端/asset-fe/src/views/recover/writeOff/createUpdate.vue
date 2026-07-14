<template>
  <div>
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
            <el-form-item label="项目名称" prop="projectName">
              <vz-project-info
                v-model="form!.projectId"
                :default-value="form!.projectId"
                placeholder="项目名称"
                :filter-data="{
                  isWriteOff: false,
                  projectStateList: projectStateList
                }"
                @handle-ok="handleOk"
                select-type="radio"
              ></vz-project-info>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="代偿金额" prop="compensationAmount" :inline-message="true">
              <vz-input-unit
                v-model="form!.compensationAmount"
                :value="form!.compensationAmount"
                placeholder="请输入代偿金额"
                text="元"
                :disabled="true"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="代偿时间" prop="compensationDate">
              <el-date-picker
                v-model="form!.compensationDate"
                type="date"
                placeholder="请选择代偿时间"
                class="w100"
                :disabled="true"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="核销金额" prop="writeDffAmount" :inline-message="true">
              <vz-input-unit
                v-model="form!.writeDffAmount"
                :value="form!.writeDffAmount"
                placeholder="请输入核销金额"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="核销时间" prop="writeOffDate">
              <el-date-picker v-model="form!.writeOffDate" type="date" placeholder="请选择核销时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="核销项目分类" prop="writeOffClassification">
              <el-select v-model="form!.writeOffClassification" clearable class="w100">
                <el-option label="A" value="A"></el-option>
                <el-option label="B" value="B"></el-option>
                <el-option label="C" value="C"></el-option>
                <el-option label="D" value="D"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="累计回款金额" prop="totalCollectionAmount" :inline-message="true">
              <vz-input-unit
                v-model="form!.totalCollectionAmount"
                :value="form!.totalCollectionAmount"
                placeholder="请输入累计回款金额"
                :disabled="true"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="回款情况说明" prop="remarks">
              <el-input
                v-model="form!.remarks"
                type="textarea"
                show-word-limit
                :rows="5"
                placeholder="请输入回款情况说明"
                clearable
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="反担保措施">
      <form-table :table-data="reveInfoList" readonly></form-table>
    </vz-card>
    <vz-card title="其他财产信息">
      <form-table :table-data="propertyInfoList" readonly></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="drawerProps.isView" :query-data="{ doType: 'WRITE_OFF', doId: form!.id }"></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="writeOffDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { WriteOffRequest, WriteOffClassificationEnum } from "@/api/modules/recovery/writeOff/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { writeOff } from "@/api/modules/recovery/writeOff/api";
import { securityTypeNatureOptions } from "@/api/modules/recovery/projectInfo/interface";
import VzProjectInfo from "@/components/source/vzProjectInfo.vue";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api";
import { useBaseStore } from "@/stores/modules/baseInfo";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules: any = reactive({
  projectName: [{ required: true, message: "请输入项目名称", trigger: "blur" }],
  writeOffDate: [{ required: true, message: "请选择核销时间", type: "date", trigger: "change" }],
  writeOffClassification: [{ required: true, message: "请选择核销项目分类", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }],
  writeDffAmount: [{ required: true, message: "核销金额不能为空", trigger: "change" }]
});
const propertyInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "reveMeasure",
      label: "其他财产线索",
      width: "150",
      isRequired: true,
      type: "text"
    },
    {
      prop: "debtRepaymentDate",
      label: "裁定以资抵债日期",
      width: "60",
      type: "date"
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额(元)",
      width: "60",
      text: "元",
      type: "money"
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "60",
      type: "boolean"
    },
    {
      prop: "disposeMoney",
      label: "处置回款金(元)",
      width: "60",
      text: "元",
      type: "money"
    }
  ]
});
const reveInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "securityWay",
      label: "担保方式",
      width: "110",
      isRequired: true,
      type: "select",
      dictType: "SECURITY_WAY"
    },
    {
      prop: "securityType",
      label: "担保类型",
      width: "110",
      isRequired: true,
      type: "select",
      enum: securityTypeNatureOptions
    },
    {
      prop: "reveName",
      label: "反担保人名称",
      width: "150",
      isRequired: true
    },
    {
      prop: "reveMeasure",
      label: "反担保措施",
      width: "200",
      type: "text"
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean"
    },
    {
      prop: "disposeMoney",
      label: "处置回款金额(元)",
      width: "160",
      text: "元",
      type: "money"
    }
  ]
});

const handleOk = async (obj: any) => {
  form.value = {
    ...form.value,
    projectId: obj.id,
    compensationAmount: obj.compensationMoney,
    compensationDate: obj.compensationDate,
    projectName: obj.projectName
  };
  const { data } = await projectInfo().findById(obj.id);
  if (data.revePropertyInfoRequest === null) {
    return;
  }
  form.value.totalCollectionAmount = data.totalCollectionAmount;
  form.value.writeDffAmount = data.writeDffAmount;
  if (data.revePropertyInfoRequest.length != 0) {
    reveInfoList.value.data = data.revePropertyInfoRequest.filter(res => res.billType === "REVE");
    propertyInfoList.value.data = data.revePropertyInfoRequest.filter(res => res.billType === "PROPERTY");
  }
};

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<WriteOffRequest>>({
  /** 核销项目分类 A:A B:B C:C D:D */
  writeOffClassification: WriteOffClassificationEnum.A,
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找

const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await writeOff().findById(drawerProps.value.id);

  reveInfoList.value.data = data.revePropertyInfoRequest && data.revePropertyInfoRequest.filter(res => res.billType === "REVE");
  propertyInfoList.value.data =
    data.revePropertyInfoRequest && data.revePropertyInfoRequest.filter(res => res.billType === "PROPERTY");
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const saveFun = async () => {
  const fileEl = fileRef.value?.tableRulesRef;
  try {
    await ruleFormRef.value!.validate();
    if (fileEl) {
      await fileEl.validate();
    }
  } catch (error) {
    return false;
  }
  ruleFormRef.value!.validate();
  form.value.fileRequests = fileRef.value.submitForm;
  return true;
};
const handleSave = async () => {
  const result = await saveFun();
  if (!result) return false;
  try {
    drawerProps.value.title === "新增" ? await writeOff().add(form.value) : await writeOff().update(form.value);
    ElMessage.success({ message: `${drawerProps.value.title}核销保存成功！` });
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
    await writeOff().submit(form.value);
    ElMessage.success({ message: `提交核销成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

const projectStateList = ref();
onMounted(() => {
  useBaseStore()
    .findEnumByName("PROJECT_STATE")
    .then(res => {
      projectStateList.value = res
        .filter(item => item.itemCode !== "PROJECT_STATE_01" && item.itemCode !== "PROJECT_STATE_15")
        .map(items => items.id);
    });
  findById();
});

defineExpose({
  handleSave,
  handleSubmit
});
</script>
