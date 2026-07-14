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
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-if="isShowBtn" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
        <el-button type="primary" v-if="isShowBtn" v-auth="'add'" @click="importApi()">导入</el-button>
      </template>
      <!-- 合并列表数据 s -->
      <template #zcInfo="scope">
        <div class="flx" style="font-weight: bold">{{ scope.row.propertyName }}</div>
        <div class="flx">资产分类：<dict-name dict-type="PROPERTY_TYPE" :dict-value="scope.row.propertyType"></dict-name></div>
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
      <template #sourceInfo="scope">
        <div class="flx">来源方式：<dict-name dict-type="SOURCE_TYPE" :dict-value="scope.row.sourceType"></dict-name></div>
        <div class="flx">来源项目：{{ scope.row.projectName }}</div>
        <div class="flx">
          所在区域：
          <dict-area :value="scope.row.province"></dict-area>- <dict-area :value="scope.row.city"></dict-area>-
          <dict-area :value="scope.row.district"></dict-area>
        </div>
      </template>
      <!--      <template #cqInfo="scope">-->
      <!--        <div class="flx">权证号/编号：{{ scope.row.propertyCode }}</div>-->
      <!--        <div class="flx">-->
      <!--          权证到期日：-->
      <!--          <dict-date :date="scope.row.propertyEndDate" format="YYYY/MM/DD"></dict-date>-->
      <!--        </div>-->
      <!--        <div class="flx">-->
      <!--          资产登记日期：-->
      <!--          <dict-date :date="scope.row.propertyTransferOwnership" format="YYYY/MM/DD"></dict-date>-->
      <!--        </div>-->
      <!--      </template>-->
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
      <template #projectId="scope">
        <vzProjectInfo :default-value="scope.row.projectId" :disabled="true" select-type="radio"></vzProjectInfo>
      </template>
      <template #city="scope">
        <div class="flx">
          <dict-area :value="scope.row!.province"></dict-area> - <dict-area :value="scope.row!.city"></dict-area> -
          <dict-area :value="scope.row!.district"></dict-area>
        </div>
      </template>
      <template #propertyTag="scope">
        <dict-name dict-type="PROPERTY_TAG" :dict-value="scope.row.propertyTag" />
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button type="primary" v-auth="'edit'" link @click="openDrawer('编辑', scope.row)">编辑</el-button>
        <el-button type="primary" v-auth="'delete'" link @click="deleteAccount(scope.row)">删除</el-button>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
    <ImportExcel ref="importRef" />
  </div>
</template>

