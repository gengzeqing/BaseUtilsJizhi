package org.base.http.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 耿
 */
@Data
public class OaFileVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileId;
    private String fdKey;
    private String fdFileName;
    private byte[] fdAttachment;

}
