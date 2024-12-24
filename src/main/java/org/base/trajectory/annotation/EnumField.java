package org.base.trajectory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 耿
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumField {

    Class<?> enumClass();  // 指定枚举类型

    boolean isDynamic() default false;  // 标记是否是动态枚举（在不同上下文中映射不同）

}
