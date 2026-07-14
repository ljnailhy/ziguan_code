<template>
  <div class="layout-pd">
    <div v-if="!props!.readonly">
      <div>
        <div class="button-group">
          <el-button type="success" @click="onAddRow" size="small">新增</el-button>
          <el-button type="danger" @click="batchDelete" size="small">批量删除</el-button>
        </div>
      </div>
      <el-form ref="tableRulesRef" :model="uploadForm" size="default" scroll-to-error>
        <el-table
          ref="tableRef"
          @selection-change="handleSelectionChange"
          :data="uploadForm.fileForm"
          border
          v-loading="loading"
          element-loading-text="附件上传中,请稍后···"
          class="module-table-uncollected"
        >
          <el-table-column type="selection" width="50" />
          <el-table-column type="index" width="60" label="序号" align="center"></el-table-column>
          <el-table-column width="250" align="center">
            <template #header>
              <span class="color-danger">*</span>
              <span>附件名称</span>
            </template>
            <template #default="scope">
              <el-form-item
                class="mb0"
                :prop="`fileForm[${scope.$index}].annexName`"
                :rules="[{ required: true, trigger: 'change' }]"
              >
                <el-upload
                  v-model:file-list="fileList[scope.$index]"
                  ref="upload"
                  :show-file-list="false"
                  class="upload-demo"
                  :headers="{ Authorization: uploadToken }"
                  action="/api/infrastructure/sys/file/upload/minio/asset-service/FILE"
                  :limit="1"
                  :on-exceed="handleExceed"
                  :on-success="
                    (response: any) => {
                      successUpload(response, scope.row);
                    }
                  "
                  :on-change="
                    (file: any, fileList: any) => {
                      changeUpload(file, fileList, scope.row);
                    }
                  "
                  :data="fileData"
                  name="files"
                >
                  <el-input v-model="scope.row.annexName" readonly placeholder="请选择附件">
                    <template #append> 上传 </template>
                  </el-input>
                </el-upload>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="文件名称" min-width="250" align="center" prop="fileName">
            <template #default="scope">
              <el-form-item
                class="mb0"
                :prop="`fileForm[${scope.$index}].fileName`"
                :rules="[{ required: true, trigger: 'change', message: '请上传附件' }]"
              >
                <el-input placeholder="请选择附件" v-model="scope.row.fileName" maxlength="50"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="附件大小" show-overflow-tooltip align="center">
            <template #default="scope">
              <div v-if="scope.row.fileSize > 0">{{ (scope.row.fileSize / 1024 / 1024).toFixed(4) }}M</div>
            </template>
          </el-table-column>
          <el-table-column label="上传人" show-overflow-tooltip align="center" width="120">
            <template #default="scope">
              <dict-user-name :user-code="scope.row.creator"></dict-user-name>
            </template>
          </el-table-column>
          <el-table-column label="上传时间" show-overflow-tooltip align="center" width="180">
            <template #default="scope">
              <!-- {{ scope.row.createStamp }} -->
              <dict-date :date="scope.row.createStamp" format="YYYY-MM-DD HH:mm:ss"></dict-date>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="60" align="center" fixed="right">
            <template #default="scope">
              <el-icon size="16" color="#999999" @click="onDelRow(scope.$index, scope.row)">
                <Delete />
              </el-icon>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </div>
    <div v-else>
      <el-table ref="tableRef" :data="uploadForm.fileForm" border class="module-table-uncollected">
        <el-table-column type="index" width="60" label="序号" align="center"></el-table-column>
        <el-table-column label="附件" min-width="250" show-overflow-tooltip align="center" prop="annexName">
          <template #header>
            <span class="color-danger">*</span>
            <span>附件名称</span>
          </template>
          <template #default="scope">
            <el-link @click="downFiles(scope.row)" type="primary">{{ scope.row.annexName }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="文件名称" min-width="300" show-overflow-tooltip align="center" prop="fileName">
          <template #default="scope">
            {{ scope.row.fileName }}
          </template>
        </el-table-column>
        <el-table-column label="附件大小" show-overflow-tooltip align="center" width="100">
          <template #default="scope">
            <div v-if="scope.row.fileSize > 0">{{ (scope.row.fileSize / 1024 / 1024).toFixed(4) }}M</div>
          </template>
        </el-table-column>
        <el-table-column label="上传人" show-overflow-tooltip align="center" width="120">
          <template #default="scope">
            <dict-user-name :user-code="scope.row.creator"></dict-user-name>
          </template>
        </el-table-column>
        <el-table-column label="上传时间" show-overflow-tooltip align="center" width="180">
          <template #default="scope">
            <dict-date :date="scope.row.createStamp" format="YYYY-MM-DD HH:mm:ss"></dict-date>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup lang="ts" name="fileTable">
import { reactive, ref, nextTick, onMounted, watch } from "vue";
import { ElMessage, genFileId } from "element-plus";
import { Delete } from "@element-plus/icons-vue";
import type { UploadInstance, UploadProps, UploadRawFile, FormInstance } from "element-plus";
import { useFileApi } from "@/api/modules/files/file";
import { deduplicateArraysById } from "@/utils";

// 定义变量内容
const tableRulesRef = ref<FormInstance>();
const tableRef = ref();
const uploadToken = ref();

interface FileTableProps {
  queryData?: { [key: string]: any };
  readonly?: boolean;
}

const props = withDefaults(defineProps<FileTableProps>(), {
  readonly: false,
  queryData: () => ({})
});

//定义变量
const uploadForm = reactive<any>({
  fileForm: []
});
const bakForm = ref([]);
const submitForm = ref([]);
const fileData = ref();
const selectArr = ref([]);
const loading = ref(false);
const fileList = ref<any>([]);
const upload = ref<UploadInstance>();
const state = reactive<any>({
  form: {
    fileName: "",
    annexName: "",
    fileUrl: "",
    isFiled: false,
    fileSize: 0,
    creator: localStorage.getItem("userInfo") && JSON.parse(JSON.stringify(localStorage.getItem("userInfo")))?.userInfo?.realName,
    createStamp: "",
    remark: ""
  }
});

//批量删除
const batchDelete = () => {
  if (selectArr.value.length <= 0) {
    return ElMessage.warning("请选择要删除的数据！");
  }
  selectArr.value.forEach(row => {
    const index = uploadForm.fileForm.findIndex(item => item === row);
    uploadForm.fileForm.splice(index, 1);
    fileList.value.splice(index, 1);
    emit("delete", row, index);
  });

  // 清空选中状态
  selectArr.value = [];
  tableRef.value.doLayout();
};

//删除一行
const onDelRow = (index: number, row: any) => {
  uploadForm.fileForm.splice(index, 1);
  console.log(fileList.value[index]);
  fileList.value.splice(index, 1);
  emit("delete", index, row);
};

// 新增一行
const onAddRow = () => {
  uploadForm.fileForm.push({
    ...state.form,
    operateType: "ADD"
  });
  tableRef.value.doLayout();
  nextTick(() => {
    tableRef.value.setScrollTop(1000000);
  });
};

//选择
const handleSelectionChange = (val: any) => {
  selectArr.value = val;
};

//选择附件
const changeUpload = (file: any, uploadFiles: any, item: any) => {
  const newFileList = uploadFiles.slice(-1);
  const index = uploadForm.fileForm.findIndex((v: any) => v === item);
  fileList.value[index] = newFileList;
  if (uploadForm.fileForm && uploadForm.fileForm.length > 0) {
    uploadForm.fileForm[index].fileName = file.name && file.name.split(".")[0];
    uploadForm.fileForm[index].annexName = file.name;
    uploadForm.fileForm[index].fileSize = file.size > 0 ? file.size : 0;
  }
};

const handleExceed: UploadProps["onExceed"] = files => {
  upload.value!.clearFiles();
  const file = files[0] as UploadRawFile;
  file.uid = genFileId();
  upload.value!.handleStart(file);
  upload.value!.submit();
};

//成功上传的回调
const successUpload = (data: any, item: number) => {
  const index = uploadForm.fileForm.findIndex((v: any) => v === item);
  if (data?.code === 0 && data?.data.length > 0) {
    uploadForm.fileForm[index].fileUrl = data.data[0].fileUrl;
    uploadForm.fileForm[index].createStamp = new Date().getTime();
  } else {
    ElMessage.warning(data?.errorMessage || "文件上传失败");
    uploadForm.fileForm[index] = state.form;
  }
};

//下载
const downFiles = (row: any) => {
  if (!row.fileUrl) return;
  const url = window.location.origin;
  const downloadUrl = url + "/minio/" + row.fileUrl; // 文件的 URL
  const downloadLink = document.createElement("a");
  downloadLink.href = downloadUrl;
  downloadLink.download = row.fileName; // 文件名
  document.body.appendChild(downloadLink);
  downloadLink.click();
  document.body.removeChild(downloadLink);
};

// 定义 emit 事件
const emit = defineEmits<{
  updateFile: [form: any];
  delete: [index: number, row: any];
}>();

watch(
  () => props.queryData,
  val => {
    if (!val.doType && !val.doId) {
      uploadForm.fileForm = [];
      emit("updateFile", uploadForm.fileForm);
    }
    if (val.doId || (val.doIds && val.doIds.length > 0)) {
      useFileApi()
        .findAll({ ...props.queryData, size: 1000, current: 1 })
        .then(res => {
          uploadForm.fileForm.splice(0, uploadForm.fileForm.length, ...res.data);
          bakForm.value = JSON.parse(JSON.stringify(res.data));
          emit("updateFile", uploadForm.fileForm);
        });
    }
  },
  {
    deep: true,
    immediate: true
  }
);
watch(
  () => uploadForm.fileForm,
  val => {
    submitForm.value = deduplicateArraysById(bakForm.value, val);
  },
  {
    deep: true,
    immediate: true
  }
);
onMounted(() => {
  uploadToken.value = localStorage.getItem("Authorization");
  fileData.value = {
    data: JSON.stringify({ moreDir: "" })
  };
});

//报漏变量给父级
defineExpose({
  submitForm,
  tableRulesRef
});
</script>
<style scoped lang="scss">
:deep(.el-table__cell) {
  border-right: 0 !important;
}
.layout-pd {
  .flex-margin {
    display: flex;
    width: 100%;
    cursor: pointer;
  }
}
.el-icon {
  margin-right: 10px;
}
.button-group {
  margin-bottom: 10px;
  text-align: right;
}
</style>
