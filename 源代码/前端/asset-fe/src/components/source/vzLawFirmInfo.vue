<template>
  <div style="width: 100%" class="flx">
    <span v-if="props.disabled">{{ state.dictionaryName || "--" }}</span>
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
      <div class="table-box">
        <vz-table
          @rowdouble-click="doubleClick"
          @row-click="rowclick"
          ref="vzTableRef"
          :columns="columns"
          :request-api="getTableList"
          :init-param="initParam"
        >
        </vz-table>
      </div>
      <template #footer>
        <el-button class="c_blue" @click="cancelDialog">取消</el-button>
        <el-button type="primary" @click="handleOk">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref, watch } from "vue";
import { ElMessage } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { LawFirmInfoPageRequest, LawFirmInfoDTO } from "@/api/modules/source/lawFirmInfo/interface";
import { ColumnProps } from "@/components/VzTable/interface";
import { lawFirmInfo } from "@/api/modules/source/lawFirmInfo/api"; // api

// 引入组件

// 定义父组件传过来的值
interface LawFirmInfoProps {
  filterData?: { [key: string]: any };
  disabled?: boolean;
  placeholder?: string;
  defaultValue?: string | number;
  companyId?: string;
  title?: string;
}
const getTableList = (params: LawFirmInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return lawFirmInfo().findAssemblyList(newParams);
};
// 表格配置项
const columns = reactive<ColumnProps<LawFirmInfoDTO>[]>([
  { type: "radio", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "name",
    label: "律所名称",
    minWidth: 120,
    search: { el: "input" }
  },
  {
    prop: "projectNumber",
    label: "在管项目数(个)",
    width: 160
  },
  {
    prop: "compensationMoneySum",
    label: "累计追偿金额(元)",
    type: "money",
    width: 160
  },
  {
    prop: "collectionAmountSum",
    label: "累计回款金额(元)",
    type: "money",
    width: 160
  },
  {
    prop: "collectionRate",
    label: "回款率(%)",
    width: 120
  }
  // {
  //   prop: "creator",
  //   type: "user",
  //   label: "创建人",
  //   width: 120
  // },
  // {
  //   type: "date",
  //   prop: "createStamp",
  //   label: "创建时间",
  //   width: 120
  // }
]);
const props = withDefaults(defineProps<LawFirmInfoProps>(), {
  placeholder: "",
  disabled: false,
  defaultValue: "",
  companyId: "",
  title: "律所信息",
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
  dialogVisible: false,
  dictionaryName: ""
});
//定义方法
const handleFocus = () => {
  state.dialogVisible = true;
};
const initParam = reactive({});
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
  return state.dictionaryName;
};
const getName = async (id: number | string) => {
  if (!id) return;
  const { data } = await lawFirmInfo().findById(id);
  return data.name;
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
