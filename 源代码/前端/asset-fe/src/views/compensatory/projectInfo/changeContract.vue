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
                  v-model="projectInfo!.projectName"
                  :value="projectInfo!.projectName"
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
                  v-model="projectInfo!.productName"
                  :dict-value="projectInfo!.productName"
                  style="width: 100%"
                ></vz-select>
                <vz-select
                  v-show="PRODUCT_NAME === 'ON_LINE'"
                  dict-type="ON_LINE"
                  v-model="projectInfo!.productName"
                  :dict-value="projectInfo!.productName"
                  style="width: 100%"
                ></vz-select>
                <vz-select
                  v-show="PRODUCT_NAME === 'TRADITIONAL_PRODUCT'"
                  dict-type="TRADITIONAL_PRODUCT"
                  v-model="projectInfo!.productName"
                  :dict-value="projectInfo!.productName"
                  style="width: 100%"
                ></vz-select>
              </el-form-item>
            </el-col> -->
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="性质" prop="nature">
                <el-select
                  v-model="projectInfo!.nature"
                  :value="projectInfo!.nature"
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
                  v-model="projectInfo!.compensationMoney"
                  :value="projectInfo!.compensationMoney"
                  placeholder="请输入代偿金额"
                  text="元"
                  clearable
                ></vz-input-unit>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="代偿时间" prop="compensationDate">
                <el-date-picker v-model="projectInfo!.compensationDate" type="date" placeholder="请选择代偿时间" class="w100" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="诉讼时效" prop="proceedingAgeingDate">
                <el-date-picker
                  v-model="projectInfo!.proceedingAgeingDate"
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
                  v-model="projectInfo!.transferDate"
                  type="date"
                  placeholder="请选择移交至保全部日期"
                  class="w100"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </vz-card>
      <vz-card title="变更合同">
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
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="合同名称" prop="contractId">
                <!--  v-if="form!.contractId"-->
                <vzContractInfo
                  v-model="form!.contractId"
                  :default-value="form!.contractId"
                  :filter-data="{ contractType: form!.contractType }"
                  placeholder="合同信息"
                  @handle-ok="contractOk"
                ></vzContractInfo>
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
import { billContract } from "@/api/modules/compensatory/billContract/api"; // api

// 引入组件
import vzContractInfo from "@/components/source/vzContractInfo.vue";
import { ProjectInfoDTO } from "@/api/modules/recovery/projectInfo/interface";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { subjectInfo } from "@/api/modules/source/subjectInfo/api";

// 定义父组件传过来的值
interface customerInfoInfoProps {
  filterData?: { [key: string]: any };
  disabled?: boolean;
  placeholder?: string;
  defaultValue?: string | number;
  companyId?: string;
  title?: string;
}
const rules = reactive({
  contractId: [{ required: true, message: "律师名称不能为空!" }]
});
const projectInfo = ref<any>();
const PRODUCT_NAME = ref("ON_LINE");
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
      prop: "contractId",
      label: "合同名称",
      width: "110",
      isRequired: true,
      disabled: true,
      type: "contract"
    },
    {
      prop: "createStamp",
      label: "创建时间",
      width: "150",
      isRequired: true,
      disabled: true,
      type: "datetime"
    }
  ]
});
const props = withDefaults(defineProps<customerInfoInfoProps>(), {
  placeholder: "",
  disabled: false,
  defaultValue: "",
  companyId: "",
  title: "合同变更",
  filterData: () => ({})
});
const emit = defineEmits(["handleOk", "open", "doubleClick", "clear"]);

//定义变量
const dialogTitle = ref(props.title);
const form = ref<any>({});
const state = reactive({
  dialogVisible: false,
  dictionaryName: ""
});
//定义方法
const handleFocus = (params: ProjectInfoDTO) => {
  projectInfo.value = params;
  changeType(params.type);
  subjectInfo()
    .findById(params.debtorId)
    .then((res: EmptyObjectType) => {
      projectInfo.value.nature = res.data.nature;
    });
  useBaseStore()
    .findEnumByName("CONTRACT_TYPE")
    .then((res: EmptyObjectType) => {
      for (let i = 0; i < res.length; i++) {
        if ("CONTRACT_TYPE_001" == res[i].itemCode) {
          form.value = {
            contractId: params.contractId,
            doType: "PROJECT_INFO",
            contractType: res[i].id,
            doId: params.id
          };
        }
      }
    });
  billContract()
    .findByDoId(params.id, "PROJECT_INFO")
    .then((res: EmptyObjectType) => {
      allocationInfoDetailRequestList.value.data = res.data;
    });
  state.dialogVisible = true;
};
const contractOk = (row: EmptyObjectType) => {
  form!.value.contractId = row.id;
};
//点击确定
const handleOk = () => {
  if (!form!.value.contractId) {
    ElMessage.warning("请选择合同信息");
    return;
  }
  if (projectInfo.value.contractId == form!.value.contractId) {
    ElMessage.warning("新合同和旧合同不能相同");
    return;
  }
  projectInfo.value.contractId = form.value.contractId;
  billContract()
    .add(form.value)
    .then((res: EmptyObjectType) => {
      if (res.code == 0) {
        ElMessage.success({ message: `合同变更成功！` });
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
