<template>
  <el-row :gutter="10">
    <el-col :span="24">
      <div ref="containerRef" class="projectInfo">
        <ProjectInfo :form="form" :charge="charge" :product="product" :find-by-id="findById" v-if="form.id"></ProjectInfo>
        <!-- <DebtInfo :form="form"></DebtInfo> -->

        <el-radio-group v-model="tabPosition" style="margin-bottom: 10px">
          <el-radio-button value="1">项目信息</el-radio-button>
          <el-radio-button value="2">借款信息</el-radio-button>
          <el-radio-button value="9">协议信息</el-radio-button>
          <el-radio-button value="3">诉讼过程信息</el-radio-button>
          <el-radio-button value="4">收付款信息</el-radio-button>
          <el-radio-button value="5">抵债资产</el-radio-button>
          <el-radio-button value="6">审批流程</el-radio-button>
          <el-radio-button value="7">附件信息</el-radio-button>
          <el-radio-button value="8">项目工作记录</el-radio-button>
        </el-radio-group>
        <div v-if="tabPosition == '1'">
          <vz-card title="详细信息" label-width="120">
            <el-row :gutter="10">
              <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <CellItem label="债务人姓名" label-width="120">
                  <template #value>
                    <vz-subject v-model="form!.debtorId" :default-value="form!.debtorId" disabled></vz-subject>
                  </template>
                </CellItem>
                <CellItem label="证件号码" label-width="120">
                  <template #value>
                    {{ form!.subjectInfoRequest.documentNumber ||'--' }}
                  </template>
                </CellItem>

                <CellItem label="联系人电话" label-width="120">
                  <template #value>
                    {{ form!.subjectInfoRequest.phone ||'--'}}
                  </template>
                </CellItem>

                <CellItem label="行业分类" label-width="120">
                  <template #value>
                    <dict-name dict-type="INDUSTRY_GXB" :dict-value="form!.industryType"></dict-name>
                  </template>
                </CellItem>
                <CellItem label="关联企业" label-width="120">
                  <template #value> {{ form.relationEnterprise || "--" }} </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <CellItem label="债务人性质" label-width="120">
                  <template #value>
                    <dict-enum :options="natureOptions" :value="form!.subjectInfoRequest.nature"></dict-enum>
                  </template>
                </CellItem>
                <CellItem label="法定代表人" label-width="120">
                  <template #value>
                    {{ form!.subjectInfoRequest.legalRepresentative ||'--'}}
                  </template>
                </CellItem>
                <CellItem label="所属区域" label-width="120">
                  <template #value>
                    <div class="flx">
                      <dict-area :value="form.subjectInfoRequest.belongProvince"></dict-area>-<dict-area
                        :value="form.subjectInfoRequest.belongCity"
                      ></dict-area>
                      -
                      <dict-area :value="form.subjectInfoRequest.belongDistrict"></dict-area>
                    </div>
                  </template>
                </CellItem>
                <CellItem label="企业划型" :value="form.pledPactCode" label-width="120">
                  <template #value>
                    <dict-name dict-type="BIG_SMALL" :dict-value="form!.bigSmall"></dict-name>
                  </template>
                </CellItem>

                <CellItem label="关联企业信用代码" label-width="120">
                  <template #value> {{ form.relationEnterpriseNo || "--" }} </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <CellItem label="证件类型" label-width="120">
                  <template #value>
                    <dict-enum :options="documentTypeOptions" :value="form.subjectInfoRequest!.documentType"></dict-enum>
                  </template>
                </CellItem>
                <CellItem label="联系人" label-width="120">
                  <template #value>
                    {{form!.subjectInfoRequest.contacts||'--'}}
                  </template>
                </CellItem>
                <CellItem label="详细地址" :value="form!.subjectInfoRequest.address" label-width="120" />
                <!-- <CellItem label="项目名称" label-width="120">
                  <template #value>
                    {{form!.subjectInfoRequest.subjectName}}
                  </template>
                </CellItem> -->
                <CellItem label="政策扶持领域类别" label-width="120">
                  <template #value>
                    <dict-name dict-type="INDUSTRY_POLICY_SUPPORT" :dict-value="form!.goverType"></dict-name>
                  </template>
                </CellItem>
                <CellItem label="项目原所属组织" label-width="120">
                  <template #value>
                    <dict-org-name :value="form.primaryAffiliatedOrg"></dict-org-name>
                  </template>
                </CellItem>
              </el-col>
            </el-row>
          </vz-card>
          <vz-card title="代偿信息">
            <el-row :gutter="10">
              <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <CellItem label="代偿金额(元)" :value="form.compensationMoney" label-width="110">
                  <template #value>
                    <div v-currency="form.compensationMoney"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <CellItem label="代偿时间" label-width="110">
                  <template #value>
                    <dict-date :date="form!.compensationDate" format="YYYY-MM-DD"></dict-date>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <CellItem label="诉讼时效" label-width="110">
                  <template #value>
                    <dict-date :date="form!.proceedingAgeingDate" format="YYYY-MM-DD"></dict-date>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <CellItem label="执行时效" label-width="110">
                  <template #value>
                    <dict-date :date="form!.adjustTrialDate" format="YYYY-MM-DD"></dict-date>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
                <CellItem label="移交保全部日期" label-width="110">
                  <template #value>
                    <dict-date :date="form!.transferDate" format="YYYY-MM-DD"></dict-date>
                  </template>
                </CellItem>
              </el-col>

              <!--              <el-col :xs="24" :sm="24" :md="24" :lg="8" :xl="24">-->
              <!--                <CellItem label="代偿方案说明" :value="form.compensationPlan" label-width="110" />-->
              <!--              </el-col>-->
              <el-col :xs="24" :sm="24" :md="24" :lg="8" :xl="24">
                <CellItem label="备注" :value="form.remark" label-width="110" />
              </el-col>
            </el-row>
          </vz-card>
          <vz-card title="反担保措施">
            <form-table :table-data="reveInfoList" readonly></form-table>
          </vz-card>
          <vz-card title="其他财产信息">
            <form-table :table-data="propertyInfoList" readonly></form-table>
          </vz-card>
        </div>
        <div v-if="tabPosition == '2'">
          <vz-card title="业务信息">
            <form-table ref="businessRef" :table-data="businessArray" :product-list="productList" :readonly="true"> </form-table>
          </vz-card>
          <vz-card title="借款信息">
            <form-table ref="projectLoanRef" :table-data="projectLoanArray" :readonly="true"> </form-table>
          </vz-card>
        </div>
        <div v-if="tabPosition == '3'">
          <Litigation :project-id="route.params.id"></Litigation>
          <RegisterInfo :project-id="route.params.id" :project-type="['REGISTER']"></RegisterInfo>
          <RevokeInfo :project-id="route.params.id" project-type="PRESERVATION"></RevokeInfo>
          <RegisterInfo
            :project-id="route.params.id"
            :project-type="['FIRST_INSTANCE', 'SECOND_INSTANCE', 'RETRIAL']"
          ></RegisterInfo>
          <TrialInfo :project-id="route.params.id"></TrialInfo>
          <RevokeInfo :project-id="route.params.id" project-type="DROP_LAWSUIT"></RevokeInfo>
          <ExecuteInfo :project-id="route.params.id"></ExecuteInfo>
          <RevokeInfo :project-id="route.params.id" project-type="FINAL"></RevokeInfo>
          <RevokeInfo :project-id="route.params.id" project-type="OTHER"></RevokeInfo>
          <RevokeInfo :project-id="route.params.id" project-type="CLOSE_CASE"></RevokeInfo>
        </div>
        <div v-if="tabPosition == '4'">
          <vz-card title="付款信息">
            <Payment :project-id="route.params.id" :project-name="form.projectName"></Payment>
          </vz-card>
          <vz-card title="回款信息">
            <BackPayment :project-id="route.params.id" :project-name="form.projectName"></BackPayment>
          </vz-card>
        </div>
        <vz-card title="抵债资产" v-if="tabPosition == '5'">
          <AssetInfo :project-id="route.params.id"></AssetInfo>
        </vz-card>
        <vz-card title="审批流程" v-if="tabPosition == '6'">
          <WorkFlowInfo :project-id="route.params.id"></WorkFlowInfo>
        </vz-card>
        <vz-card id="part7" title="附件信息" v-if="tabPosition == '7'">
          <el-row :gutter="10">
            <el-col :span="3">
              <el-tabs tab-position="left" v-model="activeName" class="demo-tabs" @tab-click="tabClick">
                <el-tab-pane label="项目入库" name="PROJECT_INFO"></el-tab-pane>
                <el-tab-pane label="项目分配" name="ALLOCATION_INFO"></el-tab-pane>
                <el-tab-pane label="诉讼管理" name="RECOVERY_LITIGATION"></el-tab-pane>
                <el-tab-pane label="付款管理" name="RECOVERY_PAYMENT"></el-tab-pane>
                <el-tab-pane label="回款管理" name="RECOVERY_PAYMENT_COLLECTION"></el-tab-pane>
                <el-tab-pane label="项目核销" name="WRITE_OFF"></el-tab-pane>
                <el-tab-pane label="项目移交" name="PROJECT_TRANSFER"></el-tab-pane>
              </el-tabs>
            </el-col>
            <el-col :span="21">
              <file-table :table-data="proWorkList" :query-data="fileData" readonly></file-table>
            </el-col>
          </el-row>
        </vz-card>
        <vz-card id="part7" title="项目工作日志" v-if="tabPosition == '8'">
          <WorkInfo :project-id="route.params.id"></WorkInfo>
        </vz-card>
        <div v-if="tabPosition == '9'">
          <vz-card title="协议信息">
            <Contract :project-id="route.params.id"></Contract>
          </vz-card>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script setup lang="tsx" name="home">
