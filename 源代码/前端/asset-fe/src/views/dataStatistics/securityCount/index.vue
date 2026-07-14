<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam" :pagination="false">
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer(scope.row)">查看</el-button>
      </template>
      <template #tableHeader>
        <el-button type="primary" v-auth="'export'" @click="handleExport()">导出</el-button>
      </template>
      <template #manageInfo="scope">
        <div class="flx">
          在管项目数(个)：
          <div v-text="scope.row.manageNum"></div>
        </div>
        <div class="flx">
          累计追偿金额(万元)：
          <div v-currency="scope.row.totalCompensationMoney"></div>
        </div>
        <div class="flx">
          核销项目数(个)：
          <div v-text="scope.row.writeOffNum"></div>
        </div>
      </template>
      <template #ownInfo="scope">
        <div class="flx">
          回款目标(万元)：
          <div v-currency="scope.row.ownTarget"></div>
        </div>
        <div class="flx">
          回款金额(万元)：
          <div v-currency="scope.row.ownPaymentCollectionAmount"></div>
        </div>
        <div class="flx">
          回款完成率(%)：
          <div v-text="scope.row.ownDoneRate"></div>
        </div>
      </template>
      <template #entrustInfo="scope">
        <div class="flx">
          回款目标(万元)：
          <div v-currency="scope.row.entrustMoneyTarget"></div>
        </div>
        <div class="flx">
          回款金额(万元)：
          <div v-currency="scope.row.entrustMoneyAmount"></div>
        </div>
        <div class="flx">
          回款完成率(%)：
          <div v-text="scope.row.entrustDoneRate"></div>
        </div>
      </template>
      <template #collectionInfo="scope">
        <div class="flx">
          回款目标(万元)：
          <div v-currency="scope.row.totalPaymentCollectionTarget"></div>
        </div>
        <div class="flx">
          回款金额(万元)：
          <div v-currency="scope.row.totalPaymentCollection"></div>
        </div>
        <div class="flx">
          回款完成率(%)：
          <div v-text="scope.row.paymentCollectionRate"></div>
        </div>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="writeOff">
import { ref, reactive } from "vue";
// import { ProjectInfoPageRequest } from "@/api/modules/recovery/projectInfo/interface";
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
// 当前页面的数据类型 DTO是列表返回值的数据类型
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { ManageLedgerDTO } from "@/api/modules/dataStatistics/securityCount/interface";
import { ProjectInfoPageRequest } from "@/api/modules/recovery/projectInfo/interface";
import { manageLedger } from "@/api/modules/dataStatistics/securityCount/api";
import { useRouter } from "vue-router";
import { ElMessageBox } from "element-plus";
import { useDownload } from "@/hooks/useDownload";
// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
const router = useRouter();
// 按钮权限
const { BUTTONS } = useAuthButtons();

const handleExport = async () => {
  ElMessageBox.confirm("你确定导出吗？", "温馨提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
    draggable: true
  })
    .then(async () => {
      useDownload(manageLedger().export, "项目经理台账", vzTableRef.value?.searchParam);
      // ElMessage({
      //   type: "success",
      //   message: `导出成功!`
      // });
    })
    .catch(() => {
      // cancel operation
    });
};

const columns = reactive<ColumnProps<ManageLedgerDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60, fixed: "left" },
  {
    prop: "manage",
    label: "保全经理",
    type: "user",
    minWidth: 120,
    fixed: "left",
    search: {
      render: ({ searchParam }) => {
        return <vz-user-org v-model={searchParam.manage} org-code={"ZXD_ZCBQB"} dict-value={searchParam.manage} />;
      }
    }
  },
  {
    prop: "manageInfo",
    label: "在管项目信息",
    minWidth: 260
  },
  // {
  //   prop: "transferNum",
  //   label: "移交项目数(个)",
  //   minWidth: 140
  // },
  {
    prop: "ownInfo",
    label: "自主回款情况",
    minWidth: 260
  },
  {
    prop: "entrustInfo",
    label: "委托回款情况",
    minWidth: 260
  },
  {
    prop: "collectionInfo",
    label: "累计回款情况",
    minWidth: 260
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    minWidth: 100,
    isShow: BUTTONS.value.detail
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: ProjectInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return manageLedger().findManageAll(newParams);
};
// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (row: any = {}) => {
  router.push(`/dataStatistics/securityCount/detail/${row.manage}`);
};
</script>

<style scoped lang="scss"></style>
