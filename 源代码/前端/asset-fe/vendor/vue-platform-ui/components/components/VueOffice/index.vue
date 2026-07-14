<template>
  <component
    :is="currentComponent"
    :src="'/minio' + src"
    :style="{
      width: currentComponent == 'el-image' ? 'auto' : '100vw',
      height: currentComponent == 'el-image' ? 'auto' : '100vh',
    }"
    controls
    :zoom-rate="1.2"
    :max-scale="7"
    :min-scale="0.2"
    :preview-src-list="['minio/' + src]"
    :initial-index="4"
    fit="cover"
  >
    暂不支持预览该文件类型，请下载后查看。
  </component>
</template>

<script setup lang="ts" name="vueOfficePreview">
import { computed, defineProps, withDefaults } from "vue";
// 引入 VueOfficeDocx 和 VueOfficeExcel 组件
import VueOfficeDocx from "@vue-office/docx";
import VueOfficeExcel from "@vue-office/excel";
// 引入相关样式

const props = withDefaults(defineProps<{ src: string }>(), {
  src: "",
});

// 计算当前组件
const currentComponent = computed(() => {
  if (/\.(png|jpg|jpeg|PNG|JPG|JPEG)$/i.test(props.src)) {
    return "el-image";
  } else if (/\.(mov|mp4|avi)$/i.test(props.src)) {
    return "video";
  } else if (/\.pdf$/i.test(props.src)) {
    return "iframe";
  } else if (/\.docx$/i.test(props.src)) {
    return VueOfficeDocx;
  } else if (/\.xlsx$/i.test(props.src)) {
    return VueOfficeExcel;
  }
  return "div"; // 如果不匹配任何类型，则返回 null
});
</script>

<style lang="scss">
@import "@vue-office/docx/lib/index.css";
@import "@vue-office/excel/lib/index.css";
</style>
