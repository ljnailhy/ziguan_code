<template>
  <div>
    <vz-card title="立案信息" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="立案法院" :value="form.filingCourtName"> </CellItem>
          <!-- <CellItem label="保全日期">
          <template #value> </template>
        </CellItem> -->
          <CellItem label="开庭时间">
            <template #value>
              <dict-date :date="form!.courtSessionDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
          <CellItem label="案号" :value="form.fillingCode"> </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="法官" :value="form.judgeName"> </CellItem>

          <CellItem label="判决时间">
            <template #value>
              <dict-date :date="form!.judgeDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem label="立案日期">
            <template #value> <dict-date :date="form!.fillingDate" format="YYYY-mm-dd"></dict-date> </template>
          </CellItem>
          <CellItem label="诉讼类型">
            <template #value>
              <dict-enum :options="litigationTypeOptions" :value="form!.litigationType"></dict-enum>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="备注" :value="form.remarks"> </CellItem>
        </el-col>
      </el-row>
    </vz-card>
    <el-dialog v-model="dialogVisible" title="诉讼记录" draggable width="1200px">
      <div class="table-box">
        <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam"> </vz-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup lang="tsx" name="LiAnInfo">
import { onMounted, ref, reactive } from "vue";
import { recoveryJudgement } from "@/api/modules/proceeding/recoveryJudgement/api"; // api
import {
  litigationTypeOptions,
  RecoveryJudgementDTO,
  RecoveryJudgementPageRequest
} from "@/api/modules/proceeding/recoveryJudgement/interface";
import { ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的

import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";

type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});

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

const dialogVisible = ref(false);
const initParam = reactive({
  projectId: props.projectId
});
// 表格配置项
const columns = reactive<ColumnProps<RecoveryJudgementDTO>[]>([
  { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    prop: "filingCourtName",
    label: "立案法院",
    width: 120,
    search: { el: "input" }
  },
  {
    prop: "judgeName",
    label: "法官",
    width: 120,
    search: { el: "input" }
  },
  {
    type: "date",
    prop: "fillingDate",
    label: "立案时间",
    width: 120
  },
  {
    prop: "litigationType",
    label: "类型",
    enum: litigationTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 180
  },
  {
    prop: "fillingCode",
    label: "案号",
    width: 120,
    search: { el: "input" }
  },
  {
    type: "date",
    prop: "judgeDate",
    label: "判决日期",
    width: 120
  },
  {
    type: "date",
    prop: "courtSessionDate",
    label: "开庭时间",
    width: 120
  },
  {
    prop: "remarks",
    label: "备注",
    width: 120
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
  }
]);
const getTableList = (params: RecoveryJudgementPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return recoveryJudgement().findAll(newParams);
};
const goMore = () => {
  dialogVisible.value = true;
};

onMounted(() => {
  const params: any = { size: 1, current: 1, projectId: props.projectId };
  recoveryJudgement()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
      }
    });
});
</script>
