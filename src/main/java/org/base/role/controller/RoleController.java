
package org.base.role.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.base.error.ParamException;
import org.base.role.service.RoleService;
import org.base.role.vo.BaseParam;
import org.base.role.vo.RoleVo;
import org.base.role.vo.StringToIntegerEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class RoleController {

    private final RoleService roleService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new StringToIntegerEditor());
    }

    /**
     * 角色新建
     *{
     * 	"data": "[{\"id\":1167,\"checked\":1},{\"id\":1040,\"checked\":0},{\"id\":1166,\"checked\":0}]",
     * 	"id": 11,
     * 	"mtime": 1751012395,
     * 	"name": "张跑跑",
     * 	"partnerId": 0,
     * 	"remark": "测试数据-角色描述"
     * }
     * data 是功能权限的信息 id是具体的功能、checked 是否勾选。0 没勾选，但是他的下级勾选了， 1被勾选
     * checked 是否选中
     * -1：未选中，0：半选（针对下级部分选中时，上级设置为半选），1：选中
     *
     * @param vo
     * @return
     */
    @PostMapping(value = "/api/role/create")
    public void createRole(@Validated(BaseParam.add.class) RoleVo vo) {
        roleService.createRole(vo);
    }

    /**
     * @value: 角色编辑跳转
     * @version V1.0
     */
    @PostMapping("/api/role/to/update")
    public void toUpdateRole(Integer roleId) {
        roleService.toUpdateRole(roleId);
    }

    /**
     * 角色编辑
     * @param vo
     * @return
     */
    @PostMapping("/api/role/update")
    public void updateRole(@Validated(BaseParam.edit.class) RoleVo vo) {
        if (vo == null || vo.getId() == null) {
            throw new ParamException("角色Id不能为空");
        }
        roleService.updateRole(vo);
    }
}

