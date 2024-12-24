package org.base.excel.export_data.annotation;

import java.lang.annotation.*;

/**
 * 用于描述导出的文件位置
 * @author 耿
 */
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyExcelProperty {
    /**
     * 导出的表头名称
     */
    String name() default "";

    /**
     * 导出文件的位置 （下标）
     */
    int index() default Integer.MAX_VALUE;

}