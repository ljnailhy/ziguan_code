<template>
  <div style="width: 100%">
    <el-dialog
      @open="open"
      :title="dialogTitle"
      @before-close="handleOk"
      width="70%"
      v-model="state.dialogVisible"
      :destroy-on-close="true"
      append-to-body
    >
      <vz-card title="项目信息">
        <el-form
          ref="ruleFormRef"
          label-width="140px"
          label-suffix=" :"
          :disabled="true"
          :model="projectInfo"
          scroll-to-error
          :hide-required-asterisk="true"
        >
          <el-row :gutter="35">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="项目名称" prop="subjectName">
                <el-input
                  v-model="projectInfoForm!.projectName"
                  :value="projectInfoForm!.projectName"
                  placeholder="请输入(债务人)名称"
                  clearable
                  :disabled="true"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="产品名称" prop="productName">
                <vz-select
                  v-show="PRODUCT_NAME === 'OFF_LINE'"
                  dict-type="OFF_LINE"
                  v-model="projectInfoForm!.productName"
                  :dict-value="projectInfoForm!.productName"
                  style="width: 100%"
                ></vz-select>
                <vz-select
                  v-show="PRODUCT_NAME === 'ON_LINE'"
                  dict-type="ON_LINE"
                  v-model="projectInfoForm!.productName"
                  :dict-value="projectInfoForm!.productName"
                  style="width: 100%"
                ></vz-select>
                <vz-select
                  v-show="PRODUCT_NAME === 'TRADITIONAL_PRODUCT'"
                  dict-type="TRADITIONAL_PRODUCT"
                  v-model="projectInfoForm!.productName"
                  :dict-value="projectInfoForm!.productName"
                  style="width: 100%"
                ></vz-select>
              </el-form-item>
            </el-col> -->
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="性质" prop="nature">
                <el-select
                  v-model="projectInfoForm!.nature"
                  :value="projectInfoForm!.nature"
                  placeholder="请选择主体性质"
                  :disabled="true"
                >
                  <el-option key="E" label="企业" value="E" />
                  <el-option key="NE" label="非企业经济组织" value="NE" />
                  <el-option key="IB" label="个体工商户" value="IB" />
                  <el-option key="SME" label="小微企业主" value="SME" />
                  <el-option key="FM" label="农户" value="FM" />
                  <el-option key="OTHER" label="农户" value="OTHER" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="代偿金额" prop="compensationMoney">
                <vz-input-unit
                  v-model="projectInfoForm!.compensationMoney"
                  :value="projectInfoForm!.compensationMoney"
                  placeholder="请输入代偿金额"
                  text="元"
                  clearable
                ></vz-input-unit>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="代偿时间" prop="compensationDate">
                <el-date-picker
                  v-model="projectInfoForm!.compensationDate"
                  type="date"
                  placeholder="请选择代偿时间"
                  class="w100"
                />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="诉讼时效" prop="proceedingAgeingDate">
                <el-date-picker
                  v-model="projectInfoForm!.proceedingAgeingDate"
                  type="date"
                  placeholder="请选择诉讼时效"
                  class="w100"
                />
                <!--  :disabled="true"   -->
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="移交至保全部日期" prop="transferDate">
                <el-date-picker
                  v-model="projectInfoForm!.transferDate"
                  type="date"
                  placeholder="请选择移交至保全部日期"
                  class="w100"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </vz-card>
      <vz-card title="变更保全经理">
        <el-form
          ref="ruleFormRef"
          label-width="140px"
          label-suffix=" :"
          :rules="rules"
          :disabled="false"
          :model="form"
          scroll-to-error
          :hide-required-asterisk="false"
        >
          <el-row :gutter="35">
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
              <el-form-item label="保全经理" prop="manage">
                <vz-user
                  style="width: 100%"
                  :multiple="true"
                  v-model:model="form!.manage"
                  :dict-value="form!.manage"
                  clearable
                ></vz-user>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </vz-card>
      <vz-card title="历史变更记录">
        <form-table :is-add="false" :table-data="allocationInfoDetailRequestList" :readonly="true"></form-table>
      </vz-card>
      <template #footer>
        <el-button class="c_blue" @click="cancelDialog">取消</el-button>
        <el-button type="primary" @click="handleOk">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import { ProjectInfoDTO } from "@/api/modules/recovery/projectInfo/interface";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api"; // api
