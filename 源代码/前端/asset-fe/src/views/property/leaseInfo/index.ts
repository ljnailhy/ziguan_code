import { reactive } from "vue";

//校验规则
export const rules = reactive({
  estimateId: [
    {
      required: true,
      message: "请选择评估机构",
      trigger: "change"
    }
  ],
  agencyFee: [
    {
      required: true,
      message: "请输入代理费用",
      trigger: "blur"
    }
  ],
  estimateMoney: [
    {
      required: true,
      message: "请输入评估价",
      trigger: "blur"
    }
  ],
  estimateDate: [
    {
      required: true,
      message: "请选择评估日期",
      trigger: "change"
    }
  ],
  disposeProgramme: [
    {
      required: false,
      message: "请输入处置方案",
      trigger: "blur"
    }
  ]
});
