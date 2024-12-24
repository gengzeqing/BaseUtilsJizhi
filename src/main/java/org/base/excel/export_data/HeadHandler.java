package org.base.excel.export_data;


import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HeadHandler implements CellWriteHandler {

    private ExportEntity exportEntity;


    public HeadHandler(ExportEntity exportEntity) {
        this.exportEntity = exportEntity;
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
                                 List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (!isHead) {
            return;
        }
        List<String> headNameList = head.getHeadNameList();
        Map<String, String> collect = exportEntity.getExportParams().stream().collect(Collectors.toMap(ExportParam::getFieldName, ExportParam::getColumnName));
        cell.setCellValue(collect.get(headNameList.get(0)));
    }
}
