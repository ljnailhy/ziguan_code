<template>
  <!-- 分页组件 -->
  <el-pagination
    :background="true"
    :current-page="pageable.current"
    :page-size="pageable.size"
    :page-sizes="[10, 25, 50, 100, 500, 1000]"
    :total="pageable.total"
    layout="total, sizes, prev, pager, next, jumper"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
  ></el-pagination>
</template>

<script setup lang="ts" name="Pagination">
// import { computed } from "vue";
// import { useGlobalStore } from "../../../stores//stores/modules/global";

interface Pageable {
  current: number;
  size: number;
  total: number;
}

interface PaginationProps {
  pageable: Pageable;
  handleSizeChange: (size: number) => void;
  handleCurrentChange: (currentPage: number) => void;
}

// const globalStore = useGlobalStore();

// const assemblySize = computed(() => globalStore.assemblySize);

defineProps<PaginationProps>();
</script>
