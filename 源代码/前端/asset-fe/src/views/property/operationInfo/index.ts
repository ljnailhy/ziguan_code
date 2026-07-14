import { reactive } from "vue";

//校验
export const rules = reactive({
  operationType: [{ required: true, message: "请选择运营类型", trigger: "change" }],
  operationDate: [{ required: true, message: "请选择运营时间", trigger: "change" }],
  operationContent: [{ required: true, message: "请选择运营内容", trigger: "blur" }],
  operationTitle: [{ required: true, message: "请选择运营标题", trigger: "blur" }]
});
