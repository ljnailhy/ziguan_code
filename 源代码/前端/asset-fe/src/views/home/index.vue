<template>
  <div class="home">
    <el-row :gutter="20" style="margin-top: 10px">
      <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="6" class="mb20" v-auth="'recoveryNum'">
        <div class="home-count">
          <div :title="homeStatistics!.recoveryAccount">
            <div class="home-count_title">追偿项目数</div>
            <div>
              <el-popover placement="top-start" title="追偿项目数" :width="200" trigger="hover">
                <template #default>
                  {{ homeStatistics!.recoveryAccount }}
                </template>
                <template #reference>
                  <span style="padding: 0 5px; font-size: 20px">
                    {{ homeStatistics!.recoveryAccount }}
                  </span>
                </template>
              </el-popover>

              <span>个</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="6" class="mb20" v-auth="'collectionMoney'">
        <div class="home-count home-count1">
          <div>
            <div class="home-count_title">本年累计回款金额</div>
            <div :title="homeStatistics!.yearTotalRecoveryAmount">
              <span>￥</span>
              <el-popover placement="top-start" :width="300" trigger="hover">
                <template #default>
                  <el-statistic title="本年累计回款金额" :precision="6" :value="homeStatistics!.yearTotalRecoveryAmount" />
                </template>
                <template #reference>
                  <span style="padding: 0 5px; font-size: 20px">
                    {{
                      homeStatistics!.yearTotalRecoveryAmount
                        ? parseFloat(homeStatistics!.yearTotalRecoveryAmount).toFixed(2)
                        : "--"
                    }}
                  </span>
                </template>
              </el-popover>
              <span>万</span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="6" class="mb20" v-auth="'leftCompensation'">
        <div class="home-count home-count2">
          <div>
            <div class="home-count_title">剩余追偿金额</div>
            <div :title="homeStatistics!.residueRecoveryAmount">
              <span>￥</span>
              <el-popover placement="top-start" :width="300" trigger="hover">
                <template #default>
                  <el-statistic title="剩余追偿金额" :precision="6" :value="homeStatistics!.residueRecoveryAmount" />
                </template>
                <template #reference>
                  <span style="padding: 0 5px; font-size: 20px">
                    {{
                      homeStatistics!.residueRecoveryAmount ? parseFloat(homeStatistics!.residueRecoveryAmount).toFixed(2) : "--"
                    }}
                  </span>
                </template>
              </el-popover>
              <span>万</span>
            </div>
          </div>
          <div style="padding-right: 20px">
            <el-progress type="circle" :percentage="homeStatistics!.recoveryRate" :width="80" color="rgb(255, 123, 87)">
              <template #default="{ percentage }">
                <span class="percentage-value">{{ percentage }}%</span>
                <span class="percentage-label">回款率</span>
              </template>
            </el-progress>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="6" class="mb20" v-auth="'yearRepayment'">
        <div class="home-count home-count3">
          <div>
            <div class="home-count_title">本年回款目标</div>
            <div :title="homeStatistics!.yearRecoveryCollectionTarget">
              <span>￥</span>
              <el-popover placement="top-start" :width="300" trigger="hover">
                <template #default>
                  <el-statistic title="本年回款目标" :precision="6" :value="homeStatistics!.yearRecoveryCollectionTarget" />
                </template>
                <template #reference>
                  <span style="padding: 0 5px; font-size: 20px">
                    {{
                      homeStatistics!.yearRecoveryCollectionTarget
                        ? parseFloat(homeStatistics!.yearRecoveryCollectionTarget).toFixed(2)
                        : "--"
                    }}
                  </span>
                </template>
              </el-popover>
              <span>万</span>
            </div>
          </div>
          <div style="padding-right: 20px">
            <el-progress type="circle" :percentage="homeStatistics!.doneRate" :width="80" color="rgb(255, 123, 87)">
              <template #default="{ percentage }">
                <span class="percentage-value">{{ percentage }}%</span>
                <span class="percentage-label">完成率</span>
              </template>
            </el-progress>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mb10">
      <!-- v-auth="'annualOverview'" -->
      <el-col :xs="24" :sm="12" :md="12" :lg="7" :xl="7" v-auth="'annualOverview'">
        <vz-card title="年度概况" refresh @on-refresh="getHomeStatistics()">
          <el-row style="height: 300px">
            <el-col :span="12">
              <div class="flex-warp-item-box">
                <div class="flex-margin">
                  <div class="mb5" style="font-size: 20px; font-weight: bold">
                    {{ homeStatistics.yearAddRecovery || 0 }}
                  </div>
                  <div class="pl5 pl_row_1" style="font-size: 13px">本年新增追偿项目(个)</div>
                </div>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="flex-warp-item-box">
                <div class="flex-margin">
                  <div class="mb5" style="font-size: 20px; font-weight: bold">
                    {{ homeStatistics.yearAddAssetAccount || 0 }}
                  </div>
                  <div class="pl5 pl_row_1" style="font-size: 13px">本年新增资产数(个)</div>
                </div>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="flex-warp-item-box">
                <div class="flex-margin">
                  <div
                    class="mb5"
                    style="font-size: 20px; font-weight: bold"
                    v-currency="homeStatistics.yearAddRecoveryAmount"
                  ></div>
                  <div class="pl5 pl_row_1" style="font-size: 13px">本年新增追偿金额(万)</div>
                </div>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="flex-warp-item-box">
                <div class="flex-margin">
                  <div class="mb5" style="font-size: 20px; font-weight: bold" v-currency="homeStatistics.yearAddAssetValue"></div>
                  <div class="pl5 pl_row_1" style="font-size: 13px">本年新增资产净值(万)</div>
                </div>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="flex-warp-item-box">
                <div class="flex-margin">
                  <div
                    class="mb5"
                    style="font-size: 20px; font-weight: bold"
                    v-currency="homeStatistics.yearTotalRecoveryAmount"
                  ></div>
                  <div class="pl5 pl_row_1" style="font-size: 13px">本年累计回款金额(万)</div>
                </div>
              </div>
            </el-col>
          </el-row>
        </vz-card>
      </el-col>
      <!-- v-auth="'pastOverview'" -->
      <el-col :xs="24" :sm="12" :md="12" :lg="17" :xl="17" v-auth="'pastOverview'">
        <vz-card title="历年项目概况" refresh @on-refresh="getPastProjectsFun()">
          <ECharts ref="chartInstance" :option="option" style="height: 300px" />
        </vz-card>
      </el-col>
      <!-- v-auth="'todo'" -->
      <el-col :xs="24" :sm="12" :md="12" :lg="17" :xl="17" v-auth="'todo'">
        <vz-card title="我的待办">
          <!--          <el-radio-group v-model="tabPosition" style="margin-bottom: 10px">-->
          <!--            <el-radio-button value="0">我的待办</el-radio-button>-->
          <!--            <el-radio-button value="1">在途流程</el-radio-button>-->
          <!--            <el-radio-button value="2">已办流程</el-radio-button>-->
          <!--          </el-radio-group>-->
          <Todo style="height: 500px"> </Todo>
          <!-- <el-scrollbar>
            <div style="width: 98%; height: 300px">
              <div v-infinite-scroll="load" class="infinite-list" :infinite-scroll-disabled="disabled">
                <div
                  v-for="(item, index) in todoList"
                  :key="item.id"
                  class="todo-item"
                  :class="{
                    'todo-item1': item.procDefName === '项目分配',
                    'todo-item2': item.procDefName === '执行登记',
                    'todo-item3': item.procDefName === '执行登记'
                  }"
                >
                  <div class="todo-item_index">{{ index + 1 }}</div>
                  <div class="todo-item_content">
                    <div class="todo-item_content-title" v-if="item.procVars.object">{{ item.procVars.object }}</div>
                    <div class="todo-item_content-title" v-else>【{{ item.procDefName }}】{{ item.taskName }}</div>
                    <div class="todo-item_content-name pl_row_1">{{ item.createTime }} {{ item.startUserName }}</div>
                    <el-link type="primary" @click="todoFunc(item)">立即处理</el-link>
                  </div>
                </div>
              </div>
              <p v-if="loading" style="text-align: center">加载中...</p>
              <p v-if="todoList.length > 0 && noMore" style="text-align: center">没有更多了...</p>
              <el-empty v-if="todoList.length <= 0" description="暂无待办消息" />
            </div>
          </el-scrollbar> -->
        </vz-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="12" :lg="7" :xl="7" v-auth="'quickEntrance'">
        <Message></Message>
      </el-col>
      <!-- v-auth="'litigationChart'" -->
      <el-col :xs="24" :sm="12" :md="12" :lg="17" :xl="17" v-auth="'litigationChart'">
        <vz-card title="诉讼阶段统计" refresh @on-refresh="proceedStatisticsFun()">
          <ECharts ref="proceedStatisticsRef" :option="option1" style="height: 350px" />
        </vz-card>
      </el-col>
      <!-- v-auth="'deadlineWarning'" -->
      <el-col :xs="24" :sm="12" :md="12" :lg="7" :xl="7" v-auth="'deadlineWarning'">
        <vz-card title="临期预警" refresh @on-refresh="getWarningFun()">
          <div style="height: 350px">
            <div class="col-item flex" style="height: 200px">
              <div class="col-item_left" style="width: auto; padding: 0 5px">
                <div style="text-align: center">
                  <el-button type="primary" :icon="Clock" circle />
                  <div class="col-item_left_warning">距到期小于90天</div>
                  <!-- <div style="margin-top: 10px">临期预警</div> -->
                </div>
              </div>
              <div style="display: flex; flex: 1; flex-direction: column; justify-content: space-around">
                <div class="homeflex">
                  <div>保全到期日小于90天</div>
                  <div>
                    <span style="font-weight: bold; color: #fe4066">
                      {{ (warningDate!.transferDueDate && warningDate!.transferDueDate.length) || 0 }}
                    </span>
                    (个)
                  </div>
                  <el-link type="primary" @click="goToPro(warningDate!.transferDueDate)">查看</el-link>
                </div>
                <div class="homeflex" style="padding: 15px 0; border-top: 1px dashed #e9e9e9; border-bottom: 1px dashed #e9e9e9">
                  <div>诉讼到期日小于90天</div>
                  <div>
                    <span style="font-weight: bold; color: #fe4066">
                      {{ warningDate!.proceedingAgeingDueDate && warningDate!.proceedingAgeingDueDate.length }}
                    </span>
                    (个)
                  </div>
                  <el-link type="primary" @click="goToPro(warningDate!.proceedingAgeingDueDate)">查看</el-link>
                </div>
                <div class="homeflex">
                  <div>执行到期日小于90天</div>
                  <div>
                    <span style="font-weight: bold; color: #fe4066">
                      {{ warningDate!.adjustTrialDueDate && warningDate!.adjustTrialDueDate.length }}
                    </span>
                    (个)
                  </div>
                  <el-link type="primary" @click="goToPro(warningDate!.adjustTrialDueDate)">查看</el-link>
                </div>
              </div>
            </div>
          </div>
        </vz-card>
      </el-col>
      <!-- v-auth="'assetAnalysis'" -->
      <el-col :xs="24" :sm="12" :md="12" :lg="17" :xl="17" v-auth="'assetAnalysis'">
        <vz-card title="资产分析" refresh @on-refresh="getAnalyseFun()">
          <div style="display: flex">
            <ECharts ref="AnalyseRef" :option="option5" style="width: 35%; height: 300px" />
            <ECharts ref="option4Ref" :option="option4" style="width: 65%; height: 300px" />
          </div>
        </vz-card>
      </el-col>
      <!-- v-auth="'writeOffChart'" -->
      <el-col :xs="24" :sm="12" :md="12" :lg="7" :xl="7" v-auth="'writeOffChart'">
        <vz-card title="核销项目分类" refresh @on-refresh="getClassificationFun()">
          <ECharts ref="writeOffChart" :option="option3" style="height: 300px" />
        </vz-card>
      </el-col>
    </el-row>

    <workflow ref="workflowRef" :get-list="getList" />
  </div>
