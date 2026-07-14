<template>
  <div>
    <vz-card title="执行信息" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="执行状态" label-width="110">
            <template #value>
              <dict-enum :options="executeTypeOptions" :value="form!.executeType"></dict-enum>
            </template>
          </CellItem>
          <CellItem label="执行法院" label-width="110" :value="form.executeCourt"> </CellItem>
          <CellItem label="执行裁定下达时间" label-width="110">
            <template #value>
              <dict-date :date="form!.executeRulingIssuanceTime" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="申请执行日期" label-width="110">
            <template #value>
              <dict-date :date="form!.applyExecuteDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
          <CellItem label="执行员" label-width="110">
            <template #value>
              {{ form.executer }}
            </template>
          </CellItem>
          <!-- <CellItem label="裁定以资抵债日期" :value="form!.loanMoney" label-width="110">
            <template #value>
              <div v-currency="form.loanMoney"></div>
            </template>
          </CellItem> -->
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="执行案号" :value="form.executeCode" />

          <CellItem label="联系电话">
            <template #value>
              {{ form.executerTelphone }}
            </template>
          </CellItem>
          <!-- <CellItem label="裁定抵债金额">
            <template #value>
              <dict-name dict-type="INDUSTRY_POLICY_SUPPORT" :dict-value="form!.goverType"></dict-name>
            </template>
          </CellItem> -->
        </el-col>
      </el-row>
    </vz-card>
    <el-dialog v-model="dialogVisible" title="执行信息记录" draggable width="1200px">
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
import { recoveryExecute } from "@/api/modules/proceeding/recoveryExecute/api"; // api
import {
  RecoveryExecutePageRequest,
  RecoveryExecuteDTO,
  executeTypeOptions
} from "@/api/modules/proceeding/recoveryExecute/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
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
  executeType: "",
  executeCourt: "",
  executeRulingIssuanceTime: "",
  applyExecuteDate: "",
  executer: "",
  executeCode: "",
  executerTelphone: ""
});
const initParam = reactive({
  projectId: props.projectId
});

// 表格配置项
const columns = reactive<ColumnProps<RecoveryExecuteDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "executeType",
    label: "执行状态",
    enum: executeTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 180
  },
  {
    prop: "executeCode",
    label: "执行案号",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "executeCourt",
    label: "执行法院",
    width: 120,
    search: { el: "input" }
  },
  {
    type: "date",
    prop: "applyExecuteDate",
    label: "日期",
    width: 120
  },
  {
    prop: "executer",
    label: "执行员",
    width: 120
  },
  {
    prop: "executerTelphone",
    label: "联系电话",
    width: 120
  },
  {
    type: "date",
    prop: "executeRulingIssuanceTime",
    label: "执行裁定下达时间",
    width: 160
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

const getTableList = (params: RecoveryExecutePageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return recoveryExecute().findAll(newParams);
};

const goMore = () => {
  dialogVisible.value = true;
};

onMounted(() => {
  const params: any = { size: 1, current: 1, projectId: props.projectId };
  recoveryExecute()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
      }
    });
});
</script>
