<template>
  <div class="pd15 layout-padding-view layout-padding-auto">
    <vz-table-search
      :search="props.tableData.search"
      :searchButtons="props.tableData.searchButtons"
      @search="onSearch"
      @reset="onReset"
      @add="add"
    />
    <vz-table-body
      ref="tableRef"
      @row-click="rowClick"
      @row-dblclick="rowDblclick"
      @editRow="editRow"
      @open-detail="onOpenDetail"
      v-bind="props.tableData"
      class="table-component"
    >
      <template #operate>
        <slot name="operateColumn"></slot>
      </template>
    </vz-table-body>
  </div>
</template>

<script setup lang="ts" name="vzTable">
import { defineAsyncComponent, onMounted, ref } from "vue";

// 引入组件
// const TableSearch = defineAsyncComponent(() => import('./search.vue'));
// const Table = defineAsyncComponent(() => import('./table.vue'));

// 定义子组件向父组件传值/事件
const emit = defineEmits([
  "add",
  "edit",
  "detail",
  "del",
  "rowClick",
  "rowDblclick",
]);

// 定义父组件传过来的值
const props = defineProps({
  tableData: {
    type: Object,
    default: () => <EmptyObjectType>{},
  },
});

// 定义变量内容
const tableRef = ref();

// 搜索点击时表单回调
const onSearch = (data: EmptyObjectType) => {
  const queryForm = Object.assign({}, props.tableData.param, { ...data });
  tableRef.value.getTableData(queryForm);
};
//重置点击时回掉
const onReset = (data?: EmptyObjectType) => {
  tableRef.value.pageReset();
  tableRef.value.getTableData(data);
};
//新增
const add = () => {
  emit("add");
};
//编辑
const editRow = (row: EmptyObjectType) => {
  emit("edit", row);
};
// 打开详情
const onOpenDetail = (row: EmptyObjectType) => {
  emit("detail", row);
};
//单击
const rowClick = (row: EmptyObjectType) => {
  emit("rowClick", row);
};
//双击
const rowDblclick = (row: EmptyObjectType) => {
  emit("rowDblclick", row);
};

// 页面加载时
onMounted(() => {});

defineExpose({
  onReset,
  onSearch,
});
</script>

<style scoped lang="scss">
.pd15 {
  padding: 15px;
  .table-component {
    flex: 1;
    overflow: hidden;
  }
}
</style>
