package org.base.error.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自定义注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    String bindParam() default "";  // 绑定参数名，默认为空
    String taskId() default "";      // 指定需要提取的字段名，默认为空
    String methodName() default "";      // 指定需要提取的字段名，默认为空
}