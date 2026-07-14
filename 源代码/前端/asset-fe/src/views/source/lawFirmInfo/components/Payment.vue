<template>
  <div>
    <div class="table-box">
      <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
        <template #tableHeader>
          <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
        </template>
        <template #operation="scope">
          <el-button
            type="primary"
            v-auth="'add'"
            link
            @click="openDrawer('编辑', scope.row)"
            v-if="'draft' == scope.row.flowState && scope.row.payStatus !== paymentState"
          >
            编辑
          </el-button>
          <el-button type="primary" v-auth="'add'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        </template>
      </vz-table>
    </div>
    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="recoveryPayment">
import { reactive, ref } from "vue";
import { recoveryPayment } from "@/api/modules/recovery/recoveryPayment/api"; // api
import { ColumnProps, VzTableInstance } from "@/components/VzTable/interface"; // table数据类型 固定的
import { RecoveryPaymentPageRequest, RecoveryPaymentDTO } from "@/api/modules/recovery/recoveryPayment/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useDialogStore } from "@/stores/modules/dialogParams";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useBaseStore } from "@/stores/modules/baseInfo";
// import { processStatus } from "@/enums/commonOptions";

type Props = {
  lawFirmId?: any;
  projectId?: any;
  projectName?: any;
};

const props = withDefaults(defineProps<Props>(), {
  lawFirmId: "",
  projectId: "",
  projectName: ""
});

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
// 按钮权限
const { BUTTONS } = useAuthButtons();
// 表格配置项
const columns = reactive<ColumnProps<RecoveryPaymentDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    align: "left",
    search: { el: "input" }
  },
  {
    prop: "payAmount",
    label: "付款金额(元)",
    minWidth: 140,
    type: "money",
    align: "right"
  },
  {
    type: "date",
    prop: "payDate",
    label: "付款时间",
    minWidth: 120,
    search: {
      key: "payDateRange", //指定搜索的key
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
    prop: "lawyerName",
    label: "律所名称",
    minWidth: 200
  },
  {
    prop: "payee",
    label: "收款方",
    minWidth: 200
  },
  {
    prop: "receivingAccount",
    label: "收款账号",
    minWidth: 200
  },
  {
    prop: "receivingBank",
    label: "收款银行",
    minWidth: 120
  },
  // {
  //   prop: "flowState",
  //   label: "流程状态",
  //   minWidth: 90,
  //   enum: processStatus,
  // tag: true,
  //   search: { el: "select", props: { filterable: true } }
  //   // , enum: () => useBaseStore().findEnumByName("PAY_STATUS")
  // },
  {
    prop: "payExplain",
    label: "付款说明",
    minWidth: 200,
    isShow: false
  },

  // {
  //   prop: "creator",
  //   type: "user",
  //   label: "创建人",
  //   width: 120
  // },
  // {
  //   type: "date",
  //   prop: "createStamp",
  //   label: "创建时间",
  //   width: 120
  // },

  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 120,
    isShow: BUTTONS.value.add !== undefined && BUTTONS.value.add
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({
  lawyerId: props.lawFirmId,
  projectId: props.projectId
});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const paymentState = ref();
const getTableList = async (params: RecoveryPaymentPageRequest) => {
  const data = await useBaseStore().findEnumByName("PAY_STATUS");
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  newParams.payStatus = data && data.filter(item => item.itemCode == "PAY_STATUS_002")[0].id;
  paymentState.value = data && data.filter(item => item.itemCode == "PAY_STATUS_001")[0].id;

  return recoveryPayment().findAll(newParams);
};

const dialogRef = ref();
const openDrawer = async (title: string, row: any = {}) => {
  const data = await useBaseStore().findEnumByName("PAY_STATUS");

  const params = {
    dialogName: "recoveryPayment_createUpdate",
    title,
    showBtn: false,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? recoveryPayment().add : title === "编辑" ? recoveryPayment().update : undefined,
    getTableList: vzTableRef.value?.getTableList,
    payStatus: data && data.filter(item => item.itemCode == "PAY_STATUS_002")[0].id,
    projectId: props.projectId,
    projectName: props.projectName
  };
  useDialogStore().setDrawerProps(params); //存缓存
  dialogRef.value?.acceptParams(params);
};
</script>
