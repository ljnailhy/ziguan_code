/**
 * 流程状态下拉选择项
 * @author Yuqiang Wu
 * @since 2024-07-11 10:13:14
 */
export const processStatusOptions = [
  { label: "待提交", value: "draft", tagType: "info" },
  { label: "审批中", value: "running", tagType: "primary" },
  { label: "已审批", value: "completed", tagType: "success" },
  { label: "已终止", value: "terminated", tagType: "danger" },
  { label: "已取消", value: "canceled", tagType: "warning" },
];

/**
 * 是否启用下拉选择项
 * @author Cai
 * @since 2024-07-11 10:13:14
 */
export const enabledOptions = [
  { label: "是", value: true, tagType: "success" },
  { label: "否", value: false, tagType: "danger" },
];

/**
 * 性别下拉选择项
 * @author Cai
 * @since 2024-07-11 10:13:14
 */
export const sexOptions = [
  { label: "保密", value: "UNKNOW", tagType: "info" },
  { label: "男", value: "MALE", tagType: "primary" },
  { label: "女", value: "FEMALE", tagType: "warning" },
];

/**
 * 版本类型下拉选择项
 * @author Cai
 * @since 2024-07-11 10:13:14
 */
export const versionTypeOptions = [
  { label: "商业版", value: "SALE", tagType: "warning" },
  { label: "试用版", value: "TRIAL", tagType: "info" },
  { label: "自定义", value: "CUSTOM", tagType: "primary" },
];

/**
 * 版本类型下拉选择项
 * @author Cai
 * @since 2024-07-11 10:13:14
 */
export const tenantTypeOptions = [
  { label: "系统", value: "SYSTEM", tagType: "primary" },
  { label: "普通", value: "NORMAL", tagType: "info" },
];

/**
 * 字典类型下拉选择项
 * @author Cai
 * @since 2024-07-11 10:13:14
 */
export const dictTypeOptions = [
  { label: "系统", value: "SYSTEM", tagType: "primary" },
  { label: "业务", value: "BUSINESS", tagType: "info" },
];

/**
 * 数据权限下拉选择项
 * @author Cai
 * @since 2024-07-11 10:13:14
 */
export const dataAuthorityOptions = [
  { label: "仅本人数据权限", value: "USER", tagType: "primary" },
  { label: "本部门数据权限", value: "DEPT", tagType: "warning" },
  { label: "全部数据权限", value: "ALL", tagType: "success" },
  { label: "自定义数据权限", value: "CUSTOM", tagType: "info" },
];

/**
 * 编辑权限下拉选择项
 * @author Cai
 * @since 2024-07-11 10:13:14
 */
export const editAuthorityOptions = [
  { label: "仅本人相关", value: "USER", tagType: "primary" },
  { label: "全部", value: "ALL", tagType: "success" },
];

/**
 * 编辑权限下拉选择项
 * @author Cai
 * @since 2024-07-11 10:13:14
 */
export const menuTypeOptions = [
  { label: "系统", value: "SYSTEM", tagType: "primary" },
  { label: "菜单", value: "MENU", tagType: "success" },
  { label: "操作", value: "OPERATION", tagType: "warning" },
  { label: "其他", value: "OTHER", tagType: "info" },
];
