type Id = string | number;
type Resolver<T> = (value: T | PromiseLike<T>) => void;
export declare const useVzUserStore: import("pinia").StoreDefinition<"vzUserStore", {
    orgChildrenCache: Map<string | number, any[]>;
    orgByIdCache: Map<Id, any>;
    usersByDeptCache: Map<Id, any[]>;
    usersByIdCache: Map<Id, any>;
    orgsIndexedAll: boolean;
    inflightAllOrgs: Promise<void> | null;
    inflightOrgChildren: Map<string | number, Promise<any[]>>;
    inflightUsersByDept: Map<Id, Promise<any[]>>;
    inflightUserById: Map<Id, Promise<any>>;
    batchTimer: any;
    batchPendingIds: Set<Id>;
    batchResolvers: Map<Id, Resolver<any>[]>;
}, {}, {
    indexAllOrgsOnce(): Promise<void>;
    getOrgChildren(parentId?: Id): Promise<any[]>;
    getUsersByDept(deptId: Id): Promise<any[]>;
    getUserById(id: Id): Promise<any | undefined>;
    getUsersByIdsBatched(ids: Id[]): Promise<Map<Id, any | undefined>>;
}>;
export {};
