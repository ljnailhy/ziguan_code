<template>
  <div>
    <el-form
      ref="ruleFormRef"
      label-width="150px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="form"
      scroll-to-error
      :hide-required-asterisk="drawerProps.isView"
    >
      <vz-card title="基本信息">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="债务人姓名" prop="debtorId">
              <vz-subject
                v-model="form!.debtorId"
                :default-value="form!.debtorId"
                placeholder="债务人姓名"
                :disabled="drawerProps.title == '编辑'"
                @handle-ok="handleOk"
              ></vz-subject>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="债务人性质" prop="nature">
              <el-select
                v-model="form!.subjectInfoRequest.nature"
                :value="form!.subjectInfoRequest.nature"
                placeholder="请选择主体性质"
                :disabled="true"
              >
                <el-option key="E" label="企业" value="E" />
                <el-option key="NE" label="非企业经济组织" value="NE" />
                <el-option key="IB" label="个体工商户" value="IB" />
                <el-option key="SME" label="小微企业主" value="SME" />
                <el-option key="FM" label="农户" value="FM" />
                <el-option key="OTHER" label="农户" value="OTHER" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="证件类型" prop="documentType">
              <el-select
                v-model="form!.subjectInfoRequest.documentType"
                :value="form!.subjectInfoRequest.documentType"
                placeholder="请选择主体类型"
                :disabled="true"
              >
                <el-option key="SFZ" label="身份证" value="SFZ"></el-option>
                <el-option key="YYZZ" label="营业执照" value="YYZZ"></el-option>
                <el-option key="JGZ" label="军官证" value="JGZ"></el-option>
                <el-option key="SBZ" label="士兵证" value="SBZ"></el-option>
                <el-option key="HZ" label="护照" value="HZ"></el-option>
                <el-option key="JSZ" label="驾驶证" value="JSZ"></el-option>
                <el-option key="ZZZ" label="暂住证" value="ZZZ"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="证件号码" prop="documentNumber">
              <el-input
                v-model="form!.subjectInfoRequest.documentNumber"
                :value="form!.subjectInfoRequest.documentNumber"
                placeholder="请输入证件号码"
                clearable
                maxlength="30"
                :disabled="true"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="项目名称" prop="projectName">
              <el-input
                v-model="form!.projectName"
                :value="form!.projectName"
                maxlength="20"
                placeholder="请输入项目名称"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="法定代表人" prop="legalRepresentative">
              <el-input
                v-model="form!.subjectInfoRequest.legalRepresentative"
                placeholder="请输入法定代表人"
                clearable
                maxlength="20"
                :disabled="true"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系人" prop="contacts">
              <el-input
                v-model="form!.subjectInfoRequest.contacts"
                placeholder="请输入联系人"
                clearable
                maxlength="20"
                :disabled="true"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系人电话" prop="phone">
              <el-input
                v-model="form!.subjectInfoRequest.phone"
                placeholder="请输入联系人电话"
                clearable
                maxlength="20"
                :disabled="true"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="所属区域">
              <vz-area
                @change="changeBelongArea"
                :area="belongAreaArray"
                :level="1"
                :check-strictly="false"
                :disabled="true"
              ></vz-area>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="8" class="mb20">
            <el-form-item label="详细地址" prop="address">
              <el-input
                v-model="form!.subjectInfoRequest.address"
                placeholder="请输入详细地址"
                clearable
                :disabled="true"
                maxlength="25"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="8" class="mb20">
            <el-form-item label="行业分类" prop="industryType">
              <vz-select
                dict-type="INDUSTRY_GXB"
                v-model="form!.industryType"
                :dict-value="form!.industryType"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="企业划型" prop="nature">
              <vz-select
                dict-type="BIG_SMALL"
                v-model="form!.bigSmall"
                :dict-value="form!.bigSmall"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="政策扶持领域类别" prop="INDUSTRY_POLICY_SUPPORT">
              <vz-select
                dict-type="INDUSTRY_POLICY_SUPPORT"
                v-model="form!.goverType"
                :dict-value="form!.goverType"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="关联企业" prop="relationEnterprise">
              <el-input v-model="form!.relationEnterprise" placeholder="请输入关联企业" maxlength="50" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="关联企业信用代码" prop="relationEnterpriseNo">
              <el-input
                v-model="form!.relationEnterpriseNo"
                placeholder="请输入关联企业信用代码"
                maxlength="25"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="项目原所属组织" prop="primaryAffiliatedOrg">
              <vz-org v-model:orgValue="form.primaryAffiliatedOrg" :org="form.primaryAffiliatedOrg"></vz-org>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="是否历史代偿" prop="projectIsHistory">
              <el-switch v-model="form!.projectIsHistory" />
            </el-form-item>
          </el-col>
        </el-row>
      </vz-card>
      <!-- <vz-card title="详细信息">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="大类" prop="type">
              <vz-select
                dict-type="TYPE"
                @change="changeType"
                v-model="form!.type"
                :dict-value="form!.type"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="8" class="mb20">
            <el-form-item label="产品名称" prop="productName">
              <vz-select
                v-show="PRODUCT_NAME === 'OFF_LINE'"
                dict-type="OFF_LINE"
                v-model="form!.productName"
                :dict-value="form!.productName"
                style="width: 100%"
              ></vz-select>
              <vz-select
                v-show="PRODUCT_NAME === 'ON_LINE'"
                dict-type="ON_LINE"
                v-model="form!.productName"
                :dict-value="form!.productName"
                style="width: 100%"
              ></vz-select>
              <vz-select
                v-show="PRODUCT_NAME === 'TRADITIONAL_PRODUCT'"
                dict-type="TRADITIONAL_PRODUCT"
                v-model="form!.productName"
                :dict-value="form!.productName"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="8" class="mb20">
            <el-form-item label="合作银行" prop="cooperateBank">
              <vz-select
                dict-type="COOPERATE_BANK"
                v-model="form!.cooperateBank"
                :dict-value="form!.cooperateBank"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="合作银行（支行）" prop="cooperateBankBranch">
              <el-input v-model="form!.cooperateBankBranch" placeholder="请输入合作银行（支行）" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="8" class="mb20">
            <el-form-item label="行业分类" prop="industryType">
              <vz-select
                dict-type="INDUSTRY_GXB"
                v-model="form!.industryType"
                :dict-value="form!.industryType"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="企业划型" prop="nature">
              <vz-select
                dict-type="BIG_SMALL"
                v-model="form!.bigSmall"
                :dict-value="form!.bigSmall"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="政策扶持领域类别" prop="INDUSTRY_POLICY_SUPPORT">
              <vz-select
                dict-type="INDUSTRY_POLICY_SUPPORT"
                v-model="form!.goverType"
                :dict-value="form!.goverType"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="债权起始日期" prop="debtBeginDate">
              <el-date-picker v-model="form!.debtBeginDate" type="date" placeholder="请选择债权起始日" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="债权到期日" prop="debtEndDate">
              <el-date-picker v-model="form!.debtEndDate" type="date" placeholder="请选择债权到期日" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="银行放款金额(元)" prop="loanMoney">
              <vz-input-unit
                v-model="form!.loanMoney"
                :value="form!.loanMoney"
                placeholder="请输入银行放款金额"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="委保合同号" prop="pactCode">
              <el-input v-model="form!.pactCode" placeholder="请输入委保合同号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="借据号" prop="loanCode">
              <el-input v-model="form!.loanCode" placeholder="请输入借据号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="担保费率（年）" prop="guaranteeRate">
              <vz-input-unit
                v-model="form!.guaranteeRate"
                :value="form!.guaranteeRate"
                placeholder="请输入担保费率（年）"
                text="%"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="风补基金" prop="riskCompensation">
              <vz-select
                dict-type="RISK_FUND"
                v-model="form!.riskCompensation"
                :dict-value="form!.riskCompensation"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="A角" prop="aid">
              <vz-user style="width: 100%" v-model="form!.aid" :dict-value="form!.aid" clearable></vz-user>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="B角" prop="bid">
              <vz-user style="width: 100%" v-model="form!.bid" :dict-value="form!.bid" clearable></vz-user>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="借款合同号" prop="loanPactCode">
              <el-input v-model="form!.loanPactCode" placeholder="请输入借款合同号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="保证合同号" prop="pledPactCode">
              <el-input v-model="form!.pledPactCode" placeholder="请输入保证合同号" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </vz-card> -->
      <vz-card title="代偿信息">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="代偿金额(元)" prop="compensationMoney">
              <vz-input-unit
                v-model="form!.compensationMoney"
                :value="form!.compensationMoney"
                placeholder="请输入代偿金额"
                text="元"
                :max="999999999999"
                clearable
                disabled
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="代偿时间" prop="compensationDate">
              <el-date-picker v-model="form!.compensationDate" disabled type="date" placeholder="请选择代偿时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="诉讼时效" prop="proceedingAgeingDate">
              <el-date-picker v-model="form!.proceedingAgeingDate" type="date" placeholder="请选择诉讼时效" class="w100" />
              <!--  :disabled="true"   -->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="执行时效" prop="adjustTrialDate">
              <el-date-picker v-model="form!.adjustTrialDate" type="date" placeholder="请选择执行时效" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="移交至保全部日期" prop="transferDate">
              <el-date-picker v-model="form!.transferDate" type="date" placeholder="请选择移交至保全部日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="form!.remark"
                :rows="5"
                type="textarea"
                maxlength="500"
                show-word-limit
                placeholder="请输入备注"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <!--          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">-->
          <!--            <el-form-item label="代偿方案" prop="compensationPlan">-->
          <!--              <el-input-->
          <!--                v-model="form!.compensationPlan"-->
          <!--                type="textarea"-->
          <!--                show-word-limit-->
          <!--                :rows="5"-->
          <!--                placeholder="请输入代偿方案"-->
          <!--                clearable-->
          <!--                maxlength="500"-->
          <!--              ></el-input>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
        </el-row>
      </vz-card>
    </el-form>
    <vz-card title="业务信息">
      <form-table
        ref="businessRef"
        @on-select-change="changeUser"
        @money-input="testRate"
        @del-row="delRow"
        :table-data="businessArray"
        :readonly="drawerProps.isView"
      >
        <template #productName="scope">
          <el-select v-model="scope.row.productName" placeholder="请选择" filterable>
            <el-option v-for="item in productList" :key="item.value" :label="item.label" :value="item.value"> </el-option>
          </el-select>
        </template>
      </form-table>
    </vz-card>
    <vz-card title="借款信息">
      <form-table
        ref="projectLoanRef"
        @money-input="moneyInput"
        @del-row="moneyInput"
        @on-select-change="dateChange"
        :table-data="projectLoanArray"
        :readonly="drawerProps.isView"
      >
        <template #relatedBusNo="scope">
          <el-select v-model="scope.row.relatedBusNo" placeholder="请选择" filterable>
            <el-option
              v-for="item in businessArray.data"
              :key="item.relatedBusNo"
              :label="item.relatedBusNo"
              :value="item.relatedBusNo"
            >
            </el-option>
          </el-select>
        </template>
      </form-table>
    </vz-card>
    <vz-card title="反担保措施">
      <form-table
        ref="reveInfoRef"
        @on-select-change="onSelectChange"
        :table-data="reveInfoList"
        :readonly="drawerProps.isView"
      ></form-table>
    </vz-card>
    <vz-card title="其他财产信息">
      <form-table ref="propertyInfoRef" :table-data="propertyInfoList" :readonly="drawerProps.isView"></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table
        ref="fileRef"
        :readonly="drawerProps.isView"
        :query-data="{ doType: 'PROJECT_INFO', doId: form!.id }"
      ></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="tsx" name="projectInfoDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import {
  ProjectInfoExtRequestType,
  RevePropertyInfoRequestType,
  SubjectInfoRequest
} from "@/api/modules/recovery/projectInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { projectInfo } from "@/api/modules/recovery/projectInfo/api";
import VzSubject from "@/components/source/vzSubject.vue";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { deduplicateArraysById } from "@/utils";
import { securityTypeNatureOptions, businessTypeOptions } from "@/api/modules/recovery/projectInfo/interface";
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();
const belongAreaArray = ref<any>([]);
const businessArray = ref<any>({
  data: [],
  relatedBusNo: true,
  header: [
    {
      prop: "relatedBusNo",
      label: "业务编码",
      width: "200",
      disabled: true
    },
    {
      prop: "productName",
      label: "产品名称",
      width: "250",
      type: "select",
      isRequired: true
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
      type: "money",
      showWord: false,
      text: "%",
      max: 100
    },
    {
      prop: "dividedInsuranceDebtor",
      label: "分险比例（债权人）",
      width: "160",
      type: "money",
      showWord: false,
      text: "%",
      max: 100
    },
    {
      prop: "dividedInsuranceSecurity",
      label: "分险比例（原担保）",
      width: "160",
      type: "money",
      showWord: false,
      text: "%",
      max: 100
    },
    {
      prop: "dividedInsuranceOther",
      label: "分险比例（其他）",
      width: "160",
      type: "money",
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
      width: "200",
      isRequired: true
    },
    {
      prop: "loanCode",
      label: "借据号",
      width: "160",
      type: "text",
      maxlength: 30
    },
    {
      prop: "loanMoney",
      label: "银行放款金额(元)",
      width: "160",
      type: "money",
      showWord: false,
      text: "元",
      max: 99999999
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
      type: "text",
      maxlength: 150
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
      type: "money",
      showWord: false,
      text: "%",
      max: 100
    },
    {
      prop: "loanPactCode",
      label: "借款合同号",
      width: "160",
      type: "text",
      maxlength: 150
    },
    {
      prop: "pledPactCode",
      label: "保证合同号",
      width: "160",
      type: "text",
      maxlength: 150
    },
    {
      prop: "pactCode",
      label: "委保合同号",
      width: "160",
      type: "text",
      maxlength: 150
    },
    {
      prop: "guaranteeRate",
      label: "担保费率",
      width: "160",
      type: "money",
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
      type: "text",
      dictType: "SECURITY_WAY"
    },
    {
      prop: "securityType",
      label: "担保类型",
      width: "110",
      type: "select",
      enum: securityTypeNatureOptions
    },
    {
      prop: "reveName",
      label: "反担保人名称",
      width: "150",
      maxlength: 50
    },
    {
      prop: "reveMeasure",
      label: "反担保措施",
      width: "220",
      type: "textarea",
      autosize: true,
      isRequired: true,
      maxlength: 500
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "60",
      type: "boolean"
    },
    {
      prop: "disposeMoney",
      label: "处置回款金额(元)",
      width: "120",
      text: "元",
      type: "money",
      max: 99999999
    },
    {
      prop: "remark",
      label: "备注",
      width: "100",
      type: "textarea",
      autosize: true,
      maxlength: 255
    }
  ]
});

const propertyInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "reveMeasure",
      label: "其他财产线索",
      width: "220",
      isRequired: true,
      type: "textarea",
      autosize: true,
      maxlength: 500
    },
    {
      prop: "debtRepaymentDate",
      label: "裁定以资抵债日期",
      width: "90",
      type: "date"
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额",
      width: "90",
      type: "money",
      text: "元",
      max: 99999999
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "60",
      type: "boolean"
    },
    {
      prop: "disposeMoney",
      label: "处置回款金额(元)",
      width: "90",
      type: "money",
      text: "元",
      max: 99999999
    },
    {
      prop: "remark",
      label: "备注",
      width: "100",
      type: "textarea",
      autosize: true,
      maxlength: 255
    }
  ]
});
//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);
const form = ref<any>({
  /** 是否移交 */
  isTransfer: false,
  /** 是否核销 */
  isWriteOff: false,
  /** 是否删除 */
  isDelete: false,
  id: undefined,
  type: 562825782341701,
  projectInfoExtRequest: {} as ProjectInfoExtRequestType,
  revePropertyInfoRequest: [] as RevePropertyInfoRequestType[],
  subjectInfoRequest: {} as SubjectInfoRequest,
  businessInfos: [],
  projectLoanInfos: []
});
const rules = reactive({
  debtorId: [{ required: true, message: "债务人姓名不能为空" }],
  industryType: [{ required: true, message: "请选择行业分类!", trigger: "change" }],
  projectName: [{ required: true, message: "请输入项目名称!", trigger: "change" }]
});

