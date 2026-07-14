<template>
  <div>
    <vz-card title="撤诉信息" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="立案法院">
            <template #value>
              <dict-date :date="form!.courtSessionDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="撤诉时间">
            <template #value>
              <dict-date :date="form!.courtSessionDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="撤诉说明" :value="form.remarks"> </CellItem>
        </el-col>
      </el-row>
    </vz-card>
    <el-dialog v-model="dialogVisible" title="诉状信息" draggable width="1200px">
      <div class="table-box">
        <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam">
          <template #operation="scope">
            <el-button type="primary" v-auth="'detail'" link @click="openDrawer(scope.row)">查看</el-button>
          </template>
        </vz-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="tsx" name="Litigation">
import { onMounted, ref, reactive } from "vue";
import { recoveryLitigation } from "@/api/modules/proceeding/recoveryLitigation/api"; // api
import { RecoveryLitigationPageRequest, RecoveryLitigationDTO } from "@/api/modules/proceeding/recoveryLitigation/interface";
import { ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的

//引入组件
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";

type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});

//表单信息
const form = ref({
  filingCourtName: "",
  fillingCode: "",
  fillingDate: "",
  isStock: "",
  lastEditStamp: "",
  lastEditor: "",
  litigationType: "",
  projectId: "",
  judgeName: "",
  remarks: "",
  courtSessionDate: "",
  judgeDate: ""
});

// 初始化参数
const initParam = reactive({
  projectId: props.projectId
});

// 表格配置项
const columns = reactive<ColumnProps<RecoveryLitigationDTO>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    align: "left",
    search: { el: "input" }
  },
  {
    prop: "compensationAmount",
    label: "代偿金额(元)",
    width: 160,
    type: "money",
    align: "right",
    headerAlign: "center"
  },
  {
    prop: "interest",
    label: "利息（元）",
    minWidth: 120,
    type: "money",
    align: "right"
  },
  {
    prop: "liquidatedDamages",
    label: "违约金（元）",
    minWidth: 120,
    type: "money",
    align: "right"
  },
  {
    prop: "otherFees",
    label: "其他费用",
    minWidth: 120,
    type: "money",
    align: "right"
  },
  {
    prop: "creator",
    type: "user",
    label: "创建人",
    width: 120
  },
  {
    type: "date",
    prop: "createStamp",
    label: "创建时间",
    width: 120,
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
    width: 80
  }
]);

// 请求列表
const getTableList = (params: RecoveryLitigationPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return recoveryLitigation().findAll(newParams);
};

//查看更多
const dialogVisible = ref(false);
const goMore = () => {
  dialogVisible.value = true;
};

//查看
const openDrawer = (row: any) => {
  console.log(row);
};

//页面加载时
onMounted(() => {
  const params: any = { size: 1, current: 1, projectId: props.projectId };
  recoveryLitigation()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
      }
    });
});
</script>
