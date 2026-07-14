<template>
  <div>
    <vz-card title="资产信息">
      <form-table
        :table-data="assetInfo"
        ref="assetInfoRef"
        @on-select-change="chooseAsset"
        :readonly="drawerProps.isView"
      ></form-table>
    </vz-card>
    <vz-card title="转让方案">
      <el-form
        ref="ruleFormRef"
        scroll-to-error
        label-width="130px"
        label-suffix=" :"
        :rules="rules"
        :model="form"
        :disabled="drawerProps.isView"
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="评估机构" prop="estimateId">
              <vzAgency :default-value="form!.estimateId" @handle-ok="handleOk" placeholder="请选择评估机构"></vzAgency>
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
                :disabled="drawerProps.isView"
              />
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="评估有效期开始时间" prop="estimateDate">
              <el-date-picker v-model="form!.estimateDate" type="date" placeholder="请选择评估有效期开始时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
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
    <vz-card title="中介信息">
      <form-table
        ref="intermediaryRef"
        :table-data="intermediaryInfo"
        :filter-data="{ agencyType: INTERMEDIARY_AGENCY }"
        :readonly="drawerProps.isView"
      >
        <!-- <template #operation="scope">
          <el-link type="warning" @click="onOpenCustomer(scope)">登记客户线索</el-link>
        </template> -->
      </form-table>
      <el-dialog v-model="dialogVisible" title="登记客户线索" width="800">
        <form-table :table-data="customerArray" @on-select-change="selectCustomer" :readonly="drawerProps.isView"></form-table>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="confirmCustomer"> 确定 </el-button>
          </div>
        </template>
      </el-dialog>
    </vz-card>
    <vz-card title="准客户信息">
      <form-table :table-data="customerArray" :readonly="drawerProps.isView"> </form-table>
    </vz-card>
    <vz-card title="挂网信息">
      <form-table ref="networkInfoRef" :table-data="networkInfo" :readonly="drawerProps.isView"></form-table>
    </vz-card>
    <vz-card title="转让信息">
      <el-form
        ref="ruleFormRef"
        scroll-to-error
        label-width="130px"
        label-suffix=" :"
        :rules="rules"
        :model="form"
        :disabled="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="受让人" prop="customId">
              <vzCustomerInfo
                v-model:value="form!.customId"
                :default-value="form!.customId"
                title="请选择受让人"
              ></vzCustomerInfo>
              <!-- <el-input v-model="form!.customId" placeholder="请输入受让人" clearable></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="成交价" prop="dealMoney" :inline-message="true">
              <vz-input-unit
                v-model="form!.dealMoney"
                :value="form!.dealMoney"
                placeholder="请输入成交价"
                @change="changeDealMoney"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="成交日期" prop="dealDate">
              <el-date-picker
                @change="changeDealMoney"
                v-model="form!.dealDate"
                type="date"
                value-format="x"
                placeholder="请选择成交日期"
                class="w100"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="税费" prop="taxation" :inline-message="true">
              <vz-input-unit
                v-model="form!.taxation"
                :value="form!.taxation"
                placeholder="请输入税费"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="转让日期" prop="transferOwnershipDate">
              <el-date-picker v-model="form!.transferOwnershipDate" type="date" placeholder="请选择转让日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="是否挂网" prop="isAgainAuction">
              <el-switch v-model="form.isAgainAuction" inline-prompt active-text="是" inactive-text="否" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="form!.remark"
                :rows="5"
                type="textarea"
                maxlength="500"
                show-word-limit
                placeholder="请输入备注"
                clearable
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="产权信息">
      <form-table ref="detailListRef" :table-data="propertyRightInfoList" :readonly="drawerProps.isView"></form-table>
    </vz-card>
    <!-- <vz-card title="资产转让分摊">
      <form-table
        ref="assetMoneyRef"
        @money-input="countMoney"
        :show-button="drawerProps.readonly"
        :table-data="assetMoney"
        :filter-data="assetInfo.data && assetInfo.data.map(item => item.propertyId)"
        :readonly="drawerProps.isView"
      ></form-table>
    </vz-card> -->
    <vz-card title="合同信息">
      <form-table
        :table-data="contractInfoArray"
        @on-select-change="onSelectChange"
        :readonly="drawerProps.isView"
        :filter-data="{ contractType: CONTRACT_TYPE_002 }"
      ></form-table>
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
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
// import { AssetTransferRequest, AssetTransferDTO } from "@/api/modules/property/assetTransfer/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { assetTransfer } from "@/api/modules/property/assetTransfer/api"; // api
import { billContract } from "@/api/modules/compensatory/billContract/api"; // api
import { propertyStateOptions } from "@/api/modules/property/propertyInfo/interface";
// import { intermediaryCustomerLead } from "@/api/modules/property/intermediaryCustomerLead/api"; // api
import { contractInfo } from "@/api/modules/source/contractInfo/api"; // 合同信息
import { assetIncomeDistribution } from "@/api/modules/property/assetIncomeDistribution/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";
// import { customerInfo } from "@/api/modules/source/customerInfo/api"; // 客户信息
import { deduplicateArraysById, mergerdArrayFun } from "@/utils";
import { useBaseStore } from "@/stores/modules/baseInfo";
//引入组件
import vzAgency from "@/components/source/vz-agency.vue";
import vzCustomerInfo from "@/components/source/vzCustomerInfo.vue";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  estimateId: [{ required: true, trigger: "change", message: "请选择评估机构" }],
  estimateMoney: [{ required: true, trigger: "blur", message: "请输入评估价格" }],
  estimateDate: [{ required: true, trigger: "change", message: "请选择评估日期" }]
});

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
      enum: propertyStateOptions
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "120",
      type: "file"
      // isRequired: true
    },
    {
      prop: "remark",
      label: "备注",
      width: "160",
      type: "text"
    }
  ]
});
//中介信息
const intermediaryInfo = ref<any>({
  data: [],
  header: [
    {
      prop: "intermediaryId",
      label: "中介名称",
      width: "150",
      type: "agency",
      isRequired: true
    },
    {
      prop: "contacts",
      label: "联系人",
      width: "200",
      type: "text",
      maxlength: 50
    },
    {
      prop: "contactsNumber",
      label: "联系电话",
      width: "200",
      type: "phone",
      maxlength: 11
    },
    {
      prop: "remark",
      label: "备注",
      width: "160",
      type: "text"
    }
  ]
});
//客户线索
const customerArray = ref<any>({
  data: [],
  header: [
    {
      prop: "customId",
      label: "客户名称",
      width: 160,
      type: "customer"
    },
    {
      prop: "customPhone",
      label: "联系电话",
      width: "200",
      type: "phone",
      maxlength: 11
    },
    {
      prop: "propertyId",
      label: "推荐中介",
      width: "250",
      isRequired: true,
      type: "agency"
    },
    {
      prop: "protectDate",
      label: "推荐客户保护期",
      width: "250",
      type: "daterange"
    },
    {
      prop: "remark",
      label: "备注",
      width: "200",
      type: "text"
    }
  ]
});
//挂网信息
const networkInfo = ref<any>({
  data: [],
  header: [
    {
      prop: "hangNetworkType",
      label: "类型",
      width: "150",
      type: "select",
      dictType: "HANG_NETWORK_TYPE",
      isRequired: true
    },
    {
      prop: "hangNetworkDate",
      label: "挂网时间",
      width: "120",
      type: "date"
    },
    {
      prop: "hangNetworkMoney",
      label: "挂网价格/万元",
      width: "120",
      type: "money"
    },
    {
      prop: "hangNetworkQuotation",
      label: "摘牌价格/万元",
      width: "120",
      type: "money"
    },
    {
      prop: "hangNetworkState",
      label: "状态",
      type: "select",
      dictType: "HANG_NETWORK_STATE",
      width: "120"
    },
    {
      prop: "remark",
      label: "备注",
      width: "160",
      type: "text"
    }
  ]
});
//合同信息
const contractInfoArray = ref<any>({
  data: [],
  header: [
    {
      prop: "contractId",
      label: "合同名称",
      width: "250",
      type: "contract",
      filterData: ""
    },
    {
      prop: "contractCode",
      label: "合同编号",
      width: "160",
      disabled: true
    },
    {
      prop: "contractMoney",
      label: "合同金额",
      type: "money",
      width: 160,
      disabled: true
    },
    {
      prop: "signingDate",
      label: "签约时间",
      width: "120",
      type: "date",
      disabled: true
    },
    {
      prop: "startDate",
      label: "开始时间",
      width: "120",
      type: "date",
      disabled: true
    },
    {
      prop: "endDate",
      label: "结束时间",
      width: "120",
      type: "date",
      disabled: true
    }
  ]
});
//资产费用分摊
const assetMoney = ref<any>({
  data: [],
  header: [
    {
      prop: "propertyId",
      label: "资产名称",
      width: "150",
      type: "asset",
      disabled: true
    },
    {
      prop: "propertyDate",
      label: "转让日期",
      width: "120",
      type: "date"
    },
    {
      prop: "propertyMoney",
      label: "转让金额",
      width: "120",
      type: "money"
    }
  ]
});
const propertyRightInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "propertyCode",
      label: "权证号/编号",
      width: "200",
      type: "propertyRightInfo",
      isRequired: true
    },
    {
      prop: "assetUnitState",
      label: "资产单元状态",
      width: "120",
      maxlength: 127,
      type: "select",
      isRequired: true,
      dictType: "ASSET_UNIT_STATE"
      // , enum: () => useBaseStore().findEnumByName("ASSET_UNIT_STATE")
    },
    {
      prop: "originalValue",
      label: "资产原值",
      width: "150",
      type: "money",
      maxlength: 127,
      disabled: true
    },
    {
      prop: "area",
      label: "面积(m²)",
      width: "120",
      type: "money",
      disabled: true
    },
    {
      prop: "propertyOwner",
      label: "产权人名称",
      width: "150",
      maxlength: 50,
      disabled: true
    },
    {
      prop: "propertyEndDate",
      label: "权证到期日",
      width: "160",
      type: "date",
      disabled: true
    },
    {
      prop: "propertyTransferOwnership",
      label: "资产登记日期",
      type: "date",
      width: "160",
      disabled: true
    },
    {
      prop: "assetUse",
      label: "资产用途",
      width: "150",
      maxlength: 127,
      disabled: true
    },
    {
      prop: "address",
      label: "坐落",
      width: "200",
      maxlength: 127,
      disabled: true
    },
    {
      prop: "remark",
      label: "备注",
      width: "300",
      maxlength: 127
    }

    // {
    //   prop: "landUseNature",
    //   label: "用地性质",
    //   width: "140",
    //   dictType: "LAND_USE_NATURE",
    //   // enum: () => useBaseStore().findEnumByName("LAND_USE_NATURE"),
    //   maxlength: 127
    // },
  ]
});
// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  try {
    const { data } = await assetTransfer().findById(drawerProps.value.id);
    form.value = data;
    if (data.intermediaryCustomerLeadRequestList) {
      customerArray.value.data = data.intermediaryCustomerLeadRequestList.map(item => {
        return {
          ...item,
          protectDate: item.startDate && item.endDate ? [item.startDate, item.endDate] : []
        };
      });
    }
    if (data.propertyRightInfoRequests) {
      propertyRightInfoList.value.data = data.propertyRightInfoRequests;
    }
    if (data.estimateDate && data.estimateEndDate) {
      estimateDateRange.value = [data.estimateDate, data.estimateEndDate];
    }
    const handleSubOperation = async (operation, ...args) => {
      try {
        await operation(...args);
      } catch (err) {
        console.error(`${operation.name} failed:`, err);
      }
    };
    await handleSubOperation(getPropertyBill, "ASSET_TRANSFER", data.id);
    await handleSubOperation(getIntermediary, "ASSET_TRANSFER", data.id);
    await handleSubOperation(getHangNetwork, "ASSET_TRANSFER", data.id);
    await handleSubOperation(getBillContract, "ASSET_TRANSFER", data.id);
    await handleSubOperation(findDistributionFun, "ASSET_TRANSFER", data.id);
  } catch (err) {
    console.error("findById failed:", err);
  }
};

