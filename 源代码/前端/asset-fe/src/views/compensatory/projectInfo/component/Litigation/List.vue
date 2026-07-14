<template>
  <div>
    <el-dialog v-model="dialogVisible" title="诉状信息" draggable width="1200px">
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
import { recoveryLitigation } from "@/api/modules/proceeding/recoveryLitigation/api"; // api
import { RecoveryLitigationPageRequest, RecoveryLitigationDTO } from "@/api/modules/proceeding/recoveryLitigation/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
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
const columns = reactive<ColumnProps<RecoveryLitigationDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "compensationAmount",
    label: "代偿金额(元)",
    width: 160,
    type: "money",
    align: "right",
    headerAlign: "center"
  },

  {
    prop: "interest",
    label: "利息（元）",
    minWidth: 120,
    type: "money",
    align: "right"
  },
  {
    prop: "liquidatedDamages",
    label: "违约金（元）",
    minWidth: 120,
    type: "money",
    align: "right"
  },
  {
    prop: "otherFees",
    label: "其他费用(元)",
    minWidth: 120,
    type: "money",
    align: "right"
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

const getTableList = (params: RecoveryLitigationPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  newParams.isSubmit = true;
  return recoveryLitigation().findAll(newParams);
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
