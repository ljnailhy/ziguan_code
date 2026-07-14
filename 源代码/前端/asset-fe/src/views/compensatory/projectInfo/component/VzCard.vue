<template>
  <el-card
    :shadow="props.shadow"
    :body-style="{ padding: isShow ? 'var(--el-card-padding)' : '0 var(--el-card-padding)' }"
    style="margin-bottom: 10px"
  >
    <template #header>
      <div class="card-header">
        <div class="card-header_flex">
          <span class="dot"></span>
          <slot name="title" v-if="slots['title']"></slot>
          <span v-else>{{ props.title }}</span>
          <el-tooltip :content="props.content" placement="top" v-if="props.content">
            <el-icon><Warning /></el-icon>
          </el-tooltip>
          <el-tooltip content="刷新" placement="top" v-if="props.refresh">
            <el-icon @click="onRefresh()"><Refresh /></el-icon>
          </el-tooltip>
        </div>
        <div>
          <el-button type="primary" link size="small" v-auth="'add'" @click="goEdit" v-if="props.edit">
            编辑 <el-icon class="el-icon--right"><Edit /></el-icon>
          </el-button>
          <el-button type="primary" link size="small" v-if="props.more" @click="goMore">
            更多记录 <el-icon class="el-icon--right"><DArrowRight /></el-icon>
          </el-button>
          <el-button @click="change" :icon="isShow ? ArrowDown : ArrowRight" link />
        </div>
      </div>
    </template>
    <el-collapse-transition>
      <div v-show="isShow">
        <slot></slot>
      </div>
    </el-collapse-transition>
  </el-card>
</template>
<script setup lang="ts">
import { ref, useSlots, watch } from "vue";
import { ArrowDown, ArrowRight, Warning, DArrowRight } from "@element-plus/icons-vue";

const emit = defineEmits<{
  goMore: [];
  goEdit: [];
  onRefresh: [];
}>();
interface showBasicInfoProps {
  title?: string;
  content?: string;
  shadow?: any;
  more?: boolean;
  edit?: boolean;
  collapse?: boolean;
  refresh?: boolean;
}

const props = withDefaults(defineProps<showBasicInfoProps>(), {
  title: "",
  content: "",
  shadow: "never",
  more: false,
  edit: false,
  collapse: true,
  refresh: false
});

const slots = useSlots();
const isShow = ref(true);
const change = () => {
  isShow.value = !isShow.value;
};
const goMore = () => {
  emit("goMore");
};

const goEdit = () => {
  emit("goEdit");
};
const onRefresh = () => {
  emit("onRefresh");
};

watch(
  () => props.collapse,
  value => {
    if (value === false) {
      isShow.value = value;
    }
  },
  { immediate: true }
);
</script>
<style lang="scss" scoped>
:deep(.el-card__header) {
  padding: 12px 20px;
}
.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  .dot {
    display: inline-block;
    width: 10px;
    height: 10px;
    background: linear-gradient(58.6414deg, var(--el-color-primary) 14.8709%, var(--el-color-primary-light-3) 78.017%);
    transition: unset;
    border-radius: 50%;
    margin-right: 5px;
  }
  &_flex {
    display: flex;
    align-items: center;
    font-weight: bold;
  }
  .el-tooltip__trigger {
    margin-left: 5px;
    margin-top: 2px;
  }
}
</style>
