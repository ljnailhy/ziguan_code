<template>
  <div style="width: 100%">
    <el-select
      v-model="modelValue"
      :multiple="isMultiple"
      :clearable="props.clearable"
      :filterable="props.filterable"
      collapse-tags
      collapse-tags-tooltip
      :max-collapse-tags="2"
      :placeholder="props.placeholder"
      value-key="id"
      style="width: 100%"
      @change="handleChange"
      :disabled="props.disabled"
    >
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
  </div>
</template>
<script setup lang="ts" name="vzEnum">
import { ref, Ref, onMounted, watch, computed, PropType } from "vue";

interface Option {
  value: string | number;
  label: string;
}

const props = defineProps({
  dictType: {
    type: [Number, String],
    default: () => "",
  },
  dictValue: {
    type: [String, Number, Array] as PropType<
      string | string[] | number | number[]
    >,
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
  options: {
    type: Array as () => Option[],
    default: () => [],
  },
  clearable: {
    type: Boolean,
    default: () => false,
  },
  filterable: {
    type: Boolean,
    default: () => false,
  },
});

const emit = defineEmits(["update:modelValue", "change"]);

let modelValue: Ref<string | number | (string[] | number[])>;

// 根据选择模式定义 selectedValues
if (props.multiple) {
  modelValue = ref<string[] | number[]>(
    Array.isArray(props.dictValue) ? props.dictValue : []
  );
} else {
  modelValue = ref<string | number>(
    Array.isArray(props.dictValue) ? props.dictValue[0] : props.dictValue || ""
  );
}

watch(
  () => props.dictValue,
  (value) => {
    modelValue.value = value;
  },
  {
    deep: true,
  }
);
const handleChange = (value: string | number) => {
  modelValue.value = value;
  emit("update:modelValue", value);
  emit("change", value);
};

const isMultiple = computed(() => props.multiple === true); // 根据选择模式判断是否启用多选功能

onMounted(() => {});
</script>