</template>

<script setup lang="ts" name="home">
import { ref, defineAsyncComponent, onMounted } from "vue";
import { processApi } from "@/api/modules/workflow/process";
import echarts from "@/components/ECharts/config";
import { Clock } from "@element-plus/icons-vue";
import {
  getHomeStatistics,
  getPastProjects,
  getAnalyse,
  getWarning,
  getClassification,
  proceedStatistics
} from "@/api/modules/home/home";
// import { propertyStateOptions } from "@/api/modules/property/propertyInfo/interface";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { useDialogStore } from "@/stores/modules/dialogParams";

import ECharts from "@/components/ECharts/index.vue";
import router from "@/routers";
const workflow = defineAsyncComponent(() => import("./workflow/workflow.vue"));
const Message = defineAsyncComponent(() => import("./Message.vue"));
const Todo = defineAsyncComponent(() => import("./Todo.vue"));

const todoList = ref<any>([]);
const queryData = ref({ pageNum: 1, pageSize: 10 });
const loading = ref(false);
// const count = ref(0);
const noMore = ref(false);
const disabled = ref(false);
//待办
const getList = () => {
  todoList.value = [];
  queryData.value.pageNum = 1;
  getProcessFunc();
};
const getProcessFunc = () => {
  // todoList.value = [];

  processApi()
    .listTodoProcess(queryData.value)
    .then((res: any) => {
      if (res.code == 0) {
        todoList.value = [...todoList.value, ...res.data];
      }
      loading.value = false;
      if (res.data.length < 10 || todoList.value >= res.total) {
        noMore.value = true;
        disabled.value = true;
      }
    });
};
const workflowRef = ref();

