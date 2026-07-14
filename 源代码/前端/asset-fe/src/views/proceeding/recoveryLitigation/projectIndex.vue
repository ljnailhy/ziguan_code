<template>
  <div class="table-box">
    <vz-table
      ref="vzTableRef"
      @rowdouble-click="rowdoubleClick"
      :columns="columns"
      :request-api="getTableList"
      :init-param="initParam"
    >
      <!-- 表格 header 按钮 -->
      <!--      <template #belongProvince="scope">-->
      <!--        <div style="display: flex">-->
      <!--          <dict-area :value="scope.row['belongProvince']"></dict-area>&nbsp;-->
      <!--          <dict-area :value="scope.row['belongCity']"></dict-area>&nbsp;-->
      <!--          <dict-area :value="scope.row['belongDistrict']"></dict-area>-->
      <!--        </div>-->
      <!--      </template>-->

      <template #lawyer="scope">
        <div v-if="scope.row['lawyer']" class="flx">
          <div class="flx" v-for="(item, index) in scope.row.lawyer.split(',')" :key="item">
            <vzLawyerInfo :default-value="item" :disabled="true" class="flx"></vzLawyerInfo>
            <span v-if="scope.row.lawyer.split(',').length - 1 !== index">、</span>
          </div>
        </div>
        <div v-else>--</div>
      </template>
      <template #tableHeader>
        <!-- <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
        <el-button type="primary" v-auth="'add'" @click="importApi()">导入</el-button>
        <el-button type="primary" v-auth="'add'" @click="syncCompensatory()">同步代偿项目</el-button> -->
      </template>
      <!-- 合并列表数据 s -->
      <template #dcInfo="scope">
        <div class="flx" style="font-weight: bold">
          {{ scope.row.projectName }}
        </div>
        <div class="flx">
          代偿金额：
          <div v-currency="scope.row.compensationMoney"></div>
        </div>
        <div class="flx">
          代偿时间：
          <dict-date :date="scope.row.compensationDate" format="YYYY/MM/DD"></dict-date>
        </div>
      </template>
      <template #documentType="scope">
        <div class="flx">
          证件类型：
          <dict-enum :options="documentTypeOptions" :value="scope.row.documentType"></dict-enum>
        </div>
        <div class="flx">
          证件号码：
          {{ scope.row.documentNumber }}
        </div>
      </template>
      <template #proceedingAgeingDate="scope">
        <div class="flx">
          行业分类：
          <dict-name dict-type="INDUSTRY_GXB" :dict-value="scope.row.industryType"></dict-name>
        </div>
        <div class="flx">
          移交至保全部日期：
          <dict-date :date="scope.row.transferDate" format="YYYY/MM/DD"></dict-date>
        </div>
        <div class="flx">
          诉讼时效：
          <dict-date :date="scope.row.proceedingAgeingDate" format="YYYY/MM/DD"></dict-date>
        </div>
      </template>
      <template #lawyers="scope">
        <div class="flx">
          律所：
          <vzLawFirmInfo :default-value="scope.row.lawFirmId" :disabled="true"></vzLawFirmInfo>
        </div>
        <div class="flx">
          律师：
          <div v-if="scope.row['lawyer']" class="flx">
            <div class="flx" v-for="(item, index) in scope.row.lawyer.split(',')" :key="item">
              <vzLawyerInfo :default-value="item" :disabled="true" class="flx"></vzLawyerInfo>
              <span v-if="scope.row.lawyer.split(',').length - 1 !== index">、</span>
            </div>
          </div>
          <div v-else>--</div>
        </div>
        <div class="flx">
          保全经理：
          <dict-user-name :user-code="scope.row.manage"></dict-user-name>
        </div>
      </template>
      <template #creator="scope">
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
        <el-button type="primary" v-auth="'workRegister'" link @click="workRegister(scope.row)">工作登记</el-button>
        <el-dropdown
          v-if="
            BUTTONS.judgement ||
            BUTTONS.withdrawLawsuit ||
            BUTTONS.preservation ||
            BUTTONS.adjustTrial ||
            BUTTONS.execute ||
            BUTTONS.closeCase ||
            BUTTONS.other ||
            BUTTONS.finalVersion
          "
        >
          <el-button type="primary" link style="margin-left: 12px"> 更多 </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item v-if="BUTTONS.judgement" @click="openrecoveryLitigationDrawer('新增', scope.row)">
                诉状
              </el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.judgement" @click="openJudgementDrawer('立案', scope.row)">立案</el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.withdrawLawsuit" @click="openPreservationDrawer('撤诉', 'DROP_LAWSUIT', scope.row)">
                撤诉
              </el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.preservation" @click="openPreservationDrawer('保全', 'PRESERVATION', scope.row)">
                保全
              </el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.judgement" @click="openJudgementDrawer('审理', scope.row)"> 审理 </el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.adjustTrial" @click="openAdjustTrialDrawer('新增', scope.row)">
                调解/判决
              </el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.execute" @click="openExecuteDrawer('新增', scope.row)"> 执行 </el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.finalVersion" @click="openPreservationDrawer('终本', 'FINAL', scope.row)">
                终本
              </el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.closeCase" @click="openPreservationDrawer('结案', 'CLOSE_CASE', scope.row)">
                结案
              </el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.other" @click="openPreservationDrawer('其他诉讼信息', 'OTHER', scope.row)">
                其他
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

        <!-- <el-button type="primary" v-auth="'editProperty'" link @click="openDrawer('编辑', scope.row)">编辑</el-button> -->
        <!-- <el-button type="primary" v-auth="'edit'" link @click="openDrawer('编辑', scope.row)">编辑</el-button>
        <el-button type="primary" v-auth="'delete'" link @click="deleteAccount(scope.row)">删除</el-button>
        <el-button type="primary" v-auth="'workRegister'" link @click="workRegister(scope.row)">工作登记</el-button>
        <el-button type="primary" v-auth="'changeContract'" link @click="changeContract(scope.row)">合同变更</el-button>
        <el-button type="primary" v-auth="'changeLawyer'" link @click="changeLawyer(scope.row)">律师变更</el-button>
        <el-button type="primary" v-auth="'propertyRegister'" link @click="propertyRegister(scope.row)">财产登记</el-button> -->
        <!-- <el-button type="primary" v-auth="'judgement'" link @click="openrecoveryLitigationDrawer('新增', scope.row)">
          诉状
        </el-button>
        <el-button type="primary" v-auth="'judgement'" link @click="openJudgementDrawer('立案', scope.row)">立案</el-button>
        <el-button
          type="primary"
          v-auth="'withdrawLawsuit'"
          link
          @click="openPreservationDrawer('撤诉', 'DROP_LAWSUIT', scope.row)"
        >
          撤诉
        </el-button>
        <el-button type="primary" v-auth="'preservation'" link @click="openPreservationDrawer('保全', 'PRESERVATION', scope.row)">
          保全
        </el-button>
        <el-button type="primary" v-auth="'judgement'" link @click="openJudgementDrawer('审理', scope.row)">审理</el-button>
        <el-button type="primary" v-auth="'adjustTrial'" link @click="openAdjustTrialDrawer('新增', scope.row)">
          调解审判
        </el-button>
        <el-button type="primary" v-auth="'execute'" link @click="openExecuteDrawer('新增', scope.row)"> 执行 </el-button>
        <el-button type="primary" v-auth="'finalVersion'" link @click="openPreservationDrawer('终本', 'FINAL', scope.row)">
          终本
        </el-button>
        <el-button type="primary" v-auth="'closeCase'" link @click="openPreservationDrawer('结案', 'CLOSE_CASE', scope.row)">
          结案
        </el-button>
        <el-button type="primary" v-auth="'other'" link @click="openPreservationDrawer('其他诉讼信息', 'OTHER', scope.row)">
          其他
        </el-button> -->
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
    <ImportExcel ref="importRef" />
    <vzWork ref="vzWorkRef" v-show="false"></vzWork>
    <zcProperty ref="zcPropertyRef" v-show="false"></zcProperty>
    <zcChangeContract ref="zcChangeContractRef" :filter-data="contractFilterData"></zcChangeContract>
    <zcChangeLawyer ref="zcChangeLawyerRef" :filter-data="lawyerFilterData"></zcChangeLawyer>
    <el-dialog v-model="dialogVisible" title="代偿项目" width="80%">
      <vz-table ref="ayncDataRef" @rowdouble-click="rowdoubleClick" :columns="columnsSync" :request-api="getSyncTableList">
      </vz-table>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="ayncData">确定同步</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="tsx" name="projectInfo">
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { ProjectInfoPageRequest, ProjectInfoDTO, SyncRequest } from "@/api/modules/recovery/projectInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useRouter } from "vue-router";
import { recoveryJudgement } from "@/api/modules/proceeding/recoveryJudgement/api";
import { recoveryAdjustTrial } from "@/api/modules/proceeding/recoveryAdjustTrial/api";
import { recoveryLitigationDetails } from "@/api/modules/proceeding/recoveryLitigationDetails/api";
import { recoveryLitigation } from "@/api/modules/proceeding/recoveryLitigation/api";
import { recoveryExecute } from "@/api/modules/proceeding/recoveryExecute/api";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { documentTypeOptions } from "@/api/modules/source/subjectInfo/interface";

