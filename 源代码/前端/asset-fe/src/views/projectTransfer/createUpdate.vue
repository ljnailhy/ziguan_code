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
        scroll-to-error
        :hide-required-asterisk="drawerProps.isView"
      >
        <el-row :gutter="35">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="标题名称" prop="title">
              <el-input v-model="form!.title" placeholder="请输入标题" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="移交说明" prop="transferIllustrate">
              <el-input
                type="textarea"
                :rows="5"
                v-model="form!.transferIllustrate"
                placeholder="请输入移交说明"
                clearable
                maxlength="2000"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="移交说明" prop="transferIllustrate">
              <WangEditor
                :disabled="drawerProps.isView"
                v-model:value="form!.transferIllustrate"
                placeholder="请输入分配变更说明"
                height="400px"
              />
            </el-form-item>
          </el-col> -->
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="项目移交明细">
      <form-table
        ref="projectTransferDetailedRequestListRef"
        :table-data="projectTransferDetailedRequestList"
        :readonly="drawerProps.isView"
        @on-select-change="onSelectChange"
      ></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="drawerProps.isView" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
    <projectInfo
      v-show="false"
      ref="projectInfoRef"
      @handle-ok="handleOk"
      :filter-data="{ projectStateList: projectState }"
      select-type="selection"
    ></projectInfo>
  </div>
</template>

<script setup lang="ts" name="projectTransferDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance } from "element-plus";
import { ProjectTransferRequest } from "@/api/modules/projectTransfer/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { projectTransfer } from "@/api/modules/projectTransfer/api"; // api
import { billLawyer } from "@/api/modules/compensatory/billLawyer/api";
import { projectTransferDetailed } from "@/api/modules/projectTransferDetailed/api";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { recoveryPaymentCollection } from "@/api/modules/recovery/recoveryPaymentCollection/api";
import { deduplicateArraysById } from "@/utils";

// import WangEditor from "@/components/WangEditor/index.vue";
import projectInfo from "@/components/source/vzProjectInfo.vue";
import { useBaseStore } from "@/stores/modules/baseInfo";

