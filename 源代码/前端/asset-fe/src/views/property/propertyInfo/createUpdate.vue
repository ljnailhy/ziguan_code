<template>
  <div>
    <el-form
      ref="ruleFormRef"
      label-width="180px"
      label-suffix=" :"
      :rules="rules"
      :disabled="drawerProps.isView"
      :model="form"
      :hide-required-asterisk="drawerProps.isView"
    >
      <vz-card title="基本信息">
        <el-row :gutter="35">
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="资产状态" prop="propertyState">
            <el-select v-model="form!.propertyState" clearable class="w100">
              <el-option label="空闲" value="IDLE"></el-option>
              <el-option label="占用" value="OCCUPY"></el-option>
              <el-option label="已出租" value="LEASED"></el-option>
              <el-option label="未出租" value="NOT_LEASED"></el-option>
              <el-option label="已转让" value="TRANSFERRED"></el-option>
              <el-option label="未转让" value="NOT_TRANSFERRED"></el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产名称" prop="propertyName">
              <el-input v-model="form!.propertyName" placeholder="请输入资产名称" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="大类" prop="type">
              <vz-select
                dict-type="PROPERTY_LARGE_CATEGORY"
                v-model="form!.type"
                :dict-value="form!.type"
                style="width: 100%"
              ></vz-select>
              <!-- <el-input v-model="form!.type" placeholder="请输入大类" clearable></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产分类" prop="propertyType">
              <!-- :option-disabled="form.type" -->
              <vz-select
                dict-type="PROPERTY_TYPE"
                v-model="form!.propertyType"
                :option-disabled="form.type"
                :dict-value="form!.propertyType"
                style="width: 100%"
              ></vz-select>
              <!-- <el-input v-model="form!.propertyType" placeholder="请输入资产分类" clearable></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="来源方式" prop="sourceType">
              <vz-select
                @change="sourceTypeChange()"
                dict-type="SOURCE_TYPE"
                v-model="form!.sourceType"
                :dict-value="form!.sourceType"
                style="width: 100%"
              ></vz-select>
              <!-- <el-input v-model="form!.sourceType" placeholder="请选择来源方式" clearable></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="来源项目" prop="projectId">
              <vz-project-info
                v-show="SOURCE_TYPE_001"
                v-model="form!.projectId"
                :default-value="form!.projectId"
                placeholder="项目名称"
                @handle-ok="handleOk"
                select-type="radio"
              ></vz-project-info>
              <el-input
                v-show="!SOURCE_TYPE_001"
                v-model="form!.projectName"
                placeholder="请输入项目名称"
                clearable
                maxlength="50"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20" v-if="SOURCE_TYPE_001">
            <el-form-item label="关联反担保措施" prop="reveId">
              <VzReveInfo
                v-model="form!.reveId"
                :default-value="form!.reveId"
                :filter-data="{ doId: form!.projectId, doType: 'PROJECT_INFO', billType: 'REVE' }"
                placeholder="反担保措施"
                @handle-ok="handleReveOk"
              ></VzReveInfo>
              <!-- <el-input v-model="form!.reveId" placeholder="请输入反担保id" clearable></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="所在区域" prop="province">
              <vz-area @change="changeBelongArea" :area="belongAreaArray" :level="1" :check-strictly="false"></vz-area>
              <!-- <el-input v-model="form!.province" placeholder="请输入资产地址_省" clearable></el-input> -->
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="资产地址_市" prop="city">
            <el-input v-model="form!.city" placeholder="请输入资产地址_市" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="资产地址_区" prop="district">
            <el-input v-model="form!.district" placeholder="请输入资产地址_区" clearable></el-input>
          </el-form-item>
        </el-col> -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="详细地址" prop="address">
              <el-input v-model="form!.address" placeholder="请输入详细地址" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产标签" prop="propertyTag">
              <vz-select
                multiple
                dict-type="PROPERTY_TAG"
                v-model="form!.propertyTag"
                :dict-value="form!.propertyTag"
                style="width: 100%"
              ></vz-select>
              <!-- <el-input v-model="form!.propertyTag" placeholder="请输入资产标签" clearable></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产取得方式" prop="accessWay">
              <vz-select
                dict-type="ACCESS_WAY"
                v-model="form!.accessWay"
                :dict-value="form!.accessWay"
                style="width: 100%"
              ></vz-select>
              <!-- <el-input v-model="form!.accessWay" placeholder="请输入资产取得方式" clearable></el-input> -->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="面积" prop="area">
              <vz-input-unit
                v-model="form!.area"
                :value="form!.area"
                :show-word="false"
                text="m²"
                :disabled="true"
                clearable
                :max="99999999"
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="跟进人" prop="followUpPerson">
              <vz-user
                style="width: 100%"
                :multiple="true"
                v-model:model="form!.followUpPerson"
                :dict-value="form!.followUpPerson"
                clearable
              ></vz-user>
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="是否拆分" prop="separable">
              <el-switch v-model="form!.separable" />
            </el-form-item>
          </el-col> -->
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="用地性质" prop="landUseNature">
              <vz-select
                dict-type="LAND_USE_NATURE"
                v-model="form!.landUseNature"
                :dict-value="form!.landUseNature"
                style="width: 100%"
              ></vz-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产获得时间" prop="assertTime">
              <el-date-picker v-model="form!.assertTime" type="date" placeholder="请选择资产获得时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="所属单位" prop="affiliatedUnit">
              <vz-org v-model:orgValue="form.affiliatedUnit" :org="form.affiliatedUnit" org-type="COMPANY"></vz-org>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产状态" prop="propertyState">
              <el-select v-model="form!.propertyState" clearable class="w100">
                <el-option label="闲置" value="IDLE"></el-option>
                <el-option label="自用" value="SELF_USE"></el-option>
                <el-option label="已出租" value="LEASED"></el-option>
                <el-option label="已转让" value="TRANSFERRED"></el-option>
                <el-option label="待办证" value="PART_IDLE"></el-option>
                <el-option label="被占用" value="OCCUPY"></el-option>
                <el-option label="部分租赁" value="PART_LEASE"></el-option>
                <el-option label="部分转让" value="PART_TRANSFER"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="资产描述" prop="propertyDescribe">
              <el-input
                :rows="5"
                type="textarea"
                v-model="form!.propertyDescribe"
                placeholder="请输入资产描述"
                clearable
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="资产备注" prop="transferOwnershipRemark">
              <el-input
                :rows="5"
                type="textarea"
                v-model="form!.transferOwnershipRemark"
                placeholder="请输入资产备注"
                clearable
                maxlength="500"
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-col>
          <!--          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">-->
          <!--            <el-form-item label="资产用途" prop="assetUse">-->
          <!--              <el-input-->
          <!--                :rows="5"-->
          <!--                type="textarea"-->
          <!--                v-model="form!.assetUse"-->
          <!--                placeholder="请输入资产用途"-->
          <!--                clearable-->
          <!--                maxlength="200"-->
          <!--                show-word-limit-->
          <!--              ></el-input>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
        </el-row>
      </vz-card>
      <vz-card title="资产价值">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产抵债价格" prop="debtRepaymentMoney" :inline-message="true">
              <vz-input-unit
                v-model="form!.debtRepaymentMoney"
                :value="form!.debtRepaymentMoney"
                placeholder="请输入资产抵债价格"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产原值" prop="originalValue" :inline-message="true">
              <vz-input-unit
                v-model="form!.originalValue"
                :value="form!.originalValue"
                placeholder="请输入资产原值"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产净值" prop="netWorth" :inline-message="true">
              <vz-input-unit
                v-model="form!.netWorth"
                :value="form!.netWorth"
                placeholder="请输入资产净值"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产处置价格" prop="disposalPrice" :inline-message="true">
              <vz-input-unit
                v-model="form!.disposalPrice"
                :value="form!.disposalPrice"
                placeholder="请输入资产处置价格"
                text="元"
                :max="999999999999"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="获得资产支付税费" prop="taxeFee" :inline-message="true">
              <vz-input-unit
                v-model="form!.taxeFee"
                :value="form!.taxeFee"
                placeholder="请输入获得资产支付税费"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="处置资产支付税费" prop="disposeFee" :inline-message="true">
              <vz-input-unit
                v-model="form!.disposeFee"
                :value="form!.disposeFee"
                placeholder="请输入处置资产支付税费"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="获得资产支付的其他费用" prop="originalObligorFee" :inline-message="true">
              <vz-input-unit
                v-model="form!.originalObligorFee"
                :value="form!.originalObligorFee"
                placeholder="请输入获得资产支付的其他费用"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="资产盈亏" prop="profitAndLoss" :inline-message="true">
              <vz-input-unit
                v-model="form!.profitAndLoss"
                :value="form!.profitAndLoss"
                placeholder="请输入资产盈亏"
                text="元"
                clearable
              ></vz-input-unit>
            </el-form-item>
          </el-col>
        </el-row>
      </vz-card>
      <vz-card title="产权信息">
        <form-table
          ref="detailListRef"
          @del-row="areaBlur"
          @money-input="areaBlur"
          :table-data="propertyRightInfoList"
          :readonly="drawerProps.isView"
        ></form-table>
      </vz-card>
    </el-form>
    <vz-card title="资产实景图片">
      <UploadImgs v-model:file-list="pictureList" width="250px">
        <template #empty>
          <el-icon><Picture /></el-icon>
          <span>请上传资产实景图片</span>
        </template>
        <!-- <template #tip> 长方形组件（可拖拽上传） </template> -->
      </UploadImgs>
    </vz-card>
    <vz-card title="附件信息">
      <file-table
        ref="fileRef"
        :readonly="drawerProps.isView"
        :query-data="{ doId: form!.id, doType: 'PROPERTY_INFO' }"
      ></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="propertyInfoDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { PropertyInfoRequest, PropertyStateEnum } from "@/api/modules/property/propertyInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { propertyInfo } from "@/api/modules/property/propertyInfo/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useUserStore } from "@/stores/modules/user";
