<template>
  <div>
    {{ getMenu(props.dictValue) }}
  </div>
</template>
<script setup lang="ts" name="DictName">
import commonFunction from "../../utils/commonFunction";

const { getMenu } = commonFunction();

const props = defineProps({
  dictValue: {
    type: [String, Number],
    default: () => "",
  },
});
</script>
