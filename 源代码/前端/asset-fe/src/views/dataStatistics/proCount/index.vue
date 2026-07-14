<template>
  <div class="table-box">
    <vz-table
      ref="vzTableRef"
      :columns="columns"
      :request-api="getTableList"
      :init-param="initParam"
      :tree-props="{ children: 'reveInfoList', hasChildren: 'true' }"
    >
      <template #proInfo="scope">
        <div class="flx" style="font-weight: bold">
          {{ scope.row.projectName }}
        </div>
        <div class="flx">
          代偿金额：
          <div v-currency="scope.row.compensationMoney"></div>
        </div>
        <div class="flx">
          代偿时间：
          <dict-date :date="scope.row.compensationDate" format="YYYY-MM-DD"></dict-date>
        </div>
        <div class="flx">
          移交至保全部日期：
          <dict-date :date="scope.row.transferDate" format="YYYY-MM-DD"></dict-date>
        </div>
      </template>
      <template #stateInfo="scope">
        <div class="flx">
          项目状态：
          <dict-name dict-type="PROJECT_STATE" :dict-value="scope.row.projectState"></dict-name>
        </div>
        <div class="flx">是否核销：{{ scope.row.isWriteOff ? "是" : "否" }}</div>
      </template>
      <template #layerManage="scope">
        <div class="flx">
          保全经理：
          <div v-text="scope.row.manage"></div>
        </div>
        <div class="flx">
          代理律所：
          <div v-text="scope.row.name"></div>
        </div>
        <div class="flx">
          代理律师：
          <div v-text="scope.row.lawyerName"></div>
        </div>
        <div class="flx">
          代理合同到期日：
          <dict-date :date="scope.row.endDate" format="YYYY-MM-DD"></dict-date>
        </div>
      </template>
      <template #ssFeeInfo="scope">
        <div class="flx">
          案件受理费：
          <div v-currency="scope.row.payType01"></div>
        </div>
        <div class="flx">
          保全费：
          <div v-currency="scope.row.payType02"></div>
        </div>
        <div class="flx">
          保险费：
          <div v-currency="scope.row.payType03"></div>
        </div>
        <div class="flx">
          律师费：
          <div v-currency="scope.row.payType05"></div>
        </div>
        <div class="flx">
          其他费用：
          <div v-currency="scope.row.payType04"></div>
        </div>
      </template>
      <template #sxInfo="scope">
        <div class="flx">
          诉讼时效：
          <dict-date :date="scope.row.proceedingAgeingDate" format="YYYY-MM-DD"></dict-date>
        </div>
        <div class="flx">
          执行时效：
          <dict-date :date="scope.row.adjustTrialDate" format="YYYY-MM-DD"></dict-date>
        </div>
      </template>
      <template #ssDate="scope">
        <div class="flx">
          立案日期：
          <dict-date :date="scope.row.fillingDate" format="YYYY-MM-DD"></dict-date>
        </div>
        <div class="flx">
          保全日期：
          <dict-date :date="scope.row.preservationDate" format="YYYY-MM-DD"></dict-date>
        </div>
        <div class="flx">
          开庭日期：
          <dict-date :date="scope.row.courtSessionDate" format="YYYY-MM-DD"></dict-date>
        </div>
        <div class="flx">
          判决日期：
          <dict-date :date="scope.row.judgeDate" format="YYYY-MM-DD"></dict-date>
        </div>
      </template>
      <template #ahInfo="scope">
        <div class="flx">立案案号：{{ scope.row.fillingCode }}</div>
        <div class="flx">保全案号：{{ scope.row.preservationCode }}</div>
        <div class="flx">判决案号：{{ scope.row.adjustCode }}</div>
        <div class="flx">执行案号：{{ scope.row.executeCode }}</div>
      </template>

      <template #otherInfo="scope">
        <div class="flx">
          证件号码：
          <div v-text="scope.row.documentNumber"></div>
        </div>
        <div class="flx">
          行业分类：
          <div v-text="scope.row.industryType"></div>
        </div>
        <div class="flx">
          所在区域：
          <div>{{ scope.row.belongProvince }}-</div>
          <div>{{ scope.row.belongCity }}-</div>
          <div>{{ scope.row.belongDistrict }}</div>
        </div>
      </template>
      <template #recoveryCollectionAmount="scope">
        <div class="flx">
          现金：
          <div v-currency="scope.row.cashAmount"></div>
        </div>
        <div class="flx">
          再担保：
          <div v-currency="scope.row.reGuaranteeAmount"></div>
        </div>
        <div class="flx">
          抵债：
          <div v-currency="scope.row.mortgageAmount"></div>
        </div>
        <div class="flx">
          银票：
          <div v-currency="scope.row.silverBillAmount"></div>
        </div>

        <div class="flx">
          合计金额：
          <div v-currency="scope.row.recoveryCollectionAmount"></div>
        </div>
      </template>
      <template #writeOffInfo="scope">
        <div class="flx">
          核销时间：
          <dict-date :date="scope.row.writeOffDate" format="YYYY-MM-DD"></dict-date>
        </div>
        <div class="flx">
          核销金额：
          <div v-currency="scope.row.writeDffAmount"></div>
        </div>
        <div class="flx">
          核销项目分类：
          <dict-enum :options="writeOffClassificationOptions" :value="scope.row.writeOffClassification"></dict-enum>
        </div>
      </template>
      <template #abInfo="scope">
        <div class="flx">A角：{{ scope.row.aname }}</div>
        <div class="flx">B角：{{ scope.row.bname }}</div>
      </template>
      <template #preserveDate="scope">
        <dict-date :date="scope.row.preserveDate" format="YYYY-MM-DD"></dict-date>
      </template>
      <template #debtRepaymentDate="scope">
        <dict-date :date="scope.row.debtRepaymentDate" format="YYYY-MM-DD"></dict-date>
      </template>
      <template #debtRepaymentMoney="scope">
        <div v-currency="scope.row.debtRepaymentMoney"></div>
      </template>
      <template #isDispose="scope">
        {{ scope.row.isDispose ? "是" : "否" }}
      </template>
      <template #isWriteOff="scope">
        {{ scope.row.isWriteOff ? "是" : "否" }}
      </template>
      <!-- <template #disposeMoney="scope">
        <div v-currency="scope.row.disposeMoney"></div>
      </template>
      <template #payType01="scope">
        <div v-currency="scope.row.payType01"></div>
      </template>
      <template #payType02="scope">
        <div v-currency="scope.row.payType02"></div>
      </template>
      <template #payType03="scope">
        <div v-currency="scope.row.payType03"></div>
      </template>
      <template #payType04="scope">
        <div v-currency="scope.row.payType04"></div>
      </template>
      <template #payType05="scope">
        <div v-currency="scope.row.payType05"></div>
      </template> -->
      <!-- 表格 header 按钮 -->
      <template #operation="scope">
        <el-button type="primary" link @click="openDrawer(scope.row)">查看</el-button>
      </template>
      <template #tableHeader>
        <el-button type="primary" v-auth="'export'" @click="handleExport()">导出</el-button>
      </template>
    </vz-table>

    <!--    <common-dialog ref="dialogRef"></common-dialog>-->
  </div>
