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
      <tables
        ref="vzTableRef"
        :is-show-btn="false"
        :filter-data="filterData"
        :select-type="props.selectType"
        @row-click="rowclick"
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
import { useBaseStore } from "@/stores/modules/baseInfo";
import { TypeProps } from "@/components/VzTable/interface"; //
// 引入组件
const tables = defineAsyncComponent(() => import("@/views/compensatory/projectInfo/index.vue"));

// 定义父组件传过来的值
interface customerInfoInfoProps {
  filterData?: { [key: string]: any };
  disabled?: boolean;
  placeholder?: string;
  defaultValue?: string | number;
  companyId?: string;
  title?: string;
  selectType: TypeProps;
}

const props = withDefaults(defineProps<customerInfoInfoProps>(), {
  placeholder: "",
  disabled: false,
  defaultValue: "",
  companyId: "",
  title: "项目信息",
  filterData: () => ({})
});

const emit = defineEmits(["handleOk", "open", "doubleClick", "clear"]);

//定义变量
const dialogTitle = ref(props.title);
const vzTableRef = ref();
const dictList = ref<EmptyObjectType>({});
const value = ref(props.defaultValue);
const state = reactive({
  dialogVisible: false,
  dictionaryName: ""
});
//定义方法
const handleFocus = () => {
  state.dialogVisible = true;
};

const selectOne = ref<any>({});
//双击
const doubleClick = async (obj: EmptyObjectType) => {
  // await getName(obj.row.id);
  emit("handleOk", obj.row);
  state.dialogVisible = false;
};

const rowclick = (obj: EmptyObjectType) => {
  dictList.value = obj.row;
};

//点击确定
const handleOk = async () => {
  const selectedList = vzTableRef.value.vzTableRef.selectedList;
  if (Object.keys(dictList.value).length === 0 && selectedList === 0) {
    ElMessage.warning("请先选择" + props.title);
    return;
  }
  if (selectedList && selectedList.length === 1) {
    emit("handleOk", selectedList[0]);
  } else if (selectedList && selectedList.length > 1) {
    emit("handleOk", selectedList);
  } else {
    emit("handleOk", dictList.value);
  }
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
//查找中文
const getName = async (id: number | string) => {
  if (!id) return;
  const data: any = await useBaseStore().findProjectName(id);
  selectOne.value = data;

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
// watch(
//   () => props.filterData,
//   newArr => {
//     vzTableRef.value?.changeDefaultParam(newArr);
//   },
//   { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
// );
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
