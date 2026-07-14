<template>
  <div>
    <vz-card title="诉状信息" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="代偿金额(元)">
            <template #value>
              <div v-currency="form.compensationAmount"></div>
            </template>
          </CellItem>
          <CellItem label="其他费用(元)">
            <template #value>
              <div v-currency="form.otherFees"></div>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="利息(元)">
            <template #value>
              <div v-currency="form.interest"></div>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="违约金(元)">
            <template #value>
              <div v-currency="form.liquidatedDamages"></div>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="备注" :value="form.remarks"> </CellItem>
        </el-col>
      </el-row>
    </vz-card>
    <List ref="listRef" :project-id="projectId"></List>
  </div>
</template>
<script setup lang="tsx" name="Litigation">
import { onMounted, ref } from "vue";
import { recoveryLitigation } from "@/api/modules/proceeding/recoveryLitigation/api"; // api

//引入组件
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import List from "./List.vue";

type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});

//表单信息
const form = ref({
  projectName: "",
  compensationAmount: "",
  interest: "",
  liquidatedDamages: "",
  otherFees: "",
  remarks: ""
});

//查看更多
const listRef = ref();
const goMore = () => {
  listRef.value!.dialogVisible = true;
};

//页面加载时
onMounted(() => {
  const params: any = { size: 1, current: 1, projectId: props.projectId, isSubmit: true };
  recoveryLitigation()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
      }
    });
});
</script>
