import dict_date from "./components/common/dictDate.vue";
import dict_name from "./components/common/dictName.vue";
import dict_switch from "./components/common/dictSwitch.vue";
import dict_user_name from "./components/common/dictUserName.vue";
import dict_org_name from "./components/common/dictOrgName.vue";
import dict_enum from "./components/common/dictEnum.vue";
import dict_tenant from "./components/common/dictTenant.vue";
import dict_menu from "./components/common/dictMenu.vue";
import dict_version from "./components/common/dictVersion.vue";
import dict_area from "./components/common/dictArea.vue";
import display_text from "./components/common/DisplayText.vue";

import vz_select from "./components/common/vzSelect.vue";
import vz_user from "./components/common/vzUser.vue";
import vz_user_org from "./components/common/vzUserWithoutOrg.vue";
import vz_enum from "./components/common/vzEnum.vue";
import vz_tenant from "./components/common/vzTenantDialog.vue";
import vz_menu from "./components/common/vzMenu.vue";
import vz_org from "./components/common/vzOrg.vue";
import vz_version from "./components/common/vzVersionDialog.vue";
import vz_area from "./components/common/vzArea.vue";
import vz_upload from "./components/common/vzUpload.vue";
import vz_role from "./components/common/vzRole.vue";
import vz_money from "./components/common/vz_money_input.vue";

import vz_table from "./components/VzTable/index.vue";
import vz_login from "./components/vz-login/index.vue";
import vz_table_search from "./components/table/search.vue";
import vz_table_body from "./components/table/table.vue";
import vz_office_preview from "./components/VueOffice/index.vue";

import {
  ColumnProps,
  TypeProps,
  VzTableInstance,
} from "./components/VzTable/interface/index";

import {
  processStatusOptions,
  enabledOptions,
  versionTypeOptions,
  tenantTypeOptions,
  dataAuthorityOptions,
  dictTypeOptions,
  editAuthorityOptions,
  menuTypeOptions,
} from "./enums/commonOptions";

import { ProcessStatusEnum, OrderEnum } from "./enums/commonEnums";

import commonFunction from "./utils/commonFunction";
import initIconfont from "./utils/initIconfont";
import emitter from "./utils/emitter";
import regions from "./utils/regions";
import request from "./utils/request";
import setIntroduction from "./utils/setIntroduction";
import watermark from "./utils/watermark";
import platformStore from "./stores";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

//stores
import { useCacheInfo } from "./stores/baseInfo";
import { useCommonUserStore } from "./stores/modules/userStore";
import { useKeepAliveStore } from "./stores/modules/keepAlive";

export * from "./utils/arrayOperation";
// export * from "./utils/authFunction";
export * from "./utils/formatTime";
export * from "./utils/loading";
export * from "./utils/storage";
export * from "./utils/theme";
export * from "./utils/toolsValidate";
export * from "./utils/crypto";
export * from "./utils/keyManager";
export * from "pinia";

export default {
  install(Vue, options = {}) {
    Vue.component("dict-date", dict_date);
    Vue.component("dict-user-name", dict_user_name);
    Vue.component("dict-org-name", dict_org_name);
    Vue.component("dict-name", dict_name);
    Vue.component("dict-switch", dict_switch);
    Vue.component("dict-enum", dict_enum);
    Vue.component("dict-tenant", dict_tenant);
    Vue.component("dict-menu", dict_menu);
    Vue.component("dict-version", dict_version);
    Vue.component("dict-area", dict_area);
    Vue.component("display-text", display_text);

    Vue.component("vz-user", vz_user);
    Vue.component("vz-user-org", vz_user_org);
    Vue.component("vz-select", vz_select);
    Vue.component("vz-enum", vz_enum);
    Vue.component("vz-tenant", vz_tenant);
    Vue.component("vz-menu", vz_menu);
    Vue.component("vz-org", vz_org);
    Vue.component("vz-version", vz_version);
    Vue.component("vz-area", vz_area);
    Vue.component("vz-upload", vz_upload);
    Vue.component("vz-role", vz_role);
    Vue.component("vz-input-unit", vz_money);
    Vue.component("vz-login", vz_login);

    Vue.component("vz-table", vz_table);
    Vue.component("vz-table-search", vz_table_search);
    Vue.component("vz-table-body", vz_table_body);
    Vue.component("vz-office-preview", vz_office_preview);
  },
};

export {
  dict_date,
  dict_user_name,
  dict_org_name,
  dict_name,
  dict_switch,
  dict_enum,
  dict_tenant,
  dict_menu,
  dict_version,
  dict_area,
  vz_select,
  vz_user,
  vz_enum,
  vz_tenant,
  vz_menu,
  vz_org,
  vz_version,
  vz_area,
  vz_upload,
  vz_role,
  vz_table,
  vz_table_search,
  vz_table_body,
  vz_user_org,
  vz_office_preview,
  display_text,
  vz_login,
};

export {
  commonFunction,
  initIconfont,
  emitter,
  regions,
  request,
  setIntroduction,
  watermark,
  platformStore,
  piniaPluginPersistedstate,
  //状态
  useCacheInfo,
  useCommonUserStore,
  useKeepAliveStore,
  processStatusOptions,
  enabledOptions,
  versionTypeOptions,
  tenantTypeOptions,
  dataAuthorityOptions,
  dictTypeOptions,
  editAuthorityOptions,
  menuTypeOptions,
  //枚举
  ProcessStatusEnum,
  OrderEnum,
};

export type { ColumnProps, TypeProps, VzTableInstance };
