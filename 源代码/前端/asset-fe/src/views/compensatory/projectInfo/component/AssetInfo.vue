<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <template #operation="scope">
        <el-button type="primary" link @click="openDrawer(scope.row)">查看</el-button>
      </template>
    </vz-table>
  </div>
</template>

<script setup lang="tsx" name="workRegister">
import { ref, reactive } from "vue";
import { propertyInfo } from "@/api/modules/property/propertyInfo/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { PropertyInfoPageRequest, PropertyInfoDTO, propertyStateOptions } from "@/api/modules/property/propertyInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useRouter } from "vue-router";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});
const initParam = reactive({
  projectId: props.projectId
});
// 表格配置项
const columns = reactive<ColumnProps<PropertyInfoDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "propertyName",
    label: "资产名称",
    width: 200,
    headerAlign: "center",
    align: "left",
    search: { el: "input" }
  },
  {
    prop: "type",
    label: "大类",
    width: 120,
    enum: () => useBaseStore().findEnumByName("PROPERTY_LARGE_CATEGORY")
  },
  {
    prop: "propertyType",
    label: "资产分类",
    width: 120,
    enum: () => useBaseStore().findEnumByName("PROPERTY_TYPE")
  },
  {
    prop: "netWorth",
    label: "资产净值(元)",
    minWidth: 120,
    type: "money",
    align: "right"
  },
  {
    prop: "debtRepaymentMoney",
    label: "资产抵债价格(元)",
    minWidth: 180,
    type: "money",
    align: "right"
  },

  {
    prop: "propertyState",
    label: "资产状态",
    enum: propertyStateOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 180
  },

  {
    type: "area",
    prop: "city",
    label: "所在区域",
    minWidth: 100
  },
  {
    prop: "area",
    label: "面积",
    width: 140
  },
  {
    prop: "accessWay",
    label: "资产取得方式",
    width: 120,
    enum: () => useBaseStore().findEnumByName("ACCESS_WAY")
  },

  {
    prop: "propertyCode",
    label: "产权证号",
    width: 140,
    search: { el: "input" }
  },
  {
    type: "date",
    prop: "propertyEndDate",
    label: "权证到期日",
    minWidth: 120,
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
    type: "date",
    prop: "propertyTransferOwnership",
    label: "资产过户日期",
    minWidth: 120
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 90
  }
]);

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: PropertyInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return propertyInfo().findAll(newParams);
};

const router = useRouter();
const openDrawer = (row: Partial<PropertyInfoDTO> = {}) => {
  router.push(`/propertyInfo/detail/${row.id}`);
};
</script>

<style scoped lang="scss"></style>
