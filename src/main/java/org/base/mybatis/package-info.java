package org.base.mybatis;
/**
 * mybatisplus 建立 子查询
 *
 *  QueryWrapper<WorkflowChannelApplyNodeVo> select = new QueryWrapper<WorkflowChannelApplyNodeVo>()
 *  select.getParamNameValuePairs() 获取所有的构造参数条件 条件的VALUE值
 *  select.getCustomSqlSegment()    完整的构造条件 包含 where  如：WHERE (APPLY_ID = #{ew.paramNameValuePairs.MPGENVAL1})
 *  select.getSqlSelect()           获取指定的select 参数 如：select MAX(ID) FROM TB_WORKFLOW_APPLY_NODE
 *
 *  mybatisplus 的 inSql 方法，可以建立子查询 （适用于有多个返回值的情况，如果能确认只有一个返回的参数可以使用eq）
 *
 *  Utils.replaceSqlPlaceholders(sqlSegment, paramNameValuePairs)  替换sql 中的参数
 *
 */