// const todoFunc = row => {
//   row.getTableList = getProcessFunc;
//   workflowRef.value!.openDialog(row);
// };

// const load = () => {
//   loading.value = true;
//   queryData.value.pageNum += 1;
//   getProcessFunc();
// };

const option1 = ref<any>({
  tooltip: {
    trigger: "axis",
    axisPointer: {
      type: "none",
      crossStyle: {
        color: "#999"
      }
    }
  },
  xAxis: {
    type: "category",
    data: [],
    axisLabel: {
      rotate: -45 //文字过多时，倾斜角度
    }
  },
  yAxis: {
    type: "value"
  },
  series: [
    {
      data: [],
      type: "bar",
      barWidth: 20,
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: "rgba(108,80,243,0.3)" },
          { offset: 1, color: "rgba(108,80,243,0)" }
        ]),
        //柱状图圆角
        borderRadius: [30, 30, 0, 0]
      }
    }
  ]
});
const option3: any = {
  color: ["rgb(4, 227, 138)", "#3bffd0", "#22e4ff", "#009dff"],
  tooltip: {
    trigger: "item"
  },
  legend: {
    type: "scroll",
    orient: "vertical",
    x: "right",
    y: "center",
    left: "75%"
  },
  series: [
    {
      name: "核销项目分类",
      type: "pie",
      radius: ["40%", "60%"],
      center: ["35%", "50%"],
      avoidLabelOverlap: false,
      padAngle: 2,
      itemStyle: {
        borderRadius: 10
      },
      label: {
        show: false,
        position: "center"
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 30,
          fontWeight: "bold"
        }
      },
      labelLine: {
        show: false
      },
      data: []
    }
  ]
};
const option4: any = {
  title: {
    text: "资产原值分类统计",
    left: "center"
  },
  tooltip: {
    trigger: "axis"
  },
  xAxis: [
    {
      type: "category",
      data: []
    }
  ],
  yAxis: [
    {
      type: "value",
      axisLabel: {
        rotate: 60,
        formatter: "{value} 万"
      }
    }
  ],
  series: [
    {
      name: "资产原值",
      type: "bar",
      barWidth: 20,
      data: [],
      markPoint: {
        data: [
          { type: "max", name: "Max" },
          { type: "min", name: "Min" }
        ]
      },

      markLine: {
        data: [{ type: "average", name: "Avg" }]
      }
    }
  ]

  // tooltip: {
  //   trigger: "axis",
  //   axisPointer: {
  //     type: "none",
  //     crossStyle: {
  //       color: "#999"
  //     }
  //   }
  // },
  // legend: {
  //   data: ["净值(万元)", "资产数"]
  // },

  // xAxis: [
  //   {
  //     type: "category",
  //     data: [],
  //     axisPointer: {
  //       type: "shadow"
  //     }
  //   }
  // ],
  // yAxis: [
  //   {
  //     type: "value",
  //     name: "净值(万元)",
  //     min: 0,
  //     // interval: 100000,
  //     axisLabel: {
  //       rotate: 60,
  //       formatter: "{value} 万"
  //     }
  //   },
  //   {
  //     type: "value",
  //     name: "资产数",
  //     min: 0,
  //     interval: 100,
  //     axisLabel: {
  //       formatter: "{value}"
  //     }
  //   }
  // ],
  // series: [
  //   {
  //     name: "净值(万元)",
  //     type: "bar",
  //     barWidth: 20, //柱图宽
  //     showBackground: true,
  //     itemStyle: {
  //       borderRadius: [30, 30, 0, 0],
  //       color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
  //         { offset: 0, color: "rgb(0, 157, 255)" },
  //         { offset: 0.5, color: "#009dffab" },
  //         { offset: 1, color: "#009dff38" }
  //       ])
  //     },
  //     tooltip: {
  //       valueFormatter: function (value) {
  //         return value + " 万";
  //       }
  //     },

  //     data: []
  //   },
  //   {
  //     name: "资产数",
  //     type: "line",
  //     yAxisIndex: 1,
  //     tooltip: {
  //       valueFormatter: function (value) {
  //         return value + " 个";
  //       }
  //     },
  //     itemStyle: {
  //       color: "#34bffa"
  //     },
  //     data: []
  //   }
  // ]
};
const option5: any = {
  tooltip: {
    trigger: "item"
  },
  // legend: {
  //   top: "5%",
  //   left: "center"
  // },
  legend: {
    type: "scroll",
    orient: "horizontal"
  },
  series: [
    {
      name: "资产原值",
      type: "pie",
      // radius: ["50%", "70%"],
      radius: "50%",
      // avoidLabelOverlap: false,
      padAngle: 2,
      itemStyle: {
        borderRadius: 10
      },
      // label: {
      //   show: false,
      //   position: "center"
      // },
      label: {
        alignTo: "edge",
        formatter: "{name|{b}}\n{time|{c} 万}",
        minMargin: 5,
        edgeDistance: 10,
        lineHeight: 15,
        rich: {
          time: {
            fontSize: 10,
            color: "#999"
          }
        }
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: "bold"
        }
      },
      // labelLine: {
      //   show: false
      // },
      data: []
    }
  ]
};
//历年项目概况
const option = ref<any>({
  tooltip: {
    trigger: "axis",
    axisPointer: {
      type: "none",
      crossStyle: {
        color: "#999"
      }
    }
  },
  legend: {
    data: ["追偿金额", "回款金额", "追偿项目数"]
  },
  xAxis: [
    {
      type: "category",
      data: [],
      axisPointer: {
        type: "shadow"
      }
    }
  ],

  yAxis: [
    {
      type: "value",
      name: "回款金额",
      min: 0,
      // interval: ,
      axisLabel: {
        formatter: "{value} 万",
        rotate: 50,
        textStyle: {
          fontSize: "10"
        }
      }
    },
    {
      type: "value",
      name: "追偿项目数",
      min: 0,
      interval: "{value}",
      axisLabel: {
        formatter: "{value}" //个
      }
    }
  ],
  series: [
    {
      name: "追偿金额",
      type: "bar",
      barWidth: 20, //柱图宽
      // showBackground: true,
      itemStyle: {
        borderRadius: [25, 25, 0, 0],
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: "#ff7b57" },
          { offset: 0.5, color: "#fcad1c" },
          { offset: 1, color: "#f5cd10" }
        ])
      },
      tooltip: {
        valueFormatter: function (value) {
          return value + " 万";
        }
      },
      data: []
    },
    {
      name: "回款金额",
      type: "bar",
      barWidth: 20, //柱图宽
      // showBackground: true,
      itemStyle: {
        borderRadius: [5, 5, 0, 0],
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: "#90d5b1" },
          { offset: 0.5, color: "#90d5b1" },
          { offset: 1, color: "#90d5b1" }
        ])
      },
      tooltip: {
        valueFormatter: function (value) {
          return value + " 万";
        }
      },
      data: []
    },
    {
      name: "追偿项目数",
      type: "line",
      yAxisIndex: 1,
      tooltip: {
        valueFormatter: function (value) {
          return value + " 个";
        }
      },
      itemStyle: {
        color: "#34bffa"
      },
      data: []
    }
  ]
});

