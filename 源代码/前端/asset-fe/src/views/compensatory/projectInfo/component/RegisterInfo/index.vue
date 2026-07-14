<template>
  <div>
    <vz-card :title="form.litigationType == 'REGISTER' ? '立案信息' : '审理信息'" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" v-if="form!.litigationType != 'REGISTER'">
          <CellItem label="立案案号">
            <template #value>
              <vzRegister :disabled="true" :default-value="form!.registerId"></vzRegister>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
          <CellItem label="立案法院">
            <template #value>
              {{ form!.filingCourtName ||"--" }}
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" v-if="form!.litigationType != 'REGISTER'">
          <CellItem label="类型">
            <template #value>
              <dict-enum :options="litigationTypeOptions" :value="form!.litigationType"></dict-enum>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" v-if="form!.litigationType == 'REGISTER'">
          <CellItem label="立案类型">
            <template #value>
              <dict-enum :options="registerTypeOptions" :value="form!.registerType"></dict-enum>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
          <CellItem label="法官">
            <template #value>
              {{ form!.judgeName ||'--'}}
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
          <CellItem label="法官联系方式">
            <template #value>
              {{ form!.judgePhone||'--' }}
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
          <CellItem label="立案时间">
            <template #value>
              <dict-date :date="form!.fillingDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" v-if="form!.litigationType == 'REGISTER'">
          <CellItem label="案号">
            <template #value>
              {{ form.fillingCode || "--" }}
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" v-if="form!.litigationType != LitigationTypeEnum.REGISTER">
          <CellItem label="判决日期">
            <template #value>
              <dict-date :date="form!.judgeDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" v-if="form!.litigationType != LitigationTypeEnum.REGISTER">
          <CellItem label="开庭时间">
            <template #value>
              <dict-date :date="form!.courtSessionDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem label="备注" :value="form.remarks"> </CellItem>
        </el-col>
      </el-row>
    </vz-card>
    <List ref="listRef" :project-id="projectId" :project-type="projectType"></List>
  </div>
</template>
<script setup lang="tsx" name="RegisterInfo">
import { onMounted, ref } from "vue";
import { recoveryJudgement } from "@/api/modules/proceeding/recoveryJudgement/api"; // api
import {
  litigationTypeOptions,
  registerTypeOptions,
  LitigationTypeEnum
} from "@/api/modules/proceeding/recoveryJudgement/interface";

//引入组件
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import vzRegister from "@/components/source/vzRegister.vue";
import List from "./List.vue";

type Props = {
  projectId?: any;
  projectType?: any;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: "",
  projectType: ["REGISTER"]
});

const form = ref({
  registerId: "",
  filingCourtName: "",
  fillingCode: "",
  fillingDate: "",
  isStock: "",
  lastEditStamp: "",
  lastEditor: "",
  litigationType: "",
  projectId: "",
  judgeName: "",
  judgePhone: "",
  remarks: "",
  courtSessionDate: "",
  judgeDate: "",
  registerType: ""
});

//查看更多
const listRef = ref();
const goMore = () => {
  listRef.value!.dialogVisible = true;
};

onMounted(() => {
  const params: any = {
    size: 1,
    current: 1,
    projectId: props.projectId,
    litigationTypeList: props.projectType,
    flowState: "completed"
  };
  recoveryJudgement()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
      } else {
        form.value.litigationType = props.projectType.toString();
      }
    });
});
</script>
