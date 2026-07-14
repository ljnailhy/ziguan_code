<template>
  <el-dialog v-model="dialogVisible" :title="`查看${title}信息`" draggable width="800px">
    <vz-card title="基本信息">
      <el-form ref="ruleFormRef" label-width="100px" label-suffix=" :" :disabled="true" :model="form" scroll-to-error>
        <el-row :gutter="35">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="标题名称" prop="title">
              <el-input v-model="form!.title" placeholder="请输入标题" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="props.type == LitigationTypeEnum.DROP_LAWSUIT">
            <el-form-item label="立案案号" prop="registerId">
              <vzRegister :default-value="form!.registerId"></vzRegister>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="props.type == LitigationTypeEnum.PRESERVATION">
            <el-form-item label="保全案号" prop="preservationCode">
              <el-input v-model="form!.preservationCode" placeholder="请输入保全案号" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="props.type != LitigationTypeEnum.PRESERVATION">
            <el-form-item :label="title + '时间'" prop="detailsDate">
              <el-date-picker v-model="form!.detailsDate" type="date" placeholder="请选择时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item :label="title + '说明'" prop="detailsDescription">
              <el-input
                type="textarea"
                :rows="5"
                v-model="form!.detailsDescription"
                placeholder="请输入说明"
                clearable
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- <el-form ref="ruleFormRef" label-width="100px" label-suffix=" :" scroll-to-error :disabled="true" :model="form">
        <el-row :gutter="35">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form!.title" placeholder="请输入标题" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="props.type == LitigationTypeEnum.DROP_LAWSUIT">
            <el-form-item label="立案案号" prop="registerId">
              <vzRegister :default-value="form!.registerId"></vzRegister>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="12" class="mb20" v-if="props.type != LitigationTypeEnum.PRESERVATION">
            <el-form-item :label="title + '时间'" prop="detailsDate">
              <el-date-picker v-model="form!.detailsDate" type="date" placeholder="请选择时间" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20" v-if="type != LitigationTypeEnum.PRESERVATION">
            <el-form-item :label="title + '说明'" prop="detailsDescription">
              <el-input type="textarea":rows="5" v-model="form!.detailsDescription" placeholder="请输入说明" clearable></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form> -->
    </vz-card>
    <vz-card title="反担保措施" v-if="props.type == LitigationTypeEnum.PRESERVATION">
      <form-table :show-button="false" :table-data="reveInfoList" :readonly="true"></form-table>
    </vz-card>
    <vz-card title="其他财产信息" v-if="props.type == LitigationTypeEnum.PRESERVATION">
      <form-table :show-button="false" :table-data="propertyInfoList" :readonly="true"></form-table>
    </vz-card>

    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="true" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup lang="tsx" name="RevokeInfoDetail">
import { onMounted, ref } from "vue";
import { LitigationTypeEnum } from "@/api/modules/proceeding/recoveryLitigationDetails/interface";
import { securityTypeNatureOptions } from "@/api/modules/recovery/projectInfo/interface";
import { revePropertyInfo } from "@/api/modules/compensatory/revePropertyInfo/api";

//引入组件
import vzRegister from "@/components/source/vzRegister.vue";

//定义父组件传过来的参数
type Props = {
  type?: string;
  title?: string;
};

const props = withDefaults(defineProps<Props>(), {
  type: "",
  title: ""
});

//表单信息
const form = ref({
  title: "",
  id: "",
  litigationType: "",
  registerId: "",
  detailsDescription: "",
  detailsDate: "",
  preservationCode: ""
});

//反担保措施
const reveInfoList = ref({
  data: [],
  header: [
    {
      prop: "securityWay",
      label: "担保方式",
      width: "150",
      isRequired: true,
      type: "text",
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
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean",
      disabled: true
    },
    {
      prop: "disposeMoney",
      label: "处置回款金额(元)",
      width: "160",
      type: "number",
      disabled: true
    },
    {
      prop: "preserveDate",
      label: "保全日期",
      width: "160",
      type: "date"
    },
    {
      prop: "remark",
      label: "备注",
      width: "120",
      type: "text"
    }
  ]
});

//其他财产信息
const propertyInfoList = ref({
  data: [],
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
      type: "date",
      disabled: true
    },
    {
      prop: "debtRepaymentMoney",
      label: "裁定抵债金额(元)",
      width: "160",
      type: "number",
      disabled: true
    },
    {
      prop: "isDispose",
      label: "是否已处置",
      width: "120",
      type: "boolean",
      disabled: true
    },
    {
      prop: "disposeMoney",
      label: "处置回款金(元)",
      width: "160",
      type: "number",
      disabled: true
    },
    {
      prop: "preserveDate",
      label: "保全日期",
      width: "160",
      type: "date"
    },
    {
      prop: "remark",
      label: "备注",
      width: "120",
      type: "text"
    }
  ]
});

const queryReveProperty = (doId, doType) => {
  if (!doId && !doType) return;
  revePropertyInfo()
    .findByDoId(doId, doType, "REVE")
    .then((reveInfo: any) => {
      if (reveInfo.code == 0) {
        reveInfoList.value.data = reveInfo.data;
      }
    });
  revePropertyInfo()
    .findByDoId(doId, doType, "PROPERTY")
    .then((propertyInfo: any) => {
      if (propertyInfo.code == 0) {
        propertyInfoList.value.data = propertyInfo.data;
      }
    });
};

const dialogVisible = ref(false);
const acceptParams = (params: any) => {
  form.value = {
    ...form.value,
    ...params
  };
  dialogVisible.value = true;
};

//页面加载时
onMounted(() => {
  if (props.type == LitigationTypeEnum.PRESERVATION) {
    queryReveProperty(form.value.id, form.value.litigationType);
  }
});

//暴漏变量给父级
defineExpose({
  acceptParams
});
</script>
