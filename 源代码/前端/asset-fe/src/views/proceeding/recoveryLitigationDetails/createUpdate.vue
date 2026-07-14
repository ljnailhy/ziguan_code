<template>
  <div>
    <vz-card title="历史信息" v-if="drawerProps.projectId" :collapse="false">
      <recoveryLitigationDetailsIndex
        v-if="drawerProps.litigationType != 'PRESERVATION'"
        :selected-id="selectedId"
        @open-drawer="openDrawer"
        :filter-data="{ projectId: drawerProps.projectId, litigationType: drawerProps.litigationType }"
      ></recoveryLitigationDetailsIndex>
      <recoveryLitigationDetailsPreservationIndex
        v-else
        :selected-id="selectedId"
        @open-drawer="openDrawer"
        :filter-data="{ projectId: drawerProps.projectId, litigationType: drawerProps.litigationType }"
      ></recoveryLitigationDetailsPreservationIndex>
    </vz-card>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="150px"
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        scroll-to-error
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="标题名称" prop="title">
              <el-input v-model="form!.title" placeholder="请输入标题" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="8"
            class="mb20"
            v-if="drawerProps!.litigationType == LitigationTypeEnum.DROP_LAWSUIT"
          >
            <el-form-item label="立案案号" prop="registerId">
              <vzRegister
                :default-value="form!.registerId"
                :filter-data="{
                  projectId: drawerProps.projectId || form.projectId,
                  litigationType: 'REGISTER',
                  flowState: 'completed'
                }"
                @handle-ok="registerOk"
              ></vzRegister>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="8"
            class="mb20"
            v-if="drawerProps.litigationType == LitigationTypeEnum.PRESERVATION"
          >
            <el-form-item label="保全案号" prop="preservationCode">
              <el-input v-model="form!.preservationCode" placeholder="请输入保全案号" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <!-- 保全字段 s -->
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="8"
            class="mb20"
            v-if="drawerProps.litigationType == LitigationTypeEnum.PRESERVATION"
          >
            <el-form-item label="保全类型" prop="preserveType">
              <el-select v-model="form!.preserveType" clearable class="w100">
                <el-option label="诉前保全" value="BEFORE_LITIGATION_PRESERVE"></el-option>
                <el-option label="诉中保全" value="MIDDLE_LITIGATION_PRESERVE"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="8"
            class="mb20"
            v-if="drawerProps.litigationType == LitigationTypeEnum.PRESERVATION"
          >
            <el-form-item label="保全申请提交日期" prop="preserveApplyDate">
              <el-date-picker v-model="form!.preserveApplyDate" type="date" placeholder="请选择日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="8"
            class="mb20"
            v-if="drawerProps.litigationType == LitigationTypeEnum.PRESERVATION"
          >
            <el-form-item label="保全裁定送达日期" prop="preserveRuleDate">
              <el-date-picker v-model="form!.preserveRuleDate" type="date" placeholder="请选择日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="8"
            class="mb20"
            v-if="drawerProps.litigationType == LitigationTypeEnum.PRESERVATION"
          >
            <el-form-item label="保全费（元）" prop="insureMoney">
              <el-input v-model="form!.insureMoney" placeholder="请输入保全费" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <!-- 保全字段 e -->
          <!-- v-if="drawerProps.litigationType != LitigationTypeEnum.PRESERVATION" -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item :label="getIllustrateLabel() + '时间'" prop="detailsDate">
              <el-date-picker
                @change="dateChange()"
                v-model="form!.detailsDate"
                type="date"
                placeholder="请选择时间"
                class="w100"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item :label="getIllustrateLabel() + '说明'" prop="detailsDescription">
              <el-input
                type="textarea"
                :rows="5"
                v-model="form!.detailsDescription"
                placeholder="请输入说明"
                clearable
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="反担保措施" v-if="drawerProps.litigationType == LitigationTypeEnum.PRESERVATION">
      <form-table :show-button="false" :table-data="reveInfoList" :readonly="drawerProps.isView"></form-table>
    </vz-card>
    <vz-card title="其他财产信息" v-if="drawerProps.litigationType == LitigationTypeEnum.PRESERVATION">
      <form-table :show-button="false" :table-data="propertyInfoList" :readonly="drawerProps.isView"></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="drawerProps.isView" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="recoveryLitigationDetailsDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import {
  RecoveryLitigationDetailsRequest,
  LitigationTypeEnum
} from "@/api/modules/proceeding/recoveryLitigationDetails/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { recoveryLitigationDetails } from "@/api/modules/proceeding/recoveryLitigationDetails/api"; // api
import { securityTypeNatureOptions } from "@/api/modules/recovery/projectInfo/interface";
// import { recoveryLitigation } from "@/api/modules/proceeding/recoveryLitigation/api";
import { revePropertyInfo } from "@/api/modules/compensatory/revePropertyInfo/api";
import { useDialogStore } from "@/stores/modules/dialogParams";

