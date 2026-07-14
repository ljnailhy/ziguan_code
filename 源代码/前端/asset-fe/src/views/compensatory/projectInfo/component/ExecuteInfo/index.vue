<template>
  <div>
    <vz-card title="执行信息" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="执行状态" label-width="110">
            <template #value>
              <dict-enum :options="executeTypeOptions" :value="form!.executeType"></dict-enum>
            </template>
          </CellItem>
          <CellItem label="执行法院" label-width="110" :value="form.executeCourt"> </CellItem>
          <CellItem label="执行裁定下达时间" label-width="110">
            <template #value>
              <div v-if="form.executeRulingIssuanceTime">
                <span v-for="(item, index) in form.executeRulingIssuanceTime.split(',')" :key="index">
                  <dict-date :date="Number(item)" format="YYYY-MM-DD"> </dict-date>
                  <i v-if="index !== form.executeRulingIssuanceTime.split(',').length - 1">、</i>
                </span>
              </div>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="申请执行日期" label-width="110">
            <template #value>
              <dict-date :date="form!.applyExecuteDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
          <CellItem label="执行员" label-width="110">
            <template #value>
              {{ form.executer || "--" }}
            </template>
          </CellItem>
          <!-- <CellItem label="裁定以资抵债日期" :value="form!.loanMoney" label-width="110">
            <template #value>
              <div v-currency="form.loanMoney"></div>
            </template>
          </CellItem> -->
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="执行案号" :value="form.executeCode" />

          <CellItem label="联系电话">
            <template #value>
              {{ form.executerTelphone || "--" }}
            </template>
          </CellItem>
          <!-- <CellItem label="裁定抵债金额">
            <template #value>
              <dict-name dict-type="INDUSTRY_POLICY_SUPPORT" :dict-value="form!.goverType"></dict-name>
            </template>
          </CellItem> -->
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="备注">
            <template #value>
              {{ form.remarks || "--" }}
            </template>
          </CellItem>
        </el-col>
      </el-row>
    </vz-card>
    <List ref="listRef" :project-id="projectId"></List>
    <!-- <el-dialog v-model="dialogVisible" title="执行信息记录" draggable width="1200px">
      <div class="table-box">
        <vz-table ref="vzTableRef" :columns="columns" :request-api="getTableList" :init-param="initParam"> </vz-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
        </span>
      </template>
    </el-dialog> -->
  </div>
</template>
<script setup lang="tsx" name="LiAnInfo">
import { onMounted, ref } from "vue";
import { recoveryExecute } from "@/api/modules/proceeding/recoveryExecute/api"; // api
import { executeTypeOptions } from "@/api/modules/proceeding/recoveryExecute/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型

import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import List from "./List.vue";

type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});

const form = ref({
  executeType: "",
  executeCourt: "",
  executeRulingIssuanceTime: "",
  applyExecuteDate: "",
  executer: "",
  executeCode: "",
  executerTelphone: "",
  remarks: ""
});

//查看更多
const listRef = ref();
const goMore = () => {
  listRef.value!.dialogVisible = true;
};

onMounted(() => {
  const params: any = { size: 1, current: 1, projectId: props.projectId, flowState: "completed" };
  recoveryExecute()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
      }
    });
});
</script>
