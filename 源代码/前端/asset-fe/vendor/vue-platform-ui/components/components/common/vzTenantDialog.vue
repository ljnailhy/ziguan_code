<template>
  <div>
    <el-input
      v-model="company"
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
      append-to-body
    >
      <div class="table-demo-container layout-padding">
        <div class="table-demo-padding layout-padding-view layout-padding-auto">
          <vz-table
            ref="vzTableRef"
            :tableData="state.tableData"
            @row-click="rowclick"
            @row-dblclick="doubleClick"
          ></vz-table>
        </div>
      </div>
      <template #footer>
        <el-button class="c_blue" @click="cancelDialog">取消</el-button>
        <el-button type="primary" @click="handleOk">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref, defineAsyncComponent, watch, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { Search } from "@element-plus/icons-vue";
import { useTenantApi } from "../../api/system/tenant";

// 引入组件
// const vzTable = defineAsyncComponent(() => import('../table/index.vue'));

// 定义父组件传过来的值
const props = defineProps({
  // 占位符
  placeholder: {
    type: String,
    default: () => "",
  },
  // 是否禁用
  disabled: {
    type: Boolean,
    default: () => false,
  },
  select: {
    type: [String, Number],
    default: () => "",
  },
  company: {
    type: [String, Number],
    default: () => "",
  },
  companyId: {
    type: [String, Number],
    default: () => "",
  },
  type: {
    type: String,
    default: () => "",
  },
  unchanged: {
    type: Object,
    default: () => <EmptyObjectType>{},
  },
  isSelection: {
    type: Boolean,
    default: () => false,
  },
  row: {
    type: Object,
    default: () => <EmptyObjectType>{},
  },
});

const emit = defineEmits(["handleOk", "open", "doubleClick", "clear"]);

//定义变量
const dialogTitle = ref("选择租户");
const companyList = ref<EmptyObjectType>({});
const select = ref<EmptyArrayType>([]);
const company = ref(props.company);
const state = reactive({
  dialogVisible: false,
  concactName: "",
  tableData: {
    // 列表数据（必传）
    data: [],
    // 表头内容（必传，注意格式）
    header: [
      {
        key: "logo",
        colWidth: "100",
        title: "租户logo",
        type: "img",
        isCheck: true,
      },
      {
        key: "tenantCode",
        colWidth: "150",
        title: "租户编码",
        type: "text",
        isCheck: true,
      },
      {
        key: "tenantName",
        colWidth: "150",
        title: "租户名称",
        type: "text",
        isCheck: true,
      },
      {
        key: "tenantType",
        colWidth: "100",
        title: "租户类型",
        type: "text",
        isCheck: true,
      },
      {
        key: "versionId",
        colWidth: "100",
        title: "版本ID",
        type: "text",
        isCheck: true,
      },
      {
        key: "versionUseDeadline",
        colWidth: "130",
        title: "使用截止时间",
        type: "date",
        isCheck: true,
      },
      {
        key: "contactName",
        colWidth: "100",
        title: "联系人",
        type: "text",
        isCheck: true,
      },
      {
        key: "contactPhone",
        colWidth: "150",
        title: "联系电话",
        type: "text",
        isCheck: true,
      },
      {
        key: "province",
        colWidth: "100",
        title: "省",
        type: "text",
        isCheck: true,
      },
      {
        key: "city",
        colWidth: "100",
        title: "市",
        type: "text",
        isCheck: true,
      },
      {
        key: "district",
        colWidth: "100",
        title: "区",
        type: "text",
        isCheck: true,
      },
      {
        key: "address",
        colWidth: "200",
        title: "详细地址",
        type: "text",
        align: "left",
        isCheck: true,
      },
      {
        key: "enabled",
        colWidth: "90",
        title: "是否启用",
        type: "boolean",
        isCheck: true,
      },
      {
        key: "creator",
        colWidth: "100",
        title: "创建人",
        type: "user",
        isCheck: true,
      },
      {
        key: "createStamp",
        colWidth: "160",
        title: "创建时间",
        type: "datetime",
        isCheck: true,
      },
    ],
    // 配置项（必传）
    config: {
      listApi: useTenantApi().findAll, //列表接口
      delApi: useTenantApi().delete, //删除接口
      total: 0, // 列表总数
      loading: true, // loading 加载
      isBorder: true, // 是否显示表格边框
      isSerialNo: true, // 是否显示表格序号
      isSelection: false, // 是否显示表格多选
      isOperate: false, // 是否显示表格操作栏
    },
    // 搜索表单，动态生成（传空数组时，将不显示搜索，注意格式）
    search: [
      { label: "租户名称", prop: "tenantName" },
      { label: "租户编码", prop: "tenantCode" },
      {
        label: "是否启用",
        prop: "enabled",
        options: [
          { label: "是", value: true },
          { label: "否", value: false },
        ],
      },
      { label: "创建人", prop: "creator", type: "user" },
      { label: "创建时间", prop: "createStampRange", type: "datetimerange" },
    ],
    //表格按钮配置 动态生成（传空数组时，将不显示按钮，注意格式）
    tableButtons: [],
    //搜索表单按钮显示 动态生成（传空数组时，只显示 搜索 重置，注意格式）
    searchButtons: [],
    // 搜索参数 用于默认传参
    param: {},
    // 打印标题
    printName: "租户管理",
  },
});
//定义方法
const handleFocus = () => {
  state.dialogVisible = true;
};

//双击
const doubleClick = (obj: EmptyObjectType) => {
  emit("handleOk", obj);
  state.dialogVisible = false;
};

const rowclick = (obj: EmptyObjectType) => {
  companyList.value = obj;
};

//点击确定
const handleOk = () => {
  if (
    Object.keys(companyList.value).length === 0 &&
    select.value.length === 0
  ) {
    ElMessage.warning("请先选择");
    return;
  }
  emit("handleOk", companyList.value);
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
  return state.concactName;
};
const getAgreementName = async (id: number | string) => {
  if (!id) return;
  const { data } = await useTenantApi().findById(id);
  return data.tenantName;
};
watch(
  () => props.company,
  (newId) => {
    getAgreementName(newId).then((res) => {
      state.concactName = res;
    });
    company.value = newId;
  },
  { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
);

onMounted(() => {});
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
