<template>
  <div>
    <vz-card title="调解/判决信息" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="类型">
            <template #value>
              <dict-enum :options="adjustTrialTypeOptions" :value="form.adjustTrialType"></dict-enum>
            </template>
          </CellItem>
          <CellItem label="本金(元)">
            <template #value>
              <div v-currency="form.compensatoryAmount"></div>
            </template>
          </CellItem>
          <CellItem label="其他费用(元)">
            <template #value>
              <div v-currency="form.otherAmount"></div>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="调解/判决日期">
            <template #value>
              <dict-date :date="form.adjustTrialDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
          <CellItem label="利息(元)">
            <template #value>
              <div v-currency="form.interest"></div>
            </template>
          </CellItem>
          <!-- <CellItem label="是否支持诉请">
            <template #value> </template>
          </CellItem> -->
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="判决案号">
            <template #value>
              <dict-name dict-type="COOPERATE_BANK" :dict-value="form!.adjustCode"></dict-name>
            </template>
          </CellItem>
          <CellItem label="违约金(元)">
            <template #value>
              <div v-currency="form.backOutAmount"></div>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="特殊情况说明">
            <template #value>
              <dict-name dict-type="COOPERATE_BANK" :dict-value="form!.specialRemarks"></dict-name>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="备注" :value="form.remarks"> </CellItem>
        </el-col>
      </el-row>
    </vz-card>
    <el-dialog v-model="dialogVisible" title="调解或审判记录" draggable width="1200px">
      <div class="table-box">
        <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam"> </vz-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="tsx" name="LiAnInfo">
import { onMounted, ref, reactive } from "vue";
import { recoveryAdjustTrial } from "@/api/modules/proceeding/recoveryAdjustTrial/api"; // api
import {
  RecoveryAdjustTrialPageRequest,
  RecoveryAdjustTrialDTO,
  adjustTrialTypeOptions
} from "@/api/modules/proceeding/recoveryAdjustTrial/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的

import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";

type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});

const dialogVisible = ref(false);
const form = ref({
  interest: "",
  backOutAmount: "",
  adjustCode: "",
  specialRemarks: "",
  compensatoryAmount: "",
  otherAmount: "",
  adjustTrialType: "",
  remarks: "",
  adjustTrialDate: ""
});
const initParam = reactive({
  projectId: props.projectId
});

const columns = reactive<ColumnProps<RecoveryAdjustTrialDTO>[]>([
  { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "adjustTrialType",
    label: "调解/判决类型",
    enum: adjustTrialTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 180
  },
  {
    type: "date",
    prop: "adjustTrialDate",
    label: "调解/判决日期",
    width: 180
  },
  {
    prop: "adjustCode",
    label: "判决案号",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "compensatoryAmount",
    label: "代偿金额(元)",
    width: 120
  },
  {
    prop: "interest",
    label: "利息(元)",
    width: 120
  },
  {
    prop: "backOutAmount",
    label: "违约金(元)",
    width: 120
  },
  {
    prop: "otherAmount",
    label: "其他费用(元)",
    width: 120
  },
  {
    prop: "specialRemarks",
    label: "特殊情况说明",
    width: 120
  },
  {
    prop: "remarks",
    label: "备注",
    width: 120
  },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    width: 120
  },
  {
    type: "date",
    prop: "createStamp",
    label: "创建时间",
    width: 120,
    search: {
      key: "createStampRange", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: {
        type: "datetimerange",
        format: "YYYY-MM-DD HH:mm:ss",
        valueFormat: "x",
        defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
      }
    }
  }
]);

const getTableList = (params: RecoveryAdjustTrialPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return recoveryAdjustTrial().findAll(newParams);
};

const goMore = () => {
  dialogVisible.value = true;
};

onMounted(() => {
  const params: any = { size: 1, current: 1, projectId: props.projectId };
  recoveryAdjustTrial()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
      }
    });
});
</script>
