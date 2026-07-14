<template>
  <div style="width: 100%">
    <el-select
      v-model="modelValue"
      :multiple="isMultiple"
      clearable
      filterable
      collapse-tags
      collapse-tags-tooltip
      :max-collapse-tags="5"
      :placeholder="props.placeholder"
      style="width: 100%"
      value-key="id"
      :disabled="props.disabled"
      @change="handleChange"
    >
      <el-option
        v-for="item in getEnum"
        :key="item.id"
        :label="item.title"
        :value="item.id"
      />
    </el-select>
  </div>
</template>
<script setup lang="ts" name="vzSelect">
import { ref, Ref, onMounted, computed, PropType, watch } from "vue";
import pinia from "../../stores/index";
import { storeToRefs } from "pinia";
import { useCacheInfo } from "../../stores/baseInfo";

const props = defineProps({
  dictType: {
    type: [Number, String],
    default: () => "",
  },
  dictValue: {
    type: [Number, String, Array],
    default: () => "",
  },
  placeholder: {
    type: [String],
    default: () => "请选择",
  },
  disabled: {
    type: Boolean,
    default: () => false,
  },
  multiple: {
    type: Boolean,
    default: () => false,
  },
  optionDisabled: {
    type: [String, Number],
    default: () => "",
  },
});

const emit = defineEmits(["update:modelValue", "change"]);

const stores = useCacheInfo();
const { enums } = storeToRefs(stores);
const modelValue = ref<any>();

const handleChange = (value: string | number) => {
  modelValue.value = value;
  emit("update:modelValue", value);
  emit("change", value);
};
const getEnum = computed(() => {
  if (props.dictType && props.optionDisabled) {
    return enums.value[props.dictType].filter(
      (item) => item.parentId == props.optionDisabled
    );
  } else {
    return enums.value[props.dictType] || [];
  }
});
const isMultiple = computed(() => props.multiple === true); // 根据选择模式判断是否启用多选功能

watch(
  () => props.dictValue,
  (val: any) => {
    if (val) {
      if (props.multiple && Array.isArray(val) && val.length > 0) {
        modelValue.value = val.map(Number);
        console.log(1, val);
      } else if (props.multiple && val && val.length > 0) {
        modelValue.value = val
          .split(",")
          .map((str) => str.trim())
          .map(Number)
          .filter((num) => !isNaN(num));
        console.log(2, val);
      } else {
        modelValue.value = val * 1;
        console.log(3, val);
      }
    }
    // if (props.multiple && val) {
    //   if (Array.isArray(val) && val.length > 0) {
    //     modelValue.value = val.map(Number)
    //   } else {
    //     modelValue.value = val
    //       .split(",")
    //       .map((str) => str.trim())
    //       .map(Number)
    //       .filter((num) => !isNaN(num));
    //   }
    // } else if (val && val.length>0) {
    //   modelValue.value = Number(val)
    // }else {
    //   modelValue.value =  "";
    // }
  },
  { deep: true, immediate: true }
);
onMounted(() => {
  useCacheInfo(pinia).findEnumByName(props.dictType);
});
</script>
