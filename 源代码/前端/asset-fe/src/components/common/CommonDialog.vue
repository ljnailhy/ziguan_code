<template>
  <el-dialog
    v-model="drawerVisible"
    :close-on-click-modal="false"
    v-if="drawerVisible"
    width="80%"
    :title="`${drawerProps.title}`"
    :before-close="oncancel"
  >
    <component
      ref="createUpdateRef"
      :loading="Components[drawerProps.dialogName]"
      :is="Components[drawerProps.dialogName]"
      @close-drawer="oncancel"
    />
    <template #footer>
      <el-button v-show="!drawerProps.isView" type="primary" @click="handleSave">保存</el-button>
      <el-button v-show="!drawerProps.isView && drawerProps.showBtn" type="primary" @click="handleSubmit">提交</el-button>
      <el-button @click="oncancel" type="primary" plain>取消</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts" name="CommonDialog">
import { ref } from "vue";
import { useDialogStore } from "@/stores/modules/dialogParams";
import Components from "./Components";

interface DrawerProps {
  dialogName: string;
  title: string;
  isView: boolean;
  showBtn?: boolean;
  id?: number;
  api?: (params: any) => Promise<any>;
  getTableList?: () => void;
}

const drawerVisible = ref(false);
const drawerProps = ref<DrawerProps>({
  dialogName: "",
  isView: false,
  showBtn: false,
  title: ""
});

// 接收父组件传过来的参数
const componentKey = ref(0);
const createUpdateRef = ref();
const acceptParams = (params: DrawerProps) => {
  componentKey.value = Math.random();
  drawerProps.value = params;
  drawerVisible.value = true;
};

// 提交数据（新增/编辑）
const handleSave = () => {
  createUpdateRef.value!.handleSave(drawerVisible.value);
};

// 提交数据（流程）
const handleSubmit = () => {
  createUpdateRef.value!.handleSubmit(drawerVisible.value);
};

const oncancel = () => {
  componentKey.value = Math.random();
  useDialogStore().clearDeleteFiles();
  useDialogStore().setDrawerProps({});
  drawerVisible.value = false;
};

defineExpose({
  acceptParams
});
</script>
<style scoped lang="scss">
:deep(.el-dialog) {
  display: flex !important;
  flex-direction: column !important;
  margin: 0 !important ;
  position: absolute !important;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
  height: 90vh !important;
}

:deep(.el-dialog .el-dialog__body) {
  flex: 1 !important;
  overflow: auto !important;
  height: calc(90vh - 111px) !important;
}
</style>
