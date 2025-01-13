package org.base.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.base.mybatis.utils.Utils;
import org.base.mybatis.vo.WorkflowChannelApplyNodeVo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Main implements IService<WorkflowChannelApplyNodeVo> {
    public static void main(String[] args) {

        new Main().test();

    }

    /**
     * 建立子查询

     * SELECT
     * *
     * FROM
     * tb_workflow_apply_node
     * WHERE
     * ( id IN ( SELECT MAX( ID ) FROM TB_WORKFLOW_APPLY_NODE WHERE ( APPLY_ID = 58 AND STATUS = '测试' ) ) )
     *
     */
    public void test() {
        String name = null;
        QueryWrapper<WorkflowChannelApplyNodeVo> select = new QueryWrapper<WorkflowChannelApplyNodeVo>().
                eq("APPLY_ID", "58").
                eq(name != null, "LEVEL_NODE", name).
                eq("STATUS", "测试").
                select("select MAX(ID) FROM TB_WORKFLOW_APPLY_NODE ");
        String sql =  Utils.replaceSqlPlaceholders(select);
        System.out.println(sql);

        /**
         * 可以建立子查询
         */
        List<WorkflowChannelApplyNodeVo> workflowChannelApplyNodeVos = this.getBaseMapper().selectList(
                new LambdaQueryWrapper<WorkflowChannelApplyNodeVo>()
                        .inSql(WorkflowChannelApplyNodeVo::getId, sql));
    }


    @Override
    public boolean saveBatch(Collection<WorkflowChannelApplyNodeVo> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<WorkflowChannelApplyNodeVo> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<WorkflowChannelApplyNodeVo> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(WorkflowChannelApplyNodeVo entity) {
        return false;
    }

    @Override
    public WorkflowChannelApplyNodeVo getOne(Wrapper<WorkflowChannelApplyNodeVo> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<WorkflowChannelApplyNodeVo> queryWrapper) {
        return Collections.emptyMap();
    }

    @Override
    public <V> V getObj(Wrapper<WorkflowChannelApplyNodeVo> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<WorkflowChannelApplyNodeVo> getBaseMapper() {
        return null;
    }

    @Override
    public Class<WorkflowChannelApplyNodeVo> getEntityClass() {
        return null;
    }
}
