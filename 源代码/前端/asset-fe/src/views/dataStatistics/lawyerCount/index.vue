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
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="writeOff">
import { ref, reactive } from "vue";
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
// 当前页面的数据类型 DTO是列表返回值的数据类型
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { LawyerLedgerDTO } from "@/api/modules/dataStatistics/lawyerCount/interface";
import { LawFirmInfoRequest } from "@/api/modules/source/lawFirmInfo/interface";
import { lawyerLedger } from "@/api/modules/dataStatistics/lawyerCount/api";
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
      useDownload(lawyerLedger().export, "律所台账", vzTableRef.value?.searchParam);
      // ElMessage({
      //   type: "success",
      //   message: `导出成功!`
      // });
    })
    .catch(() => {
      // cancel operation
    });
};

const columns = reactive<ColumnProps<LawyerLedgerDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60, fixed: "left" },
  {
    prop: "name",
    label: "律所名称",
    minWidth: 300,
    fixed: "left",
    align: "left",
    headerAlign: "center",
    search: {
      render: ({ searchParam }) => {
        return <vz-law v-model={searchParam.name} default-value={searchParam.name} />;
      }
    }
  },
  {
    prop: "proNum",
    label: "代理项目数",
    minWidth: 120
  },
  {
    type: "money",
    prop: "proMoney",
    label: "代理项目金额(元)",
    minWidth: 200,
    align: "right",
    headerAlign: "center"
  },
  {
    prop: "proAmount",
    label: "回款金额(元)",
    minWidth: 200,
    align: "right",
    headerAlign: "center",
    type: "money"
  },

  {
    prop: "collectionRatio",
    label: "回款率(%)",
    minWidth: 120
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
const getTableList = (params: LawFirmInfoRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return lawyerLedger().findAll(newParams);
};
// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (row: any = {}) => {
  router.push(`/source/lawFirmInfo/detail/${row.id}`);
};
// const @/components/VzTable/interface = ref()
</script>

<style scoped lang="scss"></style>
