<template>
  <div>
    <el-dialog v-model="dialogVisible" title="调解/判决信息" draggable width="1200px">
      <div class="table-box">
        <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
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
    <Detail ref="detailRef" :type="props.type" :title="props.title"></Detail>
  </div>
</template>
<script setup lang="tsx" name="RevokeInfoList">
import { ref, reactive } from "vue";
import { recoveryAdjustTrial } from "@/api/modules/proceeding/recoveryAdjustTrial/api"; // api
import {
  RecoveryAdjustTrialPageRequest,
  RecoveryAdjustTrialDTO,
  adjustTrialTypeOptions
} from "@/api/modules/proceeding/recoveryAdjustTrial/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { processStatus } from "@/enums/commonOptions";
import { ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的

//引入组件
import Detail from "./Detail.vue";

//定义父组件传过来的参数
type Props = {
  projectId?: any;
  type?: string;
  title?: string;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: "",
  type: "",
  title: ""
});

// 初始化参数
const initParam = reactive({
  projectId: props.projectId,
  litigationType: props.type
});

//表格的配置
const columns = reactive<ColumnProps<RecoveryAdjustTrialDTO>[]>([
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
    width: 200
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
    width: 160,
    type: "money",
    align: "right"
  },
  {
    prop: "interest",
    label: "利息(元)",
    width: 160,
    type: "money",
    align: "right"
  },
  {
    prop: "backOutAmount",
    label: "违约金(元)",
    width: 160,
    type: "money",
    align: "right"
  },
  {
    prop: "otherAmount",
    label: "其他费用(元)",
    width: 160,
    type: "money",
    align: "right"
  },
  {
    prop: "flowState",
    label: "流程状态",
    enum: processStatus,
    tag: true,
    search: { el: "select", props: { filterable: true } },
    width: 120
  },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    width: 120
  },
  {
    type: "datetime",
    prop: "createStamp",
    label: "创建时间",
    width: 200,
    search: {
      key: "createStampRange", //指定搜索的key
      el: "date-picker",
      span: 2,
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
    width: 90
  }
]);

// 请求列表
const getTableList = (params: RecoveryAdjustTrialPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  newParams.flowState = "completed";
  return recoveryAdjustTrial().findAll(newParams);
};

//查看更多
const dialogVisible = ref(false);

//查看
const detailRef = ref();
const openDrawer = (row: any) => {
  detailRef.value.acceptParams(row);
};

//暴漏变量给父级
defineExpose({
  dialogVisible
});
</script>
