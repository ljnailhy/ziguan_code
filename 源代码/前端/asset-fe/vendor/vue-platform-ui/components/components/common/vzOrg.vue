<template>
  <el-cascader
    v-model="orgValue"
    filterable
    :options="options"
    @change="changeOrg"
    :props="cascaderProps"
    style="width: 100%"
    :disabled="props.disabled"
    :clearable="props.clearable"
    :placeholder="props.placeholder"
  />
</template>

<script setup lang="ts" name="vzOrg">
import { ref, onMounted, watch, computed } from "vue";
import { useOrgApi } from "../../api/system/org";
import commonFunction from "../../utils/commonFunction";
import { useCacheInfo } from "../../stores/baseInfo";

interface Option {
  id: string | number;
  menuName: string;
  menuType: string;
  menuCode: string | number;
}

const emit = defineEmits(["update:orgValue", "change"]);
const props = defineProps({
  org: {
    type: [String, Array, Number] as any,
    default: () => "",
  },
  dictValue: {
    type: [String, Array, Number] as any,
    default: () => "",
  },
  placeholder: {
    type: String,
    default: () => "搜索组织 / 选择组织",
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
  checkStrictly: {
    type: Boolean,
    default: () => false,
  },
  defaultValue: {
    type: String,
    default: () => "MENU",
  },
  orgType: {
    type: String,
    default: () => "",
  },
});

const cacheStore = useCacheInfo();
const { organizeNodesFun } = commonFunction();
const orgValue = ref<any>(props.org);
const loading = ref(false);
const options = ref<Array<Option>>([]);

// 缓存键名
const CACHE_KEY = "orgOptionsCache";
// 请求状态
const requestState = {
  isRequesting: false,
  requestPromise: null as Promise<any> | null,
};

// 计算属性用于配置
const cascaderProps = computed(() => ({
  checkStrictly: props.checkStrictly,
  emitPath: false,
  multiple: props.multiple,
  value: "value",
  label: "label",
  children: "children",
}));

// 获取组织选项（带缓存）
const getOptions = async () => {
  // 检查是否有缓存
  if (cacheStore.orgs[CACHE_KEY] && cacheStore.orgs[CACHE_KEY].length > 0) {
    options.value = cacheStore.orgs[CACHE_KEY];
    return;
  }

  // 检查是否正在请求中
  if (requestState.isRequesting && requestState.requestPromise) {
    try {
      await requestState.requestPromise;
      if (cacheStore.orgs[CACHE_KEY]) {
        options.value = cacheStore.orgs[CACHE_KEY];
      }
      return;
    } catch (error) {
      console.error("等待组织数据请求失败:", error);
      // 继续执行下面的请求逻辑
    }
  }

  // 设置请求状态
  requestState.isRequesting = true;
  loading.value = true;

  try {
    let params = props.orgType ? { orgType: props.orgType } : {};

    // 创建请求Promise
    requestState.requestPromise = useOrgApi()
      .findAll(params, { size: 10000, current: 1 })
      .then(async (data) => {
        if (data.code === 0) {
          const orgList = data.data.map((item: any) => ({
            ...item,
            value: item.id,
            label: item.orgName,
          }));

          // 组织节点结构
          const organizedOptions = await organizeNodesFun(orgList);

          // 存储到缓存
          cacheStore.orgs[CACHE_KEY] = organizedOptions;
          options.value = organizedOptions;

          return organizedOptions;
        }
        return [];
      })
      .finally(() => {
        requestState.isRequesting = false;
        requestState.requestPromise = null;
        loading.value = false;
      });

    // 等待请求完成
    await requestState.requestPromise;
  } catch (error) {
    console.error("获取组织数据失败:", error);
    requestState.isRequesting = false;
    requestState.requestPromise = null;
    loading.value = false;
  }
};

// 清除缓存的方法（可选导出）
const clearOrgCache = () => {
  delete cacheStore.orgs[CACHE_KEY];
};

// 组织选择变化
const changeOrg = (val: any) => {
  if (!val) {
    orgValue.value = "";
    emit("update:orgValue", orgValue.value);
    emit("change", orgValue.value);
    return;
  }

  if (props.multiple) {
    orgValue.value = Array.isArray(val)
      ? [...new Set(val.flat(Infinity))]
      : val;
  } else {
    orgValue.value = val || "";
  }

  emit("update:orgValue", orgValue.value);
  emit("change", orgValue.value);
};

// 监听props.org变化
watch(
  () => props.org,
  (val) => {
    orgValue.value = val;
  },
  { immediate: true }
);

// 监听orgType变化，重新获取数据
watch(
  () => props.orgType,
  () => {
    // 清除缓存并重新获取
    clearOrgCache();
    getOptions();
  }
);

onMounted(() => {
  getOptions();
});

// 暴露方法给父组件
defineExpose({
  clearOrgCache,
  refreshOrgOptions: () => {
    clearOrgCache();
    return getOptions();
  },
});
</script>

<style lang="scss">
// 可以添加一些自定义样式
</style>
