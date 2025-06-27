package org.base.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.base.role.mapper.FlUserRoleMapMapper;
import org.base.role.service.UserRoleMapService;
import org.base.role.vo.FlRoleVo;
import org.base.role.vo.FlUserRoleMapVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author geng
 * @since 2024-07-10
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleMapServiceImpl extends ServiceImpl<FlUserRoleMapMapper, FlUserRoleMapVo> implements UserRoleMapService {

    private final FlUserRoleMapMapper flUserRoleMapMapper;
    @Override
    public List<FlRoleVo> getUserRole(Integer userId) {
        return flUserRoleMapMapper.getUserRole(userId);
    }
}
