<template>
  <div class="theme-setting layout-navbars-icon">
    <i :class="'iconfont icon-pifu'" class="toolBar-icon" title="布局设置" @click="openDrawer"></i>
  </div>
</template>

<script setup lang="ts">
import mittBus from "@/utils/mittBus";
const openDrawer = () => {
  mittBus.emit("openThemeDrawer");
};
</script>
