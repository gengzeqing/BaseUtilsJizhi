package org.base.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.base.role.vo.FlRoleVo;
import org.base.role.vo.FlUserRoleMapVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author geng
 * @since 2024-07-10
 */
public interface UserRoleMapService extends IService<FlUserRoleMapVo> {

    List<FlRoleVo> getUserRole(Integer userId);
}
