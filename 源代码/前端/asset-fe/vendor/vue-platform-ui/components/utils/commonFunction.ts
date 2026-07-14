// 通用函数
import pinia from "../stores/index";
import { ElMessage } from "element-plus";
import { formatDate } from "./formatTime";
import { storeToRefs } from "pinia";
import { useCacheInfo } from "../stores/baseInfo";

// 类型定义
type ObjectType =
  | "USER"
  | "TENANT"
  | "VERSION"
  | "REGION"
  | "ORG"
  | "DICTIONARY";
type StoreObjects = Record<string, any>;
type PaddingSets = Record<string, Set<string | number>>;

// 常量
const REGEX_NUMBER_COMMA = /^(\d+,?)+$/;
const REGEX_COMMA = ",";

export default function () {
  const stores = useCacheInfo();
  const { users, enums, tenants, menus, version, regions, orgs, dictionaries } =
    storeToRefs(stores);

  // 加载状态集合
  const paddingSets: PaddingSets = {
    enums: new Set(),
    users: new Set(),
    orgs: new Set(),
    tenants: new Set(),
    menus: new Set(),
    versions: new Set(),
    regions: new Set(),
    dictionaries: new Set(),
  };

  // Store 对象映射
  const storeObjects: Record<ObjectType, StoreObjects> = {
    USER: users.value,
    TENANT: tenants.value,
    VERSION: version.value,
    REGION: regions.value,
    ORG: orgs.value,
    DICTIONARY: dictionaries.value,
  };
  const paddingSetKeyMap: Record<ObjectType, keyof PaddingSets> = {
    USER: "users",
    TENANT: "tenants",
    VERSION: "versions",
    REGION: "regions",
    ORG: "orgs",
    DICTIONARY: "dictionaries",
  };
  // 获取方法映射
  const fetchMethods: Record<ObjectType, (idList: number[]) => Promise<void>> =
    {
      USER: (idList) => useCacheInfo(pinia).findUserByIds(idList),
      TENANT: (idList) => useCacheInfo(pinia).findTenantByIds(idList),
      VERSION: (idList) => useCacheInfo(pinia).findVersionByIds(idList),
      REGION: (idList) => useCacheInfo(pinia).findRegionsByIds(idList),
      ORG: (idList) => useCacheInfo(pinia).findOrgByIds(idList),
      DICTIONARY: (idList) => useCacheInfo(pinia).findDictByIds(idList),
    };

  // 特定类型的获取函数
  const getters = {
    user: (id: string | number) => convertIdsToNames(id, "USER", true),
    tenant: (ids: string | number) => convertIdsToNames(ids, "TENANT"),
    version: (ids: string | number) => convertIdsToNames(ids, "VERSION"),
    org: (ids: string | number) => convertIdsToNames(ids, "ORG"),
    dictionary: (ids: string | number) => convertIdsToNames(ids, "DICTIONARY"),
    region: (ids: string | number) => {
      const name = convertIdsToNames(ids, "REGION");
      return String(ids) !== name ? name : "";
    },
  };
  // 格式化函数
  const formatters = {
    // 百分比格式化
    percent: (cellValue: string) => (cellValue ? `${cellValue}%` : "-"),

    // 日期时间格式化
    date: {
      YMD: (cellValue: string) => formatDate(new Date(cellValue), "YYYY-MM-DD"),
      YMDHMS: (cellValue: string) =>
        formatDate(new Date(cellValue), "YYYY-MM-DD HH:mm:ss"),
      HMS: (timeValue: number) =>
        formatDate(new Date(timeValue * 1000), "HH:mm:ss"),
    },

    // 小数格式化
    number: {
      scale: (value: string = "0", scale: number = 4) =>
        Number.parseFloat(value).toFixed(scale),
      scale2: (value: string = "0") => Number.parseFloat(value).toFixed(2),
    },
  };

  // 通用ID转换函数
  const convertIdsToNames = (
    ids: string | number,
    objectType: ObjectType,
    isSingle: boolean = false
  ): string => {
    const strIds = String(ids);
    if (!strIds || strIds === "undefined" || !REGEX_NUMBER_COMMA.test(strIds)) {
      return "";
    }

    const objects = storeObjects[objectType];
    const paddingSet =
      paddingSets[objectType.toLowerCase() as keyof PaddingSets];
    const idList = new Set(strIds.split(REGEX_COMMA));
    const nameList: string[] = [];
    const missingIds: number[] = [];
    if (!paddingSet) return ""; // 防止 undefined
    idList.forEach((id) => {
      const name = objects[id];
      if (name) {
        nameList.push(name);
        idList.delete(id);
      } else if (!paddingSet.has(id)) {
        missingIds.push(Number(id));
        paddingSet.add(id);
      }
    });

    if (missingIds.length > 0) {
      fetchMethods[objectType](missingIds);
    }

    return isSingle ? nameList[0] || strIds : nameList.join() || strIds;
  };

  // 字典项转换
  const getEnumItem = (itemIds: string | number, enumName: string): string => {
    const strIds = String(itemIds);
    if (!enumName || !strIds || strIds === "undefined") {
      return "";
    }

    const dictItems = enums.value[enumName] || [];
    const idList = new Set(strIds.split(REGEX_COMMA));
    const nameList: string[] = [];

    idList.forEach((id) => {
      const item = dictItems.find((item: any) => String(item.id) === id);
      if (item?.title) {
        nameList.push(item.title);
        idList.delete(id);
      }
    });

    // 只在未加载且未请求过时才请求

    if (dictItems.length === 0 && !paddingSets.enums.has(enumName)) {
      paddingSets.enums.add(enumName);
      useCacheInfo(pinia).findEnumByName(enumName);
    }

    return nameList.join() || strIds;
  };

  // 菜单转换
  const getMenu = (itemIds: string | number): string => {
    const strIds = String(itemIds);
    if (!strIds || strIds === "undefined") {
      return "";
    }

    const menuName = menus.value[strIds];
    if (menuName) {
      return menuName;
    }

    if (!paddingSets.menus.has(strIds)) {
      paddingSets.menus.add(strIds);
      useCacheInfo(pinia).findMenuById(strIds);
    }

    return strIds;
  };

  /**
   * 通用批量查找
   * @param objectType 类型，如 'USER' | 'ORG' | 'REGION' 等
   * @param ids 待查找的 id 数组
   */
  const batchFindObjects = async (
    objectType: ObjectType,
    ids: (string | number)[]
  ): Promise<void> => {
    if (!ids?.length) return;

    const storeObj = storeObjects[objectType];
    const paddingSet = paddingSets[paddingSetKeyMap[objectType]];
    const fetchMethod = fetchMethods[objectType];

    if (!paddingSet) return; // 防止 undefined

    const missingIds = ids.filter((id) => !storeObj[id] && !paddingSet.has(id));

    if (missingIds.length === 0) return;

    missingIds.forEach((id) => paddingSet.add(id));

    try {
      const numericIds = missingIds.map(Number).filter((id) => !isNaN(id));
      if (numericIds.length > 0 && fetchMethod) {
        await fetchMethod(numericIds);
      }
    } catch (error) {
      missingIds.forEach((id) => paddingSet.delete(id));
      ElMessage.error(`${objectType}信息加载失败`);
    }
  };

  // 组织节点树形结构
  const organizeNodes = (nodes: any[]): any[] => {
    const nodeMap: Record<string, any> = {};
    const rootNodes: any[] = [];

    // 创建节点映射
    nodes.forEach((node) => {
      nodeMap[node.id] = { ...node, children: [] };
    });

    // 构建树形结构
    nodes.forEach((node) => {
      const parentNode = nodeMap[node.parentId];
      if (parentNode) {
        parentNode.children.push(nodeMap[node.id]);
      } else {
        rootNodes.push(nodeMap[node.id]);
      }
    });

    return rootNodes;
  };

  // 表格格式化函数（兼容Element Table）
  const tableFormatters = {
    percent: (_: any, __: any, cellValue: string) =>
      formatters.percent(cellValue),
    dateYMD: (_: any, __: any, cellValue: string) =>
      formatters.date.YMD(cellValue),
    dateYMDHMS: (_: any, __: any, cellValue: string) =>
      formatters.date.YMDHMS(cellValue),
    dateHMS: (row: any, __: any, cellValue: any) => {
      const time =
        typeof row === "number"
          ? row
          : typeof cellValue === "number"
          ? cellValue
          : 0;
      return formatters.date.HMS(time);
    },
  };

  return {
    // Store 引用
    users,
    enums,
    tenants,
    menus,
    version,
    regions,
    orgs,
    dictionaries,
    paddingSets,

    // 批量操作
    batchFindObjects,
    // findAllUser: () => useCacheInfo(pinia).findAllUser(),

    // 格式化函数
    percentFormat: tableFormatters.percent,
    dateFormatYMD: tableFormatters.dateYMD,
    dateFormatYMDHMS: tableFormatters.dateYMDHMS,
    dateFormatHMS: tableFormatters.dateHMS,
    scaleFormat: formatters.number.scale,
    scale2Format: formatters.number.scale2,

    // 转换函数
    getUser: getters.user,
    getTenant: getters.tenant,
    getVersion: getters.version,
    getOrg: getters.org,
    getRegion: getters.region,
    getEnumItem,
    getMenu,

    // 工具函数
    organizeNodesFun: organizeNodes,
  };
}
