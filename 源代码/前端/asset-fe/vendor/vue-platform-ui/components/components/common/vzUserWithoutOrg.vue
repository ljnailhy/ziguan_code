<template>
  <div style="width: 100%">
    <el-select
      v-model="modelValue"
      :multiple="isMultiple"
      remote-show-suffix
      filterable
      clearable
      remote
      reserve-keyword
      :disabled="props.disabled"
      :loading="loading"
      placeholder="搜索真实姓名 / 选择人员"
      @change="handleChange"
      style="width: 100%"
    >
      <!-- <template #footer>
        <el-pagination
          @current-change="handleCurrentChange"
          small
          background
          layout="prev, pager, next"
          :total="total"
          class="mt-4"
        />
      </template> -->
      <el-option
        v-for="item in options"
        :key="item.id"
        :label="item.username"
        :value="item.id"
      >
        <span style="font-size: 14px; font-weight: bold">{{
          item.username
        }}</span>
        <span style="color: var(--el-color-info-light-5)"> / </span>
        <span style="color: var(--el-color-info-light-5); font-size: 12px">{{
          item.account
        }}</span>
        <!-- <span style="color: var(--el-color-primary)"> / </span>
        <span>{{ item.phone }}</span> -->
      </el-option>
    </el-select>
  </div>
</template>
<script setup lang="ts" name="vzUser">
import { ref, Ref, onMounted, computed, PropType, watch } from "vue";
import { useUsersApi } from "../../api/system/user";
import { useOrgApi } from "../../api/system/org";

interface Option {
  id: string | number;
  username: string;
  account: string;
  phone: string | number;
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
  type: {
    type: String,
    default: () => "user",
  },
  orgCode: {
    type: [String, Number],
    default: () => "",
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
const params = ref({
  current: 1,
  size: 10000,
});

const options = ref<Array<Option>>([]);
const total = ref(0);
const loading = ref(false);
const isMultiple = computed(() => props.multiple === true); // 根据选择模式判断是否启用多选功能

const handleChange = (value: string | number) => {
  modelValue.value = value;
  emit("update:modelValue", value);
  emit("change", value);
};
// const remoteMethod = (query: string) => {
//   if(!query)return
//   keyword.value = query;
//   getUserFun();
// };

// const handleCurrentChange = (val: number) => {
//   params.value.current = val;
//   getUserFun();
// };
const orgId = ref();
const getOrgId = async () => {
  if (!props.orgCode) return;
  const { data } = await useOrgApi().findAll(
    { size: 1, current: 1, orgCode: props.orgCode },
    { size: 1, current: 1 }
  );
  if (data && data.length) {
    orgId.value = data[0].id;
  }
};
const getUserFun = async () => {
  await getOrgId();
  loading.value = true;
  useUsersApi()
    .findByOrg(
      {
        ...params.value,
        orgId: orgId.value,
      },
      params.value
    )
    .then((res) => {
      options.value = res.data;
      total.value = res.page.total;
    })
    .finally(() => {
      loading.value = false;
    });
};

watch(
  () => props.dictValue,
  (val: any) => {
    if (val) {
      if (props.multiple && val) {
        const newVal = val
          .toString()
          .split(",")
          .map((str) => str.trim())
          .map(Number)
          .filter((num) => !isNaN(num));
        modelValue.value = Array.isArray(val) ? val : newVal;
      } else {
        modelValue.value = Number(val);
      }
    } else {
      modelValue.value = val;
    }
  },
  { immediate: true, deep: true }
);
onMounted(() => {
  getUserFun();
});
</script>
<style lang="scss"></style>
