<template>
  <div class="table-box">
    <vz-table
      ref="vzTableRef"
      @rowdouble-click="rowdoubleClick"
      :columns="columns"
      :request-api="getTableList"
      :init-param="initParam"
    >
      <template #compensationDate="scope">
        <dict-date :date="Number(scope.row.compensationDate)" format="YYYY-MM-DD"> </dict-date>
      </template>
      <template #transferDate="scope">
        <dict-date :date="Number(scope.row.transferDate)" format="YYYY-MM-DD"> </dict-date>
      </template>
      <template #compensationMoney="scope">
        <div v-currency="scope.row['compensationMoney']"></div>
      </template>
      <template #interestLitigation="scope">
        <div v-currency="scope.row['interestLitigation']"></div>
      </template>
      <template #liquidatedDamagesLitigation="scope">
        <div v-currency="scope.row['liquidatedDamagesLitigation']"></div>
      </template>
      <template #otherFeesLitigation="scope">
        <div v-currency="scope.row['otherFeesLitigation']"></div>
      </template>
      <template #compensationAmountTrial="scope">
        <div v-currency="scope.row['compensationAmountTrial']"></div>
      </template>
      <template #interestTrial="scope">
        <div v-currency="scope.row['interestTrial']"></div>
      </template>
      <template #liquidatedDamagesTrial="scope">
        <div v-currency="scope.row['liquidatedDamagesTrial']"></div>
      </template>
      <template #otherFeesTrial="scope">
        <div v-currency="scope.row['otherFeesTrial']"></div>
      </template>
      <template #cashMoney="scope">
        <div v-currency="scope.row['cashMoney']"></div>
      </template>
      <template #reGuaranteeMoney="scope">
        <div v-currency="scope.row['reGuaranteeMoney']"></div>
      </template>
      <template #mortgageMoney="scope">
        <div v-currency="scope.row['mortgageMoney']"></div>
      </template>
      <template #silverBillMoney="scope">
        <div v-currency="scope.row['silverBillMoney']"></div>
      </template>
      <template #belongCity="scope">
        <div class="flx">
          <dict-area :value="scope.row.belongProvince"></dict-area>- <dict-area :value="scope.row.belongCity"></dict-area>-
          <dict-area :value="scope.row.belongDistrict"></dict-area>
        </div>
      </template>
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'export'" @click="handleExport()">导出</el-button>
      </template>
    </vz-table>
  </div>
</template>

<script setup lang="tsx" name="projectInfo">
import { ref, reactive } from "vue";
import { projectInfoExt } from "@/api/modules/recovery/projectInfoExt/api";
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { ProjectInfoPageRequest, ProjectInfoDTO } from "@/api/modules/recovery/projectInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useBaseStore } from "@/stores/modules/baseInfo";
import { adjustTrialTypeOptions } from "@/api/modules/proceeding/recoveryAdjustTrial/interface";
import { useDownload } from "@/hooks/useDownload";
import { ElMessageBox } from "element-plus";

//引入组件

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
      useDownload(projectInfoExt().exportLitigationLedgers, "诉讼台账", vzTableRef.value?.searchParam);
    })
    .catch(() => {
      // cancel operation
    });
};

const emit = defineEmits<{
  rowdoubleClick: [{ row?: any; column?: any; event?: Event }];
}>();
const rowdoubleClick = (row: any) => {
  emit("rowdoubleClick", row);
};

interface ProjectPropsType {
  filterData?: { [key: string]: any };
  isShowBtn?: boolean;
}

const props = withDefaults(defineProps<ProjectPropsType>(), {
  filterData: () => ({}),
  isShowBtn: false
});

// 表格配置项
const columns = reactive<ColumnProps<ProjectInfoDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60, fixed: "left" },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    fixed: "left",
    align: "left",
    headerAlign: "center",
    search: { el: "input" }
  },
  {
    label: "代偿项目基本情况",
    prop: "base",
    _children: [
      // {
      //   prop: "type",
      //   label: "大类",
      //   minWidth: 160,
      //   // search: { el: "select", props: { filterable: true } }
      //   enum: () => useBaseStore().findEnumByName("TYPE")
      // },
      // {
      //   prop: "product",
      //   label: "产品名称",
      //   minWidth: 160
      //   // search: { el: "select", props: { filterable: true } }
      //   // enum: () => useBaseStore().findEnumByName("PRODUCT_NAME")
      // },
      {
        prop: "industryType",
        label: "行业分类",
        minWidth: 200,
        search: { el: "select", props: { filterable: true } },
        enum: () => useBaseStore().findEnumByName("INDUSTRY_GXB")
      },
      {
        prop: "projectState",
        label: "项目状态",
        minWidth: 200,
        search: { el: "select", props: { filterable: true } },
        enum: () => useBaseStore().findEnumByName("PROJECT_STATE")
      },
      {
        prop: "manage",
        label: "保全经理",
        minWidth: 100,
        search: {
          render: ({ searchParam }) => {
            return <vz-user v-model:model={searchParam.manage} dict-value={searchParam.manage} />;
          }
        }
      },
      {
        prop: "lawFirmName",
        label: "律所",
        minWidth: 200
      },
      {
        type: "area",
        prop: "belongCity",
        label: "所属区域",
        minWidth: 100
      },
      {
        type: "date",
        prop: "compensationDate",
        label: "代偿时间",
        minWidth: 120,
        search: {
          key: "compensationDateRange", //指定搜索的key
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
        prop: "compensationMoney",
        label: "代偿金额(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      },
      {
        type: "date",
        prop: "transferDate",
        label: "移交至保全部日期",
        minWidth: 160
      }
    ]
  },
  {
    label: "诉讼请求",
    prop: "base",
    _children: [
      // {
      //   prop: "compensationAmountLitigation",
      //   label: "代偿金额(元)",
      //   minWidth: 120,
      //   type: "money",
      //   align: "right"
      // },
      {
        prop: "interestLitigation",
        label: "利息(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      },
      {
        prop: "liquidatedDamagesLitigation",
        label: "违约金(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      },
      {
        prop: "otherFeesLitigation",
        label: "其他费用(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      }
    ]
  },
  {
    label: "审批结果",
    prop: "base",
    _children: [
      {
        prop: "adjustTrialType",
        label: "调解/判决类型",
        enum: adjustTrialTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
        // search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
        // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
        width: 180
      },
      {
        prop: "compensationAmountTrial",
        label: "代偿金额(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      },
      {
        prop: "interestTrial",
        label: "利息(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      },
      {
        prop: "liquidatedDamagesTrial",
        label: "违约金(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      },
      {
        prop: "otherFeesTrial",
        label: "其他费用(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      }
    ]
  },
  {
    label: "回款信息",
    prop: "base",
    _children: [
      {
        prop: "cashMoney",
        label: "现金(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      },
      {
        prop: "reGuaranteeMoney",
        label: "再担保(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      },
      {
        prop: "mortgageMoney",
        label: "抵债(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      },
      {
        prop: "silverBillMoney",
        label: "银票(元)",
        minWidth: 120,
        type: "money",
        align: "right"
      }
    ]
  }
]);
// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)

const initParam = reactive({ ...props.filterData });

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: ProjectInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return projectInfoExt().litigationLedger(newParams);
};
// 暴露给父组件使用
defineExpose({ vzTableRef });
</script>

<style scoped lang="scss"></style>
