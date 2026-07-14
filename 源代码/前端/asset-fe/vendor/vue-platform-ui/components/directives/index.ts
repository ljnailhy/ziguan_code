import { App, Directive } from "vue";

import money from "./modules/currency";
import thousands from "./modules/thousands";

const directivesList: { [key: string]: Directive } = {
  thousands,
  money,
};

const directives = {
  install: function (app: App<Element>) {
    Object.keys(directivesList).forEach((key) => {
      app.directive(key, directivesList[key]);
    });
  },
};

export default directives;
