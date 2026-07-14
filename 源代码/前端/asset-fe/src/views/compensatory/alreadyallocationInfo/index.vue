<template>
  <div class="table-box">
    <vz-table
      ref="vzTableRef"
      @rowdouble-click="rowdoubleClick"
      @row-click="rowClick"
      @reset="reset"
      :columns="columns"
      :request-api="getTableList"
      :init-param="initParam"
    >
      <template #tableHeader>
        <el-button type="primary" v-if="isShowBtn" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
        <el-button type="primary" v-if="isShowBtn" v-auth="'import'" @click="importApi()">导入</el-button>
        <el-button type="primary" v-if="isShowBtn" v-auth="'sync'" @click="syncCompensatory()">代偿项目同步</el-button>
      </template>
      <template #compensationDate="scope">
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
      <template #lawyer="scope">
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
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button type="primary" v-auth="'edit'" link @click="openDrawer('编辑', scope.row)">编辑</el-button>
        <el-dropdown
          v-if="BUTTONS.delete || BUTTONS.workRegister || BUTTONS.changeContract || BUTTONS.changeLawyer || BUTTONS.changeManage"
        >
          <el-button type="primary" link style="margin-left: 12px"> 更多 </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item
                v-if="BUTTONS.delete && !projectState.includes(scope.row.projectState)"
                @click="deleteAccount(scope.row)"
              >
                删除
              </el-dropdown-item>
              <el-dropdown-item v-if="BUTTONS.workRegister" @click="workRegister(scope.row)">工作登记</el-dropdown-item>
              <el-dropdown-item
                v-if="BUTTONS.changeContract && projectState.includes(scope.row.projectState)"
                @click="changeContract(scope.row)"
              >
                合同变更
              </el-dropdown-item>
              <el-dropdown-item
                v-if="BUTTONS.changeLawyer && projectState.includes(scope.row.projectState)"
                @click="changeLawyer(scope.row)"
              >
                律师变更
              </el-dropdown-item>
              <el-dropdown-item
                v-if="BUTTONS.changeManage && projectState.includes(scope.row.projectState)"
                @click="changeManage(scope.row)"
              >
                保全经理变更
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
    <ImportExcel ref="importRef" />
    <vzWork ref="vzWorkRef" v-show="false"></vzWork>
    <zcProperty ref="zcPropertyRef" v-show="false"></zcProperty>
    <zcChangeContract ref="zcChangeContractRef" v-show="false"></zcChangeContract>
    <zcChangeLawyer ref="zcChangeLawyerRef" v-show="false"></zcChangeLawyer>
    <zcChangeManage ref="zcChangeManageRef" v-show="false"></zcChangeManage>
    <el-dialog v-model="dialogVisible" title="同步代偿项目" width="80%">
      <div class="table-box">
        <vz-table ref="ayncDataRef" @rowdouble-click="rowdoubleClick" :columns="columnsSync" :request-api="getSyncTableList">
          <template #industryPolicySupport="scope">
            <div v-if="scope.row.industryPolicySupport" class="flx">
              <div v-for="(item, index) in scope.row.industryPolicySupport.split(',')" :key="index">
                <dict-name dict-type="INDUSTRY_POLICY_SUPPORT" :dict-value="item"></dict-name>
                <span v-if="scope.row.industryPolicySupport.split(',').length - 1 !== index">、</span>
              </div>
            </div>
            <div v-else>--</div>
          </template>
          <template #productName="scope">
            <div v-if="scope.row.productName">
              <div v-for="(item, index) in scope.row.productName.split(',')" :key="index">
                <dict-enum :options="productList" :value="item"></dict-enum>
                <span v-if="scope.row.productName.split(',').length - 1 !== index">、</span>
              </div>
            </div>
          </template>
        </vz-table>
      </div>

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
import { VzTableInstance, ColumnProps, TypeProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import {
  ProjectInfoPageRequest,
  ProjectInfoDTO,
  SyncRequest,
  businessTypeOptions
} from "@/api/modules/recovery/projectInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { useRouter, useRoute } from "vue-router";
import { bigSmallOptions } from "@/api/modules/recovery/projectInfoExt/interface";
import { documentTypeOptions, natureOptions } from "@/api/modules/source/subjectInfo/interface";

//引入组件
import vzWork from "@/views/compensatory/workRegister/vzWork.vue";
import ImportExcel from "@/components/ImportExcel/index.vue";
import vzLawyerInfo from "@/components/source/vzLawyerInfo.vue";
import vzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";
import zcChangeContract from "@/views/compensatory/projectInfo/changeContract.vue";
import zcChangeLawyer from "@/views/compensatory/projectInfo/changeLawyer.vue";
import zcProperty from "@/views/compensatory/revePropertyInfo/zcProperty.vue";
import zcChangeManage from "@/views/compensatory/projectInfo/changeManage.vue";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
const router = useRouter();
const route = useRoute();
const vzWorkRef = ref();
const zcPropertyRef = ref();
const zcChangeContractRef = ref();
const zcChangeLawyerRef = ref();
const zcChangeManageRef = ref();
// const contractFilterData = ref();
// const lawyerFilterData = ref();
const dialogVisible = ref(false);
// 按钮权限
const { BUTTONS } = useAuthButtons();
const emit = defineEmits<{
  rowdoubleClick: [{ row?: any; column?: any; event?: Event }];
  rowClick: [{ row?: any; column?: any; event?: Event }];
}>();
const rowdoubleClick = (row: any) => {
  emit("rowdoubleClick", row);
};

const rowClick = (row: any) => {
  vzTableRef.value!.radio = row.id;
  emit("rowClick", row);
};

const ayncDataRef = ref();
const ayncData = () => {
  if (ayncDataRef.value.selectedList.length == 0) {
    return ElMessage.warning("请至少勾选一条");
  }
  let ArrData: string[] = [];
  for (let i = 0; i < ayncDataRef.value.selectedList.length; i++) {
    ArrData.push(ayncDataRef.value.selectedList[i].credentialNo);
  }
  return projectInfo()
    .syncCompensatory(ArrData)
    .then((res: EmptyObjectType) => {
      if (res.code == 0) {
        ElMessage.success({ message: `同步成功！` });
        vzTableRef.value?.getTableList();
        dialogVisible.value = false;
      } else {
        ElMessage.warning(res.msg);
      }
    });
};

interface ProjectPropsType {
  filterData?: { [key: string]: any };
  isShowBtn?: boolean;
  selectType?: TypeProps;
}

const props = withDefaults(defineProps<ProjectPropsType>(), {
  filterData: () => ({}),
  isShowBtn: true,
  selectType: "selection"
});

// 表格配置项
const columns = reactive<ColumnProps<ProjectInfoDTO>[]>([
  { type: props.selectType, fixed: "left", width: 70, isShow: !props.isShowBtn },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 200,
    align: "left",
    headerAlign: "center",
    search: { el: "input" }
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
  //   label: "代偿信息",
  //   minWidth: 200
  // },
  {
    // type: "date",
    prop: "compensationDate",
    label: "代偿信息",
    minWidth: 200,
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
  {
    prop: "documentType",
    label: "证件信息",
    minWidth: 250,
    enum: documentTypeOptions
  },
  // {
  //   prop: "documentNumber",
  //   label: "证件号",
  //   minWidth: 200
  // },
  {
    prop: "industryType",
    label: "行业分类",
    minWidth: 200,
    search: { el: "select", props: { filterable: true } },
    enum: () => useBaseStore().findEnumByName("INDUSTRY_GXB"),
    isShow: false
  },
  // {
  //   type: "date",
  //   prop: "transferDate",
  //   label: "移交至保全部日期",
  //   isShow: false,
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
  //   prop: "name",
  //   label: "律所",
  //   minWidth: 120
  // },
  {
    prop: "lawyer",
    label: "律所&保全经理",
    minWidth: 250
  },
  // {
  //   prop: "manage",
  //   label: "保全经理",
  //   width: "200",
  //   type: "user"
  // },
  {
    prop: "creator",
    label: "创建信息",
    minWidth: 250
  },
  {
    prop: "createStamp",
    label: "创建时间",
    minWidth: 180,
    isShow: false,
    search: {
      key: "createStampRange", //指定搜索的key
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
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 180,
    isShow:
      ((BUTTONS.value.edit !== undefined && BUTTONS.value.edit) ||
        (BUTTONS.value.detail !== undefined && BUTTONS.value.detail) ||
        (BUTTONS.value.delete !== undefined && BUTTONS.value.delete)) &&
      props.isShowBtn
  }
]);

// 同步表格配置
const columnsSync = reactive<ColumnProps<SyncRequest>[]>([
  { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "customerName",
    label: "债务人名称",
    minWidth: 260,
    search: { el: "input", props: { filterable: true } }
  },
  {
    prop: "productName",
    label: "产品名称",
    minWidth: 250,
    search: { el: "select", props: { filterable: true } },
    enum: () => useBaseStore().findProductList()
  },
  {
    prop: "businessType",
    label: "业务类型",
    minWidth: 120,
    search: { el: "select", props: { filterable: true } },
    enum: businessTypeOptions
  },
  {
    prop: "repaymentAmount",
    label: "代偿金额(元)",
    minWidth: 200
  },
  {
    prop: "repaymentDate",
    label: "代偿时间",
    minWidth: 120,
    type: "date",
    search: {
      key: "repaymentDateRange", //指定搜索的key
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
  {
    prop: "customerProperty",
    label: "债务人性质",
    minWidth: 120,
    enum: natureOptions,
    search: { el: "select", props: { filterable: true } }
  },
  {
    prop: "credentialType",
    label: "证件类型",
    minWidth: 90,
    search: { el: "select", props: { filterable: true } },
    enum: documentTypeOptions
  },
  {
    prop: "credentialNo",
    label: "证件号码",
    minWidth: 200,
    search: { el: "input", props: { filterable: true } }
  },
  // {
  //   prop: "contact",
  //   label: "联系人",
  //   minWidth: 120
  // },
  // {
  //   prop: "tel",
  //   label: "联系人电话",
  //   minWidth: 120
  // },

  {
    prop: "province",
    label: "省",
    type: "area",
    minWidth: 120
  },
  {
    prop: "city",
    label: "市",
    type: "area",
    minWidth: 120
  },
  {
    prop: "area",
    label: "区",
    type: "area",
    minWidth: 120
  },
  {
    prop: "industryGxb",
    label: "行业分类",
    minWidth: 120,
    enum: () => useBaseStore().findEnumByName("INDUSTRY_GXB")
  },
  {
    prop: "enterpriseSize",
    label: "企业划型",
    minWidth: 120,
    enum: bigSmallOptions
  },
  {
    prop: "industryPolicySupport",
    label: "政策扶持领域",
    minWidth: 160,
    enum: () => useBaseStore().findEnumByName("INDUSTRY_POLICY_SUPPORT"),
    search: { el: "select", props: { filterable: true } }
  }
  // ,
  // {
  //   prop: "projectFrom",
  //   label: "项目来源",
  //   enum: bigSmallOptions,
  //   minWidth: 120
  // }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({ ...props.filterData });

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const projectState = ref();
const getTableList = async (params: ProjectInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;

  if (useDialogStore().projectIds) {
    newParams.idList = useDialogStore().projectIds;
  }
  const projectStateList = await useBaseStore().findEnumByName("PROJECT_STATE");
  const state = (projectState.value = projectStateList
    .filter(item => item.itemCode != "PROJECT_STATE_01") // 筛选条件
    .map(item => item.id));
  projectState.value = state;
  if (route.name == "proceedingProjectInfo") {
    const merged = Object.assign({}, newParams, {
      projectStateList: state
    });
    return projectInfo().findLimitsAll(merged);
  }
  return projectInfo().findAll(newParams);
};
const reset = () => {
  useDialogStore().setProjectIds([]);
  vzTableRef.value?.getTableList();
};

const getSyncTableList = (params: SyncRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return projectInfo().findCompensatorySyncAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: ProjectInfoDTO) => {
  await useHandleData(projectInfo().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};
// 工作登记
const workRegister = async (params: ProjectInfoDTO) => {
  vzWorkRef.value.handleFocus("PROJECT_INFO", params.id);
};

// 财产登记
// const propertyRegister = async (params: ProjectInfoDTO) => {
//   zcPropertyRef.value.handleFocus("PROJECT_INFO", params.id);
// };
//导入
const importRef = ref();
const importApi = () => {
  let params = {
    title: "数据",
    // tempApi: exportUserInfo,
    importApi: projectInfo().importData,
    getTableList: vzTableRef.value?.getTableList
  };
  importRef.value.acceptParams(params);
};
// 合同变更
const changeContract = async (params: ProjectInfoDTO) => {
  //  查询类型是合同类型并且是代理合同
  // useBaseStore()
  //   .findEnumByName("CONTRACT_TYPE")
  //   .then((res: EmptyObjectType) => {
  //     for (let i = 0; i < res.length; i++) {
  //       if ("CONTRACT_TYPE_001" == res[i].itemCode) {
  //         contractFilterData.value = { contractType: res[i].id };
  //       }
  //     }
  zcChangeContractRef.value.handleFocus(params);
  // if (params.contractId) {
  //   zcChangeContractRef.value.handleFocus(params);
  // } else {
  //   ElMessage.warning("请先分配合同");
  // }
  // });
};
// 律师变更
const changeLawyer = async (params: ProjectInfoDTO) => {
  //  判断当前项目是否存在律所
  if (params.lawFirmId) {
    // lawyerFilterData.value = { lawFirmId: params.lawFirmId };
    zcChangeLawyerRef.value.handleFocus(params);
  } else {
    ElMessage.warning("请先分配律所");
  }
};
// 保全经理变更
const changeManage = async (params: ProjectInfoDTO) => {
  zcChangeManageRef.value.handleFocus(params);
};
// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const openDrawer = (title: string, row: any = {}) => {
  if (title === "查看") {
    router.replace({
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
    showBtn: false,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? projectInfo().add : title === "编辑" ? projectInfo().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};

const syncCompensatory = () => {
  dialogVisible.value = true;
};

const productList = ref();
onMounted(() => {
  useBaseStore()
    .findProductList()
    .then(res => {
      productList.value = res;
    });
});
// 暴露给父组件使用
defineExpose({ vzTableRef });
</script>

<style scoped lang="scss"></style>
