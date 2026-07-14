<template>
  <div style="width: 100%" class="flx">
    <el-select v-model="modelValue" clearable placeholder="请选择律所" @change="handleChange">
      <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id" />
    </el-select>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { lawFirmInfo } from "@/api/modules/source/lawFirmInfo/api"; // api

const emit = defineEmits(["update:modelValue", "change"]);
// 定义父组件传过来的值
interface LawFirmInfoProps {
  filterData?: { [key: string]: any };
  disabled?: boolean;
  placeholder?: string;
  defaultValue?: string | number;
  companyId?: string;
  title?: string;
}

const props = withDefaults(defineProps<LawFirmInfoProps>(), {
  placeholder: "",
  disabled: false,
  defaultValue: "",
  companyId: "",
  title: "律所信息",
  filterData: () => ({})
});

const modelValue = ref<any>(props.defaultValue);

const handleChange = (value: string | number) => {
  modelValue.value = value;
  emit("update:modelValue", value);
  emit("change", value);
};

//watch默认值
watch(
  () => props.defaultValue,
  newId => {
    modelValue.value = newId;
  },
  { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
);

const options = ref<any>([]);
onMounted(() => {
  const params: any = {
    size: 10000,
    current: 1
  };
  lawFirmInfo()
    .findAssemblyList(params)
    .then(res => {
      options.value = res.data;
    });
});
</script>
<style lang="scss" scoped></style>
