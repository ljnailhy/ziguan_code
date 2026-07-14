<template>
  <div ref="containerRef" class="home">
    <vz-card title="律所信息">
      <div class="flex">
        <div class="name-card">
          <div class="name-card_top">
            <p class="name-card_date">
              <dict-date v-if="endDateInfo" :date="endDateInfo" format="YYYY-MM-DD"></dict-date>
            </p>
            <div class="name-card_top-detail">
              <el-avatar shape="square" :size="50"> LOGO </el-avatar>
              <div>{{ rowData.name }}</div>
            </div>
          </div>
          <div class="name-card_user">
            <div>
              <el-icon><User /></el-icon>{{ rowData.contacts }}
            </div>
            <div>
              <el-icon><Phone /></el-icon>{{ rowData.phone }}
            </div>
            <div class="flx">
              <el-icon><MapLocation /></el-icon>
              <el-tooltip :content="rowData.address" placement="top">
                <div style="width: 100%; white-space: nowrap; width: 100%; overflow: hidden; text-overflow: ellipsis">
                  {{ rowData.address }}
                </div>
              </el-tooltip>
            </div>
          </div>
        </div>
        <div style="flex: 1">
          <div style="height: 30%; margin-left: 18px">
            <div class="sub-title">当前管理</div>
            <el-row :gutter="16" style="border-bottom: 1px dashed #e9e9e9">
              <el-col :span="4" style="display: flex; align-items: center">
                <span class="iconfont icon-chukuguanli-"></span>
                <CellItem label="在管项目数(个)" isblk :title="rowData!.lawInfoDTO.manageProjectNum">
                  <template #value>
                    <div class="valueClass">
                      {{ rowData!.lawInfoDTO.manageProjectNum }}
                    </div>
                  </template>
                </CellItem>
                <!-- <div class="statistic-card">
                  <el-statistic :value="rowData!.lawInfoDTO.manageProjectNum" title="在管项目数(个)"> </el-statistic>
                </div> -->
              </el-col>
              <el-col :span="4" style="display: flex; align-items: center">
                <span class="iconfont icon-gongshihejia-"></span>
                <CellItem label="代偿金额(万)" isblk :title="rowData!.lawInfoDTO.totalCompensationMoney">
                  <template #value>
                    <div v-currency="rowData.lawInfoDTO && rowData.lawInfoDTO.totalCompensationMoney" class="valueClass"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :span="4" style="display: flex; align-items: center">
                <span class="iconfont icon-zhiliangchengben-"></span>
                <CellItem label="律师费(万)" isblk :title="rowData!.lawInfoDTO.lawFee">
                  <template #value>
                    <div v-currency="rowData.lawInfoDTO && rowData.lawInfoDTO.lawFee" class="valueClass"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :span="4" style="display: flex; align-items: center">
                <span class="iconfont icon-woyaofangkuan"></span>
                <CellItem label="累计回款金额(万)" isblk :title="rowData!.lawInfoDTO.totalPaymentCollection">
                  <template #value>
                    <div v-currency="rowData.lawInfoDTO && rowData.lawInfoDTO.totalPaymentCollection" class="valueClass"></div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :span="4" style="display: flex; align-items: center">
                <span class="iconfont icon-houtaizonglan"></span>
                <CellItem label="本年回款率(%)" isblk :title="rowData!.lawInfoDTO.collectionRateYear">
                  <template #value>
                    <div class="valueClass">
                      {{ rowData.lawInfoDTO && rowData.lawInfoDTO.collectionRateYear }}
                    </div>
                  </template>
                </CellItem>
              </el-col>
              <el-col :span="4" style="display: flex; align-items: center">
                <span class="iconfont icon-yizhanshiqianyue"></span>
                <CellItem label="累计回款率(%)" isblk :title="rowData!.lawInfoDTO.collectionRate">
                  <template #value>
                    <div class="valueClass">
                      {{ (rowData.lawInfoDTO && rowData.lawInfoDTO.collectionRate) || "--" }}
                    </div>
                  </template>
                </CellItem>
              </el-col>
            </el-row>
            <div class="sub-title" style="margin-top: 10px">案件状态</div>
          </div>
          <div style="height: 80%">
            <ECharts :option="option" ref="chartInstance" style="width: 100%" />
          </div>
        </div>
      </div>
    </vz-card>
    <vz-card title="律师团队">
      <el-row :gutter="20" v-if="lawyerInfoRequestList && lawyerInfoRequestList.length > 0">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="6" class="mb20" v-for="item in lawyerInfoRequestList" :key="item.id">
          <div class="lawFirmInfo">
            <el-avatar shape="square" :size="50" :icon="UserFilled" />
            <div class="name">{{ item.lawyerName }}</div>
            <div class="phone">{{ item.phone }}</div>
            <span class="idCard">执业证号：{{ item.credentialNumber }}</span>
            <el-progress
              :text-inside="false"
              :stroke-width="8"
              :percentage="item.collectionRate > 100 ? 100 : item.collectionRate"
            />
            <div class="flex lawFirmInfo-bottom">
              <div class="item">
                <p>{{ item.projectNumber || 0 }}</p>
                <p>在管项目(个)</p>
              </div>
              <div class="item">
                <p v-currency="item.compensationMoneySum / 10000"></p>
                <p>在管追偿(万)</p>
              </div>
              <div class="item">
                <p v-currency="item.collectionAmountSum / 10000"></p>
                <p>当前回款(万)</p>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
      <el-empty description="暂无律师团队" v-else />
    </vz-card>
    <vz-card :title="tabList[tabPosition]">
      <el-radio-group v-model="tabPosition" style="margin-bottom: 20px">
        <el-radio-button value="0">在管项目</el-radio-button>
        <el-radio-button value="1">结案项目</el-radio-button>
        <el-radio-button value="2">协议信息</el-radio-button>
        <el-radio-button value="3">收付款信息</el-radio-button>
        <el-radio-button value="4">日常工作记录</el-radio-button>
      </el-radio-group>
      <el-scrollbar>
        <div style="height: 600px">
          <Project v-if="tabPosition == '0'" :law-firm-id="route.params.id"></Project>
          <Project v-if="tabPosition == '1'" :law-firm-id="route.params.id" :project-state="PROJECT_STATE"></Project>
          <Contract v-if="tabPosition == '2'" :law-firm-id="route.params.id"></Contract>

          <div v-if="tabPosition == '3'">
            <div class="mb10"><span class="dot"></span>付款信息</div>
            <Payment :law-firm-id="route.params.id"></Payment>
            <div class="mt10 mb10"><span class="dot"></span>回款信息</div>
            <BackPayment :law-firm-id="route.params.id"></BackPayment>
          </div>
          <WorkInfo v-if="tabPosition == '4'" :law-firm-id="projIds"></WorkInfo>
        </div>
      </el-scrollbar>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="home">
