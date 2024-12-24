package org.base.excel.template.utils;

import com.alibaba.excel.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.base.excel.template.annotation.ExcelDate;

/**
 * @author 耿
 */
@Data
@Slf4j
public class ExcelDateResolve {
    /**
     * 验证的时间格式
     */
    String sourceFormat;

    /**
     * 设置下拉框的起始行，默认为第二行
     */
    private int firstRow;

    /**
     * 设置下拉框的结束行，默认为最后一行
     */
    private int lastRow;

    public String resolveSelectedSource(ExcelDate excelDate) {
        if (excelDate == null) {
            return null;
        }

        String sourceFormat = excelDate.sourceFormat();
        if (StringUtils.isNotBlank(sourceFormat)) {
            return sourceFormat;
        }
        return null;
    }

}
