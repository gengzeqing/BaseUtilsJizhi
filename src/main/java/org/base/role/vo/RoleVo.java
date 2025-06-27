package org.base.role.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.*;
import java.util.List;

@Data
@TableName("fl_role")
@Alias("role")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVo extends BaseVo {

    @NotNull(message = "名称不能为空",groups = {BaseParam.edit.class,BaseParam.add.class})
    @Size(min = 1, max =20,message = "名称长度1-20")
    private String name;
    @Size(min = 0, max =50,message = "名称长度50")
    private String remark;

    @TableField(exist = false)
    private String data;
    @TableField(exist = false)
    private Integer userId;
    @TableField(exist = false)
    private Integer roleId;
    private Integer partnerId;
    private Integer roleType;//角色类型  1瑞福德角色  2合作商角色

    /**
     * 功能权限
     */
    @TableField(exist = false)
    private List<FunctionVo> functionVOs;
}
