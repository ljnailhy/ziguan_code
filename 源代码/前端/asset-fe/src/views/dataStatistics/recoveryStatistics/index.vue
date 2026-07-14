<template>
  <div class="table-box">
    <vz-table
      ref="vzTableRef"
      :columns="columns"
      :request-api="getTableList"
      :span-method="objectSpanMethod"
      :init-param="initParam"
      :show-summary="false"
      :tree-props="{ children: 'reveInfoList', hasChildren: 'true' }"
    >
      <template #tableHeader>
        <el-button type="primary" v-auth="'export'" @click="handleExport()">导出</el-button>
      </template>
    </vz-table>
  </div>
</template>

<script setup lang="tsx" name="writeOff">
import { ref, reactive } from "vue";
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { recoveryLedger } from "@/api/modules/dataStatistics/recoveryStatistics/api";
import { ElMessageBox } from "element-plus";
import { useDownload } from "@/hooks/useDownload";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
const handleExport = async () => {
  ElMessageBox.confirm("你确定导出吗？", "温馨提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
    draggable: true
  })
    .then(async () => {
      await useDownload(recoveryLedger().exportCompensatoryCount, "追偿情况统计", vzTableRef.value?.searchParam);
    })
    .catch(() => {
      // cancel operation
    });
};
// 表格配置项
const columns = reactive<ColumnProps<any>[]>([
  // { type: "index", label: "序号", width: 60 },
  {
    label: "时间",
    prop: "month",
    width: 200,
    fixed: "left",
    _children: [
      { prop: "season", label: "季度", width: 100 },
      { prop: "month", label: "月份", width: 100 }
    ]
  },

  {
    label: "年份",
    prop: "request",
    width: 200,
    isShow: false,
    search: {
      el: "date-picker",
      span: 1,
      defaultValue: 2024,
      props: {
        type: "year",
        format: "YYYY",
        valueFormat: "YYYY",
        defaultValue: ["2024"]
      }
    }
  },
  {
    prop: "base",
    label: "追偿",

    _children: [
      { prop: "lastYearCompensatoryMoney", money: true, label: "上年末追偿金额", width: 200 },
      { prop: "thisYearCompensatoryMoney", money: true, label: "本年新增追偿金额", width: 200 },
      { prop: "totalCompensatoryMoney", money: true, label: "累计追偿金额", width: 200 },
      { prop: "historyCompensatoryMoney", money: true, label: "其中：历史风险项目（2017年4月28日之前介入项目）", width: 200 },
      { prop: "addCompensatoryMoney", money: true, label: "其中：新增项目（2017年4月28日之后介入项目）", width: 200 }
    ]
  },
  {
    prop: "return",
    label: "回款",
    _children: [
      { prop: "lastYearCollectionMoney", money: true, label: "上年末回款金额", width: 200 },
      { prop: "thisYearCollectionMoney", money: true, label: "本年新增回款金额", width: 200 },
      { prop: "totalCollectionMoney", money: true, label: "累计回款金额", width: 200 },
      {
        prop: "historyCollectionMoney",
        money: true,
        label: "其中：历史风险项目追偿回款（2017年4月28日之前介入项目）",
        width: 200
      },
      { prop: "addCollectionMoney", money: true, label: "其中：新增项目追偿回款（2017年4月28日之后介入项目）", width: 200 }
    ]
  },
  {
    prop: "yue",
    label: "余额",
    _children: [
      { prop: "lastYearBalance", money: true, label: "上年末追偿余额", width: 200 },
      { prop: "totalBalance", money: true, label: "追偿余额", width: 200 }
    ]
  }
]);

// 列合并
const objectSpanMethod = ({ rowIndex, columnIndex }: any) => {
  if (columnIndex === 0) {
    if (rowIndex % 4 === 0) return { rowspan: 4, colspan: 1 };
    else return { rowspan: 0, colspan: 0 };
  }
};

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: any) => {
  let newParams = JSON.parse(JSON.stringify(params));
  return recoveryLedger().findAll(newParams);
};
</script>

<style scoped lang="scss"></style>
