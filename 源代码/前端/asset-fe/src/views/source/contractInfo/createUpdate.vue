<template>
  <div>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="120px"
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        scroll-to-error
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="合同名称" prop="contractName">
              <el-input v-model="form!.contractName" placeholder="请输入合同名称" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="合同类型" prop="contractType">
              <vz-select
                dict-type="CONTRACT_TYPE"
                v-model="form!.contractType"
                :dict-value="form!.contractType"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="合同金额" prop="contractMoney">
              <vz-input-unit
                v-model="form!.contractMoney"
                :value="form!.contractMoney"
                placeholder="请输入合同金额"
                :max="999999999999"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="签约日期" prop="signingDate">
              <el-date-picker v-model="form!.signingDate" type="date" placeholder="请选择签约日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="开始时间" prop="startDate">
              <el-date-picker v-model="form!.startDate" type="date" placeholder="请选择开始时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="截止时间" prop="endDate">
              <el-date-picker v-model="form!.endDate" type="date" placeholder="请选择截止时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="CONTRACT_TYPE_001 === form!.contractType">
            <el-form-item label="代理方式" :prop="CONTRACT_TYPE_001 == form!.contractType ? 'agentWay' : ''">
              <vz-select
                dict-type="AGENT_WAY"
                v-model="form!.agentWay"
                :dict-value="form!.agentWay"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="CONTRACT_TYPE_001 === form!.contractType">
            <el-form-item label="律所" :prop="CONTRACT_TYPE_001 == form!.contractType ? 'lawFirmId' : ''">
              <vzLawFirmInfo :default-value="form!.lawFirmId" @handle-ok="lawFirmHandleOk"></vzLawFirmInfo>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="代理费收费比例" prop="agencyFeeRatio">
              <vz-input-unit
                v-model="form!.agencyFeeRatio"
                :value="form!.agencyFeeRatio"
                :max="100"
                placeholder="请输入代理费收费比例"
                text="%"
                :show-word="false"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="合同摘要" prop="contractAbstract" maxlength="2000">
              <el-input
                :rows="5"
                type="textarea"
                v-model="form!.contractAbstract"
                placeholder="请输入合同摘要"
                clearable
                show-word-limit
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="关联项目">
      <form-table
        ref="projectInfoListRef"
        :table-data="projectInfoList"
        select-type="radio"
        @on-select-change="onSelectChange"
        :readonly="drawerProps.isView"
      ></form-table>
    </vz-card>
    <vz-card title="签约方信息">
      <form-table ref="signatureInfoRef" :table-data="signatureInfoList" :readonly="drawerProps.isView"></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :query-data="{ doId: form!.id }" :readonly="drawerProps.isView"></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="contractInfoDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { contractInfo } from "@/api/modules/source/contractInfo/api"; // api
import { signatureInfo } from "@/api/modules/source/signatureInfo/api";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { deduplicateArraysById } from "@/utils";

import vzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";

