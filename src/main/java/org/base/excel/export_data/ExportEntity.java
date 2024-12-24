package org.base.excel.export_data;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ExportEntity {

    /**
     * 导出参数
     */
    private List<ExportParam> exportParams;

    /**
     * 平铺后的数据
     */
    private List<Map<String,Object>> data;

    /**
     * 每列的合并行数组
     */
    private Map<String, List<Integer[]>> mergeRowMap;
}