<script setup lang="tsx" name="propertyInfo">
import { ref, reactive } from "vue";
import { propertyInfo } from "@/api/modules/property/propertyInfo/api"; // api
import { VzTableInstance, ColumnProps, TypeProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { PropertyInfoPageRequest, PropertyInfoDTO, propertyStateOptions } from "@/api/modules/property/propertyInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useRouter } from "vue-router";
import { useDialogStore } from "@/stores/modules/dialogParams";

import vzProjectInfo from "@/components/source/vzProjectInfo.vue";
import ImportExcel from "@/components/ImportExcel/index.vue";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
const router = useRouter();

// 按钮权限
const { BUTTONS } = useAuthButtons();

const emit = defineEmits<{
  rowdoubleClick: [{ row?: any; column?: any; event?: Event }];
  rowClick: [{ row?: any; column?: any; event?: Event }];
}>();

interface ProjectPropsType {
  filterData?: { [key: string]: any };
  isShowBtn?: boolean;
  selectType?: TypeProps;
}

const props = withDefaults(defineProps<ProjectPropsType>(), {
  filterData: () => ({}),
  isShowBtn: true,
  selectType: "selection"
});

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
  { type: props.selectType, fixed: "left", width: 70, isShow: !props.isShowBtn },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "propertyName",
    label: "资产名称",
    width: 250,
    headerAlign: "center",
    align: "left",
    isShow: false,
    search: { el: "input" }
  },
  {
    prop: "propertyState",
    label: "资产状态",
    isShow: false,
    enum: propertyStateOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 120
  },
  // {
  //   prop: "type",
  //   label: "大类",
  //   width: 120,
  //   enum: () => useBaseStore().findEnumByName("PROPERTY_LARGE_CATEGORY")
  // },
  {
    prop: "propertyType",
    label: "资产分类",
    width: 120,
    isShow: false,
    enum: () => useBaseStore().findEnumByName("PROPERTY_TYPE")
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
    width: 200,
    isShow: false,
    search: { el: "input" }
  },
  {
    prop: "city",
    label: "所在区域",
    minWidth: 200,
    isShow: false,
    search: {
      render: ({ searchParam }) => {
        return (
          <vz-area
            area={[searchParam.province, searchParam.city]}
            onChange={val => ((searchParam.province = val[0]), (searchParam.city = val[1]))}
            level={2}
            check-strictly={false}
          />
        );
      }
    }
  },
  // {
  //   prop: "propertyTag",
  //   label: "资产标签",
  //   width: 120,
  //   enum: () => useBaseStore().findEnumByName("PROPERTY_TAG"),
  //   search: { el: "select", props: { filterable: true } }
  // },

  {
    prop: "zcInfo",
    label: "资产信息",
    minWidth: 250
  },
  {
    prop: "moneyInfo",
    label: "资产价值",
    minWidth: 250
  },
  {
    prop: "sourceInfo",
    label: "资产来源",
    minWidth: 260
  },
  // {
  //   prop: "cqInfo",
  //   label: "产权信息",
  //   minWidth: 250
  // },
  // {
  //   prop: "originalValue",
  //   label: "资产原值(元)",
  //   minWidth: 200,
  //   type: "money",
  //   align: "right"
  // },
  // {
  //   prop: "debtRepaymentMoney",
  //   label: "资产抵债价格(元)",
  //   minWidth: 200,
  //   type: "money",
  //   align: "right"
  // },
  // {
  //   prop: "netWorth",
  //   label: "资产净值(元)",
  //   minWidth: 200,
  //   type: "money",
  //   align: "right"
  // },
  // {
  //   prop: "disposalPrice",
  //   label: "资产处置价格(元)",
  //   minWidth: 200,
  //   type: "money",
  //   align: "right"
  // },
  // {
  //   prop: "profitAndLoss",
  //   label: "资产盈亏(元)",
  //   minWidth: 200,
  //   type: "money",
  //   align: "right"
  // },
  // {
  //   prop: "area",
  //   label: "面积(m²)",
  //   width: 140
  // },

  // {
  //   prop: "accessWay",
  //   label: "资产取得方式",
  //   width: 120,
  //   enum: () => useBaseStore().findEnumByName("ACCESS_WAY")
  // },

  // {
  //   prop: "propertyCode",
  //   label: "产权证号",
  //   width: 140,
  //   search: { el: "input" }
  // },
  // {
  //   type: "date",
  //   prop: "propertyEndDate",
  //   label: "权证到期日",
  //   minWidth: 120,
  //   search: {
  //     key: "propertyEndDateRange", //指定搜索的key
  //     el: "date-picker",
  //     span: 1,
  //     props: {
  //       type: "datetimerange",
  //       format: "YYYY-MM-DD HH:mm:ss",
  //       valueFormat: "x",
  //       defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
  //     }
  //   }
  // },
  // {
  //   type: "date",
  //   prop: "propertyTransferOwnership",
  //   label: "资产过户日期",
  //   minWidth: 120
  // },
  // {
  //   prop: "creator",
  //   type: "user",
  //   label: "创建人",
  //   width: 120,
  //   isShow: props.isShowBtn
  // },
  // {
  //   type: "datetime",
  //   prop: "createStamp",
  //   label: "创建时间",
  //   width: 120,
  //   isShow: props.isShowBtn
  // },
  {
    prop: "creatorInfo",
    label: "创建信息",
    minWidth: 230
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 160,
    isShow:
      ((BUTTONS.value.edit !== undefined && BUTTONS.value.edit) ||
        (BUTTONS.value.detail !== undefined && BUTTONS.value.detail) ||
        (BUTTONS.value.delete !== undefined && BUTTONS.value.delete)) &&
      props.isShowBtn
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({ ...props.filterData });

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: PropertyInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  if (props.filterData && props.filterData.length > 0) {
    newParams.ids = props.filterData;
  }
  return propertyInfo().findAll(newParams);
};
// 删除用户信息
const deleteAccount = async (params: PropertyInfoDTO) => {
  await useHandleData(propertyInfo().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

//导入
const importRef = ref();
const importApi = () => {
  let params = {
    title: "数据",
    tempApi: "/asset-service/file/template/资产导入模板.xls",
    getTableList: vzTableRef.value?.getTableList,
    importApi: propertyInfo().importData
  };
  importRef.value.acceptParams(params);
};
// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<PropertyInfoDTO> = {}) => {
  if (title === "查看") {
    router.push(`/propertyInfo/detail/${row.id}`);
    return;
  }
  const params = {
    dialogName: "propertyInfo_createUpdate",
    title,
    showBtn: false,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? propertyInfo().add : title === "编辑" ? propertyInfo().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
defineExpose({
  vzTableRef
});
</script>

<style scoped lang="scss"></style>
