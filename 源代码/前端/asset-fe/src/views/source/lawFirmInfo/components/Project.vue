<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <template #lawyer="scope">
        <div v-if="scope.row['lawyer']" class="flx">
          <div class="flx" v-for="(item, index) in scope.row.lawyer.split(',')" :key="item">
            <vzLawyerInfo :default-value="item" :disabled="true" class="flx"></vzLawyerInfo>
            <span v-if="scope.row.lawyer.split(',').length - 1 !== index">、</span>
          </div>
        </div>
        <div v-else>--</div>
      </template>
      <template #operation="scope">
        <el-button type="primary" link @click="openDrawer('查看', scope.row)">查看</el-button>
      </template>
    </vz-table>
  </div>
</template>

<script setup lang="tsx" name="projectInfo">
import { ref, reactive } from "vue";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { ProjectInfoPageRequest, ProjectInfoDTO } from "@/api/modules/recovery/projectInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useRouter } from "vue-router";

import vzLawyerInfo from "@/components/source/vzLawyerInfo.vue";
// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
const router = useRouter();
type Props = {
  lawFirmId?: any;
  projectState?: string | number;
};

const props = withDefaults(defineProps<Props>(), {
  lawFirmId: "",
  projectState: ""
});

// 表格配置项
const columns = reactive<ColumnProps<ProjectInfoDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    align: "left",
    headerAlign: "center",
    search: { el: "input" }
  },
  // {
  //   prop: "product",
  //   label: "产品名称",
  //   minWidth: 160
  //   // search: { el: "select", props: { filterable: true } },
  //   // enum: () => useBaseStore().findEnumByName("PRODUCT_NAME")
  // },
  {
    prop: "projectState",
    label: "项目状态",
    minWidth: 120,
    search: { el: "select", props: { filterable: true } },
    enum: () => useBaseStore().findEnumByName("PROJECT_STATE")
  },

  {
    prop: "compensationMoney",
    label: "代偿金额(元)",
    minWidth: 160,
    type: "money",
    align: "right",
    headerAlign: "center"
  },
  {
    type: "date",
    prop: "compensationDate",
    label: "代偿时间",
    minWidth: 120,
    search: {
      key: "compensationDateRange", //指定搜索的key
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
    prop: "industryType",
    label: "行业分类",
    minWidth: 120,
    search: { el: "select", props: { filterable: true } },
    enum: () => useBaseStore().findEnumByName("INDUSTRY_GXB")
  },
  {
    prop: "manage",
    label: "保全经理",
    minWidth: 160,
    type: "user"
  },
  {
    prop: "lawyer",
    label: "责任律师",
    minWidth: 160,
    align: "left",
    headerAlign: "center"
  },
  {
    type: "date",
    prop: "proceedingAgeingDate",
    label: "诉讼时效",
    minWidth: 120
    // search: {
    //   key: "proceedingAgeingDateRange", //指定搜索的key
    //   el: "date-picker",
    //   span: 1,
    //   props: {
    //     type: "datetimerange",
    //     format: "YYYY-MM-DD HH:mm:ss",
    //     valueFormat: "x",
    //     defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
    //   }
    // }
  },
  {
    type: "date",
    prop: "adjustTrialDate",
    label: "执行时效",
    minWidth: 120
  },
  {
    prop: "residueReveMeasureNum",
    label: "剩余反担保措施",
    minWidth: 160
  },
  {
    prop: "totalCollectionAmount",
    label: "累计回款金额(元)",
    minWidth: 160,
    type: "money",
    align: "right",
    headerAlign: "center"
  },
  {
    prop: "residueRecoveryAmount",
    label: "剩余代偿余额(元)",
    minWidth: 160,
    type: "money",
    align: "right",
    headerAlign: "center"
  },
  {
    prop: "lawFee",
    label: "律师费(元)",
    minWidth: 160,
    type: "money",
    align: "right",
    headerAlign: "center"
  },
  // {
  //   type: "date",
  //   prop: "transferDate",
  //   label: "移交至保全部日期",
  //   minWidth: 160
  //   // search: {
  //   //   key: "createStampRange", //指定搜索的key
  //   //   el: "date-picker",
  //   //   span: 1,
  //   //   props: { type: "datetimerange", format: "YYYY-MM-DD HH:mm:ss", valueFormat: "x" }
  //   // }
  // },
  // {
  //   type: "area",
  //   prop: "belongCity",
  //   label: "所属区域",
  //   minWidth: 100
  // },
  // {
  //   prop: "cooperateBank",
  //   label: "合作银行",
  //   minWidth: 200,
  //   // search: { el: "select", props: { filterable: true } },
  //   enum: () => useBaseStore().findEnumByName("COOPERATE_BANK")
  // },
  // {
  //   prop: "bigSmall",
  //   label: "企业划型",
  //   width: "110",
  //   enum: () => useBaseStore().findEnumByName("BIG_SMALL")
  // },
  // {
  //   prop: "goverType",
  //   label: "政策扶持领域",
  //   minWidth: 120,
  //   // search: { el: "select", props: { filterable: true } },
  //   enum: () => useBaseStore().findEnumByName("INDUSTRY_POLICY_SUPPORT")
  // },
  // {
  //   type: "date",
  //   prop: "transferDate",
  //   label: "移交至保全部日期",
  //   minWidth: 140
  // },
  // {
  //   type: "date",
  //   prop: "debtBeginDate",
  //   label: "债权起始日期",
  //   minWidth: 140
  // },
  // {
  //   type: "date",
  //   prop: "debtEndDate",
  //   label: "债权到期日期",
  //   minWidth: 140
  // },
  // {
  //   prop: "loanMoney",
  //   label: "银行放款金额(元)",
  //   minWidth: 160,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },
  // {
  //   prop: "loanPactCode",
  //   label: "借款合同号",
  //   minWidth: 180
  // },
  // {
  //   prop: "pledPactCode",
  //   label: "保证合同号",
  //   minWidth: 180
  // },
  // {
  //   prop: "pactCode",
  //   label: "委保合同号",
  //   minWidth: 180
  // },
  // {
  //   prop: "loanCode",
  //   label: "借据号",
  //   minWidth: 180
  // },
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
  //   minWidth: 180
  //   // search: {
  //   //   key: "createStampRange", //指定搜索的key
  //   //   el: "date-picker",
  //   //   span: 1,
  //   //   props: {
  //   //     type: "datetimerange",
  //   //     format: "YYYY-MM-DD HH:mm:ss",
  //   //     valueFormat: "x",
  //   //     defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
  //   //   }
  //   // }
  // },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 100
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({
  lawFirmId: props.lawFirmId,
  projectState: props.projectState
});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: ProjectInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return projectInfo().findAll(newParams);
};

const openDrawer = (title: string, row: any = {}) => {
  if (title === "查看") {
    router.push({
      path: `/compensatory/projectInfo/detail/${row.id}`,
      query: {
        product: row.product
      }
    });
    return;
  }
};
// 暴露给父组件使用
defineExpose({ vzTableRef });
</script>

<style scoped lang="scss"></style>
