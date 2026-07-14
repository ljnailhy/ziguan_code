<template>
  <div style="width: 100%">
    <span v-if="props.disabled">{{ dictionaryName }}</span>
    <el-input
      v-else
      v-model="value"
      :value="exchangePlacehodler()"
      :suffix-icon="Search"
      :placeholder="props.placeholder || dialogTitle"
      clearable
      style="width: 100%"
      :disabled="props.disabled || false"
      @click="handleFocus"
      readonly
    >
    </el-input>
    <el-dialog
      @open="open"
      :title="dialogTitle"
      @before-close="handleOk"
      width="70%"
      v-model="state.dialogVisible"
      :destroy-on-close="true"
      append-to-body
    >
      <tables
        ref="vzTableRef"
        :is-show-btn="true"
        @row-click="rowclick"
        select-type="radio"
        :show-select="true"
        @rowdouble-click="doubleClick"
      ></tables>
      <template #footer>
        <el-button class="c_blue" @click="cancelDialog">取消</el-button>
        <el-button type="primary" @click="handleOk">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref, watch, defineAsyncComponent } from "vue";
import { ElMessage } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { subjectInfo } from "@/api/modules/source/subjectInfo/api"; // api

// 引入组件
const tables = defineAsyncComponent(() => import("@/views/source/subjectInfo/index.vue"));

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
  title: "主体信息",
  filterData: () => ({})
});

const emit = defineEmits(["handleOk", "open", "doubleClick", "clear"]);

//定义变量
const dialogTitle = ref(props.title);
const vzTableRef = ref();
const dictList = ref<EmptyObjectType>({});
const select = ref<EmptyArrayType>([]);
const value = ref(props.defaultValue);
const state = reactive({
  dialogVisible: false
});
const dictionaryName = ref<string | undefined>("");
//定义方法
const handleFocus = () => {
  state.dialogVisible = true;
};

//双击
const doubleClick = (obj: EmptyObjectType) => {
  emit("handleOk", obj.row);
  state.dialogVisible = false;
};

const rowclick = (obj: EmptyObjectType) => {
  dictList.value = obj.row;
};

//点击确定
const handleOk = () => {
  if (Object.keys(dictList.value).length === 0 && select.value.length === 0) {
    ElMessage.warning("请先选择" + props.title);
    return;
  }
  emit("handleOk", dictList.value);
  state.dialogVisible = false;
};

const open = () => {
  emit("open");
};

//取消弹窗
const cancelDialog = () => {
  state.dialogVisible = false;
};

const exchangePlacehodler = () => {
  return dictionaryName.value;
};
const getName = async (id: number | string) => {
  if (!id) return;
  const { data } = await subjectInfo().findById(id);
  return data.subjectName;
};
//watch默认值
watch(
  () => props.defaultValue,
  newId => {
    getName(newId).then(res => {
      dictionaryName.value = res;
    });
    value.value = newId;
  },
  { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
);
watch(
  () => props.filterData,
  newArr => {
    vzTableRef.value?.initParam(newArr);
  },
  { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
);
</script>
<style lang="scss" scoped>
.el-button {
  min-width: 80px;
}
.table-demo {
  flex: 1;
  overflow: hidden;
}
</style>
