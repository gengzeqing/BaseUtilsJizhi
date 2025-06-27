package org.base.role.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用基础参数，相关实体参数校验可继承此类
 */
@Data
public class BaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 参数校验分组：分页
     */
    public @interface pageList {
    }

    /**
     * 参数校验分组：列表
     */
    public @interface list {
    }

    /**
     * 参数校验分组：增加
     */
    public @interface add {
    }

    /**
     * 参数校验分组：草稿
     */
    public @interface draft {
    }

    /**
     * 参数校验分组：编辑
     */
    public @interface edit {
    }

    /**
     * 参数校验分组：更新信息
     */
    public @interface update {
    }

    /**
     * 参数校验分组：删除
     */
    public @interface delete {
    }

    /**
     * 参数校验分组：详情
     */
    public @interface detail {
    }

    /**
     * 参数校验分组：导出
     */
    public @interface export {
    }

    /**
     * 参数校验：附件提交
     */
    public @interface submitFile {
    }


    /**
     * 参数校验：查询
     */
    public @interface query {
    }

    /**
     * 参数校验：合作商信息汇总变更
     */
    public @interface partnerUpdate {
    }

}
