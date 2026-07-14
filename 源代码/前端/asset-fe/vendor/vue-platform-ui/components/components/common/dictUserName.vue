<!-- <template>
  <span>{{ displayName }}</span>
</template>

<script setup lang="ts">
import { computed } from "vue";
import commonFunction from "../../utils/commonFunction";

const props = defineProps({
  userCode: {
    type: [String, Number],
    default: "",
  },
});

const { users } = commonFunction();

const displayName = computed(() => {
  return users.value[String(props.userCode)] || props.userCode;
});
</script> -->
<template>
  <div>
    {{ userName }}
  </div>
</template>

<script setup lang="ts" name="dictUserName">
import { ref, watch, onMounted } from "vue";
import { PropType } from "vue";
import commonFunction from "../../utils/commonFunction";

const { getUser } = commonFunction();

const props = defineProps({
  userCode: {
    type: [String, Number] as PropType<string | number>,
    default: () => "",
  },
});

const userName = ref("");

const updateUserName = () => {
  userName.value = getUser(props.userCode);
};

onMounted(updateUserName);
watch(() => props.userCode, updateUserName);
</script>