const homeStatistics = ref<any>({});
const getStatisticsFun = () => {
  getHomeStatistics().then(res => {
    homeStatistics.value = res.data;
  });
};

// const pastProjects = ref();
const chartInstance = ref();
const getPastProjectsFun = () => {
  getPastProjects().then(res => {
    if (res.data && res.data.length > 0) {
      const sortedData = res.data.sort((a, b) => a.projectYear - b.projectYear);
      option.value.xAxis[0].data = sortedData.map(item => item.projectYear);
      option.value.series[0].data = sortedData.map(item => item.recoveryAmount);
      option.value.series[1].data = sortedData.map(item => item.recoveryCollectionAmount);
      option.value.series[2].data = sortedData.map(item => item.recoveryAccount);
      option.value.yAxis[0].max = Math.max(...option.value.series[0].data);
      option.value.yAxis[1].max = Math.max(...option.value.series[2].data) + 100;
      chartInstance.value.draw();
    }
  });
};

const AnalyseRef = ref();
const option4Ref = ref();
const getAnalyseFun = () => {
  getAnalyse().then(res => {
    if (res.data.analysePie) {
      option5.series[0].data = res.data.analysePie.map(item => {
        return {
          value: item.totalMoney / 10000,
          name: item.typeName
        };
      });

      // option5.series[0].data = propertyStateOptions.map(item => {
      //   const matchedItem = res.data.assetStatusDTO.find(pd => pd.propertyType === item.value);
      //   if (matchedItem) {
      //     return {
      //       value: matchedItem.propertyNum || 0,
      //       name: item.label
      //     };
      //   }
      // });
      AnalyseRef.value.draw();
    }
    if (res.data.analyseHistogram) {
      option4.xAxis[0].data = res.data.analyseHistogram.map(item => item.typeName);
      option4.series[0].data = res.data.analyseHistogram.map(item => item.totalMoney / 10000);
      option4Ref.value.draw();
    }
    // if (res.data.assetPropertyTypeDTO) {
    //   useBaseStore()
    //     .findEnumByName("PROPERTY_TYPE")
    //     .then(item => {
    //       // 创建映射表，将 array1 中的数据按 propertyType 作为键存储起来
    //       const mapArray1 = res.data.assetPropertyTypeDTO.reduce((map, obj) => {
    //         map[obj.propertyType] = obj;
    //         return map;
    //       }, {});

    //       // 更新 array2 中的数据
    //       const updatedArray2 = item.map(item2 => ({
    //         ...item2,
    //         netWorth: mapArray1[item2.id] ? mapArray1[item2.id].netWorth : 0,
    //         propertyTypeNum: mapArray1[item2.id] ? mapArray1[item2.id].propertyTypeNum : 0
    //       }));
    //       console.log(updatedArray2);
    //       option4.xAxis[0].data = updatedArray2.map(label => label.label);
    //       option4.series[0].data = updatedArray2.map(netWorth => netWorth.netWorth);
    //       option4.series[1].data = updatedArray2.map(propertyTypeNum => propertyTypeNum.propertyTypeNum);
    //       option4Ref.value.draw();
    //     });
    // }
  });
};
const warningDate = ref<any>({});
const getWarningFun = () => {
  getWarning().then(res => {
    warningDate.value = res.data;
  });
};

