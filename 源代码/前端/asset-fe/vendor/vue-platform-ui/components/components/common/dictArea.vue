<template>
  <div>
    {{ getRegion(props.value) }}
  </div>
</template>
<script setup lang="ts" name="dictRegionName">
import commonFunction from "../../utils/commonFunction";

const { getRegion } = commonFunction();

const props = defineProps({
  value: {
    type: [Number, String],
    default: () => "",
  },
});
</script>
