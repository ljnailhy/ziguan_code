<template>
  <div>
    <el-dialog v-model="dialogVisible" :title="`${title}信息`" draggable width="1200px">
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
import { recoveryLitigationDetails } from "@/api/modules/proceeding/recoveryLitigationDetails/api"; // api
import {
  RecoveryLitigationDetailsPageRequest,
  RecoveryLitigationDetailsDTO,
  LitigationTypeEnum
} from "@/api/modules/proceeding/recoveryLitigationDetails/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
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
const columns = reactive<ColumnProps<RecoveryLitigationDetailsDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "title",
    label: "标题",
    minWidth: 120,
    search: { el: "input" }
  },
  {
    prop: "preservationCode",
    label: "保全案号",
    minWidth: 120,

    isShow: (props.type && props.type == LitigationTypeEnum.PRESERVATION) || false
  },
  {
    type: "date",
    prop: "detailsDate",
    label: props.title + "时间",
    width: 120,
    isShow: (props.type && props.type != LitigationTypeEnum.PRESERVATION) || false,
    search: {
      key: "detailsDateRange", //指定搜索的key
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
    prop: "flowState",
    label: "流程状态",
    enum: processStatus,
    tag: true,
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
    width: 120
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 90
  }
]);

// 请求列表
const getTableList = (params: RecoveryLitigationDetailsPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  newParams.flowState = "completed";
  return recoveryLitigationDetails().findAll(newParams);
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
