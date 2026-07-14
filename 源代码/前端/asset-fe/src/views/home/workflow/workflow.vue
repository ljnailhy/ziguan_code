<template>
  <div>
    <el-drawer
      :title="state.title"
      v-model="state.isShowDialog"
      :destroy-on-close="true"
      size="100%"
      :close-on-click-modal="false"
    >
      <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick" type="border-card">
        <el-tab-pane label="任务办理" name="approval">
          <div class="scrollbox">
            <component ref="form" :is="flowComponents[rowData.formKey.replaceAll('_edit', '')]" />
            <iframe
              v-if="activeName === 'approval' && !rowData.duration"
              width="100%"
              height="900px"
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
        <el-tab-pane label="流程跟踪" name="track">
          <div class="scrollbox">
            <iframe v-if="activeName === 'track'" width="100%" height="100%" frameborder="no" :src="frameUrl"></iframe>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-drawer>
  </div>
</template>

<script setup lang="ts" name="workflow">
import { ref, nextTick, watch, computed } from "vue";
import { ElMessage, type TabsPaneContext } from "element-plus";
import flowComponents from "@/components/common/Components";
import { getEventData } from "@/utils/windowMessageUtils";
import { useDialogStore } from "@/stores/modules/dialogParams";
// import { postToast } from "@/utils/windowMessageUtils";

const workflowDomain = import.meta.env.VITE_APP_WORKFLOW_DOMAIN;
// const vehicleExamineApprove = defineAsyncComponent(() => import("/@/views/vehicle/examineApprove/vehicleExamineApprove.vue"));

type Props = {
  getList: () => void;
};

const props = withDefaults(defineProps<Props>(), {});

// // 定义变量内容
const activeName = ref<string | number | undefined>("approval");
const state = ref({ isShowDialog: false, type: "", title: "审批流程", submitTxt: "", refresh: 0 });

// 打开弹窗
const rowData = ref<EmptyObjectType>({});
const dialogParams = ref({});
const form = ref();
const messageEventHandler = async event => {
  const iframeWindow = window.frames[0];
  const data = getEventData(event, iframeWindow);

  if (!data) {
    return;
  }

  switch (data?.type) {
    //只保存
    case "save":
      const saveresult = await save();

      if (!saveresult) break;
      onCancel();
      break;
    // 同意
    case "agree":
      const result = await save();
      if (!result) break;
      if (result && Array.isArray(result) && result.length > 0) {
        onCancel();
        break;
      }
      // 给子页面发送保存数据的消息
      iframeWindow.postMessage(
        {
          type: "saveData"
        },
        event.origin
      );
      break;
    case "close":
      onCancel();
      break;
    // 流程中进行保存
    case "save_approve":
      // 保存
      const approveresult = await save();
      if (!approveresult) break;
      // 给父页面发送保存数据的消息
      iframeWindow.postMessage(
        {
          type: "saveData"
        },
        event.origin
      );
      break;
    default:
      // 给父页面发送保存数据的消息
      if (iframeWindow) {
        iframeWindow.postMessage(
          {
            type: "saveData"
          },
          event.origin
        );
      }
      break;
  }
};
const openDialog = (row: EmptyObjectType) => {
  state.value.refresh = Math.random();
  rowData.value = row.row;
  const isReadOnly = rowData.value.duration ? true : false;
  const isView = !rowData.value.formKey.includes("_edit");
  dialogParams.value = {
    id: rowData.value.procVars.doId,
    title: "查看",
    readonly: isReadOnly,
    isView: isView,
    getTableList: props.getList
  };
  useDialogStore().setDrawerProps(dialogParams.value); //存缓存
  state.value.isShowDialog = true;
  initUrl(row.row);
  nextTick(() => {
    window.addEventListener("message", messageEventHandler);
  });
};

const handleClick = (tab: TabsPaneContext) => {
  if (tab.paneName === "approval") {
    dialogParams.value = {
      id: rowData.value.procVars.doId,
      title: "查看",
      isView: !rowData.value.formKey.includes("_edit"),
      getTableList: props.getList
    };
    useDialogStore().setDrawerProps(dialogParams.value); //存缓存
    // form.value.handleAccept(dialogParams.value);
  }
  activeName.value = tab.paneName;
  initUrl(rowData.value);
};

const save = async () => {
  const result = await form.value?.handleSave();
  return result;
};

// 使用 computed 来获取 Pinia store 中的校验错误消息
const validationErrorMessage = computed(() => useDialogStore().validationErrorMessage);

watch(
  () => validationErrorMessage.value,
  val => {
    if (val) {
      if (val === "pass") {
        onCancel();
      } else {
        ElMessage.warning(validationErrorMessage.value);
      }
      console.log(val);
      useDialogStore().setValidationErrorMessage(""); // 清除错误消息
    }
  },
  { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
);
// watchEffect(() => {
//   if (validationErrorMessage.value && validationErrorMessage.value === "pass") {
//     state.value.isShowDialog = false;
//   } else if (validationErrorMessage.value && validationErrorMessage.value !== "pass") {
//     ElMessage.warning(validationErrorMessage.value);
//   }
//   debugger;
//   useDialogStore().setValidationErrorMessage(""); // 清除错误消息
// });

const frameUrl = ref("");
const initUrl = (row: EmptyObjectType) => {
  frameUrl.value = `${workflowDomain}/#/workflow/process/detail/${row.procInsId}?refresh=${state.value.refresh}&taskId=${row.taskId}&processed=true&activeName=${activeName.value}`;
};
// 关闭弹窗
const onCancel = () => {
  state.value.isShowDialog = false;
  window.removeEventListener("message", messageEventHandler);
  setTimeout(() => {
    props?.getList();
  }, 500);
};

watch(
  () => form.value,
  val => {
    if (val) {
      dialogParams.value = {
        id: rowData.value.procVars.doId,
        title: "查看",
        isView: !rowData.value.formKey.includes("_edit"),
        getTableList: props.getList
      };
      useDialogStore().setDrawerProps(dialogParams.value); //存缓存
    }
  },
  { immediate: true } // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
);

// 暴露变量
defineExpose({
  openDialog,
  onCancel
});
</script>
<style scoped lang="scss">
:deep(.el-tabs__content div.scrollbox) {
  height: calc(90vh - 90px);
  overflow-y: auto;
}
:deep(.el-drawer__body) {
  overflow: hidden;
}
</style>