</template>

<script setup lang="tsx" name="writeOff">
import { ref, reactive } from "vue";
import { VzTableInstance, ColumnProps } from "@/components/VzTable/interface"; // table数据类型 固定的
// 当前页面的数据类型 DTO是列表返回值的数据类型
import { IsWriteOffOptions, ProjectInfoPageRequest } from "@/api/modules/recovery/projectInfo/interface";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { ProjectLedgerDTO } from "@/api/modules/dataStatistics/proCount/interface";
import { projectLedger } from "@/api/modules/dataStatistics/proCount/api";
import { useDownload } from "@/hooks/useDownload";
import { ElMessageBox } from "element-plus";
import { useRouter } from "vue-router";
import { writeOffClassificationOptions } from "@/api/modules/recovery/writeOff/interface";

// vzTable 实例
const vzTableRef = ref<VzTableInstance>();
const router = useRouter();

const handleExport = async () => {
  ElMessageBox.confirm("你确定导出吗？", "温馨提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
    draggable: true
  })
    .then(async () => {
      useDownload(projectLedger().exportProjectLedger, "项目台账", vzTableRef.value?.searchParam);
    })
    .catch(() => {
      // cancel operation
    });
};

// 表格配置项
const columns = reactive<ColumnProps<ProjectLedgerDTO>[]>([
  // { type: "selection", fixed: "left", width: 70 },
  { type: "index", label: "序号", width: 60, fixed: "left" },
  {
    prop: "proInfo",
    label: "项目信息",
    fixed: "left",
    minWidth: 320
  },
  {
    prop: "stateInfo",
    label: "状态信息",
    minWidth: 200
  },
  {
    prop: "layerManage",
    label: "律所&保全经理",
    width: "250"
  },
  {
    prop: "recoveryCollectionAmount",
    label: "回款金额(元)",
    minWidth: 220
  },
  {
    prop: "ssFeeInfo",
    label: "诉讼杂费",
    minWidth: 250
  },

  {
    prop: "projectName",
    label: "项目名称",
    minWidth: 250,
    align: "left",
    fixed: "left",
    isShow: false,
    headerAlign: "center",
    search: { el: "input" }
  },
  {
    prop: "projectState",
    label: "项目状态",
    minWidth: 90,
    isShow: false,
    search: { el: "select", props: { filterable: true } },
    enum: () => useBaseStore().findEnumByName("PROJECT_STATE")
  },
  {
    prop: "industryType",
    label: "行业分类",
    minWidth: 200,
    search: { el: "select", props: { filterable: true } },
    enum: () => useBaseStore().findEnumByName("INDUSTRY_GXB"),
    isShow: false
  },
  {
    prop: "manage",
    label: "保全经理",
    width: "120",
    isShow: false,
    type: "user",
    search: {
      render: ({ searchParam }) => {
        return <vz-user-org v-model={searchParam.manage} org-code={"ZXD_ZCBQB"} dict-value={searchParam.manage} />;
      }
    }
  },
  {
    prop: "lawFirmName",
    label: "律所名称",
    width: "120",
    isShow: false,
    // type: "user",
    search: {
      render: ({ searchParam }) => {
        return <vz-law v-model={searchParam.name} default-value={searchParam.name} />;
      }
    }
  },
  {
    prop: "isWriteOff",
    label: "是否核销",
    type: "boolean",
    isShow: false,
    enum: IsWriteOffOptions,
    search: { el: "select", props: { filterable: true } },
    minWidth: 90
  },
  {
    type: "date",
    prop: "compensationDate",
    label: "代偿时间",
    minWidth: 200,
    isShow: false,
    search: {
      key: "compensationDateRange", //指定搜索的key
      el: "date-picker",
      span: 1,
      props: {
        type: "datetimerange",
        format: "YYYY-MM-DD HH:mm:ss",
        valueFormat: "x",
        defaultTime: [new Date("1970-01-01T16:00:00Z"), new Date("1970-01-01T15:59:59Z")]
      }
    }
  },
  // {
  //   prop: "compensationMoney",
  //   label: "代偿金额(元)",
  //   minWidth: 150,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },
  {
    prop: "sxInfo",
    label: "诉讼&执行时效",
    minWidth: 200
  },
  {
    prop: "ssDate",
    label: "诉讼时点",
    minWidth: 200
  },
  {
    prop: "ahInfo",
    label: "案号",
    minWidth: 300
  },
  {
    prop: "otherInfo",
    label: "其他信息",
    minWidth: 300
  },
  // {
  //   type: "area",
  //   prop: "belongCity",
  //   label: "所属城市",
  //   minWidth: 200,
  //   isShow: false,
  //   search: { el: "input", props: { filterable: true } }
  // },
  {
    prop: "belongCity",
    label: "所在区域",
    minWidth: 200,
    isShow: false,
    search: {
      render: ({ searchParam }) => {
        return (
          <vz-area
            area={[searchParam.province, searchParam.city, searchParam.district]}
            onChange={val => ((searchParam.province = val[0]), (searchParam.city = val[1]), (searchParam.district = val[2]))}
            level={1}
            check-strictly={true}
          />
        );
      }
    }
  },
  {
    prop: "defendant",
    label: "被告(被执行人)",
    minWidth: 300
  },
  {
    prop: "reveMeasure",
    label: "反担保措施/财产线索",
    align: "left",
    minWidth: 500
  },
  // {
  //   prop: "preserveDate",
  //   label: "保全日期",
  //   minWidth: 120,
  //   type: "date"
  // },
  // {
  //   type: "date",
  //   prop: "debtRepaymentDate",
  //   label: "裁定以资抵债日期",
  //   minWidth: 150
  // },
  {
    type: "money",
    prop: "debtRepaymentMoney",
    label: "裁定抵债金额",
    minWidth: 120,
    align: "right",
    headerAlign: "center"
  },
  // {
  //   type: "boolean",
  //   prop: "isDispose",
  //   label: "是否已处置",
  //   minWidth: 100
  // },
  {
    prop: "disposeMoney",
    label: "处置金额(元)",
    minWidth: 120,
    type: "money",
    align: "right",
    headerAlign: "center"
  },
  // {
  //   prop: "aname",
  //   label: "A角",
  //   minWidth: 120
  // },
  // {
  //   prop: "bname",
  //   label: "B角",
  //   minWidth: 120
  // },

  {
    prop: "writeOffInfo",
    label: "核销项目信息",
    minWidth: 180
  },
  {
    prop: "abInfo",
    label: "项目经理A/B角",
    minWidth: 150
  },
  // {
  //   prop: "proceedingAgeingDate",
  //   label: "诉讼时效",
  //   minWidth: 120,
  //   type: "date"
  // },
  // {
  //   prop: "adjustTrialDate",
  //   label: "执行时效",
  //   minWidth: 120,
  //   type: "date"
  // }
  // {
  //   prop: "fillingDate",
  //   label: "立案日期",
  //   minWidth: 120,
  //   type: "date"
  // },
  // {
  //   prop: "preservationDate",
  //   label: "保全日期",
  //   minWidth: 120,
  //   type: "date"
  // },
  // {
  //   prop: "courtSessionDate",
  //   label: "开庭日期",
  //   minWidth: 120,
  //   type: "date"
  // },
  // {
  //   prop: "judgeDate",
  //   label: "判决日期",
  //   minWidth: 120,
  //   type: "date"
  // },
  // {
  //   prop: "preservationCode",
  //   label: "保全案号",
  //   minWidth: 160
  // },
  // {
  //   prop: "adjustCode",
  //   label: "判决案号",
  //   minWidth: 160
  // },
  // {
  //   prop: "executeCode",
  //   label: "执行案号",
  //   minWidth: 160
  // }
  // {
  //   prop: "residueRecoveryAmount",
  //   label: "代偿余额(元)",
  //   minWidth: 150,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },
  // {
  //   prop: "payType01",
  //   label: "诉讼费",
  //   minWidth: 120,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },
  // {
  //   prop: "payType02",
  //   label: "保全费",
  //   minWidth: 120,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },
  // {
  //   prop: "payType03",
  //   label: "保险费",
  //   minWidth: 120,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },
  // {
  //   prop: "payType05",
  //   label: "律师费用",
  //   minWidth: 120,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // },
  // {
  //   prop: "payType04",
  //   label: "其他费用",
  //   minWidth: 120,
  //   type: "money",
  //   align: "right",
  //   headerAlign: "center"
  // }
  {
    prop: "operation",
    label: "操作",
    fixed: "right",
    minWidth: 100
  }
]);

// 如果表格需要初始化请求参数，直接定义传给 vzTable
// (之后每次请求都会自动带上该参数，此参数更改之后也会一直带上，改变此参数会自动刷新表格数据)
const initParam = reactive({});

// 如果在请求之前对当前请求参数做一些操作，可以自定义如下函数：params 为当前所有的请求参数（包括分页），最后返回请求列表接口
// 默认不做操作就直接在 vzTable 组件上绑定	:requestApi="useCustomApi().findAll()"
const getTableList = (params: ProjectInfoPageRequest) => {
  let newParams = JSON.parse(JSON.stringify(params));
  delete newParams.createStamp;
  return projectLedger().findProjectLedgerAll(newParams);
};
// const objectSpanMethod = ({ rowIndex, columnIndex }: SpanMethodProps) => {
//   if (columnIndex === 3) {
//     if (rowIndex % 2 === 0) return { rowspan: 2, colspan: 1 };
//     else return { rowspan: 0, colspan: 0 };
//   }
// };
// 打开 dialog(新增、查看、编辑)
// const dialogRef = ref<any>(null);
const openDrawer = (row: any = {}) => {
  router.push(`/compensatory/projectInfo/detail/${row.id}`);
};
</script>

<style scoped lang="scss"></style>
