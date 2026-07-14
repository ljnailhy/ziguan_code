/**
 * 流程状态下拉选择项
 * @author Yuqiang Wu
 * @since 2024-07-11 10:13:14
 */
export const processStatus = [
  { label: "待提交", value: "draft", tagType: "info" },
  { label: "审批中", value: "running", tagType: "primary" },
  { label: "已审批", value: "completed", tagType: "success" },
  { label: "已终止", value: "terminated", tagType: "danger" },
  { label: "已取消", value: "canceled", tagType: "warning" }
];
