<template>
  <span>
    {{ displayText }}
  </span>
</template>

<script setup lang="ts">
import { computed } from "vue";
import commonFunction from "../../utils/commonFunction";
import { useCacheInfo } from "../../stores/baseInfo";

const props = defineProps({
  value: [String, Number],
  type: {
    // 支持多种转换类型
    type: String as () => "user" | "org" | "region" | "dict" | "custom",
    default: "user",
  },
});

const { users, orgs, regions, dictionaries } = commonFunction();
const commonNames = useCacheInfo().commonNames;

// 公共的处理函数
const processIds = (
  ids: string[],
  valueMap: Record<string, string>
): string => {
  return ids
    .map((id) => id.trim())
    .filter((id) => isValidId(id)) // 过滤无效ID
    .map((id) => valueMap[id] || id) // 获取显示文本
    .join(",");
};

// 检查ID是否有效
const isValidId = (id: string): boolean => {
  return !!id && id !== "undefined" && id !== "null" && id !== "";
};

const displayText = computed(() => {
  if (!props.value) return "";
  const ids = String(props.value).split(",");
  switch (props.type) {
    case "user":
      return processIds(ids, users.value);
    case "org":
      return processIds(ids, orgs.value);
    case "region":
      return processIds(ids, regions.value);
    case "dict":
      return processIds(ids, dictionaries.value);
    case "custom":
      return processIds(ids, commonNames);
    default:
      return props.value;
  }
});
</script>
