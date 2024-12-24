package org.base.utils;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author 耿
 * @version 1.0
 * @description 数据处理工具类
 */
public class DataUtil {

    /**
     * 判断数据 是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(@Nullable Object obj) {
        // 判断obj是否为null
        if (obj == null) {
            return true;
        }
        // 如果是Optional类型，判断是否为空
        if (obj instanceof Optional<?>) {
            return !((Optional<?>) obj).isPresent();
        }
        // 如果是CharSequence类型（如String），判断长度是否为0
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        // 如果是数组类型，判断数组长度是否为0
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        // 如果是Collection类型，判断集合是否为空
        if (obj instanceof Collection<?>) {
            return ((Collection<?>) obj).isEmpty();
        }
        // 如果是Map类型，判断Map是否为空
        if (obj instanceof Map<?, ?>) {
            return ((Map<?, ?>) obj).isEmpty();
        }
        // 默认情况：如果没有匹配的类型，则返回false
        return false;
    }


}
