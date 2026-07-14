<template>
  <div class="module-table-uncollected">
    <div v-if="!props.readonly">
      <div class="button-group" v-if="showButton">
        <el-button type="success" size="small" @click="onAddRow">新增</el-button>
        <el-button type="danger" size="small" @click="batchDelete">批量删除</el-button>
      </div>
      <el-form :scroll-to-error="true" ref="tableRulesRef" :model="tableData" :disabled="props!.readonly">
        <el-table ref="tableRef" :data="tableData.data" border v-bind="$attrs" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" v-if="showButton" />
          <el-table-column width="60" label="序号" align="center" type="index"> </el-table-column>
          <el-table-column
            v-for="(item, index) in tableData.header"
            :key="index"
            :prop="item.prop"
            :min-width="item.width"
            :label="item.label"
            :align="item.align || 'center'"
          >
            <template #header>
              <span v-if="item.isRequired" class="color-danger">*</span>
              <span>{{ item.label }}</span>
              <el-tooltip v-if="item.isTooltip" effect="dark" content="这是tooltip" placement="top">
                <i class="iconfont icon-quanxian" />
              </el-tooltip>
            </template>
            <template #default="scope">
              <el-form-item
                v-if="!slots[item.prop]"
                :prop="`data.${scope.$index}.${item.prop}`"
                :rules="[
                  {
                    required: item.isRequired || false,
                    message: '不能为空',
                    trigger: `${item.type}` == 'input' ? 'blur' : 'change'
                  }
                ]"
              >
                <vz-select
                  v-if="item.dictType"
                  :multiple="item.multiple || false"
                  :dict-type="item.dictType"
                  :dict-value="scope.row[item.prop]"
                  v-model="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                ></vz-select>
                <el-select
                  :disabled="item.disabled || false"
                  v-else-if="item.type === 'select'"
                  :multiple="item.multiple || false"
                  v-model="scope.row[item.prop]"
                  :placeholder="item.placeholder || `请选择${item.label}`"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                >
                  <el-option
                    v-for="option in item.enum"
                    :disabled="option.value == item.selectDisabled || false"
                    :key="option.value"
                    :label="option.label"
                    :value="option.value"
                  />
                </el-select>
                <vz-org
                  :disabled="item.disabled || false"
                  v-else-if="item.type == 'org'"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                  v-model:orgValue="scope.row[item.prop]"
                  :org="scope.row[item.prop]"
                ></vz-org>
                <vz-user
                  :disabled="item.disabled || false"
                  style="width: 100%"
                  v-else-if="item.type == 'user'"
                  :multiple="item.multiple || false"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                  v-model:model="scope.row[item.prop]"
                  :dict-value="scope.row[item.prop]"
                ></vz-user>
                <vz-user-org
                  :disabled="item.disabled || false"
                  style="width: 100%"
                  v-else-if="item.type == 'orguser'"
                  :multiple="item.multiple || false"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                  v-model="scope.row[item.prop]"
                  :dict-value="scope.row[item.prop]"
                  :org-code="item.orgCode"
                >
                </vz-user-org>
                <el-date-picker
                  v-model="scope.row[item.prop]"
                  clearable
                  :placeholder="item.placeholder ? item.placeholder : `请选择${item.label}`"
                  v-else-if="item.type === 'year'"
                  :type="item.type"
                  value-format="x"
                  style="width: 100%"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                  :disabled="item.disabled || false"
                />
                <el-date-picker
                  v-model="scope.row[item.prop]"
                  clearable
                  :placeholder="item.placeholder ? item.placeholder : `请选择${item.label}`"
                  v-else-if="item.type === 'month'"
                  :type="item.type"
                  value-format="x"
                  style="width: 100%"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                  :disabled="item.disabled || false"
                />
                <el-date-picker
                  v-model="scope.row[item.prop]"
                  clearable
                  :placeholder="item.placeholder ? item.placeholder : `请选择${item.label}`"
                  v-else-if="item.type === 'date'"
                  :type="item.type"
                  value-format="x"
                  style="width: 100%"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                  :disabled="item.disabled || false"
                />
                <el-date-picker
                  v-model="scope.row[item.prop]"
                  clearable
                  :placeholder="item.placeholder ? item.placeholder : `请选择${item.label}`"
                  v-else-if="item.type === 'daterange'"
                  :type="item.type"
                  value-format="x"
                  style="width: 100%"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                  :disabled="item.disabled || false"
                />
                <el-date-picker
                  v-model="scope.row[item.prop]"
                  clearable
                  :placeholder="item.placeholder ? item.placeholder : `请选择${item.label}`"
                  v-else-if="item.type === 'datetime'"
                  :type="item.type"
                  value-format="x"
                  @change="onSelectChange($event, scope.$index, item, scope.row)"
                  style="width: 100%"
                  :disabled="item.disabled || false"
                />
                <vzLawFirmInfo
                  v-else-if="item.type === 'lawFirm'"
                  :filter-data="scope.row.lawFirmType"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vzLawFirmInfo>
                <vzLawyerInfo
                  v-else-if="item.type === 'lawyer'"
                  :filter-data="scope.row.lawyerType"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vzLawyerInfo>
                <vzContractInfo
                  v-else-if="item.type === 'contract'"
                  :filter-data="props.filterData || scope.row.contractType"
                  v-model:value="scope.row[item.prop]"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vzContractInfo>
                <vzProjectInfo
                  v-else-if="item.type === 'project'"
                  :filter-data="scope.row.projectType || item.projectType"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  :select-type="props.selectType"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vzProjectInfo>
                <VzSubject
                  v-else-if="item.type === 'subject'"
                  :filter-data="scope.row.subjectType"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></VzSubject>
                <vzPropertyInfo
                  v-else-if="item.type === 'property'"
                  :filter-data="scope.row.subjectType"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vzPropertyInfo>
                <vzCustomerInfo
                  v-else-if="item.type === 'customer'"
                  v-model:value="scope.row[item.prop]"
                  :filter-data="scope.row.subjectType"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vzCustomerInfo>
                <vzAgency
                  v-else-if="item.type === 'agency'"
                  :filter-data="props.filterData || scope.row.subjectType"
                  v-model:value="scope.row[item.prop]"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vzAgency>
                <vz-reve-info
                  v-else-if="item.type === 'reve'"
                  :filter-data="props.filterData"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vz-reve-info>
                <vz-property-right-info
                  v-else-if="item.type === 'propertyRightInfo'"
                  :filter-data="props.filterData"
                  v-model:value="scope.row[item.prop]"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  :select-type="props.selectType"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vz-property-right-info>
                <vz-asset
                  v-else-if="item.type === 'asset'"
                  v-model:value="scope.row[item.prop]"
                  :filter-data="props.filterData"
                  :default-value="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  @handle-ok="
                    (row: any) => {
                      onSelectChange(row, scope.$index, item, scope.row);
                    }
                  "
                ></vz-asset>
                <vz-input-unit
                  v-else-if="item.type === 'money'"
                  :value="scope.row[item.prop]"
                  v-model="scope.row[item.prop]"
                  :disabled="item.disabled || false"
                  :show-word="item.showWord || false"
                  :text="item.text"
                  :max="item.max"
                  @change="moneyinput($event, scope.$index, item, scope.row)"
                ></vz-input-unit>
                <el-switch
                  :disabled="item.disabled || false"
                  @change="switchChange(scope.row[item.prop], scope.$index)"
                  v-else-if="item.type === 'boolean'"
                  v-model="scope.row[item.prop]"
                  inline-prompt
                  :active-value="item.activeValue || true"
                  :inactive-value="item.inactiveValue || false"
                  active-text="是"
                  inactive-text="否"
                />
                <UploadFiles
                  class="upload-demo"
                  :limit="1"
                  v-else-if="item.type == 'file'"
                  :disabled="item.disabled || false"
                  :filter-data="{ doType: item.doType, doId: scope.row['id'] }"
                  v-model:file-list="scope.row[item.prop]"
                >
                </UploadFiles>

                <el-input
                  v-else
                  :type="item.type || 'text'"
                  :rows="item.rows || 1"
                  :autosize="item.autosize || false"
                  :disabled="item.disabled || false"
                  :maxlength="item.maxlength"
                  v-model="scope.row[item.prop]"
                  :placeholder="item.placeholder || `请输入${item.label}`"
                >
                  <template #append v-if="item.append">
                    {{ item.append }}
                  </template>
                </el-input>
              </el-form-item>
              <slot :name="item.prop" :index="scope.$index" :row="scope.row"></slot>
            </template>
          </el-table-column>
          <el-table-column label="操作" :width="props.operationWidth" align="center" fixed="right" v-if="props.showOperation">
            <template #default="scope">
              <el-link
                style="margin: 0 5px"
                type="primary"
                @click="onDelRow(scope.$index, scope.row)"
                v-if="!props.tableData.isHideDelete || scope.row.operateType == 'ADD'"
              >
                删除
              </el-link>

              <slot name="operation" :index="scope.$index" :row="scope.row"></slot>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
    </div>
    <viewTableForm
      :table-data="tableData"
      :product-list="props.productList"
      :view-operation="props.viewOperation"
      :operation-width="props.operationWidth"
      v-else
    >
      <template #operation="{ index, row }">
        <slot name="operation" :index="index" :row="row"></slot>
      </template>
    </viewTableForm>
  </div>
