package org.base.role.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author wh
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Alias("function_")
@TableName("fl_function")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FunctionVo extends BaseVo {

    private Integer id;
    @TableField(exist = false)
    private Integer roleId;
    private String name;
    private Integer levelType;
    private Integer parentId;
    private String description;
    private Integer status;
    @TableField(exist = false)
    private Integer funcId;
    /**
     * 是否选中
     * -1：未选中，0：半选（针对下级部分选中时，上级设置为半选），1：选中
     */
    private Integer checked;

    /**
     * 层级是否展开
     */
    @TableField(exist = false)
    private Boolean open = false;
    @TableField(exist = false)
    private List<FunctionVo> children;
}
