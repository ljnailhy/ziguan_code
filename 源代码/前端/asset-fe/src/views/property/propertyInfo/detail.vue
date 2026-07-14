<template>
  <el-row :gutter="10">
    <el-col :span="24">
      <div ref="containerRef" class="projectInfo">
        <ProjectInfo :form="form" :charge="charge"></ProjectInfo>
        <el-radio-group v-model="tabPosition" style="margin-bottom: 10px">
          <el-radio-button value="1">详情信息</el-radio-button>
          <el-radio-button value="2">运营信息</el-radio-button>
          <el-radio-button value="3">租赁信息</el-radio-button>
          <el-radio-button value="4">转让信息</el-radio-button>
        </el-radio-group>
        <div v-if="tabPosition == '1'">
          <vz-card title="基本信息">
            <el-row :gutter="10">
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="资产名称">
                  <template #value>
                    {{form!.propertyName}}
                  </template>
                </CellItem>
                <CellItem label="大类">
                  <template #value>
                    <dict-name dict-type="PROPERTY_LARGE_CATEGORY" :dict-value="form!.type"></dict-name>
                  </template>
                </CellItem>
                <CellItem label="资产分类">
                  <template #value>
                    <dict-name dict-type="PROPERTY_TYPE" :dict-value="form!.propertyType"></dict-name>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="来源项目">
                  <template #value>
                    <vz-project-info
                      v-if="SOURCE_TYPE===form!.sourceType"
                      v-model="form!.projectId"
                      :default-value="form!.projectId"
                      placeholder="项目名称"
                      disabled
                      select-type="radio"
                    ></vz-project-info>
                    <span v-else>{{ form.projectName }}</span>
                  </template>
                </CellItem>
                <CellItem label="所在区域">
                  <template #value>
                    <div class="flx">
                      <dict-area :value="form.province"></dict-area>- <dict-area :value="form.city"></dict-area>-
                      <dict-area :value="form.district"></dict-area>
                    </div>
                  </template>
                </CellItem>
                <CellItem label="资产取得方式">
                  <template #value>
                    <dict-name dict-type="ACCESS_WAY" :dict-value="form!.accessWay"></dict-name>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <!--                <CellItem label="面积(m²00)">-->
                <!--                  <template #value> {{ form.area }} </template>-->
                <!--                </CellItem>-->
                <CellItem label="资产标签">
                  <template #value>
                    <dict-name dict-type="PROPERTY_TAG" :dict-value="form!.propertyTag"></dict-name>
                  </template>
                </CellItem>

                <CellItem label="详细地址" :value="form!.address"> </CellItem>
                <CellItem label="资产获得时间">
                  <template #value> <dict-date :date="form!.assertTime" format="YYYY-MM-DD"></dict-date> </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="来源方式">
                  <template #value>
                    <dict-name dict-type="SOURCE_TYPE" :dict-value="form!.sourceType"></dict-name>
                  </template>
                </CellItem>
                <!-- <CellItem label="是否拆分" :value="form.separable ? '是' : '否'" /> -->
                <CellItem label="用地性质">
                  <template #value>
                    <dict-name dict-type="LAND_USE_NATURE" :dict-value="form!.landUseNature"></dict-name>
                  </template>
                </CellItem>
                <CellItem label="跟进人">
                  <template #value>
                    <VzUser v-model="form!.followUpPerson" :default-value="form!.followUpPerson" :disabled="true"></VzUser>
                  </template>
                </CellItem>
              </el-col>
              <!-- <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <CellItem label="所属单位">
                  <template #value>
                    <VzOrg v-model="form!.affiliatedUnit" :default-value="form!.affiliatedUnit" :disabled="true"></VzOrg>
                  </template>
                </CellItem>
              </el-col> -->
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <CellItem label="关联反担保措施">
                  <template #value>
                    <VzReveInfo v-model="form!.reveId" :default-value="form!.reveId" :disabled="true"></VzReveInfo>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <CellItem label="资产描述">
                  <template #value>
                    {{ form.propertyDescribe }}
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <CellItem label="资产备注" :value="form.transferOwnershipRemark" />
              </el-col>
              <!-- <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <CellItem label="资产用途" :value="
                form.assetUse" />
              </el-col> -->
            </el-row>
          </vz-card>
          <!--          <vz-card title="">-->
          <!--            <el-row :gutter="10">-->
          <!--              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">-->
          <!--                <CellItem label="产权证号">-->
          <!--                  <template #value>-->
          <!--                    {{form!.propertyCode}}-->
          <!--                  </template>-->
          <!--                </CellItem>-->
          <!--              </el-col>-->
          <!--              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">-->
          <!--                <CellItem label="权证到期日">-->
          <!--                  <template #value>-->
          <!--                    <dict-date :date="form!.propertyEndDate" format="YYYY-MM-DD"></dict-date>-->
          <!--                  </template>-->
          <!--                </CellItem>-->
          <!--              </el-col>-->
          <!--              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">-->
          <!--                <CellItem label="资产登记日期">-->
          <!--                  <template #value>-->
          <!--                    <dict-date :date="form!.propertyTransferOwnership" format="YYYY-MM-DD"></dict-date>-->
          <!--                  </template>-->
          <!--                </CellItem>-->
          <!--              </el-col>-->
          <!--              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
          <!--                <CellItem label="资产备注">-->
          <!--                  <template #value>-->
          <!--                    {{ form.transferOwnershipRemark }}-->
          <!--                  </template>-->
          <!--                </CellItem>-->
          <!--              </el-col>-->
          <!--            </el-row>-->
          <!--          </vz-card>-->

          <vz-card title="资产价值">
            <el-row :gutter="10">
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="资产抵债价格(元)" label-width="110">
                  <template #value>
                    <div v-currency="form.debtRepaymentMoney"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="资产原值(元)" label-width="110">
                  <template #value>
                    <div v-currency="form.originalValue"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="资产净值(元)" label-width="110">
                  <template #value>
                    <div v-currency="form.netWorth"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="资产处置价格(元)" label-width="110">
                  <template #value>
                    <div v-currency="form.disposalPrice"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="获得资产支付税费(元)" label-width="140">
                  <template #value>
                    <div v-currency="form.taxeFee"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="处置资产支付税费(元)" label-width="140">
                  <template #value>
                    <div v-currency="form.disposeFee"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="获得资产支付的其他费用(元)" label-width="180">
                  <template #value>
                    <div v-currency="form.originalObligorFee"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="6">
                <CellItem label="资产盈亏(元)" label-width="110">
                  <template #value>
                    <div v-currency="form.profitAndLoss"></div>
                  </template>
                </CellItem>
              </el-col>
            </el-row>
          </vz-card>
          <vz-card title="产权信息">
            <form-table :table-data="propertyRightInfoList" readonly></form-table>
          </vz-card>
          <vz-card title="附件信息">
            <file-table :readonly="true" :query-data="{ doId: form!.id }"></file-table>
          </vz-card>
        </div>
        <div v-if="tabPosition == '2'" style="height: 800px">
          <OperationInfo :is-show-btn="true" :filter-data="{propertyId:form!.id}"></OperationInfo>
        </div>
        <div v-if="tabPosition == '3'" style="height: 800px">
          <LeaseInfo :is-show-btn="true" :filter-data="{propertyId:form!.id}"></LeaseInfo>
        </div>
        <div v-if="tabPosition == '4'" style="height: 800px">
          <AssetTransfer :is-show-btn="true" :filter-data="{propertyId:form!.id}"></AssetTransfer>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script setup lang="tsx" name="home">
