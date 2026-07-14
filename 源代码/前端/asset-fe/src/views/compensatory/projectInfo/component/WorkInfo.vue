<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <template #operation="scope">
        <el-button type="primary" link @click="openDrawer('查看', scope.row)">查看</el-button>
      </template>
    </vz-table>
    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="workRegister">
import { ref, reactive } from "vue";
import { workRegister } from "@/api/modules/compensatory/workRegister/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { WorkRegisterPageRequest, WorkRegisterDTO } from "@/api/modules/compensatory/workRegister/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useDialogStore } from "@/stores/modules/dialogParams";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 表格配置项
const columns = reactive<ColumnProps<WorkRegisterDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    width: 300,
    align: "left",

    search: { el: "input" }
  },
  {
    prop: "type",
    label: "类型",
    width: 120,
    enum: () => useBaseStore().findEnumByName("WORK_TYPE"),
    search: { el: "select" }
  },
  {
    type: "date",
    prop: "workDate",
    label: "工作时间",
    width: 120,
    search: {
      // key: "workDateRange", //指定搜索的key
      el: "date-picker",
      span: 1
      // props: {
      //   type: "datetimerange",
      //   format: "YYYY-MM-DD HH:mm:ss",
      //   valueFormat: "x",
      //   defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
      // }
    }
  },
  {
    prop: "workContent",
    label: "工作内容",
    search: { el: "input" },
    minWidth: 120,
    align: "left",
    headerAlign: "center"
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
    width: 120
  },
  { prop: "operation", label: "操作", fixed: "right", width: 100 }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
type Props = {
  projectId?: any;
  lawFirmId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});
console.log(props.projectId);
const initParam = reactive({
  // doId: props.projectId,
  // // doIds: props.lawFirmId.length ? props.lawFirmId : [0],
  // doType: "PROJECT_INFO"
});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: WorkRegisterPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  newParams.doType = "PROJECT_INFO";
  if (props.lawFirmId) {
    newParams.doIds = props.lawFirmId.length ? props.lawFirmId : [0];
  } else {
    newParams.doId = props.projectId;
  }
  return workRegister().findAll(newParams);
};

const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<WorkRegisterDTO> = {}) => {
  const params = {
    dialogName: "workRegister_createUpdate",
    title,
    showBtn: false,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? workRegister().add : title === "编辑" ? workRegister().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
</script>

<style scoped lang="scss"></style>
