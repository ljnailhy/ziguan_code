<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
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

<script setup lang="tsx" name="projectTransferDetailed">
import { ref, reactive } from "vue";
import { projectTransferDetailed } from "@/api/modules/projectTransferDetailed/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { ProjectTransferDetailedPageRequest, ProjectTransferDetailedDTO } from "@/api/modules/projectTransferDetailed/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();

// 表格配置项
const columns = reactive<ColumnProps<ProjectTransferDetailedDTO>[]>([
  { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "transferId",
    label: "项目移交id",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "projectId",
    label: "项目id",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "compensationMoney",
    label: "代偿金额",
    width: 120,
    search: { el: "input" }
  },
  {
    type: "date",
    prop: "compensationDate",
    label: "代偿时间",
    width: 120
  },
  {
    prop: "manage",
    label: "保全经理",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "lawFirmId",
    label: "律所",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "contractId",
    label: "关联合同",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "collectionAmount",
    label: "回款金额",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "residueCompensation",
    label: "剩余代偿金额",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    width: 120
  },
  {
    type: "date",
    prop: "createStamp",
    label: "创建时间",
    width: 120,
    search: {
      key: "createStampRange", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: { type: "datetimerange", format: "YYYY-MM-DD HH:mm:ss", valueFormat: "x" }
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
    width: 220,
    isShow: BUTTONS.value.edit || BUTTONS.value.detail || BUTTONS.value.delete
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: ProjectTransferDetailedPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return projectTransferDetailed().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: ProjectTransferDetailedDTO) => {
  await useHandleData(projectTransferDetailed().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<ProjectTransferDetailedDTO> = {}) => {
  const params = {
    dialogName: "projectTransferDetailed_createUpdate",
    title,
    showBtn: false,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? projectTransferDetailed().add : title === "编辑" ? projectTransferDetailed().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
};
</script>

<style scoped lang="scss"></style>
