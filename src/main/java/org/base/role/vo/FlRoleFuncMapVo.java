package org.base.role.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@TableName("fl_role_func_map")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlRoleFuncMapVo extends BaseVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer roleId;

    /**
     * 功能ID
     */
    private Integer funcId;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 是否选中0否
     */
    private Integer checked;

    /**
     * 修改人
     */
    private Integer modifyId;

    /**
     * 修改时间戳
     */
    private Long mtime;

}