import { useFileApi } from "@/api/modules/files/file";

import VzProjectInfo from "@/components/source/vzProjectInfo.vue";
import VzReveInfo from "@/components/source/vzReveInfo.vue";
import VzCard from "@/views/compensatory/projectInfo/component/VzCard.vue";
import { deduplicateArraysById } from "@/utils";
import UploadImgs from "@/components/Upload/Imgs.vue";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();
const detailListRef = ref();
const belongAreaArray = ref<any>([]);
const pictureList = ref([]);
const bakpictureList = ref([]);
const rules = reactive({
  propertyState: [
    {
      required: true,
      message: "请选择资产状态",
      trigger: "change"
    }
  ],
  propertyName: [
    {
      required: true,
      message: "资产名称是必填项",
      trigger: "blur"
    }
  ],
  type: [
    {
      required: true,
      message: "大类是必填项",
      trigger: "change"
    }
  ],
  propertyType: [
    {
      required: true,
      message: "资产分类是必填项",
      trigger: "change"
    }
  ],
  sourceType: [
    {
      required: true,
      message: "来源方式是必填项",
      trigger: "change"
    }
  ],
  projectName: [
    {
      required: true,
      message: "来源项目是必填项",
      trigger: "change"
    }
  ],
  propertyDescribe: [
    {
      required: true,
      message: "资产描述是必填项",
      trigger: "blur"
    }
  ],
  propertyCode: [
    {
      required: true,
      message: "产权证号是必填项",
      trigger: "blur"
    }
  ],
  debtRepaymentMoney: [
    {
      required: false,
      message: "资产抵债价格是必填项",
      trigger: "blur"
    }
  ],
  netWorth: [
    {
      required: false,
      message: "资产净值是必填项",
      trigger: "blur"
    }
  ],
  originalValue: [
    {
      required: false,
      message: "资产原值是必填项",
      trigger: "blur"
    }
  ]
});
const areaBlur = async () => {
  form.value.area = 0;
  form.value.area = propertyRightInfoList.value.data.reduce((accumulator, currentItem) => {
    if (currentItem.operateType === "DELETE") {
      return accumulator;
    }
    let amount = currentItem.area;
    if (typeof amount === "string") {
      amount = parseFloat(amount);
    }
    amount = typeof amount === "number" ? amount : 0;
    return accumulator + amount;
  }, 0);

  // 计算资产原值
  form.value.originalValue = propertyRightInfoList.value.data.reduce((accumulator, currentItem) => {
    if (currentItem.operateType === "DELETE") {
      return accumulator;
    }
    let amount = currentItem.originalValue;
    if (typeof amount === "string") {
      amount = parseFloat(amount);
    }
    amount = typeof amount === "number" ? amount : 0;
    return accumulator + amount;
  }, 0);
};
const propertyRightInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "propertyCode",
      label: "权证号/编号",
      width: "200",
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
      isRequired: false,
      maxlength: 127
    },
    {
      prop: "area",
      label: "面积(m²)",
      width: "120",
      type: "money"
    },
    {
      prop: "propertyOwner",
      label: "产权人名称",
      width: "150",
      maxlength: 50
    },
    {
      prop: "propertyEndDate",
      label: "权证到期日",
      width: "160",
      type: "date"
    },
    {
      prop: "propertyTransferOwnership",
      label: "资产登记日期",
      type: "date",
      width: "160"
    },
    {
      prop: "assetUse",
      label: "资产用途",
      width: "150",
      maxlength: 127
    },
    {
      prop: "address",
      label: "坐落",
      width: "200",
      maxlength: 127
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
//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<PropertyInfoRequest>>({
  /** 资产状态'IDLE'：空闲,'OCCUPY'：占用,'LEASED'：已出租,'TRANSFERRED'：已转让,'NOT_TRANSFERRED'：未转让,'NOT_LEASED'：未出租 */
  propertyState: PropertyStateEnum.IDLE,
  propertyTag: "",
  id: undefined // 这只是一个后面没逗号的坑位
});
const SOURCE_TYPE_001 = ref(false);
const sourceTypeChange = async () => {
  await getsorceType();
  if (SOURCE_TYPE_001.value) {
    form.value.projectName = "";
  } else {
    form.value.projectId = 0;
    form.value.projectName = "";
  }
};
const getsorceType = async () => {
  const data = await useBaseStore().findEnumByName("SOURCE_TYPE");
  const sourceType = data.filter(item => item.itemCode == "SOURCE_TYPE_001")[0].id;
  SOURCE_TYPE_001.value = form.value.sourceType == sourceType;

  // then(sourceType => {
  //   console.log(sourceType);
  //   for (const index in sourceType) {
  //     const item = sourceType[index];
  //     if (item.itemCode == "SOURCE_TYPE_001") {
  //       SOURCE_TYPE_001.value = form.value.sourceType == item.id;
  //     }
  //   }
  // });
};
// const optionDisabledRef = ref();
// const PROPERTY_LARGE_CATEGORY_001 = ["PROPERTY_TYPE_005", "PROPERTY_TYPE_004"];
// const PROPERTY_LARGE_CATEGORY_002 = ["PROPERTY_TYPE_001", "PROPERTY_TYPE_002", "PROPERTY_TYPE_003"];
// const propertyLargeCategoryChange = (isCleanup: Boolean) => {
//   if (isCleanup) {
//     form.value.propertyType = 0;
//   }
//   useBaseStore()
//     .findEnumByName("PROPERTY_LARGE_CATEGORY")
//     .then(propertyLargeCategory => {
//       useBaseStore()
//         .findEnumByName("PROPERTY_TYPE")
//         .then(propertyType => {
//           if (propertyLargeCategory[0].id == form.value.type) {
//             optionDisabledRef.value = propertyType
//               .filter(item => PROPERTY_LARGE_CATEGORY_002.includes(item.itemCode))
//               .map(item => item.id);
//           } else if (propertyLargeCategory[1].id == form.value.type) {
//             optionDisabledRef.value = propertyType
//               .filter(item => PROPERTY_LARGE_CATEGORY_001.includes(item.itemCode))
//               .map(item => item.id);
//           } else {
//             optionDisabledRef.value = propertyType.map(item => item.id);
//           }
//         });
//     });
// };
const handleOk = (obj: any) => {
  form.value.projectId = obj.id;
  form.value.projectName = obj.projectName;
  form.value.reveId = null;
};
const handleReveOk = (obj: any) => {
  form.value.reveId = obj.id;
};

