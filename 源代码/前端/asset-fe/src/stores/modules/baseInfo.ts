import { defineStore } from "@platform/vue-platform-ui";
import { BaseState } from "@/stores/interface";
import { useBaseInfoApi } from "@/api/modules/dictionary";
import { projectInfo } from "@/api/modules/recovery/projectInfo/api"; // 项目信息
import { propertyInfo } from "@/api/modules/property/propertyInfo/api"; // 资产信息
import { agency } from "@/api/modules/source/agency/api"; // 机构信息
import { contractInfo } from "@/api/modules/source/contractInfo/api"; // 合同信息
import { customerInfo } from "@/api/modules/source/customerInfo/api";
import { revePropertyInfo } from "@/api/modules/compensatory/revePropertyInfo/api"; // 客户信息
// import { getFlatMenuList, getShowMenuList, getAllBreadcrumbList, parseMetaRecursively } from "@/utils";
// import authMenuList from "@/assets/json/authMenuList.json"; //后面用后端路由时候删掉这个
export const useBaseStore = defineStore({
  id: "vz-baseInfo",
  state: (): BaseState => ({
    enums: {},
    projects: {},
    assets: {},
    agencies: {},
    contracts: {},
    customers: {},
    product: []
  }),
  getters: {},
  actions: {
    async findEnumByName(name: string | number) {
      if (this.enums[name] && this.enums[name].length > 0) return this.enums[name];
      const { data, code } = await useBaseInfoApi().findDicByCode(name);
      if (!code) {
        const obj = data.items?.map((item: any) => {
          return {
            ...item,
            value: item.id,
            label: item.itemName
          };
        });
        this.enums[name] = obj;
        return obj;
      }
    },
    async findProjectName(id: string | number) {
      if (this.projects[id]) return this.projects[id];
      const { data, code } = await projectInfo().findById(id);
      if (!code) {
        this.projects[id] = {
          id: data.id,
          projectName: data.projectName
        };
        return this.projects[id];
      }
    },
    async findReveName(id: string | number) {
      if (this.projects[id]) return this.projects[id];
      const { data, code } = await revePropertyInfo().findById(id);
      if (!code) {
        this.projects[id] = {
          id: data.id,
          reveMeasure: data.reveMeasure
        };
        return this.projects[id];
      }
    },
    async findAssetName(id: string | number) {
      if (this.assets[id]) return this.assets[id];
      const { data, code } = await propertyInfo().findById(id);
      if (!code) {
        this.assets[id] = {
          id: data.id,
          propertyName: data.propertyName
        };
        return this.assets[id];
      }
    },
    async findAgencyName(id: string | number) {
      if (this.agencies[id]) return this.agencies[id];
      const { data, code } = await agency().findById(id);
      if (!code) {
        this.agencies[id] = {
          id: data.id,
          agencyName: data.agencyName
        };
        return this.agencies[id];
      }
    },
    async findContractName(id: string | number) {
      if (this.contracts[id]) return this.contracts[id];
      const { data, code } = await contractInfo().findById(id);
      if (!code) {
        this.contracts[id] = {
          id: data.id,
          contractName: data.contractName
        };
        return this.contracts[id];
      }
    },
    async findCustomerName(id: string | number) {
      if (this.customers[id]) return this.customers[id];
      const { data, code } = await customerInfo().findById(id);
      if (!code) {
        this.customers[id] = {
          id: data.id,
          customerName: data.customerName
        };
        return this.customers[id];
      }
    },
    async findProductList() {
      if (this.product && this.product.length) return this.product;
      const { data, code } = await projectInfo().findProductName();
      if (!code) {
        const productList = data?.map((item: any) => {
          return {
            ...item,
            value: item.productId,
            label: item.productName
          };
        });
        this.product = productList;
        return productList;
      }
    }
  }
});
