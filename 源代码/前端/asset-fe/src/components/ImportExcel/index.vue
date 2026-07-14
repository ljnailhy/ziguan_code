<template>
  <div>
    <el-dialog v-model="dialogVisible" :title="`批量导入${parameter.title}`" :destroy-on-close="true" width="580px" draggable>
      <el-form class="drawer-multiColumn-form" label-width="100px">
        <el-form-item label="模板下载 :">
          <el-button type="primary" :icon="Download" @click="downloadTemp"> 点击下载 </el-button>
        </el-form-item>
        <el-form-item label="文件上传 :">
          <el-upload
            action="#"
            class="upload"
            :drag="true"
            :limit="excelLimit"
            :multiple="true"
            :show-file-list="true"
            :http-request="uploadExcel"
            :before-upload="beforeExcelUpload"
            :on-exceed="handleExceed"
            :on-success="excelUploadSuccess"
            :on-error="excelUploadError"
            :accept="parameter.fileType!.join(',')"
          >
            <slot name="empty">
              <el-icon class="el-icon--upload">
                <upload-filled />
              </el-icon>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </slot>
            <template #tip>
              <slot name="tip">
                <div class="el-upload__tip">请上传 .xls , .xlsx 标准格式文件，文件最大为 {{ parameter.fileSize }}M</div>
              </slot>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="ImportExcel">
import { ref } from "vue";
// import { useDownload } from "@/hooks/useDownload";
import { Download } from "@element-plus/icons-vue";
import type { Action } from "element-plus";
import { ElNotification, UploadRequestOptions, UploadRawFile } from "element-plus";
import { ElMessage, ElMessageBox } from "element-plus";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api";
export interface ExcelParameterProps {
  title: string; // 标题
  fileSize?: number; // 上传文件的大小
  fileType?: File.ExcelMimeType[]; // 上传文件的类型
  tempApi?: string; // 下载模板的Api
  importApi?: (params: any) => Promise<any>; // 批量导入的Api
  getTableList?: () => void; // 获取表格数据的Api
}

// 最大文件上传数
const excelLimit = ref(1);
// dialog状态
const dialogVisible = ref(false);
// 父组件传过来的参数
const parameter = ref<ExcelParameterProps>({
  title: "",
  fileSize: 50,
  tempApi: "/asset-service/file/template/项目导入模板.xlsx",
  fileType: ["application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"]
});

// 接收父组件参数
const acceptParams = (params: ExcelParameterProps) => {
  parameter.value = { ...parameter.value, ...params };
  dialogVisible.value = true;
};

// Excel 导入模板下载
const downloadTemp = () => {
  const link = document.createElement("a");
  // const name = location.origin;
  // 模板路径 /src/assets/...
  link.href = "/minio" + parameter.value.tempApi;

  link.download = ""; // 如果你想要指定下载的文件名，可以在这里设置
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  ElMessage.success("下载成功");
  // if (!parameter.value.tempApi) return;
  // useDownload(parameter.value.tempApi, `${parameter.value.title}模板`);
};

// 文件上传
let excelFormData = new FormData();
const uploadExcel = async (param: UploadRequestOptions) => {
  excelFormData = new FormData();
  excelFormData.append("file", param.file);
  excelFormData.append("isImport", "");
  const data = await parameter.value.importApi!(excelFormData);
  if (data.code === 0) {
    ElMessage.success("导入成功");
  }
  parameter.value.getTableList && parameter.value.getTableList();
  dialogVisible.value = false;
};

/**
 * @description 文件上传之前判断
 * @param file 上传的文件
 * */
const beforeExcelUpload = (file: UploadRawFile) => {
  const isExcel = parameter.value.fileType!.includes(file.type as File.ExcelMimeType);
  const fileSize = file.size / 1024 / 1024 < parameter.value.fileSize!;
  if (!isExcel)
    ElNotification({
      title: "温馨提示",
      message: "上传文件只能是 xls / xlsx 格式！",
      type: "warning"
    });
  if (!fileSize)
    setTimeout(() => {
      ElNotification({
        title: "温馨提示",
        message: `上传文件大小不能超过 ${parameter.value.fileSize}MB！`,
        type: "warning"
      });
    }, 0);
  return isExcel && fileSize;
};

// 文件数超出提示
const handleExceed = () => {
  ElNotification({
    title: "温馨提示",
    message: "最多只能上传一个文件！",
    type: "warning"
  });
};

// 上传错误提示
const excelUploadError = (error: any) => {
  if (error.code === 201) {
    ElMessageBox.confirm(error.errorMessage, "温馨提示", {
      distinguishCancelAndClose: true,
      confirmButtonText: "全部导入",
      cancelButtonText: "部分导入"
    })
      .then(() => {
        //点击按钮1
        excelFormData.delete("isImport");
        excelFormData.append("isImport", "true");
        projectInfo()
          .importData(excelFormData)
          .then((data: any) => {
            if (data.code === 0) {
              ElMessage.success("导入成功");
              parameter.value.getTableList && parameter.value.getTableList();
            }
          });
        dialogVisible.value = false;
        excelFormData = new FormData();
      })
      .catch((action: Action) => {
        if (action === "cancel") {
          //点击按钮2
          excelFormData.delete("isImport");
          excelFormData.append("isImport", "false");
          projectInfo()
            .importData(excelFormData)
            .then((data: any) => {
              if (data.code === 0) {
                ElMessage.success("导入成功");
                parameter.value.getTableList && parameter.value.getTableList();
              }
            });
          dialogVisible.value = false;
          excelFormData = new FormData();
        } else {
          //点击了关闭
          dialogVisible.value = false;
          excelFormData = new FormData();
        }
      });
  }
  if (error.code === 202) {
    const message = error.errorMessage;
    ElMessageBox.alert(message, "温馨提示", {
      confirmButtonText: "确定",
      customClass: "my-alert-message"
    });
    const messageBox = document.querySelector(".el-message-box") as HTMLElement;
    if (messageBox) {
      messageBox.style.whiteSpace = "pre-line";
    }
  } else {
    ElNotification({
      title: "温馨提示",
      message: `批量添加${parameter.value.title}失败，请您重新上传！`,
      type: "error"
    });
  }
};

// 上传成功提示
const excelUploadSuccess = () => {
  ElNotification({
    title: "温馨提示",
    message: `批量添加${parameter.value.title}成功！`,
    type: "success"
  });
};

defineExpose({
  acceptParams
});
</script>
<style lang="scss" scoped>
@import "./index.scss";

:deep(.el-overlay .el-overlay-dialog .el-dialog .el-dialog__body) {
  max-height: calc(90vh - 111px) !important;
  height: auto !important;
}
</style>
