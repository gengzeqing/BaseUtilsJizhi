package org.base.download.vo;

import lombok.Data;

/**
 * @author 耿
 */
@Data
public class ImageData {
    private String processNumber;
    private String partnerName;
    private byte[] data;
    private String fileExtension;
    private String count;

    // 构造方法
    public ImageData(String processNumber, String partnerName, byte[] data, String fileExtension, String count) {
        this.processNumber = processNumber;
        this.partnerName = partnerName;
        this.data = data;
        this.fileExtension = fileExtension;
        this.count = count;
    }
}
