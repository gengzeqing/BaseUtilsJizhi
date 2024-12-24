package org.base.excel.template.annotation;


import java.lang.annotation.*;

/**
 * 标注导出的列为时间类型，并验证内容是否合法
 * @author 耿
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelDate {

    /**
     * 验证的时间格式
     */
    String sourceFormat() default "yyyy-MM-dd";

    /**
     * 设置下拉框的起始行，默认为第二行
     */
    int firstRow() default 1;

    /**
     * 设置下拉框的结束行，默认为最后一行
     */
    //int lastRow() default 0x10000;
    int lastRow() default 10000;
}