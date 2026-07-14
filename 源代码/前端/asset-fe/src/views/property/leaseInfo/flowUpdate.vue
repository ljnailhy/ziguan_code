<template>
  <div>
    <vz-card title="资产信息">
      <form-table
        :table-data="assetInfo"
        ref="assetInfoRef"
        @on-select-change="chooseAsset"
        :filter-data="{ propertyStateList: ['IDLE', 'PART_LEASE', 'PART_TRANSFER'] }"
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
              <el-switch v-model="form!.isEstimate" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="form!.isEstimate == true">
            <el-form-item label="评估机构" prop="estimateId">
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
    <vz-card title="中介信息">
      <form-table
        ref="intermediaryRef"
        :table-data="intermediaryInfo"
        :filter-data="{ agencyType: INTERMEDIARY_AGENCY }"
        :readonly="drawerProps.readonly"
        :view-operation="true"
        @on-select-change="selectAgency"
      >
        <!-- <template #operation="scope">
          <el-link type="warning" @click="onOpenCustomer(scope, 'customer')">登记客户线索</el-link>
        </template> -->
      </form-table>
    </vz-card>
    <vz-card title="准客户信息">
      <form-table
        :table-data="customerArray"
        :filter-data="{
          agencyType: INTERMEDIARY_AGENCY,
          ids: intermediaryInfo.data && intermediaryInfo.data.map(item => item.intermediaryId)
        }"
        @on-select-change="selectCustomer"
        :readonly="drawerProps.readonly"
      >
      </form-table>
    </vz-card>
    <vz-card title="挂网信息">
      <form-table ref="networkInfoRef" :table-data="networkInfo" :readonly="drawerProps.readonly"></form-table>
    </vz-card>
    <vz-card title="租赁信息">
      <el-form
        ref="ruleFormRef1"
        label-width="150px"
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.readonly"
        :model="form"
        :hide-required-asterisk="false"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="月租金" prop="monthRent" :inline-message="true">
              <vz-input-unit
                v-model="form!.monthRent"
                :value="form!.monthRent"
                placeholder="请输入月租金"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="年租金" prop="yearRent" :inline-message="true">
              <vz-input-unit
                v-model="form!.yearRent"
                :value="form!.yearRent"
                placeholder="请输入年租金"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="保证金" prop="margin" :inline-message="true">
              <vz-input-unit
                v-model="form!.margin"
                :value="form!.margin"
                placeholder="请输入保证金"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="租赁期限" prop="leaseTermStart">
              <el-date-picker
                v-model="leaseTermDate"
                @change="changePaymentCycle"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="x"
              />
            </el-form-item>
            <!-- <el-form-item label="租赁期限开始日期" prop="leaseTermStart">
              <el-date-picker v-model="form!.leaseTermStart" type="date" placeholder="请选择租赁期限开始日期" class="w100" />
            </el-form-item> -->
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="租赁期限结束日期" prop="leaseTermEnd">
              <el-date-picker v-model="form!.leaseTermEnd" type="date" placeholder="请选择租赁期限结束日期" class="w100" />
            </el-form-item>
          </el-col> -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="缴纳周期" prop="paymentCycle">
              <el-select v-model="form!.paymentCycle" clearable class="w100" @change="changePaymentCycle">
                <el-option label="年" value="YEAR"></el-option>
                <el-option label="半年" value="HALF_YEAR"></el-option>
                <el-option label="季" value="SEASON"></el-option>
                <el-option label="月" value="MONTH"></el-option>
                <el-option label="一次性" value="DISPOSABLE"></el-option>
                <el-option label="其他" value="OTHER"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="承租人" prop="lessee">
              <vzCustomerInfo v-model:value="form!.lessee" select-type="radio" :default-value="form!.lessee"></vzCustomerInfo>
              <!-- <el-input v-model="form!.lessee" placeholder="请输入承租人" clearable></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="成交中介" prop="intermediaryId">
              <vzAgency
                :default-value="form!.intermediaryId"
                :filter-data="{ agencyType: INTERMEDIARY_AGENCY }"
                @handle-ok="handleOkAgency"
                placeholder="请选择评估机构"
              ></vzAgency>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="中介费用" prop="intermediaryFee">
              <vz-input-unit
                v-model="form!.intermediaryFee"
                :value="form!.intermediaryFee"
                placeholder="请输入中介费用"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="租赁用途" prop="leasePurpose">
              <el-input type="textarea" :rows="5" v-model="form!.leasePurpose" placeholder="请输入租赁用途" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="产权信息">
      <form-table
        @on-select-change="changeProperty"
        @del-row="delRow"
        ref="detailListRef"
        :table-data="propertyRightInfoList"
        select-type="radio"
        :filter-data="{ doIds: assetInfo.data && assetInfo.data.map(item => item.propertyId), isTransfer: false }"
        :readonly="drawerProps.readonly"
      ></form-table>
    </vz-card>
    <vz-card title="缴费周期">
      <form-table
        ref="leasePaymentCycleRef"
        :operation-width="180"
        :show-button="leasePaymentButton"
        @money-input="moneyInput"
        @on-select-change="moneyInput"
        :table-data="leasePaymentCycleArray"
        :readonly="drawerProps.readonly"
        :view-operation="true"
      >
        <template #operation="scope">
          <el-link type="warning" @click="onOpenCustomer(scope, 'asset')">查看资产分账</el-link>
        </template>
      </form-table>
      <el-dialog v-model="assetDialogVisible" title="资产收入分配" :destroy-on-close="true" width="800">
        <form-table ref="assetMoneyRef" :table-data="assetMoney" :readonly="true"></form-table>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="assetDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="confirmCustomer('asset')"> 确定 </el-button>
          </div>
        </template>
      </el-dialog>
    </vz-card>
    <vz-card title="合同信息">
      <form-table
        ref="contractInfoArrayRef"
        :table-data="contractInfoArray"
        @on-select-change="onSelectChange"
        :readonly="drawerProps.readonly"
        :filter-data="{ contractType: CONTRACT_TYPE_005 }"
      ></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table
        ref="fileRef"
        :readonly="drawerProps.readonly"
        :query-data="{ doId: form!.id, doType: 'LEASE_INFO' }"
      ></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="leaseInfoDrawer">
