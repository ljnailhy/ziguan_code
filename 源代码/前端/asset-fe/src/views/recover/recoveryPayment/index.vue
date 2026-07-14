<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
      </template>
      <!-- 合并列表数据 s -->
      <template #skInfo="scope">
        <div class="flx">收款方：{{ scope.row.payee }}</div>
        <div class="flx">收款银行：{{ scope.row.receivingAccount }}</div>
        <div class="flx">收款账号：{{ scope.row.receivingBank }}</div>
      </template>
      <template #fkInfo="scope">
        <div class="flx">
          付款金额：
          <div v-currency="scope.row.payAmount"></div>
        </div>
        <div class="flx">
          付款时间：
          <dict-date :date="scope.row.payDate" format="YYYY/MM/DD"></dict-date>
        </div>
      </template>
      <template #creatorInfo="scope">
        <div class="flx">
          创建人：
          <dict-user-name :user-code="scope.row.creator"></dict-user-name>
        </div>
        <div class="flx">
          创建时间：
          <dict-date :date="scope.row.createStamp" format="YYYY/MM/DD HH:mm:ss"></dict-date>
        </div>
      </template>
      <!-- 合并列表数据 e -->
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button
          type="primary"
          v-auth="'edit'"
          link
          @click="openDrawer('编辑', scope.row)"
          v-if="'draft' == scope.row.flowState && scope.row.payStatus !== paymentState"
        >
          编辑
        </el-button>
        <el-button
          type="primary"
          v-auth="'delete'"
          link
          @click="deleteAccount(scope.row)"
          v-if="'draft' == scope.row.flowState && scope.row.payStatus !== paymentState"
        >
          删除
        </el-button>
        <el-button
          type="primary"
          v-auth="'flowchart'"
          link
          @click="openFlowchart(scope.row)"
          v-if="'draft' != scope.row.flowState"
        >
          查看流程图
        </el-button>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
    <vz-flowchart ref="flowchartRef" />
  </div>
</template>

<script setup lang="tsx" name="recoveryPayment">
import { ref, reactive } from "vue";
import { recoveryPayment } from "@/api/modules/recovery/recoveryPayment/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { RecoveryPaymentPageRequest, RecoveryPaymentDTO } from "@/api/modules/recovery/recoveryPayment/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import VzFlowchart from "@/components/source/vzFlowchart.vue";
import { processStatus } from "@/enums/commonOptions";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { useBaseStore } from "@/stores/modules/baseInfo";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();
// vzTable 实例
const flowchartRef = ref();
const openFlowchart = (row: EmptyObjectType) => {
  flowchartRef.value.openDialog(row.id);
};
// 表格配置项
const columns = reactive<ColumnProps<RecoveryPaymentDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    align: "left",
    search: { el: "input" }
  },
  {
    prop: "fkInfo",
    label: "付款信息",
    minWidth: 200
  },
  // {
  //   prop: "payAmount",
  //   label: "付款金额(元)",
  //   minWidth: 140,
  //   type: "money"
  // },
  // {
  //   prop: "payee",
  //   label: "收款方",
  //   minWidth: 200,
  //   search: { el: "input" }
  // },
  // {
  //   prop: "receivingAccount",
  //   label: "收款账号",
  //   minWidth: 200
  // },
  // {
  //   prop: "receivingBank",
  //   label: "收款银行",
  //   minWidth: 120
  // },
  {
    prop: "skInfo",
    label: "收款信息",
    minWidth: 250
  },
  {
    prop: "lawyerName",
    label: "律所名称",
    minWidth: 180,
    search: { el: "input" }
  },
  {
    prop: "flowState",
    label: "流程状态",
    enum: processStatus,
    tag: true,
    search: { el: "select", props: { filterable: true } },
    width: 90
  },
  {
    // type: "date",
    prop: "payDate",
    label: "付款时间",
    minWidth: 120,
    isShow: false,
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
    prop: "creatorInfo",
    label: "创建信息",
    minWidth: 230
  },
  // {
  //   prop: "payExplain",
  //   label: "付款说明",
  //   minWidth: 200,
  //   isShow: false
  // },
  // {
  //   prop: "remarks",
  //   label: "备注",
  //   isShow: false,
  //   minWidth: 200
  // },
  // {
  //   prop: "creator",
  //   type: "user",
  //   label: "创建人",
  //   width: 120
  // },
  // {
  //   type: "datetime",
  //   prop: "createStamp",
  //   label: "创建时间",
  //   width: 200
  // },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 160,
    isShow:
      (BUTTONS.value.edit !== undefined && BUTTONS.value.edit) ||
      (BUTTONS.value.detail !== undefined && BUTTONS.value.detail) ||
      (BUTTONS.value.delete !== undefined && BUTTONS.value.delete)
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const paymentState = ref();
const getTableList = async (params: RecoveryPaymentPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  const paymentStateList = await useBaseStore().findEnumByName("PAY_STATUS");
  const state = (paymentState.value = paymentStateList
    .filter(item => item.itemCode != "PAY_STATUS_001") // 筛选条件
    .map(item => item.id));
  paymentState.value = state[0];
  return recoveryPayment().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: RecoveryPaymentDTO) => {
  await useHandleData(recoveryPayment().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<RecoveryPaymentDTO> = {}) => {
  const params = {
    dialogName: "recoveryPayment_createUpdate",
    title,
    showBtn: true,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? recoveryPayment().add : title === "编辑" ? recoveryPayment().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
</script>

<style scoped lang="scss"></style>
