<template>
  <div class="table-box">
    <vz-table
      ref="vzTableRef"
      :columns="columns"
      @rowdouble-click="rowdoubleClick"
      @row-click="rowClick"
      :request-api="getTableList"
      highlight-current-row
      :init-param="initParam"
    >
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-if="isShowBtn" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button type="primary" v-auth="'edit'" link @click="openDrawer('编辑', scope.row)">编辑</el-button>
        <el-button type="primary" v-auth="'delete'" link @click="deleteAccount(scope.row)">删除</el-button>
      </template>
      <!-- <template #belongDistrict="scope">
        <dict-area :value="scope.row.belongDistrict"></dict-area>
      </template> -->
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="subjectInfo">
import { ref, reactive } from "vue";
import { subjectInfo } from "@/api/modules/source/subjectInfo/api"; // api
import { VzTableInstance, ColumnProps, TypeProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import {
  SubjectInfoPageRequest,
  SubjectInfoDTO,
  natureOptions,
  subjectTypeOptions,
  documentTypeOptions
} from "@/api/modules/source/subjectInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useDialogStore } from "@/stores/modules/dialogParams";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();

interface SubjectInfoPropsType {
  filterData?: { [key: string]: any };
  isShowBtn?: boolean;
  selectType?: TypeProps;
}

const props = withDefaults(defineProps<SubjectInfoPropsType>(), {
  filterData: () => ({}),
  isShowBtn: true,
  selectType: "radio"
});

// 表格配置项
const columns = reactive<ColumnProps<SubjectInfoDTO>[]>([
  { type: props.selectType, fixed: "left", width: 70, isShow: !props.isShowBtn },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "subjectName",
    label: "主体名称",
    minWidth: 250,
    align: "left",
    search: { el: "input" }
  },
  {
    prop: "subjectType",
    label: "主体类型",
    enum: subjectTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 100
  },
  {
    prop: "nature",
    label: "主体性质",
    enum: natureOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 160
  },
  {
    prop: "documentType",
    label: "证件类型",
    enum: documentTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 120
  },
  {
    prop: "documentNumber",
    label: "证件号码",
    width: 200
  },
  {
    prop: "legalRepresentative",
    label: "法定代表人",
    width: 120
  },
  {
    prop: "contacts",
    label: "联系人",
    width: 120
  },
  {
    prop: "phone",
    label: "联系人电话",
    width: 120
  },
  // {
  //   prop: "belongProvince",
  //   label: "区域_省",
  //   width: 120,
  //   search: { el: "input" }
  // },
  // {
  //   prop: "belongCity",
  //   label: "区域_市",
  //   width: 120,
  //   search: { el: "input" }
  // },
  // {
  //   prop: "belongDistrict",
  //   label: "区域",
  //   width: 120,
  //   search: { el: "input" }
  // },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    isShow: props.isShowBtn,
    width: 120
  },
  {
    type: "datetime",
    prop: "createStamp",
    label: "创建时间",
    isShow: props.isShowBtn,
    width: 180,
    search: {
      key: "createStampRange", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: {
        type: "datetimerange",
        format: "YYYY-MM-DD HH:mm:ss",
        valueFormat: "x",
        defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
      }
    }
    // 自定义 search
    // search: {
    //   render: ({ searchParam }) => {
    //     return (
    //       <el-date-picker
    //         vModel_trim={searchParam.createStampRange}
    //         type="datetimerange"
    //         start-placeholder="开始时间"
    //         end-placeholder="结束时间"
    //         format="YYYY-MM-DD HH:mm:ss"
    //         valueFormat="x"
    //         date-format="YYYY/MM/DD ddd"
    //         time-format="A hh:mm:ss"
    //       />
    //     );
    //   }
    // }
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 160,
    isShow:
      ((BUTTONS.value.edit !== undefined && BUTTONS.value.edit) ||
        (BUTTONS.value.detail !== undefined && BUTTONS.value.detail) ||
        (BUTTONS.value.delete !== undefined && BUTTONS.value.delete)) &&
      props.isShowBtn
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: SubjectInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return subjectInfo().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: SubjectInfoDTO) => {
  await useHandleData(subjectInfo().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<SubjectInfoDTO> = {}) => {
  const params = {
    dialogName: "subjectInfo_createUpdate",
    title,
    showBtn: false,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? subjectInfo().add : title === "编辑" ? subjectInfo().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};

const emit = defineEmits<{
  rowdoubleClick: [{ row?: any; column?: any; event?: Event }];
  rowClick: [{ row?: any; column?: any; event?: Event }];
}>();

const rowdoubleClick = (row: any) => {
  emit("rowdoubleClick", row);
};
const rowClick = (row: any) => {
  vzTableRef.value!.radio = row.id;
  emit("rowClick", row);
};
//	组件默认参数变动
// const changeDefaultParam = (obj: EmptyObjectType) => {
//   vzTableRef.value.param = obj;
// };
</script>

<style scoped lang="scss"></style>
