<template>
  <div style="width: 100%">
    <span v-if="props.disabled">{{ state.dictionaryName }}</span>
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
      <tables ref="tableRef" @rowclick="rowclick" @row-click="rowclick" @rowdouble-click="doubleClick"></tables>
      <template #footer>
        <el-button class="c_blue" @click="cancelDialog">取消</el-button>
        <!-- <el-button type="primary" @click="handleOk">确定</el-button> -->
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref, watch, defineAsyncComponent } from "vue";
import { ElMessage } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api"; // api

// 引入组件
const tables = defineAsyncComponent(() => import("./index.vue"));

// 定义父组件传过来的值
interface customerInfoInfoProps {
  filterData?: { [key: string]: any };
  disabled?: boolean;
  placeholder?: string;
  defaultValue?: string | number;
  companyId?: string;
  title?: string;
}

const props = withDefaults(defineProps<customerInfoInfoProps>(), {
  placeholder: "",
  disabled: false,
  defaultValue: "",
  companyId: "",
  title: "工作登记",
  filterData: () => ({})
});
interface ParamsType {}
const emit = defineEmits(["handleOk", "open", "doubleClick", "clear"]);

//定义变量
const dialogTitle = ref(props.title);
const tableRef = ref<any>(null);
const dictList = ref<EmptyObjectType>({});
const value = ref(props.defaultValue);
const params = ref<ParamsType>({ doId: "", doType: "" });
const state = reactive({
  dialogVisible: false,
  dictionaryName: ""
});
//定义方法
const handleFocus = (doType: String, doId: number) => {
  params.value = {
    doType: doType,
    doId: doId
  };
  localStorage.setItem("initParams", JSON.stringify(params.value));
  state.dialogVisible = true;
  // setTimeout(() => {
  //   tableRef.value?.handleAccept(params.value);
  //   localStorage.setItem("initParams", JSON.stringify(params.value));
  // }, 100);
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
  if (tableRef.value.selectedListIds.length === 0) {
    ElMessage.warning("请先选择" + props.title);
    return;
  }
  emit("handleOk", tableRef.value.selectedListIds);
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
  return state.dictionaryName;
};
const getName = async (id: number | string) => {
  if (!id) return;
  const { data } = await projectInfo().findById(id);
  return data.projectName;
};
//watch默认值
watch(
  () => props.defaultValue,
  newId => {
    getName(newId).then(res => {
      state.dictionaryName = res || "";
    });
    value.value = newId;
  },
  { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
);
watch(
  () => props.filterData,
  newArr => {
    tableRef.value?.changeDefaultParam(newArr);
  },
  { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
);
// 暴露给父组件使用
defineExpose({ handleFocus });
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
