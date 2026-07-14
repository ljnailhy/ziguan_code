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
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="客户名称" prop="customerName">
              <el-input v-model="form!.customerName" placeholder="请输入客户名称" maxlength="20" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="证件类型" prop="documentType">
              <vz-select
                dict-type="ID_TYPE"
                v-model="form!.documentType"
                :dict-value="form!.documentType"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="证件号" prop="documentCode">
              <el-input v-model="form!.documentCode" placeholder="请输入证件号" maxlength="32" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系人" prop="contacts">
              <el-input v-model="form!.contacts" placeholder="请输入联系人" maxlength="20" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系电话" prop="contactsPhone">
              <el-input v-model="form!.contactsPhone" placeholder="请输入联系电话" maxlength="20" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="来源中介" prop="mediumSource">
              <el-input v-model="form!.mediumSource" placeholder="请输入来源中介" maxlength="50" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="客户来源" prop="customerSource">
              <el-select v-model="form!.customerSource" placeholder="请选择企业划型">
                <el-option key="INTRODUCE" label="中介推荐" value="INTRODUCE" />
                <el-option key="NATURAL_PERSON" label="自然上客" value="NATURAL_PERSON" />
              </el-select>
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="意向资产" prop="intentionalAssets">
              <el-input v-model="form!.intentionalAssets" placeholder="请输入意向资产" maxlength="50" clearable></el-input>
            </el-form-item>
          </el-col> -->
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="form!.remark"
                type="textarea"
                show-word-limit
                maxlength="500"
                :rows="5"
                placeholder="请输入备注"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="附件信息">
      <file-table
        ref="fileRef"
        :readonly="drawerProps.isView"
        :query-data="{ doType: 'CUSTOMER_INFO', doId: form!.id }"
      ></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="customerInfoDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { CustomerInfoRequest, CustomerSourceEnum } from "@/api/modules/source/customerInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { customerInfo } from "@/api/modules/source/customerInfo/api";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { textPhone } from "@/utils";
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  customerName: [{ required: true, message: "请输入客户名称", trigger: "blur" }],
  customerSource: [{ required: true, message: "请选择客户来源'", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }],
  contactsPhone: [{ required: true, validator: textPhone, trigger: "blur" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<CustomerInfoRequest>>({
  /** 客户来源'INTRODUCE'：介绍,'NATURAL_PERSON'：自然人 */
  customerSource: CustomerSourceEnum.INTRODUCE,
  fileRequests: [],
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await customerInfo().findById(drawerProps.value.id);
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const handleSave = () => {
  form.value.fileRequests = fileRef.value.submitForm;
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
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
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}客户信息表成功！` });
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
      ElMessage.success({ message: `${drawerProps.value.title}客户信息表成功！` });
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
