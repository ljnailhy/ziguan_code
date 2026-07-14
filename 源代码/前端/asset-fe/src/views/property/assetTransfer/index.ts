import { reactive } from "vue";

export const rules = reactive({
  estimateId: [{ required: true, trigger: "change", message: "请选择评估机构" }],
  estimateMoney: [{ required: true, trigger: "blur", message: "请输入评估价格" }],
  programme: [{ required: false, trigger: "blur", message: "请输入处置方案" }],
  estimateDate: [{ required: true, trigger: "change", message: "请选择评估日期" }]
});
