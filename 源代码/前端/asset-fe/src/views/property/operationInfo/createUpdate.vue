<template>
  <div>
    <vz-card title="资产信息">
      <form-table
        :table-data="assetInfo"
        ref="assetInfoRef"
        @on-select-change="chooseAsset"
        @del-row="delRow"
        :readonly="drawerProps.isView"
      ></form-table>
    </vz-card>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="100px"
        scroll-to-error
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="运营标题" prop="operationTitle">
              <el-input v-model="form!.operationTitle" placeholder="请输入运营标题" maxlength="127" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="运营类型" prop="operationType">
              <vz-select
                dict-type="OPERATION_TYPE"
                :dict-value="form!.operationType"
                v-model="form!.operationType"
                placeholder="请选择运营类型"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="运营日期" prop="operationDate">
              <el-date-picker
                @change="changeOperationDate"
                v-model="form!.operationDate"
                type="date"
                placeholder="请选择运营日期"
                class="w100"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="费用金额" prop="costMoney" :inline-message="true">
              <vz-input-unit
                :disabled="true"
                v-model="form!.costMoney"
                :value="form!.costMoney"
                placeholder="请输入费用金额"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="运营内容" prop="operationContent">
              <el-input
                type="textarea"
                :rows="5"
                show-word-limit
                maxlength="500"
                v-model="form!.operationContent"
                placeholder="请输入运营内容"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="资产运营费用">
      <form-table
        ref="assetMoneyRef"
        @money-input="countMoney"
        @on-select-change="chooseAssetMoney"
        :filter-data="assetInfo.data && assetInfo.data.map(item => item.propertyId)"
        :table-data="assetMoney"
        :readonly="drawerProps.isView"
      >
      </form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table
        ref="fileRef"
        :readonly="drawerProps.isView"
        :query-data="{ doId: form!.id, doType: 'OPERATION_INFO' }"
      ></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="operationInfoDrawer">
import { ref, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { OperationInfoRequest } from "@/api/modules/property/operationInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { operationInfo } from "@/api/modules/property/operationInfo/api"; // api
import { propertyStateOptions } from "@/api/modules/property/propertyInfo/interface";
import { assetTransfer } from "@/api/modules/property/assetTransfer/api"; // api
import { assetIncomeDistribution } from "@/api/modules/property/assetIncomeDistribution/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";
import { rules } from "@/views/property/operationInfo/index";
import { deduplicateArraysById } from "@/utils";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

//定义form表单数据
const form = ref<Partial<OperationInfoRequest>>({
  assetIncomeDistributionRequestList: [],
  propertyBillRequestList: []
});
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
      width: "120",
      isRequired: true,
      type: "select",
      enum: propertyStateOptions
    },
    {
      prop: "propertyTag",
      label: "资产标签",
      width: "200",
      isRequired: true,
      type: "select",
      multiple: true,
      dictType: "PROPERTY_TAG"
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "180",
      type: "file"
      // isRequired: true
    },
    {
      prop: "remark",
      label: "备注",
      width: "160",
      type: "text",
      maxlength: 255
    }
  ]
});

//资产收入分配
const assetMoney = ref<any>({
  data: [],
  header: [
    {
      prop: "propertyId",
      label: "资产名称",
      width: "150",
      type: "asset"
    },
    {
      prop: "costType",
      label: "费用类型",
      width: "120",
      type: "select",
      dictType: "ASSET_PAYMENT_TYPE"
    },
    {
      prop: "propertyDate",
      label: "支出日期",
      width: "120",
      type: "date"
    },
    {
      prop: "propertyMoney",
      label: "支出金额(元)",
      width: "120",
      type: "money",
      text: "元",
      max: 999999999999
    },
    {
      prop: "remark",
      label: "备注",
      width: "160",
      type: "text",
      maxlength: 255
    }
  ]
});

//获取资产的分录表
const bakassetInfo = ref();
const getPropertyBill = async (type: string, id: number) => {
  const { data } = await assetTransfer().findPropertyBill(type, id);
  assetInfo.value.data = data;
  bakassetInfo.value = JSON.parse(JSON.stringify(data));
};

//获取资产收入分配表
const bakassetMoney = ref();
const findDistributionFun = async (type: string, id: number) => {
  const { data } = await assetIncomeDistribution().findByDoId(type, id);
  assetMoney.value.data = data;
  bakassetMoney.value = JSON.parse(JSON.stringify(data));
};
//资产信息选择
const mergerdArrayFun = (arr: any, arr1: any) => {
  if (!arr)
    return arr1.map(items => {
      return {
        ...items,
        propertyId: items.id,
        propertyState: items.propertyState,
        fileInfoList: [],
        remark: ""
      };
    });

  const arrPropertyIds = new Set(arr.map(item => item.propertyId));
  const itemsToAdd = arr1
    .filter(item => !arrPropertyIds.has(item.id))
    .map(items => {
      return {
        ...items,
        propertyId: items.id,
        propertyState: items.propertyState,
        fileInfoList: [],
        remark: ""
      };
    });

  const finalArr = [...arr, ...itemsToAdd];
  return finalArr;
};

