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
        scroll-to-error
      >
        <el-row :gutter="35">
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="所属组织" prop="orgId">
              <vz-org v-model:orgValue="form!.orgId" :org="form!.orgId"></vz-org>
            </el-form-item>
          </el-col> -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="律所名称" prop="name">
              <el-input v-model="form!.name" placeholder="请输入律所名称" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系人" prop="contacts">
              <el-input v-model="form!.contacts" placeholder="请输入联系人" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form!.phone" placeholder="请输入联系电话" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="律所地址" prop="address">
              <el-input v-model="form!.address" placeholder="请输入律所地址" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="收款账号" prop="dueNumber">
              <el-input v-model="form!.dueNumber" placeholder="请输入收款账号" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="收款银行" prop="dueBank">
              <el-input v-model="form!.dueBank" placeholder="请输入收款银行" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input
                type="textarea"
                :rows="5"
                v-model="form!.remark"
                placeholder="请输入备注"
                clearable
                maxlength="2000"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="律师团队">
      <form-table ref="lawyerRef" :table-data="lawyerInfoRequestList"></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="lawFirmInfoDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { LawFirmInfoRequest } from "@/api/modules/source/lawFirmInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { lawFirmInfo } from "@/api/modules/source/lawFirmInfo/api"; // api
import { lawyerInfo } from "@/api/modules/source/lawyerInfo/api";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { textPhone, deduplicateArraysById } from "@/utils";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const lawyerInfoRequestList = ref({
  data: [],
  header: [
    {
      prop: "lawyerName",
      label: "律师姓名",
      width: "110",
      isRequired: true,
      maxlength: 50,
      type: "text"
    },
    {
      prop: "phone",
      label: "联系电话",
      width: "150",
      // isRequired: true,
      maxlength: 30,
      type: "text"
    },
    {
      prop: "credentialNumber",
      label: "执业证号",
      width: "150",
      maxlength: 50,
      // isRequired: true,
      type: "text"
    },
    {
      prop: "userId",
      label: "登录账号",
      width: "150",
      type: "orguser",
      orgCode: "LAWYER"
    },
    {
      prop: "remark",
      label: "备注",
      width: "210",
      maxlength: 500,
      type: "text"
    }
  ]
});
const rules = reactive({
  name: [{ required: true, message: "请输入律所名称", trigger: "blur" }],
  orgId: [{ required: true, message: "请选择所属组织", trigger: "change" }],
  phone: [
    { required: true, message: "请填写手机号码", trigger: "blur" },
    { validator: textPhone, trigger: "blur" }
  ],
  contacts: [{ required: true, message: "请填写联系人", trigger: "blur" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<LawFirmInfoRequest>>({
  /** 是否有效 */
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找
const baklawyerInfoRequestList = ref([]);
const findById = async () => {
  // lawyerInfoRequestList.value.data = [];
  // baklawyerInfoRequestList.value = [];
  if (!drawerProps.value.id) return;
  const { data } = await lawFirmInfo().findById(drawerProps.value.id);
  form.value = data;
  //	加载律师团队
  lawyerInfo()
    .findByLawFirmId(drawerProps.value.id)
    .then((res: EmptyObjectType) => {
      if (res.code == 0) {
        lawyerInfoRequestList.value.data = res.data;
        baklawyerInfoRequestList.value = JSON.parse(JSON.stringify(res.data));
      } else {
        ElMessage.warning(res.msg);
      }
    });
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const lawyerRef = ref();
const handleSave = async () => {
  const formEl = fileRef.value.tableRulesRef;
  try {
    await Promise.all([formEl.validate(), ruleFormRef.value!.validate(), lawyerRef.value!.tableRulesRef.validate()]);
    form.value.fileInfoList = fileRef.value.submitForm;
    form.value.lawyerInfoRequestList = deduplicateArraysById(baklawyerInfoRequestList.value, lawyerInfoRequestList.value.data);

    await drawerProps.value.api!(form.value);
    ElMessage.success({ message: `${drawerProps.value.title}律所信息成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
  } catch (error) {
    console.log(error);
  }
};

// 提交数据（流程）
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}律所信息成功！` });
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