import { onMounted, ref } from "vue";
import { ElMessage } from "element-plus";
import { LawInfoDTOType } from "@/api/modules/source/lawFirmInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { lawFirmInfo } from "@/api/modules/source/lawFirmInfo/api"; // api
import { lawyerInfo } from "@/api/modules/source/lawyerInfo/api";
import { useRoute } from "vue-router";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { UserFilled } from "@element-plus/icons-vue";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api"; // api
import { contractInfo } from "@/api/modules/source/contractInfo/api"; // api

import WorkInfo from "@/views/compensatory/projectInfo/component/WorkInfo.vue";
import Project from "@/views/source/lawFirmInfo/components/Project.vue";
import Contract from "@/views/source/lawFirmInfo/components/Contract.vue";
import Payment from "@/views/source/lawFirmInfo/components/Payment.vue";
import BackPayment from "@/views/source/lawFirmInfo/components/BackPayment.vue";
import ECharts from "@/components/ECharts/index.vue";
import CellItem from "@/views/compensatory/projectInfo/component/CellItem.vue";

const containerRef = ref<HTMLElement | null>(null);
const tabPosition = ref("0");

const tabList = ref(["在管项目", "结案项目", "协议信息", "收付款信息", "日常工作记录"]);
const rootStyle = getComputedStyle(document.documentElement);
const primaryColor = rootStyle.getPropertyValue("--el-color-primary").trim();

const chartInstance = ref();
const option: any = {
  xAxis: {
    type: "category",
    data: [],
    show: false, // 不显示坐标轴线、坐标轴刻度线和坐标轴上的文字
    axisTick: {
      show: false // 不显示坐标轴刻度线
    },
    axisLine: {
      show: false // 不显示坐标轴线
    },
    axisLabel: {
      show: false // 不显示坐标轴上的文字
    },
    splitLine: {
      show: false // 不显示网格线
    }
  },
  yAxis: {
    type: "value",
    show: false, // 不显示坐标轴线、坐标轴刻度线和坐标轴上的文字
    axisTick: {
      show: false // 不显示坐标轴刻度线
    },
    axisLine: {
      show: false // 不显示坐标轴线
    },
    axisLabel: {
      show: false // 不显示坐标轴上的文字
    },
    splitLine: {
      show: false // 不显示网格线
    }
  },

  series: [
    {
      data: [],
      type: "line",
      smooth: true,
      symbolSize: 10,
      itemStyle: {
        // 折点颜色
        color: primaryColor,
        // 折点的边线颜色
        borderColor: primaryColor,
        // 折点的边线宽度
        borderWidth: 2
      },
      label: {
        show: true,
        position: [10, 10],
        backgroundColor: "rgba(255, 255, 255, 0.7)", // 标签背景色
        borderColor: "#777", // 标签边框颜色
        borderWidth: 1, // 标签边框宽度
        borderRadius: 4, // 标签边角半径
        padding: 5, // 标签内边距
        rotate: 45, //倾斜角度
        formatter: function (params) {
          return params.name + " : " + params.value;
        },
        rich: {
          d: {
            color: primaryColor,
            fontSize: 10,
            lineHeight: 24,
            height: 24
          },
          b: {
            color: primaryColor,
            fontSize: 10,
            lineHeight: 20,
            align: "left"
          }
        },
        textStyle: {
          color: primaryColor,
          align: "right",
          fontSize: 10
        }
      }
    }
  ]
};

