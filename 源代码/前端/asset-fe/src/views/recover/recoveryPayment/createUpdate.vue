<template>
  <div>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="130px"
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        scroll-to-error
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <!--            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">-->
          <!--              <el-form-item label="项目名称" prop="projectName">-->
          <!--                <el-input v-model="form!.projectName" placeholder="请输入项目名称" clearable></el-input>-->
          <!--              </el-form-item>-->
          <!--            </el-col>-->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="项目名称" prop="projectId">
              <vz-project-info
                v-model="form!.projectId"
                :default-value="form!.projectId"
                :disabled="drawerProps.projectId ? true : false"
                placeholder="项目名称"
                :filter-data="{
                  projectStateList: projectStateList
                }"
                @handle-ok="handleOk"
                select-type="radio"
              ></vz-project-info>
            </el-form-item>
          </el-col>
          <!--            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">-->
          <!--              <el-form-item label="项目名称" prop="projectName">-->
          <!--                <vz-reve-info> </vz-reve-info>-->
          <!--              </el-form-item>-->
          <!--            </el-col>-->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="付款金额(元)" prop="payAmount" :inline-message="true">
              <vz-input-unit
                v-model="form!.payAmount"
                :value="form!.payAmount"
                placeholder="请输入付款金额"
                :disabled="true"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="付款时间" prop="payDate">
              <el-date-picker v-model="form!.payDate" type="date" placeholder="请选择付款时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="付款律所" prop="lawyerName">
              <vz-law-firm-info
                v-model="form!.lawyerId"
                :default-value="form!.lawyerId"
                placeholder="付款律所"
                @handle-ok="handleOk1"
              ></vz-law-firm-info>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="收款方" prop="payee">
              <el-input v-model="form!.payee" placeholder="请输入收款方" maxlength="50" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="收款账号" prop="receivingAccount">
              <el-input v-model="form!.receivingAccount" placeholder="请输入收款账号" maxlength="20" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="收款银行" prop="receivingBank">
              <el-input v-model="form!.receivingBank" placeholder="请输入收款银行" maxlength="50" clearable></el-input>
            </el-form-item>
          </el-col>
          <!--            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">-->
          <!--              <el-form-item label="状态" prop="payStatus">-->
          <!--                <el-switch v-model="form!.payStatus" />-->
          <!--              </el-form-item>-->
          <!--            </el-col>-->
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="付款说明" prop="payExplain">
              <el-input
                v-model="form!.payExplain"
                type="textarea"
                show-word-limit
                :rows="5"
                placeholder="请输入付款说明"
                clearable
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
          <!--          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">-->
          <!--            <el-form-item label="备注" prop="remarks">-->
          <!--              <el-input-->
          <!--                v-model="form!.remarks"-->
          <!--                type="textarea"-->
          <!--                show-word-limit-->
          <!--               :rows="5"-->
          <!--                placeholder="请输入备注"-->
          <!--                clearable-->
          <!--                maxlength="500"-->
          <!--              ></el-input>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="付款明细">
      <form-table
        ref="detailListEl"
        :table-data="detailList"
        :readonly="drawerProps.isView"
        @money-input="moneyBlur"
        @del-row="moneyBlur"
      ></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table
        ref="fileRef"
        :readonly="drawerProps.isView"
        :query-data="{ doType: 'RECOVERY_PAYMENT', doId: form!.id }"
      ></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="recoveryPaymentDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { RecoveryPaymentRequest } from "@/api/modules/recovery/recoveryPayment/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { recoveryPayment } from "@/api/modules/recovery/recoveryPayment/api";
import VzProjectInfo from "@/components/source/vzProjectInfo.vue";
import VzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { deduplicateArraysById } from "@/utils";
import { useBaseStore } from "@/stores/modules/baseInfo";

// api
const handleOk = (obj: any) => {
  form.value = {
    projectName: obj.projectName,
    projectId: obj.id
  };
  detailList.value.data = [];
};

const handleOk1 = (obj: any) => {
  form.value = {
    ...form.value,
    lawyerName: obj.lawyerName,
    lawyerId: obj.id,
    payee: obj.name,
    receivingAccount: obj.dueNumber,
    receivingBank: obj.dueBank
  };
};

