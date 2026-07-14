<template>
  <div class="id-card">
    <el-avatar shape="circle" :icon="UserFilled" />

    <div class="id-card_company" v-if="props.info.projectName">{{ props.info.projectName }}</div>
    <div class="id-card_company" v-else>{{ props.info.manageName }}</div>
    <div class="flex id-card_info">
      <div class="id-card_info-product">
        <div>{{ leftTitle }}</div>
        <div>
          <dict-name dict-type="INDUSTRY_GXB" :dict-value="props.info!.industryType"></dict-name>
        </div>
      </div>
      <div class="id-card_info-nature">
        <div>{{ rightTitle }}</div>
        <div><dict-enum :options="natureOptions" :value="props.info!.nature"></dict-enum></div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup name="IdCard">
import { natureOptions } from "@/api/modules/source/subjectInfo/interface";
import { UserFilled } from "@element-plus/icons-vue";
type Props = {
  info: any;
  product: string;
  leftTitle?: string;
  rightTitle?: string;
};

const props = withDefaults(defineProps<Props>(), {
  info: {},
  leftTitle: "行业分类",
  rightTitle: "性质",
  product: ""
});
</script>
<style lang="scss" scoped>
.id-card {
  text-align: center;
  margin-right: 20px;
  padding: 20px;
  border-radius: 10px;
  background: linear-gradient(to right, var(--el-color-primary), var(--el-color-primary-light-4));
  color: #ffffff;
  &_company {
    font-size: 14px;
    margin-top: 15px;
  }
  &_info {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
    font-size: 14px;
    &-product {
      text-align: left;
      & > div:first-child {
        font-size: 12px;
        margin-bottom: 5px;
      }
    }
    &-nature {
      text-align: left;
      & > div:first-child {
        font-size: 12px;
        margin-bottom: 5px;
      }
    }
  }
}
</style>
