<template>
  <div>
    <vz-card title="保全信息" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="立案法院">
            <template #value>
              <vzRegister :default-value="form!.registerId" disabled></vzRegister>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="8" :xl="8">
          <CellItem label="撤诉时间">
            <template #value>
              <dict-date :date="form!.detailsDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="撤诉说明" :value="form.detailsDescription"> </CellItem>
        </el-col>
      </el-row>
    </vz-card>
    <List ref="listRef" :project-id="projectId"></List>
  </div>
</template>
<script setup lang="tsx" name="LiAnInfo">
import { onMounted, ref } from "vue";
import { recoveryLitigationDetails } from "@/api/modules/proceeding/recoveryLitigationDetails/api"; // api

//引入组件
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import vzRegister from "@/components/source/vzRegister.vue";
import List from "./List.vue";

type Props = {
  projectId?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: ""
});

const form = ref({
  registerId: "",
  detailsDate: "",
  detailsDescription: ""
});

//查看更多
const listRef = ref();
const goMore = () => {
  listRef.value!.dialogVisible = true;
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
