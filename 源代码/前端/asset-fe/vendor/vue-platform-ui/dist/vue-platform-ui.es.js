var dc = Object.defineProperty;
var pc = (e, t, a) => t in e ? dc(e, t, { enumerable: !0, configurable: !0, writable: !0, value: a }) : e[t] = a;
var On = (e, t, a) => pc(e, typeof t != "symbol" ? t + "" : t, a);
import { defineComponent as Ue, openBlock as z, createElementBlock as he, toDisplayString as We, hasInjectionContext as Bo, inject as dr, effectScope as To, ref as se, markRaw as Nr, toRaw as Pa, isRef as Jt, isReactive as yn, toRef as Xa, getCurrentInstance as hc, watch as nt, unref as ye, reactive as or, nextTick as Lr, computed as He, getCurrentScope as vc, onScopeDispose as xc, toRefs as Ni, onMounted as yt, resolveComponent as Q, createVNode as Z, withCtx as re, Fragment as it, renderList as St, createBlock as ve, createElementVNode as $e, createTextVNode as rt, normalizeClass as a0, withDirectives as aa, createCommentVNode as qe, resolveDynamicComponent as Na, mergeProps as nr, createSlots as n0, renderSlot as Rt, onBeforeMount as gc, onActivated as yc, onUnmounted as mc, onDeactivated as Cc, provide as Ea, useSlots as Io, normalizeStyle as mn, useAttrs as Ec, vShow as i0, withModifiers as bc, pushScopeId as Sc, popScopeId as Ac, normalizeProps as _c, guardReactiveProps as Bc, isVNode as Tc, resolveDirective as Ic } from "vue";
import { ElMessageBox as Ri, ElMessage as ze, ElTable as wc, ElLoading as Dc, ElNotification as Nc } from "element-plus";
import wo from "axios";
import ba from "js-cookie";
import ki from "qs";
import * as Rc from "@element-plus/icons-vue";
import { Search as Ua, Plus as kc, Refresh as Cn, ArrowDown as Fc, ArrowUp as Lc, Operation as Pc, User as Uc, Lock as Oc, WarnTriangleFilled as Vc, CircleClose as Mc, UserFilled as Kc, Download as Hc, Setting as zc } from "@element-plus/icons-vue";
import Do from "sortablejs";
import { useRouter as $c } from "vue-router";
import qc from "@vue-office/docx";
import jc from "@vue-office/excel";
import Gc from "mitt";
function _a(e, t) {
  if (!e) return "-";
  let a = e.getDay(), r = Wc(e), n = Math.floor((e.getMonth() + 3) / 3).toString();
  const i = {
    "Y+": e.getFullYear().toString(),
    // 年
    "M+": (e.getMonth() + 1).toString(),
    // 月(月份从0开始，要+1)
    "D+": e.getDate().toString(),
    // 日
    "H+": e.getHours().toString(),
    // 时
    "m+": e.getMinutes().toString(),
    // 分
    "s+": e.getSeconds().toString(),
    // 秒
    "q+": n
    // 季度
  }, s = {
    0: "日",
    1: "一",
    2: "二",
    3: "三",
    4: "四",
    5: "五",
    6: "六"
  }, o = {
    1: "一",
    2: "二",
    3: "三",
    4: "四"
  };
  /(W+)/.test(t) && (t = t.replace(RegExp.$1, RegExp.$1.length > 1 ? RegExp.$1.length > 2 ? "星期" + s[a] : "周" + s[a] : s[a])), /(Q+)/.test(t) && (t = t.replace(RegExp.$1, RegExp.$1.length == 4 ? "第" + o[n] + "季度" : o[n])), /(Z+)/.test(t) && (t = t.replace(RegExp.$1, RegExp.$1.length == 3 ? "第" + r + "周" : r + ""));
  for (let u in i) {
    let l = new RegExp("(" + u + ")").exec(t);
    l && (t = t.replace(l[1], RegExp.$1.length == 1 ? i[u] : i[u].padStart(RegExp.$1.length, "0")));
  }
  return t;
}
function Wc(e) {
  let t = new Date(e.getTime()), a = t.getDay() || 7;
  t.setDate(t.getDate() - a + 1 + 5);
  let r = new Date(t.getFullYear(), 0, 1), n = r.getDay(), i = 1;
  n != 0 && (i = 7 - n + 1), r = new Date(t.getFullYear(), 0, 1 + i);
  let s = Math.ceil((t.valueOf() - r.valueOf()) / 864e5);
  return Math.ceil(s / 7);
}
function fy(e, t = "YYYY-MM-DD") {
  let a, r, n = (/* @__PURE__ */ new Date()).getTime();
  if (a = new Date(e).getTime(), n = Number.parseInt(`${n - a}`), n < 1e4)
    return "刚刚";
  if (n < 6e4 && n >= 1e4)
    return r = Math.floor(n / 1e3), `${r}秒前`;
  if (n < 36e5 && n >= 6e4)
    return r = Math.floor(n / 6e4), `${r}分钟前`;
  if (n < 864e5 && n >= 36e5)
    return r = Math.floor(n / 36e5), `${r}小时前`;
  if (n < 2592e5 && n >= 864e5)
    return r = Math.floor(n / 864e5), `${r}天前`;
  {
    let i = new Date(e);
    return _a(i, t);
  }
}
function dy(e) {
  let t = new Date(e).getHours();
  return t < 6 ? "凌晨好" : t < 9 ? "早上好" : t < 12 ? "上午好" : t < 14 ? "中午好" : t < 17 ? "下午好" : t < 19 ? "傍晚好" : t < 22 ? "晚上好" : "夜里好";
}
const Yc = { style: { display: "inline-block" } }, Qc = /* @__PURE__ */ Ue({
  __name: "dictDate",
  props: {
    date: {
      type: [Number, String],
      default: () => ""
    },
    format: {
      type: String,
      default: () => ""
    }
  },
  setup(e) {
    const t = e, a = () => t.date ? _a(new Date(t.date), t.format) : "--";
    return (r, n) => (z(), he("div", Yc, We(a()), 1));
  }
});
var No = !1;
function za(e, t, a) {
  return Array.isArray(e) ? (e.length = Math.max(e.length, t), e.splice(t, 1, a), a) : (e[t] = a, a);
}
function Vn(e, t) {
  if (Array.isArray(e)) {
    e.splice(t, 1);
    return;
  }
  delete e[t];
}
function Xc() {
  return Ro().__VUE_DEVTOOLS_GLOBAL_HOOK__;
}
function Ro() {
  return typeof navigator < "u" && typeof window < "u" ? window : typeof globalThis < "u" ? globalThis : {};
}
const Zc = typeof Proxy == "function", Jc = "devtools-plugin:setup", eu = "plugin:settings:set";
let jr, Fi;
function tu() {
  var e;
  return jr !== void 0 || (typeof window < "u" && window.performance ? (jr = !0, Fi = window.performance) : typeof globalThis < "u" && (!((e = globalThis.perf_hooks) === null || e === void 0) && e.performance) ? (jr = !0, Fi = globalThis.perf_hooks.performance) : jr = !1), jr;
}
function ru() {
  return tu() ? Fi.now() : Date.now();
}
class au {
  constructor(t, a) {
    this.target = null, this.targetQueue = [], this.onQueue = [], this.plugin = t, this.hook = a;
    const r = {};
    if (t.settings)
      for (const s in t.settings) {
        const o = t.settings[s];
        r[s] = o.defaultValue;
      }
    const n = `__vue-devtools-plugin-settings__${t.id}`;
    let i = Object.assign({}, r);
    try {
      const s = localStorage.getItem(n), o = JSON.parse(s);
      Object.assign(i, o);
    } catch {
    }
    this.fallbacks = {
      getSettings() {
        return i;
      },
      setSettings(s) {
        try {
          localStorage.setItem(n, JSON.stringify(s));
        } catch {
        }
        i = s;
      },
      now() {
        return ru();
      }
    }, a && a.on(eu, (s, o) => {
      s === this.plugin.id && this.fallbacks.setSettings(o);
    }), this.proxiedOn = new Proxy({}, {
      get: (s, o) => this.target ? this.target.on[o] : (...u) => {
        this.onQueue.push({
          method: o,
          args: u
        });
      }
    }), this.proxiedTarget = new Proxy({}, {
      get: (s, o) => this.target ? this.target[o] : o === "on" ? this.proxiedOn : Object.keys(this.fallbacks).includes(o) ? (...u) => (this.targetQueue.push({
        method: o,
        args: u,
        resolve: () => {
        }
      }), this.fallbacks[o](...u)) : (...u) => new Promise((l) => {
        this.targetQueue.push({
          method: o,
          args: u,
          resolve: l
        });
      })
    });
  }
  async setRealTarget(t) {
    this.target = t;
    for (const a of this.onQueue)
      this.target.on[a.method](...a.args);
    for (const a of this.targetQueue)
      a.resolve(await this.target[a.method](...a.args));
  }
}
function ko(e, t) {
  const a = e, r = Ro(), n = Xc(), i = Zc && a.enableEarlyProxy;
  if (n && (r.__VUE_DEVTOOLS_PLUGIN_API_AVAILABLE__ || !i))
    n.emit(Jc, e, t);
  else {
    const s = i ? new au(a, n) : null;
    (r.__VUE_DEVTOOLS_PLUGINS__ = r.__VUE_DEVTOOLS_PLUGINS__ || []).push({
      pluginDescriptor: a,
      setupFn: t,
      proxy: s
    }), s && t(s.proxiedTarget);
  }
}
/*!
 * pinia v2.1.7
 * (c) 2023 Eduardo San Martin Morote
 * @license MIT
 */
let Qr;
const na = (e) => Qr = e, py = () => Bo() && dr(En) || Qr, En = process.env.NODE_ENV !== "production" ? Symbol("pinia") : (
  /* istanbul ignore next */
  Symbol()
);
function Pr(e) {
  return e && typeof e == "object" && Object.prototype.toString.call(e) === "[object Object]" && typeof e.toJSON != "function";
}
var Zt;
(function(e) {
  e.direct = "direct", e.patchObject = "patch object", e.patchFunction = "patch function";
})(Zt || (Zt = {}));
const Oa = typeof window < "u", ta = (process.env.NODE_ENV !== "production" || !1) && process.env.NODE_ENV !== "test" && Oa, $0 = typeof window == "object" && window.window === window ? window : typeof self == "object" && self.self === self ? self : typeof global == "object" && global.global === global ? global : typeof globalThis == "object" ? globalThis : { HTMLElement: null };
function nu(e, { autoBom: t = !1 } = {}) {
  return t && /^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(e.type) ? new Blob(["\uFEFF", e], { type: e.type }) : e;
}
function s0(e, t, a) {
  const r = new XMLHttpRequest();
  r.open("GET", e), r.responseType = "blob", r.onload = function() {
    Po(r.response, t, a);
  }, r.onerror = function() {
    console.error("could not download file");
  }, r.send();
}
function Fo(e) {
  const t = new XMLHttpRequest();
  t.open("HEAD", e, !1);
  try {
    t.send();
  } catch {
  }
  return t.status >= 200 && t.status <= 299;
}
function Za(e) {
  try {
    e.dispatchEvent(new MouseEvent("click"));
  } catch {
    const a = document.createEvent("MouseEvents");
    a.initMouseEvent("click", !0, !0, window, 0, 0, 0, 80, 20, !1, !1, !1, !1, 0, null), e.dispatchEvent(a);
  }
}
const Ja = typeof navigator == "object" ? navigator : { userAgent: "" }, Lo = /Macintosh/.test(Ja.userAgent) && /AppleWebKit/.test(Ja.userAgent) && !/Safari/.test(Ja.userAgent), Po = Oa ? (
  // Use download attribute first if possible (#193 Lumia mobile) unless this is a macOS WebView or mini program
  typeof HTMLAnchorElement < "u" && "download" in HTMLAnchorElement.prototype && !Lo ? iu : (
    // Use msSaveOrOpenBlob as a second approach
    "msSaveOrOpenBlob" in Ja ? su : (
      // Fallback to using FileReader and a popup
      ou
    )
  )
) : () => {
};
function iu(e, t = "download", a) {
  const r = document.createElement("a");
  r.download = t, r.rel = "noopener", typeof e == "string" ? (r.href = e, r.origin !== location.origin ? Fo(r.href) ? s0(e, t, a) : (r.target = "_blank", Za(r)) : Za(r)) : (r.href = URL.createObjectURL(e), setTimeout(function() {
    URL.revokeObjectURL(r.href);
  }, 4e4), setTimeout(function() {
    Za(r);
  }, 0));
}
function su(e, t = "download", a) {
  if (typeof e == "string")
    if (Fo(e))
      s0(e, t, a);
    else {
      const r = document.createElement("a");
      r.href = e, r.target = "_blank", setTimeout(function() {
        Za(r);
      });
    }
  else
    navigator.msSaveOrOpenBlob(nu(e, a), t);
}
function ou(e, t, a, r) {
  if (r = r || open("", "_blank"), r && (r.document.title = r.document.body.innerText = "downloading..."), typeof e == "string")
    return s0(e, t, a);
  const n = e.type === "application/octet-stream", i = /constructor/i.test(String($0.HTMLElement)) || "safari" in $0, s = /CriOS\/[\d]+/.test(navigator.userAgent);
  if ((s || n && i || Lo) && typeof FileReader < "u") {
    const o = new FileReader();
    o.onloadend = function() {
      let u = o.result;
      if (typeof u != "string")
        throw r = null, new Error("Wrong reader.result type");
      u = s ? u : u.replace(/^data:[^;]*;/, "data:attachment/file;"), r ? r.location.href = u : location.assign(u), r = null;
    }, o.readAsDataURL(e);
  } else {
    const o = URL.createObjectURL(e);
    r ? r.location.assign(o) : location.href = o, r = null, setTimeout(function() {
      URL.revokeObjectURL(o);
    }, 4e4);
  }
}
function ct(e, t) {
  const a = "🍍 " + e;
  typeof __VUE_DEVTOOLS_TOAST__ == "function" ? __VUE_DEVTOOLS_TOAST__(a, t) : t === "error" ? console.error(a) : t === "warn" ? console.warn(a) : console.log(a);
}
function o0(e) {
  return "_a" in e && "install" in e;
}
function Uo() {
  if (!("clipboard" in navigator))
    return ct("Your browser doesn't support the Clipboard API", "error"), !0;
}
function Oo(e) {
  return e instanceof Error && e.message.toLowerCase().includes("document is not focused") ? (ct('You need to activate the "Emulate a focused page" setting in the "Rendering" panel of devtools.', "warn"), !0) : !1;
}
async function lu(e) {
  if (!Uo())
    try {
      await navigator.clipboard.writeText(JSON.stringify(e.state.value)), ct("Global state copied to clipboard.");
    } catch (t) {
      if (Oo(t))
        return;
      ct("Failed to serialize the state. Check the console for more details.", "error"), console.error(t);
    }
}
async function cu(e) {
  if (!Uo())
    try {
      Vo(e, JSON.parse(await navigator.clipboard.readText())), ct("Global state pasted from clipboard.");
    } catch (t) {
      if (Oo(t))
        return;
      ct("Failed to deserialize the state from clipboard. Check the console for more details.", "error"), console.error(t);
    }
}
async function uu(e) {
  try {
    Po(new Blob([JSON.stringify(e.state.value)], {
      type: "text/plain;charset=utf-8"
    }), "pinia-state.json");
  } catch (t) {
    ct("Failed to export the state as JSON. Check the console for more details.", "error"), console.error(t);
  }
}
let tr;
function fu() {
  tr || (tr = document.createElement("input"), tr.type = "file", tr.accept = ".json");
  function e() {
    return new Promise((t, a) => {
      tr.onchange = async () => {
        const r = tr.files;
        if (!r)
          return t(null);
        const n = r.item(0);
        return t(n ? { text: await n.text(), file: n } : null);
      }, tr.oncancel = () => t(null), tr.onerror = a, tr.click();
    });
  }
  return e;
}
async function du(e) {
  try {
    const a = await fu()();
    if (!a)
      return;
    const { text: r, file: n } = a;
    Vo(e, JSON.parse(r)), ct(`Global state imported from "${n.name}".`);
  } catch (t) {
    ct("Failed to import the state from JSON. Check the console for more details.", "error"), console.error(t);
  }
}
function Vo(e, t) {
  for (const a in t) {
    const r = e.state.value[a];
    r ? Object.assign(r, t[a]) : e.state.value[a] = t[a];
  }
}
function Vt(e) {
  return {
    _custom: {
      display: e
    }
  };
}
const Mo = "🍍 Pinia (root)", Li = "_root";
function pu(e) {
  return o0(e) ? {
    id: Li,
    label: Mo
  } : {
    id: e.$id,
    label: e.$id
  };
}
function hu(e) {
  if (o0(e)) {
    const a = Array.from(e._s.keys()), r = e._s;
    return {
      state: a.map((i) => ({
        editable: !0,
        key: i,
        value: e.state.value[i]
      })),
      getters: a.filter((i) => r.get(i)._getters).map((i) => {
        const s = r.get(i);
        return {
          editable: !1,
          key: i,
          value: s._getters.reduce((o, u) => (o[u] = s[u], o), {})
        };
      })
    };
  }
  const t = {
    state: Object.keys(e.$state).map((a) => ({
      editable: !0,
      key: a,
      value: e.$state[a]
    }))
  };
  return e._getters && e._getters.length && (t.getters = e._getters.map((a) => ({
    editable: !1,
    key: a,
    value: e[a]
  }))), e._customProperties.size && (t.customProperties = Array.from(e._customProperties).map((a) => ({
    editable: !0,
    key: a,
    value: e[a]
  }))), t;
}
function vu(e) {
  return e ? Array.isArray(e) ? e.reduce((t, a) => (t.keys.push(a.key), t.operations.push(a.type), t.oldValue[a.key] = a.oldValue, t.newValue[a.key] = a.newValue, t), {
    oldValue: {},
    keys: [],
    operations: [],
    newValue: {}
  }) : {
    operation: Vt(e.type),
    key: Vt(e.key),
    oldValue: e.oldValue,
    newValue: e.newValue
  } : {};
}
function xu(e) {
  switch (e) {
    case Zt.direct:
      return "mutation";
    case Zt.patchFunction:
      return "$patch";
    case Zt.patchObject:
      return "$patch";
    default:
      return "unknown";
  }
}
let Xr = !0;
const en = [], Br = "pinia:mutations", gt = "pinia", { assign: gu } = Object, sn = (e) => "🍍 " + e;
function Ko(e, t) {
  ko({
    id: "dev.esm.pinia",
    label: "Pinia 🍍",
    logo: "https://pinia.vuejs.org/logo.svg",
    packageName: "pinia",
    homepage: "https://pinia.vuejs.org",
    componentStateTypes: en,
    app: e
  }, (a) => {
    typeof a.now != "function" && ct("You seem to be using an outdated version of Vue Devtools. Are you still using the Beta release instead of the stable one? You can find the links at https://devtools.vuejs.org/guide/installation.html."), a.addTimelineLayer({
      id: Br,
      label: "Pinia 🍍",
      color: 15064968
    }), a.addInspector({
      id: gt,
      label: "Pinia 🍍",
      icon: "storage",
      treeFilterPlaceholder: "Search stores",
      actions: [
        {
          icon: "content_copy",
          action: () => {
            lu(t);
          },
          tooltip: "Serialize and copy the state"
        },
        {
          icon: "content_paste",
          action: async () => {
            await cu(t), a.sendInspectorTree(gt), a.sendInspectorState(gt);
          },
          tooltip: "Replace the state with the content of your clipboard"
        },
        {
          icon: "save",
          action: () => {
            uu(t);
          },
          tooltip: "Save the state as a JSON file"
        },
        {
          icon: "folder_open",
          action: async () => {
            await du(t), a.sendInspectorTree(gt), a.sendInspectorState(gt);
          },
          tooltip: "Import the state from a JSON file"
        }
      ],
      nodeActions: [
        {
          icon: "restore",
          tooltip: 'Reset the state (with "$reset")',
          action: (r) => {
            const n = t._s.get(r);
            n ? typeof n.$reset != "function" ? ct(`Cannot reset "${r}" store because it doesn't have a "$reset" method implemented.`, "warn") : (n.$reset(), ct(`Store "${r}" reset.`)) : ct(`Cannot reset "${r}" store because it wasn't found.`, "warn");
          }
        }
      ]
    }), a.on.inspectComponent((r, n) => {
      const i = r.componentInstance && r.componentInstance.proxy;
      if (i && i._pStores) {
        const s = r.componentInstance.proxy._pStores;
        Object.values(s).forEach((o) => {
          r.instanceData.state.push({
            type: sn(o.$id),
            key: "state",
            editable: !0,
            value: o._isOptionsAPI ? {
              _custom: {
                value: Pa(o.$state),
                actions: [
                  {
                    icon: "restore",
                    tooltip: "Reset the state of this store",
                    action: () => o.$reset()
                  }
                ]
              }
            } : (
              // NOTE: workaround to unwrap transferred refs
              Object.keys(o.$state).reduce((u, l) => (u[l] = o.$state[l], u), {})
            )
          }), o._getters && o._getters.length && r.instanceData.state.push({
            type: sn(o.$id),
            key: "getters",
            editable: !1,
            value: o._getters.reduce((u, l) => {
              try {
                u[l] = o[l];
              } catch (c) {
                u[l] = c;
              }
              return u;
            }, {})
          });
        });
      }
    }), a.on.getInspectorTree((r) => {
      if (r.app === e && r.inspectorId === gt) {
        let n = [t];
        n = n.concat(Array.from(t._s.values())), r.rootNodes = (r.filter ? n.filter((i) => "$id" in i ? i.$id.toLowerCase().includes(r.filter.toLowerCase()) : Mo.toLowerCase().includes(r.filter.toLowerCase())) : n).map(pu);
      }
    }), a.on.getInspectorState((r) => {
      if (r.app === e && r.inspectorId === gt) {
        const n = r.nodeId === Li ? t : t._s.get(r.nodeId);
        if (!n)
          return;
        n && (r.state = hu(n));
      }
    }), a.on.editInspectorState((r, n) => {
      if (r.app === e && r.inspectorId === gt) {
        const i = r.nodeId === Li ? t : t._s.get(r.nodeId);
        if (!i)
          return ct(`store "${r.nodeId}" not found`, "error");
        const { path: s } = r;
        o0(i) ? s.unshift("state") : (s.length !== 1 || !i._customProperties.has(s[0]) || s[0] in i.$state) && s.unshift("$state"), Xr = !1, r.set(i, s, r.state.value), Xr = !0;
      }
    }), a.on.editComponentState((r) => {
      if (r.type.startsWith("🍍")) {
        const n = r.type.replace(/^🍍\s*/, ""), i = t._s.get(n);
        if (!i)
          return ct(`store "${n}" not found`, "error");
        const { path: s } = r;
        if (s[0] !== "state")
          return ct(`Invalid path for store "${n}":
${s}
Only state can be modified.`);
        s[0] = "$state", Xr = !1, r.set(i, s, r.state.value), Xr = !0;
      }
    });
  });
}
function yu(e, t) {
  en.includes(sn(t.$id)) || en.push(sn(t.$id)), ko({
    id: "dev.esm.pinia",
    label: "Pinia 🍍",
    logo: "https://pinia.vuejs.org/logo.svg",
    packageName: "pinia",
    homepage: "https://pinia.vuejs.org",
    componentStateTypes: en,
    app: e,
    settings: {
      logStoreChanges: {
        label: "Notify about new/deleted stores",
        type: "boolean",
        defaultValue: !0
      }
      // useEmojis: {
      //   label: 'Use emojis in messages ⚡️',
      //   type: 'boolean',
      //   defaultValue: true,
      // },
    }
  }, (a) => {
    const r = typeof a.now == "function" ? a.now.bind(a) : Date.now;
    t.$onAction(({ after: s, onError: o, name: u, args: l }) => {
      const c = Ho++;
      a.addTimelineEvent({
        layerId: Br,
        event: {
          time: r(),
          title: "🛫 " + u,
          subtitle: "start",
          data: {
            store: Vt(t.$id),
            action: Vt(u),
            args: l
          },
          groupId: c
        }
      }), s((f) => {
        pr = void 0, a.addTimelineEvent({
          layerId: Br,
          event: {
            time: r(),
            title: "🛬 " + u,
            subtitle: "end",
            data: {
              store: Vt(t.$id),
              action: Vt(u),
              args: l,
              result: f
            },
            groupId: c
          }
        });
      }), o((f) => {
        pr = void 0, a.addTimelineEvent({
          layerId: Br,
          event: {
            time: r(),
            logType: "error",
            title: "💥 " + u,
            subtitle: "end",
            data: {
              store: Vt(t.$id),
              action: Vt(u),
              args: l,
              error: f
            },
            groupId: c
          }
        });
      });
    }, !0), t._customProperties.forEach((s) => {
      nt(() => ye(t[s]), (o, u) => {
        a.notifyComponentUpdate(), a.sendInspectorState(gt), Xr && a.addTimelineEvent({
          layerId: Br,
          event: {
            time: r(),
            title: "Change",
            subtitle: s,
            data: {
              newValue: o,
              oldValue: u
            },
            groupId: pr
          }
        });
      }, { deep: !0 });
    }), t.$subscribe(({ events: s, type: o }, u) => {
      if (a.notifyComponentUpdate(), a.sendInspectorState(gt), !Xr)
        return;
      const l = {
        time: r(),
        title: xu(o),
        data: gu({ store: Vt(t.$id) }, vu(s)),
        groupId: pr
      };
      o === Zt.patchFunction ? l.subtitle = "⤵️" : o === Zt.patchObject ? l.subtitle = "🧩" : s && !Array.isArray(s) && (l.subtitle = s.type), s && (l.data["rawEvent(s)"] = {
        _custom: {
          display: "DebuggerEvent",
          type: "object",
          tooltip: "raw DebuggerEvent[]",
          value: s
        }
      }), a.addTimelineEvent({
        layerId: Br,
        event: l
      });
    }, { detached: !0, flush: "sync" });
    const n = t._hotUpdate;
    t._hotUpdate = Nr((s) => {
      n(s), a.addTimelineEvent({
        layerId: Br,
        event: {
          time: r(),
          title: "🔥 " + t.$id,
          subtitle: "HMR update",
          data: {
            store: Vt(t.$id),
            info: Vt("HMR update")
          }
        }
      }), a.notifyComponentUpdate(), a.sendInspectorTree(gt), a.sendInspectorState(gt);
    });
    const { $dispose: i } = t;
    t.$dispose = () => {
      i(), a.notifyComponentUpdate(), a.sendInspectorTree(gt), a.sendInspectorState(gt), a.getSettings().logStoreChanges && ct(`Disposed "${t.$id}" store 🗑`);
    }, a.notifyComponentUpdate(), a.sendInspectorTree(gt), a.sendInspectorState(gt), a.getSettings().logStoreChanges && ct(`"${t.$id}" store installed 🆕`);
  });
}
let Ho = 0, pr;
function q0(e, t, a) {
  const r = t.reduce((n, i) => (n[i] = Pa(e)[i], n), {});
  for (const n in r)
    e[n] = function() {
      const i = Ho, s = a ? new Proxy(e, {
        get(...u) {
          return pr = i, Reflect.get(...u);
        },
        set(...u) {
          return pr = i, Reflect.set(...u);
        }
      }) : e;
      pr = i;
      const o = r[n].apply(s, arguments);
      return pr = void 0, o;
    };
}
function mu({ app: e, store: t, options: a }) {
  if (t.$id.startsWith("__hot:"))
    return;
  t._isOptionsAPI = !!a.state, q0(t, Object.keys(a.actions), t._isOptionsAPI);
  const r = t._hotUpdate;
  Pa(t)._hotUpdate = function(n) {
    r.apply(this, arguments), q0(t, Object.keys(n._hmrPayload.actions), !!t._isOptionsAPI);
  }, yu(
    e,
    // FIXME: is there a way to allow the assignment from Store<Id, S, G, A> to StoreGeneric?
    t
  );
}
function Cu() {
  const e = To(!0), t = e.run(() => se({}));
  let a = [], r = [];
  const n = Nr({
    install(i) {
      na(n), n._a = i, i.provide(En, n), i.config.globalProperties.$pinia = n, ta && Ko(i, n), r.forEach((s) => a.push(s)), r = [];
    },
    use(i) {
      return !this._a && !No ? r.push(i) : a.push(i), this;
    },
    _p: a,
    // it's actually undefined here
    // @ts-expect-error
    _a: null,
    _e: e,
    _s: /* @__PURE__ */ new Map(),
    state: t
  });
  return ta && typeof Proxy < "u" && n.use(mu), n;
}
const Eu = (e) => typeof e == "function" && typeof e.$id == "string";
function zo(e, t) {
  for (const a in t) {
    const r = t[a];
    if (!(a in e))
      continue;
    const n = e[a];
    Pr(n) && Pr(r) && !Jt(r) && !yn(r) ? e[a] = zo(n, r) : e[a] = r;
  }
  return e;
}
function hy(e, t) {
  return process.env.NODE_ENV === "production" ? () => {
  } : (a) => {
    const r = t.data.pinia || e._pinia;
    if (r) {
      t.data.pinia = r;
      for (const n in a) {
        const i = a[n];
        if (Eu(i) && r._s.has(i.$id)) {
          const s = i.$id;
          if (s !== e.$id)
            return console.warn(`The id of the store changed from "${e.$id}" to "${s}". Reloading.`), t.invalidate();
          const o = r._s.get(s);
          if (!o) {
            console.log("[Pinia]: skipping hmr because store doesn't exist yet");
            return;
          }
          i(r, o);
        }
      }
    }
  };
}
const $o = () => {
};
function j0(e, t, a, r = $o) {
  e.push(t);
  const n = () => {
    const i = e.indexOf(t);
    i > -1 && (e.splice(i, 1), r());
  };
  return !a && vc() && xc(n), n;
}
function Gr(e, ...t) {
  e.slice().forEach((a) => {
    a(...t);
  });
}
const bu = (e) => e();
function Pi(e, t) {
  e instanceof Map && t instanceof Map && t.forEach((a, r) => e.set(r, a)), e instanceof Set && t instanceof Set && t.forEach(e.add, e);
  for (const a in t) {
    if (!t.hasOwnProperty(a))
      continue;
    const r = t[a], n = e[a];
    Pr(n) && Pr(r) && e.hasOwnProperty(a) && !Jt(r) && !yn(r) ? e[a] = Pi(n, r) : e[a] = r;
  }
  return e;
}
const qo = process.env.NODE_ENV !== "production" ? Symbol("pinia:skipHydration") : (
  /* istanbul ignore next */
  Symbol()
);
function vy(e) {
  return Object.defineProperty(e, qo, {});
}
function Su(e) {
  return !Pr(e) || !e.hasOwnProperty(qo);
}
const { assign: Nt } = Object;
function G0(e) {
  return !!(Jt(e) && e.effect);
}
function W0(e, t, a, r) {
  const { state: n, actions: i, getters: s } = t, o = a.state.value[e];
  let u;
  function l() {
    !o && (process.env.NODE_ENV === "production" || !r) && (a.state.value[e] = n ? n() : {});
    const c = process.env.NODE_ENV !== "production" && r ? (
      // use ref() to unwrap refs inside state TODO: check if this is still necessary
      Ni(se(n ? n() : {}).value)
    ) : Ni(a.state.value[e]);
    return Nt(c, i, Object.keys(s || {}).reduce((f, d) => (process.env.NODE_ENV !== "production" && d in c && console.warn(`[🍍]: A getter cannot have the same name as another state property. Rename one of them. Found with "${d}" in store "${e}".`), f[d] = Nr(He(() => {
      na(a);
      const v = a._s.get(e);
      return s[d].call(v, v);
    })), f), {}));
  }
  return u = Ui(e, l, t, a, r, !0), u;
}
function Ui(e, t, a = {}, r, n, i) {
  let s;
  const o = Nt({ actions: {} }, a);
  if (process.env.NODE_ENV !== "production" && !r._e.active)
    throw new Error("Pinia destroyed");
  const u = {
    deep: !0
    // flush: 'post',
  };
  process.env.NODE_ENV !== "production" && !No && (u.onTrigger = (I) => {
    l ? v = I : l == !1 && !T._hotUpdating && (Array.isArray(v) ? v.push(I) : console.error("🍍 debuggerEvents should be an array. This is most likely an internal Pinia bug."));
  });
  let l, c, f = [], d = [], v;
  const p = r.state.value[e];
  !i && !p && (process.env.NODE_ENV === "production" || !n) && (r.state.value[e] = {});
  const h = se({});
  let x;
  function y(I) {
    let B;
    l = c = !1, process.env.NODE_ENV !== "production" && (v = []), typeof I == "function" ? (I(r.state.value[e]), B = {
      type: Zt.patchFunction,
      storeId: e,
      events: v
    }) : (Pi(r.state.value[e], I), B = {
      type: Zt.patchObject,
      payload: I,
      storeId: e,
      events: v
    });
    const R = x = Symbol();
    Lr().then(() => {
      x === R && (l = !0);
    }), c = !0, Gr(f, B, r.state.value[e]);
  }
  const g = i ? function() {
    const { state: B } = a, R = B ? B() : {};
    this.$patch((F) => {
      Nt(F, R);
    });
  } : (
    /* istanbul ignore next */
    process.env.NODE_ENV !== "production" ? () => {
      throw new Error(`🍍: Store "${e}" is built using the setup syntax and does not implement $reset().`);
    } : $o
  );
  function m() {
    s.stop(), f = [], d = [], r._s.delete(e);
  }
  function C(I, B) {
    return function() {
      na(r);
      const R = Array.from(arguments), F = [], U = [];
      function M(fe) {
        F.push(fe);
      }
      function ee(fe) {
        U.push(fe);
      }
      Gr(d, {
        args: R,
        name: I,
        store: T,
        after: M,
        onError: ee
      });
      let q;
      try {
        q = B.apply(this && this.$id === e ? this : T, R);
      } catch (fe) {
        throw Gr(U, fe), fe;
      }
      return q instanceof Promise ? q.then((fe) => (Gr(F, fe), fe)).catch((fe) => (Gr(U, fe), Promise.reject(fe))) : (Gr(F, q), q);
    };
  }
  const S = /* @__PURE__ */ Nr({
    actions: {},
    getters: {},
    state: [],
    hotState: h
  }), _ = {
    _p: r,
    // _s: scope,
    $id: e,
    $onAction: j0.bind(null, d),
    $patch: y,
    $reset: g,
    $subscribe(I, B = {}) {
      const R = j0(f, I, B.detached, () => F()), F = s.run(() => nt(() => r.state.value[e], (U) => {
        (B.flush === "sync" ? c : l) && I({
          storeId: e,
          type: Zt.direct,
          events: v
        }, U);
      }, Nt({}, u, B)));
      return R;
    },
    $dispose: m
  }, T = or(process.env.NODE_ENV !== "production" || ta ? Nt(
    {
      _hmrPayload: S,
      _customProperties: Nr(/* @__PURE__ */ new Set())
      // devtools custom properties
    },
    _
    // must be added later
    // setupStore
  ) : _);
  r._s.set(e, T);
  const P = (r._a && r._a.runWithContext || bu)(() => r._e.run(() => (s = To()).run(t)));
  for (const I in P) {
    const B = P[I];
    if (Jt(B) && !G0(B) || yn(B))
      process.env.NODE_ENV !== "production" && n ? za(h.value, I, Xa(P, I)) : i || (p && Su(B) && (Jt(B) ? B.value = p[I] : Pi(B, p[I])), r.state.value[e][I] = B), process.env.NODE_ENV !== "production" && S.state.push(I);
    else if (typeof B == "function") {
      const R = process.env.NODE_ENV !== "production" && n ? B : C(I, B);
      P[I] = R, process.env.NODE_ENV !== "production" && (S.actions[I] = B), o.actions[I] = B;
    } else process.env.NODE_ENV !== "production" && G0(B) && (S.getters[I] = i ? (
      // @ts-expect-error
      a.getters[I]
    ) : B, Oa && (P._getters || // @ts-expect-error: same
    (P._getters = Nr([]))).push(I));
  }
  if (Nt(T, P), Nt(Pa(T), P), Object.defineProperty(T, "$state", {
    get: () => process.env.NODE_ENV !== "production" && n ? h.value : r.state.value[e],
    set: (I) => {
      if (process.env.NODE_ENV !== "production" && n)
        throw new Error("cannot set hotState");
      y((B) => {
        Nt(B, I);
      });
    }
  }), process.env.NODE_ENV !== "production" && (T._hotUpdate = Nr((I) => {
    T._hotUpdating = !0, I._hmrPayload.state.forEach((B) => {
      if (B in T.$state) {
        const R = I.$state[B], F = T.$state[B];
        typeof R == "object" && Pr(R) && Pr(F) ? zo(R, F) : I.$state[B] = F;
      }
      za(T, B, Xa(I.$state, B));
    }), Object.keys(T.$state).forEach((B) => {
      B in I.$state || Vn(T, B);
    }), l = !1, c = !1, r.state.value[e] = Xa(I._hmrPayload, "hotState"), c = !0, Lr().then(() => {
      l = !0;
    });
    for (const B in I._hmrPayload.actions) {
      const R = I[B];
      za(T, B, C(B, R));
    }
    for (const B in I._hmrPayload.getters) {
      const R = I._hmrPayload.getters[B], F = i ? (
        // special handling of options api
        He(() => (na(r), R.call(T, T)))
      ) : R;
      za(T, B, F);
    }
    Object.keys(T._hmrPayload.getters).forEach((B) => {
      B in I._hmrPayload.getters || Vn(T, B);
    }), Object.keys(T._hmrPayload.actions).forEach((B) => {
      B in I._hmrPayload.actions || Vn(T, B);
    }), T._hmrPayload = I._hmrPayload, T._getters = I._getters, T._hotUpdating = !1;
  })), ta) {
    const I = {
      writable: !0,
      configurable: !0,
      // avoid warning on devtools trying to display this property
      enumerable: !1
    };
    ["_p", "_hmrPayload", "_getters", "_customProperties"].forEach((B) => {
      Object.defineProperty(T, B, Nt({ value: T[B] }, I));
    });
  }
  return r._p.forEach((I) => {
    if (ta) {
      const B = s.run(() => I({
        store: T,
        app: r._a,
        pinia: r,
        options: o
      }));
      Object.keys(B || {}).forEach((R) => T._customProperties.add(R)), Nt(T, B);
    } else
      Nt(T, s.run(() => I({
        store: T,
        app: r._a,
        pinia: r,
        options: o
      })));
  }), process.env.NODE_ENV !== "production" && T.$state && typeof T.$state == "object" && typeof T.$state.constructor == "function" && !T.$state.constructor.toString().includes("[native code]") && console.warn(`[🍍]: The "state" must be a plain object. It cannot be
	state: () => new MyClass()
Found in store "${T.$id}".`), p && i && a.hydrate && a.hydrate(T.$state, p), l = !0, c = !0, T;
}
function ca(e, t, a) {
  let r, n;
  const i = typeof t == "function";
  if (typeof e == "string")
    r = e, n = i ? a : t;
  else if (n = e, r = e.id, process.env.NODE_ENV !== "production" && typeof r != "string")
    throw new Error('[🍍]: "defineStore()" must be passed a store id as its first argument.');
  function s(o, u) {
    const l = Bo();
    if (o = // in test mode, ignore the argument provided as we can always retrieve a
    // pinia instance with getActivePinia()
    (process.env.NODE_ENV === "test" && Qr && Qr._testing ? null : o) || (l ? dr(En, null) : null), o && na(o), process.env.NODE_ENV !== "production" && !Qr)
      throw new Error(`[🍍]: "getActivePinia()" was called but there was no active Pinia. Are you trying to use a store before calling "app.use(pinia)"?
See https://pinia.vuejs.org/core-concepts/outside-component-usage.html for help.
This will fail in production.`);
    o = Qr, o._s.has(r) || (i ? Ui(r, t, n, o) : W0(r, n, o), process.env.NODE_ENV !== "production" && (s._pinia = o));
    const c = o._s.get(r);
    if (process.env.NODE_ENV !== "production" && u) {
      const f = "__hot:" + r, d = i ? Ui(f, t, n, o, !0) : W0(f, Nt({}, n), o, !0);
      u._hotUpdate(d), delete o.state.value[f], o._s.delete(f);
    }
    if (process.env.NODE_ENV !== "production" && Oa) {
      const f = hc();
      if (f && f.proxy && // avoid adding stores that are just built for hot module replacement
      !u) {
        const d = f.proxy, v = "_pStores" in d ? d._pStores : d._pStores = {};
        v[r] = c;
      }
    }
    return c;
  }
  return s.$id = r, s;
}
let jo = "Store";
function xy(e) {
  jo = e;
}
function gy(...e) {
  return process.env.NODE_ENV !== "production" && Array.isArray(e[0]) && (console.warn(`[🍍]: Directly pass all stores to "mapStores()" without putting them in an array:
Replace
	mapStores([useAuthStore, useCartStore])
with
	mapStores(useAuthStore, useCartStore)
This will fail in production if not fixed.`), e = e[0]), e.reduce((t, a) => (t[a.$id + jo] = function() {
    return a(this.$pinia);
  }, t), {});
}
function Au(e, t) {
  return Array.isArray(t) ? t.reduce((a, r) => (a[r] = function() {
    return e(this.$pinia)[r];
  }, a), {}) : Object.keys(t).reduce((a, r) => (a[r] = function() {
    const n = e(this.$pinia), i = t[r];
    return typeof i == "function" ? i.call(this, n) : n[i];
  }, a), {});
}
const yy = Au;
function my(e, t) {
  return Array.isArray(t) ? t.reduce((a, r) => (a[r] = function(...n) {
    return e(this.$pinia)[r](...n);
  }, a), {}) : Object.keys(t).reduce((a, r) => (a[r] = function(...n) {
    return e(this.$pinia)[t[r]](...n);
  }, a), {});
}
function Cy(e, t) {
  return Array.isArray(t) ? t.reduce((a, r) => (a[r] = {
    get() {
      return e(this.$pinia)[r];
    },
    set(n) {
      return e(this.$pinia)[r] = n;
    }
  }, a), {}) : Object.keys(t).reduce((a, r) => (a[r] = {
    get() {
      return e(this.$pinia)[t[r]];
    },
    set(n) {
      return e(this.$pinia)[t[r]] = n;
    }
  }, a), {});
}
function ua(e) {
  {
    e = Pa(e);
    const t = {};
    for (const a in e) {
      const r = e[a];
      (Jt(r) || yn(r)) && (t[a] = // ---
      Xa(e, a));
    }
    return t;
  }
}
const Ey = function(e) {
  e.mixin({
    beforeCreate() {
      const t = this.$options;
      if (t.pinia) {
        const a = t.pinia;
        if (!this._provided) {
          const r = {};
          Object.defineProperty(this, "_provided", {
            get: () => r,
            set: (n) => Object.assign(r, n)
          });
        }
        this._provided[En] = a, this.$pinia || (this.$pinia = a), a._a = this, Oa && na(a), ta && Ko(a._a, a);
      } else !this.$pinia && t.parent && t.parent.$pinia && (this.$pinia = t.parent.$pinia);
    },
    destroyed() {
      delete this._pStores;
    }
  });
};
function _u(e) {
  return typeof e == "object" && e !== null;
}
function Y0(e, t) {
  return e = _u(e) ? e : /* @__PURE__ */ Object.create(null), new Proxy(e, {
    get(a, r, n) {
      return r === "key" ? Reflect.get(a, r, n) : Reflect.get(a, r, n) || Reflect.get(t, r, n);
    }
  });
}
function Bu(e, t) {
  return t.reduce((a, r) => a == null ? void 0 : a[r], e);
}
function Tu(e, t, a) {
  return t.slice(0, -1).reduce((r, n) => /^(__proto__)$/.test(n) ? {} : r[n] = r[n] || {}, e)[t[t.length - 1]] = a, e;
}
function Iu(e, t) {
  return t.reduce((a, r) => {
    const n = r.split(".");
    return Tu(a, n, Bu(e, n));
  }, {});
}
function wu(e, t) {
  return (a) => {
    var r;
    try {
      const {
        storage: n = localStorage,
        beforeRestore: i = void 0,
        afterRestore: s = void 0,
        serializer: o = {
          serialize: JSON.stringify,
          deserialize: JSON.parse
        },
        key: u = t.$id,
        paths: l = null,
        debug: c = !1
      } = a;
      return {
        storage: n,
        beforeRestore: i,
        afterRestore: s,
        serializer: o,
        key: ((r = e.key) != null ? r : (f) => f)(typeof u == "string" ? u : u(t.$id)),
        paths: l,
        debug: c
      };
    } catch (n) {
      return a.debug && console.error("[pinia-plugin-persistedstate]", n), null;
    }
  };
}
function Q0(e, { storage: t, serializer: a, key: r, debug: n }) {
  try {
    const i = t == null ? void 0 : t.getItem(r);
    i && e.$patch(a == null ? void 0 : a.deserialize(i));
  } catch (i) {
    n && console.error("[pinia-plugin-persistedstate]", i);
  }
}
function X0(e, { storage: t, serializer: a, key: r, paths: n, debug: i }) {
  try {
    const s = Array.isArray(n) ? Iu(e, n) : e;
    t.setItem(r, a.serialize(s));
  } catch (s) {
    i && console.error("[pinia-plugin-persistedstate]", s);
  }
}
function Du(e = {}) {
  return (t) => {
    const { auto: a = !1 } = e, {
      options: { persist: r = a },
      store: n,
      pinia: i
    } = t;
    if (!r)
      return;
    if (!(n.$id in i.state.value)) {
      const o = i._s.get(n.$id.replace("__hot:", ""));
      o && Promise.resolve().then(() => o.$persist());
      return;
    }
    const s = (Array.isArray(r) ? r.map((o) => Y0(o, e)) : [Y0(r, e)]).map(wu(e, n)).filter(Boolean);
    n.$persist = () => {
      s.forEach((o) => {
        X0(n.$state, o);
      });
    }, n.$hydrate = ({ runHooks: o = !0 } = {}) => {
      s.forEach((u) => {
        const { beforeRestore: l, afterRestore: c } = u;
        o && (l == null || l(t)), Q0(n, u), o && (c == null || c(t));
      });
    }, s.forEach((o) => {
      const { beforeRestore: u, afterRestore: l } = o;
      u == null || u(t), Q0(n, o), l == null || l(t), n.$subscribe(
        (c, f) => {
          X0(f, o);
        },
        {
          detached: !0
        }
      );
    });
  };
}
var Nu = Du();
const Kt = Cu();
Kt.use(Nu);
const ra = {
  // 查看 v2.4.3版本更新日志
  setKey(e) {
    return `${__NEXT_NAME__}:${e}`;
  },
  // 设置永久缓存
  set(e, t) {
    window.localStorage.setItem(ra.setKey(e), JSON.stringify(t));
  },
  // 获取永久缓存
  get(e) {
    let t = window.localStorage.getItem(ra.setKey(e));
    return JSON.parse(t);
  },
  // 移除永久缓存
  remove(e) {
    window.localStorage.removeItem(ra.setKey(e));
  },
  // 移除全部永久缓存
  clear() {
    window.localStorage.clear();
  }
}, Z0 = {
  // 设置临时缓存
  set(e, t, a) {
    if (e === "token") return ba.set(e, t, { expires: a });
    window.sessionStorage.setItem(ra.setKey(e), JSON.stringify(t));
  },
  // 获取临时缓存
  get(e) {
    if (e === "token") return ba.get(e);
    let t = window.sessionStorage.getItem(ra.setKey(e));
    return JSON.parse(t);
  },
  // 移除临时缓存
  remove(e) {
    if (e === "token") return ba.remove(e);
    window.sessionStorage.removeItem(ra.setKey(e));
  },
  // 移除全部临时缓存
  clear() {
    ba.remove("token"), window.sessionStorage.clear();
  }
}, Se = wo.create({
  // @ts-ignore
  baseURL: "/api",
  timeout: 5e4,
  headers: { "Content-Type": "application/json" },
  paramsSerializer: {
    serialize(e) {
      return ki.stringify(e, { allowDots: !0 });
    }
  }
});
Se.interceptors.request.use(
  (e) => (localStorage.getItem("Authorization") && (e.headers.Authorization = `${localStorage.getItem(
    "Authorization"
  )}`), localStorage.getItem("tenant") && localStorage.getItem("tenant") != "undefined" && (e.headers.TenantId = JSON.parse(
    localStorage.getItem("tenant")
  ).id), e),
  (e) => Promise.reject(e)
);
Se.interceptors.response.use(
  (e) => {
    const t = e.data;
    return t.code && t.code !== 0 ? ((t.code === 401 || t.code === 4001) && (Z0.clear(), localStorage.removeItem("Authorization"), localStorage.removeItem("tenant"), Ri.alert("你已被登出，请重新登录", "提示", {}).then(() => {
    }).catch(() => {
    })), ze.error(t.errorMessage), Promise.reject(Se.interceptors.response)) : t;
  },
  (e) => {
    const t = e.response;
    return t.data.status === 500 && t.data.message === "access token has expired,please reacquire token" ? (Z0.clear(), localStorage.removeItem("Authorization"), localStorage.removeItem("tenant"), Ri.alert("你已被登出，请重新登录", "提示", {}).then(() => {
    }).catch(() => {
    })) : e.message.indexOf("timeout") != -1 ? ze.error("网络超时") : e.message == "Network Error" ? ze.error("网络连接错误") : t.data.message.includes("用户名或密码错误") ? ze.error("用户名或密码错误") : e.response.data ? ze.error(e.response.statusText) : ze.error("接口路径找不到"), Promise.reject(e);
  }
);
const _e = "/infrastructure";
function Tr() {
  return {
    findAll: (e, t) => Se({
      url: `${_e}/sys/users`,
      method: "post",
      data: e,
      params: t
    }),
    add: (e) => Se({
      url: `${_e}/sys/user`,
      method: "post",
      data: e
    }),
    update: (e) => Se({
      url: `${_e}/sys/user`,
      method: "put",
      data: e
    }),
    delete: (e) => Se({
      url: `${_e}/sys/user/${e}`,
      method: "delete"
    }),
    findById: (e) => Se({
      url: `${_e}/sys/user/${e}`,
      method: "get"
    }),
    findByOrg: (e, t) => Se({
      url: `${_e}/sys/users/org`,
      method: "POST",
      data: e,
      params: t
    }),
    findByIds: (e) => Se({
      url: `${_e}/sys/users/ids?hadDetail=false`,
      method: "post",
      data: e
    })
  };
}
function J0() {
  return {
    findDicByCode: (e) => Se({
      url: `${_e}/sys/dictionary/code/${e}`,
      method: "get"
    }),
    queryMenu: (e) => Se({
      url: `${_e}/sys/authoritys/current`,
      method: "post",
      data: e
    }),
    findDictByIds: (e) => Se({
      url: `${_e}/sys/dictionarys/ids`,
      method: "post",
      data: e
    }),
    findUserByIds: (e) => Se({
      url: `${_e}/sys/users/ids?hadDetail=false`,
      method: "post",
      data: e
    }),
    findUserById: (e) => Se({
      url: `${_e}/sys/user/${e}`,
      method: "get"
    }),
    findRegionsByAll: (e) => Se({
      url: `${_e}/sys/regions`,
      method: "post",
      data: e
    }),
    getFiles: (e) => Se({
      url: `${_e}/sys/files?size=10000`,
      method: "post",
      data: e
    }),
    getFilesPage: (e, t) => Se({
      url: `${_e}/sys/files`,
      method: "post",
      data: e,
      params: t
    }),
    addFiles: (e) => Se({
      url: `${_e}/sys/file/batch`,
      method: "post",
      data: e
    }),
    getAccount: () => Se({
      url: `${_e}/sys/user/current`,
      method: "get"
    }),
    getConfigures: (e) => Se({
      url: `${_e}/sys/file/configures?size=1000`,
      method: "post",
      data: e
    })
  };
}
function Go() {
  return {
    findRegionsByAll: (e) => Se({
      url: `${_e}/sys/regions`,
      method: "post",
      data: e
    }),
    findRegionsByIds: (e) => Se({
      url: `${_e}/sys/regions/ids`,
      method: "post",
      data: e
    })
  };
}
function Ba() {
  return {
    findAll: (e, t) => Se({
      url: `${_e}/sys/tenants`,
      method: "post",
      data: e,
      params: t
    }),
    add: (e) => Se({
      url: `${_e}/sys/tenant`,
      method: "post",
      data: e
    }),
    update: (e) => Se({
      url: `${_e}/sys/tenant`,
      method: "put",
      data: e
    }),
    delete: (e) => Se({
      url: `${_e}/sys/tenant/${e}`,
      method: "delete"
    }),
    findById: (e) => Se({
      url: `${_e}/sys/tenant/${e}`,
      method: "get"
    }),
    findByIds: (e) => Se({
      url: `${_e}/sys/tenants/ids`,
      method: "post",
      data: e
    })
  };
}
function Wo() {
  return {
    findAll: (e, t) => Se({
      url: `${_e}/sys/menus`,
      method: "post",
      data: e,
      params: t
    }),
    add: (e) => Se({
      url: `${_e}/sys/menu`,
      method: "post",
      data: e
    }),
    update: (e) => Se({
      url: `${_e}/sys/menu`,
      method: "put",
      data: e
    }),
    delete: (e) => Se({
      url: `${_e}/sys/menu/${e}`,
      method: "delete"
    }),
    findById: (e) => Se({
      url: `${_e}/sys/menu/${e}`,
      method: "get"
    })
  };
}
function tn() {
  return {
    findAll: (e, t) => Se({
      url: `${_e}/sys/versions`,
      method: "post",
      data: e,
      params: t
    }),
    add: (e) => Se({
      url: `${_e}/sys/version`,
      method: "post",
      data: e
    }),
    update: (e) => Se({
      url: `${_e}/sys/version`,
      method: "put",
      data: e
    }),
    delete: (e) => Se({
      url: `${_e}/sys/version/${e}`,
      method: "delete"
    }),
    findById: (e) => Se({
      url: `${_e}/sys/version/${e}`,
      method: "get"
    }),
    findByIds: (e) => Se({
      url: `${_e}/sys/versions/ids`,
      method: "post",
      data: e
    })
  };
}
function Ra() {
  return {
    findAll: (e, t) => Se({
      url: `${_e}/sys/orgs`,
      method: "post",
      data: e,
      params: t
    }),
    add: (e) => Se({
      url: `${_e}/sys/org`,
      method: "post",
      data: e
    }),
    update: (e) => Se({
      url: `${_e}/sys/org`,
      method: "put",
      data: e
    }),
    delete: (e) => Se({
      url: `${_e}/sys/org/${e}`,
      method: "delete"
    }),
    findById: (e) => Se({
      url: `${_e}/sys/org/${e}`,
      method: "get"
    }),
    findByIds: (e) => Se({
      url: `${_e}/sys/orgs/ids`,
      method: "post",
      data: e
    })
  };
}
const l0 = (e, t) => ({
  key: e,
  storage: localStorage,
  // storage: sessionStorage,
  paths: t
}), xt = ca({
  id: "commonCache",
  state: () => ({
    users: {},
    enums: {},
    dictionaries: {},
    enumList: {},
    tenants: {},
    menus: {},
    version: {},
    regions: {},
    orgs: {
      orgOptionsCache: []
    },
    commonNames: {},
    requestedEnums: {}
  }),
  actions: {
    // 公共的findByIds
    async findChineseByIds(e, t, a) {
      const { data: r, code: n } = await e(t);
      n || r.forEach((i) => {
        this.commonNames[i.id] = i[a];
      });
    },
    // 通过ID集合找人
    async findUserByIds(e) {
      const { data: t, code: a } = await Tr().findByIds(e);
      a || t.forEach((r) => {
        this.users[r.id] = r.username;
      });
    },
    // 通过ID集合找字典
    async findDictByIds(e) {
      const { data: t, code: a } = await J0().findDictByIds(e);
      a || (console.log(t), t.forEach((r) => {
        this.dictionaries[r.id] = r.username;
      }));
    },
    // 省市区
    async findRegionsByIds(e) {
      const { data: t, code: a } = await Go().findRegionsByIds(e);
      a || t.forEach((r) => {
        this.regions[r.id] = r.name;
      });
    },
    // 字典项
    async findEnumByName(e) {
      var t;
      if (this.requestedEnums[e])
        return console.log(`${e} 已经请求过，直接返回缓存数据`), this.enums[e] || [];
      try {
        this.requestedEnums[e] = !0;
        const { data: a, code: r } = await J0().findDicByCode(e);
        if (r)
          return delete this.requestedEnums[e], console.error(`获取 ${e} 枚举数据失败，code: ${r}`), [];
        {
          const n = (t = a.items) == null ? void 0 : t.map((i) => ({
            ...i,
            id: i.id,
            title: i.itemName
          }));
          return this.enums[e] = n, n;
        }
      } catch (a) {
        return delete this.requestedEnums[e], console.error(`获取 ${e} 枚举数据时发生异常:`, a), [];
      }
    },
    // 重置缓存的方法
    resetEnumCache(e) {
      e ? (delete this.requestedEnums[e], delete this.enums[e]) : (this.requestedEnums = {}, this.enums = {});
    },
    // 菜单
    async findMenuById(e) {
      if (this.menus[e] && this.menus[e].length > 0) return;
      const { data: t, code: a } = await Wo().findById(e);
      a || (this.menus[e] = t.menuName);
    },
    //租户
    async findTenantByIds(e) {
      const { data: t, code: a } = await Ba().findByIds(e);
      a || t.forEach((r) => {
        this.tenants[r.id] = r.tenantName;
      });
    },
    //版本
    async findVersionByIds(e) {
      const { data: t, code: a } = await tn().findByIds(e);
      a || t.forEach((r) => {
        this.version[r.id] = r.versionName;
      });
    },
    // 通过ID集合组织
    async findOrgByIds(e) {
      const { data: t, code: a } = await Ra().findByIds(e);
      a || t.forEach((r) => {
        this.orgs[r.id] = r.orgName;
      });
    },
    async cacheOrgOptions(e, t) {
      this.orgs[e] = t;
    },
    // 获取缓存的组织选项
    getCachedOrgOptions(e) {
      return this.orgs[e] || [];
    },
    // 清除组织选项缓存
    clearOrgOptionsCache(e) {
      e ? delete this.orgs[e] : (Object.keys(this.orgs).forEach((t) => {
        t !== "orgOptionsCache" && delete this.orgs[t];
      }), this.orgs.orgOptionsCache = []);
    }
  },
  persist: l0("commonCache")
}), Ru = /^(\d+,?)+$/, es = ",";
function zt() {
  const e = xt(), { users: t, enums: a, tenants: r, menus: n, version: i, regions: s, orgs: o, dictionaries: u } = ua(e), l = {
    enums: /* @__PURE__ */ new Set(),
    users: /* @__PURE__ */ new Set(),
    orgs: /* @__PURE__ */ new Set(),
    tenants: /* @__PURE__ */ new Set(),
    menus: /* @__PURE__ */ new Set(),
    versions: /* @__PURE__ */ new Set(),
    regions: /* @__PURE__ */ new Set(),
    dictionaries: /* @__PURE__ */ new Set()
  }, c = {
    USER: t.value,
    TENANT: r.value,
    VERSION: i.value,
    REGION: s.value,
    ORG: o.value,
    DICTIONARY: u.value
  }, f = {
    USER: "users",
    TENANT: "tenants",
    VERSION: "versions",
    REGION: "regions",
    ORG: "orgs",
    DICTIONARY: "dictionaries"
  }, d = {
    USER: (S) => xt(Kt).findUserByIds(S),
    TENANT: (S) => xt(Kt).findTenantByIds(S),
    VERSION: (S) => xt(Kt).findVersionByIds(S),
    REGION: (S) => xt(Kt).findRegionsByIds(S),
    ORG: (S) => xt(Kt).findOrgByIds(S),
    DICTIONARY: (S) => xt(Kt).findDictByIds(S)
  }, v = {
    user: (S) => h(S, "USER", !0),
    tenant: (S) => h(S, "TENANT"),
    version: (S) => h(S, "VERSION"),
    org: (S) => h(S, "ORG"),
    dictionary: (S) => h(S, "DICTIONARY"),
    region: (S) => {
      const _ = h(S, "REGION");
      return String(S) !== _ ? _ : "";
    }
  }, p = {
    // 百分比格式化
    percent: (S) => S ? `${S}%` : "-",
    // 日期时间格式化
    date: {
      YMD: (S) => _a(new Date(S), "YYYY-MM-DD"),
      YMDHMS: (S) => _a(new Date(S), "YYYY-MM-DD HH:mm:ss"),
      HMS: (S) => _a(new Date(S * 1e3), "HH:mm:ss")
    },
    // 小数格式化
    number: {
      scale: (S = "0", _ = 4) => Number.parseFloat(S).toFixed(_),
      scale2: (S = "0") => Number.parseFloat(S).toFixed(2)
    }
  }, h = (S, _, T = !1) => {
    const D = String(S);
    if (!D || D === "undefined" || !Ru.test(D))
      return "";
    const P = c[_], I = l[_.toLowerCase()], B = new Set(D.split(es)), R = [], F = [];
    return I ? (B.forEach((U) => {
      const M = P[U];
      M ? (R.push(M), B.delete(U)) : I.has(U) || (F.push(Number(U)), I.add(U));
    }), F.length > 0 && d[_](F), T ? R[0] || D : R.join() || D) : "";
  }, x = (S, _) => {
    const T = String(S);
    if (!_ || !T || T === "undefined")
      return "";
    const D = a.value[_] || [], P = new Set(T.split(es)), I = [];
    return P.forEach((B) => {
      const R = D.find((F) => String(F.id) === B);
      R != null && R.title && (I.push(R.title), P.delete(B));
    }), D.length === 0 && !l.enums.has(_) && (l.enums.add(_), xt(Kt).findEnumByName(_)), I.join() || T;
  }, y = (S) => {
    const _ = String(S);
    if (!_ || _ === "undefined")
      return "";
    const T = n.value[_];
    return T || (l.menus.has(_) || (l.menus.add(_), xt(Kt).findMenuById(_)), _);
  }, g = async (S, _) => {
    if (!(_ != null && _.length)) return;
    const T = c[S], D = l[f[S]], P = d[S];
    if (!D) return;
    const I = _.filter((B) => !T[B] && !D.has(B));
    if (I.length !== 0) {
      I.forEach((B) => D.add(B));
      try {
        const B = I.map(Number).filter((R) => !isNaN(R));
        B.length > 0 && P && await P(B);
      } catch {
        I.forEach((R) => D.delete(R)), ze.error(`${S}信息加载失败`);
      }
    }
  }, m = (S) => {
    const _ = {}, T = [];
    return S.forEach((D) => {
      _[D.id] = { ...D, children: [] };
    }), S.forEach((D) => {
      const P = _[D.parentId];
      P ? P.children.push(_[D.id]) : T.push(_[D.id]);
    }), T;
  }, C = {
    percent: (S, _, T) => p.percent(T),
    dateYMD: (S, _, T) => p.date.YMD(T),
    dateYMDHMS: (S, _, T) => p.date.YMDHMS(T),
    dateHMS: (S, _, T) => {
      const D = typeof S == "number" ? S : typeof T == "number" ? T : 0;
      return p.date.HMS(D);
    }
  };
  return {
    // Store 引用
    users: t,
    enums: a,
    tenants: r,
    menus: n,
    version: i,
    regions: s,
    orgs: o,
    dictionaries: u,
    paddingSets: l,
    // 批量操作
    batchFindObjects: g,
    // findAllUser: () => useCacheInfo(pinia).findAllUser(),
    // 格式化函数
    percentFormat: C.percent,
    dateFormatYMD: C.dateYMD,
    dateFormatYMDHMS: C.dateYMDHMS,
    dateFormatHMS: C.dateHMS,
    scaleFormat: p.number.scale,
    scale2Format: p.number.scale2,
    // 转换函数
    getUser: v.user,
    getTenant: v.tenant,
    getVersion: v.version,
    getOrg: v.org,
    getRegion: v.region,
    getEnumItem: x,
    getMenu: y,
    // 工具函数
    organizeNodesFun: m
  };
}
const ku = /* @__PURE__ */ Ue({
  __name: "dictName",
  props: {
    dictValue: {
      type: [String, Number],
      default: () => ""
    },
    dictType: {
      type: String,
      default: () => ""
    }
  },
  setup(e) {
    const { getEnumItem: t } = zt(), a = e;
    return (r, n) => (z(), he("div", null, We(ye(t)(a.dictValue, a.dictType)), 1));
  }
}), Fu = /* @__PURE__ */ Ue({
  __name: "dictSwitch",
  props: {
    value: {
      type: [Number, String, Boolean],
      default: () => ""
    }
  },
  setup(e) {
    const t = e;
    return (a, r) => (z(), he("div", null, We(t.value == !0 ? "是" : "否"), 1));
  }
}), Lu = /* @__PURE__ */ Ue({
  __name: "dictUserName",
  props: {
    userCode: {
      type: [String, Number],
      default: () => ""
    }
  },
  setup(e) {
    const { getUser: t } = zt(), a = e, r = se(""), n = () => {
      r.value = t(a.userCode);
    };
    return yt(n), nt(() => a.userCode, n), (i, s) => (z(), he("div", null, We(r.value), 1));
  }
}), Pu = /* @__PURE__ */ Ue({
  __name: "dictOrgName",
  props: {
    value: {
      type: [String, Number],
      default: ""
    }
  },
  setup(e) {
    const t = e, { orgs: a } = zt(), r = He(() => a.value[String(t.value)] || t.value);
    return (n, i) => (z(), he("span", null, We(r.value), 1));
  }
}), Uu = /* @__PURE__ */ Ue({
  __name: "dictEnum",
  props: {
    value: {
      type: [Number, String],
      default: () => ""
    },
    options: {
      type: Array,
      default: () => []
    }
  },
  setup(e) {
    const t = e;
    return (a, r) => {
      var n;
      return z(), he("div", null, We((n = t.options.filter((i) => i.value == t.value)[0]) == null ? void 0 : n.label), 1);
    };
  }
}), Ou = /* @__PURE__ */ Ue({
  __name: "dictTenant",
  props: {
    tenantId: {
      type: [Number, String],
      default: () => ""
    }
  },
  setup(e) {
    const { getTenant: t } = zt(), a = e;
    return (r, n) => (z(), he("div", null, We(ye(t)(a.tenantId)), 1));
  }
}), Vu = /* @__PURE__ */ Ue({
  __name: "dictMenu",
  props: {
    dictValue: {
      type: [String, Number],
      default: () => ""
    }
  },
  setup(e) {
    const { getMenu: t } = zt(), a = e;
    return (r, n) => (z(), he("div", null, We(ye(t)(a.dictValue)), 1));
  }
}), Mu = /* @__PURE__ */ Ue({
  __name: "dictVersion",
  props: {
    versionId: {
      type: [Number, String],
      default: () => ""
    }
  },
  setup(e) {
    const { getVersion: t } = zt(), a = e;
    return (r, n) => (z(), he("div", null, We(ye(t)(a.versionId)), 1));
  }
}), Ku = /* @__PURE__ */ Ue({
  __name: "dictArea",
  props: {
    value: {
      type: [Number, String],
      default: () => ""
    }
  },
  setup(e) {
    const { getRegion: t } = zt(), a = e;
    return (r, n) => (z(), he("div", null, We(ye(t)(a.value)), 1));
  }
}), Hu = /* @__PURE__ */ Ue({
  __name: "DisplayText",
  props: {
    value: [String, Number],
    type: {
      // 支持多种转换类型
      type: String,
      default: "user"
    }
  },
  setup(e) {
    const t = e, { users: a, orgs: r, regions: n, dictionaries: i } = zt(), s = xt().commonNames, o = (c, f) => c.map((d) => d.trim()).filter((d) => u(d)).map((d) => f[d] || d).join(","), u = (c) => !!c && c !== "undefined" && c !== "null" && c !== "", l = He(() => {
      if (!t.value) return "";
      const c = String(t.value).split(",");
      switch (t.type) {
        case "user":
          return o(c, a.value);
        case "org":
          return o(c, r.value);
        case "region":
          return o(c, n.value);
        case "dict":
          return o(c, i.value);
        case "custom":
          return o(c, s);
        default:
          return t.value;
      }
    });
    return (c, f) => (z(), he("span", null, We(l.value), 1));
  }
}), zu = { style: { width: "100%" } }, $u = /* @__PURE__ */ Ue({
  __name: "vzSelect",
  props: {
    dictType: {
      type: [Number, String],
      default: () => ""
    },
    dictValue: {
      type: [Number, String, Array],
      default: () => ""
    },
    placeholder: {
      type: [String],
      default: () => "请选择"
    },
    disabled: {
      type: Boolean,
      default: () => !1
    },
    multiple: {
      type: Boolean,
      default: () => !1
    },
    optionDisabled: {
      type: [String, Number],
      default: () => ""
    }
  },
  emits: ["update:modelValue", "change"],
  setup(e, { emit: t }) {
    const a = e, r = t, n = xt(), { enums: i } = ua(n), s = se(), o = (c) => {
      s.value = c, r("update:modelValue", c), r("change", c);
    }, u = He(() => a.dictType && a.optionDisabled ? i.value[a.dictType].filter(
      (c) => c.parentId == a.optionDisabled
    ) : i.value[a.dictType] || []), l = He(() => a.multiple === !0);
    return nt(
      () => a.dictValue,
      (c) => {
        c && (a.multiple && Array.isArray(c) && c.length > 0 ? (s.value = c.map(Number), console.log(1, c)) : a.multiple && c && c.length > 0 ? (s.value = c.split(",").map((f) => f.trim()).map(Number).filter((f) => !isNaN(f)), console.log(2, c)) : (s.value = c * 1, console.log(3, c)));
      },
      { deep: !0, immediate: !0 }
    ), yt(() => {
      xt(Kt).findEnumByName(a.dictType);
    }), (c, f) => {
      const d = Q("el-option"), v = Q("el-select");
      return z(), he("div", zu, [
        Z(v, {
          modelValue: s.value,
          "onUpdate:modelValue": f[0] || (f[0] = (p) => s.value = p),
          multiple: l.value,
          clearable: "",
          filterable: "",
          "collapse-tags": "",
          "collapse-tags-tooltip": "",
          "max-collapse-tags": 5,
          placeholder: a.placeholder,
          style: { width: "100%" },
          "value-key": "id",
          disabled: a.disabled,
          onChange: o
        }, {
          default: re(() => [
            (z(!0), he(it, null, St(u.value, (p) => (z(), ve(d, {
              key: p.id,
              label: p.title,
              value: p.id
            }, null, 8, ["label", "value"]))), 128))
          ]),
          _: 1
        }, 8, ["modelValue", "multiple", "placeholder", "disabled"])
      ]);
    };
  }
}), qu = /* @__PURE__ */ Ue({
  __name: "vzUser",
  props: {
    multiple: { type: Boolean, default: !1 },
    disabled: { type: Boolean, default: !1 },
    checkStrictly: { type: Boolean, default: !1 },
    placeholder: { type: String, default: "请选择职员" },
    dictValue: { type: [String, Number], default: "" }
  },
  emits: ["update:model", "change"],
  setup(e, { emit: t }) {
    const a = e, r = t, n = se(), i = se([]), s = () => {
      const p = globalThis;
      return p.__VzUserCache || (p.__VzUserCache = {
        orgChildrenCache: /* @__PURE__ */ new Map(),
        orgByIdCache: /* @__PURE__ */ new Map(),
        usersByDeptCache: /* @__PURE__ */ new Map(),
        usersByIdCache: /* @__PURE__ */ new Map(),
        orgsIndexedAll: !1,
        inflight: {
          allOrgs: null,
          orgChildren: /* @__PURE__ */ new Map(),
          usersByDept: /* @__PURE__ */ new Map(),
          userById: /* @__PURE__ */ new Map(),
          usersByIds: /* @__PURE__ */ new Map()
        },
        batchPendingIds: /* @__PURE__ */ new Set(),
        batchResolvers: /* @__PURE__ */ new Map(),
        batchTimer: null
      }), p.__VzUserCache;
    }, o = async () => {
      const p = s();
      if (!p.orgsIndexedAll)
        return p.inflight.allOrgs || (p.inflight.allOrgs = (async () => {
          const h = await Ra().findAll({ size: 1e3 }, { size: 1e3 }), x = (h == null ? void 0 : h.data) || [];
          for (const g of x) p.orgByIdCache.set(g.id, g);
          const y = {};
          for (const g of x) {
            const m = g.parentId == null || g.parentId === -1 ? "root" : String(g.parentId);
            y[m] || (y[m] = []), y[m].push(g);
          }
          for (const g of Object.keys(y))
            p.orgChildrenCache.set(g, y[g]);
          p.orgsIndexedAll = !0, p.inflight.allOrgs = null;
        })()), p.inflight.allOrgs;
    }, u = async (p) => {
      const h = s(), x = p == null || p === -1 ? "root" : p;
      if (h.orgChildrenCache.has(x)) return h.orgChildrenCache.get(x);
      if (h.inflight.orgChildren.has(x))
        return h.inflight.orgChildren.get(x);
      const y = (async () => {
        try {
          const m = await Ra().findAll({ parentId: p ?? -1 });
          if (!m.code) {
            const C = m.data || [];
            for (const S of C) h.orgByIdCache.set(S.id, S);
            return h.orgChildrenCache.set(x, C), C;
          }
        } catch {
        }
        return await o(), h.orgChildrenCache.get(x) || [];
      })();
      h.inflight.orgChildren.set(x, y);
      const g = await y;
      return h.inflight.orgChildren.delete(x), g;
    }, l = async (p) => {
      const h = s();
      if (h.usersByDeptCache.has(p))
        return h.usersByDeptCache.get(p);
      if (h.inflight.usersByDept.has(p))
        return h.inflight.usersByDept.get(p);
      const x = (async () => {
        try {
          const g = await Tr().findByOrg({ deptId: p }), m = g.code ? [] : g.data || [];
          return h.usersByDeptCache.set(p, m), m;
        } catch {
          return h.usersByDeptCache.set(p, []), [];
        }
      })();
      h.inflight.usersByDept.set(p, x);
      const y = await x;
      return h.inflight.usersByDept.delete(p), y;
    }, c = {
      multiple: a.multiple,
      emitPath: !1,
      checkStrictly: a.checkStrictly,
      lazy: !0,
      lazyLoad(p, h) {
        if (p.level === 0)
          u(void 0).then((x) => {
            const y = (x || []).map((g) => ({
              value: g.id,
              label: g.orgName,
              leaf: !1
            }));
            h(y);
          });
        else {
          const x = p.data.value;
          Promise.all([u(x), l(x)]).then(
            ([y, g]) => {
              const m = (y || []).map((S) => ({
                value: S.id,
                label: S.orgName,
                disabled: S.id === x,
                leaf: !1
              })), C = (g || []).map((S) => ({
                value: S.id,
                label: S.username,
                disabled: S.parentId === x,
                leaf: !0
              }));
              h([...m, ...C]);
            }
          );
        }
      }
    }, f = (p) => {
      n.value = p, r("update:model", p), r("change", p);
    }, d = async (p) => {
      var D, P, I;
      const h = s();
      let x = h.usersByIdCache.get(p);
      if (!x)
        try {
          const B = Tr().findById;
          typeof B == "function" && (h.inflight.userById.has(p) || h.inflight.userById.set(
            p,
            (async () => {
              const R = await B(p);
              return R == null ? void 0 : R.data;
            })()
          ), x = await h.inflight.userById.get(p), x && h.usersByIdCache.set(p, x));
        } catch {
        }
      if (!x)
        for (const B of h.usersByDeptCache.values()) {
          const R = B.find((F) => F.id == p);
          if (R) {
            x = R, h.usersByIdCache.set(p, x);
            break;
          }
        }
      if (!x)
        try {
          const B = await Tr().findByOrg({ current: 1, size: 1e4 });
          x = ((B == null ? void 0 : B.data) || []).find((F) => F.id == p), x && h.usersByIdCache.set(p, x);
        } catch {
        }
      if (!x) return;
      const y = x.deptId;
      h.orgByIdCache.has(y) || await o();
      const g = [];
      let m = h.orgByIdCache.get(y), C = 0;
      for (; m && C++ < 50 && (g.unshift(m), !(m.parentId == null || m.parentId === -1)); )
        if (m = h.orgByIdCache.get(m.parentId), !m) {
          const B = await u((D = g[0]) == null ? void 0 : D.parentId);
          for (const R of B || []) h.orgByIdCache.set(R.id, R);
          m = h.orgByIdCache.get((P = g[0]) == null ? void 0 : P.parentId);
        }
      await u(void 0);
      for (const B of g)
        await u(B.id);
      await l(y);
      const S = await u(void 0);
      (!Array.isArray(i.value) || i.value.length === 0) && (i.value = (S || []).map((B) => ({
        value: B.id,
        label: B.orgName,
        leaf: !1,
        children: void 0
      })));
      const _ = (B, R, F) => {
        let U = B.find((M) => M.value === R.id);
        return U ? F != null && U.disabled === void 0 && (U.disabled = R.id === F) : (U = {
          value: R.id,
          label: R.orgName,
          leaf: !1,
          disabled: F != null ? R.id === F : void 0,
          children: void 0
        }, B.push(U)), U.children || (U.children = []), U;
      };
      let T = i.value;
      for (const B of g) {
        const R = T.length > 0 ? (I = T[0]) == null ? void 0 : I.parentId : void 0, F = _(T, B, R), U = await u(B.id), M = await l(B.id);
        for (const ee of U || []) _(F.children, ee, B.id);
        for (const ee of M || [])
          F.children.find((q) => q.value === ee.id) || F.children.push({
            value: ee.id,
            label: ee.username,
            leaf: !0,
            disabled: ee.parentId === B.id
          });
        T = F.children;
      }
    }, v = async (p) => {
      const h = s(), x = Array.from(new Set(p)), y = x.filter((g) => !h.usersByIdCache.has(g));
      if (y.length > 0) {
        const g = y.map(
          (m) => new Promise((C) => {
            const S = h.batchResolvers.get(m) || [];
            S.push(C), h.batchResolvers.set(m, S), h.batchPendingIds.add(m);
          })
        );
        h.batchTimer || (h.batchTimer = setTimeout(async () => {
          const m = Array.from(h.batchPendingIds);
          h.batchPendingIds.clear(), h.batchTimer = null;
          let C = [];
          try {
            const _ = Tr().findByIds;
            if (typeof _ == "function") {
              const T = await _(m);
              C = (T == null ? void 0 : T.data) || [];
            } else
              C = (await Promise.all(
                m.map((D) => {
                  var P, I;
                  return (I = (P = Tr()).findById) == null ? void 0 : I.call(P, D);
                })
              )).map((D) => D == null ? void 0 : D.data).filter((D) => D != null);
          } catch {
            C = [];
          }
          const S = /* @__PURE__ */ new Map();
          for (const _ of C || [])
            h.usersByIdCache.set(_.id, _), S.set(_.id, _);
          for (const _ of m) {
            const T = h.batchResolvers.get(_) || [], D = S.get(_);
            for (const P of T) P(D);
            h.batchResolvers.delete(_);
          }
        }, 0)), await Promise.all(g);
      }
      for (const g of x) await d(g);
    };
    return nt(
      () => a.dictValue,
      (p) => {
        if (p)
          if (a.multiple) {
            const h = Array.isArray(p) ? p : p.toString().split(",").map((x) => x.trim()).map((x) => isNaN(Number(x)) ? x : Number(x));
            v(h).then(() => {
              n.value = h;
            });
          } else {
            const h = isNaN(Number(p)) ? p : Number(p);
            v([h]).then(() => {
              n.value = h;
            });
          }
        else
          n.value = p;
      },
      { immediate: !0, deep: !0 }
    ), yt(() => {
    }), (p, h) => {
      const x = Q("el-cascader");
      return z(), ve(x, {
        modelValue: n.value,
        "onUpdate:modelValue": h[0] || (h[0] = (y) => n.value = y),
        options: i.value,
        props: c,
        disabled: a.disabled,
        placeholder: a.placeholder,
        "show-all-levels": !1,
        clearable: !0,
        filterable: "",
        onChange: f,
        style: { width: "100%" }
      }, null, 8, ["modelValue", "options", "disabled", "placeholder"]);
    };
  }
}), ju = { style: { width: "100%" } }, Gu = { style: { "font-size": "14px", "font-weight": "bold" } }, Wu = /* @__PURE__ */ $e("span", { style: { color: "var(--el-color-info-light-5)" } }, " / ", -1), Yu = { style: { color: "var(--el-color-info-light-5)", "font-size": "12px" } }, Qu = /* @__PURE__ */ Ue({
  __name: "vzUserWithoutOrg",
  props: {
    dictType: {
      type: [Number, String],
      default: () => ""
    },
    dictValue: {
      type: [String, Number, Array],
      default: () => ""
    },
    placeholder: {
      type: [String],
      default: () => "请选择"
    },
    disabled: {
      type: Boolean,
      default: () => !1
    },
    multiple: {
      type: Boolean,
      default: () => !1
    },
    type: {
      type: String,
      default: () => "user"
    },
    orgCode: {
      type: [String, Number],
      default: () => ""
    }
  },
  emits: ["update:modelValue", "change"],
  setup(e, { emit: t }) {
    const a = e, r = t;
    let n;
    a.multiple ? n = se(
      Array.isArray(a.dictValue) ? a.dictValue : []
    ) : n = se(
      Array.isArray(a.dictValue) ? a.dictValue[0] : a.dictValue || ""
    );
    const i = se({
      current: 1,
      size: 1e4
    }), s = se([]), o = se(0), u = se(!1), l = He(() => a.multiple === !0), c = (p) => {
      n.value = p, r("update:modelValue", p), r("change", p);
    }, f = se(), d = async () => {
      if (!a.orgCode) return;
      const { data: p } = await Ra().findAll(
        { size: 1, current: 1, orgCode: a.orgCode },
        { size: 1, current: 1 }
      );
      p && p.length && (f.value = p[0].id);
    }, v = async () => {
      await d(), u.value = !0, Tr().findByOrg(
        {
          ...i.value,
          orgId: f.value
        },
        i.value
      ).then((p) => {
        s.value = p.data, o.value = p.page.total;
      }).finally(() => {
        u.value = !1;
      });
    };
    return nt(
      () => a.dictValue,
      (p) => {
        if (p)
          if (a.multiple && p) {
            const h = p.toString().split(",").map((x) => x.trim()).map(Number).filter((x) => !isNaN(x));
            n.value = Array.isArray(p) ? p : h;
          } else
            n.value = Number(p);
        else
          n.value = p;
      },
      { immediate: !0, deep: !0 }
    ), yt(() => {
      v();
    }), (p, h) => {
      const x = Q("el-option"), y = Q("el-select");
      return z(), he("div", ju, [
        Z(y, {
          modelValue: ye(n),
          "onUpdate:modelValue": h[0] || (h[0] = (g) => Jt(n) ? n.value = g : n = g),
          multiple: l.value,
          "remote-show-suffix": "",
          filterable: "",
          clearable: "",
          remote: "",
          "reserve-keyword": "",
          disabled: a.disabled,
          loading: u.value,
          placeholder: "搜索真实姓名 / 选择人员",
          onChange: c,
          style: { width: "100%" }
        }, {
          default: re(() => [
            (z(!0), he(it, null, St(s.value, (g) => (z(), ve(x, {
              key: g.id,
              label: g.username,
              value: g.id
            }, {
              default: re(() => [
                $e("span", Gu, We(g.username), 1),
                Wu,
                $e("span", Yu, We(g.account), 1)
              ]),
              _: 2
            }, 1032, ["label", "value"]))), 128))
          ]),
          _: 1
        }, 8, ["modelValue", "multiple", "disabled", "loading"])
      ]);
    };
  }
}), Xu = { style: { width: "100%" } }, Zu = /* @__PURE__ */ Ue({
  __name: "vzEnum",
  props: {
    dictType: {
      type: [Number, String],
      default: () => ""
    },
    dictValue: {
      type: [String, Number, Array],
      default: () => ""
    },
    placeholder: {
      type: [String],
      default: () => "请选择"
    },
    disabled: {
      type: Boolean,
      default: () => !1
    },
    multiple: {
      type: Boolean,
      default: () => !1
    },
    options: {
      type: Array,
      default: () => []
    },
    clearable: {
      type: Boolean,
      default: () => !1
    },
    filterable: {
      type: Boolean,
      default: () => !1
    }
  },
  emits: ["update:modelValue", "change"],
  setup(e, { emit: t }) {
    const a = e, r = t;
    let n;
    a.multiple ? n = se(
      Array.isArray(a.dictValue) ? a.dictValue : []
    ) : n = se(
      Array.isArray(a.dictValue) ? a.dictValue[0] : a.dictValue || ""
    ), nt(
      () => a.dictValue,
      (o) => {
        n.value = o;
      },
      {
        deep: !0
      }
    );
    const i = (o) => {
      n.value = o, r("update:modelValue", o), r("change", o);
    }, s = He(() => a.multiple === !0);
    return yt(() => {
    }), (o, u) => {
      const l = Q("el-option"), c = Q("el-select");
      return z(), he("div", Xu, [
        Z(c, {
          modelValue: ye(n),
          "onUpdate:modelValue": u[0] || (u[0] = (f) => Jt(n) ? n.value = f : n = f),
          multiple: s.value,
          clearable: a.clearable,
          filterable: a.filterable,
          "collapse-tags": "",
          "collapse-tags-tooltip": "",
          "max-collapse-tags": 2,
          placeholder: a.placeholder,
          "value-key": "id",
          style: { width: "100%" },
          onChange: i,
          disabled: a.disabled
        }, {
          default: re(() => [
            (z(!0), he(it, null, St(e.options, (f) => (z(), ve(l, {
              key: f.value,
              label: f.label,
              value: f.value
            }, null, 8, ["label", "value"]))), 128))
          ]),
          _: 1
        }, 8, ["modelValue", "multiple", "clearable", "filterable", "placeholder", "disabled"])
      ]);
    };
  }
}), Ju = { class: "table-demo-container layout-padding" }, ef = { class: "table-demo-padding layout-padding-view layout-padding-auto" }, tf = /* @__PURE__ */ Ue({
  __name: "vzTenantDialog",
  props: {
    // 占位符
    placeholder: {
      type: String,
      default: () => ""
    },
    // 是否禁用
    disabled: {
      type: Boolean,
      default: () => !1
    },
    select: {
      type: [String, Number],
      default: () => ""
    },
    company: {
      type: [String, Number],
      default: () => ""
    },
    companyId: {
      type: [String, Number],
      default: () => ""
    },
    type: {
      type: String,
      default: () => ""
    },
    unchanged: {
      type: Object,
      default: () => ({})
    },
    isSelection: {
      type: Boolean,
      default: () => !1
    },
    row: {
      type: Object,
      default: () => ({})
    }
  },
  emits: ["handleOk", "open", "doubleClick", "clear"],
  setup(e, { emit: t }) {
    const a = e, r = t, n = se("选择租户"), i = se({}), s = se([]), o = se(a.company), u = or({
      dialogVisible: !1,
      concactName: "",
      tableData: {
        // 列表数据（必传）
        data: [],
        // 表头内容（必传，注意格式）
        header: [
          {
            key: "logo",
            colWidth: "100",
            title: "租户logo",
            type: "img",
            isCheck: !0
          },
          {
            key: "tenantCode",
            colWidth: "150",
            title: "租户编码",
            type: "text",
            isCheck: !0
          },
          {
            key: "tenantName",
            colWidth: "150",
            title: "租户名称",
            type: "text",
            isCheck: !0
          },
          {
            key: "tenantType",
            colWidth: "100",
            title: "租户类型",
            type: "text",
            isCheck: !0
          },
          {
            key: "versionId",
            colWidth: "100",
            title: "版本ID",
            type: "text",
            isCheck: !0
          },
          {
            key: "versionUseDeadline",
            colWidth: "130",
            title: "使用截止时间",
            type: "date",
            isCheck: !0
          },
          {
            key: "contactName",
            colWidth: "100",
            title: "联系人",
            type: "text",
            isCheck: !0
          },
          {
            key: "contactPhone",
            colWidth: "150",
            title: "联系电话",
            type: "text",
            isCheck: !0
          },
          {
            key: "province",
            colWidth: "100",
            title: "省",
            type: "text",
            isCheck: !0
          },
          {
            key: "city",
            colWidth: "100",
            title: "市",
            type: "text",
            isCheck: !0
          },
          {
            key: "district",
            colWidth: "100",
            title: "区",
            type: "text",
            isCheck: !0
          },
          {
            key: "address",
            colWidth: "200",
            title: "详细地址",
            type: "text",
            align: "left",
            isCheck: !0
          },
          {
            key: "enabled",
            colWidth: "90",
            title: "是否启用",
            type: "boolean",
            isCheck: !0
          },
          {
            key: "creator",
            colWidth: "100",
            title: "创建人",
            type: "user",
            isCheck: !0
          },
          {
            key: "createStamp",
            colWidth: "160",
            title: "创建时间",
            type: "datetime",
            isCheck: !0
          }
        ],
        // 配置项（必传）
        config: {
          listApi: Ba().findAll,
          //列表接口
          delApi: Ba().delete,
          //删除接口
          total: 0,
          // 列表总数
          loading: !0,
          // loading 加载
          isBorder: !0,
          // 是否显示表格边框
          isSerialNo: !0,
          // 是否显示表格序号
          isSelection: !1,
          // 是否显示表格多选
          isOperate: !1
          // 是否显示表格操作栏
        },
        // 搜索表单，动态生成（传空数组时，将不显示搜索，注意格式）
        search: [
          { label: "租户名称", prop: "tenantName" },
          { label: "租户编码", prop: "tenantCode" },
          {
            label: "是否启用",
            prop: "enabled",
            options: [
              { label: "是", value: !0 },
              { label: "否", value: !1 }
            ]
          },
          { label: "创建人", prop: "creator", type: "user" },
          { label: "创建时间", prop: "createStampRange", type: "datetimerange" }
        ],
        //表格按钮配置 动态生成（传空数组时，将不显示按钮，注意格式）
        tableButtons: [],
        //搜索表单按钮显示 动态生成（传空数组时，只显示 搜索 重置，注意格式）
        searchButtons: [],
        // 搜索参数 用于默认传参
        param: {},
        // 打印标题
        printName: "租户管理"
      }
    }), l = () => {
      u.dialogVisible = !0;
    }, c = (y) => {
      r("handleOk", y), u.dialogVisible = !1;
    }, f = (y) => {
      i.value = y;
    }, d = () => {
      if (Object.keys(i.value).length === 0 && s.value.length === 0) {
        ze.warning("请先选择");
        return;
      }
      r("handleOk", i.value), u.dialogVisible = !1;
    }, v = () => {
      r("open");
    }, p = () => {
      u.dialogVisible = !1;
    }, h = () => u.concactName, x = async (y) => {
      if (!y) return;
      const { data: g } = await Ba().findById(y);
      return g.tenantName;
    };
    return nt(
      () => a.company,
      (y) => {
        x(y).then((g) => {
          u.concactName = g;
        }), o.value = y;
      },
      { immediate: !0 }
      // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
    ), yt(() => {
    }), (y, g) => {
      const m = Q("el-input"), C = Q("vz-table"), S = Q("el-button"), _ = Q("el-dialog");
      return z(), he("div", null, [
        Z(m, {
          modelValue: o.value,
          "onUpdate:modelValue": g[0] || (g[0] = (T) => o.value = T),
          value: h(),
          "suffix-icon": ye(Ua),
          placeholder: a.placeholder || n.value,
          clearable: "",
          style: { width: "100%" },
          disabled: a.disabled || !1,
          onClick: l,
          readonly: ""
        }, null, 8, ["modelValue", "value", "suffix-icon", "placeholder", "disabled"]),
        Z(_, {
          onOpen: v,
          title: n.value,
          onBeforeClose: d,
          width: "70%",
          modelValue: u.dialogVisible,
          "onUpdate:modelValue": g[1] || (g[1] = (T) => u.dialogVisible = T),
          "append-to-body": ""
        }, {
          footer: re(() => [
            Z(S, {
              class: "c_blue",
              onClick: p
            }, {
              default: re(() => [
                rt("取消")
              ]),
              _: 1
            }),
            Z(S, {
              type: "primary",
              onClick: d
            }, {
              default: re(() => [
                rt("确定")
              ]),
              _: 1
            })
          ]),
          default: re(() => [
            $e("div", Ju, [
              $e("div", ef, [
                Z(C, {
                  ref: "vzTableRef",
                  tableData: u.tableData,
                  onRowClick: f,
                  onRowDblclick: c
                }, null, 8, ["tableData"])
              ])
            ])
          ]),
          _: 1
        }, 8, ["title", "modelValue"])
      ]);
    };
  }
}), Or = (e, t) => {
  const a = e.__vccOpts || e;
  for (const [r, n] of t)
    a[r] = n;
  return a;
}, rf = /* @__PURE__ */ Or(tf, [["__scopeId", "data-v-f42f93f5"]]), af = ca("routesList", {
  state: () => ({
    routesList: [],
    isColumnsMenuHover: !1,
    isColumnsNavHover: !1
  }),
  actions: {
    async setRoutesList(e) {
      this.routesList = e;
    },
    async setColumnsMenuHover(e) {
      this.isColumnsMenuHover = e;
    },
    async setColumnsNavHover(e) {
      this.isColumnsNavHover = e;
    }
  }
}), nf = { style: { width: "100%" } }, sf = /* @__PURE__ */ Ue({
  __name: "vzMenu",
  props: {
    placeholder: {
      type: [String],
      default: () => "搜索真实姓名 / 选择人员"
    },
    disabled: {
      type: Boolean,
      default: () => !1
    },
    multiple: {
      type: Boolean,
      default: () => !1
    },
    clearable: {
      type: Boolean,
      default: () => !0
    },
    defaultValue: {
      type: [String, Number, Array],
      default: () => []
    }
  },
  emits: ["update:menuValue", "change"],
  setup(e, { emit: t }) {
    const a = t, r = e, n = {
      checkStrictly: !0
    }, i = se([]), s = se(!1), o = af(), { routesList: u } = ua(o), { organizeNodesFun: l } = zt(), c = se(r.defaultValue), f = () => {
      s.value = !0, Wo().findAll({ size: 1e4, current: 1 }, { size: 1e4, current: 1 }).then(async (v) => {
        const p = v.data.map((h) => ({
          ...h,
          value: h.id,
          label: h.menuName
        }));
        i.value = await l(p);
      });
    }, d = (v) => {
      c.value = v, a("update:menuValue", v && v.length > 0 ? v[v.length - 1] : "");
    };
    return nt(
      () => r.defaultValue,
      (v) => {
        c.value = v || [];
      }
    ), yt(() => {
      f(), console.log(u.value);
    }), (v, p) => {
      const h = Q("el-cascader");
      return z(), he("div", nf, [
        Z(h, {
          modelValue: c.value,
          "onUpdate:modelValue": p[0] || (p[0] = (x) => c.value = x),
          placeholder: "请选择菜单",
          options: i.value,
          onChange: d,
          props: n,
          filterable: "",
          style: { width: "100%" },
          clearable: r.clearable
        }, null, 8, ["modelValue", "options", "clearable"])
      ]);
    };
  }
}), Ar = "orgOptionsCache", of = /* @__PURE__ */ Ue({
  __name: "vzOrg",
  props: {
    org: {
      type: [String, Array, Number],
      default: () => ""
    },
    dictValue: {
      type: [String, Array, Number],
      default: () => ""
    },
    placeholder: {
      type: String,
      default: () => "搜索组织 / 选择组织"
    },
    disabled: {
      type: Boolean,
      default: () => !1
    },
    multiple: {
      type: Boolean,
      default: () => !1
    },
    clearable: {
      type: Boolean,
      default: () => !0
    },
    checkStrictly: {
      type: Boolean,
      default: () => !1
    },
    defaultValue: {
      type: String,
      default: () => "MENU"
    },
    orgType: {
      type: String,
      default: () => ""
    }
  },
  emits: ["update:orgValue", "change"],
  setup(e, { expose: t, emit: a }) {
    const r = a, n = e, i = xt(), { organizeNodesFun: s } = zt(), o = se(n.org), u = se(!1), l = se([]), c = {
      isRequesting: !1,
      requestPromise: null
    }, f = He(() => ({
      checkStrictly: n.checkStrictly,
      emitPath: !1,
      multiple: n.multiple,
      value: "value",
      label: "label",
      children: "children"
    })), d = async () => {
      if (i.orgs[Ar] && i.orgs[Ar].length > 0) {
        l.value = i.orgs[Ar];
        return;
      }
      if (c.isRequesting && c.requestPromise)
        try {
          await c.requestPromise, i.orgs[Ar] && (l.value = i.orgs[Ar]);
          return;
        } catch (h) {
          console.error("等待组织数据请求失败:", h);
        }
      c.isRequesting = !0, u.value = !0;
      try {
        let h = n.orgType ? { orgType: n.orgType } : {};
        c.requestPromise = Ra().findAll(h, { size: 1e4, current: 1 }).then(async (x) => {
          if (x.code === 0) {
            const y = x.data.map((m) => ({
              ...m,
              value: m.id,
              label: m.orgName
            })), g = await s(y);
            return i.orgs[Ar] = g, l.value = g, g;
          }
          return [];
        }).finally(() => {
          c.isRequesting = !1, c.requestPromise = null, u.value = !1;
        }), await c.requestPromise;
      } catch (h) {
        console.error("获取组织数据失败:", h), c.isRequesting = !1, c.requestPromise = null, u.value = !1;
      }
    }, v = () => {
      delete i.orgs[Ar];
    }, p = (h) => {
      if (!h) {
        o.value = "", r("update:orgValue", o.value), r("change", o.value);
        return;
      }
      n.multiple ? o.value = Array.isArray(h) ? [...new Set(h.flat(1 / 0))] : h : o.value = h || "", r("update:orgValue", o.value), r("change", o.value);
    };
    return nt(
      () => n.org,
      (h) => {
        o.value = h;
      },
      { immediate: !0 }
    ), nt(
      () => n.orgType,
      () => {
        v(), d();
      }
    ), yt(() => {
      d();
    }), t({
      clearOrgCache: v,
      refreshOrgOptions: () => (v(), d())
    }), (h, x) => {
      const y = Q("el-cascader");
      return z(), ve(y, {
        modelValue: o.value,
        "onUpdate:modelValue": x[0] || (x[0] = (g) => o.value = g),
        filterable: "",
        options: l.value,
        onChange: p,
        props: f.value,
        style: { width: "100%" },
        disabled: n.disabled,
        clearable: n.clearable,
        placeholder: n.placeholder
      }, null, 8, ["modelValue", "options", "props", "disabled", "clearable", "placeholder"]);
    };
  }
}), lf = { class: "table-demo-container layout-padding" }, cf = { class: "table-demo-padding layout-padding-view layout-padding-auto" }, uf = /* @__PURE__ */ Ue({
  __name: "vzVersionDialog",
  props: {
    // 占位符
    placeholder: {
      type: String,
      default: () => ""
    },
    // 是否禁用
    disabled: {
      type: Boolean,
      default: () => !1
    },
    select: {
      type: [String, Number],
      default: () => ""
    },
    company: {
      type: [String, Number],
      default: () => ""
    },
    companyId: {
      type: [String, Number],
      default: () => ""
    },
    type: {
      type: String,
      default: () => ""
    },
    unchanged: {
      type: Object,
      default: () => ({})
    },
    isSelection: {
      type: Boolean,
      default: () => !1
    },
    row: {
      type: Object,
      default: () => ({})
    }
  },
  emits: ["handleOk", "open", "doubleClick", "clear"],
  setup(e, { emit: t }) {
    const a = e, r = t, n = se("选择版本"), i = se({}), s = se([]), o = se(a.company), u = or({
      dialogVisible: !1,
      concactName: "",
      tableData: {
        // 列表数据（必传）
        data: [],
        // 表头内容（必传，注意格式）
        header: [
          { key: "versionName", colWidth: "150", title: "版本名称", type: "text", isCheck: !0 },
          {
            key: "versionType",
            colWidth: "100",
            title: "版本类型",
            type: "enum",
            options: [
              { label: "商业版", value: "SALE" },
              { label: "试用版", value: "TRIAL" },
              { label: "自定义", value: "CUSTOM" }
            ],
            isCheck: !0
          },
          { key: "effectiveDays", colWidth: "100", title: "有效天数", type: "text", isCheck: !0 },
          { key: "remarks", colWidth: "350", title: "备注", type: "text", align: "left", isCheck: !0 },
          { key: "enabled", colWidth: "90", title: "是否启用", type: "boolean", isCheck: !0 },
          { key: "creator", colWidth: "100", title: "创建人", type: "user", isCheck: !0 },
          { key: "createStamp", colWidth: "160", title: "创建时间", type: "datetime", isCheck: !0 }
        ],
        // 配置项（必传）
        config: {
          listApi: tn().findAll,
          //列表接口
          delApi: tn().delete,
          //删除接口
          total: 0,
          // 列表总数
          loading: !0,
          // loading 加载
          isBorder: !0,
          // 是否显示表格边框
          isSerialNo: !0,
          // 是否显示表格序号
          isSelection: !1,
          // 是否显示表格多选
          isOperate: !1
          // 是否显示表格操作栏
        },
        // 搜索表单，动态生成（传空数组时，将不显示搜索，注意格式）
        search: [
          { label: "版本名称", prop: "versionName" },
          {
            label: "版本类型",
            prop: "versionType",
            options: [
              { label: "商业版", value: "SALE" },
              { label: "试用版", value: "TRIAL" },
              { label: "自定义", value: "CUSTOM" }
            ],
            clearable: !0
          },
          { label: "创建人", prop: "creator", type: "user" },
          { label: "创建时间", prop: "createStampRange", type: "datetimerange" }
        ],
        //表格按钮配置 动态生成（传空数组时，将不显示按钮，注意格式）
        tableButtons: [],
        //搜索表单按钮显示 动态生成（传空数组时，只显示 搜索 重置，注意格式）
        searchButtons: [],
        // 搜索参数 用于默认传参
        param: { enabled: !0 },
        // 打印标题
        printName: "版本管理"
      }
    }), l = () => {
      u.dialogVisible = !0;
    }, c = (y) => {
      r("handleOk", y), u.dialogVisible = !1;
    }, f = (y) => {
      i.value = y;
    }, d = () => {
      if (Object.keys(i.value).length === 0 && s.value.length === 0) {
        ze.warning("请先选择");
        return;
      }
      r("handleOk", i.value), u.dialogVisible = !1;
    }, v = () => {
      r("open");
    }, p = () => {
      u.dialogVisible = !1;
    }, h = () => u.concactName, x = async (y) => {
      if (!y) return;
      const { data: g } = await tn().findById(y);
      return g.versionName;
    };
    return nt(
      () => a.company,
      (y) => {
        x(y).then((g) => {
          u.concactName = g;
        }), o.value = y;
      },
      { immediate: !0 }
      // 设置 immediate 为 true，确保在组件初始化时也触发一次监听
    ), yt(() => {
    }), (y, g) => {
      const m = Q("el-input"), C = Q("vz-table"), S = Q("el-button"), _ = Q("el-dialog");
      return z(), he("div", null, [
        Z(m, {
          modelValue: o.value,
          "onUpdate:modelValue": g[0] || (g[0] = (T) => o.value = T),
          value: h(),
          "suffix-icon": ye(Ua),
          placeholder: a.placeholder || n.value,
          clearable: "",
          style: { width: "100%" },
          disabled: a.disabled || !1,
          onClick: l,
          readonly: ""
        }, null, 8, ["modelValue", "value", "suffix-icon", "placeholder", "disabled"]),
        Z(_, {
          onOpen: v,
          title: n.value,
          onBeforeClose: d,
          width: "70%",
          modelValue: u.dialogVisible,
          "onUpdate:modelValue": g[1] || (g[1] = (T) => u.dialogVisible = T),
          "append-to-body": ""
        }, {
          footer: re(() => [
            Z(S, {
              class: "c_blue",
              onClick: p
            }, {
              default: re(() => [
                rt("取消")
              ]),
              _: 1
            }),
            Z(S, {
              type: "primary",
              onClick: d
            }, {
              default: re(() => [
                rt("确定")
              ]),
              _: 1
            })
          ]),
          default: re(() => [
            $e("div", lf, [
              $e("div", cf, [
                Z(C, {
                  ref: "vzTableRef",
                  tableData: u.tableData,
                  onRowClick: f,
                  onRowDblclick: c
                }, null, 8, ["tableData"])
              ])
            ])
          ]),
          _: 1
        }, 8, ["title", "modelValue"])
      ]);
    };
  }
}), ff = /* @__PURE__ */ Or(uf, [["__scopeId", "data-v-3b4d6d94"]]), df = Go(), ts = ca({
  id: "vz-regionsInfo",
  state: () => ({
    regionInfos: {
      province: [],
      city: [],
      district: []
    }
  }),
  actions: {
    //获取省市区
    async getAreaList(e, t, a) {
      if (e != "city" && e != "district" && this.regionInfos[e] && this.regionInfos[e].length > 0)
        return;
      const { data: r } = await df.findRegionsByAll(t);
      this.regionInfos[e] = r.map((n) => ({
        value: n.id,
        label: n.name,
        leaf: a >= 2
      }));
    }
  },
  persist: l0("vz-regionsInfo")
});
function pf() {
  const e = ts(), { regionInfos: t } = ua(e);
  return {
    getArea: async (r, n, i) => (await ts(Kt).getAreaList(r, n, i), t.value[r])
  };
}
const hf = { style: { width: "100%" } }, vf = /* @__PURE__ */ Ue({
  __name: "vzArea",
  props: {
    area: {
      type: Array,
      default: () => []
    },
    disabled: {
      type: Boolean,
      defalut: () => !1
    },
    checkStrictly: {
      type: Boolean,
      defalut: () => !1
    },
    level: {
      type: Number,
      default: () => 2
    },
    provinces: {
      type: Array,
      default: () => []
    }
  },
  emits: ["update:areaArray", "change", "expandchange"],
  setup(e, { emit: t }) {
    const a = e, { getArea: r } = pf(), n = t, i = se(), s = se(a.area), o = (c) => {
      s.value = c, n("update:areaArray", c), n("change", c);
    }, u = (c) => {
      n("expandchange", c);
    }, l = {
      lazy: !0,
      checkStrictly: a.checkStrictly,
      lazyLoad: async (c, f) => {
        const { level: d, value: v } = c;
        if (d == 0) {
          const p = await r("province", { level: 1 }, d);
          if (a.provinces.length <= 0) return f(p);
          const h = p.filter(
            (x) => a.provinces.some((y) => x.label.includes(y))
          );
          f(h);
        } else if (d === 1) {
          const p = await r(
            "city",
            { level: 2, parentId: v },
            d
          );
          f(p);
        } else if (d === 2) {
          const p = await r(
            "district",
            { level: 3, parentId: v },
            d
          );
          f(p);
        }
      }
    };
    return nt(
      () => a.area,
      (c) => {
        s.value = c, n("update:areaArray", c);
      },
      {
        deep: !0,
        immediate: !0
      }
    ), (c, f) => {
      const d = Q("el-cascader");
      return z(), he("div", hf, [
        Z(d, {
          ref_key: "classifyRef",
          ref: i,
          modelValue: s.value,
          "onUpdate:modelValue": f[0] || (f[0] = (v) => s.value = v),
          props: l,
          onChange: o,
          onExpandChange: u,
          style: { width: "100%" },
          clearable: "",
          filterable: "",
          onClear: o,
          disabled: a.disabled,
          placeholder: "请选择省市区"
        }, null, 8, ["modelValue", "disabled"])
      ]);
    };
  }
}), xf = /* @__PURE__ */ Or(vf, [["__scopeId", "data-v-89cef323"]]), gf = ["src"], yf = /* @__PURE__ */ Ue({
  __name: "vzUpload",
  props: {
    limit: {
      type: Number,
      default: () => 1
    },
    url: {
      type: String,
      default: () => ""
    }
  },
  emits: ["onSuccess", "update:fileList"],
  setup(e, { emit: t }) {
    var p;
    const a = t, r = e, n = se(""), i = se(!1), s = ba.get("token"), o = se(s), u = se((p = JSON.parse(localStorage.getItem("tenant") || "{}")) == null ? void 0 : p.id), l = se([]), c = (h) => {
      r.limit == 1 && a("update:fileList", "");
    }, f = (h) => {
      r.limit == 1 && a("update:fileList", "/files/" + h.data), a("onSuccess", h);
    }, d = (h) => {
      n.value = h.url, i.value = !0;
    }, v = He(() => l.value.length >= r.limit);
    return nt(
      () => r.url,
      (h) => {
        l.value = h ? [{ url: h }] : [];
      }
    ), yt(() => {
      var h;
      o.value = s, u.value = (h = JSON.parse(localStorage.getItem("tenant") || "{}")) == null ? void 0 : h.id;
    }), (h, x) => {
      const y = Q("el-icon"), g = Q("el-upload"), m = Q("el-dialog");
      return z(), he("div", null, [
        Z(g, {
          class: a0(["avatar-uploader", { disabled: v.value }]),
          headers: { Authorization: o.value, TenantId: u.value },
          action: "/api/infrastructure/sys/file/upload",
          "file-list": l.value,
          "onUpdate:fileList": x[0] || (x[0] = (C) => l.value = C),
          "list-type": "picture-card",
          limit: r.limit,
          "on-preview": d,
          "on-remove": c,
          "on-success": f
        }, {
          default: re(() => [
            Z(y, null, {
              default: re(() => [
                Z(ye(kc))
              ]),
              _: 1
            })
          ]),
          _: 1
        }, 8, ["class", "headers", "file-list", "limit"]),
        Z(m, {
          modelValue: i.value,
          "onUpdate:modelValue": x[1] || (x[1] = (C) => i.value = C)
        }, {
          default: re(() => [
            $e("img", {
              "w-full": "",
              src: n.value,
              alt: "Preview Image"
            }, null, 8, gf)
          ]),
          _: 1
        }, 8, ["modelValue"])
      ]);
    };
  }
});
function mf() {
  return {
    findAll: (e, t) => Se({
      url: `${_e}/sys/roles`,
      method: "post",
      data: e,
      params: t
    }),
    add: (e) => Se({
      url: `${_e}/sys/role`,
      method: "post",
      data: e
    }),
    update: (e) => Se({
      url: `${_e}/sys/role`,
      method: "put",
      data: e
    }),
    delete: (e) => Se({
      url: `${_e}/sys/role/${e}`,
      method: "delete"
    }),
    findById: (e) => Se({
      url: `${_e}/sys/role/${e}`,
      method: "get"
    })
  };
}
const Cf = { style: { width: "100%" } }, Ef = /* @__PURE__ */ Ue({
  __name: "vzRole",
  props: {
    dictType: {
      type: [Number, String],
      default: () => ""
    },
    dictValue: {
      type: [String, Array],
      default: () => ""
    },
    placeholder: {
      type: [String],
      default: () => "请选择角色"
    },
    disabled: {
      type: Boolean,
      default: () => !1
    },
    multiple: {
      type: Boolean,
      default: () => !1
    },
    role: {
      type: Array,
      default: () => []
    }
  },
  emits: ["update:modelValue", "change"],
  setup(e, { emit: t }) {
    const a = e, r = t;
    let n;
    a.multiple ? n = se(Array.isArray(a.dictValue) ? a.dictValue : []) : n = se(Array.isArray(a.dictValue) ? a.dictValue[0] : a.dictValue || "");
    const i = se({
      current: 1,
      size: 50
    }), s = se([]), o = se(0), u = se(""), l = se(!1), c = He(() => a.multiple === !0), f = (h) => {
      n.value = h, r("update:modelValue", h), r("change", h);
    }, d = (h) => {
      u.value = h, p();
    }, v = (h) => {
      i.value.current = h, p();
    }, p = () => {
      l.value = !0, mf().findAll(
        {
          ...i.value,
          roleName: u.value
        },
        i.value
      ).then((h) => {
        s.value = h.data, o.value = h.page.total;
      }).finally(() => {
        l.value = !1;
      });
    };
    return nt(
      () => a.role,
      (h) => {
        n.value = h || [];
      }
    ), yt(() => {
      p();
    }), (h, x) => {
      const y = Q("el-pagination"), g = Q("el-option"), m = Q("el-select");
      return z(), he("div", Cf, [
        Z(m, {
          modelValue: ye(n),
          "onUpdate:modelValue": x[0] || (x[0] = (C) => Jt(n) ? n.value = C : n = C),
          multiple: c.value,
          "remote-show-suffix": "",
          filterable: "",
          remote: "",
          "reserve-keyword": "",
          "remote-method": d,
          loading: l.value,
          placeholder: "搜索角色名称",
          onChange: f,
          style: { width: "100%" },
          "value-key": "id"
        }, {
          footer: re(() => [
            Z(y, {
              onCurrentChange: v,
              small: "",
              background: "",
              layout: "prev, pager, next",
              "page-size": i.value.size,
              total: o.value,
              class: "mt-4"
            }, null, 8, ["page-size", "total"])
          ]),
          default: re(() => [
            (z(!0), he(it, null, St(s.value, (C) => (z(), ve(g, {
              key: C.id,
              label: C.roleName,
              value: C
            }, {
              default: re(() => [
                $e("span", null, We(C.roleName), 1)
              ]),
              _: 2
            }, 1032, ["label", "value"]))), 128))
          ]),
          _: 1
        }, 8, ["modelValue", "multiple", "loading"])
      ]);
    };
  }
}), bf = { class: "moneyInput" }, Sf = { class: "money" }, Af = {
  key: 0,
  class: "chineseMoney"
}, _f = /* @__PURE__ */ Ue({
  __name: "vz_money_input",
  props: {
    step: { default: 1 },
    max: { default: 999999999999 },
    min: {},
    text: { default: "" },
    disabled: { type: Boolean, default: !1 },
    precision: { default: 2 },
    value: {},
    showWord: { type: Boolean, default: !0 },
    placeholder: {}
  },
  emits: ["update:modelValue", "change", "blur", "focus"],
  setup(e, { emit: t }) {
    const a = e, r = t, n = se(), i = (d) => {
      const v = d.key;
      return ["e", "+", "-"].includes(v) ? (d.returnValue = !1, !1) : !0;
    }, s = (d) => {
      n.value = d, d > a.max ? r("update:modelValue", a.max) : r("update:modelValue", d);
    }, o = (d) => {
      r("change", d);
    }, u = (d) => {
      r("blur", d);
    }, l = (d) => {
      r("focus", d);
    };
    nt(
      () => a.value,
      (d) => {
        n.value = d;
      },
      { deep: !0, immediate: !0 }
    );
    const c = (d) => {
      var v = a.max;
      if (!d || d > v)
        return "";
      var p = "零", h = "壹", x = "贰", y = "叁", g = "肆", m = "伍", C = "陆", S = "柒", _ = "捌", T = "玖", D = "拾", P = "佰", I = "仟", B = "万", R = "亿", F = "元", U = "角", M = "分", ee = "整", q, fe, Ee, xe, O, H, $, K, be, pe, Ne, le, X, te;
      if (d = String(d), xe = String(d).split("."), xe.length > 1 ? (q = xe[0], fe = xe[1], fe = fe.substr(0, 2)) : (q = xe[0], fe = ""), O = [
        p,
        h,
        x,
        y,
        g,
        m,
        C,
        S,
        _,
        T
      ], H = ["", D, P, I], $ = ["", B, R], K = [U, M], Ee = "", Number(q) > 0) {
        for (be = 0, pe = 0; pe < q.length; pe++)
          Ne = q.length - pe - 1, le = q.substr(pe, 1), X = Ne / 4, te = Ne % 4, le == "0" ? be++ : (be > 0 && (Ee += O[0]), be = 0, Ee += O[Number(le)] + H[te]), te == 0 && be < 4 && (Ee += $[X], be = 0);
        Ee += F;
      }
      if (fe != "")
        for (pe = 0; pe < fe.length; pe++)
          le = fe.substr(pe, 1), le != "0" && (Ee += O[Number(le)] + K[pe]);
      return Ee == "" && (Ee = p + F), fe == "" && (Ee += ee), Ee;
    }, f = {
      mounted: function(d) {
        const v = d.getElementsByTagName("input")[0], p = document.createElement("input");
        p.type = "text", p.autocomplete = "off", p.classList.add("el-input__inner"), v.insertAdjacentElement("afterend", p);
        const h = v.value;
        if (h) {
          const x = parseFloat(h.replace(/,/g, ""));
          p.value = x.toLocaleString("zh", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
        }
        p.style.display = "block", v.style.display = "none", p.onfocus = () => {
          p.style.display = "none", v.style.display = "block", v.focus();
        }, v.onblur = () => {
          v.style.display = "none", p.style.display = "block";
        }, v.addEventListener("input", () => {
          const x = v.value.replace(/,/g, "");
          if (x) {
            const y = parseFloat(x) > a.max ? a.max : parseFloat(x);
            p.value = y.toLocaleString("zh", {
              minimumFractionDigits: 2,
              maximumFractionDigits: 2
            });
          } else
            p.value = "";
        });
      },
      updated: function(d) {
        if (d.tagName.toLocaleUpperCase() !== "INPUT") {
          const v = d.getElementsByTagName("input")[0], p = d.getElementsByTagName("input")[1];
          d.classList.value.includes("is-disabled") ? p.setAttribute("disabled", !0) : p.removeAttribute("disabled");
          const x = v.value.replace(/,/g, "");
          if (x) {
            const y = parseFloat(x) > a.max ? a.max : parseFloat(x);
            p.value = y.toLocaleString("zh", {
              minimumFractionDigits: 2,
              maximumFractionDigits: 2
            });
          } else
            p.value = "";
        }
      }
    };
    return (d, v) => {
      const p = Q("el-input-number");
      return z(), he("div", bf, [
        aa(Z(p, {
          modelValue: n.value,
          "onUpdate:modelValue": v[0] || (v[0] = (h) => n.value = h),
          step: a.step,
          min: a.min,
          max: a.max,
          disabled: a.disabled,
          precision: a.precision,
          controls: !1,
          placeholder: a.placeholder,
          onInput: s,
          onChange: o,
          onFocus: l,
          onBlur: u,
          onKeydown: i
        }, null, 8, ["modelValue", "step", "min", "max", "disabled", "precision", "placeholder"]), [
          [f]
        ]),
        $e("span", Sf, We(d.text), 1),
        d.showWord ? (z(), he("div", Af, We(c(n.value)), 1)) : qe("", !0)
      ]);
    };
  }
}), Bf = /* @__PURE__ */ Or(_f, [["__scopeId", "data-v-ac677ce7"]]);
var Tf = typeof global == "object" && global && global.Object === Object && global, If = typeof self == "object" && self && self.Object === Object && self, bn = Tf || If || Function("return this")(), ia = bn.Symbol, Yo = Object.prototype, wf = Yo.hasOwnProperty, Df = Yo.toString, ya = ia ? ia.toStringTag : void 0;
function Nf(e) {
  var t = wf.call(e, ya), a = e[ya];
  try {
    e[ya] = void 0;
    var r = !0;
  } catch {
  }
  var n = Df.call(e);
  return r && (t ? e[ya] = a : delete e[ya]), n;
}
var Rf = Object.prototype, kf = Rf.toString;
function Ff(e) {
  return kf.call(e);
}
var Lf = "[object Null]", Pf = "[object Undefined]", rs = ia ? ia.toStringTag : void 0;
function Qo(e) {
  return e == null ? e === void 0 ? Pf : Lf : rs && rs in Object(e) ? Nf(e) : Ff(e);
}
function Uf(e) {
  return e != null && typeof e == "object";
}
var Of = "[object Symbol]";
function Sn(e) {
  return typeof e == "symbol" || Uf(e) && Qo(e) == Of;
}
function Vf(e, t) {
  for (var a = -1, r = e == null ? 0 : e.length, n = Array(r); ++a < r; )
    n[a] = t(e[a], a, e);
  return n;
}
var c0 = Array.isArray, Mf = 1 / 0, as = ia ? ia.prototype : void 0, ns = as ? as.toString : void 0;
function Xo(e) {
  if (typeof e == "string")
    return e;
  if (c0(e))
    return Vf(e, Xo) + "";
  if (Sn(e))
    return ns ? ns.call(e) : "";
  var t = e + "";
  return t == "0" && 1 / e == -Mf ? "-0" : t;
}
var Kf = /\s/;
function Hf(e) {
  for (var t = e.length; t-- && Kf.test(e.charAt(t)); )
    ;
  return t;
}
var zf = /^\s+/;
function $f(e) {
  return e && e.slice(0, Hf(e) + 1).replace(zf, "");
}
function ka(e) {
  var t = typeof e;
  return e != null && (t == "object" || t == "function");
}
var is = NaN, qf = /^[-+]0x[0-9a-f]+$/i, jf = /^0b[01]+$/i, Gf = /^0o[0-7]+$/i, Wf = parseInt;
function ss(e) {
  if (typeof e == "number")
    return e;
  if (Sn(e))
    return is;
  if (ka(e)) {
    var t = typeof e.valueOf == "function" ? e.valueOf() : e;
    e = ka(t) ? t + "" : t;
  }
  if (typeof e != "string")
    return e === 0 ? e : +e;
  e = $f(e);
  var a = jf.test(e);
  return a || Gf.test(e) ? Wf(e.slice(2), a ? 2 : 8) : qf.test(e) ? is : +e;
}
var Yf = "[object AsyncFunction]", Qf = "[object Function]", Xf = "[object GeneratorFunction]", Zf = "[object Proxy]";
function Jf(e) {
  if (!ka(e))
    return !1;
  var t = Qo(e);
  return t == Qf || t == Xf || t == Yf || t == Zf;
}
var Mn = bn["__core-js_shared__"], os = function() {
  var e = /[^.]+$/.exec(Mn && Mn.keys && Mn.keys.IE_PROTO || "");
  return e ? "Symbol(src)_1." + e : "";
}();
function ed(e) {
  return !!os && os in e;
}
var td = Function.prototype, rd = td.toString;
function ad(e) {
  if (e != null) {
    try {
      return rd.call(e);
    } catch {
    }
    try {
      return e + "";
    } catch {
    }
  }
  return "";
}
var nd = /[\\^$.*+?()[\]{}|]/g, id = /^\[object .+?Constructor\]$/, sd = Function.prototype, od = Object.prototype, ld = sd.toString, cd = od.hasOwnProperty, ud = RegExp(
  "^" + ld.call(cd).replace(nd, "\\$&").replace(/hasOwnProperty|(function).*?(?=\\\()| for .+?(?=\\\])/g, "$1.*?") + "$"
);
function fd(e) {
  if (!ka(e) || ed(e))
    return !1;
  var t = Jf(e) ? ud : id;
  return t.test(ad(e));
}
function dd(e, t) {
  return e == null ? void 0 : e[t];
}
function Zo(e, t) {
  var a = dd(e, t);
  return fd(a) ? a : void 0;
}
function pd(e, t) {
  return e === t || e !== e && t !== t;
}
var hd = /\.|\[(?:[^[\]]*|(["'])(?:(?!\1)[^\\]|\\.)*?\1)\]/, vd = /^\w*$/;
function xd(e, t) {
  if (c0(e))
    return !1;
  var a = typeof e;
  return a == "number" || a == "symbol" || a == "boolean" || e == null || Sn(e) ? !0 : vd.test(e) || !hd.test(e) || t != null && e in Object(t);
}
var Fa = Zo(Object, "create");
function gd() {
  this.__data__ = Fa ? Fa(null) : {}, this.size = 0;
}
function yd(e) {
  var t = this.has(e) && delete this.__data__[e];
  return this.size -= t ? 1 : 0, t;
}
var md = "__lodash_hash_undefined__", Cd = Object.prototype, Ed = Cd.hasOwnProperty;
function bd(e) {
  var t = this.__data__;
  if (Fa) {
    var a = t[e];
    return a === md ? void 0 : a;
  }
  return Ed.call(t, e) ? t[e] : void 0;
}
var Sd = Object.prototype, Ad = Sd.hasOwnProperty;
function _d(e) {
  var t = this.__data__;
  return Fa ? t[e] !== void 0 : Ad.call(t, e);
}
var Bd = "__lodash_hash_undefined__";
function Td(e, t) {
  var a = this.__data__;
  return this.size += this.has(e) ? 0 : 1, a[e] = Fa && t === void 0 ? Bd : t, this;
}
function Ur(e) {
  var t = -1, a = e == null ? 0 : e.length;
  for (this.clear(); ++t < a; ) {
    var r = e[t];
    this.set(r[0], r[1]);
  }
}
Ur.prototype.clear = gd;
Ur.prototype.delete = yd;
Ur.prototype.get = bd;
Ur.prototype.has = _d;
Ur.prototype.set = Td;
function Id() {
  this.__data__ = [], this.size = 0;
}
function An(e, t) {
  for (var a = e.length; a--; )
    if (pd(e[a][0], t))
      return a;
  return -1;
}
var wd = Array.prototype, Dd = wd.splice;
function Nd(e) {
  var t = this.__data__, a = An(t, e);
  if (a < 0)
    return !1;
  var r = t.length - 1;
  return a == r ? t.pop() : Dd.call(t, a, 1), --this.size, !0;
}
function Rd(e) {
  var t = this.__data__, a = An(t, e);
  return a < 0 ? void 0 : t[a][1];
}
function kd(e) {
  return An(this.__data__, e) > -1;
}
function Fd(e, t) {
  var a = this.__data__, r = An(a, e);
  return r < 0 ? (++this.size, a.push([e, t])) : a[r][1] = t, this;
}
function fa(e) {
  var t = -1, a = e == null ? 0 : e.length;
  for (this.clear(); ++t < a; ) {
    var r = e[t];
    this.set(r[0], r[1]);
  }
}
fa.prototype.clear = Id;
fa.prototype.delete = Nd;
fa.prototype.get = Rd;
fa.prototype.has = kd;
fa.prototype.set = Fd;
var Ld = Zo(bn, "Map");
function Pd() {
  this.size = 0, this.__data__ = {
    hash: new Ur(),
    map: new (Ld || fa)(),
    string: new Ur()
  };
}
function Ud(e) {
  var t = typeof e;
  return t == "string" || t == "number" || t == "symbol" || t == "boolean" ? e !== "__proto__" : e === null;
}
function _n(e, t) {
  var a = e.__data__;
  return Ud(t) ? a[typeof t == "string" ? "string" : "hash"] : a.map;
}
function Od(e) {
  var t = _n(this, e).delete(e);
  return this.size -= t ? 1 : 0, t;
}
function Vd(e) {
  return _n(this, e).get(e);
}
function Md(e) {
  return _n(this, e).has(e);
}
function Kd(e, t) {
  var a = _n(this, e), r = a.size;
  return a.set(e, t), this.size += a.size == r ? 0 : 1, this;
}
function Vr(e) {
  var t = -1, a = e == null ? 0 : e.length;
  for (this.clear(); ++t < a; ) {
    var r = e[t];
    this.set(r[0], r[1]);
  }
}
Vr.prototype.clear = Pd;
Vr.prototype.delete = Od;
Vr.prototype.get = Vd;
Vr.prototype.has = Md;
Vr.prototype.set = Kd;
var Hd = "Expected a function";
function u0(e, t) {
  if (typeof e != "function" || t != null && typeof t != "function")
    throw new TypeError(Hd);
  var a = function() {
    var r = arguments, n = t ? t.apply(this, r) : r[0], i = a.cache;
    if (i.has(n))
      return i.get(n);
    var s = e.apply(this, r);
    return a.cache = i.set(n, s) || i, s;
  };
  return a.cache = new (u0.Cache || Vr)(), a;
}
u0.Cache = Vr;
var zd = 500;
function $d(e) {
  var t = u0(e, function(r) {
    return a.size === zd && a.clear(), r;
  }), a = t.cache;
  return t;
}
var qd = /[^.[\]]+|\[(?:(-?\d+(?:\.\d+)?)|(["'])((?:(?!\2)[^\\]|\\.)*?)\2)\]|(?=(?:\.|\[\])(?:\.|\[\]|$))/g, jd = /\\(\\)?/g, Gd = $d(function(e) {
  var t = [];
  return e.charCodeAt(0) === 46 && t.push(""), e.replace(qd, function(a, r, n, i) {
    t.push(n ? i.replace(jd, "$1") : r || a);
  }), t;
});
function Wd(e) {
  return e == null ? "" : Xo(e);
}
function Yd(e, t) {
  return c0(e) ? e : xd(e, t) ? [e] : Gd(Wd(e));
}
var Qd = 1 / 0;
function Xd(e) {
  if (typeof e == "string" || Sn(e))
    return e;
  var t = e + "";
  return t == "0" && 1 / e == -Qd ? "-0" : t;
}
function Zd(e, t) {
  t = Yd(t, e);
  for (var a = 0, r = t.length; e != null && a < r; )
    e = e[Xd(t[a++])];
  return a && a == r ? e : void 0;
}
function Jd(e, t, a) {
  var r = e == null ? void 0 : Zd(e, t);
  return r === void 0 ? a : r;
}
var Kn = function() {
  return bn.Date.now();
}, ep = "Expected a function", tp = Math.max, rp = Math.min;
function ap(e, t, a) {
  var r, n, i, s, o, u, l = 0, c = !1, f = !1, d = !0;
  if (typeof e != "function")
    throw new TypeError(ep);
  t = ss(t) || 0, ka(a) && (c = !!a.leading, f = "maxWait" in a, i = f ? tp(ss(a.maxWait) || 0, t) : i, d = "trailing" in a ? !!a.trailing : d);
  function v(_) {
    var T = r, D = n;
    return r = n = void 0, l = _, s = e.apply(D, T), s;
  }
  function p(_) {
    return l = _, o = setTimeout(y, t), c ? v(_) : s;
  }
  function h(_) {
    var T = _ - u, D = _ - l, P = t - T;
    return f ? rp(P, i - D) : P;
  }
  function x(_) {
    var T = _ - u, D = _ - l;
    return u === void 0 || T >= t || T < 0 || f && D >= i;
  }
  function y() {
    var _ = Kn();
    if (x(_))
      return g(_);
    o = setTimeout(y, h(_));
  }
  function g(_) {
    return o = void 0, d && r ? v(_) : (r = n = void 0, s);
  }
  function m() {
    o !== void 0 && clearTimeout(o), l = 0, r = u = n = o = void 0;
  }
  function C() {
    return o === void 0 ? s : g(Kn());
  }
  function S() {
    var _ = Kn(), T = x(_);
    if (r = arguments, n = this, u = _, T) {
      if (o === void 0)
        return p(u);
      if (f)
        return clearTimeout(o), o = setTimeout(y, t), v(u);
    }
    return o === void 0 && (o = setTimeout(y, t)), s;
  }
  return S.cancel = m, S.flush = C, S;
}
const np = (e, t = {}, a = !0, r, n) => {
  const i = or({
    // 表格数据
    tableData: [],
    // 分页数据
    pageable: {
      // 当前页数
      current: 1,
      // 每页显示条数
      size: 50,
      // 总条数
      total: 0
    },
    // 查询参数(只包括查询)
    searchParam: {},
    // 初始化默认的查询参数
    searchInitParam: {},
    // 总参数(包含分页和查询参数)
    totalParam: {}
  }), s = He({
    get: () => ({
      current: i.pageable.current,
      size: i.pageable.size
    }),
    set: (p) => {
      console.log("我是分页更新之后的值", p);
    }
  }), o = async () => {
    if (e)
      try {
        Object.assign(
          i.totalParam,
          t,
          a ? s.value : {}
        );
        let { data: p, page: h, total: x } = await e({
          ...i.searchInitParam,
          ...i.totalParam
        });
        if (r && (p = r(p)), i.tableData = p, a)
          if (x) {
            const { current: y, size: g } = i.pageable;
            l({ current: y, size: g, total: x });
          } else {
            const { current: y, size: g, total: m } = h;
            l({ current: y, size: g, total: m });
          }
      } catch (p) {
        n && n(p);
      }
  }, u = () => {
    i.totalParam = {};
    let p = {};
    for (let h in i.searchParam)
      (i.searchParam[h] || i.searchParam[h] === !1 || i.searchParam[h] === 0) && (p[h] = i.searchParam[h]);
    Object.assign(
      i.totalParam,
      p,
      a ? s.value : {}
    );
  }, l = (p) => {
    Object.assign(i.pageable, p);
  }, c = () => {
    i.pageable.current = 1, u(), o();
  }, f = () => {
    i.pageable.current = 1, i.searchParam = { ...i.searchInitParam }, u(), o();
  }, d = (p) => {
    i.pageable.current = 1, i.pageable.size = p, o();
  }, v = (p) => {
    i.pageable.current = p, o();
  };
  return {
    ...Ni(i),
    getTableList: o,
    search: c,
    reset: f,
    handleSizeChange: d,
    handleCurrentChange: v,
    updatedTotalParam: u
  };
}, ip = (e = "id") => {
  const t = se(!1), a = se([]), r = He(() => {
    let i = [];
    return a.value.forEach((s) => i.push(s[e])), i;
  });
  return {
    isSelected: t,
    selectedList: a,
    selectedListIds: r,
    selectionChange: (i) => {
      i.length ? t.value = !0 : t.value = !1, a.value = i;
    }
  };
};
function sp(e) {
  return e && Array.isArray(e);
}
function op() {
  const t = (/* @__PURE__ */ new Date()).getHours();
  return t >= 6 && t <= 10 ? "早上好 ⛅，新的一天就要开始啦，起来后来杯温水或者咖啡，动力满满喔 ~" : t >= 10 && t <= 14 ? "中午好 🌞，饭前喝口水，然后去吃最爱吃的饭，接着适当休息放松喔 ~" : t >= 14 && t <= 18 ? "下午好 🌞，繁忙的下午也不要忘记喝水、休息喔 ~" : t >= 18 && t <= 24 ? "晚上好 🌛，休息啦，先吃晚饭，然后出去散散步，或者锻炼身体喔 ~" : t >= 0 && t <= 6 ? "凌晨好 🌛，没想到你那么努力，未来的美好悄然走向你，不过还是希望你早点休息，放下手上的事情，美滋滋的睡个好觉喔 ~" : "";
}
function lp(e) {
  return sp(e) ? e.length ? e.join(" / ") : "--" : e ?? "--";
}
function Hn(e, t) {
  return t.includes(".") ? (t.split(".").forEach((a) => e = e[a] ?? "--"), e) : e[t] ?? "--";
}
function Rr(e) {
  const t = e.split(".");
  return t.length == 1 ? e : t[t.length - 1];
}
function ls(e, t, a, r) {
  const n = (a == null ? void 0 : a.value) ?? "value", i = (a == null ? void 0 : a.label) ?? "label", s = (a == null ? void 0 : a.children) ?? "children";
  let o = {};
  return Array.isArray(t) && (o = Jo(t, e, n, s)), r == "tag" ? o != null && o.tagType ? o.tagType : "" : o ? o[i] : "--";
}
function Jo(e, t, a, r) {
  return e.reduce((n, i) => {
    if (n) return n;
    if (i[a] === t) return i;
    if (i[r])
      return Jo(i[r], t, a, r);
  }, null);
}
const cp = /* @__PURE__ */ Ue({
  __name: "SearchFormItem",
  props: {
    column: {},
    searchParam: {}
  },
  setup(e) {
    const t = e, a = He(() => t.searchParam), r = He(() => {
      var l, c, f;
      return {
        label: ((l = t.column.fieldNames) == null ? void 0 : l.label) ?? "label",
        value: ((c = t.column.fieldNames) == null ? void 0 : c.value) ?? "value",
        children: ((f = t.column.fieldNames) == null ? void 0 : f.children) ?? "children"
      };
    }), n = dr("enumMap", se(/* @__PURE__ */ new Map())), i = He(() => {
      var c;
      let l = n.value.get(t.column.prop);
      return l ? (((c = t.column.search) == null ? void 0 : c.el) === "select-v2" && t.column.fieldNames && (l = l.map((f) => ({
        ...f,
        label: f[r.value.label],
        value: f[r.value.value]
      }))), l) : [];
    }), s = He(() => {
      var p, h;
      const l = r.value.label, c = r.value.value, f = r.value.children, d = (p = t.column.search) == null ? void 0 : p.el;
      let v = ((h = t.column.search) == null ? void 0 : h.props) ?? {};
      return d === "tree-select" && (v = {
        ...v,
        props: { ...v.props, label: l, children: f },
        nodeKey: c
      }), d === "cascader" && (v = {
        ...v,
        props: { ...v.props, label: l, value: c, children: f }
      }), v;
    }), o = He(() => {
      var f, d, v, p, h, x, y;
      const l = t.column.search;
      return ["datetimerange", "daterange", "monthrange"].includes(
        (f = l == null ? void 0 : l.props) == null ? void 0 : f.type
      ) || (d = l == null ? void 0 : l.props) != null && d.isRange ? {
        rangeSeparator: ((v = l == null ? void 0 : l.props) == null ? void 0 : v.rangeSeparator) ?? "至",
        startPlaceholder: ((p = l == null ? void 0 : l.props) == null ? void 0 : p.startPlaceholder) ?? "开始时间",
        endPlaceholder: ((h = l == null ? void 0 : l.props) == null ? void 0 : h.endPlaceholder) ?? "结束时间"
      } : { placeholder: ((x = l == null ? void 0 : l.props) == null ? void 0 : x.placeholder) ?? ((y = l == null ? void 0 : l.el) != null && y.includes("input") ? "请输入" : "请选择") };
    }), u = He(() => {
      var c;
      const l = t.column.search;
      return ((c = l == null ? void 0 : l.props) == null ? void 0 : c.clearable) ?? ((l == null ? void 0 : l.defaultValue) == null || (l == null ? void 0 : l.defaultValue) == null);
    });
    return (l, c) => {
      var f, d, v, p, h, x;
      return z(), ve(Na(((f = l.column.search) == null ? void 0 : f.render) ?? `el-${(d = l.column.search) == null ? void 0 : d.el}`), nr({
        ...s.value,
        ...o.value,
        searchParam: a.value,
        clearable: u.value
      }, {
        modelValue: a.value[((v = l.column.search) == null ? void 0 : v.key) ?? ye(Rr)(l.column.prop)],
        "onUpdate:modelValue": c[0] || (c[0] = (y) => {
          var g;
          return a.value[((g = l.column.search) == null ? void 0 : g.key) ?? ye(Rr)(l.column.prop)] = y;
        }),
        modelModifiers: { trim: !0 },
        data: ((p = l.column.search) == null ? void 0 : p.el) === "tree-select" ? i.value : [],
        options: ["cascader", "select-v2"].includes((h = l.column.search) == null ? void 0 : h.el) ? i.value : []
      }), n0({
        default: re(() => {
          var y;
          return [
            ((y = l.column.search) == null ? void 0 : y.el) === "select" ? (z(!0), he(it, { key: 0 }, St(i.value, (g, m) => (z(), ve(Na("el-option"), {
              key: m,
              label: g[r.value.label],
              value: g[r.value.value]
            }, null, 8, ["label", "value"]))), 128)) : Rt(l.$slots, "default", { key: 1 })
          ];
        }),
        _: 2
      }, [
        ((x = l.column.search) == null ? void 0 : x.el) === "cascader" ? {
          name: "default",
          fn: re(({ data: y }) => [
            $e("span", null, We(y[r.value.label]), 1)
          ]),
          key: "0"
        } : void 0
      ]), 1040, ["modelValue", "data", "options"]);
    };
  }
}), up = /* @__PURE__ */ Ue({
  __name: "index",
  props: {
    cols: { default: () => ({ xs: 1, sm: 2, md: 2, lg: 3, xl: 4 }) },
    collapsed: { type: Boolean, default: !1 },
    collapsedRows: { default: 1 },
    gap: { default: 0 }
  },
  setup(e, { expose: t }) {
    const a = e;
    gc(() => a.collapsed && u()), yt(() => {
      r({ target: { innerWidth: window.innerWidth } }), window.addEventListener("resize", r);
    }), yc(() => {
      r({ target: { innerWidth: window.innerWidth } }), window.addEventListener("resize", r);
    }), mc(() => {
      window.removeEventListener("resize", r);
    }), Cc(() => {
      window.removeEventListener("resize", r);
    });
    const r = (f) => {
      let d = f.target.innerWidth;
      switch (!!d) {
        case d < 768:
          n.value = "xs";
          break;
        case (d >= 768 && d < 992):
          n.value = "sm";
          break;
        case (d >= 992 && d < 1200):
          n.value = "md";
          break;
        case (d >= 1200 && d < 1920):
          n.value = "lg";
          break;
        case d >= 1920:
          n.value = "xl";
          break;
      }
    };
    Ea("gap", Array.isArray(a.gap) ? a.gap[0] : a.gap);
    let n = se("xl");
    Ea("breakPoint", n);
    const i = se(-1);
    Ea("shouldHiddenIndex", i);
    const s = He(() => typeof a.cols == "object" ? a.cols[n.value] ?? a.cols : a.cols);
    Ea("cols", s);
    const o = Io().default(), u = () => {
      var p, h, x, y;
      let f = [], d = null;
      o.forEach((g) => {
        var m;
        typeof g.type == "object" && g.type.__name === "GridItem" && ((m = g.props) == null ? void 0 : m.suffix) !== void 0 && (d = g), typeof g.type == "symbol" && Array.isArray(g.children) && f.push(...g.children);
      });
      let v = 0;
      d && (v = (((p = d.props[n.value]) == null ? void 0 : p.span) ?? ((h = d.props) == null ? void 0 : h.span) ?? 1) + (((x = d.props[n.value]) == null ? void 0 : x.offset) ?? ((y = d.props) == null ? void 0 : y.offset) ?? 0));
      try {
        let g = !1;
        f.reduce((m = 0, C, S) => {
          var _, T, D, P;
          if (m += (((_ = C.props[n.value]) == null ? void 0 : _.span) ?? ((T = C.props) == null ? void 0 : T.span) ?? 1) + (((D = C.props[n.value]) == null ? void 0 : D.offset) ?? ((P = C.props) == null ? void 0 : P.offset) ?? 0), Number(m) > a.collapsedRows * s.value - v)
            throw i.value = S, g = !0, "find it";
          return m;
        }, 0), g || (i.value = -1);
      } catch {
      }
    };
    nt(
      () => n.value,
      () => {
        a.collapsed && u();
      }
    ), nt(
      () => a.collapsed,
      (f) => {
        if (f) return u();
        i.value = -1;
      }
    );
    const l = He(() => typeof a.gap == "number" ? `${a.gap}px` : Array.isArray(a.gap) ? `${a.gap[1]}px ${a.gap[0]}px` : "unset"), c = He(() => ({
      display: "grid",
      gridGap: l.value,
      gridTemplateColumns: `repeat(${s.value}, minmax(0, 1fr))`
    }));
    return t({ breakPoint: n }), (f, d) => (z(), he("div", {
      style: mn(c.value)
    }, [
      Rt(f.$slots, "default")
    ], 4));
  }
}), cs = /* @__PURE__ */ Ue({
  __name: "GridItem",
  props: {
    offset: { default: 0 },
    span: { default: 1 },
    suffix: { type: Boolean, default: !1 },
    xs: { default: void 0 },
    sm: { default: void 0 },
    md: { default: void 0 },
    lg: { default: void 0 },
    xl: { default: void 0 }
  },
  setup(e) {
    const t = e, a = Ec(), r = se(!0), n = dr("breakPoint", se("xl")), i = dr("shouldHiddenIndex", se(-1));
    nt(
      () => [i.value, n.value],
      (l) => {
        a.index && (r.value = !(l[0] !== -1 && parseInt(a.index) >= Number(l[0])));
      },
      { immediate: !0 }
    );
    const s = dr("gap", 0), o = dr("cols", se(4)), u = He(() => {
      var f, d;
      let l = ((f = t[n.value]) == null ? void 0 : f.span) ?? t.span, c = ((d = t[n.value]) == null ? void 0 : d.offset) ?? t.offset;
      return t.suffix ? {
        gridColumnStart: o.value - l - c + 1,
        gridColumnEnd: `span ${l + c}`,
        marginLeft: c !== 0 ? `calc(((100% + ${s}px) / ${l + c}) * ${c})` : "unset"
      } : {
        gridColumn: `span ${l + c > o.value ? o.value : l + c}/span ${l + c > o.value ? o.value : l + c}`,
        marginLeft: c !== 0 ? `calc(((100% + ${s}px) / ${l + c}) * ${c})` : "unset"
      };
    });
    return (l, c) => aa((z(), he("div", {
      style: mn(u.value)
    }, [
      Rt(l.$slots, "default")
    ], 4)), [
      [i0, r.value]
    ]);
  }
}), fp = {
  key: 0,
  class: "card table-search"
}, dp = /* @__PURE__ */ $e("i", {
  class: /* @__PURE__ */ a0("iconfont icon-yiwen")
}, null, -1), pp = /* @__PURE__ */ $e("span", null, " :", -1), hp = { class: "operation" }, vp = /* @__PURE__ */ Ue({
  __name: "index",
  props: {
    columns: { default: () => [] },
    searchParam: { default: () => ({}) },
    searchCol: {},
    search: {},
    reset: {}
  },
  setup(e) {
    const t = e, a = (o) => {
      var u, l, c, f, d, v, p;
      return {
        span: (u = o.search) == null ? void 0 : u.span,
        offset: ((l = o.search) == null ? void 0 : l.offset) ?? 0,
        xs: (c = o.search) == null ? void 0 : c.xs,
        sm: (f = o.search) == null ? void 0 : f.sm,
        md: (d = o.search) == null ? void 0 : d.md,
        lg: (v = o.search) == null ? void 0 : v.lg,
        xl: (p = o.search) == null ? void 0 : p.xl
      };
    }, r = se(!0), n = se(), i = He(() => {
      var o;
      return (o = n.value) == null ? void 0 : o.breakPoint;
    }), s = He(() => {
      let o = !1;
      return t.columns.reduce((u, l) => {
        var c, f, d, v;
        return u += (((c = l.search[i.value]) == null ? void 0 : c.span) ?? ((f = l.search) == null ? void 0 : f.span) ?? 1) + (((d = l.search[i.value]) == null ? void 0 : d.offset) ?? ((v = l.search) == null ? void 0 : v.offset) ?? 0), typeof t.searchCol != "number" ? u >= t.searchCol[i.value] && (o = !0) : u >= t.searchCol && (o = !0), u;
      }, 0), o;
    });
    return (o, u) => {
      const l = Q("el-tooltip"), c = Q("el-space"), f = Q("el-form-item"), d = Q("el-button"), v = Q("el-icon"), p = Q("el-form");
      return o.columns.length ? (z(), he("div", fp, [
        Z(p, {
          ref: "formRef",
          model: o.searchParam,
          onSubmit: u[1] || (u[1] = bc(() => {
          }, ["prevent"]))
        }, {
          default: re(() => [
            Z(up, {
              ref_key: "gridRef",
              ref: n,
              collapsed: r.value,
              gap: [20, 0],
              cols: o.searchCol
            }, {
              default: re(() => [
                (z(!0), he(it, null, St(o.columns, (h, x) => (z(), ve(cs, nr({
                  key: h.prop,
                  ref_for: !0
                }, a(h), { index: x }), {
                  default: re(() => [
                    Z(f, null, {
                      label: re(() => [
                        Z(c, { size: 4 }, {
                          default: re(() => {
                            var y, g, m;
                            return [
                              $e("span", null, We(`${((y = h.search) == null ? void 0 : y.label) ?? h.label}`), 1),
                              (g = h.search) != null && g.tooltip ? (z(), ve(l, {
                                key: 0,
                                effect: "dark",
                                content: (m = h.search) == null ? void 0 : m.tooltip,
                                placement: "top"
                              }, {
                                default: re(() => [
                                  dp
                                ]),
                                _: 2
                              }, 1032, ["content"])) : qe("", !0)
                            ];
                          }),
                          _: 2
                        }, 1024),
                        pp
                      ]),
                      default: re(() => [
                        Z(cp, {
                          column: h,
                          "search-param": o.searchParam
                        }, null, 8, ["column", "search-param"])
                      ]),
                      _: 2
                    }, 1024)
                  ]),
                  _: 2
                }, 1040, ["index"]))), 128)),
                Z(cs, { suffix: "" }, {
                  default: re(() => [
                    $e("div", hp, [
                      Z(d, {
                        type: "primary",
                        icon: ye(Ua),
                        onClick: o.search,
                        "native-type": "submit"
                      }, {
                        default: re(() => [
                          rt(" 搜索 ")
                        ]),
                        _: 1
                      }, 8, ["icon", "onClick"]),
                      Z(d, {
                        icon: ye(Cn),
                        onClick: o.reset
                      }, {
                        default: re(() => [
                          rt(" 重置 ")
                        ]),
                        _: 1
                      }, 8, ["icon", "onClick"]),
                      s.value ? (z(), ve(d, {
                        key: 0,
                        type: "primary",
                        link: "",
                        class: "search-isOpen",
                        onClick: u[0] || (u[0] = (h) => r.value = !r.value)
                      }, {
                        default: re(() => [
                          rt(We(r.value ? "展开" : "收起") + " ", 1),
                          Z(v, { class: "el-icon--right" }, {
                            default: re(() => [
                              (z(), ve(Na(r.value ? ye(Fc) : ye(Lc))))
                            ]),
                            _: 1
                          })
                        ]),
                        _: 1
                      })) : qe("", !0)
                    ])
                  ]),
                  _: 1
                })
              ]),
              _: 1
            }, 8, ["collapsed", "cols"])
          ]),
          _: 1
        }, 8, ["model"])
      ])) : qe("", !0);
    };
  }
}), xp = /* @__PURE__ */ Ue({
  __name: "Pagination",
  props: {
    pageable: {},
    handleSizeChange: { type: Function },
    handleCurrentChange: { type: Function }
  },
  setup(e) {
    return (t, a) => {
      const r = Q("el-pagination");
      return z(), ve(r, {
        background: !0,
        "current-page": t.pageable.current,
        "page-size": t.pageable.size,
        "page-sizes": [10, 25, 50, 100, 500, 1e3],
        total: t.pageable.total,
        layout: "total, sizes, prev, pager, next, jumper",
        onSizeChange: t.handleSizeChange,
        onCurrentChange: t.handleCurrentChange
      }, null, 8, ["current-page", "page-size", "total", "onSizeChange", "onCurrentChange"]);
    };
  }
}), gp = (e) => (Sc("data-v-0ee82156"), e = e(), Ac(), e), yp = { class: "table-main" }, mp = /* @__PURE__ */ gp(() => /* @__PURE__ */ $e("div", { class: "table-empty" }, [
  /* @__PURE__ */ $e("div", null, "暂无可配置列")
], -1)), Cp = /* @__PURE__ */ Ue({
  __name: "ColSetting",
  props: {
    colSetting: {}
  },
  setup(e, { expose: t }) {
    const a = se(!1);
    return t({
      openColSetting: () => {
        a.value = !0;
      }
    }), (n, i) => {
      const s = Q("el-table-column"), o = Q("el-switch"), u = Q("el-table"), l = Q("el-drawer");
      return z(), ve(l, {
        modelValue: a.value,
        "onUpdate:modelValue": i[0] || (i[0] = (c) => a.value = c),
        title: "列设置",
        size: "450px"
      }, {
        default: re(() => [
          $e("div", yp, [
            Z(u, {
              data: n.colSetting,
              border: !0,
              "row-key": "prop",
              "default-expand-all": "",
              "tree-props": { children: "_children" }
            }, {
              empty: re(() => [
                mp
              ]),
              default: re(() => [
                Z(s, {
                  prop: "label",
                  align: "center",
                  label: "列名"
                }),
                Z(s, {
                  prop: "isShow",
                  align: "center",
                  label: "显示"
                }, {
                  default: re((c) => [
                    Z(o, {
                      modelValue: c.row.isShow,
                      "onUpdate:modelValue": (f) => c.row.isShow = f
                    }, null, 8, ["modelValue", "onUpdate:modelValue"])
                  ]),
                  _: 1
                }),
                Z(s, {
                  prop: "sortable",
                  align: "center",
                  label: "排序"
                }, {
                  default: re((c) => [
                    Z(o, {
                      modelValue: c.row.sortable,
                      "onUpdate:modelValue": (f) => c.row.sortable = f
                    }, null, 8, ["modelValue", "onUpdate:modelValue"])
                  ]),
                  _: 1
                })
              ]),
              _: 1
            }, 8, ["data"])
          ])
        ]),
        _: 1
      }, 8, ["modelValue"]);
    };
  }
}), Ep = /* @__PURE__ */ Or(Cp, [["__scopeId", "data-v-0ee82156"]]);
function bp(e) {
  return typeof e == "function" || Object.prototype.toString.call(e) === "[object Object]" && !Tc(e);
}
const Sp = /* @__PURE__ */ Ue({
  __name: "TableColumn",
  props: {
    column: {}
  },
  setup(e) {
    const t = Io(), a = dr("enumMap", se(/* @__PURE__ */ new Map())), r = (o, u) => a.value.get(o.prop) && o.isFilterEnum ? ls(Hn(u.row, o.prop), a.value.get(o.prop), o.fieldNames) : lp(Hn(u.row, o.prop)), n = (o, u) => ls(Hn(u.row, o.prop), a.value.get(o.prop), o.fieldNames, "tag") || "primary", i = {
      tag: (o, u) => {
        let l;
        return Z(Q("el-tag"), {
          type: n(o, u)
        }, bp(l = r(o, u)) ? l : {
          default: () => [l]
        });
      },
      user: (o, u) => Z(Q("display-text"), {
        value: u.row[o.prop],
        type: "user"
      }, null),
      date: (o, u) => Z(Q("dict-date"), {
        date: u.row[o.prop],
        format: o.format ? o.format : "YYYY-MM-DD HH:mm:ss"
      }, null),
      money: (o, u) => Z(Q("el-statistic"), {
        value: u.row[o.prop],
        precision: 2
      }, null),
      org: (o, u) => Z(Q("display-text"), {
        value: u.row[o.prop],
        type: "org"
      }, null),
      area: (o, u) => Z(Q("display-text"), {
        value: u.row[o.prop],
        type: "region"
      }, null),
      custom: (o, u) => Z(Q("display-text"), {
        value: u.row[o.prop],
        type: "custom"
      }, null)
    }, s = (o) => Z(it, null, [o.isShow && Z(Q("el-table-column"), nr(o, {
      align: o.align ?? "center",
      showOverflowTooltip: o.showOverflowTooltip ?? o.prop !== "operation"
    }), {
      default: (u) => {
        if (o._children) return o._children.map((l) => s(l));
        if (o.render) return o.render(u);
        if (t[Rr(o.prop)]) return t[Rr(o.prop)](u);
        for (const l of Object.keys(i))
          if (o[l]) return i[l](o, u);
        return r(o, u);
      },
      header: (u) => o.headerRender ? o.headerRender(u) : t[`${Rr(o.prop)}Header`] ? t[`${Rr(o.prop)}Header`](u) : o.label
    })]);
    return (o, u) => (z(), ve(s, _c(Bc(o.column)), null, 16));
  }
}), Ap = { class: "table-header" }, _p = { class: "header-button-lf" }, Bp = {
  key: 0,
  class: "header-button-ri"
}, Tp = /* @__PURE__ */ $e("i", null, null, -1), Ip = { key: 3 }, wp = { key: 4 }, Dp = { key: 5 }, Np = { key: 6 }, Rp = { key: 7 }, kp = { key: 8 }, Fp = { key: 9 }, Lp = { key: 10 }, Pp = { key: 11 }, Up = { class: "table-empty" }, Op = /* @__PURE__ */ Ue({
  __name: "index",
  props: {
    columns: { default: () => [] },
    translateApis: { default: () => [] },
    dictionaryCodes: { default: () => [] },
    card: { type: Boolean, default: !0 },
    data: {},
    requestApi: {},
    requestAuto: { type: Boolean, default: !0 },
    requestError: {},
    dataCallback: {},
    title: {},
    pagination: { type: Boolean, default: !0 },
    initParam: { default: {} },
    border: { type: Boolean, default: !0 },
    toolButton: { type: [Array, Boolean], default: !0 },
    rowKey: { default: "id" },
    searchCol: { default: () => ({ xs: 1, sm: 2, md: 2, lg: 3, xl: 4 }) }
  },
  emits: ["search", "reset", "rowClick", "rowdoubleClick", "dargSort"],
  setup(e, { expose: t, emit: a }) {
    var le;
    const r = e, n = se(), { batchFindObjects: i, paddingSets: s, enums: o } = zt(), u = [
      "selection",
      "radio",
      "index",
      "expand",
      "sort",
      "user",
      "year",
      "month",
      "money",
      "date",
      "datetime",
      "area",
      "org",
      "boolean"
    ], l = se(!0), c = (X) => Array.isArray(r.toolButton) ? r.toolButton.includes(X) : r.toolButton, f = se(""), { selectionChange: d, selectedList: v, selectedListIds: p, isSelected: h } = ip(r.rowKey), {
      tableData: x,
      pageable: y,
      searchParam: g,
      searchInitParam: m,
      getTableList: C,
      search: S,
      reset: _,
      handleSizeChange: T,
      handleCurrentChange: D
    } = np(
      r.requestApi,
      r.initParam,
      r.pagination,
      r.dataCallback,
      r.requestError
    ), P = () => n.value.clearSelection();
    yt(() => {
      pe(), r.requestAuto && C(), r.data && (y.value.total = r.data.length);
    });
    const I = He(() => r.data ? r.pagination ? r.data.slice(
      (y.value.current - 1) * y.value.size,
      y.value.size * y.value.current
    ) : r.data : x.value), B = ap((X) => {
      var ge;
      if (!X || X.length === 0) return;
      const te = {
        userIds: /* @__PURE__ */ new Set(),
        orgIds: /* @__PURE__ */ new Set(),
        regionIds: /* @__PURE__ */ new Set(),
        dictTypes: new Set(r.dictionaryCodes || [])
      };
      r.columns.forEach((de) => {
        if (!de.prop) return;
        const Te = de.prop, W = de.user || de.type === "user", Xe = de.org || de.type === "org", De = de.area || de.type === "area";
        if (W || Xe || De)
          for (let At = 0; At < X.length; At++) {
            const Sr = X[At], $t = Jd(Sr, Te);
            $t && String($t).split(",").forEach((ue) => {
              W && te.userIds.add(ue.trim()), Xe && te.orgIds.add(ue.trim()), De && te.regionIds.add(ue.trim());
            });
          }
      }), (ge = r.translateApis) == null || ge.forEach((de) => {
        const Te = /* @__PURE__ */ new Set();
        for (let Xe = 0; Xe < X.length; Xe++) {
          const De = X[Xe][de.prop];
          De && Te.add(String(De));
        }
        const W = Array.from(Te).filter(
          (Xe) => !xt().commonNames[Xe]
        );
        W.length > 0 && xt().findChineseByIds(de.api, W, de.nameKey);
      });
      const ce = [];
      te.userIds.size > 0 && ce.push(
        i("USER", Array.from(te.userIds))
      ), te.orgIds.size > 0 && ce.push(
        i("ORG", Array.from(te.orgIds))
      ), te.regionIds.size > 0 && ce.push(
        i("REGION", Array.from(te.regionIds))
      ), te.dictTypes.forEach((de) => {
        de && !o.value[de] && !s.enums.has(de) && (s.enums.add(de), ce.push(Promise.resolve(xt().findEnumByName(de))));
      }), ce.length > 0 && Promise.all(ce).catch((de) => {
        console.error("Batch request error:", de);
      });
    }, 100);
    nt(
      () => I.value,
      (X) => {
        B(X);
      },
      { immediate: !0 }
    ), nt(
      () => r.data || x.value,
      (X) => {
        B(X);
      },
      { immediate: !0 }
    ), nt(() => r.initParam, C, { deep: !0 });
    const R = or(r.columns), F = He(() => ee(R)), U = se(/* @__PURE__ */ new Map()), M = async ({ prop: X, enum: te }) => {
      if (!te || U.value.has(X) && (typeof te == "function" || U.value.get(X) === te))
        return;
      if (typeof te != "function")
        return U.value.set(X, ye(te));
      U.value.set(X, []);
      const ce = await te();
      U.value.set(X, ce);
    };
    Ea("enumMap", U);
    const ee = (X, te = []) => (X.forEach(async (ce) => {
      var ge;
      (ge = ce._children) != null && ge.length && te.push(...ee(ce._children)), te.push(ce), ce.isShow = ce.isShow ?? !0, ce.isFilterEnum = ce.isFilterEnum ?? !0, await M(ce);
    }), te.filter((ce) => {
      var ge;
      return !((ge = ce._children) != null && ge.length);
    })), q = He(() => {
      var X;
      return (X = F.value) == null ? void 0 : X.filter((te) => {
        var ce, ge;
        return ((ce = te.search) == null ? void 0 : ce.el) || ((ge = te.search) == null ? void 0 : ge.render);
      }).sort((te, ce) => te.search.order - ce.search.order);
    });
    (le = q.value) == null || le.forEach((X, te) => {
      var de, Te, W;
      X.search.order = ((de = X.search) == null ? void 0 : de.order) ?? te + 2;
      const ce = ((Te = X.search) == null ? void 0 : Te.key) ?? Rr(X.prop), ge = (W = X.search) == null ? void 0 : W.defaultValue;
      ge != null && (m.value[ce] = ge, g.value[ce] = ge);
    });
    const fe = se(), Ee = R.filter((X) => {
      const { type: te, prop: ce, isShow: ge } = X;
      return !u.includes(te) && ce !== "operation" && ge;
    }), xe = () => fe.value.openColSetting(), O = a, H = () => {
      S(), O("search");
    }, $ = () => {
      _(), O("reset");
    }, K = (X, te, ce) => {
      O("rowClick", { row: X, column: te, event: ce });
    }, be = (X, te, ce) => {
      O("rowdoubleClick", { row: X, column: te, event: ce });
    }, pe = () => {
      const X = document.querySelector(
        ".el-table__body-wrapper tbody"
      );
      Do.create(X, {
        handle: ".move",
        animation: 300,
        onEnd({ newIndex: te, oldIndex: ce }) {
          const [ge] = I.value.splice(ce, 1);
          I.value.splice(te, 0, ge), O("dargSort", { newIndex: te, oldIndex: ce });
        }
      });
    };
    t({
      element: n,
      tableData: I,
      radio: f,
      pageable: y,
      searchParam: g,
      searchInitParam: m,
      getTableList: C,
      search: S,
      reset: _,
      handleSizeChange: T,
      handleCurrentChange: D,
      clearSelection: P,
      enumMap: U,
      isSelected: h,
      selectedList: v,
      selectedListIds: p
    });
    const Ne = {
      beforeMount(X, te) {
        const ce = (ge) => {
          if (!ge) return "0.00";
          const de = Number(ge);
          return isNaN(de) ? ge : de.toLocaleString("zh-CN", {
            style: "currency",
            currency: "CNY"
          });
        };
        X.innerHTML = ce(te.value);
      },
      updated(X, te) {
        const ce = (ge) => {
          if (!ge) return "0.00";
          const de = Number(ge);
          return isNaN(de) ? ge : de.toLocaleString("zh-CN", {
            style: "currency",
            currency: "CNY"
          });
        };
        X.innerHTML = ce(te.value);
      }
    };
    return (X, te) => {
      const ce = Q("el-button"), ge = Q("el-radio"), de = Q("DCaret"), Te = Q("el-icon"), W = Q("el-tag"), Xe = Q("dict-org-name"), De = Q("dict-user-name"), At = Q("dict-date"), Sr = Q("dict-area"), $t = Q("el-table-column"), _t = Q("el-empty");
      return z(), he(it, null, [
        aa(Z(vp, {
          search: H,
          reset: $,
          columns: q.value,
          "search-param": ye(g),
          "search-col": X.searchCol
        }, null, 8, ["columns", "search-param", "search-col"]), [
          [i0, l.value]
        ]),
        $e("div", {
          class: a0(["table-main", { card: X.card }])
        }, [
          $e("div", Ap, [
            $e("div", _p, [
              Rt(X.$slots, "tableHeader", {
                selectedList: ye(v),
                selectedListIds: ye(p),
                isSelected: ye(h)
              })
            ]),
            X.toolButton ? (z(), he("div", Bp, [
              Rt(X.$slots, "toolButton", {}, () => {
                var ue;
                return [
                  c("refresh") ? (z(), ve(ce, {
                    key: 0,
                    icon: ye(Cn),
                    circle: "",
                    onClick: ye(C)
                  }, null, 8, ["icon", "onClick"])) : qe("", !0),
                  c("setting") && X.columns.length ? (z(), ve(ce, {
                    key: 1,
                    icon: ye(Pc),
                    circle: "",
                    onClick: xe
                  }, null, 8, ["icon"])) : qe("", !0),
                  c("search") && ((ue = q.value) != null && ue.length) ? (z(), ve(ce, {
                    key: 2,
                    icon: ye(Ua),
                    circle: "",
                    onClick: te[0] || (te[0] = (Ze) => l.value = !l.value)
                  }, null, 8, ["icon"])) : qe("", !0)
                ];
              })
            ])) : qe("", !0)
          ]),
          Z(ye(wc), nr({
            ref_key: "tableRef",
            ref: n
          }, X.$attrs, {
            data: I.value,
            border: X.border,
            "row-key": X.rowKey,
            onRowClick: K,
            onRowDblclick: be,
            onSelectionChange: ye(d)
          }), {
            append: re(() => [
              Rt(X.$slots, "append")
            ]),
            empty: re(() => [
              $e("div", Up, [
                Rt(X.$slots, "empty", {}, () => [
                  Z(_t, { description: "暂无数据" })
                ])
              ])
            ]),
            default: re(() => [
              Rt(X.$slots, "default"),
              (z(!0), he(it, null, St(R, (ue) => (z(), he(it, { key: ue }, [
                ue.type && u.includes(ue.type) && ue.isShow ? (z(), ve($t, nr({
                  key: 0,
                  ref_for: !0
                }, ue, {
                  align: ue.align ?? "center",
                  "reserve-selection": ue.type == "selection"
                }), {
                  default: re((Ze) => [
                    ue.type == "expand" ? (z(), he(it, { key: 0 }, [
                      ue.render ? (z(), ve(Na(ue.render), nr({
                        key: 0,
                        ref_for: !0
                      }, Ze), null, 16)) : Rt(X.$slots, ue.type, nr({
                        key: 1,
                        ref_for: !0
                      }, Ze))
                    ], 64)) : qe("", !0),
                    ue.type == "radio" ? (z(), ve(ge, {
                      key: 1,
                      modelValue: f.value,
                      "onUpdate:modelValue": te[1] || (te[1] = (lr) => f.value = lr),
                      label: Ze.row[X.rowKey]
                    }, {
                      default: re(() => [
                        Tp
                      ]),
                      _: 2
                    }, 1032, ["modelValue", "label"])) : qe("", !0),
                    ue.type == "sort" ? (z(), ve(W, {
                      key: 2,
                      class: "move"
                    }, {
                      default: re(() => [
                        Z(Te, null, {
                          default: re(() => [
                            Z(de)
                          ]),
                          _: 1
                        })
                      ]),
                      _: 1
                    })) : qe("", !0),
                    ue.type == "org" && ue.prop ? (z(), he("span", Ip, [
                      Z(Xe, {
                        value: Ze.row[ue.prop]
                      }, null, 8, ["value"])
                    ])) : qe("", !0),
                    ue.type == "user" && ue.prop ? (z(), he("span", wp, [
                      Z(De, {
                        "user-code": Ze.row[ue.prop]
                      }, null, 8, ["user-code"])
                    ])) : qe("", !0),
                    ue.type == "year" && ue.prop ? (z(), he("span", Dp, [
                      Z(At, {
                        date: Ze.row[ue.prop],
                        format: "YYYY"
                      }, null, 8, ["date"])
                    ])) : qe("", !0),
                    ue.type == "month" && ue.prop ? (z(), he("span", Np, [
                      Z(At, {
                        date: Ze.row[ue.prop],
                        format: "YYYY-MM"
                      }, null, 8, ["date"])
                    ])) : qe("", !0),
                    ue.type == "date" && ue.prop ? (z(), he("span", Rp, [
                      Z(At, {
                        date: Ze.row[ue.prop],
                        format: "YYYY-MM-DD"
                      }, null, 8, ["date"])
                    ])) : qe("", !0),
                    ue.type == "datetime" && ue.prop ? (z(), he("span", kp, [
                      Z(At, {
                        date: Ze.row[ue.prop],
                        format: "YYYY-MM-DD HH:mm:ss"
                      }, null, 8, ["date"])
                    ])) : qe("", !0),
                    ue.type == "area" && ue.prop ? (z(), he("span", Fp, [
                      Z(Sr, {
                        value: Ze.row[ue.prop]
                      }, null, 8, ["value"])
                    ])) : qe("", !0),
                    ue.type == "money" && ue.prop ? aa((z(), he("span", Lp, null, 512)), [
                      [Ne, Ze.row[ue.prop]]
                    ]) : qe("", !0),
                    ue.type == "boolean" && ue.prop ? (z(), he("span", Pp, [
                      Ze.row[ue.prop] ? (z(), ve(W, {
                        key: 0,
                        type: "success"
                      }, {
                        default: re(() => [
                          rt("是")
                        ]),
                        _: 1
                      })) : (z(), ve(W, {
                        key: 1,
                        type: "danger"
                      }, {
                        default: re(() => [
                          rt("否")
                        ]),
                        _: 1
                      }))
                    ])) : qe("", !0)
                  ]),
                  _: 2
                }, 1040, ["align", "reserve-selection"])) : qe("", !0),
                !ue.type && ue.prop && ue.isShow ? (z(), ve(Sp, {
                  key: 1,
                  column: ue
                }, n0({ _: 2 }, [
                  St(Object.keys(X.$slots), (Ze) => ({
                    name: Ze,
                    fn: re((lr) => [
                      Rt(X.$slots, Ze, nr({ ref_for: !0 }, lr))
                    ])
                  }))
                ]), 1032, ["column"])) : qe("", !0)
              ], 64))), 128))
            ]),
            _: 3
          }, 16, ["data", "border", "row-key", "onSelectionChange"]),
          Rt(X.$slots, "pagination", {}, () => [
            X.pagination ? (z(), ve(xp, {
              key: 0,
              pageable: ye(y),
              "handle-size-change": ye(T),
              "handle-current-change": ye(D)
            }, null, 8, ["pageable", "handle-size-change", "handle-current-change"])) : qe("", !0)
          ])
        ], 2),
        X.toolButton ? (z(), ve(Ep, {
          key: 0,
          ref_key: "colRef",
          ref: fe,
          "col-setting": ye(Ee),
          "onUpdate:colSetting": te[2] || (te[2] = (ue) => Jt(Ee) ? Ee.value = ue : null)
        }, null, 8, ["col-setting"])) : qe("", !0)
      ], 64);
    };
  }
});
var we = typeof globalThis < "u" ? globalThis : typeof window < "u" ? window : typeof global < "u" ? global : typeof self < "u" ? self : {};
function el(e) {
  return e && e.__esModule && Object.prototype.hasOwnProperty.call(e, "default") ? e.default : e;
}
function Vp(e) {
  if (e.__esModule) return e;
  var t = e.default;
  if (typeof t == "function") {
    var a = function r() {
      return this instanceof r ? Reflect.construct(t, arguments, this.constructor) : t.apply(this, arguments);
    };
    a.prototype = t.prototype;
  } else a = {};
  return Object.defineProperty(a, "__esModule", { value: !0 }), Object.keys(e).forEach(function(r) {
    var n = Object.getOwnPropertyDescriptor(e, r);
    Object.defineProperty(a, r, n.get ? n : {
      enumerable: !0,
      get: function() {
        return e[r];
      }
    });
  }), a;
}
var tl = { exports: {} };
function Mp(e) {
  throw new Error('Could not dynamically require "' + e + '". Please configure the dynamicRequireTargets or/and ignoreDynamicRequires option of @rollup/plugin-commonjs appropriately for this require call to work.');
}
var zn = { exports: {} };
const Kp = {}, Hp = /* @__PURE__ */ Object.freeze(/* @__PURE__ */ Object.defineProperty({
  __proto__: null,
  default: Kp
}, Symbol.toStringTag, { value: "Module" })), Bn = /* @__PURE__ */ Vp(Hp);
var us;
function Oe() {
  return us || (us = 1, function(e, t) {
    (function(a, r) {
      e.exports = r();
    })(we, function() {
      var a = a || function(r, n) {
        var i;
        if (typeof window < "u" && window.crypto && (i = window.crypto), typeof self < "u" && self.crypto && (i = self.crypto), typeof globalThis < "u" && globalThis.crypto && (i = globalThis.crypto), !i && typeof window < "u" && window.msCrypto && (i = window.msCrypto), !i && typeof we < "u" && we.crypto && (i = we.crypto), !i && typeof Mp == "function")
          try {
            i = Bn;
          } catch {
          }
        var s = function() {
          if (i) {
            if (typeof i.getRandomValues == "function")
              try {
                return i.getRandomValues(new Uint32Array(1))[0];
              } catch {
              }
            if (typeof i.randomBytes == "function")
              try {
                return i.randomBytes(4).readInt32LE();
              } catch {
              }
          }
          throw new Error("Native crypto module could not be used to get secure random number.");
        }, o = Object.create || /* @__PURE__ */ function() {
          function g() {
          }
          return function(m) {
            var C;
            return g.prototype = m, C = new g(), g.prototype = null, C;
          };
        }(), u = {}, l = u.lib = {}, c = l.Base = /* @__PURE__ */ function() {
          return {
            /**
             * Creates a new object that inherits from this object.
             *
             * @param {Object} overrides Properties to copy into the new object.
             *
             * @return {Object} The new object.
             *
             * @static
             *
             * @example
             *
             *     var MyType = CryptoJS.lib.Base.extend({
             *         field: 'value',
             *
             *         method: function () {
             *         }
             *     });
             */
            extend: function(g) {
              var m = o(this);
              return g && m.mixIn(g), (!m.hasOwnProperty("init") || this.init === m.init) && (m.init = function() {
                m.$super.init.apply(this, arguments);
              }), m.init.prototype = m, m.$super = this, m;
            },
            /**
             * Extends this object and runs the init method.
             * Arguments to create() will be passed to init().
             *
             * @return {Object} The new object.
             *
             * @static
             *
             * @example
             *
             *     var instance = MyType.create();
             */
            create: function() {
              var g = this.extend();
              return g.init.apply(g, arguments), g;
            },
            /**
             * Initializes a newly created object.
             * Override this method to add some logic when your objects are created.
             *
             * @example
             *
             *     var MyType = CryptoJS.lib.Base.extend({
             *         init: function () {
             *             // ...
             *         }
             *     });
             */
            init: function() {
            },
            /**
             * Copies properties into this object.
             *
             * @param {Object} properties The properties to mix in.
             *
             * @example
             *
             *     MyType.mixIn({
             *         field: 'value'
             *     });
             */
            mixIn: function(g) {
              for (var m in g)
                g.hasOwnProperty(m) && (this[m] = g[m]);
              g.hasOwnProperty("toString") && (this.toString = g.toString);
            },
            /**
             * Creates a copy of this object.
             *
             * @return {Object} The clone.
             *
             * @example
             *
             *     var clone = instance.clone();
             */
            clone: function() {
              return this.init.prototype.extend(this);
            }
          };
        }(), f = l.WordArray = c.extend({
          /**
           * Initializes a newly created word array.
           *
           * @param {Array} words (Optional) An array of 32-bit words.
           * @param {number} sigBytes (Optional) The number of significant bytes in the words.
           *
           * @example
           *
           *     var wordArray = CryptoJS.lib.WordArray.create();
           *     var wordArray = CryptoJS.lib.WordArray.create([0x00010203, 0x04050607]);
           *     var wordArray = CryptoJS.lib.WordArray.create([0x00010203, 0x04050607], 6);
           */
          init: function(g, m) {
            g = this.words = g || [], m != n ? this.sigBytes = m : this.sigBytes = g.length * 4;
          },
          /**
           * Converts this word array to a string.
           *
           * @param {Encoder} encoder (Optional) The encoding strategy to use. Default: CryptoJS.enc.Hex
           *
           * @return {string} The stringified word array.
           *
           * @example
           *
           *     var string = wordArray + '';
           *     var string = wordArray.toString();
           *     var string = wordArray.toString(CryptoJS.enc.Utf8);
           */
          toString: function(g) {
            return (g || v).stringify(this);
          },
          /**
           * Concatenates a word array to this word array.
           *
           * @param {WordArray} wordArray The word array to append.
           *
           * @return {WordArray} This word array.
           *
           * @example
           *
           *     wordArray1.concat(wordArray2);
           */
          concat: function(g) {
            var m = this.words, C = g.words, S = this.sigBytes, _ = g.sigBytes;
            if (this.clamp(), S % 4)
              for (var T = 0; T < _; T++) {
                var D = C[T >>> 2] >>> 24 - T % 4 * 8 & 255;
                m[S + T >>> 2] |= D << 24 - (S + T) % 4 * 8;
              }
            else
              for (var P = 0; P < _; P += 4)
                m[S + P >>> 2] = C[P >>> 2];
            return this.sigBytes += _, this;
          },
          /**
           * Removes insignificant bits.
           *
           * @example
           *
           *     wordArray.clamp();
           */
          clamp: function() {
            var g = this.words, m = this.sigBytes;
            g[m >>> 2] &= 4294967295 << 32 - m % 4 * 8, g.length = r.ceil(m / 4);
          },
          /**
           * Creates a copy of this word array.
           *
           * @return {WordArray} The clone.
           *
           * @example
           *
           *     var clone = wordArray.clone();
           */
          clone: function() {
            var g = c.clone.call(this);
            return g.words = this.words.slice(0), g;
          },
          /**
           * Creates a word array filled with random bytes.
           *
           * @param {number} nBytes The number of random bytes to generate.
           *
           * @return {WordArray} The random word array.
           *
           * @static
           *
           * @example
           *
           *     var wordArray = CryptoJS.lib.WordArray.random(16);
           */
          random: function(g) {
            for (var m = [], C = 0; C < g; C += 4)
              m.push(s());
            return new f.init(m, g);
          }
        }), d = u.enc = {}, v = d.Hex = {
          /**
           * Converts a word array to a hex string.
           *
           * @param {WordArray} wordArray The word array.
           *
           * @return {string} The hex string.
           *
           * @static
           *
           * @example
           *
           *     var hexString = CryptoJS.enc.Hex.stringify(wordArray);
           */
          stringify: function(g) {
            for (var m = g.words, C = g.sigBytes, S = [], _ = 0; _ < C; _++) {
              var T = m[_ >>> 2] >>> 24 - _ % 4 * 8 & 255;
              S.push((T >>> 4).toString(16)), S.push((T & 15).toString(16));
            }
            return S.join("");
          },
          /**
           * Converts a hex string to a word array.
           *
           * @param {string} hexStr The hex string.
           *
           * @return {WordArray} The word array.
           *
           * @static
           *
           * @example
           *
           *     var wordArray = CryptoJS.enc.Hex.parse(hexString);
           */
          parse: function(g) {
            for (var m = g.length, C = [], S = 0; S < m; S += 2)
              C[S >>> 3] |= parseInt(g.substr(S, 2), 16) << 24 - S % 8 * 4;
            return new f.init(C, m / 2);
          }
        }, p = d.Latin1 = {
          /**
           * Converts a word array to a Latin1 string.
           *
           * @param {WordArray} wordArray The word array.
           *
           * @return {string} The Latin1 string.
           *
           * @static
           *
           * @example
           *
           *     var latin1String = CryptoJS.enc.Latin1.stringify(wordArray);
           */
          stringify: function(g) {
            for (var m = g.words, C = g.sigBytes, S = [], _ = 0; _ < C; _++) {
              var T = m[_ >>> 2] >>> 24 - _ % 4 * 8 & 255;
              S.push(String.fromCharCode(T));
            }
            return S.join("");
          },
          /**
           * Converts a Latin1 string to a word array.
           *
           * @param {string} latin1Str The Latin1 string.
           *
           * @return {WordArray} The word array.
           *
           * @static
           *
           * @example
           *
           *     var wordArray = CryptoJS.enc.Latin1.parse(latin1String);
           */
          parse: function(g) {
            for (var m = g.length, C = [], S = 0; S < m; S++)
              C[S >>> 2] |= (g.charCodeAt(S) & 255) << 24 - S % 4 * 8;
            return new f.init(C, m);
          }
        }, h = d.Utf8 = {
          /**
           * Converts a word array to a UTF-8 string.
           *
           * @param {WordArray} wordArray The word array.
           *
           * @return {string} The UTF-8 string.
           *
           * @static
           *
           * @example
           *
           *     var utf8String = CryptoJS.enc.Utf8.stringify(wordArray);
           */
          stringify: function(g) {
            try {
              return decodeURIComponent(escape(p.stringify(g)));
            } catch {
              throw new Error("Malformed UTF-8 data");
            }
          },
          /**
           * Converts a UTF-8 string to a word array.
           *
           * @param {string} utf8Str The UTF-8 string.
           *
           * @return {WordArray} The word array.
           *
           * @static
           *
           * @example
           *
           *     var wordArray = CryptoJS.enc.Utf8.parse(utf8String);
           */
          parse: function(g) {
            return p.parse(unescape(encodeURIComponent(g)));
          }
        }, x = l.BufferedBlockAlgorithm = c.extend({
          /**
           * Resets this block algorithm's data buffer to its initial state.
           *
           * @example
           *
           *     bufferedBlockAlgorithm.reset();
           */
          reset: function() {
            this._data = new f.init(), this._nDataBytes = 0;
          },
          /**
           * Adds new data to this block algorithm's buffer.
           *
           * @param {WordArray|string} data The data to append. Strings are converted to a WordArray using UTF-8.
           *
           * @example
           *
           *     bufferedBlockAlgorithm._append('data');
           *     bufferedBlockAlgorithm._append(wordArray);
           */
          _append: function(g) {
            typeof g == "string" && (g = h.parse(g)), this._data.concat(g), this._nDataBytes += g.sigBytes;
          },
          /**
           * Processes available data blocks.
           *
           * This method invokes _doProcessBlock(offset), which must be implemented by a concrete subtype.
           *
           * @param {boolean} doFlush Whether all blocks and partial blocks should be processed.
           *
           * @return {WordArray} The processed data.
           *
           * @example
           *
           *     var processedData = bufferedBlockAlgorithm._process();
           *     var processedData = bufferedBlockAlgorithm._process(!!'flush');
           */
          _process: function(g) {
            var m, C = this._data, S = C.words, _ = C.sigBytes, T = this.blockSize, D = T * 4, P = _ / D;
            g ? P = r.ceil(P) : P = r.max((P | 0) - this._minBufferSize, 0);
            var I = P * T, B = r.min(I * 4, _);
            if (I) {
              for (var R = 0; R < I; R += T)
                this._doProcessBlock(S, R);
              m = S.splice(0, I), C.sigBytes -= B;
            }
            return new f.init(m, B);
          },
          /**
           * Creates a copy of this object.
           *
           * @return {Object} The clone.
           *
           * @example
           *
           *     var clone = bufferedBlockAlgorithm.clone();
           */
          clone: function() {
            var g = c.clone.call(this);
            return g._data = this._data.clone(), g;
          },
          _minBufferSize: 0
        });
        l.Hasher = x.extend({
          /**
           * Configuration options.
           */
          cfg: c.extend(),
          /**
           * Initializes a newly created hasher.
           *
           * @param {Object} cfg (Optional) The configuration options to use for this hash computation.
           *
           * @example
           *
           *     var hasher = CryptoJS.algo.SHA256.create();
           */
          init: function(g) {
            this.cfg = this.cfg.extend(g), this.reset();
          },
          /**
           * Resets this hasher to its initial state.
           *
           * @example
           *
           *     hasher.reset();
           */
          reset: function() {
            x.reset.call(this), this._doReset();
          },
          /**
           * Updates this hasher with a message.
           *
           * @param {WordArray|string} messageUpdate The message to append.
           *
           * @return {Hasher} This hasher.
           *
           * @example
           *
           *     hasher.update('message');
           *     hasher.update(wordArray);
           */
          update: function(g) {
            return this._append(g), this._process(), this;
          },
          /**
           * Finalizes the hash computation.
           * Note that the finalize operation is effectively a destructive, read-once operation.
           *
           * @param {WordArray|string} messageUpdate (Optional) A final message update.
           *
           * @return {WordArray} The hash.
           *
           * @example
           *
           *     var hash = hasher.finalize();
           *     var hash = hasher.finalize('message');
           *     var hash = hasher.finalize(wordArray);
           */
          finalize: function(g) {
            g && this._append(g);
            var m = this._doFinalize();
            return m;
          },
          blockSize: 16,
          /**
           * Creates a shortcut function to a hasher's object interface.
           *
           * @param {Hasher} hasher The hasher to create a helper for.
           *
           * @return {Function} The shortcut function.
           *
           * @static
           *
           * @example
           *
           *     var SHA256 = CryptoJS.lib.Hasher._createHelper(CryptoJS.algo.SHA256);
           */
          _createHelper: function(g) {
            return function(m, C) {
              return new g.init(C).finalize(m);
            };
          },
          /**
           * Creates a shortcut function to the HMAC's object interface.
           *
           * @param {Hasher} hasher The hasher to use in this HMAC helper.
           *
           * @return {Function} The shortcut function.
           *
           * @static
           *
           * @example
           *
           *     var HmacSHA256 = CryptoJS.lib.Hasher._createHmacHelper(CryptoJS.algo.SHA256);
           */
          _createHmacHelper: function(g) {
            return function(m, C) {
              return new y.HMAC.init(g, C).finalize(m);
            };
          }
        });
        var y = u.algo = {};
        return u;
      }(Math);
      return a;
    });
  }(zn)), zn.exports;
}
var $n = { exports: {} }, fs;
function Tn() {
  return fs || (fs = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      return function(r) {
        var n = a, i = n.lib, s = i.Base, o = i.WordArray, u = n.x64 = {};
        u.Word = s.extend({
          /**
           * Initializes a newly created 64-bit word.
           *
           * @param {number} high The high 32 bits.
           * @param {number} low The low 32 bits.
           *
           * @example
           *
           *     var x64Word = CryptoJS.x64.Word.create(0x00010203, 0x04050607);
           */
          init: function(l, c) {
            this.high = l, this.low = c;
          }
          /**
           * Bitwise NOTs this word.
           *
           * @return {X64Word} A new x64-Word object after negating.
           *
           * @example
           *
           *     var negated = x64Word.not();
           */
          // not: function () {
          // var high = ~this.high;
          // var low = ~this.low;
          // return X64Word.create(high, low);
          // },
          /**
           * Bitwise ANDs this word with the passed word.
           *
           * @param {X64Word} word The x64-Word to AND with this word.
           *
           * @return {X64Word} A new x64-Word object after ANDing.
           *
           * @example
           *
           *     var anded = x64Word.and(anotherX64Word);
           */
          // and: function (word) {
          // var high = this.high & word.high;
          // var low = this.low & word.low;
          // return X64Word.create(high, low);
          // },
          /**
           * Bitwise ORs this word with the passed word.
           *
           * @param {X64Word} word The x64-Word to OR with this word.
           *
           * @return {X64Word} A new x64-Word object after ORing.
           *
           * @example
           *
           *     var ored = x64Word.or(anotherX64Word);
           */
          // or: function (word) {
          // var high = this.high | word.high;
          // var low = this.low | word.low;
          // return X64Word.create(high, low);
          // },
          /**
           * Bitwise XORs this word with the passed word.
           *
           * @param {X64Word} word The x64-Word to XOR with this word.
           *
           * @return {X64Word} A new x64-Word object after XORing.
           *
           * @example
           *
           *     var xored = x64Word.xor(anotherX64Word);
           */
          // xor: function (word) {
          // var high = this.high ^ word.high;
          // var low = this.low ^ word.low;
          // return X64Word.create(high, low);
          // },
          /**
           * Shifts this word n bits to the left.
           *
           * @param {number} n The number of bits to shift.
           *
           * @return {X64Word} A new x64-Word object after shifting.
           *
           * @example
           *
           *     var shifted = x64Word.shiftL(25);
           */
          // shiftL: function (n) {
          // if (n < 32) {
          // var high = (this.high << n) | (this.low >>> (32 - n));
          // var low = this.low << n;
          // } else {
          // var high = this.low << (n - 32);
          // var low = 0;
          // }
          // return X64Word.create(high, low);
          // },
          /**
           * Shifts this word n bits to the right.
           *
           * @param {number} n The number of bits to shift.
           *
           * @return {X64Word} A new x64-Word object after shifting.
           *
           * @example
           *
           *     var shifted = x64Word.shiftR(7);
           */
          // shiftR: function (n) {
          // if (n < 32) {
          // var low = (this.low >>> n) | (this.high << (32 - n));
          // var high = this.high >>> n;
          // } else {
          // var low = this.high >>> (n - 32);
          // var high = 0;
          // }
          // return X64Word.create(high, low);
          // },
          /**
           * Rotates this word n bits to the left.
           *
           * @param {number} n The number of bits to rotate.
           *
           * @return {X64Word} A new x64-Word object after rotating.
           *
           * @example
           *
           *     var rotated = x64Word.rotL(25);
           */
          // rotL: function (n) {
          // return this.shiftL(n).or(this.shiftR(64 - n));
          // },
          /**
           * Rotates this word n bits to the right.
           *
           * @param {number} n The number of bits to rotate.
           *
           * @return {X64Word} A new x64-Word object after rotating.
           *
           * @example
           *
           *     var rotated = x64Word.rotR(7);
           */
          // rotR: function (n) {
          // return this.shiftR(n).or(this.shiftL(64 - n));
          // },
          /**
           * Adds this word with the passed word.
           *
           * @param {X64Word} word The x64-Word to add with this word.
           *
           * @return {X64Word} A new x64-Word object after adding.
           *
           * @example
           *
           *     var added = x64Word.add(anotherX64Word);
           */
          // add: function (word) {
          // var low = (this.low + word.low) | 0;
          // var carry = (low >>> 0) < (this.low >>> 0) ? 1 : 0;
          // var high = (this.high + word.high + carry) | 0;
          // return X64Word.create(high, low);
          // }
        }), u.WordArray = s.extend({
          /**
           * Initializes a newly created word array.
           *
           * @param {Array} words (Optional) An array of CryptoJS.x64.Word objects.
           * @param {number} sigBytes (Optional) The number of significant bytes in the words.
           *
           * @example
           *
           *     var wordArray = CryptoJS.x64.WordArray.create();
           *
           *     var wordArray = CryptoJS.x64.WordArray.create([
           *         CryptoJS.x64.Word.create(0x00010203, 0x04050607),
           *         CryptoJS.x64.Word.create(0x18191a1b, 0x1c1d1e1f)
           *     ]);
           *
           *     var wordArray = CryptoJS.x64.WordArray.create([
           *         CryptoJS.x64.Word.create(0x00010203, 0x04050607),
           *         CryptoJS.x64.Word.create(0x18191a1b, 0x1c1d1e1f)
           *     ], 10);
           */
          init: function(l, c) {
            l = this.words = l || [], c != r ? this.sigBytes = c : this.sigBytes = l.length * 8;
          },
          /**
           * Converts this 64-bit word array to a 32-bit word array.
           *
           * @return {CryptoJS.lib.WordArray} This word array's data as a 32-bit word array.
           *
           * @example
           *
           *     var x32WordArray = x64WordArray.toX32();
           */
          toX32: function() {
            for (var l = this.words, c = l.length, f = [], d = 0; d < c; d++) {
              var v = l[d];
              f.push(v.high), f.push(v.low);
            }
            return o.create(f, this.sigBytes);
          },
          /**
           * Creates a copy of this word array.
           *
           * @return {X64WordArray} The clone.
           *
           * @example
           *
           *     var clone = x64WordArray.clone();
           */
          clone: function() {
            for (var l = s.clone.call(this), c = l.words = this.words.slice(0), f = c.length, d = 0; d < f; d++)
              c[d] = c[d].clone();
            return l;
          }
        });
      }(), a;
    });
  }($n)), $n.exports;
}
var qn = { exports: {} }, ds;
function zp() {
  return ds || (ds = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      return function() {
        if (typeof ArrayBuffer == "function") {
          var r = a, n = r.lib, i = n.WordArray, s = i.init, o = i.init = function(u) {
            if (u instanceof ArrayBuffer && (u = new Uint8Array(u)), (u instanceof Int8Array || typeof Uint8ClampedArray < "u" && u instanceof Uint8ClampedArray || u instanceof Int16Array || u instanceof Uint16Array || u instanceof Int32Array || u instanceof Uint32Array || u instanceof Float32Array || u instanceof Float64Array) && (u = new Uint8Array(u.buffer, u.byteOffset, u.byteLength)), u instanceof Uint8Array) {
              for (var l = u.byteLength, c = [], f = 0; f < l; f++)
                c[f >>> 2] |= u[f] << 24 - f % 4 * 8;
              s.call(this, c, l);
            } else
              s.apply(this, arguments);
          };
          o.prototype = i;
        }
      }(), a.lib.WordArray;
    });
  }(qn)), qn.exports;
}
var jn = { exports: {} }, ps;
function $p() {
  return ps || (ps = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.WordArray, s = r.enc;
        s.Utf16 = s.Utf16BE = {
          /**
           * Converts a word array to a UTF-16 BE string.
           *
           * @param {WordArray} wordArray The word array.
           *
           * @return {string} The UTF-16 BE string.
           *
           * @static
           *
           * @example
           *
           *     var utf16String = CryptoJS.enc.Utf16.stringify(wordArray);
           */
          stringify: function(u) {
            for (var l = u.words, c = u.sigBytes, f = [], d = 0; d < c; d += 2) {
              var v = l[d >>> 2] >>> 16 - d % 4 * 8 & 65535;
              f.push(String.fromCharCode(v));
            }
            return f.join("");
          },
          /**
           * Converts a UTF-16 BE string to a word array.
           *
           * @param {string} utf16Str The UTF-16 BE string.
           *
           * @return {WordArray} The word array.
           *
           * @static
           *
           * @example
           *
           *     var wordArray = CryptoJS.enc.Utf16.parse(utf16String);
           */
          parse: function(u) {
            for (var l = u.length, c = [], f = 0; f < l; f++)
              c[f >>> 1] |= u.charCodeAt(f) << 16 - f % 2 * 16;
            return i.create(c, l * 2);
          }
        }, s.Utf16LE = {
          /**
           * Converts a word array to a UTF-16 LE string.
           *
           * @param {WordArray} wordArray The word array.
           *
           * @return {string} The UTF-16 LE string.
           *
           * @static
           *
           * @example
           *
           *     var utf16Str = CryptoJS.enc.Utf16LE.stringify(wordArray);
           */
          stringify: function(u) {
            for (var l = u.words, c = u.sigBytes, f = [], d = 0; d < c; d += 2) {
              var v = o(l[d >>> 2] >>> 16 - d % 4 * 8 & 65535);
              f.push(String.fromCharCode(v));
            }
            return f.join("");
          },
          /**
           * Converts a UTF-16 LE string to a word array.
           *
           * @param {string} utf16Str The UTF-16 LE string.
           *
           * @return {WordArray} The word array.
           *
           * @static
           *
           * @example
           *
           *     var wordArray = CryptoJS.enc.Utf16LE.parse(utf16Str);
           */
          parse: function(u) {
            for (var l = u.length, c = [], f = 0; f < l; f++)
              c[f >>> 1] |= o(u.charCodeAt(f) << 16 - f % 2 * 16);
            return i.create(c, l * 2);
          }
        };
        function o(u) {
          return u << 8 & 4278255360 | u >>> 8 & 16711935;
        }
      }(), a.enc.Utf16;
    });
  }(jn)), jn.exports;
}
var Gn = { exports: {} }, hs;
function Mr() {
  return hs || (hs = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.WordArray, s = r.enc;
        s.Base64 = {
          /**
           * Converts a word array to a Base64 string.
           *
           * @param {WordArray} wordArray The word array.
           *
           * @return {string} The Base64 string.
           *
           * @static
           *
           * @example
           *
           *     var base64String = CryptoJS.enc.Base64.stringify(wordArray);
           */
          stringify: function(u) {
            var l = u.words, c = u.sigBytes, f = this._map;
            u.clamp();
            for (var d = [], v = 0; v < c; v += 3)
              for (var p = l[v >>> 2] >>> 24 - v % 4 * 8 & 255, h = l[v + 1 >>> 2] >>> 24 - (v + 1) % 4 * 8 & 255, x = l[v + 2 >>> 2] >>> 24 - (v + 2) % 4 * 8 & 255, y = p << 16 | h << 8 | x, g = 0; g < 4 && v + g * 0.75 < c; g++)
                d.push(f.charAt(y >>> 6 * (3 - g) & 63));
            var m = f.charAt(64);
            if (m)
              for (; d.length % 4; )
                d.push(m);
            return d.join("");
          },
          /**
           * Converts a Base64 string to a word array.
           *
           * @param {string} base64Str The Base64 string.
           *
           * @return {WordArray} The word array.
           *
           * @static
           *
           * @example
           *
           *     var wordArray = CryptoJS.enc.Base64.parse(base64String);
           */
          parse: function(u) {
            var l = u.length, c = this._map, f = this._reverseMap;
            if (!f) {
              f = this._reverseMap = [];
              for (var d = 0; d < c.length; d++)
                f[c.charCodeAt(d)] = d;
            }
            var v = c.charAt(64);
            if (v) {
              var p = u.indexOf(v);
              p !== -1 && (l = p);
            }
            return o(u, l, f);
          },
          _map: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
        };
        function o(u, l, c) {
          for (var f = [], d = 0, v = 0; v < l; v++)
            if (v % 4) {
              var p = c[u.charCodeAt(v - 1)] << v % 4 * 2, h = c[u.charCodeAt(v)] >>> 6 - v % 4 * 2, x = p | h;
              f[d >>> 2] |= x << 24 - d % 4 * 8, d++;
            }
          return i.create(f, d);
        }
      }(), a.enc.Base64;
    });
  }(Gn)), Gn.exports;
}
var Wn = { exports: {} }, vs;
function qp() {
  return vs || (vs = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.WordArray, s = r.enc;
        s.Base64url = {
          /**
           * Converts a word array to a Base64url string.
           *
           * @param {WordArray} wordArray The word array.
           *
           * @param {boolean} urlSafe Whether to use url safe
           *
           * @return {string} The Base64url string.
           *
           * @static
           *
           * @example
           *
           *     var base64String = CryptoJS.enc.Base64url.stringify(wordArray);
           */
          stringify: function(u, l) {
            l === void 0 && (l = !0);
            var c = u.words, f = u.sigBytes, d = l ? this._safe_map : this._map;
            u.clamp();
            for (var v = [], p = 0; p < f; p += 3)
              for (var h = c[p >>> 2] >>> 24 - p % 4 * 8 & 255, x = c[p + 1 >>> 2] >>> 24 - (p + 1) % 4 * 8 & 255, y = c[p + 2 >>> 2] >>> 24 - (p + 2) % 4 * 8 & 255, g = h << 16 | x << 8 | y, m = 0; m < 4 && p + m * 0.75 < f; m++)
                v.push(d.charAt(g >>> 6 * (3 - m) & 63));
            var C = d.charAt(64);
            if (C)
              for (; v.length % 4; )
                v.push(C);
            return v.join("");
          },
          /**
           * Converts a Base64url string to a word array.
           *
           * @param {string} base64Str The Base64url string.
           *
           * @param {boolean} urlSafe Whether to use url safe
           *
           * @return {WordArray} The word array.
           *
           * @static
           *
           * @example
           *
           *     var wordArray = CryptoJS.enc.Base64url.parse(base64String);
           */
          parse: function(u, l) {
            l === void 0 && (l = !0);
            var c = u.length, f = l ? this._safe_map : this._map, d = this._reverseMap;
            if (!d) {
              d = this._reverseMap = [];
              for (var v = 0; v < f.length; v++)
                d[f.charCodeAt(v)] = v;
            }
            var p = f.charAt(64);
            if (p) {
              var h = u.indexOf(p);
              h !== -1 && (c = h);
            }
            return o(u, c, d);
          },
          _map: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
          _safe_map: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
        };
        function o(u, l, c) {
          for (var f = [], d = 0, v = 0; v < l; v++)
            if (v % 4) {
              var p = c[u.charCodeAt(v - 1)] << v % 4 * 2, h = c[u.charCodeAt(v)] >>> 6 - v % 4 * 2, x = p | h;
              f[d >>> 2] |= x << 24 - d % 4 * 8, d++;
            }
          return i.create(f, d);
        }
      }(), a.enc.Base64url;
    });
  }(Wn)), Wn.exports;
}
var Yn = { exports: {} }, xs;
function Kr() {
  return xs || (xs = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      return function(r) {
        var n = a, i = n.lib, s = i.WordArray, o = i.Hasher, u = n.algo, l = [];
        (function() {
          for (var h = 0; h < 64; h++)
            l[h] = r.abs(r.sin(h + 1)) * 4294967296 | 0;
        })();
        var c = u.MD5 = o.extend({
          _doReset: function() {
            this._hash = new s.init([
              1732584193,
              4023233417,
              2562383102,
              271733878
            ]);
          },
          _doProcessBlock: function(h, x) {
            for (var y = 0; y < 16; y++) {
              var g = x + y, m = h[g];
              h[g] = (m << 8 | m >>> 24) & 16711935 | (m << 24 | m >>> 8) & 4278255360;
            }
            var C = this._hash.words, S = h[x + 0], _ = h[x + 1], T = h[x + 2], D = h[x + 3], P = h[x + 4], I = h[x + 5], B = h[x + 6], R = h[x + 7], F = h[x + 8], U = h[x + 9], M = h[x + 10], ee = h[x + 11], q = h[x + 12], fe = h[x + 13], Ee = h[x + 14], xe = h[x + 15], O = C[0], H = C[1], $ = C[2], K = C[3];
            O = f(O, H, $, K, S, 7, l[0]), K = f(K, O, H, $, _, 12, l[1]), $ = f($, K, O, H, T, 17, l[2]), H = f(H, $, K, O, D, 22, l[3]), O = f(O, H, $, K, P, 7, l[4]), K = f(K, O, H, $, I, 12, l[5]), $ = f($, K, O, H, B, 17, l[6]), H = f(H, $, K, O, R, 22, l[7]), O = f(O, H, $, K, F, 7, l[8]), K = f(K, O, H, $, U, 12, l[9]), $ = f($, K, O, H, M, 17, l[10]), H = f(H, $, K, O, ee, 22, l[11]), O = f(O, H, $, K, q, 7, l[12]), K = f(K, O, H, $, fe, 12, l[13]), $ = f($, K, O, H, Ee, 17, l[14]), H = f(H, $, K, O, xe, 22, l[15]), O = d(O, H, $, K, _, 5, l[16]), K = d(K, O, H, $, B, 9, l[17]), $ = d($, K, O, H, ee, 14, l[18]), H = d(H, $, K, O, S, 20, l[19]), O = d(O, H, $, K, I, 5, l[20]), K = d(K, O, H, $, M, 9, l[21]), $ = d($, K, O, H, xe, 14, l[22]), H = d(H, $, K, O, P, 20, l[23]), O = d(O, H, $, K, U, 5, l[24]), K = d(K, O, H, $, Ee, 9, l[25]), $ = d($, K, O, H, D, 14, l[26]), H = d(H, $, K, O, F, 20, l[27]), O = d(O, H, $, K, fe, 5, l[28]), K = d(K, O, H, $, T, 9, l[29]), $ = d($, K, O, H, R, 14, l[30]), H = d(H, $, K, O, q, 20, l[31]), O = v(O, H, $, K, I, 4, l[32]), K = v(K, O, H, $, F, 11, l[33]), $ = v($, K, O, H, ee, 16, l[34]), H = v(H, $, K, O, Ee, 23, l[35]), O = v(O, H, $, K, _, 4, l[36]), K = v(K, O, H, $, P, 11, l[37]), $ = v($, K, O, H, R, 16, l[38]), H = v(H, $, K, O, M, 23, l[39]), O = v(O, H, $, K, fe, 4, l[40]), K = v(K, O, H, $, S, 11, l[41]), $ = v($, K, O, H, D, 16, l[42]), H = v(H, $, K, O, B, 23, l[43]), O = v(O, H, $, K, U, 4, l[44]), K = v(K, O, H, $, q, 11, l[45]), $ = v($, K, O, H, xe, 16, l[46]), H = v(H, $, K, O, T, 23, l[47]), O = p(O, H, $, K, S, 6, l[48]), K = p(K, O, H, $, R, 10, l[49]), $ = p($, K, O, H, Ee, 15, l[50]), H = p(H, $, K, O, I, 21, l[51]), O = p(O, H, $, K, q, 6, l[52]), K = p(K, O, H, $, D, 10, l[53]), $ = p($, K, O, H, M, 15, l[54]), H = p(H, $, K, O, _, 21, l[55]), O = p(O, H, $, K, F, 6, l[56]), K = p(K, O, H, $, xe, 10, l[57]), $ = p($, K, O, H, B, 15, l[58]), H = p(H, $, K, O, fe, 21, l[59]), O = p(O, H, $, K, P, 6, l[60]), K = p(K, O, H, $, ee, 10, l[61]), $ = p($, K, O, H, T, 15, l[62]), H = p(H, $, K, O, U, 21, l[63]), C[0] = C[0] + O | 0, C[1] = C[1] + H | 0, C[2] = C[2] + $ | 0, C[3] = C[3] + K | 0;
          },
          _doFinalize: function() {
            var h = this._data, x = h.words, y = this._nDataBytes * 8, g = h.sigBytes * 8;
            x[g >>> 5] |= 128 << 24 - g % 32;
            var m = r.floor(y / 4294967296), C = y;
            x[(g + 64 >>> 9 << 4) + 15] = (m << 8 | m >>> 24) & 16711935 | (m << 24 | m >>> 8) & 4278255360, x[(g + 64 >>> 9 << 4) + 14] = (C << 8 | C >>> 24) & 16711935 | (C << 24 | C >>> 8) & 4278255360, h.sigBytes = (x.length + 1) * 4, this._process();
            for (var S = this._hash, _ = S.words, T = 0; T < 4; T++) {
              var D = _[T];
              _[T] = (D << 8 | D >>> 24) & 16711935 | (D << 24 | D >>> 8) & 4278255360;
            }
            return S;
          },
          clone: function() {
            var h = o.clone.call(this);
            return h._hash = this._hash.clone(), h;
          }
        });
        function f(h, x, y, g, m, C, S) {
          var _ = h + (x & y | ~x & g) + m + S;
          return (_ << C | _ >>> 32 - C) + x;
        }
        function d(h, x, y, g, m, C, S) {
          var _ = h + (x & g | y & ~g) + m + S;
          return (_ << C | _ >>> 32 - C) + x;
        }
        function v(h, x, y, g, m, C, S) {
          var _ = h + (x ^ y ^ g) + m + S;
          return (_ << C | _ >>> 32 - C) + x;
        }
        function p(h, x, y, g, m, C, S) {
          var _ = h + (y ^ (x | ~g)) + m + S;
          return (_ << C | _ >>> 32 - C) + x;
        }
        n.MD5 = o._createHelper(c), n.HmacMD5 = o._createHmacHelper(c);
      }(Math), a.MD5;
    });
  }(Yn)), Yn.exports;
}
var Qn = { exports: {} }, gs;
function rl() {
  return gs || (gs = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.WordArray, s = n.Hasher, o = r.algo, u = [], l = o.SHA1 = s.extend({
          _doReset: function() {
            this._hash = new i.init([
              1732584193,
              4023233417,
              2562383102,
              271733878,
              3285377520
            ]);
          },
          _doProcessBlock: function(c, f) {
            for (var d = this._hash.words, v = d[0], p = d[1], h = d[2], x = d[3], y = d[4], g = 0; g < 80; g++) {
              if (g < 16)
                u[g] = c[f + g] | 0;
              else {
                var m = u[g - 3] ^ u[g - 8] ^ u[g - 14] ^ u[g - 16];
                u[g] = m << 1 | m >>> 31;
              }
              var C = (v << 5 | v >>> 27) + y + u[g];
              g < 20 ? C += (p & h | ~p & x) + 1518500249 : g < 40 ? C += (p ^ h ^ x) + 1859775393 : g < 60 ? C += (p & h | p & x | h & x) - 1894007588 : C += (p ^ h ^ x) - 899497514, y = x, x = h, h = p << 30 | p >>> 2, p = v, v = C;
            }
            d[0] = d[0] + v | 0, d[1] = d[1] + p | 0, d[2] = d[2] + h | 0, d[3] = d[3] + x | 0, d[4] = d[4] + y | 0;
          },
          _doFinalize: function() {
            var c = this._data, f = c.words, d = this._nDataBytes * 8, v = c.sigBytes * 8;
            return f[v >>> 5] |= 128 << 24 - v % 32, f[(v + 64 >>> 9 << 4) + 14] = Math.floor(d / 4294967296), f[(v + 64 >>> 9 << 4) + 15] = d, c.sigBytes = f.length * 4, this._process(), this._hash;
          },
          clone: function() {
            var c = s.clone.call(this);
            return c._hash = this._hash.clone(), c;
          }
        });
        r.SHA1 = s._createHelper(l), r.HmacSHA1 = s._createHmacHelper(l);
      }(), a.SHA1;
    });
  }(Qn)), Qn.exports;
}
var Xn = { exports: {} }, ys;
function f0() {
  return ys || (ys = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      return function(r) {
        var n = a, i = n.lib, s = i.WordArray, o = i.Hasher, u = n.algo, l = [], c = [];
        (function() {
          function v(y) {
            for (var g = r.sqrt(y), m = 2; m <= g; m++)
              if (!(y % m))
                return !1;
            return !0;
          }
          function p(y) {
            return (y - (y | 0)) * 4294967296 | 0;
          }
          for (var h = 2, x = 0; x < 64; )
            v(h) && (x < 8 && (l[x] = p(r.pow(h, 1 / 2))), c[x] = p(r.pow(h, 1 / 3)), x++), h++;
        })();
        var f = [], d = u.SHA256 = o.extend({
          _doReset: function() {
            this._hash = new s.init(l.slice(0));
          },
          _doProcessBlock: function(v, p) {
            for (var h = this._hash.words, x = h[0], y = h[1], g = h[2], m = h[3], C = h[4], S = h[5], _ = h[6], T = h[7], D = 0; D < 64; D++) {
              if (D < 16)
                f[D] = v[p + D] | 0;
              else {
                var P = f[D - 15], I = (P << 25 | P >>> 7) ^ (P << 14 | P >>> 18) ^ P >>> 3, B = f[D - 2], R = (B << 15 | B >>> 17) ^ (B << 13 | B >>> 19) ^ B >>> 10;
                f[D] = I + f[D - 7] + R + f[D - 16];
              }
              var F = C & S ^ ~C & _, U = x & y ^ x & g ^ y & g, M = (x << 30 | x >>> 2) ^ (x << 19 | x >>> 13) ^ (x << 10 | x >>> 22), ee = (C << 26 | C >>> 6) ^ (C << 21 | C >>> 11) ^ (C << 7 | C >>> 25), q = T + ee + F + c[D] + f[D], fe = M + U;
              T = _, _ = S, S = C, C = m + q | 0, m = g, g = y, y = x, x = q + fe | 0;
            }
            h[0] = h[0] + x | 0, h[1] = h[1] + y | 0, h[2] = h[2] + g | 0, h[3] = h[3] + m | 0, h[4] = h[4] + C | 0, h[5] = h[5] + S | 0, h[6] = h[6] + _ | 0, h[7] = h[7] + T | 0;
          },
          _doFinalize: function() {
            var v = this._data, p = v.words, h = this._nDataBytes * 8, x = v.sigBytes * 8;
            return p[x >>> 5] |= 128 << 24 - x % 32, p[(x + 64 >>> 9 << 4) + 14] = r.floor(h / 4294967296), p[(x + 64 >>> 9 << 4) + 15] = h, v.sigBytes = p.length * 4, this._process(), this._hash;
          },
          clone: function() {
            var v = o.clone.call(this);
            return v._hash = this._hash.clone(), v;
          }
        });
        n.SHA256 = o._createHelper(d), n.HmacSHA256 = o._createHmacHelper(d);
      }(Math), a.SHA256;
    });
  }(Xn)), Xn.exports;
}
var Zn = { exports: {} }, ms;
function jp() {
  return ms || (ms = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), f0());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.WordArray, s = r.algo, o = s.SHA256, u = s.SHA224 = o.extend({
          _doReset: function() {
            this._hash = new i.init([
              3238371032,
              914150663,
              812702999,
              4144912697,
              4290775857,
              1750603025,
              1694076839,
              3204075428
            ]);
          },
          _doFinalize: function() {
            var l = o._doFinalize.call(this);
            return l.sigBytes -= 4, l;
          }
        });
        r.SHA224 = o._createHelper(u), r.HmacSHA224 = o._createHmacHelper(u);
      }(), a.SHA224;
    });
  }(Zn)), Zn.exports;
}
var Jn = { exports: {} }, Cs;
function al() {
  return Cs || (Cs = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), Tn());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.Hasher, s = r.x64, o = s.Word, u = s.WordArray, l = r.algo;
        function c() {
          return o.create.apply(o, arguments);
        }
        var f = [
          c(1116352408, 3609767458),
          c(1899447441, 602891725),
          c(3049323471, 3964484399),
          c(3921009573, 2173295548),
          c(961987163, 4081628472),
          c(1508970993, 3053834265),
          c(2453635748, 2937671579),
          c(2870763221, 3664609560),
          c(3624381080, 2734883394),
          c(310598401, 1164996542),
          c(607225278, 1323610764),
          c(1426881987, 3590304994),
          c(1925078388, 4068182383),
          c(2162078206, 991336113),
          c(2614888103, 633803317),
          c(3248222580, 3479774868),
          c(3835390401, 2666613458),
          c(4022224774, 944711139),
          c(264347078, 2341262773),
          c(604807628, 2007800933),
          c(770255983, 1495990901),
          c(1249150122, 1856431235),
          c(1555081692, 3175218132),
          c(1996064986, 2198950837),
          c(2554220882, 3999719339),
          c(2821834349, 766784016),
          c(2952996808, 2566594879),
          c(3210313671, 3203337956),
          c(3336571891, 1034457026),
          c(3584528711, 2466948901),
          c(113926993, 3758326383),
          c(338241895, 168717936),
          c(666307205, 1188179964),
          c(773529912, 1546045734),
          c(1294757372, 1522805485),
          c(1396182291, 2643833823),
          c(1695183700, 2343527390),
          c(1986661051, 1014477480),
          c(2177026350, 1206759142),
          c(2456956037, 344077627),
          c(2730485921, 1290863460),
          c(2820302411, 3158454273),
          c(3259730800, 3505952657),
          c(3345764771, 106217008),
          c(3516065817, 3606008344),
          c(3600352804, 1432725776),
          c(4094571909, 1467031594),
          c(275423344, 851169720),
          c(430227734, 3100823752),
          c(506948616, 1363258195),
          c(659060556, 3750685593),
          c(883997877, 3785050280),
          c(958139571, 3318307427),
          c(1322822218, 3812723403),
          c(1537002063, 2003034995),
          c(1747873779, 3602036899),
          c(1955562222, 1575990012),
          c(2024104815, 1125592928),
          c(2227730452, 2716904306),
          c(2361852424, 442776044),
          c(2428436474, 593698344),
          c(2756734187, 3733110249),
          c(3204031479, 2999351573),
          c(3329325298, 3815920427),
          c(3391569614, 3928383900),
          c(3515267271, 566280711),
          c(3940187606, 3454069534),
          c(4118630271, 4000239992),
          c(116418474, 1914138554),
          c(174292421, 2731055270),
          c(289380356, 3203993006),
          c(460393269, 320620315),
          c(685471733, 587496836),
          c(852142971, 1086792851),
          c(1017036298, 365543100),
          c(1126000580, 2618297676),
          c(1288033470, 3409855158),
          c(1501505948, 4234509866),
          c(1607167915, 987167468),
          c(1816402316, 1246189591)
        ], d = [];
        (function() {
          for (var p = 0; p < 80; p++)
            d[p] = c();
        })();
        var v = l.SHA512 = i.extend({
          _doReset: function() {
            this._hash = new u.init([
              new o.init(1779033703, 4089235720),
              new o.init(3144134277, 2227873595),
              new o.init(1013904242, 4271175723),
              new o.init(2773480762, 1595750129),
              new o.init(1359893119, 2917565137),
              new o.init(2600822924, 725511199),
              new o.init(528734635, 4215389547),
              new o.init(1541459225, 327033209)
            ]);
          },
          _doProcessBlock: function(p, h) {
            for (var x = this._hash.words, y = x[0], g = x[1], m = x[2], C = x[3], S = x[4], _ = x[5], T = x[6], D = x[7], P = y.high, I = y.low, B = g.high, R = g.low, F = m.high, U = m.low, M = C.high, ee = C.low, q = S.high, fe = S.low, Ee = _.high, xe = _.low, O = T.high, H = T.low, $ = D.high, K = D.low, be = P, pe = I, Ne = B, le = R, X = F, te = U, ce = M, ge = ee, de = q, Te = fe, W = Ee, Xe = xe, De = O, At = H, Sr = $, $t = K, _t = 0; _t < 80; _t++) {
              var ue, Ze, lr = d[_t];
              if (_t < 16)
                Ze = lr.high = p[h + _t * 2] | 0, ue = lr.low = p[h + _t * 2 + 1] | 0;
              else {
                var R0 = d[_t - 15], $r = R0.high, xa = R0.low, Jl = ($r >>> 1 | xa << 31) ^ ($r >>> 8 | xa << 24) ^ $r >>> 7, k0 = (xa >>> 1 | $r << 31) ^ (xa >>> 8 | $r << 24) ^ (xa >>> 7 | $r << 25), F0 = d[_t - 2], qr = F0.high, ga = F0.low, ec = (qr >>> 19 | ga << 13) ^ (qr << 3 | ga >>> 29) ^ qr >>> 6, L0 = (ga >>> 19 | qr << 13) ^ (ga << 3 | qr >>> 29) ^ (ga >>> 6 | qr << 26), P0 = d[_t - 7], tc = P0.high, rc = P0.low, U0 = d[_t - 16], ac = U0.high, O0 = U0.low;
                ue = k0 + rc, Ze = Jl + tc + (ue >>> 0 < k0 >>> 0 ? 1 : 0), ue = ue + L0, Ze = Ze + ec + (ue >>> 0 < L0 >>> 0 ? 1 : 0), ue = ue + O0, Ze = Ze + ac + (ue >>> 0 < O0 >>> 0 ? 1 : 0), lr.high = Ze, lr.low = ue;
              }
              var nc = de & W ^ ~de & De, V0 = Te & Xe ^ ~Te & At, ic = be & Ne ^ be & X ^ Ne & X, sc = pe & le ^ pe & te ^ le & te, oc = (be >>> 28 | pe << 4) ^ (be << 30 | pe >>> 2) ^ (be << 25 | pe >>> 7), M0 = (pe >>> 28 | be << 4) ^ (pe << 30 | be >>> 2) ^ (pe << 25 | be >>> 7), lc = (de >>> 14 | Te << 18) ^ (de >>> 18 | Te << 14) ^ (de << 23 | Te >>> 9), cc = (Te >>> 14 | de << 18) ^ (Te >>> 18 | de << 14) ^ (Te << 23 | de >>> 9), K0 = f[_t], uc = K0.high, H0 = K0.low, It = $t + cc, cr = Sr + lc + (It >>> 0 < $t >>> 0 ? 1 : 0), It = It + V0, cr = cr + nc + (It >>> 0 < V0 >>> 0 ? 1 : 0), It = It + H0, cr = cr + uc + (It >>> 0 < H0 >>> 0 ? 1 : 0), It = It + ue, cr = cr + Ze + (It >>> 0 < ue >>> 0 ? 1 : 0), z0 = M0 + sc, fc = oc + ic + (z0 >>> 0 < M0 >>> 0 ? 1 : 0);
              Sr = De, $t = At, De = W, At = Xe, W = de, Xe = Te, Te = ge + It | 0, de = ce + cr + (Te >>> 0 < ge >>> 0 ? 1 : 0) | 0, ce = X, ge = te, X = Ne, te = le, Ne = be, le = pe, pe = It + z0 | 0, be = cr + fc + (pe >>> 0 < It >>> 0 ? 1 : 0) | 0;
            }
            I = y.low = I + pe, y.high = P + be + (I >>> 0 < pe >>> 0 ? 1 : 0), R = g.low = R + le, g.high = B + Ne + (R >>> 0 < le >>> 0 ? 1 : 0), U = m.low = U + te, m.high = F + X + (U >>> 0 < te >>> 0 ? 1 : 0), ee = C.low = ee + ge, C.high = M + ce + (ee >>> 0 < ge >>> 0 ? 1 : 0), fe = S.low = fe + Te, S.high = q + de + (fe >>> 0 < Te >>> 0 ? 1 : 0), xe = _.low = xe + Xe, _.high = Ee + W + (xe >>> 0 < Xe >>> 0 ? 1 : 0), H = T.low = H + At, T.high = O + De + (H >>> 0 < At >>> 0 ? 1 : 0), K = D.low = K + $t, D.high = $ + Sr + (K >>> 0 < $t >>> 0 ? 1 : 0);
          },
          _doFinalize: function() {
            var p = this._data, h = p.words, x = this._nDataBytes * 8, y = p.sigBytes * 8;
            h[y >>> 5] |= 128 << 24 - y % 32, h[(y + 128 >>> 10 << 5) + 30] = Math.floor(x / 4294967296), h[(y + 128 >>> 10 << 5) + 31] = x, p.sigBytes = h.length * 4, this._process();
            var g = this._hash.toX32();
            return g;
          },
          clone: function() {
            var p = i.clone.call(this);
            return p._hash = this._hash.clone(), p;
          },
          blockSize: 1024 / 32
        });
        r.SHA512 = i._createHelper(v), r.HmacSHA512 = i._createHmacHelper(v);
      }(), a.SHA512;
    });
  }(Jn)), Jn.exports;
}
var ei = { exports: {} }, Es;
function Gp() {
  return Es || (Es = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), Tn(), al());
    })(we, function(a) {
      return function() {
        var r = a, n = r.x64, i = n.Word, s = n.WordArray, o = r.algo, u = o.SHA512, l = o.SHA384 = u.extend({
          _doReset: function() {
            this._hash = new s.init([
              new i.init(3418070365, 3238371032),
              new i.init(1654270250, 914150663),
              new i.init(2438529370, 812702999),
              new i.init(355462360, 4144912697),
              new i.init(1731405415, 4290775857),
              new i.init(2394180231, 1750603025),
              new i.init(3675008525, 1694076839),
              new i.init(1203062813, 3204075428)
            ]);
          },
          _doFinalize: function() {
            var c = u._doFinalize.call(this);
            return c.sigBytes -= 16, c;
          }
        });
        r.SHA384 = u._createHelper(l), r.HmacSHA384 = u._createHmacHelper(l);
      }(), a.SHA384;
    });
  }(ei)), ei.exports;
}
var ti = { exports: {} }, bs;
function Wp() {
  return bs || (bs = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), Tn());
    })(we, function(a) {
      return function(r) {
        var n = a, i = n.lib, s = i.WordArray, o = i.Hasher, u = n.x64, l = u.Word, c = n.algo, f = [], d = [], v = [];
        (function() {
          for (var x = 1, y = 0, g = 0; g < 24; g++) {
            f[x + 5 * y] = (g + 1) * (g + 2) / 2 % 64;
            var m = y % 5, C = (2 * x + 3 * y) % 5;
            x = m, y = C;
          }
          for (var x = 0; x < 5; x++)
            for (var y = 0; y < 5; y++)
              d[x + 5 * y] = y + (2 * x + 3 * y) % 5 * 5;
          for (var S = 1, _ = 0; _ < 24; _++) {
            for (var T = 0, D = 0, P = 0; P < 7; P++) {
              if (S & 1) {
                var I = (1 << P) - 1;
                I < 32 ? D ^= 1 << I : T ^= 1 << I - 32;
              }
              S & 128 ? S = S << 1 ^ 113 : S <<= 1;
            }
            v[_] = l.create(T, D);
          }
        })();
        var p = [];
        (function() {
          for (var x = 0; x < 25; x++)
            p[x] = l.create();
        })();
        var h = c.SHA3 = o.extend({
          /**
           * Configuration options.
           *
           * @property {number} outputLength
           *   The desired number of bits in the output hash.
           *   Only values permitted are: 224, 256, 384, 512.
           *   Default: 512
           */
          cfg: o.cfg.extend({
            outputLength: 512
          }),
          _doReset: function() {
            for (var x = this._state = [], y = 0; y < 25; y++)
              x[y] = new l.init();
            this.blockSize = (1600 - 2 * this.cfg.outputLength) / 32;
          },
          _doProcessBlock: function(x, y) {
            for (var g = this._state, m = this.blockSize / 2, C = 0; C < m; C++) {
              var S = x[y + 2 * C], _ = x[y + 2 * C + 1];
              S = (S << 8 | S >>> 24) & 16711935 | (S << 24 | S >>> 8) & 4278255360, _ = (_ << 8 | _ >>> 24) & 16711935 | (_ << 24 | _ >>> 8) & 4278255360;
              var T = g[C];
              T.high ^= _, T.low ^= S;
            }
            for (var D = 0; D < 24; D++) {
              for (var P = 0; P < 5; P++) {
                for (var I = 0, B = 0, R = 0; R < 5; R++) {
                  var T = g[P + 5 * R];
                  I ^= T.high, B ^= T.low;
                }
                var F = p[P];
                F.high = I, F.low = B;
              }
              for (var P = 0; P < 5; P++)
                for (var U = p[(P + 4) % 5], M = p[(P + 1) % 5], ee = M.high, q = M.low, I = U.high ^ (ee << 1 | q >>> 31), B = U.low ^ (q << 1 | ee >>> 31), R = 0; R < 5; R++) {
                  var T = g[P + 5 * R];
                  T.high ^= I, T.low ^= B;
                }
              for (var fe = 1; fe < 25; fe++) {
                var I, B, T = g[fe], Ee = T.high, xe = T.low, O = f[fe];
                O < 32 ? (I = Ee << O | xe >>> 32 - O, B = xe << O | Ee >>> 32 - O) : (I = xe << O - 32 | Ee >>> 64 - O, B = Ee << O - 32 | xe >>> 64 - O);
                var H = p[d[fe]];
                H.high = I, H.low = B;
              }
              var $ = p[0], K = g[0];
              $.high = K.high, $.low = K.low;
              for (var P = 0; P < 5; P++)
                for (var R = 0; R < 5; R++) {
                  var fe = P + 5 * R, T = g[fe], be = p[fe], pe = p[(P + 1) % 5 + 5 * R], Ne = p[(P + 2) % 5 + 5 * R];
                  T.high = be.high ^ ~pe.high & Ne.high, T.low = be.low ^ ~pe.low & Ne.low;
                }
              var T = g[0], le = v[D];
              T.high ^= le.high, T.low ^= le.low;
            }
          },
          _doFinalize: function() {
            var x = this._data, y = x.words;
            this._nDataBytes * 8;
            var g = x.sigBytes * 8, m = this.blockSize * 32;
            y[g >>> 5] |= 1 << 24 - g % 32, y[(r.ceil((g + 1) / m) * m >>> 5) - 1] |= 128, x.sigBytes = y.length * 4, this._process();
            for (var C = this._state, S = this.cfg.outputLength / 8, _ = S / 8, T = [], D = 0; D < _; D++) {
              var P = C[D], I = P.high, B = P.low;
              I = (I << 8 | I >>> 24) & 16711935 | (I << 24 | I >>> 8) & 4278255360, B = (B << 8 | B >>> 24) & 16711935 | (B << 24 | B >>> 8) & 4278255360, T.push(B), T.push(I);
            }
            return new s.init(T, S);
          },
          clone: function() {
            for (var x = o.clone.call(this), y = x._state = this._state.slice(0), g = 0; g < 25; g++)
              y[g] = y[g].clone();
            return x;
          }
        });
        n.SHA3 = o._createHelper(h), n.HmacSHA3 = o._createHmacHelper(h);
      }(Math), a.SHA3;
    });
  }(ti)), ti.exports;
}
var ri = { exports: {} }, Ss;
function Yp() {
  return Ss || (Ss = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      /** @preserve
      			(c) 2012 by Cédric Mesnil. All rights reserved.
      
      			Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
      
      			    - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
      			    - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
      
      			THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
      			*/
      return function(r) {
        var n = a, i = n.lib, s = i.WordArray, o = i.Hasher, u = n.algo, l = s.create([
          0,
          1,
          2,
          3,
          4,
          5,
          6,
          7,
          8,
          9,
          10,
          11,
          12,
          13,
          14,
          15,
          7,
          4,
          13,
          1,
          10,
          6,
          15,
          3,
          12,
          0,
          9,
          5,
          2,
          14,
          11,
          8,
          3,
          10,
          14,
          4,
          9,
          15,
          8,
          1,
          2,
          7,
          0,
          6,
          13,
          11,
          5,
          12,
          1,
          9,
          11,
          10,
          0,
          8,
          12,
          4,
          13,
          3,
          7,
          15,
          14,
          5,
          6,
          2,
          4,
          0,
          5,
          9,
          7,
          12,
          2,
          10,
          14,
          1,
          3,
          8,
          11,
          6,
          15,
          13
        ]), c = s.create([
          5,
          14,
          7,
          0,
          9,
          2,
          11,
          4,
          13,
          6,
          15,
          8,
          1,
          10,
          3,
          12,
          6,
          11,
          3,
          7,
          0,
          13,
          5,
          10,
          14,
          15,
          8,
          12,
          4,
          9,
          1,
          2,
          15,
          5,
          1,
          3,
          7,
          14,
          6,
          9,
          11,
          8,
          12,
          2,
          10,
          0,
          4,
          13,
          8,
          6,
          4,
          1,
          3,
          11,
          15,
          0,
          5,
          12,
          2,
          13,
          9,
          7,
          10,
          14,
          12,
          15,
          10,
          4,
          1,
          5,
          8,
          7,
          6,
          2,
          13,
          14,
          0,
          3,
          9,
          11
        ]), f = s.create([
          11,
          14,
          15,
          12,
          5,
          8,
          7,
          9,
          11,
          13,
          14,
          15,
          6,
          7,
          9,
          8,
          7,
          6,
          8,
          13,
          11,
          9,
          7,
          15,
          7,
          12,
          15,
          9,
          11,
          7,
          13,
          12,
          11,
          13,
          6,
          7,
          14,
          9,
          13,
          15,
          14,
          8,
          13,
          6,
          5,
          12,
          7,
          5,
          11,
          12,
          14,
          15,
          14,
          15,
          9,
          8,
          9,
          14,
          5,
          6,
          8,
          6,
          5,
          12,
          9,
          15,
          5,
          11,
          6,
          8,
          13,
          12,
          5,
          12,
          13,
          14,
          11,
          8,
          5,
          6
        ]), d = s.create([
          8,
          9,
          9,
          11,
          13,
          15,
          15,
          5,
          7,
          7,
          8,
          11,
          14,
          14,
          12,
          6,
          9,
          13,
          15,
          7,
          12,
          8,
          9,
          11,
          7,
          7,
          12,
          7,
          6,
          15,
          13,
          11,
          9,
          7,
          15,
          11,
          8,
          6,
          6,
          14,
          12,
          13,
          5,
          14,
          13,
          13,
          7,
          5,
          15,
          5,
          8,
          11,
          14,
          14,
          6,
          14,
          6,
          9,
          12,
          9,
          12,
          5,
          15,
          8,
          8,
          5,
          12,
          9,
          12,
          5,
          14,
          6,
          8,
          13,
          6,
          5,
          15,
          13,
          11,
          11
        ]), v = s.create([0, 1518500249, 1859775393, 2400959708, 2840853838]), p = s.create([1352829926, 1548603684, 1836072691, 2053994217, 0]), h = u.RIPEMD160 = o.extend({
          _doReset: function() {
            this._hash = s.create([1732584193, 4023233417, 2562383102, 271733878, 3285377520]);
          },
          _doProcessBlock: function(_, T) {
            for (var D = 0; D < 16; D++) {
              var P = T + D, I = _[P];
              _[P] = (I << 8 | I >>> 24) & 16711935 | (I << 24 | I >>> 8) & 4278255360;
            }
            var B = this._hash.words, R = v.words, F = p.words, U = l.words, M = c.words, ee = f.words, q = d.words, fe, Ee, xe, O, H, $, K, be, pe, Ne;
            $ = fe = B[0], K = Ee = B[1], be = xe = B[2], pe = O = B[3], Ne = H = B[4];
            for (var le, D = 0; D < 80; D += 1)
              le = fe + _[T + U[D]] | 0, D < 16 ? le += x(Ee, xe, O) + R[0] : D < 32 ? le += y(Ee, xe, O) + R[1] : D < 48 ? le += g(Ee, xe, O) + R[2] : D < 64 ? le += m(Ee, xe, O) + R[3] : le += C(Ee, xe, O) + R[4], le = le | 0, le = S(le, ee[D]), le = le + H | 0, fe = H, H = O, O = S(xe, 10), xe = Ee, Ee = le, le = $ + _[T + M[D]] | 0, D < 16 ? le += C(K, be, pe) + F[0] : D < 32 ? le += m(K, be, pe) + F[1] : D < 48 ? le += g(K, be, pe) + F[2] : D < 64 ? le += y(K, be, pe) + F[3] : le += x(K, be, pe) + F[4], le = le | 0, le = S(le, q[D]), le = le + Ne | 0, $ = Ne, Ne = pe, pe = S(be, 10), be = K, K = le;
            le = B[1] + xe + pe | 0, B[1] = B[2] + O + Ne | 0, B[2] = B[3] + H + $ | 0, B[3] = B[4] + fe + K | 0, B[4] = B[0] + Ee + be | 0, B[0] = le;
          },
          _doFinalize: function() {
            var _ = this._data, T = _.words, D = this._nDataBytes * 8, P = _.sigBytes * 8;
            T[P >>> 5] |= 128 << 24 - P % 32, T[(P + 64 >>> 9 << 4) + 14] = (D << 8 | D >>> 24) & 16711935 | (D << 24 | D >>> 8) & 4278255360, _.sigBytes = (T.length + 1) * 4, this._process();
            for (var I = this._hash, B = I.words, R = 0; R < 5; R++) {
              var F = B[R];
              B[R] = (F << 8 | F >>> 24) & 16711935 | (F << 24 | F >>> 8) & 4278255360;
            }
            return I;
          },
          clone: function() {
            var _ = o.clone.call(this);
            return _._hash = this._hash.clone(), _;
          }
        });
        function x(_, T, D) {
          return _ ^ T ^ D;
        }
        function y(_, T, D) {
          return _ & T | ~_ & D;
        }
        function g(_, T, D) {
          return (_ | ~T) ^ D;
        }
        function m(_, T, D) {
          return _ & D | T & ~D;
        }
        function C(_, T, D) {
          return _ ^ (T | ~D);
        }
        function S(_, T) {
          return _ << T | _ >>> 32 - T;
        }
        n.RIPEMD160 = o._createHelper(h), n.HmacRIPEMD160 = o._createHmacHelper(h);
      }(), a.RIPEMD160;
    });
  }(ri)), ri.exports;
}
var ai = { exports: {} }, As;
function d0() {
  return As || (As = 1, function(e, t) {
    (function(a, r) {
      e.exports = r(Oe());
    })(we, function(a) {
      (function() {
        var r = a, n = r.lib, i = n.Base, s = r.enc, o = s.Utf8, u = r.algo;
        u.HMAC = i.extend({
          /**
           * Initializes a newly created HMAC.
           *
           * @param {Hasher} hasher The hash algorithm to use.
           * @param {WordArray|string} key The secret key.
           *
           * @example
           *
           *     var hmacHasher = CryptoJS.algo.HMAC.create(CryptoJS.algo.SHA256, key);
           */
          init: function(l, c) {
            l = this._hasher = new l.init(), typeof c == "string" && (c = o.parse(c));
            var f = l.blockSize, d = f * 4;
            c.sigBytes > d && (c = l.finalize(c)), c.clamp();
            for (var v = this._oKey = c.clone(), p = this._iKey = c.clone(), h = v.words, x = p.words, y = 0; y < f; y++)
              h[y] ^= 1549556828, x[y] ^= 909522486;
            v.sigBytes = p.sigBytes = d, this.reset();
          },
          /**
           * Resets this HMAC to its initial state.
           *
           * @example
           *
           *     hmacHasher.reset();
           */
          reset: function() {
            var l = this._hasher;
            l.reset(), l.update(this._iKey);
          },
          /**
           * Updates this HMAC with a message.
           *
           * @param {WordArray|string} messageUpdate The message to append.
           *
           * @return {HMAC} This HMAC instance.
           *
           * @example
           *
           *     hmacHasher.update('message');
           *     hmacHasher.update(wordArray);
           */
          update: function(l) {
            return this._hasher.update(l), this;
          },
          /**
           * Finalizes the HMAC computation.
           * Note that the finalize operation is effectively a destructive, read-once operation.
           *
           * @param {WordArray|string} messageUpdate (Optional) A final message update.
           *
           * @return {WordArray} The HMAC.
           *
           * @example
           *
           *     var hmac = hmacHasher.finalize();
           *     var hmac = hmacHasher.finalize('message');
           *     var hmac = hmacHasher.finalize(wordArray);
           */
          finalize: function(l) {
            var c = this._hasher, f = c.finalize(l);
            c.reset();
            var d = c.finalize(this._oKey.clone().concat(f));
            return d;
          }
        });
      })();
    });
  }(ai)), ai.exports;
}
var ni = { exports: {} }, _s;
function Qp() {
  return _s || (_s = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), f0(), d0());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.Base, s = n.WordArray, o = r.algo, u = o.SHA256, l = o.HMAC, c = o.PBKDF2 = i.extend({
          /**
           * Configuration options.
           *
           * @property {number} keySize The key size in words to generate. Default: 4 (128 bits)
           * @property {Hasher} hasher The hasher to use. Default: SHA256
           * @property {number} iterations The number of iterations to perform. Default: 250000
           */
          cfg: i.extend({
            keySize: 128 / 32,
            hasher: u,
            iterations: 25e4
          }),
          /**
           * Initializes a newly created key derivation function.
           *
           * @param {Object} cfg (Optional) The configuration options to use for the derivation.
           *
           * @example
           *
           *     var kdf = CryptoJS.algo.PBKDF2.create();
           *     var kdf = CryptoJS.algo.PBKDF2.create({ keySize: 8 });
           *     var kdf = CryptoJS.algo.PBKDF2.create({ keySize: 8, iterations: 1000 });
           */
          init: function(f) {
            this.cfg = this.cfg.extend(f);
          },
          /**
           * Computes the Password-Based Key Derivation Function 2.
           *
           * @param {WordArray|string} password The password.
           * @param {WordArray|string} salt A salt.
           *
           * @return {WordArray} The derived key.
           *
           * @example
           *
           *     var key = kdf.compute(password, salt);
           */
          compute: function(f, d) {
            for (var v = this.cfg, p = l.create(v.hasher, f), h = s.create(), x = s.create([1]), y = h.words, g = x.words, m = v.keySize, C = v.iterations; y.length < m; ) {
              var S = p.update(d).finalize(x);
              p.reset();
              for (var _ = S.words, T = _.length, D = S, P = 1; P < C; P++) {
                D = p.finalize(D), p.reset();
                for (var I = D.words, B = 0; B < T; B++)
                  _[B] ^= I[B];
              }
              h.concat(S), g[0]++;
            }
            return h.sigBytes = m * 4, h;
          }
        });
        r.PBKDF2 = function(f, d, v) {
          return c.create(v).compute(f, d);
        };
      }(), a.PBKDF2;
    });
  }(ni)), ni.exports;
}
var ii = { exports: {} }, Bs;
function br() {
  return Bs || (Bs = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), rl(), d0());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.Base, s = n.WordArray, o = r.algo, u = o.MD5, l = o.EvpKDF = i.extend({
          /**
           * Configuration options.
           *
           * @property {number} keySize The key size in words to generate. Default: 4 (128 bits)
           * @property {Hasher} hasher The hash algorithm to use. Default: MD5
           * @property {number} iterations The number of iterations to perform. Default: 1
           */
          cfg: i.extend({
            keySize: 128 / 32,
            hasher: u,
            iterations: 1
          }),
          /**
           * Initializes a newly created key derivation function.
           *
           * @param {Object} cfg (Optional) The configuration options to use for the derivation.
           *
           * @example
           *
           *     var kdf = CryptoJS.algo.EvpKDF.create();
           *     var kdf = CryptoJS.algo.EvpKDF.create({ keySize: 8 });
           *     var kdf = CryptoJS.algo.EvpKDF.create({ keySize: 8, iterations: 1000 });
           */
          init: function(c) {
            this.cfg = this.cfg.extend(c);
          },
          /**
           * Derives a key from a password.
           *
           * @param {WordArray|string} password The password.
           * @param {WordArray|string} salt A salt.
           *
           * @return {WordArray} The derived key.
           *
           * @example
           *
           *     var key = kdf.compute(password, salt);
           */
          compute: function(c, f) {
            for (var d, v = this.cfg, p = v.hasher.create(), h = s.create(), x = h.words, y = v.keySize, g = v.iterations; x.length < y; ) {
              d && p.update(d), d = p.update(c).finalize(f), p.reset();
              for (var m = 1; m < g; m++)
                d = p.finalize(d), p.reset();
              h.concat(d);
            }
            return h.sigBytes = y * 4, h;
          }
        });
        r.EvpKDF = function(c, f, d) {
          return l.create(d).compute(c, f);
        };
      }(), a.EvpKDF;
    });
  }(ii)), ii.exports;
}
var si = { exports: {} }, Ts;
function dt() {
  return Ts || (Ts = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), br());
    })(we, function(a) {
      a.lib.Cipher || function(r) {
        var n = a, i = n.lib, s = i.Base, o = i.WordArray, u = i.BufferedBlockAlgorithm, l = n.enc;
        l.Utf8;
        var c = l.Base64, f = n.algo, d = f.EvpKDF, v = i.Cipher = u.extend({
          /**
           * Configuration options.
           *
           * @property {WordArray} iv The IV to use for this operation.
           */
          cfg: s.extend(),
          /**
           * Creates this cipher in encryption mode.
           *
           * @param {WordArray} key The key.
           * @param {Object} cfg (Optional) The configuration options to use for this operation.
           *
           * @return {Cipher} A cipher instance.
           *
           * @static
           *
           * @example
           *
           *     var cipher = CryptoJS.algo.AES.createEncryptor(keyWordArray, { iv: ivWordArray });
           */
          createEncryptor: function(I, B) {
            return this.create(this._ENC_XFORM_MODE, I, B);
          },
          /**
           * Creates this cipher in decryption mode.
           *
           * @param {WordArray} key The key.
           * @param {Object} cfg (Optional) The configuration options to use for this operation.
           *
           * @return {Cipher} A cipher instance.
           *
           * @static
           *
           * @example
           *
           *     var cipher = CryptoJS.algo.AES.createDecryptor(keyWordArray, { iv: ivWordArray });
           */
          createDecryptor: function(I, B) {
            return this.create(this._DEC_XFORM_MODE, I, B);
          },
          /**
           * Initializes a newly created cipher.
           *
           * @param {number} xformMode Either the encryption or decryption transormation mode constant.
           * @param {WordArray} key The key.
           * @param {Object} cfg (Optional) The configuration options to use for this operation.
           *
           * @example
           *
           *     var cipher = CryptoJS.algo.AES.create(CryptoJS.algo.AES._ENC_XFORM_MODE, keyWordArray, { iv: ivWordArray });
           */
          init: function(I, B, R) {
            this.cfg = this.cfg.extend(R), this._xformMode = I, this._key = B, this.reset();
          },
          /**
           * Resets this cipher to its initial state.
           *
           * @example
           *
           *     cipher.reset();
           */
          reset: function() {
            u.reset.call(this), this._doReset();
          },
          /**
           * Adds data to be encrypted or decrypted.
           *
           * @param {WordArray|string} dataUpdate The data to encrypt or decrypt.
           *
           * @return {WordArray} The data after processing.
           *
           * @example
           *
           *     var encrypted = cipher.process('data');
           *     var encrypted = cipher.process(wordArray);
           */
          process: function(I) {
            return this._append(I), this._process();
          },
          /**
           * Finalizes the encryption or decryption process.
           * Note that the finalize operation is effectively a destructive, read-once operation.
           *
           * @param {WordArray|string} dataUpdate The final data to encrypt or decrypt.
           *
           * @return {WordArray} The data after final processing.
           *
           * @example
           *
           *     var encrypted = cipher.finalize();
           *     var encrypted = cipher.finalize('data');
           *     var encrypted = cipher.finalize(wordArray);
           */
          finalize: function(I) {
            I && this._append(I);
            var B = this._doFinalize();
            return B;
          },
          keySize: 128 / 32,
          ivSize: 128 / 32,
          _ENC_XFORM_MODE: 1,
          _DEC_XFORM_MODE: 2,
          /**
           * Creates shortcut functions to a cipher's object interface.
           *
           * @param {Cipher} cipher The cipher to create a helper for.
           *
           * @return {Object} An object with encrypt and decrypt shortcut functions.
           *
           * @static
           *
           * @example
           *
           *     var AES = CryptoJS.lib.Cipher._createHelper(CryptoJS.algo.AES);
           */
          _createHelper: /* @__PURE__ */ function() {
            function I(B) {
              return typeof B == "string" ? P : _;
            }
            return function(B) {
              return {
                encrypt: function(R, F, U) {
                  return I(F).encrypt(B, R, F, U);
                },
                decrypt: function(R, F, U) {
                  return I(F).decrypt(B, R, F, U);
                }
              };
            };
          }()
        });
        i.StreamCipher = v.extend({
          _doFinalize: function() {
            var I = this._process(!0);
            return I;
          },
          blockSize: 1
        });
        var p = n.mode = {}, h = i.BlockCipherMode = s.extend({
          /**
           * Creates this mode for encryption.
           *
           * @param {Cipher} cipher A block cipher instance.
           * @param {Array} iv The IV words.
           *
           * @static
           *
           * @example
           *
           *     var mode = CryptoJS.mode.CBC.createEncryptor(cipher, iv.words);
           */
          createEncryptor: function(I, B) {
            return this.Encryptor.create(I, B);
          },
          /**
           * Creates this mode for decryption.
           *
           * @param {Cipher} cipher A block cipher instance.
           * @param {Array} iv The IV words.
           *
           * @static
           *
           * @example
           *
           *     var mode = CryptoJS.mode.CBC.createDecryptor(cipher, iv.words);
           */
          createDecryptor: function(I, B) {
            return this.Decryptor.create(I, B);
          },
          /**
           * Initializes a newly created mode.
           *
           * @param {Cipher} cipher A block cipher instance.
           * @param {Array} iv The IV words.
           *
           * @example
           *
           *     var mode = CryptoJS.mode.CBC.Encryptor.create(cipher, iv.words);
           */
          init: function(I, B) {
            this._cipher = I, this._iv = B;
          }
        }), x = p.CBC = function() {
          var I = h.extend();
          I.Encryptor = I.extend({
            /**
             * Processes the data block at offset.
             *
             * @param {Array} words The data words to operate on.
             * @param {number} offset The offset where the block starts.
             *
             * @example
             *
             *     mode.processBlock(data.words, offset);
             */
            processBlock: function(R, F) {
              var U = this._cipher, M = U.blockSize;
              B.call(this, R, F, M), U.encryptBlock(R, F), this._prevBlock = R.slice(F, F + M);
            }
          }), I.Decryptor = I.extend({
            /**
             * Processes the data block at offset.
             *
             * @param {Array} words The data words to operate on.
             * @param {number} offset The offset where the block starts.
             *
             * @example
             *
             *     mode.processBlock(data.words, offset);
             */
            processBlock: function(R, F) {
              var U = this._cipher, M = U.blockSize, ee = R.slice(F, F + M);
              U.decryptBlock(R, F), B.call(this, R, F, M), this._prevBlock = ee;
            }
          });
          function B(R, F, U) {
            var M, ee = this._iv;
            ee ? (M = ee, this._iv = r) : M = this._prevBlock;
            for (var q = 0; q < U; q++)
              R[F + q] ^= M[q];
          }
          return I;
        }(), y = n.pad = {}, g = y.Pkcs7 = {
          /**
           * Pads data using the algorithm defined in PKCS #5/7.
           *
           * @param {WordArray} data The data to pad.
           * @param {number} blockSize The multiple that the data should be padded to.
           *
           * @static
           *
           * @example
           *
           *     CryptoJS.pad.Pkcs7.pad(wordArray, 4);
           */
          pad: function(I, B) {
            for (var R = B * 4, F = R - I.sigBytes % R, U = F << 24 | F << 16 | F << 8 | F, M = [], ee = 0; ee < F; ee += 4)
              M.push(U);
            var q = o.create(M, F);
            I.concat(q);
          },
          /**
           * Unpads data that had been padded using the algorithm defined in PKCS #5/7.
           *
           * @param {WordArray} data The data to unpad.
           *
           * @static
           *
           * @example
           *
           *     CryptoJS.pad.Pkcs7.unpad(wordArray);
           */
          unpad: function(I) {
            var B = I.words[I.sigBytes - 1 >>> 2] & 255;
            I.sigBytes -= B;
          }
        };
        i.BlockCipher = v.extend({
          /**
           * Configuration options.
           *
           * @property {Mode} mode The block mode to use. Default: CBC
           * @property {Padding} padding The padding strategy to use. Default: Pkcs7
           */
          cfg: v.cfg.extend({
            mode: x,
            padding: g
          }),
          reset: function() {
            var I;
            v.reset.call(this);
            var B = this.cfg, R = B.iv, F = B.mode;
            this._xformMode == this._ENC_XFORM_MODE ? I = F.createEncryptor : (I = F.createDecryptor, this._minBufferSize = 1), this._mode && this._mode.__creator == I ? this._mode.init(this, R && R.words) : (this._mode = I.call(F, this, R && R.words), this._mode.__creator = I);
          },
          _doProcessBlock: function(I, B) {
            this._mode.processBlock(I, B);
          },
          _doFinalize: function() {
            var I, B = this.cfg.padding;
            return this._xformMode == this._ENC_XFORM_MODE ? (B.pad(this._data, this.blockSize), I = this._process(!0)) : (I = this._process(!0), B.unpad(I)), I;
          },
          blockSize: 128 / 32
        });
        var m = i.CipherParams = s.extend({
          /**
           * Initializes a newly created cipher params object.
           *
           * @param {Object} cipherParams An object with any of the possible cipher parameters.
           *
           * @example
           *
           *     var cipherParams = CryptoJS.lib.CipherParams.create({
           *         ciphertext: ciphertextWordArray,
           *         key: keyWordArray,
           *         iv: ivWordArray,
           *         salt: saltWordArray,
           *         algorithm: CryptoJS.algo.AES,
           *         mode: CryptoJS.mode.CBC,
           *         padding: CryptoJS.pad.PKCS7,
           *         blockSize: 4,
           *         formatter: CryptoJS.format.OpenSSL
           *     });
           */
          init: function(I) {
            this.mixIn(I);
          },
          /**
           * Converts this cipher params object to a string.
           *
           * @param {Format} formatter (Optional) The formatting strategy to use.
           *
           * @return {string} The stringified cipher params.
           *
           * @throws Error If neither the formatter nor the default formatter is set.
           *
           * @example
           *
           *     var string = cipherParams + '';
           *     var string = cipherParams.toString();
           *     var string = cipherParams.toString(CryptoJS.format.OpenSSL);
           */
          toString: function(I) {
            return (I || this.formatter).stringify(this);
          }
        }), C = n.format = {}, S = C.OpenSSL = {
          /**
           * Converts a cipher params object to an OpenSSL-compatible string.
           *
           * @param {CipherParams} cipherParams The cipher params object.
           *
           * @return {string} The OpenSSL-compatible string.
           *
           * @static
           *
           * @example
           *
           *     var openSSLString = CryptoJS.format.OpenSSL.stringify(cipherParams);
           */
          stringify: function(I) {
            var B, R = I.ciphertext, F = I.salt;
            return F ? B = o.create([1398893684, 1701076831]).concat(F).concat(R) : B = R, B.toString(c);
          },
          /**
           * Converts an OpenSSL-compatible string to a cipher params object.
           *
           * @param {string} openSSLStr The OpenSSL-compatible string.
           *
           * @return {CipherParams} The cipher params object.
           *
           * @static
           *
           * @example
           *
           *     var cipherParams = CryptoJS.format.OpenSSL.parse(openSSLString);
           */
          parse: function(I) {
            var B, R = c.parse(I), F = R.words;
            return F[0] == 1398893684 && F[1] == 1701076831 && (B = o.create(F.slice(2, 4)), F.splice(0, 4), R.sigBytes -= 16), m.create({ ciphertext: R, salt: B });
          }
        }, _ = i.SerializableCipher = s.extend({
          /**
           * Configuration options.
           *
           * @property {Formatter} format The formatting strategy to convert cipher param objects to and from a string. Default: OpenSSL
           */
          cfg: s.extend({
            format: S
          }),
          /**
           * Encrypts a message.
           *
           * @param {Cipher} cipher The cipher algorithm to use.
           * @param {WordArray|string} message The message to encrypt.
           * @param {WordArray} key The key.
           * @param {Object} cfg (Optional) The configuration options to use for this operation.
           *
           * @return {CipherParams} A cipher params object.
           *
           * @static
           *
           * @example
           *
           *     var ciphertextParams = CryptoJS.lib.SerializableCipher.encrypt(CryptoJS.algo.AES, message, key);
           *     var ciphertextParams = CryptoJS.lib.SerializableCipher.encrypt(CryptoJS.algo.AES, message, key, { iv: iv });
           *     var ciphertextParams = CryptoJS.lib.SerializableCipher.encrypt(CryptoJS.algo.AES, message, key, { iv: iv, format: CryptoJS.format.OpenSSL });
           */
          encrypt: function(I, B, R, F) {
            F = this.cfg.extend(F);
            var U = I.createEncryptor(R, F), M = U.finalize(B), ee = U.cfg;
            return m.create({
              ciphertext: M,
              key: R,
              iv: ee.iv,
              algorithm: I,
              mode: ee.mode,
              padding: ee.padding,
              blockSize: I.blockSize,
              formatter: F.format
            });
          },
          /**
           * Decrypts serialized ciphertext.
           *
           * @param {Cipher} cipher The cipher algorithm to use.
           * @param {CipherParams|string} ciphertext The ciphertext to decrypt.
           * @param {WordArray} key The key.
           * @param {Object} cfg (Optional) The configuration options to use for this operation.
           *
           * @return {WordArray} The plaintext.
           *
           * @static
           *
           * @example
           *
           *     var plaintext = CryptoJS.lib.SerializableCipher.decrypt(CryptoJS.algo.AES, formattedCiphertext, key, { iv: iv, format: CryptoJS.format.OpenSSL });
           *     var plaintext = CryptoJS.lib.SerializableCipher.decrypt(CryptoJS.algo.AES, ciphertextParams, key, { iv: iv, format: CryptoJS.format.OpenSSL });
           */
          decrypt: function(I, B, R, F) {
            F = this.cfg.extend(F), B = this._parse(B, F.format);
            var U = I.createDecryptor(R, F).finalize(B.ciphertext);
            return U;
          },
          /**
           * Converts serialized ciphertext to CipherParams,
           * else assumed CipherParams already and returns ciphertext unchanged.
           *
           * @param {CipherParams|string} ciphertext The ciphertext.
           * @param {Formatter} format The formatting strategy to use to parse serialized ciphertext.
           *
           * @return {CipherParams} The unserialized ciphertext.
           *
           * @static
           *
           * @example
           *
           *     var ciphertextParams = CryptoJS.lib.SerializableCipher._parse(ciphertextStringOrParams, format);
           */
          _parse: function(I, B) {
            return typeof I == "string" ? B.parse(I, this) : I;
          }
        }), T = n.kdf = {}, D = T.OpenSSL = {
          /**
           * Derives a key and IV from a password.
           *
           * @param {string} password The password to derive from.
           * @param {number} keySize The size in words of the key to generate.
           * @param {number} ivSize The size in words of the IV to generate.
           * @param {WordArray|string} salt (Optional) A 64-bit salt to use. If omitted, a salt will be generated randomly.
           *
           * @return {CipherParams} A cipher params object with the key, IV, and salt.
           *
           * @static
           *
           * @example
           *
           *     var derivedParams = CryptoJS.kdf.OpenSSL.execute('Password', 256/32, 128/32);
           *     var derivedParams = CryptoJS.kdf.OpenSSL.execute('Password', 256/32, 128/32, 'saltsalt');
           */
          execute: function(I, B, R, F, U) {
            if (F || (F = o.random(64 / 8)), U)
              var M = d.create({ keySize: B + R, hasher: U }).compute(I, F);
            else
              var M = d.create({ keySize: B + R }).compute(I, F);
            var ee = o.create(M.words.slice(B), R * 4);
            return M.sigBytes = B * 4, m.create({ key: M, iv: ee, salt: F });
          }
        }, P = i.PasswordBasedCipher = _.extend({
          /**
           * Configuration options.
           *
           * @property {KDF} kdf The key derivation function to use to generate a key and IV from a password. Default: OpenSSL
           */
          cfg: _.cfg.extend({
            kdf: D
          }),
          /**
           * Encrypts a message using a password.
           *
           * @param {Cipher} cipher The cipher algorithm to use.
           * @param {WordArray|string} message The message to encrypt.
           * @param {string} password The password.
           * @param {Object} cfg (Optional) The configuration options to use for this operation.
           *
           * @return {CipherParams} A cipher params object.
           *
           * @static
           *
           * @example
           *
           *     var ciphertextParams = CryptoJS.lib.PasswordBasedCipher.encrypt(CryptoJS.algo.AES, message, 'password');
           *     var ciphertextParams = CryptoJS.lib.PasswordBasedCipher.encrypt(CryptoJS.algo.AES, message, 'password', { format: CryptoJS.format.OpenSSL });
           */
          encrypt: function(I, B, R, F) {
            F = this.cfg.extend(F);
            var U = F.kdf.execute(R, I.keySize, I.ivSize, F.salt, F.hasher);
            F.iv = U.iv;
            var M = _.encrypt.call(this, I, B, U.key, F);
            return M.mixIn(U), M;
          },
          /**
           * Decrypts serialized ciphertext using a password.
           *
           * @param {Cipher} cipher The cipher algorithm to use.
           * @param {CipherParams|string} ciphertext The ciphertext to decrypt.
           * @param {string} password The password.
           * @param {Object} cfg (Optional) The configuration options to use for this operation.
           *
           * @return {WordArray} The plaintext.
           *
           * @static
           *
           * @example
           *
           *     var plaintext = CryptoJS.lib.PasswordBasedCipher.decrypt(CryptoJS.algo.AES, formattedCiphertext, 'password', { format: CryptoJS.format.OpenSSL });
           *     var plaintext = CryptoJS.lib.PasswordBasedCipher.decrypt(CryptoJS.algo.AES, ciphertextParams, 'password', { format: CryptoJS.format.OpenSSL });
           */
          decrypt: function(I, B, R, F) {
            F = this.cfg.extend(F), B = this._parse(B, F.format);
            var U = F.kdf.execute(R, I.keySize, I.ivSize, B.salt, F.hasher);
            F.iv = U.iv;
            var M = _.decrypt.call(this, I, B, U.key, F);
            return M;
          }
        });
      }();
    });
  }(si)), si.exports;
}
var oi = { exports: {} }, Is;
function Xp() {
  return Is || (Is = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return a.mode.CFB = function() {
        var r = a.lib.BlockCipherMode.extend();
        r.Encryptor = r.extend({
          processBlock: function(i, s) {
            var o = this._cipher, u = o.blockSize;
            n.call(this, i, s, u, o), this._prevBlock = i.slice(s, s + u);
          }
        }), r.Decryptor = r.extend({
          processBlock: function(i, s) {
            var o = this._cipher, u = o.blockSize, l = i.slice(s, s + u);
            n.call(this, i, s, u, o), this._prevBlock = l;
          }
        });
        function n(i, s, o, u) {
          var l, c = this._iv;
          c ? (l = c.slice(0), this._iv = void 0) : l = this._prevBlock, u.encryptBlock(l, 0);
          for (var f = 0; f < o; f++)
            i[s + f] ^= l[f];
        }
        return r;
      }(), a.mode.CFB;
    });
  }(oi)), oi.exports;
}
var li = { exports: {} }, ws;
function Zp() {
  return ws || (ws = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return a.mode.CTR = function() {
        var r = a.lib.BlockCipherMode.extend(), n = r.Encryptor = r.extend({
          processBlock: function(i, s) {
            var o = this._cipher, u = o.blockSize, l = this._iv, c = this._counter;
            l && (c = this._counter = l.slice(0), this._iv = void 0);
            var f = c.slice(0);
            o.encryptBlock(f, 0), c[u - 1] = c[u - 1] + 1 | 0;
            for (var d = 0; d < u; d++)
              i[s + d] ^= f[d];
          }
        });
        return r.Decryptor = n, r;
      }(), a.mode.CTR;
    });
  }(li)), li.exports;
}
var ci = { exports: {} }, Ds;
function Jp() {
  return Ds || (Ds = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      /** @preserve
       * Counter block mode compatible with  Dr Brian Gladman fileenc.c
       * derived from CryptoJS.mode.CTR
       * Jan Hruby jhruby.web@gmail.com
       */
      return a.mode.CTRGladman = function() {
        var r = a.lib.BlockCipherMode.extend();
        function n(o) {
          if ((o >> 24 & 255) === 255) {
            var u = o >> 16 & 255, l = o >> 8 & 255, c = o & 255;
            u === 255 ? (u = 0, l === 255 ? (l = 0, c === 255 ? c = 0 : ++c) : ++l) : ++u, o = 0, o += u << 16, o += l << 8, o += c;
          } else
            o += 1 << 24;
          return o;
        }
        function i(o) {
          return (o[0] = n(o[0])) === 0 && (o[1] = n(o[1])), o;
        }
        var s = r.Encryptor = r.extend({
          processBlock: function(o, u) {
            var l = this._cipher, c = l.blockSize, f = this._iv, d = this._counter;
            f && (d = this._counter = f.slice(0), this._iv = void 0), i(d);
            var v = d.slice(0);
            l.encryptBlock(v, 0);
            for (var p = 0; p < c; p++)
              o[u + p] ^= v[p];
          }
        });
        return r.Decryptor = s, r;
      }(), a.mode.CTRGladman;
    });
  }(ci)), ci.exports;
}
var ui = { exports: {} }, Ns;
function eh() {
  return Ns || (Ns = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return a.mode.OFB = function() {
        var r = a.lib.BlockCipherMode.extend(), n = r.Encryptor = r.extend({
          processBlock: function(i, s) {
            var o = this._cipher, u = o.blockSize, l = this._iv, c = this._keystream;
            l && (c = this._keystream = l.slice(0), this._iv = void 0), o.encryptBlock(c, 0);
            for (var f = 0; f < u; f++)
              i[s + f] ^= c[f];
          }
        });
        return r.Decryptor = n, r;
      }(), a.mode.OFB;
    });
  }(ui)), ui.exports;
}
var fi = { exports: {} }, Rs;
function th() {
  return Rs || (Rs = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return a.mode.ECB = function() {
        var r = a.lib.BlockCipherMode.extend();
        return r.Encryptor = r.extend({
          processBlock: function(n, i) {
            this._cipher.encryptBlock(n, i);
          }
        }), r.Decryptor = r.extend({
          processBlock: function(n, i) {
            this._cipher.decryptBlock(n, i);
          }
        }), r;
      }(), a.mode.ECB;
    });
  }(fi)), fi.exports;
}
var di = { exports: {} }, ks;
function rh() {
  return ks || (ks = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return a.pad.AnsiX923 = {
        pad: function(r, n) {
          var i = r.sigBytes, s = n * 4, o = s - i % s, u = i + o - 1;
          r.clamp(), r.words[u >>> 2] |= o << 24 - u % 4 * 8, r.sigBytes += o;
        },
        unpad: function(r) {
          var n = r.words[r.sigBytes - 1 >>> 2] & 255;
          r.sigBytes -= n;
        }
      }, a.pad.Ansix923;
    });
  }(di)), di.exports;
}
var pi = { exports: {} }, Fs;
function ah() {
  return Fs || (Fs = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return a.pad.Iso10126 = {
        pad: function(r, n) {
          var i = n * 4, s = i - r.sigBytes % i;
          r.concat(a.lib.WordArray.random(s - 1)).concat(a.lib.WordArray.create([s << 24], 1));
        },
        unpad: function(r) {
          var n = r.words[r.sigBytes - 1 >>> 2] & 255;
          r.sigBytes -= n;
        }
      }, a.pad.Iso10126;
    });
  }(pi)), pi.exports;
}
var hi = { exports: {} }, Ls;
function nh() {
  return Ls || (Ls = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return a.pad.Iso97971 = {
        pad: function(r, n) {
          r.concat(a.lib.WordArray.create([2147483648], 1)), a.pad.ZeroPadding.pad(r, n);
        },
        unpad: function(r) {
          a.pad.ZeroPadding.unpad(r), r.sigBytes--;
        }
      }, a.pad.Iso97971;
    });
  }(hi)), hi.exports;
}
var vi = { exports: {} }, Ps;
function ih() {
  return Ps || (Ps = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return a.pad.ZeroPadding = {
        pad: function(r, n) {
          var i = n * 4;
          r.clamp(), r.sigBytes += i - (r.sigBytes % i || i);
        },
        unpad: function(r) {
          for (var n = r.words, i = r.sigBytes - 1, i = r.sigBytes - 1; i >= 0; i--)
            if (n[i >>> 2] >>> 24 - i % 4 * 8 & 255) {
              r.sigBytes = i + 1;
              break;
            }
        }
      }, a.pad.ZeroPadding;
    });
  }(vi)), vi.exports;
}
var xi = { exports: {} }, Us;
function sh() {
  return Us || (Us = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return a.pad.NoPadding = {
        pad: function() {
        },
        unpad: function() {
        }
      }, a.pad.NoPadding;
    });
  }(xi)), xi.exports;
}
var gi = { exports: {} }, Os;
function oh() {
  return Os || (Os = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), dt());
    })(we, function(a) {
      return function(r) {
        var n = a, i = n.lib, s = i.CipherParams, o = n.enc, u = o.Hex, l = n.format;
        l.Hex = {
          /**
           * Converts the ciphertext of a cipher params object to a hexadecimally encoded string.
           *
           * @param {CipherParams} cipherParams The cipher params object.
           *
           * @return {string} The hexadecimally encoded string.
           *
           * @static
           *
           * @example
           *
           *     var hexString = CryptoJS.format.Hex.stringify(cipherParams);
           */
          stringify: function(c) {
            return c.ciphertext.toString(u);
          },
          /**
           * Converts a hexadecimally encoded ciphertext string to a cipher params object.
           *
           * @param {string} input The hexadecimally encoded string.
           *
           * @return {CipherParams} The cipher params object.
           *
           * @static
           *
           * @example
           *
           *     var cipherParams = CryptoJS.format.Hex.parse(hexString);
           */
          parse: function(c) {
            var f = u.parse(c);
            return s.create({ ciphertext: f });
          }
        };
      }(), a.format.Hex;
    });
  }(gi)), gi.exports;
}
var yi = { exports: {} }, Vs;
function lh() {
  return Vs || (Vs = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), Mr(), Kr(), br(), dt());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.BlockCipher, s = r.algo, o = [], u = [], l = [], c = [], f = [], d = [], v = [], p = [], h = [], x = [];
        (function() {
          for (var m = [], C = 0; C < 256; C++)
            C < 128 ? m[C] = C << 1 : m[C] = C << 1 ^ 283;
          for (var S = 0, _ = 0, C = 0; C < 256; C++) {
            var T = _ ^ _ << 1 ^ _ << 2 ^ _ << 3 ^ _ << 4;
            T = T >>> 8 ^ T & 255 ^ 99, o[S] = T, u[T] = S;
            var D = m[S], P = m[D], I = m[P], B = m[T] * 257 ^ T * 16843008;
            l[S] = B << 24 | B >>> 8, c[S] = B << 16 | B >>> 16, f[S] = B << 8 | B >>> 24, d[S] = B;
            var B = I * 16843009 ^ P * 65537 ^ D * 257 ^ S * 16843008;
            v[T] = B << 24 | B >>> 8, p[T] = B << 16 | B >>> 16, h[T] = B << 8 | B >>> 24, x[T] = B, S ? (S = D ^ m[m[m[I ^ D]]], _ ^= m[m[_]]) : S = _ = 1;
          }
        })();
        var y = [0, 1, 2, 4, 8, 16, 32, 64, 128, 27, 54], g = s.AES = i.extend({
          _doReset: function() {
            var m;
            if (!(this._nRounds && this._keyPriorReset === this._key)) {
              for (var C = this._keyPriorReset = this._key, S = C.words, _ = C.sigBytes / 4, T = this._nRounds = _ + 6, D = (T + 1) * 4, P = this._keySchedule = [], I = 0; I < D; I++)
                I < _ ? P[I] = S[I] : (m = P[I - 1], I % _ ? _ > 6 && I % _ == 4 && (m = o[m >>> 24] << 24 | o[m >>> 16 & 255] << 16 | o[m >>> 8 & 255] << 8 | o[m & 255]) : (m = m << 8 | m >>> 24, m = o[m >>> 24] << 24 | o[m >>> 16 & 255] << 16 | o[m >>> 8 & 255] << 8 | o[m & 255], m ^= y[I / _ | 0] << 24), P[I] = P[I - _] ^ m);
              for (var B = this._invKeySchedule = [], R = 0; R < D; R++) {
                var I = D - R;
                if (R % 4)
                  var m = P[I];
                else
                  var m = P[I - 4];
                R < 4 || I <= 4 ? B[R] = m : B[R] = v[o[m >>> 24]] ^ p[o[m >>> 16 & 255]] ^ h[o[m >>> 8 & 255]] ^ x[o[m & 255]];
              }
            }
          },
          encryptBlock: function(m, C) {
            this._doCryptBlock(m, C, this._keySchedule, l, c, f, d, o);
          },
          decryptBlock: function(m, C) {
            var S = m[C + 1];
            m[C + 1] = m[C + 3], m[C + 3] = S, this._doCryptBlock(m, C, this._invKeySchedule, v, p, h, x, u);
            var S = m[C + 1];
            m[C + 1] = m[C + 3], m[C + 3] = S;
          },
          _doCryptBlock: function(m, C, S, _, T, D, P, I) {
            for (var B = this._nRounds, R = m[C] ^ S[0], F = m[C + 1] ^ S[1], U = m[C + 2] ^ S[2], M = m[C + 3] ^ S[3], ee = 4, q = 1; q < B; q++) {
              var fe = _[R >>> 24] ^ T[F >>> 16 & 255] ^ D[U >>> 8 & 255] ^ P[M & 255] ^ S[ee++], Ee = _[F >>> 24] ^ T[U >>> 16 & 255] ^ D[M >>> 8 & 255] ^ P[R & 255] ^ S[ee++], xe = _[U >>> 24] ^ T[M >>> 16 & 255] ^ D[R >>> 8 & 255] ^ P[F & 255] ^ S[ee++], O = _[M >>> 24] ^ T[R >>> 16 & 255] ^ D[F >>> 8 & 255] ^ P[U & 255] ^ S[ee++];
              R = fe, F = Ee, U = xe, M = O;
            }
            var fe = (I[R >>> 24] << 24 | I[F >>> 16 & 255] << 16 | I[U >>> 8 & 255] << 8 | I[M & 255]) ^ S[ee++], Ee = (I[F >>> 24] << 24 | I[U >>> 16 & 255] << 16 | I[M >>> 8 & 255] << 8 | I[R & 255]) ^ S[ee++], xe = (I[U >>> 24] << 24 | I[M >>> 16 & 255] << 16 | I[R >>> 8 & 255] << 8 | I[F & 255]) ^ S[ee++], O = (I[M >>> 24] << 24 | I[R >>> 16 & 255] << 16 | I[F >>> 8 & 255] << 8 | I[U & 255]) ^ S[ee++];
            m[C] = fe, m[C + 1] = Ee, m[C + 2] = xe, m[C + 3] = O;
          },
          keySize: 256 / 32
        });
        r.AES = i._createHelper(g);
      }(), a.AES;
    });
  }(yi)), yi.exports;
}
var mi = { exports: {} }, Ms;
function ch() {
  return Ms || (Ms = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), Mr(), Kr(), br(), dt());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.WordArray, s = n.BlockCipher, o = r.algo, u = [
          57,
          49,
          41,
          33,
          25,
          17,
          9,
          1,
          58,
          50,
          42,
          34,
          26,
          18,
          10,
          2,
          59,
          51,
          43,
          35,
          27,
          19,
          11,
          3,
          60,
          52,
          44,
          36,
          63,
          55,
          47,
          39,
          31,
          23,
          15,
          7,
          62,
          54,
          46,
          38,
          30,
          22,
          14,
          6,
          61,
          53,
          45,
          37,
          29,
          21,
          13,
          5,
          28,
          20,
          12,
          4
        ], l = [
          14,
          17,
          11,
          24,
          1,
          5,
          3,
          28,
          15,
          6,
          21,
          10,
          23,
          19,
          12,
          4,
          26,
          8,
          16,
          7,
          27,
          20,
          13,
          2,
          41,
          52,
          31,
          37,
          47,
          55,
          30,
          40,
          51,
          45,
          33,
          48,
          44,
          49,
          39,
          56,
          34,
          53,
          46,
          42,
          50,
          36,
          29,
          32
        ], c = [1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28], f = [
          {
            0: 8421888,
            268435456: 32768,
            536870912: 8421378,
            805306368: 2,
            1073741824: 512,
            1342177280: 8421890,
            1610612736: 8389122,
            1879048192: 8388608,
            2147483648: 514,
            2415919104: 8389120,
            2684354560: 33280,
            2952790016: 8421376,
            3221225472: 32770,
            3489660928: 8388610,
            3758096384: 0,
            4026531840: 33282,
            134217728: 0,
            402653184: 8421890,
            671088640: 33282,
            939524096: 32768,
            1207959552: 8421888,
            1476395008: 512,
            1744830464: 8421378,
            2013265920: 2,
            2281701376: 8389120,
            2550136832: 33280,
            2818572288: 8421376,
            3087007744: 8389122,
            3355443200: 8388610,
            3623878656: 32770,
            3892314112: 514,
            4160749568: 8388608,
            1: 32768,
            268435457: 2,
            536870913: 8421888,
            805306369: 8388608,
            1073741825: 8421378,
            1342177281: 33280,
            1610612737: 512,
            1879048193: 8389122,
            2147483649: 8421890,
            2415919105: 8421376,
            2684354561: 8388610,
            2952790017: 33282,
            3221225473: 514,
            3489660929: 8389120,
            3758096385: 32770,
            4026531841: 0,
            134217729: 8421890,
            402653185: 8421376,
            671088641: 8388608,
            939524097: 512,
            1207959553: 32768,
            1476395009: 8388610,
            1744830465: 2,
            2013265921: 33282,
            2281701377: 32770,
            2550136833: 8389122,
            2818572289: 514,
            3087007745: 8421888,
            3355443201: 8389120,
            3623878657: 0,
            3892314113: 33280,
            4160749569: 8421378
          },
          {
            0: 1074282512,
            16777216: 16384,
            33554432: 524288,
            50331648: 1074266128,
            67108864: 1073741840,
            83886080: 1074282496,
            100663296: 1073758208,
            117440512: 16,
            134217728: 540672,
            150994944: 1073758224,
            167772160: 1073741824,
            184549376: 540688,
            201326592: 524304,
            218103808: 0,
            234881024: 16400,
            251658240: 1074266112,
            8388608: 1073758208,
            25165824: 540688,
            41943040: 16,
            58720256: 1073758224,
            75497472: 1074282512,
            92274688: 1073741824,
            109051904: 524288,
            125829120: 1074266128,
            142606336: 524304,
            159383552: 0,
            176160768: 16384,
            192937984: 1074266112,
            209715200: 1073741840,
            226492416: 540672,
            243269632: 1074282496,
            260046848: 16400,
            268435456: 0,
            285212672: 1074266128,
            301989888: 1073758224,
            318767104: 1074282496,
            335544320: 1074266112,
            352321536: 16,
            369098752: 540688,
            385875968: 16384,
            402653184: 16400,
            419430400: 524288,
            436207616: 524304,
            452984832: 1073741840,
            469762048: 540672,
            486539264: 1073758208,
            503316480: 1073741824,
            520093696: 1074282512,
            276824064: 540688,
            293601280: 524288,
            310378496: 1074266112,
            327155712: 16384,
            343932928: 1073758208,
            360710144: 1074282512,
            377487360: 16,
            394264576: 1073741824,
            411041792: 1074282496,
            427819008: 1073741840,
            444596224: 1073758224,
            461373440: 524304,
            478150656: 0,
            494927872: 16400,
            511705088: 1074266128,
            528482304: 540672
          },
          {
            0: 260,
            1048576: 0,
            2097152: 67109120,
            3145728: 65796,
            4194304: 65540,
            5242880: 67108868,
            6291456: 67174660,
            7340032: 67174400,
            8388608: 67108864,
            9437184: 67174656,
            10485760: 65792,
            11534336: 67174404,
            12582912: 67109124,
            13631488: 65536,
            14680064: 4,
            15728640: 256,
            524288: 67174656,
            1572864: 67174404,
            2621440: 0,
            3670016: 67109120,
            4718592: 67108868,
            5767168: 65536,
            6815744: 65540,
            7864320: 260,
            8912896: 4,
            9961472: 256,
            11010048: 67174400,
            12058624: 65796,
            13107200: 65792,
            14155776: 67109124,
            15204352: 67174660,
            16252928: 67108864,
            16777216: 67174656,
            17825792: 65540,
            18874368: 65536,
            19922944: 67109120,
            20971520: 256,
            22020096: 67174660,
            23068672: 67108868,
            24117248: 0,
            25165824: 67109124,
            26214400: 67108864,
            27262976: 4,
            28311552: 65792,
            29360128: 67174400,
            30408704: 260,
            31457280: 65796,
            32505856: 67174404,
            17301504: 67108864,
            18350080: 260,
            19398656: 67174656,
            20447232: 0,
            21495808: 65540,
            22544384: 67109120,
            23592960: 256,
            24641536: 67174404,
            25690112: 65536,
            26738688: 67174660,
            27787264: 65796,
            28835840: 67108868,
            29884416: 67109124,
            30932992: 67174400,
            31981568: 4,
            33030144: 65792
          },
          {
            0: 2151682048,
            65536: 2147487808,
            131072: 4198464,
            196608: 2151677952,
            262144: 0,
            327680: 4198400,
            393216: 2147483712,
            458752: 4194368,
            524288: 2147483648,
            589824: 4194304,
            655360: 64,
            720896: 2147487744,
            786432: 2151678016,
            851968: 4160,
            917504: 4096,
            983040: 2151682112,
            32768: 2147487808,
            98304: 64,
            163840: 2151678016,
            229376: 2147487744,
            294912: 4198400,
            360448: 2151682112,
            425984: 0,
            491520: 2151677952,
            557056: 4096,
            622592: 2151682048,
            688128: 4194304,
            753664: 4160,
            819200: 2147483648,
            884736: 4194368,
            950272: 4198464,
            1015808: 2147483712,
            1048576: 4194368,
            1114112: 4198400,
            1179648: 2147483712,
            1245184: 0,
            1310720: 4160,
            1376256: 2151678016,
            1441792: 2151682048,
            1507328: 2147487808,
            1572864: 2151682112,
            1638400: 2147483648,
            1703936: 2151677952,
            1769472: 4198464,
            1835008: 2147487744,
            1900544: 4194304,
            1966080: 64,
            2031616: 4096,
            1081344: 2151677952,
            1146880: 2151682112,
            1212416: 0,
            1277952: 4198400,
            1343488: 4194368,
            1409024: 2147483648,
            1474560: 2147487808,
            1540096: 64,
            1605632: 2147483712,
            1671168: 4096,
            1736704: 2147487744,
            1802240: 2151678016,
            1867776: 4160,
            1933312: 2151682048,
            1998848: 4194304,
            2064384: 4198464
          },
          {
            0: 128,
            4096: 17039360,
            8192: 262144,
            12288: 536870912,
            16384: 537133184,
            20480: 16777344,
            24576: 553648256,
            28672: 262272,
            32768: 16777216,
            36864: 537133056,
            40960: 536871040,
            45056: 553910400,
            49152: 553910272,
            53248: 0,
            57344: 17039488,
            61440: 553648128,
            2048: 17039488,
            6144: 553648256,
            10240: 128,
            14336: 17039360,
            18432: 262144,
            22528: 537133184,
            26624: 553910272,
            30720: 536870912,
            34816: 537133056,
            38912: 0,
            43008: 553910400,
            47104: 16777344,
            51200: 536871040,
            55296: 553648128,
            59392: 16777216,
            63488: 262272,
            65536: 262144,
            69632: 128,
            73728: 536870912,
            77824: 553648256,
            81920: 16777344,
            86016: 553910272,
            90112: 537133184,
            94208: 16777216,
            98304: 553910400,
            102400: 553648128,
            106496: 17039360,
            110592: 537133056,
            114688: 262272,
            118784: 536871040,
            122880: 0,
            126976: 17039488,
            67584: 553648256,
            71680: 16777216,
            75776: 17039360,
            79872: 537133184,
            83968: 536870912,
            88064: 17039488,
            92160: 128,
            96256: 553910272,
            100352: 262272,
            104448: 553910400,
            108544: 0,
            112640: 553648128,
            116736: 16777344,
            120832: 262144,
            124928: 537133056,
            129024: 536871040
          },
          {
            0: 268435464,
            256: 8192,
            512: 270532608,
            768: 270540808,
            1024: 268443648,
            1280: 2097152,
            1536: 2097160,
            1792: 268435456,
            2048: 0,
            2304: 268443656,
            2560: 2105344,
            2816: 8,
            3072: 270532616,
            3328: 2105352,
            3584: 8200,
            3840: 270540800,
            128: 270532608,
            384: 270540808,
            640: 8,
            896: 2097152,
            1152: 2105352,
            1408: 268435464,
            1664: 268443648,
            1920: 8200,
            2176: 2097160,
            2432: 8192,
            2688: 268443656,
            2944: 270532616,
            3200: 0,
            3456: 270540800,
            3712: 2105344,
            3968: 268435456,
            4096: 268443648,
            4352: 270532616,
            4608: 270540808,
            4864: 8200,
            5120: 2097152,
            5376: 268435456,
            5632: 268435464,
            5888: 2105344,
            6144: 2105352,
            6400: 0,
            6656: 8,
            6912: 270532608,
            7168: 8192,
            7424: 268443656,
            7680: 270540800,
            7936: 2097160,
            4224: 8,
            4480: 2105344,
            4736: 2097152,
            4992: 268435464,
            5248: 268443648,
            5504: 8200,
            5760: 270540808,
            6016: 270532608,
            6272: 270540800,
            6528: 270532616,
            6784: 8192,
            7040: 2105352,
            7296: 2097160,
            7552: 0,
            7808: 268435456,
            8064: 268443656
          },
          {
            0: 1048576,
            16: 33555457,
            32: 1024,
            48: 1049601,
            64: 34604033,
            80: 0,
            96: 1,
            112: 34603009,
            128: 33555456,
            144: 1048577,
            160: 33554433,
            176: 34604032,
            192: 34603008,
            208: 1025,
            224: 1049600,
            240: 33554432,
            8: 34603009,
            24: 0,
            40: 33555457,
            56: 34604032,
            72: 1048576,
            88: 33554433,
            104: 33554432,
            120: 1025,
            136: 1049601,
            152: 33555456,
            168: 34603008,
            184: 1048577,
            200: 1024,
            216: 34604033,
            232: 1,
            248: 1049600,
            256: 33554432,
            272: 1048576,
            288: 33555457,
            304: 34603009,
            320: 1048577,
            336: 33555456,
            352: 34604032,
            368: 1049601,
            384: 1025,
            400: 34604033,
            416: 1049600,
            432: 1,
            448: 0,
            464: 34603008,
            480: 33554433,
            496: 1024,
            264: 1049600,
            280: 33555457,
            296: 34603009,
            312: 1,
            328: 33554432,
            344: 1048576,
            360: 1025,
            376: 34604032,
            392: 33554433,
            408: 34603008,
            424: 0,
            440: 34604033,
            456: 1049601,
            472: 1024,
            488: 33555456,
            504: 1048577
          },
          {
            0: 134219808,
            1: 131072,
            2: 134217728,
            3: 32,
            4: 131104,
            5: 134350880,
            6: 134350848,
            7: 2048,
            8: 134348800,
            9: 134219776,
            10: 133120,
            11: 134348832,
            12: 2080,
            13: 0,
            14: 134217760,
            15: 133152,
            2147483648: 2048,
            2147483649: 134350880,
            2147483650: 134219808,
            2147483651: 134217728,
            2147483652: 134348800,
            2147483653: 133120,
            2147483654: 133152,
            2147483655: 32,
            2147483656: 134217760,
            2147483657: 2080,
            2147483658: 131104,
            2147483659: 134350848,
            2147483660: 0,
            2147483661: 134348832,
            2147483662: 134219776,
            2147483663: 131072,
            16: 133152,
            17: 134350848,
            18: 32,
            19: 2048,
            20: 134219776,
            21: 134217760,
            22: 134348832,
            23: 131072,
            24: 0,
            25: 131104,
            26: 134348800,
            27: 134219808,
            28: 134350880,
            29: 133120,
            30: 2080,
            31: 134217728,
            2147483664: 131072,
            2147483665: 2048,
            2147483666: 134348832,
            2147483667: 133152,
            2147483668: 32,
            2147483669: 134348800,
            2147483670: 134217728,
            2147483671: 134219808,
            2147483672: 134350880,
            2147483673: 134217760,
            2147483674: 134219776,
            2147483675: 0,
            2147483676: 133120,
            2147483677: 2080,
            2147483678: 131104,
            2147483679: 134350848
          }
        ], d = [
          4160749569,
          528482304,
          33030144,
          2064384,
          129024,
          8064,
          504,
          2147483679
        ], v = o.DES = s.extend({
          _doReset: function() {
            for (var y = this._key, g = y.words, m = [], C = 0; C < 56; C++) {
              var S = u[C] - 1;
              m[C] = g[S >>> 5] >>> 31 - S % 32 & 1;
            }
            for (var _ = this._subKeys = [], T = 0; T < 16; T++) {
              for (var D = _[T] = [], P = c[T], C = 0; C < 24; C++)
                D[C / 6 | 0] |= m[(l[C] - 1 + P) % 28] << 31 - C % 6, D[4 + (C / 6 | 0)] |= m[28 + (l[C + 24] - 1 + P) % 28] << 31 - C % 6;
              D[0] = D[0] << 1 | D[0] >>> 31;
              for (var C = 1; C < 7; C++)
                D[C] = D[C] >>> (C - 1) * 4 + 3;
              D[7] = D[7] << 5 | D[7] >>> 27;
            }
            for (var I = this._invSubKeys = [], C = 0; C < 16; C++)
              I[C] = _[15 - C];
          },
          encryptBlock: function(y, g) {
            this._doCryptBlock(y, g, this._subKeys);
          },
          decryptBlock: function(y, g) {
            this._doCryptBlock(y, g, this._invSubKeys);
          },
          _doCryptBlock: function(y, g, m) {
            this._lBlock = y[g], this._rBlock = y[g + 1], p.call(this, 4, 252645135), p.call(this, 16, 65535), h.call(this, 2, 858993459), h.call(this, 8, 16711935), p.call(this, 1, 1431655765);
            for (var C = 0; C < 16; C++) {
              for (var S = m[C], _ = this._lBlock, T = this._rBlock, D = 0, P = 0; P < 8; P++)
                D |= f[P][((T ^ S[P]) & d[P]) >>> 0];
              this._lBlock = T, this._rBlock = _ ^ D;
            }
            var I = this._lBlock;
            this._lBlock = this._rBlock, this._rBlock = I, p.call(this, 1, 1431655765), h.call(this, 8, 16711935), h.call(this, 2, 858993459), p.call(this, 16, 65535), p.call(this, 4, 252645135), y[g] = this._lBlock, y[g + 1] = this._rBlock;
          },
          keySize: 64 / 32,
          ivSize: 64 / 32,
          blockSize: 64 / 32
        });
        function p(y, g) {
          var m = (this._lBlock >>> y ^ this._rBlock) & g;
          this._rBlock ^= m, this._lBlock ^= m << y;
        }
        function h(y, g) {
          var m = (this._rBlock >>> y ^ this._lBlock) & g;
          this._lBlock ^= m, this._rBlock ^= m << y;
        }
        r.DES = s._createHelper(v);
        var x = o.TripleDES = s.extend({
          _doReset: function() {
            var y = this._key, g = y.words;
            if (g.length !== 2 && g.length !== 4 && g.length < 6)
              throw new Error("Invalid key length - 3DES requires the key length to be 64, 128, 192 or >192.");
            var m = g.slice(0, 2), C = g.length < 4 ? g.slice(0, 2) : g.slice(2, 4), S = g.length < 6 ? g.slice(0, 2) : g.slice(4, 6);
            this._des1 = v.createEncryptor(i.create(m)), this._des2 = v.createEncryptor(i.create(C)), this._des3 = v.createEncryptor(i.create(S));
          },
          encryptBlock: function(y, g) {
            this._des1.encryptBlock(y, g), this._des2.decryptBlock(y, g), this._des3.encryptBlock(y, g);
          },
          decryptBlock: function(y, g) {
            this._des3.decryptBlock(y, g), this._des2.encryptBlock(y, g), this._des1.decryptBlock(y, g);
          },
          keySize: 192 / 32,
          ivSize: 64 / 32,
          blockSize: 64 / 32
        });
        r.TripleDES = s._createHelper(x);
      }(), a.TripleDES;
    });
  }(mi)), mi.exports;
}
var Ci = { exports: {} }, Ks;
function uh() {
  return Ks || (Ks = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), Mr(), Kr(), br(), dt());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.StreamCipher, s = r.algo, o = s.RC4 = i.extend({
          _doReset: function() {
            for (var c = this._key, f = c.words, d = c.sigBytes, v = this._S = [], p = 0; p < 256; p++)
              v[p] = p;
            for (var p = 0, h = 0; p < 256; p++) {
              var x = p % d, y = f[x >>> 2] >>> 24 - x % 4 * 8 & 255;
              h = (h + v[p] + y) % 256;
              var g = v[p];
              v[p] = v[h], v[h] = g;
            }
            this._i = this._j = 0;
          },
          _doProcessBlock: function(c, f) {
            c[f] ^= u.call(this);
          },
          keySize: 256 / 32,
          ivSize: 0
        });
        function u() {
          for (var c = this._S, f = this._i, d = this._j, v = 0, p = 0; p < 4; p++) {
            f = (f + 1) % 256, d = (d + c[f]) % 256;
            var h = c[f];
            c[f] = c[d], c[d] = h, v |= c[(c[f] + c[d]) % 256] << 24 - p * 8;
          }
          return this._i = f, this._j = d, v;
        }
        r.RC4 = i._createHelper(o);
        var l = s.RC4Drop = o.extend({
          /**
           * Configuration options.
           *
           * @property {number} drop The number of keystream words to drop. Default 192
           */
          cfg: o.cfg.extend({
            drop: 192
          }),
          _doReset: function() {
            o._doReset.call(this);
            for (var c = this.cfg.drop; c > 0; c--)
              u.call(this);
          }
        });
        r.RC4Drop = i._createHelper(l);
      }(), a.RC4;
    });
  }(Ci)), Ci.exports;
}
var Ei = { exports: {} }, Hs;
function fh() {
  return Hs || (Hs = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), Mr(), Kr(), br(), dt());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.StreamCipher, s = r.algo, o = [], u = [], l = [], c = s.Rabbit = i.extend({
          _doReset: function() {
            for (var d = this._key.words, v = this.cfg.iv, p = 0; p < 4; p++)
              d[p] = (d[p] << 8 | d[p] >>> 24) & 16711935 | (d[p] << 24 | d[p] >>> 8) & 4278255360;
            var h = this._X = [
              d[0],
              d[3] << 16 | d[2] >>> 16,
              d[1],
              d[0] << 16 | d[3] >>> 16,
              d[2],
              d[1] << 16 | d[0] >>> 16,
              d[3],
              d[2] << 16 | d[1] >>> 16
            ], x = this._C = [
              d[2] << 16 | d[2] >>> 16,
              d[0] & 4294901760 | d[1] & 65535,
              d[3] << 16 | d[3] >>> 16,
              d[1] & 4294901760 | d[2] & 65535,
              d[0] << 16 | d[0] >>> 16,
              d[2] & 4294901760 | d[3] & 65535,
              d[1] << 16 | d[1] >>> 16,
              d[3] & 4294901760 | d[0] & 65535
            ];
            this._b = 0;
            for (var p = 0; p < 4; p++)
              f.call(this);
            for (var p = 0; p < 8; p++)
              x[p] ^= h[p + 4 & 7];
            if (v) {
              var y = v.words, g = y[0], m = y[1], C = (g << 8 | g >>> 24) & 16711935 | (g << 24 | g >>> 8) & 4278255360, S = (m << 8 | m >>> 24) & 16711935 | (m << 24 | m >>> 8) & 4278255360, _ = C >>> 16 | S & 4294901760, T = S << 16 | C & 65535;
              x[0] ^= C, x[1] ^= _, x[2] ^= S, x[3] ^= T, x[4] ^= C, x[5] ^= _, x[6] ^= S, x[7] ^= T;
              for (var p = 0; p < 4; p++)
                f.call(this);
            }
          },
          _doProcessBlock: function(d, v) {
            var p = this._X;
            f.call(this), o[0] = p[0] ^ p[5] >>> 16 ^ p[3] << 16, o[1] = p[2] ^ p[7] >>> 16 ^ p[5] << 16, o[2] = p[4] ^ p[1] >>> 16 ^ p[7] << 16, o[3] = p[6] ^ p[3] >>> 16 ^ p[1] << 16;
            for (var h = 0; h < 4; h++)
              o[h] = (o[h] << 8 | o[h] >>> 24) & 16711935 | (o[h] << 24 | o[h] >>> 8) & 4278255360, d[v + h] ^= o[h];
          },
          blockSize: 128 / 32,
          ivSize: 64 / 32
        });
        function f() {
          for (var d = this._X, v = this._C, p = 0; p < 8; p++)
            u[p] = v[p];
          v[0] = v[0] + 1295307597 + this._b | 0, v[1] = v[1] + 3545052371 + (v[0] >>> 0 < u[0] >>> 0 ? 1 : 0) | 0, v[2] = v[2] + 886263092 + (v[1] >>> 0 < u[1] >>> 0 ? 1 : 0) | 0, v[3] = v[3] + 1295307597 + (v[2] >>> 0 < u[2] >>> 0 ? 1 : 0) | 0, v[4] = v[4] + 3545052371 + (v[3] >>> 0 < u[3] >>> 0 ? 1 : 0) | 0, v[5] = v[5] + 886263092 + (v[4] >>> 0 < u[4] >>> 0 ? 1 : 0) | 0, v[6] = v[6] + 1295307597 + (v[5] >>> 0 < u[5] >>> 0 ? 1 : 0) | 0, v[7] = v[7] + 3545052371 + (v[6] >>> 0 < u[6] >>> 0 ? 1 : 0) | 0, this._b = v[7] >>> 0 < u[7] >>> 0 ? 1 : 0;
          for (var p = 0; p < 8; p++) {
            var h = d[p] + v[p], x = h & 65535, y = h >>> 16, g = ((x * x >>> 17) + x * y >>> 15) + y * y, m = ((h & 4294901760) * h | 0) + ((h & 65535) * h | 0);
            l[p] = g ^ m;
          }
          d[0] = l[0] + (l[7] << 16 | l[7] >>> 16) + (l[6] << 16 | l[6] >>> 16) | 0, d[1] = l[1] + (l[0] << 8 | l[0] >>> 24) + l[7] | 0, d[2] = l[2] + (l[1] << 16 | l[1] >>> 16) + (l[0] << 16 | l[0] >>> 16) | 0, d[3] = l[3] + (l[2] << 8 | l[2] >>> 24) + l[1] | 0, d[4] = l[4] + (l[3] << 16 | l[3] >>> 16) + (l[2] << 16 | l[2] >>> 16) | 0, d[5] = l[5] + (l[4] << 8 | l[4] >>> 24) + l[3] | 0, d[6] = l[6] + (l[5] << 16 | l[5] >>> 16) + (l[4] << 16 | l[4] >>> 16) | 0, d[7] = l[7] + (l[6] << 8 | l[6] >>> 24) + l[5] | 0;
        }
        r.Rabbit = i._createHelper(c);
      }(), a.Rabbit;
    });
  }(Ei)), Ei.exports;
}
var bi = { exports: {} }, zs;
function dh() {
  return zs || (zs = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), Mr(), Kr(), br(), dt());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.StreamCipher, s = r.algo, o = [], u = [], l = [], c = s.RabbitLegacy = i.extend({
          _doReset: function() {
            var d = this._key.words, v = this.cfg.iv, p = this._X = [
              d[0],
              d[3] << 16 | d[2] >>> 16,
              d[1],
              d[0] << 16 | d[3] >>> 16,
              d[2],
              d[1] << 16 | d[0] >>> 16,
              d[3],
              d[2] << 16 | d[1] >>> 16
            ], h = this._C = [
              d[2] << 16 | d[2] >>> 16,
              d[0] & 4294901760 | d[1] & 65535,
              d[3] << 16 | d[3] >>> 16,
              d[1] & 4294901760 | d[2] & 65535,
              d[0] << 16 | d[0] >>> 16,
              d[2] & 4294901760 | d[3] & 65535,
              d[1] << 16 | d[1] >>> 16,
              d[3] & 4294901760 | d[0] & 65535
            ];
            this._b = 0;
            for (var x = 0; x < 4; x++)
              f.call(this);
            for (var x = 0; x < 8; x++)
              h[x] ^= p[x + 4 & 7];
            if (v) {
              var y = v.words, g = y[0], m = y[1], C = (g << 8 | g >>> 24) & 16711935 | (g << 24 | g >>> 8) & 4278255360, S = (m << 8 | m >>> 24) & 16711935 | (m << 24 | m >>> 8) & 4278255360, _ = C >>> 16 | S & 4294901760, T = S << 16 | C & 65535;
              h[0] ^= C, h[1] ^= _, h[2] ^= S, h[3] ^= T, h[4] ^= C, h[5] ^= _, h[6] ^= S, h[7] ^= T;
              for (var x = 0; x < 4; x++)
                f.call(this);
            }
          },
          _doProcessBlock: function(d, v) {
            var p = this._X;
            f.call(this), o[0] = p[0] ^ p[5] >>> 16 ^ p[3] << 16, o[1] = p[2] ^ p[7] >>> 16 ^ p[5] << 16, o[2] = p[4] ^ p[1] >>> 16 ^ p[7] << 16, o[3] = p[6] ^ p[3] >>> 16 ^ p[1] << 16;
            for (var h = 0; h < 4; h++)
              o[h] = (o[h] << 8 | o[h] >>> 24) & 16711935 | (o[h] << 24 | o[h] >>> 8) & 4278255360, d[v + h] ^= o[h];
          },
          blockSize: 128 / 32,
          ivSize: 64 / 32
        });
        function f() {
          for (var d = this._X, v = this._C, p = 0; p < 8; p++)
            u[p] = v[p];
          v[0] = v[0] + 1295307597 + this._b | 0, v[1] = v[1] + 3545052371 + (v[0] >>> 0 < u[0] >>> 0 ? 1 : 0) | 0, v[2] = v[2] + 886263092 + (v[1] >>> 0 < u[1] >>> 0 ? 1 : 0) | 0, v[3] = v[3] + 1295307597 + (v[2] >>> 0 < u[2] >>> 0 ? 1 : 0) | 0, v[4] = v[4] + 3545052371 + (v[3] >>> 0 < u[3] >>> 0 ? 1 : 0) | 0, v[5] = v[5] + 886263092 + (v[4] >>> 0 < u[4] >>> 0 ? 1 : 0) | 0, v[6] = v[6] + 1295307597 + (v[5] >>> 0 < u[5] >>> 0 ? 1 : 0) | 0, v[7] = v[7] + 3545052371 + (v[6] >>> 0 < u[6] >>> 0 ? 1 : 0) | 0, this._b = v[7] >>> 0 < u[7] >>> 0 ? 1 : 0;
          for (var p = 0; p < 8; p++) {
            var h = d[p] + v[p], x = h & 65535, y = h >>> 16, g = ((x * x >>> 17) + x * y >>> 15) + y * y, m = ((h & 4294901760) * h | 0) + ((h & 65535) * h | 0);
            l[p] = g ^ m;
          }
          d[0] = l[0] + (l[7] << 16 | l[7] >>> 16) + (l[6] << 16 | l[6] >>> 16) | 0, d[1] = l[1] + (l[0] << 8 | l[0] >>> 24) + l[7] | 0, d[2] = l[2] + (l[1] << 16 | l[1] >>> 16) + (l[0] << 16 | l[0] >>> 16) | 0, d[3] = l[3] + (l[2] << 8 | l[2] >>> 24) + l[1] | 0, d[4] = l[4] + (l[3] << 16 | l[3] >>> 16) + (l[2] << 16 | l[2] >>> 16) | 0, d[5] = l[5] + (l[4] << 8 | l[4] >>> 24) + l[3] | 0, d[6] = l[6] + (l[5] << 16 | l[5] >>> 16) + (l[4] << 16 | l[4] >>> 16) | 0, d[7] = l[7] + (l[6] << 8 | l[6] >>> 24) + l[5] | 0;
        }
        r.RabbitLegacy = i._createHelper(c);
      }(), a.RabbitLegacy;
    });
  }(bi)), bi.exports;
}
var Si = { exports: {} }, $s;
function ph() {
  return $s || ($s = 1, function(e, t) {
    (function(a, r, n) {
      e.exports = r(Oe(), Mr(), Kr(), br(), dt());
    })(we, function(a) {
      return function() {
        var r = a, n = r.lib, i = n.BlockCipher, s = r.algo;
        const o = 16, u = [
          608135816,
          2242054355,
          320440878,
          57701188,
          2752067618,
          698298832,
          137296536,
          3964562569,
          1160258022,
          953160567,
          3193202383,
          887688300,
          3232508343,
          3380367581,
          1065670069,
          3041331479,
          2450970073,
          2306472731
        ], l = [
          [
            3509652390,
            2564797868,
            805139163,
            3491422135,
            3101798381,
            1780907670,
            3128725573,
            4046225305,
            614570311,
            3012652279,
            134345442,
            2240740374,
            1667834072,
            1901547113,
            2757295779,
            4103290238,
            227898511,
            1921955416,
            1904987480,
            2182433518,
            2069144605,
            3260701109,
            2620446009,
            720527379,
            3318853667,
            677414384,
            3393288472,
            3101374703,
            2390351024,
            1614419982,
            1822297739,
            2954791486,
            3608508353,
            3174124327,
            2024746970,
            1432378464,
            3864339955,
            2857741204,
            1464375394,
            1676153920,
            1439316330,
            715854006,
            3033291828,
            289532110,
            2706671279,
            2087905683,
            3018724369,
            1668267050,
            732546397,
            1947742710,
            3462151702,
            2609353502,
            2950085171,
            1814351708,
            2050118529,
            680887927,
            999245976,
            1800124847,
            3300911131,
            1713906067,
            1641548236,
            4213287313,
            1216130144,
            1575780402,
            4018429277,
            3917837745,
            3693486850,
            3949271944,
            596196993,
            3549867205,
            258830323,
            2213823033,
            772490370,
            2760122372,
            1774776394,
            2652871518,
            566650946,
            4142492826,
            1728879713,
            2882767088,
            1783734482,
            3629395816,
            2517608232,
            2874225571,
            1861159788,
            326777828,
            3124490320,
            2130389656,
            2716951837,
            967770486,
            1724537150,
            2185432712,
            2364442137,
            1164943284,
            2105845187,
            998989502,
            3765401048,
            2244026483,
            1075463327,
            1455516326,
            1322494562,
            910128902,
            469688178,
            1117454909,
            936433444,
            3490320968,
            3675253459,
            1240580251,
            122909385,
            2157517691,
            634681816,
            4142456567,
            3825094682,
            3061402683,
            2540495037,
            79693498,
            3249098678,
            1084186820,
            1583128258,
            426386531,
            1761308591,
            1047286709,
            322548459,
            995290223,
            1845252383,
            2603652396,
            3431023940,
            2942221577,
            3202600964,
            3727903485,
            1712269319,
            422464435,
            3234572375,
            1170764815,
            3523960633,
            3117677531,
            1434042557,
            442511882,
            3600875718,
            1076654713,
            1738483198,
            4213154764,
            2393238008,
            3677496056,
            1014306527,
            4251020053,
            793779912,
            2902807211,
            842905082,
            4246964064,
            1395751752,
            1040244610,
            2656851899,
            3396308128,
            445077038,
            3742853595,
            3577915638,
            679411651,
            2892444358,
            2354009459,
            1767581616,
            3150600392,
            3791627101,
            3102740896,
            284835224,
            4246832056,
            1258075500,
            768725851,
            2589189241,
            3069724005,
            3532540348,
            1274779536,
            3789419226,
            2764799539,
            1660621633,
            3471099624,
            4011903706,
            913787905,
            3497959166,
            737222580,
            2514213453,
            2928710040,
            3937242737,
            1804850592,
            3499020752,
            2949064160,
            2386320175,
            2390070455,
            2415321851,
            4061277028,
            2290661394,
            2416832540,
            1336762016,
            1754252060,
            3520065937,
            3014181293,
            791618072,
            3188594551,
            3933548030,
            2332172193,
            3852520463,
            3043980520,
            413987798,
            3465142937,
            3030929376,
            4245938359,
            2093235073,
            3534596313,
            375366246,
            2157278981,
            2479649556,
            555357303,
            3870105701,
            2008414854,
            3344188149,
            4221384143,
            3956125452,
            2067696032,
            3594591187,
            2921233993,
            2428461,
            544322398,
            577241275,
            1471733935,
            610547355,
            4027169054,
            1432588573,
            1507829418,
            2025931657,
            3646575487,
            545086370,
            48609733,
            2200306550,
            1653985193,
            298326376,
            1316178497,
            3007786442,
            2064951626,
            458293330,
            2589141269,
            3591329599,
            3164325604,
            727753846,
            2179363840,
            146436021,
            1461446943,
            4069977195,
            705550613,
            3059967265,
            3887724982,
            4281599278,
            3313849956,
            1404054877,
            2845806497,
            146425753,
            1854211946
          ],
          [
            1266315497,
            3048417604,
            3681880366,
            3289982499,
            290971e4,
            1235738493,
            2632868024,
            2414719590,
            3970600049,
            1771706367,
            1449415276,
            3266420449,
            422970021,
            1963543593,
            2690192192,
            3826793022,
            1062508698,
            1531092325,
            1804592342,
            2583117782,
            2714934279,
            4024971509,
            1294809318,
            4028980673,
            1289560198,
            2221992742,
            1669523910,
            35572830,
            157838143,
            1052438473,
            1016535060,
            1802137761,
            1753167236,
            1386275462,
            3080475397,
            2857371447,
            1040679964,
            2145300060,
            2390574316,
            1461121720,
            2956646967,
            4031777805,
            4028374788,
            33600511,
            2920084762,
            1018524850,
            629373528,
            3691585981,
            3515945977,
            2091462646,
            2486323059,
            586499841,
            988145025,
            935516892,
            3367335476,
            2599673255,
            2839830854,
            265290510,
            3972581182,
            2759138881,
            3795373465,
            1005194799,
            847297441,
            406762289,
            1314163512,
            1332590856,
            1866599683,
            4127851711,
            750260880,
            613907577,
            1450815602,
            3165620655,
            3734664991,
            3650291728,
            3012275730,
            3704569646,
            1427272223,
            778793252,
            1343938022,
            2676280711,
            2052605720,
            1946737175,
            3164576444,
            3914038668,
            3967478842,
            3682934266,
            1661551462,
            3294938066,
            4011595847,
            840292616,
            3712170807,
            616741398,
            312560963,
            711312465,
            1351876610,
            322626781,
            1910503582,
            271666773,
            2175563734,
            1594956187,
            70604529,
            3617834859,
            1007753275,
            1495573769,
            4069517037,
            2549218298,
            2663038764,
            504708206,
            2263041392,
            3941167025,
            2249088522,
            1514023603,
            1998579484,
            1312622330,
            694541497,
            2582060303,
            2151582166,
            1382467621,
            776784248,
            2618340202,
            3323268794,
            2497899128,
            2784771155,
            503983604,
            4076293799,
            907881277,
            423175695,
            432175456,
            1378068232,
            4145222326,
            3954048622,
            3938656102,
            3820766613,
            2793130115,
            2977904593,
            26017576,
            3274890735,
            3194772133,
            1700274565,
            1756076034,
            4006520079,
            3677328699,
            720338349,
            1533947780,
            354530856,
            688349552,
            3973924725,
            1637815568,
            332179504,
            3949051286,
            53804574,
            2852348879,
            3044236432,
            1282449977,
            3583942155,
            3416972820,
            4006381244,
            1617046695,
            2628476075,
            3002303598,
            1686838959,
            431878346,
            2686675385,
            1700445008,
            1080580658,
            1009431731,
            832498133,
            3223435511,
            2605976345,
            2271191193,
            2516031870,
            1648197032,
            4164389018,
            2548247927,
            300782431,
            375919233,
            238389289,
            3353747414,
            2531188641,
            2019080857,
            1475708069,
            455242339,
            2609103871,
            448939670,
            3451063019,
            1395535956,
            2413381860,
            1841049896,
            1491858159,
            885456874,
            4264095073,
            4001119347,
            1565136089,
            3898914787,
            1108368660,
            540939232,
            1173283510,
            2745871338,
            3681308437,
            4207628240,
            3343053890,
            4016749493,
            1699691293,
            1103962373,
            3625875870,
            2256883143,
            3830138730,
            1031889488,
            3479347698,
            1535977030,
            4236805024,
            3251091107,
            2132092099,
            1774941330,
            1199868427,
            1452454533,
            157007616,
            2904115357,
            342012276,
            595725824,
            1480756522,
            206960106,
            497939518,
            591360097,
            863170706,
            2375253569,
            3596610801,
            1814182875,
            2094937945,
            3421402208,
            1082520231,
            3463918190,
            2785509508,
            435703966,
            3908032597,
            1641649973,
            2842273706,
            3305899714,
            1510255612,
            2148256476,
            2655287854,
            3276092548,
            4258621189,
            236887753,
            3681803219,
            274041037,
            1734335097,
            3815195456,
            3317970021,
            1899903192,
            1026095262,
            4050517792,
            356393447,
            2410691914,
            3873677099,
            3682840055
          ],
          [
            3913112168,
            2491498743,
            4132185628,
            2489919796,
            1091903735,
            1979897079,
            3170134830,
            3567386728,
            3557303409,
            857797738,
            1136121015,
            1342202287,
            507115054,
            2535736646,
            337727348,
            3213592640,
            1301675037,
            2528481711,
            1895095763,
            1721773893,
            3216771564,
            62756741,
            2142006736,
            835421444,
            2531993523,
            1442658625,
            3659876326,
            2882144922,
            676362277,
            1392781812,
            170690266,
            3921047035,
            1759253602,
            3611846912,
            1745797284,
            664899054,
            1329594018,
            3901205900,
            3045908486,
            2062866102,
            2865634940,
            3543621612,
            3464012697,
            1080764994,
            553557557,
            3656615353,
            3996768171,
            991055499,
            499776247,
            1265440854,
            648242737,
            3940784050,
            980351604,
            3713745714,
            1749149687,
            3396870395,
            4211799374,
            3640570775,
            1161844396,
            3125318951,
            1431517754,
            545492359,
            4268468663,
            3499529547,
            1437099964,
            2702547544,
            3433638243,
            2581715763,
            2787789398,
            1060185593,
            1593081372,
            2418618748,
            4260947970,
            69676912,
            2159744348,
            86519011,
            2512459080,
            3838209314,
            1220612927,
            3339683548,
            133810670,
            1090789135,
            1078426020,
            1569222167,
            845107691,
            3583754449,
            4072456591,
            1091646820,
            628848692,
            1613405280,
            3757631651,
            526609435,
            236106946,
            48312990,
            2942717905,
            3402727701,
            1797494240,
            859738849,
            992217954,
            4005476642,
            2243076622,
            3870952857,
            3732016268,
            765654824,
            3490871365,
            2511836413,
            1685915746,
            3888969200,
            1414112111,
            2273134842,
            3281911079,
            4080962846,
            172450625,
            2569994100,
            980381355,
            4109958455,
            2819808352,
            2716589560,
            2568741196,
            3681446669,
            3329971472,
            1835478071,
            660984891,
            3704678404,
            4045999559,
            3422617507,
            3040415634,
            1762651403,
            1719377915,
            3470491036,
            2693910283,
            3642056355,
            3138596744,
            1364962596,
            2073328063,
            1983633131,
            926494387,
            3423689081,
            2150032023,
            4096667949,
            1749200295,
            3328846651,
            309677260,
            2016342300,
            1779581495,
            3079819751,
            111262694,
            1274766160,
            443224088,
            298511866,
            1025883608,
            3806446537,
            1145181785,
            168956806,
            3641502830,
            3584813610,
            1689216846,
            3666258015,
            3200248200,
            1692713982,
            2646376535,
            4042768518,
            1618508792,
            1610833997,
            3523052358,
            4130873264,
            2001055236,
            3610705100,
            2202168115,
            4028541809,
            2961195399,
            1006657119,
            2006996926,
            3186142756,
            1430667929,
            3210227297,
            1314452623,
            4074634658,
            4101304120,
            2273951170,
            1399257539,
            3367210612,
            3027628629,
            1190975929,
            2062231137,
            2333990788,
            2221543033,
            2438960610,
            1181637006,
            548689776,
            2362791313,
            3372408396,
            3104550113,
            3145860560,
            296247880,
            1970579870,
            3078560182,
            3769228297,
            1714227617,
            3291629107,
            3898220290,
            166772364,
            1251581989,
            493813264,
            448347421,
            195405023,
            2709975567,
            677966185,
            3703036547,
            1463355134,
            2715995803,
            1338867538,
            1343315457,
            2802222074,
            2684532164,
            233230375,
            2599980071,
            2000651841,
            3277868038,
            1638401717,
            4028070440,
            3237316320,
            6314154,
            819756386,
            300326615,
            590932579,
            1405279636,
            3267499572,
            3150704214,
            2428286686,
            3959192993,
            3461946742,
            1862657033,
            1266418056,
            963775037,
            2089974820,
            2263052895,
            1917689273,
            448879540,
            3550394620,
            3981727096,
            150775221,
            3627908307,
            1303187396,
            508620638,
            2975983352,
            2726630617,
            1817252668,
            1876281319,
            1457606340,
            908771278,
            3720792119,
            3617206836,
            2455994898,
            1729034894,
            1080033504
          ],
          [
            976866871,
            3556439503,
            2881648439,
            1522871579,
            1555064734,
            1336096578,
            3548522304,
            2579274686,
            3574697629,
            3205460757,
            3593280638,
            3338716283,
            3079412587,
            564236357,
            2993598910,
            1781952180,
            1464380207,
            3163844217,
            3332601554,
            1699332808,
            1393555694,
            1183702653,
            3581086237,
            1288719814,
            691649499,
            2847557200,
            2895455976,
            3193889540,
            2717570544,
            1781354906,
            1676643554,
            2592534050,
            3230253752,
            1126444790,
            2770207658,
            2633158820,
            2210423226,
            2615765581,
            2414155088,
            3127139286,
            673620729,
            2805611233,
            1269405062,
            4015350505,
            3341807571,
            4149409754,
            1057255273,
            2012875353,
            2162469141,
            2276492801,
            2601117357,
            993977747,
            3918593370,
            2654263191,
            753973209,
            36408145,
            2530585658,
            25011837,
            3520020182,
            2088578344,
            530523599,
            2918365339,
            1524020338,
            1518925132,
            3760827505,
            3759777254,
            1202760957,
            3985898139,
            3906192525,
            674977740,
            4174734889,
            2031300136,
            2019492241,
            3983892565,
            4153806404,
            3822280332,
            352677332,
            2297720250,
            60907813,
            90501309,
            3286998549,
            1016092578,
            2535922412,
            2839152426,
            457141659,
            509813237,
            4120667899,
            652014361,
            1966332200,
            2975202805,
            55981186,
            2327461051,
            676427537,
            3255491064,
            2882294119,
            3433927263,
            1307055953,
            942726286,
            933058658,
            2468411793,
            3933900994,
            4215176142,
            1361170020,
            2001714738,
            2830558078,
            3274259782,
            1222529897,
            1679025792,
            2729314320,
            3714953764,
            1770335741,
            151462246,
            3013232138,
            1682292957,
            1483529935,
            471910574,
            1539241949,
            458788160,
            3436315007,
            1807016891,
            3718408830,
            978976581,
            1043663428,
            3165965781,
            1927990952,
            4200891579,
            2372276910,
            3208408903,
            3533431907,
            1412390302,
            2931980059,
            4132332400,
            1947078029,
            3881505623,
            4168226417,
            2941484381,
            1077988104,
            1320477388,
            886195818,
            18198404,
            3786409e3,
            2509781533,
            112762804,
            3463356488,
            1866414978,
            891333506,
            18488651,
            661792760,
            1628790961,
            3885187036,
            3141171499,
            876946877,
            2693282273,
            1372485963,
            791857591,
            2686433993,
            3759982718,
            3167212022,
            3472953795,
            2716379847,
            445679433,
            3561995674,
            3504004811,
            3574258232,
            54117162,
            3331405415,
            2381918588,
            3769707343,
            4154350007,
            1140177722,
            4074052095,
            668550556,
            3214352940,
            367459370,
            261225585,
            2610173221,
            4209349473,
            3468074219,
            3265815641,
            314222801,
            3066103646,
            3808782860,
            282218597,
            3406013506,
            3773591054,
            379116347,
            1285071038,
            846784868,
            2669647154,
            3771962079,
            3550491691,
            2305946142,
            453669953,
            1268987020,
            3317592352,
            3279303384,
            3744833421,
            2610507566,
            3859509063,
            266596637,
            3847019092,
            517658769,
            3462560207,
            3443424879,
            370717030,
            4247526661,
            2224018117,
            4143653529,
            4112773975,
            2788324899,
            2477274417,
            1456262402,
            2901442914,
            1517677493,
            1846949527,
            2295493580,
            3734397586,
            2176403920,
            1280348187,
            1908823572,
            3871786941,
            846861322,
            1172426758,
            3287448474,
            3383383037,
            1655181056,
            3139813346,
            901632758,
            1897031941,
            2986607138,
            3066810236,
            3447102507,
            1393639104,
            373351379,
            950779232,
            625454576,
            3124240540,
            4148612726,
            2007998917,
            544563296,
            2244738638,
            2330496472,
            2058025392,
            1291430526,
            424198748,
            50039436,
            29584100,
            3605783033,
            2429876329,
            2791104160,
            1057563949,
            3255363231,
            3075367218,
            3463963227,
            1469046755,
            985887462
          ]
        ];
        var c = {
          pbox: [],
          sbox: []
        };
        function f(x, y) {
          let g = y >> 24 & 255, m = y >> 16 & 255, C = y >> 8 & 255, S = y & 255, _ = x.sbox[0][g] + x.sbox[1][m];
          return _ = _ ^ x.sbox[2][C], _ = _ + x.sbox[3][S], _;
        }
        function d(x, y, g) {
          let m = y, C = g, S;
          for (let _ = 0; _ < o; ++_)
            m = m ^ x.pbox[_], C = f(x, m) ^ C, S = m, m = C, C = S;
          return S = m, m = C, C = S, C = C ^ x.pbox[o], m = m ^ x.pbox[o + 1], { left: m, right: C };
        }
        function v(x, y, g) {
          let m = y, C = g, S;
          for (let _ = o + 1; _ > 1; --_)
            m = m ^ x.pbox[_], C = f(x, m) ^ C, S = m, m = C, C = S;
          return S = m, m = C, C = S, C = C ^ x.pbox[1], m = m ^ x.pbox[0], { left: m, right: C };
        }
        function p(x, y, g) {
          for (let T = 0; T < 4; T++) {
            x.sbox[T] = [];
            for (let D = 0; D < 256; D++)
              x.sbox[T][D] = l[T][D];
          }
          let m = 0;
          for (let T = 0; T < o + 2; T++)
            x.pbox[T] = u[T] ^ y[m], m++, m >= g && (m = 0);
          let C = 0, S = 0, _ = 0;
          for (let T = 0; T < o + 2; T += 2)
            _ = d(x, C, S), C = _.left, S = _.right, x.pbox[T] = C, x.pbox[T + 1] = S;
          for (let T = 0; T < 4; T++)
            for (let D = 0; D < 256; D += 2)
              _ = d(x, C, S), C = _.left, S = _.right, x.sbox[T][D] = C, x.sbox[T][D + 1] = S;
          return !0;
        }
        var h = s.Blowfish = i.extend({
          _doReset: function() {
            if (this._keyPriorReset !== this._key) {
              var x = this._keyPriorReset = this._key, y = x.words, g = x.sigBytes / 4;
              p(c, y, g);
            }
          },
          encryptBlock: function(x, y) {
            var g = d(c, x[y], x[y + 1]);
            x[y] = g.left, x[y + 1] = g.right;
          },
          decryptBlock: function(x, y) {
            var g = v(c, x[y], x[y + 1]);
            x[y] = g.left, x[y + 1] = g.right;
          },
          blockSize: 64 / 32,
          keySize: 128 / 32,
          ivSize: 64 / 32
        });
        r.Blowfish = i._createHelper(h);
      }(), a.Blowfish;
    });
  }(Si)), Si.exports;
}
(function(e, t) {
  (function(a, r, n) {
    e.exports = r(Oe(), Tn(), zp(), $p(), Mr(), qp(), Kr(), rl(), f0(), jp(), al(), Gp(), Wp(), Yp(), d0(), Qp(), br(), dt(), Xp(), Zp(), Jp(), eh(), th(), rh(), ah(), nh(), ih(), sh(), oh(), lh(), ch(), uh(), fh(), dh(), ph());
  })(we, function(a) {
    return a;
  });
})(tl);
var Ft = tl.exports;
const hh = /* @__PURE__ */ el(Ft), nl = "/api/oauth2", p0 = "/api/infrastructure";
let il;
const vh = () => {
  il = Dc.service({
    fullscreen: !0,
    lock: !0,
    text: "拼命加载中,请稍后···",
    background: "rgba(0, 0, 0, 0.7)"
  });
}, xh = () => {
  il.close();
};
let Ta = 0;
const gh = () => {
  Ta === 0 && vh(), Ta++;
}, qs = () => {
  Ta <= 0 || (Ta--, Ta === 0 && xh());
};
var on = /* @__PURE__ */ ((e) => (e[e.SUCCESS = 0] = "SUCCESS", e[e.ERROR = 500] = "ERROR", e[e.OVERDUE = 401] = "OVERDUE", e[e.TIMEOUT = 3e4] = "TIMEOUT", e.TYPE = "success", e))(on || {});
const yh = (e) => {
  switch (e) {
    case 400:
      ze.error("请求失败！请您稍后重试");
      break;
    case 401:
      ze.error("登录失效！请您重新登录");
      break;
    case 403:
      ze.error("当前账号无权限访问！");
      break;
    case 404:
      ze.error("你所访问的资源不存在！");
      break;
    case 405:
      ze.error("请求方式错误！请您稍后重试");
      break;
    case 408:
      ze.error("请求超时！请您稍后重试");
      break;
    case 500:
      ze.error("服务异常！");
      break;
    case 502:
      ze.error("网关错误！");
      break;
    case 503:
      ze.error("服务不可用！");
      break;
    case 504:
      ze.error("网关超时！");
      break;
    default:
      ze.error("请求失败！");
  }
};
let $a = /* @__PURE__ */ new Map();
const js = (e) => [e.method, e.url, ki.stringify(e.data), ki.stringify(e.params)].join("&");
class mh {
  /**
   * @description: 添加请求
   * @param {Object} config
   * @return void
   */
  addPending(t) {
    this.removePending(t);
    const a = js(t), r = new AbortController();
    t.signal = r.signal, $a.set(a, r);
  }
  /**
   * @description: 移除请求
   * @param {Object} config
   */
  removePending(t) {
    const a = js(t), r = $a.get(a);
    r && r.abort();
  }
  /**
   * @description: 清空所有pending
   */
  removeAllPending() {
    $a.forEach((t) => {
      t && t.abort();
    }), $a.clear();
  }
}
var Re = {
  // default options
  options: {
    usePureJavaScript: !1
  }
}, h0 = {}, Ch = h0, Gs = {};
h0.encode = function(e, t, a) {
  if (typeof t != "string")
    throw new TypeError('"alphabet" must be a string.');
  if (a !== void 0 && typeof a != "number")
    throw new TypeError('"maxline" must be a number.');
  var r = "";
  if (!(e instanceof Uint8Array))
    r = Eh(e, t);
  else {
    var n = 0, i = t.length, s = t.charAt(0), o = [0];
    for (n = 0; n < e.length; ++n) {
      for (var u = 0, l = e[n]; u < o.length; ++u)
        l += o[u] << 8, o[u] = l % i, l = l / i | 0;
      for (; l > 0; )
        o.push(l % i), l = l / i | 0;
    }
    for (n = 0; e[n] === 0 && n < e.length - 1; ++n)
      r += s;
    for (n = o.length - 1; n >= 0; --n)
      r += t[o[n]];
  }
  if (a) {
    var c = new RegExp(".{1," + a + "}", "g");
    r = r.match(c).join(`\r
`);
  }
  return r;
};
h0.decode = function(e, t) {
  if (typeof e != "string")
    throw new TypeError('"input" must be a string.');
  if (typeof t != "string")
    throw new TypeError('"alphabet" must be a string.');
  var a = Gs[t];
  if (!a) {
    a = Gs[t] = [];
    for (var r = 0; r < t.length; ++r)
      a[t.charCodeAt(r)] = r;
  }
  e = e.replace(/\s/g, "");
  for (var n = t.length, i = t.charAt(0), s = [0], r = 0; r < e.length; r++) {
    var o = a[e.charCodeAt(r)];
    if (o === void 0)
      return;
    for (var u = 0, l = o; u < s.length; ++u)
      l += s[u] * n, s[u] = l & 255, l >>= 8;
    for (; l > 0; )
      s.push(l & 255), l >>= 8;
  }
  for (var c = 0; e[c] === i && c < e.length - 1; ++c)
    s.push(0);
  return typeof Buffer < "u" ? Buffer.from(s.reverse()) : new Uint8Array(s.reverse());
};
function Eh(e, t) {
  var a = 0, r = t.length, n = t.charAt(0), i = [0];
  for (a = 0; a < e.length(); ++a) {
    for (var s = 0, o = e.at(a); s < i.length; ++s)
      o += i[s] << 8, i[s] = o % r, o = o / r | 0;
    for (; o > 0; )
      i.push(o % r), o = o / r | 0;
  }
  var u = "";
  for (a = 0; e.at(a) === 0 && a < e.length() - 1; ++a)
    u += n;
  for (a = i.length - 1; a >= 0; --a)
    u += t[i[a]];
  return u;
}
var Ws = Re, Ys = Ch, k = Ws.util = Ws.util || {};
(function() {
  if (typeof process < "u" && process.nextTick && !process.browser) {
    k.nextTick = process.nextTick, typeof setImmediate == "function" ? k.setImmediate = setImmediate : k.setImmediate = k.nextTick;
    return;
  }
  if (typeof setImmediate == "function") {
    k.setImmediate = function() {
      return setImmediate.apply(void 0, arguments);
    }, k.nextTick = function(s) {
      return setImmediate(s);
    };
    return;
  }
  if (k.setImmediate = function(s) {
    setTimeout(s, 0);
  }, typeof window < "u" && typeof window.postMessage == "function") {
    let s = function(o) {
      if (o.source === window && o.data === e) {
        o.stopPropagation();
        var u = t.slice();
        t.length = 0, u.forEach(function(l) {
          l();
        });
      }
    };
    var e = "forge.setImmediate", t = [];
    k.setImmediate = function(o) {
      t.push(o), t.length === 1 && window.postMessage(e, "*");
    }, window.addEventListener("message", s, !0);
  }
  if (typeof MutationObserver < "u") {
    var a = Date.now(), r = !0, n = document.createElement("div"), t = [];
    new MutationObserver(function() {
      var o = t.slice();
      t.length = 0, o.forEach(function(u) {
        u();
      });
    }).observe(n, { attributes: !0 });
    var i = k.setImmediate;
    k.setImmediate = function(o) {
      Date.now() - a > 15 ? (a = Date.now(), i(o)) : (t.push(o), t.length === 1 && n.setAttribute("a", r = !r));
    };
  }
  k.nextTick = k.setImmediate;
})();
k.isNodejs = typeof process < "u" && process.versions && process.versions.node;
k.globalScope = function() {
  return k.isNodejs ? we : typeof self > "u" ? window : self;
}();
k.isArray = Array.isArray || function(e) {
  return Object.prototype.toString.call(e) === "[object Array]";
};
k.isArrayBuffer = function(e) {
  return typeof ArrayBuffer < "u" && e instanceof ArrayBuffer;
};
k.isArrayBufferView = function(e) {
  return e && k.isArrayBuffer(e.buffer) && e.byteLength !== void 0;
};
function Va(e) {
  if (!(e === 8 || e === 16 || e === 24 || e === 32))
    throw new Error("Only 8, 16, 24, or 32 bits supported: " + e);
}
k.ByteBuffer = v0;
function v0(e) {
  if (this.data = "", this.read = 0, typeof e == "string")
    this.data = e;
  else if (k.isArrayBuffer(e) || k.isArrayBufferView(e))
    if (typeof Buffer < "u" && e instanceof Buffer)
      this.data = e.toString("binary");
    else {
      var t = new Uint8Array(e);
      try {
        this.data = String.fromCharCode.apply(null, t);
      } catch {
        for (var a = 0; a < t.length; ++a)
          this.putByte(t[a]);
      }
    }
  else (e instanceof v0 || typeof e == "object" && typeof e.data == "string" && typeof e.read == "number") && (this.data = e.data, this.read = e.read);
  this._constructedStringLength = 0;
}
k.ByteStringBuffer = v0;
var bh = 4096;
k.ByteStringBuffer.prototype._optimizeConstructedString = function(e) {
  this._constructedStringLength += e, this._constructedStringLength > bh && (this.data.substr(0, 1), this._constructedStringLength = 0);
};
k.ByteStringBuffer.prototype.length = function() {
  return this.data.length - this.read;
};
k.ByteStringBuffer.prototype.isEmpty = function() {
  return this.length() <= 0;
};
k.ByteStringBuffer.prototype.putByte = function(e) {
  return this.putBytes(String.fromCharCode(e));
};
k.ByteStringBuffer.prototype.fillWithByte = function(e, t) {
  e = String.fromCharCode(e);
  for (var a = this.data; t > 0; )
    t & 1 && (a += e), t >>>= 1, t > 0 && (e += e);
  return this.data = a, this._optimizeConstructedString(t), this;
};
k.ByteStringBuffer.prototype.putBytes = function(e) {
  return this.data += e, this._optimizeConstructedString(e.length), this;
};
k.ByteStringBuffer.prototype.putString = function(e) {
  return this.putBytes(k.encodeUtf8(e));
};
k.ByteStringBuffer.prototype.putInt16 = function(e) {
  return this.putBytes(
    String.fromCharCode(e >> 8 & 255) + String.fromCharCode(e & 255)
  );
};
k.ByteStringBuffer.prototype.putInt24 = function(e) {
  return this.putBytes(
    String.fromCharCode(e >> 16 & 255) + String.fromCharCode(e >> 8 & 255) + String.fromCharCode(e & 255)
  );
};
k.ByteStringBuffer.prototype.putInt32 = function(e) {
  return this.putBytes(
    String.fromCharCode(e >> 24 & 255) + String.fromCharCode(e >> 16 & 255) + String.fromCharCode(e >> 8 & 255) + String.fromCharCode(e & 255)
  );
};
k.ByteStringBuffer.prototype.putInt16Le = function(e) {
  return this.putBytes(
    String.fromCharCode(e & 255) + String.fromCharCode(e >> 8 & 255)
  );
};
k.ByteStringBuffer.prototype.putInt24Le = function(e) {
  return this.putBytes(
    String.fromCharCode(e & 255) + String.fromCharCode(e >> 8 & 255) + String.fromCharCode(e >> 16 & 255)
  );
};
k.ByteStringBuffer.prototype.putInt32Le = function(e) {
  return this.putBytes(
    String.fromCharCode(e & 255) + String.fromCharCode(e >> 8 & 255) + String.fromCharCode(e >> 16 & 255) + String.fromCharCode(e >> 24 & 255)
  );
};
k.ByteStringBuffer.prototype.putInt = function(e, t) {
  Va(t);
  var a = "";
  do
    t -= 8, a += String.fromCharCode(e >> t & 255);
  while (t > 0);
  return this.putBytes(a);
};
k.ByteStringBuffer.prototype.putSignedInt = function(e, t) {
  return e < 0 && (e += 2 << t - 1), this.putInt(e, t);
};
k.ByteStringBuffer.prototype.putBuffer = function(e) {
  return this.putBytes(e.getBytes());
};
k.ByteStringBuffer.prototype.getByte = function() {
  return this.data.charCodeAt(this.read++);
};
k.ByteStringBuffer.prototype.getInt16 = function() {
  var e = this.data.charCodeAt(this.read) << 8 ^ this.data.charCodeAt(this.read + 1);
  return this.read += 2, e;
};
k.ByteStringBuffer.prototype.getInt24 = function() {
  var e = this.data.charCodeAt(this.read) << 16 ^ this.data.charCodeAt(this.read + 1) << 8 ^ this.data.charCodeAt(this.read + 2);
  return this.read += 3, e;
};
k.ByteStringBuffer.prototype.getInt32 = function() {
  var e = this.data.charCodeAt(this.read) << 24 ^ this.data.charCodeAt(this.read + 1) << 16 ^ this.data.charCodeAt(this.read + 2) << 8 ^ this.data.charCodeAt(this.read + 3);
  return this.read += 4, e;
};
k.ByteStringBuffer.prototype.getInt16Le = function() {
  var e = this.data.charCodeAt(this.read) ^ this.data.charCodeAt(this.read + 1) << 8;
  return this.read += 2, e;
};
k.ByteStringBuffer.prototype.getInt24Le = function() {
  var e = this.data.charCodeAt(this.read) ^ this.data.charCodeAt(this.read + 1) << 8 ^ this.data.charCodeAt(this.read + 2) << 16;
  return this.read += 3, e;
};
k.ByteStringBuffer.prototype.getInt32Le = function() {
  var e = this.data.charCodeAt(this.read) ^ this.data.charCodeAt(this.read + 1) << 8 ^ this.data.charCodeAt(this.read + 2) << 16 ^ this.data.charCodeAt(this.read + 3) << 24;
  return this.read += 4, e;
};
k.ByteStringBuffer.prototype.getInt = function(e) {
  Va(e);
  var t = 0;
  do
    t = (t << 8) + this.data.charCodeAt(this.read++), e -= 8;
  while (e > 0);
  return t;
};
k.ByteStringBuffer.prototype.getSignedInt = function(e) {
  var t = this.getInt(e), a = 2 << e - 2;
  return t >= a && (t -= a << 1), t;
};
k.ByteStringBuffer.prototype.getBytes = function(e) {
  var t;
  return e ? (e = Math.min(this.length(), e), t = this.data.slice(this.read, this.read + e), this.read += e) : e === 0 ? t = "" : (t = this.read === 0 ? this.data : this.data.slice(this.read), this.clear()), t;
};
k.ByteStringBuffer.prototype.bytes = function(e) {
  return typeof e > "u" ? this.data.slice(this.read) : this.data.slice(this.read, this.read + e);
};
k.ByteStringBuffer.prototype.at = function(e) {
  return this.data.charCodeAt(this.read + e);
};
k.ByteStringBuffer.prototype.setAt = function(e, t) {
  return this.data = this.data.substr(0, this.read + e) + String.fromCharCode(t) + this.data.substr(this.read + e + 1), this;
};
k.ByteStringBuffer.prototype.last = function() {
  return this.data.charCodeAt(this.data.length - 1);
};
k.ByteStringBuffer.prototype.copy = function() {
  var e = k.createBuffer(this.data);
  return e.read = this.read, e;
};
k.ByteStringBuffer.prototype.compact = function() {
  return this.read > 0 && (this.data = this.data.slice(this.read), this.read = 0), this;
};
k.ByteStringBuffer.prototype.clear = function() {
  return this.data = "", this.read = 0, this;
};
k.ByteStringBuffer.prototype.truncate = function(e) {
  var t = Math.max(0, this.length() - e);
  return this.data = this.data.substr(this.read, t), this.read = 0, this;
};
k.ByteStringBuffer.prototype.toHex = function() {
  for (var e = "", t = this.read; t < this.data.length; ++t) {
    var a = this.data.charCodeAt(t);
    a < 16 && (e += "0"), e += a.toString(16);
  }
  return e;
};
k.ByteStringBuffer.prototype.toString = function() {
  return k.decodeUtf8(this.bytes());
};
function Sh(e, t) {
  t = t || {}, this.read = t.readOffset || 0, this.growSize = t.growSize || 1024;
  var a = k.isArrayBuffer(e), r = k.isArrayBufferView(e);
  if (a || r) {
    a ? this.data = new DataView(e) : this.data = new DataView(e.buffer, e.byteOffset, e.byteLength), this.write = "writeOffset" in t ? t.writeOffset : this.data.byteLength;
    return;
  }
  this.data = new DataView(new ArrayBuffer(0)), this.write = 0, e != null && this.putBytes(e), "writeOffset" in t && (this.write = t.writeOffset);
}
k.DataBuffer = Sh;
k.DataBuffer.prototype.length = function() {
  return this.write - this.read;
};
k.DataBuffer.prototype.isEmpty = function() {
  return this.length() <= 0;
};
k.DataBuffer.prototype.accommodate = function(e, t) {
  if (this.length() >= e)
    return this;
  t = Math.max(t || this.growSize, e);
  var a = new Uint8Array(
    this.data.buffer,
    this.data.byteOffset,
    this.data.byteLength
  ), r = new Uint8Array(this.length() + t);
  return r.set(a), this.data = new DataView(r.buffer), this;
};
k.DataBuffer.prototype.putByte = function(e) {
  return this.accommodate(1), this.data.setUint8(this.write++, e), this;
};
k.DataBuffer.prototype.fillWithByte = function(e, t) {
  this.accommodate(t);
  for (var a = 0; a < t; ++a)
    this.data.setUint8(e);
  return this;
};
k.DataBuffer.prototype.putBytes = function(e, t) {
  if (k.isArrayBufferView(e)) {
    var a = new Uint8Array(e.buffer, e.byteOffset, e.byteLength), r = a.byteLength - a.byteOffset;
    this.accommodate(r);
    var n = new Uint8Array(this.data.buffer, this.write);
    return n.set(a), this.write += r, this;
  }
  if (k.isArrayBuffer(e)) {
    var a = new Uint8Array(e);
    this.accommodate(a.byteLength);
    var n = new Uint8Array(this.data.buffer);
    return n.set(a, this.write), this.write += a.byteLength, this;
  }
  if (e instanceof k.DataBuffer || typeof e == "object" && typeof e.read == "number" && typeof e.write == "number" && k.isArrayBufferView(e.data)) {
    var a = new Uint8Array(e.data.byteLength, e.read, e.length());
    this.accommodate(a.byteLength);
    var n = new Uint8Array(e.data.byteLength, this.write);
    return n.set(a), this.write += a.byteLength, this;
  }
  if (e instanceof k.ByteStringBuffer && (e = e.data, t = "binary"), t = t || "binary", typeof e == "string") {
    var i;
    if (t === "hex")
      return this.accommodate(Math.ceil(e.length / 2)), i = new Uint8Array(this.data.buffer, this.write), this.write += k.binary.hex.decode(e, i, this.write), this;
    if (t === "base64")
      return this.accommodate(Math.ceil(e.length / 4) * 3), i = new Uint8Array(this.data.buffer, this.write), this.write += k.binary.base64.decode(e, i, this.write), this;
    if (t === "utf8" && (e = k.encodeUtf8(e), t = "binary"), t === "binary" || t === "raw")
      return this.accommodate(e.length), i = new Uint8Array(this.data.buffer, this.write), this.write += k.binary.raw.decode(i), this;
    if (t === "utf16")
      return this.accommodate(e.length * 2), i = new Uint16Array(this.data.buffer, this.write), this.write += k.text.utf16.encode(i), this;
    throw new Error("Invalid encoding: " + t);
  }
  throw Error("Invalid parameter: " + e);
};
k.DataBuffer.prototype.putBuffer = function(e) {
  return this.putBytes(e), e.clear(), this;
};
k.DataBuffer.prototype.putString = function(e) {
  return this.putBytes(e, "utf16");
};
k.DataBuffer.prototype.putInt16 = function(e) {
  return this.accommodate(2), this.data.setInt16(this.write, e), this.write += 2, this;
};
k.DataBuffer.prototype.putInt24 = function(e) {
  return this.accommodate(3), this.data.setInt16(this.write, e >> 8 & 65535), this.data.setInt8(this.write, e >> 16 & 255), this.write += 3, this;
};
k.DataBuffer.prototype.putInt32 = function(e) {
  return this.accommodate(4), this.data.setInt32(this.write, e), this.write += 4, this;
};
k.DataBuffer.prototype.putInt16Le = function(e) {
  return this.accommodate(2), this.data.setInt16(this.write, e, !0), this.write += 2, this;
};
k.DataBuffer.prototype.putInt24Le = function(e) {
  return this.accommodate(3), this.data.setInt8(this.write, e >> 16 & 255), this.data.setInt16(this.write, e >> 8 & 65535, !0), this.write += 3, this;
};
k.DataBuffer.prototype.putInt32Le = function(e) {
  return this.accommodate(4), this.data.setInt32(this.write, e, !0), this.write += 4, this;
};
k.DataBuffer.prototype.putInt = function(e, t) {
  Va(t), this.accommodate(t / 8);
  do
    t -= 8, this.data.setInt8(this.write++, e >> t & 255);
  while (t > 0);
  return this;
};
k.DataBuffer.prototype.putSignedInt = function(e, t) {
  return Va(t), this.accommodate(t / 8), e < 0 && (e += 2 << t - 1), this.putInt(e, t);
};
k.DataBuffer.prototype.getByte = function() {
  return this.data.getInt8(this.read++);
};
k.DataBuffer.prototype.getInt16 = function() {
  var e = this.data.getInt16(this.read);
  return this.read += 2, e;
};
k.DataBuffer.prototype.getInt24 = function() {
  var e = this.data.getInt16(this.read) << 8 ^ this.data.getInt8(this.read + 2);
  return this.read += 3, e;
};
k.DataBuffer.prototype.getInt32 = function() {
  var e = this.data.getInt32(this.read);
  return this.read += 4, e;
};
k.DataBuffer.prototype.getInt16Le = function() {
  var e = this.data.getInt16(this.read, !0);
  return this.read += 2, e;
};
k.DataBuffer.prototype.getInt24Le = function() {
  var e = this.data.getInt8(this.read) ^ this.data.getInt16(this.read + 1, !0) << 8;
  return this.read += 3, e;
};
k.DataBuffer.prototype.getInt32Le = function() {
  var e = this.data.getInt32(this.read, !0);
  return this.read += 4, e;
};
k.DataBuffer.prototype.getInt = function(e) {
  Va(e);
  var t = 0;
  do
    t = (t << 8) + this.data.getInt8(this.read++), e -= 8;
  while (e > 0);
  return t;
};
k.DataBuffer.prototype.getSignedInt = function(e) {
  var t = this.getInt(e), a = 2 << e - 2;
  return t >= a && (t -= a << 1), t;
};
k.DataBuffer.prototype.getBytes = function(e) {
  var t;
  return e ? (e = Math.min(this.length(), e), t = this.data.slice(this.read, this.read + e), this.read += e) : e === 0 ? t = "" : (t = this.read === 0 ? this.data : this.data.slice(this.read), this.clear()), t;
};
k.DataBuffer.prototype.bytes = function(e) {
  return typeof e > "u" ? this.data.slice(this.read) : this.data.slice(this.read, this.read + e);
};
k.DataBuffer.prototype.at = function(e) {
  return this.data.getUint8(this.read + e);
};
k.DataBuffer.prototype.setAt = function(e, t) {
  return this.data.setUint8(e, t), this;
};
k.DataBuffer.prototype.last = function() {
  return this.data.getUint8(this.write - 1);
};
k.DataBuffer.prototype.copy = function() {
  return new k.DataBuffer(this);
};
k.DataBuffer.prototype.compact = function() {
  if (this.read > 0) {
    var e = new Uint8Array(this.data.buffer, this.read), t = new Uint8Array(e.byteLength);
    t.set(e), this.data = new DataView(t), this.write -= this.read, this.read = 0;
  }
  return this;
};
k.DataBuffer.prototype.clear = function() {
  return this.data = new DataView(new ArrayBuffer(0)), this.read = this.write = 0, this;
};
k.DataBuffer.prototype.truncate = function(e) {
  return this.write = Math.max(0, this.length() - e), this.read = Math.min(this.read, this.write), this;
};
k.DataBuffer.prototype.toHex = function() {
  for (var e = "", t = this.read; t < this.data.byteLength; ++t) {
    var a = this.data.getUint8(t);
    a < 16 && (e += "0"), e += a.toString(16);
  }
  return e;
};
k.DataBuffer.prototype.toString = function(e) {
  var t = new Uint8Array(this.data, this.read, this.length());
  if (e = e || "utf8", e === "binary" || e === "raw")
    return k.binary.raw.encode(t);
  if (e === "hex")
    return k.binary.hex.encode(t);
  if (e === "base64")
    return k.binary.base64.encode(t);
  if (e === "utf8")
    return k.text.utf8.decode(t);
  if (e === "utf16")
    return k.text.utf16.decode(t);
  throw new Error("Invalid encoding: " + e);
};
k.createBuffer = function(e, t) {
  return t = t || "raw", e !== void 0 && t === "utf8" && (e = k.encodeUtf8(e)), new k.ByteBuffer(e);
};
k.fillString = function(e, t) {
  for (var a = ""; t > 0; )
    t & 1 && (a += e), t >>>= 1, t > 0 && (e += e);
  return a;
};
k.xorBytes = function(e, t, a) {
  for (var r = "", n = "", i = "", s = 0, o = 0; a > 0; --a, ++s)
    n = e.charCodeAt(s) ^ t.charCodeAt(s), o >= 10 && (r += i, i = "", o = 0), i += String.fromCharCode(n), ++o;
  return r += i, r;
};
k.hexToBytes = function(e) {
  var t = "", a = 0;
  for (e.length & !0 && (a = 1, t += String.fromCharCode(parseInt(e[0], 16))); a < e.length; a += 2)
    t += String.fromCharCode(parseInt(e.substr(a, 2), 16));
  return t;
};
k.bytesToHex = function(e) {
  return k.createBuffer(e).toHex();
};
k.int32ToBytes = function(e) {
  return String.fromCharCode(e >> 24 & 255) + String.fromCharCode(e >> 16 & 255) + String.fromCharCode(e >> 8 & 255) + String.fromCharCode(e & 255);
};
var hr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=", vr = [
  /*43 -43 = 0*/
  /*'+',  1,  2,  3,'/' */
  62,
  -1,
  -1,
  -1,
  63,
  /*'0','1','2','3','4','5','6','7','8','9' */
  52,
  53,
  54,
  55,
  56,
  57,
  58,
  59,
  60,
  61,
  /*15, 16, 17,'=', 19, 20, 21 */
  -1,
  -1,
  -1,
  64,
  -1,
  -1,
  -1,
  /*65 - 43 = 22*/
  /*'A','B','C','D','E','F','G','H','I','J','K','L','M', */
  0,
  1,
  2,
  3,
  4,
  5,
  6,
  7,
  8,
  9,
  10,
  11,
  12,
  /*'N','O','P','Q','R','S','T','U','V','W','X','Y','Z' */
  13,
  14,
  15,
  16,
  17,
  18,
  19,
  20,
  21,
  22,
  23,
  24,
  25,
  /*91 - 43 = 48 */
  /*48, 49, 50, 51, 52, 53 */
  -1,
  -1,
  -1,
  -1,
  -1,
  -1,
  /*97 - 43 = 54*/
  /*'a','b','c','d','e','f','g','h','i','j','k','l','m' */
  26,
  27,
  28,
  29,
  30,
  31,
  32,
  33,
  34,
  35,
  36,
  37,
  38,
  /*'n','o','p','q','r','s','t','u','v','w','x','y','z' */
  39,
  40,
  41,
  42,
  43,
  44,
  45,
  46,
  47,
  48,
  49,
  50,
  51
], sl = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
k.encode64 = function(e, t) {
  for (var a = "", r = "", n, i, s, o = 0; o < e.length; )
    n = e.charCodeAt(o++), i = e.charCodeAt(o++), s = e.charCodeAt(o++), a += hr.charAt(n >> 2), a += hr.charAt((n & 3) << 4 | i >> 4), isNaN(i) ? a += "==" : (a += hr.charAt((i & 15) << 2 | s >> 6), a += isNaN(s) ? "=" : hr.charAt(s & 63)), t && a.length > t && (r += a.substr(0, t) + `\r
`, a = a.substr(t));
  return r += a, r;
};
k.decode64 = function(e) {
  e = e.replace(/[^A-Za-z0-9\+\/\=]/g, "");
  for (var t = "", a, r, n, i, s = 0; s < e.length; )
    a = vr[e.charCodeAt(s++) - 43], r = vr[e.charCodeAt(s++) - 43], n = vr[e.charCodeAt(s++) - 43], i = vr[e.charCodeAt(s++) - 43], t += String.fromCharCode(a << 2 | r >> 4), n !== 64 && (t += String.fromCharCode((r & 15) << 4 | n >> 2), i !== 64 && (t += String.fromCharCode((n & 3) << 6 | i)));
  return t;
};
k.encodeUtf8 = function(e) {
  return unescape(encodeURIComponent(e));
};
k.decodeUtf8 = function(e) {
  return decodeURIComponent(escape(e));
};
k.binary = {
  raw: {},
  hex: {},
  base64: {},
  base58: {},
  baseN: {
    encode: Ys.encode,
    decode: Ys.decode
  }
};
k.binary.raw.encode = function(e) {
  return String.fromCharCode.apply(null, e);
};
k.binary.raw.decode = function(e, t, a) {
  var r = t;
  r || (r = new Uint8Array(e.length)), a = a || 0;
  for (var n = a, i = 0; i < e.length; ++i)
    r[n++] = e.charCodeAt(i);
  return t ? n - a : r;
};
k.binary.hex.encode = k.bytesToHex;
k.binary.hex.decode = function(e, t, a) {
  var r = t;
  r || (r = new Uint8Array(Math.ceil(e.length / 2))), a = a || 0;
  var n = 0, i = a;
  for (e.length & 1 && (n = 1, r[i++] = parseInt(e[0], 16)); n < e.length; n += 2)
    r[i++] = parseInt(e.substr(n, 2), 16);
  return t ? i - a : r;
};
k.binary.base64.encode = function(e, t) {
  for (var a = "", r = "", n, i, s, o = 0; o < e.byteLength; )
    n = e[o++], i = e[o++], s = e[o++], a += hr.charAt(n >> 2), a += hr.charAt((n & 3) << 4 | i >> 4), isNaN(i) ? a += "==" : (a += hr.charAt((i & 15) << 2 | s >> 6), a += isNaN(s) ? "=" : hr.charAt(s & 63)), t && a.length > t && (r += a.substr(0, t) + `\r
`, a = a.substr(t));
  return r += a, r;
};
k.binary.base64.decode = function(e, t, a) {
  var r = t;
  r || (r = new Uint8Array(Math.ceil(e.length / 4) * 3)), e = e.replace(/[^A-Za-z0-9\+\/\=]/g, ""), a = a || 0;
  for (var n, i, s, o, u = 0, l = a; u < e.length; )
    n = vr[e.charCodeAt(u++) - 43], i = vr[e.charCodeAt(u++) - 43], s = vr[e.charCodeAt(u++) - 43], o = vr[e.charCodeAt(u++) - 43], r[l++] = n << 2 | i >> 4, s !== 64 && (r[l++] = (i & 15) << 4 | s >> 2, o !== 64 && (r[l++] = (s & 3) << 6 | o));
  return t ? l - a : r.subarray(0, l);
};
k.binary.base58.encode = function(e, t) {
  return k.binary.baseN.encode(e, sl, t);
};
k.binary.base58.decode = function(e, t) {
  return k.binary.baseN.decode(e, sl, t);
};
k.text = {
  utf8: {},
  utf16: {}
};
k.text.utf8.encode = function(e, t, a) {
  e = k.encodeUtf8(e);
  var r = t;
  r || (r = new Uint8Array(e.length)), a = a || 0;
  for (var n = a, i = 0; i < e.length; ++i)
    r[n++] = e.charCodeAt(i);
  return t ? n - a : r;
};
k.text.utf8.decode = function(e) {
  return k.decodeUtf8(String.fromCharCode.apply(null, e));
};
k.text.utf16.encode = function(e, t, a) {
  var r = t;
  r || (r = new Uint8Array(e.length * 2));
  var n = new Uint16Array(r.buffer);
  a = a || 0;
  for (var i = a, s = a, o = 0; o < e.length; ++o)
    n[s++] = e.charCodeAt(o), i += 2;
  return t ? i - a : r;
};
k.text.utf16.decode = function(e) {
  return String.fromCharCode.apply(null, new Uint16Array(e.buffer));
};
k.deflate = function(e, t, a) {
  if (t = k.decode64(e.deflate(k.encode64(t)).rval), a) {
    var r = 2, n = t.charCodeAt(1);
    n & 32 && (r = 6), t = t.substring(r, t.length - 4);
  }
  return t;
};
k.inflate = function(e, t, a) {
  var r = e.inflate(k.encode64(t)).rval;
  return r === null ? null : k.decode64(r);
};
var x0 = function(e, t, a) {
  if (!e)
    throw new Error("WebStorage not available.");
  var r;
  if (a === null ? r = e.removeItem(t) : (a = k.encode64(JSON.stringify(a)), r = e.setItem(t, a)), typeof r < "u" && r.rval !== !0) {
    var n = new Error(r.error.message);
    throw n.id = r.error.id, n.name = r.error.name, n;
  }
}, g0 = function(e, t) {
  if (!e)
    throw new Error("WebStorage not available.");
  var a = e.getItem(t);
  if (e.init)
    if (a.rval === null) {
      if (a.error) {
        var r = new Error(a.error.message);
        throw r.id = a.error.id, r.name = a.error.name, r;
      }
      a = null;
    } else
      a = a.rval;
  return a !== null && (a = JSON.parse(k.decode64(a))), a;
}, Ah = function(e, t, a, r) {
  var n = g0(e, t);
  n === null && (n = {}), n[a] = r, x0(e, t, n);
}, _h = function(e, t, a) {
  var r = g0(e, t);
  return r !== null && (r = a in r ? r[a] : null), r;
}, Bh = function(e, t, a) {
  var r = g0(e, t);
  if (r !== null && a in r) {
    delete r[a];
    var n = !0;
    for (var i in r) {
      n = !1;
      break;
    }
    n && (r = null), x0(e, t, r);
  }
}, Th = function(e, t) {
  x0(e, t, null);
}, In = function(e, t, a) {
  var r = null;
  typeof a > "u" && (a = ["web", "flash"]);
  var n, i = !1, s = null;
  for (var o in a) {
    n = a[o];
    try {
      if (n === "flash" || n === "both") {
        if (t[0] === null)
          throw new Error("Flash local storage not available.");
        r = e.apply(this, t), i = n === "flash";
      }
      (n === "web" || n === "both") && (t[0] = localStorage, r = e.apply(this, t), i = !0);
    } catch (u) {
      s = u;
    }
    if (i)
      break;
  }
  if (!i)
    throw s;
  return r;
};
k.setItem = function(e, t, a, r, n) {
  In(Ah, arguments, n);
};
k.getItem = function(e, t, a, r) {
  return In(_h, arguments, r);
};
k.removeItem = function(e, t, a, r) {
  In(Bh, arguments, r);
};
k.clearItems = function(e, t, a) {
  In(Th, arguments, a);
};
k.isEmpty = function(e) {
  for (var t in e)
    if (e.hasOwnProperty(t))
      return !1;
  return !0;
};
k.format = function(e) {
  for (var t = /%./g, a, r, n = 0, i = [], s = 0; a = t.exec(e); ) {
    r = e.substring(s, t.lastIndex - 2), r.length > 0 && i.push(r), s = t.lastIndex;
    var o = a[0][1];
    switch (o) {
      case "s":
      case "o":
        n < arguments.length ? i.push(arguments[n++ + 1]) : i.push("<?>");
        break;
      case "%":
        i.push("%");
        break;
      default:
        i.push("<%" + o + "?>");
    }
  }
  return i.push(e.substring(s)), i.join("");
};
k.formatNumber = function(e, t, a, r) {
  var n = e, i = isNaN(t = Math.abs(t)) ? 2 : t, s = a === void 0 ? "," : a, o = r === void 0 ? "." : r, u = n < 0 ? "-" : "", l = parseInt(n = Math.abs(+n || 0).toFixed(i), 10) + "", c = l.length > 3 ? l.length % 3 : 0;
  return u + (c ? l.substr(0, c) + o : "") + l.substr(c).replace(/(\d{3})(?=\d)/g, "$1" + o) + (i ? s + Math.abs(n - l).toFixed(i).slice(2) : "");
};
k.formatSize = function(e) {
  return e >= 1073741824 ? e = k.formatNumber(e / 1073741824, 2, ".", "") + " GiB" : e >= 1048576 ? e = k.formatNumber(e / 1048576, 2, ".", "") + " MiB" : e >= 1024 ? e = k.formatNumber(e / 1024, 0) + " KiB" : e = k.formatNumber(e, 0) + " bytes", e;
};
k.bytesFromIP = function(e) {
  return e.indexOf(".") !== -1 ? k.bytesFromIPv4(e) : e.indexOf(":") !== -1 ? k.bytesFromIPv6(e) : null;
};
k.bytesFromIPv4 = function(e) {
  if (e = e.split("."), e.length !== 4)
    return null;
  for (var t = k.createBuffer(), a = 0; a < e.length; ++a) {
    var r = parseInt(e[a], 10);
    if (isNaN(r))
      return null;
    t.putByte(r);
  }
  return t.getBytes();
};
k.bytesFromIPv6 = function(e) {
  var t = 0;
  e = e.split(":").filter(function(s) {
    return s.length === 0 && ++t, !0;
  });
  for (var a = (8 - e.length + t) * 2, r = k.createBuffer(), n = 0; n < 8; ++n) {
    if (!e[n] || e[n].length === 0) {
      r.fillWithByte(0, a), a = 0;
      continue;
    }
    var i = k.hexToBytes(e[n]);
    i.length < 2 && r.putByte(0), r.putBytes(i);
  }
  return r.getBytes();
};
k.bytesToIP = function(e) {
  return e.length === 4 ? k.bytesToIPv4(e) : e.length === 16 ? k.bytesToIPv6(e) : null;
};
k.bytesToIPv4 = function(e) {
  if (e.length !== 4)
    return null;
  for (var t = [], a = 0; a < e.length; ++a)
    t.push(e.charCodeAt(a));
  return t.join(".");
};
k.bytesToIPv6 = function(e) {
  if (e.length !== 16)
    return null;
  for (var t = [], a = [], r = 0, n = 0; n < e.length; n += 2) {
    for (var i = k.bytesToHex(e[n] + e[n + 1]); i[0] === "0" && i !== "0"; )
      i = i.substr(1);
    if (i === "0") {
      var s = a[a.length - 1], o = t.length;
      !s || o !== s.end + 1 ? a.push({ start: o, end: o }) : (s.end = o, s.end - s.start > a[r].end - a[r].start && (r = a.length - 1));
    }
    t.push(i);
  }
  if (a.length > 0) {
    var u = a[r];
    u.end - u.start > 0 && (t.splice(u.start, u.end - u.start + 1, ""), u.start === 0 && t.unshift(""), u.end === 7 && t.push(""));
  }
  return t.join(":");
};
k.estimateCores = function(e, t) {
  if (typeof e == "function" && (t = e, e = {}), e = e || {}, "cores" in k && !e.update)
    return t(null, k.cores);
  if (typeof navigator < "u" && "hardwareConcurrency" in navigator && navigator.hardwareConcurrency > 0)
    return k.cores = navigator.hardwareConcurrency, t(null, k.cores);
  if (typeof Worker > "u")
    return k.cores = 1, t(null, k.cores);
  if (typeof Blob > "u")
    return k.cores = 2, t(null, k.cores);
  var a = URL.createObjectURL(new Blob([
    "(",
    (function() {
      self.addEventListener("message", function(s) {
        var o = Date.now(), u = o + 4;
        self.postMessage({ st: o, et: u });
      });
    }).toString(),
    ")()"
  ], { type: "application/javascript" }));
  r([], 5, 16);
  function r(s, o, u) {
    if (o === 0) {
      var l = Math.floor(s.reduce(function(c, f) {
        return c + f;
      }, 0) / s.length);
      return k.cores = Math.max(1, l), URL.revokeObjectURL(a), t(null, k.cores);
    }
    n(u, function(c, f) {
      s.push(i(u, f)), r(s, o - 1, u);
    });
  }
  function n(s, o) {
    for (var u = [], l = [], c = 0; c < s; ++c) {
      var f = new Worker(a);
      f.addEventListener("message", function(d) {
        if (l.push(d.data), l.length === s) {
          for (var v = 0; v < s; ++v)
            u[v].terminate();
          o(null, l);
        }
      }), u.push(f);
    }
    for (var c = 0; c < s; ++c)
      u[c].postMessage(c);
  }
  function i(s, o) {
    for (var u = [], l = 0; l < s; ++l)
      for (var c = o[l], f = u[l] = [], d = 0; d < s; ++d)
        if (l !== d) {
          var v = o[d];
          (c.st > v.st && c.st < v.et || v.st > c.st && v.st < c.et) && f.push(d);
        }
    return u.reduce(function(p, h) {
      return Math.max(p, h.length);
    }, 0);
  }
};
var ft = Re;
ft.cipher = ft.cipher || {};
ft.cipher.algorithms = ft.cipher.algorithms || {};
ft.cipher.createCipher = function(e, t) {
  var a = e;
  if (typeof a == "string" && (a = ft.cipher.getAlgorithm(a), a && (a = a())), !a)
    throw new Error("Unsupported algorithm: " + e);
  return new ft.cipher.BlockCipher({
    algorithm: a,
    key: t,
    decrypt: !1
  });
};
ft.cipher.createDecipher = function(e, t) {
  var a = e;
  if (typeof a == "string" && (a = ft.cipher.getAlgorithm(a), a && (a = a())), !a)
    throw new Error("Unsupported algorithm: " + e);
  return new ft.cipher.BlockCipher({
    algorithm: a,
    key: t,
    decrypt: !0
  });
};
ft.cipher.registerAlgorithm = function(e, t) {
  e = e.toUpperCase(), ft.cipher.algorithms[e] = t;
};
ft.cipher.getAlgorithm = function(e) {
  return e = e.toUpperCase(), e in ft.cipher.algorithms ? ft.cipher.algorithms[e] : null;
};
var y0 = ft.cipher.BlockCipher = function(e) {
  this.algorithm = e.algorithm, this.mode = this.algorithm.mode, this.blockSize = this.mode.blockSize, this._finish = !1, this._input = null, this.output = null, this._op = e.decrypt ? this.mode.decrypt : this.mode.encrypt, this._decrypt = e.decrypt, this.algorithm.initialize(e);
};
y0.prototype.start = function(e) {
  e = e || {};
  var t = {};
  for (var a in e)
    t[a] = e[a];
  t.decrypt = this._decrypt, this._finish = !1, this._input = ft.util.createBuffer(), this.output = e.output || ft.util.createBuffer(), this.mode.start(t);
};
y0.prototype.update = function(e) {
  for (e && this._input.putBuffer(e); !this._op.call(this.mode, this._input, this.output, this._finish) && !this._finish; )
    ;
  this._input.compact();
};
y0.prototype.finish = function(e) {
  e && (this.mode.name === "ECB" || this.mode.name === "CBC") && (this.mode.pad = function(a) {
    return e(this.blockSize, a, !1);
  }, this.mode.unpad = function(a) {
    return e(this.blockSize, a, !0);
  });
  var t = {};
  return t.decrypt = this._decrypt, t.overflow = this._input.length() % this.blockSize, !(!this._decrypt && this.mode.pad && !this.mode.pad(this._input, t) || (this._finish = !0, this.update(), this._decrypt && this.mode.unpad && !this.mode.unpad(this.output, t)) || this.mode.afterFinish && !this.mode.afterFinish(this.output, t));
};
var ut = Re;
ut.cipher = ut.cipher || {};
var Le = ut.cipher.modes = ut.cipher.modes || {};
Le.ecb = function(e) {
  e = e || {}, this.name = "ECB", this.cipher = e.cipher, this.blockSize = e.blockSize || 16, this._ints = this.blockSize / 4, this._inBlock = new Array(this._ints), this._outBlock = new Array(this._ints);
};
Le.ecb.prototype.start = function(e) {
};
Le.ecb.prototype.encrypt = function(e, t, a) {
  if (e.length() < this.blockSize && !(a && e.length() > 0))
    return !0;
  for (var r = 0; r < this._ints; ++r)
    this._inBlock[r] = e.getInt32();
  this.cipher.encrypt(this._inBlock, this._outBlock);
  for (var r = 0; r < this._ints; ++r)
    t.putInt32(this._outBlock[r]);
};
Le.ecb.prototype.decrypt = function(e, t, a) {
  if (e.length() < this.blockSize && !(a && e.length() > 0))
    return !0;
  for (var r = 0; r < this._ints; ++r)
    this._inBlock[r] = e.getInt32();
  this.cipher.decrypt(this._inBlock, this._outBlock);
  for (var r = 0; r < this._ints; ++r)
    t.putInt32(this._outBlock[r]);
};
Le.ecb.prototype.pad = function(e, t) {
  var a = e.length() === this.blockSize ? this.blockSize : this.blockSize - e.length();
  return e.fillWithByte(a, a), !0;
};
Le.ecb.prototype.unpad = function(e, t) {
  if (t.overflow > 0)
    return !1;
  var a = e.length(), r = e.at(a - 1);
  return r > this.blockSize << 2 ? !1 : (e.truncate(r), !0);
};
Le.cbc = function(e) {
  e = e || {}, this.name = "CBC", this.cipher = e.cipher, this.blockSize = e.blockSize || 16, this._ints = this.blockSize / 4, this._inBlock = new Array(this._ints), this._outBlock = new Array(this._ints);
};
Le.cbc.prototype.start = function(e) {
  if (e.iv === null) {
    if (!this._prev)
      throw new Error("Invalid IV parameter.");
    this._iv = this._prev.slice(0);
  } else if ("iv" in e)
    this._iv = wn(e.iv, this.blockSize), this._prev = this._iv.slice(0);
  else
    throw new Error("Invalid IV parameter.");
};
Le.cbc.prototype.encrypt = function(e, t, a) {
  if (e.length() < this.blockSize && !(a && e.length() > 0))
    return !0;
  for (var r = 0; r < this._ints; ++r)
    this._inBlock[r] = this._prev[r] ^ e.getInt32();
  this.cipher.encrypt(this._inBlock, this._outBlock);
  for (var r = 0; r < this._ints; ++r)
    t.putInt32(this._outBlock[r]);
  this._prev = this._outBlock;
};
Le.cbc.prototype.decrypt = function(e, t, a) {
  if (e.length() < this.blockSize && !(a && e.length() > 0))
    return !0;
  for (var r = 0; r < this._ints; ++r)
    this._inBlock[r] = e.getInt32();
  this.cipher.decrypt(this._inBlock, this._outBlock);
  for (var r = 0; r < this._ints; ++r)
    t.putInt32(this._prev[r] ^ this._outBlock[r]);
  this._prev = this._inBlock.slice(0);
};
Le.cbc.prototype.pad = function(e, t) {
  var a = e.length() === this.blockSize ? this.blockSize : this.blockSize - e.length();
  return e.fillWithByte(a, a), !0;
};
Le.cbc.prototype.unpad = function(e, t) {
  if (t.overflow > 0)
    return !1;
  var a = e.length(), r = e.at(a - 1);
  return r > this.blockSize << 2 ? !1 : (e.truncate(r), !0);
};
Le.cfb = function(e) {
  e = e || {}, this.name = "CFB", this.cipher = e.cipher, this.blockSize = e.blockSize || 16, this._ints = this.blockSize / 4, this._inBlock = null, this._outBlock = new Array(this._ints), this._partialBlock = new Array(this._ints), this._partialOutput = ut.util.createBuffer(), this._partialBytes = 0;
};
Le.cfb.prototype.start = function(e) {
  if (!("iv" in e))
    throw new Error("Invalid IV parameter.");
  this._iv = wn(e.iv, this.blockSize), this._inBlock = this._iv.slice(0), this._partialBytes = 0;
};
Le.cfb.prototype.encrypt = function(e, t, a) {
  var r = e.length();
  if (r === 0)
    return !0;
  if (this.cipher.encrypt(this._inBlock, this._outBlock), this._partialBytes === 0 && r >= this.blockSize) {
    for (var n = 0; n < this._ints; ++n)
      this._inBlock[n] = e.getInt32() ^ this._outBlock[n], t.putInt32(this._inBlock[n]);
    return;
  }
  var i = (this.blockSize - r) % this.blockSize;
  i > 0 && (i = this.blockSize - i), this._partialOutput.clear();
  for (var n = 0; n < this._ints; ++n)
    this._partialBlock[n] = e.getInt32() ^ this._outBlock[n], this._partialOutput.putInt32(this._partialBlock[n]);
  if (i > 0)
    e.read -= this.blockSize;
  else
    for (var n = 0; n < this._ints; ++n)
      this._inBlock[n] = this._partialBlock[n];
  if (this._partialBytes > 0 && this._partialOutput.getBytes(this._partialBytes), i > 0 && !a)
    return t.putBytes(this._partialOutput.getBytes(
      i - this._partialBytes
    )), this._partialBytes = i, !0;
  t.putBytes(this._partialOutput.getBytes(
    r - this._partialBytes
  )), this._partialBytes = 0;
};
Le.cfb.prototype.decrypt = function(e, t, a) {
  var r = e.length();
  if (r === 0)
    return !0;
  if (this.cipher.encrypt(this._inBlock, this._outBlock), this._partialBytes === 0 && r >= this.blockSize) {
    for (var n = 0; n < this._ints; ++n)
      this._inBlock[n] = e.getInt32(), t.putInt32(this._inBlock[n] ^ this._outBlock[n]);
    return;
  }
  var i = (this.blockSize - r) % this.blockSize;
  i > 0 && (i = this.blockSize - i), this._partialOutput.clear();
  for (var n = 0; n < this._ints; ++n)
    this._partialBlock[n] = e.getInt32(), this._partialOutput.putInt32(this._partialBlock[n] ^ this._outBlock[n]);
  if (i > 0)
    e.read -= this.blockSize;
  else
    for (var n = 0; n < this._ints; ++n)
      this._inBlock[n] = this._partialBlock[n];
  if (this._partialBytes > 0 && this._partialOutput.getBytes(this._partialBytes), i > 0 && !a)
    return t.putBytes(this._partialOutput.getBytes(
      i - this._partialBytes
    )), this._partialBytes = i, !0;
  t.putBytes(this._partialOutput.getBytes(
    r - this._partialBytes
  )), this._partialBytes = 0;
};
Le.ofb = function(e) {
  e = e || {}, this.name = "OFB", this.cipher = e.cipher, this.blockSize = e.blockSize || 16, this._ints = this.blockSize / 4, this._inBlock = null, this._outBlock = new Array(this._ints), this._partialOutput = ut.util.createBuffer(), this._partialBytes = 0;
};
Le.ofb.prototype.start = function(e) {
  if (!("iv" in e))
    throw new Error("Invalid IV parameter.");
  this._iv = wn(e.iv, this.blockSize), this._inBlock = this._iv.slice(0), this._partialBytes = 0;
};
Le.ofb.prototype.encrypt = function(e, t, a) {
  var r = e.length();
  if (e.length() === 0)
    return !0;
  if (this.cipher.encrypt(this._inBlock, this._outBlock), this._partialBytes === 0 && r >= this.blockSize) {
    for (var n = 0; n < this._ints; ++n)
      t.putInt32(e.getInt32() ^ this._outBlock[n]), this._inBlock[n] = this._outBlock[n];
    return;
  }
  var i = (this.blockSize - r) % this.blockSize;
  i > 0 && (i = this.blockSize - i), this._partialOutput.clear();
  for (var n = 0; n < this._ints; ++n)
    this._partialOutput.putInt32(e.getInt32() ^ this._outBlock[n]);
  if (i > 0)
    e.read -= this.blockSize;
  else
    for (var n = 0; n < this._ints; ++n)
      this._inBlock[n] = this._outBlock[n];
  if (this._partialBytes > 0 && this._partialOutput.getBytes(this._partialBytes), i > 0 && !a)
    return t.putBytes(this._partialOutput.getBytes(
      i - this._partialBytes
    )), this._partialBytes = i, !0;
  t.putBytes(this._partialOutput.getBytes(
    r - this._partialBytes
  )), this._partialBytes = 0;
};
Le.ofb.prototype.decrypt = Le.ofb.prototype.encrypt;
Le.ctr = function(e) {
  e = e || {}, this.name = "CTR", this.cipher = e.cipher, this.blockSize = e.blockSize || 16, this._ints = this.blockSize / 4, this._inBlock = null, this._outBlock = new Array(this._ints), this._partialOutput = ut.util.createBuffer(), this._partialBytes = 0;
};
Le.ctr.prototype.start = function(e) {
  if (!("iv" in e))
    throw new Error("Invalid IV parameter.");
  this._iv = wn(e.iv, this.blockSize), this._inBlock = this._iv.slice(0), this._partialBytes = 0;
};
Le.ctr.prototype.encrypt = function(e, t, a) {
  var r = e.length();
  if (r === 0)
    return !0;
  if (this.cipher.encrypt(this._inBlock, this._outBlock), this._partialBytes === 0 && r >= this.blockSize)
    for (var n = 0; n < this._ints; ++n)
      t.putInt32(e.getInt32() ^ this._outBlock[n]);
  else {
    var i = (this.blockSize - r) % this.blockSize;
    i > 0 && (i = this.blockSize - i), this._partialOutput.clear();
    for (var n = 0; n < this._ints; ++n)
      this._partialOutput.putInt32(e.getInt32() ^ this._outBlock[n]);
    if (i > 0 && (e.read -= this.blockSize), this._partialBytes > 0 && this._partialOutput.getBytes(this._partialBytes), i > 0 && !a)
      return t.putBytes(this._partialOutput.getBytes(
        i - this._partialBytes
      )), this._partialBytes = i, !0;
    t.putBytes(this._partialOutput.getBytes(
      r - this._partialBytes
    )), this._partialBytes = 0;
  }
  Dn(this._inBlock);
};
Le.ctr.prototype.decrypt = Le.ctr.prototype.encrypt;
Le.gcm = function(e) {
  e = e || {}, this.name = "GCM", this.cipher = e.cipher, this.blockSize = e.blockSize || 16, this._ints = this.blockSize / 4, this._inBlock = new Array(this._ints), this._outBlock = new Array(this._ints), this._partialOutput = ut.util.createBuffer(), this._partialBytes = 0, this._R = 3774873600;
};
Le.gcm.prototype.start = function(e) {
  if (!("iv" in e))
    throw new Error("Invalid IV parameter.");
  var t = ut.util.createBuffer(e.iv);
  this._cipherLength = 0;
  var a;
  if ("additionalData" in e ? a = ut.util.createBuffer(e.additionalData) : a = ut.util.createBuffer(), "tagLength" in e ? this._tagLength = e.tagLength : this._tagLength = 128, this._tag = null, e.decrypt && (this._tag = ut.util.createBuffer(e.tag).getBytes(), this._tag.length !== this._tagLength / 8))
    throw new Error("Authentication tag does not match tag length.");
  this._hashBlock = new Array(this._ints), this.tag = null, this._hashSubkey = new Array(this._ints), this.cipher.encrypt([0, 0, 0, 0], this._hashSubkey), this.componentBits = 4, this._m = this.generateHashTable(this._hashSubkey, this.componentBits);
  var r = t.length();
  if (r === 12)
    this._j0 = [t.getInt32(), t.getInt32(), t.getInt32(), 1];
  else {
    for (this._j0 = [0, 0, 0, 0]; t.length() > 0; )
      this._j0 = this.ghash(
        this._hashSubkey,
        this._j0,
        [t.getInt32(), t.getInt32(), t.getInt32(), t.getInt32()]
      );
    this._j0 = this.ghash(
      this._hashSubkey,
      this._j0,
      [0, 0].concat(Oi(r * 8))
    );
  }
  this._inBlock = this._j0.slice(0), Dn(this._inBlock), this._partialBytes = 0, a = ut.util.createBuffer(a), this._aDataLength = Oi(a.length() * 8);
  var n = a.length() % this.blockSize;
  for (n && a.fillWithByte(0, this.blockSize - n), this._s = [0, 0, 0, 0]; a.length() > 0; )
    this._s = this.ghash(this._hashSubkey, this._s, [
      a.getInt32(),
      a.getInt32(),
      a.getInt32(),
      a.getInt32()
    ]);
};
Le.gcm.prototype.encrypt = function(e, t, a) {
  var r = e.length();
  if (r === 0)
    return !0;
  if (this.cipher.encrypt(this._inBlock, this._outBlock), this._partialBytes === 0 && r >= this.blockSize) {
    for (var n = 0; n < this._ints; ++n)
      t.putInt32(this._outBlock[n] ^= e.getInt32());
    this._cipherLength += this.blockSize;
  } else {
    var i = (this.blockSize - r) % this.blockSize;
    i > 0 && (i = this.blockSize - i), this._partialOutput.clear();
    for (var n = 0; n < this._ints; ++n)
      this._partialOutput.putInt32(e.getInt32() ^ this._outBlock[n]);
    if (i <= 0 || a) {
      if (a) {
        var s = r % this.blockSize;
        this._cipherLength += s, this._partialOutput.truncate(this.blockSize - s);
      } else
        this._cipherLength += this.blockSize;
      for (var n = 0; n < this._ints; ++n)
        this._outBlock[n] = this._partialOutput.getInt32();
      this._partialOutput.read -= this.blockSize;
    }
    if (this._partialBytes > 0 && this._partialOutput.getBytes(this._partialBytes), i > 0 && !a)
      return e.read -= this.blockSize, t.putBytes(this._partialOutput.getBytes(
        i - this._partialBytes
      )), this._partialBytes = i, !0;
    t.putBytes(this._partialOutput.getBytes(
      r - this._partialBytes
    )), this._partialBytes = 0;
  }
  this._s = this.ghash(this._hashSubkey, this._s, this._outBlock), Dn(this._inBlock);
};
Le.gcm.prototype.decrypt = function(e, t, a) {
  var r = e.length();
  if (r < this.blockSize && !(a && r > 0))
    return !0;
  this.cipher.encrypt(this._inBlock, this._outBlock), Dn(this._inBlock), this._hashBlock[0] = e.getInt32(), this._hashBlock[1] = e.getInt32(), this._hashBlock[2] = e.getInt32(), this._hashBlock[3] = e.getInt32(), this._s = this.ghash(this._hashSubkey, this._s, this._hashBlock);
  for (var n = 0; n < this._ints; ++n)
    t.putInt32(this._outBlock[n] ^ this._hashBlock[n]);
  r < this.blockSize ? this._cipherLength += r % this.blockSize : this._cipherLength += this.blockSize;
};
Le.gcm.prototype.afterFinish = function(e, t) {
  var a = !0;
  t.decrypt && t.overflow && e.truncate(this.blockSize - t.overflow), this.tag = ut.util.createBuffer();
  var r = this._aDataLength.concat(Oi(this._cipherLength * 8));
  this._s = this.ghash(this._hashSubkey, this._s, r);
  var n = [];
  this.cipher.encrypt(this._j0, n);
  for (var i = 0; i < this._ints; ++i)
    this.tag.putInt32(this._s[i] ^ n[i]);
  return this.tag.truncate(this.tag.length() % (this._tagLength / 8)), t.decrypt && this.tag.bytes() !== this._tag && (a = !1), a;
};
Le.gcm.prototype.multiply = function(e, t) {
  for (var a = [0, 0, 0, 0], r = t.slice(0), n = 0; n < 128; ++n) {
    var i = e[n / 32 | 0] & 1 << 31 - n % 32;
    i && (a[0] ^= r[0], a[1] ^= r[1], a[2] ^= r[2], a[3] ^= r[3]), this.pow(r, r);
  }
  return a;
};
Le.gcm.prototype.pow = function(e, t) {
  for (var a = e[3] & 1, r = 3; r > 0; --r)
    t[r] = e[r] >>> 1 | (e[r - 1] & 1) << 31;
  t[0] = e[0] >>> 1, a && (t[0] ^= this._R);
};
Le.gcm.prototype.tableMultiply = function(e) {
  for (var t = [0, 0, 0, 0], a = 0; a < 32; ++a) {
    var r = a / 8 | 0, n = e[r] >>> (7 - a % 8) * 4 & 15, i = this._m[a][n];
    t[0] ^= i[0], t[1] ^= i[1], t[2] ^= i[2], t[3] ^= i[3];
  }
  return t;
};
Le.gcm.prototype.ghash = function(e, t, a) {
  return t[0] ^= a[0], t[1] ^= a[1], t[2] ^= a[2], t[3] ^= a[3], this.tableMultiply(t);
};
Le.gcm.prototype.generateHashTable = function(e, t) {
  for (var a = 8 / t, r = 4 * a, n = 16 * a, i = new Array(n), s = 0; s < n; ++s) {
    var o = [0, 0, 0, 0], u = s / r | 0, l = (r - 1 - s % r) * t;
    o[u] = 1 << t - 1 << l, i[s] = this.generateSubHashTable(this.multiply(o, e), t);
  }
  return i;
};
Le.gcm.prototype.generateSubHashTable = function(e, t) {
  var a = 1 << t, r = a >>> 1, n = new Array(a);
  n[r] = e.slice(0);
  for (var i = r >>> 1; i > 0; )
    this.pow(n[2 * i], n[i] = []), i >>= 1;
  for (i = 2; i < r; ) {
    for (var s = 1; s < i; ++s) {
      var o = n[i], u = n[s];
      n[i + s] = [
        o[0] ^ u[0],
        o[1] ^ u[1],
        o[2] ^ u[2],
        o[3] ^ u[3]
      ];
    }
    i *= 2;
  }
  for (n[0] = [0, 0, 0, 0], i = r + 1; i < a; ++i) {
    var l = n[i ^ r];
    n[i] = [e[0] ^ l[0], e[1] ^ l[1], e[2] ^ l[2], e[3] ^ l[3]];
  }
  return n;
};
function wn(e, t) {
  if (typeof e == "string" && (e = ut.util.createBuffer(e)), ut.util.isArray(e) && e.length > 4) {
    var a = e;
    e = ut.util.createBuffer();
    for (var r = 0; r < a.length; ++r)
      e.putByte(a[r]);
  }
  if (e.length() < t)
    throw new Error(
      "Invalid IV length; got " + e.length() + " bytes and expected " + t + " bytes."
    );
  if (!ut.util.isArray(e)) {
    for (var n = [], i = t / 4, r = 0; r < i; ++r)
      n.push(e.getInt32());
    e = n;
  }
  return e;
}
function Dn(e) {
  e[e.length - 1] = e[e.length - 1] + 1 & 4294967295;
}
function Oi(e) {
  return [e / 4294967296 | 0, e & 4294967295];
}
var Qe = Re;
Qe.aes = Qe.aes || {};
Qe.aes.startEncrypting = function(e, t, a, r) {
  var n = Nn({
    key: e,
    output: a,
    decrypt: !1,
    mode: r
  });
  return n.start(t), n;
};
Qe.aes.createEncryptionCipher = function(e, t) {
  return Nn({
    key: e,
    output: null,
    decrypt: !1,
    mode: t
  });
};
Qe.aes.startDecrypting = function(e, t, a, r) {
  var n = Nn({
    key: e,
    output: a,
    decrypt: !0,
    mode: r
  });
  return n.start(t), n;
};
Qe.aes.createDecryptionCipher = function(e, t) {
  return Nn({
    key: e,
    output: null,
    decrypt: !0,
    mode: t
  });
};
Qe.aes.Algorithm = function(e, t) {
  m0 || ll();
  var a = this;
  a.name = e, a.mode = new t({
    blockSize: 16,
    cipher: {
      encrypt: function(r, n) {
        return Mi(a._w, r, n, !1);
      },
      decrypt: function(r, n) {
        return Mi(a._w, r, n, !0);
      }
    }
  }), a._init = !1;
};
Qe.aes.Algorithm.prototype.initialize = function(e) {
  if (!this._init) {
    var t = e.key, a;
    if (typeof t == "string" && (t.length === 16 || t.length === 24 || t.length === 32))
      t = Qe.util.createBuffer(t);
    else if (Qe.util.isArray(t) && (t.length === 16 || t.length === 24 || t.length === 32)) {
      a = t, t = Qe.util.createBuffer();
      for (var r = 0; r < a.length; ++r)
        t.putByte(a[r]);
    }
    if (!Qe.util.isArray(t)) {
      a = t, t = [];
      var n = a.length();
      if (n === 16 || n === 24 || n === 32) {
        n = n >>> 2;
        for (var r = 0; r < n; ++r)
          t.push(a.getInt32());
      }
    }
    if (!Qe.util.isArray(t) || !(t.length === 4 || t.length === 6 || t.length === 8))
      throw new Error("Invalid key parameter.");
    var i = this.mode.name, s = ["CFB", "OFB", "CTR", "GCM"].indexOf(i) !== -1;
    this._w = cl(t, e.decrypt && !s), this._init = !0;
  }
};
Qe.aes._expandKey = function(e, t) {
  return m0 || ll(), cl(e, t);
};
Qe.aes._updateBlock = Mi;
da("AES-ECB", Qe.cipher.modes.ecb);
da("AES-CBC", Qe.cipher.modes.cbc);
da("AES-CFB", Qe.cipher.modes.cfb);
da("AES-OFB", Qe.cipher.modes.ofb);
da("AES-CTR", Qe.cipher.modes.ctr);
da("AES-GCM", Qe.cipher.modes.gcm);
function da(e, t) {
  var a = function() {
    return new Qe.aes.Algorithm(e, t);
  };
  Qe.cipher.registerAlgorithm(e, a);
}
var m0 = !1, Wr = 4, mt, Vi, ol, kr, Ht;
function ll() {
  m0 = !0, ol = [0, 1, 2, 4, 8, 16, 32, 64, 128, 27, 54];
  for (var e = new Array(256), t = 0; t < 128; ++t)
    e[t] = t << 1, e[t + 128] = t + 128 << 1 ^ 283;
  mt = new Array(256), Vi = new Array(256), kr = new Array(4), Ht = new Array(4);
  for (var t = 0; t < 4; ++t)
    kr[t] = new Array(256), Ht[t] = new Array(256);
  for (var a = 0, r = 0, n, i, s, o, u, l, c, t = 0; t < 256; ++t) {
    o = r ^ r << 1 ^ r << 2 ^ r << 3 ^ r << 4, o = o >> 8 ^ o & 255 ^ 99, mt[a] = o, Vi[o] = a, u = e[o], n = e[a], i = e[n], s = e[i], l = u << 24 ^ // 2
    o << 16 ^ // 1
    o << 8 ^ // 1
    (o ^ u), c = (n ^ i ^ s) << 24 ^ // E (14)
    (a ^ s) << 16 ^ // 9
    (a ^ i ^ s) << 8 ^ // D (13)
    (a ^ n ^ s);
    for (var f = 0; f < 4; ++f)
      kr[f][a] = l, Ht[f][o] = c, l = l << 24 | l >>> 8, c = c << 24 | c >>> 8;
    a === 0 ? a = r = 1 : (a = n ^ e[e[e[n ^ s]]], r ^= e[e[r]]);
  }
}
function cl(e, t) {
  for (var a = e.slice(0), r, n = 1, i = a.length, s = i + 6 + 1, o = Wr * s, u = i; u < o; ++u)
    r = a[u - 1], u % i === 0 ? (r = mt[r >>> 16 & 255] << 24 ^ mt[r >>> 8 & 255] << 16 ^ mt[r & 255] << 8 ^ mt[r >>> 24] ^ ol[n] << 24, n++) : i > 6 && u % i === 4 && (r = mt[r >>> 24] << 24 ^ mt[r >>> 16 & 255] << 16 ^ mt[r >>> 8 & 255] << 8 ^ mt[r & 255]), a[u] = a[u - i] ^ r;
  if (t) {
    var l, c = Ht[0], f = Ht[1], d = Ht[2], v = Ht[3], p = a.slice(0);
    o = a.length;
    for (var u = 0, h = o - Wr; u < o; u += Wr, h -= Wr)
      if (u === 0 || u === o - Wr)
        p[u] = a[h], p[u + 1] = a[h + 3], p[u + 2] = a[h + 2], p[u + 3] = a[h + 1];
      else
        for (var x = 0; x < Wr; ++x)
          l = a[h + x], p[u + (3 & -x)] = c[mt[l >>> 24]] ^ f[mt[l >>> 16 & 255]] ^ d[mt[l >>> 8 & 255]] ^ v[mt[l & 255]];
    a = p;
  }
  return a;
}
function Mi(e, t, a, r) {
  var n = e.length / 4 - 1, i, s, o, u, l;
  r ? (i = Ht[0], s = Ht[1], o = Ht[2], u = Ht[3], l = Vi) : (i = kr[0], s = kr[1], o = kr[2], u = kr[3], l = mt);
  var c, f, d, v, p, h, x;
  c = t[0] ^ e[0], f = t[r ? 3 : 1] ^ e[1], d = t[2] ^ e[2], v = t[r ? 1 : 3] ^ e[3];
  for (var y = 3, g = 1; g < n; ++g)
    p = i[c >>> 24] ^ s[f >>> 16 & 255] ^ o[d >>> 8 & 255] ^ u[v & 255] ^ e[++y], h = i[f >>> 24] ^ s[d >>> 16 & 255] ^ o[v >>> 8 & 255] ^ u[c & 255] ^ e[++y], x = i[d >>> 24] ^ s[v >>> 16 & 255] ^ o[c >>> 8 & 255] ^ u[f & 255] ^ e[++y], v = i[v >>> 24] ^ s[c >>> 16 & 255] ^ o[f >>> 8 & 255] ^ u[d & 255] ^ e[++y], c = p, f = h, d = x;
  a[0] = l[c >>> 24] << 24 ^ l[f >>> 16 & 255] << 16 ^ l[d >>> 8 & 255] << 8 ^ l[v & 255] ^ e[++y], a[r ? 3 : 1] = l[f >>> 24] << 24 ^ l[d >>> 16 & 255] << 16 ^ l[v >>> 8 & 255] << 8 ^ l[c & 255] ^ e[++y], a[2] = l[d >>> 24] << 24 ^ l[v >>> 16 & 255] << 16 ^ l[c >>> 8 & 255] << 8 ^ l[f & 255] ^ e[++y], a[r ? 1 : 3] = l[v >>> 24] << 24 ^ l[c >>> 16 & 255] << 16 ^ l[f >>> 8 & 255] << 8 ^ l[d & 255] ^ e[++y];
}
function Nn(e) {
  e = e || {};
  var t = (e.mode || "CBC").toUpperCase(), a = "AES-" + t, r;
  e.decrypt ? r = Qe.cipher.createDecipher(a, e.key) : r = Qe.cipher.createCipher(a, e.key);
  var n = r.start;
  return r.start = function(i, s) {
    var o = null;
    s instanceof Qe.util.ByteBuffer && (o = s, s = {}), s = s || {}, s.output = o, s.iv = i, n.call(r, s);
  }, r;
}
var Ia = Re;
Ia.pki = Ia.pki || {};
var Ki = Ia.pki.oids = Ia.oids = Ia.oids || {};
function Y(e, t) {
  Ki[e] = t, Ki[t] = e;
}
function je(e, t) {
  Ki[e] = t;
}
Y("1.2.840.113549.1.1.1", "rsaEncryption");
Y("1.2.840.113549.1.1.4", "md5WithRSAEncryption");
Y("1.2.840.113549.1.1.5", "sha1WithRSAEncryption");
Y("1.2.840.113549.1.1.7", "RSAES-OAEP");
Y("1.2.840.113549.1.1.8", "mgf1");
Y("1.2.840.113549.1.1.9", "pSpecified");
Y("1.2.840.113549.1.1.10", "RSASSA-PSS");
Y("1.2.840.113549.1.1.11", "sha256WithRSAEncryption");
Y("1.2.840.113549.1.1.12", "sha384WithRSAEncryption");
Y("1.2.840.113549.1.1.13", "sha512WithRSAEncryption");
Y("1.3.101.112", "EdDSA25519");
Y("1.2.840.10040.4.3", "dsa-with-sha1");
Y("1.3.14.3.2.7", "desCBC");
Y("1.3.14.3.2.26", "sha1");
Y("1.3.14.3.2.29", "sha1WithRSASignature");
Y("2.16.840.1.101.3.4.2.1", "sha256");
Y("2.16.840.1.101.3.4.2.2", "sha384");
Y("2.16.840.1.101.3.4.2.3", "sha512");
Y("2.16.840.1.101.3.4.2.4", "sha224");
Y("2.16.840.1.101.3.4.2.5", "sha512-224");
Y("2.16.840.1.101.3.4.2.6", "sha512-256");
Y("1.2.840.113549.2.2", "md2");
Y("1.2.840.113549.2.5", "md5");
Y("1.2.840.113549.1.7.1", "data");
Y("1.2.840.113549.1.7.2", "signedData");
Y("1.2.840.113549.1.7.3", "envelopedData");
Y("1.2.840.113549.1.7.4", "signedAndEnvelopedData");
Y("1.2.840.113549.1.7.5", "digestedData");
Y("1.2.840.113549.1.7.6", "encryptedData");
Y("1.2.840.113549.1.9.1", "emailAddress");
Y("1.2.840.113549.1.9.2", "unstructuredName");
Y("1.2.840.113549.1.9.3", "contentType");
Y("1.2.840.113549.1.9.4", "messageDigest");
Y("1.2.840.113549.1.9.5", "signingTime");
Y("1.2.840.113549.1.9.6", "counterSignature");
Y("1.2.840.113549.1.9.7", "challengePassword");
Y("1.2.840.113549.1.9.8", "unstructuredAddress");
Y("1.2.840.113549.1.9.14", "extensionRequest");
Y("1.2.840.113549.1.9.20", "friendlyName");
Y("1.2.840.113549.1.9.21", "localKeyId");
Y("1.2.840.113549.1.9.22.1", "x509Certificate");
Y("1.2.840.113549.1.12.10.1.1", "keyBag");
Y("1.2.840.113549.1.12.10.1.2", "pkcs8ShroudedKeyBag");
Y("1.2.840.113549.1.12.10.1.3", "certBag");
Y("1.2.840.113549.1.12.10.1.4", "crlBag");
Y("1.2.840.113549.1.12.10.1.5", "secretBag");
Y("1.2.840.113549.1.12.10.1.6", "safeContentsBag");
Y("1.2.840.113549.1.5.13", "pkcs5PBES2");
Y("1.2.840.113549.1.5.12", "pkcs5PBKDF2");
Y("1.2.840.113549.1.12.1.1", "pbeWithSHAAnd128BitRC4");
Y("1.2.840.113549.1.12.1.2", "pbeWithSHAAnd40BitRC4");
Y("1.2.840.113549.1.12.1.3", "pbeWithSHAAnd3-KeyTripleDES-CBC");
Y("1.2.840.113549.1.12.1.4", "pbeWithSHAAnd2-KeyTripleDES-CBC");
Y("1.2.840.113549.1.12.1.5", "pbeWithSHAAnd128BitRC2-CBC");
Y("1.2.840.113549.1.12.1.6", "pbewithSHAAnd40BitRC2-CBC");
Y("1.2.840.113549.2.7", "hmacWithSHA1");
Y("1.2.840.113549.2.8", "hmacWithSHA224");
Y("1.2.840.113549.2.9", "hmacWithSHA256");
Y("1.2.840.113549.2.10", "hmacWithSHA384");
Y("1.2.840.113549.2.11", "hmacWithSHA512");
Y("1.2.840.113549.3.7", "des-EDE3-CBC");
Y("2.16.840.1.101.3.4.1.2", "aes128-CBC");
Y("2.16.840.1.101.3.4.1.22", "aes192-CBC");
Y("2.16.840.1.101.3.4.1.42", "aes256-CBC");
Y("2.5.4.3", "commonName");
Y("2.5.4.4", "surname");
Y("2.5.4.5", "serialNumber");
Y("2.5.4.6", "countryName");
Y("2.5.4.7", "localityName");
Y("2.5.4.8", "stateOrProvinceName");
Y("2.5.4.9", "streetAddress");
Y("2.5.4.10", "organizationName");
Y("2.5.4.11", "organizationalUnitName");
Y("2.5.4.12", "title");
Y("2.5.4.13", "description");
Y("2.5.4.15", "businessCategory");
Y("2.5.4.17", "postalCode");
Y("2.5.4.42", "givenName");
Y("1.3.6.1.4.1.311.60.2.1.2", "jurisdictionOfIncorporationStateOrProvinceName");
Y("1.3.6.1.4.1.311.60.2.1.3", "jurisdictionOfIncorporationCountryName");
Y("2.16.840.1.113730.1.1", "nsCertType");
Y("2.16.840.1.113730.1.13", "nsComment");
je("2.5.29.1", "authorityKeyIdentifier");
je("2.5.29.2", "keyAttributes");
je("2.5.29.3", "certificatePolicies");
je("2.5.29.4", "keyUsageRestriction");
je("2.5.29.5", "policyMapping");
je("2.5.29.6", "subtreesConstraint");
je("2.5.29.7", "subjectAltName");
je("2.5.29.8", "issuerAltName");
je("2.5.29.9", "subjectDirectoryAttributes");
je("2.5.29.10", "basicConstraints");
je("2.5.29.11", "nameConstraints");
je("2.5.29.12", "policyConstraints");
je("2.5.29.13", "basicConstraints");
Y("2.5.29.14", "subjectKeyIdentifier");
Y("2.5.29.15", "keyUsage");
je("2.5.29.16", "privateKeyUsagePeriod");
Y("2.5.29.17", "subjectAltName");
Y("2.5.29.18", "issuerAltName");
Y("2.5.29.19", "basicConstraints");
je("2.5.29.20", "cRLNumber");
je("2.5.29.21", "cRLReason");
je("2.5.29.22", "expirationDate");
je("2.5.29.23", "instructionCode");
je("2.5.29.24", "invalidityDate");
je("2.5.29.25", "cRLDistributionPoints");
je("2.5.29.26", "issuingDistributionPoint");
je("2.5.29.27", "deltaCRLIndicator");
je("2.5.29.28", "issuingDistributionPoint");
je("2.5.29.29", "certificateIssuer");
je("2.5.29.30", "nameConstraints");
Y("2.5.29.31", "cRLDistributionPoints");
Y("2.5.29.32", "certificatePolicies");
je("2.5.29.33", "policyMappings");
je("2.5.29.34", "policyConstraints");
Y("2.5.29.35", "authorityKeyIdentifier");
je("2.5.29.36", "policyConstraints");
Y("2.5.29.37", "extKeyUsage");
je("2.5.29.46", "freshestCRL");
je("2.5.29.54", "inhibitAnyPolicy");
Y("1.3.6.1.4.1.11129.2.4.2", "timestampList");
Y("1.3.6.1.5.5.7.1.1", "authorityInfoAccess");
Y("1.3.6.1.5.5.7.3.1", "serverAuth");
Y("1.3.6.1.5.5.7.3.2", "clientAuth");
Y("1.3.6.1.5.5.7.3.3", "codeSigning");
Y("1.3.6.1.5.5.7.3.4", "emailProtection");
Y("1.3.6.1.5.5.7.3.8", "timeStamping");
var Je = Re, ne = Je.asn1 = Je.asn1 || {};
ne.Class = {
  UNIVERSAL: 0,
  APPLICATION: 64,
  CONTEXT_SPECIFIC: 128,
  PRIVATE: 192
};
ne.Type = {
  NONE: 0,
  BOOLEAN: 1,
  INTEGER: 2,
  BITSTRING: 3,
  OCTETSTRING: 4,
  NULL: 5,
  OID: 6,
  ODESC: 7,
  EXTERNAL: 8,
  REAL: 9,
  ENUMERATED: 10,
  EMBEDDED: 11,
  UTF8: 12,
  ROID: 13,
  SEQUENCE: 16,
  SET: 17,
  PRINTABLESTRING: 19,
  IA5STRING: 22,
  UTCTIME: 23,
  GENERALIZEDTIME: 24,
  BMPSTRING: 30
};
ne.create = function(e, t, a, r, n) {
  if (Je.util.isArray(r)) {
    for (var i = [], s = 0; s < r.length; ++s)
      r[s] !== void 0 && i.push(r[s]);
    r = i;
  }
  var o = {
    tagClass: e,
    type: t,
    constructed: a,
    composed: a || Je.util.isArray(r),
    value: r
  };
  return n && "bitStringContents" in n && (o.bitStringContents = n.bitStringContents, o.original = ne.copy(o)), o;
};
ne.copy = function(e, t) {
  var a;
  if (Je.util.isArray(e)) {
    a = [];
    for (var r = 0; r < e.length; ++r)
      a.push(ne.copy(e[r], t));
    return a;
  }
  return typeof e == "string" ? e : (a = {
    tagClass: e.tagClass,
    type: e.type,
    constructed: e.constructed,
    composed: e.composed,
    value: ne.copy(e.value, t)
  }, t && !t.excludeBitStringContents && (a.bitStringContents = e.bitStringContents), a);
};
ne.equals = function(e, t, a) {
  if (Je.util.isArray(e)) {
    if (!Je.util.isArray(t) || e.length !== t.length)
      return !1;
    for (var r = 0; r < e.length; ++r)
      if (!ne.equals(e[r], t[r]))
        return !1;
    return !0;
  }
  if (typeof e != typeof t)
    return !1;
  if (typeof e == "string")
    return e === t;
  var n = e.tagClass === t.tagClass && e.type === t.type && e.constructed === t.constructed && e.composed === t.composed && ne.equals(e.value, t.value);
  return a && a.includeBitStringContents && (n = n && e.bitStringContents === t.bitStringContents), n;
};
ne.getBerValueLength = function(e) {
  var t = e.getByte();
  if (t !== 128) {
    var a, r = t & 128;
    return r ? a = e.getInt((t & 127) << 3) : a = t, a;
  }
};
function Sa(e, t, a) {
  if (a > t) {
    var r = new Error("Too few bytes to parse DER.");
    throw r.available = e.length(), r.remaining = t, r.requested = a, r;
  }
}
var Ih = function(e, t) {
  var a = e.getByte();
  if (t--, a !== 128) {
    var r, n = a & 128;
    if (!n)
      r = a;
    else {
      var i = a & 127;
      Sa(e, t, i), r = e.getInt(i << 3);
    }
    if (r < 0)
      throw new Error("Negative length: " + r);
    return r;
  }
};
ne.fromDer = function(e, t) {
  t === void 0 && (t = {
    strict: !0,
    parseAllBytes: !0,
    decodeBitStrings: !0
  }), typeof t == "boolean" && (t = {
    strict: t,
    parseAllBytes: !0,
    decodeBitStrings: !0
  }), "strict" in t || (t.strict = !0), "parseAllBytes" in t || (t.parseAllBytes = !0), "decodeBitStrings" in t || (t.decodeBitStrings = !0), typeof e == "string" && (e = Je.util.createBuffer(e));
  var a = e.length(), r = rn(e, e.length(), 0, t);
  if (t.parseAllBytes && e.length() !== 0) {
    var n = new Error("Unparsed DER bytes remain after ASN.1 parsing.");
    throw n.byteCount = a, n.remaining = e.length(), n;
  }
  return r;
};
function rn(e, t, a, r) {
  var n;
  Sa(e, t, 2);
  var i = e.getByte();
  t--;
  var s = i & 192, o = i & 31;
  n = e.length();
  var u = Ih(e, t);
  if (t -= n - e.length(), u !== void 0 && u > t) {
    if (r.strict) {
      var l = new Error("Too few bytes to read ASN.1 value.");
      throw l.available = e.length(), l.remaining = t, l.requested = u, l;
    }
    u = t;
  }
  var c, f, d = (i & 32) === 32;
  if (d)
    if (c = [], u === void 0)
      for (; ; ) {
        if (Sa(e, t, 2), e.bytes(2) === "\0\0") {
          e.getBytes(2), t -= 2;
          break;
        }
        n = e.length(), c.push(rn(e, t, a + 1, r)), t -= n - e.length();
      }
    else
      for (; u > 0; )
        n = e.length(), c.push(rn(e, u, a + 1, r)), t -= n - e.length(), u -= n - e.length();
  if (c === void 0 && s === ne.Class.UNIVERSAL && o === ne.Type.BITSTRING && (f = e.bytes(u)), c === void 0 && r.decodeBitStrings && s === ne.Class.UNIVERSAL && // FIXME: OCTET STRINGs not yet supported here
  // .. other parts of forge expect to decode OCTET STRINGs manually
  o === ne.Type.BITSTRING && u > 1) {
    var v = e.read, p = t, h = 0;
    if (o === ne.Type.BITSTRING && (Sa(e, t, 1), h = e.getByte(), t--), h === 0)
      try {
        n = e.length();
        var x = {
          // enforce strict mode to avoid parsing ASN.1 from plain data
          strict: !0,
          decodeBitStrings: !0
        }, y = rn(e, t, a + 1, x), g = n - e.length();
        t -= g, o == ne.Type.BITSTRING && g++;
        var m = y.tagClass;
        g === u && (m === ne.Class.UNIVERSAL || m === ne.Class.CONTEXT_SPECIFIC) && (c = [y]);
      } catch {
      }
    c === void 0 && (e.read = v, t = p);
  }
  if (c === void 0) {
    if (u === void 0) {
      if (r.strict)
        throw new Error("Non-constructed ASN.1 object of indefinite length.");
      u = t;
    }
    if (o === ne.Type.BMPSTRING)
      for (c = ""; u > 0; u -= 2)
        Sa(e, t, 2), c += String.fromCharCode(e.getInt16()), t -= 2;
    else
      c = e.getBytes(u), t -= u;
  }
  var C = f === void 0 ? null : {
    bitStringContents: f
  };
  return ne.create(s, o, d, c, C);
}
ne.toDer = function(e) {
  var t = Je.util.createBuffer(), a = e.tagClass | e.type, r = Je.util.createBuffer(), n = !1;
  if ("bitStringContents" in e && (n = !0, e.original && (n = ne.equals(e, e.original))), n)
    r.putBytes(e.bitStringContents);
  else if (e.composed) {
    e.constructed ? a |= 32 : r.putByte(0);
    for (var i = 0; i < e.value.length; ++i)
      e.value[i] !== void 0 && r.putBuffer(ne.toDer(e.value[i]));
  } else if (e.type === ne.Type.BMPSTRING)
    for (var i = 0; i < e.value.length; ++i)
      r.putInt16(e.value.charCodeAt(i));
  else
    e.type === ne.Type.INTEGER && e.value.length > 1 && // leading 0x00 for positive integer
    (e.value.charCodeAt(0) === 0 && !(e.value.charCodeAt(1) & 128) || // leading 0xFF for negative integer
    e.value.charCodeAt(0) === 255 && (e.value.charCodeAt(1) & 128) === 128) ? r.putBytes(e.value.substr(1)) : r.putBytes(e.value);
  if (t.putByte(a), r.length() <= 127)
    t.putByte(r.length() & 127);
  else {
    var s = r.length(), o = "";
    do
      o += String.fromCharCode(s & 255), s = s >>> 8;
    while (s > 0);
    t.putByte(o.length | 128);
    for (var i = o.length - 1; i >= 0; --i)
      t.putByte(o.charCodeAt(i));
  }
  return t.putBuffer(r), t;
};
ne.oidToDer = function(e) {
  var t = e.split("."), a = Je.util.createBuffer();
  a.putByte(40 * parseInt(t[0], 10) + parseInt(t[1], 10));
  for (var r, n, i, s, o = 2; o < t.length; ++o) {
    r = !0, n = [], i = parseInt(t[o], 10);
    do
      s = i & 127, i = i >>> 7, r || (s |= 128), n.push(s), r = !1;
    while (i > 0);
    for (var u = n.length - 1; u >= 0; --u)
      a.putByte(n[u]);
  }
  return a;
};
ne.derToOid = function(e) {
  var t;
  typeof e == "string" && (e = Je.util.createBuffer(e));
  var a = e.getByte();
  t = Math.floor(a / 40) + "." + a % 40;
  for (var r = 0; e.length() > 0; )
    a = e.getByte(), r = r << 7, a & 128 ? r += a & 127 : (t += "." + (r + a), r = 0);
  return t;
};
ne.utcTimeToDate = function(e) {
  var t = /* @__PURE__ */ new Date(), a = parseInt(e.substr(0, 2), 10);
  a = a >= 50 ? 1900 + a : 2e3 + a;
  var r = parseInt(e.substr(2, 2), 10) - 1, n = parseInt(e.substr(4, 2), 10), i = parseInt(e.substr(6, 2), 10), s = parseInt(e.substr(8, 2), 10), o = 0;
  if (e.length > 11) {
    var u = e.charAt(10), l = 10;
    u !== "+" && u !== "-" && (o = parseInt(e.substr(10, 2), 10), l += 2);
  }
  if (t.setUTCFullYear(a, r, n), t.setUTCHours(i, s, o, 0), l && (u = e.charAt(l), u === "+" || u === "-")) {
    var c = parseInt(e.substr(l + 1, 2), 10), f = parseInt(e.substr(l + 4, 2), 10), d = c * 60 + f;
    d *= 6e4, u === "+" ? t.setTime(+t - d) : t.setTime(+t + d);
  }
  return t;
};
ne.generalizedTimeToDate = function(e) {
  var t = /* @__PURE__ */ new Date(), a = parseInt(e.substr(0, 4), 10), r = parseInt(e.substr(4, 2), 10) - 1, n = parseInt(e.substr(6, 2), 10), i = parseInt(e.substr(8, 2), 10), s = parseInt(e.substr(10, 2), 10), o = parseInt(e.substr(12, 2), 10), u = 0, l = 0, c = !1;
  e.charAt(e.length - 1) === "Z" && (c = !0);
  var f = e.length - 5, d = e.charAt(f);
  if (d === "+" || d === "-") {
    var v = parseInt(e.substr(f + 1, 2), 10), p = parseInt(e.substr(f + 4, 2), 10);
    l = v * 60 + p, l *= 6e4, d === "+" && (l *= -1), c = !0;
  }
  return e.charAt(14) === "." && (u = parseFloat(e.substr(14), 10) * 1e3), c ? (t.setUTCFullYear(a, r, n), t.setUTCHours(i, s, o, u), t.setTime(+t + l)) : (t.setFullYear(a, r, n), t.setHours(i, s, o, u)), t;
};
ne.dateToUtcTime = function(e) {
  if (typeof e == "string")
    return e;
  var t = "", a = [];
  a.push(("" + e.getUTCFullYear()).substr(2)), a.push("" + (e.getUTCMonth() + 1)), a.push("" + e.getUTCDate()), a.push("" + e.getUTCHours()), a.push("" + e.getUTCMinutes()), a.push("" + e.getUTCSeconds());
  for (var r = 0; r < a.length; ++r)
    a[r].length < 2 && (t += "0"), t += a[r];
  return t += "Z", t;
};
ne.dateToGeneralizedTime = function(e) {
  if (typeof e == "string")
    return e;
  var t = "", a = [];
  a.push("" + e.getUTCFullYear()), a.push("" + (e.getUTCMonth() + 1)), a.push("" + e.getUTCDate()), a.push("" + e.getUTCHours()), a.push("" + e.getUTCMinutes()), a.push("" + e.getUTCSeconds());
  for (var r = 0; r < a.length; ++r)
    a[r].length < 2 && (t += "0"), t += a[r];
  return t += "Z", t;
};
ne.integerToDer = function(e) {
  var t = Je.util.createBuffer();
  if (e >= -128 && e < 128)
    return t.putSignedInt(e, 8);
  if (e >= -32768 && e < 32768)
    return t.putSignedInt(e, 16);
  if (e >= -8388608 && e < 8388608)
    return t.putSignedInt(e, 24);
  if (e >= -2147483648 && e < 2147483648)
    return t.putSignedInt(e, 32);
  var a = new Error("Integer too large; max is 32-bits.");
  throw a.integer = e, a;
};
ne.derToInteger = function(e) {
  typeof e == "string" && (e = Je.util.createBuffer(e));
  var t = e.length() * 8;
  if (t > 32)
    throw new Error("Integer too large; max is 32-bits.");
  return e.getSignedInt(t);
};
ne.validate = function(e, t, a, r) {
  var n = !1;
  if ((e.tagClass === t.tagClass || typeof t.tagClass > "u") && (e.type === t.type || typeof t.type > "u"))
    if (e.constructed === t.constructed || typeof t.constructed > "u") {
      if (n = !0, t.value && Je.util.isArray(t.value))
        for (var i = 0, s = 0; n && s < t.value.length; ++s)
          n = t.value[s].optional || !1, e.value[i] && (n = ne.validate(e.value[i], t.value[s], a, r), n ? ++i : t.value[s].optional && (n = !0)), !n && r && r.push(
            "[" + t.name + '] Tag class "' + t.tagClass + '", type "' + t.type + '" expected value length "' + t.value.length + '", got "' + e.value.length + '"'
          );
      if (n && a && (t.capture && (a[t.capture] = e.value), t.captureAsn1 && (a[t.captureAsn1] = e), t.captureBitStringContents && "bitStringContents" in e && (a[t.captureBitStringContents] = e.bitStringContents), t.captureBitStringValue && "bitStringContents" in e))
        if (e.bitStringContents.length < 2)
          a[t.captureBitStringValue] = "";
        else {
          var o = e.bitStringContents.charCodeAt(0);
          if (o !== 0)
            throw new Error(
              "captureBitStringValue only supported for zero unused bits"
            );
          a[t.captureBitStringValue] = e.bitStringContents.slice(1);
        }
    } else r && r.push(
      "[" + t.name + '] Expected constructed "' + t.constructed + '", got "' + e.constructed + '"'
    );
  else r && (e.tagClass !== t.tagClass && r.push(
    "[" + t.name + '] Expected tag class "' + t.tagClass + '", got "' + e.tagClass + '"'
  ), e.type !== t.type && r.push(
    "[" + t.name + '] Expected type "' + t.type + '", got "' + e.type + '"'
  ));
  return n;
};
var Qs = /[^\\u0000-\\u00ff]/;
ne.prettyPrint = function(e, t, a) {
  var r = "";
  t = t || 0, a = a || 2, t > 0 && (r += `
`);
  for (var n = "", i = 0; i < t * a; ++i)
    n += " ";
  switch (r += n + "Tag: ", e.tagClass) {
    case ne.Class.UNIVERSAL:
      r += "Universal:";
      break;
    case ne.Class.APPLICATION:
      r += "Application:";
      break;
    case ne.Class.CONTEXT_SPECIFIC:
      r += "Context-Specific:";
      break;
    case ne.Class.PRIVATE:
      r += "Private:";
      break;
  }
  if (e.tagClass === ne.Class.UNIVERSAL)
    switch (r += e.type, e.type) {
      case ne.Type.NONE:
        r += " (None)";
        break;
      case ne.Type.BOOLEAN:
        r += " (Boolean)";
        break;
      case ne.Type.INTEGER:
        r += " (Integer)";
        break;
      case ne.Type.BITSTRING:
        r += " (Bit string)";
        break;
      case ne.Type.OCTETSTRING:
        r += " (Octet string)";
        break;
      case ne.Type.NULL:
        r += " (Null)";
        break;
      case ne.Type.OID:
        r += " (Object Identifier)";
        break;
      case ne.Type.ODESC:
        r += " (Object Descriptor)";
        break;
      case ne.Type.EXTERNAL:
        r += " (External or Instance of)";
        break;
      case ne.Type.REAL:
        r += " (Real)";
        break;
      case ne.Type.ENUMERATED:
        r += " (Enumerated)";
        break;
      case ne.Type.EMBEDDED:
        r += " (Embedded PDV)";
        break;
      case ne.Type.UTF8:
        r += " (UTF8)";
        break;
      case ne.Type.ROID:
        r += " (Relative Object Identifier)";
        break;
      case ne.Type.SEQUENCE:
        r += " (Sequence)";
        break;
      case ne.Type.SET:
        r += " (Set)";
        break;
      case ne.Type.PRINTABLESTRING:
        r += " (Printable String)";
        break;
      case ne.Type.IA5String:
        r += " (IA5String (ASCII))";
        break;
      case ne.Type.UTCTIME:
        r += " (UTC time)";
        break;
      case ne.Type.GENERALIZEDTIME:
        r += " (Generalized time)";
        break;
      case ne.Type.BMPSTRING:
        r += " (BMP String)";
        break;
    }
  else
    r += e.type;
  if (r += `
`, r += n + "Constructed: " + e.constructed + `
`, e.composed) {
    for (var s = 0, o = "", i = 0; i < e.value.length; ++i)
      e.value[i] !== void 0 && (s += 1, o += ne.prettyPrint(e.value[i], t + 1, a), i + 1 < e.value.length && (o += ","));
    r += n + "Sub values: " + s + o;
  } else {
    if (r += n + "Value: ", e.type === ne.Type.OID) {
      var u = ne.derToOid(e.value);
      r += u, Je.pki && Je.pki.oids && u in Je.pki.oids && (r += " (" + Je.pki.oids[u] + ") ");
    }
    if (e.type === ne.Type.INTEGER)
      try {
        r += ne.derToInteger(e.value);
      } catch {
        r += "0x" + Je.util.bytesToHex(e.value);
      }
    else if (e.type === ne.Type.BITSTRING) {
      if (e.value.length > 1 ? r += "0x" + Je.util.bytesToHex(e.value.slice(1)) : r += "(none)", e.value.length > 0) {
        var l = e.value.charCodeAt(0);
        l == 1 ? r += " (1 unused bit shown)" : l > 1 && (r += " (" + l + " unused bits shown)");
      }
    } else if (e.type === ne.Type.OCTETSTRING)
      Qs.test(e.value) || (r += "(" + e.value + ") "), r += "0x" + Je.util.bytesToHex(e.value);
    else if (e.type === ne.Type.UTF8)
      try {
        r += Je.util.decodeUtf8(e.value);
      } catch (c) {
        if (c.message === "URI malformed")
          r += "0x" + Je.util.bytesToHex(e.value) + " (malformed UTF8)";
        else
          throw c;
      }
    else e.type === ne.Type.PRINTABLESTRING || e.type === ne.Type.IA5String ? r += e.value : Qs.test(e.value) ? r += "0x" + Je.util.bytesToHex(e.value) : e.value.length === 0 ? r += "[null]" : r += e.value;
  }
  return r;
};
var ln = Re;
ln.md = ln.md || {};
ln.md.algorithms = ln.md.algorithms || {};
var ar = Re, wh = ar.hmac = ar.hmac || {};
wh.create = function() {
  var e = null, t = null, a = null, r = null, n = {};
  return n.start = function(i, s) {
    if (i !== null)
      if (typeof i == "string")
        if (i = i.toLowerCase(), i in ar.md.algorithms)
          t = ar.md.algorithms[i].create();
        else
          throw new Error('Unknown hash algorithm "' + i + '"');
      else
        t = i;
    if (s === null)
      s = e;
    else {
      if (typeof s == "string")
        s = ar.util.createBuffer(s);
      else if (ar.util.isArray(s)) {
        var o = s;
        s = ar.util.createBuffer();
        for (var u = 0; u < o.length; ++u)
          s.putByte(o[u]);
      }
      var l = s.length();
      l > t.blockLength && (t.start(), t.update(s.bytes()), s = t.digest()), a = ar.util.createBuffer(), r = ar.util.createBuffer(), l = s.length();
      for (var u = 0; u < l; ++u) {
        var o = s.at(u);
        a.putByte(54 ^ o), r.putByte(92 ^ o);
      }
      if (l < t.blockLength)
        for (var o = t.blockLength - l, u = 0; u < o; ++u)
          a.putByte(54), r.putByte(92);
      e = s, a = a.bytes(), r = r.bytes();
    }
    t.start(), t.update(a);
  }, n.update = function(i) {
    t.update(i);
  }, n.getMac = function() {
    var i = t.digest().bytes();
    return t.start(), t.update(r), t.update(i), t.digest();
  }, n.digest = n.getMac, n;
};
var Wt = Re, ul = Wt.md5 = Wt.md5 || {};
Wt.md.md5 = Wt.md.algorithms.md5 = ul;
ul.create = function() {
  fl || Dh();
  var e = null, t = Wt.util.createBuffer(), a = new Array(16), r = {
    algorithm: "md5",
    blockLength: 64,
    digestLength: 16,
    // 56-bit length of message so far (does not including padding)
    messageLength: 0,
    // true message length
    fullMessageLength: null,
    // size of message length in bytes
    messageLengthSize: 8
  };
  return r.start = function() {
    r.messageLength = 0, r.fullMessageLength = r.messageLength64 = [];
    for (var n = r.messageLengthSize / 4, i = 0; i < n; ++i)
      r.fullMessageLength.push(0);
    return t = Wt.util.createBuffer(), e = {
      h0: 1732584193,
      h1: 4023233417,
      h2: 2562383102,
      h3: 271733878
    }, r;
  }, r.start(), r.update = function(n, i) {
    i === "utf8" && (n = Wt.util.encodeUtf8(n));
    var s = n.length;
    r.messageLength += s, s = [s / 4294967296 >>> 0, s >>> 0];
    for (var o = r.fullMessageLength.length - 1; o >= 0; --o)
      r.fullMessageLength[o] += s[1], s[1] = s[0] + (r.fullMessageLength[o] / 4294967296 >>> 0), r.fullMessageLength[o] = r.fullMessageLength[o] >>> 0, s[0] = s[1] / 4294967296 >>> 0;
    return t.putBytes(n), Xs(e, a, t), (t.read > 2048 || t.length() === 0) && t.compact(), r;
  }, r.digest = function() {
    var n = Wt.util.createBuffer();
    n.putBytes(t.bytes());
    var i = r.fullMessageLength[r.fullMessageLength.length - 1] + r.messageLengthSize, s = i & r.blockLength - 1;
    n.putBytes(Hi.substr(0, r.blockLength - s));
    for (var o, u = 0, l = r.fullMessageLength.length - 1; l >= 0; --l)
      o = r.fullMessageLength[l] * 8 + u, u = o / 4294967296 >>> 0, n.putInt32Le(o >>> 0);
    var c = {
      h0: e.h0,
      h1: e.h1,
      h2: e.h2,
      h3: e.h3
    };
    Xs(c, a, n);
    var f = Wt.util.createBuffer();
    return f.putInt32Le(c.h0), f.putInt32Le(c.h1), f.putInt32Le(c.h2), f.putInt32Le(c.h3), f;
  }, r;
};
var Hi = null, an = null, Aa = null, Zr = null, fl = !1;
function Dh() {
  Hi = "", Hi += Wt.util.fillString("\0", 64), an = [
    0,
    1,
    2,
    3,
    4,
    5,
    6,
    7,
    8,
    9,
    10,
    11,
    12,
    13,
    14,
    15,
    1,
    6,
    11,
    0,
    5,
    10,
    15,
    4,
    9,
    14,
    3,
    8,
    13,
    2,
    7,
    12,
    5,
    8,
    11,
    14,
    1,
    4,
    7,
    10,
    13,
    0,
    3,
    6,
    9,
    12,
    15,
    2,
    0,
    7,
    14,
    5,
    12,
    3,
    10,
    1,
    8,
    15,
    6,
    13,
    4,
    11,
    2,
    9
  ], Aa = [
    7,
    12,
    17,
    22,
    7,
    12,
    17,
    22,
    7,
    12,
    17,
    22,
    7,
    12,
    17,
    22,
    5,
    9,
    14,
    20,
    5,
    9,
    14,
    20,
    5,
    9,
    14,
    20,
    5,
    9,
    14,
    20,
    4,
    11,
    16,
    23,
    4,
    11,
    16,
    23,
    4,
    11,
    16,
    23,
    4,
    11,
    16,
    23,
    6,
    10,
    15,
    21,
    6,
    10,
    15,
    21,
    6,
    10,
    15,
    21,
    6,
    10,
    15,
    21
  ], Zr = new Array(64);
  for (var e = 0; e < 64; ++e)
    Zr[e] = Math.floor(Math.abs(Math.sin(e + 1)) * 4294967296);
  fl = !0;
}
function Xs(e, t, a) {
  for (var r, n, i, s, o, u, l, c, f = a.length(); f >= 64; ) {
    for (n = e.h0, i = e.h1, s = e.h2, o = e.h3, c = 0; c < 16; ++c)
      t[c] = a.getInt32Le(), u = o ^ i & (s ^ o), r = n + u + Zr[c] + t[c], l = Aa[c], n = o, o = s, s = i, i += r << l | r >>> 32 - l;
    for (; c < 32; ++c)
      u = s ^ o & (i ^ s), r = n + u + Zr[c] + t[an[c]], l = Aa[c], n = o, o = s, s = i, i += r << l | r >>> 32 - l;
    for (; c < 48; ++c)
      u = i ^ s ^ o, r = n + u + Zr[c] + t[an[c]], l = Aa[c], n = o, o = s, s = i, i += r << l | r >>> 32 - l;
    for (; c < 64; ++c)
      u = s ^ (i | ~o), r = n + u + Zr[c] + t[an[c]], l = Aa[c], n = o, o = s, s = i, i += r << l | r >>> 32 - l;
    e.h0 = e.h0 + n | 0, e.h1 = e.h1 + i | 0, e.h2 = e.h2 + s | 0, e.h3 = e.h3 + o | 0, f -= 64;
  }
}
var cn = Re, dl = cn.pem = cn.pem || {};
dl.encode = function(e, t) {
  t = t || {};
  var a = "-----BEGIN " + e.type + `-----\r
`, r;
  if (e.procType && (r = {
    name: "Proc-Type",
    values: [String(e.procType.version), e.procType.type]
  }, a += qa(r)), e.contentDomain && (r = { name: "Content-Domain", values: [e.contentDomain] }, a += qa(r)), e.dekInfo && (r = { name: "DEK-Info", values: [e.dekInfo.algorithm] }, e.dekInfo.parameters && r.values.push(e.dekInfo.parameters), a += qa(r)), e.headers)
    for (var n = 0; n < e.headers.length; ++n)
      a += qa(e.headers[n]);
  return e.procType && (a += `\r
`), a += cn.util.encode64(e.body, t.maxline || 64) + `\r
`, a += "-----END " + e.type + `-----\r
`, a;
};
dl.decode = function(e) {
  for (var t = [], a = /\s*-----BEGIN ([A-Z0-9- ]+)-----\r?\n?([\x21-\x7e\s]+?(?:\r?\n\r?\n))?([:A-Za-z0-9+\/=\s]+?)-----END \1-----/g, r = /([\x21-\x7e]+):\s*([\x21-\x7e\s^:]+)/, n = /\r?\n/, i; i = a.exec(e), !!i; ) {
    var s = i[1];
    s === "NEW CERTIFICATE REQUEST" && (s = "CERTIFICATE REQUEST");
    var o = {
      type: s,
      procType: null,
      contentDomain: null,
      dekInfo: null,
      headers: [],
      body: cn.util.decode64(i[3])
    };
    if (t.push(o), !!i[2]) {
      for (var u = i[2].split(n), l = 0; i && l < u.length; ) {
        for (var c = u[l].replace(/\s+$/, ""), f = l + 1; f < u.length; ++f) {
          var d = u[f];
          if (!/\s/.test(d[0]))
            break;
          c += d, l = f;
        }
        if (i = c.match(r), i) {
          for (var v = { name: i[1], values: [] }, p = i[2].split(","), h = 0; h < p.length; ++h)
            v.values.push(Nh(p[h]));
          if (o.procType)
            if (!o.contentDomain && v.name === "Content-Domain")
              o.contentDomain = p[0] || "";
            else if (!o.dekInfo && v.name === "DEK-Info") {
              if (v.values.length === 0)
                throw new Error('Invalid PEM formatted message. The "DEK-Info" header must have at least one subfield.');
              o.dekInfo = { algorithm: p[0], parameters: p[1] || null };
            } else
              o.headers.push(v);
          else {
            if (v.name !== "Proc-Type")
              throw new Error('Invalid PEM formatted message. The first encapsulated header must be "Proc-Type".');
            if (v.values.length !== 2)
              throw new Error('Invalid PEM formatted message. The "Proc-Type" header must have two subfields.');
            o.procType = { version: p[0], type: p[1] };
          }
        }
        ++l;
      }
      if (o.procType === "ENCRYPTED" && !o.dekInfo)
        throw new Error('Invalid PEM formatted message. The "DEK-Info" header must be present if "Proc-Type" is "ENCRYPTED".');
    }
  }
  if (t.length === 0)
    throw new Error("Invalid PEM formatted message.");
  return t;
};
function qa(e) {
  for (var t = e.name + ": ", a = [], r = function(u, l) {
    return " " + l;
  }, n = 0; n < e.values.length; ++n)
    a.push(e.values[n].replace(/^(\S+\r\n)/, r));
  t += a.join(",") + `\r
`;
  for (var i = 0, s = -1, n = 0; n < t.length; ++n, ++i)
    if (i > 65 && s !== -1) {
      var o = t[s];
      o === "," ? (++s, t = t.substr(0, s) + `\r
 ` + t.substr(s)) : t = t.substr(0, s) + `\r
` + o + t.substr(s + 1), i = n - s - 1, s = -1, ++n;
    } else (t[n] === " " || t[n] === "	" || t[n] === ",") && (s = n);
  return t;
}
function Nh(e) {
  return e.replace(/^\s+/, "");
}
var tt = Re;
tt.des = tt.des || {};
tt.des.startEncrypting = function(e, t, a, r) {
  var n = Rn({
    key: e,
    output: a,
    decrypt: !1,
    mode: r || (t === null ? "ECB" : "CBC")
  });
  return n.start(t), n;
};
tt.des.createEncryptionCipher = function(e, t) {
  return Rn({
    key: e,
    output: null,
    decrypt: !1,
    mode: t
  });
};
tt.des.startDecrypting = function(e, t, a, r) {
  var n = Rn({
    key: e,
    output: a,
    decrypt: !0,
    mode: r || (t === null ? "ECB" : "CBC")
  });
  return n.start(t), n;
};
tt.des.createDecryptionCipher = function(e, t) {
  return Rn({
    key: e,
    output: null,
    decrypt: !0,
    mode: t
  });
};
tt.des.Algorithm = function(e, t) {
  var a = this;
  a.name = e, a.mode = new t({
    blockSize: 8,
    cipher: {
      encrypt: function(r, n) {
        return Zs(a._keys, r, n, !1);
      },
      decrypt: function(r, n) {
        return Zs(a._keys, r, n, !0);
      }
    }
  }), a._init = !1;
};
tt.des.Algorithm.prototype.initialize = function(e) {
  if (!this._init) {
    var t = tt.util.createBuffer(e.key);
    if (this.name.indexOf("3DES") === 0 && t.length() !== 24)
      throw new Error("Invalid Triple-DES key size: " + t.length() * 8);
    this._keys = Mh(t), this._init = !0;
  }
};
er("DES-ECB", tt.cipher.modes.ecb);
er("DES-CBC", tt.cipher.modes.cbc);
er("DES-CFB", tt.cipher.modes.cfb);
er("DES-OFB", tt.cipher.modes.ofb);
er("DES-CTR", tt.cipher.modes.ctr);
er("3DES-ECB", tt.cipher.modes.ecb);
er("3DES-CBC", tt.cipher.modes.cbc);
er("3DES-CFB", tt.cipher.modes.cfb);
er("3DES-OFB", tt.cipher.modes.ofb);
er("3DES-CTR", tt.cipher.modes.ctr);
function er(e, t) {
  var a = function() {
    return new tt.des.Algorithm(e, t);
  };
  tt.cipher.registerAlgorithm(e, a);
}
var Rh = [16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756], kh = [-2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, -2147483648, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, -2147483648, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, -2147483648, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, -2147483648, -2146435040, -2146402272, 1081344], Fh = [520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584], Lh = [8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928], Ph = [256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080], Uh = [536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312], Oh = [2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154], Vh = [268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696];
function Mh(e) {
  for (var t = [0, 4, 536870912, 536870916, 65536, 65540, 536936448, 536936452, 512, 516, 536871424, 536871428, 66048, 66052, 536936960, 536936964], a = [0, 1, 1048576, 1048577, 67108864, 67108865, 68157440, 68157441, 256, 257, 1048832, 1048833, 67109120, 67109121, 68157696, 68157697], r = [0, 8, 2048, 2056, 16777216, 16777224, 16779264, 16779272, 0, 8, 2048, 2056, 16777216, 16777224, 16779264, 16779272], n = [0, 2097152, 134217728, 136314880, 8192, 2105344, 134225920, 136323072, 131072, 2228224, 134348800, 136445952, 139264, 2236416, 134356992, 136454144], i = [0, 262144, 16, 262160, 0, 262144, 16, 262160, 4096, 266240, 4112, 266256, 4096, 266240, 4112, 266256], s = [0, 1024, 32, 1056, 0, 1024, 32, 1056, 33554432, 33555456, 33554464, 33555488, 33554432, 33555456, 33554464, 33555488], o = [0, 268435456, 524288, 268959744, 2, 268435458, 524290, 268959746, 0, 268435456, 524288, 268959744, 2, 268435458, 524290, 268959746], u = [0, 65536, 2048, 67584, 536870912, 536936448, 536872960, 536938496, 131072, 196608, 133120, 198656, 537001984, 537067520, 537004032, 537069568], l = [0, 262144, 0, 262144, 2, 262146, 2, 262146, 33554432, 33816576, 33554432, 33816576, 33554434, 33816578, 33554434, 33816578], c = [0, 268435456, 8, 268435464, 0, 268435456, 8, 268435464, 1024, 268436480, 1032, 268436488, 1024, 268436480, 1032, 268436488], f = [0, 32, 0, 32, 1048576, 1048608, 1048576, 1048608, 8192, 8224, 8192, 8224, 1056768, 1056800, 1056768, 1056800], d = [0, 16777216, 512, 16777728, 2097152, 18874368, 2097664, 18874880, 67108864, 83886080, 67109376, 83886592, 69206016, 85983232, 69206528, 85983744], v = [0, 4096, 134217728, 134221824, 524288, 528384, 134742016, 134746112, 16, 4112, 134217744, 134221840, 524304, 528400, 134742032, 134746128], p = [0, 4, 256, 260, 0, 4, 256, 260, 1, 5, 257, 261, 1, 5, 257, 261], h = e.length() > 8 ? 3 : 1, x = [], y = [0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0], g = 0, m, C = 0; C < h; C++) {
    var S = e.getInt32(), _ = e.getInt32();
    m = (S >>> 4 ^ _) & 252645135, _ ^= m, S ^= m << 4, m = (_ >>> -16 ^ S) & 65535, S ^= m, _ ^= m << -16, m = (S >>> 2 ^ _) & 858993459, _ ^= m, S ^= m << 2, m = (_ >>> -16 ^ S) & 65535, S ^= m, _ ^= m << -16, m = (S >>> 1 ^ _) & 1431655765, _ ^= m, S ^= m << 1, m = (_ >>> 8 ^ S) & 16711935, S ^= m, _ ^= m << 8, m = (S >>> 1 ^ _) & 1431655765, _ ^= m, S ^= m << 1, m = S << 8 | _ >>> 20 & 240, S = _ << 24 | _ << 8 & 16711680 | _ >>> 8 & 65280 | _ >>> 24 & 240, _ = m;
    for (var T = 0; T < y.length; ++T) {
      y[T] ? (S = S << 2 | S >>> 26, _ = _ << 2 | _ >>> 26) : (S = S << 1 | S >>> 27, _ = _ << 1 | _ >>> 27), S &= -15, _ &= -15;
      var D = t[S >>> 28] | a[S >>> 24 & 15] | r[S >>> 20 & 15] | n[S >>> 16 & 15] | i[S >>> 12 & 15] | s[S >>> 8 & 15] | o[S >>> 4 & 15], P = u[_ >>> 28] | l[_ >>> 24 & 15] | c[_ >>> 20 & 15] | f[_ >>> 16 & 15] | d[_ >>> 12 & 15] | v[_ >>> 8 & 15] | p[_ >>> 4 & 15];
      m = (P >>> 16 ^ D) & 65535, x[g++] = D ^ m, x[g++] = P ^ m << 16;
    }
  }
  return x;
}
function Zs(e, t, a, r) {
  var n = e.length === 32 ? 3 : 9, i;
  n === 3 ? i = r ? [30, -2, -2] : [0, 32, 2] : i = r ? [94, 62, -2, 32, 64, 2, 30, -2, -2] : [0, 32, 2, 62, 30, -2, 64, 96, 2];
  var s, o = t[0], u = t[1];
  s = (o >>> 4 ^ u) & 252645135, u ^= s, o ^= s << 4, s = (o >>> 16 ^ u) & 65535, u ^= s, o ^= s << 16, s = (u >>> 2 ^ o) & 858993459, o ^= s, u ^= s << 2, s = (u >>> 8 ^ o) & 16711935, o ^= s, u ^= s << 8, s = (o >>> 1 ^ u) & 1431655765, u ^= s, o ^= s << 1, o = o << 1 | o >>> 31, u = u << 1 | u >>> 31;
  for (var l = 0; l < n; l += 3) {
    for (var c = i[l + 1], f = i[l + 2], d = i[l]; d != c; d += f) {
      var v = u ^ e[d], p = (u >>> 4 | u << 28) ^ e[d + 1];
      s = o, o = u, u = s ^ (kh[v >>> 24 & 63] | Lh[v >>> 16 & 63] | Uh[v >>> 8 & 63] | Vh[v & 63] | Rh[p >>> 24 & 63] | Fh[p >>> 16 & 63] | Ph[p >>> 8 & 63] | Oh[p & 63]);
    }
    s = o, o = u, u = s;
  }
  o = o >>> 1 | o << 31, u = u >>> 1 | u << 31, s = (o >>> 1 ^ u) & 1431655765, u ^= s, o ^= s << 1, s = (u >>> 8 ^ o) & 16711935, o ^= s, u ^= s << 8, s = (u >>> 2 ^ o) & 858993459, o ^= s, u ^= s << 2, s = (o >>> 16 ^ u) & 65535, u ^= s, o ^= s << 16, s = (o >>> 4 ^ u) & 252645135, u ^= s, o ^= s << 4, a[0] = o, a[1] = u;
}
function Rn(e) {
  e = e || {};
  var t = (e.mode || "CBC").toUpperCase(), a = "DES-" + t, r;
  e.decrypt ? r = tt.cipher.createDecipher(a, e.key) : r = tt.cipher.createCipher(a, e.key);
  var n = r.start;
  return r.start = function(i, s) {
    var o = null;
    s instanceof tt.util.ByteBuffer && (o = s, s = {}), s = s || {}, s.output = o, s.iv = i, n.call(r, s);
  }, r;
}
var Ct = Re, Kh = Ct.pkcs5 = Ct.pkcs5 || {}, rr;
Ct.util.isNodejs && !Ct.options.usePureJavaScript && (rr = Bn);
Ct.pbkdf2 = Kh.pbkdf2 = function(e, t, a, r, n, i) {
  if (typeof n == "function" && (i = n, n = null), Ct.util.isNodejs && !Ct.options.usePureJavaScript && rr.pbkdf2 && (n === null || typeof n != "object") && (rr.pbkdf2Sync.length > 4 || !n || n === "sha1"))
    return typeof n != "string" && (n = "sha1"), e = Buffer.from(e, "binary"), t = Buffer.from(t, "binary"), i ? rr.pbkdf2Sync.length === 4 ? rr.pbkdf2(e, t, a, r, function(m, C) {
      if (m)
        return i(m);
      i(null, C.toString("binary"));
    }) : rr.pbkdf2(e, t, a, r, n, function(m, C) {
      if (m)
        return i(m);
      i(null, C.toString("binary"));
    }) : rr.pbkdf2Sync.length === 4 ? rr.pbkdf2Sync(e, t, a, r).toString("binary") : rr.pbkdf2Sync(e, t, a, r, n).toString("binary");
  if ((typeof n > "u" || n === null) && (n = "sha1"), typeof n == "string") {
    if (!(n in Ct.md.algorithms))
      throw new Error("Unknown hash algorithm: " + n);
    n = Ct.md[n].create();
  }
  var s = n.digestLength;
  if (r > 4294967295 * s) {
    var o = new Error("Derived key is too long.");
    if (i)
      return i(o);
    throw o;
  }
  var u = Math.ceil(r / s), l = r - (u - 1) * s, c = Ct.hmac.create();
  c.start(n, e);
  var f = "", d, v, p;
  if (!i) {
    for (var h = 1; h <= u; ++h) {
      c.start(null, null), c.update(t), c.update(Ct.util.int32ToBytes(h)), d = p = c.digest().getBytes();
      for (var x = 2; x <= a; ++x)
        c.start(null, null), c.update(p), v = c.digest().getBytes(), d = Ct.util.xorBytes(d, v, s), p = v;
      f += h < u ? d : d.substr(0, l);
    }
    return f;
  }
  var h = 1, x;
  function y() {
    if (h > u)
      return i(null, f);
    c.start(null, null), c.update(t), c.update(Ct.util.int32ToBytes(h)), d = p = c.digest().getBytes(), x = 2, g();
  }
  function g() {
    if (x <= a)
      return c.start(null, null), c.update(p), v = c.digest().getBytes(), d = Ct.util.xorBytes(d, v, s), p = v, ++x, Ct.util.setImmediate(g);
    f += h < u ? d : d.substr(0, l), ++h, y();
  }
  y();
};
var Yt = Re, pl = Yt.sha256 = Yt.sha256 || {};
Yt.md.sha256 = Yt.md.algorithms.sha256 = pl;
pl.create = function() {
  hl || Hh();
  var e = null, t = Yt.util.createBuffer(), a = new Array(64), r = {
    algorithm: "sha256",
    blockLength: 64,
    digestLength: 32,
    // 56-bit length of message so far (does not including padding)
    messageLength: 0,
    // true message length
    fullMessageLength: null,
    // size of message length in bytes
    messageLengthSize: 8
  };
  return r.start = function() {
    r.messageLength = 0, r.fullMessageLength = r.messageLength64 = [];
    for (var n = r.messageLengthSize / 4, i = 0; i < n; ++i)
      r.fullMessageLength.push(0);
    return t = Yt.util.createBuffer(), e = {
      h0: 1779033703,
      h1: 3144134277,
      h2: 1013904242,
      h3: 2773480762,
      h4: 1359893119,
      h5: 2600822924,
      h6: 528734635,
      h7: 1541459225
    }, r;
  }, r.start(), r.update = function(n, i) {
    i === "utf8" && (n = Yt.util.encodeUtf8(n));
    var s = n.length;
    r.messageLength += s, s = [s / 4294967296 >>> 0, s >>> 0];
    for (var o = r.fullMessageLength.length - 1; o >= 0; --o)
      r.fullMessageLength[o] += s[1], s[1] = s[0] + (r.fullMessageLength[o] / 4294967296 >>> 0), r.fullMessageLength[o] = r.fullMessageLength[o] >>> 0, s[0] = s[1] / 4294967296 >>> 0;
    return t.putBytes(n), Js(e, a, t), (t.read > 2048 || t.length() === 0) && t.compact(), r;
  }, r.digest = function() {
    var n = Yt.util.createBuffer();
    n.putBytes(t.bytes());
    var i = r.fullMessageLength[r.fullMessageLength.length - 1] + r.messageLengthSize, s = i & r.blockLength - 1;
    n.putBytes(zi.substr(0, r.blockLength - s));
    for (var o, u, l = r.fullMessageLength[0] * 8, c = 0; c < r.fullMessageLength.length - 1; ++c)
      o = r.fullMessageLength[c + 1] * 8, u = o / 4294967296 >>> 0, l += u, n.putInt32(l >>> 0), l = o >>> 0;
    n.putInt32(l);
    var f = {
      h0: e.h0,
      h1: e.h1,
      h2: e.h2,
      h3: e.h3,
      h4: e.h4,
      h5: e.h5,
      h6: e.h6,
      h7: e.h7
    };
    Js(f, a, n);
    var d = Yt.util.createBuffer();
    return d.putInt32(f.h0), d.putInt32(f.h1), d.putInt32(f.h2), d.putInt32(f.h3), d.putInt32(f.h4), d.putInt32(f.h5), d.putInt32(f.h6), d.putInt32(f.h7), d;
  }, r;
};
var zi = null, hl = !1, vl = null;
function Hh() {
  zi = "", zi += Yt.util.fillString("\0", 64), vl = [
    1116352408,
    1899447441,
    3049323471,
    3921009573,
    961987163,
    1508970993,
    2453635748,
    2870763221,
    3624381080,
    310598401,
    607225278,
    1426881987,
    1925078388,
    2162078206,
    2614888103,
    3248222580,
    3835390401,
    4022224774,
    264347078,
    604807628,
    770255983,
    1249150122,
    1555081692,
    1996064986,
    2554220882,
    2821834349,
    2952996808,
    3210313671,
    3336571891,
    3584528711,
    113926993,
    338241895,
    666307205,
    773529912,
    1294757372,
    1396182291,
    1695183700,
    1986661051,
    2177026350,
    2456956037,
    2730485921,
    2820302411,
    3259730800,
    3345764771,
    3516065817,
    3600352804,
    4094571909,
    275423344,
    430227734,
    506948616,
    659060556,
    883997877,
    958139571,
    1322822218,
    1537002063,
    1747873779,
    1955562222,
    2024104815,
    2227730452,
    2361852424,
    2428436474,
    2756734187,
    3204031479,
    3329325298
  ], hl = !0;
}
function Js(e, t, a) {
  for (var r, n, i, s, o, u, l, c, f, d, v, p, h, x, y, g = a.length(); g >= 64; ) {
    for (l = 0; l < 16; ++l)
      t[l] = a.getInt32();
    for (; l < 64; ++l)
      r = t[l - 2], r = (r >>> 17 | r << 15) ^ (r >>> 19 | r << 13) ^ r >>> 10, n = t[l - 15], n = (n >>> 7 | n << 25) ^ (n >>> 18 | n << 14) ^ n >>> 3, t[l] = r + t[l - 7] + n + t[l - 16] | 0;
    for (c = e.h0, f = e.h1, d = e.h2, v = e.h3, p = e.h4, h = e.h5, x = e.h6, y = e.h7, l = 0; l < 64; ++l)
      s = (p >>> 6 | p << 26) ^ (p >>> 11 | p << 21) ^ (p >>> 25 | p << 7), o = x ^ p & (h ^ x), i = (c >>> 2 | c << 30) ^ (c >>> 13 | c << 19) ^ (c >>> 22 | c << 10), u = c & f | d & (c ^ f), r = y + s + o + vl[l] + t[l], n = i + u, y = x, x = h, h = p, p = v + r >>> 0, v = d, d = f, f = c, c = r + n >>> 0;
    e.h0 = e.h0 + c | 0, e.h1 = e.h1 + f | 0, e.h2 = e.h2 + d | 0, e.h3 = e.h3 + v | 0, e.h4 = e.h4 + p | 0, e.h5 = e.h5 + h | 0, e.h6 = e.h6 + x | 0, e.h7 = e.h7 + y | 0, g -= 64;
  }
}
var jt = Re, nn = null;
jt.util.isNodejs && !jt.options.usePureJavaScript && !process.versions["node-webkit"] && (nn = Bn);
var zh = jt.prng = jt.prng || {};
zh.create = function(e) {
  for (var t = {
    plugin: e,
    key: null,
    seed: null,
    time: null,
    // number of reseeds so far
    reseeds: 0,
    // amount of data generated so far
    generated: 0,
    // no initial key bytes
    keyBytes: ""
  }, a = e.md, r = new Array(32), n = 0; n < 32; ++n)
    r[n] = a.create();
  t.pools = r, t.pool = 0, t.generate = function(l, c) {
    if (!c)
      return t.generateSync(l);
    var f = t.plugin.cipher, d = t.plugin.increment, v = t.plugin.formatKey, p = t.plugin.formatSeed, h = jt.util.createBuffer();
    t.key = null, x();
    function x(y) {
      if (y)
        return c(y);
      if (h.length() >= l)
        return c(null, h.getBytes(l));
      if (t.generated > 1048575 && (t.key = null), t.key === null)
        return jt.util.nextTick(function() {
          i(x);
        });
      var g = f(t.key, t.seed);
      t.generated += g.length, h.putBytes(g), t.key = v(f(t.key, d(t.seed))), t.seed = p(f(t.key, t.seed)), jt.util.setImmediate(x);
    }
  }, t.generateSync = function(l) {
    var c = t.plugin.cipher, f = t.plugin.increment, d = t.plugin.formatKey, v = t.plugin.formatSeed;
    t.key = null;
    for (var p = jt.util.createBuffer(); p.length() < l; ) {
      t.generated > 1048575 && (t.key = null), t.key === null && s();
      var h = c(t.key, t.seed);
      t.generated += h.length, p.putBytes(h), t.key = d(c(t.key, f(t.seed))), t.seed = v(c(t.key, t.seed));
    }
    return p.getBytes(l);
  };
  function i(l) {
    if (t.pools[0].messageLength >= 32)
      return o(), l();
    var c = 32 - t.pools[0].messageLength << 5;
    t.seedFile(c, function(f, d) {
      if (f)
        return l(f);
      t.collect(d), o(), l();
    });
  }
  function s() {
    if (t.pools[0].messageLength >= 32)
      return o();
    var l = 32 - t.pools[0].messageLength << 5;
    t.collect(t.seedFileSync(l)), o();
  }
  function o() {
    t.reseeds = t.reseeds === 4294967295 ? 0 : t.reseeds + 1;
    var l = t.plugin.md.create();
    l.update(t.keyBytes);
    for (var c = 1, f = 0; f < 32; ++f)
      t.reseeds % c === 0 && (l.update(t.pools[f].digest().getBytes()), t.pools[f].start()), c = c << 1;
    t.keyBytes = l.digest().getBytes(), l.start(), l.update(t.keyBytes);
    var d = l.digest().getBytes();
    t.key = t.plugin.formatKey(t.keyBytes), t.seed = t.plugin.formatSeed(d), t.generated = 0;
  }
  function u(l) {
    var c = null, f = jt.util.globalScope, d = f.crypto || f.msCrypto;
    d && d.getRandomValues && (c = function(S) {
      return d.getRandomValues(S);
    });
    var v = jt.util.createBuffer();
    if (c)
      for (; v.length() < l; ) {
        var p = Math.max(1, Math.min(l - v.length(), 65536) / 4), h = new Uint32Array(Math.floor(p));
        try {
          c(h);
          for (var x = 0; x < h.length; ++x)
            v.putInt32(h[x]);
        } catch (S) {
          if (!(typeof QuotaExceededError < "u" && S instanceof QuotaExceededError))
            throw S;
        }
      }
    if (v.length() < l)
      for (var y, g, m, C = Math.floor(Math.random() * 65536); v.length() < l; ) {
        g = 16807 * (C & 65535), y = 16807 * (C >> 16), g += (y & 32767) << 16, g += y >> 15, g = (g & 2147483647) + (g >> 31), C = g & 4294967295;
        for (var x = 0; x < 3; ++x)
          m = C >>> (x << 3), m ^= Math.floor(Math.random() * 256), v.putByte(m & 255);
      }
    return v.getBytes(l);
  }
  return nn ? (t.seedFile = function(l, c) {
    nn.randomBytes(l, function(f, d) {
      if (f)
        return c(f);
      c(null, d.toString());
    });
  }, t.seedFileSync = function(l) {
    return nn.randomBytes(l).toString();
  }) : (t.seedFile = function(l, c) {
    try {
      c(null, u(l));
    } catch (f) {
      c(f);
    }
  }, t.seedFileSync = u), t.collect = function(l) {
    for (var c = l.length, f = 0; f < c; ++f)
      t.pools[t.pool].update(l.substr(f, 1)), t.pool = t.pool === 31 ? 0 : t.pool + 1;
  }, t.collectInt = function(l, c) {
    for (var f = "", d = 0; d < c; d += 8)
      f += String.fromCharCode(l >> d & 255);
    t.collect(f);
  }, t.registerWorker = function(l) {
    if (l === self)
      t.seedFile = function(f, d) {
        function v(p) {
          var h = p.data;
          h.forge && h.forge.prng && (self.removeEventListener("message", v), d(h.forge.prng.err, h.forge.prng.bytes));
        }
        self.addEventListener("message", v), self.postMessage({ forge: { prng: { needed: f } } });
      };
    else {
      var c = function(f) {
        var d = f.data;
        d.forge && d.forge.prng && t.seedFile(d.forge.prng.needed, function(v, p) {
          l.postMessage({ forge: { prng: { err: v, bytes: p } } });
        });
      };
      l.addEventListener("message", c);
    }
  }, t;
};
var ot = Re;
(function() {
  if (ot.random && ot.random.getBytes) {
    ot.random;
    return;
  }
  (function(e) {
    var t = {}, a = new Array(4), r = ot.util.createBuffer();
    t.formatKey = function(f) {
      var d = ot.util.createBuffer(f);
      return f = new Array(4), f[0] = d.getInt32(), f[1] = d.getInt32(), f[2] = d.getInt32(), f[3] = d.getInt32(), ot.aes._expandKey(f, !1);
    }, t.formatSeed = function(f) {
      var d = ot.util.createBuffer(f);
      return f = new Array(4), f[0] = d.getInt32(), f[1] = d.getInt32(), f[2] = d.getInt32(), f[3] = d.getInt32(), f;
    }, t.cipher = function(f, d) {
      return ot.aes._updateBlock(f, d, a, !1), r.putInt32(a[0]), r.putInt32(a[1]), r.putInt32(a[2]), r.putInt32(a[3]), r.getBytes();
    }, t.increment = function(f) {
      return ++f[3], f;
    }, t.md = ot.md.sha256;
    function n() {
      var f = ot.prng.create(t);
      return f.getBytes = function(d, v) {
        return f.generate(d, v);
      }, f.getBytesSync = function(d) {
        return f.generate(d);
      }, f;
    }
    var i = n(), s = null, o = ot.util.globalScope, u = o.crypto || o.msCrypto;
    if (u && u.getRandomValues && (s = function(f) {
      return u.getRandomValues(f);
    }), ot.options.usePureJavaScript || !ot.util.isNodejs && !s) {
      if (i.collectInt(+/* @__PURE__ */ new Date(), 32), typeof navigator < "u") {
        var l = "";
        for (var c in navigator)
          try {
            typeof navigator[c] == "string" && (l += navigator[c]);
          } catch {
          }
        i.collect(l), l = null;
      }
      e && (e().mousemove(function(f) {
        i.collectInt(f.clientX, 16), i.collectInt(f.clientY, 16);
      }), e().keypress(function(f) {
        i.collectInt(f.charCode, 8);
      }));
    }
    if (!ot.random)
      ot.random = i;
    else
      for (var c in i)
        ot.random[c] = i[c];
    ot.random.createInstance = n, ot.random;
  })(typeof jQuery < "u" ? jQuery : null);
})();
var Bt = Re, Ai = [
  217,
  120,
  249,
  196,
  25,
  221,
  181,
  237,
  40,
  233,
  253,
  121,
  74,
  160,
  216,
  157,
  198,
  126,
  55,
  131,
  43,
  118,
  83,
  142,
  98,
  76,
  100,
  136,
  68,
  139,
  251,
  162,
  23,
  154,
  89,
  245,
  135,
  179,
  79,
  19,
  97,
  69,
  109,
  141,
  9,
  129,
  125,
  50,
  189,
  143,
  64,
  235,
  134,
  183,
  123,
  11,
  240,
  149,
  33,
  34,
  92,
  107,
  78,
  130,
  84,
  214,
  101,
  147,
  206,
  96,
  178,
  28,
  115,
  86,
  192,
  20,
  167,
  140,
  241,
  220,
  18,
  117,
  202,
  31,
  59,
  190,
  228,
  209,
  66,
  61,
  212,
  48,
  163,
  60,
  182,
  38,
  111,
  191,
  14,
  218,
  70,
  105,
  7,
  87,
  39,
  242,
  29,
  155,
  188,
  148,
  67,
  3,
  248,
  17,
  199,
  246,
  144,
  239,
  62,
  231,
  6,
  195,
  213,
  47,
  200,
  102,
  30,
  215,
  8,
  232,
  234,
  222,
  128,
  82,
  238,
  247,
  132,
  170,
  114,
  172,
  53,
  77,
  106,
  42,
  150,
  26,
  210,
  113,
  90,
  21,
  73,
  116,
  75,
  159,
  208,
  94,
  4,
  24,
  164,
  236,
  194,
  224,
  65,
  110,
  15,
  81,
  203,
  204,
  36,
  145,
  175,
  80,
  161,
  244,
  112,
  57,
  153,
  124,
  58,
  133,
  35,
  184,
  180,
  122,
  252,
  2,
  54,
  91,
  37,
  85,
  151,
  49,
  45,
  93,
  250,
  152,
  227,
  138,
  146,
  174,
  5,
  223,
  41,
  16,
  103,
  108,
  186,
  201,
  211,
  0,
  230,
  207,
  225,
  158,
  168,
  44,
  99,
  22,
  1,
  63,
  88,
  226,
  137,
  169,
  13,
  56,
  52,
  27,
  171,
  51,
  255,
  176,
  187,
  72,
  12,
  95,
  185,
  177,
  205,
  46,
  197,
  243,
  219,
  71,
  229,
  165,
  156,
  119,
  10,
  166,
  32,
  104,
  254,
  127,
  193,
  173
], eo = [1, 2, 3, 5], $h = function(e, t) {
  return e << t & 65535 | (e & 65535) >> 16 - t;
}, qh = function(e, t) {
  return (e & 65535) >> t | e << 16 - t & 65535;
};
Bt.rc2 = Bt.rc2 || {};
Bt.rc2.expandKey = function(e, t) {
  typeof e == "string" && (e = Bt.util.createBuffer(e)), t = t || 128;
  var a = e, r = e.length(), n = t, i = Math.ceil(n / 8), s = 255 >> (n & 7), o;
  for (o = r; o < 128; o++)
    a.putByte(Ai[a.at(o - 1) + a.at(o - r) & 255]);
  for (a.setAt(128 - i, Ai[a.at(128 - i) & s]), o = 127 - i; o >= 0; o--)
    a.setAt(o, Ai[a.at(o + 1) ^ a.at(o + i)]);
  return a;
};
var xl = function(e, t, a) {
  var r = !1, n = null, i = null, s = null, o, u, l, c, f = [];
  for (e = Bt.rc2.expandKey(e, t), l = 0; l < 64; l++)
    f.push(e.getInt16Le());
  a ? (o = function(p) {
    for (l = 0; l < 4; l++)
      p[l] += f[c] + (p[(l + 3) % 4] & p[(l + 2) % 4]) + (~p[(l + 3) % 4] & p[(l + 1) % 4]), p[l] = $h(p[l], eo[l]), c++;
  }, u = function(p) {
    for (l = 0; l < 4; l++)
      p[l] += f[p[(l + 3) % 4] & 63];
  }) : (o = function(p) {
    for (l = 3; l >= 0; l--)
      p[l] = qh(p[l], eo[l]), p[l] -= f[c] + (p[(l + 3) % 4] & p[(l + 2) % 4]) + (~p[(l + 3) % 4] & p[(l + 1) % 4]), c--;
  }, u = function(p) {
    for (l = 3; l >= 0; l--)
      p[l] -= f[p[(l + 3) % 4] & 63];
  });
  var d = function(p) {
    var h = [];
    for (l = 0; l < 4; l++) {
      var x = n.getInt16Le();
      s !== null && (a ? x ^= s.getInt16Le() : s.putInt16Le(x)), h.push(x & 65535);
    }
    c = a ? 0 : 63;
    for (var y = 0; y < p.length; y++)
      for (var g = 0; g < p[y][0]; g++)
        p[y][1](h);
    for (l = 0; l < 4; l++)
      s !== null && (a ? s.putInt16Le(h[l]) : h[l] ^= s.getInt16Le()), i.putInt16Le(h[l]);
  }, v = null;
  return v = {
    /**
     * Starts or restarts the encryption or decryption process, whichever
     * was previously configured.
     *
     * To use the cipher in CBC mode, iv may be given either as a string
     * of bytes, or as a byte buffer.  For ECB mode, give null as iv.
     *
     * @param iv the initialization vector to use, null for ECB mode.
     * @param output the output the buffer to write to, null to create one.
     */
    start: function(p, h) {
      p && typeof p == "string" && (p = Bt.util.createBuffer(p)), r = !1, n = Bt.util.createBuffer(), i = h || new Bt.util.createBuffer(), s = p, v.output = i;
    },
    /**
     * Updates the next block.
     *
     * @param input the buffer to read from.
     */
    update: function(p) {
      for (r || n.putBuffer(p); n.length() >= 8; )
        d([
          [5, o],
          [1, u],
          [6, o],
          [1, u],
          [5, o]
        ]);
    },
    /**
     * Finishes encrypting or decrypting.
     *
     * @param pad a padding function to use, null for PKCS#7 padding,
     *           signature(blockSize, buffer, decrypt).
     *
     * @return true if successful, false on error.
     */
    finish: function(p) {
      var h = !0;
      if (a)
        if (p)
          h = p(8, n, !a);
        else {
          var x = n.length() === 8 ? 8 : 8 - n.length();
          n.fillWithByte(x, x);
        }
      if (h && (r = !0, v.update()), !a && (h = n.length() === 0, h))
        if (p)
          h = p(8, i, !a);
        else {
          var y = i.length(), g = i.at(y - 1);
          g > y ? h = !1 : i.truncate(g);
        }
      return h;
    }
  }, v;
};
Bt.rc2.startEncrypting = function(e, t, a) {
  var r = Bt.rc2.createEncryptionCipher(e, 128);
  return r.start(t, a), r;
};
Bt.rc2.createEncryptionCipher = function(e, t) {
  return xl(e, t, !0);
};
Bt.rc2.startDecrypting = function(e, t, a) {
  var r = Bt.rc2.createDecryptionCipher(e, 128);
  return r.start(t, a), r;
};
Bt.rc2.createDecryptionCipher = function(e, t) {
  return xl(e, t, !1);
};
var $i = Re;
$i.jsbn = $i.jsbn || {};
var ir;
function G(e, t, a) {
  this.data = [], e != null && (typeof e == "number" ? this.fromNumber(e, t, a) : t == null && typeof e != "string" ? this.fromString(e, 256) : this.fromString(e, t));
}
$i.jsbn.BigInteger = G;
function Ke() {
  return new G(null);
}
function jh(e, t, a, r, n, i) {
  for (; --i >= 0; ) {
    var s = t * this.data[e++] + a.data[r] + n;
    n = Math.floor(s / 67108864), a.data[r++] = s & 67108863;
  }
  return n;
}
function Gh(e, t, a, r, n, i) {
  for (var s = t & 32767, o = t >> 15; --i >= 0; ) {
    var u = this.data[e] & 32767, l = this.data[e++] >> 15, c = o * u + l * s;
    u = s * u + ((c & 32767) << 15) + a.data[r] + (n & 1073741823), n = (u >>> 30) + (c >>> 15) + o * l + (n >>> 30), a.data[r++] = u & 1073741823;
  }
  return n;
}
function to(e, t, a, r, n, i) {
  for (var s = t & 16383, o = t >> 14; --i >= 0; ) {
    var u = this.data[e] & 16383, l = this.data[e++] >> 14, c = o * u + l * s;
    u = s * u + ((c & 16383) << 14) + a.data[r] + n, n = (u >> 28) + (c >> 14) + o * l, a.data[r++] = u & 268435455;
  }
  return n;
}
typeof navigator > "u" ? (G.prototype.am = to, ir = 28) : navigator.appName == "Microsoft Internet Explorer" ? (G.prototype.am = Gh, ir = 30) : navigator.appName != "Netscape" ? (G.prototype.am = jh, ir = 26) : (G.prototype.am = to, ir = 28);
G.prototype.DB = ir;
G.prototype.DM = (1 << ir) - 1;
G.prototype.DV = 1 << ir;
var C0 = 52;
G.prototype.FV = Math.pow(2, C0);
G.prototype.F1 = C0 - ir;
G.prototype.F2 = 2 * ir - C0;
var Wh = "0123456789abcdefghijklmnopqrstuvwxyz", kn = new Array(), pa, Lt;
pa = 48;
for (Lt = 0; Lt <= 9; ++Lt) kn[pa++] = Lt;
pa = 97;
for (Lt = 10; Lt < 36; ++Lt) kn[pa++] = Lt;
pa = 65;
for (Lt = 10; Lt < 36; ++Lt) kn[pa++] = Lt;
function ro(e) {
  return Wh.charAt(e);
}
function gl(e, t) {
  var a = kn[e.charCodeAt(t)];
  return a ?? -1;
}
function Yh(e) {
  for (var t = this.t - 1; t >= 0; --t) e.data[t] = this.data[t];
  e.t = this.t, e.s = this.s;
}
function Qh(e) {
  this.t = 1, this.s = e < 0 ? -1 : 0, e > 0 ? this.data[0] = e : e < -1 ? this.data[0] = e + this.DV : this.t = 0;
}
function xr(e) {
  var t = Ke();
  return t.fromInt(e), t;
}
function Xh(e, t) {
  var a;
  if (t == 16) a = 4;
  else if (t == 8) a = 3;
  else if (t == 256) a = 8;
  else if (t == 2) a = 1;
  else if (t == 32) a = 5;
  else if (t == 4) a = 2;
  else {
    this.fromRadix(e, t);
    return;
  }
  this.t = 0, this.s = 0;
  for (var r = e.length, n = !1, i = 0; --r >= 0; ) {
    var s = a == 8 ? e[r] & 255 : gl(e, r);
    if (s < 0) {
      e.charAt(r) == "-" && (n = !0);
      continue;
    }
    n = !1, i == 0 ? this.data[this.t++] = s : i + a > this.DB ? (this.data[this.t - 1] |= (s & (1 << this.DB - i) - 1) << i, this.data[this.t++] = s >> this.DB - i) : this.data[this.t - 1] |= s << i, i += a, i >= this.DB && (i -= this.DB);
  }
  a == 8 && e[0] & 128 && (this.s = -1, i > 0 && (this.data[this.t - 1] |= (1 << this.DB - i) - 1 << i)), this.clamp(), n && G.ZERO.subTo(this, this);
}
function Zh() {
  for (var e = this.s & this.DM; this.t > 0 && this.data[this.t - 1] == e; ) --this.t;
}
function Jh(e) {
  if (this.s < 0) return "-" + this.negate().toString(e);
  var t;
  if (e == 16) t = 4;
  else if (e == 8) t = 3;
  else if (e == 2) t = 1;
  else if (e == 32) t = 5;
  else if (e == 4) t = 2;
  else return this.toRadix(e);
  var a = (1 << t) - 1, r, n = !1, i = "", s = this.t, o = this.DB - s * this.DB % t;
  if (s-- > 0)
    for (o < this.DB && (r = this.data[s] >> o) > 0 && (n = !0, i = ro(r)); s >= 0; )
      o < t ? (r = (this.data[s] & (1 << o) - 1) << t - o, r |= this.data[--s] >> (o += this.DB - t)) : (r = this.data[s] >> (o -= t) & a, o <= 0 && (o += this.DB, --s)), r > 0 && (n = !0), n && (i += ro(r));
  return n ? i : "0";
}
function e1() {
  var e = Ke();
  return G.ZERO.subTo(this, e), e;
}
function t1() {
  return this.s < 0 ? this.negate() : this;
}
function r1(e) {
  var t = this.s - e.s;
  if (t != 0) return t;
  var a = this.t;
  if (t = a - e.t, t != 0) return this.s < 0 ? -t : t;
  for (; --a >= 0; ) if ((t = this.data[a] - e.data[a]) != 0) return t;
  return 0;
}
function Fn(e) {
  var t = 1, a;
  return (a = e >>> 16) != 0 && (e = a, t += 16), (a = e >> 8) != 0 && (e = a, t += 8), (a = e >> 4) != 0 && (e = a, t += 4), (a = e >> 2) != 0 && (e = a, t += 2), (a = e >> 1) != 0 && (e = a, t += 1), t;
}
function a1() {
  return this.t <= 0 ? 0 : this.DB * (this.t - 1) + Fn(this.data[this.t - 1] ^ this.s & this.DM);
}
function n1(e, t) {
  var a;
  for (a = this.t - 1; a >= 0; --a) t.data[a + e] = this.data[a];
  for (a = e - 1; a >= 0; --a) t.data[a] = 0;
  t.t = this.t + e, t.s = this.s;
}
function i1(e, t) {
  for (var a = e; a < this.t; ++a) t.data[a - e] = this.data[a];
  t.t = Math.max(this.t - e, 0), t.s = this.s;
}
function s1(e, t) {
  var a = e % this.DB, r = this.DB - a, n = (1 << r) - 1, i = Math.floor(e / this.DB), s = this.s << a & this.DM, o;
  for (o = this.t - 1; o >= 0; --o)
    t.data[o + i + 1] = this.data[o] >> r | s, s = (this.data[o] & n) << a;
  for (o = i - 1; o >= 0; --o) t.data[o] = 0;
  t.data[i] = s, t.t = this.t + i + 1, t.s = this.s, t.clamp();
}
function o1(e, t) {
  t.s = this.s;
  var a = Math.floor(e / this.DB);
  if (a >= this.t) {
    t.t = 0;
    return;
  }
  var r = e % this.DB, n = this.DB - r, i = (1 << r) - 1;
  t.data[0] = this.data[a] >> r;
  for (var s = a + 1; s < this.t; ++s)
    t.data[s - a - 1] |= (this.data[s] & i) << n, t.data[s - a] = this.data[s] >> r;
  r > 0 && (t.data[this.t - a - 1] |= (this.s & i) << n), t.t = this.t - a, t.clamp();
}
function l1(e, t) {
  for (var a = 0, r = 0, n = Math.min(e.t, this.t); a < n; )
    r += this.data[a] - e.data[a], t.data[a++] = r & this.DM, r >>= this.DB;
  if (e.t < this.t) {
    for (r -= e.s; a < this.t; )
      r += this.data[a], t.data[a++] = r & this.DM, r >>= this.DB;
    r += this.s;
  } else {
    for (r += this.s; a < e.t; )
      r -= e.data[a], t.data[a++] = r & this.DM, r >>= this.DB;
    r -= e.s;
  }
  t.s = r < 0 ? -1 : 0, r < -1 ? t.data[a++] = this.DV + r : r > 0 && (t.data[a++] = r), t.t = a, t.clamp();
}
function c1(e, t) {
  var a = this.abs(), r = e.abs(), n = a.t;
  for (t.t = n + r.t; --n >= 0; ) t.data[n] = 0;
  for (n = 0; n < r.t; ++n) t.data[n + a.t] = a.am(0, r.data[n], t, n, 0, a.t);
  t.s = 0, t.clamp(), this.s != e.s && G.ZERO.subTo(t, t);
}
function u1(e) {
  for (var t = this.abs(), a = e.t = 2 * t.t; --a >= 0; ) e.data[a] = 0;
  for (a = 0; a < t.t - 1; ++a) {
    var r = t.am(a, t.data[a], e, 2 * a, 0, 1);
    (e.data[a + t.t] += t.am(a + 1, 2 * t.data[a], e, 2 * a + 1, r, t.t - a - 1)) >= t.DV && (e.data[a + t.t] -= t.DV, e.data[a + t.t + 1] = 1);
  }
  e.t > 0 && (e.data[e.t - 1] += t.am(a, t.data[a], e, 2 * a, 0, 1)), e.s = 0, e.clamp();
}
function f1(e, t, a) {
  var r = e.abs();
  if (!(r.t <= 0)) {
    var n = this.abs();
    if (n.t < r.t) {
      t != null && t.fromInt(0), a != null && this.copyTo(a);
      return;
    }
    a == null && (a = Ke());
    var i = Ke(), s = this.s, o = e.s, u = this.DB - Fn(r.data[r.t - 1]);
    u > 0 ? (r.lShiftTo(u, i), n.lShiftTo(u, a)) : (r.copyTo(i), n.copyTo(a));
    var l = i.t, c = i.data[l - 1];
    if (c != 0) {
      var f = c * (1 << this.F1) + (l > 1 ? i.data[l - 2] >> this.F2 : 0), d = this.FV / f, v = (1 << this.F1) / f, p = 1 << this.F2, h = a.t, x = h - l, y = t ?? Ke();
      for (i.dlShiftTo(x, y), a.compareTo(y) >= 0 && (a.data[a.t++] = 1, a.subTo(y, a)), G.ONE.dlShiftTo(l, y), y.subTo(i, i); i.t < l; ) i.data[i.t++] = 0;
      for (; --x >= 0; ) {
        var g = a.data[--h] == c ? this.DM : Math.floor(a.data[h] * d + (a.data[h - 1] + p) * v);
        if ((a.data[h] += i.am(0, g, a, x, 0, l)) < g)
          for (i.dlShiftTo(x, y), a.subTo(y, a); a.data[h] < --g; ) a.subTo(y, a);
      }
      t != null && (a.drShiftTo(l, t), s != o && G.ZERO.subTo(t, t)), a.t = l, a.clamp(), u > 0 && a.rShiftTo(u, a), s < 0 && G.ZERO.subTo(a, a);
    }
  }
}
function d1(e) {
  var t = Ke();
  return this.abs().divRemTo(e, null, t), this.s < 0 && t.compareTo(G.ZERO) > 0 && e.subTo(t, t), t;
}
function Hr(e) {
  this.m = e;
}
function p1(e) {
  return e.s < 0 || e.compareTo(this.m) >= 0 ? e.mod(this.m) : e;
}
function h1(e) {
  return e;
}
function v1(e) {
  e.divRemTo(this.m, null, e);
}
function x1(e, t, a) {
  e.multiplyTo(t, a), this.reduce(a);
}
function g1(e, t) {
  e.squareTo(t), this.reduce(t);
}
Hr.prototype.convert = p1;
Hr.prototype.revert = h1;
Hr.prototype.reduce = v1;
Hr.prototype.mulTo = x1;
Hr.prototype.sqrTo = g1;
function y1() {
  if (this.t < 1) return 0;
  var e = this.data[0];
  if (!(e & 1)) return 0;
  var t = e & 3;
  return t = t * (2 - (e & 15) * t) & 15, t = t * (2 - (e & 255) * t) & 255, t = t * (2 - ((e & 65535) * t & 65535)) & 65535, t = t * (2 - e * t % this.DV) % this.DV, t > 0 ? this.DV - t : -t;
}
function zr(e) {
  this.m = e, this.mp = e.invDigit(), this.mpl = this.mp & 32767, this.mph = this.mp >> 15, this.um = (1 << e.DB - 15) - 1, this.mt2 = 2 * e.t;
}
function m1(e) {
  var t = Ke();
  return e.abs().dlShiftTo(this.m.t, t), t.divRemTo(this.m, null, t), e.s < 0 && t.compareTo(G.ZERO) > 0 && this.m.subTo(t, t), t;
}
function C1(e) {
  var t = Ke();
  return e.copyTo(t), this.reduce(t), t;
}
function E1(e) {
  for (; e.t <= this.mt2; )
    e.data[e.t++] = 0;
  for (var t = 0; t < this.m.t; ++t) {
    var a = e.data[t] & 32767, r = a * this.mpl + ((a * this.mph + (e.data[t] >> 15) * this.mpl & this.um) << 15) & e.DM;
    for (a = t + this.m.t, e.data[a] += this.m.am(0, r, e, t, 0, this.m.t); e.data[a] >= e.DV; )
      e.data[a] -= e.DV, e.data[++a]++;
  }
  e.clamp(), e.drShiftTo(this.m.t, e), e.compareTo(this.m) >= 0 && e.subTo(this.m, e);
}
function b1(e, t) {
  e.squareTo(t), this.reduce(t);
}
function S1(e, t, a) {
  e.multiplyTo(t, a), this.reduce(a);
}
zr.prototype.convert = m1;
zr.prototype.revert = C1;
zr.prototype.reduce = E1;
zr.prototype.mulTo = S1;
zr.prototype.sqrTo = b1;
function A1() {
  return (this.t > 0 ? this.data[0] & 1 : this.s) == 0;
}
function _1(e, t) {
  if (e > 4294967295 || e < 1) return G.ONE;
  var a = Ke(), r = Ke(), n = t.convert(this), i = Fn(e) - 1;
  for (n.copyTo(a); --i >= 0; )
    if (t.sqrTo(a, r), (e & 1 << i) > 0) t.mulTo(r, n, a);
    else {
      var s = a;
      a = r, r = s;
    }
  return t.revert(a);
}
function B1(e, t) {
  var a;
  return e < 256 || t.isEven() ? a = new Hr(t) : a = new zr(t), this.exp(e, a);
}
G.prototype.copyTo = Yh;
G.prototype.fromInt = Qh;
G.prototype.fromString = Xh;
G.prototype.clamp = Zh;
G.prototype.dlShiftTo = n1;
G.prototype.drShiftTo = i1;
G.prototype.lShiftTo = s1;
G.prototype.rShiftTo = o1;
G.prototype.subTo = l1;
G.prototype.multiplyTo = c1;
G.prototype.squareTo = u1;
G.prototype.divRemTo = f1;
G.prototype.invDigit = y1;
G.prototype.isEven = A1;
G.prototype.exp = _1;
G.prototype.toString = Jh;
G.prototype.negate = e1;
G.prototype.abs = t1;
G.prototype.compareTo = r1;
G.prototype.bitLength = a1;
G.prototype.mod = d1;
G.prototype.modPowInt = B1;
G.ZERO = xr(0);
G.ONE = xr(1);
function T1() {
  var e = Ke();
  return this.copyTo(e), e;
}
function I1() {
  if (this.s < 0) {
    if (this.t == 1) return this.data[0] - this.DV;
    if (this.t == 0) return -1;
  } else {
    if (this.t == 1) return this.data[0];
    if (this.t == 0) return 0;
  }
  return (this.data[1] & (1 << 32 - this.DB) - 1) << this.DB | this.data[0];
}
function w1() {
  return this.t == 0 ? this.s : this.data[0] << 24 >> 24;
}
function D1() {
  return this.t == 0 ? this.s : this.data[0] << 16 >> 16;
}
function N1(e) {
  return Math.floor(Math.LN2 * this.DB / Math.log(e));
}
function R1() {
  return this.s < 0 ? -1 : this.t <= 0 || this.t == 1 && this.data[0] <= 0 ? 0 : 1;
}
function k1(e) {
  if (e == null && (e = 10), this.signum() == 0 || e < 2 || e > 36) return "0";
  var t = this.chunkSize(e), a = Math.pow(e, t), r = xr(a), n = Ke(), i = Ke(), s = "";
  for (this.divRemTo(r, n, i); n.signum() > 0; )
    s = (a + i.intValue()).toString(e).substr(1) + s, n.divRemTo(r, n, i);
  return i.intValue().toString(e) + s;
}
function F1(e, t) {
  this.fromInt(0), t == null && (t = 10);
  for (var a = this.chunkSize(t), r = Math.pow(t, a), n = !1, i = 0, s = 0, o = 0; o < e.length; ++o) {
    var u = gl(e, o);
    if (u < 0) {
      e.charAt(o) == "-" && this.signum() == 0 && (n = !0);
      continue;
    }
    s = t * s + u, ++i >= a && (this.dMultiply(r), this.dAddOffset(s, 0), i = 0, s = 0);
  }
  i > 0 && (this.dMultiply(Math.pow(t, i)), this.dAddOffset(s, 0)), n && G.ZERO.subTo(this, this);
}
function L1(e, t, a) {
  if (typeof t == "number")
    if (e < 2) this.fromInt(1);
    else
      for (this.fromNumber(e, a), this.testBit(e - 1) || this.bitwiseTo(G.ONE.shiftLeft(e - 1), E0, this), this.isEven() && this.dAddOffset(1, 0); !this.isProbablePrime(t); )
        this.dAddOffset(2, 0), this.bitLength() > e && this.subTo(G.ONE.shiftLeft(e - 1), this);
  else {
    var r = new Array(), n = e & 7;
    r.length = (e >> 3) + 1, t.nextBytes(r), n > 0 ? r[0] &= (1 << n) - 1 : r[0] = 0, this.fromString(r, 256);
  }
}
function P1() {
  var e = this.t, t = new Array();
  t[0] = this.s;
  var a = this.DB - e * this.DB % 8, r, n = 0;
  if (e-- > 0)
    for (a < this.DB && (r = this.data[e] >> a) != (this.s & this.DM) >> a && (t[n++] = r | this.s << this.DB - a); e >= 0; )
      a < 8 ? (r = (this.data[e] & (1 << a) - 1) << 8 - a, r |= this.data[--e] >> (a += this.DB - 8)) : (r = this.data[e] >> (a -= 8) & 255, a <= 0 && (a += this.DB, --e)), r & 128 && (r |= -256), n == 0 && (this.s & 128) != (r & 128) && ++n, (n > 0 || r != this.s) && (t[n++] = r);
  return t;
}
function U1(e) {
  return this.compareTo(e) == 0;
}
function O1(e) {
  return this.compareTo(e) < 0 ? this : e;
}
function V1(e) {
  return this.compareTo(e) > 0 ? this : e;
}
function M1(e, t, a) {
  var r, n, i = Math.min(e.t, this.t);
  for (r = 0; r < i; ++r) a.data[r] = t(this.data[r], e.data[r]);
  if (e.t < this.t) {
    for (n = e.s & this.DM, r = i; r < this.t; ++r) a.data[r] = t(this.data[r], n);
    a.t = this.t;
  } else {
    for (n = this.s & this.DM, r = i; r < e.t; ++r) a.data[r] = t(n, e.data[r]);
    a.t = e.t;
  }
  a.s = t(this.s, e.s), a.clamp();
}
function K1(e, t) {
  return e & t;
}
function H1(e) {
  var t = Ke();
  return this.bitwiseTo(e, K1, t), t;
}
function E0(e, t) {
  return e | t;
}
function z1(e) {
  var t = Ke();
  return this.bitwiseTo(e, E0, t), t;
}
function yl(e, t) {
  return e ^ t;
}
function $1(e) {
  var t = Ke();
  return this.bitwiseTo(e, yl, t), t;
}
function ml(e, t) {
  return e & ~t;
}
function q1(e) {
  var t = Ke();
  return this.bitwiseTo(e, ml, t), t;
}
function j1() {
  for (var e = Ke(), t = 0; t < this.t; ++t) e.data[t] = this.DM & ~this.data[t];
  return e.t = this.t, e.s = ~this.s, e;
}
function G1(e) {
  var t = Ke();
  return e < 0 ? this.rShiftTo(-e, t) : this.lShiftTo(e, t), t;
}
function W1(e) {
  var t = Ke();
  return e < 0 ? this.lShiftTo(-e, t) : this.rShiftTo(e, t), t;
}
function Y1(e) {
  if (e == 0) return -1;
  var t = 0;
  return e & 65535 || (e >>= 16, t += 16), e & 255 || (e >>= 8, t += 8), e & 15 || (e >>= 4, t += 4), e & 3 || (e >>= 2, t += 2), e & 1 || ++t, t;
}
function Q1() {
  for (var e = 0; e < this.t; ++e)
    if (this.data[e] != 0) return e * this.DB + Y1(this.data[e]);
  return this.s < 0 ? this.t * this.DB : -1;
}
function X1(e) {
  for (var t = 0; e != 0; )
    e &= e - 1, ++t;
  return t;
}
function Z1() {
  for (var e = 0, t = this.s & this.DM, a = 0; a < this.t; ++a) e += X1(this.data[a] ^ t);
  return e;
}
function J1(e) {
  var t = Math.floor(e / this.DB);
  return t >= this.t ? this.s != 0 : (this.data[t] & 1 << e % this.DB) != 0;
}
function ev(e, t) {
  var a = G.ONE.shiftLeft(e);
  return this.bitwiseTo(a, t, a), a;
}
function tv(e) {
  return this.changeBit(e, E0);
}
function rv(e) {
  return this.changeBit(e, ml);
}
function av(e) {
  return this.changeBit(e, yl);
}
function nv(e, t) {
  for (var a = 0, r = 0, n = Math.min(e.t, this.t); a < n; )
    r += this.data[a] + e.data[a], t.data[a++] = r & this.DM, r >>= this.DB;
  if (e.t < this.t) {
    for (r += e.s; a < this.t; )
      r += this.data[a], t.data[a++] = r & this.DM, r >>= this.DB;
    r += this.s;
  } else {
    for (r += this.s; a < e.t; )
      r += e.data[a], t.data[a++] = r & this.DM, r >>= this.DB;
    r += e.s;
  }
  t.s = r < 0 ? -1 : 0, r > 0 ? t.data[a++] = r : r < -1 && (t.data[a++] = this.DV + r), t.t = a, t.clamp();
}
function iv(e) {
  var t = Ke();
  return this.addTo(e, t), t;
}
function sv(e) {
  var t = Ke();
  return this.subTo(e, t), t;
}
function ov(e) {
  var t = Ke();
  return this.multiplyTo(e, t), t;
}
function lv(e) {
  var t = Ke();
  return this.divRemTo(e, t, null), t;
}
function cv(e) {
  var t = Ke();
  return this.divRemTo(e, null, t), t;
}
function uv(e) {
  var t = Ke(), a = Ke();
  return this.divRemTo(e, t, a), new Array(t, a);
}
function fv(e) {
  this.data[this.t] = this.am(0, e - 1, this, 0, 0, this.t), ++this.t, this.clamp();
}
function dv(e, t) {
  if (e != 0) {
    for (; this.t <= t; ) this.data[this.t++] = 0;
    for (this.data[t] += e; this.data[t] >= this.DV; )
      this.data[t] -= this.DV, ++t >= this.t && (this.data[this.t++] = 0), ++this.data[t];
  }
}
function Ma() {
}
function Cl(e) {
  return e;
}
function pv(e, t, a) {
  e.multiplyTo(t, a);
}
function hv(e, t) {
  e.squareTo(t);
}
Ma.prototype.convert = Cl;
Ma.prototype.revert = Cl;
Ma.prototype.mulTo = pv;
Ma.prototype.sqrTo = hv;
function vv(e) {
  return this.exp(e, new Ma());
}
function xv(e, t, a) {
  var r = Math.min(this.t + e.t, t);
  for (a.s = 0, a.t = r; r > 0; ) a.data[--r] = 0;
  var n;
  for (n = a.t - this.t; r < n; ++r) a.data[r + this.t] = this.am(0, e.data[r], a, r, 0, this.t);
  for (n = Math.min(e.t, t); r < n; ++r) this.am(0, e.data[r], a, r, 0, t - r);
  a.clamp();
}
function gv(e, t, a) {
  --t;
  var r = a.t = this.t + e.t - t;
  for (a.s = 0; --r >= 0; ) a.data[r] = 0;
  for (r = Math.max(t - this.t, 0); r < e.t; ++r)
    a.data[this.t + r - t] = this.am(t - r, e.data[r], a, 0, 0, this.t + r - t);
  a.clamp(), a.drShiftTo(1, a);
}
function ha(e) {
  this.r2 = Ke(), this.q3 = Ke(), G.ONE.dlShiftTo(2 * e.t, this.r2), this.mu = this.r2.divide(e), this.m = e;
}
function yv(e) {
  if (e.s < 0 || e.t > 2 * this.m.t) return e.mod(this.m);
  if (e.compareTo(this.m) < 0) return e;
  var t = Ke();
  return e.copyTo(t), this.reduce(t), t;
}
function mv(e) {
  return e;
}
function Cv(e) {
  for (e.drShiftTo(this.m.t - 1, this.r2), e.t > this.m.t + 1 && (e.t = this.m.t + 1, e.clamp()), this.mu.multiplyUpperTo(this.r2, this.m.t + 1, this.q3), this.m.multiplyLowerTo(this.q3, this.m.t + 1, this.r2); e.compareTo(this.r2) < 0; ) e.dAddOffset(1, this.m.t + 1);
  for (e.subTo(this.r2, e); e.compareTo(this.m) >= 0; ) e.subTo(this.m, e);
}
function Ev(e, t) {
  e.squareTo(t), this.reduce(t);
}
function bv(e, t, a) {
  e.multiplyTo(t, a), this.reduce(a);
}
ha.prototype.convert = yv;
ha.prototype.revert = mv;
ha.prototype.reduce = Cv;
ha.prototype.mulTo = bv;
ha.prototype.sqrTo = Ev;
function Sv(e, t) {
  var a = e.bitLength(), r, n = xr(1), i;
  if (a <= 0) return n;
  a < 18 ? r = 1 : a < 48 ? r = 3 : a < 144 ? r = 4 : a < 768 ? r = 5 : r = 6, a < 8 ? i = new Hr(t) : t.isEven() ? i = new ha(t) : i = new zr(t);
  var s = new Array(), o = 3, u = r - 1, l = (1 << r) - 1;
  if (s[1] = i.convert(this), r > 1) {
    var c = Ke();
    for (i.sqrTo(s[1], c); o <= l; )
      s[o] = Ke(), i.mulTo(c, s[o - 2], s[o]), o += 2;
  }
  var f = e.t - 1, d, v = !0, p = Ke(), h;
  for (a = Fn(e.data[f]) - 1; f >= 0; ) {
    for (a >= u ? d = e.data[f] >> a - u & l : (d = (e.data[f] & (1 << a + 1) - 1) << u - a, f > 0 && (d |= e.data[f - 1] >> this.DB + a - u)), o = r; !(d & 1); )
      d >>= 1, --o;
    if ((a -= o) < 0 && (a += this.DB, --f), v)
      s[d].copyTo(n), v = !1;
    else {
      for (; o > 1; )
        i.sqrTo(n, p), i.sqrTo(p, n), o -= 2;
      o > 0 ? i.sqrTo(n, p) : (h = n, n = p, p = h), i.mulTo(p, s[d], n);
    }
    for (; f >= 0 && !(e.data[f] & 1 << a); )
      i.sqrTo(n, p), h = n, n = p, p = h, --a < 0 && (a = this.DB - 1, --f);
  }
  return i.revert(n);
}
function Av(e) {
  var t = this.s < 0 ? this.negate() : this.clone(), a = e.s < 0 ? e.negate() : e.clone();
  if (t.compareTo(a) < 0) {
    var r = t;
    t = a, a = r;
  }
  var n = t.getLowestSetBit(), i = a.getLowestSetBit();
  if (i < 0) return t;
  for (n < i && (i = n), i > 0 && (t.rShiftTo(i, t), a.rShiftTo(i, a)); t.signum() > 0; )
    (n = t.getLowestSetBit()) > 0 && t.rShiftTo(n, t), (n = a.getLowestSetBit()) > 0 && a.rShiftTo(n, a), t.compareTo(a) >= 0 ? (t.subTo(a, t), t.rShiftTo(1, t)) : (a.subTo(t, a), a.rShiftTo(1, a));
  return i > 0 && a.lShiftTo(i, a), a;
}
function _v(e) {
  if (e <= 0) return 0;
  var t = this.DV % e, a = this.s < 0 ? e - 1 : 0;
  if (this.t > 0)
    if (t == 0) a = this.data[0] % e;
    else for (var r = this.t - 1; r >= 0; --r) a = (t * a + this.data[r]) % e;
  return a;
}
function Bv(e) {
  var t = e.isEven();
  if (this.isEven() && t || e.signum() == 0) return G.ZERO;
  for (var a = e.clone(), r = this.clone(), n = xr(1), i = xr(0), s = xr(0), o = xr(1); a.signum() != 0; ) {
    for (; a.isEven(); )
      a.rShiftTo(1, a), t ? ((!n.isEven() || !i.isEven()) && (n.addTo(this, n), i.subTo(e, i)), n.rShiftTo(1, n)) : i.isEven() || i.subTo(e, i), i.rShiftTo(1, i);
    for (; r.isEven(); )
      r.rShiftTo(1, r), t ? ((!s.isEven() || !o.isEven()) && (s.addTo(this, s), o.subTo(e, o)), s.rShiftTo(1, s)) : o.isEven() || o.subTo(e, o), o.rShiftTo(1, o);
    a.compareTo(r) >= 0 ? (a.subTo(r, a), t && n.subTo(s, n), i.subTo(o, i)) : (r.subTo(a, r), t && s.subTo(n, s), o.subTo(i, o));
  }
  if (r.compareTo(G.ONE) != 0) return G.ZERO;
  if (o.compareTo(e) >= 0) return o.subtract(e);
  if (o.signum() < 0) o.addTo(e, o);
  else return o;
  return o.signum() < 0 ? o.add(e) : o;
}
var Mt = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509], Tv = (1 << 26) / Mt[Mt.length - 1];
function Iv(e) {
  var t, a = this.abs();
  if (a.t == 1 && a.data[0] <= Mt[Mt.length - 1]) {
    for (t = 0; t < Mt.length; ++t)
      if (a.data[0] == Mt[t]) return !0;
    return !1;
  }
  if (a.isEven()) return !1;
  for (t = 1; t < Mt.length; ) {
    for (var r = Mt[t], n = t + 1; n < Mt.length && r < Tv; ) r *= Mt[n++];
    for (r = a.modInt(r); t < n; ) if (r % Mt[t++] == 0) return !1;
  }
  return a.millerRabin(e);
}
function wv(e) {
  var t = this.subtract(G.ONE), a = t.getLowestSetBit();
  if (a <= 0) return !1;
  for (var r = t.shiftRight(a), n = Dv(), i, s = 0; s < e; ++s) {
    do
      i = new G(this.bitLength(), n);
    while (i.compareTo(G.ONE) <= 0 || i.compareTo(t) >= 0);
    var o = i.modPow(r, this);
    if (o.compareTo(G.ONE) != 0 && o.compareTo(t) != 0) {
      for (var u = 1; u++ < a && o.compareTo(t) != 0; )
        if (o = o.modPowInt(2, this), o.compareTo(G.ONE) == 0) return !1;
      if (o.compareTo(t) != 0) return !1;
    }
  }
  return !0;
}
function Dv() {
  return {
    // x is an array to fill with bytes
    nextBytes: function(e) {
      for (var t = 0; t < e.length; ++t)
        e[t] = Math.floor(Math.random() * 256);
    }
  };
}
G.prototype.chunkSize = N1;
G.prototype.toRadix = k1;
G.prototype.fromRadix = F1;
G.prototype.fromNumber = L1;
G.prototype.bitwiseTo = M1;
G.prototype.changeBit = ev;
G.prototype.addTo = nv;
G.prototype.dMultiply = fv;
G.prototype.dAddOffset = dv;
G.prototype.multiplyLowerTo = xv;
G.prototype.multiplyUpperTo = gv;
G.prototype.modInt = _v;
G.prototype.millerRabin = wv;
G.prototype.clone = T1;
G.prototype.intValue = I1;
G.prototype.byteValue = w1;
G.prototype.shortValue = D1;
G.prototype.signum = R1;
G.prototype.toByteArray = P1;
G.prototype.equals = U1;
G.prototype.min = O1;
G.prototype.max = V1;
G.prototype.and = H1;
G.prototype.or = z1;
G.prototype.xor = $1;
G.prototype.andNot = q1;
G.prototype.not = j1;
G.prototype.shiftLeft = G1;
G.prototype.shiftRight = W1;
G.prototype.getLowestSetBit = Q1;
G.prototype.bitCount = Z1;
G.prototype.testBit = J1;
G.prototype.setBit = tv;
G.prototype.clearBit = rv;
G.prototype.flipBit = av;
G.prototype.add = iv;
G.prototype.subtract = sv;
G.prototype.multiply = ov;
G.prototype.divide = lv;
G.prototype.remainder = cv;
G.prototype.divideAndRemainder = uv;
G.prototype.modPow = Sv;
G.prototype.modInverse = Bv;
G.prototype.pow = vv;
G.prototype.gcd = Av;
G.prototype.isProbablePrime = Iv;
var Qt = Re, El = Qt.sha1 = Qt.sha1 || {};
Qt.md.sha1 = Qt.md.algorithms.sha1 = El;
El.create = function() {
  bl || Nv();
  var e = null, t = Qt.util.createBuffer(), a = new Array(80), r = {
    algorithm: "sha1",
    blockLength: 64,
    digestLength: 20,
    // 56-bit length of message so far (does not including padding)
    messageLength: 0,
    // true message length
    fullMessageLength: null,
    // size of message length in bytes
    messageLengthSize: 8
  };
  return r.start = function() {
    r.messageLength = 0, r.fullMessageLength = r.messageLength64 = [];
    for (var n = r.messageLengthSize / 4, i = 0; i < n; ++i)
      r.fullMessageLength.push(0);
    return t = Qt.util.createBuffer(), e = {
      h0: 1732584193,
      h1: 4023233417,
      h2: 2562383102,
      h3: 271733878,
      h4: 3285377520
    }, r;
  }, r.start(), r.update = function(n, i) {
    i === "utf8" && (n = Qt.util.encodeUtf8(n));
    var s = n.length;
    r.messageLength += s, s = [s / 4294967296 >>> 0, s >>> 0];
    for (var o = r.fullMessageLength.length - 1; o >= 0; --o)
      r.fullMessageLength[o] += s[1], s[1] = s[0] + (r.fullMessageLength[o] / 4294967296 >>> 0), r.fullMessageLength[o] = r.fullMessageLength[o] >>> 0, s[0] = s[1] / 4294967296 >>> 0;
    return t.putBytes(n), ao(e, a, t), (t.read > 2048 || t.length() === 0) && t.compact(), r;
  }, r.digest = function() {
    var n = Qt.util.createBuffer();
    n.putBytes(t.bytes());
    var i = r.fullMessageLength[r.fullMessageLength.length - 1] + r.messageLengthSize, s = i & r.blockLength - 1;
    n.putBytes(qi.substr(0, r.blockLength - s));
    for (var o, u, l = r.fullMessageLength[0] * 8, c = 0; c < r.fullMessageLength.length - 1; ++c)
      o = r.fullMessageLength[c + 1] * 8, u = o / 4294967296 >>> 0, l += u, n.putInt32(l >>> 0), l = o >>> 0;
    n.putInt32(l);
    var f = {
      h0: e.h0,
      h1: e.h1,
      h2: e.h2,
      h3: e.h3,
      h4: e.h4
    };
    ao(f, a, n);
    var d = Qt.util.createBuffer();
    return d.putInt32(f.h0), d.putInt32(f.h1), d.putInt32(f.h2), d.putInt32(f.h3), d.putInt32(f.h4), d;
  }, r;
};
var qi = null, bl = !1;
function Nv() {
  qi = "", qi += Qt.util.fillString("\0", 64), bl = !0;
}
function ao(e, t, a) {
  for (var r, n, i, s, o, u, l, c, f = a.length(); f >= 64; ) {
    for (n = e.h0, i = e.h1, s = e.h2, o = e.h3, u = e.h4, c = 0; c < 16; ++c)
      r = a.getInt32(), t[c] = r, l = o ^ i & (s ^ o), r = (n << 5 | n >>> 27) + l + u + 1518500249 + r, u = o, o = s, s = (i << 30 | i >>> 2) >>> 0, i = n, n = r;
    for (; c < 20; ++c)
      r = t[c - 3] ^ t[c - 8] ^ t[c - 14] ^ t[c - 16], r = r << 1 | r >>> 31, t[c] = r, l = o ^ i & (s ^ o), r = (n << 5 | n >>> 27) + l + u + 1518500249 + r, u = o, o = s, s = (i << 30 | i >>> 2) >>> 0, i = n, n = r;
    for (; c < 32; ++c)
      r = t[c - 3] ^ t[c - 8] ^ t[c - 14] ^ t[c - 16], r = r << 1 | r >>> 31, t[c] = r, l = i ^ s ^ o, r = (n << 5 | n >>> 27) + l + u + 1859775393 + r, u = o, o = s, s = (i << 30 | i >>> 2) >>> 0, i = n, n = r;
    for (; c < 40; ++c)
      r = t[c - 6] ^ t[c - 16] ^ t[c - 28] ^ t[c - 32], r = r << 2 | r >>> 30, t[c] = r, l = i ^ s ^ o, r = (n << 5 | n >>> 27) + l + u + 1859775393 + r, u = o, o = s, s = (i << 30 | i >>> 2) >>> 0, i = n, n = r;
    for (; c < 60; ++c)
      r = t[c - 6] ^ t[c - 16] ^ t[c - 28] ^ t[c - 32], r = r << 2 | r >>> 30, t[c] = r, l = i & s | o & (i ^ s), r = (n << 5 | n >>> 27) + l + u + 2400959708 + r, u = o, o = s, s = (i << 30 | i >>> 2) >>> 0, i = n, n = r;
    for (; c < 80; ++c)
      r = t[c - 6] ^ t[c - 16] ^ t[c - 28] ^ t[c - 32], r = r << 2 | r >>> 30, t[c] = r, l = i ^ s ^ o, r = (n << 5 | n >>> 27) + l + u + 3395469782 + r, u = o, o = s, s = (i << 30 | i >>> 2) >>> 0, i = n, n = r;
    e.h0 = e.h0 + n | 0, e.h1 = e.h1 + i | 0, e.h2 = e.h2 + s | 0, e.h3 = e.h3 + o | 0, e.h4 = e.h4 + u | 0, f -= 64;
  }
}
var Xt = Re, Sl = Xt.pkcs1 = Xt.pkcs1 || {};
Sl.encode_rsa_oaep = function(e, t, a) {
  var r, n, i, s;
  typeof a == "string" ? (r = a, n = arguments[3] || void 0, i = arguments[4] || void 0) : a && (r = a.label || void 0, n = a.seed || void 0, i = a.md || void 0, a.mgf1 && a.mgf1.md && (s = a.mgf1.md)), i ? i.start() : i = Xt.md.sha1.create(), s || (s = i);
  var o = Math.ceil(e.n.bitLength() / 8), u = o - 2 * i.digestLength - 2;
  if (t.length > u) {
    var l = new Error("RSAES-OAEP input message length is too long.");
    throw l.length = t.length, l.maxLength = u, l;
  }
  r || (r = ""), i.update(r, "raw");
  for (var c = i.digest(), f = "", d = u - t.length, v = 0; v < d; v++)
    f += "\0";
  var p = c.getBytes() + f + "" + t;
  if (!n)
    n = Xt.random.getBytes(i.digestLength);
  else if (n.length !== i.digestLength) {
    var l = new Error("Invalid RSAES-OAEP seed. The seed length must match the digest length.");
    throw l.seedLength = n.length, l.digestLength = i.digestLength, l;
  }
  var h = un(n, o - i.digestLength - 1, s), x = Xt.util.xorBytes(p, h, p.length), y = un(x, i.digestLength, s), g = Xt.util.xorBytes(n, y, n.length);
  return "\0" + g + x;
};
Sl.decode_rsa_oaep = function(e, t, a) {
  var r, n, i;
  typeof a == "string" ? (r = a, n = arguments[3] || void 0) : a && (r = a.label || void 0, n = a.md || void 0, a.mgf1 && a.mgf1.md && (i = a.mgf1.md));
  var s = Math.ceil(e.n.bitLength() / 8);
  if (t.length !== s) {
    var x = new Error("RSAES-OAEP encoded message length is invalid.");
    throw x.length = t.length, x.expectedLength = s, x;
  }
  if (n === void 0 ? n = Xt.md.sha1.create() : n.start(), i || (i = n), s < 2 * n.digestLength + 2)
    throw new Error("RSAES-OAEP key is too short for the hash function.");
  r || (r = ""), n.update(r, "raw");
  for (var o = n.digest().getBytes(), u = t.charAt(0), l = t.substring(1, n.digestLength + 1), c = t.substring(1 + n.digestLength), f = un(c, n.digestLength, i), d = Xt.util.xorBytes(l, f, l.length), v = un(d, s - n.digestLength - 1, i), p = Xt.util.xorBytes(c, v, c.length), h = p.substring(0, n.digestLength), x = u !== "\0", y = 0; y < n.digestLength; ++y)
    x |= o.charAt(y) !== h.charAt(y);
  for (var g = 1, m = n.digestLength, C = n.digestLength; C < p.length; C++) {
    var S = p.charCodeAt(C), _ = S & 1 ^ 1, T = g ? 65534 : 0;
    x |= S & T, g = g & _, m += g;
  }
  if (x || p.charCodeAt(m) !== 1)
    throw new Error("Invalid RSAES-OAEP padding.");
  return p.substring(m + 1);
};
function un(e, t, a) {
  a || (a = Xt.md.sha1.create());
  for (var r = "", n = Math.ceil(t / a.digestLength), i = 0; i < n; ++i) {
    var s = String.fromCharCode(
      i >> 24 & 255,
      i >> 16 & 255,
      i >> 8 & 255,
      i & 255
    );
    a.start(), a.update(e + s), r += a.digest().getBytes();
  }
  return r.substring(0, t);
}
var ur = Re;
(function() {
  if (ur.prime) {
    ur.prime;
    return;
  }
  var e = ur.prime = ur.prime || {}, t = ur.jsbn.BigInteger, a = [6, 4, 2, 4, 2, 4, 6, 2], r = new t(null);
  r.fromInt(30);
  var n = function(f, d) {
    return f | d;
  };
  e.generateProbablePrime = function(f, d, v) {
    typeof d == "function" && (v = d, d = {}), d = d || {};
    var p = d.algorithm || "PRIMEINC";
    typeof p == "string" && (p = { name: p }), p.options = p.options || {};
    var h = d.prng || ur.random, x = {
      // x is an array to fill with bytes
      nextBytes: function(y) {
        for (var g = h.getBytesSync(y.length), m = 0; m < y.length; ++m)
          y[m] = g.charCodeAt(m);
      }
    };
    if (p.name === "PRIMEINC")
      return i(f, x, p.options, v);
    throw new Error("Invalid prime generation algorithm: " + p.name);
  };
  function i(f, d, v, p) {
    return "workers" in v ? u(f, d, v, p) : s(f, d, v, p);
  }
  function s(f, d, v, p) {
    var h = l(f, d), x = 0, y = c(h.bitLength());
    "millerRabinTests" in v && (y = v.millerRabinTests);
    var g = 10;
    "maxBlockTime" in v && (g = v.maxBlockTime), o(h, f, d, x, y, g, p);
  }
  function o(f, d, v, p, h, x, y) {
    var g = +/* @__PURE__ */ new Date();
    do {
      if (f.bitLength() > d && (f = l(d, v)), f.isProbablePrime(h))
        return y(null, f);
      f.dAddOffset(a[p++ % 8], 0);
    } while (x < 0 || +/* @__PURE__ */ new Date() - g < x);
    ur.util.setImmediate(function() {
      o(f, d, v, p, h, x, y);
    });
  }
  function u(f, d, v, p) {
    if (typeof Worker > "u")
      return s(f, d, v, p);
    var h = l(f, d), x = v.workers, y = v.workLoad || 100, g = y * 30 / 8, m = v.workerScript || "forge/prime.worker.js";
    if (x === -1)
      return ur.util.estimateCores(function(S, _) {
        S && (_ = 2), x = _ - 1, C();
      });
    C();
    function C() {
      x = Math.max(1, x);
      for (var S = [], _ = 0; _ < x; ++_)
        S[_] = new Worker(m);
      for (var _ = 0; _ < x; ++_)
        S[_].addEventListener("message", D);
      var T = !1;
      function D(P) {
        if (!T) {
          var I = P.data;
          if (I.found) {
            for (var B = 0; B < S.length; ++B)
              S[B].terminate();
            return T = !0, p(null, new t(I.prime, 16));
          }
          h.bitLength() > f && (h = l(f, d));
          var R = h.toString(16);
          P.target.postMessage({
            hex: R,
            workLoad: y
          }), h.dAddOffset(g, 0);
        }
      }
    }
  }
  function l(f, d) {
    var v = new t(f, d), p = f - 1;
    return v.testBit(p) || v.bitwiseTo(t.ONE.shiftLeft(p), n, v), v.dAddOffset(31 - v.mod(r).byteValue(), 0), v;
  }
  function c(f) {
    return f <= 100 ? 27 : f <= 150 ? 18 : f <= 200 ? 15 : f <= 250 ? 12 : f <= 300 ? 9 : f <= 350 ? 8 : f <= 400 ? 7 : f <= 500 ? 6 : f <= 600 ? 5 : f <= 800 ? 4 : f <= 1250 ? 3 : 2;
  }
})();
var me = Re;
if (typeof Me > "u")
  var Me = me.jsbn.BigInteger;
var ji = me.util.isNodejs ? Bn : null, L = me.asn1, Pt = me.util;
me.pki = me.pki || {};
me.pki.rsa = me.rsa = me.rsa || {};
var Be = me.pki, Rv = [6, 4, 2, 4, 2, 4, 6, 2], kv = {
  // PrivateKeyInfo
  name: "PrivateKeyInfo",
  tagClass: L.Class.UNIVERSAL,
  type: L.Type.SEQUENCE,
  constructed: !0,
  value: [{
    // Version (INTEGER)
    name: "PrivateKeyInfo.version",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyVersion"
  }, {
    // privateKeyAlgorithm
    name: "PrivateKeyInfo.privateKeyAlgorithm",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "AlgorithmIdentifier.algorithm",
      tagClass: L.Class.UNIVERSAL,
      type: L.Type.OID,
      constructed: !1,
      capture: "privateKeyOid"
    }]
  }, {
    // PrivateKey
    name: "PrivateKeyInfo",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.OCTETSTRING,
    constructed: !1,
    capture: "privateKey"
  }]
}, Fv = {
  // RSAPrivateKey
  name: "RSAPrivateKey",
  tagClass: L.Class.UNIVERSAL,
  type: L.Type.SEQUENCE,
  constructed: !0,
  value: [{
    // Version (INTEGER)
    name: "RSAPrivateKey.version",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyVersion"
  }, {
    // modulus (n)
    name: "RSAPrivateKey.modulus",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyModulus"
  }, {
    // publicExponent (e)
    name: "RSAPrivateKey.publicExponent",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyPublicExponent"
  }, {
    // privateExponent (d)
    name: "RSAPrivateKey.privateExponent",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyPrivateExponent"
  }, {
    // prime1 (p)
    name: "RSAPrivateKey.prime1",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyPrime1"
  }, {
    // prime2 (q)
    name: "RSAPrivateKey.prime2",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyPrime2"
  }, {
    // exponent1 (d mod (p-1))
    name: "RSAPrivateKey.exponent1",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyExponent1"
  }, {
    // exponent2 (d mod (q-1))
    name: "RSAPrivateKey.exponent2",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyExponent2"
  }, {
    // coefficient ((inverse of q) mod p)
    name: "RSAPrivateKey.coefficient",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyCoefficient"
  }]
}, Lv = {
  // RSAPublicKey
  name: "RSAPublicKey",
  tagClass: L.Class.UNIVERSAL,
  type: L.Type.SEQUENCE,
  constructed: !0,
  value: [{
    // modulus (n)
    name: "RSAPublicKey.modulus",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "publicKeyModulus"
  }, {
    // publicExponent (e)
    name: "RSAPublicKey.exponent",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.INTEGER,
    constructed: !1,
    capture: "publicKeyExponent"
  }]
}, Pv = me.pki.rsa.publicKeyValidator = {
  name: "SubjectPublicKeyInfo",
  tagClass: L.Class.UNIVERSAL,
  type: L.Type.SEQUENCE,
  constructed: !0,
  captureAsn1: "subjectPublicKeyInfo",
  value: [{
    name: "SubjectPublicKeyInfo.AlgorithmIdentifier",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "AlgorithmIdentifier.algorithm",
      tagClass: L.Class.UNIVERSAL,
      type: L.Type.OID,
      constructed: !1,
      capture: "publicKeyOid"
    }]
  }, {
    // subjectPublicKey
    name: "SubjectPublicKeyInfo.subjectPublicKey",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.BITSTRING,
    constructed: !1,
    value: [{
      // RSAPublicKey
      name: "SubjectPublicKeyInfo.subjectPublicKey.RSAPublicKey",
      tagClass: L.Class.UNIVERSAL,
      type: L.Type.SEQUENCE,
      constructed: !0,
      optional: !0,
      captureAsn1: "rsaPublicKey"
    }]
  }]
}, Uv = {
  name: "DigestInfo",
  tagClass: L.Class.UNIVERSAL,
  type: L.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "DigestInfo.DigestAlgorithm",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "DigestInfo.DigestAlgorithm.algorithmIdentifier",
      tagClass: L.Class.UNIVERSAL,
      type: L.Type.OID,
      constructed: !1,
      capture: "algorithmIdentifier"
    }, {
      // NULL paramters
      name: "DigestInfo.DigestAlgorithm.parameters",
      tagClass: L.Class.UNIVERSAL,
      type: L.Type.NULL,
      // captured only to check existence for md2 and md5
      capture: "parameters",
      optional: !0,
      constructed: !1
    }]
  }, {
    // digest
    name: "DigestInfo.digest",
    tagClass: L.Class.UNIVERSAL,
    type: L.Type.OCTETSTRING,
    constructed: !1,
    capture: "digest"
  }]
}, Ov = function(e) {
  var t;
  if (e.algorithm in Be.oids)
    t = Be.oids[e.algorithm];
  else {
    var a = new Error("Unknown message digest algorithm.");
    throw a.algorithm = e.algorithm, a;
  }
  var r = L.oidToDer(t).getBytes(), n = L.create(
    L.Class.UNIVERSAL,
    L.Type.SEQUENCE,
    !0,
    []
  ), i = L.create(
    L.Class.UNIVERSAL,
    L.Type.SEQUENCE,
    !0,
    []
  );
  i.value.push(L.create(
    L.Class.UNIVERSAL,
    L.Type.OID,
    !1,
    r
  )), i.value.push(L.create(
    L.Class.UNIVERSAL,
    L.Type.NULL,
    !1,
    ""
  ));
  var s = L.create(
    L.Class.UNIVERSAL,
    L.Type.OCTETSTRING,
    !1,
    e.digest().getBytes()
  );
  return n.value.push(i), n.value.push(s), L.toDer(n).getBytes();
}, Al = function(e, t, a) {
  if (a)
    return e.modPow(t.e, t.n);
  if (!t.p || !t.q)
    return e.modPow(t.d, t.n);
  t.dP || (t.dP = t.d.mod(t.p.subtract(Me.ONE))), t.dQ || (t.dQ = t.d.mod(t.q.subtract(Me.ONE))), t.qInv || (t.qInv = t.q.modInverse(t.p));
  var r;
  do
    r = new Me(
      me.util.bytesToHex(me.random.getBytes(t.n.bitLength() / 8)),
      16
    );
  while (r.compareTo(t.n) >= 0 || !r.gcd(t.n).equals(Me.ONE));
  e = e.multiply(r.modPow(t.e, t.n)).mod(t.n);
  for (var n = e.mod(t.p).modPow(t.dP, t.p), i = e.mod(t.q).modPow(t.dQ, t.q); n.compareTo(i) < 0; )
    n = n.add(t.p);
  var s = n.subtract(i).multiply(t.qInv).mod(t.p).multiply(t.q).add(i);
  return s = s.multiply(r.modInverse(t.n)).mod(t.n), s;
};
Be.rsa.encrypt = function(e, t, a) {
  var r = a, n, i = Math.ceil(t.n.bitLength() / 8);
  a !== !1 && a !== !0 ? (r = a === 2, n = _l(e, t, a)) : (n = me.util.createBuffer(), n.putBytes(e));
  for (var s = new Me(n.toHex(), 16), o = Al(s, t, r), u = o.toString(16), l = me.util.createBuffer(), c = i - Math.ceil(u.length / 2); c > 0; )
    l.putByte(0), --c;
  return l.putBytes(me.util.hexToBytes(u)), l.getBytes();
};
Be.rsa.decrypt = function(e, t, a, r) {
  var n = Math.ceil(t.n.bitLength() / 8);
  if (e.length !== n) {
    var i = new Error("Encrypted message length is invalid.");
    throw i.length = e.length, i.expected = n, i;
  }
  var s = new Me(me.util.createBuffer(e).toHex(), 16);
  if (s.compareTo(t.n) >= 0)
    throw new Error("Encrypted message is invalid.");
  for (var o = Al(s, t, a), u = o.toString(16), l = me.util.createBuffer(), c = n - Math.ceil(u.length / 2); c > 0; )
    l.putByte(0), --c;
  return l.putBytes(me.util.hexToBytes(u)), r !== !1 ? fn(l.getBytes(), t, a) : l.getBytes();
};
Be.rsa.createKeyPairGenerationState = function(e, t, a) {
  typeof e == "string" && (e = parseInt(e, 10)), e = e || 2048, a = a || {};
  var r = a.prng || me.random, n = {
    // x is an array to fill with bytes
    nextBytes: function(o) {
      for (var u = r.getBytesSync(o.length), l = 0; l < o.length; ++l)
        o[l] = u.charCodeAt(l);
    }
  }, i = a.algorithm || "PRIMEINC", s;
  if (i === "PRIMEINC")
    s = {
      algorithm: i,
      state: 0,
      bits: e,
      rng: n,
      eInt: t || 65537,
      e: new Me(null),
      p: null,
      q: null,
      qBits: e >> 1,
      pBits: e - (e >> 1),
      pqState: 0,
      num: null,
      keys: null
    }, s.e.fromInt(s.eInt);
  else
    throw new Error("Invalid key generation algorithm: " + i);
  return s;
};
Be.rsa.stepKeyPairGenerationState = function(e, t) {
  "algorithm" in e || (e.algorithm = "PRIMEINC");
  var a = new Me(null);
  a.fromInt(30);
  for (var r = 0, n = function(f, d) {
    return f | d;
  }, i = +/* @__PURE__ */ new Date(), s, o = 0; e.keys === null && (t <= 0 || o < t); ) {
    if (e.state === 0) {
      var u = e.p === null ? e.pBits : e.qBits, l = u - 1;
      e.pqState === 0 ? (e.num = new Me(u, e.rng), e.num.testBit(l) || e.num.bitwiseTo(
        Me.ONE.shiftLeft(l),
        n,
        e.num
      ), e.num.dAddOffset(31 - e.num.mod(a).byteValue(), 0), r = 0, ++e.pqState) : e.pqState === 1 ? e.num.bitLength() > u ? e.pqState = 0 : e.num.isProbablePrime(
        Mv(e.num.bitLength())
      ) ? ++e.pqState : e.num.dAddOffset(Rv[r++ % 8], 0) : e.pqState === 2 ? e.pqState = e.num.subtract(Me.ONE).gcd(e.e).compareTo(Me.ONE) === 0 ? 3 : 0 : e.pqState === 3 && (e.pqState = 0, e.p === null ? e.p = e.num : e.q = e.num, e.p !== null && e.q !== null && ++e.state, e.num = null);
    } else if (e.state === 1)
      e.p.compareTo(e.q) < 0 && (e.num = e.p, e.p = e.q, e.q = e.num), ++e.state;
    else if (e.state === 2)
      e.p1 = e.p.subtract(Me.ONE), e.q1 = e.q.subtract(Me.ONE), e.phi = e.p1.multiply(e.q1), ++e.state;
    else if (e.state === 3)
      e.phi.gcd(e.e).compareTo(Me.ONE) === 0 ? ++e.state : (e.p = null, e.q = null, e.state = 0);
    else if (e.state === 4)
      e.n = e.p.multiply(e.q), e.n.bitLength() === e.bits ? ++e.state : (e.q = null, e.state = 0);
    else if (e.state === 5) {
      var c = e.e.modInverse(e.phi);
      e.keys = {
        privateKey: Be.rsa.setPrivateKey(
          e.n,
          e.e,
          c,
          e.p,
          e.q,
          c.mod(e.p1),
          c.mod(e.q1),
          e.q.modInverse(e.p)
        ),
        publicKey: Be.rsa.setPublicKey(e.n, e.e)
      };
    }
    s = +/* @__PURE__ */ new Date(), o += s - i, i = s;
  }
  return e.keys !== null;
};
Be.rsa.generateKeyPair = function(e, t, a, r) {
  if (arguments.length === 1 ? typeof e == "object" ? (a = e, e = void 0) : typeof e == "function" && (r = e, e = void 0) : arguments.length === 2 ? typeof e == "number" ? typeof t == "function" ? (r = t, t = void 0) : typeof t != "number" && (a = t, t = void 0) : (a = e, r = t, e = void 0, t = void 0) : arguments.length === 3 && (typeof t == "number" ? typeof a == "function" && (r = a, a = void 0) : (r = a, a = t, t = void 0)), a = a || {}, e === void 0 && (e = a.bits || 2048), t === void 0 && (t = a.e || 65537), !me.options.usePureJavaScript && !a.prng && e >= 256 && e <= 16384 && (t === 65537 || t === 3)) {
    if (r) {
      if (no("generateKeyPair"))
        return ji.generateKeyPair("rsa", {
          modulusLength: e,
          publicExponent: t,
          publicKeyEncoding: {
            type: "spki",
            format: "pem"
          },
          privateKeyEncoding: {
            type: "pkcs8",
            format: "pem"
          }
        }, function(o, u, l) {
          if (o)
            return r(o);
          r(null, {
            privateKey: Be.privateKeyFromPem(l),
            publicKey: Be.publicKeyFromPem(u)
          });
        });
      if (io("generateKey") && io("exportKey"))
        return Pt.globalScope.crypto.subtle.generateKey({
          name: "RSASSA-PKCS1-v1_5",
          modulusLength: e,
          publicExponent: oo(t),
          hash: { name: "SHA-256" }
        }, !0, ["sign", "verify"]).then(function(o) {
          return Pt.globalScope.crypto.subtle.exportKey(
            "pkcs8",
            o.privateKey
          );
        }).then(void 0, function(o) {
          r(o);
        }).then(function(o) {
          if (o) {
            var u = Be.privateKeyFromAsn1(
              L.fromDer(me.util.createBuffer(o))
            );
            r(null, {
              privateKey: u,
              publicKey: Be.setRsaPublicKey(u.n, u.e)
            });
          }
        });
      if (so("generateKey") && so("exportKey")) {
        var n = Pt.globalScope.msCrypto.subtle.generateKey({
          name: "RSASSA-PKCS1-v1_5",
          modulusLength: e,
          publicExponent: oo(t),
          hash: { name: "SHA-256" }
        }, !0, ["sign", "verify"]);
        n.oncomplete = function(o) {
          var u = o.target.result, l = Pt.globalScope.msCrypto.subtle.exportKey(
            "pkcs8",
            u.privateKey
          );
          l.oncomplete = function(c) {
            var f = c.target.result, d = Be.privateKeyFromAsn1(
              L.fromDer(me.util.createBuffer(f))
            );
            r(null, {
              privateKey: d,
              publicKey: Be.setRsaPublicKey(d.n, d.e)
            });
          }, l.onerror = function(c) {
            r(c);
          };
        }, n.onerror = function(o) {
          r(o);
        };
        return;
      }
    } else if (no("generateKeyPairSync")) {
      var i = ji.generateKeyPairSync("rsa", {
        modulusLength: e,
        publicExponent: t,
        publicKeyEncoding: {
          type: "spki",
          format: "pem"
        },
        privateKeyEncoding: {
          type: "pkcs8",
          format: "pem"
        }
      });
      return {
        privateKey: Be.privateKeyFromPem(i.privateKey),
        publicKey: Be.publicKeyFromPem(i.publicKey)
      };
    }
  }
  var s = Be.rsa.createKeyPairGenerationState(e, t, a);
  if (!r)
    return Be.rsa.stepKeyPairGenerationState(s, 0), s.keys;
  Vv(s, a, r);
};
Be.setRsaPublicKey = Be.rsa.setPublicKey = function(e, t) {
  var a = {
    n: e,
    e: t
  };
  return a.encrypt = function(r, n, i) {
    if (typeof n == "string" ? n = n.toUpperCase() : n === void 0 && (n = "RSAES-PKCS1-V1_5"), n === "RSAES-PKCS1-V1_5")
      n = {
        encode: function(o, u, l) {
          return _l(o, u, 2).getBytes();
        }
      };
    else if (n === "RSA-OAEP" || n === "RSAES-OAEP")
      n = {
        encode: function(o, u) {
          return me.pkcs1.encode_rsa_oaep(u, o, i);
        }
      };
    else if (["RAW", "NONE", "NULL", null].indexOf(n) !== -1)
      n = { encode: function(o) {
        return o;
      } };
    else if (typeof n == "string")
      throw new Error('Unsupported encryption scheme: "' + n + '".');
    var s = n.encode(r, a, !0);
    return Be.rsa.encrypt(s, a, !0);
  }, a.verify = function(r, n, i, s) {
    typeof i == "string" ? i = i.toUpperCase() : i === void 0 && (i = "RSASSA-PKCS1-V1_5"), s === void 0 && (s = {
      _parseAllDigestBytes: !0
    }), "_parseAllDigestBytes" in s || (s._parseAllDigestBytes = !0), i === "RSASSA-PKCS1-V1_5" ? i = {
      verify: function(u, l) {
        l = fn(l, a, !0);
        var c = L.fromDer(l, {
          parseAllBytes: s._parseAllDigestBytes
        }), f = {}, d = [];
        if (!L.validate(c, Uv, f, d)) {
          var v = new Error(
            "ASN.1 object does not contain a valid RSASSA-PKCS1-v1_5 DigestInfo value."
          );
          throw v.errors = d, v;
        }
        var p = L.derToOid(f.algorithmIdentifier);
        if (!(p === me.oids.md2 || p === me.oids.md5 || p === me.oids.sha1 || p === me.oids.sha224 || p === me.oids.sha256 || p === me.oids.sha384 || p === me.oids.sha512 || p === me.oids["sha512-224"] || p === me.oids["sha512-256"])) {
          var v = new Error(
            "Unknown RSASSA-PKCS1-v1_5 DigestAlgorithm identifier."
          );
          throw v.oid = p, v;
        }
        if ((p === me.oids.md2 || p === me.oids.md5) && !("parameters" in f))
          throw new Error(
            "ASN.1 object does not contain a valid RSASSA-PKCS1-v1_5 DigestInfo value. Missing algorithm identifer NULL parameters."
          );
        return u === f.digest;
      }
    } : (i === "NONE" || i === "NULL" || i === null) && (i = {
      verify: function(u, l) {
        return l = fn(l, a, !0), u === l;
      }
    });
    var o = Be.rsa.decrypt(n, a, !0, !1);
    return i.verify(r, o, a.n.bitLength());
  }, a;
};
Be.setRsaPrivateKey = Be.rsa.setPrivateKey = function(e, t, a, r, n, i, s, o) {
  var u = {
    n: e,
    e: t,
    d: a,
    p: r,
    q: n,
    dP: i,
    dQ: s,
    qInv: o
  };
  return u.decrypt = function(l, c, f) {
    typeof c == "string" ? c = c.toUpperCase() : c === void 0 && (c = "RSAES-PKCS1-V1_5");
    var d = Be.rsa.decrypt(l, u, !1, !1);
    if (c === "RSAES-PKCS1-V1_5")
      c = { decode: fn };
    else if (c === "RSA-OAEP" || c === "RSAES-OAEP")
      c = {
        decode: function(v, p) {
          return me.pkcs1.decode_rsa_oaep(p, v, f);
        }
      };
    else if (["RAW", "NONE", "NULL", null].indexOf(c) !== -1)
      c = { decode: function(v) {
        return v;
      } };
    else
      throw new Error('Unsupported encryption scheme: "' + c + '".');
    return c.decode(d, u, !1);
  }, u.sign = function(l, c) {
    var f = !1;
    typeof c == "string" && (c = c.toUpperCase()), c === void 0 || c === "RSASSA-PKCS1-V1_5" ? (c = { encode: Ov }, f = 1) : (c === "NONE" || c === "NULL" || c === null) && (c = { encode: function() {
      return l;
    } }, f = 1);
    var d = c.encode(l, u.n.bitLength());
    return Be.rsa.encrypt(d, u, f);
  }, u;
};
Be.wrapRsaPrivateKey = function(e) {
  return L.create(L.Class.UNIVERSAL, L.Type.SEQUENCE, !0, [
    // version (0)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      L.integerToDer(0).getBytes()
    ),
    // privateKeyAlgorithm
    L.create(L.Class.UNIVERSAL, L.Type.SEQUENCE, !0, [
      L.create(
        L.Class.UNIVERSAL,
        L.Type.OID,
        !1,
        L.oidToDer(Be.oids.rsaEncryption).getBytes()
      ),
      L.create(L.Class.UNIVERSAL, L.Type.NULL, !1, "")
    ]),
    // PrivateKey
    L.create(
      L.Class.UNIVERSAL,
      L.Type.OCTETSTRING,
      !1,
      L.toDer(e).getBytes()
    )
  ]);
};
Be.privateKeyFromAsn1 = function(e) {
  var t = {}, a = [];
  if (L.validate(e, kv, t, a) && (e = L.fromDer(me.util.createBuffer(t.privateKey))), t = {}, a = [], !L.validate(e, Fv, t, a)) {
    var r = new Error("Cannot read private key. ASN.1 object does not contain an RSAPrivateKey.");
    throw r.errors = a, r;
  }
  var n, i, s, o, u, l, c, f;
  return n = me.util.createBuffer(t.privateKeyModulus).toHex(), i = me.util.createBuffer(t.privateKeyPublicExponent).toHex(), s = me.util.createBuffer(t.privateKeyPrivateExponent).toHex(), o = me.util.createBuffer(t.privateKeyPrime1).toHex(), u = me.util.createBuffer(t.privateKeyPrime2).toHex(), l = me.util.createBuffer(t.privateKeyExponent1).toHex(), c = me.util.createBuffer(t.privateKeyExponent2).toHex(), f = me.util.createBuffer(t.privateKeyCoefficient).toHex(), Be.setRsaPrivateKey(
    new Me(n, 16),
    new Me(i, 16),
    new Me(s, 16),
    new Me(o, 16),
    new Me(u, 16),
    new Me(l, 16),
    new Me(c, 16),
    new Me(f, 16)
  );
};
Be.privateKeyToAsn1 = Be.privateKeyToRSAPrivateKey = function(e) {
  return L.create(L.Class.UNIVERSAL, L.Type.SEQUENCE, !0, [
    // version (0 = only 2 primes, 1 multiple primes)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      L.integerToDer(0).getBytes()
    ),
    // modulus (n)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.n)
    ),
    // publicExponent (e)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.e)
    ),
    // privateExponent (d)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.d)
    ),
    // privateKeyPrime1 (p)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.p)
    ),
    // privateKeyPrime2 (q)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.q)
    ),
    // privateKeyExponent1 (dP)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.dP)
    ),
    // privateKeyExponent2 (dQ)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.dQ)
    ),
    // coefficient (qInv)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.qInv)
    )
  ]);
};
Be.publicKeyFromAsn1 = function(e) {
  var t = {}, a = [];
  if (L.validate(e, Pv, t, a)) {
    var r = L.derToOid(t.publicKeyOid);
    if (r !== Be.oids.rsaEncryption) {
      var n = new Error("Cannot read public key. Unknown OID.");
      throw n.oid = r, n;
    }
    e = t.rsaPublicKey;
  }
  if (a = [], !L.validate(e, Lv, t, a)) {
    var n = new Error("Cannot read public key. ASN.1 object does not contain an RSAPublicKey.");
    throw n.errors = a, n;
  }
  var i = me.util.createBuffer(t.publicKeyModulus).toHex(), s = me.util.createBuffer(t.publicKeyExponent).toHex();
  return Be.setRsaPublicKey(
    new Me(i, 16),
    new Me(s, 16)
  );
};
Be.publicKeyToAsn1 = Be.publicKeyToSubjectPublicKeyInfo = function(e) {
  return L.create(L.Class.UNIVERSAL, L.Type.SEQUENCE, !0, [
    // AlgorithmIdentifier
    L.create(L.Class.UNIVERSAL, L.Type.SEQUENCE, !0, [
      // algorithm
      L.create(
        L.Class.UNIVERSAL,
        L.Type.OID,
        !1,
        L.oidToDer(Be.oids.rsaEncryption).getBytes()
      ),
      // parameters (null)
      L.create(L.Class.UNIVERSAL, L.Type.NULL, !1, "")
    ]),
    // subjectPublicKey
    L.create(L.Class.UNIVERSAL, L.Type.BITSTRING, !1, [
      Be.publicKeyToRSAPublicKey(e)
    ])
  ]);
};
Be.publicKeyToRSAPublicKey = function(e) {
  return L.create(L.Class.UNIVERSAL, L.Type.SEQUENCE, !0, [
    // modulus (n)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.n)
    ),
    // publicExponent (e)
    L.create(
      L.Class.UNIVERSAL,
      L.Type.INTEGER,
      !1,
      qt(e.e)
    )
  ]);
};
function _l(e, t, a) {
  var r = me.util.createBuffer(), n = Math.ceil(t.n.bitLength() / 8);
  if (e.length > n - 11) {
    var i = new Error("Message is too long for PKCS#1 v1.5 padding.");
    throw i.length = e.length, i.max = n - 11, i;
  }
  r.putByte(0), r.putByte(a);
  var s = n - 3 - e.length, o;
  if (a === 0 || a === 1) {
    o = a === 0 ? 0 : 255;
    for (var u = 0; u < s; ++u)
      r.putByte(o);
  } else
    for (; s > 0; ) {
      for (var l = 0, c = me.random.getBytes(s), u = 0; u < s; ++u)
        o = c.charCodeAt(u), o === 0 ? ++l : r.putByte(o);
      s = l;
    }
  return r.putByte(0), r.putBytes(e), r;
}
function fn(e, t, a, r) {
  var n = Math.ceil(t.n.bitLength() / 8), i = me.util.createBuffer(e), s = i.getByte(), o = i.getByte();
  if (s !== 0 || a && o !== 0 && o !== 1 || !a && o != 2 || a && o === 0 && typeof r > "u")
    throw new Error("Encryption block is invalid.");
  var u = 0;
  if (o === 0) {
    u = n - 3 - r;
    for (var l = 0; l < u; ++l)
      if (i.getByte() !== 0)
        throw new Error("Encryption block is invalid.");
  } else if (o === 1)
    for (u = 0; i.length() > 1; ) {
      if (i.getByte() !== 255) {
        --i.read;
        break;
      }
      ++u;
    }
  else if (o === 2)
    for (u = 0; i.length() > 1; ) {
      if (i.getByte() === 0) {
        --i.read;
        break;
      }
      ++u;
    }
  var c = i.getByte();
  if (c !== 0 || u !== n - 3 - i.length())
    throw new Error("Encryption block is invalid.");
  return i.getBytes();
}
function Vv(e, t, a) {
  typeof t == "function" && (a = t, t = {}), t = t || {};
  var r = {
    algorithm: {
      name: t.algorithm || "PRIMEINC",
      options: {
        workers: t.workers || 2,
        workLoad: t.workLoad || 100,
        workerScript: t.workerScript
      }
    }
  };
  "prng" in t && (r.prng = t.prng), n();
  function n() {
    i(e.pBits, function(o, u) {
      if (o)
        return a(o);
      if (e.p = u, e.q !== null)
        return s(o, e.q);
      i(e.qBits, s);
    });
  }
  function i(o, u) {
    me.prime.generateProbablePrime(o, r, u);
  }
  function s(o, u) {
    if (o)
      return a(o);
    if (e.q = u, e.p.compareTo(e.q) < 0) {
      var l = e.p;
      e.p = e.q, e.q = l;
    }
    if (e.p.subtract(Me.ONE).gcd(e.e).compareTo(Me.ONE) !== 0) {
      e.p = null, n();
      return;
    }
    if (e.q.subtract(Me.ONE).gcd(e.e).compareTo(Me.ONE) !== 0) {
      e.q = null, i(e.qBits, s);
      return;
    }
    if (e.p1 = e.p.subtract(Me.ONE), e.q1 = e.q.subtract(Me.ONE), e.phi = e.p1.multiply(e.q1), e.phi.gcd(e.e).compareTo(Me.ONE) !== 0) {
      e.p = e.q = null, n();
      return;
    }
    if (e.n = e.p.multiply(e.q), e.n.bitLength() !== e.bits) {
      e.q = null, i(e.qBits, s);
      return;
    }
    var c = e.e.modInverse(e.phi);
    e.keys = {
      privateKey: Be.rsa.setPrivateKey(
        e.n,
        e.e,
        c,
        e.p,
        e.q,
        c.mod(e.p1),
        c.mod(e.q1),
        e.q.modInverse(e.p)
      ),
      publicKey: Be.rsa.setPublicKey(e.n, e.e)
    }, a(null, e.keys);
  }
}
function qt(e) {
  var t = e.toString(16);
  t[0] >= "8" && (t = "00" + t);
  var a = me.util.hexToBytes(t);
  return a.length > 1 && // leading 0x00 for positive integer
  (a.charCodeAt(0) === 0 && !(a.charCodeAt(1) & 128) || // leading 0xFF for negative integer
  a.charCodeAt(0) === 255 && (a.charCodeAt(1) & 128) === 128) ? a.substr(1) : a;
}
function Mv(e) {
  return e <= 100 ? 27 : e <= 150 ? 18 : e <= 200 ? 15 : e <= 250 ? 12 : e <= 300 ? 9 : e <= 350 ? 8 : e <= 400 ? 7 : e <= 500 ? 6 : e <= 600 ? 5 : e <= 800 ? 4 : e <= 1250 ? 3 : 2;
}
function no(e) {
  return me.util.isNodejs && typeof ji[e] == "function";
}
function io(e) {
  return typeof Pt.globalScope < "u" && typeof Pt.globalScope.crypto == "object" && typeof Pt.globalScope.crypto.subtle == "object" && typeof Pt.globalScope.crypto.subtle[e] == "function";
}
function so(e) {
  return typeof Pt.globalScope < "u" && typeof Pt.globalScope.msCrypto == "object" && typeof Pt.globalScope.msCrypto.subtle == "object" && typeof Pt.globalScope.msCrypto.subtle[e] == "function";
}
function oo(e) {
  for (var t = me.util.hexToBytes(e.toString(16)), a = new Uint8Array(t.length), r = 0; r < t.length; ++r)
    a[r] = t.charCodeAt(r);
  return a;
}
var oe = Re;
if (typeof Kv > "u")
  var Kv = oe.jsbn.BigInteger;
var V = oe.asn1, Ie = oe.pki = oe.pki || {};
Ie.pbe = oe.pbe = oe.pbe || {};
var _r = Ie.oids, Hv = {
  name: "EncryptedPrivateKeyInfo",
  tagClass: V.Class.UNIVERSAL,
  type: V.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "EncryptedPrivateKeyInfo.encryptionAlgorithm",
    tagClass: V.Class.UNIVERSAL,
    type: V.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "AlgorithmIdentifier.algorithm",
      tagClass: V.Class.UNIVERSAL,
      type: V.Type.OID,
      constructed: !1,
      capture: "encryptionOid"
    }, {
      name: "AlgorithmIdentifier.parameters",
      tagClass: V.Class.UNIVERSAL,
      type: V.Type.SEQUENCE,
      constructed: !0,
      captureAsn1: "encryptionParams"
    }]
  }, {
    // encryptedData
    name: "EncryptedPrivateKeyInfo.encryptedData",
    tagClass: V.Class.UNIVERSAL,
    type: V.Type.OCTETSTRING,
    constructed: !1,
    capture: "encryptedData"
  }]
}, zv = {
  name: "PBES2Algorithms",
  tagClass: V.Class.UNIVERSAL,
  type: V.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "PBES2Algorithms.keyDerivationFunc",
    tagClass: V.Class.UNIVERSAL,
    type: V.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "PBES2Algorithms.keyDerivationFunc.oid",
      tagClass: V.Class.UNIVERSAL,
      type: V.Type.OID,
      constructed: !1,
      capture: "kdfOid"
    }, {
      name: "PBES2Algorithms.params",
      tagClass: V.Class.UNIVERSAL,
      type: V.Type.SEQUENCE,
      constructed: !0,
      value: [{
        name: "PBES2Algorithms.params.salt",
        tagClass: V.Class.UNIVERSAL,
        type: V.Type.OCTETSTRING,
        constructed: !1,
        capture: "kdfSalt"
      }, {
        name: "PBES2Algorithms.params.iterationCount",
        tagClass: V.Class.UNIVERSAL,
        type: V.Type.INTEGER,
        constructed: !1,
        capture: "kdfIterationCount"
      }, {
        name: "PBES2Algorithms.params.keyLength",
        tagClass: V.Class.UNIVERSAL,
        type: V.Type.INTEGER,
        constructed: !1,
        optional: !0,
        capture: "keyLength"
      }, {
        // prf
        name: "PBES2Algorithms.params.prf",
        tagClass: V.Class.UNIVERSAL,
        type: V.Type.SEQUENCE,
        constructed: !0,
        optional: !0,
        value: [{
          name: "PBES2Algorithms.params.prf.algorithm",
          tagClass: V.Class.UNIVERSAL,
          type: V.Type.OID,
          constructed: !1,
          capture: "prfOid"
        }]
      }]
    }]
  }, {
    name: "PBES2Algorithms.encryptionScheme",
    tagClass: V.Class.UNIVERSAL,
    type: V.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "PBES2Algorithms.encryptionScheme.oid",
      tagClass: V.Class.UNIVERSAL,
      type: V.Type.OID,
      constructed: !1,
      capture: "encOid"
    }, {
      name: "PBES2Algorithms.encryptionScheme.iv",
      tagClass: V.Class.UNIVERSAL,
      type: V.Type.OCTETSTRING,
      constructed: !1,
      capture: "encIv"
    }]
  }]
}, $v = {
  name: "pkcs-12PbeParams",
  tagClass: V.Class.UNIVERSAL,
  type: V.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "pkcs-12PbeParams.salt",
    tagClass: V.Class.UNIVERSAL,
    type: V.Type.OCTETSTRING,
    constructed: !1,
    capture: "salt"
  }, {
    name: "pkcs-12PbeParams.iterations",
    tagClass: V.Class.UNIVERSAL,
    type: V.Type.INTEGER,
    constructed: !1,
    capture: "iterations"
  }]
};
Ie.encryptPrivateKeyInfo = function(e, t, a) {
  a = a || {}, a.saltSize = a.saltSize || 8, a.count = a.count || 2048, a.algorithm = a.algorithm || "aes128", a.prfAlgorithm = a.prfAlgorithm || "sha1";
  var r = oe.random.getBytesSync(a.saltSize), n = a.count, i = V.integerToDer(n), s, o, u;
  if (a.algorithm.indexOf("aes") === 0 || a.algorithm === "des") {
    var l, c, f;
    switch (a.algorithm) {
      case "aes128":
        s = 16, l = 16, c = _r["aes128-CBC"], f = oe.aes.createEncryptionCipher;
        break;
      case "aes192":
        s = 24, l = 16, c = _r["aes192-CBC"], f = oe.aes.createEncryptionCipher;
        break;
      case "aes256":
        s = 32, l = 16, c = _r["aes256-CBC"], f = oe.aes.createEncryptionCipher;
        break;
      case "des":
        s = 8, l = 8, c = _r.desCBC, f = oe.des.createEncryptionCipher;
        break;
      default:
        var d = new Error("Cannot encrypt private key. Unknown encryption algorithm.");
        throw d.algorithm = a.algorithm, d;
    }
    var v = "hmacWith" + a.prfAlgorithm.toUpperCase(), p = Tl(v), h = oe.pkcs5.pbkdf2(t, r, n, s, p), x = oe.random.getBytesSync(l), y = f(h);
    y.start(x), y.update(V.toDer(e)), y.finish(), u = y.output.getBytes();
    var g = qv(r, i, s, v);
    o = V.create(
      V.Class.UNIVERSAL,
      V.Type.SEQUENCE,
      !0,
      [
        V.create(
          V.Class.UNIVERSAL,
          V.Type.OID,
          !1,
          V.oidToDer(_r.pkcs5PBES2).getBytes()
        ),
        V.create(V.Class.UNIVERSAL, V.Type.SEQUENCE, !0, [
          // keyDerivationFunc
          V.create(V.Class.UNIVERSAL, V.Type.SEQUENCE, !0, [
            V.create(
              V.Class.UNIVERSAL,
              V.Type.OID,
              !1,
              V.oidToDer(_r.pkcs5PBKDF2).getBytes()
            ),
            // PBKDF2-params
            g
          ]),
          // encryptionScheme
          V.create(V.Class.UNIVERSAL, V.Type.SEQUENCE, !0, [
            V.create(
              V.Class.UNIVERSAL,
              V.Type.OID,
              !1,
              V.oidToDer(c).getBytes()
            ),
            // iv
            V.create(
              V.Class.UNIVERSAL,
              V.Type.OCTETSTRING,
              !1,
              x
            )
          ])
        ])
      ]
    );
  } else if (a.algorithm === "3des") {
    s = 24;
    var m = new oe.util.ByteBuffer(r), h = Ie.pbe.generatePkcs12Key(t, m, 1, n, s), x = Ie.pbe.generatePkcs12Key(t, m, 2, n, s), y = oe.des.createEncryptionCipher(h);
    y.start(x), y.update(V.toDer(e)), y.finish(), u = y.output.getBytes(), o = V.create(
      V.Class.UNIVERSAL,
      V.Type.SEQUENCE,
      !0,
      [
        V.create(
          V.Class.UNIVERSAL,
          V.Type.OID,
          !1,
          V.oidToDer(_r["pbeWithSHAAnd3-KeyTripleDES-CBC"]).getBytes()
        ),
        // pkcs-12PbeParams
        V.create(V.Class.UNIVERSAL, V.Type.SEQUENCE, !0, [
          // salt
          V.create(V.Class.UNIVERSAL, V.Type.OCTETSTRING, !1, r),
          // iteration count
          V.create(
            V.Class.UNIVERSAL,
            V.Type.INTEGER,
            !1,
            i.getBytes()
          )
        ])
      ]
    );
  } else {
    var d = new Error("Cannot encrypt private key. Unknown encryption algorithm.");
    throw d.algorithm = a.algorithm, d;
  }
  var C = V.create(V.Class.UNIVERSAL, V.Type.SEQUENCE, !0, [
    // encryptionAlgorithm
    o,
    // encryptedData
    V.create(
      V.Class.UNIVERSAL,
      V.Type.OCTETSTRING,
      !1,
      u
    )
  ]);
  return C;
};
Ie.decryptPrivateKeyInfo = function(e, t) {
  var a = null, r = {}, n = [];
  if (!V.validate(e, Hv, r, n)) {
    var i = new Error("Cannot read encrypted private key. ASN.1 object is not a supported EncryptedPrivateKeyInfo.");
    throw i.errors = n, i;
  }
  var s = V.derToOid(r.encryptionOid), o = Ie.pbe.getCipher(s, r.encryptionParams, t), u = oe.util.createBuffer(r.encryptedData);
  return o.update(u), o.finish() && (a = V.fromDer(o.output)), a;
};
Ie.encryptedPrivateKeyToPem = function(e, t) {
  var a = {
    type: "ENCRYPTED PRIVATE KEY",
    body: V.toDer(e).getBytes()
  };
  return oe.pem.encode(a, { maxline: t });
};
Ie.encryptedPrivateKeyFromPem = function(e) {
  var t = oe.pem.decode(e)[0];
  if (t.type !== "ENCRYPTED PRIVATE KEY") {
    var a = new Error('Could not convert encrypted private key from PEM; PEM header type is "ENCRYPTED PRIVATE KEY".');
    throw a.headerType = t.type, a;
  }
  if (t.procType && t.procType.type === "ENCRYPTED")
    throw new Error("Could not convert encrypted private key from PEM; PEM is encrypted.");
  return V.fromDer(t.body);
};
Ie.encryptRsaPrivateKey = function(e, t, a) {
  if (a = a || {}, !a.legacy) {
    var r = Ie.wrapRsaPrivateKey(Ie.privateKeyToAsn1(e));
    return r = Ie.encryptPrivateKeyInfo(r, t, a), Ie.encryptedPrivateKeyToPem(r);
  }
  var n, i, s, o;
  switch (a.algorithm) {
    case "aes128":
      n = "AES-128-CBC", s = 16, i = oe.random.getBytesSync(16), o = oe.aes.createEncryptionCipher;
      break;
    case "aes192":
      n = "AES-192-CBC", s = 24, i = oe.random.getBytesSync(16), o = oe.aes.createEncryptionCipher;
      break;
    case "aes256":
      n = "AES-256-CBC", s = 32, i = oe.random.getBytesSync(16), o = oe.aes.createEncryptionCipher;
      break;
    case "3des":
      n = "DES-EDE3-CBC", s = 24, i = oe.random.getBytesSync(8), o = oe.des.createEncryptionCipher;
      break;
    case "des":
      n = "DES-CBC", s = 8, i = oe.random.getBytesSync(8), o = oe.des.createEncryptionCipher;
      break;
    default:
      var u = new Error('Could not encrypt RSA private key; unsupported encryption algorithm "' + a.algorithm + '".');
      throw u.algorithm = a.algorithm, u;
  }
  var l = oe.pbe.opensslDeriveBytes(t, i.substr(0, 8), s), c = o(l);
  c.start(i), c.update(V.toDer(Ie.privateKeyToAsn1(e))), c.finish();
  var f = {
    type: "RSA PRIVATE KEY",
    procType: {
      version: "4",
      type: "ENCRYPTED"
    },
    dekInfo: {
      algorithm: n,
      parameters: oe.util.bytesToHex(i).toUpperCase()
    },
    body: c.output.getBytes()
  };
  return oe.pem.encode(f);
};
Ie.decryptRsaPrivateKey = function(e, t) {
  var a = null, r = oe.pem.decode(e)[0];
  if (r.type !== "ENCRYPTED PRIVATE KEY" && r.type !== "PRIVATE KEY" && r.type !== "RSA PRIVATE KEY") {
    var n = new Error('Could not convert private key from PEM; PEM header type is not "ENCRYPTED PRIVATE KEY", "PRIVATE KEY", or "RSA PRIVATE KEY".');
    throw n.headerType = n, n;
  }
  if (r.procType && r.procType.type === "ENCRYPTED") {
    var i, s;
    switch (r.dekInfo.algorithm) {
      case "DES-CBC":
        i = 8, s = oe.des.createDecryptionCipher;
        break;
      case "DES-EDE3-CBC":
        i = 24, s = oe.des.createDecryptionCipher;
        break;
      case "AES-128-CBC":
        i = 16, s = oe.aes.createDecryptionCipher;
        break;
      case "AES-192-CBC":
        i = 24, s = oe.aes.createDecryptionCipher;
        break;
      case "AES-256-CBC":
        i = 32, s = oe.aes.createDecryptionCipher;
        break;
      case "RC2-40-CBC":
        i = 5, s = function(f) {
          return oe.rc2.createDecryptionCipher(f, 40);
        };
        break;
      case "RC2-64-CBC":
        i = 8, s = function(f) {
          return oe.rc2.createDecryptionCipher(f, 64);
        };
        break;
      case "RC2-128-CBC":
        i = 16, s = function(f) {
          return oe.rc2.createDecryptionCipher(f, 128);
        };
        break;
      default:
        var n = new Error('Could not decrypt private key; unsupported encryption algorithm "' + r.dekInfo.algorithm + '".');
        throw n.algorithm = r.dekInfo.algorithm, n;
    }
    var o = oe.util.hexToBytes(r.dekInfo.parameters), u = oe.pbe.opensslDeriveBytes(t, o.substr(0, 8), i), l = s(u);
    if (l.start(o), l.update(oe.util.createBuffer(r.body)), l.finish())
      a = l.output.getBytes();
    else
      return a;
  } else
    a = r.body;
  return r.type === "ENCRYPTED PRIVATE KEY" ? a = Ie.decryptPrivateKeyInfo(V.fromDer(a), t) : a = V.fromDer(a), a !== null && (a = Ie.privateKeyFromAsn1(a)), a;
};
Ie.pbe.generatePkcs12Key = function(e, t, a, r, n, i) {
  var s, o;
  if (typeof i > "u" || i === null) {
    if (!("sha1" in oe.md))
      throw new Error('"sha1" hash algorithm unavailable.');
    i = oe.md.sha1.create();
  }
  var u = i.digestLength, l = i.blockLength, c = new oe.util.ByteBuffer(), f = new oe.util.ByteBuffer();
  if (e != null) {
    for (o = 0; o < e.length; o++)
      f.putInt16(e.charCodeAt(o));
    f.putInt16(0);
  }
  var d = f.length(), v = t.length(), p = new oe.util.ByteBuffer();
  p.fillWithByte(a, l);
  var h = l * Math.ceil(v / l), x = new oe.util.ByteBuffer();
  for (o = 0; o < h; o++)
    x.putByte(t.at(o % v));
  var y = l * Math.ceil(d / l), g = new oe.util.ByteBuffer();
  for (o = 0; o < y; o++)
    g.putByte(f.at(o % d));
  var m = x;
  m.putBuffer(g);
  for (var C = Math.ceil(n / u), S = 1; S <= C; S++) {
    var _ = new oe.util.ByteBuffer();
    _.putBytes(p.bytes()), _.putBytes(m.bytes());
    for (var T = 0; T < r; T++)
      i.start(), i.update(_.getBytes()), _ = i.digest();
    var D = new oe.util.ByteBuffer();
    for (o = 0; o < l; o++)
      D.putByte(_.at(o % u));
    var P = Math.ceil(v / l) + Math.ceil(d / l), I = new oe.util.ByteBuffer();
    for (s = 0; s < P; s++) {
      var B = new oe.util.ByteBuffer(m.getBytes(l)), R = 511;
      for (o = D.length() - 1; o >= 0; o--)
        R = R >> 8, R += D.at(o) + B.at(o), B.setAt(o, R & 255);
      I.putBuffer(B);
    }
    m = I, c.putBuffer(_);
  }
  return c.truncate(c.length() - n), c;
};
Ie.pbe.getCipher = function(e, t, a) {
  switch (e) {
    case Ie.oids.pkcs5PBES2:
      return Ie.pbe.getCipherForPBES2(e, t, a);
    case Ie.oids["pbeWithSHAAnd3-KeyTripleDES-CBC"]:
    case Ie.oids["pbewithSHAAnd40BitRC2-CBC"]:
      return Ie.pbe.getCipherForPKCS12PBE(e, t, a);
    default:
      var r = new Error("Cannot read encrypted PBE data block. Unsupported OID.");
      throw r.oid = e, r.supportedOids = [
        "pkcs5PBES2",
        "pbeWithSHAAnd3-KeyTripleDES-CBC",
        "pbewithSHAAnd40BitRC2-CBC"
      ], r;
  }
};
Ie.pbe.getCipherForPBES2 = function(e, t, a) {
  var r = {}, n = [];
  if (!V.validate(t, zv, r, n)) {
    var i = new Error("Cannot read password-based-encryption algorithm parameters. ASN.1 object is not a supported EncryptedPrivateKeyInfo.");
    throw i.errors = n, i;
  }
  if (e = V.derToOid(r.kdfOid), e !== Ie.oids.pkcs5PBKDF2) {
    var i = new Error("Cannot read encrypted private key. Unsupported key derivation function OID.");
    throw i.oid = e, i.supportedOids = ["pkcs5PBKDF2"], i;
  }
  if (e = V.derToOid(r.encOid), e !== Ie.oids["aes128-CBC"] && e !== Ie.oids["aes192-CBC"] && e !== Ie.oids["aes256-CBC"] && e !== Ie.oids["des-EDE3-CBC"] && e !== Ie.oids.desCBC) {
    var i = new Error("Cannot read encrypted private key. Unsupported encryption scheme OID.");
    throw i.oid = e, i.supportedOids = [
      "aes128-CBC",
      "aes192-CBC",
      "aes256-CBC",
      "des-EDE3-CBC",
      "desCBC"
    ], i;
  }
  var s = r.kdfSalt, o = oe.util.createBuffer(r.kdfIterationCount);
  o = o.getInt(o.length() << 3);
  var u, l;
  switch (Ie.oids[e]) {
    case "aes128-CBC":
      u = 16, l = oe.aes.createDecryptionCipher;
      break;
    case "aes192-CBC":
      u = 24, l = oe.aes.createDecryptionCipher;
      break;
    case "aes256-CBC":
      u = 32, l = oe.aes.createDecryptionCipher;
      break;
    case "des-EDE3-CBC":
      u = 24, l = oe.des.createDecryptionCipher;
      break;
    case "desCBC":
      u = 8, l = oe.des.createDecryptionCipher;
      break;
  }
  var c = Bl(r.prfOid), f = oe.pkcs5.pbkdf2(a, s, o, u, c), d = r.encIv, v = l(f);
  return v.start(d), v;
};
Ie.pbe.getCipherForPKCS12PBE = function(e, t, a) {
  var r = {}, n = [];
  if (!V.validate(t, $v, r, n)) {
    var i = new Error("Cannot read password-based-encryption algorithm parameters. ASN.1 object is not a supported EncryptedPrivateKeyInfo.");
    throw i.errors = n, i;
  }
  var s = oe.util.createBuffer(r.salt), o = oe.util.createBuffer(r.iterations);
  o = o.getInt(o.length() << 3);
  var u, l, c;
  switch (e) {
    case Ie.oids["pbeWithSHAAnd3-KeyTripleDES-CBC"]:
      u = 24, l = 8, c = oe.des.startDecrypting;
      break;
    case Ie.oids["pbewithSHAAnd40BitRC2-CBC"]:
      u = 5, l = 8, c = function(h, x) {
        var y = oe.rc2.createDecryptionCipher(h, 40);
        return y.start(x, null), y;
      };
      break;
    default:
      var i = new Error("Cannot read PKCS #12 PBE data block. Unsupported OID.");
      throw i.oid = e, i;
  }
  var f = Bl(r.prfOid), d = Ie.pbe.generatePkcs12Key(a, s, 1, o, u, f);
  f.start();
  var v = Ie.pbe.generatePkcs12Key(a, s, 2, o, l, f);
  return c(d, v);
};
Ie.pbe.opensslDeriveBytes = function(e, t, a, r) {
  if (typeof r > "u" || r === null) {
    if (!("md5" in oe.md))
      throw new Error('"md5" hash algorithm unavailable.');
    r = oe.md.md5.create();
  }
  t === null && (t = "");
  for (var n = [lo(r, e + t)], i = 16, s = 1; i < a; ++s, i += 16)
    n.push(lo(r, n[s - 1] + e + t));
  return n.join("").substr(0, a);
};
function lo(e, t) {
  return e.start().update(t).digest().getBytes();
}
function Bl(e) {
  var t;
  if (!e)
    t = "hmacWithSHA1";
  else if (t = Ie.oids[V.derToOid(e)], !t) {
    var a = new Error("Unsupported PRF OID.");
    throw a.oid = e, a.supported = [
      "hmacWithSHA1",
      "hmacWithSHA224",
      "hmacWithSHA256",
      "hmacWithSHA384",
      "hmacWithSHA512"
    ], a;
  }
  return Tl(t);
}
function Tl(e) {
  var t = oe.md;
  switch (e) {
    case "hmacWithSHA224":
      t = oe.md.sha512;
    case "hmacWithSHA1":
    case "hmacWithSHA256":
    case "hmacWithSHA384":
    case "hmacWithSHA512":
      e = e.substr(8).toLowerCase();
      break;
    default:
      var a = new Error("Unsupported PRF algorithm.");
      throw a.algorithm = e, a.supported = [
        "hmacWithSHA1",
        "hmacWithSHA224",
        "hmacWithSHA256",
        "hmacWithSHA384",
        "hmacWithSHA512"
      ], a;
  }
  if (!t || !(e in t))
    throw new Error("Unknown hash algorithm: " + e);
  return t[e].create();
}
function qv(e, t, a, r) {
  var n = V.create(V.Class.UNIVERSAL, V.Type.SEQUENCE, !0, [
    // salt
    V.create(
      V.Class.UNIVERSAL,
      V.Type.OCTETSTRING,
      !1,
      e
    ),
    // iteration count
    V.create(
      V.Class.UNIVERSAL,
      V.Type.INTEGER,
      !1,
      t.getBytes()
    )
  ]);
  return r !== "hmacWithSHA1" && n.value.push(
    // key length
    V.create(
      V.Class.UNIVERSAL,
      V.Type.INTEGER,
      !1,
      oe.util.hexToBytes(a.toString(16))
    ),
    // AlgorithmIdentifier
    V.create(V.Class.UNIVERSAL, V.Type.SEQUENCE, !0, [
      // algorithm
      V.create(
        V.Class.UNIVERSAL,
        V.Type.OID,
        !1,
        V.oidToDer(Ie.oids[r]).getBytes()
      ),
      // parameters (null)
      V.create(V.Class.UNIVERSAL, V.Type.NULL, !1, "")
    ])
  ), n;
}
var sa = Re, ie = sa.asn1, va = sa.pkcs7asn1 = sa.pkcs7asn1 || {};
sa.pkcs7 = sa.pkcs7 || {};
sa.pkcs7.asn1 = va;
var Il = {
  name: "ContentInfo",
  tagClass: ie.Class.UNIVERSAL,
  type: ie.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "ContentInfo.ContentType",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.OID,
    constructed: !1,
    capture: "contentType"
  }, {
    name: "ContentInfo.content",
    tagClass: ie.Class.CONTEXT_SPECIFIC,
    type: 0,
    constructed: !0,
    optional: !0,
    captureAsn1: "content"
  }]
};
va.contentInfoValidator = Il;
var wl = {
  name: "EncryptedContentInfo",
  tagClass: ie.Class.UNIVERSAL,
  type: ie.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "EncryptedContentInfo.contentType",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.OID,
    constructed: !1,
    capture: "contentType"
  }, {
    name: "EncryptedContentInfo.contentEncryptionAlgorithm",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "EncryptedContentInfo.contentEncryptionAlgorithm.algorithm",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.OID,
      constructed: !1,
      capture: "encAlgorithm"
    }, {
      name: "EncryptedContentInfo.contentEncryptionAlgorithm.parameter",
      tagClass: ie.Class.UNIVERSAL,
      captureAsn1: "encParameter"
    }]
  }, {
    name: "EncryptedContentInfo.encryptedContent",
    tagClass: ie.Class.CONTEXT_SPECIFIC,
    type: 0,
    /* The PKCS#7 structure output by OpenSSL somewhat differs from what
     * other implementations do generate.
     *
     * OpenSSL generates a structure like this:
     * SEQUENCE {
     *    ...
     *    [0]
     *       26 DA 67 D2 17 9C 45 3C B1 2A A8 59 2F 29 33 38
     *       C3 C3 DF 86 71 74 7A 19 9F 40 D0 29 BE 85 90 45
     *       ...
     * }
     *
     * Whereas other implementations (and this PKCS#7 module) generate:
     * SEQUENCE {
     *    ...
     *    [0] {
     *       OCTET STRING
     *          26 DA 67 D2 17 9C 45 3C B1 2A A8 59 2F 29 33 38
     *          C3 C3 DF 86 71 74 7A 19 9F 40 D0 29 BE 85 90 45
     *          ...
     *    }
     * }
     *
     * In order to support both, we just capture the context specific
     * field here.  The OCTET STRING bit is removed below.
     */
    capture: "encryptedContent",
    captureAsn1: "encryptedContentAsn1"
  }]
};
va.envelopedDataValidator = {
  name: "EnvelopedData",
  tagClass: ie.Class.UNIVERSAL,
  type: ie.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "EnvelopedData.Version",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.INTEGER,
    constructed: !1,
    capture: "version"
  }, {
    name: "EnvelopedData.RecipientInfos",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.SET,
    constructed: !0,
    captureAsn1: "recipientInfos"
  }].concat(wl)
};
va.encryptedDataValidator = {
  name: "EncryptedData",
  tagClass: ie.Class.UNIVERSAL,
  type: ie.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "EncryptedData.Version",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.INTEGER,
    constructed: !1,
    capture: "version"
  }].concat(wl)
};
var jv = {
  name: "SignerInfo",
  tagClass: ie.Class.UNIVERSAL,
  type: ie.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "SignerInfo.version",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.INTEGER,
    constructed: !1
  }, {
    name: "SignerInfo.issuerAndSerialNumber",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "SignerInfo.issuerAndSerialNumber.issuer",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.SEQUENCE,
      constructed: !0,
      captureAsn1: "issuer"
    }, {
      name: "SignerInfo.issuerAndSerialNumber.serialNumber",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.INTEGER,
      constructed: !1,
      capture: "serial"
    }]
  }, {
    name: "SignerInfo.digestAlgorithm",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "SignerInfo.digestAlgorithm.algorithm",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.OID,
      constructed: !1,
      capture: "digestAlgorithm"
    }, {
      name: "SignerInfo.digestAlgorithm.parameter",
      tagClass: ie.Class.UNIVERSAL,
      constructed: !1,
      captureAsn1: "digestParameter",
      optional: !0
    }]
  }, {
    name: "SignerInfo.authenticatedAttributes",
    tagClass: ie.Class.CONTEXT_SPECIFIC,
    type: 0,
    constructed: !0,
    optional: !0,
    capture: "authenticatedAttributes"
  }, {
    name: "SignerInfo.digestEncryptionAlgorithm",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.SEQUENCE,
    constructed: !0,
    capture: "signatureAlgorithm"
  }, {
    name: "SignerInfo.encryptedDigest",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.OCTETSTRING,
    constructed: !1,
    capture: "signature"
  }, {
    name: "SignerInfo.unauthenticatedAttributes",
    tagClass: ie.Class.CONTEXT_SPECIFIC,
    type: 1,
    constructed: !0,
    optional: !0,
    capture: "unauthenticatedAttributes"
  }]
};
va.signedDataValidator = {
  name: "SignedData",
  tagClass: ie.Class.UNIVERSAL,
  type: ie.Type.SEQUENCE,
  constructed: !0,
  value: [
    {
      name: "SignedData.Version",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.INTEGER,
      constructed: !1,
      capture: "version"
    },
    {
      name: "SignedData.DigestAlgorithms",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.SET,
      constructed: !0,
      captureAsn1: "digestAlgorithms"
    },
    Il,
    {
      name: "SignedData.Certificates",
      tagClass: ie.Class.CONTEXT_SPECIFIC,
      type: 0,
      optional: !0,
      captureAsn1: "certificates"
    },
    {
      name: "SignedData.CertificateRevocationLists",
      tagClass: ie.Class.CONTEXT_SPECIFIC,
      type: 1,
      optional: !0,
      captureAsn1: "crls"
    },
    {
      name: "SignedData.SignerInfos",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.SET,
      capture: "signerInfos",
      optional: !0,
      value: [jv]
    }
  ]
};
va.recipientInfoValidator = {
  name: "RecipientInfo",
  tagClass: ie.Class.UNIVERSAL,
  type: ie.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "RecipientInfo.version",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.INTEGER,
    constructed: !1,
    capture: "version"
  }, {
    name: "RecipientInfo.issuerAndSerial",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "RecipientInfo.issuerAndSerial.issuer",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.SEQUENCE,
      constructed: !0,
      captureAsn1: "issuer"
    }, {
      name: "RecipientInfo.issuerAndSerial.serialNumber",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.INTEGER,
      constructed: !1,
      capture: "serial"
    }]
  }, {
    name: "RecipientInfo.keyEncryptionAlgorithm",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "RecipientInfo.keyEncryptionAlgorithm.algorithm",
      tagClass: ie.Class.UNIVERSAL,
      type: ie.Type.OID,
      constructed: !1,
      capture: "encAlgorithm"
    }, {
      name: "RecipientInfo.keyEncryptionAlgorithm.parameter",
      tagClass: ie.Class.UNIVERSAL,
      constructed: !1,
      captureAsn1: "encParameter",
      optional: !0
    }]
  }, {
    name: "RecipientInfo.encryptedKey",
    tagClass: ie.Class.UNIVERSAL,
    type: ie.Type.OCTETSTRING,
    constructed: !1,
    capture: "encKey"
  }]
};
var Fr = Re;
Fr.mgf = Fr.mgf || {};
var Gv = Fr.mgf.mgf1 = Fr.mgf1 = Fr.mgf1 || {};
Gv.create = function(e) {
  var t = {
    /**
     * Generate mask of specified length.
     *
     * @param {String} seed The seed for mask generation.
     * @param maskLen Number of bytes to generate.
     * @return {String} The generated mask.
     */
    generate: function(a, r) {
      for (var n = new Fr.util.ByteBuffer(), i = Math.ceil(r / e.digestLength), s = 0; s < i; s++) {
        var o = new Fr.util.ByteBuffer();
        o.putInt32(s), e.start(), e.update(a + o.getBytes()), n.putBuffer(e.digest());
      }
      return n.truncate(n.length() - r), n.getBytes();
    }
  };
  return t;
};
var dn = Re;
dn.mgf = dn.mgf || {};
dn.mgf.mgf1 = dn.mgf1;
var Ir = Re, Wv = Ir.pss = Ir.pss || {};
Wv.create = function(e) {
  arguments.length === 3 && (e = {
    md: arguments[0],
    mgf: arguments[1],
    saltLength: arguments[2]
  });
  var t = e.md, a = e.mgf, r = t.digestLength, n = e.salt || null;
  typeof n == "string" && (n = Ir.util.createBuffer(n));
  var i;
  if ("saltLength" in e)
    i = e.saltLength;
  else if (n !== null)
    i = n.length();
  else
    throw new Error("Salt length not specified or specific salt not given.");
  if (n !== null && n.length() !== i)
    throw new Error("Given salt length does not match length of given salt.");
  var s = e.prng || Ir.random, o = {};
  return o.encode = function(u, l) {
    var c, f = l - 1, d = Math.ceil(f / 8), v = u.digest().getBytes();
    if (d < r + i + 2)
      throw new Error("Message is too long to encrypt.");
    var p;
    n === null ? p = s.getBytesSync(i) : p = n.bytes();
    var h = new Ir.util.ByteBuffer();
    h.fillWithByte(0, 8), h.putBytes(v), h.putBytes(p), t.start(), t.update(h.getBytes());
    var x = t.digest().getBytes(), y = new Ir.util.ByteBuffer();
    y.fillWithByte(0, d - i - r - 2), y.putByte(1), y.putBytes(p);
    var g = y.getBytes(), m = d - r - 1, C = a.generate(x, m), S = "";
    for (c = 0; c < m; c++)
      S += String.fromCharCode(g.charCodeAt(c) ^ C.charCodeAt(c));
    var _ = 65280 >> 8 * d - f & 255;
    return S = String.fromCharCode(S.charCodeAt(0) & ~_) + S.substr(1), S + x + "¼";
  }, o.verify = function(u, l, c) {
    var f, d = c - 1, v = Math.ceil(d / 8);
    if (l = l.substr(-v), v < r + i + 2)
      throw new Error("Inconsistent parameters to PSS signature verification.");
    if (l.charCodeAt(v - 1) !== 188)
      throw new Error("Encoded message does not end in 0xBC.");
    var p = v - r - 1, h = l.substr(0, p), x = l.substr(p, r), y = 65280 >> 8 * v - d & 255;
    if (h.charCodeAt(0) & y)
      throw new Error("Bits beyond keysize not zero as expected.");
    var g = a.generate(x, p), m = "";
    for (f = 0; f < p; f++)
      m += String.fromCharCode(h.charCodeAt(f) ^ g.charCodeAt(f));
    m = String.fromCharCode(m.charCodeAt(0) & ~y) + m.substr(1);
    var C = v - r - i - 2;
    for (f = 0; f < C; f++)
      if (m.charCodeAt(f) !== 0)
        throw new Error("Leftmost octets not zero as expected");
    if (m.charCodeAt(C) !== 1)
      throw new Error("Inconsistent PSS signature, 0x01 marker not found");
    var S = m.substr(-i), _ = new Ir.util.ByteBuffer();
    _.fillWithByte(0, 8), _.putBytes(u), _.putBytes(S), t.start(), t.update(_.getBytes());
    var T = t.digest().getBytes();
    return x === T;
  }, o;
};
var Ce = Re, E = Ce.asn1, J = Ce.pki = Ce.pki || {}, Ve = J.oids, at = {};
at.CN = Ve.commonName;
at.commonName = "CN";
at.C = Ve.countryName;
at.countryName = "C";
at.L = Ve.localityName;
at.localityName = "L";
at.ST = Ve.stateOrProvinceName;
at.stateOrProvinceName = "ST";
at.O = Ve.organizationName;
at.organizationName = "O";
at.OU = Ve.organizationalUnitName;
at.organizationalUnitName = "OU";
at.E = Ve.emailAddress;
at.emailAddress = "E";
var Dl = Ce.pki.rsa.publicKeyValidator, Yv = {
  name: "Certificate",
  tagClass: E.Class.UNIVERSAL,
  type: E.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "Certificate.TBSCertificate",
    tagClass: E.Class.UNIVERSAL,
    type: E.Type.SEQUENCE,
    constructed: !0,
    captureAsn1: "tbsCertificate",
    value: [
      {
        name: "Certificate.TBSCertificate.version",
        tagClass: E.Class.CONTEXT_SPECIFIC,
        type: 0,
        constructed: !0,
        optional: !0,
        value: [{
          name: "Certificate.TBSCertificate.version.integer",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.INTEGER,
          constructed: !1,
          capture: "certVersion"
        }]
      },
      {
        name: "Certificate.TBSCertificate.serialNumber",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.INTEGER,
        constructed: !1,
        capture: "certSerialNumber"
      },
      {
        name: "Certificate.TBSCertificate.signature",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.SEQUENCE,
        constructed: !0,
        value: [{
          name: "Certificate.TBSCertificate.signature.algorithm",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.OID,
          constructed: !1,
          capture: "certinfoSignatureOid"
        }, {
          name: "Certificate.TBSCertificate.signature.parameters",
          tagClass: E.Class.UNIVERSAL,
          optional: !0,
          captureAsn1: "certinfoSignatureParams"
        }]
      },
      {
        name: "Certificate.TBSCertificate.issuer",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.SEQUENCE,
        constructed: !0,
        captureAsn1: "certIssuer"
      },
      {
        name: "Certificate.TBSCertificate.validity",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.SEQUENCE,
        constructed: !0,
        // Note: UTC and generalized times may both appear so the capture
        // names are based on their detected order, the names used below
        // are only for the common case, which validity time really means
        // "notBefore" and which means "notAfter" will be determined by order
        value: [{
          // notBefore (Time) (UTC time case)
          name: "Certificate.TBSCertificate.validity.notBefore (utc)",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.UTCTIME,
          constructed: !1,
          optional: !0,
          capture: "certValidity1UTCTime"
        }, {
          // notBefore (Time) (generalized time case)
          name: "Certificate.TBSCertificate.validity.notBefore (generalized)",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.GENERALIZEDTIME,
          constructed: !1,
          optional: !0,
          capture: "certValidity2GeneralizedTime"
        }, {
          // notAfter (Time) (only UTC time is supported)
          name: "Certificate.TBSCertificate.validity.notAfter (utc)",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.UTCTIME,
          constructed: !1,
          optional: !0,
          capture: "certValidity3UTCTime"
        }, {
          // notAfter (Time) (only UTC time is supported)
          name: "Certificate.TBSCertificate.validity.notAfter (generalized)",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.GENERALIZEDTIME,
          constructed: !1,
          optional: !0,
          capture: "certValidity4GeneralizedTime"
        }]
      },
      {
        // Name (subject) (RDNSequence)
        name: "Certificate.TBSCertificate.subject",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.SEQUENCE,
        constructed: !0,
        captureAsn1: "certSubject"
      },
      // SubjectPublicKeyInfo
      Dl,
      {
        // issuerUniqueID (optional)
        name: "Certificate.TBSCertificate.issuerUniqueID",
        tagClass: E.Class.CONTEXT_SPECIFIC,
        type: 1,
        constructed: !0,
        optional: !0,
        value: [{
          name: "Certificate.TBSCertificate.issuerUniqueID.id",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.BITSTRING,
          constructed: !1,
          // TODO: support arbitrary bit length ids
          captureBitStringValue: "certIssuerUniqueId"
        }]
      },
      {
        // subjectUniqueID (optional)
        name: "Certificate.TBSCertificate.subjectUniqueID",
        tagClass: E.Class.CONTEXT_SPECIFIC,
        type: 2,
        constructed: !0,
        optional: !0,
        value: [{
          name: "Certificate.TBSCertificate.subjectUniqueID.id",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.BITSTRING,
          constructed: !1,
          // TODO: support arbitrary bit length ids
          captureBitStringValue: "certSubjectUniqueId"
        }]
      },
      {
        // Extensions (optional)
        name: "Certificate.TBSCertificate.extensions",
        tagClass: E.Class.CONTEXT_SPECIFIC,
        type: 3,
        constructed: !0,
        captureAsn1: "certExtensions",
        optional: !0
      }
    ]
  }, {
    // AlgorithmIdentifier (signature algorithm)
    name: "Certificate.signatureAlgorithm",
    tagClass: E.Class.UNIVERSAL,
    type: E.Type.SEQUENCE,
    constructed: !0,
    value: [{
      // algorithm
      name: "Certificate.signatureAlgorithm.algorithm",
      tagClass: E.Class.UNIVERSAL,
      type: E.Type.OID,
      constructed: !1,
      capture: "certSignatureOid"
    }, {
      name: "Certificate.TBSCertificate.signature.parameters",
      tagClass: E.Class.UNIVERSAL,
      optional: !0,
      captureAsn1: "certSignatureParams"
    }]
  }, {
    // SignatureValue
    name: "Certificate.signatureValue",
    tagClass: E.Class.UNIVERSAL,
    type: E.Type.BITSTRING,
    constructed: !1,
    captureBitStringValue: "certSignature"
  }]
}, Qv = {
  name: "rsapss",
  tagClass: E.Class.UNIVERSAL,
  type: E.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "rsapss.hashAlgorithm",
    tagClass: E.Class.CONTEXT_SPECIFIC,
    type: 0,
    constructed: !0,
    value: [{
      name: "rsapss.hashAlgorithm.AlgorithmIdentifier",
      tagClass: E.Class.UNIVERSAL,
      type: E.Class.SEQUENCE,
      constructed: !0,
      optional: !0,
      value: [{
        name: "rsapss.hashAlgorithm.AlgorithmIdentifier.algorithm",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.OID,
        constructed: !1,
        capture: "hashOid"
        /* parameter block omitted, for SHA1 NULL anyhow. */
      }]
    }]
  }, {
    name: "rsapss.maskGenAlgorithm",
    tagClass: E.Class.CONTEXT_SPECIFIC,
    type: 1,
    constructed: !0,
    value: [{
      name: "rsapss.maskGenAlgorithm.AlgorithmIdentifier",
      tagClass: E.Class.UNIVERSAL,
      type: E.Class.SEQUENCE,
      constructed: !0,
      optional: !0,
      value: [{
        name: "rsapss.maskGenAlgorithm.AlgorithmIdentifier.algorithm",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.OID,
        constructed: !1,
        capture: "maskGenOid"
      }, {
        name: "rsapss.maskGenAlgorithm.AlgorithmIdentifier.params",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.SEQUENCE,
        constructed: !0,
        value: [{
          name: "rsapss.maskGenAlgorithm.AlgorithmIdentifier.params.algorithm",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.OID,
          constructed: !1,
          capture: "maskGenHashOid"
          /* parameter block omitted, for SHA1 NULL anyhow. */
        }]
      }]
    }]
  }, {
    name: "rsapss.saltLength",
    tagClass: E.Class.CONTEXT_SPECIFIC,
    type: 2,
    optional: !0,
    value: [{
      name: "rsapss.saltLength.saltLength",
      tagClass: E.Class.UNIVERSAL,
      type: E.Class.INTEGER,
      constructed: !1,
      capture: "saltLength"
    }]
  }, {
    name: "rsapss.trailerField",
    tagClass: E.Class.CONTEXT_SPECIFIC,
    type: 3,
    optional: !0,
    value: [{
      name: "rsapss.trailer.trailer",
      tagClass: E.Class.UNIVERSAL,
      type: E.Class.INTEGER,
      constructed: !1,
      capture: "trailer"
    }]
  }]
}, Xv = {
  name: "CertificationRequestInfo",
  tagClass: E.Class.UNIVERSAL,
  type: E.Type.SEQUENCE,
  constructed: !0,
  captureAsn1: "certificationRequestInfo",
  value: [
    {
      name: "CertificationRequestInfo.integer",
      tagClass: E.Class.UNIVERSAL,
      type: E.Type.INTEGER,
      constructed: !1,
      capture: "certificationRequestInfoVersion"
    },
    {
      // Name (subject) (RDNSequence)
      name: "CertificationRequestInfo.subject",
      tagClass: E.Class.UNIVERSAL,
      type: E.Type.SEQUENCE,
      constructed: !0,
      captureAsn1: "certificationRequestInfoSubject"
    },
    // SubjectPublicKeyInfo
    Dl,
    {
      name: "CertificationRequestInfo.attributes",
      tagClass: E.Class.CONTEXT_SPECIFIC,
      type: 0,
      constructed: !0,
      optional: !0,
      capture: "certificationRequestInfoAttributes",
      value: [{
        name: "CertificationRequestInfo.attributes",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.SEQUENCE,
        constructed: !0,
        value: [{
          name: "CertificationRequestInfo.attributes.type",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.OID,
          constructed: !1
        }, {
          name: "CertificationRequestInfo.attributes.value",
          tagClass: E.Class.UNIVERSAL,
          type: E.Type.SET,
          constructed: !0
        }]
      }]
    }
  ]
}, Zv = {
  name: "CertificationRequest",
  tagClass: E.Class.UNIVERSAL,
  type: E.Type.SEQUENCE,
  constructed: !0,
  captureAsn1: "csr",
  value: [
    Xv,
    {
      // AlgorithmIdentifier (signature algorithm)
      name: "CertificationRequest.signatureAlgorithm",
      tagClass: E.Class.UNIVERSAL,
      type: E.Type.SEQUENCE,
      constructed: !0,
      value: [{
        // algorithm
        name: "CertificationRequest.signatureAlgorithm.algorithm",
        tagClass: E.Class.UNIVERSAL,
        type: E.Type.OID,
        constructed: !1,
        capture: "csrSignatureOid"
      }, {
        name: "CertificationRequest.signatureAlgorithm.parameters",
        tagClass: E.Class.UNIVERSAL,
        optional: !0,
        captureAsn1: "csrSignatureParams"
      }]
    },
    {
      // signature
      name: "CertificationRequest.signature",
      tagClass: E.Class.UNIVERSAL,
      type: E.Type.BITSTRING,
      constructed: !1,
      captureBitStringValue: "csrSignature"
    }
  ]
};
J.RDNAttributesAsArray = function(e, t) {
  for (var a = [], r, n, i, s = 0; s < e.value.length; ++s) {
    r = e.value[s];
    for (var o = 0; o < r.value.length; ++o)
      i = {}, n = r.value[o], i.type = E.derToOid(n.value[0].value), i.value = n.value[1].value, i.valueTagClass = n.value[1].type, i.type in Ve && (i.name = Ve[i.type], i.name in at && (i.shortName = at[i.name])), t && (t.update(i.type), t.update(i.value)), a.push(i);
  }
  return a;
};
J.CRIAttributesAsArray = function(e) {
  for (var t = [], a = 0; a < e.length; ++a)
    for (var r = e[a], n = E.derToOid(r.value[0].value), i = r.value[1].value, s = 0; s < i.length; ++s) {
      var o = {};
      if (o.type = n, o.value = i[s].value, o.valueTagClass = i[s].type, o.type in Ve && (o.name = Ve[o.type], o.name in at && (o.shortName = at[o.name])), o.type === Ve.extensionRequest) {
        o.extensions = [];
        for (var u = 0; u < o.value.length; ++u)
          o.extensions.push(J.certificateExtensionFromAsn1(o.value[u]));
      }
      t.push(o);
    }
  return t;
};
function Cr(e, t) {
  typeof t == "string" && (t = { shortName: t });
  for (var a = null, r, n = 0; a === null && n < e.attributes.length; ++n)
    r = e.attributes[n], (t.type && t.type === r.type || t.name && t.name === r.name || t.shortName && t.shortName === r.shortName) && (a = r);
  return a;
}
var pn = function(e, t, a) {
  var r = {};
  if (e !== Ve["RSASSA-PSS"])
    return r;
  a && (r = {
    hash: {
      algorithmOid: Ve.sha1
    },
    mgf: {
      algorithmOid: Ve.mgf1,
      hash: {
        algorithmOid: Ve.sha1
      }
    },
    saltLength: 20
  });
  var n = {}, i = [];
  if (!E.validate(t, Qv, n, i)) {
    var s = new Error("Cannot read RSASSA-PSS parameter block.");
    throw s.errors = i, s;
  }
  return n.hashOid !== void 0 && (r.hash = r.hash || {}, r.hash.algorithmOid = E.derToOid(n.hashOid)), n.maskGenOid !== void 0 && (r.mgf = r.mgf || {}, r.mgf.algorithmOid = E.derToOid(n.maskGenOid), r.mgf.hash = r.mgf.hash || {}, r.mgf.hash.algorithmOid = E.derToOid(n.maskGenHashOid)), n.saltLength !== void 0 && (r.saltLength = n.saltLength.charCodeAt(0)), r;
}, Ln = function(e) {
  switch (Ve[e.signatureOid]) {
    case "sha1WithRSAEncryption":
    case "sha1WithRSASignature":
      return Ce.md.sha1.create();
    case "md5WithRSAEncryption":
      return Ce.md.md5.create();
    case "sha256WithRSAEncryption":
      return Ce.md.sha256.create();
    case "sha384WithRSAEncryption":
      return Ce.md.sha384.create();
    case "sha512WithRSAEncryption":
      return Ce.md.sha512.create();
    case "RSASSA-PSS":
      return Ce.md.sha256.create();
    default:
      var t = new Error(
        "Could not compute " + e.type + " digest. Unknown signature OID."
      );
      throw t.signatureOid = e.signatureOid, t;
  }
}, Nl = function(e) {
  var t = e.certificate, a;
  switch (t.signatureOid) {
    case Ve.sha1WithRSAEncryption:
    case Ve.sha1WithRSASignature:
      break;
    case Ve["RSASSA-PSS"]:
      var r, n;
      if (r = Ve[t.signatureParameters.mgf.hash.algorithmOid], r === void 0 || Ce.md[r] === void 0) {
        var i = new Error("Unsupported MGF hash function.");
        throw i.oid = t.signatureParameters.mgf.hash.algorithmOid, i.name = r, i;
      }
      if (n = Ve[t.signatureParameters.mgf.algorithmOid], n === void 0 || Ce.mgf[n] === void 0) {
        var i = new Error("Unsupported MGF function.");
        throw i.oid = t.signatureParameters.mgf.algorithmOid, i.name = n, i;
      }
      if (n = Ce.mgf[n].create(Ce.md[r].create()), r = Ve[t.signatureParameters.hash.algorithmOid], r === void 0 || Ce.md[r] === void 0) {
        var i = new Error("Unsupported RSASSA-PSS hash function.");
        throw i.oid = t.signatureParameters.hash.algorithmOid, i.name = r, i;
      }
      a = Ce.pss.create(
        Ce.md[r].create(),
        n,
        t.signatureParameters.saltLength
      );
      break;
  }
  return t.publicKey.verify(
    e.md.digest().getBytes(),
    e.signature,
    a
  );
};
J.certificateFromPem = function(e, t, a) {
  var r = Ce.pem.decode(e)[0];
  if (r.type !== "CERTIFICATE" && r.type !== "X509 CERTIFICATE" && r.type !== "TRUSTED CERTIFICATE") {
    var n = new Error(
      'Could not convert certificate from PEM; PEM header type is not "CERTIFICATE", "X509 CERTIFICATE", or "TRUSTED CERTIFICATE".'
    );
    throw n.headerType = r.type, n;
  }
  if (r.procType && r.procType.type === "ENCRYPTED")
    throw new Error(
      "Could not convert certificate from PEM; PEM is encrypted."
    );
  var i = E.fromDer(r.body, a);
  return J.certificateFromAsn1(i, t);
};
J.certificateToPem = function(e, t) {
  var a = {
    type: "CERTIFICATE",
    body: E.toDer(J.certificateToAsn1(e)).getBytes()
  };
  return Ce.pem.encode(a, { maxline: t });
};
J.publicKeyFromPem = function(e) {
  var t = Ce.pem.decode(e)[0];
  if (t.type !== "PUBLIC KEY" && t.type !== "RSA PUBLIC KEY") {
    var a = new Error('Could not convert public key from PEM; PEM header type is not "PUBLIC KEY" or "RSA PUBLIC KEY".');
    throw a.headerType = t.type, a;
  }
  if (t.procType && t.procType.type === "ENCRYPTED")
    throw new Error("Could not convert public key from PEM; PEM is encrypted.");
  var r = E.fromDer(t.body);
  return J.publicKeyFromAsn1(r);
};
J.publicKeyToPem = function(e, t) {
  var a = {
    type: "PUBLIC KEY",
    body: E.toDer(J.publicKeyToAsn1(e)).getBytes()
  };
  return Ce.pem.encode(a, { maxline: t });
};
J.publicKeyToRSAPublicKeyPem = function(e, t) {
  var a = {
    type: "RSA PUBLIC KEY",
    body: E.toDer(J.publicKeyToRSAPublicKey(e)).getBytes()
  };
  return Ce.pem.encode(a, { maxline: t });
};
J.getPublicKeyFingerprint = function(e, t) {
  t = t || {};
  var a = t.md || Ce.md.sha1.create(), r = t.type || "RSAPublicKey", n;
  switch (r) {
    case "RSAPublicKey":
      n = E.toDer(J.publicKeyToRSAPublicKey(e)).getBytes();
      break;
    case "SubjectPublicKeyInfo":
      n = E.toDer(J.publicKeyToAsn1(e)).getBytes();
      break;
    default:
      throw new Error('Unknown fingerprint type "' + t.type + '".');
  }
  a.start(), a.update(n);
  var i = a.digest();
  if (t.encoding === "hex") {
    var s = i.toHex();
    return t.delimiter ? s.match(/.{2}/g).join(t.delimiter) : s;
  } else {
    if (t.encoding === "binary")
      return i.getBytes();
    if (t.encoding)
      throw new Error('Unknown encoding "' + t.encoding + '".');
  }
  return i;
};
J.certificationRequestFromPem = function(e, t, a) {
  var r = Ce.pem.decode(e)[0];
  if (r.type !== "CERTIFICATE REQUEST") {
    var n = new Error('Could not convert certification request from PEM; PEM header type is not "CERTIFICATE REQUEST".');
    throw n.headerType = r.type, n;
  }
  if (r.procType && r.procType.type === "ENCRYPTED")
    throw new Error("Could not convert certification request from PEM; PEM is encrypted.");
  var i = E.fromDer(r.body, a);
  return J.certificationRequestFromAsn1(i, t);
};
J.certificationRequestToPem = function(e, t) {
  var a = {
    type: "CERTIFICATE REQUEST",
    body: E.toDer(J.certificationRequestToAsn1(e)).getBytes()
  };
  return Ce.pem.encode(a, { maxline: t });
};
J.createCertificate = function() {
  var e = {};
  return e.version = 2, e.serialNumber = "00", e.signatureOid = null, e.signature = null, e.siginfo = {}, e.siginfo.algorithmOid = null, e.validity = {}, e.validity.notBefore = /* @__PURE__ */ new Date(), e.validity.notAfter = /* @__PURE__ */ new Date(), e.issuer = {}, e.issuer.getField = function(t) {
    return Cr(e.issuer, t);
  }, e.issuer.addField = function(t) {
    Ut([t]), e.issuer.attributes.push(t);
  }, e.issuer.attributes = [], e.issuer.hash = null, e.subject = {}, e.subject.getField = function(t) {
    return Cr(e.subject, t);
  }, e.subject.addField = function(t) {
    Ut([t]), e.subject.attributes.push(t);
  }, e.subject.attributes = [], e.subject.hash = null, e.extensions = [], e.publicKey = null, e.md = null, e.setSubject = function(t, a) {
    Ut(t), e.subject.attributes = t, delete e.subject.uniqueId, a && (e.subject.uniqueId = a), e.subject.hash = null;
  }, e.setIssuer = function(t, a) {
    Ut(t), e.issuer.attributes = t, delete e.issuer.uniqueId, a && (e.issuer.uniqueId = a), e.issuer.hash = null;
  }, e.setExtensions = function(t) {
    for (var a = 0; a < t.length; ++a)
      Rl(t[a], { cert: e });
    e.extensions = t;
  }, e.getExtension = function(t) {
    typeof t == "string" && (t = { name: t });
    for (var a = null, r, n = 0; a === null && n < e.extensions.length; ++n)
      r = e.extensions[n], (t.id && r.id === t.id || t.name && r.name === t.name) && (a = r);
    return a;
  }, e.sign = function(t, a) {
    e.md = a || Ce.md.sha1.create();
    var r = Ve[e.md.algorithm + "WithRSAEncryption"];
    if (!r) {
      var n = new Error("Could not compute certificate digest. Unknown message digest algorithm OID.");
      throw n.algorithm = e.md.algorithm, n;
    }
    e.signatureOid = e.siginfo.algorithmOid = r, e.tbsCertificate = J.getTBSCertificate(e);
    var i = E.toDer(e.tbsCertificate);
    e.md.update(i.getBytes()), e.signature = t.sign(e.md);
  }, e.verify = function(t) {
    var a = !1;
    if (!e.issued(t)) {
      var r = t.issuer, n = e.subject, i = new Error(
        "The parent certificate did not issue the given child certificate; the child certificate's issuer does not match the parent's subject."
      );
      throw i.expectedIssuer = n.attributes, i.actualIssuer = r.attributes, i;
    }
    var s = t.md;
    if (s === null) {
      s = Ln({
        signatureOid: t.signatureOid,
        type: "certificate"
      });
      var o = t.tbsCertificate || J.getTBSCertificate(t), u = E.toDer(o);
      s.update(u.getBytes());
    }
    return s !== null && (a = Nl({
      certificate: e,
      md: s,
      signature: t.signature
    })), a;
  }, e.isIssuer = function(t) {
    var a = !1, r = e.issuer, n = t.subject;
    if (r.hash && n.hash)
      a = r.hash === n.hash;
    else if (r.attributes.length === n.attributes.length) {
      a = !0;
      for (var i, s, o = 0; a && o < r.attributes.length; ++o)
        i = r.attributes[o], s = n.attributes[o], (i.type !== s.type || i.value !== s.value) && (a = !1);
    }
    return a;
  }, e.issued = function(t) {
    return t.isIssuer(e);
  }, e.generateSubjectKeyIdentifier = function() {
    return J.getPublicKeyFingerprint(e.publicKey, { type: "RSAPublicKey" });
  }, e.verifySubjectKeyIdentifier = function() {
    for (var t = Ve.subjectKeyIdentifier, a = 0; a < e.extensions.length; ++a) {
      var r = e.extensions[a];
      if (r.id === t) {
        var n = e.generateSubjectKeyIdentifier().getBytes();
        return Ce.util.hexToBytes(r.subjectKeyIdentifier) === n;
      }
    }
    return !1;
  }, e;
};
J.certificateFromAsn1 = function(e, t) {
  var a = {}, r = [];
  if (!E.validate(e, Yv, a, r)) {
    var n = new Error("Cannot read X.509 certificate. ASN.1 object is not an X509v3 Certificate.");
    throw n.errors = r, n;
  }
  var i = E.derToOid(a.publicKeyOid);
  if (i !== J.oids.rsaEncryption)
    throw new Error("Cannot read public key. OID is not RSA.");
  var s = J.createCertificate();
  s.version = a.certVersion ? a.certVersion.charCodeAt(0) : 0;
  var o = Ce.util.createBuffer(a.certSerialNumber);
  s.serialNumber = o.toHex(), s.signatureOid = Ce.asn1.derToOid(a.certSignatureOid), s.signatureParameters = pn(
    s.signatureOid,
    a.certSignatureParams,
    !0
  ), s.siginfo.algorithmOid = Ce.asn1.derToOid(a.certinfoSignatureOid), s.siginfo.parameters = pn(
    s.siginfo.algorithmOid,
    a.certinfoSignatureParams,
    !1
  ), s.signature = a.certSignature;
  var u = [];
  if (a.certValidity1UTCTime !== void 0 && u.push(E.utcTimeToDate(a.certValidity1UTCTime)), a.certValidity2GeneralizedTime !== void 0 && u.push(E.generalizedTimeToDate(
    a.certValidity2GeneralizedTime
  )), a.certValidity3UTCTime !== void 0 && u.push(E.utcTimeToDate(a.certValidity3UTCTime)), a.certValidity4GeneralizedTime !== void 0 && u.push(E.generalizedTimeToDate(
    a.certValidity4GeneralizedTime
  )), u.length > 2)
    throw new Error("Cannot read notBefore/notAfter validity times; more than two times were provided in the certificate.");
  if (u.length < 2)
    throw new Error("Cannot read notBefore/notAfter validity times; they were not provided as either UTCTime or GeneralizedTime.");
  if (s.validity.notBefore = u[0], s.validity.notAfter = u[1], s.tbsCertificate = a.tbsCertificate, t) {
    s.md = Ln({
      signatureOid: s.signatureOid,
      type: "certificate"
    });
    var l = E.toDer(s.tbsCertificate);
    s.md.update(l.getBytes());
  }
  var c = Ce.md.sha1.create(), f = E.toDer(a.certIssuer);
  c.update(f.getBytes()), s.issuer.getField = function(p) {
    return Cr(s.issuer, p);
  }, s.issuer.addField = function(p) {
    Ut([p]), s.issuer.attributes.push(p);
  }, s.issuer.attributes = J.RDNAttributesAsArray(a.certIssuer), a.certIssuerUniqueId && (s.issuer.uniqueId = a.certIssuerUniqueId), s.issuer.hash = c.digest().toHex();
  var d = Ce.md.sha1.create(), v = E.toDer(a.certSubject);
  return d.update(v.getBytes()), s.subject.getField = function(p) {
    return Cr(s.subject, p);
  }, s.subject.addField = function(p) {
    Ut([p]), s.subject.attributes.push(p);
  }, s.subject.attributes = J.RDNAttributesAsArray(a.certSubject), a.certSubjectUniqueId && (s.subject.uniqueId = a.certSubjectUniqueId), s.subject.hash = d.digest().toHex(), a.certExtensions ? s.extensions = J.certificateExtensionsFromAsn1(a.certExtensions) : s.extensions = [], s.publicKey = J.publicKeyFromAsn1(a.subjectPublicKeyInfo), s;
};
J.certificateExtensionsFromAsn1 = function(e) {
  for (var t = [], a = 0; a < e.value.length; ++a)
    for (var r = e.value[a], n = 0; n < r.value.length; ++n)
      t.push(J.certificateExtensionFromAsn1(r.value[n]));
  return t;
};
J.certificateExtensionFromAsn1 = function(e) {
  var t = {};
  if (t.id = E.derToOid(e.value[0].value), t.critical = !1, e.value[1].type === E.Type.BOOLEAN ? (t.critical = e.value[1].value.charCodeAt(0) !== 0, t.value = e.value[2].value) : t.value = e.value[1].value, t.id in Ve) {
    if (t.name = Ve[t.id], t.name === "keyUsage") {
      var a = E.fromDer(t.value), r = 0, n = 0;
      a.value.length > 1 && (r = a.value.charCodeAt(1), n = a.value.length > 2 ? a.value.charCodeAt(2) : 0), t.digitalSignature = (r & 128) === 128, t.nonRepudiation = (r & 64) === 64, t.keyEncipherment = (r & 32) === 32, t.dataEncipherment = (r & 16) === 16, t.keyAgreement = (r & 8) === 8, t.keyCertSign = (r & 4) === 4, t.cRLSign = (r & 2) === 2, t.encipherOnly = (r & 1) === 1, t.decipherOnly = (n & 128) === 128;
    } else if (t.name === "basicConstraints") {
      var a = E.fromDer(t.value);
      a.value.length > 0 && a.value[0].type === E.Type.BOOLEAN ? t.cA = a.value[0].value.charCodeAt(0) !== 0 : t.cA = !1;
      var i = null;
      a.value.length > 0 && a.value[0].type === E.Type.INTEGER ? i = a.value[0].value : a.value.length > 1 && (i = a.value[1].value), i !== null && (t.pathLenConstraint = E.derToInteger(i));
    } else if (t.name === "extKeyUsage")
      for (var a = E.fromDer(t.value), s = 0; s < a.value.length; ++s) {
        var o = E.derToOid(a.value[s].value);
        o in Ve ? t[Ve[o]] = !0 : t[o] = !0;
      }
    else if (t.name === "nsCertType") {
      var a = E.fromDer(t.value), r = 0;
      a.value.length > 1 && (r = a.value.charCodeAt(1)), t.client = (r & 128) === 128, t.server = (r & 64) === 64, t.email = (r & 32) === 32, t.objsign = (r & 16) === 16, t.reserved = (r & 8) === 8, t.sslCA = (r & 4) === 4, t.emailCA = (r & 2) === 2, t.objCA = (r & 1) === 1;
    } else if (t.name === "subjectAltName" || t.name === "issuerAltName") {
      t.altNames = [];
      for (var u, a = E.fromDer(t.value), l = 0; l < a.value.length; ++l) {
        u = a.value[l];
        var c = {
          type: u.type,
          value: u.value
        };
        switch (t.altNames.push(c), u.type) {
          case 1:
          case 2:
          case 6:
            break;
          case 7:
            c.ip = Ce.util.bytesToIP(u.value);
            break;
          case 8:
            c.oid = E.derToOid(u.value);
            break;
        }
      }
    } else if (t.name === "subjectKeyIdentifier") {
      var a = E.fromDer(t.value);
      t.subjectKeyIdentifier = Ce.util.bytesToHex(a.value);
    }
  }
  return t;
};
J.certificationRequestFromAsn1 = function(e, t) {
  var a = {}, r = [];
  if (!E.validate(e, Zv, a, r)) {
    var n = new Error("Cannot read PKCS#10 certificate request. ASN.1 object is not a PKCS#10 CertificationRequest.");
    throw n.errors = r, n;
  }
  var i = E.derToOid(a.publicKeyOid);
  if (i !== J.oids.rsaEncryption)
    throw new Error("Cannot read public key. OID is not RSA.");
  var s = J.createCertificationRequest();
  if (s.version = a.csrVersion ? a.csrVersion.charCodeAt(0) : 0, s.signatureOid = Ce.asn1.derToOid(a.csrSignatureOid), s.signatureParameters = pn(
    s.signatureOid,
    a.csrSignatureParams,
    !0
  ), s.siginfo.algorithmOid = Ce.asn1.derToOid(a.csrSignatureOid), s.siginfo.parameters = pn(
    s.siginfo.algorithmOid,
    a.csrSignatureParams,
    !1
  ), s.signature = a.csrSignature, s.certificationRequestInfo = a.certificationRequestInfo, t) {
    s.md = Ln({
      signatureOid: s.signatureOid,
      type: "certification request"
    });
    var o = E.toDer(s.certificationRequestInfo);
    s.md.update(o.getBytes());
  }
  var u = Ce.md.sha1.create();
  return s.subject.getField = function(l) {
    return Cr(s.subject, l);
  }, s.subject.addField = function(l) {
    Ut([l]), s.subject.attributes.push(l);
  }, s.subject.attributes = J.RDNAttributesAsArray(
    a.certificationRequestInfoSubject,
    u
  ), s.subject.hash = u.digest().toHex(), s.publicKey = J.publicKeyFromAsn1(a.subjectPublicKeyInfo), s.getAttribute = function(l) {
    return Cr(s, l);
  }, s.addAttribute = function(l) {
    Ut([l]), s.attributes.push(l);
  }, s.attributes = J.CRIAttributesAsArray(
    a.certificationRequestInfoAttributes || []
  ), s;
};
J.createCertificationRequest = function() {
  var e = {};
  return e.version = 0, e.signatureOid = null, e.signature = null, e.siginfo = {}, e.siginfo.algorithmOid = null, e.subject = {}, e.subject.getField = function(t) {
    return Cr(e.subject, t);
  }, e.subject.addField = function(t) {
    Ut([t]), e.subject.attributes.push(t);
  }, e.subject.attributes = [], e.subject.hash = null, e.publicKey = null, e.attributes = [], e.getAttribute = function(t) {
    return Cr(e, t);
  }, e.addAttribute = function(t) {
    Ut([t]), e.attributes.push(t);
  }, e.md = null, e.setSubject = function(t) {
    Ut(t), e.subject.attributes = t, e.subject.hash = null;
  }, e.setAttributes = function(t) {
    Ut(t), e.attributes = t;
  }, e.sign = function(t, a) {
    e.md = a || Ce.md.sha1.create();
    var r = Ve[e.md.algorithm + "WithRSAEncryption"];
    if (!r) {
      var n = new Error("Could not compute certification request digest. Unknown message digest algorithm OID.");
      throw n.algorithm = e.md.algorithm, n;
    }
    e.signatureOid = e.siginfo.algorithmOid = r, e.certificationRequestInfo = J.getCertificationRequestInfo(e);
    var i = E.toDer(e.certificationRequestInfo);
    e.md.update(i.getBytes()), e.signature = t.sign(e.md);
  }, e.verify = function() {
    var t = !1, a = e.md;
    if (a === null) {
      a = Ln({
        signatureOid: e.signatureOid,
        type: "certification request"
      });
      var r = e.certificationRequestInfo || J.getCertificationRequestInfo(e), n = E.toDer(r);
      a.update(n.getBytes());
    }
    return a !== null && (t = Nl({
      certificate: e,
      md: a,
      signature: e.signature
    })), t;
  }, e;
};
function oa(e) {
  for (var t = E.create(
    E.Class.UNIVERSAL,
    E.Type.SEQUENCE,
    !0,
    []
  ), a, r, n = e.attributes, i = 0; i < n.length; ++i) {
    a = n[i];
    var s = a.value, o = E.Type.PRINTABLESTRING;
    "valueTagClass" in a && (o = a.valueTagClass, o === E.Type.UTF8 && (s = Ce.util.encodeUtf8(s))), r = E.create(E.Class.UNIVERSAL, E.Type.SET, !0, [
      E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
        // AttributeType
        E.create(
          E.Class.UNIVERSAL,
          E.Type.OID,
          !1,
          E.oidToDer(a.type).getBytes()
        ),
        // AttributeValue
        E.create(E.Class.UNIVERSAL, o, !1, s)
      ])
    ]), t.value.push(r);
  }
  return t;
}
function Ut(e) {
  for (var t, a = 0; a < e.length; ++a) {
    if (t = e[a], typeof t.name > "u" && (t.type && t.type in J.oids ? t.name = J.oids[t.type] : t.shortName && t.shortName in at && (t.name = J.oids[at[t.shortName]])), typeof t.type > "u")
      if (t.name && t.name in J.oids)
        t.type = J.oids[t.name];
      else {
        var r = new Error("Attribute type not specified.");
        throw r.attribute = t, r;
      }
    if (typeof t.shortName > "u" && t.name && t.name in at && (t.shortName = at[t.name]), t.type === Ve.extensionRequest && (t.valueConstructed = !0, t.valueTagClass = E.Type.SEQUENCE, !t.value && t.extensions)) {
      t.value = [];
      for (var n = 0; n < t.extensions.length; ++n)
        t.value.push(J.certificateExtensionToAsn1(
          Rl(t.extensions[n])
        ));
    }
    if (typeof t.value > "u") {
      var r = new Error("Attribute value not specified.");
      throw r.attribute = t, r;
    }
  }
}
function Rl(e, t) {
  if (t = t || {}, typeof e.name > "u" && e.id && e.id in J.oids && (e.name = J.oids[e.id]), typeof e.id > "u")
    if (e.name && e.name in J.oids)
      e.id = J.oids[e.name];
    else {
      var a = new Error("Extension ID not specified.");
      throw a.extension = e, a;
    }
  if (typeof e.value < "u")
    return e;
  if (e.name === "keyUsage") {
    var r = 0, n = 0, i = 0;
    e.digitalSignature && (n |= 128, r = 7), e.nonRepudiation && (n |= 64, r = 6), e.keyEncipherment && (n |= 32, r = 5), e.dataEncipherment && (n |= 16, r = 4), e.keyAgreement && (n |= 8, r = 3), e.keyCertSign && (n |= 4, r = 2), e.cRLSign && (n |= 2, r = 1), e.encipherOnly && (n |= 1, r = 0), e.decipherOnly && (i |= 128, r = 7);
    var s = String.fromCharCode(r);
    i !== 0 ? s += String.fromCharCode(n) + String.fromCharCode(i) : n !== 0 && (s += String.fromCharCode(n)), e.value = E.create(
      E.Class.UNIVERSAL,
      E.Type.BITSTRING,
      !1,
      s
    );
  } else if (e.name === "basicConstraints")
    e.value = E.create(
      E.Class.UNIVERSAL,
      E.Type.SEQUENCE,
      !0,
      []
    ), e.cA && e.value.value.push(E.create(
      E.Class.UNIVERSAL,
      E.Type.BOOLEAN,
      !1,
      "ÿ"
    )), "pathLenConstraint" in e && e.value.value.push(E.create(
      E.Class.UNIVERSAL,
      E.Type.INTEGER,
      !1,
      E.integerToDer(e.pathLenConstraint).getBytes()
    ));
  else if (e.name === "extKeyUsage") {
    e.value = E.create(
      E.Class.UNIVERSAL,
      E.Type.SEQUENCE,
      !0,
      []
    );
    var o = e.value.value;
    for (var u in e)
      e[u] === !0 && (u in Ve ? o.push(E.create(
        E.Class.UNIVERSAL,
        E.Type.OID,
        !1,
        E.oidToDer(Ve[u]).getBytes()
      )) : u.indexOf(".") !== -1 && o.push(E.create(
        E.Class.UNIVERSAL,
        E.Type.OID,
        !1,
        E.oidToDer(u).getBytes()
      )));
  } else if (e.name === "nsCertType") {
    var r = 0, n = 0;
    e.client && (n |= 128, r = 7), e.server && (n |= 64, r = 6), e.email && (n |= 32, r = 5), e.objsign && (n |= 16, r = 4), e.reserved && (n |= 8, r = 3), e.sslCA && (n |= 4, r = 2), e.emailCA && (n |= 2, r = 1), e.objCA && (n |= 1, r = 0);
    var s = String.fromCharCode(r);
    n !== 0 && (s += String.fromCharCode(n)), e.value = E.create(
      E.Class.UNIVERSAL,
      E.Type.BITSTRING,
      !1,
      s
    );
  } else if (e.name === "subjectAltName" || e.name === "issuerAltName") {
    e.value = E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, []);
    for (var l, c = 0; c < e.altNames.length; ++c) {
      l = e.altNames[c];
      var s = l.value;
      if (l.type === 7 && l.ip) {
        if (s = Ce.util.bytesFromIP(l.ip), s === null) {
          var a = new Error(
            'Extension "ip" value is not a valid IPv4 or IPv6 address.'
          );
          throw a.extension = e, a;
        }
      } else l.type === 8 && (l.oid ? s = E.oidToDer(E.oidToDer(l.oid)) : s = E.oidToDer(s));
      e.value.value.push(E.create(
        E.Class.CONTEXT_SPECIFIC,
        l.type,
        !1,
        s
      ));
    }
  } else if (e.name === "nsComment" && t.cert) {
    if (!/^[\x00-\x7F]*$/.test(e.comment) || e.comment.length < 1 || e.comment.length > 128)
      throw new Error('Invalid "nsComment" content.');
    e.value = E.create(
      E.Class.UNIVERSAL,
      E.Type.IA5STRING,
      !1,
      e.comment
    );
  } else if (e.name === "subjectKeyIdentifier" && t.cert) {
    var f = t.cert.generateSubjectKeyIdentifier();
    e.subjectKeyIdentifier = f.toHex(), e.value = E.create(
      E.Class.UNIVERSAL,
      E.Type.OCTETSTRING,
      !1,
      f.getBytes()
    );
  } else if (e.name === "authorityKeyIdentifier" && t.cert) {
    e.value = E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, []);
    var o = e.value.value;
    if (e.keyIdentifier) {
      var d = e.keyIdentifier === !0 ? t.cert.generateSubjectKeyIdentifier().getBytes() : e.keyIdentifier;
      o.push(
        E.create(E.Class.CONTEXT_SPECIFIC, 0, !1, d)
      );
    }
    if (e.authorityCertIssuer) {
      var v = [
        E.create(E.Class.CONTEXT_SPECIFIC, 4, !0, [
          oa(e.authorityCertIssuer === !0 ? t.cert.issuer : e.authorityCertIssuer)
        ])
      ];
      o.push(
        E.create(E.Class.CONTEXT_SPECIFIC, 1, !0, v)
      );
    }
    if (e.serialNumber) {
      var p = Ce.util.hexToBytes(e.serialNumber === !0 ? t.cert.serialNumber : e.serialNumber);
      o.push(
        E.create(E.Class.CONTEXT_SPECIFIC, 2, !1, p)
      );
    }
  } else if (e.name === "cRLDistributionPoints") {
    e.value = E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, []);
    for (var o = e.value.value, h = E.create(
      E.Class.UNIVERSAL,
      E.Type.SEQUENCE,
      !0,
      []
    ), x = E.create(
      E.Class.CONTEXT_SPECIFIC,
      0,
      !0,
      []
    ), l, c = 0; c < e.altNames.length; ++c) {
      l = e.altNames[c];
      var s = l.value;
      if (l.type === 7 && l.ip) {
        if (s = Ce.util.bytesFromIP(l.ip), s === null) {
          var a = new Error(
            'Extension "ip" value is not a valid IPv4 or IPv6 address.'
          );
          throw a.extension = e, a;
        }
      } else l.type === 8 && (l.oid ? s = E.oidToDer(E.oidToDer(l.oid)) : s = E.oidToDer(s));
      x.value.push(E.create(
        E.Class.CONTEXT_SPECIFIC,
        l.type,
        !1,
        s
      ));
    }
    h.value.push(E.create(
      E.Class.CONTEXT_SPECIFIC,
      0,
      !0,
      [x]
    )), o.push(h);
  }
  if (typeof e.value > "u") {
    var a = new Error("Extension value not specified.");
    throw a.extension = e, a;
  }
  return e;
}
function b0(e, t) {
  switch (e) {
    case Ve["RSASSA-PSS"]:
      var a = [];
      return t.hash.algorithmOid !== void 0 && a.push(E.create(E.Class.CONTEXT_SPECIFIC, 0, !0, [
        E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
          E.create(
            E.Class.UNIVERSAL,
            E.Type.OID,
            !1,
            E.oidToDer(t.hash.algorithmOid).getBytes()
          ),
          E.create(E.Class.UNIVERSAL, E.Type.NULL, !1, "")
        ])
      ])), t.mgf.algorithmOid !== void 0 && a.push(E.create(E.Class.CONTEXT_SPECIFIC, 1, !0, [
        E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
          E.create(
            E.Class.UNIVERSAL,
            E.Type.OID,
            !1,
            E.oidToDer(t.mgf.algorithmOid).getBytes()
          ),
          E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
            E.create(
              E.Class.UNIVERSAL,
              E.Type.OID,
              !1,
              E.oidToDer(t.mgf.hash.algorithmOid).getBytes()
            ),
            E.create(E.Class.UNIVERSAL, E.Type.NULL, !1, "")
          ])
        ])
      ])), t.saltLength !== void 0 && a.push(E.create(E.Class.CONTEXT_SPECIFIC, 2, !0, [
        E.create(
          E.Class.UNIVERSAL,
          E.Type.INTEGER,
          !1,
          E.integerToDer(t.saltLength).getBytes()
        )
      ])), E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, a);
    default:
      return E.create(E.Class.UNIVERSAL, E.Type.NULL, !1, "");
  }
}
function Jv(e) {
  var t = E.create(E.Class.CONTEXT_SPECIFIC, 0, !0, []);
  if (e.attributes.length === 0)
    return t;
  for (var a = e.attributes, r = 0; r < a.length; ++r) {
    var n = a[r], i = n.value, s = E.Type.UTF8;
    "valueTagClass" in n && (s = n.valueTagClass), s === E.Type.UTF8 && (i = Ce.util.encodeUtf8(i));
    var o = !1;
    "valueConstructed" in n && (o = n.valueConstructed);
    var u = E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
      // AttributeType
      E.create(
        E.Class.UNIVERSAL,
        E.Type.OID,
        !1,
        E.oidToDer(n.type).getBytes()
      ),
      E.create(E.Class.UNIVERSAL, E.Type.SET, !0, [
        // AttributeValue
        E.create(
          E.Class.UNIVERSAL,
          s,
          o,
          i
        )
      ])
    ]);
    t.value.push(u);
  }
  return t;
}
var ex = /* @__PURE__ */ new Date("1950-01-01T00:00:00Z"), tx = /* @__PURE__ */ new Date("2050-01-01T00:00:00Z");
function co(e) {
  return e >= ex && e < tx ? E.create(
    E.Class.UNIVERSAL,
    E.Type.UTCTIME,
    !1,
    E.dateToUtcTime(e)
  ) : E.create(
    E.Class.UNIVERSAL,
    E.Type.GENERALIZEDTIME,
    !1,
    E.dateToGeneralizedTime(e)
  );
}
J.getTBSCertificate = function(e) {
  var t = co(e.validity.notBefore), a = co(e.validity.notAfter), r = E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
    // version
    E.create(E.Class.CONTEXT_SPECIFIC, 0, !0, [
      // integer
      E.create(
        E.Class.UNIVERSAL,
        E.Type.INTEGER,
        !1,
        E.integerToDer(e.version).getBytes()
      )
    ]),
    // serialNumber
    E.create(
      E.Class.UNIVERSAL,
      E.Type.INTEGER,
      !1,
      Ce.util.hexToBytes(e.serialNumber)
    ),
    // signature
    E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
      // algorithm
      E.create(
        E.Class.UNIVERSAL,
        E.Type.OID,
        !1,
        E.oidToDer(e.siginfo.algorithmOid).getBytes()
      ),
      // parameters
      b0(
        e.siginfo.algorithmOid,
        e.siginfo.parameters
      )
    ]),
    // issuer
    oa(e.issuer),
    // validity
    E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
      t,
      a
    ]),
    // subject
    oa(e.subject),
    // SubjectPublicKeyInfo
    J.publicKeyToAsn1(e.publicKey)
  ]);
  return e.issuer.uniqueId && r.value.push(
    E.create(E.Class.CONTEXT_SPECIFIC, 1, !0, [
      E.create(
        E.Class.UNIVERSAL,
        E.Type.BITSTRING,
        !1,
        // TODO: support arbitrary bit length ids
        "\0" + e.issuer.uniqueId
      )
    ])
  ), e.subject.uniqueId && r.value.push(
    E.create(E.Class.CONTEXT_SPECIFIC, 2, !0, [
      E.create(
        E.Class.UNIVERSAL,
        E.Type.BITSTRING,
        !1,
        // TODO: support arbitrary bit length ids
        "\0" + e.subject.uniqueId
      )
    ])
  ), e.extensions.length > 0 && r.value.push(J.certificateExtensionsToAsn1(e.extensions)), r;
};
J.getCertificationRequestInfo = function(e) {
  var t = E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
    // version
    E.create(
      E.Class.UNIVERSAL,
      E.Type.INTEGER,
      !1,
      E.integerToDer(e.version).getBytes()
    ),
    // subject
    oa(e.subject),
    // SubjectPublicKeyInfo
    J.publicKeyToAsn1(e.publicKey),
    // attributes
    Jv(e)
  ]);
  return t;
};
J.distinguishedNameToAsn1 = function(e) {
  return oa(e);
};
J.certificateToAsn1 = function(e) {
  var t = e.tbsCertificate || J.getTBSCertificate(e);
  return E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
    // TBSCertificate
    t,
    // AlgorithmIdentifier (signature algorithm)
    E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
      // algorithm
      E.create(
        E.Class.UNIVERSAL,
        E.Type.OID,
        !1,
        E.oidToDer(e.signatureOid).getBytes()
      ),
      // parameters
      b0(e.signatureOid, e.signatureParameters)
    ]),
    // SignatureValue
    E.create(
      E.Class.UNIVERSAL,
      E.Type.BITSTRING,
      !1,
      "\0" + e.signature
    )
  ]);
};
J.certificateExtensionsToAsn1 = function(e) {
  var t = E.create(E.Class.CONTEXT_SPECIFIC, 3, !0, []), a = E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, []);
  t.value.push(a);
  for (var r = 0; r < e.length; ++r)
    a.value.push(J.certificateExtensionToAsn1(e[r]));
  return t;
};
J.certificateExtensionToAsn1 = function(e) {
  var t = E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, []);
  t.value.push(E.create(
    E.Class.UNIVERSAL,
    E.Type.OID,
    !1,
    E.oidToDer(e.id).getBytes()
  )), e.critical && t.value.push(E.create(
    E.Class.UNIVERSAL,
    E.Type.BOOLEAN,
    !1,
    "ÿ"
  ));
  var a = e.value;
  return typeof e.value != "string" && (a = E.toDer(a).getBytes()), t.value.push(E.create(
    E.Class.UNIVERSAL,
    E.Type.OCTETSTRING,
    !1,
    a
  )), t;
};
J.certificationRequestToAsn1 = function(e) {
  var t = e.certificationRequestInfo || J.getCertificationRequestInfo(e);
  return E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
    // CertificationRequestInfo
    t,
    // AlgorithmIdentifier (signature algorithm)
    E.create(E.Class.UNIVERSAL, E.Type.SEQUENCE, !0, [
      // algorithm
      E.create(
        E.Class.UNIVERSAL,
        E.Type.OID,
        !1,
        E.oidToDer(e.signatureOid).getBytes()
      ),
      // parameters
      b0(e.signatureOid, e.signatureParameters)
    ]),
    // signature
    E.create(
      E.Class.UNIVERSAL,
      E.Type.BITSTRING,
      !1,
      "\0" + e.signature
    )
  ]);
};
J.createCaStore = function(e) {
  var t = {
    // stored certificates
    certs: {}
  };
  t.getIssuer = function(s) {
    var o = a(s.issuer);
    return o;
  }, t.addCertificate = function(s) {
    if (typeof s == "string" && (s = Ce.pki.certificateFromPem(s)), r(s.subject), !t.hasCertificate(s))
      if (s.subject.hash in t.certs) {
        var o = t.certs[s.subject.hash];
        Ce.util.isArray(o) || (o = [o]), o.push(s), t.certs[s.subject.hash] = o;
      } else
        t.certs[s.subject.hash] = s;
  }, t.hasCertificate = function(s) {
    typeof s == "string" && (s = Ce.pki.certificateFromPem(s));
    var o = a(s.subject);
    if (!o)
      return !1;
    Ce.util.isArray(o) || (o = [o]);
    for (var u = E.toDer(J.certificateToAsn1(s)).getBytes(), l = 0; l < o.length; ++l) {
      var c = E.toDer(J.certificateToAsn1(o[l])).getBytes();
      if (u === c)
        return !0;
    }
    return !1;
  }, t.listAllCertificates = function() {
    var s = [];
    for (var o in t.certs)
      if (t.certs.hasOwnProperty(o)) {
        var u = t.certs[o];
        if (!Ce.util.isArray(u))
          s.push(u);
        else
          for (var l = 0; l < u.length; ++l)
            s.push(u[l]);
      }
    return s;
  }, t.removeCertificate = function(s) {
    var o;
    if (typeof s == "string" && (s = Ce.pki.certificateFromPem(s)), r(s.subject), !t.hasCertificate(s))
      return null;
    var u = a(s.subject);
    if (!Ce.util.isArray(u))
      return o = t.certs[s.subject.hash], delete t.certs[s.subject.hash], o;
    for (var l = E.toDer(J.certificateToAsn1(s)).getBytes(), c = 0; c < u.length; ++c) {
      var f = E.toDer(J.certificateToAsn1(u[c])).getBytes();
      l === f && (o = u[c], u.splice(c, 1));
    }
    return u.length === 0 && delete t.certs[s.subject.hash], o;
  };
  function a(s) {
    return r(s), t.certs[s.hash] || null;
  }
  function r(s) {
    if (!s.hash) {
      var o = Ce.md.sha1.create();
      s.attributes = J.RDNAttributesAsArray(oa(s), o), s.hash = o.digest().toHex();
    }
  }
  if (e)
    for (var n = 0; n < e.length; ++n) {
      var i = e[n];
      t.addCertificate(i);
    }
  return t;
};
J.certificateError = {
  bad_certificate: "forge.pki.BadCertificate",
  unsupported_certificate: "forge.pki.UnsupportedCertificate",
  certificate_revoked: "forge.pki.CertificateRevoked",
  certificate_expired: "forge.pki.CertificateExpired",
  certificate_unknown: "forge.pki.CertificateUnknown",
  unknown_ca: "forge.pki.UnknownCertificateAuthority"
};
J.verifyCertificateChain = function(e, t, a) {
  typeof a == "function" && (a = { verify: a }), a = a || {}, t = t.slice(0);
  var r = t.slice(0), n = a.validityCheckDate;
  typeof n > "u" && (n = /* @__PURE__ */ new Date());
  var i = !0, s = null, o = 0;
  do {
    var u = t.shift(), l = null, c = !1;
    if (n && (n < u.validity.notBefore || n > u.validity.notAfter) && (s = {
      message: "Certificate is not valid yet or has expired.",
      error: J.certificateError.certificate_expired,
      notBefore: u.validity.notBefore,
      notAfter: u.validity.notAfter,
      // TODO: we might want to reconsider renaming 'now' to
      // 'validityCheckDate' should this API be changed in the future.
      now: n
    }), s === null) {
      if (l = t[0] || e.getIssuer(u), l === null && u.isIssuer(u) && (c = !0, l = u), l) {
        var f = l;
        Ce.util.isArray(f) || (f = [f]);
        for (var d = !1; !d && f.length > 0; ) {
          l = f.shift();
          try {
            d = l.verify(u);
          } catch {
          }
        }
        d || (s = {
          message: "Certificate signature is invalid.",
          error: J.certificateError.bad_certificate
        });
      }
      s === null && (!l || c) && !e.hasCertificate(u) && (s = {
        message: "Certificate is not trusted.",
        error: J.certificateError.unknown_ca
      });
    }
    if (s === null && l && !u.isIssuer(l) && (s = {
      message: "Certificate issuer is invalid.",
      error: J.certificateError.bad_certificate
    }), s === null)
      for (var v = {
        keyUsage: !0,
        basicConstraints: !0
      }, p = 0; s === null && p < u.extensions.length; ++p) {
        var h = u.extensions[p];
        h.critical && !(h.name in v) && (s = {
          message: "Certificate has an unsupported critical extension.",
          error: J.certificateError.unsupported_certificate
        });
      }
    if (s === null && (!i || t.length === 0 && (!l || c))) {
      var x = u.getExtension("basicConstraints"), y = u.getExtension("keyUsage");
      if (y !== null && (!y.keyCertSign || x === null) && (s = {
        message: "Certificate keyUsage or basicConstraints conflict or indicate that the certificate is not a CA. If the certificate is the only one in the chain or isn't the first then the certificate must be a valid CA.",
        error: J.certificateError.bad_certificate
      }), s === null && x !== null && !x.cA && (s = {
        message: "Certificate basicConstraints indicates the certificate is not a CA.",
        error: J.certificateError.bad_certificate
      }), s === null && y !== null && "pathLenConstraint" in x) {
        var g = o - 1;
        g > x.pathLenConstraint && (s = {
          message: "Certificate basicConstraints pathLenConstraint violated.",
          error: J.certificateError.bad_certificate
        });
      }
    }
    var m = s === null ? !0 : s.error, C = a.verify ? a.verify(m, o, r) : m;
    if (C === !0)
      s = null;
    else
      throw m === !0 && (s = {
        message: "The application rejected the certificate.",
        error: J.certificateError.bad_certificate
      }), (C || C === 0) && (typeof C == "object" && !Ce.util.isArray(C) ? (C.message && (s.message = C.message), C.error && (s.error = C.error)) : typeof C == "string" && (s.error = C)), s;
    i = !1, ++o;
  } while (t.length > 0);
  return !0;
};
var Ye = Re, w = Ye.asn1, ke = Ye.pki, La = Ye.pkcs12 = Ye.pkcs12 || {}, kl = {
  name: "ContentInfo",
  tagClass: w.Class.UNIVERSAL,
  type: w.Type.SEQUENCE,
  // a ContentInfo
  constructed: !0,
  value: [{
    name: "ContentInfo.contentType",
    tagClass: w.Class.UNIVERSAL,
    type: w.Type.OID,
    constructed: !1,
    capture: "contentType"
  }, {
    name: "ContentInfo.content",
    tagClass: w.Class.CONTEXT_SPECIFIC,
    constructed: !0,
    captureAsn1: "content"
  }]
}, rx = {
  name: "PFX",
  tagClass: w.Class.UNIVERSAL,
  type: w.Type.SEQUENCE,
  constructed: !0,
  value: [
    {
      name: "PFX.version",
      tagClass: w.Class.UNIVERSAL,
      type: w.Type.INTEGER,
      constructed: !1,
      capture: "version"
    },
    kl,
    {
      name: "PFX.macData",
      tagClass: w.Class.UNIVERSAL,
      type: w.Type.SEQUENCE,
      constructed: !0,
      optional: !0,
      captureAsn1: "mac",
      value: [{
        name: "PFX.macData.mac",
        tagClass: w.Class.UNIVERSAL,
        type: w.Type.SEQUENCE,
        // DigestInfo
        constructed: !0,
        value: [{
          name: "PFX.macData.mac.digestAlgorithm",
          tagClass: w.Class.UNIVERSAL,
          type: w.Type.SEQUENCE,
          // DigestAlgorithmIdentifier
          constructed: !0,
          value: [{
            name: "PFX.macData.mac.digestAlgorithm.algorithm",
            tagClass: w.Class.UNIVERSAL,
            type: w.Type.OID,
            constructed: !1,
            capture: "macAlgorithm"
          }, {
            name: "PFX.macData.mac.digestAlgorithm.parameters",
            tagClass: w.Class.UNIVERSAL,
            captureAsn1: "macAlgorithmParameters"
          }]
        }, {
          name: "PFX.macData.mac.digest",
          tagClass: w.Class.UNIVERSAL,
          type: w.Type.OCTETSTRING,
          constructed: !1,
          capture: "macDigest"
        }]
      }, {
        name: "PFX.macData.macSalt",
        tagClass: w.Class.UNIVERSAL,
        type: w.Type.OCTETSTRING,
        constructed: !1,
        capture: "macSalt"
      }, {
        name: "PFX.macData.iterations",
        tagClass: w.Class.UNIVERSAL,
        type: w.Type.INTEGER,
        constructed: !1,
        optional: !0,
        capture: "macIterations"
      }]
    }
  ]
}, ax = {
  name: "SafeBag",
  tagClass: w.Class.UNIVERSAL,
  type: w.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "SafeBag.bagId",
    tagClass: w.Class.UNIVERSAL,
    type: w.Type.OID,
    constructed: !1,
    capture: "bagId"
  }, {
    name: "SafeBag.bagValue",
    tagClass: w.Class.CONTEXT_SPECIFIC,
    constructed: !0,
    captureAsn1: "bagValue"
  }, {
    name: "SafeBag.bagAttributes",
    tagClass: w.Class.UNIVERSAL,
    type: w.Type.SET,
    constructed: !0,
    optional: !0,
    capture: "bagAttributes"
  }]
}, nx = {
  name: "Attribute",
  tagClass: w.Class.UNIVERSAL,
  type: w.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "Attribute.attrId",
    tagClass: w.Class.UNIVERSAL,
    type: w.Type.OID,
    constructed: !1,
    capture: "oid"
  }, {
    name: "Attribute.attrValues",
    tagClass: w.Class.UNIVERSAL,
    type: w.Type.SET,
    constructed: !0,
    capture: "values"
  }]
}, ix = {
  name: "CertBag",
  tagClass: w.Class.UNIVERSAL,
  type: w.Type.SEQUENCE,
  constructed: !0,
  value: [{
    name: "CertBag.certId",
    tagClass: w.Class.UNIVERSAL,
    type: w.Type.OID,
    constructed: !1,
    capture: "certId"
  }, {
    name: "CertBag.certValue",
    tagClass: w.Class.CONTEXT_SPECIFIC,
    constructed: !0,
    /* So far we only support X.509 certificates (which are wrapped in
       an OCTET STRING, hence hard code that here). */
    value: [{
      name: "CertBag.certValue[0]",
      tagClass: w.Class.UNIVERSAL,
      type: w.Class.OCTETSTRING,
      constructed: !1,
      capture: "cert"
    }]
  }]
};
function ma(e, t, a, r) {
  for (var n = [], i = 0; i < e.length; i++)
    for (var s = 0; s < e[i].safeBags.length; s++) {
      var o = e[i].safeBags[s];
      if (!(r !== void 0 && o.type !== r)) {
        if (t === null) {
          n.push(o);
          continue;
        }
        o.attributes[t] !== void 0 && o.attributes[t].indexOf(a) >= 0 && n.push(o);
      }
    }
  return n;
}
La.pkcs12FromAsn1 = function(e, t, a) {
  typeof t == "string" ? (a = t, t = !0) : t === void 0 && (t = !0);
  var r = {}, n = [];
  if (!w.validate(e, rx, r, n)) {
    var i = new Error("Cannot read PKCS#12 PFX. ASN.1 object is not an PKCS#12 PFX.");
    throw i.errors = i, i;
  }
  var s = {
    version: r.version.charCodeAt(0),
    safeContents: [],
    /**
     * Gets bags with matching attributes.
     *
     * @param filter the attributes to filter by:
     *          [localKeyId] the localKeyId to search for.
     *          [localKeyIdHex] the localKeyId in hex to search for.
     *          [friendlyName] the friendly name to search for.
     *          [bagType] bag type to narrow each attribute search by.
     *
     * @return a map of attribute type to an array of matching bags or, if no
     *           attribute was given but a bag type, the map key will be the
     *           bag type.
     */
    getBags: function(x) {
      var y = {}, g;
      return "localKeyId" in x ? g = x.localKeyId : "localKeyIdHex" in x && (g = Ye.util.hexToBytes(x.localKeyIdHex)), g === void 0 && !("friendlyName" in x) && "bagType" in x && (y[x.bagType] = ma(
        s.safeContents,
        null,
        null,
        x.bagType
      )), g !== void 0 && (y.localKeyId = ma(
        s.safeContents,
        "localKeyId",
        g,
        x.bagType
      )), "friendlyName" in x && (y.friendlyName = ma(
        s.safeContents,
        "friendlyName",
        x.friendlyName,
        x.bagType
      )), y;
    },
    /**
     * DEPRECATED: use getBags() instead.
     *
     * Get bags with matching friendlyName attribute.
     *
     * @param friendlyName the friendly name to search for.
     * @param [bagType] bag type to narrow search by.
     *
     * @return an array of bags with matching friendlyName attribute.
     */
    getBagsByFriendlyName: function(x, y) {
      return ma(
        s.safeContents,
        "friendlyName",
        x,
        y
      );
    },
    /**
     * DEPRECATED: use getBags() instead.
     *
     * Get bags with matching localKeyId attribute.
     *
     * @param localKeyId the localKeyId to search for.
     * @param [bagType] bag type to narrow search by.
     *
     * @return an array of bags with matching localKeyId attribute.
     */
    getBagsByLocalKeyId: function(x, y) {
      return ma(
        s.safeContents,
        "localKeyId",
        x,
        y
      );
    }
  };
  if (r.version.charCodeAt(0) !== 3) {
    var i = new Error("PKCS#12 PFX of version other than 3 not supported.");
    throw i.version = r.version.charCodeAt(0), i;
  }
  if (w.derToOid(r.contentType) !== ke.oids.data) {
    var i = new Error("Only PKCS#12 PFX in password integrity mode supported.");
    throw i.oid = w.derToOid(r.contentType), i;
  }
  var o = r.content.value[0];
  if (o.tagClass !== w.Class.UNIVERSAL || o.type !== w.Type.OCTETSTRING)
    throw new Error("PKCS#12 authSafe content data is not an OCTET STRING.");
  if (o = S0(o), r.mac) {
    var u = null, l = 0, c = w.derToOid(r.macAlgorithm);
    switch (c) {
      case ke.oids.sha1:
        u = Ye.md.sha1.create(), l = 20;
        break;
      case ke.oids.sha256:
        u = Ye.md.sha256.create(), l = 32;
        break;
      case ke.oids.sha384:
        u = Ye.md.sha384.create(), l = 48;
        break;
      case ke.oids.sha512:
        u = Ye.md.sha512.create(), l = 64;
        break;
      case ke.oids.md5:
        u = Ye.md.md5.create(), l = 16;
        break;
    }
    if (u === null)
      throw new Error("PKCS#12 uses unsupported MAC algorithm: " + c);
    var f = new Ye.util.ByteBuffer(r.macSalt), d = "macIterations" in r ? parseInt(Ye.util.bytesToHex(r.macIterations), 16) : 1, v = La.generateKey(
      a,
      f,
      3,
      d,
      l,
      u
    ), p = Ye.hmac.create();
    p.start(u, v), p.update(o.value);
    var h = p.getMac();
    if (h.getBytes() !== r.macDigest)
      throw new Error("PKCS#12 MAC could not be verified. Invalid password?");
  }
  return sx(s, o.value, t, a), s;
};
function S0(e) {
  if (e.composed || e.constructed) {
    for (var t = Ye.util.createBuffer(), a = 0; a < e.value.length; ++a)
      t.putBytes(e.value[a].value);
    e.composed = e.constructed = !1, e.value = t.getBytes();
  }
  return e;
}
function sx(e, t, a, r) {
  if (t = w.fromDer(t, a), t.tagClass !== w.Class.UNIVERSAL || t.type !== w.Type.SEQUENCE || t.constructed !== !0)
    throw new Error("PKCS#12 AuthenticatedSafe expected to be a SEQUENCE OF ContentInfo");
  for (var n = 0; n < t.value.length; n++) {
    var i = t.value[n], s = {}, o = [];
    if (!w.validate(i, kl, s, o)) {
      var u = new Error("Cannot read ContentInfo.");
      throw u.errors = o, u;
    }
    var l = {
      encrypted: !1
    }, c = null, f = s.content.value[0];
    switch (w.derToOid(s.contentType)) {
      case ke.oids.data:
        if (f.tagClass !== w.Class.UNIVERSAL || f.type !== w.Type.OCTETSTRING)
          throw new Error("PKCS#12 SafeContents Data is not an OCTET STRING.");
        c = S0(f).value;
        break;
      case ke.oids.encryptedData:
        c = ox(f, r), l.encrypted = !0;
        break;
      default:
        var u = new Error("Unsupported PKCS#12 contentType.");
        throw u.contentType = w.derToOid(s.contentType), u;
    }
    l.safeBags = lx(c, a, r), e.safeContents.push(l);
  }
}
function ox(e, t) {
  var a = {}, r = [];
  if (!w.validate(
    e,
    Ye.pkcs7.asn1.encryptedDataValidator,
    a,
    r
  )) {
    var n = new Error("Cannot read EncryptedContentInfo.");
    throw n.errors = r, n;
  }
  var i = w.derToOid(a.contentType);
  if (i !== ke.oids.data) {
    var n = new Error(
      "PKCS#12 EncryptedContentInfo ContentType is not Data."
    );
    throw n.oid = i, n;
  }
  i = w.derToOid(a.encAlgorithm);
  var s = ke.pbe.getCipher(i, a.encParameter, t), o = S0(a.encryptedContentAsn1), u = Ye.util.createBuffer(o.value);
  if (s.update(u), !s.finish())
    throw new Error("Failed to decrypt PKCS#12 SafeContents.");
  return s.output.getBytes();
}
function lx(e, t, a) {
  if (!t && e.length === 0)
    return [];
  if (e = w.fromDer(e, t), e.tagClass !== w.Class.UNIVERSAL || e.type !== w.Type.SEQUENCE || e.constructed !== !0)
    throw new Error(
      "PKCS#12 SafeContents expected to be a SEQUENCE OF SafeBag."
    );
  for (var r = [], n = 0; n < e.value.length; n++) {
    var i = e.value[n], s = {}, o = [];
    if (!w.validate(i, ax, s, o)) {
      var u = new Error("Cannot read SafeBag.");
      throw u.errors = o, u;
    }
    var l = {
      type: w.derToOid(s.bagId),
      attributes: cx(s.bagAttributes)
    };
    r.push(l);
    var c, f, d = s.bagValue.value[0];
    switch (l.type) {
      case ke.oids.pkcs8ShroudedKeyBag:
        if (d = ke.decryptPrivateKeyInfo(d, a), d === null)
          throw new Error(
            "Unable to decrypt PKCS#8 ShroudedKeyBag, wrong password?"
          );
      case ke.oids.keyBag:
        try {
          l.key = ke.privateKeyFromAsn1(d);
        } catch {
          l.key = null, l.asn1 = d;
        }
        continue;
      case ke.oids.certBag:
        c = ix, f = function() {
          if (w.derToOid(s.certId) !== ke.oids.x509Certificate) {
            var p = new Error(
              "Unsupported certificate type, only X.509 supported."
            );
            throw p.oid = w.derToOid(s.certId), p;
          }
          var h = w.fromDer(s.cert, t);
          try {
            l.cert = ke.certificateFromAsn1(h, !0);
          } catch {
            l.cert = null, l.asn1 = h;
          }
        };
        break;
      default:
        var u = new Error("Unsupported PKCS#12 SafeBag type.");
        throw u.oid = l.type, u;
    }
    if (c !== void 0 && !w.validate(d, c, s, o)) {
      var u = new Error("Cannot read PKCS#12 " + c.name);
      throw u.errors = o, u;
    }
    f();
  }
  return r;
}
function cx(e) {
  var t = {};
  if (e !== void 0)
    for (var a = 0; a < e.length; ++a) {
      var r = {}, n = [];
      if (!w.validate(e[a], nx, r, n)) {
        var i = new Error("Cannot read PKCS#12 BagAttribute.");
        throw i.errors = n, i;
      }
      var s = w.derToOid(r.oid);
      if (ke.oids[s] !== void 0) {
        t[ke.oids[s]] = [];
        for (var o = 0; o < r.values.length; ++o)
          t[ke.oids[s]].push(r.values[o].value);
      }
    }
  return t;
}
La.toPkcs12Asn1 = function(e, t, a, r) {
  r = r || {}, r.saltSize = r.saltSize || 8, r.count = r.count || 2048, r.algorithm = r.algorithm || r.encAlgorithm || "aes128", "useMac" in r || (r.useMac = !0), "localKeyId" in r || (r.localKeyId = null), "generateLocalKeyId" in r || (r.generateLocalKeyId = !0);
  var n = r.localKeyId, i;
  if (n !== null)
    n = Ye.util.hexToBytes(n);
  else if (r.generateLocalKeyId)
    if (t) {
      var s = Ye.util.isArray(t) ? t[0] : t;
      typeof s == "string" && (s = ke.certificateFromPem(s));
      var o = Ye.md.sha1.create();
      o.update(w.toDer(ke.certificateToAsn1(s)).getBytes()), n = o.digest().getBytes();
    } else
      n = Ye.random.getBytes(20);
  var u = [];
  n !== null && u.push(
    // localKeyID
    w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
      // attrId
      w.create(
        w.Class.UNIVERSAL,
        w.Type.OID,
        !1,
        w.oidToDer(ke.oids.localKeyId).getBytes()
      ),
      // attrValues
      w.create(w.Class.UNIVERSAL, w.Type.SET, !0, [
        w.create(
          w.Class.UNIVERSAL,
          w.Type.OCTETSTRING,
          !1,
          n
        )
      ])
    ])
  ), "friendlyName" in r && u.push(
    // friendlyName
    w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
      // attrId
      w.create(
        w.Class.UNIVERSAL,
        w.Type.OID,
        !1,
        w.oidToDer(ke.oids.friendlyName).getBytes()
      ),
      // attrValues
      w.create(w.Class.UNIVERSAL, w.Type.SET, !0, [
        w.create(
          w.Class.UNIVERSAL,
          w.Type.BMPSTRING,
          !1,
          r.friendlyName
        )
      ])
    ])
  ), u.length > 0 && (i = w.create(w.Class.UNIVERSAL, w.Type.SET, !0, u));
  var l = [], c = [];
  t !== null && (Ye.util.isArray(t) ? c = t : c = [t]);
  for (var f = [], d = 0; d < c.length; ++d) {
    t = c[d], typeof t == "string" && (t = ke.certificateFromPem(t));
    var v = d === 0 ? i : void 0, p = ke.certificateToAsn1(t), h = w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
      // bagId
      w.create(
        w.Class.UNIVERSAL,
        w.Type.OID,
        !1,
        w.oidToDer(ke.oids.certBag).getBytes()
      ),
      // bagValue
      w.create(w.Class.CONTEXT_SPECIFIC, 0, !0, [
        // CertBag
        w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
          // certId
          w.create(
            w.Class.UNIVERSAL,
            w.Type.OID,
            !1,
            w.oidToDer(ke.oids.x509Certificate).getBytes()
          ),
          // certValue (x509Certificate)
          w.create(w.Class.CONTEXT_SPECIFIC, 0, !0, [
            w.create(
              w.Class.UNIVERSAL,
              w.Type.OCTETSTRING,
              !1,
              w.toDer(p).getBytes()
            )
          ])
        ])
      ]),
      // bagAttributes (OPTIONAL)
      v
    ]);
    f.push(h);
  }
  if (f.length > 0) {
    var x = w.create(
      w.Class.UNIVERSAL,
      w.Type.SEQUENCE,
      !0,
      f
    ), y = (
      // PKCS#7 ContentInfo
      w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
        // contentType
        w.create(
          w.Class.UNIVERSAL,
          w.Type.OID,
          !1,
          // OID for the content type is 'data'
          w.oidToDer(ke.oids.data).getBytes()
        ),
        // content
        w.create(w.Class.CONTEXT_SPECIFIC, 0, !0, [
          w.create(
            w.Class.UNIVERSAL,
            w.Type.OCTETSTRING,
            !1,
            w.toDer(x).getBytes()
          )
        ])
      ])
    );
    l.push(y);
  }
  var g = null;
  if (e !== null) {
    var m = ke.wrapRsaPrivateKey(ke.privateKeyToAsn1(e));
    a === null ? g = w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
      // bagId
      w.create(
        w.Class.UNIVERSAL,
        w.Type.OID,
        !1,
        w.oidToDer(ke.oids.keyBag).getBytes()
      ),
      // bagValue
      w.create(w.Class.CONTEXT_SPECIFIC, 0, !0, [
        // PrivateKeyInfo
        m
      ]),
      // bagAttributes (OPTIONAL)
      i
    ]) : g = w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
      // bagId
      w.create(
        w.Class.UNIVERSAL,
        w.Type.OID,
        !1,
        w.oidToDer(ke.oids.pkcs8ShroudedKeyBag).getBytes()
      ),
      // bagValue
      w.create(w.Class.CONTEXT_SPECIFIC, 0, !0, [
        // EncryptedPrivateKeyInfo
        ke.encryptPrivateKeyInfo(m, a, r)
      ]),
      // bagAttributes (OPTIONAL)
      i
    ]);
    var C = w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [g]), S = (
      // PKCS#7 ContentInfo
      w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
        // contentType
        w.create(
          w.Class.UNIVERSAL,
          w.Type.OID,
          !1,
          // OID for the content type is 'data'
          w.oidToDer(ke.oids.data).getBytes()
        ),
        // content
        w.create(w.Class.CONTEXT_SPECIFIC, 0, !0, [
          w.create(
            w.Class.UNIVERSAL,
            w.Type.OCTETSTRING,
            !1,
            w.toDer(C).getBytes()
          )
        ])
      ])
    );
    l.push(S);
  }
  var _ = w.create(
    w.Class.UNIVERSAL,
    w.Type.SEQUENCE,
    !0,
    l
  ), T;
  if (r.useMac) {
    var o = Ye.md.sha1.create(), D = new Ye.util.ByteBuffer(
      Ye.random.getBytes(r.saltSize)
    ), P = r.count, e = La.generateKey(a, D, 3, P, 20), I = Ye.hmac.create();
    I.start(o, e), I.update(w.toDer(_).getBytes());
    var B = I.getMac();
    T = w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
      // mac DigestInfo
      w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
        // digestAlgorithm
        w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
          // algorithm = SHA-1
          w.create(
            w.Class.UNIVERSAL,
            w.Type.OID,
            !1,
            w.oidToDer(ke.oids.sha1).getBytes()
          ),
          // parameters = Null
          w.create(w.Class.UNIVERSAL, w.Type.NULL, !1, "")
        ]),
        // digest
        w.create(
          w.Class.UNIVERSAL,
          w.Type.OCTETSTRING,
          !1,
          B.getBytes()
        )
      ]),
      // macSalt OCTET STRING
      w.create(
        w.Class.UNIVERSAL,
        w.Type.OCTETSTRING,
        !1,
        D.getBytes()
      ),
      // iterations INTEGER (XXX: Only support count < 65536)
      w.create(
        w.Class.UNIVERSAL,
        w.Type.INTEGER,
        !1,
        w.integerToDer(P).getBytes()
      )
    ]);
  }
  return w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
    // version (3)
    w.create(
      w.Class.UNIVERSAL,
      w.Type.INTEGER,
      !1,
      w.integerToDer(3).getBytes()
    ),
    // PKCS#7 ContentInfo
    w.create(w.Class.UNIVERSAL, w.Type.SEQUENCE, !0, [
      // contentType
      w.create(
        w.Class.UNIVERSAL,
        w.Type.OID,
        !1,
        // OID for the content type is 'data'
        w.oidToDer(ke.oids.data).getBytes()
      ),
      // content
      w.create(w.Class.CONTEXT_SPECIFIC, 0, !0, [
        w.create(
          w.Class.UNIVERSAL,
          w.Type.OCTETSTRING,
          !1,
          w.toDer(_).getBytes()
        )
      ])
    ]),
    T
  ]);
};
La.generateKey = Ye.pbe.generatePkcs12Key;
var Er = Re, A0 = Er.asn1, la = Er.pki = Er.pki || {};
la.pemToDer = function(e) {
  var t = Er.pem.decode(e)[0];
  if (t.procType && t.procType.type === "ENCRYPTED")
    throw new Error("Could not convert PEM to DER; PEM is encrypted.");
  return Er.util.createBuffer(t.body);
};
la.privateKeyFromPem = function(e) {
  var t = Er.pem.decode(e)[0];
  if (t.type !== "PRIVATE KEY" && t.type !== "RSA PRIVATE KEY") {
    var a = new Error('Could not convert private key from PEM; PEM header type is not "PRIVATE KEY" or "RSA PRIVATE KEY".');
    throw a.headerType = t.type, a;
  }
  if (t.procType && t.procType.type === "ENCRYPTED")
    throw new Error("Could not convert private key from PEM; PEM is encrypted.");
  var r = A0.fromDer(t.body);
  return la.privateKeyFromAsn1(r);
};
la.privateKeyToPem = function(e, t) {
  var a = {
    type: "RSA PRIVATE KEY",
    body: A0.toDer(la.privateKeyToAsn1(e)).getBytes()
  };
  return Er.pem.encode(a, { maxline: t });
};
la.privateKeyInfoToPem = function(e, t) {
  var a = {
    type: "PRIVATE KEY",
    body: A0.toDer(e).getBytes()
  };
  return Er.pem.encode(a, { maxline: t });
};
var j = Re, Pn = function(e, t, a, r) {
  var n = j.util.createBuffer(), i = e.length >> 1, s = i + (e.length & 1), o = e.substr(0, s), u = e.substr(i, s), l = j.util.createBuffer(), c = j.hmac.create();
  a = t + a;
  var f = Math.ceil(r / 16), d = Math.ceil(r / 20);
  c.start("MD5", o);
  var v = j.util.createBuffer();
  l.putBytes(a);
  for (var p = 0; p < f; ++p)
    c.start(null, null), c.update(l.getBytes()), l.putBuffer(c.digest()), c.start(null, null), c.update(l.bytes() + a), v.putBuffer(c.digest());
  c.start("SHA1", u);
  var h = j.util.createBuffer();
  l.clear(), l.putBytes(a);
  for (var p = 0; p < d; ++p)
    c.start(null, null), c.update(l.getBytes()), l.putBuffer(c.digest()), c.start(null, null), c.update(l.bytes() + a), h.putBuffer(c.digest());
  return n.putBytes(j.util.xorBytes(
    v.getBytes(),
    h.getBytes(),
    r
  )), n;
}, ux = function(e, t, a) {
  var r = j.hmac.create();
  r.start("SHA1", e);
  var n = j.util.createBuffer();
  return n.putInt32(t[0]), n.putInt32(t[1]), n.putByte(a.type), n.putByte(a.version.major), n.putByte(a.version.minor), n.putInt16(a.length), n.putBytes(a.fragment.bytes()), r.update(n.getBytes()), r.digest().getBytes();
}, fx = function(e, t, a) {
  var r = !1;
  try {
    var n = e.deflate(t.fragment.getBytes());
    t.fragment = j.util.createBuffer(n), t.length = n.length, r = !0;
  } catch {
  }
  return r;
}, dx = function(e, t, a) {
  var r = !1;
  try {
    var n = e.inflate(t.fragment.getBytes());
    t.fragment = j.util.createBuffer(n), t.length = n.length, r = !0;
  } catch {
  }
  return r;
}, wt = function(e, t) {
  var a = 0;
  switch (t) {
    case 1:
      a = e.getByte();
      break;
    case 2:
      a = e.getInt16();
      break;
    case 3:
      a = e.getInt24();
      break;
    case 4:
      a = e.getInt32();
      break;
  }
  return j.util.createBuffer(e.getBytes(a));
}, kt = function(e, t, a) {
  e.putInt(a.length(), t << 3), e.putBuffer(a);
}, A = {};
A.Versions = {
  TLS_1_0: { major: 3, minor: 1 },
  TLS_1_1: { major: 3, minor: 2 },
  TLS_1_2: { major: 3, minor: 3 }
};
A.SupportedVersions = [
  A.Versions.TLS_1_1,
  A.Versions.TLS_1_0
];
A.Version = A.SupportedVersions[0];
A.MaxFragment = 15360;
A.ConnectionEnd = {
  server: 0,
  client: 1
};
A.PRFAlgorithm = {
  tls_prf_sha256: 0
};
A.BulkCipherAlgorithm = {
  none: null,
  rc4: 0,
  des3: 1,
  aes: 2
};
A.CipherType = {
  stream: 0,
  block: 1,
  aead: 2
};
A.MACAlgorithm = {
  none: null,
  hmac_md5: 0,
  hmac_sha1: 1,
  hmac_sha256: 2,
  hmac_sha384: 3,
  hmac_sha512: 4
};
A.CompressionMethod = {
  none: 0,
  deflate: 1
};
A.ContentType = {
  change_cipher_spec: 20,
  alert: 21,
  handshake: 22,
  application_data: 23,
  heartbeat: 24
};
A.HandshakeType = {
  hello_request: 0,
  client_hello: 1,
  server_hello: 2,
  certificate: 11,
  server_key_exchange: 12,
  certificate_request: 13,
  server_hello_done: 14,
  certificate_verify: 15,
  client_key_exchange: 16,
  finished: 20
};
A.Alert = {};
A.Alert.Level = {
  warning: 1,
  fatal: 2
};
A.Alert.Description = {
  close_notify: 0,
  unexpected_message: 10,
  bad_record_mac: 20,
  decryption_failed: 21,
  record_overflow: 22,
  decompression_failure: 30,
  handshake_failure: 40,
  bad_certificate: 42,
  unsupported_certificate: 43,
  certificate_revoked: 44,
  certificate_expired: 45,
  certificate_unknown: 46,
  illegal_parameter: 47,
  unknown_ca: 48,
  access_denied: 49,
  decode_error: 50,
  decrypt_error: 51,
  export_restriction: 60,
  protocol_version: 70,
  insufficient_security: 71,
  internal_error: 80,
  user_canceled: 90,
  no_renegotiation: 100
};
A.HeartbeatMessageType = {
  heartbeat_request: 1,
  heartbeat_response: 2
};
A.CipherSuites = {};
A.getCipherSuite = function(e) {
  var t = null;
  for (var a in A.CipherSuites) {
    var r = A.CipherSuites[a];
    if (r.id[0] === e.charCodeAt(0) && r.id[1] === e.charCodeAt(1)) {
      t = r;
      break;
    }
  }
  return t;
};
A.handleUnexpected = function(e, t) {
  var a = !e.open && e.entity === A.ConnectionEnd.client;
  a || e.error(e, {
    message: "Unexpected message. Received TLS record out of order.",
    send: !0,
    alert: {
      level: A.Alert.Level.fatal,
      description: A.Alert.Description.unexpected_message
    }
  });
};
A.handleHelloRequest = function(e, t, a) {
  !e.handshaking && e.handshakes > 0 && (A.queue(e, A.createAlert(e, {
    level: A.Alert.Level.warning,
    description: A.Alert.Description.no_renegotiation
  })), A.flush(e)), e.process();
};
A.parseHelloMessage = function(e, t, a) {
  var r = null, n = e.entity === A.ConnectionEnd.client;
  if (a < 38)
    e.error(e, {
      message: n ? "Invalid ServerHello message. Message too short." : "Invalid ClientHello message. Message too short.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.illegal_parameter
      }
    });
  else {
    var i = t.fragment, s = i.length();
    if (r = {
      version: {
        major: i.getByte(),
        minor: i.getByte()
      },
      random: j.util.createBuffer(i.getBytes(32)),
      session_id: wt(i, 1),
      extensions: []
    }, n ? (r.cipher_suite = i.getBytes(2), r.compression_method = i.getByte()) : (r.cipher_suites = wt(i, 2), r.compression_methods = wt(i, 1)), s = a - (s - i.length()), s > 0) {
      for (var o = wt(i, 2); o.length() > 0; )
        r.extensions.push({
          type: [o.getByte(), o.getByte()],
          data: wt(o, 2)
        });
      if (!n)
        for (var u = 0; u < r.extensions.length; ++u) {
          var l = r.extensions[u];
          if (l.type[0] === 0 && l.type[1] === 0)
            for (var c = wt(l.data, 2); c.length() > 0; ) {
              var f = c.getByte();
              if (f !== 0)
                break;
              e.session.extensions.server_name.serverNameList.push(
                wt(c, 2).getBytes()
              );
            }
        }
    }
    if (e.session.version && (r.version.major !== e.session.version.major || r.version.minor !== e.session.version.minor))
      return e.error(e, {
        message: "TLS version change is disallowed during renegotiation.",
        send: !0,
        alert: {
          level: A.Alert.Level.fatal,
          description: A.Alert.Description.protocol_version
        }
      });
    if (n)
      e.session.cipherSuite = A.getCipherSuite(r.cipher_suite);
    else
      for (var d = j.util.createBuffer(r.cipher_suites.bytes()); d.length() > 0 && (e.session.cipherSuite = A.getCipherSuite(d.getBytes(2)), e.session.cipherSuite === null); )
        ;
    if (e.session.cipherSuite === null)
      return e.error(e, {
        message: "No cipher suites in common.",
        send: !0,
        alert: {
          level: A.Alert.Level.fatal,
          description: A.Alert.Description.handshake_failure
        },
        cipherSuite: j.util.bytesToHex(r.cipher_suite)
      });
    n ? e.session.compressionMethod = r.compression_method : e.session.compressionMethod = A.CompressionMethod.none;
  }
  return r;
};
A.createSecurityParameters = function(e, t) {
  var a = e.entity === A.ConnectionEnd.client, r = t.random.bytes(), n = a ? e.session.sp.client_random : r, i = a ? r : A.createRandom().getBytes();
  e.session.sp = {
    entity: e.entity,
    prf_algorithm: A.PRFAlgorithm.tls_prf_sha256,
    bulk_cipher_algorithm: null,
    cipher_type: null,
    enc_key_length: null,
    block_length: null,
    fixed_iv_length: null,
    record_iv_length: null,
    mac_algorithm: null,
    mac_length: null,
    mac_key_length: null,
    compression_algorithm: e.session.compressionMethod,
    pre_master_secret: null,
    master_secret: null,
    client_random: n,
    server_random: i
  };
};
A.handleServerHello = function(e, t, a) {
  var r = A.parseHelloMessage(e, t, a);
  if (!e.fail) {
    if (r.version.minor <= e.version.minor)
      e.version.minor = r.version.minor;
    else
      return e.error(e, {
        message: "Incompatible TLS version.",
        send: !0,
        alert: {
          level: A.Alert.Level.fatal,
          description: A.Alert.Description.protocol_version
        }
      });
    e.session.version = e.version;
    var n = r.session_id.bytes();
    n.length > 0 && n === e.session.id ? (e.expect = Fl, e.session.resuming = !0, e.session.sp.server_random = r.random.bytes()) : (e.expect = hx, e.session.resuming = !1, A.createSecurityParameters(e, r)), e.session.id = n, e.process();
  }
};
A.handleClientHello = function(e, t, a) {
  var r = A.parseHelloMessage(e, t, a);
  if (!e.fail) {
    var n = r.session_id.bytes(), i = null;
    if (e.sessionCache && (i = e.sessionCache.getSession(n), i === null ? n = "" : (i.version.major !== r.version.major || i.version.minor > r.version.minor) && (i = null, n = "")), n.length === 0 && (n = j.random.getBytes(32)), e.session.id = n, e.session.clientHelloVersion = r.version, e.session.sp = {}, i)
      e.version = e.session.version = i.version, e.session.sp = i.sp;
    else {
      for (var s, o = 1; o < A.SupportedVersions.length && (s = A.SupportedVersions[o], !(s.minor <= r.version.minor)); ++o)
        ;
      e.version = { major: s.major, minor: s.minor }, e.session.version = e.version;
    }
    i !== null ? (e.expect = _0, e.session.resuming = !0, e.session.sp.client_random = r.random.bytes()) : (e.expect = e.verifyClient !== !1 ? Ex : Gi, e.session.resuming = !1, A.createSecurityParameters(e, r)), e.open = !0, A.queue(e, A.createRecord(e, {
      type: A.ContentType.handshake,
      data: A.createServerHello(e)
    })), e.session.resuming ? (A.queue(e, A.createRecord(e, {
      type: A.ContentType.change_cipher_spec,
      data: A.createChangeCipherSpec()
    })), e.state.pending = A.createConnectionState(e), e.state.current.write = e.state.pending.write, A.queue(e, A.createRecord(e, {
      type: A.ContentType.handshake,
      data: A.createFinished(e)
    }))) : (A.queue(e, A.createRecord(e, {
      type: A.ContentType.handshake,
      data: A.createCertificate(e)
    })), e.fail || (A.queue(e, A.createRecord(e, {
      type: A.ContentType.handshake,
      data: A.createServerKeyExchange(e)
    })), e.verifyClient !== !1 && A.queue(e, A.createRecord(e, {
      type: A.ContentType.handshake,
      data: A.createCertificateRequest(e)
    })), A.queue(e, A.createRecord(e, {
      type: A.ContentType.handshake,
      data: A.createServerHelloDone(e)
    })))), A.flush(e), e.process();
  }
};
A.handleCertificate = function(e, t, a) {
  if (a < 3)
    return e.error(e, {
      message: "Invalid Certificate message. Message too short.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.illegal_parameter
      }
    });
  var r = t.fragment, n = {
    certificate_list: wt(r, 3)
  }, i, s, o = [];
  try {
    for (; n.certificate_list.length() > 0; )
      i = wt(n.certificate_list, 3), s = j.asn1.fromDer(i), i = j.pki.certificateFromAsn1(s, !0), o.push(i);
  } catch (l) {
    return e.error(e, {
      message: "Could not parse certificate list.",
      cause: l,
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.bad_certificate
      }
    });
  }
  var u = e.entity === A.ConnectionEnd.client;
  (u || e.verifyClient === !0) && o.length === 0 ? e.error(e, {
    message: u ? "No server certificate provided." : "No client certificate provided.",
    send: !0,
    alert: {
      level: A.Alert.Level.fatal,
      description: A.Alert.Description.illegal_parameter
    }
  }) : o.length === 0 ? e.expect = u ? uo : Gi : (u ? e.session.serverCertificate = o[0] : e.session.clientCertificate = o[0], A.verifyCertificateChain(e, o) && (e.expect = u ? uo : Gi)), e.process();
};
A.handleServerKeyExchange = function(e, t, a) {
  if (a > 0)
    return e.error(e, {
      message: "Invalid key parameters. Only RSA is supported.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.unsupported_certificate
      }
    });
  e.expect = vx, e.process();
};
A.handleClientKeyExchange = function(e, t, a) {
  if (a < 48)
    return e.error(e, {
      message: "Invalid key parameters. Only RSA is supported.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.unsupported_certificate
      }
    });
  var r = t.fragment, n = {
    enc_pre_master_secret: wt(r, 2).getBytes()
  }, i = null;
  if (e.getPrivateKey)
    try {
      i = e.getPrivateKey(e, e.session.serverCertificate), i = j.pki.privateKeyFromPem(i);
    } catch (u) {
      e.error(e, {
        message: "Could not get private key.",
        cause: u,
        send: !0,
        alert: {
          level: A.Alert.Level.fatal,
          description: A.Alert.Description.internal_error
        }
      });
    }
  if (i === null)
    return e.error(e, {
      message: "No private key set.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.internal_error
      }
    });
  try {
    var s = e.session.sp;
    s.pre_master_secret = i.decrypt(n.enc_pre_master_secret);
    var o = e.session.clientHelloVersion;
    if (o.major !== s.pre_master_secret.charCodeAt(0) || o.minor !== s.pre_master_secret.charCodeAt(1))
      throw new Error("TLS version rollback attack detected.");
  } catch {
    s.pre_master_secret = j.random.getBytes(48);
  }
  e.expect = _0, e.session.clientCertificate !== null && (e.expect = bx), e.process();
};
A.handleCertificateRequest = function(e, t, a) {
  if (a < 3)
    return e.error(e, {
      message: "Invalid CertificateRequest. Message too short.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.illegal_parameter
      }
    });
  var r = t.fragment, n = {
    certificate_types: wt(r, 1),
    certificate_authorities: wt(r, 2)
  };
  e.session.certificateRequest = n, e.expect = xx, e.process();
};
A.handleCertificateVerify = function(e, t, a) {
  if (a < 2)
    return e.error(e, {
      message: "Invalid CertificateVerify. Message too short.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.illegal_parameter
      }
    });
  var r = t.fragment;
  r.read -= 4;
  var n = r.bytes();
  r.read += 4;
  var i = {
    signature: wt(r, 2).getBytes()
  }, s = j.util.createBuffer();
  s.putBuffer(e.session.md5.digest()), s.putBuffer(e.session.sha1.digest()), s = s.getBytes();
  try {
    var o = e.session.clientCertificate;
    if (!o.publicKey.verify(s, i.signature, "NONE"))
      throw new Error("CertificateVerify signature does not match.");
    e.session.md5.update(n), e.session.sha1.update(n);
  } catch {
    return e.error(e, {
      message: "Bad signature in CertificateVerify.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.handshake_failure
      }
    });
  }
  e.expect = _0, e.process();
};
A.handleServerHelloDone = function(e, t, a) {
  if (a > 0)
    return e.error(e, {
      message: "Invalid ServerHelloDone message. Invalid length.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.record_overflow
      }
    });
  if (e.serverCertificate === null) {
    var r = {
      message: "No server certificate provided. Not enough security.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.insufficient_security
      }
    }, n = 0, i = e.verify(e, r.alert.description, n, []);
    if (i !== !0)
      return (i || i === 0) && (typeof i == "object" && !j.util.isArray(i) ? (i.message && (r.message = i.message), i.alert && (r.alert.description = i.alert)) : typeof i == "number" && (r.alert.description = i)), e.error(e, r);
  }
  e.session.certificateRequest !== null && (t = A.createRecord(e, {
    type: A.ContentType.handshake,
    data: A.createCertificate(e)
  }), A.queue(e, t)), t = A.createRecord(e, {
    type: A.ContentType.handshake,
    data: A.createClientKeyExchange(e)
  }), A.queue(e, t), e.expect = mx;
  var s = function(o, u) {
    o.session.certificateRequest !== null && o.session.clientCertificate !== null && A.queue(o, A.createRecord(o, {
      type: A.ContentType.handshake,
      data: A.createCertificateVerify(o, u)
    })), A.queue(o, A.createRecord(o, {
      type: A.ContentType.change_cipher_spec,
      data: A.createChangeCipherSpec()
    })), o.state.pending = A.createConnectionState(o), o.state.current.write = o.state.pending.write, A.queue(o, A.createRecord(o, {
      type: A.ContentType.handshake,
      data: A.createFinished(o)
    })), o.expect = Fl, A.flush(o), o.process();
  };
  if (e.session.certificateRequest === null || e.session.clientCertificate === null)
    return s(e, null);
  A.getClientSignature(e, s);
};
A.handleChangeCipherSpec = function(e, t) {
  if (t.fragment.getByte() !== 1)
    return e.error(e, {
      message: "Invalid ChangeCipherSpec message received.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.illegal_parameter
      }
    });
  var a = e.entity === A.ConnectionEnd.client;
  (e.session.resuming && a || !e.session.resuming && !a) && (e.state.pending = A.createConnectionState(e)), e.state.current.read = e.state.pending.read, (!e.session.resuming && a || e.session.resuming && !a) && (e.state.pending = null), e.expect = a ? gx : Sx, e.process();
};
A.handleFinished = function(e, t, a) {
  var r = t.fragment;
  r.read -= 4;
  var n = r.bytes();
  r.read += 4;
  var i = t.fragment.getBytes();
  r = j.util.createBuffer(), r.putBuffer(e.session.md5.digest()), r.putBuffer(e.session.sha1.digest());
  var s = e.entity === A.ConnectionEnd.client, o = s ? "server finished" : "client finished", u = e.session.sp, l = 12, c = Pn;
  if (r = c(u.master_secret, o, r.getBytes(), l), r.getBytes() !== i)
    return e.error(e, {
      message: "Invalid verify_data in Finished message.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.decrypt_error
      }
    });
  e.session.md5.update(n), e.session.sha1.update(n), (e.session.resuming && s || !e.session.resuming && !s) && (A.queue(e, A.createRecord(e, {
    type: A.ContentType.change_cipher_spec,
    data: A.createChangeCipherSpec()
  })), e.state.current.write = e.state.pending.write, e.state.pending = null, A.queue(e, A.createRecord(e, {
    type: A.ContentType.handshake,
    data: A.createFinished(e)
  }))), e.expect = s ? yx : Ax, e.handshaking = !1, ++e.handshakes, e.peerCertificate = s ? e.session.serverCertificate : e.session.clientCertificate, A.flush(e), e.isConnected = !0, e.connected(e), e.process();
};
A.handleAlert = function(e, t) {
  var a = t.fragment, r = {
    level: a.getByte(),
    description: a.getByte()
  }, n;
  switch (r.description) {
    case A.Alert.Description.close_notify:
      n = "Connection closed.";
      break;
    case A.Alert.Description.unexpected_message:
      n = "Unexpected message.";
      break;
    case A.Alert.Description.bad_record_mac:
      n = "Bad record MAC.";
      break;
    case A.Alert.Description.decryption_failed:
      n = "Decryption failed.";
      break;
    case A.Alert.Description.record_overflow:
      n = "Record overflow.";
      break;
    case A.Alert.Description.decompression_failure:
      n = "Decompression failed.";
      break;
    case A.Alert.Description.handshake_failure:
      n = "Handshake failure.";
      break;
    case A.Alert.Description.bad_certificate:
      n = "Bad certificate.";
      break;
    case A.Alert.Description.unsupported_certificate:
      n = "Unsupported certificate.";
      break;
    case A.Alert.Description.certificate_revoked:
      n = "Certificate revoked.";
      break;
    case A.Alert.Description.certificate_expired:
      n = "Certificate expired.";
      break;
    case A.Alert.Description.certificate_unknown:
      n = "Certificate unknown.";
      break;
    case A.Alert.Description.illegal_parameter:
      n = "Illegal parameter.";
      break;
    case A.Alert.Description.unknown_ca:
      n = "Unknown certificate authority.";
      break;
    case A.Alert.Description.access_denied:
      n = "Access denied.";
      break;
    case A.Alert.Description.decode_error:
      n = "Decode error.";
      break;
    case A.Alert.Description.decrypt_error:
      n = "Decrypt error.";
      break;
    case A.Alert.Description.export_restriction:
      n = "Export restriction.";
      break;
    case A.Alert.Description.protocol_version:
      n = "Unsupported protocol version.";
      break;
    case A.Alert.Description.insufficient_security:
      n = "Insufficient security.";
      break;
    case A.Alert.Description.internal_error:
      n = "Internal error.";
      break;
    case A.Alert.Description.user_canceled:
      n = "User canceled.";
      break;
    case A.Alert.Description.no_renegotiation:
      n = "Renegotiation not supported.";
      break;
    default:
      n = "Unknown error.";
      break;
  }
  if (r.description === A.Alert.Description.close_notify)
    return e.close();
  e.error(e, {
    message: n,
    send: !1,
    // origin is the opposite end
    origin: e.entity === A.ConnectionEnd.client ? "server" : "client",
    alert: r
  }), e.process();
};
A.handleHandshake = function(e, t) {
  var a = t.fragment, r = a.getByte(), n = a.getInt24();
  if (n > a.length())
    return e.fragmented = t, t.fragment = j.util.createBuffer(), a.read -= 4, e.process();
  e.fragmented = null, a.read -= 4;
  var i = a.bytes(n + 4);
  a.read += 4, r in hn[e.entity][e.expect] ? (e.entity === A.ConnectionEnd.server && !e.open && !e.fail && (e.handshaking = !0, e.session = {
    version: null,
    extensions: {
      server_name: {
        serverNameList: []
      }
    },
    cipherSuite: null,
    compressionMethod: null,
    serverCertificate: null,
    clientCertificate: null,
    md5: j.md.md5.create(),
    sha1: j.md.sha1.create()
  }), r !== A.HandshakeType.hello_request && r !== A.HandshakeType.certificate_verify && r !== A.HandshakeType.finished && (e.session.md5.update(i), e.session.sha1.update(i)), hn[e.entity][e.expect][r](e, t, n)) : A.handleUnexpected(e, t);
};
A.handleApplicationData = function(e, t) {
  e.data.putBuffer(t.fragment), e.dataReady(e), e.process();
};
A.handleHeartbeat = function(e, t) {
  var a = t.fragment, r = a.getByte(), n = a.getInt16(), i = a.getBytes(n);
  if (r === A.HeartbeatMessageType.heartbeat_request) {
    if (e.handshaking || n > i.length)
      return e.process();
    A.queue(e, A.createRecord(e, {
      type: A.ContentType.heartbeat,
      data: A.createHeartbeat(
        A.HeartbeatMessageType.heartbeat_response,
        i
      )
    })), A.flush(e);
  } else if (r === A.HeartbeatMessageType.heartbeat_response) {
    if (i !== e.expectedHeartbeatPayload)
      return e.process();
    e.heartbeatReceived && e.heartbeatReceived(e, j.util.createBuffer(i));
  }
  e.process();
};
var px = 0, hx = 1, uo = 2, vx = 3, xx = 4, Fl = 5, gx = 6, yx = 7, mx = 8, Cx = 0, Ex = 1, Gi = 2, bx = 3, _0 = 4, Sx = 5, Ax = 6, b = A.handleUnexpected, Ll = A.handleChangeCipherSpec, pt = A.handleAlert, Et = A.handleHandshake, Pl = A.handleApplicationData, ht = A.handleHeartbeat, B0 = [];
B0[A.ConnectionEnd.client] = [
  //      CC,AL,HS,AD,HB
  /*SHE*/
  [b, pt, Et, b, ht],
  /*SCE*/
  [b, pt, Et, b, ht],
  /*SKE*/
  [b, pt, Et, b, ht],
  /*SCR*/
  [b, pt, Et, b, ht],
  /*SHD*/
  [b, pt, Et, b, ht],
  /*SCC*/
  [Ll, pt, b, b, ht],
  /*SFI*/
  [b, pt, Et, b, ht],
  /*SAD*/
  [b, pt, Et, Pl, ht],
  /*SER*/
  [b, pt, Et, b, ht]
];
B0[A.ConnectionEnd.server] = [
  //      CC,AL,HS,AD
  /*CHE*/
  [b, pt, Et, b, ht],
  /*CCE*/
  [b, pt, Et, b, ht],
  /*CKE*/
  [b, pt, Et, b, ht],
  /*CCV*/
  [b, pt, Et, b, ht],
  /*CCC*/
  [Ll, pt, b, b, ht],
  /*CFI*/
  [b, pt, Et, b, ht],
  /*CAD*/
  [b, pt, Et, Pl, ht],
  /*CER*/
  [b, pt, Et, b, ht]
];
var fr = A.handleHelloRequest, _x = A.handleServerHello, Ul = A.handleCertificate, fo = A.handleServerKeyExchange, _i = A.handleCertificateRequest, ja = A.handleServerHelloDone, Ol = A.handleFinished, hn = [];
hn[A.ConnectionEnd.client] = [
  //      HR,01,SH,03,04,05,06,07,08,09,10,SC,SK,CR,HD,15,CK,17,18,19,FI
  /*SHE*/
  [b, b, _x, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b],
  /*SCE*/
  [fr, b, b, b, b, b, b, b, b, b, b, Ul, fo, _i, ja, b, b, b, b, b, b],
  /*SKE*/
  [fr, b, b, b, b, b, b, b, b, b, b, b, fo, _i, ja, b, b, b, b, b, b],
  /*SCR*/
  [fr, b, b, b, b, b, b, b, b, b, b, b, b, _i, ja, b, b, b, b, b, b],
  /*SHD*/
  [fr, b, b, b, b, b, b, b, b, b, b, b, b, b, ja, b, b, b, b, b, b],
  /*SCC*/
  [fr, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b],
  /*SFI*/
  [fr, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, Ol],
  /*SAD*/
  [fr, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b],
  /*SER*/
  [fr, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b]
];
var Bx = A.handleClientHello, Tx = A.handleClientKeyExchange, Ix = A.handleCertificateVerify;
hn[A.ConnectionEnd.server] = [
  //      01,CH,02,03,04,05,06,07,08,09,10,CC,12,13,14,CV,CK,17,18,19,FI
  /*CHE*/
  [b, Bx, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b],
  /*CCE*/
  [b, b, b, b, b, b, b, b, b, b, b, Ul, b, b, b, b, b, b, b, b, b],
  /*CKE*/
  [b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, Tx, b, b, b, b],
  /*CCV*/
  [b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, Ix, b, b, b, b, b],
  /*CCC*/
  [b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b],
  /*CFI*/
  [b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, Ol],
  /*CAD*/
  [b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b],
  /*CER*/
  [b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b, b]
];
A.generateKeys = function(e, t) {
  var a = Pn, r = t.client_random + t.server_random;
  e.session.resuming || (t.master_secret = a(
    t.pre_master_secret,
    "master secret",
    r,
    48
  ).bytes(), t.pre_master_secret = null), r = t.server_random + t.client_random;
  var n = 2 * t.mac_key_length + 2 * t.enc_key_length, i = e.version.major === A.Versions.TLS_1_0.major && e.version.minor === A.Versions.TLS_1_0.minor;
  i && (n += 2 * t.fixed_iv_length);
  var s = a(t.master_secret, "key expansion", r, n), o = {
    client_write_MAC_key: s.getBytes(t.mac_key_length),
    server_write_MAC_key: s.getBytes(t.mac_key_length),
    client_write_key: s.getBytes(t.enc_key_length),
    server_write_key: s.getBytes(t.enc_key_length)
  };
  return i && (o.client_write_IV = s.getBytes(t.fixed_iv_length), o.server_write_IV = s.getBytes(t.fixed_iv_length)), o;
};
A.createConnectionState = function(e) {
  var t = e.entity === A.ConnectionEnd.client, a = function() {
    var i = {
      // two 32-bit numbers, first is most significant
      sequenceNumber: [0, 0],
      macKey: null,
      macLength: 0,
      macFunction: null,
      cipherState: null,
      cipherFunction: function(s) {
        return !0;
      },
      compressionState: null,
      compressFunction: function(s) {
        return !0;
      },
      updateSequenceNumber: function() {
        i.sequenceNumber[1] === 4294967295 ? (i.sequenceNumber[1] = 0, ++i.sequenceNumber[0]) : ++i.sequenceNumber[1];
      }
    };
    return i;
  }, r = {
    read: a(),
    write: a()
  };
  if (r.read.update = function(i, s) {
    return r.read.cipherFunction(s, r.read) ? r.read.compressFunction(i, s, r.read) || i.error(i, {
      message: "Could not decompress record.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.decompression_failure
      }
    }) : i.error(i, {
      message: "Could not decrypt record or bad MAC.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        // doesn't matter if decryption failed or MAC was
        // invalid, return the same error so as not to reveal
        // which one occurred
        description: A.Alert.Description.bad_record_mac
      }
    }), !i.fail;
  }, r.write.update = function(i, s) {
    return r.write.compressFunction(i, s, r.write) ? r.write.cipherFunction(s, r.write) || i.error(i, {
      message: "Could not encrypt record.",
      send: !1,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.internal_error
      }
    }) : i.error(i, {
      message: "Could not compress record.",
      send: !1,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.internal_error
      }
    }), !i.fail;
  }, e.session) {
    var n = e.session.sp;
    switch (e.session.cipherSuite.initSecurityParameters(n), n.keys = A.generateKeys(e, n), r.read.macKey = t ? n.keys.server_write_MAC_key : n.keys.client_write_MAC_key, r.write.macKey = t ? n.keys.client_write_MAC_key : n.keys.server_write_MAC_key, e.session.cipherSuite.initConnectionState(r, e, n), n.compression_algorithm) {
      case A.CompressionMethod.none:
        break;
      case A.CompressionMethod.deflate:
        r.read.compressFunction = dx, r.write.compressFunction = fx;
        break;
      default:
        throw new Error("Unsupported compression algorithm.");
    }
  }
  return r;
};
A.createRandom = function() {
  var e = /* @__PURE__ */ new Date(), t = +e + e.getTimezoneOffset() * 6e4, a = j.util.createBuffer();
  return a.putInt32(t), a.putBytes(j.random.getBytes(28)), a;
};
A.createRecord = function(e, t) {
  if (!t.data)
    return null;
  var a = {
    type: t.type,
    version: {
      major: e.version.major,
      minor: e.version.minor
    },
    length: t.data.length(),
    fragment: t.data
  };
  return a;
};
A.createAlert = function(e, t) {
  var a = j.util.createBuffer();
  return a.putByte(t.level), a.putByte(t.description), A.createRecord(e, {
    type: A.ContentType.alert,
    data: a
  });
};
A.createClientHello = function(e) {
  e.session.clientHelloVersion = {
    major: e.version.major,
    minor: e.version.minor
  };
  for (var t = j.util.createBuffer(), a = 0; a < e.cipherSuites.length; ++a) {
    var r = e.cipherSuites[a];
    t.putByte(r.id[0]), t.putByte(r.id[1]);
  }
  var n = t.length(), i = j.util.createBuffer();
  i.putByte(A.CompressionMethod.none);
  var s = i.length(), o = j.util.createBuffer();
  if (e.virtualHost) {
    var u = j.util.createBuffer();
    u.putByte(0), u.putByte(0);
    var l = j.util.createBuffer();
    l.putByte(0), kt(l, 2, j.util.createBuffer(e.virtualHost));
    var c = j.util.createBuffer();
    kt(c, 2, l), kt(u, 2, c), o.putBuffer(u);
  }
  var f = o.length();
  f > 0 && (f += 2);
  var d = e.session.id, v = d.length + 1 + // session ID vector
  2 + // version (major + minor)
  4 + 28 + // random time and random bytes
  2 + n + // cipher suites vector
  1 + s + // compression methods vector
  f, p = j.util.createBuffer();
  return p.putByte(A.HandshakeType.client_hello), p.putInt24(v), p.putByte(e.version.major), p.putByte(e.version.minor), p.putBytes(e.session.sp.client_random), kt(p, 1, j.util.createBuffer(d)), kt(p, 2, t), kt(p, 1, i), f > 0 && kt(p, 2, o), p;
};
A.createServerHello = function(e) {
  var t = e.session.id, a = t.length + 1 + // session ID vector
  2 + // version (major + minor)
  4 + 28 + // random time and random bytes
  2 + // chosen cipher suite
  1, r = j.util.createBuffer();
  return r.putByte(A.HandshakeType.server_hello), r.putInt24(a), r.putByte(e.version.major), r.putByte(e.version.minor), r.putBytes(e.session.sp.server_random), kt(r, 1, j.util.createBuffer(t)), r.putByte(e.session.cipherSuite.id[0]), r.putByte(e.session.cipherSuite.id[1]), r.putByte(e.session.compressionMethod), r;
};
A.createCertificate = function(e) {
  var t = e.entity === A.ConnectionEnd.client, a = null;
  if (e.getCertificate) {
    var r;
    t ? r = e.session.certificateRequest : r = e.session.extensions.server_name.serverNameList, a = e.getCertificate(e, r);
  }
  var n = j.util.createBuffer();
  if (a !== null)
    try {
      j.util.isArray(a) || (a = [a]);
      for (var i = null, s = 0; s < a.length; ++s) {
        var o = j.pem.decode(a[s])[0];
        if (o.type !== "CERTIFICATE" && o.type !== "X509 CERTIFICATE" && o.type !== "TRUSTED CERTIFICATE") {
          var u = new Error('Could not convert certificate from PEM; PEM header type is not "CERTIFICATE", "X509 CERTIFICATE", or "TRUSTED CERTIFICATE".');
          throw u.headerType = o.type, u;
        }
        if (o.procType && o.procType.type === "ENCRYPTED")
          throw new Error("Could not convert certificate from PEM; PEM is encrypted.");
        var l = j.util.createBuffer(o.body);
        i === null && (i = j.asn1.fromDer(l.bytes(), !1));
        var c = j.util.createBuffer();
        kt(c, 3, l), n.putBuffer(c);
      }
      a = j.pki.certificateFromAsn1(i), t ? e.session.clientCertificate = a : e.session.serverCertificate = a;
    } catch (v) {
      return e.error(e, {
        message: "Could not send certificate list.",
        cause: v,
        send: !0,
        alert: {
          level: A.Alert.Level.fatal,
          description: A.Alert.Description.bad_certificate
        }
      });
    }
  var f = 3 + n.length(), d = j.util.createBuffer();
  return d.putByte(A.HandshakeType.certificate), d.putInt24(f), kt(d, 3, n), d;
};
A.createClientKeyExchange = function(e) {
  var t = j.util.createBuffer();
  t.putByte(e.session.clientHelloVersion.major), t.putByte(e.session.clientHelloVersion.minor), t.putBytes(j.random.getBytes(46));
  var a = e.session.sp;
  a.pre_master_secret = t.getBytes();
  var r = e.session.serverCertificate.publicKey;
  t = r.encrypt(a.pre_master_secret);
  var n = t.length + 2, i = j.util.createBuffer();
  return i.putByte(A.HandshakeType.client_key_exchange), i.putInt24(n), i.putInt16(t.length), i.putBytes(t), i;
};
A.createServerKeyExchange = function(e) {
  var t = j.util.createBuffer();
  return t;
};
A.getClientSignature = function(e, t) {
  var a = j.util.createBuffer();
  a.putBuffer(e.session.md5.digest()), a.putBuffer(e.session.sha1.digest()), a = a.getBytes(), e.getSignature = e.getSignature || function(r, n, i) {
    var s = null;
    if (r.getPrivateKey)
      try {
        s = r.getPrivateKey(r, r.session.clientCertificate), s = j.pki.privateKeyFromPem(s);
      } catch (o) {
        r.error(r, {
          message: "Could not get private key.",
          cause: o,
          send: !0,
          alert: {
            level: A.Alert.Level.fatal,
            description: A.Alert.Description.internal_error
          }
        });
      }
    s === null ? r.error(r, {
      message: "No private key set.",
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: A.Alert.Description.internal_error
      }
    }) : n = s.sign(n, null), i(r, n);
  }, e.getSignature(e, a, t);
};
A.createCertificateVerify = function(e, t) {
  var a = t.length + 2, r = j.util.createBuffer();
  return r.putByte(A.HandshakeType.certificate_verify), r.putInt24(a), r.putInt16(t.length), r.putBytes(t), r;
};
A.createCertificateRequest = function(e) {
  var t = j.util.createBuffer();
  t.putByte(1);
  var a = j.util.createBuffer();
  for (var r in e.caStore.certs) {
    var n = e.caStore.certs[r], i = j.pki.distinguishedNameToAsn1(n.subject), s = j.asn1.toDer(i);
    a.putInt16(s.length()), a.putBuffer(s);
  }
  var o = 1 + t.length() + 2 + a.length(), u = j.util.createBuffer();
  return u.putByte(A.HandshakeType.certificate_request), u.putInt24(o), kt(u, 1, t), kt(u, 2, a), u;
};
A.createServerHelloDone = function(e) {
  var t = j.util.createBuffer();
  return t.putByte(A.HandshakeType.server_hello_done), t.putInt24(0), t;
};
A.createChangeCipherSpec = function() {
  var e = j.util.createBuffer();
  return e.putByte(1), e;
};
A.createFinished = function(e) {
  var t = j.util.createBuffer();
  t.putBuffer(e.session.md5.digest()), t.putBuffer(e.session.sha1.digest());
  var a = e.entity === A.ConnectionEnd.client, r = e.session.sp, n = 12, i = Pn, s = a ? "client finished" : "server finished";
  t = i(r.master_secret, s, t.getBytes(), n);
  var o = j.util.createBuffer();
  return o.putByte(A.HandshakeType.finished), o.putInt24(t.length()), o.putBuffer(t), o;
};
A.createHeartbeat = function(e, t, a) {
  typeof a > "u" && (a = t.length);
  var r = j.util.createBuffer();
  r.putByte(e), r.putInt16(a), r.putBytes(t);
  var n = r.length(), i = Math.max(16, n - a - 3);
  return r.putBytes(j.random.getBytes(i)), r;
};
A.queue = function(e, t) {
  if (t && !(t.fragment.length() === 0 && (t.type === A.ContentType.handshake || t.type === A.ContentType.alert || t.type === A.ContentType.change_cipher_spec))) {
    if (t.type === A.ContentType.handshake) {
      var a = t.fragment.bytes();
      e.session.md5.update(a), e.session.sha1.update(a), a = null;
    }
    var r;
    if (t.fragment.length() <= A.MaxFragment)
      r = [t];
    else {
      r = [];
      for (var n = t.fragment.bytes(); n.length > A.MaxFragment; )
        r.push(A.createRecord(e, {
          type: t.type,
          data: j.util.createBuffer(n.slice(0, A.MaxFragment))
        })), n = n.slice(A.MaxFragment);
      n.length > 0 && r.push(A.createRecord(e, {
        type: t.type,
        data: j.util.createBuffer(n)
      }));
    }
    for (var i = 0; i < r.length && !e.fail; ++i) {
      var s = r[i], o = e.state.current.write;
      o.update(e, s) && e.records.push(s);
    }
  }
};
A.flush = function(e) {
  for (var t = 0; t < e.records.length; ++t) {
    var a = e.records[t];
    e.tlsData.putByte(a.type), e.tlsData.putByte(a.version.major), e.tlsData.putByte(a.version.minor), e.tlsData.putInt16(a.fragment.length()), e.tlsData.putBuffer(e.records[t].fragment);
  }
  return e.records = [], e.tlsDataReady(e);
};
var Bi = function(e) {
  switch (e) {
    case !0:
      return !0;
    case j.pki.certificateError.bad_certificate:
      return A.Alert.Description.bad_certificate;
    case j.pki.certificateError.unsupported_certificate:
      return A.Alert.Description.unsupported_certificate;
    case j.pki.certificateError.certificate_revoked:
      return A.Alert.Description.certificate_revoked;
    case j.pki.certificateError.certificate_expired:
      return A.Alert.Description.certificate_expired;
    case j.pki.certificateError.certificate_unknown:
      return A.Alert.Description.certificate_unknown;
    case j.pki.certificateError.unknown_ca:
      return A.Alert.Description.unknown_ca;
    default:
      return A.Alert.Description.bad_certificate;
  }
}, wx = function(e) {
  switch (e) {
    case !0:
      return !0;
    case A.Alert.Description.bad_certificate:
      return j.pki.certificateError.bad_certificate;
    case A.Alert.Description.unsupported_certificate:
      return j.pki.certificateError.unsupported_certificate;
    case A.Alert.Description.certificate_revoked:
      return j.pki.certificateError.certificate_revoked;
    case A.Alert.Description.certificate_expired:
      return j.pki.certificateError.certificate_expired;
    case A.Alert.Description.certificate_unknown:
      return j.pki.certificateError.certificate_unknown;
    case A.Alert.Description.unknown_ca:
      return j.pki.certificateError.unknown_ca;
    default:
      return j.pki.certificateError.bad_certificate;
  }
};
A.verifyCertificateChain = function(e, t) {
  try {
    var a = {};
    for (var r in e.verifyOptions)
      a[r] = e.verifyOptions[r];
    a.verify = function(i, s, o) {
      var u = Bi(i), l = e.verify(e, i, s, o);
      if (l !== !0) {
        if (typeof l == "object" && !j.util.isArray(l)) {
          var c = new Error("The application rejected the certificate.");
          throw c.send = !0, c.alert = {
            level: A.Alert.Level.fatal,
            description: A.Alert.Description.bad_certificate
          }, l.message && (c.message = l.message), l.alert && (c.alert.description = l.alert), c;
        }
        l !== i && (l = wx(l));
      }
      return l;
    }, j.pki.verifyCertificateChain(e.caStore, t, a);
  } catch (i) {
    var n = i;
    (typeof n != "object" || j.util.isArray(n)) && (n = {
      send: !0,
      alert: {
        level: A.Alert.Level.fatal,
        description: Bi(i)
      }
    }), "send" in n || (n.send = !0), "alert" in n || (n.alert = {
      level: A.Alert.Level.fatal,
      description: Bi(n.error)
    }), e.error(e, n);
  }
  return !e.fail;
};
A.createSessionCache = function(e, t) {
  var a = null;
  if (e && e.getSession && e.setSession && e.order)
    a = e;
  else {
    a = {}, a.cache = e || {}, a.capacity = Math.max(t || 100, 1), a.order = [];
    for (var r in e)
      a.order.length <= t ? a.order.push(r) : delete e[r];
    a.getSession = function(n) {
      var i = null, s = null;
      if (n ? s = j.util.bytesToHex(n) : a.order.length > 0 && (s = a.order[0]), s !== null && s in a.cache) {
        i = a.cache[s], delete a.cache[s];
        for (var o in a.order)
          if (a.order[o] === s) {
            a.order.splice(o, 1);
            break;
          }
      }
      return i;
    }, a.setSession = function(n, i) {
      if (a.order.length === a.capacity) {
        var s = a.order.shift();
        delete a.cache[s];
      }
      var s = j.util.bytesToHex(n);
      a.order.push(s), a.cache[s] = i;
    };
  }
  return a;
};
A.createConnection = function(e) {
  var t = null;
  e.caStore ? j.util.isArray(e.caStore) ? t = j.pki.createCaStore(e.caStore) : t = e.caStore : t = j.pki.createCaStore();
  var a = e.cipherSuites || null;
  if (a === null) {
    a = [];
    for (var r in A.CipherSuites)
      a.push(A.CipherSuites[r]);
  }
  var n = e.server ? A.ConnectionEnd.server : A.ConnectionEnd.client, i = e.sessionCache ? A.createSessionCache(e.sessionCache) : null, s = {
    version: { major: A.Version.major, minor: A.Version.minor },
    entity: n,
    sessionId: e.sessionId,
    caStore: t,
    sessionCache: i,
    cipherSuites: a,
    connected: e.connected,
    virtualHost: e.virtualHost || null,
    verifyClient: e.verifyClient || !1,
    verify: e.verify || function(c, f, d, v) {
      return f;
    },
    verifyOptions: e.verifyOptions || {},
    getCertificate: e.getCertificate || null,
    getPrivateKey: e.getPrivateKey || null,
    getSignature: e.getSignature || null,
    input: j.util.createBuffer(),
    tlsData: j.util.createBuffer(),
    data: j.util.createBuffer(),
    tlsDataReady: e.tlsDataReady,
    dataReady: e.dataReady,
    heartbeatReceived: e.heartbeatReceived,
    closed: e.closed,
    error: function(c, f) {
      f.origin = f.origin || (c.entity === A.ConnectionEnd.client ? "client" : "server"), f.send && (A.queue(c, A.createAlert(c, f.alert)), A.flush(c));
      var d = f.fatal !== !1;
      d && (c.fail = !0), e.error(c, f), d && c.close(!1);
    },
    deflate: e.deflate || null,
    inflate: e.inflate || null
  };
  s.reset = function(c) {
    s.version = { major: A.Version.major, minor: A.Version.minor }, s.record = null, s.session = null, s.peerCertificate = null, s.state = {
      pending: null,
      current: null
    }, s.expect = s.entity === A.ConnectionEnd.client ? px : Cx, s.fragmented = null, s.records = [], s.open = !1, s.handshakes = 0, s.handshaking = !1, s.isConnected = !1, s.fail = !(c || typeof c > "u"), s.input.clear(), s.tlsData.clear(), s.data.clear(), s.state.current = A.createConnectionState(s);
  }, s.reset();
  var o = function(c, f) {
    var d = f.type - A.ContentType.change_cipher_spec, v = B0[c.entity][c.expect];
    d in v ? v[d](c, f) : A.handleUnexpected(c, f);
  }, u = function(c) {
    var f = 0, d = c.input, v = d.length();
    if (v < 5)
      f = 5 - v;
    else {
      c.record = {
        type: d.getByte(),
        version: {
          major: d.getByte(),
          minor: d.getByte()
        },
        length: d.getInt16(),
        fragment: j.util.createBuffer(),
        ready: !1
      };
      var p = c.record.version.major === c.version.major;
      p && c.session && c.session.version && (p = c.record.version.minor === c.version.minor), p || c.error(c, {
        message: "Incompatible TLS version.",
        send: !0,
        alert: {
          level: A.Alert.Level.fatal,
          description: A.Alert.Description.protocol_version
        }
      });
    }
    return f;
  }, l = function(c) {
    var f = 0, d = c.input, v = d.length();
    if (v < c.record.length)
      f = c.record.length - v;
    else {
      c.record.fragment.putBytes(d.getBytes(c.record.length)), d.compact();
      var p = c.state.current.read;
      p.update(c, c.record) && (c.fragmented !== null && (c.fragmented.type === c.record.type ? (c.fragmented.fragment.putBuffer(c.record.fragment), c.record = c.fragmented) : c.error(c, {
        message: "Invalid fragmented record.",
        send: !0,
        alert: {
          level: A.Alert.Level.fatal,
          description: A.Alert.Description.unexpected_message
        }
      })), c.record.ready = !0);
    }
    return f;
  };
  return s.handshake = function(c) {
    if (s.entity !== A.ConnectionEnd.client)
      s.error(s, {
        message: "Cannot initiate handshake as a server.",
        fatal: !1
      });
    else if (s.handshaking)
      s.error(s, {
        message: "Handshake already in progress.",
        fatal: !1
      });
    else {
      s.fail && !s.open && s.handshakes === 0 && (s.fail = !1), s.handshaking = !0, c = c || "";
      var f = null;
      c.length > 0 && (s.sessionCache && (f = s.sessionCache.getSession(c)), f === null && (c = "")), c.length === 0 && s.sessionCache && (f = s.sessionCache.getSession(), f !== null && (c = f.id)), s.session = {
        id: c,
        version: null,
        cipherSuite: null,
        compressionMethod: null,
        serverCertificate: null,
        certificateRequest: null,
        clientCertificate: null,
        sp: {},
        md5: j.md.md5.create(),
        sha1: j.md.sha1.create()
      }, f && (s.version = f.version, s.session.sp = f.sp), s.session.sp.client_random = A.createRandom().getBytes(), s.open = !0, A.queue(s, A.createRecord(s, {
        type: A.ContentType.handshake,
        data: A.createClientHello(s)
      })), A.flush(s);
    }
  }, s.process = function(c) {
    var f = 0;
    return c && s.input.putBytes(c), s.fail || (s.record !== null && s.record.ready && s.record.fragment.isEmpty() && (s.record = null), s.record === null && (f = u(s)), !s.fail && s.record !== null && !s.record.ready && (f = l(s)), !s.fail && s.record !== null && s.record.ready && o(s, s.record)), f;
  }, s.prepare = function(c) {
    return A.queue(s, A.createRecord(s, {
      type: A.ContentType.application_data,
      data: j.util.createBuffer(c)
    })), A.flush(s);
  }, s.prepareHeartbeatRequest = function(c, f) {
    return c instanceof j.util.ByteBuffer && (c = c.bytes()), typeof f > "u" && (f = c.length), s.expectedHeartbeatPayload = c, A.queue(s, A.createRecord(s, {
      type: A.ContentType.heartbeat,
      data: A.createHeartbeat(
        A.HeartbeatMessageType.heartbeat_request,
        c,
        f
      )
    })), A.flush(s);
  }, s.close = function(c) {
    if (!s.fail && s.sessionCache && s.session) {
      var f = {
        id: s.session.id,
        version: s.session.version,
        sp: s.session.sp
      };
      f.sp.keys = null, s.sessionCache.setSession(f.id, f);
    }
    s.open && (s.open = !1, s.input.clear(), (s.isConnected || s.handshaking) && (s.isConnected = s.handshaking = !1, A.queue(s, A.createAlert(s, {
      level: A.Alert.Level.warning,
      description: A.Alert.Description.close_notify
    })), A.flush(s)), s.closed(s)), s.reset(c);
  }, s;
};
j.tls = j.tls || {};
for (var Ti in A)
  typeof A[Ti] != "function" && (j.tls[Ti] = A[Ti]);
j.tls.prf_tls1 = Pn;
j.tls.hmac_sha1 = ux;
j.tls.createSessionCache = A.createSessionCache;
j.tls.createConnection = A.createConnection;
var mr = Re, Ot = mr.tls;
Ot.CipherSuites.TLS_RSA_WITH_AES_128_CBC_SHA = {
  id: [0, 47],
  name: "TLS_RSA_WITH_AES_128_CBC_SHA",
  initSecurityParameters: function(e) {
    e.bulk_cipher_algorithm = Ot.BulkCipherAlgorithm.aes, e.cipher_type = Ot.CipherType.block, e.enc_key_length = 16, e.block_length = 16, e.fixed_iv_length = 16, e.record_iv_length = 16, e.mac_algorithm = Ot.MACAlgorithm.hmac_sha1, e.mac_length = 20, e.mac_key_length = 20;
  },
  initConnectionState: Vl
};
Ot.CipherSuites.TLS_RSA_WITH_AES_256_CBC_SHA = {
  id: [0, 53],
  name: "TLS_RSA_WITH_AES_256_CBC_SHA",
  initSecurityParameters: function(e) {
    e.bulk_cipher_algorithm = Ot.BulkCipherAlgorithm.aes, e.cipher_type = Ot.CipherType.block, e.enc_key_length = 32, e.block_length = 16, e.fixed_iv_length = 16, e.record_iv_length = 16, e.mac_algorithm = Ot.MACAlgorithm.hmac_sha1, e.mac_length = 20, e.mac_key_length = 20;
  },
  initConnectionState: Vl
};
function Vl(e, t, a) {
  var r = t.entity === mr.tls.ConnectionEnd.client;
  e.read.cipherState = {
    init: !1,
    cipher: mr.cipher.createDecipher("AES-CBC", r ? a.keys.server_write_key : a.keys.client_write_key),
    iv: r ? a.keys.server_write_IV : a.keys.client_write_IV
  }, e.write.cipherState = {
    init: !1,
    cipher: mr.cipher.createCipher("AES-CBC", r ? a.keys.client_write_key : a.keys.server_write_key),
    iv: r ? a.keys.client_write_IV : a.keys.server_write_IV
  }, e.read.cipherFunction = kx, e.write.cipherFunction = Dx, e.read.macLength = e.write.macLength = a.mac_length, e.read.macFunction = e.write.macFunction = Ot.hmac_sha1;
}
function Dx(e, t) {
  var a = !1, r = t.macFunction(t.macKey, t.sequenceNumber, e);
  e.fragment.putBytes(r), t.updateSequenceNumber();
  var n;
  e.version.minor === Ot.Versions.TLS_1_0.minor ? n = t.cipherState.init ? null : t.cipherState.iv : n = mr.random.getBytesSync(16), t.cipherState.init = !0;
  var i = t.cipherState.cipher;
  return i.start({ iv: n }), e.version.minor >= Ot.Versions.TLS_1_1.minor && i.output.putBytes(n), i.update(e.fragment), i.finish(Nx) && (e.fragment = i.output, e.length = e.fragment.length(), a = !0), a;
}
function Nx(e, t, a) {
  if (!a) {
    var r = e - t.length() % e;
    t.fillWithByte(r - 1, r);
  }
  return !0;
}
function Rx(e, t, a) {
  var r = !0;
  if (a) {
    for (var n = t.length(), i = t.last(), s = n - 1 - i; s < n - 1; ++s)
      r = r && t.at(s) == i;
    r && t.truncate(i + 1);
  }
  return r;
}
function kx(e, t) {
  var a = !1, r;
  e.version.minor === Ot.Versions.TLS_1_0.minor ? r = t.cipherState.init ? null : t.cipherState.iv : r = e.fragment.getBytes(16), t.cipherState.init = !0;
  var n = t.cipherState.cipher;
  n.start({ iv: r }), n.update(e.fragment), a = n.finish(Rx);
  var i = t.macLength, s = mr.random.getBytesSync(i), o = n.output.length();
  o >= i ? (e.fragment = n.output.getBytes(o - i), s = n.output.getBytes(i)) : e.fragment = n.output.getBytes(), e.fragment = mr.util.createBuffer(e.fragment), e.length = e.fragment.length();
  var u = t.macFunction(t.macKey, t.sequenceNumber, e);
  return t.updateSequenceNumber(), a = Fx(t.macKey, s, u) && a, a;
}
function Fx(e, t, a) {
  var r = mr.hmac.create();
  return r.start("SHA1", e), r.update(t), t = r.digest().getBytes(), r.start(null, null), r.update(a), a = r.digest().getBytes(), t === a;
}
var et = Re, Ka = et.sha512 = et.sha512 || {};
et.md.sha512 = et.md.algorithms.sha512 = Ka;
var Ml = et.sha384 = et.sha512.sha384 = et.sha512.sha384 || {};
Ml.create = function() {
  return Ka.create("SHA-384");
};
et.md.sha384 = et.md.algorithms.sha384 = Ml;
et.sha512.sha256 = et.sha512.sha256 || {
  create: function() {
    return Ka.create("SHA-512/256");
  }
};
et.md["sha512/256"] = et.md.algorithms["sha512/256"] = et.sha512.sha256;
et.sha512.sha224 = et.sha512.sha224 || {
  create: function() {
    return Ka.create("SHA-512/224");
  }
};
et.md["sha512/224"] = et.md.algorithms["sha512/224"] = et.sha512.sha224;
Ka.create = function(e) {
  if (Kl || Lx(), typeof e > "u" && (e = "SHA-512"), !(e in wr))
    throw new Error("Invalid SHA-512 algorithm: " + e);
  for (var t = wr[e], a = null, r = et.util.createBuffer(), n = new Array(80), i = 0; i < 80; ++i)
    n[i] = new Array(2);
  var s = 64;
  switch (e) {
    case "SHA-384":
      s = 48;
      break;
    case "SHA-512/256":
      s = 32;
      break;
    case "SHA-512/224":
      s = 28;
      break;
  }
  var o = {
    // SHA-512 => sha512
    algorithm: e.replace("-", "").toLowerCase(),
    blockLength: 128,
    digestLength: s,
    // 56-bit length of message so far (does not including padding)
    messageLength: 0,
    // true message length
    fullMessageLength: null,
    // size of message length in bytes
    messageLengthSize: 16
  };
  return o.start = function() {
    o.messageLength = 0, o.fullMessageLength = o.messageLength128 = [];
    for (var u = o.messageLengthSize / 4, l = 0; l < u; ++l)
      o.fullMessageLength.push(0);
    r = et.util.createBuffer(), a = new Array(t.length);
    for (var l = 0; l < t.length; ++l)
      a[l] = t[l].slice(0);
    return o;
  }, o.start(), o.update = function(u, l) {
    l === "utf8" && (u = et.util.encodeUtf8(u));
    var c = u.length;
    o.messageLength += c, c = [c / 4294967296 >>> 0, c >>> 0];
    for (var f = o.fullMessageLength.length - 1; f >= 0; --f)
      o.fullMessageLength[f] += c[1], c[1] = c[0] + (o.fullMessageLength[f] / 4294967296 >>> 0), o.fullMessageLength[f] = o.fullMessageLength[f] >>> 0, c[0] = c[1] / 4294967296 >>> 0;
    return r.putBytes(u), po(a, n, r), (r.read > 2048 || r.length() === 0) && r.compact(), o;
  }, o.digest = function() {
    var u = et.util.createBuffer();
    u.putBytes(r.bytes());
    var l = o.fullMessageLength[o.fullMessageLength.length - 1] + o.messageLengthSize, c = l & o.blockLength - 1;
    u.putBytes(Wi.substr(0, o.blockLength - c));
    for (var f, d, v = o.fullMessageLength[0] * 8, p = 0; p < o.fullMessageLength.length - 1; ++p)
      f = o.fullMessageLength[p + 1] * 8, d = f / 4294967296 >>> 0, v += d, u.putInt32(v >>> 0), v = f >>> 0;
    u.putInt32(v);
    for (var h = new Array(a.length), p = 0; p < a.length; ++p)
      h[p] = a[p].slice(0);
    po(h, n, u);
    var x = et.util.createBuffer(), y;
    e === "SHA-512" ? y = h.length : e === "SHA-384" ? y = h.length - 2 : y = h.length - 4;
    for (var p = 0; p < y; ++p)
      x.putInt32(h[p][0]), (p !== y - 1 || e !== "SHA-512/224") && x.putInt32(h[p][1]);
    return x;
  }, o;
};
var Wi = null, Kl = !1, Yi = null, wr = null;
function Lx() {
  Wi = "", Wi += et.util.fillString("\0", 128), Yi = [
    [1116352408, 3609767458],
    [1899447441, 602891725],
    [3049323471, 3964484399],
    [3921009573, 2173295548],
    [961987163, 4081628472],
    [1508970993, 3053834265],
    [2453635748, 2937671579],
    [2870763221, 3664609560],
    [3624381080, 2734883394],
    [310598401, 1164996542],
    [607225278, 1323610764],
    [1426881987, 3590304994],
    [1925078388, 4068182383],
    [2162078206, 991336113],
    [2614888103, 633803317],
    [3248222580, 3479774868],
    [3835390401, 2666613458],
    [4022224774, 944711139],
    [264347078, 2341262773],
    [604807628, 2007800933],
    [770255983, 1495990901],
    [1249150122, 1856431235],
    [1555081692, 3175218132],
    [1996064986, 2198950837],
    [2554220882, 3999719339],
    [2821834349, 766784016],
    [2952996808, 2566594879],
    [3210313671, 3203337956],
    [3336571891, 1034457026],
    [3584528711, 2466948901],
    [113926993, 3758326383],
    [338241895, 168717936],
    [666307205, 1188179964],
    [773529912, 1546045734],
    [1294757372, 1522805485],
    [1396182291, 2643833823],
    [1695183700, 2343527390],
    [1986661051, 1014477480],
    [2177026350, 1206759142],
    [2456956037, 344077627],
    [2730485921, 1290863460],
    [2820302411, 3158454273],
    [3259730800, 3505952657],
    [3345764771, 106217008],
    [3516065817, 3606008344],
    [3600352804, 1432725776],
    [4094571909, 1467031594],
    [275423344, 851169720],
    [430227734, 3100823752],
    [506948616, 1363258195],
    [659060556, 3750685593],
    [883997877, 3785050280],
    [958139571, 3318307427],
    [1322822218, 3812723403],
    [1537002063, 2003034995],
    [1747873779, 3602036899],
    [1955562222, 1575990012],
    [2024104815, 1125592928],
    [2227730452, 2716904306],
    [2361852424, 442776044],
    [2428436474, 593698344],
    [2756734187, 3733110249],
    [3204031479, 2999351573],
    [3329325298, 3815920427],
    [3391569614, 3928383900],
    [3515267271, 566280711],
    [3940187606, 3454069534],
    [4118630271, 4000239992],
    [116418474, 1914138554],
    [174292421, 2731055270],
    [289380356, 3203993006],
    [460393269, 320620315],
    [685471733, 587496836],
    [852142971, 1086792851],
    [1017036298, 365543100],
    [1126000580, 2618297676],
    [1288033470, 3409855158],
    [1501505948, 4234509866],
    [1607167915, 987167468],
    [1816402316, 1246189591]
  ], wr = {}, wr["SHA-512"] = [
    [1779033703, 4089235720],
    [3144134277, 2227873595],
    [1013904242, 4271175723],
    [2773480762, 1595750129],
    [1359893119, 2917565137],
    [2600822924, 725511199],
    [528734635, 4215389547],
    [1541459225, 327033209]
  ], wr["SHA-384"] = [
    [3418070365, 3238371032],
    [1654270250, 914150663],
    [2438529370, 812702999],
    [355462360, 4144912697],
    [1731405415, 4290775857],
    [2394180231, 1750603025],
    [3675008525, 1694076839],
    [1203062813, 3204075428]
  ], wr["SHA-512/256"] = [
    [573645204, 4230739756],
    [2673172387, 3360449730],
    [596883563, 1867755857],
    [2520282905, 1497426621],
    [2519219938, 2827943907],
    [3193839141, 1401305490],
    [721525244, 746961066],
    [246885852, 2177182882]
  ], wr["SHA-512/224"] = [
    [2352822216, 424955298],
    [1944164710, 2312950998],
    [502970286, 855612546],
    [1738396948, 1479516111],
    [258812777, 2077511080],
    [2011393907, 79989058],
    [1067287976, 1780299464],
    [286451373, 2446758561]
  ], Kl = !0;
}
function po(e, t, a) {
  for (var r, n, i, s, o, u, l, c, f, d, v, p, h, x, y, g, m, C, S, _, T, D, P, I, B, R, F, U, M, ee, q, fe, Ee, xe, O, H = a.length(); H >= 128; ) {
    for (M = 0; M < 16; ++M)
      t[M][0] = a.getInt32() >>> 0, t[M][1] = a.getInt32() >>> 0;
    for (; M < 80; ++M)
      fe = t[M - 2], ee = fe[0], q = fe[1], r = ((ee >>> 19 | q << 13) ^ // ROTR 19
      (q >>> 29 | ee << 3) ^ // ROTR 61/(swap + ROTR 29)
      ee >>> 6) >>> 0, n = ((ee << 13 | q >>> 19) ^ // ROTR 19
      (q << 3 | ee >>> 29) ^ // ROTR 61/(swap + ROTR 29)
      (ee << 26 | q >>> 6)) >>> 0, xe = t[M - 15], ee = xe[0], q = xe[1], i = ((ee >>> 1 | q << 31) ^ // ROTR 1
      (ee >>> 8 | q << 24) ^ // ROTR 8
      ee >>> 7) >>> 0, s = ((ee << 31 | q >>> 1) ^ // ROTR 1
      (ee << 24 | q >>> 8) ^ // ROTR 8
      (ee << 25 | q >>> 7)) >>> 0, Ee = t[M - 7], O = t[M - 16], q = n + Ee[1] + s + O[1], t[M][0] = r + Ee[0] + i + O[0] + (q / 4294967296 >>> 0) >>> 0, t[M][1] = q >>> 0;
    for (h = e[0][0], x = e[0][1], y = e[1][0], g = e[1][1], m = e[2][0], C = e[2][1], S = e[3][0], _ = e[3][1], T = e[4][0], D = e[4][1], P = e[5][0], I = e[5][1], B = e[6][0], R = e[6][1], F = e[7][0], U = e[7][1], M = 0; M < 80; ++M)
      l = ((T >>> 14 | D << 18) ^ // ROTR 14
      (T >>> 18 | D << 14) ^ // ROTR 18
      (D >>> 9 | T << 23)) >>> 0, c = ((T << 18 | D >>> 14) ^ // ROTR 14
      (T << 14 | D >>> 18) ^ // ROTR 18
      (D << 23 | T >>> 9)) >>> 0, f = (B ^ T & (P ^ B)) >>> 0, d = (R ^ D & (I ^ R)) >>> 0, o = ((h >>> 28 | x << 4) ^ // ROTR 28
      (x >>> 2 | h << 30) ^ // ROTR 34/(swap + ROTR 2)
      (x >>> 7 | h << 25)) >>> 0, u = ((h << 4 | x >>> 28) ^ // ROTR 28
      (x << 30 | h >>> 2) ^ // ROTR 34/(swap + ROTR 2)
      (x << 25 | h >>> 7)) >>> 0, v = (h & y | m & (h ^ y)) >>> 0, p = (x & g | C & (x ^ g)) >>> 0, q = U + c + d + Yi[M][1] + t[M][1], r = F + l + f + Yi[M][0] + t[M][0] + (q / 4294967296 >>> 0) >>> 0, n = q >>> 0, q = u + p, i = o + v + (q / 4294967296 >>> 0) >>> 0, s = q >>> 0, F = B, U = R, B = P, R = I, P = T, I = D, q = _ + n, T = S + r + (q / 4294967296 >>> 0) >>> 0, D = q >>> 0, S = m, _ = C, m = y, C = g, y = h, g = x, q = n + s, h = r + i + (q / 4294967296 >>> 0) >>> 0, x = q >>> 0;
    q = e[0][1] + x, e[0][0] = e[0][0] + h + (q / 4294967296 >>> 0) >>> 0, e[0][1] = q >>> 0, q = e[1][1] + g, e[1][0] = e[1][0] + y + (q / 4294967296 >>> 0) >>> 0, e[1][1] = q >>> 0, q = e[2][1] + C, e[2][0] = e[2][0] + m + (q / 4294967296 >>> 0) >>> 0, e[2][1] = q >>> 0, q = e[3][1] + _, e[3][0] = e[3][0] + S + (q / 4294967296 >>> 0) >>> 0, e[3][1] = q >>> 0, q = e[4][1] + D, e[4][0] = e[4][0] + T + (q / 4294967296 >>> 0) >>> 0, e[4][1] = q >>> 0, q = e[5][1] + I, e[5][0] = e[5][0] + P + (q / 4294967296 >>> 0) >>> 0, e[5][1] = q >>> 0, q = e[6][1] + R, e[6][0] = e[6][0] + B + (q / 4294967296 >>> 0) >>> 0, e[6][1] = q >>> 0, q = e[7][1] + U, e[7][0] = e[7][0] + F + (q / 4294967296 >>> 0) >>> 0, e[7][1] = q >>> 0, H -= 128;
  }
}
var T0 = {}, Px = Re, lt = Px.asn1;
T0.privateKeyValidator = {
  // PrivateKeyInfo
  name: "PrivateKeyInfo",
  tagClass: lt.Class.UNIVERSAL,
  type: lt.Type.SEQUENCE,
  constructed: !0,
  value: [{
    // Version (INTEGER)
    name: "PrivateKeyInfo.version",
    tagClass: lt.Class.UNIVERSAL,
    type: lt.Type.INTEGER,
    constructed: !1,
    capture: "privateKeyVersion"
  }, {
    // privateKeyAlgorithm
    name: "PrivateKeyInfo.privateKeyAlgorithm",
    tagClass: lt.Class.UNIVERSAL,
    type: lt.Type.SEQUENCE,
    constructed: !0,
    value: [{
      name: "AlgorithmIdentifier.algorithm",
      tagClass: lt.Class.UNIVERSAL,
      type: lt.Type.OID,
      constructed: !1,
      capture: "privateKeyOid"
    }]
  }, {
    // PrivateKey
    name: "PrivateKeyInfo",
    tagClass: lt.Class.UNIVERSAL,
    type: lt.Type.OCTETSTRING,
    constructed: !1,
    capture: "privateKey"
  }]
};
T0.publicKeyValidator = {
  name: "SubjectPublicKeyInfo",
  tagClass: lt.Class.UNIVERSAL,
  type: lt.Type.SEQUENCE,
  constructed: !0,
  captureAsn1: "subjectPublicKeyInfo",
  value: [
    {
      name: "SubjectPublicKeyInfo.AlgorithmIdentifier",
      tagClass: lt.Class.UNIVERSAL,
      type: lt.Type.SEQUENCE,
      constructed: !0,
      value: [{
        name: "AlgorithmIdentifier.algorithm",
        tagClass: lt.Class.UNIVERSAL,
        type: lt.Type.OID,
        constructed: !1,
        capture: "publicKeyOid"
      }]
    },
    // capture group for ed25519PublicKey
    {
      tagClass: lt.Class.UNIVERSAL,
      type: lt.Type.BITSTRING,
      constructed: !1,
      composed: !0,
      captureBitStringValue: "ed25519PublicKey"
    }
    // FIXME: this is capture group for rsaPublicKey, use it in this API or
    // discard?
    /* {
      // subjectPublicKey
      name: 'SubjectPublicKeyInfo.subjectPublicKey',
      tagClass: asn1.Class.UNIVERSAL,
      type: asn1.Type.BITSTRING,
      constructed: false,
      value: [{
        // RSAPublicKey
        name: 'SubjectPublicKeyInfo.subjectPublicKey.RSAPublicKey',
        tagClass: asn1.Class.UNIVERSAL,
        type: asn1.Type.SEQUENCE,
        constructed: true,
        optional: true,
        captureAsn1: 'rsaPublicKey'
      }]
    } */
  ]
};
var vt = Re, Hl = T0, Ux = Hl.publicKeyValidator, Ox = Hl.privateKeyValidator;
if (typeof Vx > "u")
  var Vx = vt.jsbn.BigInteger;
var Qi = vt.util.ByteBuffer, Tt = typeof Buffer > "u" ? Uint8Array : Buffer;
vt.pki = vt.pki || {};
vt.pki.ed25519 = vt.ed25519 = vt.ed25519 || {};
var Fe = vt.ed25519;
Fe.constants = {};
Fe.constants.PUBLIC_KEY_BYTE_LENGTH = 32;
Fe.constants.PRIVATE_KEY_BYTE_LENGTH = 64;
Fe.constants.SEED_BYTE_LENGTH = 32;
Fe.constants.SIGN_BYTE_LENGTH = 64;
Fe.constants.HASH_BYTE_LENGTH = 64;
Fe.generateKeyPair = function(e) {
  e = e || {};
  var t = e.seed;
  if (t === void 0)
    t = vt.random.getBytesSync(Fe.constants.SEED_BYTE_LENGTH);
  else if (typeof t == "string") {
    if (t.length !== Fe.constants.SEED_BYTE_LENGTH)
      throw new TypeError(
        '"seed" must be ' + Fe.constants.SEED_BYTE_LENGTH + " bytes in length."
      );
  } else if (!(t instanceof Uint8Array))
    throw new TypeError(
      '"seed" must be a node.js Buffer, Uint8Array, or a binary string.'
    );
  t = sr({ message: t, encoding: "binary" });
  for (var a = new Tt(Fe.constants.PUBLIC_KEY_BYTE_LENGTH), r = new Tt(Fe.constants.PRIVATE_KEY_BYTE_LENGTH), n = 0; n < 32; ++n)
    r[n] = t[n];
  return zx(a, r), { publicKey: a, privateKey: r };
};
Fe.privateKeyFromAsn1 = function(e) {
  var t = {}, a = [], r = vt.asn1.validate(e, Ox, t, a);
  if (!r) {
    var n = new Error("Invalid Key.");
    throw n.errors = a, n;
  }
  var i = vt.asn1.derToOid(t.privateKeyOid), s = vt.oids.EdDSA25519;
  if (i !== s)
    throw new Error('Invalid OID "' + i + '"; OID must be "' + s + '".');
  var o = t.privateKey, u = sr({
    message: vt.asn1.fromDer(o).value,
    encoding: "binary"
  });
  return { privateKeyBytes: u };
};
Fe.publicKeyFromAsn1 = function(e) {
  var t = {}, a = [], r = vt.asn1.validate(e, Ux, t, a);
  if (!r) {
    var n = new Error("Invalid Key.");
    throw n.errors = a, n;
  }
  var i = vt.asn1.derToOid(t.publicKeyOid), s = vt.oids.EdDSA25519;
  if (i !== s)
    throw new Error('Invalid OID "' + i + '"; OID must be "' + s + '".');
  var o = t.ed25519PublicKey;
  if (o.length !== Fe.constants.PUBLIC_KEY_BYTE_LENGTH)
    throw new Error("Key length is invalid.");
  return sr({
    message: o,
    encoding: "binary"
  });
};
Fe.publicKeyFromPrivateKey = function(e) {
  e = e || {};
  var t = sr({
    message: e.privateKey,
    encoding: "binary"
  });
  if (t.length !== Fe.constants.PRIVATE_KEY_BYTE_LENGTH)
    throw new TypeError(
      '"options.privateKey" must have a byte length of ' + Fe.constants.PRIVATE_KEY_BYTE_LENGTH
    );
  for (var a = new Tt(Fe.constants.PUBLIC_KEY_BYTE_LENGTH), r = 0; r < a.length; ++r)
    a[r] = t[32 + r];
  return a;
};
Fe.sign = function(e) {
  e = e || {};
  var t = sr(e), a = sr({
    message: e.privateKey,
    encoding: "binary"
  });
  if (a.length === Fe.constants.SEED_BYTE_LENGTH) {
    var r = Fe.generateKeyPair({ seed: a });
    a = r.privateKey;
  } else if (a.length !== Fe.constants.PRIVATE_KEY_BYTE_LENGTH)
    throw new TypeError(
      '"options.privateKey" must have a byte length of ' + Fe.constants.SEED_BYTE_LENGTH + " or " + Fe.constants.PRIVATE_KEY_BYTE_LENGTH
    );
  var n = new Tt(
    Fe.constants.SIGN_BYTE_LENGTH + t.length
  );
  $x(n, t, t.length, a);
  for (var i = new Tt(Fe.constants.SIGN_BYTE_LENGTH), s = 0; s < i.length; ++s)
    i[s] = n[s];
  return i;
};
Fe.verify = function(e) {
  e = e || {};
  var t = sr(e);
  if (e.signature === void 0)
    throw new TypeError(
      '"options.signature" must be a node.js Buffer, a Uint8Array, a forge ByteBuffer, or a binary string.'
    );
  var a = sr({
    message: e.signature,
    encoding: "binary"
  });
  if (a.length !== Fe.constants.SIGN_BYTE_LENGTH)
    throw new TypeError(
      '"options.signature" must have a byte length of ' + Fe.constants.SIGN_BYTE_LENGTH
    );
  var r = sr({
    message: e.publicKey,
    encoding: "binary"
  });
  if (r.length !== Fe.constants.PUBLIC_KEY_BYTE_LENGTH)
    throw new TypeError(
      '"options.publicKey" must have a byte length of ' + Fe.constants.PUBLIC_KEY_BYTE_LENGTH
    );
  var n = new Tt(Fe.constants.SIGN_BYTE_LENGTH + t.length), i = new Tt(Fe.constants.SIGN_BYTE_LENGTH + t.length), s;
  for (s = 0; s < Fe.constants.SIGN_BYTE_LENGTH; ++s)
    n[s] = a[s];
  for (s = 0; s < t.length; ++s)
    n[s + Fe.constants.SIGN_BYTE_LENGTH] = t[s];
  return qx(i, n, n.length, r) >= 0;
};
function sr(e) {
  var t = e.message;
  if (t instanceof Uint8Array || t instanceof Tt)
    return t;
  var a = e.encoding;
  if (t === void 0)
    if (e.md)
      t = e.md.digest().getBytes(), a = "binary";
    else
      throw new TypeError('"options.message" or "options.md" not specified.');
  if (typeof t == "string" && !a)
    throw new TypeError('"options.encoding" must be "binary" or "utf8".');
  if (typeof t == "string") {
    if (typeof Buffer < "u")
      return Buffer.from(t, a);
    t = new Qi(t, a);
  } else if (!(t instanceof Qi))
    throw new TypeError(
      '"options.message" must be a node.js Buffer, a Uint8Array, a forge ByteBuffer, or a string with "options.encoding" specifying its encoding.'
    );
  for (var r = new Tt(t.length()), n = 0; n < r.length; ++n)
    r[n] = t.at(n);
  return r;
}
var Xi = Ae(), vn = Ae([1]), Mx = Ae([
  30883,
  4953,
  19914,
  30187,
  55467,
  16705,
  2637,
  112,
  59544,
  30585,
  16505,
  36039,
  65139,
  11119,
  27886,
  20995
]), Kx = Ae([
  61785,
  9906,
  39828,
  60374,
  45398,
  33411,
  5274,
  224,
  53552,
  61171,
  33010,
  6542,
  64743,
  22239,
  55772,
  9222
]), ho = Ae([
  54554,
  36645,
  11616,
  51542,
  42930,
  38181,
  51040,
  26924,
  56412,
  64982,
  57905,
  49316,
  21502,
  52590,
  14035,
  8553
]), vo = Ae([
  26200,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214,
  26214
]), Ii = new Float64Array([
  237,
  211,
  245,
  92,
  26,
  99,
  18,
  88,
  214,
  156,
  247,
  162,
  222,
  249,
  222,
  20,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  16
]), Hx = Ae([
  41136,
  18958,
  6951,
  50414,
  58488,
  44335,
  6150,
  12099,
  55207,
  15867,
  153,
  11085,
  57099,
  20417,
  9344,
  11139
]);
function wa(e, t) {
  var a = vt.md.sha512.create(), r = new Qi(e);
  a.update(r.getBytes(t), "binary");
  var n = a.digest().getBytes();
  if (typeof Buffer < "u")
    return Buffer.from(n, "binary");
  for (var i = new Tt(Fe.constants.HASH_BYTE_LENGTH), s = 0; s < 64; ++s)
    i[s] = n.charCodeAt(s);
  return i;
}
function zx(e, t) {
  var a = [Ae(), Ae(), Ae(), Ae()], r, n = wa(t, 32);
  for (n[0] &= 248, n[31] &= 127, n[31] |= 64, w0(a, n), I0(e, a), r = 0; r < 32; ++r)
    t[r + 32] = e[r];
  return 0;
}
function $x(e, t, a, r) {
  var n, i, s = new Float64Array(64), o = [Ae(), Ae(), Ae(), Ae()], u = wa(r, 32);
  u[0] &= 248, u[31] &= 127, u[31] |= 64;
  var l = a + 64;
  for (n = 0; n < a; ++n)
    e[64 + n] = t[n];
  for (n = 0; n < 32; ++n)
    e[32 + n] = u[32 + n];
  var c = wa(e.subarray(32), a + 32);
  for (Zi(c), w0(o, c), I0(e, o), n = 32; n < 64; ++n)
    e[n] = r[n];
  var f = wa(e, a + 64);
  for (Zi(f), n = 32; n < 64; ++n)
    s[n] = 0;
  for (n = 0; n < 32; ++n)
    s[n] = c[n];
  for (n = 0; n < 32; ++n)
    for (i = 0; i < 32; i++)
      s[n + i] += f[n] * u[i];
  return zl(e.subarray(32), s), l;
}
function qx(e, t, a, r) {
  var n, i, s = new Tt(32), o = [Ae(), Ae(), Ae(), Ae()], u = [Ae(), Ae(), Ae(), Ae()];
  if (i = -1, a < 64 || jx(u, r))
    return -1;
  for (n = 0; n < a; ++n)
    e[n] = t[n];
  for (n = 0; n < 32; ++n)
    e[n + 32] = r[n];
  var l = wa(e, a);
  if (Zi(l), jl(o, u, l), w0(u, t.subarray(32)), Ji(o, u), I0(s, o), a -= 64, $l(t, 0, s, 0)) {
    for (n = 0; n < a; ++n)
      e[n] = 0;
    return -1;
  }
  for (n = 0; n < a; ++n)
    e[n] = t[n + 64];
  return i = a, i;
}
function zl(e, t) {
  var a, r, n, i;
  for (r = 63; r >= 32; --r) {
    for (a = 0, n = r - 32, i = r - 12; n < i; ++n)
      t[n] += a - 16 * t[r] * Ii[n - (r - 32)], a = t[n] + 128 >> 8, t[n] -= a * 256;
    t[n] += a, t[r] = 0;
  }
  for (a = 0, n = 0; n < 32; ++n)
    t[n] += a - (t[31] >> 4) * Ii[n], a = t[n] >> 8, t[n] &= 255;
  for (n = 0; n < 32; ++n)
    t[n] -= a * Ii[n];
  for (r = 0; r < 32; ++r)
    t[r + 1] += t[r] >> 8, e[r] = t[r] & 255;
}
function Zi(e) {
  for (var t = new Float64Array(64), a = 0; a < 64; ++a)
    t[a] = e[a], e[a] = 0;
  zl(e, t);
}
function Ji(e, t) {
  var a = Ae(), r = Ae(), n = Ae(), i = Ae(), s = Ae(), o = Ae(), u = Ae(), l = Ae(), c = Ae();
  Jr(a, e[1], e[0]), Jr(c, t[1], t[0]), Ge(a, a, c), Yr(r, e[0], e[1]), Yr(c, t[0], t[1]), Ge(r, r, c), Ge(n, e[3], t[3]), Ge(n, n, Kx), Ge(i, e[2], t[2]), Yr(i, i, i), Jr(s, r, a), Jr(o, i, n), Yr(u, i, n), Yr(l, r, a), Ge(e[0], s, o), Ge(e[1], l, u), Ge(e[2], u, o), Ge(e[3], s, l);
}
function xo(e, t, a) {
  for (var r = 0; r < 4; ++r)
    Gl(e[r], t[r], a);
}
function I0(e, t) {
  var a = Ae(), r = Ae(), n = Ae();
  Qx(n, t[2]), Ge(a, t[0], n), Ge(r, t[1], n), xn(e, r), e[31] ^= ql(a) << 7;
}
function xn(e, t) {
  var a, r, n, i = Ae(), s = Ae();
  for (a = 0; a < 16; ++a)
    s[a] = t[a];
  for (wi(s), wi(s), wi(s), r = 0; r < 2; ++r) {
    for (i[0] = s[0] - 65517, a = 1; a < 15; ++a)
      i[a] = s[a] - 65535 - (i[a - 1] >> 16 & 1), i[a - 1] &= 65535;
    i[15] = s[15] - 32767 - (i[14] >> 16 & 1), n = i[15] >> 16 & 1, i[14] &= 65535, Gl(s, i, 1 - n);
  }
  for (a = 0; a < 16; a++)
    e[2 * a] = s[a] & 255, e[2 * a + 1] = s[a] >> 8;
}
function jx(e, t) {
  var a = Ae(), r = Ae(), n = Ae(), i = Ae(), s = Ae(), o = Ae(), u = Ae();
  return gr(e[2], vn), Gx(e[1], t), Dr(n, e[1]), Ge(i, n, Mx), Jr(n, n, e[2]), Yr(i, e[2], i), Dr(s, i), Dr(o, s), Ge(u, o, s), Ge(a, u, n), Ge(a, a, i), Wx(a, a), Ge(a, a, n), Ge(a, a, i), Ge(a, a, i), Ge(e[0], a, i), Dr(r, e[0]), Ge(r, r, i), go(r, n) && Ge(e[0], e[0], Hx), Dr(r, e[0]), Ge(r, r, i), go(r, n) ? -1 : (ql(e[0]) === t[31] >> 7 && Jr(e[0], Xi, e[0]), Ge(e[3], e[0], e[1]), 0);
}
function Gx(e, t) {
  var a;
  for (a = 0; a < 16; ++a)
    e[a] = t[2 * a] + (t[2 * a + 1] << 8);
  e[15] &= 32767;
}
function Wx(e, t) {
  var a = Ae(), r;
  for (r = 0; r < 16; ++r)
    a[r] = t[r];
  for (r = 250; r >= 0; --r)
    Dr(a, a), r !== 1 && Ge(a, a, t);
  for (r = 0; r < 16; ++r)
    e[r] = a[r];
}
function go(e, t) {
  var a = new Tt(32), r = new Tt(32);
  return xn(a, e), xn(r, t), $l(a, 0, r, 0);
}
function $l(e, t, a, r) {
  return Yx(e, t, a, r, 32);
}
function Yx(e, t, a, r, n) {
  var i, s = 0;
  for (i = 0; i < n; ++i)
    s |= e[t + i] ^ a[r + i];
  return (1 & s - 1 >>> 8) - 1;
}
function ql(e) {
  var t = new Tt(32);
  return xn(t, e), t[0] & 1;
}
function jl(e, t, a) {
  var r, n;
  for (gr(e[0], Xi), gr(e[1], vn), gr(e[2], vn), gr(e[3], Xi), n = 255; n >= 0; --n)
    r = a[n / 8 | 0] >> (n & 7) & 1, xo(e, t, r), Ji(t, e), Ji(e, e), xo(e, t, r);
}
function w0(e, t) {
  var a = [Ae(), Ae(), Ae(), Ae()];
  gr(a[0], ho), gr(a[1], vo), gr(a[2], vn), Ge(a[3], ho, vo), jl(e, a, t);
}
function gr(e, t) {
  var a;
  for (a = 0; a < 16; a++)
    e[a] = t[a] | 0;
}
function Qx(e, t) {
  var a = Ae(), r;
  for (r = 0; r < 16; ++r)
    a[r] = t[r];
  for (r = 253; r >= 0; --r)
    Dr(a, a), r !== 2 && r !== 4 && Ge(a, a, t);
  for (r = 0; r < 16; ++r)
    e[r] = a[r];
}
function wi(e) {
  var t, a, r = 1;
  for (t = 0; t < 16; ++t)
    a = e[t] + r + 65535, r = Math.floor(a / 65536), e[t] = a - r * 65536;
  e[0] += r - 1 + 37 * (r - 1);
}
function Gl(e, t, a) {
  for (var r, n = ~(a - 1), i = 0; i < 16; ++i)
    r = n & (e[i] ^ t[i]), e[i] ^= r, t[i] ^= r;
}
function Ae(e) {
  var t, a = new Float64Array(16);
  if (e)
    for (t = 0; t < e.length; ++t)
      a[t] = e[t];
  return a;
}
function Yr(e, t, a) {
  for (var r = 0; r < 16; ++r)
    e[r] = t[r] + a[r];
}
function Jr(e, t, a) {
  for (var r = 0; r < 16; ++r)
    e[r] = t[r] - a[r];
}
function Dr(e, t) {
  Ge(e, t, t);
}
function Ge(e, t, a) {
  var r, n, i = 0, s = 0, o = 0, u = 0, l = 0, c = 0, f = 0, d = 0, v = 0, p = 0, h = 0, x = 0, y = 0, g = 0, m = 0, C = 0, S = 0, _ = 0, T = 0, D = 0, P = 0, I = 0, B = 0, R = 0, F = 0, U = 0, M = 0, ee = 0, q = 0, fe = 0, Ee = 0, xe = a[0], O = a[1], H = a[2], $ = a[3], K = a[4], be = a[5], pe = a[6], Ne = a[7], le = a[8], X = a[9], te = a[10], ce = a[11], ge = a[12], de = a[13], Te = a[14], W = a[15];
  r = t[0], i += r * xe, s += r * O, o += r * H, u += r * $, l += r * K, c += r * be, f += r * pe, d += r * Ne, v += r * le, p += r * X, h += r * te, x += r * ce, y += r * ge, g += r * de, m += r * Te, C += r * W, r = t[1], s += r * xe, o += r * O, u += r * H, l += r * $, c += r * K, f += r * be, d += r * pe, v += r * Ne, p += r * le, h += r * X, x += r * te, y += r * ce, g += r * ge, m += r * de, C += r * Te, S += r * W, r = t[2], o += r * xe, u += r * O, l += r * H, c += r * $, f += r * K, d += r * be, v += r * pe, p += r * Ne, h += r * le, x += r * X, y += r * te, g += r * ce, m += r * ge, C += r * de, S += r * Te, _ += r * W, r = t[3], u += r * xe, l += r * O, c += r * H, f += r * $, d += r * K, v += r * be, p += r * pe, h += r * Ne, x += r * le, y += r * X, g += r * te, m += r * ce, C += r * ge, S += r * de, _ += r * Te, T += r * W, r = t[4], l += r * xe, c += r * O, f += r * H, d += r * $, v += r * K, p += r * be, h += r * pe, x += r * Ne, y += r * le, g += r * X, m += r * te, C += r * ce, S += r * ge, _ += r * de, T += r * Te, D += r * W, r = t[5], c += r * xe, f += r * O, d += r * H, v += r * $, p += r * K, h += r * be, x += r * pe, y += r * Ne, g += r * le, m += r * X, C += r * te, S += r * ce, _ += r * ge, T += r * de, D += r * Te, P += r * W, r = t[6], f += r * xe, d += r * O, v += r * H, p += r * $, h += r * K, x += r * be, y += r * pe, g += r * Ne, m += r * le, C += r * X, S += r * te, _ += r * ce, T += r * ge, D += r * de, P += r * Te, I += r * W, r = t[7], d += r * xe, v += r * O, p += r * H, h += r * $, x += r * K, y += r * be, g += r * pe, m += r * Ne, C += r * le, S += r * X, _ += r * te, T += r * ce, D += r * ge, P += r * de, I += r * Te, B += r * W, r = t[8], v += r * xe, p += r * O, h += r * H, x += r * $, y += r * K, g += r * be, m += r * pe, C += r * Ne, S += r * le, _ += r * X, T += r * te, D += r * ce, P += r * ge, I += r * de, B += r * Te, R += r * W, r = t[9], p += r * xe, h += r * O, x += r * H, y += r * $, g += r * K, m += r * be, C += r * pe, S += r * Ne, _ += r * le, T += r * X, D += r * te, P += r * ce, I += r * ge, B += r * de, R += r * Te, F += r * W, r = t[10], h += r * xe, x += r * O, y += r * H, g += r * $, m += r * K, C += r * be, S += r * pe, _ += r * Ne, T += r * le, D += r * X, P += r * te, I += r * ce, B += r * ge, R += r * de, F += r * Te, U += r * W, r = t[11], x += r * xe, y += r * O, g += r * H, m += r * $, C += r * K, S += r * be, _ += r * pe, T += r * Ne, D += r * le, P += r * X, I += r * te, B += r * ce, R += r * ge, F += r * de, U += r * Te, M += r * W, r = t[12], y += r * xe, g += r * O, m += r * H, C += r * $, S += r * K, _ += r * be, T += r * pe, D += r * Ne, P += r * le, I += r * X, B += r * te, R += r * ce, F += r * ge, U += r * de, M += r * Te, ee += r * W, r = t[13], g += r * xe, m += r * O, C += r * H, S += r * $, _ += r * K, T += r * be, D += r * pe, P += r * Ne, I += r * le, B += r * X, R += r * te, F += r * ce, U += r * ge, M += r * de, ee += r * Te, q += r * W, r = t[14], m += r * xe, C += r * O, S += r * H, _ += r * $, T += r * K, D += r * be, P += r * pe, I += r * Ne, B += r * le, R += r * X, F += r * te, U += r * ce, M += r * ge, ee += r * de, q += r * Te, fe += r * W, r = t[15], C += r * xe, S += r * O, _ += r * H, T += r * $, D += r * K, P += r * be, I += r * pe, B += r * Ne, R += r * le, F += r * X, U += r * te, M += r * ce, ee += r * ge, q += r * de, fe += r * Te, Ee += r * W, i += 38 * S, s += 38 * _, o += 38 * T, u += 38 * D, l += 38 * P, c += 38 * I, f += 38 * B, d += 38 * R, v += 38 * F, p += 38 * U, h += 38 * M, x += 38 * ee, y += 38 * q, g += 38 * fe, m += 38 * Ee, n = 1, r = i + n + 65535, n = Math.floor(r / 65536), i = r - n * 65536, r = s + n + 65535, n = Math.floor(r / 65536), s = r - n * 65536, r = o + n + 65535, n = Math.floor(r / 65536), o = r - n * 65536, r = u + n + 65535, n = Math.floor(r / 65536), u = r - n * 65536, r = l + n + 65535, n = Math.floor(r / 65536), l = r - n * 65536, r = c + n + 65535, n = Math.floor(r / 65536), c = r - n * 65536, r = f + n + 65535, n = Math.floor(r / 65536), f = r - n * 65536, r = d + n + 65535, n = Math.floor(r / 65536), d = r - n * 65536, r = v + n + 65535, n = Math.floor(r / 65536), v = r - n * 65536, r = p + n + 65535, n = Math.floor(r / 65536), p = r - n * 65536, r = h + n + 65535, n = Math.floor(r / 65536), h = r - n * 65536, r = x + n + 65535, n = Math.floor(r / 65536), x = r - n * 65536, r = y + n + 65535, n = Math.floor(r / 65536), y = r - n * 65536, r = g + n + 65535, n = Math.floor(r / 65536), g = r - n * 65536, r = m + n + 65535, n = Math.floor(r / 65536), m = r - n * 65536, r = C + n + 65535, n = Math.floor(r / 65536), C = r - n * 65536, i += n - 1 + 37 * (n - 1), n = 1, r = i + n + 65535, n = Math.floor(r / 65536), i = r - n * 65536, r = s + n + 65535, n = Math.floor(r / 65536), s = r - n * 65536, r = o + n + 65535, n = Math.floor(r / 65536), o = r - n * 65536, r = u + n + 65535, n = Math.floor(r / 65536), u = r - n * 65536, r = l + n + 65535, n = Math.floor(r / 65536), l = r - n * 65536, r = c + n + 65535, n = Math.floor(r / 65536), c = r - n * 65536, r = f + n + 65535, n = Math.floor(r / 65536), f = r - n * 65536, r = d + n + 65535, n = Math.floor(r / 65536), d = r - n * 65536, r = v + n + 65535, n = Math.floor(r / 65536), v = r - n * 65536, r = p + n + 65535, n = Math.floor(r / 65536), p = r - n * 65536, r = h + n + 65535, n = Math.floor(r / 65536), h = r - n * 65536, r = x + n + 65535, n = Math.floor(r / 65536), x = r - n * 65536, r = y + n + 65535, n = Math.floor(r / 65536), y = r - n * 65536, r = g + n + 65535, n = Math.floor(r / 65536), g = r - n * 65536, r = m + n + 65535, n = Math.floor(r / 65536), m = r - n * 65536, r = C + n + 65535, n = Math.floor(r / 65536), C = r - n * 65536, i += n - 1 + 37 * (n - 1), e[0] = i, e[1] = s, e[2] = o, e[3] = u, e[4] = l, e[5] = c, e[6] = f, e[7] = d, e[8] = v, e[9] = p, e[10] = h, e[11] = x, e[12] = y, e[13] = g, e[14] = m, e[15] = C;
}
var Dt = Re;
Dt.kem = Dt.kem || {};
var yo = Dt.jsbn.BigInteger;
Dt.kem.rsa = {};
Dt.kem.rsa.create = function(e, t) {
  t = t || {};
  var a = t.prng || Dt.random, r = {};
  return r.encrypt = function(n, i) {
    var s = Math.ceil(n.n.bitLength() / 8), o;
    do
      o = new yo(
        Dt.util.bytesToHex(a.getBytesSync(s)),
        16
      ).mod(n.n);
    while (o.compareTo(yo.ONE) <= 0);
    o = Dt.util.hexToBytes(o.toString(16));
    var u = s - o.length;
    u > 0 && (o = Dt.util.fillString("\0", u) + o);
    var l = n.encrypt(o, "NONE"), c = e.generate(o, i);
    return { encapsulation: l, key: c };
  }, r.decrypt = function(n, i, s) {
    var o = n.decrypt(i, "NONE");
    return e.generate(o, s);
  }, r;
};
Dt.kem.kdf1 = function(e, t) {
  Wl(this, e, 0, t || e.digestLength);
};
Dt.kem.kdf2 = function(e, t) {
  Wl(this, e, 1, t || e.digestLength);
};
function Wl(e, t, a, r) {
  e.generate = function(n, i) {
    for (var s = new Dt.util.ByteBuffer(), o = Math.ceil(i / r) + a, u = new Dt.util.ByteBuffer(), l = a; l < o; ++l) {
      u.putInt32(l), t.start(), t.update(n + u.getBytes());
      var c = t.digest();
      s.putBytes(c.getBytes(r));
    }
    return s.truncate(s.length() - i), s.getBytes();
  };
}
var Pe = Re;
Pe.log = Pe.log || {};
Pe.log.levels = [
  "none",
  "error",
  "warning",
  "info",
  "debug",
  "verbose",
  "max"
];
var gn = {}, e0 = [], Da = null;
Pe.log.LEVEL_LOCKED = 2;
Pe.log.NO_LEVEL_CHECK = 4;
Pe.log.INTERPOLATE = 8;
for (var yr = 0; yr < Pe.log.levels.length; ++yr) {
  var mo = Pe.log.levels[yr];
  gn[mo] = {
    index: yr,
    name: mo.toUpperCase()
  };
}
Pe.log.logMessage = function(e) {
  for (var t = gn[e.level].index, a = 0; a < e0.length; ++a) {
    var r = e0[a];
    if (r.flags & Pe.log.NO_LEVEL_CHECK)
      r.f(e);
    else {
      var n = gn[r.level].index;
      t <= n && r.f(r, e);
    }
  }
};
Pe.log.prepareStandard = function(e) {
  "standard" in e || (e.standard = gn[e.level].name + //' ' + +message.timestamp +
  " [" + e.category + "] " + e.message);
};
Pe.log.prepareFull = function(e) {
  if (!("full" in e)) {
    var t = [e.message];
    t = t.concat([]), e.full = Pe.util.format.apply(this, t);
  }
};
Pe.log.prepareStandardFull = function(e) {
  "standardFull" in e || (Pe.log.prepareStandard(e), e.standardFull = e.standard);
};
for (var Co = ["error", "warning", "info", "debug", "verbose"], yr = 0; yr < Co.length; ++yr)
  (function(t) {
    Pe.log[t] = function(a, r) {
      var n = Array.prototype.slice.call(arguments).slice(2), i = {
        timestamp: /* @__PURE__ */ new Date(),
        level: t,
        category: a,
        message: r,
        arguments: n
        /*standard*/
        /*full*/
        /*fullMessage*/
      };
      Pe.log.logMessage(i);
    };
  })(Co[yr]);
Pe.log.makeLogger = function(e) {
  var t = {
    flags: 0,
    f: e
  };
  return Pe.log.setLevel(t, "none"), t;
};
Pe.log.setLevel = function(e, t) {
  var a = !1;
  if (e && !(e.flags & Pe.log.LEVEL_LOCKED))
    for (var r = 0; r < Pe.log.levels.length; ++r) {
      var n = Pe.log.levels[r];
      if (t == n) {
        e.level = t, a = !0;
        break;
      }
    }
  return a;
};
Pe.log.lock = function(e, t) {
  typeof t > "u" || t ? e.flags |= Pe.log.LEVEL_LOCKED : e.flags &= ~Pe.log.LEVEL_LOCKED;
};
Pe.log.addLogger = function(e) {
  e0.push(e);
};
if (typeof console < "u" && "log" in console) {
  var Ca;
  if (console.error && console.warn && console.info && console.debug) {
    var Xx = {
      error: console.error,
      warning: console.warn,
      info: console.info,
      debug: console.debug,
      verbose: console.debug
    }, D0 = function(e, t) {
      Pe.log.prepareStandard(t);
      var a = Xx[t.level], r = [t.standard];
      r = r.concat(t.arguments.slice()), a.apply(console, r);
    };
    Ca = Pe.log.makeLogger(D0);
  } else {
    var D0 = function(t, a) {
      Pe.log.prepareStandardFull(a), console.log(a.standardFull);
    };
    Ca = Pe.log.makeLogger(D0);
  }
  Pe.log.setLevel(Ca, "debug"), Pe.log.addLogger(Ca), Da = Ca;
} else
  console = {
    log: function() {
    }
  };
if (Da !== null && typeof window < "u" && window.location) {
  var Ga = new URL(window.location.href).searchParams;
  if (Ga.has("console.level") && Pe.log.setLevel(
    Da,
    Ga.get("console.level").slice(-1)[0]
  ), Ga.has("console.lock")) {
    var Zx = Ga.get("console.lock").slice(-1)[0];
    Zx == "true" && Pe.log.lock(Da);
  }
}
Pe.log.consoleLogger = Da;
var ae = Re, N = ae.asn1, bt = ae.pkcs7 = ae.pkcs7 || {};
bt.messageFromPem = function(e) {
  var t = ae.pem.decode(e)[0];
  if (t.type !== "PKCS7") {
    var a = new Error('Could not convert PKCS#7 message from PEM; PEM header type is not "PKCS#7".');
    throw a.headerType = t.type, a;
  }
  if (t.procType && t.procType.type === "ENCRYPTED")
    throw new Error("Could not convert PKCS#7 message from PEM; PEM is encrypted.");
  var r = N.fromDer(t.body);
  return bt.messageFromAsn1(r);
};
bt.messageToPem = function(e, t) {
  var a = {
    type: "PKCS7",
    body: N.toDer(e.toAsn1()).getBytes()
  };
  return ae.pem.encode(a, { maxline: t });
};
bt.messageFromAsn1 = function(e) {
  var t = {}, a = [];
  if (!N.validate(e, bt.asn1.contentInfoValidator, t, a)) {
    var r = new Error("Cannot read PKCS#7 message. ASN.1 object is not an PKCS#7 ContentInfo.");
    throw r.errors = a, r;
  }
  var n = N.derToOid(t.contentType), i;
  switch (n) {
    case ae.pki.oids.envelopedData:
      i = bt.createEnvelopedData();
      break;
    case ae.pki.oids.encryptedData:
      i = bt.createEncryptedData();
      break;
    case ae.pki.oids.signedData:
      i = bt.createSignedData();
      break;
    default:
      throw new Error("Cannot read PKCS#7 message. ContentType with OID " + n + " is not (yet) supported.");
  }
  return i.fromAsn1(t.content.value[0]), i;
};
bt.createSignedData = function() {
  var e = null;
  return e = {
    type: ae.pki.oids.signedData,
    version: 1,
    certificates: [],
    crls: [],
    // TODO: add json-formatted signer stuff here?
    signers: [],
    // populated during sign()
    digestAlgorithmIdentifiers: [],
    contentInfo: null,
    signerInfos: [],
    fromAsn1: function(r) {
      if (N0(e, r, bt.asn1.signedDataValidator), e.certificates = [], e.crls = [], e.digestAlgorithmIdentifiers = [], e.contentInfo = null, e.signerInfos = [], e.rawCapture.certificates)
        for (var n = e.rawCapture.certificates.value, i = 0; i < n.length; ++i)
          e.certificates.push(ae.pki.certificateFromAsn1(n[i]));
    },
    toAsn1: function() {
      e.contentInfo || e.sign();
      for (var r = [], n = 0; n < e.certificates.length; ++n)
        r.push(ae.pki.certificateToAsn1(e.certificates[n]));
      var i = [], s = N.create(N.Class.CONTEXT_SPECIFIC, 0, !0, [
        N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
          // Version
          N.create(
            N.Class.UNIVERSAL,
            N.Type.INTEGER,
            !1,
            N.integerToDer(e.version).getBytes()
          ),
          // DigestAlgorithmIdentifiers
          N.create(
            N.Class.UNIVERSAL,
            N.Type.SET,
            !0,
            e.digestAlgorithmIdentifiers
          ),
          // ContentInfo
          e.contentInfo
        ])
      ]);
      return r.length > 0 && s.value[0].value.push(
        N.create(N.Class.CONTEXT_SPECIFIC, 0, !0, r)
      ), i.length > 0 && s.value[0].value.push(
        N.create(N.Class.CONTEXT_SPECIFIC, 1, !0, i)
      ), s.value[0].value.push(
        N.create(
          N.Class.UNIVERSAL,
          N.Type.SET,
          !0,
          e.signerInfos
        )
      ), N.create(
        N.Class.UNIVERSAL,
        N.Type.SEQUENCE,
        !0,
        [
          // ContentType
          N.create(
            N.Class.UNIVERSAL,
            N.Type.OID,
            !1,
            N.oidToDer(e.type).getBytes()
          ),
          // [0] SignedData
          s
        ]
      );
    },
    /**
     * Add (another) entity to list of signers.
     *
     * Note: If authenticatedAttributes are provided, then, per RFC 2315,
     * they must include at least two attributes: content type and
     * message digest. The message digest attribute value will be
     * auto-calculated during signing and will be ignored if provided.
     *
     * Here's an example of providing these two attributes:
     *
     * forge.pkcs7.createSignedData();
     * p7.addSigner({
     *   issuer: cert.issuer.attributes,
     *   serialNumber: cert.serialNumber,
     *   key: privateKey,
     *   digestAlgorithm: forge.pki.oids.sha1,
     *   authenticatedAttributes: [{
     *     type: forge.pki.oids.contentType,
     *     value: forge.pki.oids.data
     *   }, {
     *     type: forge.pki.oids.messageDigest
     *   }]
     * });
     *
     * TODO: Support [subjectKeyIdentifier] as signer's ID.
     *
     * @param signer the signer information:
     *          key the signer's private key.
     *          [certificate] a certificate containing the public key
     *            associated with the signer's private key; use this option as
     *            an alternative to specifying signer.issuer and
     *            signer.serialNumber.
     *          [issuer] the issuer attributes (eg: cert.issuer.attributes).
     *          [serialNumber] the signer's certificate's serial number in
     *           hexadecimal (eg: cert.serialNumber).
     *          [digestAlgorithm] the message digest OID, as a string, to use
     *            (eg: forge.pki.oids.sha1).
     *          [authenticatedAttributes] an optional array of attributes
     *            to also sign along with the content.
     */
    addSigner: function(r) {
      var n = r.issuer, i = r.serialNumber;
      if (r.certificate) {
        var s = r.certificate;
        typeof s == "string" && (s = ae.pki.certificateFromPem(s)), n = s.issuer.attributes, i = s.serialNumber;
      }
      var o = r.key;
      if (!o)
        throw new Error(
          "Could not add PKCS#7 signer; no private key specified."
        );
      typeof o == "string" && (o = ae.pki.privateKeyFromPem(o));
      var u = r.digestAlgorithm || ae.pki.oids.sha1;
      switch (u) {
        case ae.pki.oids.sha1:
        case ae.pki.oids.sha256:
        case ae.pki.oids.sha384:
        case ae.pki.oids.sha512:
        case ae.pki.oids.md5:
          break;
        default:
          throw new Error(
            "Could not add PKCS#7 signer; unknown message digest algorithm: " + u
          );
      }
      var l = r.authenticatedAttributes || [];
      if (l.length > 0) {
        for (var c = !1, f = !1, d = 0; d < l.length; ++d) {
          var v = l[d];
          if (!c && v.type === ae.pki.oids.contentType) {
            if (c = !0, f)
              break;
            continue;
          }
          if (!f && v.type === ae.pki.oids.messageDigest) {
            if (f = !0, c)
              break;
            continue;
          }
        }
        if (!c || !f)
          throw new Error("Invalid signer.authenticatedAttributes. If signer.authenticatedAttributes is specified, then it must contain at least two attributes, PKCS #9 content-type and PKCS #9 message-digest.");
      }
      e.signers.push({
        key: o,
        version: 1,
        issuer: n,
        serialNumber: i,
        digestAlgorithm: u,
        signatureAlgorithm: ae.pki.oids.rsaEncryption,
        signature: null,
        authenticatedAttributes: l,
        unauthenticatedAttributes: []
      });
    },
    /**
     * Signs the content.
     * @param options Options to apply when signing:
     *    [detached] boolean. If signing should be done in detached mode. Defaults to false.
     */
    sign: function(r) {
      if (r = r || {}, (typeof e.content != "object" || e.contentInfo === null) && (e.contentInfo = N.create(
        N.Class.UNIVERSAL,
        N.Type.SEQUENCE,
        !0,
        [
          // ContentType
          N.create(
            N.Class.UNIVERSAL,
            N.Type.OID,
            !1,
            N.oidToDer(ae.pki.oids.data).getBytes()
          )
        ]
      ), "content" in e)) {
        var n;
        e.content instanceof ae.util.ByteBuffer ? n = e.content.bytes() : typeof e.content == "string" && (n = ae.util.encodeUtf8(e.content)), r.detached ? e.detachedContent = N.create(N.Class.UNIVERSAL, N.Type.OCTETSTRING, !1, n) : e.contentInfo.value.push(
          // [0] EXPLICIT content
          N.create(N.Class.CONTEXT_SPECIFIC, 0, !0, [
            N.create(
              N.Class.UNIVERSAL,
              N.Type.OCTETSTRING,
              !1,
              n
            )
          ])
        );
      }
      if (e.signers.length !== 0) {
        var i = t();
        a(i);
      }
    },
    verify: function() {
      throw new Error("PKCS#7 signature verification not yet implemented.");
    },
    /**
     * Add a certificate.
     *
     * @param cert the certificate to add.
     */
    addCertificate: function(r) {
      typeof r == "string" && (r = ae.pki.certificateFromPem(r)), e.certificates.push(r);
    },
    /**
     * Add a certificate revokation list.
     *
     * @param crl the certificate revokation list to add.
     */
    addCertificateRevokationList: function(r) {
      throw new Error("PKCS#7 CRL support not yet implemented.");
    }
  }, e;
  function t() {
    for (var r = {}, n = 0; n < e.signers.length; ++n) {
      var i = e.signers[n], s = i.digestAlgorithm;
      s in r || (r[s] = ae.md[ae.pki.oids[s]].create()), i.authenticatedAttributes.length === 0 ? i.md = r[s] : i.md = ae.md[ae.pki.oids[s]].create();
    }
    e.digestAlgorithmIdentifiers = [];
    for (var s in r)
      e.digestAlgorithmIdentifiers.push(
        // AlgorithmIdentifier
        N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
          // algorithm
          N.create(
            N.Class.UNIVERSAL,
            N.Type.OID,
            !1,
            N.oidToDer(s).getBytes()
          ),
          // parameters (null)
          N.create(N.Class.UNIVERSAL, N.Type.NULL, !1, "")
        ])
      );
    return r;
  }
  function a(r) {
    var n;
    if (e.detachedContent ? n = e.detachedContent : (n = e.contentInfo.value[1], n = n.value[0]), !n)
      throw new Error(
        "Could not sign PKCS#7 message; there is no content to sign."
      );
    var i = N.derToOid(e.contentInfo.value[0].value), s = N.toDer(n);
    s.getByte(), N.getBerValueLength(s), s = s.getBytes();
    for (var o in r)
      r[o].start().update(s);
    for (var u = /* @__PURE__ */ new Date(), l = 0; l < e.signers.length; ++l) {
      var c = e.signers[l];
      if (c.authenticatedAttributes.length === 0) {
        if (i !== ae.pki.oids.data)
          throw new Error(
            "Invalid signer; authenticatedAttributes must be present when the ContentInfo content type is not PKCS#7 Data."
          );
      } else {
        c.authenticatedAttributesAsn1 = N.create(
          N.Class.CONTEXT_SPECIFIC,
          0,
          !0,
          []
        );
        for (var f = N.create(
          N.Class.UNIVERSAL,
          N.Type.SET,
          !0,
          []
        ), d = 0; d < c.authenticatedAttributes.length; ++d) {
          var v = c.authenticatedAttributes[d];
          v.type === ae.pki.oids.messageDigest ? v.value = r[c.digestAlgorithm].digest() : v.type === ae.pki.oids.signingTime && (v.value || (v.value = u)), f.value.push(t0(v)), c.authenticatedAttributesAsn1.value.push(t0(v));
        }
        s = N.toDer(f).getBytes(), c.md.start().update(s);
      }
      c.signature = c.key.sign(c.md, "RSASSA-PKCS1-V1_5");
    }
    e.signerInfos = ng(e.signers);
  }
};
bt.createEncryptedData = function() {
  var e = null;
  return e = {
    type: ae.pki.oids.encryptedData,
    version: 0,
    encryptedContent: {
      algorithm: ae.pki.oids["aes256-CBC"]
    },
    /**
     * Reads an EncryptedData content block (in ASN.1 format)
     *
     * @param obj The ASN.1 representation of the EncryptedData content block
     */
    fromAsn1: function(t) {
      N0(e, t, bt.asn1.encryptedDataValidator);
    },
    /**
     * Decrypt encrypted content
     *
     * @param key The (symmetric) key as a byte buffer
     */
    decrypt: function(t) {
      t !== void 0 && (e.encryptedContent.key = t), Yl(e);
    }
  }, e;
};
bt.createEnvelopedData = function() {
  var e = null;
  return e = {
    type: ae.pki.oids.envelopedData,
    version: 0,
    recipients: [],
    encryptedContent: {
      algorithm: ae.pki.oids["aes256-CBC"]
    },
    /**
     * Reads an EnvelopedData content block (in ASN.1 format)
     *
     * @param obj the ASN.1 representation of the EnvelopedData content block.
     */
    fromAsn1: function(t) {
      var a = N0(e, t, bt.asn1.envelopedDataValidator);
      e.recipients = tg(a.recipientInfos.value);
    },
    toAsn1: function() {
      return N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
        // ContentType
        N.create(
          N.Class.UNIVERSAL,
          N.Type.OID,
          !1,
          N.oidToDer(e.type).getBytes()
        ),
        // [0] EnvelopedData
        N.create(N.Class.CONTEXT_SPECIFIC, 0, !0, [
          N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
            // Version
            N.create(
              N.Class.UNIVERSAL,
              N.Type.INTEGER,
              !1,
              N.integerToDer(e.version).getBytes()
            ),
            // RecipientInfos
            N.create(
              N.Class.UNIVERSAL,
              N.Type.SET,
              !0,
              rg(e.recipients)
            ),
            // EncryptedContentInfo
            N.create(
              N.Class.UNIVERSAL,
              N.Type.SEQUENCE,
              !0,
              ig(e.encryptedContent)
            )
          ])
        ])
      ]);
    },
    /**
     * Find recipient by X.509 certificate's issuer.
     *
     * @param cert the certificate with the issuer to look for.
     *
     * @return the recipient object.
     */
    findRecipient: function(t) {
      for (var a = t.issuer.attributes, r = 0; r < e.recipients.length; ++r) {
        var n = e.recipients[r], i = n.issuer;
        if (n.serialNumber === t.serialNumber && i.length === a.length) {
          for (var s = !0, o = 0; o < a.length; ++o)
            if (i[o].type !== a[o].type || i[o].value !== a[o].value) {
              s = !1;
              break;
            }
          if (s)
            return n;
        }
      }
      return null;
    },
    /**
     * Decrypt enveloped content
     *
     * @param recipient The recipient object related to the private key
     * @param privKey The (RSA) private key object
     */
    decrypt: function(t, a) {
      if (e.encryptedContent.key === void 0 && t !== void 0 && a !== void 0)
        switch (t.encryptedContent.algorithm) {
          case ae.pki.oids.rsaEncryption:
          case ae.pki.oids.desCBC:
            var r = a.decrypt(t.encryptedContent.content);
            e.encryptedContent.key = ae.util.createBuffer(r);
            break;
          default:
            throw new Error("Unsupported asymmetric cipher, OID " + t.encryptedContent.algorithm);
        }
      Yl(e);
    },
    /**
     * Add (another) entity to list of recipients.
     *
     * @param cert The certificate of the entity to add.
     */
    addRecipient: function(t) {
      e.recipients.push({
        version: 0,
        issuer: t.issuer.attributes,
        serialNumber: t.serialNumber,
        encryptedContent: {
          // We simply assume rsaEncryption here, since forge.pki only
          // supports RSA so far.  If the PKI module supports other
          // ciphers one day, we need to modify this one as well.
          algorithm: ae.pki.oids.rsaEncryption,
          key: t.publicKey
        }
      });
    },
    /**
     * Encrypt enveloped content.
     *
     * This function supports two optional arguments, cipher and key, which
     * can be used to influence symmetric encryption.  Unless cipher is
     * provided, the cipher specified in encryptedContent.algorithm is used
     * (defaults to AES-256-CBC).  If no key is provided, encryptedContent.key
     * is (re-)used.  If that one's not set, a random key will be generated
     * automatically.
     *
     * @param [key] The key to be used for symmetric encryption.
     * @param [cipher] The OID of the symmetric cipher to use.
     */
    encrypt: function(t, a) {
      if (e.encryptedContent.content === void 0) {
        a = a || e.encryptedContent.algorithm, t = t || e.encryptedContent.key;
        var r, n, i;
        switch (a) {
          case ae.pki.oids["aes128-CBC"]:
            r = 16, n = 16, i = ae.aes.createEncryptionCipher;
            break;
          case ae.pki.oids["aes192-CBC"]:
            r = 24, n = 16, i = ae.aes.createEncryptionCipher;
            break;
          case ae.pki.oids["aes256-CBC"]:
            r = 32, n = 16, i = ae.aes.createEncryptionCipher;
            break;
          case ae.pki.oids["des-EDE3-CBC"]:
            r = 24, n = 8, i = ae.des.createEncryptionCipher;
            break;
          default:
            throw new Error("Unsupported symmetric cipher, OID " + a);
        }
        if (t === void 0)
          t = ae.util.createBuffer(ae.random.getBytes(r));
        else if (t.length() != r)
          throw new Error("Symmetric key has wrong length; got " + t.length() + " bytes, expected " + r + ".");
        e.encryptedContent.algorithm = a, e.encryptedContent.key = t, e.encryptedContent.parameter = ae.util.createBuffer(
          ae.random.getBytes(n)
        );
        var s = i(t);
        if (s.start(e.encryptedContent.parameter.copy()), s.update(e.content), !s.finish())
          throw new Error("Symmetric encryption failed.");
        e.encryptedContent.content = s.output;
      }
      for (var o = 0; o < e.recipients.length; ++o) {
        var u = e.recipients[o];
        if (u.encryptedContent.content === void 0)
          switch (u.encryptedContent.algorithm) {
            case ae.pki.oids.rsaEncryption:
              u.encryptedContent.content = u.encryptedContent.key.encrypt(
                e.encryptedContent.key.data
              );
              break;
            default:
              throw new Error("Unsupported asymmetric cipher, OID " + u.encryptedContent.algorithm);
          }
      }
    }
  }, e;
};
function Jx(e) {
  var t = {}, a = [];
  if (!N.validate(e, bt.asn1.recipientInfoValidator, t, a)) {
    var r = new Error("Cannot read PKCS#7 RecipientInfo. ASN.1 object is not an PKCS#7 RecipientInfo.");
    throw r.errors = a, r;
  }
  return {
    version: t.version.charCodeAt(0),
    issuer: ae.pki.RDNAttributesAsArray(t.issuer),
    serialNumber: ae.util.createBuffer(t.serial).toHex(),
    encryptedContent: {
      algorithm: N.derToOid(t.encAlgorithm),
      parameter: t.encParameter ? t.encParameter.value : void 0,
      content: t.encKey
    }
  };
}
function eg(e) {
  return N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
    // Version
    N.create(
      N.Class.UNIVERSAL,
      N.Type.INTEGER,
      !1,
      N.integerToDer(e.version).getBytes()
    ),
    // IssuerAndSerialNumber
    N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
      // Name
      ae.pki.distinguishedNameToAsn1({ attributes: e.issuer }),
      // Serial
      N.create(
        N.Class.UNIVERSAL,
        N.Type.INTEGER,
        !1,
        ae.util.hexToBytes(e.serialNumber)
      )
    ]),
    // KeyEncryptionAlgorithmIdentifier
    N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
      // Algorithm
      N.create(
        N.Class.UNIVERSAL,
        N.Type.OID,
        !1,
        N.oidToDer(e.encryptedContent.algorithm).getBytes()
      ),
      // Parameter, force NULL, only RSA supported for now.
      N.create(N.Class.UNIVERSAL, N.Type.NULL, !1, "")
    ]),
    // EncryptedKey
    N.create(
      N.Class.UNIVERSAL,
      N.Type.OCTETSTRING,
      !1,
      e.encryptedContent.content
    )
  ]);
}
function tg(e) {
  for (var t = [], a = 0; a < e.length; ++a)
    t.push(Jx(e[a]));
  return t;
}
function rg(e) {
  for (var t = [], a = 0; a < e.length; ++a)
    t.push(eg(e[a]));
  return t;
}
function ag(e) {
  var t = N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
    // version
    N.create(
      N.Class.UNIVERSAL,
      N.Type.INTEGER,
      !1,
      N.integerToDer(e.version).getBytes()
    ),
    // issuerAndSerialNumber
    N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
      // name
      ae.pki.distinguishedNameToAsn1({ attributes: e.issuer }),
      // serial
      N.create(
        N.Class.UNIVERSAL,
        N.Type.INTEGER,
        !1,
        ae.util.hexToBytes(e.serialNumber)
      )
    ]),
    // digestAlgorithm
    N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
      // algorithm
      N.create(
        N.Class.UNIVERSAL,
        N.Type.OID,
        !1,
        N.oidToDer(e.digestAlgorithm).getBytes()
      ),
      // parameters (null)
      N.create(N.Class.UNIVERSAL, N.Type.NULL, !1, "")
    ])
  ]);
  if (e.authenticatedAttributesAsn1 && t.value.push(e.authenticatedAttributesAsn1), t.value.push(N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
    // algorithm
    N.create(
      N.Class.UNIVERSAL,
      N.Type.OID,
      !1,
      N.oidToDer(e.signatureAlgorithm).getBytes()
    ),
    // parameters (null)
    N.create(N.Class.UNIVERSAL, N.Type.NULL, !1, "")
  ])), t.value.push(N.create(
    N.Class.UNIVERSAL,
    N.Type.OCTETSTRING,
    !1,
    e.signature
  )), e.unauthenticatedAttributes.length > 0) {
    for (var a = N.create(N.Class.CONTEXT_SPECIFIC, 1, !0, []), r = 0; r < e.unauthenticatedAttributes.length; ++r) {
      var n = e.unauthenticatedAttributes[r];
      a.values.push(t0(n));
    }
    t.value.push(a);
  }
  return t;
}
function ng(e) {
  for (var t = [], a = 0; a < e.length; ++a)
    t.push(ag(e[a]));
  return t;
}
function t0(e) {
  var t;
  if (e.type === ae.pki.oids.contentType)
    t = N.create(
      N.Class.UNIVERSAL,
      N.Type.OID,
      !1,
      N.oidToDer(e.value).getBytes()
    );
  else if (e.type === ae.pki.oids.messageDigest)
    t = N.create(
      N.Class.UNIVERSAL,
      N.Type.OCTETSTRING,
      !1,
      e.value.bytes()
    );
  else if (e.type === ae.pki.oids.signingTime) {
    var a = /* @__PURE__ */ new Date("1950-01-01T00:00:00Z"), r = /* @__PURE__ */ new Date("2050-01-01T00:00:00Z"), n = e.value;
    if (typeof n == "string") {
      var i = Date.parse(n);
      isNaN(i) ? n.length === 13 ? n = N.utcTimeToDate(n) : n = N.generalizedTimeToDate(n) : n = new Date(i);
    }
    n >= a && n < r ? t = N.create(
      N.Class.UNIVERSAL,
      N.Type.UTCTIME,
      !1,
      N.dateToUtcTime(n)
    ) : t = N.create(
      N.Class.UNIVERSAL,
      N.Type.GENERALIZEDTIME,
      !1,
      N.dateToGeneralizedTime(n)
    );
  }
  return N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
    // AttributeType
    N.create(
      N.Class.UNIVERSAL,
      N.Type.OID,
      !1,
      N.oidToDer(e.type).getBytes()
    ),
    N.create(N.Class.UNIVERSAL, N.Type.SET, !0, [
      // AttributeValue
      t
    ])
  ]);
}
function ig(e) {
  return [
    // ContentType, always Data for the moment
    N.create(
      N.Class.UNIVERSAL,
      N.Type.OID,
      !1,
      N.oidToDer(ae.pki.oids.data).getBytes()
    ),
    // ContentEncryptionAlgorithmIdentifier
    N.create(N.Class.UNIVERSAL, N.Type.SEQUENCE, !0, [
      // Algorithm
      N.create(
        N.Class.UNIVERSAL,
        N.Type.OID,
        !1,
        N.oidToDer(e.algorithm).getBytes()
      ),
      // Parameters (IV)
      e.parameter ? N.create(
        N.Class.UNIVERSAL,
        N.Type.OCTETSTRING,
        !1,
        e.parameter.getBytes()
      ) : void 0
    ]),
    // [0] EncryptedContent
    N.create(N.Class.CONTEXT_SPECIFIC, 0, !0, [
      N.create(
        N.Class.UNIVERSAL,
        N.Type.OCTETSTRING,
        !1,
        e.content.getBytes()
      )
    ])
  ];
}
function N0(e, t, a) {
  var r = {}, n = [];
  if (!N.validate(t, a, r, n)) {
    var i = new Error("Cannot read PKCS#7 message. ASN.1 object is not a supported PKCS#7 message.");
    throw i.errors = i, i;
  }
  var s = N.derToOid(r.contentType);
  if (s !== ae.pki.oids.data)
    throw new Error("Unsupported PKCS#7 message. Only wrapped ContentType Data supported.");
  if (r.encryptedContent) {
    var o = "";
    if (ae.util.isArray(r.encryptedContent))
      for (var u = 0; u < r.encryptedContent.length; ++u) {
        if (r.encryptedContent[u].type !== N.Type.OCTETSTRING)
          throw new Error("Malformed PKCS#7 message, expecting encrypted content constructed of only OCTET STRING objects.");
        o += r.encryptedContent[u].value;
      }
    else
      o = r.encryptedContent;
    e.encryptedContent = {
      algorithm: N.derToOid(r.encAlgorithm),
      parameter: ae.util.createBuffer(r.encParameter.value),
      content: ae.util.createBuffer(o)
    };
  }
  if (r.content) {
    var o = "";
    if (ae.util.isArray(r.content))
      for (var u = 0; u < r.content.length; ++u) {
        if (r.content[u].type !== N.Type.OCTETSTRING)
          throw new Error("Malformed PKCS#7 message, expecting content constructed of only OCTET STRING objects.");
        o += r.content[u].value;
      }
    else
      o = r.content;
    e.content = ae.util.createBuffer(o);
  }
  return e.version = r.version.charCodeAt(0), e.rawCapture = r, r;
}
function Yl(e) {
  if (e.encryptedContent.key === void 0)
    throw new Error("Symmetric key not available.");
  if (e.content === void 0) {
    var t;
    switch (e.encryptedContent.algorithm) {
      case ae.pki.oids["aes128-CBC"]:
      case ae.pki.oids["aes192-CBC"]:
      case ae.pki.oids["aes256-CBC"]:
        t = ae.aes.createDecryptionCipher(e.encryptedContent.key);
        break;
      case ae.pki.oids.desCBC:
      case ae.pki.oids["des-EDE3-CBC"]:
        t = ae.des.createDecryptionCipher(e.encryptedContent.key);
        break;
      default:
        throw new Error("Unsupported symmetric cipher, OID " + e.encryptedContent.algorithm);
    }
    if (t.start(e.encryptedContent.parameter), t.update(e.encryptedContent.content), !t.finish())
      throw new Error("Symmetric decryption failed.");
    e.content = t.output;
  }
}
var st = Re, Un = st.ssh = st.ssh || {};
Un.privateKeyToPutty = function(e, t, a) {
  a = a || "", t = t || "";
  var r = "ssh-rsa", n = t === "" ? "none" : "aes256-cbc", i = "PuTTY-User-Key-File-2: " + r + `\r
`;
  i += "Encryption: " + n + `\r
`, i += "Comment: " + a + `\r
`;
  var s = st.util.createBuffer();
  ea(s, r), Gt(s, e.e), Gt(s, e.n);
  var o = st.util.encode64(s.bytes(), 64), u = Math.floor(o.length / 66) + 1;
  i += "Public-Lines: " + u + `\r
`, i += o;
  var l = st.util.createBuffer();
  Gt(l, e.d), Gt(l, e.p), Gt(l, e.q), Gt(l, e.qInv);
  var c;
  if (!t)
    c = st.util.encode64(l.bytes(), 64);
  else {
    var f = l.length() + 16 - 1;
    f -= f % 16;
    var d = Wa(l.bytes());
    d.truncate(d.length() - f + l.length()), l.putBuffer(d);
    var v = st.util.createBuffer();
    v.putBuffer(Wa("\0\0\0\0", t)), v.putBuffer(Wa("\0\0\0", t));
    var p = st.aes.createEncryptionCipher(v.truncate(8), "CBC");
    p.start(st.util.createBuffer().fillWithByte(0, 16)), p.update(l.copy()), p.finish();
    var h = p.output;
    h.truncate(16), c = st.util.encode64(h.bytes(), 64);
  }
  u = Math.floor(c.length / 66) + 1, i += `\r
Private-Lines: ` + u + `\r
`, i += c;
  var x = Wa("putty-private-key-file-mac-key", t), y = st.util.createBuffer();
  ea(y, r), ea(y, n), ea(y, a), y.putInt32(s.length()), y.putBuffer(s), y.putInt32(l.length()), y.putBuffer(l);
  var g = st.hmac.create();
  return g.start("sha1", x), g.update(y.bytes()), i += `\r
Private-MAC: ` + g.digest().toHex() + `\r
`, i;
};
Un.publicKeyToOpenSSH = function(e, t) {
  var a = "ssh-rsa";
  t = t || "";
  var r = st.util.createBuffer();
  return ea(r, a), Gt(r, e.e), Gt(r, e.n), a + " " + st.util.encode64(r.bytes()) + " " + t;
};
Un.privateKeyToOpenSSH = function(e, t) {
  return t ? st.pki.encryptRsaPrivateKey(
    e,
    t,
    { legacy: !0, algorithm: "aes128" }
  ) : st.pki.privateKeyToPem(e);
};
Un.getPublicKeyFingerprint = function(e, t) {
  t = t || {};
  var a = t.md || st.md.md5.create(), r = "ssh-rsa", n = st.util.createBuffer();
  ea(n, r), Gt(n, e.e), Gt(n, e.n), a.start(), a.update(n.getBytes());
  var i = a.digest();
  if (t.encoding === "hex") {
    var s = i.toHex();
    return t.delimiter ? s.match(/.{2}/g).join(t.delimiter) : s;
  } else {
    if (t.encoding === "binary")
      return i.getBytes();
    if (t.encoding)
      throw new Error('Unknown encoding "' + t.encoding + '".');
  }
  return i;
};
function Gt(e, t) {
  var a = t.toString(16);
  a[0] >= "8" && (a = "00" + a);
  var r = st.util.hexToBytes(a);
  e.putInt32(r.length), e.putBytes(r);
}
function ea(e, t) {
  e.putInt32(t.length), e.putString(t);
}
function Wa() {
  for (var e = st.md.sha1.create(), t = arguments.length, a = 0; a < t; ++a)
    e.update(arguments[a]);
  return e.digest();
}
var Ya = Re, sg = { BASE_URL: "/", DEV: !1, MODE: "production", PROD: !0, SSR: !1, VITE_APP_BASE_SERVER_URL: "http://localhost:7080", VITE_APP_BASE_URL: "/api", VITE_APP_DOCUMENT_TITLE: "平台前端组件库", VITE_APP_INFRASTRUCTURE_SERVICE_URL: "http://localhost:7080", VITE_APP_INFRASTRUCTURE_URL: "/api", VITE_APP_LOGIN_URL: "/", VITE_APP_OAUTH2_SERVICE_URL: "http://localhost:7080", VITE_APP_OAUTH2_URL: "/api", VITE_APP_PUBLIC_PATH: "/", VITE_APP_WORKFLOW_DOMAIN: "/workflow" };
const og = sg.VITE_CRYPTO_PUBLIC_KEY || `-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxcb7zuFV8aHSDUIfARHt
QPT1Qn+o8fxM0S3zNA39wBVjnrc3FMWo2LKc6autHM0WEhKAbftO+pihGjINcO1Y
WJVTvRRbkVGy84ZOxQPs8gfyAR7TqdpzeSjuPxlZTJHy4hRxCxq+ZN6bf4V8oS5g
KbogmemanZPlq4NyIQkbNCWQdpzxMEJjPMGhuWjN+LtIrSrsGKcUL1yUEczdA6lY
3YGAgaD3whd9RW5fpVzOzHKJfekGeaqp5sZmyJVn0iFjw/nB6WSpiX5U1Qkla8FT
HjCyYIJ30gBKMfFZCOtLPilninyCuBaDZ3guNb/3ekQDlq2vYyPZYJpKJCzqrrHs
ywIDAQAB
-----END PUBLIC KEY-----
`;
function lg(e) {
  try {
    const t = og, r = Ya.pki.publicKeyFromPem(t).encrypt(e, "RSA-OAEP", {
      md: Ya.md.sha256.create(),
      mgf1: {
        md: Ya.md.sha256.create()
      }
    });
    return Ya.util.encode64(r);
  } catch (t) {
    return console.error("RSA加密错误:", t), e;
  }
}
const cg = (e, t, a) => {
  try {
    const r = localStorage.getItem("derivedKey") && JSON.parse(localStorage.getItem("derivedKey")), n = Ft.enc.Utf8.parse(a);
    return Ft.AES.encrypt(e, r, {
      iv: n,
      mode: Ft.mode.CBC,
      padding: Ft.pad.Pkcs7
    }).toString();
  } catch (r) {
    return console.warn("AES加密失败:", r), e;
  }
}, ug = (e, t, a) => {
  try {
    const r = localStorage.getItem("derivedKey") && JSON.parse(localStorage.getItem("derivedKey")), n = Ft.enc.Utf8.parse(a), s = Ft.AES.decrypt(e, r, {
      iv: n,
      mode: Ft.mode.CBC,
      padding: Ft.pad.Pkcs7
    }).toString(Ft.enc.Utf8);
    return s || e;
  } catch (r) {
    return console.warn("AES解密失败:", r), e;
  }
}, fg = () => {
  const e = new Uint8Array(8);
  return window.crypto.getRandomValues(e), Array.from(e).map((t) => t.toString(16).padStart(2, "0")).join("");
}, dg = () => {
  const e = new Uint8Array(16);
  return window.crypto.getRandomValues(e), Array.from(e).map((t) => t.toString(16).padStart(2, "0")).join("");
};
class pg {
  constructor() {
    On(this, "keyMap", /* @__PURE__ */ new Map());
  }
  // 生成请求的唯一标识
  generateRequestId(t) {
    return `${t.method}_${t.url}_${JSON.stringify(
      t.params
    )}_${JSON.stringify(t.data)}`;
  }
  // 设置密钥
  setKeys(t, a) {
    const r = this.generateRequestId(t);
    this.keyMap.set(r, a);
  }
  // 获取密钥
  getKeys(t) {
    const a = this.generateRequestId(t);
    return this.keyMap.get(a);
  }
  // 删除密钥
  removeKeys(t) {
    const a = this.generateRequestId(t);
    this.keyMap.delete(a);
  }
}
const Di = new pg();
var hg = { BASE_URL: "/", DEV: !1, MODE: "production", PROD: !0, SSR: !1, VITE_APP_BASE_SERVER_URL: "http://localhost:7080", VITE_APP_BASE_URL: "/api", VITE_APP_DOCUMENT_TITLE: "平台前端组件库", VITE_APP_INFRASTRUCTURE_SERVICE_URL: "http://localhost:7080", VITE_APP_INFRASTRUCTURE_URL: "/api", VITE_APP_LOGIN_URL: "/", VITE_APP_OAUTH2_SERVICE_URL: "http://localhost:7080", VITE_APP_OAUTH2_URL: "/api", VITE_APP_PUBLIC_PATH: "/", VITE_APP_WORKFLOW_DOMAIN: "/workflow" };
const r0 = (e, t, a = 3, r = 1) => r > a ? e : Array.isArray(e) ? e.map(
  (n) => r0(n, t, a, r + 1)
) : (e && typeof e == "object" && Object.keys(e).forEach((n) => {
  const i = e[n];
  console.log(n.toLowerCase().includes("phone")), n.toLowerCase().includes("phone") || n.toLowerCase().includes("account") ? e[n] = ug(i, t.aesKey, t.iv) : i && (typeof i == "object" || Array.isArray(i)) && (e[n] = r0(i, t, a, r + 1));
}), e), vg = {
  // 默认地址请求地址，可在 .env.** 文件中修改
  baseURL: hg.VITE_API_URL,
  // 设置超时时间
  timeout: on.TIMEOUT,
  // 跨域时候允许携带凭证
  withCredentials: !0
}, Eo = new mh();
class xg {
  constructor(t) {
    On(this, "service");
    this.service = wo.create(t), this.service.interceptors.request.use(
      (a) => {
        var n;
        localStorage.getItem("Authorization") && ((n = a.url) == null ? void 0 : n.indexOf("oauth2/login")) == -1 && (a.headers.Authorization = `${localStorage.getItem(
          "Authorization"
        )}`);
        const r = localStorage.getItem("tenant");
        r && r !== "undefined" && (a.headers.TenantId = JSON.parse(r).id);
        try {
          const i = localStorage.getItem("aesKey"), s = fg();
          i && (Di.setKeys(a, { aesKey: i, iv: s }), a.headers["platform-encrypt-key"] = lg(
            i.trim()
          ), a.headers["platform-encrypt-iv"] = s, a.data && typeof a.data == "object" && Object.keys(a.data).forEach((o) => {
            (o.toLowerCase().includes("phone") || o.toLowerCase().includes("account") || o.toLowerCase().includes("password")) && (a.data[o] = cg(a.data[o], i, s));
          }));
        } catch (i) {
          console.error("加密失败:", i);
        }
        return a.cancel ?? (a.cancel = !0), a.cancel && Eo.addPending(a), a.loading ?? (a.loading = !0), a.loading && gh(), a.headers && a.headers.set, a;
      },
      (a) => Promise.reject(a)
    ), this.service.interceptors.response.use(
      (a) => {
        const { data: r, config: n } = a;
        if (Eo.removePending(n), n.loading && qs(), r.code == on.OVERDUE)
          return ze.error(r.msg), Promise.reject(r);
        if (r.code && r.code !== on.SUCCESS)
          return ze.error(r.errorMessage), Promise.reject(r);
        try {
          const i = Di.getKeys(n);
          r.data && i && (r.data = r0(r.data, i, 2));
        } catch (i) {
          console.error("解密失败:", i);
        } finally {
          Di.removeKeys(a.config);
        }
        return r;
      },
      async (a) => {
        const { response: r } = a;
        return qs(), a.message.indexOf("timeout") !== -1 && ze.error("请求超时！请您稍后重试"), a.message.indexOf("Network Error") !== -1 && ze.error("网络错误！请您稍后重试"), r && yh(r.status), Promise.reject(a);
      }
    );
  }
  /**
   * @description 常用请求方法封装
   */
  get(t, a, r = {}) {
    return this.service.get(t, { params: a, ...r });
  }
  post(t, a, r = {}) {
    return this.service.post(t, a, r);
  }
  put(t, a, r = {}) {
    return this.service.put(t, a, r);
  }
  delete(t, a, r = {}) {
    return this.service.delete(t, { params: a, ...r });
  }
  download(t, a, r = {}) {
    return this.service.post(t, a, { ...r, responseType: "blob" });
  }
}
const Ha = new xg(vg), gg = (e) => Ha.post(`${nl}/login`, e, {
  loading: !1
}), yg = () => Ha.get(
  `${nl}/captcha/image`,
  {},
  {
    loading: !1
  }
), mg = () => Ha.post(
  `${p0}/sys/loginlog`,
  {},
  { loading: !1 }
), Cg = () => Ha.post(
  `${p0}/sys/authoritys/current`,
  {},
  { loading: !1 }
);
function Eg() {
  return Ha.get(`${p0}/sys/user/current`);
}
const bg = ca({
  id: "userStore",
  state: () => ({
    token: "",
    userInfo: { name: "" },
    tenantInfo: {},
    menuList: []
  }),
  getters: {},
  actions: {
    // Set menuList
    setMenuList(e) {
      this.menuList = e;
    },
    // Set Token
    setToken(e) {
      this.token = e;
    },
    // Set setUserInfo
    setUserInfo(e) {
      this.userInfo = e;
    },
    // Set setTenantInfo
    setTenantInfo(e) {
      this.tenantInfo = e;
    },
    clearUserData() {
      this.token = "", this.userInfo = { name: "" }, this.tenantInfo = {}, this.menuList = [];
    }
  },
  persist: l0("userStore")
}), Sg = ca({
  id: "vz-keepAlive",
  state: () => ({
    keepAliveName: []
  }),
  actions: {
    // Add KeepAliveName
    async addKeepAliveName(e) {
      !this.keepAliveName.includes(e) && this.keepAliveName.push(e);
    },
    // Remove KeepAliveName
    async removeKeepAliveName(e) {
      this.keepAliveName = this.keepAliveName.filter((t) => t !== e);
    },
    // Set KeepAliveName
    async setKeepAliveName(e = []) {
      this.keepAliveName = e;
    }
  }
});
function Ag() {
  const e = $c(), t = bg(), a = Sg(), r = se(!1), n = se(!0), i = se(null), s = async () => {
    try {
      const { data: c } = await Cg();
      t.setMenuList(c);
    } catch (c) {
      console.error("获取菜单失败:", c);
    }
  }, o = async () => {
    try {
      const { data: c } = await Eg();
      t.setUserInfo(c);
    } catch (c) {
      console.error("获取用户信息失败:", c);
    }
  }, u = async (c) => {
    try {
      const f = await Ba().findById(c);
      localStorage.setItem("tenant", JSON.stringify(f.data)), t.setTenantInfo(f.data);
    } catch (f) {
      console.error("获取租户信息失败:", f);
    }
  };
  return {
    loading: r,
    isShowForm: n,
    chooseData: i,
    handleLoginSuccess: async (c, f, d) => {
      var v, p;
      if (!c.access_token) {
        r.value = !1, n.value = !1, i.value = c;
        return;
      }
      localStorage.setItem("Authorization", `bearer ${c.access_token}`), (v = c.loginUser) != null && v.tenantId && (localStorage.setItem(
        "tenant",
        JSON.stringify({ id: c.loginUser.tenantId })
      ), u((p = c.loginUser) == null ? void 0 : p.tenantId)), t.setToken(c.access_token), await o(), await f(), await s(), a.setKeepAliveName([]), d && (e.push("/home"), window.location.reload()), setTimeout(() => {
        var h;
        Nc({
          title: op(),
          message: `${(h = c.loginUser) == null ? void 0 : h.username} 您好！欢迎登录!`,
          type: "success",
          duration: 3e3
        }), mg();
      }, 1e3);
    }
  };
}
var _g = { BASE_URL: "/", DEV: !1, MODE: "production", PROD: !0, SSR: !1, VITE_APP_BASE_SERVER_URL: "http://localhost:7080", VITE_APP_BASE_URL: "/api", VITE_APP_DOCUMENT_TITLE: "平台前端组件库", VITE_APP_INFRASTRUCTURE_SERVICE_URL: "http://localhost:7080", VITE_APP_INFRASTRUCTURE_URL: "/api", VITE_APP_LOGIN_URL: "/", VITE_APP_OAUTH2_SERVICE_URL: "http://localhost:7080", VITE_APP_OAUTH2_URL: "/api", VITE_APP_PUBLIC_PATH: "/", VITE_APP_WORKFLOW_DOMAIN: "/workflow" };
const Bg = /* @__PURE__ */ $e("div", { class: "font12 mt30 login-msg" }, " * 温馨提示：建议使用谷歌、Microsoft Edge、版本 79.0.1072.62 及以上浏览器、360浏览器，并关闭兼容模式。 ", -1), Tg = {
  key: 1,
  class: "login-btn"
}, Ig = { class: "flex-between" }, wg = /* @__PURE__ */ Ue({
  __name: "index",
  props: {
    initDynamicRouter: {}
  },
  setup(e) {
    const t = e, { loading: a, isShowForm: r, chooseData: n, handleLoginSuccess: i } = Ag(), s = se(), o = or({
      account: [{ required: !0, message: "请输入用户名", trigger: "blur" }],
      password: [{ required: !0, message: "请输入密码", trigger: "blur" }],
      captcha: [{ required: !0, message: "请输入验证码", trigger: "blur" }]
    }), u = se(0), l = se(), c = or({
      account: "",
      password: "",
      captcha: "",
      captchaKey: "",
      terminal: "pc",
      type: "login"
    }), f = () => {
      r.value = !0;
    }, d = (m) => {
      m && m.validate(async (C) => {
        C && await v();
      });
    }, v = async (m) => {
      m && localStorage.setItem("tenant", JSON.stringify({ id: m })), a.value = !0;
      try {
        const { data: C } = await gg(c);
        r.value = !0, await i(C, t.initDynamicRouter, !0);
      } catch (C) {
        C.code === 100 && C.data && (r.value = !1, n.value = C.data), C.code === 101 && g();
      } finally {
        a.value = !1;
      }
    }, p = (m) => {
      const C = m ? m.toString() : "";
      v(C);
    }, h = (m) => {
      m && m.resetFields();
    }, x = _g.VITE_CRYPTO_SALT || "8710FB89B0ECD98F5A98E45D3BFB549F", y = async () => {
      const m = dg(), C = Ft.enc.Utf8.parse(x), S = 65536, _ = 256 / 32, T = Ft.PBKDF2(m, C, {
        keySize: _,
        iterations: S,
        hasher: hh.algo.SHA256
      });
      localStorage.setItem("aesKey", m), localStorage.setItem("derivedKey", JSON.stringify(T));
    }, g = async () => {
      try {
        const { data: m } = await yg();
        l.value = m, c.captchaKey = m.captchaKey;
      } catch (m) {
        console.log(m);
      }
    };
    return yt(async () => {
      await y(), await g(), document.onkeydown = (m) => {
        if (m = window.event || m, m.code === "Enter" || m.code === "enter" || m.code === "NumpadEnter") {
          if (a.value) return;
          d(s.value);
        }
      };
    }), (m, C) => {
      const S = Q("el-input"), _ = Q("el-form-item"), T = Q("el-image"), D = Q("el-form"), P = Q("el-button"), I = Q("el-link"), B = Q("el-radio"), R = Q("el-radio-group"), F = Q("el-card");
      return z(), he("div", null, [
        ye(r) ? (z(), ve(D, {
          key: 0,
          ref_key: "loginFormRef",
          ref: s,
          model: c,
          rules: o,
          size: "large"
        }, {
          default: re(() => [
            Z(_, { prop: "account" }, {
              default: re(() => [
                Z(S, {
                  modelValue: c.account,
                  "onUpdate:modelValue": C[0] || (C[0] = (U) => c.account = U),
                  "prefix-icon": ye(Uc),
                  placeholder: "请输入用户名"
                }, null, 8, ["modelValue", "prefix-icon"])
              ]),
              _: 1
            }),
            Z(_, { prop: "password" }, {
              default: re(() => [
                Z(S, {
                  modelValue: c.password,
                  "onUpdate:modelValue": C[1] || (C[1] = (U) => c.password = U),
                  "prefix-icon": ye(Oc),
                  type: "password",
                  placeholder: "请输入密码",
                  "show-password": ""
                }, null, 8, ["modelValue", "prefix-icon"])
              ]),
              _: 1
            }),
            Z(_, { prop: "captcha" }, {
              default: re(() => [
                Z(S, {
                  modelValue: c.captcha,
                  "onUpdate:modelValue": C[3] || (C[3] = (U) => c.captcha = U),
                  "prefix-icon": ye(Vc),
                  placeholder: "请输入验证码"
                }, {
                  append: re(() => {
                    var U;
                    return [
                      Z(T, {
                        style: { cursor: "pointer", height: "38px", width: "100%" },
                        src: (U = l.value) == null ? void 0 : U.captchaImage,
                        onClick: C[2] || (C[2] = (M) => g())
                      }, null, 8, ["src"])
                    ];
                  }),
                  _: 1
                }, 8, ["modelValue", "prefix-icon"])
              ]),
              _: 1
            }),
            Bg
          ]),
          _: 1
        }, 8, ["model", "rules"])) : qe("", !0),
        ye(r) ? (z(), he("div", Tg, [
          Z(P, {
            icon: ye(Mc),
            round: "",
            size: "large",
            onClick: C[4] || (C[4] = (U) => h(s.value))
          }, {
            default: re(() => [
              rt(" 重置 ")
            ]),
            _: 1
          }, 8, ["icon"]),
          Z(P, {
            icon: ye(Kc),
            round: "",
            size: "large",
            type: "primary",
            loading: ye(a),
            onClick: C[5] || (C[5] = (U) => d(s.value))
          }, {
            default: re(() => [
              rt(" 登录 ")
            ]),
            _: 1
          }, 8, ["icon", "loading"])
        ])) : (z(), ve(F, {
          key: 2,
          shadow: "never"
        }, {
          header: re(() => [
            $e("div", Ig, [
              rt(" 请选租户进入 "),
              Z(I, {
                type: "primary",
                onClick: f
              }, {
                default: re(() => [
                  rt("返回")
                ]),
                _: 1
              })
            ])
          ]),
          default: re(() => [
            Z(R, {
              modelValue: u.value,
              "onUpdate:modelValue": C[6] || (C[6] = (U) => u.value = U),
              onChange: p
            }, {
              default: re(() => [
                (z(!0), he(it, null, St(ye(n), (U) => (z(), ve(B, {
                  size: "default",
                  style: { "margin-bottom": "10px" },
                  value: U.id,
                  label: U.id,
                  key: U.id,
                  border: ""
                }, {
                  default: re(() => [
                    rt(We(U.tenantName), 1)
                  ]),
                  _: 2
                }, 1032, ["value", "label"]))), 128))
              ]),
              _: 1
            }, 8, ["modelValue"])
          ]),
          _: 1
        }))
      ]);
    };
  }
}), Ql = ca("themeConfig", {
  state: () => ({
    themeConfig: {
      // 是否开启布局配置抽屉
      isDrawer: !1,
      /**
       * 全局主题
       */
      // 默认 primary 主题颜色
      primary: "#2D62E2",
      // 是否开启深色模式
      isIsDark: !1,
      /**
       * 顶栏设置
       */
      // 默认顶栏导航背景颜色
      topBar: "#ffffff",
      // 默认顶栏导航字体颜色
      topBarColor: "#606266",
      // 是否开启顶栏背景颜色渐变
      isTopBarColorGradual: !1,
      /**
       * 菜单设置
       */
      // 默认菜单导航背景颜色
      menuBar: "#ffffff",
      // 默认菜单导航字体颜色
      menuBarColor: "rgba(0, 0, 0, 0.9)",
      // 默认菜单高亮背景色
      menuBarActiveColor: "#f2f3ff",
      // 是否开启菜单背景颜色渐变
      isMenuBarColorGradual: !1,
      /**
       * 分栏设置
       */
      // 默认分栏菜单背景颜色
      columnsMenuBar: "#545c64",
      // 默认分栏菜单字体颜色
      columnsMenuBarColor: "#e6e6e6",
      // 是否开启分栏菜单背景颜色渐变
      isColumnsMenuBarColorGradual: !1,
      // 是否开启分栏菜单鼠标悬停预加载(预览菜单)
      isColumnsMenuHoverPreload: !1,
      /**
       * 界面设置
       */
      // 是否开启菜单水平折叠效果
      isCollapse: !1,
      // 是否开启菜单手风琴效果
      isUniqueOpened: !0,
      // 是否开启固定 Header
      isFixedHeader: !0,
      // 初始化变量，用于更新菜单 el-scrollbar 的高度，请勿删除
      isFixedHeaderChange: !1,
      // 是否开启经典布局分割菜单（仅经典布局生效）
      isClassicSplitMenu: !1,
      // 是否开启自动锁屏
      isLockScreen: !1,
      // 开启自动锁屏倒计时(s/秒)
      lockScreenTime: 30,
      /**
       * 界面显示
       */
      // 是否开启侧边栏 Logo
      isShowLogo: !0,
      // 初始化变量，用于 el-scrollbar 的高度更新，请勿删除
      isShowLogoChange: !1,
      // 是否开启 Breadcrumb，强制经典、横向布局不显示
      isBreadcrumb: !0,
      // 是否开启 Tagsview
      isTagsview: !0,
      // 是否开启 Breadcrumb 图标
      isBreadcrumbIcon: !1,
      // 是否开启 Tagsview 图标
      isTagsviewIcon: !1,
      // 是否开启 TagsView 缓存
      isCacheTagsView: !1,
      // 是否开启 TagsView 拖拽
      isSortableTagsView: !0,
      // 是否开启 TagsView 共用
      isShareTagsView: !1,
      // 是否开启 Footer 底部版权信息
      isFooter: !1,
      // 是否开启灰色模式
      isGrayscale: !1,
      // 是否开启色弱模式
      isInvert: !1,
      // 是否开启水印
      isWartermark: !0,
      // 水印文案
      wartermarkText: "平台管理",
      /**
       * 其它设置
       */
      // Tagsview 风格：可选值"<tags-style-one|tags-style-four|tags-style-five>"，默认 tags-style-five
      // 定义的值与 `/src/layout/navBars/tagsView/tagsView.vue` 中的 class 同名
      tagsStyle: "tags-style-five",
      // 主页面切换动画：可选值"<slide-right|slide-left|opacitys>"，默认 slide-right
      animation: "slide-right",
      // 分栏高亮风格：可选值"<columns-round|columns-card>"，默认 columns-round
      columnsAsideStyle: "columns-round",
      // 分栏布局风格：可选值"<columns-horizontal|columns-vertical>"，默认 columns-horizontal
      columnsAsideLayout: "columns-vertical",
      /**
       * 布局切换
       * 注意：为了演示，切换布局时，颜色会被还原成默认，代码位置：/@/layout/navBars/topBar/setings.vue
       * 中的 `initSetLayoutChange(设置布局切换，重置主题样式)` 方法
       */
      // 布局切换：可选值"<defaults|classic|transverse|columns>"，默认 defaults
      layout: "defaults",
      /**
       * 后端控制路由
       */
      // 是否开启后端控制路由
      isRequestRoutes: !1,
      /**
       * 全局网站标题 / 副标题
       */
      // 网站主标题（菜单导航、浏览器当前网页标题）
      globalTitle: "平台管理",
      // 网站副标题（登录页顶部文字）
      globalViceTitle: "平台管理",
      // 网站副标题（登录页顶部文字）
      globalViceTitleMsg: "平台管理",
      // 默认初始语言，可选值"<zh-cn|en|zh-tw>"，默认 zh-cn
      globalI18n: "zh-cn",
      // 默认全局组件大小，可选值"<large|'default'|small>"，默认 'large'
      globalComponentSize: "default"
    }
  }),
  actions: {
    setThemeConfig(e) {
      this.themeConfig = e.themeConfig;
    }
  }
}), Dg = { class: "table-search-container" }, Ng = /* @__PURE__ */ Ue({
  __name: "search",
  props: {
    // 搜索表单
    search: {
      type: Array,
      default: () => []
    },
    searchButtons: {
      type: Array,
      default: () => []
    }
  },
  emits: ["search", "add"],
  setup(e, { emit: t }) {
    const a = e, r = t, n = [
      new Date(2e3, 1, 1, 0, 0, 0),
      new Date(2e3, 2, 1, 23, 59, 59)
    ], i = se(), s = Ql(), { themeConfig: o } = ua(s), u = or({
      form: {},
      isToggle: !1
    }), l = (v) => {
      v.value === "add" && r("add");
    }, c = (v) => {
      v && v.validate((p) => {
        if (p) {
          const h = {};
          for (const x in u.form)
            u.form.hasOwnProperty(x) && u.form[x] !== "" && (h[x] = u.form[x]);
          r("search", h);
        } else
          return !1;
      });
    }, f = (v) => {
      v && (v.resetFields(), r("search", u.form));
    }, d = () => {
      if (a.search.length <= 0) return !1;
      a.search.forEach((v) => u.form[v.prop] = "");
    };
    return yt(() => {
      d();
    }), (v, p) => {
      const h = Q("el-date-picker"), x = Q("vz-user"), y = Q("el-option"), g = Q("el-select"), m = Q("el-input"), C = Q("el-form-item"), S = Q("el-col"), _ = Q("SvgIcon"), T = Q("el-link"), D = Q("el-button"), P = Q("el-row"), I = Q("el-form");
      return z(), he("div", Dg, [
        a.search.length > 0 ? (z(), ve(I, {
          key: 0,
          ref_key: "tableSearchRef",
          ref: i,
          model: u.form,
          size: ye(o).globalComponentSize,
          "label-width": "100px",
          class: "table-form"
        }, {
          default: re(() => [
            Z(P, null, {
              default: re(() => [
                (z(!0), he(it, null, St(e.search, (B, R) => aa((z(), ve(S, {
                  xs: 24,
                  sm: 12,
                  md: 8,
                  lg: 8,
                  xl: 4,
                  class: "mb20",
                  key: R
                }, {
                  default: re(() => [
                    B.type !== "" ? (z(), ve(C, {
                      key: 0,
                      label: B.label,
                      prop: B.prop
                    }, {
                      default: re(() => [
                        B.type === "date" ? (z(), ve(h, {
                          key: 0,
                          modelValue: u.form[B.prop],
                          "onUpdate:modelValue": (F) => u.form[B.prop] = F,
                          type: "date",
                          placeholder: B.placeholder,
                          style: { width: "100%" }
                        }, null, 8, ["modelValue", "onUpdate:modelValue", "placeholder"])) : B.type == "datetimerange" ? (z(), ve(h, {
                          key: 1,
                          modelValue: u.form[B.prop],
                          "onUpdate:modelValue": (F) => u.form[B.prop] = F,
                          "default-time": n,
                          type: "datetimerange",
                          "range-separator": "至",
                          "start-placeholder": "开始时间",
                          "end-placeholder": "结束时间"
                        }, null, 8, ["modelValue", "onUpdate:modelValue"])) : B.type == "user" ? (z(), ve(x, {
                          key: 2,
                          style: { width: "100%" },
                          modelValue: u.form[B.prop],
                          "onUpdate:modelValue": (F) => u.form[B.prop] = F
                        }, null, 8, ["modelValue", "onUpdate:modelValue"])) : B.options ? (z(), ve(g, {
                          key: 3,
                          modelValue: u.form[B.prop],
                          "onUpdate:modelValue": (F) => u.form[B.prop] = F,
                          placeholder: B.placeholder,
                          style: { width: "100%" }
                        }, {
                          default: re(() => [
                            (z(!0), he(it, null, St(B.options, (F) => (z(), ve(y, {
                              key: F.value,
                              label: F.label,
                              value: F.value
                            }, null, 8, ["label", "value"]))), 128))
                          ]),
                          _: 2
                        }, 1032, ["modelValue", "onUpdate:modelValue", "placeholder"])) : (z(), ve(m, {
                          key: 4,
                          modelValue: u.form[B.prop],
                          "onUpdate:modelValue": (F) => u.form[B.prop] = F,
                          placeholder: B.placeholder,
                          clearable: "",
                          style: { width: "100%" }
                        }, null, 8, ["modelValue", "onUpdate:modelValue", "placeholder"]))
                      ]),
                      _: 2
                    }, 1032, ["label", "prop"])) : qe("", !0)
                  ]),
                  _: 2
                }, 1024)), [
                  [i0, R === 0 || u.isToggle]
                ])), 128)),
                Z(S, {
                  xs: 24,
                  sm: 12,
                  md: 12,
                  lg: 12,
                  xl: 12,
                  class: "mb20"
                }, {
                  default: re(() => [
                    Z(C, {
                      class: "table-form-btn",
                      "label-width": e.search.length <= 1 ? "10px" : "60px"
                    }, n0({
                      default: re(() => [
                        $e("div", null, [
                          Z(D, {
                            type: "primary",
                            onClick: p[1] || (p[1] = (B) => c(i.value)),
                            icon: ye(Ua)
                          }, {
                            default: re(() => [
                              rt("查询 ")
                            ]),
                            _: 1
                          }, 8, ["icon"]),
                          Z(D, {
                            class: "ml10",
                            onClick: p[2] || (p[2] = (B) => f(i.value)),
                            plain: "",
                            icon: ye(Cn)
                          }, {
                            default: re(() => [
                              rt(" 重置 ")
                            ]),
                            _: 1
                          }, 8, ["icon"]),
                          (z(!0), he(it, null, St(e.searchButtons, (B) => (z(), ve(D, {
                            key: B.value,
                            type: B.type,
                            class: "ml10",
                            onClick: (R) => l(B)
                          }, {
                            default: re(() => [
                              Z(_, {
                                name: B.icon,
                                style: { "vertical-align": "middle" }
                              }, null, 8, ["name"]),
                              rt(" " + We(B.label), 1)
                            ]),
                            _: 2
                          }, 1032, ["type", "onClick"]))), 128))
                        ])
                      ]),
                      _: 2
                    }, [
                      e.search.length > 1 ? {
                        name: "label",
                        fn: re(() => [
                          $e("div", {
                            class: "table-form-btn-toggle ml10",
                            onClick: p[0] || (p[0] = (B) => u.isToggle = !u.isToggle)
                          }, [
                            Z(T, {
                              type: "primary",
                              underline: !1
                            }, {
                              default: re(() => [
                                rt(We(u.isToggle ? "收起" : "展开"), 1),
                                Z(_, {
                                  name: u.isToggle ? "ele-ArrowUp" : "ele-ArrowDown"
                                }, null, 8, ["name"])
                              ]),
                              _: 1
                            })
                          ])
                        ]),
                        key: "0"
                      } : void 0
                    ]), 1032, ["label-width"])
                  ]),
                  _: 1
                })
              ]),
              _: 1
            })
          ]),
          _: 1
        }, 8, ["model", "size"])) : qe("", !0)
      ]);
    };
  }
}), Rg = /* @__PURE__ */ Or(Ng, [["__scopeId", "data-v-303c0258"]]);
var kg = { exports: {} };
(function(e, t) {
  (function(r, n) {
    e.exports = n();
  })(window, function() {
    return (
      /******/
      function(a) {
        var r = {};
        function n(i) {
          if (r[i])
            return r[i].exports;
          var s = r[i] = {
            /******/
            i,
            /******/
            l: !1,
            /******/
            exports: {}
            /******/
          };
          return a[i].call(s.exports, s, s.exports, n), s.l = !0, s.exports;
        }
        return n.m = a, n.c = r, n.d = function(i, s, o) {
          n.o(i, s) || Object.defineProperty(i, s, { enumerable: !0, get: o });
        }, n.r = function(i) {
          typeof Symbol < "u" && Symbol.toStringTag && Object.defineProperty(i, Symbol.toStringTag, { value: "Module" }), Object.defineProperty(i, "__esModule", { value: !0 });
        }, n.t = function(i, s) {
          if (s & 1 && (i = n(i)), s & 8 || s & 4 && typeof i == "object" && i && i.__esModule) return i;
          var o = /* @__PURE__ */ Object.create(null);
          if (n.r(o), Object.defineProperty(o, "default", { enumerable: !0, value: i }), s & 2 && typeof i != "string") for (var u in i) n.d(o, u, (function(l) {
            return i[l];
          }).bind(null, u));
          return o;
        }, n.n = function(i) {
          var s = i && i.__esModule ? (
            /******/
            function() {
              return i.default;
            }
          ) : (
            /******/
            function() {
              return i;
            }
          );
          return n.d(s, "a", s), s;
        }, n.o = function(i, s) {
          return Object.prototype.hasOwnProperty.call(i, s);
        }, n.p = "", n(n.s = 0);
      }({
        /***/
        "./src/index.js": (
          /*!**********************!*\
            !*** ./src/index.js ***!
            \**********************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r), n(
              /*! ./sass/index.scss */
              "./src/sass/index.scss"
            );
            var i = n(
              /*! ./js/init */
              "./src/js/init.js"
            ), s = i.default.init;
            typeof window < "u" && (window.printJS = s), r.default = s;
          }
        ),
        /***/
        "./src/js/browser.js": (
          /*!***************************!*\
            !*** ./src/js/browser.js ***!
            \***************************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r);
            var i = {
              // Firefox 1.0+
              isFirefox: function() {
                return typeof InstallTrigger < "u";
              },
              // Internet Explorer 6-11
              isIE: function() {
                return navigator.userAgent.indexOf("MSIE") !== -1 || !!document.documentMode;
              },
              // Edge 20+
              isEdge: function() {
                return !i.isIE() && !!window.StyleMedia;
              },
              // Chrome 1+
              isChrome: function() {
                var o = arguments.length > 0 && arguments[0] !== void 0 ? arguments[0] : window;
                return !!o.chrome;
              },
              // At least Safari 3+: "[object HTMLElementConstructor]"
              isSafari: function() {
                return Object.prototype.toString.call(window.HTMLElement).indexOf("Constructor") > 0 || navigator.userAgent.toLowerCase().indexOf("safari") !== -1;
              },
              // IOS Chrome
              isIOSChrome: function() {
                return navigator.userAgent.toLowerCase().indexOf("crios") !== -1;
              }
            };
            r.default = i;
          }
        ),
        /***/
        "./src/js/functions.js": (
          /*!*****************************!*\
            !*** ./src/js/functions.js ***!
            \*****************************/
          /*! exports provided: addWrapper, capitalizePrint, collectStyles, addHeader, cleanUp, isRawHTML */
          /***/
          function(a, r, n) {
            n.r(r), n.d(r, "addWrapper", function() {
              return u;
            }), n.d(r, "capitalizePrint", function() {
              return l;
            }), n.d(r, "collectStyles", function() {
              return c;
            }), n.d(r, "addHeader", function() {
              return d;
            }), n.d(r, "cleanUp", function() {
              return v;
            }), n.d(r, "isRawHTML", function() {
              return p;
            });
            var i = n(
              /*! ./modal */
              "./src/js/modal.js"
            ), s = n(
              /*! ./browser */
              "./src/js/browser.js"
            );
            function o(h) {
              "@babel/helpers - typeof";
              return typeof Symbol == "function" && typeof Symbol.iterator == "symbol" ? o = function(y) {
                return typeof y;
              } : o = function(y) {
                return y && typeof Symbol == "function" && y.constructor === Symbol && y !== Symbol.prototype ? "symbol" : typeof y;
              }, o(h);
            }
            function u(h, x) {
              var y = "font-family:" + x.font + " !important; font-size: " + x.font_size + " !important; width:100%;";
              return '<div style="' + y + '">' + h + "</div>";
            }
            function l(h) {
              return h.charAt(0).toUpperCase() + h.slice(1);
            }
            function c(h, x) {
              for (var y = document.defaultView || window, g = "", m = y.getComputedStyle(h, ""), C = 0; C < m.length; C++)
                (x.targetStyles.indexOf("*") !== -1 || x.targetStyle.indexOf(m[C]) !== -1 || f(x.targetStyles, m[C])) && m.getPropertyValue(m[C]) && (g += m[C] + ":" + m.getPropertyValue(m[C]) + ";");
              return g += "max-width: " + x.maxWidth + "px !important; font-size: " + x.font_size + " !important;", g;
            }
            function f(h, x) {
              for (var y = 0; y < h.length; y++)
                if (o(x) === "object" && x.indexOf(h[y]) !== -1) return !0;
              return !1;
            }
            function d(h, x) {
              var y = document.createElement("div");
              if (p(x.header))
                y.innerHTML = x.header;
              else {
                var g = document.createElement("h1"), m = document.createTextNode(x.header);
                g.appendChild(m), g.setAttribute("style", x.headerStyle), y.appendChild(g);
              }
              h.insertBefore(y, h.childNodes[0]);
            }
            function v(h) {
              h.showModal && i.default.close(), h.onLoadingEnd && h.onLoadingEnd(), (h.showModal || h.onLoadingStart) && window.URL.revokeObjectURL(h.printable);
              var x = "mouseover";
              (s.default.isChrome() || s.default.isFirefox()) && (x = "focus");
              var y = function g() {
                window.removeEventListener(x, g), h.onPrintDialogClose();
                var m = document.getElementById(h.frameId);
                m && m.remove();
              };
              window.addEventListener(x, y);
            }
            function p(h) {
              var x = new RegExp("<([A-Za-z][A-Za-z0-9]*)\\b[^>]*>(.*?)</\\1>");
              return x.test(h);
            }
          }
        ),
        /***/
        "./src/js/html.js": (
          /*!************************!*\
            !*** ./src/js/html.js ***!
            \************************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r);
            var i = n(
              /*! ./functions */
              "./src/js/functions.js"
            ), s = n(
              /*! ./print */
              "./src/js/print.js"
            );
            function o(c) {
              "@babel/helpers - typeof";
              return typeof Symbol == "function" && typeof Symbol.iterator == "symbol" ? o = function(d) {
                return typeof d;
              } : o = function(d) {
                return d && typeof Symbol == "function" && d.constructor === Symbol && d !== Symbol.prototype ? "symbol" : typeof d;
              }, o(c);
            }
            r.default = {
              print: function(f, d) {
                var v = l(f.printable) ? f.printable : document.getElementById(f.printable);
                if (!v) {
                  window.console.error("Invalid HTML element id: " + f.printable);
                  return;
                }
                f.printableElement = u(v, f), f.header && Object(i.addHeader)(f.printableElement, f), s.default.send(f, d);
              }
            };
            function u(c, f) {
              for (var d = c.cloneNode(), v = Array.prototype.slice.call(c.childNodes), p = 0; p < v.length; p++)
                if (f.ignoreElements.indexOf(v[p].id) === -1) {
                  var h = u(v[p], f);
                  d.appendChild(h);
                }
              switch (f.scanStyles && c.nodeType === 1 && d.setAttribute("style", Object(i.collectStyles)(c, f)), c.tagName) {
                case "SELECT":
                  d.value = c.value;
                  break;
                case "CANVAS":
                  d.getContext("2d").drawImage(c, 0, 0);
                  break;
              }
              return d;
            }
            function l(c) {
              return o(c) === "object" && c && (c instanceof HTMLElement || c.nodeType === 1);
            }
          }
        ),
        /***/
        "./src/js/image.js": (
          /*!*************************!*\
            !*** ./src/js/image.js ***!
            \*************************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r);
            var i = n(
              /*! ./functions */
              "./src/js/functions.js"
            ), s = n(
              /*! ./print */
              "./src/js/print.js"
            ), o = n(
              /*! ./browser */
              "./src/js/browser.js"
            );
            r.default = {
              print: function(l, c) {
                l.printable.constructor !== Array && (l.printable = [l.printable]), l.printableElement = document.createElement("div"), l.printable.forEach(function(f) {
                  var d = document.createElement("img");
                  if (d.setAttribute("style", l.imageStyle), d.src = f, o.default.isFirefox()) {
                    var v = d.src;
                    d.src = v;
                  }
                  var p = document.createElement("div");
                  p.appendChild(d), l.printableElement.appendChild(p);
                }), l.header && Object(i.addHeader)(l.printableElement, l), s.default.send(l, c);
              }
            };
          }
        ),
        /***/
        "./src/js/init.js": (
          /*!************************!*\
            !*** ./src/js/init.js ***!
            \************************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r);
            var i = n(
              /*! ./browser */
              "./src/js/browser.js"
            ), s = n(
              /*! ./modal */
              "./src/js/modal.js"
            ), o = n(
              /*! ./pdf */
              "./src/js/pdf.js"
            ), u = n(
              /*! ./html */
              "./src/js/html.js"
            ), l = n(
              /*! ./raw-html */
              "./src/js/raw-html.js"
            ), c = n(
              /*! ./image */
              "./src/js/image.js"
            ), f = n(
              /*! ./json */
              "./src/js/json.js"
            );
            function d(p) {
              "@babel/helpers - typeof";
              return typeof Symbol == "function" && typeof Symbol.iterator == "symbol" ? d = function(x) {
                return typeof x;
              } : d = function(x) {
                return x && typeof Symbol == "function" && x.constructor === Symbol && x !== Symbol.prototype ? "symbol" : typeof x;
              }, d(p);
            }
            var v = ["pdf", "html", "image", "json", "raw-html"];
            r.default = {
              init: function() {
                var h = {
                  printable: null,
                  fallbackPrintable: null,
                  type: "pdf",
                  header: null,
                  headerStyle: "font-weight: 300;",
                  maxWidth: 800,
                  properties: null,
                  gridHeaderStyle: "font-weight: bold; padding: 5px; border: 1px solid #dddddd;",
                  gridStyle: "border: 1px solid lightgray; margin-bottom: -1px;",
                  showModal: !1,
                  onError: function(_) {
                    throw _;
                  },
                  onLoadingStart: null,
                  onLoadingEnd: null,
                  onPrintDialogClose: function() {
                  },
                  onIncompatibleBrowser: function() {
                  },
                  modalMessage: "Retrieving Document...",
                  frameId: "printJS",
                  printableElement: null,
                  documentTitle: "Document",
                  targetStyle: ["clear", "display", "width", "min-width", "height", "min-height", "max-height"],
                  targetStyles: ["border", "box", "break", "text-decoration"],
                  ignoreElements: [],
                  repeatTableHeader: !0,
                  css: null,
                  style: null,
                  scanStyles: !0,
                  base64: !1,
                  // Deprecated
                  onPdfOpen: null,
                  font: "TimesNewRoman",
                  font_size: "12pt",
                  honorMarginPadding: !0,
                  honorColor: !1,
                  imageStyle: "max-width: 100%;"
                }, x = arguments[0];
                if (x === void 0)
                  throw new Error("printJS expects at least 1 attribute.");
                switch (d(x)) {
                  case "string":
                    h.printable = encodeURI(x), h.fallbackPrintable = h.printable, h.type = arguments[1] || h.type;
                    break;
                  case "object":
                    h.printable = x.printable, h.fallbackPrintable = typeof x.fallbackPrintable < "u" ? x.fallbackPrintable : h.printable, h.fallbackPrintable = h.base64 ? "data:application/pdf;base64,".concat(h.fallbackPrintable) : h.fallbackPrintable;
                    for (var y in h)
                      y === "printable" || y === "fallbackPrintable" || (h[y] = typeof x[y] < "u" ? x[y] : h[y]);
                    break;
                  default:
                    throw new Error('Unexpected argument type! Expected "string" or "object", got ' + d(x));
                }
                if (!h.printable) throw new Error("Missing printable information.");
                if (!h.type || typeof h.type != "string" || v.indexOf(h.type.toLowerCase()) === -1)
                  throw new Error("Invalid print type. Available types are: pdf, html, image and json.");
                h.showModal && s.default.show(h), h.onLoadingStart && h.onLoadingStart();
                var g = document.getElementById(h.frameId);
                g && g.parentNode.removeChild(g);
                var m = document.createElement("iframe");
                switch (i.default.isFirefox() ? m.setAttribute("style", "width: 1px; height: 100px; position: fixed; left: 0; top: 0; opacity: 0; border-width: 0; margin: 0; padding: 0") : m.setAttribute("style", "visibility: hidden; height: 0; width: 0; position: absolute; border: 0"), m.setAttribute("id", h.frameId), h.type !== "pdf" && (m.srcdoc = "<html><head><title>" + h.documentTitle + "</title>", h.css && (Array.isArray(h.css) || (h.css = [h.css]), h.css.forEach(function(S) {
                  m.srcdoc += '<link rel="stylesheet" href="' + S + '">';
                })), m.srcdoc += "</head><body></body></html>"), h.type) {
                  case "pdf":
                    if (i.default.isIE())
                      try {
                        console.info("Print.js doesn't support PDF printing in Internet Explorer.");
                        var C = window.open(h.fallbackPrintable, "_blank");
                        C.focus(), h.onIncompatibleBrowser();
                      } catch (S) {
                        h.onError(S);
                      } finally {
                        h.showModal && s.default.close(), h.onLoadingEnd && h.onLoadingEnd();
                      }
                    else
                      o.default.print(h, m);
                    break;
                  case "image":
                    c.default.print(h, m);
                    break;
                  case "html":
                    u.default.print(h, m);
                    break;
                  case "raw-html":
                    l.default.print(h, m);
                    break;
                  case "json":
                    f.default.print(h, m);
                    break;
                }
              }
            };
          }
        ),
        /***/
        "./src/js/json.js": (
          /*!************************!*\
            !*** ./src/js/json.js ***!
            \************************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r);
            var i = n(
              /*! ./functions */
              "./src/js/functions.js"
            ), s = n(
              /*! ./print */
              "./src/js/print.js"
            );
            function o(l) {
              "@babel/helpers - typeof";
              return typeof Symbol == "function" && typeof Symbol.iterator == "symbol" ? o = function(f) {
                return typeof f;
              } : o = function(f) {
                return f && typeof Symbol == "function" && f.constructor === Symbol && f !== Symbol.prototype ? "symbol" : typeof f;
              }, o(l);
            }
            r.default = {
              print: function(c, f) {
                if (o(c.printable) !== "object")
                  throw new Error("Invalid javascript data object (JSON).");
                if (typeof c.repeatTableHeader != "boolean")
                  throw new Error("Invalid value for repeatTableHeader attribute (JSON).");
                if (!c.properties || !Array.isArray(c.properties))
                  throw new Error("Invalid properties array for your JSON data.");
                c.properties = c.properties.map(function(d) {
                  return {
                    field: o(d) === "object" ? d.field : d,
                    displayName: o(d) === "object" ? d.displayName : d,
                    columnSize: o(d) === "object" && d.columnSize ? d.columnSize + ";" : 100 / c.properties.length + "%;"
                  };
                }), c.printableElement = document.createElement("div"), c.header && Object(i.addHeader)(c.printableElement, c), c.printableElement.innerHTML += u(c), s.default.send(c, f);
              }
            };
            function u(l) {
              var c = l.printable, f = l.properties, d = '<table style="border-collapse: collapse; width: 100%;">';
              l.repeatTableHeader && (d += "<thead>"), d += "<tr>";
              for (var v = 0; v < f.length; v++)
                d += '<th style="width:' + f[v].columnSize + ";" + l.gridHeaderStyle + '">' + Object(i.capitalizePrint)(f[v].displayName) + "</th>";
              d += "</tr>", l.repeatTableHeader && (d += "</thead>"), d += "<tbody>";
              for (var p = 0; p < c.length; p++) {
                d += "<tr>";
                for (var h = 0; h < f.length; h++) {
                  var x = c[p], y = f[h].field.split(".");
                  if (y.length > 1)
                    for (var g = 0; g < y.length; g++)
                      x = x[y[g]];
                  else
                    x = x[f[h].field];
                  d += '<td style="width:' + f[h].columnSize + l.gridStyle + '">' + x + "</td>";
                }
                d += "</tr>";
              }
              return d += "</tbody></table>", d;
            }
          }
        ),
        /***/
        "./src/js/modal.js": (
          /*!*************************!*\
            !*** ./src/js/modal.js ***!
            \*************************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r);
            var i = {
              show: function(o) {
                var u = "font-family:sans-serif; display:table; text-align:center; font-weight:300; font-size:30px; left:0; top:0;position:fixed; z-index: 9990;color: #0460B5; width: 100%; height: 100%; background-color:rgba(255,255,255,.9);transition: opacity .3s ease;", l = document.createElement("div");
                l.setAttribute("style", u), l.setAttribute("id", "printJS-Modal");
                var c = document.createElement("div");
                c.setAttribute("style", "display:table-cell; vertical-align:middle; padding-bottom:100px;");
                var f = document.createElement("div");
                f.setAttribute("class", "printClose"), f.setAttribute("id", "printClose"), c.appendChild(f);
                var d = document.createElement("span");
                d.setAttribute("class", "printSpinner"), c.appendChild(d);
                var v = document.createTextNode(o.modalMessage);
                c.appendChild(v), l.appendChild(c), document.getElementsByTagName("body")[0].appendChild(l), document.getElementById("printClose").addEventListener("click", function() {
                  i.close();
                });
              },
              close: function() {
                var o = document.getElementById("printJS-Modal");
                o && o.parentNode.removeChild(o);
              }
            };
            r.default = i;
          }
        ),
        /***/
        "./src/js/pdf.js": (
          /*!***********************!*\
            !*** ./src/js/pdf.js ***!
            \***********************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r);
            var i = n(
              /*! ./print */
              "./src/js/print.js"
            ), s = n(
              /*! ./functions */
              "./src/js/functions.js"
            );
            r.default = {
              print: function(l, c) {
                if (l.base64) {
                  var f = Uint8Array.from(atob(l.printable), function(v) {
                    return v.charCodeAt(0);
                  });
                  o(l, c, f);
                  return;
                }
                l.printable = /^(blob|http|\/\/)/i.test(l.printable) ? l.printable : window.location.origin + (l.printable.charAt(0) !== "/" ? "/" + l.printable : l.printable);
                var d = new window.XMLHttpRequest();
                d.responseType = "arraybuffer", d.addEventListener("error", function() {
                  Object(s.cleanUp)(l), l.onError(d.statusText, d);
                }), d.addEventListener("load", function() {
                  if ([200, 201].indexOf(d.status) === -1) {
                    Object(s.cleanUp)(l), l.onError(d.statusText, d);
                    return;
                  }
                  o(l, c, d.response);
                }), d.open("GET", l.printable, !0), d.send();
              }
            };
            function o(u, l, c) {
              var f = new window.Blob([c], {
                type: "application/pdf"
              });
              f = window.URL.createObjectURL(f), l.setAttribute("src", f), i.default.send(u, l);
            }
          }
        ),
        /***/
        "./src/js/print.js": (
          /*!*************************!*\
            !*** ./src/js/print.js ***!
            \*************************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r);
            var i = n(
              /*! ./browser */
              "./src/js/browser.js"
            ), s = n(
              /*! ./functions */
              "./src/js/functions.js"
            ), o = {
              send: function(d, v) {
                document.getElementsByTagName("body")[0].appendChild(v);
                var p = document.getElementById(d.frameId);
                p.onload = function() {
                  if (d.type === "pdf") {
                    i.default.isFirefox() ? setTimeout(function() {
                      return u(p, d);
                    }, 1e3) : u(p, d);
                    return;
                  }
                  var h = p.contentWindow || p.contentDocument;
                  if (h.document && (h = h.document), h.body.appendChild(d.printableElement), d.type !== "pdf" && d.style) {
                    var x = document.createElement("style");
                    x.innerHTML = d.style, h.head.appendChild(x);
                  }
                  var y = h.getElementsByTagName("img");
                  y.length > 0 ? l(Array.from(y)).then(function() {
                    return u(p, d);
                  }) : u(p, d);
                };
              }
            };
            function u(f, d) {
              try {
                if (f.focus(), i.default.isEdge() || i.default.isIE())
                  try {
                    f.contentWindow.document.execCommand("print", !1, null);
                  } catch {
                    f.contentWindow.print();
                  }
                else
                  f.contentWindow.print();
              } catch (v) {
                d.onError(v);
              } finally {
                i.default.isFirefox() && (f.style.visibility = "hidden", f.style.left = "-1px"), Object(s.cleanUp)(d);
              }
            }
            function l(f) {
              var d = f.map(function(v) {
                if (v.src && v.src !== window.location.href)
                  return c(v);
              });
              return Promise.all(d);
            }
            function c(f) {
              return new Promise(function(d) {
                var v = function p() {
                  !f || typeof f.naturalWidth > "u" || f.naturalWidth === 0 || !f.complete ? setTimeout(p, 500) : d();
                };
                v();
              });
            }
            r.default = o;
          }
        ),
        /***/
        "./src/js/raw-html.js": (
          /*!****************************!*\
            !*** ./src/js/raw-html.js ***!
            \****************************/
          /*! exports provided: default */
          /***/
          function(a, r, n) {
            n.r(r);
            var i = n(
              /*! ./print */
              "./src/js/print.js"
            );
            r.default = {
              print: function(o, u) {
                o.printableElement = document.createElement("div"), o.printableElement.setAttribute("style", "width:100%"), o.printableElement.innerHTML = o.printable, i.default.send(o, u);
              }
            };
          }
        ),
        /***/
        "./src/sass/index.scss": (
          /*!*****************************!*\
            !*** ./src/sass/index.scss ***!
            \*****************************/
          /*! no static exports found */
          /***/
          function(a, r, n) {
          }
        ),
        /***/
        0: (
          /*!****************************!*\
            !*** multi ./src/index.js ***!
            \****************************/
          /*! no static exports found */
          /***/
          function(a, r, n) {
            a.exports = n(
              /*! ./src/index.js */
              "./src/index.js"
            );
          }
        )
        /******/
      }).default
    );
  });
})(kg);
var Xl = { exports: {} };
/*!
 * js-table2excel v1.1.2
 * Released under the MIT License.
 * repository: https://github.com/hxj9102/table2excel.git
 */
(function(e, t) {
  (function(a, r) {
    e.exports = r();
  })(we, function() {
    var a = function(i, s) {
      var o;
      ((o = window.navigator.userAgent).indexOf("MSIE") >= 0 ? "ie" : o.indexOf("Firefox") >= 0 ? "Firefox" : o.indexOf("Chrome") >= 0 ? "Chrome" : o.indexOf("Opera") >= 0 ? "Opera" : o.indexOf("Safari") >= 0 ? "Safari" : void 0) == "ie" ? r(i) : n(i, s);
    }, r = function(i, s) {
      var o = i, u = new ActiveXObject("Excel.Application"), l = u.Workbooks.Add(), c = l.Worksheets(1), f = document.body.createTextRange();
      f.moveToElementText(o), f.select, f.execCommand("Copy"), c.Paste(), u.Visible = !0;
      try {
        u.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
      } catch (d) {
        print("Nested catch caught " + d);
      } finally {
        l.SaveAs(fname), l.Close(savechanges = !1), u.Quit(), u = null, window.setInterval("Cleanup();", 1), window.setInterval("Cleanup();", 1);
      }
    }, n = function(i, s) {
      var o, u = "data:application/vnd.ms-excel;base64," + function(f) {
        return window.btoa(unescape(encodeURIComponent(f)));
      }((o = { worksheet: s, table: i }, '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><meta charset="UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'.replace(/{(\w+)}/g, function(f, d) {
        return o[d];
      })));
      if (navigator.userAgent.indexOf("Firefox") > -1) window.location.href = u;
      else {
        var l, c = document.createElement("a");
        c.href = u, c.download = s || "", window.MouseEvent ? l = new MouseEvent("click") : (l = document.createEvent("MouseEvents")).initMouseEvent("click", !0, !1, window, 0, 0, 0, 0, 0, !1, !1, !1, !1, 0, null), c.dispatchEvent(l);
      }
    };
    return function() {
      for (var i = { image: function(y, g) {
        return g = Object.assign({ width: 40, height: 60 }, g), '<td style="width: '.concat(g.width, "px; height: ").concat(g.height, 'px; text-align: center; vertical-align: middle"><img src="').concat(y, '" width=').concat(0.99 * g.width, " height=").concat(0.99 * g.height, " /></td>");
      }, text: function(y) {
        return '<td style="text-align: center">'.concat(y, "</td>");
      } }, s = arguments.length, o = new Array(s), u = 0; u < s; u++) o[u] = arguments[u];
      var l = function(y) {
        return y.length === 1 ? y[0] : { column: y[0] || [], data: y[1] || [], excelName: y[2] || "", captionName: y[3] };
      }(o), c = l.column, f = l.data, d = l.excelName, v = l.captionName, p = v ? '<caption style="font-weight:bold">'.concat(v, "</caption>") : "", h = c.reduce(function(y, g) {
        return y += "<th>".concat(g.title, "</th>");
      }, "");
      h = "<thead><tr>".concat(h, "</tr></thead>");
      var x = f.reduce(function(y, g) {
        var m = c.reduce(function(C, S) {
          var _ = {};
          return S.type === "image" && (g.size ? (_.width = g.size[0], _.height = g.size[1]) : (S.width && (_.width = S.width), S.height && (_.height = S.height))), C += i[S.type || "text"](g[S.key], _);
        }, "");
        return y += "<tr>".concat(m, "</tr>");
      }, "");
      x = "<tbody>".concat(x, "</tbody>"), a(p + h + x, d);
    };
  });
})(Xl);
var Fg = Xl.exports;
const Lg = /* @__PURE__ */ el(Fg), Pg = { class: "table-container" }, Ug = { key: 1 }, Og = { class: "table-footer mt15" }, Vg = { class: "table-footer-tool" }, Mg = { class: "tool-box" }, Kg = ["data-key"], Hg = /* @__PURE__ */ Ue({
  __name: "table",
  props: {
    // 列表内容
    data: {
      type: Array,
      default: () => []
    },
    // 表头内容
    header: {
      type: Array,
      default: () => []
    },
    search: {
      type: Array,
      default: () => []
    },
    // 配置项
    config: {
      type: Object,
      default: () => {
      }
    },
    // 打印标题
    printName: {
      type: String,
      default: () => ""
    },
    tableButtons: {
      type: Array,
      default: () => []
    },
    param: {
      type: Object,
      default: () => {
      }
    }
  },
  emits: [
    "delRow",
    "rowDblclick",
    "rowClick",
    "pageChange",
    "sortHeader",
    "editRow",
    "openDetail"
  ],
  setup(e, { expose: t, emit: a }) {
    const r = e, n = a, i = se(), s = Ql(), { themeConfig: o } = ua(s), u = or({
      loading: !1,
      page: {
        current: 1,
        size: 50
      },
      total: 0,
      tableData: [],
      selectlist: [],
      checkListAll: !0,
      checkListIndeterminate: !1
    }), l = () => {
      if (u.tableData = [], r.data && r.data.length > 0) {
        u.tableData = r.data;
        return;
      }
    }, c = async (U) => {
      if (typeof r.config.listApi != "function")
        return;
      u.loading = !0;
      const M = {
        ...r.param,
        //默认参数
        ...U,
        //搜索参数
        ...u.page
        //页码
      };
      try {
        const { data: ee, page: q } = await r.config.listApi(M, u.page);
        u.tableData = ee, u.total = q.total;
      } finally {
        u.loading = !1;
      }
    }, f = He(() => !!r.config.isBorder), d = He(() => r.config), v = He(() => r.header.filter((U) => U.isCheck)), p = (U) => {
      U ? r.header.forEach((M) => M.isCheck = !0) : r.header.forEach((M) => M.isCheck = !1), u.checkListIndeterminate = !1;
    }, h = () => {
      const U = r.header.filter((M) => M.isCheck).length;
      u.checkListAll = U === r.header.length, u.checkListIndeterminate = U > 0 && U < r.header.length;
    }, x = (U) => {
      u.selectlist = U;
    }, y = (U) => (u.page.current - 1) * u.page.size + U + 1, g = (U) => {
      Ri.confirm("此操作将永久删除该条数据，是否继续?", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        r.config.delApi ? r.config.delApi(U.id).then((M) => {
          M.code == 0 ? (ze.success("删除成功"), c()) : ze.warning(M.message);
        }) : n("delRow", U);
      }).catch(() => {
      });
    }, m = (U, M) => {
      M.value === "edit" ? n("editRow", U) : M.value === "detail" ? n("openDetail", U) : M.value === "delete" && g(U);
    }, C = (U) => {
      n("rowClick", U);
    }, S = (U) => {
      n("rowDblclick", U);
    }, _ = (U) => {
      u.page.size = U, c();
    }, T = (U) => {
      u.page.current = U, c();
    }, D = () => {
      u.page.current = 1, u.page.size = 50, n("pageChange", u.page);
    }, P = () => {
      if (u.selectlist.length <= 0)
        return ze.warning("请先选择要导出的数据");
      Lg(
        r.header,
        u.selectlist,
        `${o.value.globalTitle} ${(/* @__PURE__ */ new Date()).toLocaleString()}`
      );
    }, I = (U) => {
      if (U) {
        var M = U.split(".").pop().toLowerCase();
        return M === "jpg" || M === "jpeg" || M === "png" || M === "gif" || M === "bmp";
      }
    }, B = (U) => "http://" + window.location.host + "/files/" + U, R = () => {
      c();
    }, F = () => {
      Lr(() => {
        const U = Do.create(i.value, {
          handle: ".handle",
          dataIdAttr: "data-key",
          animation: 150,
          onEnd: () => {
            const M = [];
            U.toArray().forEach((ee) => {
              r.header.forEach((q) => {
                q.key === ee && M.push({ ...q });
              });
            }), n("sortHeader", M);
          }
        });
      });
    };
    return yt(() => {
      c();
    }), t({
      pageReset: D,
      getTableData: c,
      setData: l
    }), (U, M) => {
      const ee = Q("el-table-column"), q = Q("el-image"), fe = Q("el-tag"), Ee = Q("dict-date"), xe = Q("dict-name"), O = Q("dict-enum"), H = Q("dict-user-name"), $ = Q("dict-version"), K = Q("dict-area"), be = Q("dict-tenant"), pe = Q("el-button"), Ne = Q("el-empty"), le = Q("el-table"), X = Q("el-tooltip"), te = Q("el-checkbox"), ce = Q("el-scrollbar"), ge = Q("el-popover"), de = Q("el-pagination"), Te = Ic("loading");
      return z(), he("div", Pg, [
        aa((z(), ve(le, nr({
          data: u.tableData,
          border: f.value
        }, U.$attrs, {
          "row-key": "id",
          stripe: "",
          style: { width: "100%" },
          "highlight-current-row": "",
          "element-loading-text": "拼命加载中,请稍后···",
          onRowClick: C,
          onRowDblclick: S,
          onSelectionChange: x
        }), {
          empty: re(() => [
            Z(Ne, { description: "暂无数据" })
          ]),
          default: re(() => [
            e.config.isSelection ? (z(), ve(ee, {
              key: 0,
              type: "selection",
              align: "center",
              "reserve-selection": !0,
              width: "45"
            })) : qe("", !0),
            e.config.isSerialNo ? (z(), ve(ee, {
              key: 1,
              type: "index",
              index: y,
              align: "center",
              label: "序号",
              width: "70"
            })) : qe("", !0),
            (z(!0), he(it, null, St(v.value, (W, Xe) => (z(), ve(ee, {
              align: W.align || "center",
              key: Xe,
              "show-overflow-tooltip": "",
              prop: W.key,
              width: W.colWidth,
              label: W.title,
              sortable: W.sortable
            }, {
              default: re((De) => [
                W.type === "image" ? (z(), ve(q, {
                  key: 0,
                  style: mn({ width: `${W.width}px`, height: `${W.height}px` }),
                  src: De.row[W.key],
                  "zoom-rate": 1.2,
                  "preview-src-list": [De.row[W.key]],
                  "preview-teleported": "",
                  fit: "cover"
                }, null, 8, ["style", "src", "preview-src-list"])) : W.type === "boolean" ? (z(), ve(fe, {
                  key: 1,
                  class: "ml-2",
                  type: De.row[W.key] ? "success" : "danger",
                  size: ye(o).globalComponentSize
                }, {
                  default: re(() => [
                    rt(We(De.row[W.key] ? "是" : "否"), 1)
                  ]),
                  _: 2
                }, 1032, ["type", "size"])) : W.type === "year" ? (z(), ve(Ee, {
                  key: 2,
                  date: De.row[W.key],
                  format: "YYYY"
                }, null, 8, ["date"])) : W.type === "month" ? (z(), ve(Ee, {
                  key: 3,
                  date: De.row[W.key],
                  format: "YYYY-MM"
                }, null, 8, ["date"])) : W.type === "date" ? (z(), ve(Ee, {
                  key: 4,
                  date: De.row[W.key],
                  format: "YYYY-MM-DD"
                }, null, 8, ["date"])) : W.type === "datetime" ? (z(), ve(Ee, {
                  key: 5,
                  date: De.row[W.key],
                  format: "YYYY-MM-DD HH:mm:ss"
                }, null, 8, ["date"])) : W.type === "dict" ? (z(), ve(xe, {
                  key: 6,
                  dictType: De.row[W.dictType],
                  dictValue: De.row[W.key]
                }, null, 8, ["dictType", "dictValue"])) : W.type === "enum" && W.options ? (z(), ve(O, {
                  key: 7,
                  options: W.options,
                  value: De.row[W.key]
                }, null, 8, ["options", "value"])) : W.type === "user" ? (z(), ve(H, {
                  key: 8,
                  userCode: De.row[W.key]
                }, null, 8, ["userCode"])) : W.type === "version" ? (z(), ve($, {
                  key: 9,
                  versionId: De.row[W.key]
                }, null, 8, ["versionId"])) : W.type === "area" ? (z(), ve(K, {
                  key: 10,
                  value: De.row[W.key]
                }, null, 8, ["value"])) : W.type === "fileSize" ? (z(), he(it, { key: 11 }, [
                  rt(We(De.row[W.key] && De.row[W.key] <= 99999 ? (De.row[W.key] / 1024).toFixed(2) + "KB" : De.row[W.key] && De.row[W.key] > 99999 ? (De.row[W.key] / 1024 / 1024).toFixed(2) + "MB" : 0), 1)
                ], 64)) : W.type === "file" ? (z(), he(it, { key: 12 }, [
                  I(De.row[W.key]) ? (z(), ve(q, {
                    key: 0,
                    src: B(De.row[W.key]),
                    "zoom-rate": 1.2,
                    "max-scale": 7,
                    "min-scale": 0.2,
                    "preview-src-list": [B(De.row[W.key])],
                    "initial-index": 4,
                    fit: "cover"
                  }, null, 8, ["src", "preview-src-list"])) : (z(), he("span", Ug, We(De.row[W.key]), 1))
                ], 64)) : W.type === "tenant" ? (z(), ve(be, {
                  key: 13,
                  tenantId: De.row[W.key]
                }, null, 8, ["tenantId"])) : (z(), he(it, { key: 14 }, [
                  rt(We(De.row[W.key]), 1)
                ], 64))
              ]),
              _: 2
            }, 1032, ["align", "prop", "width", "label", "sortable"]))), 128)),
            e.config.isOperate || e.tableButtons.length > 0 ? (z(), ve(ee, {
              key: 2,
              label: "操作",
              align: "center",
              "min-width": "150",
              fixed: "right"
            }, {
              default: re((W) => [
                (z(!0), he(it, null, St(e.tableButtons, (Xe) => (z(), ve(pe, {
                  text: "",
                  type: Xe.type,
                  key: Xe.value,
                  onClick: (De) => m(W.row, Xe),
                  size: ye(o).globalComponentSize
                }, {
                  default: re(() => [
                    rt(We(Xe.label), 1)
                  ]),
                  _: 2
                }, 1032, ["type", "onClick", "size"]))), 128))
              ]),
              _: 1
            })) : qe("", !0),
            Rt(U.$slots, "operate", {}, void 0, !0)
          ]),
          _: 3
        }, 16, ["data", "border"])), [
          [Te, u.loading]
        ]),
        $e("div", Og, [
          $e("div", Vg, [
            Z(pe, {
              icon: ye(Hc),
              size: ye(o).globalComponentSize,
              circle: "",
              onClick: P
            }, null, 8, ["icon", "size"]),
            Z(pe, {
              icon: ye(Cn),
              size: ye(o).globalComponentSize,
              circle: "",
              onClick: R
            }, null, 8, ["icon", "size"]),
            Z(ge, {
              placement: "top-end",
              trigger: "click",
              transition: "el-zoom-in-top",
              "popper-class": "table-tool-popper",
              width: 300,
              persistent: !1,
              onShow: F
            }, {
              reference: re(() => [
                Z(pe, {
                  icon: ye(zc),
                  size: ye(o).globalComponentSize,
                  circle: ""
                }, null, 8, ["icon", "size"])
              ]),
              default: re(() => [
                $e("div", Mg, [
                  Z(X, {
                    content: "拖动进行排序",
                    placement: "top-start"
                  }),
                  Z(te, {
                    modelValue: u.checkListAll,
                    "onUpdate:modelValue": M[0] || (M[0] = (W) => u.checkListAll = W),
                    indeterminate: u.checkListIndeterminate,
                    class: "ml10 mr1",
                    label: "列显示",
                    onChange: p
                  }, null, 8, ["modelValue", "indeterminate"]),
                  Z(te, {
                    modelValue: d.value.isSerialNo,
                    "onUpdate:modelValue": M[1] || (M[1] = (W) => d.value.isSerialNo = W),
                    class: "ml12 mr1",
                    label: "序号"
                  }, null, 8, ["modelValue"]),
                  Z(te, {
                    modelValue: d.value.isSelection,
                    "onUpdate:modelValue": M[2] || (M[2] = (W) => d.value.isSelection = W),
                    class: "ml12 mr1",
                    label: "多选"
                  }, null, 8, ["modelValue"])
                ]),
                Z(ce, null, {
                  default: re(() => [
                    $e("div", {
                      ref_key: "toolSetRef",
                      ref: i,
                      class: "tool-sortable"
                    }, [
                      (z(!0), he(it, null, St(e.header, (W) => (z(), he("div", {
                        class: "tool-sortable-item",
                        key: W.key,
                        "data-key": W.key
                      }, [
                        Z(te, {
                          modelValue: W.isCheck,
                          "onUpdate:modelValue": (Xe) => W.isCheck = Xe,
                          class: "ml12 mr8",
                          label: W.title,
                          onChange: h
                        }, null, 8, ["modelValue", "onUpdate:modelValue", "label"])
                      ], 8, Kg))), 128))
                    ], 512)
                  ]),
                  _: 1
                })
              ]),
              _: 1
            })
          ]),
          Z(de, {
            "current-page": u.page.current,
            "onUpdate:currentPage": M[3] || (M[3] = (W) => u.page.current = W),
            "page-size": u.page.size,
            "onUpdate:pageSize": M[4] || (M[4] = (W) => u.page.size = W),
            "pager-count": 5,
            "page-sizes": [5, 10, 20, 50, 100, 500],
            total: u.total,
            layout: "total, sizes, prev, pager, next, jumper",
            background: "",
            small: ye(o).globalComponentSize == "small",
            onSizeChange: _,
            onCurrentChange: T
          }, null, 8, ["current-page", "page-size", "total", "small"])
        ])
      ]);
    };
  }
}), zg = /* @__PURE__ */ Or(Hg, [["__scopeId", "data-v-9b44d2b7"]]), $g = /* @__PURE__ */ Ue({
  __name: "index",
  props: {
    src: { default: "" }
  },
  setup(e) {
    const t = e, a = He(() => /\.(png|jpg|jpeg|PNG|JPG|JPEG)$/i.test(t.src) ? "el-image" : /\.(mov|mp4|avi)$/i.test(t.src) ? "video" : /\.pdf$/i.test(t.src) ? "iframe" : /\.docx$/i.test(t.src) ? qc : /\.xlsx$/i.test(t.src) ? jc : "div");
    return (r, n) => (z(), ve(Na(a.value), {
      src: "/minio" + r.src,
      style: mn({
        width: a.value == "el-image" ? "auto" : "100vw",
        height: a.value == "el-image" ? "auto" : "100vh"
      }),
      controls: "",
      "zoom-rate": 1.2,
      "max-scale": 7,
      "min-scale": 0.2,
      "preview-src-list": ["minio/" + r.src],
      "initial-index": 4,
      fit: "cover"
    }, {
      default: re(() => [
        rt(" 暂不支持预览该文件类型，请下载后查看。 ")
      ]),
      _: 1
    }, 8, ["src", "style", "preview-src-list"]));
  }
}), by = [
  { label: "待提交", value: "draft", tagType: "info" },
  { label: "审批中", value: "running", tagType: "primary" },
  { label: "已审批", value: "completed", tagType: "success" },
  { label: "已终止", value: "terminated", tagType: "danger" },
  { label: "已取消", value: "canceled", tagType: "warning" }
], Sy = [
  { label: "是", value: !0, tagType: "success" },
  { label: "否", value: !1, tagType: "danger" }
], Ay = [
  { label: "商业版", value: "SALE", tagType: "warning" },
  { label: "试用版", value: "TRIAL", tagType: "info" },
  { label: "自定义", value: "CUSTOM", tagType: "primary" }
], _y = [
  { label: "系统", value: "SYSTEM", tagType: "primary" },
  { label: "普通", value: "NORMAL", tagType: "info" }
], By = [
  { label: "系统", value: "SYSTEM", tagType: "primary" },
  { label: "业务", value: "BUSINESS", tagType: "info" }
], Ty = [
  { label: "仅本人数据权限", value: "USER", tagType: "primary" },
  { label: "本部门数据权限", value: "DEPT", tagType: "warning" },
  { label: "全部数据权限", value: "ALL", tagType: "success" },
  { label: "自定义数据权限", value: "CUSTOM", tagType: "info" }
], Iy = [
  { label: "仅本人相关", value: "USER", tagType: "primary" },
  { label: "全部", value: "ALL", tagType: "success" }
], wy = [
  { label: "系统", value: "SYSTEM", tagType: "primary" },
  { label: "菜单", value: "MENU", tagType: "success" },
  { label: "操作", value: "OPERATION", tagType: "warning" },
  { label: "其他", value: "OTHER", tagType: "info" }
];
var qg = /* @__PURE__ */ ((e) => (e.ASC = "正序", e.DESC = "倒序", e))(qg || {}), jg = /* @__PURE__ */ ((e) => (e.draft = "待提交", e.running = "审批中", e.completed = "已审批", e.terminated = "已终止", e.canceled = "已取消", e))(jg || {});
const Gg = () => new Promise((e, t) => {
  Lr(() => {
    const a = document.styleSheets;
    let r = [], n = [];
    for (let i = 0; i < a.length; i++)
      a[i].href && a[i].href.indexOf("at.alicdn.com") > -1 && r.push(a[i]);
    for (let i = 0; i < r.length; i++)
      for (let s = 0; s < r[i].cssRules.length; s++)
        r[i].cssRules[s].selectorText && r[i].cssRules[s].selectorText.indexOf(".icon-") > -1 && n.push(
          `${r[i].cssRules[s].selectorText.substring(1, r[i].cssRules[s].selectorText.length).replace(/\:\:before/gi, "")}`
        );
    n.length > 0 ? e(n) : t("未获取到值，请刷新重试");
  });
}), Wg = () => new Promise((e, t) => {
  Lr(() => {
    const a = Rc, r = [];
    for (const n in a)
      r.push(`ele-${a[n].name}`);
    r.length > 0 ? e(r) : t("未获取到值，请刷新重试");
  });
}), Yg = () => new Promise((e, t) => {
  Lr(() => {
    const a = document.styleSheets;
    let r = [], n = [];
    for (let i = 0; i < a.length; i++)
      a[i].href && a[i].href.indexOf("netdna.bootstrapcdn.com") > -1 && r.push(a[i]);
    for (let i = 0; i < r.length; i++)
      for (let s = 0; s < r[i].cssRules.length; s++)
        r[i].cssRules[s].selectorText && r[i].cssRules[s].selectorText.indexOf(".fa-") === 0 && r[i].cssRules[s].selectorText.indexOf(",") === -1 && /::before/.test(r[i].cssRules[s].selectorText) && n.push(
          `${r[i].cssRules[s].selectorText.substring(1, r[i].cssRules[s].selectorText.length).replace(/\:\:before/gi, "")}`
        );
    n.length > 0 ? e(n.reverse()) : t("未获取到值，请刷新重试");
  });
}), Dy = {
  // iconfont
  ali: () => Gg(),
  // element plus
  ele: () => Wg(),
  // fontawesome
  awe: () => Yg()
}, Ny = Gc(), bo = [
  "//at.alicdn.com/t/c/font_2298093_rnp72ifj3ba.css"
  // '//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css',
], So = [];
function Qg() {
  if (bo.length <= 0) return !1;
  bo.map((e) => {
    let t = document.createElement("link");
    t.rel = "stylesheet", t.href = e, t.crossOrigin = "anonymous", document.getElementsByTagName("head")[0].appendChild(t);
  });
}
function Xg() {
  if (So.length <= 0) return !1;
  So.map((e) => {
    let t = document.createElement("script");
    t.src = e, document.body.appendChild(t);
  });
}
const Ry = {
  // 设置css
  cssCdn: () => {
    Qg();
  },
  // 设置js
  jsCdn: () => {
    Xg();
  }
}, Ao = (e) => {
  const t = "1.23452384164.123412416";
  document.getElementById(t) !== null && document.body.removeChild(document.getElementById(t));
  const a = document.createElement("canvas");
  a.width = 200, a.height = 130;
  const r = a.getContext("2d");
  r.rotate(-20 * Math.PI / 180), r.font = "12px Vedana", r.fillStyle = "rgba(200, 200, 200, 0.30)", r.textBaseline = "middle", r.fillText(e, a.width / 10, a.height / 2);
  const n = document.createElement("div");
  return n.id = t, n.style.pointerEvents = "none", n.style.top = "0px", n.style.left = "0px", n.style.position = "fixed", n.style.zIndex = "10000000", n.style.width = `${document.documentElement.clientWidth}px`, n.style.height = `${document.documentElement.clientHeight}px`, n.style.background = `url(${a.toDataURL("image/png")}) left top repeat`, document.body.appendChild(n), t;
}, ky = {
  // 设置水印
  set: (e) => {
    let t = Ao(e);
    document.getElementById(t) === null && (t = Ao(e));
  },
  // 删除水印
  del: () => {
    let e = "1.23452384164.123412416";
    document.getElementById(e) !== null && document.body.removeChild(document.getElementById(e));
  }
};
function Fy(e, t) {
  const a = _o(e), r = _o(t);
  let n = 0;
  const i = a.length;
  for (let s in r)
    for (let o in a)
      r[s] === a[o] && n++;
  return n === i;
}
function Zg(e, t) {
  if (!e || !t) return !1;
  let a = Object.getOwnPropertyNames(e), r = Object.getOwnPropertyNames(t);
  if (a.length != r.length) return !1;
  for (let n = 0; n < a.length; n++) {
    let i = a[n], s = e[i], o = t[i];
    if (!t.hasOwnProperty(i)) return !1;
    if (s instanceof Object) {
      if (!Zg(s, o)) return !1;
    } else if (s !== o)
      return !1;
  }
  return !0;
}
function _o(e, t) {
  if (Object.keys(e).length)
    if (t) {
      const a = {};
      return e.reduce((r, n) => (a[n[t]] || (a[n[t]] = n[t] && r.push(n)), r), []);
    } else
      return [...new Set(e)];
  else
    return e;
}
const Ly = {
  // 创建 loading
  start: () => {
    const e = document.body, t = document.createElement("div");
    t.setAttribute("class", "loading-next");
    const a = `
			<div class="loading-next-box">
				<div class="loading-next-box-warp">
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
					<div class="loading-next-box-item"></div>
				</div>
			</div>
		`;
    t.innerHTML = a, e.insertBefore(t, e.childNodes[0]), window.nextLoading = !0;
  },
  // 移除 loading
  done: (e = 0) => {
    Lr(() => {
      setTimeout(() => {
        var a;
        window.nextLoading = !1;
        const t = document.querySelector(".loading-next");
        (a = t == null ? void 0 : t.parentNode) == null || a.removeChild(t);
      }, e);
    });
  }
};
function Qa() {
  return {
    hexToRgb: (n) => {
      let i = "";
      if (!/^\#?[0-9A-Fa-f]{6}$/.test(n))
        return ze.warning("输入错误的hex"), "";
      n = n.replace("#", ""), i = n.match(/../g);
      for (let o = 0; o < 3; o++) i[o] = parseInt(i[o], 16);
      return i;
    },
    rgbToHex: (n, i, s) => {
      let o = /^\d{1,3}$/;
      if (!o.test(n) || !o.test(i) || !o.test(s))
        return ze.warning("输入错误的rgb颜色值"), "";
      let u = [n.toString(16), i.toString(16), s.toString(16)];
      for (let l = 0; l < 3; l++) u[l].length == 1 && (u[l] = `0${u[l]}`);
      return `#${u.join("")}`;
    },
    getDarkColor: (n, i) => {
      if (!/^\#?[0-9A-Fa-f]{6}$/.test(n))
        return ze.warning("输入错误的hex颜色值"), "";
      let o = Qa().hexToRgb(n);
      for (let u = 0; u < 3; u++) o[u] = Math.floor(o[u] * (1 - i));
      return Qa().rgbToHex(o[0], o[1], o[2]);
    },
    getLightColor: (n, i) => {
      if (!/^\#?[0-9A-Fa-f]{6}$/.test(n))
        return ze.warning("输入错误的hex颜色值"), "";
      let o = Qa().hexToRgb(n);
      for (let u = 0; u < 3; u++) o[u] = Math.floor((255 - o[u]) * i + o[u]);
      return Qa().rgbToHex(o[0], o[1], o[2]);
    }
  };
}
function Py(e) {
  let t = e.replace(/(^\s*)|(\s*$)/g, "");
  return t = t.replace(/[^\d]/g, ""), t = t.replace(/^0/g, ""), t = t.replace(/^[1-9]\d\d{1,3}$/, "100"), t;
}
function Uy(e) {
  let t = Zl(e);
  return t = t.replace(/^[1-9]\d\d{1,3}$/, "100"), t = t.replace(/^100\.$/, "100"), t;
}
function Zl(e) {
  let t = e.replace(/(^\s*)|(\s*$)/g, "");
  return t = t.replace(/[^\d.]/g, ""), t = t.replace(/^0{2}$/g, "0"), t = t.replace(/^\./g, ""), t = t.replace(".", "$#$").replace(/\./g, "").replace("$#$", "."), t = t.replace(/^(\-)*(\d+)\.(\d\d).*$/, "$1$2.$3"), t;
}
function Oy(e) {
  let t = e.replace(/(^\s*)|(\s*$)/g, "");
  return t = t.replace(/[\.]*/g, ""), t = t.replace(/(^0[\d]*)$/g, "0"), t = t.replace(/^0\d$/g, "0"), t = t.replace(/[^\d]/g, ""), t;
}
function Vy(e) {
  let t = e.replace(/[\u4e00-\u9fa5\s]+/g, "");
  return t = t.replace(/(^\s*)|(\s*$)/g, ""), t;
}
function My(e) {
  let t = e.replace(/[a-zA-Z]+/g, "");
  return t = t.replace(/(^\s*)|(\s*$)/g, ""), t;
}
function Ky(e) {
  return e.replace(/(^\s*)|(\s*$)/g, "");
}
function Hy(e) {
  let t = Zl(e);
  return t = t.toString().split("."), t[0] = t[0].replace(/\B(?=(\d{3})+(?!\d))/g, ","), t = t.join("."), t;
}
function zy(e, t = "", a = "red") {
  return t.replace(new RegExp(e, "gi"), `<span style='color: ${a}'>${e}</span>`);
}
function $y(e, t = "仟佰拾亿仟佰拾万仟佰拾元角分", a = "") {
  e += "00";
  let r = e.indexOf(".");
  r >= 0 && (e = e.substring(0, r) + e.substr(r + 1, 2)), t = t.substr(t.length - e.length);
  for (let n = 0; n < e.length; n++)
    a += "零壹贰叁肆伍陆柒捌玖".substr(e.substr(n, 1), 1) + t.substr(n, 1);
  return a = a.replace(/零角零分$/, "整").replace(/零[仟佰拾]/g, "零").replace(/零{2,}/g, "零").replace(/零([亿|万])/g, "$1").replace(/零+元/, "元").replace(/亿零{0,3}万/, "亿").replace(/^元/, "零元"), a;
}
function qy(e) {
  return !!/^((12[0-9])|(13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0|1,5-9]))\d{8}$/.test(e);
}
function jy(e) {
  return !!/\d{3}-\d{8}|\d{4}-\d{7}/.test(e);
}
function Gy(e) {
  return !!/^[a-zA-Z][a-zA-Z0-9_]{4,15}$/.test(e);
}
function Wy(e) {
  return !!/^[a-zA-Z]\w{5,15}$/.test(e);
}
function Yy(e) {
  return !!/^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&\.*]+$)(?![a-zA-z\d]+$)(?![a-zA-z!@#$%^&\.*]+$)(?![\d!@#$%^&\.*]+$)[a-zA-Z\d!@#$%^&\.*]{6,16}$/.test(e);
}
function Qy(e) {
  let t = "";
  return /^(?:\d+|[a-zA-Z]+|[!@#$%^&\.*]+){6,16}$/.test(e) && (t = "弱"), /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&\.*]+$)[a-zA-Z\d!@#$%^&\.*]{6,16}$/.test(e) && (t = "中"), /^(?![a-zA-z]+$)(?!\d+$)(?![!@#$%^&\.*]+$)(?![a-zA-z\d]+$)(?![a-zA-z!@#$%^&\.*]+$)(?![\d!@#$%^&\.*]+$)[a-zA-Z\d!@#$%^&\.*]{6,16}$/.test(e) && (t = "强"), t;
}
function Xy(e) {
  return !!/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(
    e
  );
}
function Zy(e) {
  return !!/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
    e
  );
}
function Jy(e) {
  return !!/^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(e);
}
function em(e) {
  return !!/^[\u4e00-\u9fa5]{1,6}(·[\u4e00-\u9fa5]{1,6}){0,2}$/.test(e);
}
function tm(e) {
  return !!/^[1-9][0-9]{5}$/.test(e);
}
function rm(e) {
  return !!/^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})).?)(?::\d{2,5})?(?:[/?#]\S*)?$/i.test(
    e
  );
}
function am(e) {
  return !!/^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/.test(
    e
  );
}
const nm = {
  install(e, t = {}) {
    e.component("dict-date", Qc), e.component("dict-user-name", Lu), e.component("dict-org-name", Pu), e.component("dict-name", ku), e.component("dict-switch", Fu), e.component("dict-enum", Uu), e.component("dict-tenant", Ou), e.component("dict-menu", Vu), e.component("dict-version", Mu), e.component("dict-area", Ku), e.component("display-text", Hu), e.component("vz-user", qu), e.component("vz-user-org", Qu), e.component("vz-select", $u), e.component("vz-enum", Zu), e.component("vz-tenant", rf), e.component("vz-menu", sf), e.component("vz-org", of), e.component("vz-version", ff), e.component("vz-area", xf), e.component("vz-upload", yf), e.component("vz-role", Ef), e.component("vz-input-unit", Bf), e.component("vz-login", wg), e.component("vz-table", Op), e.component("vz-table-search", Rg), e.component("vz-table-body", zg), e.component("vz-office-preview", $g);
  }
};
export {
  ra as Local,
  Zt as MutationType,
  Ly as NextLoading,
  qg as OrderEnum,
  Ey as PiniaVuePlugin,
  jg as ProcessStatusEnum,
  Z0 as Session,
  hy as acceptHMRUpdate,
  ug as aesDecrypt,
  cg as aesEncrypt,
  zt as commonFunction,
  Cu as createPinia,
  Ty as dataAuthorityOptions,
  nm as default,
  ca as defineStore,
  By as dictTypeOptions,
  Ku as dict_area,
  Qc as dict_date,
  Uu as dict_enum,
  Vu as dict_menu,
  ku as dict_name,
  Pu as dict_org_name,
  Fu as dict_switch,
  Ou as dict_tenant,
  Lu as dict_user_name,
  Mu as dict_version,
  Hu as display_text,
  Iy as editAuthorityOptions,
  Ny as emitter,
  Sy as enabledOptions,
  dy as formatAxis,
  _a as formatDate,
  fy as formatPast,
  dg as generateAesKey,
  fg as generateIV,
  py as getActivePinia,
  Wc as getWeek,
  Dy as initIconfont,
  Zg as isObjectValueEqual,
  Fy as judementSameArr,
  Di as keyManager,
  my as mapActions,
  yy as mapGetters,
  Au as mapState,
  gy as mapStores,
  Cy as mapWritableState,
  wy as menuTypeOptions,
  Nu as piniaPluginPersistedstate,
  Kt as platformStore,
  by as processStatusOptions,
  pf as regions,
  _o as removeDuplicate,
  Se as request,
  lg as rsaEncryptToBase64,
  na as setActivePinia,
  Ry as setIntroduction,
  xy as setMapStoreSuffix,
  vy as skipHydrate,
  ua as storeToRefs,
  _y as tenantTypeOptions,
  xt as useCacheInfo,
  Qa as useChangeColor,
  bg as useCommonUserStore,
  Sg as useKeepAliveStore,
  Oy as verifiyNumberInteger,
  Gy as verifyAccount,
  Ky as verifyAndSpace,
  am as verifyCarNum,
  Vy as verifyCnAndSpace,
  Zy as verifyEmail,
  My as verifyEnAndSpace,
  em as verifyFullName,
  Xy as verifyIPAddress,
  Jy as verifyIdCard,
  $y as verifyNumberCnUppercase,
  Hy as verifyNumberComma,
  Zl as verifyNumberIntegerAndFloat,
  Py as verifyNumberPercentage,
  Uy as verifyNumberPercentageFloat,
  Wy as verifyPassword,
  Yy as verifyPasswordPowerful,
  Qy as verifyPasswordStrength,
  qy as verifyPhone,
  tm as verifyPostalCode,
  jy as verifyTelPhone,
  zy as verifyTextColor,
  rm as verifyUrl,
  Ay as versionTypeOptions,
  xf as vz_area,
  Zu as vz_enum,
  wg as vz_login,
  sf as vz_menu,
  $g as vz_office_preview,
  of as vz_org,
  Ef as vz_role,
  $u as vz_select,
  Op as vz_table,
  zg as vz_table_body,
  Rg as vz_table_search,
  rf as vz_tenant,
  yf as vz_upload,
  qu as vz_user,
  Qu as vz_user_org,
  ff as vz_version,
  ky as watermark
};
