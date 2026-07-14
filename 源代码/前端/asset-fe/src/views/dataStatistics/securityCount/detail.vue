<template>
  <div ref="containerRef" class="home">
    <vz-card title="概况信息">
      <el-row :gutter="30">
        <el-col :xs="24" :sm="24" :md="12" :lg="7" :xl="7">
          <IdCard left-title="身份" right-title="联系电话" :info="rowData"></IdCard>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="17" :xl="17">
          <div>
            <!-- <div class="sub-title">当前管理</div> -->
            <el-row :gutter="16">
              <el-col :span="4">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.manageProjectNum" title="代偿项目数(个)"> </el-statistic>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.compensationMoney" title="代偿金额(万元)" :precision="4"> </el-statistic>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.recoveryCollectionAmount" title="回款金额(万元)" :precision="4"> </el-statistic>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.residueRecoveryAmount" title="剩余代偿金额(万元)" :precision="4"> </el-statistic>
                </div>
              </el-col>

              <el-col :span="5">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.totalCollectionRate" title="累计回款率(%)" :precision="2"> </el-statistic>
                </div>
              </el-col>
            </el-row>
          </div>
          <div>
            <!-- <div class="sub-title">历史业绩</div> -->
            <el-row :gutter="16">
              <el-col :span="4">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.writeOffNum" title="核销项目数(个)"> </el-statistic>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.totalWriteOffAmount" title="核销项目金额(万元)" :precision="4"> </el-statistic>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.lawFee" title="诉讼到期日小于90天"> </el-statistic>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.totalPaymentCollection" title="保全到期日小于90天"> </el-statistic>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="statistic-card">
                  <el-statistic :value="rowData!.collectionRateYear" title="执行到期日小于90天"> </el-statistic>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>
    </vz-card>
    <vz-card :title="tabList[tabPosition]">
      <el-radio-group v-model="tabPosition" style="margin-bottom: 20px" @change="changetab">
        <el-radio-button value="0">在管项目</el-radio-button>
        <el-radio-button value="1">已核销</el-radio-button>
        <el-radio-button value="2">已结案</el-radio-button>
      </el-radio-group>
      <div class="table-box">
        <vz-table ref="vzTableRef" :request-api="getTableList" :init-param="initParam" :columns="columns"> </vz-table>
      </div>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="home">
import { onMounted, ref, reactive } from "vue";
import { useRoute } from "vue-router";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { manageLedger } from "@/api/modules/dataStatistics/securityCount/api";
import { ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的

import IdCard from "@/views/dataStatistics/securityCount/component/IdCard.vue";

const containerRef = ref<HTMLElement | null>(null);
const tabPosition = ref("0");

const tabList = ref(["在管项目", "已核销", "已结案"]);

// 单个查找
const route = useRoute();
const rowData = ref<any>({
  manageProjectList: [],
  closeCaseProjectList: [],
  writeOffProjectList: []
});

const paramsValue = ref({ current: 1, size: 10, manage: route.params.id });
const findById = async () => {
  const id: any = route.params.id;
  if (!id) return;
  const { data } = await manageLedger().findById(paramsValue.value);
  rowData.value = data;
};

onMounted(async () => {
  await findById();
});

// 表格配置项
const columns = reactive<ColumnProps<any>[]>([
  { type: "index", label: "序号", width: 60 },
  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    align: "left",
    headerAlign: "center",
    search: { el: "input" }
  },

  {
    prop: "projectState",
    label: "项目状态",
    minWidth: 120,
    enum: () => useBaseStore().findEnumByName("PROJECT_STATE")
  },

  {
    prop: "compensationMoney",
    label: "代偿金额(元)",
    minWidth: 160,
    type: "money",
    align: "right"
  },
  {
    type: "date",
    prop: "compensationDate",
    label: "代偿时间",
    minWidth: 120
    // search: {
    //   key: "compensationDateRange", //指定搜索的key
    //   el: "date-picker",
    //   span: 1,
    //   props: {
    //     type: "datetimerange",
    //     format: "YYYY-MM-DD HH:mm:ss",
    //     valueFormat: "x",
    //     defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
    //   }
    // }
  },
  {
    prop: "name",
    label: "律所信息",
    minWidth: 160
  },
  {
    type: "date",
    prop: "proceedingAgeingDate",
    label: "诉讼时效",
    minWidth: 120
  },
  {
    type: "date",
    prop: "adjustTrialDate",
    label: "执行时效",
    minWidth: 120
  },
  {
    prop: "residueReveMeasureNum",
    label: "剩余反担保措施",
    minWidth: 160
  },
  {
    type: "money",
    align: "right",
    prop: "totalCollectionAmount",
    label: "累计回款(元)",
    minWidth: 160
  },
  {
    type: "money",
    align: "right",
    prop: "residueRecoveryAmount",
    label: "剩余代偿金额(元)",
    minWidth: 160
  }
]);
const initParam = reactive({});
const vzTableRef = ref<any>();
const changetab = () => {
  vzTableRef.value?.getTableList();
};
const getTableList = (params: any) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  newParams.manage = route.params.id;
  if (tabPosition.value == "2") {
    newParams.isCloseCase = true;
  } else {
    newParams.isCloseCase = null;
  }
  if (tabPosition.value == "1") {
    newParams.isWriteOff = true;
  } else {
    newParams.isWriteOff = null;
  }
  return manageLedger().findMangeDetail(newParams);
};
</script>

<style scoped lang="scss">
@import "./index.scss";
.homeflex {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 12px;
  & > div:first-child {
    width: 65px;
    color: rgb(176, 195, 233);
  }
  .el-link {
    font-size: 12px;
  }
}
.col-item {
  display: flex;
  box-sizing: border-box;
  border: 2px solid #e9e9e9;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
  height: 190px;
  &_left {
    width: 75px;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    background: var(--el-color-primary-light-9);
    justify-content: center;
    border-radius: 5px;
    margin-right: 10px;
    font-size: 13px;
    &_warning {
      margin-top: 10px;
      font-size: 10px;
      color: rgb(176, 195, 233);
    }
  }
}
</style>