import { nextTick, onMounted, ref } from "vue";
import { SubjectInfoRequest } from "@/api/modules/recovery/projectInfo/interface";
// import { bigSmallOptions } from "@/api/modules/recovery/projectInfoExt/interface";
import { documentTypeOptions, natureOptions } from "@/api/modules/source/subjectInfo/interface";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api";
import { useFileApi } from "@/api/modules/files/file";
import { useRoute } from "vue-router";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { securityTypeNatureOptions, businessTypeOptions } from "@/api/modules/recovery/projectInfo/interface";

//引入组件
import Contract from "@/views/source/lawFirmInfo/components/Contract.vue";
import VzSubject from "@/components/source/vzSubject.vue";
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import Payment from "@/views/source/lawFirmInfo/components/Payment.vue";
import BackPayment from "@/views/source/lawFirmInfo/components/BackPayment.vue";

import WorkInfo from "@/views/compensatory/projectInfo/component/WorkInfo.vue";
import WorkFlowInfo from "@/views/compensatory/projectInfo/component/WorkFlowInfo.vue";
import ProjectInfo from "@/views/compensatory/projectInfo/component/ProjectInfo.vue";
import AssetInfo from "@/views/compensatory/projectInfo/component/AssetInfo.vue";

// 诉讼信息组件
import Litigation from "@/views/compensatory/projectInfo/component/Litigation/index.vue"; //诉状
import RegisterInfo from "@/views/compensatory/projectInfo/component/RegisterInfo/index.vue"; //立案
import RevokeInfo from "@/views/compensatory/projectInfo/component/RevokeInfo/index.vue"; //撤诉
import ExecuteInfo from "@/views/compensatory/projectInfo/component/ExecuteInfo/index.vue"; //调解或审判信息
import TrialInfo from "@/views/compensatory/projectInfo/component/TrialInfo/index.vue";

