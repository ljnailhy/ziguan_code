<template>
  <div class="cell" :class="{ isborder: isbtm, isBlock: !isblk }">
    <div :style="{ width: `${props.labelWidth}px` }">{{ props.label }}</div>
    <div :style="{ width: !isblk ? `calc(100% - ${props.labelWidth}px)` : 'auto' }">
      <slot name="value">
        <el-tooltip placement="top">
          <template #content>{{ props.value || "--" }} </template>
          {{ props.value || "--" }}
        </el-tooltip>
        <!-- <el-popover
          placement="top"
          :title="props.label"
          :width="200"
          style="height: 200px"
          trigger="hover"
          :content="props.value || '--'"
        >
          <template #reference>
            {{ props.value || "--" }}
          </template>
        </el-popover> -->
      </slot>
    </div>
  </div>
</template>
<script setup name="CellItem" lang="ts">
type Props = {
  label: string;
  value?: string | number | undefined;
  labelWidth?: string;
  isbtm?: boolean;
  isblk?: boolean;
};

const props = withDefaults(defineProps<Props>(), {
  label: "",
  value: "",
  labelWidth: "100",
  isbtm: false,
  isblk: false
});
</script>
<style scoped lang="scss">
.isBlock {
  display: flex;
  & > div:first-child {
    text-align: right;
    margin-right: 10px;
  }
}
.cell {
  font-size: 13px;
  padding: 10px 0;
  &.isborder {
    border-bottom: 1px dashed #e9e9e9;
  }
  & > div:first-child {
    color: rgb(176, 195, 233);
    display: inline-block;
  }
  & > div:last-child {
    color: rgb(51, 51, 51);
    display: inline-block;
    letter-spacing: 1px;
    line-height: 1.2;
    //overflow: hidden;
    //word-wrap: break-word;
    //text-overflow: ellipsis;
    //display: -webkit-box;
    //-webkit-box-orient: vertical;
    //-webkit-line-clamp: 1;
  }
}
</style>