const moneyBlur = async () => {
  form.value.payAmount = 0;
  form.value.payAmount = detailList.value.data.reduce((accumulator, currentItem) => {
    if (currentItem.operateType === "DELETE") {
      return accumulator;
    }
    let amount = currentItem.payAmount;
    if (typeof amount === "string") {
      amount = parseFloat(amount);
    }
    amount = typeof amount === "number" ? amount : 0;
    return accumulator + amount;
  }, 0);
};

const detailList = ref<any>({
  data: [],
  header: [
    {
      prop: "payType",
      label: "付款类型",
      width: "120",
      isRequired: true,
      type: "select",
      dictType: "PAY_TYPE"
    },
    {
      prop: "payAmount",
      label: "付款金额(元)",
      width: "120",
      isRequired: true,
      maxlength: 16,
      type: "money",
      max: 9999999999
    },
    {
      prop: "remarks",
      label: "备注",
      width: "120",
      type: "text",
      maxlength: 500
    },
    {
      prop: "fileRequests",
      label: "附件",
      width: "120",
      doType: "RECOVERY_PAYMENT_DETAIL",
      type: "file"
    }
  ]
});

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  projectName: [{ required: true, message: "请输入项目名称", trigger: "blur" }],
  payAmount: [{ required: true, message: " " }],
  payDate: [{ required: true, message: "请输入付款时间!" }],
  payee: [{ required: true, message: "请输入收款方!" }],
  receivingAccount: [{ required: true, message: "请输入收款方!" }],
  receivingBank: [{ required: true, message: "请输入收款银行!" }],
  payExplain: [{ required: true, message: "请输入付款说明!" }],
  projectId: [{ required: true, message: "请选择项目!" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);
const form = ref<Partial<RecoveryPaymentRequest>>({
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找
const bakdetailList = ref([]);
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await recoveryPayment().findById(drawerProps.value.id);
  detailList.value.data = data.paymentDetailRequests;
  bakdetailList.value = JSON.parse(JSON.stringify(data.paymentDetailRequests));
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const detailListRef = ref();
const deleteFiles = useDialogStore().deleteFiles;
const saveFun = async () => {
  const fileEl = fileRef.value?.tableRulesRef;
  const detailListEl = detailListRef.value?.tableRulesRef;
  try {
    await ruleFormRef.value!.validate();
    if (fileEl) {
      await fileEl!.validate();
    }
    if (detailListEl) {
      await detailListEl!.validate();
    }
  } catch (error) {
    return false;
  }
  if (detailList.value.data.length === 0) {
    ElMessage.warning("付款明细不能为空!");
    return false;
  }
  const detailLists = detailList.value.data.map(item => {
    if (deleteFiles[item.id]) {
      return {
        ...item,
        fileInfoList: [...item.fileInfoList, ...deleteFiles[item.id]]
      };
    } else {
      return {
        ...item
      };
    }
  });
  if (drawerProps.value.payStatus) {
    form.value.payStatus = drawerProps.value.payStatus;
  }
  form.value.paymentDetailRequests = deduplicateArraysById(bakdetailList.value, detailLists);
  form.value.fileRequests = fileRef.value.submitForm;
  return true;
};
const handleSave = async () => {
  const result = await saveFun();
  if (!result) return false;

  try {
    drawerProps.value.title == "新增" ? await recoveryPayment().add(form.value) : recoveryPayment().update(form.value);
    ElMessage.success({ message: `${drawerProps.value.title}付款保存成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

// 提交数据（流程）
const handleSubmit = async () => {
  const result = await saveFun();
  if (!result) return false;

  try {
    await recoveryPayment().submit(form.value);
    ElMessage.success({ message: `${drawerProps.value.title}付款保存成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

onMounted(() => {
  if (drawerProps.value.projectId) {
    form.value!.projectId = drawerProps.value.projectId;
    form.value!.projectName = drawerProps.value.projectName;
  }
  useBaseStore()
    .findEnumByName("PROJECT_STATE")
    .then(res => {
      projectStateList.value = res.filter(item => item.itemCode !== "PROJECT_STATE_01").map(items => items.id);
    });
  findById();
});

const projectStateList = ref();
defineExpose({
  handleSave,
  handleSubmit
});
</script>
