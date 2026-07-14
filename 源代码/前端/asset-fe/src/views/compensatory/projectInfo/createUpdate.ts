import { ref } from "vue";
import { securityTypeNatureOptions, businessTypeOptions } from "@/api/modules/recovery/projectInfo/interface";

export const belongAreaArray = ref<any>([]);
export const businessArray = ref<any>({
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
      type: "user",
      isRequired: true
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

export const projectLoanArray = ref<any>({
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
      maxlength: 50
    },
    {
      prop: "loanMoney",
      label: "银行放款金额",
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
      label: "代偿金额",
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

export const reveInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "securityWay",
      label: "担保方式",
      width: "110",
      isRequired: true,
      type: "text",
      dictType: "SECURITY_WAY"
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
      maxlength: 50
    },
    {
      prop: "reveMeasure",
      label: "反担保措施",
      width: "200",
      type: "text",
      maxlength: 500
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
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
      width: "120",
      type: "text",
      maxlength: 255
    }
  ]
});

export const propertyInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "reveMeasure",
      label: "其他财产线索",
      width: "150",
      isRequired: true,
      type: "text",
      maxlength: 500
    },
    {
      prop: "debtRepaymentDate",
      label: "裁定以资抵债日期",
      width: "120",
      type: "date"
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额",
      width: "120",
      type: "money",
      text: "元",
      max: 99999999
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean"
    },
    {
      prop: "disposeMoney",
      label: "处置回款金额(元)",
      width: "120",
      type: "money",
      text: "元",
      max: 99999999
    },
    {
      prop: "remark",
      label: "备注",
      width: "120",
      type: "text",
      maxlength: 255
    }
  ]
});
