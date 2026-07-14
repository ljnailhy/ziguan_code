<template>
  <div>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="100px"
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        scroll-to-error
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="机构名称" prop="agencyName">
              <el-input v-model="form!.agencyName" placeholder="请输入机构名称" maxlength="50" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="机构类型" prop="agencyType">
              <vz-select
                dict-type="AGENCY_TYPE"
                v-model="form!.agencyType"
                :dict-value="form!.agencyType"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="代理性质" prop="agencyNature">
              <el-select v-model="form!.agencyNature" placeholder="请选择">
                <!-- <el-option key="LEASE" label="租赁" value="LEASE" /> -->
                <el-option key="ASSESS" label="评估" value="ASSESS" />
                <el-option key="LEASE_TRANSFER" label="租赁转让" value="LEASE_TRANSFER" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系人" prop="contacts">
              <el-input v-model="form!.contacts" placeholder="请输入联系人" maxlength="50" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系电话" prop="contactsPhone">
              <el-input v-model="form!.contactsPhone" placeholder="请输入联系电话" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="机构地址" prop="agencyAddress">
              <el-input v-model="form!.agencyAddress" placeholder="请输入机构地址" maxlength="100" clearable></el-input>
            </el-form-item>
          </el-col>
          <!--        <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="8" class="mb20">-->
          <!--          <el-form-item label="代理费" prop="agencyFee">-->
          <!--            <vz-input-unit v-model="form.agencyFee" :value="form.agencyFee" :min="0" text="元"></vz-input-unit>-->
          <!--          </el-form-item>-->
          <!--        </el-col>-->
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="form!.remark"
                type="textarea"
                show-word-limit
                :rows="5"
                placeholder="请输入备注"
                clearable
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="drawerProps.isView" :query-data="{ doType: 'AGENCY', doId: form!.id }"></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="agencyDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { AgencyRequest, AgencyNatureEnum } from "@/api/modules/source/agency/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { agency } from "@/api/modules/source/agency/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";
// import { textPhone } from "@/utils";
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  agencyNature: [{ required: true, message: "请选择代理性质", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }],
  agencyName: [{ required: true, message: "请输入机构名称" }],
  agencyType: [{ required: true, message: "请输入机构类型" }]
  // contactsPhone: [{ validator: textPhone, trigger: "blur" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<AgencyRequest>>({
  /** 代理性质 租赁:LEASE ASSESS:评估 LEASE_TRANSFER 租赁转让 */
  agencyNature: AgencyNatureEnum.ASSESS,
  fileRequests: [],
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await agency().findById(drawerProps.value.id);
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const handleSave = () => {
  form.value.fileRequests = fileRef.value.submitForm;
  if (fileRef.value.submitForm.length > 0) {
    for (let dataKey in form.value.fileRequests) {
      if (form.value.fileRequests[dataKey].fileName === "") {
        ElMessage.warning("文件名称不能为空!");
        return;
      }
      if (form.value.fileRequests[dataKey].annexName === "") {
        ElMessage.warning("附件名称不能为空!");
        return;
      }
    }
  }
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}代理机构成功！` });
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
      ElMessage.success({ message: `${drawerProps.value.title}代理机构成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

onMounted(() => {
  findById();
});
defineExpose({
  handleSave,
  handleSubmit
});
</script>
