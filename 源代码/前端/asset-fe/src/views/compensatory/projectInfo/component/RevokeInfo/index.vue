<template>
  <div>
    <vz-card :title="getTitle() + '信息'" more @go-more="goMore">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8">
          <CellItem :label="`${getTitle()}标题`" :value="form!.title"> </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" v-if="props.projectType == LitigationTypeEnum.DROP_LAWSUIT">
          <CellItem label="立案案号">
            <template #value>
              <vzRegister :default-value="form!.registerId" :disabled="true"></vzRegister>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" v-if="props.projectType == LitigationTypeEnum.PRESERVATION">
          <CellItem label="保全案号">
            <template #value>
              {{ form!.preservationCode || '--' }}
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="8" :xl="8" v-if="props.projectType != LitigationTypeEnum.PRESERVATION">
          <CellItem :label="`${getTitle()}时间`">
            <template #value>
              <dict-date :date="form!.detailsDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem :label="`${getTitle()}说明`" :value="form.detailsDescription"> </CellItem>
        </el-col>
      </el-row>
      <!-- <el-row :gutter="10">
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem :label="`${getTitle()}标题`" :value="form!.title"> </CellItem>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="8">
          <CellItem :label="`${getTitle()}时间`">
            <template #value>
              <dict-date :date="form!.detailsDate" format="YYYY-MM-DD"></dict-date>
            </template>
          </CellItem>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <CellItem :label="`${getTitle()}说明`" :value="form.detailsDescription"> </CellItem>
        </el-col>
      </el-row> -->
    </vz-card>
    <List ref="listRef" :project-id="projectId" :type="projectType" :title="getTitle()"></List>
    <PreservationList
      ref="preservationListRef"
      :project-id="projectId"
      :type="projectType"
      :title="getTitle()"
    ></PreservationList>
  </div>
</template>
<script setup lang="tsx" name="RevokeInfo">
import { onMounted, ref } from "vue";
import { recoveryLitigationDetails } from "@/api/modules/proceeding/recoveryLitigationDetails/api"; // api
import { LitigationTypeEnum } from "@/api/modules/proceeding/recoveryLitigationDetails/interface";
//引入组件
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import vzRegister from "@/components/source/vzRegister.vue";
import List from "./List.vue";
import PreservationList from "./PreservationList.vue";

type Props = {
  projectId?: any;
  projectType?: string;
};

const props = withDefaults(defineProps<Props>(), {
  projectId: "",
  projectType: "DROP_LAWSUIT"
});

const form = ref({
  title: "",
  registerId: "",
  detailsDate: "",
  detailsDescription: "",
  preservationCode: ""
});

// 获取title
const getTitle = () => {
  if (props.projectType === "DROP_LAWSUIT") {
    return "撤诉";
  } else if (props.projectType === "PRESERVATION") {
    return "保全";
  } else if (props.projectType === "FINAL") {
    return "终本";
  } else if (props.projectType === "CLOSE_CASE") {
    return "结案";
  } else if (props.projectType === "OTHER") {
    return "其他诉讼";
  }
};

//查看更多
const listRef = ref();
const preservationListRef = ref();
const goMore = () => {
  if (props.projectType == "PRESERVATION") {
    preservationListRef.value!.dialogVisible = true;
  } else {
    listRef.value!.dialogVisible = true;
  }
};

onMounted(() => {
  const params: any = {
    size: 1,
    current: 1,
    projectId: props.projectId,
    litigationType: props.projectType,
    flowState: "completed"
  };

  recoveryLitigationDetails()
    .findAll(params)
    .then((res: any) => {
      if (res.code === 0 && res.data && res.data.length > 0) {
        form.value = res.data[0];
        console.log(form.value);
      }
    });
});
</script>
