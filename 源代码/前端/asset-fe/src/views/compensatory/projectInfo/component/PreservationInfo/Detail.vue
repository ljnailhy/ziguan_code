<template>
  <el-dialog v-model="dialogVisible" title="查看撤诉信息" draggable width="800px">
    <vz-card title="基本信息">
      <el-form ref="ruleFormRef" label-width="100px" label-suffix=" :" scroll-to-error :disabled="true" :model="form">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="form!.litigationType != 'REGISTER'">
            <el-form-item label="立案法院" prop="registerId">
              <vzRegister :default-value="form!.registerId"></vzRegister>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="form!.litigationType == 'REGISTER'">
            <el-form-item label="立案法院" prop="filingCourtName">
              <el-input v-model="form!.filingCourtName" placeholder="请输入立案法院" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="form!.litigationType != 'REGISTER'">
            <el-form-item label="类型" prop="litigationType">
              <el-select v-model="form!.litigationType" clearable class="w100">
                <!-- <el-option label="立案" value="REGISTER"></el-option> -->
                <el-option label="一审" value="FIRST_INSTANCE"></el-option>
                <el-option label="二审" value="SECOND_INSTANCE"></el-option>
                <el-option label="再审" value="RETRIAL"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="form!.litigationType == 'REGISTER'">
            <el-form-item label="立案类型" prop="registerType">
              <el-select v-model="form!.registerType" clearable class="w100">
                <el-option label="一审立案" value="FIRST_INSTANCE"></el-option>
                <el-option label="二审立案" value="SECOND_INSTANCE"></el-option>
                <el-option label="再审立案" value="RETRIAL"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="12"
            class="mb20"
            v-if="form!.litigationType != LitigationTypeEnum.REGISTER"
          >
            <el-form-item label="法官" prop="judgeName">
              <el-input v-model="form!.judgeName" placeholder="请输入法官" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="立案时间" prop="fillingDate">
              <el-date-picker v-model="form!.fillingDate" type="date" placeholder="请选择立案时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="案号" prop="fillingCode">
              <el-input v-model="form!.fillingCode" placeholder="请输入案号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="12"
            class="mb20"
            v-if="form!.litigationType != LitigationTypeEnum.REGISTER"
          >
            <el-form-item label="判决日期" prop="judgeDate">
              <el-date-picker v-model="form!.judgeDate" type="date" placeholder="请选择判决日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="12"
            :lg="12"
            :xl="12"
            class="mb20"
            v-if="form!.litigationType != LitigationTypeEnum.REGISTER"
          >
            <el-form-item label="开庭时间" prop="courtSessionDate">
              <el-date-picker v-model="form!.courtSessionDate" type="date" placeholder="请选择开庭时间" class="w100" />
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="诉讼id" prop="litigationId">
                <el-input v-model="form!.litigationId" placeholder="请输入诉讼id" clearable></el-input>
              </el-form-item>
            </el-col> -->
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
import { LitigationTypeEnum } from "@/api/modules/proceeding/recoveryJudgement/interface";

import vzRegister from "@/components/source/vzRegister.vue";

//表单信息
const form = ref({
  id: "",
  litigationType: "",
  registerId: "",
  filingCourtName: "",
  remarks: "",
  courtSessionDate: "",
  judgeDate: "",
  fillingCode: "",
  fillingDate: "",
  judgeName: "",
  registerType: ""
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
