<template>
  <div>
    <el-form
      ref="ruleFormRef"
      label-width="100px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="form"
      :hide-required-asterisk="drawerProps.isView"
    >
      <el-row :gutter="35">
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="大类" prop="type">
            <el-input v-model="form!.type" placeholder="请输入大类" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="产品名称" prop="productName">
            <el-input v-model="form!.productName" placeholder="请输入产品名称" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="合作银行" prop="cooperateBank">
            <el-input v-model="form!.cooperateBank" placeholder="请输入合作银行" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="合作银行（支行）" prop="cooperateBankBranch">
            <el-input v-model="form!.cooperateBankBranch" placeholder="请输入合作银行（支行）" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="行业分类" prop="industryType">
            <el-input v-model="form!.industryType" placeholder="请输入行业分类" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
          <el-form-item label="企业划型 DXQY:大型企业 ZXQY:中型企业 XXQY:小型企业 WXQY:微型企业 QT:其他" prop="bigSmall">
            <el-radio-group v-model="form!.bigSmall">
              <el-radio value="DXQY">DXQY</el-radio>
              <el-radio value="ZXQY">ZXQY</el-radio>
              <el-radio value="XXQY">XXQY</el-radio>
              <el-radio value="WXQY">WXQY</el-radio>
              <el-radio value="QT">QT</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="政策扶持领域类别" prop="goverType">
            <el-input v-model="form!.goverType" placeholder="请输入政策扶持领域类别" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="债权起始日" prop="debtBeginDate">
            <el-date-picker v-model="form!.debtBeginDate" type="date" placeholder="请选择债权起始日" class="w100" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="债权到期日" prop="debtEndDate">
            <el-date-picker v-model="form!.debtEndDate" type="date" placeholder="请选择债权到期日" class="w100" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="银行放款金额" prop="loanMoney">
            <vz-input-unit v-model:value="form!.loanMoney" placeholder="请输入银行放款金额" text="元" clearable></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="委保合同号" prop="pactCode">
            <el-input v-model="form!.pactCode" placeholder="请输入委保合同号" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="借据号" prop="loanCode">
            <el-input v-model="form!.loanCode" placeholder="请输入借据号" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="担保费率（年）" prop="guaranteeRate">
            <vz-input-unit
              v-model:value="form!.guaranteeRate"
              placeholder="请输入担保费率（年）"
              text="元"
              clearable
            ></vz-input-unit>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="风补基金" prop="riskCompensation">
            <el-input v-model="form!.riskCompensation" placeholder="请输入风补基金" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="A角" prop="acode">
            <el-input v-model="form!.acode" placeholder="请输入A角" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="B角" prop="bcode">
            <el-input v-model="form!.bcode" placeholder="请输入B角" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="借款合同号" prop="loanPactCode">
            <el-input v-model="form!.loanPactCode" placeholder="请输入借款合同号" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="保证合同号" prop="pledPactCode">
            <el-input v-model="form!.pledPactCode" placeholder="请输入保证合同号" clearable></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="projectInfoExtDrawer">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { ProjectInfoExtRequest, ProjectInfoExtDTO, BigSmallEnum } from "@/api/modules/recovery/projectInfoExt/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { projectInfoExt } from "@/api/modules/recovery/projectInfoExt/api"; // api

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  bigSmall: [
    {
      required: true,
      message: "请选择企业划型",
      trigger: "change"
    }
  ],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

interface DrawerProps {
  title: string;
  isView: boolean;
  showbtn?: boolean;
  id?: number;
  api?: (params: Partial<ProjectInfoExtRequest>) => Promise<ProjectInfoExtDTO>;
  getTableList?: () => void;
}

const drawerProps = ref<DrawerProps>({
  isView: false,
  showbtn: false,
  title: ""
});

const form = ref<Partial<ProjectInfoExtRequest>>({
  /** 企业划型 DXQY:大型企业 ZXQY:中型企业 XXQY:小型企业 WXQY:微型企业 QT:其他 */
  bigSmall: BigSmallEnum.DXQY,
  id: undefined // 这只是一个后面没逗号的坑位
});

const handleAccept = (params: DrawerProps) => {
  drawerProps.value = params;
  findById();
};

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await projectInfoExt().findById(drawerProps.value.id);
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSave = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}项目信息详细信息扩展表成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

// 提交数据（流程）
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}项目信息详细信息扩展表成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};
defineExpose({
  handleSave,
  handleSubmit,
  handleAccept
});
</script>
