<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" highlight-current-row @rowdouble-click="rowClick">
      <template #tableHeader>
        <el-radio-group v-model="radio" @change="changeRadio">
          <el-radio-button label="我的待办" value="1" />
          <el-radio-button label="在途流程" value="2" />
          <el-radio-button label="已办流程" value="3" />
        </el-radio-group>
      </template>
      <template #operation="scope">
        <el-button type="primary" v-if="radio === '1'" link @click="rowClick(scope)">处理</el-button>
        <el-button type="primary" v-else link @click="rowClick(scope)">查看</el-button>
      </template>
    </vz-table>
    <workflow ref="workflowRef" :get-list="getList" />
  </div>
</template>

<script setup lang="tsx" name="subjectInfo">
import { ref, reactive, defineAsyncComponent } from "vue";
import { processApi } from "@/api/modules/workflow/process";
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { SubjectInfoDTO } from "@/api/modules/source/subjectInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型

const workflow = defineAsyncComponent(() => import("./workflow/workflow.vue"));
// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
const radio = ref("1");
console.log(radio.value);

// 表格配置项
const columns = reactive<ColumnProps<SubjectInfoDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "procVars.object",
    label: "标题",
    minWidth: 300,
    align: "left",
    search: { el: "input", span: 1, key: "title" }
  },
  {
    prop: "startUserName",
    label: "处理人",
    width: 120
  },
  {
    type: "datetime",
    prop: "createTime",
    label: "处理时间",
    width: 180,
    search: {
      key: "createStampRange", //指定搜索的key
      el: "date-picker",
      span: 2,
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
    width: 90
  }
]);

const changeRadio = () => {
  getList();
  // const targetValue = String(value);
  // const operationColumn = columns.find(item => item.prop === "operation");
  // if (operationColumn) {
  //   if (targetValue === "3" || targetValue === "2") {
  //     operationColumn.isShow = false;
  //   } else {
  //     operationColumn.isShow = true;
  //   }
  // }
};

const getTableList = (params: any) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  delete newParams.createStampRange;
  if (params.createStampRange && params.createStampRange.length) {
    newParams.beginTime = params.createStampRange[0];
    newParams.endtime = params.createStampRange[1];
  }
  newParams.pageNum = params.current;
  newParams.pageSize = params.size;
  if (radio.value === "1") {
    return processApi().listTodoProcess(newParams);
  } else if (radio.value === "2") {
    newParams.state = "running";
    return processApi().ownList(newParams);
  } else if (radio.value === "3") {
    return processApi().finishedList(newParams);
  } else {
    return processApi().listTodoProcess(newParams);
  }
};
const workflowRef = ref();
const rowClick = (row: any) => {
  row.getTableList = vzTableRef.value?.getTableList;
  workflowRef.value!.openDialog(row);
};
const getList = () => {
  vzTableRef.value?.getTableList();
};
</script>

<style scoped lang="scss"></style>
