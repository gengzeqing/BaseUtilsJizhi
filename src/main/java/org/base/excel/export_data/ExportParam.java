package org.base.excel.export_data;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 耿
 */
@Data
@Accessors(chain = true)
public class ExportParam {
    private String fieldName;
    private String columnName;
    private Integer order;
}