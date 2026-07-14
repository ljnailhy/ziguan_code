<template>
  <div style="width: 100%">
    <el-cascader
      ref="classifyRef"
      v-model="areaArray"
      :props="selectProps"
      @change="change"
      @expand-change="expandchange"
      style="width: 100%"
      clearable
      filterable
      @clear="change"
      :disabled="props.disabled"
      placeholder="请选择省市区"
    >
    </el-cascader>
  </div>
</template>

<script setup lang="ts" name="areaSelect">
import { CascaderProps } from "element-plus";
import { onMounted, watch, ref } from "vue";
import commonFunction from "../../utils/regions";

const props = defineProps({
  area: {
    type: Array,
    default: () => <EmptyArrayType>[],
  },
  disabled: {
    type: Boolean,
    defalut: () => false,
  },
  checkStrictly: {
    type: Boolean,
    defalut: () => false,
  },
  level: {
    type: Number,
    default: () => 2,
  },
  provinces: {
    type: Array,
    default: () => <EmptyArrayType>[],
  },
});
const { getArea } = commonFunction();

const emit = defineEmits(["update:areaArray", "change", "expandchange"]);
const classifyRef = ref();
const areaArray = ref<EmptyArrayType>(props.area);
// const state = reactive({
// 	selectedCities: <EmptyArrayType>[],
// });

const change = (val: Array<EmptyArrayType>) => {
  areaArray.value = val;
  emit("update:areaArray", val);
  emit("change", val);
};

const expandchange = (val: Array<EmptyArrayType>) => {
  emit("expandchange", val);
};

const selectProps: CascaderProps = {
  lazy: true,
  checkStrictly: props.checkStrictly,
  lazyLoad: async (node, resolve) => {
    const { level, value } = node;
    if (level == 0) {
      const pro: any = await getArea("province", { level: 1 }, level);
      if (props.provinces.length <= 0) return resolve(pro);
      const filterPro = pro.filter((item: any) =>
        props.provinces.some((province) => item.label.includes(province))
      );
      resolve(filterPro);
    } else if (level === 1) {
      const cities: any = await getArea(
        "city",
        { level: 2, parentId: value },
        level
      );
      resolve(cities);
    } else if (level === 2) {
      const district: any = await getArea(
        "district",
        { level: 3, parentId: value },
        level
      );
      resolve(district);
    }
    // state.selectedCities = props.area;
  },
};

watch(
  () => props.area,
  (val) => {
    areaArray.value = val;
    emit("update:areaArray", val);
  },
  {
    deep: true,
    immediate: true,
  }
);
</script>

<style scoped lang="scss">
.area-select {
  width: 100%;
}
</style>
