package org.base.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.base.role.vo.FunctionVo;
import org.base.role.vo.KeyValueVo;
import org.base.role.vo.RoleVo;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<RoleVo> {

    List<FunctionVo> getFunctionTree(@Param("roleId") Integer roleId);

    /* 查询用户配置的权限  */
    List<KeyValueVo> getMenuList(@Param("userId") Integer id, @Param("userType") Integer userType);

    /* 管理员查询所有的权限数据 */
    List<KeyValueVo> allCode();

    /* 查询角色关联的 用户 */
    List<Integer> selectEmpIdByRoleCode(@Param("roleId")Integer roleId);
}
