import { KeepAliveState } from "../interface/index";
export declare const useKeepAliveStore: import("pinia").StoreDefinition<"vz-keepAlive", KeepAliveState, {}, {
    addKeepAliveName(name: string): Promise<void>;
    removeKeepAliveName(name: string): Promise<void>;
    setKeepAliveName(keepAliveName?: string[]): Promise<void>;
}>;
