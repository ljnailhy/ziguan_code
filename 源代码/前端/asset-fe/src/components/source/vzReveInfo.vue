<template>
  <div style="width: 100%">
    <span v-if="props.disabled">{{ state.dictionaryName }}</span>
    <el-input
      v-else
      v-model="value"
      :suffix-icon="Search"
      :value="exchangePlacehodler()"
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
          ref="vzTableRef"
          @rowdouble-click="doubleClick"
          @row-click="rowclick"
          :columns="columns"
          :request-api="getTableList"
          :init-param="props.filterData"
          highlight-current-row
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
import { revePropertyInfo } from "@/api/modules/source/revePropertyInfo/api"; // api
import { ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { RevePropertyInfoPageRequest, RevePropertyInfoDTO } from "@/api/modules/source/revePropertyInfo/interface";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { securityTypeOptions } from "@/api/modules/recovery/projectInfo/interface";

// 表格配置项
const columns = reactive<ColumnProps<RevePropertyInfoDTO>[]>([
  { type: "radio", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "securityWay",
    label: "担保方式",
    minWidth: 120,
    enum: () => useBaseStore().findEnumByName("SECURITY_WAY"),
    search: { el: "select" }
  },
  {
    prop: "securityType",
    label: "担保类型",
    enum: securityTypeOptions,
    search: { el: "select", props: { filterable: true } },
    minWidth: 180
  },
  {
    prop: "reveName",
    label: "反担保人名称",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "reveMeasure",
    label: "反担保措施",
    minWidth: 120,
    search: { el: "input" }
  },
  {
    type: "boolean",
    prop: "isDispose",
    label: "是否已处置",
    width: 120
  },
  {
    prop: "disposeMoney",
    label: "处置回款金额(元)",
    width: 200
    // search: { el: "input" }
  },
  {
    prop: "remark",
    label: "备注",
    width: 120
    // search: { el: "input" }
  },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    width: 120
  },
  {
    type: "date",
    prop: "createStamp",
    label: "创建时间",
    width: 120,
    search: {
      key: "createStampRange", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: {
        type: "datetimerange",
        format: "YYYY-MM-DD HH:mm:ss",
        valueFormat: "x",
        defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
      }
    }
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
// const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: RevePropertyInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return revePropertyInfo().findAll(newParams);
};

// 定义父组件传过来的值
interface agencyInfoProps {
  filterData?: { [key: string]: any };
  disabled?: boolean;
  placeholder?: string;
  defaultValue?: string | number;
  companyId?: string;
  title?: string;
}

const props = withDefaults(defineProps<agencyInfoProps>(), {
  placeholder: "",
  disabled: false,
  defaultValue: "",
  companyId: "",
  title: "反担保信息",
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
// const selectOne = ref<any>({});
//双击
const doubleClick = (obj: EmptyObjectType) => {
  emit("handleOk", obj.row);
  state.dialogVisible = false;
};

const rowclick = (obj: EmptyObjectType) => {
  vzTableRef.value!.radio = obj.id;
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

//查找中文
const getName = async (id: number | string) => {
  if (!id) return;
  const data: any = await useBaseStore().findReveName(id);
  // selectOne.value = data;
  return data.reveMeasure;
};
//watch默认值
watch(
  () => props.defaultValue,
  newId => {
    if (newId) {
      getName(newId).then(res => {
        state.dictionaryName = res || "";
      });
    } else {
      state.dictionaryName = "";
    }

    value.value = newId;
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
