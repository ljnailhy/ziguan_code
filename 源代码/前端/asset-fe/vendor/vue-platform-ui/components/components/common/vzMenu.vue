<template>
  <div style="width: 100%">
    <el-cascader
      v-model="menuValue"
      placeholder="请选择菜单"
      :options="options"
      @change="changeMenu"
      :props="props1"
      filterable
      style="width: 100%"
      :clearable="props.clearable"
    />
  </div>
</template>
<script setup lang="ts" name="vzMenu">
import { ref, onMounted, watch } from "vue";
import { useMenuApi } from "../../api/system/menu";
import { storeToRefs } from "pinia";
import { useRoutesList } from "../../stores/routesList";
import commonFunction from "../../utils/commonFunction";

interface Option {
  id: string | number;
  menuName: string;
  menuType: string;
  menuCode: string | number;
}

const emit = defineEmits(["update:menuValue", "change"]);
const props = defineProps({
  placeholder: {
    type: [String],
    default: () => "搜索真实姓名 / 选择人员",
  },
  disabled: {
    type: Boolean,
    default: () => false,
  },
  multiple: {
    type: Boolean,
    default: () => false,
  },
  clearable: {
    type: Boolean,
    default: () => true,
  },
  defaultValue: {
    type: [String, Number, Array],
    default: () => <EmptyArrayType>[],
  },
});

//定义变量
const props1 = {
  checkStrictly: true,
};
const options = ref<Array<Option>>([]);
const loading = ref(false);
const stores = useRoutesList();
const { routesList } = storeToRefs(stores);
const { organizeNodesFun } = commonFunction();
const menuValue = ref<EmptyArrayType>(props.defaultValue);

const getOptions = () => {
  loading.value = true;
  useMenuApi()
    .findAll({ size: 10000, current: 1 }, { size: 10000, current: 1 })
    .then(async (data) => {
      const orgList = data.data.map((item: EmptyObjectType) => {
        return {
          ...item,
          value: item.id,
          label: item.menuName,
        };
      });

      options.value = await organizeNodesFun(orgList);
    });
};

const changeMenu = (val: EmptyArrayType) => {
  menuValue.value = val;
  emit("update:menuValue", val && val.length > 0 ? val[val.length - 1] : "");
};
watch(
  () => props.defaultValue,
  (val) => {
    menuValue.value = val ? val : [];
  }
);
onMounted(() => {
  getOptions();
  console.log(routesList.value);
});
</script>
<style lang="scss"></style>
