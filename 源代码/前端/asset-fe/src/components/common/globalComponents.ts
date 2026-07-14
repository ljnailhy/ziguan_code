import { App } from "vue";

import common_dialog from "./CommonDialog.vue";
import FileTable from "../FileTable/index.vue";
import FormTable from "../FormTable/index.vue";
import SvgIcon from "@/components/SvgIcon/index.vue";
import VzCard from "@/views/compensatory/projectInfo/component/VzCard.vue";
import VzLaw from "@/components/source/vzLaw.vue";

const components: EmptyObjectType = {
  "common-dialog": common_dialog,
  "file-table": FileTable,
  "form-table": FormTable,
  "svg-icon": SvgIcon,
  "vz-card": VzCard,
  "vz-law": VzLaw
};

export default {
  install: (app: App) => {
    for (const componentName in components) {
      app.component(componentName, components[componentName]);
    }
  }
};
