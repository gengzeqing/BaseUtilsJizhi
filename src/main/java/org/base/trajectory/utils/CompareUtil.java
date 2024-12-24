package org.base.trajectory.utils;

import lombok.extern.slf4j.Slf4j;
import org.base.trajectory.annotation.EnumField;
import org.base.trajectory.annotation.FieldDescription;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 比较工具类
 * 用于比较两个对象的差异
 * @author 耿
 */
@Slf4j
public class CompareUtil {

    /**
     * 比较两个对象的差异
     *
     * @param oldObj 变更前的对象
     * @param newObj 变更后的对象
     * @return 差异记录
     */
    public static List<String> compareObjects(Object oldObj, Object newObj) {
        List<String> changes = new ArrayList<>();

        // 获取对象的字段
        Field[] fields = oldObj.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                // 设置字段可访问
                field.setAccessible(true);

                // 获取字段的值
                Object oldValue = field.get(oldObj);
                Object newValue = field.get(newObj);

                // 判断值是否发生变化
                if ((oldValue == null && newValue != null) || (oldValue != null && !oldValue.equals(newValue))) {
                    // 获取字段注解中的描述
                    FieldDescription annotation = field.getAnnotation(FieldDescription.class);
                    if (annotation != null) {
                        // 如果字段有 EnumField 注解，则进行枚举转换
                        EnumField enumFieldAnnotation = field.getAnnotation(EnumField.class);
                        if (enumFieldAnnotation != null) {
                            Class<?> enumClass = enumFieldAnnotation.enumClass();
                            boolean isDynamic = enumFieldAnnotation.isDynamic();

                            // 根据是否是动态枚举选择不同的处理方式
                            if (isDynamic) {
                                oldValue = convertDynamicEnumValue(enumClass, oldValue);
                                newValue = convertDynamicEnumValue(enumClass, newValue);
                            } else {
                                oldValue = convertEnumValue(enumClass, oldValue);
                                newValue = convertEnumValue(enumClass, newValue);
                            }
                        }

                        oldValue = getObjectValue(oldValue);
                        newValue = getObjectValue(newValue);

                        // 记录变更内容
                        changes.add(String.format("%s: 变更前=%s, 变更后=%s", annotation.value(), oldValue, newValue));
                    }
                }
            } catch (Exception e) {
                log.error("compareObjects: ", e);
            }
        }
        return changes;
    }

    /**
     * 根据字段值和指定的枚举类型转换枚举描述
     *
     * @param enumClass 枚举类型
     * @param value     枚举值
     * @return 对应的枚举描述
     */
    private static String convertEnumValue(Class<?> enumClass, Object value) {
        if (value instanceof Integer) {
            // 尝试转换为枚举
            try {
                // 如果指定的类是枚举类型
                if (enumClass.isEnum()) {
                    // 将整数值转换为枚举实例
                    Object[] enumConstants = enumClass.getEnumConstants();
                    for (Object enumConstant : enumConstants) {
                        // 假设枚举类中有一个名为 getValue 的方法返回整数值
                        if (enumClass.getMethod("getValue").invoke(enumConstant).equals(value)) {
                            return enumClass.getMethod("getDescription").invoke(enumConstant).toString();
                        }
                    }
                }
            } catch (Exception e) {
                log.error("convertEnumValue: ", e);
            }
        }
        // 如果没有找到对应的枚举描述，则返回原始值
        return String.valueOf(value);
    }

    /**
     * 动态枚举值的转换逻辑
     *
     * @param enumClass 枚举类型
     * @param value     枚举值
     * @return 动态枚举描述
     */
    private static String convertDynamicEnumValue(Class<?> enumClass, Object value) {
        // 此处可以增加根据某些动态条件来转换枚举值的逻辑
        // 例如，通过上下文或配置来决定如何转换

        // 目前示范代码中，我们依然可以按照常规的方式进行枚举值转换
        // 在实际中可以做更复杂的映射
        return convertEnumValue(enumClass, value);
    }


    /**
     * 获取对象的值
     *
     * @param obj 转化为 'null' 的情况
     * @return obj
     */
    private static String getObjectValue(Object obj) {
        // 如果对象为空或者对象的值为"null"，则返回空字符串
        return obj == null || Objects.equals(obj, "null") ? "" : obj.toString();
    }
}