const onSelectChange = (val: any, index: number, item: EmptyObjectType, row: EmptyObjectType) => {
  if ("subject" == item.type) {
    row[item.prop] = val.id;
  }
};

//	省市区变化赋值
const changeBelongArea = val => {
  if (val[0]) {
    form.value.subjectInfoRequest.belongProvince = val[0];
    if (val[1]) {
      form.value.subjectInfoRequest.belongCity = val[1];
      if (val[2]) {
        form.value.subjectInfoRequest.belongDistrict = val[2];
      }
    }
  }
};

// 单个查找
const bakbusinessArray = ref([]);
const bakprojectLoanArray = ref([]);
const bakrevePropertyInfoRequest = ref([]);
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await projectInfo().findById(drawerProps.value.id);
  form.value = data;
  if (data.revePropertyInfoRequest && data.revePropertyInfoRequest.length > 0) {
    reveInfoList.value.data = data.revePropertyInfoRequest.filter(res => res.billType == "REVE");
    propertyInfoList.value.data = data.revePropertyInfoRequest.filter(res => res.billType == "PROPERTY");
    bakrevePropertyInfoRequest.value = JSON.parse(JSON.stringify(data.revePropertyInfoRequest));
  }
  if (data.businessInfos) {
    businessArray.value.data = data.businessInfos;
    bakbusinessArray.value = JSON.parse(JSON.stringify(data.businessInfos));
  }

  if (data.projectLoanInfos) {
    projectLoanArray.value.data = data.projectLoanInfos;
    bakprojectLoanArray.value = JSON.parse(JSON.stringify(data.projectLoanInfos));
  }
  belongAreaArray.value = [
    data.subjectInfoRequest.belongProvince,
    data.subjectInfoRequest.belongCity,
    data.subjectInfoRequest.belongDistrict
  ];
};

