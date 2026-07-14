<template>
  <div>
    <vz-card title="基本信息">
      <el-form
        ref="ruleFormRef"
        label-width="100px"
        label-suffix=" :"
        :rules="rules"
        :disabled="drawerProps.isView"
        :model="form"
        :hide-required-asterisk="drawerProps.isView"
        scroll-to-error
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="主体名称" prop="subjectName">
              <el-input v-model="form!.subjectName" placeholder="请输入主体名称" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="主体性质" prop="nature">
              <el-select @change="natureSelectChange" v-model="form!.nature" placeholder="请选择主体性质">
                <el-option key="E" label="企业" value="E" />
                <el-option key="NE" label="非企业经济组织" value="NE" />
                <el-option key="IB" label="个体工商户" value="IB" />
                <el-option key="SME" label="小微企业主" value="SME" />
                <el-option key="FM" label="农户" value="FM" />
                <el-option key="OTHER" label="其他" value="OTHER" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="主体类型" prop="subjectType">
              <el-select v-model="form!.subjectType" placeholder="请选择主体类型">
                <el-option key="COUNTER_GUARANTOR" label="反担保人" value="COUNTER_GUARANTOR" />
                <el-option key="DEBTOR" label="债务人" value="DEBTOR" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="证件类型" prop="documentType">
              <el-select v-model="form!.documentType" placeholder="请选择主体类型">
                <el-option key="SFZ" label="身份证" value="SFZ"></el-option>
                <el-option key="YYZZ" label="营业执照" value="YYZZ"></el-option>
                <el-option key="JGZ" label="军官证" value="JGZ"></el-option>
                <el-option key="SBZ" label="士兵证" value="SBZ"></el-option>
                <el-option key="HZ" label="护照" value="HZ"></el-option>
                <el-option key="JSZ" label="驾驶证" value="JSZ"></el-option>
                <el-option key="ZZZ" label="暂住证" value="ZZZ"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="证件号码" prop="documentNumber">
              <el-input v-model="form!.documentNumber" placeholder="请输入证件号码" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="法定代表人" prop="legalRepresentative">
              <el-input v-model="form!.legalRepresentative" placeholder="请输入法定代表人" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系人" prop="contacts">
              <el-input v-model="form!.contacts" placeholder="请输入联系人" clearable maxlength="50"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="联系人电话" prop="phone">
              <el-input v-model="form!.phone" placeholder="请输入联系人电话" clearable maxlength="30"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
            <el-form-item label="区域">
              <vz-area @change="changeBelongArea" :area="belongAreaArray" :level="1" :check-strictly="false"></vz-area>
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="区域_省" prop="belongProvince">
            <el-input v-model="form!.belongProvince" placeholder="请输入区域_省" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="区域_市" prop="belongCity">
            <el-input v-model="form!.belongCity" placeholder="请输入区域_市" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="12" :xl="8" class="mb20">
          <el-form-item label="区域_区" prop="belongDistrict">
            <el-input v-model="form!.belongDistrict" placeholder="请输入区域_区" clearable></el-input>
          </el-form-item>
        </el-col> -->
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="详细地址" prop="address">
              <el-input type="text" v-model="form!.address" placeholder="请输入详细地址" clearable maxlength="200"></el-input>
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
                show-word-limit
                maxlength="2000"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="drawerProps.isView" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
  </div>
</template>

<script setup lang="ts" name="subjectInfoDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { SubjectInfoRequest, NatureEnum, SubjectTypeEnum, DocumentTypeEnum } from "@/api/modules/source/subjectInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { subjectInfo } from "@/api/modules/source/subjectInfo/api"; // api
import { useDialogStore } from "@/stores/modules/dialogParams";
// import { textPhone } from "@/utils";

// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const belongAreaArray = ref<any>([]);
const rules = reactive({
  subjectName: [{ required: true, message: "请输入主体名称", trigger: "blur" }],
  nature: [
    {
      required: true,
      message: "请选择主体性质",
      trigger: "change"
    }
  ],
  subjectType: [{ required: true, message: "请选择主体类型", trigger: "change" }],
  documentType: [
    {
      required: true,
      message: "请选择证件类型 ",
      trigger: "change"
    }
  ],
  // phone: [{ required: true, validator: textPhone, trigger: "blur" }],
  isDelete: [{ required: true, message: "请选择是否删除", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }],
  documentNumber: [{ required: true, message: "请输入证件号码" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<SubjectInfoRequest>>({
  /** 主体性质 E:企业 NE:非企业经济组织 FM:农户 IB:个体工商户 SME:小伟企业主 OTHER：其他 */
  nature: NatureEnum.E,
  /** 主体类型COUNTER_GUARANTOR:反担保人，DEBTOR：债务人 */
  subjectType: SubjectTypeEnum.DEBTOR,
  /** 证件类型 SFZ身份证 YYZZ 营业执照 JGZ 军官证 SBZ 士兵证 HZ 护照 JSZ 驾驶证 ZZZ 暂住证 */
  documentType: DocumentTypeEnum.YYZZ,
  /** 是否删除 */
  isDelete: false,
  fileInfoList: [],
  id: undefined // 这只是一个后面没逗号的坑位
});

//	省市区变化赋值
const changeBelongArea = val => {
  if (val[0]) {
    form.value.belongProvince = val[0];
    if (val[1]) {
      form.value.belongCity = val[1];
      if (val[2]) {
        form.value.belongDistrict = val[2];
      }
    }
  }
};
// 单个查找
const findById = async () => {
  if (!drawerProps.value.id) return;
  const { data } = await subjectInfo().findById(drawerProps.value.id);
  form.value = data;
  belongAreaArray.value = [data.belongProvince, data.belongCity, data.belongDistrict];
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const fileRef = ref();
const handleSave = async () => {
  if (form!.value.phone) {
    if (!isValidPhoneNumber(form!.value.phone)) {
      ElMessage.error("手机号校验失败");
      return;
    }
  }
  const fileFormEl = fileRef.value?.tableRulesRef;
  form.value.fileInfoList = fileRef.value.submitForm;
  try {
    await ruleFormRef.value!.validate();
    if (fileFormEl) {
      fileFormEl.validate();
    }
    await drawerProps.value.api!(form.value);
    ElMessage.success({ message: `${drawerProps.value.title}主体信息成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
  } catch (error) {
    console.log(error);
  }
};
const natureSelectChange = () => {
  if (form?.value.nature && form?.value.nature == "E") {
    console.log(DocumentTypeEnum.YYZZ);
    form.value.documentType = DocumentTypeEnum.YYZZ;
  }
};
// 手机号正则：以1开头，后面跟9位数字
const mobileRegex = /^1[3-9]\d{9}$/;
// 固定电话正则：如 010-xxxxxxx 或 021-xxxxxxx
const landlineRegex = /^\d{3,4}-\d{7,8}$/;
const isValidPhoneNumber = input => {
  return mobileRegex.test(input) || landlineRegex.test(input);
};

// 提交数据（流程）
const handleSubmit = () => {
  ruleFormRef.value!.validate(async valid => {
    if (!valid) return;
    try {
      await drawerProps.value.api!(form.value);
      ElMessage.success({ message: `${drawerProps.value.title}主体信息成功！` });
      drawerProps.value.getTableList!();
      emit("closeDrawer");
    } catch (error) {
      console.log(error);
    }
  });
};

onMounted(() => {
  findById();
});
defineExpose({
  handleSave,
  handleSubmit
});
</script>
