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
      <!-- 合并列表数据 s -->
      <template #zcInfo="scope">
        <div class="flx" style="font-weight: bold">{{ scope.row.propertyName }}</div>
        <div class="flx">
          资产分类：<dict-name dict-type="PROPERTY_LARGE_CATEGORY" :dict-value="scope.row.type"></dict-name>-<dict-name
            dict-type="PROPERTY_TYPE"
            :dict-value="scope.row.propertyType"
          ></dict-name>
        </div>
        <div class="flx">资产面积：{{ scope.row.area ? scope.row.area + " m²" : "--" }}</div>
        <div class="flx">资产状态：<dict-enum :options="propertyStateOptions" :value="scope.row.propertyState"></dict-enum></div>
        <div class="flx">资产标签：<dict-name dict-type="PROPERTY_TAG" :dict-value="scope.row.propertyTag" /></div>
      </template>
      <template #moneyInfo="scope">
        <div class="flx">
          资产净值：
          <div v-currency="scope.row.netWorth"></div>
        </div>
        <div class="flx">
          资产原值：
          <div v-currency="scope.row.originalValue"></div>
        </div>
        <div class="flx">
          资产抵债价格：
          <div v-currency="scope.row.debtRepaymentMoney"></div>
        </div>
        <div class="flx">
          资产处置价格：
          <div v-currency="scope.row.disposalPrice"></div>
        </div>
        <div class="flx">
          资产盈亏：
          <div v-currency="scope.row.profitAndLoss"></div>
        </div>
      </template>
      <template #feeInfo="scope">
        <div class="flx">
          运营费用：
          <div v-currency="scope.row.yyMoney"></div>
        </div>
        <div class="flx">
          租金收入：
          <div v-currency="scope.row.zlMoney"></div>
        </div>
        <div class="flx">
          转让收入：
          <div v-currency="scope.row.zrMoney"></div>
        </div>
      </template>
      <template #sourceInfo="scope">
        <div class="flx">来源方式：<dict-name dict-type="SOURCE_TYPE" :dict-value="scope.row.sourceType"></dict-name></div>
        <div class="flx">来源项目：{{ scope.row.projectName }}</div>
        <div class="flx">
          所在区域：
          <dict-area :value="scope.row.province"></dict-area>
          <dict-area :value="scope.row.city"></dict-area>
          <dict-area :value="scope.row.district"></dict-area>
          <!-- {{ scope.row.address }} -->
        </div>
        <div class="flx">
          资产获得时间：
          <dict-date :date="scope.row.assertTime" format="YYYY/MM/DD"></dict-date>
        </div>
      </template>
      <template #creatorInfo="scope">
        <div class="flx">
          跟进人：
          <dict-user-name :user-code="scope.row.followUpPerson"></dict-user-name>
        </div>
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

      <template #city="scope">
        <div class="flx">
          <dict-area :value="scope.row.province"></dict-area>- <dict-area :value="scope.row.city"></dict-area>-
          <dict-area :value="scope.row.district"></dict-area>
        </div>
      </template>
      <!-- 表格 header 按钮 -->
      <template #operation="scope">
        <el-button type="primary" link @click="openDrawer(scope.row)">查看</el-button>
      </template>
      <template #tableHeader>
        <el-button type="primary" v-auth="'export'" @click="handleExport()">导出</el-button>
      </template>
      <!-- <template #projectId="scope">
        <vzProjectInfo :default-value="scope.row.projectId" :disabled="true" select-type="radio"></vzProjectInfo>
      </template> -->
      <template #propertyTag="scope">
        <dict-name dict-type="PROPERTY_TAG" :dict-value="scope.row.propertyTag"></dict-name>
      </template>
      <template #propertyEndDate="scope">
        <dict-date :date="scope.row.propertyEndDate" format="YYYY-MM-DD"></dict-date>
      </template>
      <template #propertyTransferOwnership="scope">
        <dict-date :date="scope.row.propertyTransferOwnership" format="YYYY-MM-DD"></dict-date>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="propertyInfo">
import { ref, reactive } from "vue";
import { propertyInfo } from "@/api/modules/property/propertyInfo/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { PropertyInfoPageRequest, PropertyInfoDTO, propertyStateOptions } from "@/api/modules/property/propertyInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useDownload } from "@/hooks/useDownload";
import { ElMessageBox } from "element-plus";
import { useRouter } from "vue-router";
// import vzProjectInfo from "@/components/source/vzProjectInfo.vue";

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
      useDownload(propertyInfo().export, "资产台账", vzTableRef.value?.searchParam);
    })
    .catch(() => {
      // cancel operation
    });
};

const emit = defineEmits<{
  rowdoubleClick: [{ row?: any; column?: any; event?: Event }];
  rowClick: [{ row?: any; column?: any; event?: Event }];
}>();

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
const columns = reactive<ColumnProps<PropertyInfoDTO>[]>([
  // { type: props.selectType, fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60, fixed: "left" },
  {
    prop: "zcInfo",
    label: "资产信息",
    fixed: "left",
    minWidth: 250
  },
  {
    prop: "moneyInfo",
    label: "资产价值",
    minWidth: 250
  },
  {
    prop: "feeInfo",
    label: "资产收支动态",
    minWidth: 220
  },
  {
    prop: "sourceInfo",
    label: "资产来源",
    minWidth: 300
  },
  {
    prop: "creatorInfo",
    label: "跟进人信息",
    minWidth: 230
  },

  {
    prop: "propertyName",
    label: "资产名称",
    width: 200,
    headerAlign: "center",
    align: "left",
    isShow: false,
    search: { el: "input" }
  },
  {
    prop: "propertyType",
    label: "资产分类",
    width: 120,
    isShow: false,
    enum: () => useBaseStore().findEnumByName("PROPERTY_TYPE"),
    search: { el: "select", props: { filterable: true } }
  },
  {
    prop: "sourceType",
    label: "来源方式",
    width: 120,
    isShow: false,
    enum: () => useBaseStore().findEnumByName("SOURCE_TYPE"),
    search: { el: "select", props: { filterable: true } }
  },
  {
    prop: "projectName",
    label: "来源项目",
    width: 140,
    isShow: false,
    search: { el: "input" }
  },
  {
    type: "area",
    prop: "city",
    label: "所在区域",
    isShow: false,
    search: { el: "input" },
    minWidth: 100
  },
  {
    prop: "accessWay",
    label: "取得方式",
    width: 140,
    isShow: false,
    search: { el: "select", props: { filterable: true } },
    enum: () => useBaseStore().findEnumByName("ACCESS_WAY")
  },
  {
    prop: "propertyState",
    label: "资产状态",
    enum: propertyStateOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    isShow: false,
    width: 180
  },
  {
    type: "date",
    prop: "propertyDate",
    label: "运营时间",
    minWidth: 200,
    isShow: false,
    search: {
      key: "propertyDateRange", //指定搜索的key
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
    prop: "operation",
    label: "操作",
    fixed: "right",
    minWidth: 90,
    isShow: BUTTONS.value.detail
  }
]);
const openDrawer = (row: any = {}) => {
  router.push(`/propertyInfo/detail/${row.id}`);
};

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: PropertyInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return propertyInfo().findAll(newParams);
};
</script>

<style scoped lang="scss"></style>
