<template>
  <div class="table-container">
    <el-table
      :data="state.tableData"
      :border="setBorder"
      v-bind="$attrs"
      row-key="id"
      stripe
      style="width: 100%"
      v-loading="state.loading"
      highlight-current-row
      element-loading-text="拼命加载中,请稍后···"
      @row-click="rowClick"
      @row-dblclick="dblclick"
      @selection-change="onSelectionChange"
    >
      <el-table-column
        type="selection"
        align="center"
        :reserve-selection="true"
        width="45"
        v-if="config.isSelection"
      />
      <el-table-column
        type="index"
        :index="indexMethod"
        align="center"
        label="序号"
        width="70"
        v-if="config.isSerialNo"
      />
      <el-table-column
        :align="item.align || 'center'"
        v-for="(item, index) in setHeader"
        :key="index"
        show-overflow-tooltip
        :prop="item.key"
        :width="item.colWidth"
        :label="item.title"
        :sortable="item.sortable"
      >
        <template v-slot="scope">
          <template v-if="item.type === 'image'">
            <el-image
              :style="{ width: `${item.width}px`, height: `${item.height}px` }"
              :src="scope.row[item.key]"
              :zoom-rate="1.2"
              :preview-src-list="[scope.row[item.key]]"
              preview-teleported
              fit="cover"
            />
          </template>
          <template v-else-if="item.type === 'boolean'">
            <el-tag
              class="ml-2"
              :type="scope.row[item.key] ? 'success' : 'danger'"
              :size="themeConfig.globalComponentSize"
            >
              {{ scope.row[item.key] ? "是" : "否" }}
            </el-tag>
          </template>
          <template v-else-if="item.type === 'year'">
            <dict-date :date="scope.row[item.key]" format="YYYY"></dict-date>
          </template>
          <template v-else-if="item.type === 'month'">
            <dict-date :date="scope.row[item.key]" format="YYYY-MM"></dict-date>
          </template>
          <template v-else-if="item.type === 'date'">
            <dict-date
              :date="scope.row[item.key]"
              format="YYYY-MM-DD"
            ></dict-date>
          </template>
          <template v-else-if="item.type === 'datetime'">
            <dict-date
              :date="scope.row[item.key]"
              format="YYYY-MM-DD HH:mm:ss"
            ></dict-date>
          </template>
          <template v-else-if="item.type === 'dict'">
            <dict-name
              :dictType="scope.row[item.dictType]"
              :dictValue="scope.row[item.key]"
            ></dict-name>
          </template>
          <template v-else-if="item.type === 'enum' && item.options">
            <dict-enum
              :options="item.options"
              :value="scope.row[item.key]"
            ></dict-enum>
          </template>
          <template v-else-if="item.type === 'user'">
            <dict-user-name :userCode="scope.row[item.key]"></dict-user-name>
          </template>
          <template v-else-if="item.type === 'version'">
            <dict-version :versionId="scope.row[item.key]"></dict-version>
          </template>
          <template v-else-if="item.type === 'area'">
            <dict-area :value="scope.row[item.key]"></dict-area>
          </template>
          <template v-else-if="item.type === 'fileSize'">
            {{
              scope.row[item.key] && scope.row[item.key] <= 99999
                ? (scope.row[item.key] / 1024).toFixed(2) + "KB"
                : scope.row[item.key] && scope.row[item.key] > 99999
                ? (scope.row[item.key] / 1024 / 1024).toFixed(2) + "MB"
                : 0
            }}
          </template>
          <template v-else-if="item.type === 'file'">
            <el-image
              v-if="isImage(scope.row[item.key])"
              :src="setImgSrc(scope.row[item.key])"
              :zoom-rate="1.2"
              :max-scale="7"
              :min-scale="0.2"
              :preview-src-list="[setImgSrc(scope.row[item.key])]"
              :initial-index="4"
              fit="cover"
            ></el-image>
            <span v-else>{{ scope.row[item.key] }}</span>
          </template>
          <template v-else-if="item.type === 'tenant'">
            <dict-tenant :tenantId="scope.row[item.key]"></dict-tenant>
          </template>
          <template v-else> {{ scope.row[item.key] }} </template>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        min-width="150"
        v-if="config.isOperate || tableButtons.length > 0"
        fixed="right"
      >
        <template v-slot="scope">
          <el-button
            text
            v-for="item in tableButtons"
            :type="item.type"
            :key="item.value"
            @click="rowClickFun(scope.row, item)"
            :size="themeConfig.globalComponentSize"
          >
            {{ item.label }}
          </el-button>
          <!-- <el-button text type="primary" @click="onDetailRow(scope.row)" :size="themeConfig.globalComponentSize">详情</el-button>
					<el-button text type="primary" @click="onEditRow(scope.row)" :size="themeConfig.globalComponentSize">修改</el-button>
					<el-button text type="primary" @click="onDelRow(scope.row)" :size="themeConfig.globalComponentSize">删除</el-button> -->
        </template>
      </el-table-column>
      <slot name="operate"></slot>
      <template #empty>
        <el-empty description="暂无数据" />
      </template>
    </el-table>
    <div class="table-footer mt15">
      <div class="table-footer-tool">
        <!-- <el-button :icon="Printer" :size="themeConfig.globalComponentSize" circle @click="onPrintTable" /> -->
        <el-button
          :icon="Download"
          :size="themeConfig.globalComponentSize"
          circle
          @click="onImportTable"
        />
        <el-button
          :icon="Refresh"
          :size="themeConfig.globalComponentSize"
          circle
          @click="onRefreshTable"
        />
        <el-popover
          placement="top-end"
          trigger="click"
          transition="el-zoom-in-top"
          popper-class="table-tool-popper"
          :width="300"
          :persistent="false"
          @show="onSetTable"
        >
          <template #reference>
            <el-button
              :icon="Setting"
              :size="themeConfig.globalComponentSize"
              circle
            />
            <!-- <SvgIcon name="iconfont icon-quanjushezhi_o" :size="22" title="设置" /> -->
          </template>
          <template #default>
            <div class="tool-box">
              <el-tooltip content="拖动进行排序" placement="top-start">
                <!-- <SvgIcon name="fa fa-question-circle-o" :size="17" class="ml11" color="#909399" /> -->
              </el-tooltip>
              <el-checkbox
                v-model="state.checkListAll"
                :indeterminate="state.checkListIndeterminate"
                class="ml10 mr1"
                label="列显示"
                @change="onCheckAllChange"
              />
              <el-checkbox
                v-model="getConfig.isSerialNo"
                class="ml12 mr1"
                label="序号"
              />
              <el-checkbox
                v-model="getConfig.isSelection"
                class="ml12 mr1"
                label="多选"
              />
            </div>
            <el-scrollbar>
              <div ref="toolSetRef" class="tool-sortable">
                <div
                  class="tool-sortable-item"
                  v-for="v in header"
                  :key="v.key"
                  :data-key="v.key"
                >
                  <!-- <i class="fa fa-arrows-alt handle cursor-pointer"></i> -->
                  <!-- <Rank style="width: 1em; height: 1em; margin-left: 4px" /> -->
                  <el-checkbox
                    v-model="v.isCheck"
                    class="ml12 mr8"
                    :label="v.title"
                    @change="onCheckChange"
                  />
                </div>
              </div>
            </el-scrollbar>
          </template>
        </el-popover>
      </div>
      <el-pagination
        v-model:current-page="state.page.current"
        v-model:page-size="state.page.size"
        :pager-count="5"
        :page-sizes="[5, 10, 20, 50, 100, 500]"
        :total="state.total"
        layout="total, sizes, prev, pager, next, jumper"
        background
        :small="themeConfig.globalComponentSize == 'small'"
        @size-change="onHandleSizeChange"
        @current-change="onHandleCurrentChange"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script setup lang="ts" name="netxTable">
