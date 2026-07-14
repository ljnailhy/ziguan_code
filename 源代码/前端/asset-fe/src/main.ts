import { createApp } from "vue";
import App from "./App.vue";
// reset style sheet
import "@/styles/reset.scss";
// CSS common style sheet
import "@/styles/common.scss";
// iconfont css
import "@/assets/iconfont/iconfont.css";

// import "https://at.alicdn.com/t/c/font_4605752_jiz5h71858m.css";
// import "@/styles/font/iconfont.css";
// font css
import "@/assets/fonts/font.scss";
// element css
import "element-plus/dist/index.css";
// element dark css
import "element-plus/theme-chalk/dark/css-vars.css";
// custom element dark css
import "@/styles/element-dark.scss";
// custom element css
import "@/styles/element.scss";
// svg icons
import "virtual:svg-icons-register";
// element plus
import ElementPlus from "element-plus";
// element icons
import * as Icons from "@element-plus/icons-vue";
// custom directives
import directives from "@/directives/index";
// vue Router
import router from "@/routers";
// vue i18n
import I18n from "@/languages/index";
// pinia store
import { platformStore } from "@platform/vue-platform-ui";
// import pinia from "@/stores";

// errorHandler
import errorHandler from "@/utils/errorHandler";

import VuePlatformUi from "@platform/vue-platform-ui";

import GlobalComponents from "./components/common/globalComponents";

const app = createApp(App);

app.config.errorHandler = errorHandler;

// register the element Icons component
Object.keys(Icons).forEach(key => {
  app.component(key, Icons[key as keyof typeof Icons]);
});

app
  .use(ElementPlus)
  .use(directives)
  .use(router)
  .use(I18n)
  .use(platformStore)
  .use(VuePlatformUi)
  .use(GlobalComponents)
  // .use(pinia)
  .mount("#app");
