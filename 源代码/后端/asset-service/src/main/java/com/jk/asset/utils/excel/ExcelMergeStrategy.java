package com.jk.asset.utils.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.jk.asset.annotation.ExcelMerge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 列值重复合并策略
 *
 * @author Lion Li
 */
@Slf4j
public class ExcelMergeStrategy implements RowWriteHandler {

	private List<?> list;
	private int headRowSize;
	private List<CellRangeAddress> cellList = Lists.newArrayList();

	public ExcelMergeStrategy(List<?> list, int headRowSize) {
		this.list = list;
		this.headRowSize = headRowSize;
	}

	@Override
	public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
		// 判断是否为标题
		if (!isHead || CollectionUtils.isNotEmpty(cellList)) {
			return;
		}
		handle(list, headRowSize);
		if (CollectionUtils.isNotEmpty(cellList)) {
			for (CellRangeAddress item : cellList) {
				writeSheetHolder.getSheet().addMergedRegion(item);
			}
		}
	}

	@SneakyThrows
	private void handle(List<?> list, int headRowSize) {
		cellList.clear();
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		Class<?> clazz = list.get(0).getClass();
		Field[] fields = clazz.getDeclaredFields();
		// 有注解的字段
		List<Field> mergeFields = new ArrayList<>();
		List<Integer> mergeFieldsIndex = new ArrayList<>();
		// 主键
		Field primaryField = null;
		int exportIndex = 0;
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field.isAnnotationPresent(ExcelMerge.class)) {
				ExcelMerge excelMerge = field.getAnnotation(ExcelMerge.class);
				if (primaryField == null && excelMerge.isPrimaryKey()) {
					primaryField = field;
				} else {
					mergeFields.add(field);
					mergeFieldsIndex.add(exportIndex);
				}
			}
			if (field.isAnnotationPresent(ExcelProperty.class)) {
				exportIndex++;
			}
		}
		// 没有指定主键，则异常
		if (null == primaryField) {
			throw new IllegalStateException("使用@ExcelMerge注解必须指定主键");
		}
		// 行合并开始下标
		Map<Field, RepeatCell> map = new HashMap<>();
		// 生成两两合并单元格
		for (int i = 0; i < list.size(); i++) {
			String primaryName = primaryField.getName();
			String primaryMethodName = "get" + primaryName.substring(0, 1).toUpperCase() + primaryName.substring(1);
			Method primaryReadMethod = clazz.getMethod(primaryMethodName);
			Object primaryVal = primaryReadMethod.invoke(list.get(i));

			for (int j = 0; j < mergeFields.size(); j++) {
				Field field = mergeFields.get(j);

				int colNum = mergeFieldsIndex.get(j);
				if (!map.containsKey(field)) {
					map.put(field, new RepeatCell(primaryVal, i));
				} else {
					RepeatCell repeatCell = map.get(field);
					Object cellValue = repeatCell.getValue();
					if (cellValue == null || "".equals(cellValue)) {
						// 空值跳过不合并
						continue;
					}
					// 不相等
					if (!cellValue.equals(primaryVal)) {
						// 上面相等的行数大于1
						if (i - repeatCell.getCurrent() > 1) {
							cellList.add(new CellRangeAddress(repeatCell.getCurrent() + headRowSize, i + headRowSize - 1, colNum, colNum));
						}
						// 更新该属性新的不同内容起始行数
						map.put(field, new RepeatCell(primaryVal, i));
					} else if (i == list.size() - 1) {
						// 最后一行行数大于该属性记录的起始行数，说明有多行是相等的
						if (i > repeatCell.getCurrent()) {
							cellList.add(new CellRangeAddress(repeatCell.getCurrent() + headRowSize, i + headRowSize, colNum, colNum));
						}
					}
				}
			}
		}
	}

	@Data
	@AllArgsConstructor
	static class RepeatCell {

		private Object value;

		private int current;

	}
}