import { reactive, computed, nextTick, ref, onMounted } from "vue";
import { Download, Refresh, Setting } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import printJs from "print-js";
import table2excel from "js-table2excel";
import Sortable from "sortablejs";
import { storeToRefs } from "pinia";
import { useThemeConfig } from "../../stores/themeConfig";
import "../../theme/tableTool.scss";

// 定义父组件传过来的值
const props = defineProps({
  // 列表内容
  data: {
    type: Array,
    default: () => [],
  },
  // 表头内容
  header: {
    type: Array,
    default: () => [],
  },
  search: {
    type: Array,
    default: () => [],
  },
  // 配置项
  config: {
    type: Object,
    default: () => {},
  },
  // 打印标题
  printName: {
    type: String,
    default: () => "",
  },
  tableButtons: {
    type: Array,
    default: () => [],
  },
  param: {
    type: Object,
    default: () => {},
  },
});

// 定义子组件向父组件传值/事件
const emit = defineEmits([
  "delRow",
  "rowDblclick",
  "rowClick",
  "pageChange",
  "sortHeader",
  "editRow",
  "openDetail",
]);

// 定义变量内容
const toolSetRef = ref();
const storesThemeConfig = useThemeConfig();
const { themeConfig } = storeToRefs(storesThemeConfig);
const state = reactive({
  loading: false,
  page: {
    current: 1,
    size: 50,
  },
  total: 0,
  tableData: [] as EmptyObjectType[],
  selectlist: [] as EmptyObjectType[],
  checkListAll: true,
  checkListIndeterminate: false,
});

