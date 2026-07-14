<template>
  <div class="id-card">
    <el-avatar shape="circle" :icon="UserFilled" />
    <div class="id-card_company"><dict-user-name :user-code="props.info.manageName"></dict-user-name></div>
    <div class="flex id-card_info">
      <div class="id-card_info-product">
        <div>{{ leftTitle }}</div>
        <div>保全经理</div>
      </div>
      <div class="id-card_info-nature">
        <div>{{ rightTitle }}</div>
        <div>{{ props.info!.phone }}</div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup name="IdCard">
import { UserFilled } from "@element-plus/icons-vue";
type Props = {
  info: any;
  product?: string;
  leftTitle?: string;
  rightTitle?: string;
};

const props = withDefaults(defineProps<Props>(), {
  info: {},
  leftTitle: "产品",
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
