<template>
  <div>
    <vz-card title="历史信息" :collapse="false" v-if="drawerProps.projectId">
      <recoveryExecuteIndex
        :selected-id="selectedId"
        @open-drawer="openDrawer"
        :filter-data="{ projectId: drawerProps.projectId }"
      ></recoveryExecuteIndex>
    </vz-card>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="150px"
        label-suffix=" :"
        scroll-to-error
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="执行状态" prop="executeType">
              <el-select v-model="form!.executeType" clearable class="w100">
                <el-option label="已申请" value="APPLY"></el-option>
                <el-option label="未申请" value="UNAPPLY"></el-option>
                <el-option label="撤销执行" value="REVOKE"></el-option>
                <el-option label="执行和解" value="RECONCILIATE"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="日期" prop="applyExecuteDate">
              <el-date-picker v-model="form!.applyExecuteDate" type="date" placeholder="请选择日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="执行案号" prop="executeCode">
              <el-input v-model="form!.executeCode" placeholder="请输入执行案号" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="执行法院" prop="executeCourt">
              <el-input v-model="form!.executeCourt" placeholder="请输入执行法院" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="执行员" prop="executer">
              <el-input v-model="form!.executer" placeholder="请输入执行员" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系电话" prop="executerTelphone">
              <el-input v-model="form!.executerTelphone" placeholder="请输入联系电话" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="执行裁定下达时间" prop="executeRulingIssuanceTime">
              <!-- <el-input v-model="form!.executeRulingIssuanceTime" placeholder="请输入执行裁定下达时间" clearable></el-input> -->

              <el-date-picker
                v-model="form!.executeRulingIssuanceTime"
                type="dates"
                value-format="x"
                placeholder="请选择执行裁定下达时间"
                class="w100"
              />
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
    <vz-card title="反担保措施">
      <form-table
        :show-button="false"
        :table-data="reveInfoList"
        :readonly="drawerProps.isView"
        :operation-width="120"
        :view-operation="true"
      >
        <template #operation="scope">
          <el-button type="primary" link @click="openFapai(scope.row)">登记法拍记录</el-button>
        </template>
      </form-table>
    </vz-card>
    <vz-card title="其他财产信息">
      <form-table
        :show-button="false"
        :table-data="propertyInfoList"
        :readonly="drawerProps.isView"
        :operation-width="120"
        :view-operation="true"
      >
        <template #operation="scope">
          <el-button type="primary" link @click="openFapai(scope.row)">登记法拍记录</el-button>
        </template>
      </form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="drawerProps.isView" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
    <!-- <el-collapse v-model="activeNames">
      <el-collapse-item name="1" title="基本信息"> </el-collapse-item>
      <el-collapse-item name="2" title="反担保措施"> </el-collapse-item>

      <el-collapse-item name="3" title="其他财产信息"> </el-collapse-item>
      <el-collapse-item name="4" title="附件信息">
        <file-table
          :upload-form="form!.fileInfoList"
          :readonly="drawerProps.isView"
          :query-data="{ doId: form!.id }"
        ></file-table>
      </el-collapse-item>
      <el-collapse-item name="5" title="诉讼信息" v-if="drawerProps.projectId"> </el-collapse-item>
    </el-collapse> -->
    <popFrameIndex ref="fapaiRef"></popFrameIndex>
  </div>
</template>

<script setup lang="ts" name="recoveryExecuteDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { RecoveryExecuteRequest, ExecuteTypeEnum } from "@/api/modules/proceeding/recoveryExecute/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { recoveryExecute } from "@/api/modules/proceeding/recoveryExecute/api"; // api
import { securityTypeNatureOptions } from "@/api/modules/recovery/projectInfo/interface";
import { revePropertyInfo } from "@/api/modules/compensatory/revePropertyInfo/api";
import { hangNetworkInfo } from "@/api/modules/hangNetworkInfo/api";
import { useDialogStore } from "@/stores/modules/dialogParams";

//引入组件
import recoveryExecuteIndex from "@/views/proceeding/recoveryExecute/index.vue";
import popFrameIndex from "@/views/proceeding/hangNetworkInfo/popFrameIndex.vue";

