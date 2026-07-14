<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="props.filterData">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-if="isShowBtn" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
      </template>
      <!-- 合并列表数据 s -->
      <template #zcInfo="scope">
        <div class="flx" style="font-weight: bold">
          <div v-if="scope.row['estimateId']" class="flx">
            <div class="flx">
              <vzAgency :default-value="scope.row.estimateId" :disabled="true" class="flx"></vzAgency>
            </div>
          </div>
          <div v-else>--</div>
        </div>
        <div class="flx">
          代理费用：
          <div v-currency="scope.row.agencyFee"></div>
        </div>
        <div class="flx">
          年租金评估价：
          <div v-currency="scope.row.estimateMoney"></div>
        </div>
        <div class="flx">
          评估有效期：<dict-date :date="scope.row.estimateDate" format="YYYY/MM/DD"></dict-date>至<dict-date
            :date="scope.row.estimateEndDate"
            format="YYYY/MM/DD"
          ></dict-date>
        </div>
        <div class="flx">是否评估：{{ scope.row.isEstimate ? "是" : "否" }}</div>
      </template>
      <template #moneyInfo="scope">
        <div class="flx">
          月租金：
          <div v-currency="scope.row.monthRent"></div>
        </div>
        <div class="flx">
          年租金：
          <div v-currency="scope.row.yearRent"></div>
        </div>
        <div class="flx">
          保证金：
          <div v-currency="scope.row.margin"></div>
        </div>
      </template>
      <template #sourceInfo="scope">
        <div class="flx">
          承租人：
          <div v-if="scope.row['lessee']" class="flx">
            <div class="flx">
              <vzCustomerInfo :default-value="scope.row.lessee" :disabled="true" class="flx"></vzCustomerInfo>
            </div>
          </div>
          <div v-else>--</div>
        </div>
        <div class="flx">
          缴纳周期：
          <dict-enum :options="paymentCycleOptions" :value="scope.row.paymentCycle"></dict-enum>
        </div>
        <div class="flx">
          租赁期限：<dict-date :date="scope.row.leaseTermStart" format="YYYY/MM/DD"></dict-date>至<dict-date
            :date="scope.row.leaseTermEnd"
            format="YYYY/MM/DD"
          ></dict-date>
        </div>
      </template>
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
      <template #estimateName="scope">
        <vzAgency :default-value="scope.row['estimateId']" :disabled="true"></vzAgency>
      </template>
      <template #lesseeName="scope">
        <vzCustomerInfo
          v-model:value="scope.row['lessee']"
          :default-value="scope.row['lessee']"
          :disabled="true"
        ></vzCustomerInfo>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button
          type="primary"
          v-auth="'edit'"
          link
          @click="openDrawer('编辑', scope.row)"
          v-if="'draft' == scope.row.flowState"
        >
          编辑
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

<script setup lang="tsx" name="leaseInfo">
import { ref, reactive } from "vue";
import { leaseInfo } from "@/api/modules/property/leaseInfo/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { LeaseInfoPageRequest, LeaseInfoDTO, paymentCycleOptions } from "@/api/modules/property/leaseInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { processStatus } from "@/enums/commonOptions";
import { useDialogStore } from "@/stores/modules/dialogParams";

import vzFlowchart from "@/components/source/vzFlowchart.vue";
import vzAgency from "@/components/source/vz-agency.vue";

import vzCustomerInfo from "@/components/source/vzCustomerInfo.vue";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();

interface ContractInfoPropsType {
  filterData?: { [key: string]: any };
  isShowBtn?: boolean;
}

const props = withDefaults(defineProps<ContractInfoPropsType>(), {
  filterData: () => ({}),
  isShowBtn: true
});

const flowchartRef = ref();
const openFlowchart = (row: EmptyObjectType) => {
  flowchartRef.value.openDialog(row.id);
};

// 评估机构、评估价、评估日期、代理费用、承租人、租赁期限开始日期、租赁期限结束日期、缴纳周期、月租金、年租金、保证金
// 表格配置项
const columns = reactive<ColumnProps<LeaseInfoDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "zcInfo",
    label: "评估机构",
    minWidth: 300
  },
  {
    prop: "sourceInfo",
    label: "承租人信息",
    minWidth: 300
  },
  {
    prop: "moneyInfo",
    label: "租金信息",
    minWidth: 250
  },

  {
    prop: "estimateName",
    label: "评估机构",
    width: 250,
    align: "left",
    isShow: false,
    search: { el: "input" },
    headerAlign: "center"
  },
  {
    prop: "lesseeName",
    label: "承租人",
    search: { el: "input" },
    isShow: false,
    width: 120
  },
  {
    prop: "paymentCycle",
    label: "缴纳周期",
    isShow: false,
    enum: paymentCycleOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 180
  },
  {
    prop: "flowState",
    label: "流程状态",
    enum: processStatus,
    tag: true,
    search: { el: "select", props: { filterable: true } },
    width: 90
  },
  {
    // type: "date",
    prop: "leaseTermEnd",
    label: "租赁结束日期",
    width: 160,
    isShow: false,
    search: {
      key: "leaseTermEndRange", //指定搜索的key
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

  // {
  //   type: "date",
  //   prop: "estimateDate",
  //   label: "评估日期",
  //   width: 120
  //   // search: {
  //   //   key: "estimateDateRange", //指定搜索的key
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

  // {
  //   type: "date",
  //   prop: "leaseTermStart",
  //   label: "租赁开始日期",
  //   width: 160
  // },
  // {
  //   prop: "leasePurpose",
  //   label: "租赁用途",
  //   width: 120,
  //   search: { el: "input" }
  // },

  // {
  //   prop: "creator",
  //   type: "user",
  //   label: "创建人",
  //   width: 120
  // },
  // {
  //   type: "datetime",
  //   prop: "createStamp",
  //   label: "创建时间",
  //   width: 220
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
// const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: LeaseInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return leaseInfo().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: LeaseInfoDTO) => {
  await useHandleData(leaseInfo().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<any> = {}) => {
  const name = "draft" != row.flowState && title === "查看" ? "leaseInfo_flowUpdate" : "leaseInfo_createUpdate";
  const params = {
    dialogName: name,
    title,
    showBtn: true,
    id: row?.id,
    isView: title === "查看",
    readonly: true,
    api: title === "新增" ? leaseInfo().add : title === "编辑" ? leaseInfo().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  useDialogStore().setDrawerProps(params); //存缓存
  dialogRef.value?.acceptParams(params);
};
</script>

<style scoped lang="scss"></style>
