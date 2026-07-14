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
            <el-form-item label="标题名称" prop="title" :inline-message="true">
              <el-input v-model="form!.title" placeholder="请输入标题" clearable maxlength="500"></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
            <el-form-item label="分配变更说明" prop="allocationIllustrate">
              <WangEditor
                :disabled="drawerProps.isView"
                v-model:value="form!.allocationIllustrate"
                placeholder="请输入分配变更说明"
                height="400px"
              />
            </el-form-item>
          </el-col> -->
        </el-row>
      </el-form>
    </vz-card>
    <vz-card title="分配明细">
      <form-table
        ref="allocationInfoDetailRequestListRef"
        @on-select-change="onSelectChange"
        :table-data="allocationInfoDetailRequestList"
        :readonly="drawerProps.isView"
        select-type="selection"
      ></form-table>
    </vz-card>
    <vz-card title="附件信息">
      <file-table ref="fileRef" :readonly="drawerProps.isView" :query-data="{ doId: form!.id }"></file-table>
    </vz-card>
    <!-- <projectInfo
      v-show="false"
      ref="projectInfoRef"
      :filter-data="{ isWriteOff: false }"
      @handle-ok="handleOk"
      select-type="selection"
    ></projectInfo> -->
  </div>
</template>

<script setup lang="ts" name="allocationInfoDrawer">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, FormInstance, ElMessageBox } from "element-plus";
// import { AllocationInfoRequest, AllocationInfoDTO } from "@/api/modules/compensatory/allocationInfo/interface"; // 当前页面的数据类型 DTO是列表返回值的数据类型
import { allocationInfo } from "@/api/modules/compensatory/allocationInfo/api"; // api
import { allocationInfoDetail } from "@/api/modules/compensatory/allocationInfoDetail/api";
import { billLawyer } from "@/api/modules/compensatory/billLawyer/api";

// import projectInfo from "@/components/source/vzProjectInfo.vue";
// import WangEditor from "@/components/WangEditor/index.vue";
import { useDialogStore } from "@/stores/modules/dialogParams";
import { useBaseStore } from "@/stores/modules/baseInfo";
import { deduplicateArraysById } from "@/utils";
// 定义 emit 事件
const emit = defineEmits<{
  closeDrawer: [];
}>();

const allocationInfoDetailRequestList = ref<any>({
  data: [],
  header: [
    {
      prop: "projectId",
      label: "项目名称",
      width: "250",
      isRequired: true,
      align: "let",
      type: "project",
      projectType: { isWriteOff: false }
    },
    {
      prop: "compensationMoney",
      label: "代偿金额（元）",
      width: "150",
      isRequired: true,
      disabled: true,
      type: "money"
    },
    {
      prop: "compensationDate",
      label: "代偿时间",
      width: "150",
      isRequired: true,
      disabled: true,
      type: "date"
    },
    {
      prop: "manage",
      label: "保全经理",
      width: "200",
      type: "orguser",
      orgCode: "ZXD_ZCBQB",
      isRequired: true
    },
    {
      prop: "lawFirm",
      label: "律所",
      width: "210",
      type: "lawFirm",
      isRequired: true
    },
    {
      prop: "lawyers",
      label: "律师",
      width: "210",
      type: "lawyer"
    },
    {
      prop: "relatedContracts",
      label: "关联合同",
      width: "210",
      type: "contract"
      // , isRequired: true
    }
  ]
});
const rules = reactive({
  //allocationIllustrate: [{ required: true, message: "请输入分配/变更说明", trigger: "change" }],
  title: [{ required: true, message: "请输入标题", trigger: "change" }],
  lastEditor: [{ required: false, message: "这只是一个后面没逗号的坑位" }]
});

const drawerProps = ref<any>(useDialogStore().drawerProps);

const form = ref<any>({
  /** 是否删除 */
  isDelete: false,
  //allocationIllustrate: "",
  id: undefined // 这只是一个后面没逗号的坑位
});

