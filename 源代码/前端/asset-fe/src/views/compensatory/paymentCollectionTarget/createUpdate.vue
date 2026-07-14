<template>
  <div>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="150px"
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        scroll-to-error
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="年度" prop="year">
              <el-date-picker v-model="form!.year" type="year" placeholder="请选择年度" class="w100" />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="自主追偿回款目标" prop="targetMoney">
              <vz-input-unit
                v-model="form!.targetMoney"
                :value="form!.targetMoney"
                placeholder="请输入自主追偿回款目标"
                text="元"
                clearable
                disabled
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="委托追偿回款目标" prop="entrustMoney">
              <vz-input-unit
                v-model="form!.entrustMoney"
                :value="form!.entrustMoney"
                placeholder="请输入委托追偿回款目标"
                text="元"
                clearable
                disabled
              ></vz-input-unit>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="备注" prop="remark">
              <el-input
                :rows="5"
                type="textarea"
                v-model="form!.remark"
                placeholder="请输入备注"
                clearable
                maxlength="2000"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="回款目标明细">
      <form-table
        :table-data="detailedInfoList"
        @on-select-change="onSelectChange"
        :readonly="drawerProps.isView"
        @money-input="moneyBlur"
        @del-row="delRow"
      ></form-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="paymentCollectionTargetDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { PaymentCollectionTargetRequest } from "@/api/modules/compensatory/paymentCollectionTarget/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { paymentCollectionTarget } from "@/api/modules/compensatory/paymentCollectionTarget/api"; // api
import { paymentCollectionTargetDetail } from "@/api/modules/compensatory/paymentCollectionTargetDetail/api";
import { getUserInfoById } from "@/api/modules/user/user";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { deduplicateArraysById } from "@/utils";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  year: [{ required: true, message: "请选择年度", trigger: "change" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<PaymentCollectionTargetRequest>>({
  id: undefined // 这只是一个后面没逗号的坑位
});

const onSelectChange = (val: any, index: number, item: EmptyObjectType, row: EmptyObjectType) => {
  if (val && item.type == "user") {
    console.log(detailedInfoList.value.data.map(userId => userId.userId));
    getUserInfoById(val).then((userInfo: any) => {
      row.companyId = userInfo.data.companyId;
      row.deptId = userInfo.data.deptId;
    });
  }
};

const detailedInfoList = ref<any>({
  data: [],
  header: [
    {
      prop: "companyId",
      label: "公司",
      width: "110",
      isRequired: true,
      type: "org",
      disabled: true
    },
    {
      prop: "deptId",
      label: "部门",
      width: "150",
      isRequired: true,
      type: "org",
      disabled: true
    },
    {
      prop: "userId",
      label: "人员",
      width: "150",
      isRequired: true,
      type: "user"
    },
    {
      prop: "targetDetailMoney",
      label: "自主追偿回款目标（元）",
      width: "150",
      type: "money",
      max: 999999999999,
      isRequired: true
    },
    {
      prop: "entrustDetailMoney",
      label: "委托追偿回款目标（元）",
      width: "150",
      type: "money",
      max: 999999999999,
      isRequired: true
    }
  ]
});
const delRow = async () => {
  // detailedInfoList.value.data.splice(index, 1);
  moneyBlur(); // 重新计算总金额
};

const moneyBlur = async () => {
  let targetMoney = 0;
  let entrustMoney = 0;
  const nodelete = detailedInfoList.value.data.filter(item => item.operateType !== "DELETE");
  for (let i = 0; i < nodelete.length; i++) {
    const detailedInfo: any = nodelete[i].targetDetailMoney;
    const detailedInfoNum = isNaN(Number(detailedInfo)) ? 0 : Number(detailedInfo);
    targetMoney = detailedInfoNum + targetMoney;

    const edMoney: any = nodelete[i].entrustDetailMoney;
    const money = isNaN(Number(edMoney)) ? 0 : Number(edMoney);
    entrustMoney = money + entrustMoney;
  }
  form!.value.targetMoney = targetMoney;
  form!.value.entrustMoney = entrustMoney;
};

// 单个查找
const bakdetailedInfoList = ref([]);
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await paymentCollectionTarget().findById(drawerProps.value.id);

  //	加载回款目标明细
  paymentCollectionTargetDetail()
    .findByTargetId(drawerProps.value.id)
    .then((res: EmptyObjectType) => {
      if (res.code == 0) {
        detailedInfoList.value.data = res.data;
        bakdetailedInfoList.value = JSON.parse(JSON.stringify(res.data));
      } else {
        ElMessage.warning(res.msg);
      }
    });
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSave = () => {
  if (form.value.targetMoney && form.value?.targetMoney > 999999999999) {
    return ElMessage.warning("自主追偿回款目标错误，请重新输入！");
  }
  if (form.value.entrustMoney && form.value?.entrustMoney > 999999999999) {
    return ElMessage.warning("委托追偿回款目标错误，请重新输入！");
  }
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    if (detailedInfoList.value.data.length === 0) {
      ElMessage.warning("至少需要一条回款目标明细");
      return;
    }
    for (let i = 0; i < detailedInfoList.value.data.length; i++) {
      const detailedInfo: any = detailedInfoList.value.data[i];
      if (!detailedInfo.companyId || !detailedInfo.deptId || !detailedInfo.userId || !detailedInfo.targetDetailMoney) {
        ElMessage.warning("回款明细必填项未填写完整请检查");
        return;
      }
    }

    try {
      // form.value.paymentCollectionTargetDetailRequests = detailedInfoList.value.data;
      form.value.paymentCollectionTargetDetailRequests = deduplicateArraysById(
        bakdetailedInfoList.value,
        detailedInfoList.value.data
      );
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}年度回款目标成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

// 提交数据（流程）
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}年度回款目标成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

//页面加载时
onMounted(() => {
  findById();
});

//暴露变量给父级
defineExpose({
  handleSave,
  handleSubmit
});
</script>
