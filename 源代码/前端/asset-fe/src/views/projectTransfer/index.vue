<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
      </template>
      <template #transferIllustrate="scope">
        <div v-html="scope.row.transferIllustrate"></div>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button
          type="primary"
          v-auth="'edit'"
          link
          @click="openDrawer('编辑', scope.row)"
          v-if="'draft' == scope.row.flowState"
        >
          编辑
        </el-button>
        <el-button type="primary" v-auth="'delete'" link @click="deleteAccount(scope.row)" v-if="'draft' == scope.row.flowState">
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
    <vz-flowchart ref="flowchartRef" />
  </div>
</template>

<script setup lang="tsx" name="projectTransfer">
import { ref, reactive } from "vue";
import { projectTransfer } from "@/api/modules/projectTransfer/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { ProjectTransferPageRequest, ProjectTransferDTO } from "@/api/modules/projectTransfer/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { processStatus } from "@/enums/commonOptions";
import vzFlowchart from "@/components/source/vzFlowchart.vue";
import { useDialogStore } from "@/stores/modules/dialogParams";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();
const flowchartRef = ref();
const openFlowchart = (row: EmptyObjectType) => {
  flowchartRef.value.openDialog(row.id);
};
// 表格配置项
const columns = reactive<ColumnProps<ProjectTransferDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "title",
    label: "标题",
    minWidth: 550,
    align: "left",
    search: { el: "input" }
  },
  {
    prop: "flowState",
    label: "流程状态",
    enum: processStatus,
    tag: true,
    // search: { el: "select", props: { filterable: true } },
    width: 120
  },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    width: 120
  },
  {
    type: "datetime",
    prop: "createStamp",
    label: "创建时间",
    width: 200,
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
      (BUTTONS.value.edit !== undefined && BUTTONS.value.edit) ||
      (BUTTONS.value.detail !== undefined && BUTTONS.value.detail) ||
      (BUTTONS.value.delete !== undefined && BUTTONS.value.delete)
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: ProjectTransferPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return projectTransfer().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: ProjectTransferDTO) => {
  await useHandleData(projectTransfer().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<ProjectTransferDTO> = {}) => {
  const params = {
    dialogName: "projectTransfer_createUpdate",
    title,
    showBtn: true,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? projectTransfer().add : title === "编辑" ? projectTransfer().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  useDialogStore().setDrawerProps(params); //存缓存
  dialogRef.value?.acceptParams(params);
};
</script>

<style scoped lang="scss"></style>