import recoveryLitigationDetailsIndex from "@/views/proceeding/recoveryLitigationDetails/index.vue";
import recoveryLitigationDetailsPreservationIndex from "@/views/proceeding/recoveryLitigationDetails/preservationIndex.vue";
import vzRegister from "@/components/source/vzRegister.vue";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  registerId: [{ required: true, message: "请选择立案案号", trigger: "blur" }],
  litigationId: [{ required: true, message: "请输入诉讼id", trigger: "blur" }],
  title: [{ required: true, message: "请输入标题", trigger: "change" }],
  preservationCode: [{ required: true, message: "请输入保全案号", trigger: "change" }],
  detailsDate: [{ required: true, message: "请选择时间", trigger: "change" }],
  detailsDescription: [{ required: true, message: "请输入说明", trigger: "change" }],
  litigationType: [
    {
      required: true,
      message: "请选择诉讼类型 final:终本 other:其他 drop_lawsuit:撤诉 close_case:结案 preservation:保全",
      trigger: "change"
    }
  ],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});
const dateChange = () => {
  // if (!form.value.title && drawerProps.value.projectName && form.value.detailsDate) {
  //   //  【撤诉】+项目名称+于+撤诉时间+提交撤诉申请，
  //   const year = form.value.detailsDate.getFullYear();
  //   const month = String(form.value.detailsDate.getMonth() + 1).padStart(2, "0"); // 月份从0开始计数，所以需要加1
  //   const day = String(form.value.detailsDate.getDate()).padStart(2, "0");
  //   const text =
  //     drawerProps.value.litigationType == LitigationTypeEnum.DROP_LAWSUIT
  //       ? `于${year}年${month}月${day}日提交撤诉申请`
  //       : drawerProps.value.litigationType == LitigationTypeEnum.FINAL
  //         ? `于${year}年${month}月${day}日提交终本申请`
  //         : drawerProps.value.litigationType == LitigationTypeEnum.CLOSE_CASE
  //           ? `于${year}年${month}月${day}日提交结案申请`
  //           : drawerProps.value.litigationType == LitigationTypeEnum.OTHER
  //             ? `于${year}年${month}月${day}日提交其他诉讼事项申请`
  //             : "";
  //   form.value.title = drawerProps.value.projectName + `${text}`;
  // }
  if (!form.value.title) {
    form.value.title = drawerProps.value.projectName;
  }
};
const getIllustrateLabel = () => {
  if (drawerProps.value.litigationType == LitigationTypeEnum.FINAL) {
    return "终本";
  }
  if (drawerProps.value.litigationType == LitigationTypeEnum.OTHER) {
    //  其他只显示时间和说明
    return "";
  }
  if (drawerProps.value.litigationType == LitigationTypeEnum.DROP_LAWSUIT) {
    return "撤诉";
  }
  if (drawerProps.value.litigationType == LitigationTypeEnum.CLOSE_CASE) {
    return "结案";
  }
  if (drawerProps.value.litigationType == LitigationTypeEnum.PRESERVATION) {
    return "保全";
  }
};
const registerOk = (row: EmptyObjectType) => {
  form!.value.registerId = row.id;
};
//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<RecoveryLitigationDetailsRequest>>({
  /** 是否存量 是1 否0 */
  isStock: false,
  /** 诉讼类型 final:终本 other:其他 drop_lawsuit:撤诉 close_case:结案 preservation:保全 */
  litigationType: LitigationTypeEnum.FINAL,
  id: undefined // 这只是一个后面没逗号的坑位
});

