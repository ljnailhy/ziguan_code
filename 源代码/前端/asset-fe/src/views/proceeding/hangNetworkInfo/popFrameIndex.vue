<template>
  <el-dialog :title="dialogTitle" @before-close="handleOk" width="70%" v-model="state.dialogVisible" append-to-body>
    <div class="table-demo-container layout-padding">
      <div class="table-demo-padding layout-padding-view layout-padding-auto">
        <form-table :table-data="hangNetworkInfoRequestList" :readonly="isView"> </form-table>
      </div>
    </div>
    <template #footer>
      <el-button class="c_blue" @click="cancelDialog">取消</el-button>
      <el-button type="primary" @click="handleOk" v-if="!isView">确定</el-button>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
import { reactive, ref } from "vue";

// 引入组件

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
  title: "挂网信息",
  filterData: () => ({})
});

//定义变量
const dialogTitle = ref(props.title);
const state = reactive({
  dialogVisible: false,
  dictionaryName: ""
});
const rowRef = ref();
const isView = ref();
//定义方法
const handleFocus = (row: any, isViewPage: Boolean) => {
  //  查询
  isView.value = isViewPage;
  rowRef.value = row;
  if (rowRef.value?.hangNetworkInfoRequestList) {
    hangNetworkInfoRequestList.value.data = rowRef.value.hangNetworkInfoRequestList;
  }
  state.dialogVisible = true;
};

const hangNetworkInfoRequestList = ref({
  data: [],
  header: [
    {
      prop: "hangNetworkType",
      label: "类型",
      width: "80",
      isRequired: true,
      type: "select",
      dictType: "HANG_NETWORK_TYPE"
    },
    {
      prop: "hangNetworkDate",
      label: "挂网时间",
      width: "120",
      type: "date"
    },
    {
      prop: "hangNetworkQuotation",
      label: "挂网价格",
      width: "90",
      type: "money",
      max: 999999999
    },
    {
      prop: "hangNetworkMoney",
      label: "成交价",
      width: "90",
      type: "money",
      max: 999999999
    },
    {
      prop: "remark",
      label: "备注",
      width: "200",
      maxlength: 50,
      type: "text"
    }
  ]
});
//点击确定
const handleOk = () => {
  rowRef.value.hangNetworkInfoRequestList = hangNetworkInfoRequestList.value.data;
  state.dialogVisible = false;
};

//取消弹窗
const cancelDialog = () => {
  state.dialogVisible = false;
};
defineExpose({
  handleFocus
});
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
