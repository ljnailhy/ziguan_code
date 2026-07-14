<template>
  <div class="table-box">
    <vz-table
      ref="vzTableRef"
      highlight-current-row
      :columns="columns"
      @rowdouble-click="rowdoubleClick"
      @row-click="rowClick"
      :request-api="getTableList"
      :init-param="initParam"
    >
      <template #propertyEndDate="scope">
        <dict-date :date="scope.row.propertyEndDate" format="YYYY/MM/DD"></dict-date>
      </template>
      <!-- 合并列表数据 s -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'export'" @click="handleExport()">导出</el-button>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="propertyInfo">
import { ref, reactive } from "vue";
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { PropertyInfoPageRequest } from "@/api/modules/property/propertyInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型

import { AssetDetailLedgerDTO } from "@/api/modules/dataStatistics/assetDetailCount/interface";
import { assetDetailLedger } from "@/api/modules/dataStatistics/assetDetailCount/api";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { ElMessageBox } from "element-plus";
import { useDownload } from "@/hooks/useDownload";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
// const router = useRouter();
// 按钮权限
// const { BUTTONS } = useAuthButtons();

// const handleExport = async () => {
//   ElMessageBox.confirm("你确定导出吗？", "温馨提示", {
//     confirmButtonText: "确定",
//     cancelButtonText: "取消",
//     type: "warning",
//     draggable: true
//   })
//     .then(async () => {
//       useDownload(propertyInfo().export, "资产台账", vzTableRef.value?.searchParam);
//     })
//     .catch(() => {
//       // cancel operation
//     });
// };

const emit = defineEmits<{
  rowdoubleClick: [{ row?: any; column?: any; event?: Event }];
  rowClick: [{ row?: any; column?: any; event?: Event }];
}>();
const handleExport = async () => {
  ElMessageBox.confirm("你确定导出吗？", "温馨提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
    draggable: true
  })
    .then(async () => {
      useDownload(assetDetailLedger().export, "资产明细台账", vzTableRef.value?.searchParam);
    })
    .catch(() => {
      // cancel operation
    });
};
// interface ProjectPropsType {
//   filterData?: { [key: string]: any };
//   isShowBtn?: boolean;
//   selectType?: TypeProps;
// }

// const props = withDefaults(defineProps<ProjectPropsType>(), {
//   filterData: () => ({}),
//   isShowBtn: true,
//   selectType: "selection"
// });

const rowdoubleClick = (row: any) => {
  vzTableRef.value!.radio = row.id;
  emit("rowdoubleClick", row);
};

const rowClick = (row: any) => {
  vzTableRef.value!.radio = row.id;
  emit("rowClick", row);
};

// 表格配置项
const columns = reactive<ColumnProps<AssetDetailLedgerDTO>[]>([
  // { type: props.selectType, fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60, fixed: "left" },
  {
    prop: "affiliatedUnit",
    label: "所属单位",
    width: 160,
    type: "org"
  },
  {
    prop: "propertyName",
    label: "资产名称",
    width: 160,
    search: { el: "input" }
  },
  {
    prop: "area",
    label: "面积(㎡)",
    width: 120
  },
  {
    prop: "address",
    label: "地理位置",
    width: 160
  },
  {
    prop: "propertyOwner",
    label: "产权所有人",
    width: 160
  },
  {
    prop: "propertyEndDate",
    label: "产权证日期",
    width: 160,
    search: {
      key: "propertyEndDateRange", //指定搜索的key
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
    prop: "propertyCode",
    label: "产权证号",
    width: 160
  },
  {
    prop: "projectName",
    label: "资产来源",
    width: 200,
    search: { el: "input" }
  },
  {
    prop: "originalValue",
    label: "账面原值",
    width: 120
  },
  {
    prop: "assetUnitState",
    label: "资产状态",
    enum: () => useBaseStore().findEnumByName("ASSET_UNIT_STATE"),
    width: 120,
    search: { el: "select" }
  },
  {
    prop: "landUseNature",
    label: "用地性质",
    enum: () => useBaseStore().findEnumByName("LAND_USE_NATURE"),
    width: 100
  },
  {
    prop: "transferOwnershipRemark",
    label: "低效闲置原因",
    width: 200
  },
  {
    prop: "propertyMoney",
    label: "当年盘活回收资金",
    width: 160
  }
]);
// const openDrawer = (row: any = {}) => {
//   router.push(`/propertyInfo/detail/${row.id}`);
// };

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: PropertyInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return assetDetailLedger().findAll(newParams);
};
</script>

<style scoped lang="scss"></style>
