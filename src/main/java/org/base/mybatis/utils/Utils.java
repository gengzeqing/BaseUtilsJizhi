package org.base.mybatis.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;
import java.util.Objects;

public class Utils {


    /**
     * QueryWrapper<WorkflowChannelApplyNodeVo> select = new QueryWrapper<WorkflowChannelApplyNodeVo>().
     *                 eq("APPLY_ID", appId).
     *                 eq(name !=null ,"LEVEL_NODE", name).
     *                 eq("STATUS", ChannelWorkEnum.WorkStatus.PASSED.getKey()).
     *                 select("select MAX(ID) FROM TB_WORKFLOW_APPLY_NODE ");
     * Map<String, Object> paramNameValuePairs = select.getParamNameValuePairs();
     * String sqlSegment = select.getCustomSqlSegment();
     * String sqlSelect = select.getSqlSelect();
     * @param sqlSegment 条件字段
     * @param paramNameValuePairs value 为空的数据 mybatisplus 已自动过滤掉,需要条件中添加 不为空的判断 如：eq(name !=null ,"LEVEL_NODE", name)
     * @return
     */
/*    public static String replaceSqlPlaceholders(String sqlSegment, Map<String, Object> paramNameValuePairs) {
        if (sqlSegment == null || paramNameValuePairs == null || paramNameValuePairs.isEmpty()) {
            return sqlSegment;
        }

        // 使用 StringBuilder 来提高性能，避免频繁创建新的 String 对象
        StringBuilder result = new StringBuilder(sqlSegment);

        // 遍历 paramNameValuePairs，替换 SQL 中的占位符
        for (Map.Entry<String, Object> entry : paramNameValuePairs.entrySet()) {
            String paramName = entry.getKey(); // 获取参数名（例如：MPGENVAL1）
            Object paramValue = entry.getValue(); // 获取参数值（例如：13561）

            // 如果参数值为 null，决定是否跳过或设置默认值
            if (Objects.isNull(paramValue) || paramValue.toString().isEmpty() || paramValue.toString().equals("null")) {
                continue; // 跳过此占位符替换，或者可以替换为默认值
            }

            // 构建占位符的格式
            String placeholder = "#{ew.paramNameValuePairs." + paramName + "}";

            // 替换 SQL 中的占位符为实际的值
            int startIndex = result.indexOf(placeholder);
            while (startIndex != -1) {
                // 替换占位符
                result.replace(startIndex, startIndex + placeholder.length(), paramValue.toString());
                // 查找下一个占位符
                startIndex = result.indexOf(placeholder, startIndex + paramValue.toString().length());
            }
        }
        return result.toString();
    }*/


    /**
     * QueryWrapper<WorkflowChannelApplyNodeVo> select = new QueryWrapper<WorkflowChannelApplyNodeVo>().
     *                 eq("APPLY_ID", appId).
     *                 eq(name !=null ,"LEVEL_NODE", name).
     *                 eq("STATUS", ChannelWorkEnum.WorkStatus.PASSED.getKey()).
     *                 select("select MAX(ID) FROM TB_WORKFLOW_APPLY_NODE ");
     * Map<String, Object> paramNameValuePairs = select.getParamNameValuePairs();
     * String sqlSegment = select.getCustomSqlSegment();
     * String sqlSelect = select.getSqlSelect();
     *  sqlSegment 条件字段
     *  paramNameValuePairs value 为空的数据 mybatisplus 已自动过滤掉,需要条件中添加 不为空的判断 如：eq(name !=null ,"LEVEL_NODE", name)
     * @return
     */
    public static String replaceSqlPlaceholders(QueryWrapper<?> queryWrapper) {
        if (queryWrapper == null) {
            return null;
        }

        Map<String, Object> paramNameValuePairs = queryWrapper.getParamNameValuePairs();
        String sqlSegment = queryWrapper.getCustomSqlSegment();


        // 使用 StringBuilder 来提高性能，避免频繁创建新的 String 对象
        StringBuilder result = new StringBuilder(sqlSegment);

        // 遍历 paramNameValuePairs，替换 SQL 中的占位符
        for (Map.Entry<String, Object> entry : paramNameValuePairs.entrySet()) {
            String paramName = entry.getKey(); // 获取参数名（例如：MPGENVAL1）
            Object paramValue = entry.getValue(); // 获取参数值（例如：13561）

            // 如果参数值为 null，决定是否跳过或设置默认值
            if (Objects.isNull(paramValue) || paramValue.toString().isEmpty() || paramValue.toString().equals("null")) {
                continue; // 跳过此占位符替换，或者可以替换为默认值
            }

            // 构建占位符的格式
            String placeholder = "#{ew.paramNameValuePairs." + paramName + "}";

            // 替换 SQL 中的占位符为实际的值
            int startIndex = result.indexOf(placeholder);
            while (startIndex != -1) {
                // 替换占位符
                result.replace(startIndex, startIndex + placeholder.length(), paramValue.toString());
                // 查找下一个占位符
                startIndex = result.indexOf(placeholder, startIndex + paramValue.toString().length());
            }
        }
        return queryWrapper.getSqlSelect() + result.toString();
    }

}