const chooseAssetMoney = async (val: any, index: number, item: any) => {
  if (item.prop == "propertyId") {
    if (Array.isArray(val) && assetMoney.value.data.length > 0) {
      assetMoney.value.data = mergerdArrayFun(bakassetMoney.value, val);
    } else {
      assetMoney.value.data[index] = {
        propertyId: val.id,
        remark: "",
        costType: "",
        propertyDate: "",
        propertyMoney: 0
      };
    }
  }
};
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

// 选择日期
const changeOperationDate = (val: any) => {
  const data = assetMoney.value.data;
  if (data.length > 0) {
    assetMoney.value.data = data.map(res => ({
      ...res,
      propertyDate: res.propertyDate || val
    }));
  }
};

// 计算分录总数
const countFun = () => {
  //分录的金额合计
  const assetMoneyData = JSON.parse(JSON.stringify(assetMoney.value.data.filter(item => item.operateType !== "DELETE")));
  const totalMoney = assetMoneyData.reduce((accumulator, currentValue) => {
    const money = parseFloat(currentValue.propertyMoney);
    if (!isNaN(money)) {
      return accumulator + money;
    } else {
      return accumulator;
    }
  }, 0);
  return totalMoney;
};

//计算
const countMoney = () => {
  form.value!.costMoney = countFun();
};

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await operationInfo().findById(drawerProps.value.id);
  form.value = data;
  await getPropertyBill("OPERATION_INFO", data.id);
  await findDistributionFun("OPERATION_INFO", data.id);
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const assetInfoRef = ref();

//保存提交的校验

const saveFun = async () => {
  const formEl = assetInfoRef.value?.tableRulesRef;
  const fileFormEl = fileRef.value?.tableRulesRef;
  const withoutDelete = assetInfo.value.data.filter(item => item.operateType !== "DELETE");
  // const assetMoneywithoutDelete = assetMoney.value.data.filter(item => item.operateType !== "DELETE");

  if (withoutDelete.length <= 0) {
    ElMessage.warning("请完善【资产信息表】！");
    return false;
  }
  // if (assetMoneywithoutDelete.length <= 0) {
  //   ElMessage.warning("请完善【资产运营费用配置表】！");
  //   return false;
  // }
  if (form.value!.costMoney && form.value!.costMoney > 999999999999) {
    ElMessage.warning("支出金额合计后已超过限制，请核对后再提交！");
    return false;
  }
  try {
    await ruleFormRef.value!.validate();
    if (formEl) {
      await formEl.validate();
    }
    if (fileFormEl) {
      await fileFormEl.validate();
    }
  } catch (error: any) {
    // 验证失败处理
    const errValues: any = Object.values(error);
    if (errValues && errValues.length > 0) {
      ElMessage.warning(errValues[0][0].message);
    }
    return false; // 表示失败
  }
  const deleteFiles = useDialogStore().deleteFiles;
  assetInfo.value.data = assetInfo.value.data.map(item => {
    if (deleteFiles[item.id]) {
      return {
        ...item,
        fileInfoList: [...item.fileInfoList, ...deleteFiles[item.id]],
        propertyTag: item.propertyTag && item.propertyTag.toString()
      };
    } else {
      return {
        ...item,
        propertyTag: item.propertyTag && item.propertyTag.toString()
      };
    }
  });

  //资产信息
  form.value.propertyBillRequestList = deduplicateArraysById(bakassetInfo.value, assetInfo.value.data);
  //资产运营费用
  form.value.assetIncomeDistributionRequestList = deduplicateArraysById(bakassetMoney.value, assetMoney.value.data);
  form.value.fileInfoList = fileRef.value.submitForm;
  return true;
};

//保存数据
const handleSave = async () => {
  try {
    const result = await saveFun();
    if (!result) return false;
    drawerProps.value.title == "新增" ? await operationInfo().add!(form.value) : await operationInfo().update!(form.value);
    ElMessage.success({ message: `保存运营信息表成功！` });
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
    if (!result) return false;
    await operationInfo().submit(form.value);
    ElMessage.success({ message: `提交运营信息表成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

//资产信息删除
const delRow = () => {
  countMoney();
};

//页面加载时
onMounted(() => {
  findById();
});

//暴漏给父级
defineExpose({
  handleSave,
  handleSubmit
});
</script>
