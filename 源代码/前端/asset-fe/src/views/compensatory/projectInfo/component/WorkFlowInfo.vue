<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam" :pagination="false">
      <template #operation="scope">
        <el-button type="primary" link @click="openDrawer(scope.row)">查看流程图</el-button>
        <el-button type="primary" link @click="openDetail(scope.row)">查看</el-button>
      </template>
    </vz-table>
    <common-dialog ref="dialogRef"></common-dialog>
    <vz-flowchart ref="flowchartRef" />
  </div>
</template>

<script setup lang="tsx" name="workRegister">
import { ref, reactive } from "vue";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api"; // 项目信息
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { WorkRegisterPageRequest, WorkRegisterDTO } from "@/api/modules/compensatory/workRegister/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { processStatus } from "@/enums/commonOptions";
import { useDialogStore } from "@/stores/modules/dialogParams";
import vzFlowchart from "@/components/source/vzFlowchart.vue";
import { ElMessage } from "element-plus";
// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 表格配置项
const columns = reactive<ColumnProps<WorkRegisterDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "billId",
    label: "流程ID",
    width: 200
  },
  {
    prop: "billTypeName",
    label: "业务单据",
    minWidth: 120,
    align: "left",
    headerAlign: "center"
  },
  {
    enum: processStatus,
    tag: true,
    prop: "processStatus",
    label: "审批状态",
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
    width: 180
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 160,
    isShow: true
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});
const initParam = reactive({
  id: props.projectId
});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: WorkRegisterPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return projectInfo().findFlowAll(newParams);
};

const flowchartRef = ref();
const openDrawer = (row: any) => {
  flowchartRef.value.openDialog(row.billId);
};
const dialogRef = ref();
const openDetail = (row: Partial<any> = {}) => {
  let dialogNameUrl = "";
  if (row.billTypeCode == "RECOVERY_ADJUST_TRIAL") {
    dialogNameUrl = "recoveryAdjustTrial_createUpdate";
  } else if (row.billTypeCode == "RECOVERY_PAYMENT_COLLECTION") {
    dialogNameUrl = "recoveryPaymentCollection_createUpdate";
  } else if (row.billTypeCode == "RECOVERY_JUDGEMENT_REGISTER") {
    dialogNameUrl = "recoveryJudgement_createUpdate";
  } else if (row.billTypeCode == "FINAL") {
    dialogNameUrl = "recoveryLitigationDetails_createUpdate";
  } else if (row.billTypeCode == "OTHER") {
    dialogNameUrl = "recoveryLitigationDetails_createUpdate";
  } else if (row.billTypeCode == "DROP_LAWSUIT") {
    dialogNameUrl = "recoveryLitigationDetails_createUpdate";
  } else if (row.billTypeCode == "CLOSE_CASE") {
    dialogNameUrl = "recoveryLitigationDetails_createUpdate";
  } else if (row.billTypeCode == "PRESERVATION") {
    dialogNameUrl = "recoveryLitigationDetails_createUpdate";
  } else if (row.billTypeCode == "ALLOCATION_INFO") {
    dialogNameUrl = "allocationInfo_createUpdate";
  } else if (row.billTypeCode == "RECOVERY_JUDGEMENT") {
    dialogNameUrl = "recoveryJudgement_createUpdate";
  } else if (row.billTypeCode == "RECOVERY_EXECUTE") {
    dialogNameUrl = "recoveryExecute_createUpdate";
  } else if (row.billTypeCode == "RECOVERY_PAYMENT") {
    dialogNameUrl = "recoveryPayment_createUpdate";
  } else if (row.billTypeCode == "RECOVERY_PAYMENT_COLLECTION") {
    dialogNameUrl = "recoveryPaymentCollection_createUpdate";
  } else if (row.billTypeCode == "PROJECT_TRANSFER") {
    dialogNameUrl = "projectTransfer_createUpdate";
  } else if (row.billTypeCode == "LEASE_INFO") {
    dialogNameUrl = "leaseInfo_createUpdate";
  } else if (row.billTypeCode == "ASSET_TRANSFER") {
    dialogNameUrl = "assetTransfer_createUpdate";
  } else if (row.billTypeCode == "OPERATION_INFO") {
    dialogNameUrl = "operationInfo_createUpdate";
  }
  if (!dialogNameUrl) return ElMessage.warning("业务单据不存在，请检查！");
  const params = {
    dialogName: dialogNameUrl,
    title: "查看",
    showBtn: true,
    id: row?.billId,
    isView: true,
    api: undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  useDialogStore().setDrawerProps(params); //存缓存
  dialogRef.value?.acceptParams(params);
};
// RECOVERY_ADJUST_TRIAL调解审判
// RECOVERY_PAYMENT_COLLECTION项目回款
// RECOVERY_JUDGEMENT_REGISTER一审二审再审（不会拿这个做doType只是给详情审批过程返回）
// FINAL终本
// OTHER其他
// DROP_LAWSUIT撤诉
// CLOSE_CASE结案
// PRESERVATION保全
// ALLOCATION_INFO项目分配
// RECOVERY_JUDGEMENT立案一审
// PRESERVATION保全
// RECOVERY_JUDGEMENT_REGISTER一审二审再审（不会拿这个做doType只是给详情审批过程返回）
// DROP_LAWSUIT撤诉
// RECOVERY_ADJUST_TRIAL调解审判
// RECOVERY_EXECUTE执行登记
// FINAL终本
// OTHER其他
// CLOSE_CASE结案
// RECOVERY_PAYMENT项目付款
// RECOVERY_PAYMENT_COLLECTION项目回款
// PROJECT_TRANSFER项目移交
// LEASE_INFO租赁登记
// ASSET_TRANSFER资产转让
// OPERATION_INFO资产运营
</script>

<style scoped lang="scss"></style>
