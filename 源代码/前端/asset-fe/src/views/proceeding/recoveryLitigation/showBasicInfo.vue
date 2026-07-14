<template>
  <div>
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="true"
      :model="form"
      :hide-required-asterisk="true"
    >
      <el-row :gutter="35">
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="项目名称" prop="projectId">
            <vzProjectInfo :default-value="form!.projectId" select-type="radio"></vzProjectInfo>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="代偿金额" prop="compensationAmount" :inline-message="true">
            <vz-input-unit
              v-model="form!.compensationAmount"
              :value="form!.compensationAmount"
              placeholder="请输入代偿金额"
              text="元"
              :disabled="true"
            ></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="利息" prop="interest" :inline-message="true">
            <vz-input-unit
              v-model="form!.interest"
              :value="form!.interest"
              placeholder="请输入利息"
              text="万元"
              clearable
            ></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="违约金" prop="liquidatedDamages" :inline-message="true">
            <vz-input-unit
              v-model="form!.liquidatedDamages"
              :value="form!.liquidatedDamages"
              placeholder="请输入违约金"
              text="万元"
              clearable
            ></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="其他费用" prop="otherFees" :inline-message="true">
            <vz-input-unit
              v-model="form!.otherFees"
              :value="form!.otherFees"
              placeholder="请输入其他费用"
              text="元"
              clearable
            ></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
          <el-form-item label="备注" prop="remarks">
            <el-input type="textarea" :rows="5" v-model="form!.remarks" placeholder="请输入备注" clearable></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="recoveryLitigationDrawer">
import { ref, reactive, onMounted, watch } from "vue";
import { RecoveryLitigationRequest } from "@/api/modules/proceeding/recoveryLitigation/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { recoveryLitigation } from "@/api/modules/proceeding/recoveryLitigation/api"; // api

import vzProjectInfo from "@/components/source/vzProjectInfo.vue";

const rules = reactive({
  projectId: [{ required: true, message: "请输入项目名称", trigger: "blur" }],
  interest: [{ required: true, message: "请输入利息", trigger: "blur" }],
  liquidatedDamages: [{ required: true, message: "请输入违约金", trigger: "blur" }],
  otherFees: [{ required: true, message: "请输入其他费用", trigger: "blur" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

interface showBasicInfoProps {
  id?: number;
  data?: any;
}

const props = withDefaults(defineProps<showBasicInfoProps>(), {
  id: 0,
  data: {}
});

const form = ref<Partial<RecoveryLitigationRequest>>({
  /** 是否存量 是1 否0 */
  isStock: false,
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找
const findById = async () => {
  if (!props.id) return;
  const { data } = await recoveryLitigation().findById(props.id);
  form.value = data;
};

watch(
  () => props.data,
  val => {
    if (!props.id && val.id) {
      form.value = props.data;
    }
  },
  { deep: true, immediate: true }
);
onMounted(() => {
  findById();
});
</script>
