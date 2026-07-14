<template>
  <div>
    <vz-card title="资产信息">
      <form-table
        :table-data="assetInfo"
        ref="assetInfoRef"
        :filter-data="{ propertyStateList: ['IDLE', 'PART_LEASE', 'PART_TRANSFER'] }"
        @on-select-change="chooseAsset"
        :readonly="drawerProps.isView"
      ></form-table>
    </vz-card>
    <vz-card title="租赁方案">
      <el-form
        ref="ruleFormRef"
        label-width="130px"
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="是否评估" prop="isEstimate">
              <el-switch v-model="form!.isEstimate" inline-prompt active-text="是" inactive-text="否" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="评估机构" prop="estimateId" v-if="form!.isEstimate == true">
              <!-- todo  写死了id后面改 -->
              <vzAgency
                :default-value="form!.estimateId"
                :filter-data="ASSESSMENT_AGENCY"
                @handle-ok="handleOk"
                placeholder="请选择评估机构"
              ></vzAgency>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.isEstimate == true">
            <el-form-item label="代理费用" prop="agencyFee" :inline-message="true">
              <vz-input-unit
                v-model="form!.agencyFee"
                :value="form!.agencyFee"
                placeholder="请输入代理费用"
                text="元"
                :max="99999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.isEstimate == true">
            <el-form-item label="年租金评估价" prop="estimateMoney" :inline-message="true">
              <vz-input-unit
                v-model="form!.estimateMoney"
                :value="form!.estimateMoney"
                placeholder="请输入年租金评估价"
                text="元"
                :max="99999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.isEstimate == true">
            <el-form-item label="评估有效期" prop="estimateDate">
              <el-date-picker
                v-model="estimateDateRange"
                type="daterange"
                value-format="x"
                @change="changeestimateDateRange"
                placeholder="请选择评估有效期"
                class="w100"
              />
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="评估有效期截止时间" prop="estimateEndDate">
              <el-date-picker v-model="form!.estimateEndDate" type="date" placeholder="请选择评估有效期截止时间" class="w100" />
            </el-form-item>
          </el-col> -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.isEstimate == true">
            <el-form-item label="联系人" prop="contacts">
              <el-input v-model="form!.contacts" placeholder="请输入联系人" clearable maxlength="11"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.isEstimate == true">
            <el-form-item label="联系电话" prop="contactsPhone">
              <el-input v-model="form!.contactsPhone" maxlength="11" placeholder="请输入联系电话" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="租赁方案" prop="disposeProgramme">
              <el-input
                type="textarea"
                :rows="7"
                v-model="form!.disposeProgramme"
                placeholder="请输入租赁方案"
                clearable
                show-word-limit
              ></el-input>
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
                show-word-limit
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
        :query-data="{ doId: form!.id, doType: 'LEASE_INFO' }"
      ></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="leaseInfoDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { LeaseInfoRequest, PaymentCycleEnum } from "@/api/modules/property/leaseInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { leaseInfo } from "@/api/modules/property/leaseInfo/api"; // api
import { propertyStateOptions } from "@/api/modules/property/propertyInfo/interface";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { assetTransfer } from "@/api/modules/property/assetTransfer/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";
import { mergerdArrayFun } from "@/utils";
import { deduplicateArraysById } from "@/utils";

//引入组件
import vzAgency from "@/components/source/vz-agency.vue";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  estimateId: [
    {
      required: true,
      message: "请选择评估机构",
      trigger: "change"
    }
  ],
  agencyFee: [
    {
      required: true,
      message: "请输入代理费用",
      trigger: "blur"
    }
  ],
  estimateMoney: [
    {
      required: true,
      message: "请输入评估价",
      trigger: "blur"
    }
  ],
  estimateDate: [
    {
      required: true,
      message: "请选择评估有效期开始时间",
      trigger: "change"
    }
  ],
  estimateEndDate: [
    {
      required: true,
      message: "请选择评估有效期截止时间",
      trigger: "change"
    }
  ],
  disposeProgramme: [
    {
      required: false,
      message: "请输入租赁方案",
      trigger: "blur"
    }
  ]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const agencyFilterData = ref<any>();
const estimateDateRange = ref<any>([]);
const form = ref<Partial<LeaseInfoRequest>>({
  /** 缴纳周期'YEAR'：年,'HALF_YEAR'：半年,'SEASON'：季,'MONTH'：月,'DISPOSABLE'：一次性,'OTHER'：其他 */
  paymentCycle: PaymentCycleEnum.YEAR,
  propertyBillRequestList: [],
  documentIntermediaryRequestList: [],
  billContractRequestList: [],
  fileInfoList: [],
  id: undefined // 这只是一个后面没逗号的坑位
});

const changeestimateDateRange = (val: any) => {
  if (val && val.length) {
    form.value.estimateDate = estimateDateRange.value[0];
    form.value.estimateEndDate = estimateDateRange.value[1];
  } else {
    form.value.estimateDate = "";
    form.value.estimateEndDate = "";
  }
};
// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await leaseInfo().findById(drawerProps.value.id);
  await getPropertyBill("LEASE_INFO", data.id);
  form.value = data;
  if (data.estimateDate && data.estimateEndDate) {
    estimateDateRange.value = [data.estimateDate, data.estimateEndDate];
  }
};