const mergerdArrayFun = async (arr: any, arr1: any) => {
  if (!arr) {
    const results = await Promise.all(
      arr1.map(async items => {
        const { data } = await billLawyer().findByDoId(items.id, "PROJECT_INFO");
        return {
          projectId: items.id,
          compensationMoney: items.compensationMoney,
          compensationDate: items.compensationDate,
          relatedContracts: items.contractId,
          manage: items.manage,
          lawyers: data && Array.isArray(data) ? data.join(",") : data,
          lawFirm: items.lawFirmId,
          lawyerType: { lawFirmId: items.lawFirmId },
          contractType: { lawFirmId: items.lawFirmId, contractType: CONTRACT_TYPE_001 }
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
          relatedContracts: items.contractId,
          manage: items.manage,
          lawyers: data && Array.isArray(data) ? data.join(",") : data,
          lawFirm: items.lawFirmId,
          lawyerType: { lawFirmId: items.lawFirmId },
          contractType: { lawFirmId: items.lawFirmId, contractType: CONTRACT_TYPE_001 }
        };
      })
  );

  const finalArr = [...arr, ...itemsToAdd];
  return finalArr;
};
const onSelectChange = async (val: any, index: number, item: any, row: EmptyObjectType) => {
  const bakInfo = JSON.parse(JSON.stringify(allocationInfoDetailRequestList.value.data));
  //  判断如果是律师过滤只能选择该律所下面的律师
  if ("lawFirm" == item.type) {
    row.lawyers = null;
    row.relatedContracts = null;
    row.lawyerType = { lawFirmId: val.id };
    row.contractType = { lawFirmId: val.id, contractType: CONTRACT_TYPE_001 };
  }
  //  下面是显示问题
  if ("orguser" == item.type || "lawyer" == item.type || "lawFirm" == item.type) {
    const text = item.type == "lawyer" ? "律师" : item.type == "orguser" ? "保全经理" : "律所";
    if (bakInfo.length > 1) {
      ElMessageBox.confirm(`是否一致用当前选中的${text}?`, "温馨提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        allocationInfoDetailRequestList.value.data.forEach(items => {
          if ("orguser" == item.type) {
            items[item.prop] = val;
          } else {
            items[item.prop] = ensureArray(val).join(",");
          }
        });
      });
    } else {
      if ("orguser" == item.type) {
        row[item.prop] = val;
      } else {
        row[item.prop] = ensureArray(val).join(",");
      }
    }
    // row[item.prop] = val;
  } else {
    row[item.prop] = ensureArray(val).join(",");
  }

  if (item.prop == "projectId") {
    allocationInfoDetailRequestList.value.data[index].projectId = "";
    if (Array.isArray(val) && allocationInfoDetailRequestList.value.data.length > 0) {
      allocationInfoDetailRequestList.value.data = await mergerdArrayFun(bakalloInfo.value, val);
    } else {
      const containsTarget = bakInfo.some(item => item.projectId === val.id);
      const currentValue = bakInfo[index];
      if (containsTarget) {
        if (currentValue.projectId !== val.id) {
          allocationInfoDetailRequestList.value.data[index] = bakInfo[index];
        }
        return ElMessage.warning("已有相同的项目，请核对后再选择！");
      } else {
        const { data } = await billLawyer().findByDoId(val.id, "PROJECT_INFO");
        allocationInfoDetailRequestList.value.data[index] = {
          projectId: val.id,
          compensationMoney: val.compensationMoney,
          compensationDate: val.compensationDate,
          relatedContracts: val.contractId,
          manage: val.manage,
          lawyers: data && Array.isArray(data) ? data.join(",") : data,
          lawFirm: val.lawFirmId,
          lawyerType: { lawFirmId: val.lawFirmId },
          contractType: { lawFirmId: val.lawFirmId, contractType: CONTRACT_TYPE_001 }
        };
      }
    }
  }
};
// const onSelectChange = (val: any, index: number, item: EmptyObjectType, row: EmptyObjectType) => {
//   //  判断如果是律师过滤只能选择该律所下面的律师
//   if ("lawFirm" == item.type) {
//     row.lawyers = null;
//     row.relatedContracts = null;
//     row.lawyerType = { lawFirmId: val.id };
//     row.contractType = { lawFirmId: val.id, contractType: CONTRACT_TYPE_001 };
//   }
//   //  下面是显示问题
//   if ("user" == item.type) {
//     row[item.prop] = val;
//   } else {
//     row[item.prop] = ensureArray(val).join(",");
//   }
// };

// const handleOk = (value: EmptyArrayType) => {
//   if (value) {
//     value = Array.isArray(value) ? value : [value];
//     allocationInfoDetailRequestList.value.data = [];
//     for (let i = 0; i < value.length; i++) {
//       let projectInfo = value[i];
//       //  查询该项目律师
//       billLawyer()
//         .findByDoId(projectInfo.id, "PROJECT_INFO")
//         .then((res: EmptyObjectType) => {
//           if (res.code == 0) {
//             // const manager =
//             //   projectInfo.manage && Array.isArray(projectInfo.manage)
//             //     ? projectInfo.manage.split(",")
//             //     : projectInfo.manage
//             //       ? [projectInfo.manage]
//             //       : "";

