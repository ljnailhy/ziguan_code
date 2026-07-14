<template>
  <el-cascader
    v-model="model"
    :options="options"
    :props="cascaderProps"
    :disabled="props.disabled"
    :placeholder="props.placeholder"
    :show-all-levels="false"
    :clearable="true"
    filterable
    @change="handleChange"
    style="width: 100%"
  />
</template>

<script lang="ts" setup name="vzUser">
import { ref, onMounted, watch } from "vue";
import { useOrgApi } from "../../api/system/org";
import { useUsersApi } from "../../api/system/user";

const props = defineProps({
  multiple: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
  checkStrictly: { type: Boolean, default: false },
  placeholder: { type: String, default: "请选择职员" },
  dictValue: { type: [String, Number], default: "" },
});

const emit = defineEmits(["update:model", "change"]);
const model = ref();
const options = ref<any[]>([]);

// 跨实例共享缓存与进行中请求去重
type VzUserGlobalCache = {
  orgChildrenCache: Map<string | number | "root", any[]>;
  orgByIdCache: Map<string | number, any>;
  usersByDeptCache: Map<string | number, any[]>;
  usersByIdCache: Map<string | number, any>;
  orgsIndexedAll: boolean;
  inflight: {
    allOrgs: Promise<void> | null;
    orgChildren: Map<string | number | "root", Promise<any[]>>;
    usersByDept: Map<string | number, Promise<any[]>>;
    userById: Map<string | number, Promise<any | undefined>>;
    usersByIds: Map<string, Promise<any[]>>;
  };
  batchPendingIds: Set<string | number>;
  batchResolvers: Map<string | number, Array<(u: any | undefined) => void>>;
  batchTimer: any;
};

/**
 * 获取全局用户缓存对象
 *
 * 该函数用于获取或初始化全局的用户缓存对象，确保在应用中只有一个缓存实例。
 * 缓存包含组织机构、用户信息等相关数据的存储结构和正在请求中的状态管理。
 *
 * @returns {VzUserGlobalCache} 返回全局用户缓存对象，包含以下主要属性：
 *   - orgChildrenCache: 组织子级关系缓存
 *   - orgByIdCache: 按ID索引的组织缓存
 *   - usersByDeptCache: 按部门索引的用户缓存
 *   - usersByIdCache: 按ID索引的用户缓存
 *   - orgsIndexedAll: 是否已索引所有组织的标记
 *   - inflight: 正在进行中的请求状态管理
 *   - batchPendingIds: 批量请求待处理的ID集合
 *   - batchResolvers: 批量请求的解析器映射
 *   - batchTimer: 批量请求定时器
 */
const getVzUserCache = (): VzUserGlobalCache => {
  const g: any = globalThis as any;
  // 检查全局对象上是否已存在缓存，如果不存在则创建新的缓存结构
  if (!g.__VzUserCache) {
    g.__VzUserCache = {
      orgChildrenCache: new Map(),
      orgByIdCache: new Map(),
      usersByDeptCache: new Map(),
      usersByIdCache: new Map(),
      orgsIndexedAll: false,
      inflight: {
        allOrgs: null,
        orgChildren: new Map(),
        usersByDept: new Map(),
        userById: new Map(),
        usersByIds: new Map(),
      },
      batchPendingIds: new Set(),
      batchResolvers: new Map(),
      batchTimer: null,
    } as VzUserGlobalCache;
  }
  return g.__VzUserCache as VzUserGlobalCache;
};

const loadAllOrgsOnce = async () => {
  const cache = getVzUserCache();
  if (cache.orgsIndexedAll) return;
  if (cache.inflight.allOrgs) return cache.inflight.allOrgs;
  cache.inflight.allOrgs = (async () => {
    const res = await useOrgApi().findAll({ size: 1000 }, { size: 1000 });
    const all = res?.data || [];
    for (const o of all) cache.orgByIdCache.set(o.id, o);
    const grouped: Record<string, any[]> = {};
    for (const o of all) {
      const key =
        o.parentId == null || o.parentId === -1 ? "root" : String(o.parentId);
      if (!grouped[key]) grouped[key] = [];
      grouped[key].push(o);
    }
    for (const k of Object.keys(grouped))
      cache.orgChildrenCache.set(k, grouped[k]);
    cache.orgsIndexedAll = true;
    cache.inflight.allOrgs = null;
  })();
  return cache.inflight.allOrgs;
};

