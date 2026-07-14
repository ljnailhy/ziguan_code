<template>
  <el-dropdown trigger="click" @command="onSysTemChange" v-if="systemList.length > 0">
    <div class="layout-navbars-icon" title="切换系统">
      <el-icon><Switch /></el-icon>
    </div>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item :command="item" v-for="item in systemList" :key="item.id">{{ item.menuName }}</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

import { useUserStore } from "@/stores/modules/user";

const systemList = ref<EmptyArrayType>([]);

const onSysTemChange = (item: EmptyObjectType) => {
  window.open(item.menuPath + "/", item.id);
};
const userStore = useUserStore();
onMounted(() => {
  const menuList = userStore.menuList;
  if (menuList && menuList.length) {
    const menuData = menuList.filter(
      (item: EmptyObjectType) => item.menuType === "SYSTEM" && item.menuPath && item.menuPath !== "/asset"
    );
    const seenMenuPaths = new Set();
    systemList.value = menuData.filter(item => {
      if (!seenMenuPaths.has(item.menuPath)) {
        seenMenuPaths.add(item.menuPath);
        return true;
      }
      return false;
    });
  }
});
</script>
