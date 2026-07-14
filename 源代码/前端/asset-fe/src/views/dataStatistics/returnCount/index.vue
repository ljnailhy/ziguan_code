<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <template #tableHeader>
        <el-button type="primary" v-auth="'export'" @click="handleExport()">导出</el-button>
      </template>
    </vz-table>
    <!--    <common-dialog ref="dialogRef"></common-dialog>-->
  </div>
</template>

<script setup lang="tsx" name="writeOff">
import { ref, reactive } from "vue";
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
// 当前页面的数据类型 DTO是列表返回值的数据类型
import { ProjectInfoPageRequest } from "@/api/modules/recovery/projectInfo/interface";
import { collectionAscriptionOptions, collectionSignOptions } from "@/api/modules/recovery/recoveryPaymentCollection/interface";
import { PaymentCollectionPageDTO } from "@/api/modules/dataStatistics/returnCount/interface";
import { paymentCollectionLedger } from "@/api/modules/dataStatistics/returnCount/api";
import { useDownload } from "@/hooks/useDownload";
import { ElMessageBox } from "element-plus";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限

const handleExport = async () => {
  ElMessageBox.confirm("你确定导出吗？", "温馨提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
    draggable: true
  })
    .then(async () => {
      useDownload(paymentCollectionLedger().export, "回款台账", vzTableRef.value?.searchParam);
    })
    .catch(() => {
      // cancel operation
    });
};

interface ProjectPropsType {
  isShowBtn?: boolean;
}
const props = withDefaults(defineProps<ProjectPropsType>(), {
  isShowBtn: true
});
const columns = reactive<ColumnProps<PaymentCollectionPageDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60, fixed: "left" },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    align: "left",
    headerAlign: "center",
    fixed: "left",
    search: { el: "input" }
  },
  {
    prop: "documentNumber",
    label: "证件号",
    minWidth: 180
  },
  {
    prop: "belongCity",
    label: "所属区域",
    minWidth: 200
  },
  {
    prop: "collectionSign",
    label: "回款标记",
    minWidth: 120,
    search: { el: "select", props: { filterable: true } },
    enum: collectionSignOptions
  },
  {
    prop: "collectionAmount",
    label: "回款金额(元)",
    minWidth: 120,
    type: "money"
  },
  {
    type: "date",
    prop: "collectionDate",
    label: "回款时间",
    minWidth: 120,
    search: {
      key: "collectionDateRange", //指定搜索的key
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
    prop: "isCollectionHistorical",
    label: "是否历史代偿",
    minWidth: 120,
    type: "boolean"
  },
  {
    prop: "collectionAscription",
    label: "项目回款归属",
    minWidth: 120,
    search: { el: "select", props: { filterable: true } },
    enum: collectionAscriptionOptions
  },
  {
    prop: "lawyerName",
    label: "回款律所",
    minWidth: 160,
    search: {
      render: ({ searchParam }) => {
        return <vz-law v-model={searchParam.name} default-value={searchParam.name} />;
      }
    }
  },
  {
    prop: "collectionSummary",
    label: "回款摘要",
    minWidth: 200
  },
  {
    prop: "compensatoryCash",
    label: "代偿金额(元)",
    minWidth: 120
  },
  {
    prop: "defaultCash",
    label: "违约金(元)",
    minWidth: 120
  },
  {
    prop: "interest",
    label: "利息(元)",
    minWidth: 120
  },
  {
    prop: "otherFee",
    label: "其他费用(元)",
    minWidth: 120
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({
  isShowBtn: props.isShowBtn
});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: ProjectInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return paymentCollectionLedger().findPaymentCollectionAll(newParams);
};
// 打开 dialog(新增、查看、编辑)
// const dialogRef = ref<any>(null);
</script>

<style scoped lang="scss"></style>