const loadOrgChildren = async (parentId?: string | number) => {
  const cache = getVzUserCache();
  const key: any = parentId == null || parentId === -1 ? "root" : parentId;
  if (cache.orgChildrenCache.has(key)) return cache.orgChildrenCache.get(key)!;
  if (cache.inflight.orgChildren.has(key))
    return cache.inflight.orgChildren.get(key)!;
  const p = (async () => {
    try {
      const res = await useOrgApi().findAll({ parentId: parentId ?? -1 });
      if (!res.code) {
        const list = res.data || [];
        for (const o of list) cache.orgByIdCache.set(o.id, o);
        cache.orgChildrenCache.set(key, list);
        return list;
      }
    } catch (e) {}
    await loadAllOrgsOnce();
    return cache.orgChildrenCache.get(key) || [];
  })();
  cache.inflight.orgChildren.set(key, p);
  const result = await p;
  cache.inflight.orgChildren.delete(key);
  return result;
};

const loadUsersByDept = async (deptId: string | number) => {
  const cache = getVzUserCache();
  if (cache.usersByDeptCache.has(deptId))
    return cache.usersByDeptCache.get(deptId)!;
  if (cache.inflight.usersByDept.has(deptId))
    return cache.inflight.usersByDept.get(deptId)!;
  const p = (async () => {
    try {
      const res = await useUsersApi().findByOrg({ deptId });
      const list = !res.code ? res.data || [] : [];
      cache.usersByDeptCache.set(deptId, list);
      return list;
    } catch (e) {
      cache.usersByDeptCache.set(deptId, []);
      return [];
    }
  })();
  cache.inflight.usersByDept.set(deptId, p);
  const result = await p;
  cache.inflight.usersByDept.delete(deptId);
  return result;
};

// 懒加载配置
const cascaderProps = {
  multiple: props.multiple,
  emitPath: false,
  checkStrictly: props.checkStrictly,
  lazy: true,
  lazyLoad(node, resolve) {
    if (node.level === 0) {
      loadOrgChildren(undefined).then((roots) => {
        const orgs = (roots || []).map((org) => ({
          value: org.id,
          label: org.orgName,
          leaf: false,
        }));
        resolve(orgs);
      });
    } else {
      // 加载子组织和用户
      const orgId = node.data.value;
      Promise.all([loadOrgChildren(orgId), loadUsersByDept(orgId)]).then(
        ([orgs, users]) => {
          const orgNodes = (orgs || []).map((org) => ({
            value: org.id,
            label: org.orgName,
            disabled: org.id === orgId,
            leaf: false,
          }));
          const userNodes = (users || []).map((user) => ({
            value: user.id,
            label: user.username,
            disabled: user.parentId === orgId,
            leaf: true,
          }));
          resolve([...orgNodes, ...userNodes]);
        }
      );
    }
  },
};

const handleChange = (value) => {
  model.value = value;
  emit("update:model", value);
  emit("change", value);
};