import { ref, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { LeaseInfoRequest, PaymentCycleEnum } from "@/api/modules/property/leaseInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { leaseInfo } from "@/api/modules/property/leaseInfo/api"; // api
import { useBaseStore } from "@/stores/modules/baseInfo";
import { assetTransfer } from "@/api/modules/property/assetTransfer/api"; // api
import { intermediaryCustomerLead } from "@/api/modules/property/intermediaryCustomerLead/api"; // api
import { contractInfo } from "@/api/modules/source/contractInfo/api"; // 合同信息
import { billContract } from "@/api/modules/compensatory/billContract/api"; // api
import { leasePaymentCycle } from "@/api/modules/property/leasePaymentCycle/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";
import { customerInfo } from "@/api/modules/source/customerInfo/api"; // 客户信息
import { assetIncomeDistribution } from "@/api/modules/property/assetIncomeDistribution/api"; // api
import { propertyStateOptions } from "@/api/modules/property/propertyInfo/interface";
import { deduplicateArraysById, mergerdArrayFun } from "@/utils";
import { rules } from "@/views/property/leaseInfo/index";

//引入组件
import vzAgency from "@/components/source/vz-agency.vue";
import vzCustomerInfo from "@/components/source/vzCustomerInfo.vue";
import VzCard from "@/views/compensatory/projectInfo/component/VzCard.vue";
const detailListRef = ref();
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

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

const changeProperty = (val: any, index: number, item: EmptyObjectType) => {
  const bakInfo = JSON.parse(JSON.stringify(propertyRightInfoList.value.data));
  if (item.prop === "propertyCode") {
    const containsTarget = bakInfo.some(item => item.propertyCode === val.propertyCode);
    const currentValue = bakInfo[index];
    if (containsTarget) {
      if (currentValue.id !== val.id) {
        propertyRightInfoList.value.data[index] = {
          parentId: bakInfo[index].id,
          propertyCode: bakInfo[index].propertyCode,
          propertyInfoId: bakInfo[index].propertyInfoId,
          area: bakInfo[index].area,
          originalValue: bakInfo[index].originalValue,
          assetUse: bakInfo[index].assetUse,
          address: bakInfo[index].address,
          propertyOwner: bakInfo[index].propertyOwner,
          propertyTransferOwnership: bakInfo[index].propertyTransferOwnership,
          propertyEndDate: bakInfo[index].propertyEndDate
        };
      }
      return ElMessage.warning("已有相同的产权信息，请核对后再选择！");
    } else {
      propertyRightInfoList.value.data[index] = {
        parentId: val.id,
        propertyCode: val.propertyCode,
        propertyInfoId: val.propertyInfoId,
        area: val.area,
        originalValue: val.originalValue,
        assetUse: val.assetUse,
        address: val.address,
        propertyOwner: val.propertyOwner,
        propertyTransferOwnership: val.propertyTransferOwnership,
        propertyEndDate: val.propertyEndDate
      };
    }
  }
};
//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);
//合同信息
const contractInfoArray = ref<any>({
  data: [],
  header: [
    {
      prop: "contractId",
      label: "合同名称",
      width: "250",
      type: "contract",
      isRequired: true
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

//中介
const intermediaryInfo = ref<any>({
  data: [],
  header: [
    {
      prop: "intermediaryId",
      label: "中介名称",
      width: "110",
      isRequired: true,
      type: "agency"
    },
    {
      prop: "Contacts",
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
      type: "money",
      max: 99999999
    },
    {
      prop: "hangNetworkQuotation",
      label: "摘牌价格/万元",
      width: "120",
      type: "money",
      max: 99999999
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

//客户线索
const customerArray = ref<any>({
  data: [],
  header: [
    {
      prop: "customId",
      label: "客户名称",
      width: "200",
      type: "customer",
      isRequired: true,
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

//缴费周期
const leasePaymentCycleArray = ref<any>({
  data: [],
  isHideDelete: true,
  header: [
    {
      prop: "startDate",
      label: "开始时间",
      width: "140",
      type: "date"
    },
    {
      prop: "endDate",
      label: "结束时间",
      width: "140",
      type: "date"
    },
    {
      prop: "remindDate",
      label: "提醒收租时间",
      width: "140",
      type: "date"
    },
    {
      prop: "paymentAmount",
      label: "缴费金额",
      width: "160",
      type: "money"
    },
    {
      prop: "state",
      label: "状态",
      width: "120",
      dictType: "PAYMENT_CYCLE_STATUS",
      type: "select"
    },
    {
      prop: "remark",
      label: "备注",
      width: "200"
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "200",
      type: "file",
      doType: "LEASE_PAYMENT_CYCLE"
    }
  ]
});

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
      type: "text"
    }
  ]
});

//资产费用分摊
const assetMoney = ref<any>({
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
      prop: "propertyId",
      label: "资产名称",
      width: "150",
      type: "asset",
      disabled: true
    },
    {
      prop: "propertyDate",
      label: "收入日期",
      width: "120",
      type: "date"
    },
    {
      prop: "propertyMoney",
      label: "收入金额",
      width: "120",
      type: "money"
    }
  ]
});
//定义表单数据
const leaseTermDate = ref<any>([]);
const form = ref<Partial<LeaseInfoRequest>>({
  /** 缴纳周期'YEAR'：年,'HALF_YEAR'：半年,'SEASON'：季,'MONTH'：月,'DISPOSABLE'：一次性,'OTHER'：其他 */
  paymentCycle: PaymentCycleEnum.YEAR,
  propertyBillRequestList: [],
  documentIntermediaryRequestList: [],
  hangNetworkInfoRequestList: [],
  billContractRequestList: [],
  fileInfoList: [],
  propertyRightInfoRequests: []
});

// 单个查找
const bakintermediaryCustomerLeadRequestList = ref([]);
const bakpropertyRightInfoList = ref<any>([]);
const findById = async () => {
  if (!drawerProps.value.id) return;

  try {
    const response = await leaseInfo().findById(drawerProps.value.id); // 假设 leaseInfo 是一个对象，findById 是其方法
    const { data } = response;

    if (data.estimateDate && data.estimateEndDate) {
      estimateDateRange.value = [data.estimateDate, data.estimateEndDate];
    }
    if (!data) {
      console.error("找不到对应的数据");
      return;
    }
    form.value = data;
    if (data.intermediaryCustomerLeadRequestList) {
      customerArray.value.data = data.intermediaryCustomerLeadRequestList.map(item => {
        return {
          ...item,
          protectDate: item.startDate && item.endDate ? [item.startDate, item.endDate] : []
        };
      });
      bakintermediaryCustomerLeadRequestList.value = JSON.parse(JSON.stringify(data.intermediaryCustomerLeadRequestList));
    }
    if (data.propertyRightInfoRequests) {
      bakpropertyRightInfoList.value = JSON.parse(JSON.stringify(data.propertyRightInfoRequests));
      propertyRightInfoList.value.data = data.propertyRightInfoRequests;
    }
    if (data.leaseTermStart && data.leaseTermEnd) {
      leaseTermDate.value = [new Date(data.leaseTermStart), new Date(data.leaseTermEnd)];
    }
    // 使用 map 和 Promise.all 来并发执行所有请求，并独立处理每个请求的错误
    await Promise.all([
      getPropertyBill("LEASE_INFO", data.id).catch(error => console.error("getPropertyBill 错误:", error)),
      getLeasePaymentCycle("LEASE_INFO", data.id).catch(error => console.error("getLeasePaymentCycle 错误:", error)),
      getIntermediary("LEASE_INFO", data.id).catch(error => console.error("getIntermediary 错误:", error)),
      getBillContract("LEASE_INFO", data.id).catch(error => console.error("getBillContract 错误:", error)),
      getHangNetwork("LEASE_INFO", data.id).catch(error => console.error("getBillContract 错误:", error))
    ]);
  } catch (error) {
    console.error("请求失败:", error);
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

//选择中介带出联系人，联系电话
const selectAgency = (val: any, index: number) => {
  intermediaryInfo.value.data[index].Contacts = val.contacts;
  intermediaryInfo.value.data[index].contactsNumber = val.contactsPhone;
};

//评估机构确定
const handleOk = (val: EmptyObjectType) => {
  form.value!.estimateId = val.id;
};
//评估机构确定
const handleOkAgency = (val: EmptyObjectType) => {
  form.value!.intermediaryId = val.id;
};
const countDateFun = (type: string, time: any) => {
  if (time.length <= 0) return;

  const timestamp1 = time[0];
  const timestamp2 = time[1];

  const date1 = new Date(timestamp1);
  const date2 = new Date(timestamp2);

  const getTimestamp = date => date.getTime();

  let periods: any = [];

  let currentStartDate = new Date(date1);

  while (currentStartDate < date2) {
    let currentEndDate = new Date(currentStartDate);

    if (type === "HALF_YEAR") {
      currentEndDate.setMonth(currentEndDate.getMonth() + 6);
    } else if (type === "SEASON") {
      currentEndDate.setMonth(currentEndDate.getMonth() + 3);
    } else if (type === "MONTH") {
      currentEndDate.setMonth(currentEndDate.getMonth() + 1);
    } else {
      currentEndDate.setFullYear(currentEndDate.getFullYear() + 1);
    }

    currentEndDate.setDate(currentEndDate.getDate() - 1);

    if (currentEndDate > date2) {
      currentEndDate = new Date(date2);
    }

    // 计算 remindDate
    const remindDate = new Date(currentEndDate);
    remindDate.setMonth(remindDate.getMonth() - 1); // 减去一个月

    periods.push({
      start: getTimestamp(currentStartDate),
      end: getTimestamp(currentEndDate),
      remindDate: getTimestamp(remindDate) // 存储为时间戳
    });

    currentStartDate = new Date(currentEndDate);
    currentStartDate.setDate(currentStartDate.getDate() + 1);
  }

  periods.forEach(period => {
    leasePaymentCycleArray.value.data.push({
      propertyId: period.propertyInfoId,
      propertyRightInfoId: period.parentId,
      startDate: period.start,
      endDate: period.end,
      remindDate: period.remindDate, // 使用时间戳
      state: PAYMENT_CYCLE_STATUS_02.value,
      assetIncomeDistributionRequestList: propertyRightInfoList.value.data
    });
  });
};

//获取挂网分录
const baknetworkInfo = ref([]);
const getHangNetwork = async (type: string, id: number) => {
  const { data } = await assetTransfer().findHangNetwork(type, id);
  networkInfo.value.data = data;
  baknetworkInfo.value = JSON.parse(JSON.stringify(data));
};

// //改变年
const leasePaymentButton = ref(false);
const changePaymentCycle = () => {
  const leaseLength = leasePaymentCycleArray.value.data.filter(item => item.state === PAYMENT_CYCLE_STATUS_01.value);
  if (leaseLength > 0) return;
  leasePaymentCycleArray.value.data = [];

  if (
    form.value!.paymentCycle === "YEAR" ||
    form.value!.paymentCycle === "HALF_YEAR" ||
    form.value!.paymentCycle === "SEASON" ||
    form.value!.paymentCycle === "MONTH"
  ) {
    countDateFun(form.value!.paymentCycle, leaseTermDate.value);
    leasePaymentButton.value = false;
    leasePaymentCycleArray.value.isHideDelete = true;
  } else if (form.value!.paymentCycle === "DISPOSABLE" && leaseTermDate.value.length > 0) {
    leasePaymentCycleArray.value.data.push({
      startDate: leaseTermDate.value[0],
      endDate: leaseTermDate.value[1],
      state: PAYMENT_CYCLE_STATUS_02.value,
      assetIncomeDistributionRequestList: propertyRightInfoList.value.data
    });
    leasePaymentButton.value = true;
    leasePaymentCycleArray.value.isHideDelete = true;
  } else if (form.value!.paymentCycle === "OTHER") {
    leasePaymentButton.value = true;
    leasePaymentCycleArray.value.isHideDelete = false;
  }
};

//客户线索 资产收入分配
const dialogVisible = ref(false);
const assetDialogVisible = ref(false);
const currentCustomerIndex = ref(0);

const onOpenCustomer = (scope: any, type: string) => {
  currentCustomerIndex.value = scope.index;
  if (type === "customer") {
    if (scope.row.intermediaryCustomerLeadRequestList && scope.row.intermediaryCustomerLeadRequestList.length) {
      customerArray.value.data = scope.row.intermediaryCustomerLeadRequestList;
    } else {
      customerArray.value.data = [];
    }
    dialogVisible.value = true;
  } else {
    const currentLeasePayment = leasePaymentCycleArray.value.data[scope.index];
    const assetMoneyArray = propertyRightInfoList.value.data;
    assetMoney.value.data = assetMoneyArray.map((res: any, idx: number) => {
      const currentArea = propertyRightInfoList.value.data[idx].area || 1;
      return {
        propertyCode: res.propertyCode,
        propertyId: res.propertyInfoId,
        propertyType: 1,
        propertyRightInfoId: res.parentId,
        propertyDate: currentLeasePayment!.endDate,
        propertyMoney: (currentArea / countFun()) * currentLeasePayment!.paymentAmount
      };
    });
    leasePaymentCycleArray.value.data[scope.index]!.assetIncomeDistributionRequestList = deduplicateArraysById(
      leasePaymentCycleArray.value.data[scope.index]!.assetIncomeDistributionRequestList,
      assetMoney.value.data
    );
    // if (scope.row.assetIncomeDistributionRequestList && scope.row.assetIncomeDistributionRequestList.length) {
    //   assetMoney.value.data = scope.row.assetIncomeDistributionRequestList;
    // } else {
    //   assetMoney.value.data = propertyRightInfoList.value.data.map(item => {
    //     return {
    //       propertyCode: item.propertyCode,
    //       propertyId: item.propertyId,
    //       propertyType: 1,
    //       propertyRightInfoId: item.parentId,
    //       // costType: item.costType,
    //       propertyDate: "",
    //       propertyMoney: ""
    //     };
    //   });
    // }

    assetDialogVisible.value = true;
  }
};

//选择客户
const selectCustomer = (val: any, index: number, item: any, row: any) => {
  if (item.prop === "customId") {
    customerArray.value.data[index].contacts = val.id;
    customerArray.value.data[index].contactsNumber = val.contactsPhone;
  } else if (item.prop === "protectDate") {
    row[item.prop] = val;
    if (val && val.length > 0) {
      row.startDate = val[0];
      row.endDate = val[1];
    }
  } else {
    row[item.prop] = val.id;
  }
};

//确定客户 确定资产收入分配
const confirmCustomer = (type: string) => {
  if (type === "customer") {
    intermediaryInfo.value.data[currentCustomerIndex.value]!.intermediaryCustomerLeadRequestList = deduplicateArraysById(
      bakcustomerArray.value,
      customerArray.value.data
    );
    dialogVisible.value = false;
  } else {
    // leasePaymentCycleArray.value.data[currentCustomerIndex.value].assetIncomeDistributionRequestList = assetMoney.value.data;
    // leasePaymentCycleArray.value.data[currentCustomerIndex.value].paymentAmount = countFun();
    assetDialogVisible.value = false;
  }
};

//获取缴费周期分录
const bakleasePaymentCycleArray = ref();

const getLeasePaymentCycle = async (type: string, id: number) => {
  const { data } = await leasePaymentCycle().findByDoId(type, id);
  // for (const element of data) {
  //   const res = await assetIncomeDistribution().findByDoId("LEASE_PAYMENT_CYCLE", element.id);
  //   element.assetIncomeDistributionRequestList = res.data;
  //   // assetMoney
  //   element.bakassetIncomeDistributionRequestList = JSON.parse(JSON.stringify(res.data));
  // }
  for (let index = 0; index < data.length; index++) {
    const element = data[index];
    const res = await assetIncomeDistribution().findByDoId("LEASE_PAYMENT_CYCLE", element.id);
    element.bakassetIncomeDistributionRequestList = JSON.parse(JSON.stringify(res.data));
    const assetMoneyArray = propertyRightInfoList.value.data;
    assetMoney.value.data = assetMoneyArray.map((res: any, idx: number) => {
      const currentArea = propertyRightInfoList.value.data[idx].area || 1;
      return {
        id: element.id,
        propertyCode: res.propertyCode,
        propertyId: res.propertyInfoId,
        propertyType: 1,
        propertyRightInfoId: res.parentId,
        propertyDate: element!.endDate,
        propertyMoney: (currentArea / countFun()) * element!.paymentAmount
      };
    });
    element.assetIncomeDistributionRequestList = deduplicateArraysById(res.data, assetMoney.value.data);
  }

  // const currentLeasePayment = leasePaymentCycleArray.value.data[scope.index];
  //   const assetMoneyArray = propertyRightInfoList.value.data;
  //   assetMoney.value.data = assetMoneyArray.map((res: any, idx: number) => {
  //     const currentArea = propertyRightInfoList.value.data[idx].area || 1;
  //     return {
  //       propertyCode: res.propertyCode,
  //       propertyId: res.propertyInfoId,
  //       propertyType: 1,
  //       propertyRightInfoId: res.parentId,
  //       propertyDate: currentLeasePayment!.endDate,
  //       propertyMoney: (currentArea / countFun()) * currentLeasePayment!.paymentAmount
  //     };
  //   });
  //   leasePaymentCycleArray.value.data[scope.index]!.assetIncomeDistributionRequestList = deduplicateArraysById(
  //     leasePaymentCycleArray.value.data[scope.index]!.assetIncomeDistributionRequestList,
  //     assetMoney.value.data
  //   );
  leasePaymentCycleArray.value.data = data;
  bakleasePaymentCycleArray.value = JSON.parse(JSON.stringify(data));
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

// let doIds;
//获取资产分录
const backassetInfo = ref();
const getPropertyBill = async (type: string, id: number) => {
  const { data } = await assetTransfer().findPropertyBill(type, id);
  assetInfo.value.data = data;
  // 确保 data 不为空
  // if (Array.isArray(data) && data.length > 0) {
  //   doIds = data.map(item => item.propertyId);
  // }
  backassetInfo.value = JSON.parse(JSON.stringify(data));
};

//获取中介分录
const bakintermediaryInfo = ref();
const bakcustomerArray = ref();
const getIntermediary = async (type: string, id: number) => {
  const { data } = await assetTransfer().findIntermediary(type, id);
  if (data && data.length > 0) {
    for (const element of data) {
      const res = await intermediaryCustomerLead().findByDoId(element.id, "DOCUMENT_INTERMEDIARY");
      if (res.data && res.data.length) {
        const res1 = await customerInfo().batch(res.data.length && res.data.map(item => item.customId));

        element.intermediaryCustomerLeadRequestList = res1.data.map(items => {
          return {
            ...items,
            customId: items.id
          };
        });
        bakcustomerArray.value = JSON.parse(JSON.stringify(element.intermediaryCustomerLeadRequestList));
      }
    }
  }
  intermediaryInfo.value.data = data;
  bakintermediaryInfo.value = JSON.parse(JSON.stringify(data));
};

//获取合同分录
const bakcontractInfoArray = ref();
const getBillContract = async (type: string, id: number | string) => {
  const { data } = await billContract().findByDoId(id, type);

  if (data && data.length > 0) {
    for (const element of data) {
      if (!element.contractId) return;
      const res = await contractInfo().findById(element.contractId);
      element.startDate = res.data.startDate;
      element.endDate = res.data.endDate;
      element.contractCode = res.data.contractCode;
      element.contractMoney = res.data.contractMoney;
      element.signingDate = res.data.signingDate;
    }
  }

  contractInfoArray.value.data = data;
  bakcontractInfoArray.value = JSON.parse(JSON.stringify(data));
};
//资产信息选择

const chooseAsset = async (val: any, index: number, item: any) => {
  const bakInfo = JSON.parse(JSON.stringify(assetInfo.value.data));

  if (item.prop == "propertyId") {
    if (Array.isArray(val) && assetInfo.value.data.length > 0) {
      assetInfo.value.data = mergerdArrayFun(backassetInfo.value, val);
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

// 计算分录总数
const countFun = () => {
  //分录的金额合计
  const propertyList = JSON.parse(JSON.stringify(propertyRightInfoList.value.data));
  const totalArea = propertyList.reduce((accumulator, currentValue) => {
    const money = parseFloat(currentValue.area);
    if (!isNaN(money)) {
      return accumulator + money;
    } else {
      return accumulator + 1;
    }
  }, 0);
  return totalArea;
};
const delRow = (index: number) => {
  if (leasePaymentCycleArray.value.data && leasePaymentCycleArray.value.data.length) {
    leasePaymentCycleArray.value.data.forEach(item => {
      item.assetIncomeDistributionRequestList.splice(index, 1);
    });
  }
  countFun();
};
//计算
const moneyInput = (val: number, index: number, item: any) => {
  const currentLeasePayment = leasePaymentCycleArray.value.data[index];
  const bakassMoneyArray = leasePaymentCycleArray.value.data[index]!.assetIncomeDistributionRequestList;
  const assetMoneyArray = bakassMoneyArray.length ? bakassMoneyArray : propertyRightInfoList.value.data;
  if (
    (item.prop === "state" || item.prop === "paymentAmount") &&
    PAYMENT_CYCLE_STATUS_01.value == currentLeasePayment.state &&
    assetMoneyArray.length
  ) {
    const newAssetMoneyArray = assetMoneyArray.map((res: any, idx: number) => {
      const currentArea = propertyRightInfoList.value.data[idx].area || 1;
      return {
        propertyCode: res.propertyCode,
        propertyId: res.propertyInfoId,
        propertyRightInfoId: res.id,
        propertyType: 1,
        propertyDate: currentLeasePayment!.startDate,
        propertyMoney: (currentArea / countFun()) * currentLeasePayment!.paymentAmount
      };
    });
    leasePaymentCycleArray.value.data[index]!.assetIncomeDistributionRequestList = deduplicateArraysById(
      leasePaymentCycleArray.value.data[index]!.assetIncomeDistributionRequestList,
      newAssetMoneyArray
    );
  } else {
    leasePaymentCycleArray.value.data[index]!.assetIncomeDistributionRequestList = assetMoneyArray.map(item => {
      return {
        propertyCode: item.propertyCode,
        propertyId: item.propertyInfoId,
        propertyRightInfoId: item.parentId,
        propertyType: 1,
        propertyDate: "",
        propertyMoney: ""
      };
    });
  }
};

const PAYMENT_CYCLE_STATUS_01 = ref<any>(); //已缴费
const PAYMENT_CYCLE_STATUS_02 = ref<any>(); //未缴费
const CONTRACT_TYPE_005 = ref(); //出租合同
const ASSESSMENT_AGENCY = ref();
const INTERMEDIARY_AGENCY = ref();
onMounted(async () => {
  await useBaseStore()
    .findEnumByName("PAYMENT_CYCLE_STATUS")
    .then(item => {
      PAYMENT_CYCLE_STATUS_01.value = item.filter(items => items.itemCode === "PAYMENT_CYCLE_STATUS_001")[0].id;
      PAYMENT_CYCLE_STATUS_02.value = item.filter(items => items.itemCode === "PAYMENT_CYCLE_STATUS_002")[0].id;
    });
  useBaseStore()
    .findEnumByName("CONTRACT_TYPE")
    .then(item => {
      CONTRACT_TYPE_005.value = item.filter(items => items.itemCode === "CONTRACT_TYPE_005")[0].id;
    });
  useBaseStore()
    .findEnumByName("AGENCY_TYPE")
    .then(item => {
      ASSESSMENT_AGENCY.value = item.filter(item => item.itemCode == "ASSESSMENT_AGENCY")[0].id;
      INTERMEDIARY_AGENCY.value = item.filter(item => item.itemCode == "INTERMEDIARY_AGENCY")[0].id;
    });
  findById();
});

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const contractInfoArrayRef = ref();
const networkInfoRef = ref();
const saveFun = async () => {
  const formEl2 = networkInfoRef.value?.tableRulesRef;
  if (assetInfo.value.data.length <= 0) {
    ElMessage.warning("请完善资产信息！");
    return false;
  }
  const detailListEl = detailListRef.value?.tableRulesRef;
  try {
    await ruleFormRef.value!.validate();
    if (detailListEl) {
      await detailListEl!.validate();
    }
  } catch (error) {
    return false;
  }
  try {
    if (ruleFormRef.value!) {
      await ruleFormRef.value!.validate();
    }
    if (contractInfoArrayRef.value.tableRulesRef) {
      await contractInfoArrayRef.value.tableRulesRef?.validate();
    }
    if (fileRef.value.tableRulesRef) {
      await fileRef.value.tableRulesRef?.validate();
    }
    if (formEl2) {
      await formEl2?.validate();
    }
  } catch (error) {
    console.log("33", error);
    return false;
  }

  if (leaseTermDate.value.length) {
    form.value.leaseTermStart = leaseTermDate.value[0];
    form.value.leaseTermEnd = leaseTermDate.value[1];
  }
  form.value.hangNetworkInfoRequestList = deduplicateArraysById(baknetworkInfo.value, networkInfo.value.data); //挂网信息
  form.value.propertyBillRequestList = deduplicateArraysById(backassetInfo.value, assetInfo.value.data); //资产信息
  form.value.documentIntermediaryRequestList = deduplicateArraysById(bakintermediaryInfo.value, intermediaryInfo.value.data); //中介信息
  form.value.leasePaymentCycleRequestList = deduplicateArraysById(
    bakleasePaymentCycleArray.value,
    leasePaymentCycleArray.value.data
  ); //缴费周期
  form.value.billContractRequestList = deduplicateArraysById(bakcontractInfoArray.value, contractInfoArray.value.data); //合同信息
  form.value.fileInfoList = fileRef.value.submitForm; //附件信息
  form.value.intermediaryCustomerLeadRequestList = deduplicateArraysById(
    bakintermediaryCustomerLeadRequestList.value,
    customerArray.value.data
  ); //客户信息
  form.value.propertyRightInfoRequests = deduplicateArraysById(bakpropertyRightInfoList.value, propertyRightInfoList.value.data); //产权

  const deleteFiles = useDialogStore().deleteFiles;
  //资产信息
  form.value.propertyBillRequestList = assetInfo.value.data.map(item => {
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
  return true;
};
const handleSave = async () => {
  const result = await saveFun();
  if (!result) return false;
  try {
    await leaseInfo().update!(form.value);
    ElMessage.success({ message: `资产租赁保存成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");

    const jfState = leasePaymentCycleArray.value.data.filter(item => item.state == PAYMENT_CYCLE_STATUS_02.value);
    if (leasePaymentCycleArray.value.data) {
      return jfState;
    }
    return true;
  } catch (error) {
    console.log("校验", error);
    return false;
  }
};

// 提交数据（流程）
const handleSubmit = async () => {
  const result = await saveFun();

  if (!result) return false;
  try {
    await leaseInfo().submit!(form.value);
    ElMessage.success({ message: `资产租赁保存成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
  } catch (error) {
    useDialogStore().setValidationErrorMessage("校验失败，请检查表单字段!");
    console.log(error);
  }
};
defineExpose({
  handleSave,
  handleSubmit
});
</script>
