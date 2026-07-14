<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
      </template>
      <template #operation="scope">
        <el-button
          type="primary"
          v-auth="'add'"
          link
          @click="openDrawer('编辑', scope.row)"
          v-if="'draft' == scope.row.flowState && scope.row.collectionStatus !== collectionState"
        >
          编辑
        </el-button>
        <el-button type="primary" v-auth="'add'" link @click="openDrawer('查看', scope.row)">查看</el-button>
      </template>
    </vz-table>
    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="recoveryPayment">
import { reactive, ref } from "vue";
import { recoveryPaymentCollection } from "@/api/modules/recovery/recoveryPaymentCollection/api"; // api
import { ColumnProps, VzTableInstance } from "@/components/VzTable/interface"; // table数据类型 固定的
import {
  RecoveryPaymentCollectionPageRequest,
  RecoveryPaymentCollectionDTO,
  collectionSignOptions,
  collectionAscriptionOptions
} from "@/api/modules/recovery/recoveryPaymentCollection/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { processStatus } from "@/enums/commonOptions";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useBaseStore } from "@/stores/modules/baseInfo";

type Props = {
  lawFirmId?: any;
  projectId?: any;
  projectName?: string;
};

const props = withDefaults(defineProps<Props>(), {
  lawFirmId: "",
  projectId: "",
  projectName: ""
});

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
// 按钮权限
const { BUTTONS } = useAuthButtons();
// 表格配置项
const columns = reactive<ColumnProps<RecoveryPaymentCollectionDTO>[]>([
  { type: "index", label: "序号", width: 60 },

  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    search: { el: "input" }
  },

  {
    prop: "collectionAmount",
    label: "合计回款金额(元)",
    minWidth: 160,
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
    prop: "collectionSign",
    label: "回款标记",
    enum: collectionSignOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")

    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    minWidth: 180
  },
  {
    prop: "collectionAscription",
    label: "回款归属",
    enum: collectionAscriptionOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")

    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    minWidth: 180
  },
  {
    type: "boolean",
    prop: "isCollectionHistorical",
    label: "是否历史代偿",
    minWidth: 120
  },
  {
    prop: "lawyerName",
    label: "律所名称",
    minWidth: 200
  },

  // {
  //   prop: "flowState",
  //   label: "流程状态",
  //   minWidth: 90,

  //   enum: processStatus,
  // tag: true
  // },

  // {
  //   prop: "creator",
  //   type: "user",
  //   label: "创建人",
  //   minWidth: 120
  // },
  // {
  //   type: "date",
  //   prop: "createStamp",
  //   label: "创建时间",
  //   minWidth: 120
  // },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 120,
    isShow: BUTTONS.value.add !== undefined && BUTTONS.value.add
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({
  lawyerId: props.lawFirmId,
  projectId: props.projectId
});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const collectionState = ref();
const getTableList = async (params: RecoveryPaymentCollectionPageRequest) => {
  const data = await useBaseStore().findEnumByName("COLLECTION_STATUS");
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  newParams.collectionStatus = data && data.filter(item => item.itemCode == "COLLECTION_STATUS_002")[0].id;
  collectionState.value = data && data.filter(item => item.itemCode == "COLLECTION_STATUS_001")[0].id;
  return recoveryPaymentCollection().findAll(newParams);
};

const dialogRef = ref();
const openDrawer = async (title: string, row: any = {}) => {
  const data = await useBaseStore().findEnumByName("COLLECTION_STATUS");
  const params = {
    dialogName: "recoveryPaymentCollection_createUpdate",
    title,
    showBtn: false,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? recoveryPaymentCollection().add : title === "编辑" ? recoveryPaymentCollection().update : undefined,
    getTableList: vzTableRef.value?.getTableList,
    collectionStatus: data && data.filter(item => item.itemCode == "COLLECTION_STATUS_002")[0].id,
    projectId: props.projectId,
    projectName: props.projectName
  };
  useDialogStore().setDrawerProps(params); //存缓存
  dialogRef.value?.acceptParams(params);
};
</script>
