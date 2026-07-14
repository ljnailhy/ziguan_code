<template>
  <div>
    <vz-card title="撤诉信息" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="撤诉信息说明" :value="form.detailsDescription"> </CellItem>
        </el-col>
      </el-row>
    </vz-card>
    <el-dialog v-model="dialogVisible" title="撤诉记录" draggable width="1200px">
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
import {
  RecoveryLitigationDetailsPageRequest,
  RecoveryLitigationDetailsDTO,
  litigationTypeOptions
} from "@/api/modules/proceeding/recoveryLitigationDetails/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { recoveryLitigationDetails } from "@/api/modules/proceeding/recoveryLitigationDetails/api"; // api
import { ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的

import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";

type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});

const dialogVisible = ref(false);
const form = ref({
  detailsDescription: ""
});
const initParam = reactive({
  projectId: props.projectId,
  litigationType: "DROP_LAWSUIT"
});

// 表格配置项
const columns = reactive<ColumnProps<RecoveryLitigationDetailsDTO>[]>([
  { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60 },
  {
    type: "date",
    prop: "detailsDate",
    label: "时间",
    width: 120
  },
  {
    prop: "detailsDescription",
    label: "说明",
    minWidth: 120,
    search: { el: "input" }
  },
  {
    prop: "litigationType",
    label: "诉讼类型",
    enum: litigationTypeOptions, // 字典项固定调用pinia内的方法 只是 传的code不一样useBaseStore().findEnumByName("CUSTOMER_SOURCE")
    // search: { el: "select", props: { filterable: true } }, //props里面定义elemenui 的字段参数，比如placeholder,filterable,clearable 之类的
    // fieldNames: { label: "label", value: "id" },  重新定义接口返回字段名，elemenui 一般是 label value,接口可能是itemName,id之类的
    width: 180
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

const getTableList = (params: RecoveryLitigationDetailsPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return recoveryLitigationDetails().findAll(newParams);
};

const goMore = () => {
  dialogVisible.value = true;
};

onMounted(() => {
  const params: any = { size: 1, current: 1, projectId: props.projectId };
  recoveryLitigationDetails()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
      }
    });
});
</script>
