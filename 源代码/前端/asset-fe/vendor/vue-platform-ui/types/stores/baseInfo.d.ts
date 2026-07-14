export declare const useCacheInfo: import("pinia").StoreDefinition<"commonCache", {
    users: EmptyObjectType<any>;
    enums: EmptyObjectType<any>;
    dictionaries: EmptyObjectType<any>;
    enumList: EmptyObjectType<any>;
    tenants: EmptyObjectType<any>;
    menus: EmptyObjectType<any>;
    version: EmptyObjectType<any>;
    regions: EmptyObjectType<any>;
    orgs: EmptyObjectType<any>;
    commonNames: EmptyObjectType<any>;
    requestedEnums: Record<string | number, boolean>;
}, {}, {
    findChineseByIds(api: Function, idList: Array<string | number>, key: string): Promise<void>;
    findUserByIds(idList: Array<string | number>): Promise<void>;
    findDictByIds(idList: Array<string | number>): Promise<void>;
    findRegionsByIds(idList: Array<string | number>): Promise<void>;
    findEnumByName(name: string | number): Promise<any>;
    resetEnumCache(name?: string | number): void;
    findMenuById(id: string | number): Promise<void>;
    findTenantByIds(idList: Array<number | string>): Promise<void>;
    findVersionByIds(idList: Array<number | string>): Promise<void>;
    findOrgByIds(idList: Array<string | number>): Promise<void>;
    cacheOrgOptions(key: string, options: any[]): Promise<void>;
    getCachedOrgOptions(key: string): any[];
    clearOrgOptionsCache(key?: string): void;
}>;
