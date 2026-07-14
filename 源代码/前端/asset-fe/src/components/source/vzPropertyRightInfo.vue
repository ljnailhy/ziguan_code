<template>
  <div style="width: 100%">
    <span v-if="props.disabled">{{ value }}</span>
    <el-input
      v-else
      v-model="value"
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
      append-to-body
      :destroy-on-close="true"
    >
      <div class="table-box">
        <vz-table
          ref="vzTableRef"
          @rowdouble-click="doubleClick"
          @row-click="rowclick"
          :columns="columns"
          :request-api="getTableList"
          :init-param="props.filterData"
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
import { ColumnProps, TypeProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { propertyRightInfo } from "@/api/modules/property/propertyRightInfo/api"; // api
import { PropertyRightInfoPageRequest, PropertyRightInfoDTO } from "@/api/modules/property/propertyRightInfo/interface";
import { useBaseStore } from "@/stores/modules/baseInfo";
// 引入组件

// 定义父组件传过来的值
interface propertyRightInfoProps {
  filterData?: { [key: string]: any };
  disabled?: boolean;
  placeholder?: string;
  defaultValue?: string | number;
  companyId?: string;
  title?: string;
  selectType?: TypeProps;
}

const props = withDefaults(defineProps<propertyRightInfoProps>(), {
  placeholder: "",
  disabled: false,
  defaultValue: "",
  companyId: "",
  title: "产权信息",
  filterData: () => ({}),
  selectType: "radio"
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
// const dictionaryName = ref<string | undefined>("");
//定义方法
const handleFocus = () => {
  state.dialogVisible = true;
};
// 表格配置项
const columns = reactive<ColumnProps<PropertyRightInfoDTO>[]>([
  { type: props.selectType, fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "propertyCode",
    label: "权证号/编号",
    width: 200,
    search: { el: "input" }
  },
  {
    prop: "assetUnitState",
    label: "资产单元状态",
    width: "120",
    enum: () => useBaseStore().findEnumByName("ASSET_UNIT_STATE")
  },
  {
    prop: "originalValue",
    label: "资产原值",
    width: "150",
    type: "money"
  },
  {
    prop: "area",
    label: "面积(㎡)",
    width: 100,
    search: { el: "input" }
  },
  {
    prop: "propertyOwner",
    label: "产权人名称",
    width: 120,
    search: { el: "input" }
  },
  {
    type: "date",
    prop: "propertyEndDate",
    label: "权证到期日",
    width: 120
  },
  {
    type: "date",
    prop: "propertyTransferOwnership",
    label: "资产登记日期",
    width: 120
  },
  {
    prop: "assetUse",
    label: "资产用途",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "address",
    label: "坐落",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "remark",
    label: "备注",
    width: 120
    // search: { el: "input" }
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
  //   width: 120,
  //   search: {
  //     key: "createStampRange", //指定搜索的key
  //     el: "date-picker",
  //     span: 1,
  //     props: { type: "datetimerange", format: "YYYY-MM-DD HH:mm:ss", valueFormat: "x" }
  //   }
  // }
  // {
  //   prop: "operation",
  //   label: "操作",
  //   fixed: "right",
  //   width: 220,
  //   isShow: BUTTONS.value.edit || BUTTONS.value.detail || BUTTONS.value.delete
  // }
]);
const getTableList = (params: PropertyRightInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return propertyRightInfo().findAll(newParams);
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

// const exchangePlacehodler = () => {
//   return dictionaryName.value;
// };
// const getCode = async (id: number | string) => {
//   if (!id) return;
//   const { data } = await propertyRightInfo().findById(id);
//   return data.propertyCode;
// };
//watch默认值
watch(
  () => props.defaultValue,
  newId => {
    value.value = newId;
  },
  { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
);
</script>
<style lang="scss" scoped></style>