// 单个查找
const route = useRoute();
const rowData = ref<any>({
  id: undefined,
  lawInfoDTO: {} as LawInfoDTOType
});
const projIds = ref([]);
const lawyerInfoRequestList = ref<any>();
const findById = async () => {
  const id: any = route.params.id;
  if (!id) return;
  const { data } = await lawFirmInfo().findById(id);
  rowData.value = data;

  projectInfo()
    .findAll({ size: 1000, current: 1, lawFirmId: data.id })
    .then(res => {
      console.log(res);
      if (res.code == "0" && res.data) {
        projIds.value = res.data.map(item => item.id);
      }
    });
  //	加载律师团队
  lawyerInfo()
    .findByLawFirmId(id)
    .then((res: EmptyObjectType) => {
      if (res.code == 0) {
        lawyerInfoRequestList.value = res.data;
      } else {
        ElMessage.warning(res.msg);
      }
    });
};
const PROJECT_STATE = ref();
const getState = async () => {
  useBaseStore()
    .findEnumByName("PROJECT_STATE")
    .then(res => {
      option.xAxis.data = res.map(item => item.itemName);

      if (rowData.value.lawInfoDTO) {
        // const filteredData: any = Object.entries(rowData.value.lawInfoDTO)
        //   .filter(([key]) => key.startsWith("projectState"))
        //   .map(([key, value]) => [parseInt(key.replace("projectState", "")), value]);
        const filteredData = res.map(item => {
          // 提取项目状态的编号，例如 "PROJECT_STATE_01" -> "01"
          const stateNumber = item.itemCode.replace("PROJECT_STATE_", "").padStart(2, "0");
          // 查找键值对数据中是否存在对应的项目状态
          const value = rowData.value.lawInfoDTO[`projectState${stateNumber}`] || 0; // 如果找不到，默认为0
          return {
            ...item,
            count: value // 将值映射到字典项中的 count 属性
          };
        });
        if (filteredData.length === 0) {
          // 如果 filteredData 为空，设置为全零数组
          option.series[0].data = new Array(option.xAxis.data.length).fill(0);
        } else {
          option.series[0].data = filteredData.map(item => item.count);
          // 获取 xAxis 的长度
          // const xAxisLength = option.xAxis.data.length;
          // // 创建一个长度为 xAxisLength 的数组并初始化为 0
          // const result = new Array(xAxisLength).fill(0);
          // // 将对应状态号的值填入数组
          // filteredData.forEach(([state, value]) => {
          //   // 确保状态号在 xAxisLength 范围内
          //   if (state <= xAxisLength) {
          //     result[state - 1] = value;
          //   }
          // });
          // option.series[0].data = result;
          // // 找到最大的状态号
          // const maxState = Math.max(...filteredData.map(([state]) => state));
          // // 创建一个长度为 maxState 的数组并初始化为 0
          // const result = new Array(maxState).fill(0);
          // // 将对应状态号的值填入数组
          // filteredData.forEach(([state, value]) => {
          //   result[state - 1] = value;
          // });
          // option.series[0].data = result;
        }
      } else {
        // 如果 lawInfoDTO 不存在，也设置为全零数组
        option.series[0].data = new Array(option.xAxis.data.length).fill(0);
      }

      chartInstance.value.draw();
      PROJECT_STATE.value = res.filter(item => item.itemCode === "PROJECT_STATE_15")[0].id;
    });
};
const endDateInfo = ref();
const getcontractInfo = async () => {
  const id: any = route.params.id;
  let newParams: any = { size: 1, current: 1, lawFirmId: id, field: "endDate", order: "DESC" };
  const { data } = await contractInfo().findAll(newParams);
  if (data) {
    console.log(data);
    endDateInfo.value = data[0]?.endDate;
  }
};
onMounted(async () => {
  await findById();
  await getState();
  await getcontractInfo();
});
</script>

<style scoped lang="scss">
@import "./index.scss";
:deep(.el-progress__text) {
  font-size: 12px !important;
  min-width: auto !important;
}
.valueClass {
  font-size: 16px;
  font-weight: bold;
  margin-top: 10px;
}
.iconfont {
  font-size: 25px;
  background: rgb(255, 243, 238);
  color: rgb(255, 177, 125);
  width: 45px;
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  margin-right: 10px;
}
</style>
