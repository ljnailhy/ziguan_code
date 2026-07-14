<template>
  <div>
    <el-dialog v-model="dialogVisible" title="撤诉信息" draggable width="1200px">
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
    <Detail ref="detailRef"></Detail>
  </div>
</template>
<script setup lang="tsx" name="LitigationList">
import { ref, reactive } from "vue";
import { recoveryLitigationDetails } from "@/api/modules/proceeding/recoveryLitigationDetails/api"; // api

import {
  RecoveryLitigationDetailsPageRequest,
  RecoveryLitigationDetailsDTO
} from "@/api/modules/proceeding/recoveryLitigationDetails/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { processStatus } from "@/enums/commonOptions";
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

const columns = reactive<ColumnProps<RecoveryLitigationDetailsDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    type: "date",
    prop: "detailsDate",
    label: "保全时间",
    width: 120,
    search: {
      key: "detailsDateRange", //指定搜索的key
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
    prop: "detailsDescription",
    label: "保全说明",
    minWidth: 120
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
  return recoveryLitigationDetails().findAll(newParams);
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