const queryReveProperty = (doId, doType) => {
  revePropertyInfo()
    .findByDoId(doId, doType, "REVE")
    .then((reveInfo: any) => {
      if (reveInfo.code == 0) {
        reveInfoList.value.data = reveInfo.data;
      }
    });
  revePropertyInfo()
    .findByDoId(doId, doType, "PROPERTY")
    .then((propertyInfo: any) => {
      if (propertyInfo.code == 0) {
        propertyInfoList.value.data = propertyInfo.data;
      }
    });
};
// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await recoveryLitigationDetails().findById(drawerProps.value.id);
  drawerProps.value.litigationId = data.litigationId;
  drawerProps.value.litigationType = data.litigationType;
  form.value = data;
};
const selectedId = ref();
const ruleFormRef = ref<FormInstance>();
const openDrawer = (title: string, row: RecoveryLitigationDetailsRequest) => {
  drawerProps.value.isView = false;
  selectedId.value = row.id;
  if ("取消" == title) {
    if (form.value.id == row.id) {
      form.value = {};
      queryReveProperty(drawerProps.value.projectId, "PROJECT_INFO");
      selectedId.value = 0;
    }
  } else {
    if ("查看" == title) {
      drawerProps.value.isView = true;
      ruleFormRef.value?.clearValidate();
    }
    form.value = row;
    queryReveProperty(form.value.id, form.value.litigationType);
  }
};
// 保存数据（新增/编辑）

const fileRef = ref();
const saveFun = async () => {
  try {
    await ruleFormRef.value!.validate();
    if (fileRef.value?.tableRulesRef) {
      await fileRef.value?.tableRulesRef!.validate();
    }
  } catch (error) {
    return false;
  }
  if (!form.value.title) {
    ElMessage.error({ message: `请输入标题！` });
    return;
  }
  form.value.litigationId = drawerProps.value.litigationId;
  form.value.litigationType = drawerProps.value.litigationType;
  form.value.projectId = drawerProps.value.projectId;
  form.value.lawFirmId = drawerProps.value.lawFirmId;
  form.value.fileInfoList = fileRef.value.submitForm;

  //  将担保/财产id清空并赋值到来源id上
  if (!form.value.id) {
    if (drawerProps.value.litigationType == LitigationTypeEnum.PRESERVATION) {
      propertyInfoList.value.data.forEach((item: any) => {
        item.sourceId = item.id;
        item.id = null;
      });
      reveInfoList.value.data.forEach((item: any) => {
        item.sourceId = item.id;
        item.id = null;
      });
    }
  }
  form.value.propertyInfoRequest = propertyInfoList.value.data;
  form.value.reveInfoRequest = reveInfoList.value.data;
  return true;
};