//	省市区变化赋值
const changeBelongArea = val => {
  form.value.province = val[0];
  form.value.city = val[1];
  form.value.district = val[2];
};
// 单个查找
const bakdetailList = ref([]);
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await propertyInfo().findById(drawerProps.value.id);
  form.value = data;
  // getsorceType();
  const { province, city, district } = data;

  if (province) {
    belongAreaArray.value[0] = province;
    if (city) {
      belongAreaArray.value[1] = city;
      if (district) {
        belongAreaArray.value[2] = district;
      }
    }
  }
  if (data.propertyRightInfoList) {
    propertyRightInfoList.value.data = data.propertyRightInfoList;
    bakdetailList.value = JSON.parse(JSON.stringify(data.propertyRightInfoList));
  }

  form.value = data;
  useFileApi()
    .findAll({ doId: data.id, doType: "PROPERTY_PICTURE", size: 1000, current: 1 })
    .then(item => {
      pictureList.value = item.data;
      bakpictureList.value = JSON.parse(JSON.stringify(item.data));
    });
  // propertyLargeCategoryChange(false);
  // sourceTypeChange();
};
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const saveFun = async () => {
  const fileEl = fileRef.value.tableRulesRef;
  const detailListEl = detailListRef.value?.tableRulesRef;
  try {
    await ruleFormRef.value!.validate();
    await fileEl!.validate();
    if (fileEl) {
      await fileEl!.validate();
    }
    if (detailListEl) {
      await detailListEl!.validate();
    }
  } catch (error) {
    return false;
  }
  form.value.fileInfoList = fileRef.value.submitForm;
  form.value.propertyRightInfoList = propertyRightInfoList.value.data;
  form.value.propertyPictureList = pictureList.value.map((item: any) => {
    return {
      ...item,
      doType: "PROPERTY_PICTURE"
    };
  });
  form.value.propertyPictureList = deduplicateArraysById(bakpictureList.value, form.value.propertyPictureList);
  form.value = {
    ...form.value,
    propertyTag: form.value.propertyTag && form.value.propertyTag.toString()
  };

  form.value.propertyRightInfoList = deduplicateArraysById(bakdetailList.value, propertyRightInfoList.value!.data);
  return true;
};
// 保存数据（新增/编辑）
const handleSave = async () => {
  const result = await saveFun();
  if (!result) return false;
  if (form.value!.followUpPerson && form.value!.followUpPerson.length) {
    form.value!.followUpPerson = form.value!.followUpPerson.toString();
  }
  try {
    drawerProps.value.title == "新增" ? await propertyInfo().add!(form.value) : await propertyInfo().update!(form.value);
    ElMessage.success({ message: `${drawerProps.value.title}资产信息成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

// 提交数据（流程）
const handleSubmit = () => {
  form.value.fileInfoList = fileRef.value.submitForm;
  form.value = {
    ...form.value,
    propertyTag: form.value.propertyTag && form.value.propertyTag.toString()
  };
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}资产信息成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

//页面加载时
onMounted(() => {
  if (!form.value.followUpPerson) {
    form.value.followUpPerson = useUserStore().userInfo?.id;
  }

  findById();
});

defineExpose({
  handleSave,
  handleSubmit
});
</script>
