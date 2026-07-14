<template>
  <el-dialog v-model="dialogVisible" title="查看执行信息" draggable width="800px">
    <vz-card title="基本信息">
      <el-form label-width="140px" label-suffix=" :" :disabled="true" :model="form" :hide-required-asterisk="true">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="执行状态" prop="executeType">
              <el-select v-model="form!.executeType" clearable class="w100">
                <el-option label="已申请" value="APPLY"></el-option>
                <el-option label="未申请" value="UNAPPLY"></el-option>
                <el-option label="撤销执行" value="REVOKE"></el-option>
                <el-option label="执行和解" value="RECONCILIATE"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="日期" prop="applyExecuteDate">
              <el-date-picker v-model="form!.applyExecuteDate" type="date" placeholder="请选择日期" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="执行案号" prop="executeCode">
              <el-input v-model="form!.executeCode" placeholder="请输入执行案号" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="执行法院" prop="executeCourt">
              <el-input v-model="form!.executeCourt" placeholder="请输入执行法院" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="执行员" prop="executer">
              <el-input v-model="form!.executer" placeholder="请输入执行员" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20">
            <el-form-item label="联系电话" prop="executerTelphone">
              <el-input v-model="form!.executerTelphone" placeholder="请输入联系电话" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="执行裁定下达时间" prop="executeRulingIssuanceTime">
              <el-date-picker
                v-model="form!.executeRulingIssuanceTime"
                type="dates"
                placeholder="请选择执行裁定下达时间"
                class="w100"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remarks">
              <el-input type="textarea" :rows="5" v-model="form!.remarks" placeholder="请输入备注" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="反担保措施">
      <form-table :show-button="false" :table-data="reveInfoList" :readonly="true" :operation-width="120" :view-operation="true">
        <template #operation="scope">
          <el-button type="primary" link @click="openFapai(scope.row)">登记法拍记录</el-button>
        </template>
      </form-table>
    </vz-card>
    <vz-card title="其他财产信息">
      <form-table
        :show-button="false"
        :table-data="propertyInfoList"
        :readonly="true"
        :operation-width="120"
        :view-operation="true"
      >
        <template #operation="scope">
          <el-button type="primary" link @click="openFapai(scope.row)">登记法拍记录</el-button>
        </template>
      </form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="true" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
      </span>
    </template>
    <popFrameIndex ref="fapaiRef"></popFrameIndex>
  </el-dialog>
</template>
<script setup lang="tsx" name="Litigation">
import { ref } from "vue";
import { revePropertyInfo } from "@/api/modules/compensatory/revePropertyInfo/api";
import { securityTypeNatureOptions } from "@/api/modules/recovery/projectInfo/interface";
import { hangNetworkInfo } from "@/api/modules/hangNetworkInfo/api";
import popFrameIndex from "@/views/proceeding/hangNetworkInfo/popFrameIndex.vue";

//表单信息
const form = ref({
  executeType: "",
  applyExecuteDate: "",
  executeCode: "",
  executeCourt: "",
  executer: "",
  executerTelphone: "",
  executeRulingIssuanceTime: "",
  remarks: "",
  id: ""
});
const reveInfoList = ref({
  data: [],
  isHideDelete: true,
  header: [
    {
      prop: "securityWay",
      label: "担保方式",
      width: "120",
      isRequired: true,
      type: "select",
      dictType: "SECURITY_WAY",
      disabled: true
    },
    {
      prop: "securityType",
      label: "担保类型",
      width: "200",
      isRequired: true,
      type: "select",
      enum: securityTypeNatureOptions,
      disabled: true
    },
    {
      prop: "reveName",
      label: "反担保人名称",
      width: "120",
      isRequired: true,
      type: "text",
      disabled: true
    },
    {
      prop: "reveMeasure",
      label: "反担保措施",
      width: "200",
      type: "text",
      disabled: true
    },
    {
      prop: "debtRepaymentDate",
      label: "裁定以资抵债日期",
      width: "200",
      type: "date"
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额(元)",
      width: "200",
      type: "money",
      showWord: false,
      text: "元"
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean"
    },
    {
      prop: "disposeMoney",
      label: "处置回款金额(元)",
      width: "200",
      type: "money",
      showWord: false,
      text: "元"
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "200",
      type: "file",
      doType: "REVE"
    }
  ]
});
const queryReveProperty = (doId, doType) => {
  revePropertyInfo()
    .findByDoId(doId, doType, "REVE")
    .then((reveInfo: any) => {
      if (reveInfo.code == 0) {
        reveInfoList.value.data = reveInfo.data;
        reveInfoList.value.data.forEach((item: any) => {
          hangNetworkInfo()
            .findByDoId(item.id, item.billType)
            .then((fapaiList: any) => {
              if (fapaiList.code == 0) {
                item.hangNetworkInfoRequestList = fapaiList.data;
              }
            });
        });
      }
    });
  revePropertyInfo()
    .findByDoId(doId, doType, "PROPERTY")
    .then((propertyInfo: any) => {
      if (propertyInfo.code == 0) {
        propertyInfoList.value.data = propertyInfo.data;
        propertyInfoList.value.data.forEach((item: any) => {
          hangNetworkInfo()
            .findByDoId(item.id, item.billType)
            .then((fapaiList: any) => {
              if (fapaiList.code == 0) {
                item.hangNetworkInfoRequestList = fapaiList.data;
              }
            });
        });
      }
    });
};
const propertyInfoList = ref({
  data: [],
  isHideDelete: true,
  header: [
    {
      prop: "reveMeasure",
      label: "其他财产线索",
      width: "150",
      isRequired: true,
      type: "text",
      disabled: true
    },
    {
      prop: "debtRepaymentDate",
      label: "裁定以资抵债日期",
      width: "160",
      type: "date"
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额(元)",
      width: "120",
      type: "number"
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean"
    },
    {
      prop: "disposeMoney",
      label: "处置回款金(元)",
      width: "120",
      type: "number"
    },
    {
      prop: "fileInfoList",
      label: "附件",
      width: "160",
      type: "file",
      doType: "PROPERTY"
    }
  ]
});
const dialogVisible = ref(false);
const acceptParams = (params: any) => {
  form.value = {
    ...form.value,
    ...params,
    executeRulingIssuanceTime: params.executeRulingIssuanceTime ? params.executeRulingIssuanceTime.split(",").map(Number) : ""
  };
  if (params.id) {
    queryReveProperty(params.id, "RECOVERY_EXECUTE");
  } else {
    queryReveProperty(params.projectId, "PROJECT_INFO");
  }
  dialogVisible.value = true;
};

const fapaiRef = ref();
const openFapai = async (row: any) => {
  fapaiRef.value.handleFocus(row, true);
};

//暴漏变量给父级
defineExpose({
  acceptParams
});
</script>