const estimateDateRange = ref<any>([]);
const changeestimateDateRange = (val: any) => {
  if (val && val.length) {
    form.value.estimateDate = estimateDateRange.value[0];
    form.value.estimateEndDate = estimateDateRange.value[1];
  } else {
    form.value.estimateDate = "";
    form.value.estimateEndDate = "";
  }
};

//获取资产分录
const bakassetInfo = ref([]);
const getPropertyBill = async (type: string, id: number) => {
  const { data } = await assetTransfer().findPropertyBill(type, id);
  assetInfo.value.data = data;
  bakassetInfo.value = JSON.parse(JSON.stringify(data));
};

//获取中介分录
const bakintermediaryInfo = ref([]);
const getIntermediary = async (type: string, id: number) => {
  const { data } = await assetTransfer().findIntermediary(type, id);
  // for (const element of data) {
  //   const res = await intermediaryCustomerLead().findByDoId(element.id, "DOCUMENT_INTERMEDIARY");
  //   if (res.data && res.data.length) {
  //     const res1 = await customerInfo().batch(res.data.length && res.data.map(item => item.customId));
  //     element.intermediaryCustomerLeadRequestList = res1.data.map(items => {
  //       return {
  //         ...items,
  //         customId: items.id
  //       };
  //     });
  //   }
  // }
  intermediaryInfo.value.data = data;
  bakintermediaryInfo.value = JSON.parse(JSON.stringify(data));
};

