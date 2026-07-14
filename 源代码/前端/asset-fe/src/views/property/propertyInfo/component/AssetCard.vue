<template>
  <div class="id-card">
    <el-carousel height="150px">
      <el-carousel-item v-for="item in pictureList" :key="item">
        <el-image style="width: 100%; height: 100%" :src="'/minio' + item.fileUrl" fit="scale-down" @click="openImgView(item)" />
      </el-carousel-item>
    </el-carousel>
    <el-image-viewer v-if="imgViewVisible" :url-list="['/minio' + viewImageUrl]" @close="imgViewVisible = false" />
    <!-- <el-avatar shape="circle" :icon="UserFilled" />
    <div class="id-card_company">{{ props.info.propertyName }}</div>
    <div class="flex id-card_info">
      <div class="id-card_info-nature">
        <div>大类</div>
        <div><dict-name dict-type="PROPERTY_LARGE_CATEGORY" :dict-value="props.info!.type"></dict-name></div>
      </div>
      <div class="id-card_info-product">
        <div>资产分类</div>
        <div><dict-name dict-type="PROPERTY_TYPE" :dict-value="props.info!.propertyType"></dict-name></div>
      </div>
    </div>
    <div style="position: absolute; top: 8px; left: 25px; font-size: 10px" v-if="props.info!.propertyCode">
      产权证号：{{ props.info!.propertyCode }}
    </div> -->
  </div>
</template>
<script lang="ts" setup name="IdCard">
import { onMounted, ref } from "vue";
import { useFileApi } from "@/api/modules/files/file";
import { useRoute } from "vue-router";

const route = useRoute();
const pictureList = ref<any>([]);
const viewImageUrl = ref();
const imgViewVisible = ref(false);
const openImgView = (item: any) => {
  viewImageUrl.value = item.fileUrl;
  imgViewVisible.value = true;
};
onMounted(() => {
  const id: any = route.params.id;
  useFileApi()
    .findAll({ doId: id, doType: "PROPERTY_PICTURE", size: 1000, current: 1 })
    .then(item => {
      pictureList.value = item.data;
    });
});

// import { UserFilled } from "@element-plus/icons-vue";
</script>
<style lang="scss" scoped>
.id-card {
  // text-align: center;
  // margin-right: 20px;
  // padding: 20px;
  // padding-top: 30px;
  // border-radius: 10px;
  // background: linear-gradient(to right, var(--el-color-primary), var(--el-color-primary-light-4));
  // color: #ffffff;
  // &_company {
  //   font-size: 14px;
  //   margin-top: 15px;
  // }
  // &_info {
  //   display: flex;
  //   justify-content: space-between;
  //   margin-top: 20px;
  //   font-size: 14px;
  //   &-product {
  //     text-align: left;
  //     & > div:first-child {
  //       font-size: 12px;
  //       margin-bottom: 5px;
  //     }
  //   }
  //   &-nature {
  //     text-align: left;
  //     & > div:first-child {
  //       font-size: 12px;
  //       margin-bottom: 5px;
  //     }
  //   }
  // }
}
</style>