const dateChange = () => {
  form.value.compensationDate = getEarliestDate(projectLoanArray.value.data.map(item => item.compensationDate));
};

const changeUser = (val, index, item, row) => {
  if (item.prop == "aid" || item.prop == "bid") {
    if (row["aid"] == row["bid"]) {
      row[item.prop] = null;
      return ElMessage.warning("A B角不能为同一人");
    }
  }
};
//校验分险比例
const testRate = (val: number, index: number, item: any, row: EmptyObjectType) => {
  if (
    item.prop == "dividedInsuranceAgainSecurity" ||
    item.prop == "dividedInsuranceDebtor" ||
    item.prop == "dividedInsuranceSecurity" ||
    item.prop == "dividedInsuranceOther"
  ) {
    const values = {
      dividedInsuranceAgainSecurity: +row.dividedInsuranceAgainSecurity || 0,
      dividedInsuranceDebtor: +row.dividedInsuranceDebtor || 0,
      dividedInsuranceSecurity: +row.dividedInsuranceSecurity || 0,
      dividedInsuranceOther: +row.dividedInsuranceOther || 0
    };

    const total =
      values.dividedInsuranceAgainSecurity +
      values.dividedInsuranceDebtor +
      values.dividedInsuranceSecurity +
      values.dividedInsuranceOther;

    const targetTotal = 100;
    const difference = total - targetTotal;

    if (difference !== 0) {
      if (total > targetTotal) {
        row[item.prop] = values[item.prop] - difference;
        ElMessage.warning("分险比例合计不能超过100");
      } else if (
        values.dividedInsuranceAgainSecurity &&
        values.dividedInsuranceDebtor &&
        values.dividedInsuranceSecurity &&
        values.dividedInsuranceOther
      ) {
        row[item.prop] = values[item.prop] + (targetTotal - total);
        ElMessage.warning("分险比例合计必须等于100");
      }
    }
  }
};
const delRow = (row: any) => {
  // 检查数组是否为空
  if (projectLoanArray.value.data.length === 0) {
    console.log("数组为空，无法删除");
    return;
  }

  // 使用 filter 方法来删除元素
  projectLoanArray.value.data = projectLoanArray.value.data.filter((element: any) => {
    // 检查要删除的元素
    return element.relatedBusNo !== row.relatedBusNo;
  });
};
const moneyInput = () => {
  form.value!.compensationMoney = countFun();
};