const writeOffChart = ref();
const getClassificationFun = () => {
  getClassification().then(res => {
    if (res.code == "0" && res.data) {
      option3.series[0].data = res.data
        .sort((a, b) => {
          return a.writeOffClassification.localeCompare(b.writeOffClassification);
        })
        .map(item => {
          return {
            value: item.writeOffNumber,
            name: item.writeOffClassification + "类：" + item.writeOffNumber
          };
        });
      writeOffChart.value.draw();
    }
  });
};

//诉讼状态
const proceedStatisticsRef = ref();
const proceedStatisticsFun = () => {
  proceedStatistics().then(proceed => {
    console.log(proceed.data);
    useBaseStore()
      .findEnumByName("PROJECT_STATE")
      .then(res => {
        option1.value.xAxis.data = res.map(item => item.itemName);
        if (proceed.data) {
          const mappedData = res.map(item => {
            // 提取项目状态的编号，例如 "PROJECT_STATE_01" -> "01"
            const stateNumber = item.itemCode.replace("PROJECT_STATE_", "").padStart(2, "0");
            // 查找键值对数据中是否存在对应的项目状态
            const value = proceed.data[`projectState${stateNumber}`] || 0; // 如果找不到，默认为0
            return {
              ...item,
              count: value // 将值映射到字典项中的 count 属性
            };
          });
          option1.value.series[0].data = mappedData.map(item => item.count);
        }
        proceedStatisticsRef.value.draw();
      });
  });
};
// const goUrl = url => {
//   router.push(url);
// };