const containerRef = ref<HTMLElement | null>(null);
const tabPosition = ref("1");
const productList = ref<any>([]);
const route = useRoute();

interface FileDataType {
  doIds?: any;
  doTypes?: any;
}
const fileData = ref<FileDataType>({ doTypes: ["PROJECT_INFO"] });
const activeName = ref("PROJECT_INFO");
const tabClick = (tab: any) => {
  getFileId(tab.paneName);
  if (typeof tab.paneName === "string" && tab.paneName == "RECOVERY_LITIGATION") {
    fileData.value.doTypes = [
      "RECOVERY_LITIGATION",
      "REGISTER",
      "DROP_LAWSUIT",
      "PRESERVATION",
      "FINAL",
      "OTHER",
      "CLOSE_CASE",
      "RECOVERY_ADJUST_TRIAL",
      "RECOVERY_EXECUTE",
      "RECOVERY_JUDGEMENT"
    ];
  } else if (typeof tab.paneName === "string") {
    fileData.value.doTypes = [tab.paneName];
  } else {
    fileData.value.doTypes = ["PROJECT_INFO"];
  }
};
const getFileId = async (fileType = "PROJECT_INFO") => {
  useFileApi()
    .getProjectFile({
      id: route.params.id,
      type: fileType
    })
    .then(res => {
      if (res.code == "0" && res.data) {
        fileData.value.doIds = res.data;
      }
    });
};

