package org.base.role.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("fl_user_role_map")
public class FlUserRoleMapVo extends BaseVo {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;
    private Integer createId;
    private Integer modifyId;
    private Long ctime;
    private Long mtime;
    private Integer partnerId;

    @TableField(exist = false)
    private String userIds;


}