const handleSave = async () => {
  const result = await saveFun();
  if (!result) return false;
  try {
    drawerProps.value.title == "新增"
      ? await recoveryLitigationDetails().add!(form.value)
      : await recoveryLitigationDetails().update!(form.value);
    //  判断是不是查询页调用保存，如果是，则调用反写接口
    // if (drawerProps.value.isView) {
    //   recoveryLitigationDetails().writeBackProject(form.value.id);
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
    await recoveryLitigationDetails().submit!(form.value);
    //  判断是不是查询页调用保存，如果是，则调用反写接口
    // if (drawerProps.value.isView) {
    //   recoveryLitigationDetails().writeBackProject(form.value.id);
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

const reveInfoList = ref({
  data: [],
  header: [
    {
      prop: "securityWay",
      label: "担保方式",
      width: "120",
      isRequired: true,
      type: "select",
      dictType: "SECURITY_WAY",
      disabled: true
    },
    {
      prop: "securityType",
      label: "担保类型",
      width: "150",
      isRequired: true,
      type: "select",
      enum: securityTypeNatureOptions,
      disabled: true
    },
    {
      prop: "reveName",
      label: "反担保人名称",
      width: "150",
      isRequired: true,
      type: "text",
      disabled: true
    },
    {
      prop: "reveMeasure",
      label: "反担保措施",
      width: "300",
      type: "text",
      disabled: true
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean",
      disabled: true
    },
    {
      prop: "disposeMoney",
      label: "处置回款金额(元)",
      width: "140",
      type: "number",
      disabled: true
    },
    // {
    //   prop: "preserveDate",
    //   label: "保全日期",
    //   width: "160",
    //   type: "date"
    // },
    // {
    //   prop: "preserveType",
    //   label: "保全类型",
    //   width: "160",
    //   type: "select",
    //   enum: [
    //     { label: "诉前保全", value: "BEFORE_LITIGATION_PRESERVE" },
    //     { label: "诉中保全", value: "MIDDLE_LITIGATION_PRESERVE" }
    //   ]
    // },
    // {
    //   prop: "preserveApplyDate",
    //   label: "保全申请提交时间",
    //   width: "160",
    //   type: "date"
    // },
    // {
    //   prop: "preserveRuleDate",
    //   label: "保全裁定送达时间",
    //   width: "160",
    //   type: "date"
    // },
    // {
    //   prop: "insureMoney",
    //   label: "保全费(元)",
    //   width: "160",
    //   type: "money"
    // },
    {
      prop: "remark",
      label: "备注",
      width: "200",
      type: "text"
    }
  ]
});
const propertyInfoList = ref({
  data: [],
  header: [
    {
      prop: "reveMeasure",
      label: "其他财产线索",
      width: "300",
      isRequired: true,
      type: "text",
      disabled: true
    },
    {
      prop: "debtRepaymentDate",
      label: "裁定以资抵债日期",
      width: "160",
      type: "date",
      disabled: true
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额(元)",
      width: "140",
      type: "number",
      disabled: true
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean",
      disabled: true
    },
    {
      prop: "disposeMoney",
      label: "处置回款金(元)",
      width: "120",
      type: "number",
      disabled: true
    },
    // {
    //   prop: "preserveDate",
    //   label: "保全日期",
    //   width: "160",
    //   type: "date"
    // },
    // {
    //   prop: "preserveType",
    //   label: "保全类型",
    //   width: "160",
    //   type: "select",
    //   enum: [
    //     { label: "诉前保全", value: "BEFORE_LITIGATION_PRESERVE" },
    //     { label: "诉中保全", value: "MIDDLE_LITIGATION_PRESERVE" }
    //   ]
    // },
    // {
    //   prop: "preserveApplyDate",
    //   label: "保全申请提交时间",
    //   width: "160",
    //   type: "date"
    // },
    // {
    //   prop: "preserveRuleDate",
    //   label: "保全裁定送达时间",
    //   width: "160",
    //   type: "date"
    // },
    // {
    //   prop: "insureMoney",
    //   label: "保全费(元)",
    //   width: "160",
    //   type: "money"
    // },
    {
      prop: "remark",
      label: "备注",
      width: "200",
      type: "text"
    }
  ]
});

onMounted(async () => {
  await findById();
  dateChange();
  //  查询诉讼信息
  // recoveryLitigation()
  //   .findById(drawerProps.value.litigationId)
  //   .then((res: any) => {
  //     if (res.code == 0) {
  //       basicInfoData.value = res.data;
  //       //  保全 查询项目信息中的保全信息和其他财产信息
  //       if (LitigationTypeEnum.PRESERVATION == drawerProps.value.litigationType) {
  //         let doId = drawerProps.value.title == "新增" ? res.data.projectId : drawerProps.value.id;
  //         let doType = drawerProps.value.title == "新增" ? "PROJECT_INFO" : drawerProps.value.litigationType;
  //         //  查询反担保和其他财产信息

  //       }
  //     }
  //   });
  if (LitigationTypeEnum.PRESERVATION != drawerProps.value.litigationType) {
    return;
  }

  if (drawerProps.value.id) {
    queryReveProperty(form.value.id, form.value.litigationType);
  } else {
    queryReveProperty(drawerProps.value.projectId, "PROJECT_INFO");
  }
});

defineExpose({
  handleSave,
  handleSubmit
});
</script>
