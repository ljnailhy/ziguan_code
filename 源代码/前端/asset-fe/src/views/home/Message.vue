<template>
  <div class="table-box">
    <vz-card title="消息提醒" refresh @on-refresh="onRefresh()">
      <el-scrollbar>
        <div style="width: 98%; height: 500px">
          <div v-infinite-scroll="load" class="infinite-list" :infinite-scroll-disabled="disabled">
            <div
              v-for="(item, index) in messageList"
              :key="item.id"
              class="todo-item"
              :class="{
                'todo-item1': item.procDefName === '项目分配',
                'todo-item2': item.procDefName === '执行登记',
                'todo-item3': item.procDefName === '执行登记'
              }"
            >
              <div class="todo-item_index">{{ index + 1 }}</div>
              <div class="todo-item_content">
                <div class="todo-item_content-title">{{ item.title }}</div>
                <div class="todo-item_content-name pl_row_1">
                  <dict-date :date="item.createStamp" format="YYYY-MM-DD HH:mm:ss"></dict-date>
                </div>
                <el-link type="primary" @click="todoFunc(item)">立即查看</el-link>
              </div>
            </div>
          </div>
          <p v-if="loading" style="text-align: center">加载中...</p>
          <p v-if="messageList.length > 0 && noMore" style="font-size: 12px; color: #dddddd; text-align: center">没有更多了...</p>
          <el-empty v-if="messageList.length <= 0" description="暂无待办消息" />
        </div>
      </el-scrollbar>
    </vz-card>
    <el-dialog v-model="dialogVisible" title="查看消息" width="60%" :close-on-click-modal="false" :append-to-body="true">
      <el-form ref="ruleFormRef" label-width="100px" label-suffix=" :" :disabled="true">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form!.title" maxlength="50" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="内容" prop="content">
              <el-input v-model="form!.content" :rows="5" type="textarea"></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="创建人" prop="creator">
              <vz-user v-model:model="form!.creator" :dict-value="form!.creator"></vz-user>
            </el-form-item>
          </el-col> -->
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="创建时间" prop="createStamp">
              <el-date-picker v-model="form!.createStamp" type="datetime" class="w100" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup lang="tsx" name="subjectInfo">
import { ref, watch } from "vue";
import { useBaseInfoApi } from "@/api/modules/dictionary"; // api
import { useUserStore } from "@/stores/modules/user";
import { ElNotification } from "element-plus";

const queryData = ref({
  current: 0,
  size: 10,
  receiveUser: useUserStore().userInfo.id.toString(),
  messageState: "SEND",
  receiveType: "asset"
});
const loading = ref(false);
const noMore = ref(false);
const messageList = ref<any>([]);
const disabled = ref(false);
const dialogVisible = ref(false);
const form = ref<any>({});
const total = ref(0);
const todoFunc = row => {
  form.value = row;
  dialogVisible.value = true;
  // 设置消息为已读
  useBaseInfoApi().readMessage(row.id);
};
const onRefresh = () => {
  queryData.value.current = 1;
  messageList.value = [];
  getProcessFunc();
};
const load = () => {
  loading.value = true;
  queryData.value.current += 1;
  getProcessFunc();
};
const getProcessFunc = () => {
  useBaseInfoApi()
    .findMessages(queryData.value)
    .then((res: any) => {
      if (res.code == 0) {
        messageList.value = [...messageList.value, ...res.data];
      }
      loading.value = false;
      if (res.data.length < 10 || messageList.value >= res.page.total) {
        noMore.value = true;
        disabled.value = true;
      }
      total.value = res.page.total;
    });
};

watch(
  () => total.value,
  (newValue, oldValue) => {
    if (newValue && newValue !== oldValue) {
      ElNotification({
        title: "消息提醒",
        message: `您有 ${newValue} 条待处理消息，请移步到系统消息进行查阅处理。`,
        position: "top-right",
        duration: 0,
        type: "warning"
      });
    }
  },
  { immediate: true } // 确保在组件初始化时也触发一次监听
);
</script>

<style scoped lang="scss">
.todo-item1::after {
  background-color: rgb(120 215 255) !important;
}
.todo-item2::after {
  background-color: rgb(120 255 230) !important;
}
.todo-item3::after {
  background-color: var(--el-color-primary) !important;
}
.todo-item {
  position: relative;
  display: flex;
  align-items: center;
  padding: 10px;
  margin-bottom: 20px;
  overflow: hidden;
  border-radius: 5px;
  box-shadow: var(--el-box-shadow-light);
  &::after {
    position: absolute;
    left: 0;
    width: 3px;
    height: 100%;
    content: " ";
    background-color: rgb(203 120 255);
  }
  &_index {
    margin-right: 10px;
    font-size: 13px;
  }
  &_content {
    width: 100%;
    .el-link {
      position: absolute;
      right: 10px;
      bottom: 11px;
      font-size: 12px;
    }
    &-title {
      display: -webkit-box;
      margin-bottom: 5px;
      overflow: hidden;
      font-size: 13px;
      color: #333333;
      text-overflow: ellipsis;
      word-wrap: break-word;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
    }
    &-name {
      width: 70%;
      font-size: 12px;
      color: #adadad;
    }
  }
}
.pl_row_1 {
  display: inline-block;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  word-wrap: break-word;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
</style>
