<template>
  <el-dialog v-model="dialogVisible" title="查看诉状信息" draggable width="800px">
    <vz-card title="基本信息">
      <el-form label-width="100px" label-suffix=" :" :disabled="true" :model="form" :hide-required-asterisk="true">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="代偿金额(元)" prop="compensationAmount">
              <vz-input-unit
                v-model="form!.compensationAmount"
                :value="form!.compensationAmount"
                placeholder="请输入代偿金额"
                text="元"
                :disabled="true"
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="利息(元)" prop="interest">
              <vz-input-unit
                v-model="form!.interest"
                :value="form!.interest"
                placeholder="请输入利息"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="违约金(元)" prop="liquidatedDamages">
              <vz-input-unit
                v-model="form!.liquidatedDamages"
                :value="form!.liquidatedDamages"
                placeholder="请输入违约金"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="其他费用(元)" prop="otherFees">
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
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="true" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup lang="tsx" name="Litigation">
import { ref } from "vue";

//表单信息
const form = ref({
  projectName: "",
  compensationAmount: "",
  interest: "",
  liquidatedDamages: "",
  otherFees: "",
  remarks: "",
  id: ""
});

const dialogVisible = ref(false);
const acceptParams = (params: any) => {
  form.value = {
    ...form.value,
    ...params
  };
  dialogVisible.value = true;
};

//暴漏变量给父级
defineExpose({
  acceptParams
});
</script>
