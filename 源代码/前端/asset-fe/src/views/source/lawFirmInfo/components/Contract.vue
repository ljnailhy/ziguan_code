<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList">
      <template #lawFirmId="scope">
        <vzLawFirmInfo :default-value="scope.row['lawFirmId']" :disabled="true"></vzLawFirmInfo>
      </template>
      <template #operation="scope">
        <el-button type="primary" link @click="openDrawer('查看', scope.row)">查看</el-button>
      </template>
    </vz-table>
    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="contractInfo">
import { reactive, ref } from "vue";
import { contractInfo } from "@/api/modules/source/contractInfo/api"; // api
import { ColumnProps, VzTableInstance } from "@/components/VzTable/interface"; // table数据类型 固定的
import { ContractInfoPageRequest, ContractInfoDTO } from "@/api/modules/source/contractInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useDialogStore } from "@/stores/modules/dialogParams";

import vzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";
type Props = {
  lawFirmId?: any;
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  lawFirmId: "",
  projectId: ""
});
// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
// 表格配置项
const columns = reactive<ColumnProps<ContractInfoDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "contractName",
    label: "合同名称",
    minWidth: 250,
    align: "left",
    headerAlign: "center",
    search: { el: "input" }
  },
  {
    prop: "contractCode",
    label: "合同编号",
    width: 160,
    search: { el: "input" }
  },
  {
    prop: "contractType",
    label: "合同类型",
    width: 160,
    enum: () => useBaseStore().findEnumByName("CONTRACT_TYPE"),
    search: { el: "select" }
  },
  {
    prop: "contractMoney",
    label: "合同金额(元)",
    width: 200,
    type: "money",
    align: "right",
    headerAlign: "center"
  },
  {
    type: "date",
    prop: "signingDate",
    label: "签约日期",
    width: 120
  },
  {
    type: "date",
    prop: "startDate",
    label: "开始时间",
    width: 120
  },
  {
    type: "date",
    prop: "endDate",
    label: "截止时间",
    width: 120
    // search: {
    //   el: "date-picker",
    //   span: 2,
    //   key: "endDateRange",
    //   props: {
    //     type: "datetimerange",
    //     format: "YYYY-MM-DD HH:mm:ss",
    //     valueFormat: "x",
    //     defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
    //   }
    // }
  },
  // {
  //   prop: "contractAbstract",
  //   label: "合同摘要",
  //   width: 120
  // },
  {
    prop: "agentWay",
    label: "代理方式",
    width: 120,
    enum: () => useBaseStore().findEnumByName("AGENT_WAY")
  },
  {
    prop: "lawFirmId",
    label: "律所",
    width: 120
  },
  {
    prop: "agencyFeeRatio",
    label: "代理费收费比例(%)",
    width: 160
  },

  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 100
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
// const initParam = reactive({
//   lawFirmId: props.lawFirmId,
//   projectId: props.projectId
// });

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: ContractInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  if (props.projectId) {
    newParams.projectId = props.projectId;
  }
  if (props.lawFirmId) {
    newParams.lawFirmId = props.lawFirmId;
  }
  return contractInfo().findAll(newParams);
};

const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<ContractInfoDTO> = {}) => {
  const params = {
    dialogName: "contractInfo_createUpdate",
    title,
    showBtn: false,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? contractInfo().add : title === "编辑" ? contractInfo().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
</script>
