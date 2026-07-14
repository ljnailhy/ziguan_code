<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="props.filterData">
      <!-- 表格 header 按钮 -->
      <!-- <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增调解或审判信息 </el-button>
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
          v-if="'draft' != scope.row.flowState"
        >
          查看流程图
        </el-button>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
    <vz-flowchart ref="flowchartRef" />
  </div>
</template>

<script setup lang="tsx" name="recoveryAdjustTrial">
import { ref, reactive } from "vue";
import { recoveryAdjustTrial } from "@/api/modules/proceeding/recoveryAdjustTrial/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import {
  RecoveryAdjustTrialPageRequest,
  RecoveryAdjustTrialDTO,
  adjustTrialTypeOptions
} from "@/api/modules/proceeding/recoveryAdjustTrial/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { processStatus } from "@/enums/commonOptions";
import vzFlowchart from "@/components/source/vzFlowchart.vue";
interface recoveryAdjustTrialProps {
  filterData?: { [key: string]: any };
  selectedId?: number;
}
const props = withDefaults(defineProps<recoveryAdjustTrialProps>(), {
  filterData: () => ({}),
  selectedId: () => 0
});
// 定义子组件向父组件传值/事件
const emit = defineEmits(["openDrawer"]);
// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();

// 表格配置项
const columns = reactive<ColumnProps<RecoveryAdjustTrialDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },

  {
    prop: "adjustTrialType",
    label: "调解/判决类型",
    enum: adjustTrialTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 180
  },
  {
    type: "date",
    prop: "adjustTrialDate",
    label: "调解/判决日期",
    width: 160
  },
  {
    prop: "adjustCode",
    label: "判决案号",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "compensatoryAmount",
    label: "代偿金额(元)",
    width: 120
  },
  {
    prop: "interest",
    label: "利息(元)",
    width: 120
  },
  {
    prop: "backOutAmount",
    label: "违约金(元)",
    width: 120
  },
  {
    prop: "otherAmount",
    label: "其他费用(元)",
    width: 120
  },
  // {
  //   prop: "specialRemarks",
  //   label: "特殊情况说明",
  //   width: 120
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
    width: 200,
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
    width: 220,
    isShow: BUTTONS.value.edit || BUTTONS.value.detail || BUTTONS.value.delete
  }
]);
const flowchartRef = ref();
const openFlowchart = (row: EmptyObjectType) => {
  flowchartRef.value.openDialog(row.id);
};
// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
// const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: RecoveryAdjustTrialPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return recoveryAdjustTrial().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: RecoveryAdjustTrialDTO) => {
  openDrawer("取消", params);
  await useHandleData(recoveryAdjustTrial().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<RecoveryAdjustTrialDTO> = {}) => {
  emit("openDrawer", title, row);
  // const params = {
  //   dialogName: "recoveryAdjustTrial_createUpdate",
  //   title,
  //   showBtn: false,
  //   id: row?.id,
  //   isView: title === "查看",
  //   api: title === "新增" ? recoveryAdjustTrial().add : title === "编辑" ? recoveryAdjustTrial().update : undefined,
  //   getTableList: vzTableRef.value?.getTableList
  // };
  // dialogRef.value?.acceptParams(params);
};
</script>

<style scoped lang="scss"></style>