//             allocationInfoDetailRequestList.value.data.push({
//               projectId: projectInfo.id,
//               compensationMoney: projectInfo.compensationMoney,
//               compensationDate: projectInfo.compensationDate,
//               relatedContracts: projectInfo.contractId,
//               manage: projectInfo.manage,
//               lawyers: res.data && Array.isArray(res.data) ? res.data.join(",") : res.data,
//               lawFirm: projectInfo.lawFirmId,
//               lawyerType: { lawFirmId: projectInfo.lawFirmId },
//               contractType: { lawFirmId: projectInfo.lawFirmId, contractType: CONTRACT_TYPE_001 }
//             });
//           }
//         });
//     }
//   }
// };

// const projectInfoRef = ref();

// 定义一个函数，检查是否为数组，如果不是则转换为数组
const ensureArray = value => {
  if (Array.isArray(value)) {
    return value.map(obj => obj.id);
  } else {
    return [value.id];
  }
};

// 单个查找
const CONTRACT_TYPE_001 = ref();
const bakalloInfo = ref([]);
const findById = async () => {
  useBaseStore()
    .findEnumByName("CONTRACT_TYPE")
    .then((res: EmptyObjectType) => {
      for (let i = 0; i < res.length; i++) {
        if ("CONTRACT_TYPE_001" == res[i].itemCode) {
          CONTRACT_TYPE_001.value = res[i].id;
        }
      }
    });
  if (!drawerProps.value.id) {
    const year = new Date().getFullYear();
    const month = new Date().getMonth() + 1;
    const day = new Date().getDate();
    form.value.title = `${year}年${month}月${day}日提交项目分配`;
    return;
  }
  const { data } = await allocationInfo().findById(drawerProps.value.id);
  form.value = data;
  //  查询分配明细
  allocationInfoDetail()
    .findByAllocationId(drawerProps.value.id)
    .then((res: EmptyObjectType) => {
      if (res.code == 0) {
        allocationInfoDetailRequestList.value.data = res.data;
        bakalloInfo.value = JSON.parse(JSON.stringify(res.data));
        for (let i = 0; i < allocationInfoDetailRequestList.value.data.length; i++) {
          const allallocationInfoDetailRequest = allocationInfoDetailRequestList.value.data[i];
          allallocationInfoDetailRequest.lawyerType = { lawFirmId: allallocationInfoDetailRequest.lawFirm };
          allallocationInfoDetailRequest.contractType = {
            lawFirmId: allallocationInfoDetailRequest.lawFirm,
            contractType: CONTRACT_TYPE_001
          };
        }
      } else {
        ElMessage.warning(res.msg);
      }
    });
};

// 保存数据（新增/编辑）
const ruleFormRef = ref<FormInstance>();
const allocationInfoDetailRequestListRef = ref();
const fileRef = ref();
const saveFun = async () => {
  const formEl = allocationInfoDetailRequestListRef.value?.tableRulesRef;
  try {
    if (formEl) {
      await formEl.validate();
    }
    if (ruleFormRef.value) {
      await ruleFormRef.value!.validate();
    }

    if (allocationInfoDetailRequestList.value.data.length == 0 || !allocationInfoDetailRequestList.value.data.length) {
      ElMessage.warning("分配明细不能为空");
      return false;
    }
  } catch (error) {
    return false;
  }
  for (let i = 0; i < allocationInfoDetailRequestList.value.data.length; i++) {
    const allocationInfoDetail: any = allocationInfoDetailRequestList.value.data[i];
    allocationInfoDetail.manage = Array.isArray(allocationInfoDetail.manage)
      ? allocationInfoDetail.manage.join(",")
      : allocationInfoDetail.manage;
  }
  form.value.fileInfoList = fileRef.value.submitForm;
  form.value.allocationInfoDetailRequestList = deduplicateArraysById(
    bakalloInfo.value,
    allocationInfoDetailRequestList.value.data
  );
  return true;
};
const handleSave = async () => {
  try {
    const result = await saveFun();

    if (!result) return false;
    drawerProps.value.title == "新增" ? await allocationInfo().add!(form.value) : await allocationInfo().update!(form.value);
    ElMessage.success({ message: `保存分配/变更主表成功！` });
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
  try {
    const result = await saveFun();
    if (!result) return false;
    await allocationInfo().submit!(form.value);
    ElMessage.success({ message: `保存分配/变更主表成功！` });
    drawerProps.value.getTableList!();
    emit("closeDrawer");
    return true;
  } catch (error) {
    console.log(error);
    return false;
  }
};

onMounted(() => {
  if (drawerProps.value.objList && drawerProps.value.objList.length > 0) {
    allocationInfoDetailRequestList.value.data = drawerProps.value.objList.map(item => {
      return {
        projectId: item.id,
        manage: item.manage,
        compensationMoney: item.compensationMoney,
        compensationDate: item.compensationDate,
        lawFirm: item.lawFirmId,
        lawyers: item.lawyer,
        relatedContracts: item.relatedContracts
      };
    });
  }
  findById();
});

defineExpose({
  handleSave,
  handleSubmit
});
</script>
