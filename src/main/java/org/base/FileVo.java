package org.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("tb_archives_work_order_file")
public class FileVo {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String  objectId;
    private String  fileName;
    private Integer fileType;
    private String  url;
    private String  thumbnail;
    /**
     * 1图片  2视频  3附件
     */
    private Integer uploadType;
    @TableField(exist = false)
    private List<FileVo> fileList;
    @TableField(exist = false)
    private String  fileId;
    private Integer workOrderId;
    private Integer archivesId;
}
