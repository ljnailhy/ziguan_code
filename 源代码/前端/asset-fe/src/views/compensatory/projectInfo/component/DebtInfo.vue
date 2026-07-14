<template>
  <div>
    <vz-card title="债务信息">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8" class="mb10">
          <div class="col-item flex">
            <div class="col-item_left">
              <div style="text-align: center">
                <el-button type="primary" :icon="UserFilled" circle />
                <div style="margin-top: 10px">债务人</div>
              </div>
            </div>
            <div style="flex: 1">
              <CellItem label="名称" value="线上">
                <template #value>
                  <vz-subject
                    v-model="form!.debtorId"
                    :default-value="form!.debtorId"
                    placeholder="债务人姓名"
                    disabled
                  ></vz-subject>
                </template>
              </CellItem>
              <CellItem label="性质">
                <template #value>
                  <dict-enum :options="natureOptions" :value="form!.nature"></dict-enum>
                </template>
              </CellItem>
              <CellItem label="证件" isbtm>
                <template #value>
                  <dict-enum :options="documentTypeOptions" :value="form.subjectInfoRequest!.documentType"></dict-enum>
                </template>
              </CellItem>
              <CellItem label="法人代表" :value="form.subjectInfoRequest!.legalRepresentative" />
              <CellItem label="联系人" :value="form.subjectInfoRequest!.contacts" />
              <CellItem label="联系电话" :value="form.subjectInfoRequest!.phone" />
              <CellItem label="详细地址" :value="form.subjectInfoRequest!.address">
                <template #value>
                  <div style="display: flex">
                    <dict-area :value="form.subjectInfoRequest!.belongProvince"></dict-area>
                    <dict-area :value="form.subjectInfoRequest!.belongCity"></dict-area>
                    <dict-area :value="form.subjectInfoRequest!.belongDistrict"></dict-area>
                    {{form.subjectInfoRequest!.address}}
                  </div>
                </template>
              </CellItem>
            </div>
          </div>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8" class="mb10">
          <div class="col-item flex">
            <div class="col-item_left">
              <div style="text-align: center">
                <el-button type="primary" :icon="Money" circle />
                <div style="margin-top: 10px">付款信息</div>
              </div>
            </div>
            <div style="flex: 1">
              <CellItem label="合作银行" label-width="90">
                <template #value>
                  <dict-name dict-type="COOPERATE_BANK" :dict-value="form!.cooperateBank"></dict-name>
                </template>
              </CellItem>
              <CellItem label="银行放款金额" :value="form.loanMoney" label-width="90">
                <template #value>
                  <div v-currency="form.loanMoney"></div>
                </template>
              </CellItem>
              <CellItem label="债权起始日期" :value="form.debtBeginDate" isbtm label-width="90">
                <template #value>
                  <dict-date :date="form!.debtBeginDate" format="YYYY-MM-DD"></dict-date>
                  至
                  <dict-date :date="form!.debtEndDate" format="YYYY-MM-DD"></dict-date>
                </template>
              </CellItem>
              <CellItem label="借款合同号" :value="form.loanPactCode" label-width="90" />
              <CellItem label="保证合同号" :value="form.pledPactCode" label-width="90" />
              <CellItem label="委保合同号" :value="form.pactCode" label-width="90" />
              <CellItem label="借据号" :value="form.loanCode" label-width="90" />
            </div>
          </div>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8" class="mb10">
          <div class="col-item flex">
            <div class="col-item_left">
              <div style="text-align: center">
                <el-button type="primary" :icon="PieChart" circle />
                <div style="margin-top: 10px">分险信息</div>
              </div>
            </div>
            <div style="flex: 1">
              <ECharts :option="option" ref="chartInstance" />
            </div>
          </div>
        </el-col>
      </el-row>
    </vz-card>
  </div>
</template>
<script setup lang="tsx" name="DebtInfo">
import { onMounted, ref } from "vue";
import { UserFilled, PieChart, Money } from "@element-plus/icons-vue";
import { documentTypeOptions, natureOptions } from "@/api/modules/source/subjectInfo/interface";

//引入组件
import ECharts from "@/components/ECharts/index.vue";
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import VzSubject from "@/components/source/vzSubject.vue";

type Props = {
  form?: any;
};

const props = withDefaults(defineProps<Props>(), {
  form: {}
});

//分险信息
const option: any = {
  tooltip: {
    trigger: "item"
  },
  legend: {
    right: "0",
    top: "left"
  },
  series: [
    {
      name: "分险信息",
      type: "pie",
      radius: ["40%", "70%"],
      center: ["50%", "60%"],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: "#fff",
        borderWidth: 2
      },
      label: {
        show: false,
        position: "center"
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 20,
          fontWeight: "bold"
        }
      },
      labelLine: {
        show: false
      },
      data: []
    }
  ]
};

//页面加载时
const chartInstance = ref();
onMounted(() => {
  option.series[0].data = [
    { value: props.form.dividedInsuranceDebtor || 0, name: "债权人" },
    { value: props.form.dividedInsuranceSecurity || 0, name: "原担保" },
    { value: props.form.dividedInsuranceAgainSecurity || 0, name: "再担保" },
    { value: props.form.dividedInsuranceOther || 0, name: "其他" }
  ];

  chartInstance.value.draw();
});
</script>
<style scoped lang="scss">
.flex {
  display: flex;
}
.col-item {
  box-sizing: border-box;
  border: 2px solid #e9e9e9;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
  height: 100%;
  &_left {
    width: 70px;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    background: var(--el-color-primary-light-9);
    justify-content: center;
    border-radius: 5px;
    margin-right: 10px;
    font-size: 13px;
  }
}
</style>
