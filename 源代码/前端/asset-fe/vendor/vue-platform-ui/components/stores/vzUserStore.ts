import { defineStore } from "pinia";
import { useOrgApi } from "../api/system/org";
import { useUsersApi } from "../api/system/user";
import piniaPersistConfig from "./helper/persist";

type Id = string | number;

type Resolver<T> = (value: T | PromiseLike<T>) => void;

export const useVzUserStore = defineStore({
  id: "vzUserStore",
  state: () => ({
    orgChildrenCache: new Map<string | number | "root", any[]>(),
    orgByIdCache: new Map<Id, any>(),
    usersByDeptCache: new Map<Id, any[]>(),
    usersByIdCache: new Map<Id, any>(),
    orgsIndexedAll: false,
    inflightAllOrgs: null as Promise<void> | null,
    inflightOrgChildren: new Map<string | number | "root", Promise<any[]>>(),
    inflightUsersByDept: new Map<Id, Promise<any[]>>(),
    inflightUserById: new Map<Id, Promise<any | undefined>>(),
    batchTimer: null as any,
    batchPendingIds: new Set<Id>(),
    batchResolvers: new Map<Id, Resolver<any | undefined>[]>(),
  }),
  actions: {
    async indexAllOrgsOnce(): Promise<void> {
      if (this.orgsIndexedAll) return;
      if (this.inflightAllOrgs) return this.inflightAllOrgs;
      this.inflightAllOrgs = (async () => {
        const res: any = await useOrgApi().findAll(
          { size: 10000 },
          { size: 10000 }
        );
        const all: any[] = res?.data || [];
        for (const o of all) this.orgByIdCache.set(o.id, o);
        const grouped: Record<string, any[]> = {};
        for (const o of all) {
          const key =
            o.parentId == null || o.parentId === -1
              ? "root"
              : String(o.parentId);
          if (!grouped[key]) grouped[key] = [];
          grouped[key].push(o);
        }
        for (const k of Object.keys(grouped))
          this.orgChildrenCache.set(k as any, grouped[k]);
        this.orgsIndexedAll = true;
        this.inflightAllOrgs = null;
      })();
      return this.inflightAllOrgs;
    },
    async getOrgChildren(parentId?: Id): Promise<any[]> {
      const key: any = parentId == null || parentId === -1 ? "root" : parentId;
      if (this.orgChildrenCache.has(key))
        return this.orgChildrenCache.get(key)!;
      if (this.inflightOrgChildren.has(key))
        return this.inflightOrgChildren.get(key)!;
      const p = (async () => {
        try {
          const res: any = await useOrgApi().findAll(
            { parentId: parentId ?? -1 },
            { size: 10000 }
          );
          if (!res.code) {
            const list: any[] = res.data || [];
            for (const o of list) this.orgByIdCache.set(o.id, o);
            this.orgChildrenCache.set(key, list);
            return list;
          }
        } catch (_) {}
        await this.indexAllOrgsOnce();
        return this.orgChildrenCache.get(key) || [];
      })();
      this.inflightOrgChildren.set(key, p);
      const result = await p;
      this.inflightOrgChildren.delete(key);
      return result;
    },
    async getUsersByDept(deptId: Id): Promise<any[]> {
      if (this.usersByDeptCache.has(deptId))
        return this.usersByDeptCache.get(deptId)!;
      if (this.inflightUsersByDept.has(deptId))
        return this.inflightUsersByDept.get(deptId)!;
      const p = (async () => {
        try {
          const res: any = await useUsersApi().findByOrg(
            { deptId },
            { current: 1, size: 10000 }
          );
          const list: any[] = !res.code ? res.data || [] : [];
          this.usersByDeptCache.set(deptId, list);
          for (const u of list) this.usersByIdCache.set(u.id, u);
          return list;
        } catch (_) {
          this.usersByDeptCache.set(deptId, []);
          return [];
        }
      })();
      this.inflightUsersByDept.set(deptId, p);
      const result = await p;
      this.inflightUsersByDept.delete(deptId);
      return result;
    },
    // Single user by id with inflight dedupe
    async getUserById(id: Id): Promise<any | undefined> {
      if (this.usersByIdCache.has(id)) return this.usersByIdCache.get(id);
      if (this.inflightUserById.has(id)) return this.inflightUserById.get(id)!;
      const p = (async () => {
        const api: any = useUsersApi();
        if (typeof api.findById !== "function") return undefined;
        const res = await api.findById(id);
        const user = res?.data;
        if (user) this.usersByIdCache.set(id, user);
        return user;
      })();
      this.inflightUserById.set(id, p);
      const result = await p;
      this.inflightUserById.delete(id);
      return result;
    },
    // Batch get users by ids once per tick across all instances
    async getUsersByIdsBatched(ids: Id[]): Promise<Map<Id, any | undefined>> {
      const result = new Map<Id, any | undefined>();
      const unknown: Id[] = [];
      for (const id of ids) {
        if (this.usersByIdCache.has(id)) {
          result.set(id, this.usersByIdCache.get(id));
        } else {
          unknown.push(id);
        }
      }
      if (unknown.length === 0) return result;

      const awaiters = unknown.map(
        (id) =>
          new Promise<any | undefined>((resolve) => {
            const list = this.batchResolvers.get(id) || [];
            list.push(resolve);
            this.batchResolvers.set(id, list);
            this.batchPendingIds.add(id);
          })
      );

      if (!this.batchTimer) {
        this.batchTimer = setTimeout(async () => {
          const pendingIds = Array.from(this.batchPendingIds);
          this.batchPendingIds.clear();
          this.batchTimer = null;

          let users: any[] = [];
          try {
            const api: any = useUsersApi();
            if (typeof api.findByIds === "function") {
              const res = await api.findByIds(pendingIds);
              users = res?.data || [];
            } else {
              // fallback: parallel single queries
              const singles = await Promise.all(
                pendingIds.map((id) => this.getUserById(id))
              );
              users = singles.filter(Boolean) as any[];
            }
          } catch (_) {
            users = [];
          }
          const byId = new Map<Id, any>();
          for (const u of users) {
            this.usersByIdCache.set(u.id, u);
            byId.set(u.id, u);
          }
          // resolve awaiters
          for (const id of pendingIds) {
            const resolvers = this.batchResolvers.get(id) || [];
            const value = byId.get(id);
            for (const r of resolvers) r(value);
            this.batchResolvers.delete(id);
          }
        }, 0);
      }

      const fetched = await Promise.all(awaiters);
      unknown.forEach((id, idx) => result.set(id, fetched[idx]));
      return result;
    },
  },
  // Do NOT persist this store: it contains non-serializable Maps and Promises
  // Persisting would overwrite runtime caches/inflight flags after refresh
  persist: false as any,
});
