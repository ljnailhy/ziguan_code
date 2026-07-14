import { defineStore } from "pinia";
import { useUsersApi } from "../api/system/user";
import { useCommonApi, useRegionApi } from "../api/common/index";
import { useTenantApi } from "../api/system/tenant";
import { useMenuApi } from "../api/system/menu";
import { useVersionApi } from "../api/system/version";
import { useOrgApi } from "../api/system/org";
import piniaPersistConfig from "./helper/persist";

export const useCacheInfo = defineStore({
  id: "commonCache",
  state: () => ({
    users: <EmptyObjectType>{},
    enums: <EmptyObjectType>{},
    dictionaries: <EmptyObjectType>{},
    enumList: <EmptyObjectType>{},
    tenants: <EmptyObjectType>{},
    menus: <EmptyObjectType>{},
    version: <EmptyObjectType>{},
    regions: <EmptyObjectType>{},
    orgs: <EmptyObjectType>{
      orgOptionsCache: [] as any[],
    },
    commonNames: <EmptyObjectType>{},
    requestedEnums: {} as Record<string | number, boolean>,
  }),
  actions: {
    // 公共的findByIds
    async findChineseByIds(
      api: Function,
      idList: Array<string | number>,
      key: string
    ) {
      const { data, code } = await api(idList);
      if (!code) {
        data.forEach((res: any) => {
          this.commonNames[res.id] = res[key];
        });
      }
    },
    // 通过ID集合找人
    async findUserByIds(idList: Array<string | number>) {
      const { data, code } = await useUsersApi().findByIds(idList);
      if (!code) {
        data.forEach((res: any) => {
          this.users[res.id] = res.username;
        });
      }
    },
    // 通过ID集合找字典
    async findDictByIds(idList: Array<string | number>) {
      const { data, code } = await useCommonApi().findDictByIds(idList);
      if (!code) {
        console.log(data);
        data.forEach((res: any) => {
          this.dictionaries[res.id] = res.username;
        });
      }
    },

    // 省市区
    async findRegionsByIds(idList: Array<string | number>) {
      const { data, code } = await useRegionApi().findRegionsByIds(idList);
      if (!code) {
        data.forEach((res: any) => {
          this.regions[res.id] = res.name;
        });
      }
    },
    // 字典项
    async findEnumByName(name: string | number) {
      // 使用对象检查
      if (this.requestedEnums[name]) {
        console.log(`${name} 已经请求过，直接返回缓存数据`);
        return this.enums[name] || [];
      }

      try {
        // 标记为已请求
        this.requestedEnums[name] = true;

        const { data, code } = await useCommonApi().findDicByCode(name);

        if (!code) {
          const obj = data.items?.map((item: any) => ({
            ...item,
            id: item.id,
            title: item.itemName,
          }));

          this.enums[name] = obj;
          return obj;
        } else {
          // 请求失败，移除标记
          delete this.requestedEnums[name];
          console.error(`获取 ${name} 枚举数据失败，code: ${code}`);
          return [];
        }
      } catch (error) {
        // 发生异常，移除标记
        delete this.requestedEnums[name];
        console.error(`获取 ${name} 枚举数据时发生异常:`, error);
        return [];
      }
    },

    // 重置缓存的方法
    resetEnumCache(name?: string | number) {
      if (name) {
        delete this.requestedEnums[name];
        delete this.enums[name];
      } else {
        this.requestedEnums = {};
        this.enums = {};
      }
    },
    // 菜单
    async findMenuById(id: string | number) {
      if (this.menus[id] && this.menus[id].length > 0) return;
      const { data, code } = await useMenuApi().findById(id);
      if (!code) {
        this.menus[id] = data.menuName;
      }
    },
    //租户
    async findTenantByIds(idList: Array<number | string>) {
      const { data, code } = await useTenantApi().findByIds(idList);
      if (!code) {
        data.forEach((res: any) => {
          this.tenants[res.id] = res.tenantName;
        });
      }
    },
    //版本
    async findVersionByIds(idList: Array<number | string>) {
      const { data, code } = await useVersionApi().findByIds(idList);
      if (!code) {
        data.forEach((res: any) => {
          this.version[res.id] = res.versionName;
        });
      }
    },
    // 通过ID集合组织
    async findOrgByIds(idList: Array<string | number>) {
      const { data, code } = await useOrgApi().findByIds(idList);
      if (!code) {
        data.forEach((res: any) => {
          this.orgs[res.id] = res.orgName;
        });
      }
    },
    async cacheOrgOptions(key: string, options: any[]) {
      this.orgs[key] = options;
    },

    // 获取缓存的组织选项
    getCachedOrgOptions(key: string): any[] {
      return this.orgs[key] || [];
    },

    // 清除组织选项缓存
    clearOrgOptionsCache(key?: string) {
      if (key) {
        delete this.orgs[key];
      } else {
        // 清空所有组织缓存
        Object.keys(this.orgs).forEach((k) => {
          if (k !== "orgOptionsCache") {
            // 保留基本结构
            delete this.orgs[k];
          }
        });
        this.orgs.orgOptionsCache = [];
      }
    },
  },
  persist: piniaPersistConfig("commonCache"),
});
