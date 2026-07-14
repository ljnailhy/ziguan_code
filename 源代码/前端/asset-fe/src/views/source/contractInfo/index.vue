<template>
  <div class="table-box">
    <vz-table
      ref="vzTableRef"
      @rowdouble-click="rowdoubleClick"
      @row-click="rowClick"
      :columns="columns"
      :request-api="getTableList"
    >
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
      </template>
      <template #lawFirmId="scope">
        <vzLawFirmInfo :default-value="scope.row!.lawFirmId" :disabled="true"></vzLawFirmInfo>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button type="primary" v-auth="'edit'" link @click="openDrawer('编辑', scope.row)">编辑</el-button>
        <el-button type="primary" v-auth="'delete'" link @click="deleteAccount(scope.row)">删除</el-button>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="contractInfo">
import { ref, reactive } from "vue";
import { contractInfo } from "@/api/modules/source/contractInfo/api"; // api
import { VzTableInstance, ColumnProps, TypeProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { ContractInfoPageRequest, ContractInfoDTO } from "@/api/modules/source/contractInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useDialogStore } from "@/stores/modules/dialogParams";

import vzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

const emit = defineEmits<{
  rowdoubleClick: [{ row?: any; column?: any; event?: Event }];
  rowClick: [{ row?: any; column?: any; event?: Event }];
}>();

// 按钮权限
const { BUTTONS } = useAuthButtons();

interface ContractInfoPropsType {
  filterData?: { [key: string]: any };
  isShowBtn?: boolean;
  selectType?: TypeProps;
}

const props = withDefaults(defineProps<ContractInfoPropsType>(), {
  filterData: () => ({}),
  isShowBtn: true,
  selectType: "radio"
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
const columns = reactive<ColumnProps<ContractInfoDTO>[]>([
  { type: props.selectType, fixed: "left", width: 70, isShow: !props.isShowBtn },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "contractName",
    label: "合同名称",
    minWidth: 250,
    search: { el: "input" },
    align: "left",
    headerAlign: "center"
  },
  {
    prop: "contractCode",
    label: "合同编号",
    width: 200,
    search: { el: "input" }
  },
  {
    prop: "contractType",
    label: "合同类型",
    width: 180,
    enum: () => useBaseStore().findEnumByName("CONTRACT_TYPE")
    // search: { el: "select" }
  },
  {
    prop: "contractMoney",
    label: "合同金额(元)",
    type: "money",
    align: "right",
    headerAlign: "center",
    width: 200
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
    label: "结束时间",
    width: 120
  },
  // {
  //   prop: "contractAbstract",
  //   label: "合同摘要",
  //   width: 120
  // },
  {
    prop: "agentWay",
    label: "代理方式",
    width: 180,
    enum: () => useBaseStore().findEnumByName("AGENT_WAY")
  },
  {
    prop: "lawFirmId",
    label: "律所",
    width: 300,
    align: "left",
    headerAlign: "center"
  },
  {
    prop: "agencyFeeRatio",
    label: "代理费收费比例(%)",
    width: 160
  },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    width: 120,
    isShow: props.isShowBtn
  },
  {
    type: "datetime",
    prop: "createStamp",
    label: "创建时间",
    width: 180,
    isShow: props.isShowBtn,
    search: {
      key: "createStampRange", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: {
        type: "datetimerange",
        format: "YYYY-MM-DD HH:mm:ss",
        valueFormat: "x",
        defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
      }
    }
    // 自定义 search
    // search: {
    //   render: ({ searchParam }) => {
    //     return (
    //       <el-date-picker
    //         vModel_trim={searchParam.createStampRange}
    //         type="datetimerange"
    //         start-placeholder="开始时间"
    //         end-placeholder="结束时间"
    //         format="YYYY-MM-DD HH:mm:ss"
    //         valueFormat="x"
    //         date-format="YYYY/MM/DD ddd"
    //         time-format="A hh:mm:ss"
    //       />
    //     );
    //   }
    // }
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
// const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: ContractInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  if (props.filterData.projectId) {
    newParams.projectId = props.filterData.projectId;
  }
  if (props.filterData.lawFirmId) {
    newParams.lawFirmId = props.filterData.lawFirmId;
  }

  return contractInfo().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: ContractInfoDTO) => {
  await useHandleData(contractInfo().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
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

<style scoped lang="scss"></style>
