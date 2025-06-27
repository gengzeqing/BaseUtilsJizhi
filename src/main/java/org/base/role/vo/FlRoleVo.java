package org.base.role.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@TableName("fl_role")
@Alias("role_")
public class FlRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String remark;

    /**
     * 状态 0：删除，1：正常
     */
    private Integer status;

    /**
     * 状态时间
     */
    private Integer statusTime;

    /**
     * 排序
     */
    private Integer index;

    /**
     * 序号
     */
    private Integer sequence;

    /**
     * 1参与审批流
     */
    private Integer type;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 创建时间
     */
    private Integer ctime;

    /**
     * 最后修改人
     */
    private Integer modifyId;

    /**
     * 最后修改时间
     */
    private Integer mtime;

    /**
     * 公司ID
     */
    private Integer partnerId;

    /**
     * 子管理员配置表id
     */
    private Integer administratorId;
}
