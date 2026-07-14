<template>
  <div>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="130px"
        label-suffix=" :"
        scroll-to-error
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <!--              <el-form-item label="项目id" prop="projectId">-->
          <!--                <el-input v-model="form!.projectId" placeholder="请输入项目id" clearable></el-input>-->
          <!--              </el-form-item>-->
          <!--            </el-col>-->
          <!--            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">-->
          <!--              <el-form-item label="项目名称" prop="projectName">-->
          <!--                <el-input v-model="form!.projectName" placeholder="请输入项目名称" clearable></el-input>-->
          <!--              </el-form-item>-->
          <!--            </el-col>-->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="项目名称" prop="projectName">
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

          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="回款律所" prop="lawyerName">
              <vz-law-firm-info
                v-model="form!.lawyerId"
                :default-value="form!.lawyerId"
                placeholder="回款律所"
                @handle-ok="handleOk1"
                select-type="radio"
              ></vz-law-firm-info>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="合计回款金额" prop="collectionAmount" :inline-message="true">
              <vz-input-unit
                v-model="form!.collectionAmount"
                :value="form!.collectionAmount"
                placeholder="请输入合计回款金额"
                :disabled="false"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="回款时间" prop="collectionDate">
              <el-date-picker v-model="form!.collectionDate" type="date" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="回款标记" prop="collectionSign">
              <el-select v-model="form!.collectionSign" clearable class="w100">
                <el-option label="现金" value="CASH"></el-option>
                <el-option label="再担保" value="RE_GUARANTEE"></el-option>
                <el-option label="抵债" value="MORTGAGE"></el-option>
                <el-option label="银票" value="SILVER_BILL"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="回款归属" prop="collectionAscription">
              <el-select v-model="form!.collectionAscription" clearable class="w100">
                <el-option label="中小担" value="SMALL_MEDIUM_DAN"></el-option>
                <el-option label="文旅" value="CULTURAL_TOURISM"></el-option>
                <el-option label="集团" value="GROUP"></el-option>
                <el-option label="核销-中小担" value="VER_SMALL_MEDIUM"></el-option>
                <el-option label="核销-集团" value="VER_GROUP"></el-option>
                <el-option label="核销-文旅" value="VER_CULTURAL_TOURISM"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="是否历史代偿" prop="isCollectionHistorical">
              <el-switch v-model="form!.isCollectionHistorical" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="回款类型" prop="collectionType">
              <el-select v-model="form!.collectionType" clearable class="w100">
                <el-option label="自主追偿" value="OWN"></el-option>
                <el-option label="委托追偿" value="ENTRUST"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="回款摘要" prop="collectionSummary">
              <el-input
                v-model="form!.collectionSummary"
                type="textarea"
                show-word-limit
                maxlength="500"
                :rows="5"
                placeholder="请输入回款摘要"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="代偿金额(元)" prop="compensatoryCash" :inline-message="true">
              <vz-input-unit
                v-model="form!.compensatoryCash"
                :value="form!.compensatoryCash"
                placeholder="请输入代偿金额"
                text="元"
                :disabled="true"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="违约金" prop="defaultCash">
              <vz-input-unit
                v-model="form!.defaultCash"
                :value="form!.defaultCash"
                placeholder="请输入违约金"
                text="元"
                :disabled="true"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="利息" prop="interest">
              <vz-input-unit
                v-model="form!.interest"
                :value="form!.interest"
                placeholder="请输入利息"
                text="元"
                clearable
                :disabled="true"
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="其他费用" prop="otherFee">
              <vz-input-unit
                v-model="form!.otherFee"
                :value="form!.otherFee"
                placeholder="请输入其他费用"
                text="元"
                clearable
                :disabled="true"
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <!--          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">-->
          <!--            <el-form-item label="备注" prop="remarks">-->
          <!--              <el-input-->
          <!--                v-model="form!.remarks"-->
          <!--                type="textarea"-->
          <!--                show-word-limit-->
          <!--                maxlength="500"-->
          <!--               :rows="5"-->
          <!--                placeholder="请输入备注"-->
          <!--                clearable-->
          <!--              ></el-input>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="回款明细">
      <form-table
        ref="detailListRef"
        @on-select-change="onSelectChange"
        :table-data="detailList"
        :readonly="drawerProps.isView"
        :filter-data="{ doType: 'PROJECT_INFO', doId: form!.projectId == null ? 123 : form!.projectId, billType: 'REVE' }"
        @money-input="moneyBlur"
        @del-row="moneyBlur"
      ></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table
        ref="fileRef"
        :readonly="drawerProps.isView"
        :query-data="{ doType: 'RECOVERY_PAYMENT_COLLECTION', doId: form!.id }"
      ></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="recoveryPaymentCollectionDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import {
  RecoveryPaymentCollectionRequest,
  collectionDetailTypeOptions
} from "@/api/modules/recovery/recoveryPaymentCollection/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { recoveryPaymentCollection } from "@/api/modules/recovery/recoveryPaymentCollection/api";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { deduplicateArraysById } from "@/utils";

