<template>
  <div>
    <el-dialog :title="state.dialog.title" v-model="state.dialog.isShowDialog" width="80%" :close-on-click-modal="false">
      <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick" type="border-card">
        <el-tab-pane label="流程跟踪" name="track">
          <div class="scrollbox">
            <iframe
              ref="iframeRef"
              v-if="activeName === 'track'"
              width="100%"
              height="100%"
              frameborder="no"
              :src="frameUrl"
            ></iframe>
          </div>
        </el-tab-pane>
        <el-tab-pane label="流转记录" name="record">
          <div class="scrollbox">
            <iframe v-if="activeName === 'record'" width="100%" height="100%" frameborder="no" :src="frameUrl"></iframe>
          </div>
        </el-tab-pane>
      </el-tabs>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="onCancel" size="default">取 消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="vzFlowchart">
import { reactive, ref } from "vue";
import { processApi } from "@/api/modules/workflow/process";

const workflowDomain = import.meta.env.VITE_APP_WORKFLOW_DOMAIN;

// 定义变量内容
const activeName = ref<string | number | undefined>("track");
const frameUrl = ref("");
const iframeRef = ref();
const state = reactive({
  dialog: {
    isShowDialog: false,
    title: "流程图"
  }
});

// 打开弹窗
const rowData = ref<string>("");
const openDialog = (id: number) => {
  processApi()
    .getHistoricVariableByDoId(id)
    .then((res: EmptyObjectType) => {
      state.dialog.isShowDialog = true;

      rowData.value = res.data;
      initUrl(res.data);
    });
};

const handleClick = (tab: any) => {
  activeName.value = tab.paneName;
  initUrl(rowData.value);
};
const initUrl = (row: string) => {
  // procInsId: '4f4111e9-1cd9-11ef-ac07-4e776613c59d',
  frameUrl.value = `${workflowDomain}/#/workflow/process/detail/${row}?refresh=${Math.random()}&taskId=&processed=true&activeName=${
    activeName.value
  }`;
};
// 关闭弹窗
const onCancel = () => {
  state.dialog.isShowDialog = false;
};
// 暴露变量
defineExpose({
  openDialog
});
</script>
<style scoped lang="scss">
div.scrollbox {
  height: calc(90vh - 120px);
  overflow-y: auto;
}
</style>
