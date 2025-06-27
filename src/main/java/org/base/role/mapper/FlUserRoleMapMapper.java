package org.base.role.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.base.role.vo.FlRoleVo;
import org.base.role.vo.FlUserRoleMapVo;

import java.util.List;

@Mapper
public interface FlUserRoleMapMapper extends BaseMapper<FlUserRoleMapVo> {

    List<FlRoleVo> getUserRole(@Param("userId")Integer userId);
}