</template>

<script setup lang="ts" name="tableForm">
import { reactive, ref, nextTick, useSlots, watch } from "vue";
import type { FormInstance } from "element-plus";
import { ElMessage } from "element-plus";
import UploadFiles from "@/components/Upload/Files.vue";
import { TypeProps } from "@/components/VzTable/interface";
import { useFileApi } from "@/api/modules/files/file";

// 引入组件
import viewTableForm from "@/components/FormTable/view.vue";
import vzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";
import vzLawyerInfo from "@/components/source/vzLawyerInfo.vue";
import VzSubject from "@/components/source/vzSubject.vue";
import vzContractInfo from "@/components/source/vzContractInfo.vue";
import vzProjectInfo from "@/components/source/vzProjectInfo.vue";
import VzReveInfo from "@/components/source/vzReveInfo.vue";
import vzPropertyInfo from "@/components/source/vzPropertyInfo.vue";
import vzCustomerInfo from "@/components/source/vzCustomerInfo.vue";
import vzAgency from "@/components/source/vz-agency.vue";
import vzAsset from "@/components/source/vzAsset.vue";
import VzPropertyRightInfo from "@/components/source/vzPropertyRightInfo.vue";

// 定义父组件传过来的值
interface FileTableProps {
  tableData?: { [key: string]: any };
  filterData?: { [key: string]: any };
  readonly?: boolean;
  isAdd?: boolean;
  showButton?: boolean;
  showOperation?: boolean;
  operationWidth?: number;
  selectType?: TypeProps;
  doType?: string;
  field?: string;
  viewOperation?: boolean;
  productList?: any;
}