//测试数据
const setData = () => {
  state.tableData = [];
  if (props.data && props.data.length > 0) {
    state.tableData = props.data;
    return;
  }
};
// 初始化数据
const getTableData = async (queryForm?: EmptyObjectType) => {
  if (typeof props.config.listApi !== "function") {
    return;
  }
  state.loading = true;
  const form = {
    ...props.param, //默认参数
    ...queryForm, //搜索参数
    ...state.page, //页码
  };
  try {
    const { data, page } = await props.config.listApi(form, state.page);
    state.tableData = data;
    state.total = page.total;
  } finally {
    state.loading = false;
  }
};
// 设置边框显示/隐藏
const setBorder = computed(() => {
  return props.config.isBorder ? true : false;
});
// 获取父组件 配置项（必传）
const getConfig = computed(() => {
  return props.config;
});
// 设置 tool header 数据
const setHeader = computed(() => {
  return props.header.filter((v) => v.isCheck);
});
// tool 列显示全选改变时
const onCheckAllChange = <T>(val: T) => {
  if (val) props.header.forEach((v) => (v.isCheck = true));
  else props.header.forEach((v) => (v.isCheck = false));
  state.checkListIndeterminate = false;
};
// tool 列显示当前项改变时
const onCheckChange = () => {
  const headers = props.header.filter((v) => v.isCheck).length;
  state.checkListAll = headers === props.header.length;
  state.checkListIndeterminate = headers > 0 && headers < props.header.length;
};
// 表格多选改变时，用于导出
const onSelectionChange = (val: EmptyObjectType[]) => {
  state.selectlist = val;
};
const indexMethod = (index: number) => {
  return (state.page.current - 1) * state.page.size + index + 1;
};
// 删除当前项
const onDelRow = (row: EmptyObjectType) => {
  ElMessageBox.confirm(`此操作将永久删除该条数据，是否继续?`, "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      if (props.config.delApi) {
        props.config.delApi(row.id).then((res: EmptyObjectType) => {
          if (res.code == 0) {
            ElMessage.success("删除成功");
            getTableData();
          } else {
            ElMessage.warning(res.message);
          }
        });
      } else {
        emit("delRow", row);
      }
    })
    .catch(() => {});
};

const rowClickFun = (row: EmptyObjectType, currentRow: EmptyObjectType) => {
  if (currentRow.value === "edit") {
    emit("editRow", row);
  } else if (currentRow.value === "detail") {
    emit("openDetail", row);
  } else if (currentRow.value === "delete") {
    onDelRow(row);
  }
};
const rowClick = (row: EmptyObjectType) => {
  emit("rowClick", row);
};
const dblclick = (row: EmptyObjectType) => {
  emit("rowDblclick", row);
};