// 懒加载默认选中路径预加载（支持单个ID）
const ensurePathForValue = async (val: string | number) => {
  const cache = getVzUserCache();
  // 先查本地缓存，命中则不再请求
  let user: any | undefined = cache.usersByIdCache.get(val);
  // 尝试按ID接口
  if (!user) {
    try {
      const maybe = (useUsersApi() as any).findById;
      if (typeof maybe === "function") {
        if (!cache.inflight.userById.has(val)) {
          cache.inflight.userById.set(
            val,
            (async () => {
              const res = await maybe(val);
              return res?.data;
            })()
          );
        }
        user = await cache.inflight.userById.get(val)!;
        if (user) cache.usersByIdCache.set(val, user);
      }
    } catch (e) {}
  }
  // 退化：从部门缓存查找
  if (!user) {
    for (const arr of cache.usersByDeptCache.values()) {
      const found = arr.find((u: any) => u.id == val);
      if (found) {
        user = found;
        cache.usersByIdCache.set(val, user);
        break;
      }
    }
  }
  // 退化：全量查找一次
  if (!user) {
    try {
      const resAll = await useUsersApi().findByOrg({ current: 1, size: 10000 });
      const list = resAll?.data || [];
      user = list.find((u: any) => u.id == val);
      if (user) cache.usersByIdCache.set(val, user);
    } catch (e) {}
  }
  if (!user) return;
  const deptId = user.deptId;

  if (!cache.orgByIdCache.has(deptId)) {
    await loadAllOrgsOnce();
  }
  const chain: any[] = [];
  let current: any = cache.orgByIdCache.get(deptId);
  let guard = 0;
  while (current && guard++ < 50) {
    chain.unshift(current);
    if (current.parentId == null || current.parentId === -1) break;
    current = cache.orgByIdCache.get(current.parentId);
    if (!current) {
      const parentChildren = await loadOrgChildren(chain[0]?.parentId);
      for (const o of parentChildren || []) cache.orgByIdCache.set(o.id, o);
      current = cache.orgByIdCache.get(chain[0]?.parentId);
    }
  }
  await loadOrgChildren(undefined);
  for (const org of chain) {
    await loadOrgChildren(org.id);
  }
  await loadUsersByDept(deptId);
  // 注入到 options 树，确保可见
  const roots = await loadOrgChildren(undefined);
  if (!Array.isArray(options.value) || options.value.length === 0) {
    options.value = (roots || []).map((org) => ({
      value: org.id,
      label: org.orgName,
      leaf: false,
      children: undefined,
    }));
  }
  const ensureChild = (
    nodes: any[],
    orgItem: any,
    parentOrgId?: string | number
  ) => {
    let node = nodes.find((n) => n.value === orgItem.id);
    if (!node) {
      node = {
        value: orgItem.id,
        label: orgItem.orgName,
        leaf: false,
        disabled: parentOrgId != null ? orgItem.id === parentOrgId : undefined,
        children: undefined,
      };
      nodes.push(node);
    } else if (parentOrgId != null && node.disabled === undefined) {
      node.disabled = orgItem.id === parentOrgId;
    }
    if (!node.children) node.children = [];
    return node;
  };
  let level = options.value as any[];
  for (const org of chain) {
    const parentOrgId =
      (level as any[]).length > 0 ? (level as any[])[0]?.parentId : undefined;
    const node = ensureChild(level, org, parentOrgId);
    // 补充该层的组织与用户（仅必要时）
    const orgs = await loadOrgChildren(org.id);
    const users = await loadUsersByDept(org.id);

    // 合并组织
    for (const o of orgs || []) ensureChild(node.children, o, org.id);
    // 合并用户
    for (const u of users || []) {
      if (!node.children.find((c: any) => c.value === u.id)) {
        node.children.push({
          value: u.id,
          label: u.username,
          leaf: true,
          disabled: u.parentId === org.id,
        });
      }
    }
    level = node.children;
  }
};

// 批量预加载默认选中（优先批量接口 findByIds）
const ensurePathsForValues = async (values: Array<string | number>) => {
  const cache = getVzUserCache();
  const ids = Array.from(new Set(values));
  const unknown = ids.filter((id) => !cache.usersByIdCache.has(id));
  if (unknown.length > 0) {
    const awaiters = unknown.map(
      (id) =>
        new Promise<any | undefined>((resolve) => {
          const list = cache.batchResolvers.get(id) || [];
          list.push(resolve);
          cache.batchResolvers.set(id, list);
          cache.batchPendingIds.add(id);
        })
    );
    if (!cache.batchTimer) {
      cache.batchTimer = setTimeout(async () => {
        const pending = Array.from(cache.batchPendingIds);
        cache.batchPendingIds.clear();
        cache.batchTimer = null;
        let users: any[] = [];
        try {
          const maybeBatch = (useUsersApi() as any).findByIds;
          if (typeof maybeBatch === "function") {
            const res = await maybeBatch(pending);
            users = res?.data || [];
          } else {
            const singles = await Promise.all(
              pending.map((id) => (useUsersApi() as any).findById?.(id))
            );
            users = singles.map((r) => r?.data).filter((u) => u != null);
          }
        } catch (_) {
          users = [];
        }
        const byId = new Map<string | number, any>();
        for (const u of users || []) {
          cache.usersByIdCache.set(u.id, u);
          byId.set(u.id, u);
        }
        for (const id of pending) {
          const resolvers = cache.batchResolvers.get(id) || [];
          const value = byId.get(id);
          for (const r of resolvers) r(value);
          cache.batchResolvers.delete(id);
        }
      }, 0);
    }
    await Promise.all(awaiters);
  }
  for (const id of ids) await ensurePathForValue(id);
};

