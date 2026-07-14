<template>
  <el-dialog v-model="dialogVisible" title="调解/判决信息" draggable width="800px">
    <vz-card title="基本信息">
      <el-form ref="ruleFormRef" label-width="120px" label-suffix=" :" scroll-to-error :disabled="true" :model="form">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="调解/判决类型" prop="adjustTrialType">
              <el-select v-model="form!.adjustTrialType" clearable class="w100">
                <el-option label="诉前调解" value="BEFORE_LITIGATION_MEDIATION"></el-option>
                <el-option label="诉中调解" value="MIDDLE_LITIGATION_MEDIATION"></el-option>
                <el-option label="判决" value="JUDG"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="调解/判决日期" prop="adjustTrialDate">
              <el-date-picker v-model="form!.adjustTrialDate" type="date" placeholder="请选择调解/判决日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="判决案号" prop="adjustCode">
              <el-input v-model="form!.adjustCode" placeholder="请输入判决案号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="代偿金额(元)" prop="compensatoryAmount">
              <vz-input-unit
                v-model="form!.compensatoryAmount"
                :value="form!.compensatoryAmount"
                placeholder="请输入代偿金额"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="利息" prop="interest">
              <vz-input-unit
                v-model="form!.interest"
                :value="form!.interest"
                placeholder="请输入利息"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="违约金" prop="backOutAmount">
              <vz-input-unit
                v-model="form!.backOutAmount"
                :value="form!.backOutAmount"
                placeholder="请输入违约金"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="其他费用" prop="otherAmount">
              <vz-input-unit
                v-model="form!.otherAmount"
                :value="form!.otherAmount"
                placeholder="请输入其他费用"
                text="元"
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
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>

    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="true" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup lang="tsx" name="RevokeInfoDetail">
import { onMounted, ref } from "vue";
import { LitigationTypeEnum } from "@/api/modules/proceeding/recoveryLitigationDetails/interface";
import { securityTypeNatureOptions } from "@/api/modules/recovery/projectInfo/interface";
import { revePropertyInfo } from "@/api/modules/compensatory/revePropertyInfo/api";

//定义父组件传过来的参数
type Props = {
  type?: string;
  title?: string;
};

const props = withDefaults(defineProps<Props>(), {
  type: "",
  title: ""
});

//表单信息
const form = ref({
  id: "",
  adjustTrialType: "",
  adjustTrialDate: "",
  adjustCode: "",
  compensatoryAmount: "",
  interest: "",
  backOutAmount: "",
  otherAmount: "",
  specialRemarks: "",
  litigationType: ""
});

//反担保措施
const reveInfoList = ref({
  data: [],
  header: [
    {
      prop: "securityWay",
      label: "担保方式",
      width: "150",
      isRequired: true,
      type: "text",
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
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean",
      disabled: true
    },
    {
      prop: "disposeMoney",
      label: "处置回款金额(元)",
      width: "120",
      type: "number",
      disabled: true
    },
    {
      prop: "preserveDate",
      label: "保全日期",
      width: "160",
      type: "date"
    },
    {
      prop: "remark",
      label: "备注",
      width: "120",
      type: "text"
    }
  ]
});

//其他财产信息
const propertyInfoList = ref({
  data: [],
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
      type: "date",
      disabled: true
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额(元)",
      width: "120",
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
    {
      prop: "preserveDate",
      label: "保全日期",
      width: "160",
      type: "date"
    },
    {
      prop: "remark",
      label: "备注",
      width: "120",
      type: "text"
    }
  ]
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

const dialogVisible = ref(false);
const acceptParams = (params: any) => {
  form.value = {
    ...form.value,
    ...params
  };
  dialogVisible.value = true;
};

//页面加载时
onMounted(() => {
  if (props.type == LitigationTypeEnum.PRESERVATION) {
    queryReveProperty(form.value.id, form.value.litigationType);
  }
});

//暴漏变量给父级
defineExpose({
  acceptParams
});
</script>
