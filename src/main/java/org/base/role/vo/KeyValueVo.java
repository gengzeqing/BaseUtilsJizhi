package org.base.role.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyValueVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer code;
    private String key;
    private String name;
    private String value;
    private String menuCode;
    private String menuName;

    public KeyValueVo(Integer code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

    public KeyValueVo(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public KeyValueVo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValueVo() {
    }
}
