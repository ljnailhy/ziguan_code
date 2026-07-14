<!-- eslint-disable prettier/prettier -->
<template>
  <div class="table-box">

    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="props.filterData">
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
          v-if="!scope.row.isSubmit && scope.row.id != selectedId"
        >
          编辑
        </el-button>
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('取消', scope.row)" v-if="scope.row.id == selectedId">
          取消
        </el-button>
        <el-button type="primary" v-auth="'delete'" link @click="deleteAccount(scope.row)" v-if="!scope.row.isSubmit">
          删除
        </el-button>
        <!-- <el-button type="primary" v-auth="'judgement'" link @click="openJudgementDrawer('新增', scope.row)">审判</el-button>
        <el-button type="primary" v-auth="'preservation'" link @click="openPreservationDrawer('新增', 'PRESERVATION', scope.row)">
          保全
        </el-button>
        <el-button
          type="primary"
          v-auth="'withdrawLawsuit'"
          link
          @click="openPreservationDrawer('新增', 'DROP_LAWSUIT', scope.row)"
        >
          撤诉
        </el-button>
        <el-button type="primary" v-auth="'finalVersion'" link @click="openPreservationDrawer('新增', 'FINAL', scope.row)">
          终本
        </el-button>
        <el-button type="primary" v-auth="'closeCase'" link @click="openPreservationDrawer('新增', 'CLOSE_CASE', scope.row)">
          结案
        </el-button>
        <el-button type="primary" v-auth="'other'" link @click="openPreservationDrawer('新增', 'OTHER', scope.row)">
          其他
        </el-button>
        <el-button type="primary" v-auth="'adjustTrial'" link @click="openAdjustTrialDrawer('新增', scope.row)">
          调解审判
        </el-button> -->
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="recoveryLitigation">
import { ref, reactive } from "vue";
import { recoveryLitigation } from "@/api/modules/proceeding/recoveryLitigation/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { RecoveryLitigationPageRequest, RecoveryLitigationDTO } from "@/api/modules/proceeding/recoveryLitigation/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
// import { recoveryJudgement } from "@/api/modules/proceeding/recoveryJudgement/api";
// import { recoveryAdjustTrial } from "@/api/modules/proceeding/recoveryAdjustTrial/api";
// import { recoveryLitigationDetails } from "@/api/modules/proceeding/recoveryLitigationDetails/api";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();

// 表格配置项
const columns = reactive<ColumnProps<RecoveryLitigationDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    align: "left"
    // search: { el: "input" }
  },
  {
    prop: "compensationAmount",
    label: "代偿金额(元)",
    width: 160,
    type: "money",
    align: "right",
    headerAlign: "center"
  },
  // {
  //   prop: "projectStatus",
  //   label: "诉讼状态",
  //   minWidth: 120,
  //   search: { el: "select", props: { filterable: true } },
  //   enum: () => useBaseStore().findEnumByName("PROJECT_STATE")
  // },
  {
    prop: "interest",
    label: "利息（元）",
    minWidth: 120,
    type: "money",
    align: "right"
  },
  {
    prop: "liquidatedDamages",
    label: "违约金（元）",
    minWidth: 120,
    type: "money",
    align: "right"
  },
  {
    prop: "otherFees",
    label: "其他费用(元)",
    minWidth: 120,
    type: "money",
    align: "right"
  },
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
    width: 160,
    isShow: BUTTONS.value.edit || BUTTONS.value.detail || BUTTONS.value.delete
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
// const initParam = reactive({});
interface recoveryLitigationProps {
  filterData?: { [key: string]: any };
  selectedId?: number;
}
const props = withDefaults(defineProps<recoveryLitigationProps>(), {
  filterData: () => ({}),
  selectedId: () => 0
});

// 定义子组件向父组件传值/事件
const emit = defineEmits(["openDrawer"]);
// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: RecoveryLitigationPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return recoveryLitigation().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: RecoveryLitigationDTO) => {
  openDrawer("取消", params);
  await useHandleData(recoveryLitigation().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<RecoveryLitigationDTO> = {}) => {
  emit("openDrawer", title, row);
  // const params = {
  //   dialogName: "recoveryLitigation_createUpdate",
  //   title,
  //   showBtn: false,
  //   id: row?.id,
  //   isView: title === "查看",
  //   api: title === "新增" ? recoveryLitigation().add : title === "编辑" ? recoveryLitigation().update : undefined,
  //   getTableList: vzTableRef.value?.getTableList
  // };
  // dialogRef.value?.acceptParams(params);
};

// const openJudgementDrawer = (title: string, row: Partial<RecoveryLitigationDTO> = {}) => {
//   const params = {
//     dialogName: "recoveryJudgement_createUpdate",
//     title,
//     showBtn: true,
//     litigationId: row?.id,
//     isView: title === "查看",
//     api: title === "新增" ? recoveryJudgement().add : title === "编辑" ? recoveryJudgement().update : undefined,
//     getTableList: vzTableRef.value?.getTableList
//   };
//   dialogRef.value?.acceptParams(params);
// };
// //  保全 撤诉
// const openPreservationDrawer = (title: string, litigationType: string, row: Partial<RecoveryLitigationDTO> = {}) => {
//   const params = {
//     dialogName: "recoveryLitigationDetails_createUpdate",
//     title,
//     showBtn: true,
//     litigationId: row?.id,
//     litigationType: litigationType,
//     isView: title === "查看",
//     api: title === "新增" ? recoveryLitigationDetails().add : title === "编辑" ? recoveryLitigationDetails().update : undefined,
//     getTableList: vzTableRef.value?.getTableList
//   };
//   dialogRef.value?.acceptParams(params);
// };
// //  调解
// const openAdjustTrialDrawer = (title: string, row: Partial<RecoveryLitigationDTO> = {}) => {
//   const params = {
//     dialogName: "recoveryAdjustTrial_createUpdate",
//     title,
//     showBtn: true,
//     litigationId: row?.id,
//     isView: title === "查看",
//     api: title === "新增" ? recoveryAdjustTrial().add : title === "编辑" ? recoveryAdjustTrial().update : undefined,
//     getTableList: vzTableRef.value?.getTableList
//   };
//   dialogRef.value?.acceptParams(params);
// };
</script>

<style scoped lang="scss"></style>