const projectInfoRef = ref();
const fileRef = ref();
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const rules = reactive({
  //transferIllustrate: [{ required: true, message: "请输入移交说明", trigger: "change" }],
  title: [{ required: true, message: "请输入标题", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

//获取父级传过来的数据
const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<Partial<ProjectTransferRequest>>({
  id: undefined // 这只是一个后面没逗号的坑位
});

// 单个查找
const bakprojectTransferDetailedRequestList = ref([]);
const projectState = ref();
const findById = async () => {
  const projectStateList = await useBaseStore().findEnumByName("PROJECT_STATE");
  const state = (projectState.value = projectStateList
    .filter(item => item.itemCode != "PROJECT_STATE_01") // 筛选条件
    .map(item => item.id));
  projectState.value = state;

  if (!drawerProps.value.id) return;
  const { data } = await projectTransfer().findById(drawerProps.value.id);
  projectTransferDetailed()
    .findByTransferId(data.id)
    .then((item: any) => {
      projectTransferDetailedRequestList.value.data = item.data;
      bakprojectTransferDetailedRequestList.value = JSON.parse(JSON.stringify(item.data));
    });
  form.value = data;
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const projectTransferDetailedRequestListRef = ref();
const saveFun = async () => {
  const fileEl = fileRef.value.tableRulesRef;
  const formEl = projectTransferDetailedRequestListRef.value?.tableRulesRef;

  try {
    await ruleFormRef.value!.validate();
    if (formEl) {
      await formEl!.validate();
    }
    if (fileEl) {
      await fileEl!.validate();
    }
  } catch (error) {
    return false;
  }

  if (!projectTransferDetailedRequestList.value.data || projectTransferDetailedRequestList.value.data.length == 0) {
    ElMessage.warning("移交明细必须有一条数据");
    return false;
  }
  for (let i = 0; i < projectTransferDetailedRequestList.value.data.length; i++) {
    const projectTransferDetailedRequest: any = projectTransferDetailedRequestList.value.data[i];
    console.log(projectTransferDetailedRequest.affiliatedOrg);
    if (!projectTransferDetailedRequest.affiliatedOrg) {
      ElMessage.warning("选择项目归属");
      return false;
    }

    projectTransferDetailedRequest.manage = Array.isArray(projectTransferDetailedRequest.manage)
      ? projectTransferDetailedRequest.manage.join(",")
      : projectTransferDetailedRequest.manage;
  }
  form.value.projectTransferDetailedRequestList = deduplicateArraysById(
    bakprojectTransferDetailedRequestList.value,
    projectTransferDetailedRequestList.value.data
  );
  form.value.fileInfoList = fileRef.value.submitForm;
  return true;
};
const handleSave = async () => {
  const result = await saveFun();
  if (!result) return false;

  try {
    drawerProps.value.title == "新增" ? await projectTransfer().add(form.value) : await projectTransfer().update(form.value);
    ElMessage.success({ message: `保存项目移交成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

// 提交数据（流程）
const handleSubmit = async () => {
  const result = await saveFun();
  if (!result) return false;
  try {
    await projectTransfer().submit(form.value);
    ElMessage.success({ message: `提交项目移交成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};
const mergerdArrayFun = async (arr: any, arr1: any) => {
  if (!arr) {
    const results = await Promise.all(
      arr1.map(async items => {
        const { data } = await billLawyer().findByDoId(items.id, "PROJECT_INFO");
        return {
          projectId: items.id,
          compensationMoney: items.compensationMoney,
          compensationDate: items.compensationDate,
          contractId: items.contractId,
          primaryAffiliatedOrg: items.affiliatedOrg,
          manage: items.manage,
          collectionAmount: data[items.id],
          residueCompensation: items.compensationMoney - data[items.id],
          lawyers: data && Array.isArray(data) ? data.join(",") : data,
          lawFirmId: items.lawFirmId
        };
      })
    );

    return results;
  }

  const arrPropertyIds = new Set(arr.map(item => item.projectId));

  // 获取需要添加的项目
  const itemsToAdd = await Promise.all(
    arr1
      .filter(item => !arrPropertyIds.has(item.id))
      .map(async items => {
        const { data } = await billLawyer().findByDoId(items.id, "PROJECT_INFO");
        return {
          projectId: items.id,
          compensationMoney: items.compensationMoney,
          compensationDate: items.compensationDate,
          contractId: items.contractId,
          primaryAffiliatedOrg: items.affiliatedOrg,
          manage: items.manage,
          collectionAmount: data[items.id],
          residueCompensation: items.compensationMoney - data[items.id],
          lawyers: data && Array.isArray(data) ? data.join(",") : data,
          lawFirmId: items.lawFirmId
        };
      })
  );

  const finalArr = [...arr, ...itemsToAdd];
  return finalArr;
};
const onSelectChange = async (val: any, index: number, item: any) => {
  const bakInfo = JSON.parse(JSON.stringify(projectTransferDetailedRequestList.value.data));
  //  判断如果是律师过滤只能选择该律所下面的律师
  // if ("lawFirm" == item.type) {
  //   row.lawyers = null;
  //   row.relatedContracts = null;
  //   row.lawyerType = { lawFirmId: val.id };
  //   row.contractType = { lawFirmId: val.id, contractType: CONTRACT_TYPE_001 };
  // }
  // //  下面是显示问题
  // if ("user" == item.type) {
  //   row[item.prop] = val;
  // } else {
  //   row[item.prop] = ensureArray(val).join(",");
  // }

  if (item.prop == "projectId") {
    projectTransferDetailedRequestList.value.data[index].projectId = "";
    if (Array.isArray(val) && projectTransferDetailedRequestList.value.data.length > 0) {
      projectTransferDetailedRequestList.value.data = await mergerdArrayFun(bakprojectTransferDetailedRequestList.value, val);
    } else {
      const containsTarget = bakInfo.some(item => item.projectId === val.id);
      const currentValue = bakInfo[index];
      if (containsTarget) {
        if (currentValue.projectId !== val.id) {
          projectTransferDetailedRequestList.value.data[index] = bakInfo[index];
        }
        return ElMessage.warning("已有相同的项目，请核对后再选择！");
      } else {
        const { data } = await billLawyer().findByDoId(val.id, "PROJECT_INFO");
        projectTransferDetailedRequestList.value.data[index] = {
          projectId: val.id,
          compensationMoney: val.compensationMoney,
          compensationDate: val.compensationDate,
          contractId: val.contractId,
          primaryAffiliatedOrg: val.affiliatedOrg,
          manage: val.manage,
          collectionAmount: data[val.id],
          residueCompensation: val.compensationMoney - data[val.id],
          lawyers: data && Array.isArray(data) ? data.join(",") : data,
          lawFirmId: val.lawFirmId
        };
      }
    }
  }
};
const handleOk = async (value: any) => {
  if (value) {
    value = Array.isArray(value) ? value : [value];
    projectTransferDetailedRequestList.value.data = [];
    const projectIds = value.map(item => item.id);
    const { data } = await recoveryPaymentCollection().batch(projectIds);
    for (let i = 0; i < value.length; i++) {
      let projectInfo = value[i];
      //  查询该项目律师
      billLawyer()
        .findByDoId(projectInfo.id, "PROJECT_INFO")
        .then((res: EmptyObjectType) => {
          if (res.code == 0) {
            // const manager =
            //   projectInfo.manage && Array.isArray(projectInfo.manage)
            //     ? projectInfo.manage.split(",")
            //     : projectInfo.manage
            //       ? [projectInfo.manage]
            //       : "";

            projectTransferDetailedRequestList.value.data.push({
              projectId: projectInfo.id,
              compensationMoney: projectInfo.compensationMoney,
              compensationDate: projectInfo.compensationDate,
              contractId: projectInfo.contractId,
              primaryAffiliatedOrg: projectInfo.affiliatedOrg,
              manage: projectInfo.manage,
              collectionAmount: data[projectInfo.id],
              residueCompensation: projectInfo.compensationMoney - data[projectInfo.id],
              lawyers: res.data && Array.isArray(res.data) ? res.data.join(",") : res.data,
              lawFirmId: projectInfo.lawFirmId
            });
          }
        });
    }
  }
};

const projectTransferDetailedRequestList = ref<any>({
  data: [],
  header: [
    {
      prop: "projectId",
      label: "项目名称",
      width: "250",
      type: "project",
      isRequired: true
      // disabled: true
    },
    {
      prop: "primaryAffiliatedOrg",
      label: "原项目归属方",
      width: "350",
      type: "org",
      isRequired: true
      // disabled: true
    },
    {
      prop: "affiliatedOrg",
      label: "项目归属",
      width: "350",
      isRequired: true,
      type: "org"
    },
    {
      prop: "compensationMoney",
      label: "代偿金额（元）",
      width: "150",
      type: "money",
      disabled: true
    },
    {
      prop: "compensationDate",
      label: "代偿时间",
      width: "180",
      type: "date",
      disabled: true
    },
    {
      prop: "collectionAmount",
      label: "累计回款金额（元）",
      width: "180",
      type: "money",
      disabled: true
    },
    {
      prop: "residueCompensation",
      label: "剩余代偿金额（元）",
      width: "180",
      type: "money",
      disabled: true
    },
    {
      prop: "manage",
      label: "保全经理",
      width: "200",
      type: "user",
      multiple: true,
      disabled: true
    },
    {
      prop: "lawFirmId",
      label: "律所",
      width: "210",
      type: "lawFirm",
      disabled: true
    },
    {
      prop: "lawyers",
      label: "律师",
      width: "210",
      type: "lawyer",
      disabled: true
    },
    {
      prop: "contractId",
      label: "关联合同",
      width: "210",
      type: "contract",
      disabled: true
    }
  ]
});

//页面加载时
onMounted(() => {
  findById();
});

defineExpose({
  handleSave,
  handleSubmit
});
</script>
