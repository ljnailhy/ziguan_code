<template>
  <div>
    <vz-card title="资产信息">
      <form-table
        :table-data="assetInfo"
        ref="assetInfoRef"
        @on-select-change="chooseAsset"
        :readonly="drawerProps.isView"
        :filter-data="{
          propertyStateList: ['IDLE', 'SELF_USE', 'LEASED', 'PART_LEASE', 'PART_TRANSFER']
        }"
      ></form-table>
    </vz-card>
    <vz-card title="转让方案">
      <el-form
        ref="ruleFormRef"
        scroll-to-error
        label-width="160px"
        label-suffix=" :"
        :rules="rules"
        :model="form"
        :disabled="drawerProps.isView"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="评估机构" prop="estimateId">
              <vzAgency
                :default-value="form!.estimateId"
                :filter-data="{ agencyType: ASSESSMENT_AGENCY }"
                @handle-ok="handleOk"
                placeholder="请选择评估机构"
              ></vzAgency>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="评估有效期" prop="estimateDate">
              <el-date-picker
                v-model="estimateDateRange"
                type="daterange"
                value-format="x"
                placeholder="请选择评估有效期"
                @change="changeestimateDateRange"
                class="w100"
              />
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="评估有效期截止时间" prop="estimateEndDate">
              <el-date-picker v-model="form!.estimateEndDate" type="date" placeholder="请选择评估有效期截止时间" class="w100" />
            </el-form-item>
          </el-col> -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="评估价" prop="estimateMoney" :inline-message="true">
              <vz-input-unit
                v-model="form!.estimateMoney"
                :value="form!.estimateMoney"
                placeholder="请输入评估价"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="应付代理费用" prop="agencyFee" :inline-message="true">
              <vz-input-unit
                v-model="form!.agencyFee"
                :value="form!.agencyFee"
                placeholder="请输入应付代理费用"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="实付代理费用" prop="realityAgencyFee" :inline-message="true">
              <vz-input-unit
                v-model="form!.realityAgencyFee"
                :value="form!.realityAgencyFee"
                placeholder="请输入实付代理费用"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系人" prop="contacts">
              <el-input v-model="form!.contacts" placeholder="请输入联系人" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系电话" prop="contactsPhone">
              <el-input v-model="form!.contactsPhone" placeholder="请输入联系电话" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="处置方案" prop="programme">
              <el-input
                v-model="form!.programme"
                type="textarea"
                maxlength="500"
                :rows="5"
                show-word-limit
                placeholder="请输入处置方案"
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
        :query-data="{ doId: form!.id, doType: 'ASSET_TRANSFER' }"
      ></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="assetTransferDrawer">
import { ref, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
// import { AssetTransferRequest, AssetTransferDTO } from "@/api/modules/property/assetTransfer/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { assetTransfer } from "@/api/modules/property/assetTransfer/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";
import { rules } from "@/views/property/assetTransfer/index";
import { deduplicateArraysById, mergerdArrayFun } from "@/utils";
import { propertyStateOptions } from "@/api/modules/property/propertyInfo/interface";
import { useBaseStore } from "@/stores/modules/baseInfo";

//引入组件
import vzAgency from "@/components/source/vz-agency.vue";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();
//资产信息
const assetInfo = ref<any>({
  data: [],
  doType: "PROPERTY_BILL",
  header: [
    {
      prop: "propertyId",
      label: "资产名称",
      width: "150",
      type: "asset",
      isRequired: true
    },
    {
      prop: "propertyState",
      label: "资产状态",
      width: "60",
      isRequired: true,
      type: "select",
      selectDisabled: "TRANSFERRED",
      enum: propertyStateOptions
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "120",
      type: "file"
    },
    {
      prop: "remark",
      label: "备注",
      width: "160",
      type: "text"
    }
  ]
});
const estimateDateRange = ref<any>([]);
const form = ref<any>({
  propertyBillRequestList: [],
  documentIntermediaryRequestList: [],
  hangNetworkInfoRequestList: [],
  fileInfoList: [],
  billContractRequestList: [],
  id: undefined // 这只是一个后面没逗号的坑位
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

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
  const { data } = await assetTransfer().findById(drawerProps.value.id);
  form.value = data;
  if (data.estimateDate && data.estimateEndDate) {
    estimateDateRange.value = [data.estimateDate, data.estimateEndDate];
  }
  await getPropertyBill("ASSET_TRANSFER", data.id);
};

//获取资产分录
const bakassetInfo = ref();
const getPropertyBill = async (type: string, id: number) => {
  const { data } = await assetTransfer().findPropertyBill(type, id);
  assetInfo.value.data = data;
  bakassetInfo.value = JSON.parse(JSON.stringify(data));
};

//评估机构确定
const handleOk = (val: EmptyObjectType) => {
  form.value.contacts = val.contacts;
  form.value.contactsPhone = val.contactsPhone;
  form.value!.estimateId = val.id;
};

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

// 保存数据（新增/编辑）
const assetInfoRef = ref();
const fileRef = ref();
const ruleFormRef = ref<FormInstance>();

const saveFun = async () => {
  const formEl = assetInfoRef.value?.tableRulesRef;
  const deleteFiles = useDialogStore().deleteFiles;
  if (assetInfo.value.data.length <= 0) {
    ElMessage.warning("请完善资产信息！");
    return false;
  }
  try {
    await ruleFormRef.value!.validate();
    if (formEl) {
      await formEl.validate();
    }
  } catch (error) {
    return false;
  }

  form.value.fileInfoList = fileRef.value.submitForm; //附件信息
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
  form.value.propertyBillRequestList = deduplicateArraysById(bakassetInfo.value, propertyBillRequestList);
  return true;
};
const handleSave = async () => {
  try {
    const result = await saveFun();
    if (!result) return false;
    drawerProps.value.title == "新增" ? await assetTransfer().add!(form.value) : await assetTransfer().update!(form.value);
    ElMessage.success({ message: `保存资产转让成功！` });
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
  try {
    const result = await saveFun();
    if (!result) return;
    await assetTransfer().submit(form.value);
    ElMessage.success({ message: `提交资产转让成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

//页面加载时
const ASSESSMENT_AGENCY = ref();
onMounted(() => {
  useBaseStore()
    .findEnumByName("AGENCY_TYPE")
    .then(item => {
      ASSESSMENT_AGENCY.value = item.filter(item => item.itemCode == "ASSESSMENT_AGENCY")[0].id;
    });
  findById();
});

//报漏变量给父级
defineExpose({
  handleSave,
  handleSubmit
});
</script>
