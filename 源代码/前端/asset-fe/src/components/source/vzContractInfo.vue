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
      v-model="dialogVisible"
      append-to-body
      :destroy-on-close="true"
    >
      <tables
        ref="vzTableRef"
        :is-show-btn="false"
        :filter-data="props.filterData"
        select-type="radio"
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
import { ref, watch, defineAsyncComponent } from "vue";
import { ElMessage } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { useBaseStore } from "@/stores/modules/baseInfo";

// 引入组件
const tables = defineAsyncComponent(() => import("@/views/source/contractInfo/index.vue"));

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
  title: "合同信息",
  filterData: () => ({})
});

const emit = defineEmits(["handleOk", "open", "doubleClick", "clear", "update:value"]);

//定义变量
const dialogTitle = ref(props.title);
const vzTableRef = ref();
const dictList = ref<EmptyObjectType>({});
const value = ref(props.defaultValue);
const dictionaryName = ref();
const dialogVisible = ref(false);

//定义方法
const handleFocus = () => {
  dialogVisible.value = true;
};

//双击
const doubleClick = (obj: EmptyObjectType) => {
  emit("handleOk", obj.row);
  emit("update:value", obj.row.id);
  dialogVisible.value = false;
};

//单击
const rowclick = (obj: EmptyObjectType) => {
  dictList.value = obj.row;
};

//点击确定
const handleOk = () => {
  if (Object.keys(dictList.value).length === 0) {
    ElMessage.warning("请先选择" + props.title);
    return;
  }
  emit("handleOk", dictList.value);
  emit("update:value", dictList.value.id);
  dialogVisible.value = false;
};

//打开弹窗
const open = () => {
  emit("open");
};

//取消弹窗
const cancelDialog = () => {
  dialogVisible.value = false;
};

//转中文
const exchangePlacehodler = () => {
  return dictionaryName.value;
};

//获取中文
const getName = async (id: number | string) => {
  if (!id) return;
  const data: any = await useBaseStore().findContractName(id);
  return data.contractName;
};

//watch默认值
watch(
  () => props.defaultValue,
  newId => {
    if (newId) {
      getName(newId).then((res: any) => {
        dictionaryName.value = res;
      });
      value.value = newId;
      emit("update:value", newId);
    } else {
      dictionaryName.value = "";
    }
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
