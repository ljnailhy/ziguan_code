<template>
  <RenderTableColumn v-bind="column" />
</template>

<script setup lang="tsx" name="TableColumn">
import { inject, ref, useSlots } from "vue";
// import type { JSX } from "vue/jsx-runtime";
import {
  ColumnProps,
  RenderScope,
  HeaderRenderScope,
} from "../interface/index";
import {
  filterEnum,
  formatValue,
  handleProp,
  handleRowAccordingToProp,
} from "../../../utils/index";

defineProps<{ column: ColumnProps }>();

const slots = useSlots();

const enumMap = inject("enumMap", ref(new Map()));

// 渲染表格数据
const renderCellData = (item: ColumnProps, scope: RenderScope<any>) => {
  return enumMap.value.get(item.prop) && item.isFilterEnum
    ? filterEnum(
        handleRowAccordingToProp(scope.row, item.prop!),
        enumMap.value.get(item.prop)!,
        item.fieldNames
      )
    : formatValue(handleRowAccordingToProp(scope.row, item.prop!));
};

// 获取 tag 类型
const getTagType = (item: ColumnProps, scope: RenderScope<any>) => {
  return (
    filterEnum(
      handleRowAccordingToProp(scope.row, item.prop!),
      enumMap.value.get(item.prop),
      item.fieldNames,
      "tag"
    ) || "primary"
  );
};

const typeRenderMap: Record<
  string,
  (item: ColumnProps, scope: RenderScope<any>) => any
> = {
  tag: (item, scope) => (
    <el-tag type={getTagType(item, scope)}>
      {renderCellData(item, scope)}
    </el-tag>
  ),
  user: (item, scope) => (
    <display-text value={scope.row[item.prop!]} type="user"></display-text>
  ),
  date: (item, scope) => (
    <dict-date
      date={scope.row[item.prop!]}
      format={item.format ? item.format : "YYYY-MM-DD HH:mm:ss"}
    ></dict-date>
  ),
  money: (item, scope) => (
    <el-statistic value={scope.row[item.prop!]} precision={2} />
  ),
  org: (item, scope) => (
    <display-text value={scope.row[item.prop!]} type="org"></display-text>
  ),
  area: (item, scope) => (
    <display-text value={scope.row[item.prop!]} type="region"></display-text>
  ),
  custom: (item, scope) => (
    <display-text value={scope.row[item.prop!]} type="custom"></display-text>
  ),
};

const RenderTableColumn = (item: ColumnProps) => {
  return (
    <>
      {item.isShow && (
        <el-table-column
          {...item}
          align={item.align ?? "center"}
          showOverflowTooltip={
            item.showOverflowTooltip ?? item.prop !== "operation"
          }
        >
          {{
            default: (scope: RenderScope<any>) => {
              if (item._children)
                return item._children.map((child) => RenderTableColumn(child));
              if (item.render) return item.render(scope);
              if (slots[handleProp(item.prop!)])
                return slots[handleProp(item.prop!)]!(scope);

              // 类型映射渲染
              for (const key of Object.keys(typeRenderMap)) {
                if ((item as any)[key]) return typeRenderMap[key](item, scope);
              }

              return renderCellData(item, scope);
            },
            header: (scope: HeaderRenderScope<any>) => {
              if (item.headerRender) return item.headerRender(scope);
              if (slots[`${handleProp(item.prop!)}Header`])
                return slots[`${handleProp(item.prop!)}Header`]!(scope);
              return item.label;
            },
          }}
        </el-table-column>
      )}
    </>
  );
};
</script>
