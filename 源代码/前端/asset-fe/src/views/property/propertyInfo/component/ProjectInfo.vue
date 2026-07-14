<template>
  <vz-card title="基本信息">
    <el-row :gutter="30">
      <el-col :xs="24" :sm="24" :md="12" :lg="7" :xl="7">
        <AssetCard :info="props.form"></AssetCard>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="17" :xl="17">
        <el-row style="padding-bottom: 20px; border-bottom: 1px dashed #e9e9e9">
          <el-col :span="5">
            <CellItem label="资产净值(元)" isblk>
              <template #value>
                <div v-currency="props.form.netWorth" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="5">
            <CellItem label="资产原值(元)" isblk>
              <template #value>
                <div v-currency="props.form.originalValue" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="5">
            <CellItem label="资产抵债价格(元)" isblk>
              <template #value>
                <div v-currency="props.form.debtRepaymentMoney" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="5">
            <CellItem label="资产处置价格(元)" isblk>
              <template #value>
                <div v-currency="props.form.disposalPrice" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="4">
            <CellItem label="资产盈亏(元)" isblk>
              <template #value>
                <div v-currency="props.form.profitAndLoss" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="5">
            <CellItem label="资产状态" isblk>
              <template #value>
                <dict-enum :options="propertyStateOptions" :value="form!.propertyState" class="valueClass"></dict-enum>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="5">
            <CellItem label="面积(m²)" isblk>
              <template #value>
                <div v-currency="props.form.totalArea" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="5">
            <CellItem label="运营费用(元)" isblk>
              <template #value>
                <div v-currency="props.form.operateIncome" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="5">
            <CellItem label="租金收入(元)" isblk>
              <template #value>
                <div v-currency="props.form.leaseIncome" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="4">
            <CellItem label="转让收入(元)" isblk>
              <template #value>
                <div v-currency="props.form.transferenceIncome" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </vz-card>
</template>
<script setup lang="tsx" name="ProjectInfo">
import AssetCard from "@/views/property/propertyInfo/component/AssetCard.vue";
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import { propertyStateOptions } from "@/api/modules/property/propertyInfo/interface";

type Props = {
  form?: any;
  charge?: number;
};

const props = withDefaults(defineProps<Props>(), {
  form: {},
  charge: 0
});
</script>
<style scoped>
.valueClass {
  margin-top: 10px;
  font-size: 16px;
  font-weight: bold;
}
.stateclass {
  font-size: 12px;
}
</style>
