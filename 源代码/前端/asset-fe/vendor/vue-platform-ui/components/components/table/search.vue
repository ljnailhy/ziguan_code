<template>
  <div class="table-search-container">
    <el-form
      ref="tableSearchRef"
      v-if="props.search.length > 0"
      :model="state.form"
      :size="themeConfig.globalComponentSize"
      label-width="100px"
      class="table-form"
    >
      <el-row>
        <el-col
          :xs="24"
          :sm="12"
          :md="8"
          :lg="8"
          :xl="4"
          class="mb20"
          v-for="(val, key) in search"
          :key="key"
          v-show="key === 0 || state.isToggle"
        >
          <template v-if="val.type !== ''">
            <el-form-item :label="val.label" :prop="val.prop">
              <el-date-picker
                v-model="state.form[val.prop]"
                type="date"
                :placeholder="val.placeholder"
                v-if="val.type === 'date'"
                style="width: 100%"
              />
              <el-date-picker
                v-else-if="val.type == 'datetimerange'"
                v-model="state.form[val.prop]"
                :default-time="defaultTime"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
              />
              <vz-user
                v-else-if="val.type == 'user'"
                style="width: 100%"
                v-model="state.form[val.prop]"
              ></vz-user>
              <el-select
                v-model="state.form[val.prop]"
                :placeholder="val.placeholder"
                v-else-if="val.options"
                style="width: 100%"
              >
                <el-option
                  v-for="item in val.options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
              <el-input
                v-model="state.form[val.prop]"
                :placeholder="val.placeholder"
                clearable
                v-else
                style="width: 100%"
              />
            </el-form-item>
          </template>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
          <el-form-item
            class="table-form-btn"
            :label-width="search.length <= 1 ? '10px' : '60px'"
          >
            <template #label v-if="search.length > 1">
              <div
                class="table-form-btn-toggle ml10"
                @click="state.isToggle = !state.isToggle"
              >
                <el-link type="primary" :underline="false"
                  >{{ state.isToggle ? "收起" : "展开"
                  }}<SvgIcon
                    :name="state.isToggle ? 'ele-ArrowUp' : 'ele-ArrowDown'"
                /></el-link>
              </div>
            </template>
            <div>
              <el-button
                type="primary"
                @click="onSearch(tableSearchRef)"
                :icon="Search"
                >查询
              </el-button>
              <el-button
                class="ml10"
                @click="onReset(tableSearchRef)"
                plain
                :icon="Refresh"
              >
                重置
              </el-button>
              <el-button
                v-for="item in searchButtons"
                :key="item.value"
                :type="item.type"
                class="ml10"
                @click="onButtonClick(item)"
              >
                <SvgIcon :name="item.icon" style="vertical-align: middle" />
                {{ item.label }}
              </el-button>
            </div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="makeTableDemoSearch">
import { reactive, ref, onMounted } from "vue";
import type { FormInstance } from "element-plus";
import {
  Delete,
  FolderAdd,
  Search,
  Refresh,
  Upload,
} from "@element-plus/icons-vue";
import { storeToRefs } from "pinia";
import { useThemeConfig } from "../../stores/themeConfig";

// 定义父组件传过来的值
const props = defineProps({
  // 搜索表单
  search: {
    type: Array,
    default: () => [],
  },
  searchButtons: {
    type: Array,
    default: () => [],
  },
});

// 定义子组件向父组件传值/事件
const emit = defineEmits(["search", "add"]);

// 定义变量内容
const defaultTime: [Date, Date] = [
  new Date(2000, 1, 1, 0, 0, 0),
  new Date(2000, 2, 1, 23, 59, 59),
];
const tableSearchRef = ref<FormInstance>();
const storesThemeConfig = useThemeConfig();
const { themeConfig } = storeToRefs(storesThemeConfig);
const state = reactive({
  form: <EmptyObjectType>{},
  isToggle: false,
});

const onButtonClick = (currentButton: EmptyObjectType) => {
  if (currentButton.value === "add") {
    emit("add");
  }
};
// 查询
const onSearch = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid: boolean) => {
    if (valid) {
      const requestData = <EmptyObjectType>{};
      // 遍历原始对象的字段
      for (const key in state.form) {
        if (state.form.hasOwnProperty(key)) {
          // 检查字段是否为空
          if (state.form[key] !== "") {
            // 如果字段不为空，将其添加到 requestData 对象中
            requestData[key] = state.form[key];
          }
        }
      }
      emit("search", requestData);
    } else {
      return false;
    }
  });
};
// 重置
const onReset = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
  emit("search", state.form);
};
// 初始化 form 字段，取自父组件 search.prop
const initFormField = () => {
  if (props.search.length <= 0) return false;
  props.search.forEach((v) => (state.form[v.prop] = ""));
};
// 页面加载时
onMounted(() => {
  initFormField();
});
</script>

<style scoped lang="scss">
.table-search-container {
  display: flex;
  .table-form {
    flex: 1;
    .table-form-btn-toggle {
      white-space: nowrap;
      user-select: none;
      display: flex;
      align-items: center;
      color: var(--el-color-primary);
    }
  }
}
</style>
