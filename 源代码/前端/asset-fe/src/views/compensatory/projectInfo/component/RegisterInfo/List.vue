<template>
  <div>
    <el-dialog v-model="dialogVisible" :title="projectType[0] == 'REGISTER' ? '立案信息' : '审理信息'" draggable width="1200px">
      <div class="table-box">
        <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
          <template #operation="scope">
            <el-button type="primary" link @click="openDrawer(scope.row)">查看</el-button>
            <el-button
              type="primary"
              link
              @click="openFlowchart(scope.row)"
              v-if="scope.row.flowState && 'draft' != scope.row.flowState"
            >
              查看流程图
            </el-button>
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
    <vz-flowchart ref="flowchartRef" />
  </div>
</template>
<script setup lang="tsx" name="RegisterInfoList">
import { ref, reactive } from "vue";
import { recoveryJudgement } from "@/api/modules/proceeding/recoveryJudgement/api"; // api
import {
  litigationTypeOptions,
  RecoveryJudgementDTO,
  registerTypeOptions,
  RecoveryJudgementPageRequest
} from "@/api/modules/proceeding/recoveryJudgement/interface";
import { processStatus } from "@/enums/commonOptions";
import { ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的

//引入组件
import Detail from "./Detail.vue";
import vzFlowchart from "@/components/source/vzFlowchart.vue";

type Props = {
  projectId?: any;
  projectType?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: "",
  projectType: ""
});

// 初始化参数
const initParam = reactive({
  projectId: props.projectId,
  litigationTypeList: props.projectType
});

// 表格配置项
const columns = reactive<ColumnProps<RecoveryJudgementDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "filingCourtName",
    label: "立案法院",
    minWidth: 120,
    search: { el: "input" }
  },
  {
    prop: "judgeName",
    label: "法官",
    width: 120
    // search: { el: "input" },
    // isShow: props.projectType[0] !== "REGISTER"
  },
  {
    prop: "judgePhone",
    label: "法官联系方式",
    width: 250
    // search: { el: "input" },
    // isShow: props.projectType[0] !== "REGISTER"
  },
  {
    prop: "fillingCode",
    label: "案号",
    width: 120,
    search: { el: "input" }
  },
  {
    type: "date",
    prop: "fillingDate",
    label: "立案时间",
    search: {
      key: "fillingDateRange", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: {
        type: "datetimerange",
        format: "YYYY-MM-DD HH:mm:ss",
        valueFormat: "x",
        defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
      }
    },
    width: 120
  },
  {
    prop: "litigationType",
    label: "类型",
    enum: litigationTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    // search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 180,
    isShow: props.projectType[0] !== "REGISTER"
  },
  {
    prop: "registerType",
    label: "立案类型",
    enum: registerTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    // search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 180,
    isShow: props.projectType[0] === "REGISTER"
  },
  {
    prop: "flowState",
    label: "流程状态",
    enum: processStatus,
    tag: true,
    width: 120
  },
  // {
  //   type: "date",
  //   prop: "judgeDate",
  //   label: "判决日期",
  //   width: 120,
  //   isShow: props.projectType[0] !== "REGISTER"
  // },
  // {
  //   type: "date",
  //   prop: "courtSessionDate",
  //   label: "开庭时间",
  //   width: 120,
  //   isShow: props.projectType[0] !== "REGISTER"
  // },

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
    width: 160
  }
]);

// 请求列表
const getTableList = (params: RecoveryJudgementPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  newParams.flowState = "completed";
  return recoveryJudgement().findAll(newParams);
};

//查看更多
const dialogVisible = ref(false);

//查看
const detailRef = ref();
const openDrawer = (row: any) => {
  detailRef.value.acceptParams(row);
};
const flowchartRef = ref();
const openFlowchart = (row: EmptyObjectType) => {
  flowchartRef.value.openDialog(row.id);
};
//页面加载时

defineExpose({
  dialogVisible
});
</script>
