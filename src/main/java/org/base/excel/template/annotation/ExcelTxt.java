package org.base.excel.template.annotation;


import java.lang.annotation.*;

/**
 * 标注导出的列为文本类型
 * @author 耿
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelTxt {

    /**
     * 设置下拉框的起始行，默认为第二行
     */
    int firstRow() default 1;

    /**
     * 设置下拉框的结束行，默认为最后一行
     */
    //int lastRow() default 0x10000;
    int lastRow() default 0x10000;
}