let CONTRACT_TYPE_001 = 0;
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const signatureInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "contractPartyType",
      label: "签约方类型",
      width: "110",
      isRequired: true,
      type: "select",
      dictType: "CONTRACT_PARTY_TYPE"
    },
    {
      prop: "contractPartyName",
      label: "签约方名称",
      width: "150",
      isRequired: true,
      maxlength: 50,
      type: "text"
    },
    {
      prop: "contractRemark",
      label: "备注",
      width: "150",
      maxlength: 255,
      type: "text"
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "150",
      type: "file",
      doType: "SIGNATURE_INFO"
    }
  ]
});
const projectInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "doId",
      label: "关联项目",
      width: "110",
      isRequired: true,
      type: "project"
    }
  ]
});
const rules = reactive<any>({
  contractName: [{ required: true, message: "请输入合同名称", trigger: "blur" }],
  contractType: [{ required: true, message: "请输入合同类型", trigger: "change" }],
  signingDate: [{ required: true, message: "请选择签约日期", type: "date", trigger: "change" }],
  agentWay: [{ required: true, message: "请输入代理方式", trigger: "change" }],
  lawFirmId: [{ required: true, message: "请输入律所", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<any>({
  id: undefined // 这只是一个后面没逗号的坑位
});

const lawFirmHandleOk = (row: EmptyObjectType) => {
  form!.value.lawFirmId = row.id;
};

// 单个查找
const baksignatureInfoList = ref([]);
const bakprojectInfoList = ref();
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await contractInfo().findById(drawerProps.value.id);
  form.value = data;
  bakprojectInfoList.value = JSON.parse(JSON.stringify(data.projectInfoList));
  projectInfoList.value.data = data.projectInfoList;
  //	加载签约方信息
  signatureInfo()
    .findByContractId(drawerProps.value.id)
    .then((res: EmptyObjectType) => {
      if (res.code == 0) {
        signatureInfoList.value.data = res.data;
        baksignatureInfoList.value = JSON.parse(JSON.stringify(res.data));
      } else {
        ElMessage.warning(res.msg);
      }
    });
};
const onSelectChange = (val: any, index: number) => {
  const containsTarget = projectInfoList.value.data.some(item => item.doId === val.id);
  if (containsTarget) {
    return ElMessage.warning("已有相同的项目信息，请核对后再选择！");
  }
  projectInfoList.value.data[index] = {
    doId: val.id
  };
};
// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const projectInfoListRef = ref();
const fileRef = ref();
const signatureInfoRef = ref();
const handleSave = async () => {
  if (CONTRACT_TYPE_001 !== form.value!.contractType) {
    form.value.agentWay = undefined;
    form.value.lawFirmId = undefined;
  }

  if (CONTRACT_TYPE_001 == form.value.contractType && !form.value.lawFirmId) {
    ElMessage.warning("请选择律所信息");
    return;
  }

  if (CONTRACT_TYPE_001 == form.value.contractType && !form.value.agentWay) {
    ElMessage.warning("请选择代理方式");
    return;
  }
  if (!signatureInfoList.value.data || signatureInfoList.value.data.length == 0) {
    ElMessage.warning("签约方信息必须有一条数据");
    return;
  }
  const fileFormEl = fileRef.value?.tableRulesRef;
  const signatureEl = signatureInfoRef.value?.tableRulesRef;
  const deleteFiles = useDialogStore().deleteFiles;

  form.value.fileInfoList = fileRef.value.submitForm;
  const signInfo = signatureInfoList.value.data.map(item => {
    if (deleteFiles[item.id]) {
      return {
        ...item,
        fileInfoList: [...item.fileInfoList, ...deleteFiles[item.id]]
      };
    } else {
      return {
        ...item
      };
    }
  });
  form.value.signatureInfoList = deduplicateArraysById(baksignatureInfoList.value, signInfo);
  form.value.projectInfoList = deduplicateArraysById(bakprojectInfoList.value, projectInfoList.value.data);

  try {
    if (projectInfoListRef.value.tableRulesRef) {
      await projectInfoListRef.value.tableRulesRef.validate();
    }
    await Promise.all([fileFormEl.validate(), signatureEl.validate(), ruleFormRef.value!.validate()]);
    if (signatureInfoList.value.data.length <= 0) {
      return ElMessage.warning("请完善签约方信息！");
    }

    await drawerProps.value.api!(form.value);
    ElMessage.success({ message: `${drawerProps.value.title}合同信息成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
  } catch (error) {
    console.log(error);
  }

  // ruleFormRef.value!.validate(async valid => {
  //   if (!valid) return;
  //   //	判断签署方必填
  //   for (let i = 0; i < signatureInfoList.value.data.length; i++) {
  //     const signatureInfo: any = signatureInfoList.value.data[i];
  //     if (!signatureInfo.contractPartyType || !signatureInfo.contractPartyName) {
  //       ElMessage.warning("签署方信息必填项未填写完整请检查");
  //       return;
  //     }
  //   }
  //   try {
  //     form.value.signatureInfoList = signatureInfoList.value.data;
  //     await drawerProps.value.api!(form.value);
  //     ElMessage.success({ message: `${drawerProps.value.title}合同信息成功！` });
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
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}合同信息成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

onMounted(() => {
  useBaseStore()
    .findEnumByName("CONTRACT_TYPE")
    .then(item => {
      item.forEach(dict => {
        if (dict.itemCode == "CONTRACT_TYPE_001") {
          CONTRACT_TYPE_001 = dict.id;
        }
      });
    });

  findById();
});
defineExpose({
  handleSave,
  handleSubmit
});
</script>
