<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
      </template>
      <!-- 合并列表数据 s -->
      <template #creatorInfo="scope">
        <div class="flx">
          创建人：
          <dict-user-name :user-code="scope.row.creator"></dict-user-name>
        </div>
        <div class="flx">
          创建时间：
          <dict-date :date="scope.row.createStamp" format="YYYY/MM/DD HH:mm:ss"></dict-date>
        </div>
      </template>
      <!-- 合并列表数据 e -->
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button
          type="primary"
          v-auth="'edit'"
          link
          @click="openDrawer('编辑', scope.row)"
          v-if="scope.row.writeOffStatus !== true"
        >
          编辑
        </el-button>
        <el-button
          type="primary"
          v-auth="'delete'"
          link
          @click="deleteAccount(scope.row)"
          v-if="scope.row.writeOffStatus !== true"
        >
          删除
        </el-button>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="writeOff">
import { ref, reactive } from "vue";
import { writeOff } from "@/api/modules/recovery/writeOff/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { WriteOffPageRequest, WriteOffDTO, writeOffClassificationOptions } from "@/api/modules/recovery/writeOff/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
// import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { useHandleData } from "@/hooks/useHandleData";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();

// 表格配置项
const columns = reactive<ColumnProps<WriteOffDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 230,
    align: "left",
    headerAlign: "center",
    search: { el: "input" }
  },
  {
    prop: "compensationAmount",
    label: "代偿金额(元)",
    minWidth: 120,
    align: "right",
    headerAlign: "center",
    type: "money"
  },
  {
    type: "date",
    prop: "compensationDate",
    label: "代偿时间",
    minWidth: 110
  },
  {
    prop: "totalCollectionAmount",
    label: "累计回款金额(元)",
    align: "right",
    headerAlign: "center",
    minWidth: 160,
    type: "money"
  },
  {
    prop: "writeDffAmount",
    label: "核销金额(元)",
    align: "right",
    headerAlign: "center",
    minWidth: 120,
    type: "money"
  },
  {
    type: "date",
    prop: "writeOffDate",
    label: "核销时间",
    width: 110,
    search: {
      key: "writeOffDateRange", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: {
        type: "datetimerange",
        format: "YYYY-MM-DD HH:mm:ss",
        valueFormat: "x",
        defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
      }
    }
  },

  {
    prop: "writeOffClassification",
    label: "核销项目分类",
    enum: writeOffClassificationOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    minWidth: 120
  },
  {
    prop: "remarks",
    label: "回款情况说明",
    minWidth: 200,
    isShow: false
  },
  // {
  //   prop: "creator",
  //   type: "user",
  //   label: "创建人",
  //   minWidth: 120
  // },
  // {
  //   type: "datetime",
  //   prop: "createStamp",
  //   label: "创建时间",
  //   minWidth: 200
  // },
  {
    prop: "creatorInfo",
    label: "创建信息",
    minWidth: 230
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    minWidth: 160,
    isShow:
      (BUTTONS.value.edit !== undefined && BUTTONS.value.edit) ||
      (BUTTONS.value.detail !== undefined && BUTTONS.value.detail) ||
      (BUTTONS.value.delete !== undefined && BUTTONS.value.delete)
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: WriteOffPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return writeOff().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: WriteOffDTO) => {
  await useHandleData(writeOff().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<WriteOffDTO> = {}) => {
  const params = {
    dialogName: "writeOff_createUpdate",
    title,
    showBtn: true,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? writeOff().add : title === "编辑" ? writeOff().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  useDialogStore().setDrawerProps(params); //存缓存
  dialogRef.value?.acceptParams(params);
};
</script>

<style scoped lang="scss"></style>