import { subjectInfo } from "@/api/modules/source/subjectInfo/api";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { manageHistoryRecord } from "@/api/modules/manageHistoryRecord/api";

// 引入组件
const rules = reactive({
  manage: [{ required: true, message: "保全经理不能为空!" }]
});
// 定义父组件传过来的值
interface customerInfoInfoProps {
  filterData?: { [key: string]: any };
  disabled?: boolean;
  placeholder?: string;
  defaultValue?: string | number;
  companyId?: string;
  showbtn?: boolean;
  title?: string;
}
const projectInfoForm = ref<any>();
const props = withDefaults(defineProps<customerInfoInfoProps>(), {
  placeholder: "",
  disabled: false,
  defaultValue: "",
  companyId: "",
  showbtn: false,
  title: "保全经理变更",
  filterData: () => ({})
});
const PRODUCT_NAME = ref("ON_LINE");
const emit = defineEmits(["handleOk", "open", "doubleClick", "clear"]);
const changeType = (val: any) => {
  useBaseStore()
    .findEnumByName("TYPE")
    .then((res: EmptyObjectType) => {
      PRODUCT_NAME.value = res.filter(item => item.id === val)[0].itemCode;
    });
};
const allocationInfoDetailRequestList = ref<any>({
  data: [],
  header: [
    {
      prop: "manage",
      label: "保全经理",
      width: "110",
      isRequired: true,
      disabled: true,
      type: "user"
    },
    {
      prop: "startDate",
      label: "开始时间",
      width: "150",
      isRequired: true,
      disabled: true,
      type: "datetime"
    },
    {
      prop: "endDate",
      label: "截止时间",
      width: "150",
      isRequired: true,
      disabled: true,
      type: "datetime"
    }
  ]
});
//定义变量
const dialogTitle = ref(props.title);
const form = ref();
const state = reactive({
  dialogVisible: false,
  dictionaryName: ""
});
//定义方法
const bakmanage = ref();
const handleFocus = (params: ProjectInfoDTO) => {
  projectInfoForm.value = params;
  changeType(params.type);
  subjectInfo()
    .findById(params.debtorId)
    .then((res: EmptyObjectType) => {
      projectInfoForm.value.nature = res.data.nature;
    });
  form.value = params;
  bakmanage.value = params.manage;
  // if (params.manage) {
  //   form.value.manage = params.manage.split(",");
  // }
  // billLawyer()
  //   .findByDoId(params.id, "PROJECT_INFO")
  //   .then((res: EmptyObjectType) => {
  //     form.value.contractId = res.data.join(",");
  //   });
  manageHistoryRecord()
    .findByProjectId(params.id)
    .then((res: EmptyObjectType) => {
      allocationInfoDetailRequestList.value.data = res.data;
    });
  state.dialogVisible = true;
};
// const ensureArray = value => {
//   debugger;
//   if (Array.isArray(value)) {
//     return value.map(obj => obj);
//   } else {
//     return [value.id];
//   }
// };
//点击确定
const handleOk = () => {
  if (!form!.value.manage || form!.value.manage.length == 0) {
    ElMessage.warning("请选择保全经理");
    return;
  }
  if (Array.isArray(form.value.manage)) {
    form.value.manage = form.value.manage.join(",");
  }
  if (bakmanage.value == form.value.manage) {
    ElMessage.warning("新旧保全经理不能相同");
    return;
  }
  projectInfo()
    .changeManage(form.value)
    .then((res: EmptyObjectType) => {
      if (res.code == 0) {
        ElMessage.success({ message: `保全经理变更成功！` });
      } else {
        ElMessage.warning(res.msg);
      }
    });
  state.dialogVisible = false;
};

const open = () => {
  emit("open");
};

//取消弹窗
const cancelDialog = () => {
  state.dialogVisible = false;
};
// 暴露给父组件使用
defineExpose({ handleFocus });
</script>
<style lang="scss" scoped>
.el-button {
  min-width: 80px;
}
.table-demo {
  flex: 1;
  overflow: hidden;
}
</style>
