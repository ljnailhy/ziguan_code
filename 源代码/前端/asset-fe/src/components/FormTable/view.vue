<template>
  <div>
    <el-table :data="props.tableData.data" border v-bind="$attrs" class="module-table-uncollected" max-height="500">
      <el-table-column width="60" label="序号" align="center" type="index"> </el-table-column>
      <el-table-column
        v-for="(item, index) in props.tableData.header"
        :key="index"
        show-overflow-tooltip
        :prop="item.prop"
        :min-width="item.width"
        :label="item.label"
        :align="item.align || 'center'"
      >
        <template #default="scope">
          <template v-if="item.type === 'image'">
            <el-image
              :style="{ width: `${item.width}px`, height: `${item.height}px` }"
              :src="scope.row[item.prop]"
              :zoom-rate="1.2"
              :preview-src-list="[scope.row[item.prop]]"
              preview-teleported
              fit="cover"
            />
          </template>
          <template v-else-if="item.type === 'boolean'">
            <template v-if="item.activeValue">
              {{ scope.row[item.prop] == "Y" ? "是" : "否" }}
            </template>
            <dict-switch v-else :value="scope.row[item.prop]"></dict-switch>
          </template>
          <template v-else-if="item.type === 'year'">
            <dict-date :date="scope.row[item.prop]" format="YYYY"></dict-date>
          </template>
          <template v-else-if="item.type === 'month'">
            <dict-date :date="scope.row[item.prop]" format="YYYY-MM"></dict-date>
          </template>
          <template v-else-if="item.type === 'date'">
            <dict-date :date="scope.row[item.prop]" format="YYYY-MM-DD"></dict-date>
          </template>
          <template v-else-if="item.type === 'datetime'">
            <dict-date :date="scope.row[item.prop]" format="YYYY-MM-DD HH:mm:ss"></dict-date>
          </template>
          <template v-else-if="item.type === 'daterange' && scope.row[item.prop] && scope.row[item.prop].length">
            <dict-date :date="scope.row[item.prop][0]" format="YYYY-MM-DD"></dict-date>
            -
            <dict-date :date="scope.row[item.prop][1]" format="YYYY-MM-DD"></dict-date>
          </template>
          <template v-else-if="item.prop === 'productName' && props.productList">
            <dict-enum :options="props.productList" :value="scope.row[item.prop]"></dict-enum>
          </template>
          <template v-else-if="item.type === 'select'">
            <dict-enum :options="item.enum" v-if="item.enum" :value="scope.row[item.prop]"></dict-enum>
            <dict-name :dict-type="item.dictType" v-if="item.dictType" :dict-value="scope.row[item.prop]"></dict-name>
          </template>
          <template v-else-if="item.type === 'user' || item.type === 'orguser'">
            <dict-user-name :user-code="scope.row[item.prop]"></dict-user-name>
          </template>
          <template v-else-if="item.type === 'money'">
            <div v-currency="scope.row[item.prop]"></div>
          </template>
          <template v-else-if="item.type === 'text' && item.text"> {{ scope.row[item.prop] }}{{ item.text }} </template>
          <template v-else-if="item.type === 'org'">
            <dict-org-name :value="scope.row[item.prop]"></dict-org-name>
          </template>
          <template v-else-if="item.type === 'lawyer'">
            <vzLawyerInfo :default-value="scope.row[item.prop]" :disabled="true"></vzLawyerInfo>
          </template>
          <template v-else-if="item.type === 'contract'">
            <vzContractInfo :default-value="scope.row[item.prop]" :disabled="true"></vzContractInfo>
          </template>
          <template v-else-if="item.type === 'project'">
            <vzProjectInfo :default-value="scope.row[item.prop]" :disabled="true" select-type="radio"></vzProjectInfo>
          </template>
          <template v-else-if="item.type === 'lawFirm'">
            <vzLawFirmInfo :default-value="scope.row[item.prop]" :disabled="true"></vzLawFirmInfo>
          </template>
          <template v-else-if="item.type === 'subject'">
            <VzSubject :default-value="scope.row[item.prop]" :disabled="true"></VzSubject>
          </template>
          <template v-else-if="item.type === 'property'">
            <vzPropertyInfo :default-value="scope.row[item.prop]" :disabled="true"></vzPropertyInfo>
          </template>
          <template v-else-if="item.type === 'customer'">
            <vzCustomerInfo :default-value="scope.row[item.prop]" :disabled="true"></vzCustomerInfo>
          </template>
          <template v-else-if="item.type === 'agency'">
            <vzAgency :default-value="scope.row[item.prop]" :disabled="true"></vzAgency>
          </template>
          <template v-else-if="item.type === 'asset'">
            <vzAsset :default-value="scope.row[item.prop]" :disabled="true"></vzAsset>
          </template>
          <template v-else-if="item.type === 'reve'">
            <vz-reve-info :default-value="scope.row[item.prop]" :disabled="true"></vz-reve-info>
          </template>
          <template v-else-if="item.type === 'propertyRightInfo'">
            {{ scope.row[item.prop] || "--" }}
          </template>
          <template v-else-if="item.type === 'file'">
            <UploadFiles
              class="upload-demo"
              :limit="1"
              :disabled="true"
              :filter-data="{ doType: item.doType, doId: scope.row['id'] }"
              v-model:file-list="scope.row[item.prop]"
            >
            </UploadFiles>
          </template>
          <template v-else> {{ scope.row[item.prop] || "--" }} </template>
        </template>
      </el-table-column>
      <el-table-column label="操作" :width="props.operationWidth" v-if="props.viewOperation" align="center" fixed="right">
        <template #default="scope">
          <slot name="operation" :index="scope.$index" :row="scope.row"></slot>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts" name="viewTableForm">
import vzLawFirmInfo from "@/components/source/vzLawFirmInfo.vue";
import vzLawyerInfo from "@/components/source/vzLawyerInfo.vue";
import VzSubject from "@/components/source/vzSubject.vue";
import vzContractInfo from "@/components/source/vzContractInfo.vue";
import vzProjectInfo from "@/components/source/vzProjectInfo.vue";
import VzReveInfo from "@/components/source/vzReveInfo.vue";
import vzPropertyInfo from "@/components/source/vzPropertyInfo.vue";
import vzCustomerInfo from "@/components/source/vzCustomerInfo.vue";
import vzAgency from "@/components/source/vz-agency.vue";
import vzAsset from "@/components/source/vzAsset.vue";
import UploadFiles from "@/components/Upload/Files.vue";
// 定义父组件传过来的值
interface FileTableProps {
  tableData?: { [key: string]: any };
  operationWidth?: number;
  viewOperation?: boolean;
  productList?: any;
}

const props = withDefaults(defineProps<FileTableProps>(), {
  tableData: () => ({}),
  // showOperation: false,
  operationWidth: 80
});
</script>
<style scoped lang="scss"></style>
