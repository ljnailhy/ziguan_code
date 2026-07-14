import { defineAsyncComponent } from "vue";

const components = {
  subjectInfo_createUpdate: defineAsyncComponent(() => import("@/views/source/subjectInfo/createUpdate.vue")),
  agency_createUpdate: defineAsyncComponent(() => import("@/views/source/agency/createUpdate.vue")),
  lawFirmInfo_createUpdate: defineAsyncComponent(() => import("@/views/source/lawFirmInfo/createUpdate.vue")),
  paymentCollectionTarget_createUpdate: defineAsyncComponent(
    () => import("@/views/compensatory/paymentCollectionTarget/createUpdate.vue")
  ),
  customerInfo_createUpdate: defineAsyncComponent(() => import("@/views/source/customerInfo/createUpdate.vue")),
  projectInfo_createUpdate: defineAsyncComponent(() => import("@/views/compensatory/projectInfo/createUpdate.vue")),
  projectInfo_createUpdate_edit: defineAsyncComponent(() => import("@/views/compensatory/projectInfo/createUpdate_edit.vue")),
  contractInfo_createUpdate: defineAsyncComponent(() => import("@/views/source/contractInfo/createUpdate.vue")),
  allocationInfo_createUpdate: defineAsyncComponent(() => import("@/views/compensatory/allocationInfo/createUpdate.vue")),
  workRegister_createUpdate: defineAsyncComponent(() => import("@/views/compensatory/workRegister/createUpdate.vue")),
  recoveryLitigation_createUpdate: defineAsyncComponent(() => import("@/views/proceeding/recoveryLitigation/createUpdate.vue")),
  recoveryJudgement_createUpdate: defineAsyncComponent(() => import("@/views/proceeding/recoveryJudgement/createUpdate.vue")),
  recoveryLitigationDetails_createUpdate: defineAsyncComponent(
    () => import("@/views/proceeding/recoveryLitigationDetails/createUpdate.vue")
  ),
  revePropertyInfo_createUpdate: defineAsyncComponent(() => import("@/views/compensatory/revePropertyInfo/createUpdate.vue")),
  recoveryAdjustTrial_createUpdate: defineAsyncComponent(() => import("@/views/proceeding/recoveryAdjustTrial/createUpdate.vue")),
  recoveryExecute_createUpdate: defineAsyncComponent(() => import("@/views/proceeding/recoveryExecute/createUpdate.vue")),
  writeOff_createUpdate: defineAsyncComponent(() => import("@/views/recover/writeOff/createUpdate.vue")),
  recoveryPaymentCollection_createUpdate: defineAsyncComponent(
    () => import("@/views/recover/recoveryPaymentCollection/createUpdate.vue")
  ),
  recoveryPayment_createUpdate: defineAsyncComponent(() => import("@/views/recover/recoveryPayment/createUpdate.vue")),
  projectInfo_changeLawyer: defineAsyncComponent(() => import("@/views/compensatory/projectInfo/changeLawyer.vue")),
  propertyInfo_createUpdate: defineAsyncComponent(() => import("@/views/property/propertyInfo/createUpdate.vue")),

  leaseInfo_flowUpdate: defineAsyncComponent(() => import("@/views/property/leaseInfo/flowUpdate.vue")), //流程中的租赁
  assetTransfer_flowUpdate: defineAsyncComponent(() => import("@/views/property/assetTransfer/flowUpdate.vue")), //流程中的转让
  leaseInfo_createUpdate: defineAsyncComponent(() => import("@/views/property/leaseInfo/createUpdate.vue")),
  assetTransfer_createUpdate: defineAsyncComponent(() => import("@/views/property/assetTransfer/createUpdate.vue")),
  assetTransfer_createUpdate_view: defineAsyncComponent(() => import("@/views/property/assetTransfer/flowUpdateView.vue")),

  operationInfo_createUpdate: defineAsyncComponent(() => import("@/views/property/operationInfo/createUpdate.vue")),
  projectTransfer_createUpdate: defineAsyncComponent(() => import("@/views/projectTransfer/createUpdate.vue")),
  propertyRightInfo_createUpdate: defineAsyncComponent(() => import("@/views/property/propertyRightInfo/createUpdate.vue"))
};

export default components;
