<template>
  <span>{{ displayName }}</span>
</template>

<script setup lang="ts">
import { computed } from "vue";
import commonFunction from "../../utils/commonFunction";

const props = defineProps({
  value: {
    type: [String, Number],
    default: "",
  },
});

const { orgs } = commonFunction();

const displayName = computed(() => {
  return orgs.value[String(props.value)] || props.value;
});
</script>
<!-- <script setup lang="ts" name="dictUserName">
import { ref, watch, onMounted } from "vue";
import { PropType } from "vue";
import commonFunction from "../../utils/commonFunction";

const { getOrg } = commonFunction();

const props = defineProps({
  value: {
    type: [String, Number] as PropType<string | number>,
    default: () => "",
  },
});

const userName = ref("");

const updateUserName = () => {
  userName.value = getOrg(props.value);
};

onMounted(updateUserName);
watch(() => props.value, updateUserName);
</script> -->

<!-- <template>
  <div>
    {{ getOrg(props.value) }}
  </div>
</template>
<script setup lang="ts" name="dictUserName">
import commonFunction from "../../utils/commonFunction";

const { getOrg } = commonFunction();

const props = defineProps({
  value: {
    type: [Number, String],
    default: () => "",
  },
});
</script> -->