const fapaiRef = ref();

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  executeType: [{ required: true, message: "请选择执行状态", trigger: "change" }],
  applyExecuteDate: [{ required: true, message: "请选择日期", trigger: "change" }],
  litigationId: [{ required: true, message: "请输入诉讼id", trigger: "blur" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<RecoveryExecuteRequest>>({
  /** 执行状态 apply:已申请 unapply:未申请 */
  executeType: ExecuteTypeEnum.APPLY,
  /** 是否存量 是1 否0 */
  isStock: false,
  id: undefined // 这只是一个后面没逗号的坑位
});

const openFapai = async (row: any) => {
  fapaiRef.value.handleFocus(row, drawerProps.value.isView);
};
// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await recoveryExecute().findById(drawerProps.value.id);
  if (data.executeRulingIssuanceTime && !Array.isArray(data.executeRulingIssuanceTime)) {
    data.executeRulingIssuanceTime = data.executeRulingIssuanceTime.split(",").map(Number);
  }
  form.value = data;
};
const queryReveProperty = (doId, doType) => {
  revePropertyInfo()
    .findByDoId(doId, doType, "REVE")
    .then((reveInfo: any) => {
      if (reveInfo.code == 0) {
        reveInfoList.value.data = reveInfo.data;
        reveInfoList.value.data.forEach((item: any) => {
          hangNetworkInfo()
            .findByDoId(item.id, item.billType)
            .then((fapaiList: any) => {
              if (fapaiList.code == 0) {
                item.hangNetworkInfoRequestList = fapaiList.data;
              }
            });
        });
      }
    });
  revePropertyInfo()
    .findByDoId(doId, doType, "PROPERTY")
    .then((propertyInfo: any) => {
      if (propertyInfo.code == 0) {
        propertyInfoList.value.data = propertyInfo.data;
        propertyInfoList.value.data.forEach((item: any) => {
          hangNetworkInfo()
            .findByDoId(item.id, item.billType)
            .then((fapaiList: any) => {
              if (fapaiList.code == 0) {
                item.hangNetworkInfoRequestList = fapaiList.data;
              }
            });
        });
      }
    });
};
const selectedId = ref();
const ruleFormRef = ref<FormInstance>();
const openDrawer = (title: string, row: RecoveryExecuteRequest) => {
  drawerProps.value.isView = false;
  selectedId.value = row.id;
  if ("取消" == title) {
    if (form.value.id == row.id) {
      form.value = {};
      selectedId.value = 0;
      queryReveProperty(drawerProps.value.projectId, "PROJECT_INFO");
    }
  } else {
    if ("查看" == title) {
      ruleFormRef.value?.clearValidate();
      drawerProps.value.isView = true;
    }
    form.value = row;
    queryReveProperty(form.value.id, "RECOVERY_EXECUTE");
  }
};
const verifyReve = () => {
  for (const index in propertyInfoList.value.data) {
    const propertyInfo: any = propertyInfoList.value.data[index];
    if (propertyInfo.isDispose && !propertyInfo.disposeMoney) {
      ElMessage.warning({ message: `处置了必须填写回款金额` });
      return false;
    }
  }
  for (const index in reveInfoList.value.data) {
    const reveInfo: any = reveInfoList.value.data[index];

    if (reveInfo.isDispose && !reveInfo.disposeMoney) {
      ElMessage.warning({ message: `处置了必须填写回款金额` });
      return false;
    }
  }
  return true;
  // propertyInfoList.value.data.forEach((item: any) => {
  //       item.sourceId = item.id;
  //       item.id = null;
  //     });
  //     reveInfoList.value.data.forEach((item: any) => {
  //       item.sourceId = item.id;
  //       item.id = null;
  //     });
};
// 保存数据（新增/编辑）

const handleSave = async () => {
  const copyForm = JSON.parse(JSON.stringify(form.value));
  try {
    if (!verifyReve()) return false;
    copyForm.projectId = drawerProps.value.projectId;
    copyForm.lawFirmId = drawerProps.value.lawFirmId;
    if (Array.isArray(copyForm!.executeRulingIssuanceTime)) {
      copyForm!.executeRulingIssuanceTime = copyForm!.executeRulingIssuanceTime?.toString();
    }
    //  将担保/财产id清空并赋值到来源id上
    if (!copyForm.id) {
      propertyInfoList.value.data.forEach((item: any) => {
        item.sourceId = item.id;
        item.id = null;
      });
      reveInfoList.value.data.forEach((item: any) => {
        item.sourceId = item.id;
        item.id = null;
      });
    }

    copyForm.propertyInfoRequest = propertyInfoList.value.data;
    copyForm.reveInfoRequest = reveInfoList.value.data;
    copyForm.fileInfoList = fileRef.value.submitForm;
    drawerProps.value.title == "新增" ? await recoveryExecute().add!(copyForm) : await recoveryExecute().update!(copyForm);
    //  判断是不是查询页调用保存，如果是，则调用反写接口
    // if (drawerProps.value.isView) {
    //   recoveryExecute().writeBackProject(form.value.id);
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
const fileRef = ref();
const handleSubmit = () => {
  const copyForm = JSON.parse(JSON.stringify(form.value));
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    if (!verifyReve()) {
      return;
    }
    try {
      copyForm.projectId = drawerProps.value.projectId;
      copyForm.lawFirmId = drawerProps.value.lawFirmId;
      copyForm.fileInfoList = fileRef.value.submitForm;
      if (copyForm.executeRulingIssuanceTime) {
        copyForm.executeRulingIssuanceTime = copyForm!.executeRulingIssuanceTime?.toString();
      }

      // :upload-form="form!.fileInfoList"
      //  将担保/财产id清空并赋值到来源id上
      if (!copyForm.id) {
        propertyInfoList.value.data.forEach((item: any) => {
          item.sourceId = item.id;
          item.id = null;
        });
        reveInfoList.value.data.forEach((item: any) => {
          item.sourceId = item.id;
          item.id = null;
        });
      }
      copyForm.propertyInfoRequest = propertyInfoList.value.data;
      copyForm.reveInfoRequest = reveInfoList.value.data;
      await recoveryExecute().submit(copyForm);
      ElMessage.success({ message: `提交成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

const reveInfoList = ref({
  data: [],
  isHideDelete: true,
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
      width: "200",
      isRequired: true,
      type: "select",
      enum: securityTypeNatureOptions,
      disabled: true
    },
    {
      prop: "reveName",
      label: "反担保人名称",
      width: "120",
      isRequired: true,
      type: "text",
      disabled: true
    },
    {
      prop: "reveMeasure",
      label: "反担保措施",
      width: "200",
      type: "text",
      disabled: true
    },
    {
      prop: "debtRepaymentDate",
      label: "裁定以资抵债日期",
      width: "200",
      type: "date"
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额(元)",
      width: "200",
      type: "money",
      showWord: false,
      text: "元"
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
      width: "200",
      type: "money",
      showWord: false,
      text: "元"
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "200",
      type: "file",
      doType: "REVE"
    }
  ]
});
const propertyInfoList = ref({
  data: [],
  isHideDelete: true,
  header: [
    {
      prop: "reveMeasure",
      label: "其他财产线索",
      width: "150",
      isRequired: true,
      type: "text",
      disabled: true
    },
    {
      prop: "debtRepaymentDate",
      label: "裁定以资抵债日期",
      width: "160",
      type: "date"
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额(元)",
      width: "120",
      type: "number"
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean"
    },
    {
      prop: "disposeMoney",
      label: "处置回款金(元)",
      width: "120",
      type: "number"
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "160",
      type: "file",
      doType: "PROPERTY"
    }
  ]
});

onMounted(() => {
  findById();
  if (drawerProps.value.id) {
    queryReveProperty(drawerProps.value.id, "RECOVERY_EXECUTE");
  } else {
    queryReveProperty(drawerProps.value.projectId, "PROJECT_INFO");
  }
});
defineExpose({
  handleSave,
  handleSubmit
});
</script>
