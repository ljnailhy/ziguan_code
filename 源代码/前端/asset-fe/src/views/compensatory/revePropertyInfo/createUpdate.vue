<template>
  <div>
    <el-collapse v-model="activeNames">
      <el-collapse-item name="1" title="基本信息">
        <el-form
          ref="ruleFormRef"
          label-width="150px"
          label-suffix=" :"
          :rules="rules"
          :disabled="drawerProps.isView"
          :model="form"
          :hide-required-asterisk="drawerProps.isView"
        >
          <el-row :gutter="35">
            <el-col :xs="24" :sm="24" :md="1242" :lg="24" :xl="24" class="mb20">
              <el-form-item label="其他财产线索" prop="reveMeasure">
                <el-input
                  type="textarea"
                  :rows="5"
                  v-model="form!.reveMeasure"
                  placeholder="请输入其他财产线索"
                  clearable
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="裁定以资抵债日期" prop="debtRepaymentDate">
                <el-date-picker v-model="form!.debtRepaymentDate" type="date" placeholder="请选择裁定以资抵债日期" class="w100" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="裁定抵债金额" prop="debtRepaymentMoney">
                <vz-input-unit
                  v-model="form!.debtRepaymentMoney"
                  :value="form!.debtRepaymentMoney"
                  placeholder="请输入裁定抵债金额"
                  text="万元"
                  clearable
                ></vz-input-unit>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="是否已处置" prop="isDispose">
                <el-switch v-model="form!.isDispose" />
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
              <el-form-item label="处置回款金额" prop="disposeMoney">
                <vz-input-unit
                  v-model="form!.disposeMoney"
                  :value="form!.disposeMoney"
                  placeholder="请输入处置回款金额"
                  text="万元"
                  clearable
                ></vz-input-unit>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-collapse-item>
      <!-- <el-collapse-item name="2" title="附件信息">
        <file-table
          :upload-form="form!.fileInfoList"
          :readonly="drawerProps.isView"
          :query-data="{ doId: form!.id }"
        ></file-table>
      </el-collapse-item> -->
    </el-collapse>
  </div>
</template>

<script setup lang="ts" name="revePropertyInfoDrawer">
import { ref, reactive } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import {
  RevePropertyInfoRequest,
  RevePropertyInfoDTO,
  BillTypeEnum,
  SecurityTypeEnum
} from "@/api/modules/compensatory/revePropertyInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { revePropertyInfo } from "@/api/modules/compensatory/revePropertyInfo/api"; // api

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const activeNames = ref(["1", "2"]);
const initParamsJson = localStorage.getItem("initParams");
const initParam: any = ref(initParamsJson ? JSON.parse(initParamsJson) : {});
const rules = reactive({
  reveMeasure: [{ required: true, message: "请输入其他财产线索", trigger: "blur" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

interface DrawerProps {
  title: string;
  isView: boolean;
  showbtn?: boolean;
  id?: number;
  api?: (params: Partial<RevePropertyInfoRequest>) => Promise<RevePropertyInfoDTO>;
  getTableList?: () => void;
}

const drawerProps = ref<DrawerProps>({
  isView: false,
  showbtn: false,
  title: ""
});

const form = ref<Partial<RevePropertyInfoRequest>>({
  /** 单据类型（其他财产，反担保） PROPERTY:其他财产 REVE:反担保 */
  billType: BillTypeEnum.REVE,
  /** 担保类型 QYBZ:企业保证 GRBZ:自然人保证 BDCDY:不动产抵押 DCDY:动产抵押YSZKZY:应收账款质押 QLZY:权力质押 CHZY:存货质押 QTLX:其他类型 NO:无 */
  securityType: SecurityTypeEnum.QYBZ,
  /** 是否已处置 */
  isDispose: false,
  id: undefined // 这只是一个后面没逗号的坑位
});

const handleAccept = (params: DrawerProps) => {
  drawerProps.value = params;
  findById();
};

// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await revePropertyInfo().findById(drawerProps.value.id);
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const handleSave = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      form.value.doId = initParam.value?.doId;
      form.value.doType = initParam.value?.doType;
      form.value.billType = initParam.value?.billType;
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}其他财产线索信息成功！` });
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
      ElMessage.success({ message: `${drawerProps.value.title}反担保/其他财产线索信息成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};
defineExpose({
  handleSave,
  handleSubmit,
  handleAccept
});
</script>
