package org.base.role.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.base.BaseServiceImpl;
import org.base.error.ParamException;
import org.base.role.mapper.RoleMapper;
import org.base.role.service.FlRoleFuncMapService;
import org.base.role.service.RoleService;
import org.base.role.service.UserRoleMapService;
import org.base.role.vo.FlRoleFuncMapVo;
import org.base.role.vo.FlUserRoleMapVo;
import org.base.role.vo.FunctionVo;
import org.base.role.vo.RoleVo;
import org.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wh
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, RoleVo> implements RoleService {

    private final FlRoleFuncMapService flRoleFuncMapService;
    private final UserRoleMapService flUserRoleMapService;

    @Override
    public void createRole(RoleVo vo) {
        RoleVo roleVo = new RoleVo();
        roleVo.setName(vo.getName());
        roleVo.setRemark(vo.getRemark());
        roleVo.setCtime(vo.getMtime());
        roleVo.setMtime(vo.getMtime());
        roleVo.setRoleType(1);
        this.baseMapper.insert(roleVo);
        //转化json数据
        List<FunctionVo> functionList = JSON.parseArray(vo.getData(), FunctionVo.class);
        if (!CollectionUtils.isEmpty(functionList)) {
            //保存角色-相对应的功能
            List<FlRoleFuncMapVo> list = Lists.newArrayList();
            for (FunctionVo functionVo : functionList) {
                FlRoleFuncMapVo roleMap = new FlRoleFuncMapVo();
                roleMap.setRoleId(roleVo.getId());
                roleMap.setCtime(vo.getMtime());
                roleMap.setMtime(vo.getMtime());
                roleMap.setFuncId(functionVo.getId());
                roleMap.setChecked(functionVo.getChecked());
                list.add(roleMap);
            }
            flRoleFuncMapService.saveBatch(list);
        }
    }


    @Override
    public Map<String, Object> toUpdateRole(Integer roleId) {
        Map<String, Object> map = new HashMap<>();
        List<FunctionVo> functionTree = getFunctionTree(roleId);
        RoleVo role = this.getById(roleId);
        map.put("dataFunction", functionTree);
        map.put("dataDetail", role);
        return map;
    }

    public List<FunctionVo> getFunctionTree(Integer roleId) {
        List<FunctionVo> functionTree = this.baseMapper.getFunctionTree(roleId);
        return createTree(functionTree);
    }

    /**
     * @Desciption: 私有方法，构建功能树
     * @author lyl (lyl@163.com) 2021年3月11日
     * @version V1.0
     */
    private List<FunctionVo> createTree(List<FunctionVo> functions) {
        if (null == functions || functions.size() == 0) {
            return new ArrayList<FunctionVo>();
        }
        List<FunctionVo> result = new ArrayList<FunctionVo>();
        for (FunctionVo each : functions) {
            int id = each.getId();
            List<FunctionVo> children = new ArrayList<FunctionVo>();
            if (each.getChildren() == null || each.getChildren().size() == 0) {
                each.setChildren(children);
            }
            for (FunctionVo inner : functions) {
                if (id == inner.getParentId()) {
                    each.getChildren().add(inner);
                }
            }

            // 第1级展开
            if (each.getLevelType() <= 1) {
                each.setOpen(true);
                result.add(each);
            }
            each = null;
        }
        return result;
    }


    @Override
    public void updateRole(RoleVo vo) {
        if (StringUtils.isEmpty(vo.getData()) || vo.getData().equals("[]")) {
            throw new ParamException("数据格式错误");
        }
        this.updateById(vo);
        // 删除原来的功能权限
        Map<String, Object> map = Maps.newHashMap();
        map.put("role_id", vo.getId().toString());
        flRoleFuncMapService.getBaseMapper().deleteByMap(map);
        //转化json数据
        List<FunctionVo> functionList = JSON.parseArray(vo.getData(), FunctionVo.class);
        if (!CollectionUtils.isEmpty(functionList)) {
            //保存角色-相对应的功能
            List<FlRoleFuncMapVo> list = Lists.newArrayList();
            for (FunctionVo functionVo : functionList) {
                FlRoleFuncMapVo roleMap = new FlRoleFuncMapVo();
                roleMap.setRoleId(vo.getId());
                roleMap.setCtime(vo.getMtime());
                roleMap.setMtime(vo.getMtime());
                roleMap.setFuncId(functionVo.getId());
                roleMap.setChecked(functionVo.getChecked());
                list.add(roleMap);
            }
            flRoleFuncMapService.saveBatch(list);
        }
        QueryWrapper<FlUserRoleMapVo> wrapper = new QueryWrapper<>();
        ;
        wrapper.select("LISTAGG(user_id,',') as userIds").eq("role_id", vo.getId());
        FlUserRoleMapVo flUserRoleMapVo = flUserRoleMapService.getBaseMapper().selectOne(wrapper);
        if (flUserRoleMapVo != null && StringUtils.isNotEmpty(flUserRoleMapVo.getUserIds())) {
            String[] split = flUserRoleMapVo.getUserIds().split(",");
            for (String userId : split) {
                // 这一步主要是拦截器里面用，角色的功能权限被改变，用户强制下线
                //redisService.set("user_web_" + userId, null, 24 * 7, 1);
            }
        }
    }

}
