import { defineStore } from "@platform/vue-platform-ui";
import { DialogState } from "@/stores/interface";

export const useDialogStore = defineStore({
  id: "vz-dialogInfo",
  state: (): DialogState => ({
    drawerProps: {},
    validationErrorMessage: "",
    deleteFiles: {},
    projectIds: []
  }),
  getters: {},
  actions: {
    setDrawerProps(drawerProps: any) {
      this.drawerProps = drawerProps;
    },
    setProjectIds(ids: any[]) {
      this.projectIds = ids;
    },
    setValidationErrorMessage(message: string) {
      this.validationErrorMessage = message;
    },
    setDeleteFiles(id: number, files: any) {
      this.deleteFiles[id] = files;
    },
    clearDeleteFiles() {
      this.deleteFiles = {};
    }
  }
});
