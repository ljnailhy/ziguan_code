<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="props.filterData">
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
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
          评估价：
          <div v-currency="scope.row.estimateMoney"></div>
        </div>
        <div class="flx">
          实付代理费用：
          <div v-currency="scope.row.realityAgencyFee"></div>
        </div>
        <div class="flx">
          评估有效期：<dict-date :date="scope.row.estimateDate" format="YYYY/MM/DD"></dict-date>至<dict-date
            :date="scope.row.estimateEndDate"
            format="YYYY/MM/DD"
          ></dict-date>
        </div>
      </template>
      <template #sourceInfo="scope">
        <div class="flx">
          受让人：
          <div v-if="scope.row['customId']" class="flx">
            <div class="flx">
              <vzCustomerInfo :default-value="scope.row.customId" :disabled="true" class="flx"></vzCustomerInfo>
            </div>
          </div>
          <div v-else>--</div>
        </div>
        <div class="flx">
          成交价：
          <div v-currency="scope.row.dealMoney"></div>
        </div>
        <div class="flx">成交时间：<dict-date :date="scope.row.dealDate" format="YYYY/MM/DD"></dict-date></div>
      </template>
      <template #ghInfo="scope">
        <div class="flx">
          税费：
          <div v-currency="scope.row.taxation"></div>
        </div>
        <div class="flx">过户时间：<dict-date :date="scope.row.transferOwnershipDate" format="YYYY/MM/DD"></dict-date></div>
        <!-- <div class="flx">是否二次挂网：{{ scope.row.isAgainAuction ? "是" : "否" }}</div> -->
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
      <template #customName="scope">
        <vzCustomerInfo
          v-model:value="scope.row['customId']"
          :default-value="scope.row['customId']"
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

<script setup lang="tsx" name="assetTransfer">
import { ref, reactive } from "vue";
import { assetTransfer } from "@/api/modules/property/assetTransfer/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { AssetTransferPageRequest, AssetTransferDTO } from "@/api/modules/property/assetTransfer/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { processStatus } from "@/enums/commonOptions";
import { useDialogStore } from "@/stores/modules/dialogParams";

import vzAgency from "@/components/source/vz-agency.vue";
import vzFlowchart from "@/components/source/vzFlowchart.vue";
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
// 表格配置项
const columns = reactive<ColumnProps<AssetTransferDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "zcInfo",
    label: "评估机构",
    minWidth: 300
  },
  {
    prop: "sourceInfo",
    label: "受让人信息",
    minWidth: 300
  },
  {
    prop: "ghInfo",
    label: "过户信息",
    minWidth: 250
  },

  {
    prop: "estimateName",
    label: "评估机构",
    minWidth: 250,
    align: "left",
    search: { el: "input" },
    isShow: false,
    headerAlign: "center"
  },
  {
    prop: "customName",
    label: "受让人",
    width: 300,
    isShow: false,
    search: { el: "input" }
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
    prop: "dealDate",
    label: "成交时间",
    width: 120,
    // type: "date",
    isShow: false,
    search: {
      key: "dealDateRange", //指定搜索的key
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
    // type: "date",
    prop: "estimateDate",
    label: "评估日期",
    width: 120,
    isShow: false,
    search: {
      key: "estimateDateRange", //指定搜索的key
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
  //   prop: "estimateMoney",
  //   label: "评估价(元)",
  //   width: 160,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },
  // {
  //   prop: "contacts",
  //   label: "联系人",
  //   width: 120
  //   // search: { el: "input" }
  // },
  // {
  //   prop: "contactsPhone",
  //   label: "联系电话",
  //   width: 120
  //   // search: { el: "input" }
  // },

  // {
  //   prop: "dealMoney",
  //   label: "成交价(元)",
  //   width: 160,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },

  // {
  //   prop: "taxation",
  //   label: "税费(元)",
  //   width: 160,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },
  // {
  //   type: "date",
  //   prop: "transferOwnershipDate",
  //   label: "过户时间",
  //   width: 120
  // },

  // {
  //   prop: "programme",
  //   label: "处置方案",
  //   width: 120
  // },
  // {
  //   prop: "remark",
  //   label: "备注",
  //   width: 120
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
  //   width: 200
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
const getTableList = (params: AssetTransferPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return assetTransfer().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: AssetTransferDTO) => {
  await useHandleData(assetTransfer().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};

// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: Partial<any> = {}) => {
  // flowUpdate
  const name = "draft" != row.flowState && title === "查看" ? "assetTransfer_flowUpdate" : "assetTransfer_createUpdate";
  const params = {
    dialogName: name,
    title,
    showBtn: true,
    readonly: true,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? assetTransfer().add : title === "编辑" ? assetTransfer().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  useDialogStore().setDrawerProps(params); //存缓存
  dialogRef.value?.acceptParams(params);
};
</script>

<style scoped lang="scss"></style>
