<template>
  <div class="table-box">
    <vz-table
      ref="vzTableRef"
      @rowdouble-click="rowdoubleClick"
      @row-click="rowClick"
      :columns="columns"
      :request-api="getTableList"
      :init-param="initParam"
    >
    </vz-table>
    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="propertyRightInfo">
import { ref, reactive } from "vue";
import { propertyRightInfo } from "@/api/modules/property/propertyRightInfo/api"; // api
import { VzTableInstance, ColumnProps, TypeProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { PropertyRightInfoPageRequest, PropertyRightInfoDTO } from "@/api/modules/property/propertyRightInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
// import { useHandleData } from "@/hooks/useHandleData";
// import { useAuthButtons } from "@/hooks/useAuthButtons";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
// const { BUTTONS } = useAuthButtons();
interface ProjectPropsType {
  filterData?: { [key: string]: any };
  isShowBtn?: boolean;
  selectType?: TypeProps;
}

const props = withDefaults(defineProps<ProjectPropsType>(), {
  filterData: () => ({}),
  isShowBtn: true,
  selectType: "selection"
});

const emit = defineEmits<{
  rowdoubleClick: [{ row?: any; column?: any; event?: Event }];
  rowClick: [{ row?: any; column?: any; event?: Event }];
}>();

const rowdoubleClick = (row: any) => {
  vzTableRef.value!.radio = row.id;
  emit("rowdoubleClick", row);
};

const rowClick = (row: any) => {
  vzTableRef.value!.radio = row.id;
  emit("rowClick", row);
};
// 表格配置项
const columns = reactive<ColumnProps<PropertyRightInfoDTO>[]>([
  { type: props.selectType, fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "propertyCode",
    label: "权证号/编号",
    width: 200,
    search: { el: "input" }
  },
  {
    prop: "assetUnitState",
    label: "资产单元状态",
    width: "120"
  },
  {
    prop: "originalValue",
    label: "资产原值",
    width: "150",
    type: "money"
  },
  {
    prop: "area",
    label: "面积(㎡)",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "propertyOwner",
    label: "产权人名称",
    width: 120,
    search: { el: "input" }
  },
  {
    type: "date",
    prop: "propertyEndDate",
    label: "权证到期日",
    width: 120
  },
  {
    type: "date",
    prop: "propertyTransferOwnership",
    label: "资产登记日期",
    width: 120
  },
  {
    prop: "assetUse",
    label: "资产用途",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "address",
    label: "坐落",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "remark",
    label: "备注",
    width: 120
    // search: { el: "input" }
  }
  // {
  //   prop: "creator",
  //   type: "user",
  //   label: "创建人",
  //   width: 120
  // },
  // {
  //   type: "date",
  //   prop: "createStamp",
  //   label: "创建时间",
  //   width: 120,
  //   search: {
  //     key: "createStampRange", //指定搜索的key
  //     el: "date-picker",
  //     span: 1,
  //     props: { type: "datetimerange", format: "YYYY-MM-DD HH:mm:ss", valueFormat: "x" }
  //   }
  // }
  // {
  //   prop: "operation",
  //   label: "操作",
  //   fixed: "right",
  //   width: 220,
  //   isShow: BUTTONS.value.edit || BUTTONS.value.detail || BUTTONS.value.delete
  // }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: PropertyRightInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return propertyRightInfo().findAll(newParams);
};

// 删除用户信息
// const deleteAccount = async (params: PropertyRightInfoDTO) => {
//   await useHandleData(propertyRightInfo().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
//   vzTableRef.value?.getTableList();
// };

// 打开 dialog(新增、查看、编辑)
// const dialogRef = ref<any>(null);
// const openDrawer = (title: string, row: Partial<PropertyRightInfoDTO> = {}) => {
//   const params = {
//     dialogName: "propertyRightInfo_createUpdate",
//     title,
//     showBtn: false,
//     id: row?.id,
//     isView: title === "查看",
//     api: title === "新增" ? propertyRightInfo().add : title === "编辑" ? propertyRightInfo().update : undefined,
//     getTableList: vzTableRef.value?.getTableList
//   };
//   dialogRef.value?.acceptParams(params);
// };
</script>

<style scoped lang="scss"></style>
