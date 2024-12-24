package org.base.trajectory.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * @author 耿
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)  // 该注解只能用于字段
public @interface FieldDescription {

    String value();  // 用于描述字段的含义
    
}

