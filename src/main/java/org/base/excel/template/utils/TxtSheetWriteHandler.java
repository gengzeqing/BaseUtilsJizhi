package org.base.excel.template.utils;


import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;

import java.util.Map;

/**
 * @author 耿
 */
@Data
@AllArgsConstructor
public class TxtSheetWriteHandler implements SheetWriteHandler {


    private final Map<Integer, ExcelTxtResolve> excelTxtResolveMap;


    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        Sheet sheet = writeSheetHolder.getSheet();
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        // 创建单元格样式
        CellStyle textStyle = workbook.createCellStyle();
        // 设置单元格数据格式为文本
        DataFormat format = workbook.createDataFormat();
        // "@" 表示文本格式
        textStyle.setDataFormat(format.getFormat("@"));
        // 创建单元格样式
        excelTxtResolveMap.forEach((k, v) -> {
            sheet.setDefaultColumnStyle(k,textStyle);
        });
    }
}