//获取挂网分录
const baknetworkInfo = ref([]);
const getHangNetwork = async (type: string, id: number) => {
  const { data } = await assetTransfer().findHangNetwork(type, id);
  networkInfo.value.data = data;
  baknetworkInfo.value = JSON.parse(JSON.stringify(data));
};

//资产收入分配
const bakassetMoney = ref([]);
const findDistributionFun = async (type: string, id: number) => {
  const { data } = await assetIncomeDistribution().findByDoId(type, id);
  assetMoney.value.data = data;
  bakassetMoney.value = JSON.parse(JSON.stringify(data));
};

const changeDealMoney = () => {
  if (assetInfo.value.data.length) {
    assetMoney.value.data = assetInfo.value.data.map(res => {
      return {
        propertyId: res.propertyId,
        propertyMoney: form.value!.dealMoney / assetInfo.value.data.length,
        propertyDate: form.value!.dealDate
      };
    });
  }
};
//获取合同分录
const bakcontractInfoArray = ref([]);
const getBillContract = async (type: string, id: number | string) => {
  const { data } = await billContract().findByDoId(id, type);
  for (const element of data) {
    const res = await contractInfo().findById(element.contractId);
    element.startDate = res.data.startDate;
    element.endDate = res.data.endDate;
    element.contractCode = res.data.contractCode;
    element.contractMoney = res.data.contractMoney;
    element.signingDate = res.data.signingDate;
  }

  contractInfoArray.value.data = data;
  bakcontractInfoArray.value = JSON.parse(JSON.stringify(data));
};