const props = withDefaults(defineProps<FileTableProps>(), {
  tableData: () => ({}),
  filterData: () => ({}),
  readonly: false,
  isAdd: true,
  showButton: true,
  showOperation: true,
  operationWidth: 80,
  selectType: "selection",
  viewOperation: false
});

// 定义子组件向父组件传值/事件
const emit = defineEmits([
  "confirmChoose",
  "enumChange",
  "openUser",
  "moneyBlur",
  "delRow",
  "addRow",
  "moneyInput",
  "switchChange",
  "onSelectChange",
  "payHandleOk",
  "accountHandleOk"
]);

//状态缓冲
const slots = useSlots();

// 定义变量内容
const tableRulesRef = ref<FormInstance>();
const tableRef = ref();
const tableData = reactive(props.tableData);
const selectedRows = ref<any>([]);
const form = ref({});

const moneyinput = (val: string, index: number, propName: any, row: EmptyObjectType) => {
  emit("moneyInput", val, index, propName, row);
};

const onSelectChange = (val: any, index: number, item: EmptyObjectType, row: EmptyObjectType) => {
  emit("onSelectChange", val, index, item, row);
};

const switchChange = (val: boolean, index: number) => {
  emit("switchChange", val, index);
};

//点击全选
const handleSelectionChange = (val: any) => {
  selectedRows.value = val;
};

//删除选中的行
const batchDelete = () => {
  if (selectedRows.value.length <= 0) {
    return ElMessage.warning("请先选择想要删除的数据！");
  }
  for (const row of selectedRows.value) {
    const index = tableData.data.findIndex(item => item === row);
    tableData.data.splice(index, 1);
    emit("delRow", row, index);
  }
  // 清空选中状态
  selectedRows.value = [];
  tableRef.value.doLayout();
};

//删除一行
const onDelRow = (index: number, row: EmptyObjectType) => {
  tableData.data.splice(index, 1);
  emit("delRow", row, index);
  tableRef.value.doLayout();
};

// console.log(businessRef.value.data);
// businessRef.value.data.push({
//   relatedBusNo: "A" + new Date().getTime()
// });
// const initFormField = () => {
//   props.tableData.header.forEach((v: any) => {
//     if (v.prop === "relatedBusNo" && v.disabled) {
//       form.value[v.prop] = "A" + new Date().getTime();
//     } else {
//       form.value[v.prop] = "";
//     }
//   });
// };

//新增一行
const onAddRow = () => {
  if (props.isAdd) {
    if (props.tableData.relatedBusNo) {
      tableData.data.push({
        ...form.value,
        relatedBusNo: "A" + new Date().getTime()
      });
    } else {
      tableData.data.push({
        ...form.value
      });
    }
  }
  emit("addRow");
  tableRef.value.doLayout();
  nextTick(() => {
    tableRef.value.setScrollTop(1000000);
  });
};

watch(
  () => props.tableData.data,
  value => {
    const type = props.tableData.header.filter(item => item.type === "file");
    if (value && value.length > 0 && type.length > 0) {
      type.forEach(types => {
        useFileApi()
          .findAll({ ...{ doIds: value.map(item => item.id), doType: types.doType }, size: 1000, current: 1 })
          .then(res => {
            value.forEach(opInfo => {
              opInfo[types.prop] = [];
              res.data.forEach(propBill => {
                if (opInfo.id === propBill.doId) {
                  opInfo[types.prop].push(propBill);
                  propBill.name = propBill.fileName;
                  propBill.url = "/minio" + propBill.fileUrl;
                }
              });
            });
          });
      });
    }
  },
  { immediate: true }
);
defineExpose({
  tableRulesRef,
  data: tableData.data
});
</script>
<style scoped lang="scss">
.button-group {
  margin-bottom: 10px;
  text-align: right;
}
:deep(.el-form-item__content) {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
