import { defineStore } from "pinia";
import { useRegionApi } from "../api/common/index";
import piniaPersistConfig from "./helper/persist";
const regionApi = useRegionApi();

export const regionsInfo = defineStore({
  id: "vz-regionsInfo",
  state: (): RegionState => ({
    regionInfos: {
      province: [],
      city: [],
      district: [],
    },
  }),
  actions: {
    //获取省市区
    async getAreaList(keyName: string, params: object, level: number) {
      if (
        keyName != "city" &&
        "district" != keyName &&
        this.regionInfos[keyName] &&
        this.regionInfos[keyName].length > 0
      )
        return;
      const { data } = await regionApi.findRegionsByAll(params);
      this.regionInfos[keyName] = data.map((item: any) => {
        return {
          value: item.id,
          label: item.name,
          leaf: level >= 2,
        };
      });
    },
  },
  persist: piniaPersistConfig("vz-regionsInfo"),
});
