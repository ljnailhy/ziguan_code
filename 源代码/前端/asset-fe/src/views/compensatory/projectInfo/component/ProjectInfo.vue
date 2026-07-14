<template>
  <vz-card title="项目信息" :edit="true" @go-edit="goEdit">
    <el-row :gutter="30">
      <el-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
        <IdCard :info="props.form" :product="props.product"></IdCard>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="13" :xl="13">
        <el-row style="padding-bottom: 20px; border-bottom: 1px dashed #e9e9e9">
          <el-col :span="6" style="display: flex; align-items: center">
            <span class="iconfont icon-woyaofangkuan primary_bg"></span>
            <CellItem label="代偿金额(元)" isblk>
              <template #value>
                <div v-currency="props.form.compensationMoney" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="6" style="display: flex; align-items: center">
            <span class="iconfont icon-kaoqinguanli primary_bg"></span>
            <CellItem label="代偿时间" isblk>
              <template #value>
                <dict-date :date="props.form!.compensationDate" format="YYYY-MM-DD" class="valueClass"></dict-date>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="6" style="display: flex; align-items: center">
            <span class="iconfont icon-kaoqinguanli primary_bg"></span>
            <CellItem label="移交保全部日期" isblk>
              <template #value>
                <dict-date :date="props.form!.transferDate" format="YYYY-MM-DD" class="valueClass"></dict-date>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="6" style="display: flex; align-items: center">
            <span class="iconfont icon-kaoqinguanli primary_bg"></span>
            <CellItem label="诉讼时效" isblk>
              <template #value>
                <dict-date :date="props.form!.proceedingAgeingDate" class="valueClass" format="YYYY-MM-DD"></dict-date>
              </template>
            </CellItem>
          </el-col>
          <!-- <el-col :span="4">
            <CellItem label="项目状态" isblk>
              <template #value>
                <el-link type="primary">
                  <dict-name dict-type="PROJECT_STATE" :dict-value="form.projectState" class="valueClass"></dict-name>
                </el-link>
              </template>
            </CellItem>
          </el-col> -->
        </el-row>
        <el-row style="margin-top: 20px">
          <el-col :span="6" style="display: flex; align-items: center">
            <span class="iconfont icon-kaoqinguanli"></span>
            <CellItem label="执行时效" isblk>
              <template #value>
                <dict-date :date="props.form!.adjustTrialDate" class="valueClass" format="YYYY-MM-DD"></dict-date>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="6" style="display: flex; align-items: center">
            <span class="iconfont icon-fankui-"></span>
            <CellItem label="核销状态" isblk>
              <template #value>
                <div class="valueClass">
                  <!-- {{ props.form.isTransfer ? "已移交" : "未移交" }}、 -->
                  {{ props.form.isWriteOff ? "已核销" : "未核销" }}
                </div>
              </template>
            </CellItem>
          </el-col>

          <el-col :span="6" style="display: flex; align-items: center">
            <span class="iconfont icon-jiesuanxiaoshuaifenxi"></span>
            <CellItem label="累计回款金额(元)" isblk>
              <template #value>
                <div v-currency="props.form.totalCollectionAmount" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="6" style="display: flex; align-items: center">
            <span class="iconfont icon-xinchoufafang"></span>
            <CellItem label="剩余代偿金额(元)" isblk>
              <template #value>
                <div v-currency="props.charge" class="valueClass"></div>
              </template>
            </CellItem>
          </el-col>
          <!-- <el-col :span="5">
            <CellItem label="代理律所" isblk>
              <template #value>
                <vzLawFirmInfo :default-value="form.lawFirmId" :disabled="true" class="valueClass"></vzLawFirmInfo>
              </template>
            </CellItem>
          </el-col>
          <el-col :span="4">
            <CellItem label="保全经理" isblk>
              <template #value>
                <dict-user-name :user-code="form.manage" class="valueClass"></dict-user-name>
              </template>
            </CellItem>
          </el-col> -->
        </el-row>
      </el-col>

      <el-col :xs="24" :sm="24" :md="12" :lg="5" :xl="5">
        <div class="stateClass">
          <div>项目状态</div>
          <el-tag type="primary" style="margin: 8px 0">
            <dict-name dict-type="PROJECT_STATE" :dict-value="form.projectState"></dict-name>
          </el-tag>
        </div>
        <div>
          <CellItem label="保全经理:" label-width="70">
            <template #value>
              <dict-user-name :user-code="props.form.manage"></dict-user-name>
            </template>
          </CellItem>
          <CellItem label="代理律师:" label-width="70">
            <template #value>
              <vzLawFirmInfo :default-value="props.form!.lawFirmId" :disabled="true"></vzLawFirmInfo>
            </template>
          </CellItem>
          <CellItem label="" label-width="70">
            <template #value>
              <div
                v-for="item in lawyerList"
                :key="item.id"
                style="display: flex; align-items: center; margin-bottom: 5px; font-size: 12px"
              >
                <el-icon color="var(--el-color-primary)"><Phone /></el-icon> {{ item.lawyerName }}/{{ item.phone }}
              </div>
            </template>
          </CellItem>
        </div>
      </el-col>
    </el-row>
    <common-dialog ref="dialogRef"></common-dialog>
  </vz-card>
</template>
<script setup lang="tsx" name="ProjectInfo">
import { ref } from "vue";
import { billLawyer } from "@/api/modules/compensatory/billLawyer/api";
import { lawyerInfo } from "@/api/modules/source/lawyerInfo/api"; // api
import { useRoute } from "vue-router";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";

//引入组件
import IdCard from "@/views/compensatory/projectInfo/component/IdCard.vue";
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";
import vzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";

import { onMounted } from "vue";
type Props = {
  form?: any;
  charge?: number;
  product?: string;
  findById?: () => void;
};

const props = withDefaults(defineProps<Props>(), {
  form: {},
  charge: 0,
  product: ""
});

const route = useRoute();
const lawyerList = ref<any>([]);
onMounted(() => {
  const id: any = route.params.id;
  billLawyer()
    .findByDoId(id, "PROJECT_INFO")
    .then((res: EmptyObjectType) => {
      if (res.data && res.data.length > 0) {
        lawyerInfo()
          .findByIds(res.data)
          .then(item => {
            lawyerList.value = item.data;
          });
      }
    });
});

const dialogRef = ref();
const goEdit = () => {
  const params = {
    dialogName: "projectInfo_createUpdate_edit",
    title: "编辑",
    showBtn: false,
    id: route.params.id,
    isView: false,
    api: projectInfo().update,
    getTableList: props.findById
  };
  dialogRef.value?.acceptParams(params);
  useDialogStore().setDrawerProps(params); //存缓存
};
</script>
<style scoped>
.valueClass {
  margin-top: 10px;
  font-size: 14px;
  font-weight: bold;
}
.stateClass {
  font-size: 12px;
  text-align: center;
}
.iconfont {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 45px;
  height: 45px;
  margin-right: 10px;
  font-size: 25px;
  color: rgb(255 177 125);
  background: rgb(255 243 238);
  border-radius: 8px;
}
.primary_bg {
  color: var(--el-color-primary-light-6) !important;
  background: var(--el-color-primary-light-9) !important;
}
</style>
