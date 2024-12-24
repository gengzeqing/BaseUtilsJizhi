package org.base.excel.template.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 耿
 */
@Data
@Slf4j
public class ExcelTxtResolve {

    /**
     * 默认为第二行
     */
    private int firstRow;

    /**
     * 设置结束行，默认为最后一行
     */
    private int lastRow;

}
