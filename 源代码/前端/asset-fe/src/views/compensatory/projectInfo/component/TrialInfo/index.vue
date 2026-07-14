<template>
  <div>
    <vz-card title="调解/判决信息" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="调解/判决类型">
            <template #value>
              <dict-enum :options="adjustTrialTypeOptions" :value="form.adjustTrialType"></dict-enum>
            </template>
          </CellItem>
          <CellItem label="代偿金额(元)">
            <template #value>
              <div v-currency="form.compensatoryAmount"></div>
            </template>
          </CellItem>
          <CellItem label="其他费用(元)">
            <template #value>
              <div v-currency="form.otherAmount"></div>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="调解/判决日期">
            <template #value>
              <dict-date :date="form.adjustTrialDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
          <CellItem label="利息(元)">
            <template #value>
              <div v-currency="form.interest"></div>
            </template>
          </CellItem>
          <!-- <CellItem label="是否支持诉请">
            <template #value> </template>
          </CellItem> -->
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="判决案号">
            <template #value>
              {{ form!.adjustCode||'--' }}
            </template>
          </CellItem>
          <CellItem label="违约金(元)">
            <template #value>
              <div v-currency="form.backOutAmount"></div>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="特殊情况说明">
            <template #value>
              {{ form!.specialRemarks }}
            </template>
          </CellItem>
        </el-col>
        <!-- <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="调解/判决结果" :value="form.remarks"> </CellItem>
        </el-col> -->
      </el-row>
    </vz-card>
    <List ref="listRef" :project-id="projectId"></List>
  </div>
</template>
<script setup lang="tsx" name="LiAnInfo">
import { onMounted, ref } from "vue";
import { recoveryAdjustTrial } from "@/api/modules/proceeding/recoveryAdjustTrial/api"; // api
import { adjustTrialTypeOptions } from "@/api/modules/proceeding/recoveryAdjustTrial/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型

//引入组件
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import List from "./List.vue";

type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});

const form = ref({
  interest: "",
  backOutAmount: "",
  adjustCode: "",
  specialRemarks: "",
  compensatoryAmount: "",
  otherAmount: "",
  adjustTrialType: "",
  remarks: "",
  adjustTrialDate: ""
});

//查看更多
const listRef = ref();
const goMore = () => {
  listRef.value!.dialogVisible = true;
};

onMounted(() => {
  const params: any = { size: 1, current: 1, projectId: props.projectId, flowState: "completed" };
  recoveryAdjustTrial()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
      }
    });
});
</script>