// 页码改变
const onHandleSizeChange = (val: number) => {
  state.page.size = val;
  getTableData();
};
// 分页改变
const onHandleCurrentChange = (val: number) => {
  state.page.current = val;
  getTableData();
};
// 搜索时，分页还原成默认
const pageReset = () => {
  state.page.current = 1;
  state.page.size = 50;
  emit("pageChange", state.page);
};
// 打印
const onPrintTable = () => {
  // https://printjs.crabbly.com/#documentation
  // 自定义打印
  let tableTh = "";
  let tableTrTd = "";
  let tableTd: any = {};
  // 表头
  props.header.forEach((v) => {
    tableTh += `<th class="table-th">${v.title}</th>`;
  });
  // 表格内容
  state.tableData.forEach((val, key) => {
    if (!tableTd[key]) tableTd[key] = [];
    props.header.forEach((v) => {
      if (v.type === "text") {
        tableTd[key].push(
          `<td class="table-th table-center">${val[v.key]}</td>`
        );
      } else if (v.type === "boolean") {
        const text = val[v.key] ? "是" : "否";
        tableTd[key].push(`<td class="table-th table-center">${text}</td>`);
      } else if (v.type === "image") {
        tableTd[key].push(
          `<td class="table-th table-center"><img src="${
            val[v.key]
          }" style="width:${v.width}px;height:${v.height}px;"/></td>`
        );
      }
    });
    tableTrTd += `<tr>${tableTd[key].join("")}</tr>`;
  });
  // 打印
  printJs({
    printable: `<div style=display:flex;flex-direction:column;text-align:center><h3>${props.printName}</h3></div><table border=1 cellspacing=0><tr>${tableTh}${tableTrTd}</table>`,
    type: "raw-html",
    css: [
      "//at.alicdn.com/t/c/font_2298093_rnp72ifj3ba.css",
      "//unpkg.com/element-plus/dist/index.css",
    ],
    style: `@media print{.mb15{margin-bottom:15px;}.el-button--small i.iconfont{font-size: 12px !important;margin-right: 5px;}}; .table-th{word-break: break-all;white-space: pre-wrap;}.table-center{text-align: center;}`,
  });
};
// 导出
const onImportTable = () => {
  if (state.selectlist.length <= 0)
    return ElMessage.warning("请先选择要导出的数据");
  table2excel(
    props.header,
    state.selectlist,
    `${themeConfig.value.globalTitle} ${new Date().toLocaleString()}`
  );
};
const isImage = (filename: EmptyObjectType) => {
  if (!filename) return;
  var ext = filename.split(".").pop().toLowerCase();
  return (
    ext === "jpg" ||
    ext === "jpeg" ||
    ext === "png" ||
    ext === "gif" ||
    ext === "bmp"
  );
};
const setImgSrc = (key: string) => {
  return "http://" + window.location.host + "/files/" + key;
};
// const downfiles = (row: EmptyObjectType) => {
// 	// let url = window.location.host;
// 	let url = window.location.origin;
// 	// window.open('http://' + url + '/files/' + row.fileUrl);
// 	const downloadUrl = url + '/files/' + row.fileUrl; // 替换为实际文件的 URL
// 	const downloadLink = document.createElement('a');
// 	downloadLink.href = downloadUrl;
// 	downloadLink.download = row.fileName; // 替换为要保存的文件名
// 	document.body.appendChild(downloadLink);
// 	downloadLink.click();
// 	document.body.removeChild(downloadLink);
// };
// 刷新
const onRefreshTable = () => {
  getTableData();
};
// 设置
const onSetTable = () => {
  nextTick(() => {
    const sortable = Sortable.create(toolSetRef.value, {
      handle: ".handle",
      dataIdAttr: "data-key",
      animation: 150,
      onEnd: () => {
        const headerList: EmptyObjectType[] = [];
        sortable.toArray().forEach((val: string) => {
          props.header.forEach((v) => {
            if (v.key === val) headerList.push({ ...v });
          });
        });
        emit("sortHeader", headerList);
      },
    });
  });
};

// 页面加载时
onMounted(() => {
  getTableData();
});

// 暴露变量
defineExpose({
  pageReset,
  getTableData,
  setData,
});
</script>

<style scoped lang="scss">
.table-container {
  display: flex;
  flex-direction: column;
  .el-table {
    flex: 1;
  }
  .table-footer {
    display: flex;
    .table-footer-tool {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      i {
        margin-right: 10px;
        cursor: pointer;
        color: var(--el-text-color-regular);
        &:last-of-type {
          margin-right: 0;
        }
      }
    }
  }
}
</style>
