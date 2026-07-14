package com.jk.asset.utils.excel;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangTao
 * date2024/8/29 17:14
 **/


public class DecimalWriteHandler implements CellWriteHandler {

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        for (WriteCellData<?> writeCellData : cellDataList) {
           if (writeCellData.getType() == CellDataTypeEnum.NUMBER && cell.getCellType() == CellType.NUMERIC){
                   double doubleValue = cell.getNumericCellValue();
                   BigDecimal bigDecimalValue = BigDecimal.valueOf(doubleValue);
                   if (bigDecimalValue.compareTo(BigDecimal.ZERO) == 0) {
                       cell.setCellValue((String) null);
                   } else {
                       cell.setCellValue(bigDecimalValue.toString());
                   }
           }
        }
    }
}

