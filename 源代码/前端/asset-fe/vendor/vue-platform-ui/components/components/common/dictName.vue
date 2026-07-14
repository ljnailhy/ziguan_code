<template>
  <div>
    {{ getEnumItem(props.dictValue, props.dictType) }}
  </div>
</template>
<script setup lang="ts" name="DictName">
import commonFunction from "../../utils/commonFunction";

const { getEnumItem } = commonFunction();

const props = defineProps({
  dictValue: {
    type: [String, Number],
    default: () => "",
  },
  dictType: {
    type: String,
    default: () => "",
  },
});
</script>
