<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
        <el-button type="primary" v-auth="'import'" @click="importApi()">导入</el-button>
      </template>
      <!-- 合并列表数据 s -->
      <template #hkInfo="scope">
        <div class="flx">
          回款金额：
          <div v-currency="scope.row.collectionAmount"></div>
        </div>
        <div class="flx">
          回款时间：
          <dict-date :date="scope.row.collectionDate" format="YYYY/MM/DD"></dict-date>
        </div>
      </template>
      <template #otherInfo="scope">
        <div class="flx">
          回款标记：<dict-enum :options="collectionSignOptions" :value="scope.row.collectionSign"></dict-enum>
        </div>
        <div class="flx">
          回款归属：<dict-enum :options="collectionAscriptionOptions" :value="scope.row.collectionAscription"></dict-enum>
        </div>
        <div class="flx" v-if="scope.row.collectionType">
          回款类型：{{ scope.row.collectionType == "OWN" ? "自主追偿" : "委托追偿" }}
        </div>
        <div class="flx">是否历史代偿：{{ scope.row.isCollectionHistorical ? "是" : "否" }}</div>
      </template>
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
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button
          type="primary"
          v-auth="'edit'"
          link
          @click="openDrawer('编辑', scope.row)"
          v-if="'draft' == scope.row.flowState && scope.row.collectionStatus !== collectionState"
        >
          编辑
        </el-button>
        <el-button
          type="primary"
          v-auth="'delete'"
          link
          @click="deleteAccount(scope.row)"
          v-if="'draft' == scope.row.flowState && scope.row.collectionStatus !== collectionState"
        >
          删除
        </el-button>
        <el-button
          type="primary"
          v-auth="'flowchart'"
          link
          @click="openFlowchart(scope.row)"
          v-if="'draft' != scope.row.flowState"
        >
          查看流程图
        </el-button>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
    <ImportExcel ref="importRef" />
    <vz-flowchart ref="flowchartRef" />
  </div>
</template>

<script setup lang="tsx" name="recoveryPaymentCollection">
import { ref, reactive } from "vue";
import { recoveryPaymentCollection } from "@/api/modules/recovery/recoveryPaymentCollection/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import {
  RecoveryPaymentCollectionPageRequest,
  RecoveryPaymentCollectionDTO,
  collectionSignOptions,
  collectionAscriptionOptions
} from "@/api/modules/recovery/recoveryPaymentCollection/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import VzFlowchart from "@/components/source/vzFlowchart.vue";
import { processStatus } from "@/enums/commonOptions";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { useBaseStore } from "@/stores/modules/baseInfo";
import ImportExcel from "@/components/ImportExcel/index.vue";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
const flowchartRef = ref();
const openFlowchart = (row: EmptyObjectType) => {
  flowchartRef.value.openDialog(row.id);
};

// 按钮权限
const { BUTTONS } = useAuthButtons();
//导入
const importRef = ref();
const importApi = () => {
  let params = {
    title: "数据",
    importApi: recoveryPaymentCollection().importData,
    getTableList: vzTableRef.value?.getTableList,
    tempApi: "/asset-service/file/template/回款信息导入模板.xlsx"
  };
  importRef.value.acceptParams(params);
};
// 表格配置项
const columns = reactive<ColumnProps<RecoveryPaymentCollectionDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  // {
  //   prop: "projectId",
  //   label: "项目id",
  //   width: 120,
  //   search: { el: "input" }
  // },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    align: "left",
    search: { el: "input" }
  },
  // {
  //   prop: "compensatoryCash",
  //   label: "代偿金额",
  //   minWidth: 120,
  //   type: "money",
  //   align: "right"
  // },
  // {
  //   prop: "collectionAmount",
  //   label: "合计回款金额(元)",
  //   minWidth: 160,
  //   type: "money"
  // },
  {
    // type: "date",
    prop: "collectionDate",
    label: "回款时间",
    isShow: false,
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
    prop: "hkInfo",
    label: "回款信息",
    minWidth: 200
  },
  {
    prop: "otherInfo",
    label: "其他信息",
    minWidth: 200
  },
  {
    prop: "collectionSign",
    label: "回款标记",
    isShow: false,
    enum: collectionSignOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    minWidth: 180
  },
  {
    prop: "collectionAscription",
    label: "回款归属",
    isShow: false,
    enum: collectionAscriptionOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    minWidth: 180
  },
  // {
  //   type: "boolean",
  //   prop: "isCollectionHistorical",
  //   label: "是否历史代偿",
  //   minWidth: 120
  // },
  {
    prop: "lawyerName",
    label: "律所名称",
    minWidth: 200,
    search: { el: "input" }
  },

  {
    prop: "flowState",
    label: "流程状态",
    minWidth: 90,
    enum: processStatus,
    search: { el: "select", props: { filterable: true } },
    tag: true
  },
  // {
  //   prop: "collectionSummary",
  //   label: "回款摘要",
  //   minWidth: 200,
  //   isShow: false
  // },
  // {
  //   prop: "remarks",
  //   label: "备注",
  //   minWidth: 120
  // }
  // ,
  // {
  //   prop: "defaultCash",
  //   label: "违约金",
  //   minWidth: 120
  // },
  // {
  //   prop: "interest",
  //   label: "利息",
  //   minWidth: 120
  // },
  // {
  //   prop: "otherFee",
  //   label: "其他费用",
  //   minWidth: 120
  // },
  {
    prop: "creatorInfo",
    label: "创建信息",
    minWidth: 230
  },
  // {
  //   prop: "creator",
  //   type: "user",
  //   label: "创建人",
  //   minWidth: 120
  // },
  // {
  //   type: "datetime",
  //   prop: "createStamp",
  //   label: "创建时间",
  //   minWidth: 200
  // },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    minWidth: 160,
    isShow:
      (BUTTONS.value.edit !== undefined && BUTTONS.value.edit) ||
      (BUTTONS.value.detail !== undefined && BUTTONS.value.detail) ||
      (BUTTONS.value.delete !== undefined && BUTTONS.value.delete)
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});
const collectionState = ref();
// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = async (params: RecoveryPaymentCollectionPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  const paymentStateList = await useBaseStore().findEnumByName("COLLECTION_STATUS");
  const state = (collectionState.value = paymentStateList
    .filter(item => item.itemCode != "COLLECTION_STATUS_001") // 筛选条件
    .map(item => item.id));
  collectionState.value = state[0];
  return recoveryPaymentCollection().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: RecoveryPaymentCollectionDTO) => {
  await useHandleData(recoveryPaymentCollection().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<RecoveryPaymentCollectionDTO> = {}) => {
  const params = {
    dialogName: "recoveryPaymentCollection_createUpdate",
    title,
    showBtn: true,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? recoveryPaymentCollection().add : title === "编辑" ? recoveryPaymentCollection().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  useDialogStore().setDrawerProps(params); //存缓存
  dialogRef.value?.acceptParams(params);
};
</script>

<style scoped lang="scss"></style>