//组件
import VzProjectInfo from "@/components/source/vzProjectInfo.vue";
import VzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";
import VzCard from "@/views/compensatory/projectInfo/component/VzCard.vue";
import { useBaseStore } from "@/stores/modules/baseInfo";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const moneyBlur = async () => {
  form.value.collectionAmount = 0;
  form.value.collectionAmount = detailList.value.data.reduce((accumulator, currentItem) => {
    if (currentItem.operateType === "DELETE") {
      return accumulator;
    }
    let amount = currentItem.collectionDetailAmount;
    if (typeof amount === "string") {
      amount = parseFloat(amount);
    }
    amount = typeof amount === "number" ? amount : 0;
    return accumulator + amount;
  }, 0);
  // 利息
  form.value.interest = 0;
  // 其他费用
  form.value.otherFee = 0;
  // 违约金
  form.value.defaultCash = 0;
  // 代偿金额
  form.value.compensatoryCash = 0;
  if (detailList.value.data.length > 0) {
    for (let dataKey in detailList.value.data) {
      if (detailList.value.data.operateType === "DELETE") {
        continue;
      }
      const currentItem = detailList.value.data[dataKey];
      let type = currentItem.collectionDetailType;
      let amount = currentItem.collectionDetailAmount;
      if (type === "INTEREST") {
        if (typeof amount === "string") {
          amount = parseFloat(amount);
        }
        amount = typeof amount === "number" ? amount : 0;
        form.value.interest = form.value.interest + amount;
      }
      if (type === "OTHER_FEE") {
        if (typeof amount === "string") {
          amount = parseFloat(amount);
        }
        amount = typeof amount === "number" ? amount : 0;
        form.value.otherFee = form.value.otherFee + amount;
      }
      if (type === "DEFAULT_CASH") {
        if (typeof amount === "string") {
          amount = parseFloat(amount);
        }
        amount = typeof amount === "number" ? amount : 0;
        form.value.defaultCash = form.value.defaultCash + amount;
      }
      if (type === "COMPENSATORY_CASH") {
        if (typeof amount === "string") {
          amount = parseFloat(amount);
        }
        amount = typeof amount === "number" ? amount : 0;
        form.value.compensatoryCash = form.value.compensatoryCash + amount;
      }
    }
  }
  if (typeof form.value.collectionAmount === "number") {
    form.value.collectionAmount = parseFloat(form.value.collectionAmount.toFixed(2));
  }
  if (typeof form.value.interest === "number") {
    form.value.interest = parseFloat(form.value.interest.toFixed(2));
  }
  if (typeof form.value.otherFee === "number") {
    form.value.otherFee = parseFloat(form.value.otherFee.toFixed(2));
  }
  if (typeof form.value.defaultCash === "number") {
    form.value.defaultCash = parseFloat(form.value.defaultCash.toFixed(2));
  }
  if (typeof form.value.compensatoryCash === "number") {
    form.value.compensatoryCash = parseFloat(form.value.compensatoryCash.toFixed(2));
  }
};
const detailList = ref<any>({
  data: [],
  header: [
    {
      prop: "collectionDetailType",
      label: "回款类型",
      width: "120",
      isRequired: true,
      type: "select",
      enum: collectionDetailTypeOptions
    },
    {
      prop: "collectionDetailAmount",
      label: "回款金额(元)",
      width: "100",
      isRequired: true,
      type: "money",
      max: 9999999999
    },
    {
      prop: "reveId",
      label: "反担保措施",
      width: "100",
      type: "reve",
      filterData: { billType: "REVE" }
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
      doType: "RECOVERY_PAYMENT_COLLECTION_DETAIL",
      type: "file"
    }
  ]
});
const onSelectChange = (val: any, index: number, item: EmptyObjectType, row: EmptyObjectType) => {
  if (item.type === "reve") {
    row[item.prop] = val.reveMeasure;
    row.reveId = val.id;
  }
  if (item.type === "select") {
    moneyBlur();
  }
};
const rules = reactive<any>({
  projectId: [{ required: true, message: "请输入项目id", trigger: "blur" }],
  projectName: [{ required: true, message: "请输入项目名称", trigger: "blur" }],
  collectionAmount: [{ required: true, message: "请输入合计金额", trigger: "blur" }],
  collectionDate: [{ required: true, message: "请选择回款时间", type: "date", trigger: "change" }],
  collectionSign: [{ required: true, message: "请选择回款标记", trigger: "change" }],
  collectionType: [{ required: true, message: "请选择回款类型", trigger: "change" }],
  collectionAscription: [
    {
      required: true,
      message: "请选择回款归属",
      trigger: "change"
    }
  ],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});
