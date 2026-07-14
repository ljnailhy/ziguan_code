<template>
  <div class="table-box">
    <vz-table
      @rowdouble-click="rowdoubleClick"
      @row-click="rowClick"
      ref="vzTableRef"
      :columns="columns"
      :request-api="getTableList"
      :init-param="props.filterData"
    >
      <!-- 表格 header 按钮 -->
      <!-- <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
      </template> -->
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button
          type="primary"
          v-auth="'edit'"
          link
          @click="openDrawer('编辑', scope.row)"
          v-if="'draft' == scope.row.flowState && scope.row.id != selectedId"
        >
          编辑
        </el-button>
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('取消', scope.row)" v-if="scope.row.id == selectedId">
          取消
        </el-button>
        <el-button type="primary" v-auth="'delete'" link @click="deleteAccount(scope.row)" v-if="'draft' == scope.row.flowState">
          删除
        </el-button>
        <el-button
          type="primary"
          v-auth="'flowchart'"
          link
          @click="openFlowchart(scope.row)"
          v-if="scope.row.flowState && 'draft' != scope.row.flowState"
        >
          查看流程图
        </el-button>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
    <vz-flowchart ref="flowchartRef" />
  </div>
</template>

<script setup lang="tsx" name="recoveryJudgement">
import { ref, reactive } from "vue";
import { recoveryJudgement } from "@/api/modules/proceeding/recoveryJudgement/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import {
  RecoveryJudgementPageRequest,
  RecoveryJudgementDTO,
  LitigationTypeEnum,
  registerTypeOptions,
  litigationTypeOptions
} from "@/api/modules/proceeding/recoveryJudgement/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { processStatus } from "@/enums/commonOptions";
import vzFlowchart from "@/components/source/vzFlowchart.vue";

const rowdoubleClick = (row: any) => {
  vzTableRef.value!.radio = row.id;
  emit("rowdoubleClick", row);
};

const rowClick = (row: any) => {
  vzTableRef.value!.radio = row.id;
  emit("rowClick", row);
};
// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
// const initParam = reactive({});
interface recoveryJudgementProps {
  filterData?: { [key: string]: any };
  selectedId?: number;
}

const props = withDefaults(defineProps<recoveryJudgementProps>(), {
  filterData: () => ({}),
  selectedId: () => 0
});

// 表格配置项
const columns = reactive<ColumnProps<RecoveryJudgementDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "filingCourtName",
    label: "立案法院",
    minWidth: 200,
    search: { el: "input" }
  },
  {
    prop: "judgeName",
    label: "法官",
    width: 120
    // search: { el: "input" },
    // isShow: props.filterData.litigationTypeList && !props.filterData.litigationTypeList.includes(LitigationTypeEnum.REGISTER)
  },
  {
    prop: "fillingCode",
    label: "案号",
    width: 250,
    search: { el: "input" }
  },
  {
    type: "date",
    prop: "fillingDate",
    label: "立案时间",
    width: 120,
    search: {
      key: "fillingDateRange", //指定搜索的key
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
    prop: "litigationType",
    label: "类型",
    enum: litigationTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    // search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 120,
    isShow: props.filterData.litigationTypeList && !props.filterData.litigationTypeList.includes(LitigationTypeEnum.REGISTER)
  },
  {
    prop: "registerType",
    label: "立案类型",
    enum: registerTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    // search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 120,
    isShow: props.filterData.litigationTypeList && props.filterData.litigationTypeList.includes(LitigationTypeEnum.REGISTER)
  },
  {
    prop: "judgePhone",
    label: "法官联系方式",
    width: 250
  },
  // {
  //   type: "date",
  //   prop: "judgeDate",
  //   label: "判决日期",
  //   width: 120,
  //   isShow: props.filterData.litigationTypeList && !props.filterData.litigationTypeList.includes(LitigationTypeEnum.REGISTER)
  // },
  // {
  //   type: "date",
  //   prop: "courtSessionDate",
  //   label: "开庭时间",
  //   width: 120,
  //   isShow: props.filterData.litigationTypeList && !props.filterData.litigationTypeList.includes(LitigationTypeEnum.REGISTER)
  // },
  {
    prop: "flowState",
    label: "流程状态",
    enum: processStatus,
    tag: true,
    width: 120
  },
  // {
  //   prop: "remarks",
  //   label: "备注",
  //   width: 120
  // },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    width: 120
  },
  {
    type: "datetime",
    prop: "createStamp",
    label: "创建时间",
    width: 200
    // search: {
    //   key: "createStampRange", //指定搜索的key
    //   el: "date-picker",
    //   span: 1,
    //   props: {
    //     type: "datetimerange",
    //     format: "YYYY-MM-DD HH:mm:ss",
    //     valueFormat: "x",
    //     defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
    //   }
    // }
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
    width: 200,
    isShow: BUTTONS.value.edit || BUTTONS.value.detail || BUTTONS.value.delete
  }
]);

// 定义子组件向父组件传值/事件
const emit = defineEmits(["openDrawer", "rowdoubleClick", "rowClick"]);
// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: RecoveryJudgementPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return recoveryJudgement().findAll(newParams);
};
const flowchartRef = ref();
const openFlowchart = (row: EmptyObjectType) => {
  flowchartRef.value.openDialog(row.id);
};
// 删除用户信息
const deleteAccount = async (params: RecoveryJudgementDTO) => {
  openDrawer("取消", params);
  await useHandleData(recoveryJudgement().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<RecoveryJudgementDTO> = {}) => {
  emit("openDrawer", title, row);
  // const params = {
  //   dialogName: "recoveryJudgement_createUpdate",
  //   title,
  //   showBtn: false,
  //   id: row?.id,
  //   isView: title === "查看",
  //   api: title === "新增" ? recoveryJudgement().add : title === "编辑" ? recoveryJudgement().update : undefined,
  //   getTableList: vzTableRef.value?.getTableList
  // };
  // dialogRef.value?.acceptParams(params);
};
</script>

<style scoped lang="scss"></style>