const getEarliestDate = dates => {
  return dates.reduce((earliest, current) => {
    const currentDate = new Date(current);
    return currentDate < earliest ? currentDate : earliest;
  }, new Date(dates[0])); // 初始化为数组中的第一个日期
};

// 计算分录总数
const countFun = () => {
  const assetMoneyData = JSON.parse(JSON.stringify(projectLoanArray.value.data));
  const totalMoney = assetMoneyData.reduce((accumulator, currentValue) => {
    const money = parseFloat(currentValue.compensationMoney);
    if (!isNaN(money)) {
      return accumulator + money;
    } else {
      return accumulator;
    }
  }, 0);
  return totalMoney;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const businessRef = ref();
const projectLoanRef = ref();
const reveInfoRef = ref();
const propertyInfoRef = ref();

const handleSave = async () => {
  const formEl = businessRef.value?.tableRulesRef;
  const formEl1 = projectLoanRef.value?.tableRulesRef;
  const formEl2 = reveInfoRef.value?.tableRulesRef;
  const formEl3 = propertyInfoRef.value?.tableRulesRef;
  const fileFormEl = fileRef.value?.tableRulesRef;

  if (countFun() > 999999999999) {
    return ElMessage.warning("代偿金额有误，请重新输入！");
  }
  try {
    const formsToValidate = [ruleFormRef.value, formEl, formEl1, formEl2, formEl3, fileFormEl];

    for (const form of formsToValidate) {
      if (form && typeof form.validate === "function") {
        await form.validate();
      }
    }
    const isProductName = businessArray.value.data
      .map(item => item.productName)
      .some(element => element === "" || element === null || element === undefined);
    if (businessArray.value.data.length <= 0) {
      ElMessage.warning("请完善业务信息表");
      return false;
    }
    if (isProductName) {
      ElMessage.warning("【业务信息表】中产品名称为必选！");
      return false;
    }
    const isrelatedBusNo = projectLoanArray.value.data
      .map(item => item.relatedBusNo)
      .some(element => element === "" || element === null || element === undefined);
    if (isrelatedBusNo) {
      ElMessage.warning("【借款信息表】中业务编号为必选！");
      return false;
    }
    if (projectLoanArray.value.data.length <= 0) {
      ElMessage.warning("请完善借款信息");
      return false;
    }

    form.value.fileRequests = fileRef.value.submitForm;
    form.value.businessInfos = deduplicateArraysById(bakbusinessArray.value, businessArray.value!.data);
    form.value.projectLoanInfos = deduplicateArraysById(bakprojectLoanArray.value, projectLoanArray.value!.data);

    let newArr = propertyInfoList.value.data.map(obj => {
      return { ...obj, billType: "PROPERTY" };
    });
    let newArr1 = reveInfoList.value.data.map(obj => {
      return { ...obj, billType: "REVE" };
    });
    form.value.revePropertyInfoRequest = deduplicateArraysById(bakrevePropertyInfoRequest.value, [...newArr, ...newArr1]);
    await drawerProps.value.api!(form.value);
    ElMessage.success({ message: `${drawerProps.value.title}项目信息成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

// 提交数据（流程）
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}项目信息成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

const handleOk = (obj: any) => {
  form.value.debtorId = obj.id;
  form.value.subjectInfoRequest = {
    ...obj,
    projectName: obj.subjectName
  };
  form.value.projectName = obj.subjectName;
  belongAreaArray.value = [obj.belongProvince, obj.belongCity, obj.belongDistrict];
};

//产品名称
const productList = ref<any>([]);
onMounted(() => {
  useBaseStore()
    .findProductList()
    .then(res => {
      productList.value = res;
    });
  findById();
});

defineExpose({
  handleSave,
  handleSubmit
});
</script>

<style scoped lang="scss">
@import "index";
</style>