const handleOk1 = (obj: any) => {
  form.value = {
    ...form.value,
    lawyerName: obj.lawyerName,
    lawyerId: obj.id
  };
};

const handleOk = (obj: any) => {
  debugger;
  form.value = {
    projectName: obj.projectName,
    projectId: obj.id,
    isCollectionHistorical: obj.projectIsHistory
  };
  detailList.value.data = [];
};

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<RecoveryPaymentCollectionRequest>>({
  /** 回款标记 CASH:现金 RE_GUARANTEE:再担保 MORTGAGE:抵债 SILVER_BILL银票 */
  // collectionSign: CollectionSignEnum.CASH,
  /** 回款归属 	GROUP集团	CULTURAL_TOURISM文旅 	SMALL_MEDIUM_DAN中小担 	VER_SMALL_MEDIUM 核销-中小担 	VER_GROUP 核销-集团	VER_CULTURAL_TOURISM 核销-文旅 */
  // collectionAscription: CollectionAscriptionEnum.SMALL_MEDIUM_DAN,
  /** 是否历史代偿 1 是 0 否 */
  isCollectionHistorical: false,
  id: undefined
});

// 单个查找
const bakdetailList = ref([]);
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await recoveryPaymentCollection().findById(drawerProps.value.id);
  if (data.paymentCollectionDetailRequests) {
    detailList.value.data = data.paymentCollectionDetailRequests;
    bakdetailList.value = JSON.parse(JSON.stringify(data.paymentCollectionDetailRequests));
  }
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
  if (form.value.collectionAmount === 0.0) {
    ElMessage.warning("回款金额需大于0");
    return false;
  }
  if (
    detailList.value.data.length !== 0 &&
    form.value.collectionAmount !== parseFloat(detailList.value.data.reduce((a, b) => a + b.collectionDetailAmount, 0).toFixed(2))
  ) {
    ElMessage.warning("回款明细合计金额与合计回款金额不同!");
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
  form.value.fileRequests = fileRef.value.submitForm;
  if (drawerProps.value.collectionStatus) {
    form.value.collectionStatus = drawerProps.value.collectionStatus;
  }
  form.value.paymentCollectionDetailRequests = deduplicateArraysById(bakdetailList.value, detailLists);
  return true;
};

const handleSave = async () => {
  const result = await saveFun();
  if (!result) return false;
  try {
    drawerProps.value.title == "新增"
      ? await recoveryPaymentCollection().add(form.value)
      : recoveryPaymentCollection().update(form.value);
    ElMessage.success({ message: `保存回款成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    return false;
  }
};

// 提交数据（流程）
const handleSubmit = async () => {
  const result = await saveFun();
  if (!result) return false;
  try {
    await recoveryPaymentCollection().submit(form.value);
    ElMessage.success({ message: `提交回款成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
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
