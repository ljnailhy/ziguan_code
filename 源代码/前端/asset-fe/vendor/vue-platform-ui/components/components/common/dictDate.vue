<template>
  <div style="display: inline-block">
    {{ getDate() }}
  </div>
</template>
<script setup lang="ts" name="DictDate">
import { formatDate } from "../../utils/formatTime";

const props = defineProps({
  date: {
    type: [Number, String],
    default: () => "",
  },
  format: {
    type: String,
    default: () => "",
  },
});
const getDate = () => {
  if (props.date) {
    return formatDate(new Date(props.date), props.format);
  } else {
    return "--";
  }
};
</script>