//引入组件
import vzWork from "@/views/compensatory/workRegister/vzWork.vue";
import ImportExcel from "@/components/ImportExcel/index.vue";
import zcChangeContract from "@/views/compensatory/projectInfo/changeContract.vue";
import zcChangeLawyer from "@/views/compensatory/projectInfo/changeLawyer.vue";
import zcProperty from "@/views/compensatory/revePropertyInfo/zcProperty.vue";
import vzLawyerInfo from "@/components/source/vzLawyerInfo.vue";
import VzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
const router = useRouter();
const vzWorkRef = ref();
const zcPropertyRef = ref();
const zcChangeContractRef = ref();
const zcChangeLawyerRef = ref();
const contractFilterData = ref();
const lawyerFilterData = ref();
const dialogVisible = ref(false);
// 按钮权限
const { BUTTONS } = useAuthButtons();
const emit = defineEmits<{
  rowdoubleClick: [{ row?: any; column?: any; event?: Event }];
}>();
const rowdoubleClick = (row: any) => {
  emit("rowdoubleClick", row);
};

const ayncDataRef = ref();
const ayncData = () => {
  if (ayncDataRef.value.selectedList.length == 0) {
    return ElMessage.warning("请至少勾选一条");
  }
  let ArrData: string[] = [];
  for (let i = 0; i < ayncDataRef.value.selectedList.length; i++) {
    ArrData.push(ayncDataRef.value.selectedList[i].business_no);
  }
  return projectInfo()
    .syncCompensatory(ArrData)
    .then((res: EmptyObjectType) => {
      if (res.code == 0) {
        ElMessage.success({ message: `同步成功！` });
        dialogVisible.value = false;
      } else {
        ElMessage.warning(res.msg);
      }
    });
};
// 表格配置项
const columns = reactive<ColumnProps<ProjectInfoDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 200,
    align: "left",
    isShow: false,
    headerAlign: "center",
    search: { el: "input" }
  },
  {
    prop: "dcInfo",
    label: "项目信息",
    minWidth: 250
  },
  {
    prop: "projectState",
    label: "项目状态",
    minWidth: 90,
    search: { el: "select", props: { filterable: true } },
    enum: () => useBaseStore().findEnumByName("PROJECT_STATE")
  },
  // {
  //   prop: "compensationMoney",
  //   label: "代偿金额(元)",
  //   minWidth: 200,
  //   type: "money",
  //   align: "right"
  // },
  // {
  //   type: "date",
  //   prop: "compensationDate",
  //   label: "代偿时间",
  //   minWidth: 120,
  //   search: {
  //     key: "compensationDateRange", //指定搜索的key
  //     el: "date-picker",
  //     span: 1,
  //     props: {
  //       type: "datetimerange",
  //       format: "YYYY-MM-DD HH:mm:ss",
  //       valueFormat: "x",
  //       defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
  //     }
  //   }
  // },

  {
    prop: "lawyers",
    label: "律所&保全经理",
    minWidth: 250
  },

  {
    prop: "compensationDate",
    label: "代偿时间",
    minWidth: 220,
    isShow: false,
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
  // {
  //   prop: "documentType",
  //   label: "证件类型",
  //   minWidth: 160,
  //   enum: documentTypeOptions
  // },
  // {
  //   prop: "documentNumber",
  //   label: "证件号",
  //   minWidth: 160
  // },
  {
    prop: "lawFirmId",
    label: "律所名称",
    minWidth: 300,
    fixed: "left",
    align: "left",
    headerAlign: "center",
    isShow: false,
    search: {
      render: ({ searchParam }) => {
        return <vz-law v-model={searchParam.lawFirmId} default-value={searchParam.lawFirmId} />;
      }
    }
  },
  {
    prop: "manage",
    label: "保全经理",
    type: "user",
    minWidth: 120,
    fixed: "left",
    isShow: false,
    search: {
      render: ({ searchParam }) => {
        return <vz-user-org v-model={searchParam.manage} org-code={"ZXD_ZCBQB"} dict-value={searchParam.manage} />;
      }
    }
  },
  {
    prop: "documentType",
    label: "证件信息",
    minWidth: 250,
    enum: documentTypeOptions
  },
  {
    prop: "industryType",
    label: "行业分类",
    minWidth: 200,
    isShow: false,
    search: { el: "select", props: { filterable: true } },
    enum: () => useBaseStore().findEnumByName("INDUSTRY_GXB")
  },
  // {
  //   prop: "name",
  //   label: "律所名称",
  //   isShow: false,
  //   search: { el: "select", props: { filterable: true } },
  //   minWidth: 120
  // },
  // {
  //   prop: "lawyer",
  //   label: "律师名称",
  //   isShow: false,
  //   search: { el: "select", props: { filterable: true } },
  //   minWidth: 250
  // },
  // {
  //   prop: "manage",
  //   label: "保全经理",
  //   width: "200",
  //   isShow: false,
  //   search: { el: "select", props: { filterable: true } }
  // },
  // {
  //   type: "date",
  //   prop: "transferDate",
  //   label: "移交至保全部日期",
  //   minWidth: 160,
  //   search: {
  //     key: "transferDateRange", //指定搜索的key
  //     el: "date-picker",
  //     span: 1,
  //     props: {
  //       type: "datetimerange",
  //       format: "YYYY-MM-DD HH:mm:ss",
  //       valueFormat: "x",
  //       defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
  //     }
  //   }
  // },
  // {
  //   type: "date",
  //   prop: "proceedingAgeingDate",
  //   label: "诉讼时效",
  //   minWidth: 120
  // },
  {
    prop: "proceedingAgeingDate",
    label: "其他信息",
    minWidth: 250
  },
  // {
  //   type: "area",
  //   prop: "belongCity",
  //   label: "所属区域",
  //   minWidth: 100
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
  //   search: { el: "select", props: { filterable: true } },
  //   enum: () => useBaseStore().findEnumByName("INDUSTRY_POLICY_SUPPORT")
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
  //   minWidth: 180,
  //   search: {
  //     key: "createStampRange", //指定搜索的key
  //     el: "date-picker",
  //     span: 1,
  //     props: {
  //       type: "datetimerange",
  //       format: "YYYY-MM-DD HH:mm:ss",
  //       valueFormat: "x",
  //       defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
  //     }
  //   }
  // },
  {
    prop: "creator",
    label: "创建信息",
    minWidth: 250
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 180,
    isShow: BUTTONS.value.edit || BUTTONS.value.detail || BUTTONS.value.delete
  }
]);
const columnsSync = reactive<ColumnProps<SyncRequest>[]>([
  { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60, isShow: true },
  {
    prop: "BEGIN_check_date",
    label: "代偿开始时间",
    minWidth: 120,
    type: "date",
    search: {
      key: "BEGIN_check_date", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: { type: "date", format: "YYYY-MM-DD", valueFormat: "x" }
    },
    isShow: true
  },
  {
    prop: "END_check_date",
    label: "代偿截至时间",
    minWidth: 120,
    type: "date",
    search: {
      key: "END_check_date", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: { type: "date", format: "YYYY-MM-DD", valueFormat: "x" }
    }
  },
  {
    prop: "business_no",
    label: "项目唯一标识",
    minWidth: 160
  },
  {
    prop: "product_NAME",
    label: "产品名称",
    minWidth: 200,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "customer_NAME",
    label: "债务人名称",
    minWidth: 120,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "customer_Property",
    label: "债务人性质",
    minWidth: 120
  },
  {
    prop: "repayment_amount",
    label: "代偿金额",
    minWidth: 120
  },
  {
    type: "date",
    prop: "repayment_date",
    label: "代偿日期",
    minWidth: 120
  },
  {
    prop: "credential_TYPE",
    label: "证件类型",
    minWidth: 120
  },
  {
    prop: "credential_NO",
    label: "证件号",
    minWidth: 160,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "province",
    label: "省",
    minWidth: 120,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "city",
    label: "市",
    minWidth: 120,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "area",
    label: "区",
    minWidth: 120,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "industry_GXB",
    label: "行业分类(工信部)",
    minWidth: 200,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "enterprise_SIZE",
    label: "企业划型",
    minWidth: 120
  },
  {
    prop: "industry_POLICY_SUPPORT",
    label: "政策扶持领域",
    minWidth: 120
  },
  {
    prop: "debt_BILL_START_DAY",
    label: "债权起始日期",
    minWidth: 120,
    type: "date"
  },
  {
    prop: "debt_BILL_DUE_DAY",
    label: "债权到期日期",
    minWidth: 120,
    type: "date"
  },
  {
    prop: "debt_amount",
    label: "银行放款金额",
    minWidth: 120
  },
  {
    prop: "loan_RATE",
    label: "贷款利率[%]",
    minWidth: 120
  },
  {
    prop: "principle_claim_contract_no",
    label: "借款合同号",
    minWidth: 200,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "warranty_contract_no",
    label: "保证合同号",
    minWidth: 200,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "guarantee_CONTRACT_NO",
    label: "委保合同号",
    minWidth: 200,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "debt_BILL_NO",
    label: "借据号",
    minWidth: 200,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "guarantee_FEE_RATE",
    label: "担保费率（年）[%]",
    minWidth: 120
  },
  {
    prop: "counter_GUARANTEE_TYPE",
    label: "反担保方式",
    minWidth: 160,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "rist_TYPE_AFTER_GUARANTEE",
    label: "风险归类",
    minWidth: 160
  },
  {
    prop: "risk_rate_bank",
    label: "分险比例（债权人）",
    minWidth: 120
  },
  {
    prop: "risk_rate_origin",
    label: "分险比例（原担保）",
    minWidth: 120
  },
  {
    prop: "risk_rate_again",
    label: "分险比例（再担保）",
    minWidth: 120
  },
  {
    prop: "risk_rate_other",
    label: "分险比例(其他)",
    minWidth: 120
  },
  {
    prop: "relation_enterprise",
    label: "关联企业",
    minWidth: 120
  },
  {
    prop: "relation_enterprise_no",
    label: "关联企业社会信用统一代码",
    minWidth: 120
  },
  {
    prop: "contact",
    label: "联系人(或法人)",
    minWidth: 120
  },
  {
    prop: "tel",
    label: "联系人电话",
    minWidth: 120
  },
  {
    prop: "a_name",
    label: "A角名称",
    minWidth: 120
  },
  {
    prop: "b_name",
    label: "B角名称",
    minWidth: 120
  }
]);
// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({ projectStateList: [] });

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = async (params: ProjectInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  const projectStateList = await useBaseStore().findEnumByName("PROJECT_STATE");

  const merged = Object.assign({}, newParams, {
    projectStateList: projectStateList
      .filter(item => item.itemCode != "PROJECT_STATE_01") // 筛选条件
      .map(item => item.id)
  });
  return projectInfo().findLimitsAll(merged);
};
const getSyncTableList = (params: SyncRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return projectInfo().getCompensatoryData(newParams);
};
const openrecoveryLitigationDrawer = (title: string, row: Partial<ProjectInfoDTO> = {}) => {
  const params = {
    dialogName: "recoveryLitigation_createUpdate",
    title: "诉状",
    showBtn: true,
    projectId: row?.id,
    lawFirmId: row?.lawFirmId,
    compensationMoney: row?.compensationMoney,
    isView: title === "查看",
    api: title === "新增" ? recoveryLitigation().add : title === "编辑" ? recoveryLitigation().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };

  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};