const form = ref<any>({
  subjectInfoRequest: {} as SubjectInfoRequest
});
const businessArray = ref<any>({
  data: [],
  header: [
    // {
    //   prop: "riskCompensation",
    //   label: "大类",
    //   width: "160",
    //   type: "select",
    //   isRequired: true,
    //   dictType: "TYPE"
    // },
    {
      prop: "relatedBusNo",
      label: "业务编码",
      width: "200",
      disabled: true
    },
    {
      prop: "productName",
      label: "产品名称",
      width: "250"
    },
    {
      prop: "aid",
      label: "A角",
      width: "160",
      type: "user"
      // , isRequired: true
    },
    {
      prop: "bid",
      label: "B角",
      width: "160",
      type: "user"
    },
    {
      prop: "businessType",
      label: "业务类型",
      width: "140",
      type: "select",
      enum: businessTypeOptions,
      isRequired: true
    },
    {
      prop: "dividedInsuranceAgainSecurity",
      label: "分险比例（再担保）",
      width: "160",
      type: "text",
      showWord: false,
      text: "%",
      max: 100
    },
    {
      prop: "dividedInsuranceDebtor",
      label: "分险比例（债权人）",
      width: "160",
      type: "text",
      showWord: false,
      text: "%",
      max: 100
    },
    {
      prop: "dividedInsuranceSecurity",
      label: "分险比例（原担保）",
      width: "160",
      type: "text",
      showWord: false,
      text: "%",
      max: 100
    },
    {
      prop: "dividedInsuranceOther",
      label: "分险比例（其他）",
      width: "160",
      type: "text",
      text: "%",
      max: 100,
      showWord: false
    },
    {
      prop: "riskCompensation",
      label: "风补基金",
      width: "160",
      type: "select",
      dictType: "RISK_FUND"
    }
    // {
    //   prop: "ristTypeAfterGuarantee",
    //   label: "风险归类",
    //   width: "110",
    //   type: "select",
    //   dictType: "RIST_TYPE_AFTER_GUARANTEE"
    // }
  ]
});
const projectLoanArray = ref<any>({
  data: [],
  header: [
    {
      prop: "relatedBusNo",
      label: "业务编码",
      width: "200"
    },
    {
      prop: "loanCode",
      label: "借据号",
      width: "160",
      type: "text"
    },
    {
      prop: "loanMoney",
      label: "银行放款金额(元)",
      width: "160",
      type: "money",
      showWord: false,
      text: "元",
      max: 999999999999
    },
    {
      prop: "debtBeginDate",
      label: "借款起始日",
      width: "160",
      type: "date"
    },
    {
      prop: "debtEndDate",
      label: "借款到期日",
      width: "160",
      type: "date"
    },
    {
      prop: "compensationMoney",
      label: "代偿金额(元)",
      width: "160",
      type: "money",
      isRequired: true,
      showWord: false,
      text: "元",
      max: 999999999999
    },
    {
      prop: "compensationDate",
      label: "代偿时间",
      width: "160",
      type: "date",
      isRequired: true
    },

    {
      prop: "cooperateBank",
      label: "合作银行",
      width: "250",
      type: "select",
      dictType: "COOPERATE_BANK"
    },
    {
      prop: "cooperateBankBranch",
      label: "合作银行支行",
      width: "300",
      type: "text"
    },
    {
      prop: "isFirstLoanAccount",
      label: "是否首次银行贷款",
      width: "160",
      type: "boolean",
      activeValue: "Y",
      inactiveValue: "N"
    },
    {
      prop: "loanRate",
      label: "贷款利率",
      width: "160",
      type: "text",
      showWord: false,
      text: "%",
      max: 100
    },
    {
      prop: "loanPactCode",
      label: "借款合同号",
      width: "160",
      type: "text"
    },
    {
      prop: "pledPactCode",
      label: "保证合同号",
      width: "160",
      type: "text"
    },
    {
      prop: "pactCode",
      label: "委保合同号",
      width: "160",
      type: "text"
    },
    {
      prop: "guaranteeRate",
      label: "担保费率",
      width: "160",
      type: "text",
      showWord: false,
      text: "%/年",
      max: 100
    }
  ]
});
const reveInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "securityWay",
      label: "担保方式",
      width: "110",
      isRequired: true,
      type: "select",
      dictType: "SECURITY_WAY",
      disabled: true
    },
    {
      prop: "securityType",
      label: "担保类型",
      width: "110",
      isRequired: true,
      type: "select",
      enum: securityTypeNatureOptions
    },
    {
      prop: "reveName",
      label: "反担保人名称",
      width: "150",
      isRequired: true,
      // type: "subject",
      disabled: true
    },
    {
      prop: "reveMeasure",
      label: "反担保措施",
      width: "200",
      type: "text",
      disabled: true
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean",
      disabled: true
    },
    {
      prop: "disposeMoney",
      label: "处置回款金额(元)",
      width: "160",
      type: "money",
      algin: "right",
      disabled: true
    },
    {
      prop: "preserveDate",
      label: "保全日期",
      width: "160",
      type: "date"
    },
    {
      prop: "remark",
      label: "备注",
      width: "200",
      type: "text"
    }
  ]
});
const propertyInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "reveMeasure",
      label: "其他财产线索",
      width: "150",
      isRequired: true,
      type: "text",
      disabled: true
    },
    {
      prop: "debtRepaymentDate",
      label: "裁定以资抵债日期",
      width: "160",
      type: "date",
      disabled: true
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额(元)",
      width: "160",
      type: "money",
      algin: "right",
      disabled: true
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean",
      disabled: true
    },
    {
      prop: "disposeMoney",
      label: "处置回款金(元)",
      width: "160",
      algin: "right",
      type: "money",
      disabled: true
    },
    {
      prop: "preserveDate",
      label: "保全日期",
      width: "160",
      type: "date"
    },
    {
      prop: "remark",
      label: "备注",
      width: "200",
      type: "text"
    }
  ]
});
const proWorkList = ref({
  data: [],
  header: [
    {
      prop: "reveMeasure",
      label: "类型",
      width: "150",
      isRequired: true,
      type: "text"
    },
    {
      prop: "debtRepaymentDate",
      label: "工作日期",
      width: "60",
      type: "date"
    },
    {
      prop: "debtRepaymentMoney",
      label: "工作内容",
      width: "60",
      type: "number"
    },
    {
      prop: "isDispose",
      label: "创建人",
      width: "60",
      type: "isBoolean"
    },
    {
      prop: "disposeMoney",
      label: "创建时间",
      width: "60",
      type: "number"
    },
    {
      prop: "disposeMoney",
      label: "附件",
      width: "60",
      type: "number"
    }
  ]
});
const charge = ref(0);
const findById = async () => {
  const id: any = route.params.id;
  if (!id) return;
  getFileId();
  const { data } = await projectInfo().findById(id);
  form.value = data;
  const compensationMoney = data.compensationMoney || 0;
  const collectionAmount = data.totalCollectionAmount || 0;
  charge.value = compensationMoney - collectionAmount;

  if (data.revePropertyInfoRequest && data.revePropertyInfoRequest.length > 0) {
    reveInfoList.value.data = data.revePropertyInfoRequest.filter(res => res.billType == "REVE");
    propertyInfoList.value.data = data.revePropertyInfoRequest.filter(res => res.billType == "PROPERTY");
  }
  if (data.businessInfos) {
    businessArray.value.data = data.businessInfos;
  }
  if (data.projectLoanInfos) {
    projectLoanArray.value.data = data.projectLoanInfos;
  }
};

const product = ref<any>("");
onMounted(() => {
  nextTick(() => {
    useBaseStore()
      .findProductList()
      .then(res => {
        productList.value = res;
      });
    findById();
    product.value = route.query.product;
  });
});
</script>

<style scoped lang="scss">
@import "./index.scss";
.valueClass {
  font-size: 10px;
  font-weight: bold;
  margin-top: 10px;
}
</style>