import { nextTick, onMounted, ref } from "vue";
import { PropertyInfoRequest } from "@/api/modules/property/propertyInfo/interface";
import { propertyInfo } from "@/api/modules/property/propertyInfo/api"; // api
import { useRoute } from "vue-router";
import { useBaseStore } from "@/stores/modules/baseInfo";

import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import ProjectInfo from "@/views/property/propertyInfo/component/ProjectInfo.vue";
import VzProjectInfo from "@/components/source/vzProjectInfo.vue";
import VzReveInfo from "@/components/source/vzReveInfo.vue";
import LeaseInfo from "@/views/property/leaseInfo/index.vue";
import AssetTransfer from "@/views/property/assetTransfer/index.vue";
import OperationInfo from "@/views/property/operationInfo/index.vue";

const containerRef = ref<HTMLElement | null>(null);
const tabPosition = ref("1");

// 单个查找
const route = useRoute();
const form = ref<Partial<PropertyInfoRequest>>({});
const charge = ref(0);
const findById = async () => {
  const id: any = route.params.id;
  if (!id) return;

  const { data } = await propertyInfo().findById(id);
  form.value = data;
  if (data.propertyRightInfoList) {
    propertyRightInfoList.value.data = data.propertyRightInfoList;
  }
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
      width: "120",
      type: "money",
      isRequired: true,
      maxlength: 127
    },
    {
      prop: "area",
      label: "面积(m²)",
      width: "90",
      type: "number"
    },
    {
      prop: "propertyOwner",
      label: "产权人名称",
      width: "100",
      maxlength: 50
    },
    {
      prop: "propertyEndDate",
      label: "权证到期日",
      width: "120",
      type: "date"
    },
    {
      prop: "propertyTransferOwnership",
      label: "资产登记日期",
      type: "date",
      width: "120"
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
  ]
});
const SOURCE_TYPE = ref();
onMounted(() => {
  nextTick(() => {
    useBaseStore()
      .findEnumByName("SOURCE_TYPE")
      .then(res => {
        SOURCE_TYPE.value = res.filter(item => item.itemCode === "SOURCE_TYPE_001")[0].id;
      });
    findById();
  });
});
</script>

<style scoped lang="scss">
@import "./index";
.valueClass {
  margin-top: 10px;
  font-size: 16px;
  font-weight: bold;
}
</style>
