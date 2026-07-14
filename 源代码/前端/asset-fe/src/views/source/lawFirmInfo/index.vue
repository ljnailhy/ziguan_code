<template>
  <div class="table-box">
    <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
      <template #enabled="scope">
        {{ scope.row.enabled ? "启用" : "禁用" }}
      </template>
      <!-- 表格 header 按钮 -->
      <template #tableHeader>
        <el-button type="primary" v-auth="'add'" @click="openDrawer('新增')">新增</el-button>
      </template>
      <!-- 表格操作 -->
      <template #operation="scope">
        <el-button :type="scope.row.enabled ? 'danger' : 'primary'" v-auth="'enable'" link @click="enableLawFirm(scope.row)">
          {{ scope.row.enabled ? "禁用" : "启用" }}
        </el-button>
        <el-button type="primary" v-auth="'detail'" link @click="openDrawer('查看', scope.row)">查看</el-button>
        <el-button type="primary" v-auth="'edit'" link @click="openDrawer('编辑', scope.row)">编辑</el-button>
        <el-button type="primary" v-auth="'delete'" link @click="deleteAccount(scope.row)">删除</el-button>
      </template>
    </vz-table>

    <common-dialog ref="dialogRef"></common-dialog>
  </div>
</template>

<script setup lang="tsx" name="lawFirmInfo">
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { lawFirmInfo } from "@/api/modules/source/lawFirmInfo/api"; // api
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
import { LawFirmInfoPageRequest, LawFirmInfoDTO } from "@/api/modules/source/lawFirmInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
// import { useBaseStore } from "@/stores/modules/baseInfo";
import { useHandleData } from "@/hooks/useHandleData";
import { useAuthButtons } from "@/hooks/useAuthButtons";
import { useRouter } from "vue-router";
import { useDialogStore } from "@/stores/modules/dialogParams";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();

// 按钮权限
const { BUTTONS } = useAuthButtons();

// 表格配置项
const columns = reactive<ColumnProps<LawFirmInfoDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  // {
  //   prop: "orgId",
  //   label: "所属组织",
  //   width: 120,
  //   type: "org"
  // },
  {
    prop: "name",
    label: "律所名称",
    minWidth: 250,
    align: "left",
    headerAlign: "center",
    search: { el: "input" }
  },
  {
    prop: "contacts",
    label: "联系人",
    width: 120
  },
  {
    prop: "phone",
    label: "联系电话",
    width: 160
  },
  {
    prop: "address",
    label: "律所地址",
    minWidth: 220,
    align: "left",
    headerAlign: "center"
  },
  {
    prop: "enabled",
    label: "状态",
    width: 120
  },
  {
    prop: "dueNumber",
    label: "收款账号",
    width: 120
  },
  {
    prop: "dueBank",
    label: "收款银行",
    width: 120
  },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    width: 120
  },
  {
    type: "datetime",
    prop: "createStamp",
    label: "创建时间",
    width: 180,
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
    // 自定义 search
    // search: {
    //   render: ({ searchParam }) => {
    //     return (
    //       <el-date-picker
    //         vModel_trim={searchParam.createStampRange}
    //         type="datetimerange"
    //         start-placeholder="开始时间"
    //         end-placeholder="结束时间"
    //         format="YYYY-MM-DD HH:mm:ss"
    //         valueFormat="x"
    //         date-format="YYYY/MM/DD ddd"
    //         time-format="A hh:mm:ss"
    //       />
    //     );
    //   }
    // }
  },
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    width: 220,
    isShow: BUTTONS.value.edit || BUTTONS.value.detail || BUTTONS.value.delete
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: LawFirmInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return lawFirmInfo().findAll(newParams);
};

// 删除用户信息
const deleteAccount = async (params: LawFirmInfoDTO) => {
  await useHandleData(lawFirmInfo().delete, { id: params.id }, `此操作将永久删除该条数据，是否继续`);
  vzTableRef.value?.getTableList();
};
const enableLawFirm = async (LawFirmInfoDTO: LawFirmInfoDTO) => {
  const action = LawFirmInfoDTO.enabled ? "禁用" : "启用";
  const message = `此操作将${action}【${LawFirmInfoDTO.name}】，是否继续？`;
  ElMessageBox.confirm(message, "温馨提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  })
    .then(async () => {
      const data: any = await lawFirmInfo().isEnable(LawFirmInfoDTO.id, !LawFirmInfoDTO.enabled);
      if (data.code === 0) {
        ElMessage.success("操作成功");
        vzTableRef.value?.getTableList();
      } else {
        ElMessage.warning(data.msg);
      }
    })
    .catch(error => {
      console.log(error);
    });
};
// 打开 dialog(新增、查看、编辑)
const dialogRef = ref<any>(null);
const router = useRouter();
const openDrawer = (title: string, row: Partial<LawFirmInfoDTO> = {}) => {
  if (title === "查看") {
    router.push(`/source/lawFirmInfo/detail/${row.id}`);
    return;
  }
  const params = {
    dialogName: "lawFirmInfo_createUpdate",
    title,
    showBtn: false,
    id: row?.id,
    isView: title === "查看",
    api: title === "新增" ? lawFirmInfo().add : title === "编辑" ? lawFirmInfo().update : undefined,
    getTableList: vzTableRef.value?.getTableList
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
</script>

<style scoped lang="scss"></style>