watch(
  () => props.dictValue,
  (val) => {
    if (val) {
      if (props.multiple) {
        const newVal = Array.isArray(val)
          ? val
          : val
              .toString()
              .split(",")
              .map((str) => str.trim())
              .map((s) => (isNaN(Number(s)) ? s : Number(s)));
        ensurePathsForValues(newVal as any[]).then(() => {
          model.value = newVal;
        });
      } else {
        const parsed = isNaN(Number(val)) ? val : Number(val);
        ensurePathsForValues([parsed as any]).then(() => {
          model.value = parsed;
        });
      }
    } else {
      model.value = val;
    }
  },
  { immediate: true, deep: true }
);

onMounted(() => {
  // 初始化顶级组织
  // cascaderProps.lazyLoad({ level: 0 }, (nodes: any[]) => {
  //   options.value = nodes;
  // });
});
</script>

<!-- <template>
  <div style="width: 100%">
    <el-cascader
      v-model="model"
      :options="options"
      :props="{
        multiple: props.multiple,
        emitPath: false,
        checkStrictly: props.checkStrictly,
      }"
      :disabled="props.disabled"
      :placeholder="placeholder"
      :show-all-levels="false"
      :clearable="true"
      filterable
      @change="handleChange"
      style="width: 100%"
    >
    </el-cascader>
  </div>
</template>

<script lang="ts" setup name="vzUser">
import { onMounted, ref, watch } from "vue";
import { useUsersApi } from "../../api/system/user";
import { useOrgApi } from "../../api/system/org";
const props = defineProps({
  multiple: {
    type: Boolean,
    default: () => false,
  },
  disabled: {
    type: Boolean,
    default: () => false,
  },
  checkStrictly: {
    type: Boolean,
    default: () => false,
  },
  placeholder: {
    type: String,
    default: () => "请选择职员",
  },
  dictValue: {
    type: [String, Number],
    default: () => "",
  },
});

const emit = defineEmits(["update:model", "change"]);
//定义变量
const options = ref();
const model = ref();
const users = ref([]);

const handleChange = (value: string | number | Array<string | number>) => {
  model.value = value;
  emit("update:model", value);
  emit("change", value);
};
const getUsers = async () => {
  const { data, code } = await useUsersApi().findByOrg(
    { current: 1, size: 10000 },
    { current: 1, size: 10000 }
  );
  if (!code) {
    users.value = data.map((item) => {
      return {
        ...item,
        value: item.id,
        label: item.username,
        leaf: true,
      };
    });
  }
  getOptions();
};
const getOptions = () => {
  useOrgApi()
    .findAll({ size: 10000 }, { size: 10000 })
    .then((data) => {
      if (!data.code) {
        const infoList = data.data.map((item) => {
          return {
            ...item,
            value: item.id,
            label: item.orgName,
            parentId: item.parentId,
          };
        });
        options.value = listTransTree(infoList);
      }
    });
};
const listTransTree = (
  list: any[],
  id = "id",
  parentId = "parentId",
  children = "children"
) => {
  return list.filter((parent) => {
    const branchArr = list.filter((child) => {
      return parent[id] === child[parentId];
    });
    const userArr = users.value.filter((child: any) => {
      return parent[id] === child.deptId;
    });

    branchArr.push(...userArr);
    if (branchArr.length > 0) {
      parent[children] = branchArr;
    } else {
      parent.disabled = true;
    }

    return (
      !parent[parentId] || !list.some((item) => item.id == parent[parentId])
    );
  });
};

watch(
  () => props.dictValue,
  (val: any) => {
    if (val) {
      if (props.multiple && val) {
        const newVal = val
          .toString()
          .split(",")
          .map((str) => str.trim())
          .map(Number)
          .filter((num) => !isNaN(num));
        model.value = Array.isArray(val) ? val : newVal;
      } else {
        model.value = Number(val);
      }
    } else {
      model.value = val;
    }
  },
  { immediate: true, deep: true }
);
onMounted(() => {
  getUsers();
});
</script> -->
