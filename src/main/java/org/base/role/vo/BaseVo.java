package org.base.role.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体的抽象实现
 */
@Data
public abstract class BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer createId;
    private Integer modifyId;
    private Long ctime ;
    private Long mtime = SystemClock.now()/1000;
}