const goToPro = (ids: any) => {
  const id = ids && ids.length ? ids : [0];
  useDialogStore().setProjectIds(id);
  router.push("/proceeding/projectInfo");
};

onMounted(() => {
  getProcessFunc();
  getStatisticsFun();
  getPastProjectsFun();
  getAnalyseFun();
  getWarningFun();
  getClassificationFun();
  proceedStatisticsFun();
});
</script>

<style scoped lang="scss">
@import "./index";
.homeflex {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 12px;
  & > div:first-child {
    width: 65px;
    color: rgb(176 195 233);
  }
  .el-link {
    font-size: 12px;
  }
}
.col-item {
  box-sizing: border-box;
  display: flex;
  height: 150px;
  padding: 10px;
  margin-bottom: 10px;
  border: 2px solid #e9e9e9;
  border-radius: 5px;
  &_left {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: center;
    width: 75px;
    margin-right: 10px;
    font-size: 13px;
    background: var(--el-color-primary-light-9);
    border-radius: 5px;
    &_warning {
      margin-top: 10px;
      font-size: 10px;
      color: rgb(176 195 233);
    }
  }
}
.flex-warp-item-box {
  padding: 15px 10px;
  margin: 10px;
  color: var(--el-text-color-primary);
  text-align: center;
  cursor: pointer;
  background: var(--next-bg-color);

  // display: flex;
  border-radius: 5px;
  transition: all 0.3s ease;
  &:hover {
    background: var(--el-color-primary-light-9);
    transition: all 0.3s ease;
  }
}
.pl_row_1 {
  display: inline-block;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  word-wrap: break-word;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
</style>