const openJudgementDrawer = (title: string, row: Partial<ProjectInfoDTO> = {}) => {
  const params = {
    dialogName: "recoveryJudgement_createUpdate",
    title: title,
    showBtn: true,
    projectId: row?.id,
    lawFirmId: row?.lawFirmId,
    litigationTypeList: title == "立案" ? ["REGISTER"] : ["FIRST_INSTANCE", "SECOND_INSTANCE", "RETRIAL"],
    litigationType: title == "立案" ? "REGISTER" : null,
    isView: false,
    api: recoveryJudgement().add,
    getTableList: vzTableRef.value?.getTableList
  };

  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
//  保全 撤诉
const openPreservationDrawer = (title: string, litigationType: string, row: Partial<ProjectInfoDTO> = {}) => {
  const params = {
    dialogName: "recoveryLitigationDetails_createUpdate",
    title,
    showBtn: true,
    projectId: row?.id,
    projectName: row?.projectName,
    lawFirmId: row?.lawFirmId,
    litigationType: litigationType,
    isView: false,
    api: recoveryLitigationDetails().add,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
//  调解
const openAdjustTrialDrawer = (title: string, row: Partial<ProjectInfoDTO> = {}) => {
  const params = {
    dialogName: "recoveryAdjustTrial_createUpdate",
    title: "调解/判决",
    showBtn: true,
    projectId: row?.id,
    lawFirmId: row?.lawFirmId,
    isView: title === "查看",
    api: title === "新增" ? recoveryAdjustTrial().add : title === "编辑" ? recoveryAdjustTrial().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
//  执行
const openExecuteDrawer = (title: string, row: Partial<ProjectInfoDTO> = {}) => {
  const params = {
    dialogName: "recoveryExecute_createUpdate",
    title: "执行",
    showBtn: true,
    projectId: row?.id,
    lawFirmId: row?.lawFirmId,
    isView: title === "查看",
    api: title === "新增" ? recoveryExecute().add : title === "编辑" ? recoveryExecute().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
// 删除用户信息
// const deleteAccount = async (params: ProjectInfoDTO) => {
//   await useHandleData(projectInfo().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
//   vzTableRef.value?.getTableList();
// };
// 工作登记
const workRegister = async (params: any) => {
  vzWorkRef.value.handleFocus("PROJECT_INFO", params.id);
};

// // 财产登记
// const propertyRegister = async (params: ProjectInfoDTO) => {
//   zcPropertyRef.value.handleFocus("PROJECT_INFO", params.id);
// };
//导入
const importRef = ref();
// const importApi = () => {
//   let params = {
//     title: "数据",
//     // tempApi: exportUserInfo,
//     importApi: projectInfo().importData
//   };
//   importRef.value.acceptParams(params);
// };
// // 合同变更
// const changeContract = async (params: ProjectInfoDTO) => {
//   //  查询类型是合同类型并且是代理合同
//   useBaseStore()
//     .findEnumByName("CONTRACT_TYPE")
//     .then((res: EmptyObjectType) => {
//       for (let i = 0; i < res.length; i++) {
//         if ("CONTRACT_TYPE_001" == res[i].itemCode) {
//           contractFilterData.value = { contractType: res[i].id };
//         }
//       }
//       zcChangeContractRef.value.handleFocus("PROJECT_INFO", params.id);
//     });
// };
// // 律师变更
// const changeLawyer = async (params: ProjectInfoDTO) => {
//   //  判断当前项目是否存在律所
//   if (params.lawFirmId) {
//     lawyerFilterData.value = { lawFirmId: params.lawFirmId };
//     zcChangeLawyerRef.value.handleFocus("PROJECT_INFO", params.id);
//   } else {
//     ElMessage.warning("请先分配律所");
//   }
// };
// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
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
  const params = {
    dialogName: "projectInfo_createUpdate",
    title,
    showBtn: true,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? projectInfo().add : title === "编辑" ? projectInfo().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
onMounted(() => {
  // useBaseStore()
  //   .findEnumByName("PROJECT_STATE")
  //   .then(dict => {
  //     console.log(dict);
  //     const projectStateList = dict
  //       .filter(item => item.itemCode != "PROJECT_STATE_01") // 筛选条件
  //       .map(item => item.id); // 提取 id
  //     initParam.projectStateList = projectStateList;
  //     console.log(initParam);
  //   });
});

// 暴露给父组件使用
defineExpose({ vzTableRef });
</script>

<style scoped lang="scss"></style>
