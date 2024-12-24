package org.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础Service接口实现类
 *
 * @author wh
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {


    protected Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

    protected String ip;


    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        super.update(entity, updateWrapper);
       /* if (!super.update(entity, updateWrapper)) {
            throw new VersionException("数据已过期");
        }*/
        return true;
    }


}