//评估机构确定
const handleOk = (val: EmptyObjectType) => {
  form.value!.estimateId = val.id;
};

//合同信息确定
const onSelectChange = (val: any, index: number) => {
  contractInfoArray.value.data[index] = {
    contractId: val.id,
    contractCode: val.contractCode,
    contractMoney: val.contractMoney,
    signingDate: val.signingDate,
    startDate: val.startDate,
    endDate: val.endDate
  };
};

//客户线索
const dialogVisible = ref(false);
const currentCustomerIndex = ref(0);
// const onOpenCustomer = (scope: any) => {
//   currentCustomerIndex.value = scope.index;
//   if (scope.row.intermediaryCustomerLeadRequestList && scope.row.intermediaryCustomerLeadRequestList.length) {
//     customerArray.value.data = scope.row.intermediaryCustomerLeadRequestList;
//   } else {
//     customerArray.value.data = [];
//   }
//   dialogVisible.value = true;
// };

// 计算分录总数
// const countFun = () => {
//   //分录的金额合计
//   const assetMoneyData = JSON.parse(JSON.stringify(assetMoney.value.data));
//   const totalMoney = assetMoneyData.reduce((accumulator, currentValue) => {
//     const money = parseFloat(currentValue.propertyMoney);
//     if (!isNaN(money)) {
//       return accumulator + money;
//     } else {
//       return accumulator;
//     }
//   }, 0);
//   return totalMoney;
// };