//评估机构确定
const handleOk = (val: EmptyObjectType) => {
  form.value!.estimateId = val.id;
  form.value!.contacts = val.contacts;
  form.value!.contactsPhone = val.contactsPhone;
};

const ruleFormRef = ref<FormInstance>();
const assetInfoRef = ref(); //资产信息
const fileRef = ref();

const saveFun = async () => {
  const formEl = assetInfoRef.value?.tableRulesRef;
  const deleteFiles = useDialogStore().deleteFiles;
  if (assetInfo.value.data.length <= 0) {
    // useDialogStore().setValidationErrorMessage("请完善资产信息!");
    ElMessage.warning("请完善资产信息！");
    return false;
  }

  try {
    // 表单验证
    if (formEl) {
      await Promise.all([formEl.validate(), ruleFormRef.value!.validate()]);
    } else {
      await Promise.all([ruleFormRef.value!.validate()]);
    }
  } catch (error: any) {
    // 验证失败处理
    // const errValues: any = Object.values(error);
    // if (errValues && errValues.length > 0) {
    //   useDialogStore().setValidationErrorMessage(errValues[0][0].message);
    // } else {
    //   useDialogStore().setValidationErrorMessage("表单验证失败，请检查表单字段!");
    // }

    return false; // 表示失败
  }

  const propertyBillRequestList = assetInfo.value.data.map(item => {
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
  //资产信息
  form.value.propertyBillRequestList = deduplicateArraysById(bakassetInfo.value, propertyBillRequestList);
  form.value.fileInfoList = fileRef.value.submitForm; //附件信息
  return true;
};
// 保存数据（新增/编辑）

const handleSave = async () => {
  try {
    const result = await saveFun();
    if (!result) return;
    if (form.value.isEstimate !== true) {
      form.value.estimateId = undefined;
      form.value.agencyFee = undefined;
      form.value.estimateMoney = undefined;
      form.value.estimateDate = undefined;
      form.value.estimateEndDate = undefined;
      form.value.contacts = undefined;
      form.value.contactsPhone = undefined;
    }
    drawerProps.value.api ? await drawerProps.value.api!(form.value) : await leaseInfo().update(form.value);
    ElMessage.success({ message: `保存成功！` });
    // useDialogStore().setValidationErrorMessage("pass");
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    // useDialogStore().setValidationErrorMessage("校验失败，请检查表单字段!");
    console.log(error);
    return false;
  }
};

// 提交数据（流程）
const handleSubmit = async () => {
  try {
    const result = await saveFun();
    if (!result) return;
    if (form.value.isEstimate !== true) {
      form.value.estimateId = undefined;
      form.value.agencyFee = undefined;
      form.value.estimateMoney = undefined;
      form.value.estimateDate = undefined;
      form.value.estimateEndDate = undefined;
      form.value.contacts = undefined;
      form.value.contactsPhone = undefined;
    }
    await leaseInfo().submit(form.value);
    ElMessage.success({ message: `提交成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

// 资产信息
const assetInfo = ref<any>({
  data: [],
  header: [
    {
      prop: "propertyId",
      label: "资产名称",
      width: "200",
      type: "asset",
      isRequired: true
    },
    {
      prop: "propertyState",
      label: "资产状态",
      width: "120",
      isRequired: true,
      type: "select",
      disabled: true,
      enum: propertyStateOptions
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "180",
      type: "file",
      doType: "PROPERTY_BILL"
      // isRequired: true
    },
    {
      prop: "remark",
      label: "备注",
      width: "160",
      type: "text",
      maxlength: 100
    }
  ]
});

//资产信息选择
const chooseAsset = async (val: any, index: number, item: any) => {
  const bakInfo = JSON.parse(JSON.stringify(assetInfo.value.data));

  if (item.prop == "propertyId") {
    if (Array.isArray(val) && assetInfo.value.data.length > 0) {
      assetInfo.value.data = mergerdArrayFun(bakassetInfo.value, val);
    } else {
      const containsTarget = bakInfo.some(item => item.propertyId === val.id);
      const currentValue = bakInfo[index];
      if (containsTarget) {
        if (currentValue.propertyId !== val.id) {
          assetInfo.value.data[index] = {
            ...bakInfo[index]
          };
        }
        return ElMessage.warning("已有相同的资产信息，请核对后再选择！");
      } else {
        assetInfo.value.data[index] = {
          propertyId: val.id,
          propertyState: val.propertyState,
          fileInfoList: [],
          remark: ""
        };
      }
    }
  }
};

//获取资产分录
const bakassetInfo = ref();
const getPropertyBill = async (type: string, id: number) => {
  const { data } = await assetTransfer().findPropertyBill(type, id);
  assetInfo.value.data = data;
  bakassetInfo.value = JSON.parse(JSON.stringify(data));
};

const ASSESSMENT_AGENCY = ref();
onMounted(() => {
  useBaseStore()
    .findEnumByName("AGENCY_TYPE")
    .then(item => {
      item.forEach(dict => {
        if (dict.itemCode == "INTERMEDIARY_AGENCY") {
          agencyFilterData.value = { agencyType: dict.id };
        } else {
          ASSESSMENT_AGENCY.value = { agencyType: dict.id };
        }
      });
    });

  findById();
});

defineExpose({
  handleSave,
  handleSubmit
});
</script>
