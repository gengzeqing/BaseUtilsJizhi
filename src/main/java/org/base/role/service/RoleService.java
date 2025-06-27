
package org.base.role.service;

import org.base.BaseService;
import org.base.role.vo.RoleVo;

import java.util.Map;

public interface RoleService extends BaseService<RoleVo> {

    /**
     * 创建角色
     *
     * @param vo
     */
    void createRole(RoleVo vo);

    /**
     * 角色关联功能权限详情
     *
     * @param roleId
     * @return
     */
    Map<String, Object> toUpdateRole(Integer roleId);

    /**
     * 编辑角色
     *
     * @param vo
     */
    void updateRole(RoleVo vo);

}

