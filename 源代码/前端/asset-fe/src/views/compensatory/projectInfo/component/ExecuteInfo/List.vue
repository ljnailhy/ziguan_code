<template>
  <div>
    <el-dialog v-model="dialogVisible" title="执行信息" draggable width="1200px">
      <div class="table-box">
        <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
          <template #executeRulingIssuanceTime="scope">
            <div v-if="scope.row.executeRulingIssuanceTime">
              <span v-for="(item, index) in scope.row.executeRulingIssuanceTime.split(',')" :key="index">
                <dict-date :date="Number(item)" format="YYYY-MM-DD"> </dict-date>
                <i v-if="index !== scope.row.executeRulingIssuanceTime.split(',').length - 1">、</i>
              </span>
            </div>
          </template>
          <template #operation="scope">
            <el-button type="primary" link @click="openDrawer(scope.row)">查看</el-button>
          </template>
        </vz-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
        </span>
      </template>
    </el-dialog>
    <Detail ref="detailRef"></Detail>
  </div>
</template>
<script setup lang="tsx" name="LitigationList">
import { ref, reactive } from "vue";
import { recoveryExecute } from "@/api/modules/proceeding/recoveryExecute/api"; // api
import {
  RecoveryExecutePageRequest,
  RecoveryExecuteDTO,
  executeTypeOptions
} from "@/api/modules/proceeding/recoveryExecute/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的

//引入组件
import Detail from "./Detail.vue";

type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});

// 初始化参数
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
    prop: "executeRulingIssuanceTime",
    label: "执行裁定下达时间",
    width: 200
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
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 80
  }
]);

const getTableList = (params: RecoveryExecutePageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  newParams.flowState = "completed";
  return recoveryExecute().findAll(newParams);
};

//查看更多
const dialogVisible = ref(false);

//查看
const detailRef = ref();
const openDrawer = (row: any) => {
  detailRef.value.acceptParams(row);
};

//页面加载时

defineExpose({
  dialogVisible
});
</script>