//计算
// const countMoney = () => {
//   form.value.dealMoney = countFun();
// };

//选择客户
const selectCustomer = (val: any, index: number) => {
  if (Array.isArray(val)) {
    customerArray.value.data = val.map(res => {
      return {
        customId: res.id,
        documentType: res.documentType,
        documentCode: res.documentCode,
        contacts: res.contacts,
        contactsPhone: res.contactsPhone
      };
    });
  } else {
    customerArray.value.data[index] = {
      customId: val.id,
      documentType: val.documentType,
      documentCode: val.documentCode,
      contacts: val.contacts,
      contactsPhone: val.contactsPhone
    };
  }
};

//确定客户
const confirmCustomer = () => {
  intermediaryInfo.value.data[currentCustomerIndex.value]!.intermediaryCustomerLeadRequestList = customerArray.value.data;
  dialogVisible.value = false;
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
const ruleFormRef = ref<FormInstance>();
const intermediaryRef = ref(); //中介信息
const fileRef = ref();
const networkInfoRef = ref();
const saveFun = async () => {
  const formEl1 = intermediaryRef.value?.tableRulesRef;
  const formEl = fileRef.value?.tableRulesRef;
  const formEl2 = networkInfoRef.value?.tableRulesRef;
  try {
    await ruleFormRef.value!.validate();
    if (formEl) {
      await formEl.validate();
    }
    if (formEl1) {
      await formEl1.validate();
    }
    if (formEl2) {
      await formEl2.validate();
    }
  } catch (error) {
    return false;
  }
  if (assetInfo.value.data.length <= 0) {
    ElMessage.warning("请完善资产信息！");
    return false;
  }
  // if (form.value?.dealMoney && countFun() < form.value?.dealMoney) {
  //   ElMessage.warning("【资产费用分摊表】合计支出金额小于成交金额，请重新分配！");
  //   return false;
  // }
  form.value.propertyBillRequestList = deduplicateArraysById(bakassetInfo.value, assetInfo.value.data); //资产信息
  form.value.documentIntermediaryRequestList = deduplicateArraysById(bakintermediaryInfo.value, intermediaryInfo.value.data); //中介信息
  form.value.hangNetworkInfoRequestList = deduplicateArraysById(baknetworkInfo.value, networkInfo.value.data); //挂网信息
  form.value.assetIncomeDistributionRequestList = deduplicateArraysById(bakassetMoney.value, assetMoney.value.data); //收入分配
  form.value.billContractRequestList = deduplicateArraysById(bakcontractInfoArray.value, contractInfoArray.value.data); //合同信息
  form.value.fileInfoList = fileRef.value.submitForm; //附件信息
  return true;
};

const handleSave = async () => {
  const result = await saveFun();

  if (!result) return false;

  try {
    await assetTransfer().update!(form.value);
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
  const result = await saveFun();
  if (!result) return false;
  try {
    await assetTransfer().submit!(form.value);
    ElMessage.success({ message: `提交资产转让成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

const CONTRACT_TYPE_002 = ref();
const ASSESSMENT_AGENCY = ref();
const INTERMEDIARY_AGENCY = ref();
onMounted(async () => {
  useBaseStore()
    .findEnumByName("CONTRACT_TYPE")
    .then(item => {
      CONTRACT_TYPE_002.value = item.filter(items => items.itemCode === "CONTRACT_TYPE_002")[0].id;
    });
  useBaseStore()
    .findEnumByName("AGENCY_TYPE")
    .then(item => {
      ASSESSMENT_AGENCY.value = item.filter(item => item.itemCode == "ASSESSMENT_AGENCY")[0].id;
      INTERMEDIARY_AGENCY.value = item.filter(item => item.itemCode == "INTERMEDIARY_AGENCY")[0].id;
    });
  findById();
});

defineExpose({
  handleSave,
  handleSubmit
});
</script>
