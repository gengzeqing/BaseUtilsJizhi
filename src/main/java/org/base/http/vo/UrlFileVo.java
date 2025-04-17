package org.base.http.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;
import org.apache.ibatis.type.Alias;
import org.base.utils.PropertiesUtil;
import org.base.utils.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
@Alias("urlFile")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlFileVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileId;
    private String fileUrl;

    public UrlFileVo(String fileId, String fileUrl) {
        this.fileId = fileId;
        this.fileUrl = fileUrl;
    }

    public UrlFileVo() {
    }

    /**
     * 获取完整URL 链接 以及 fileId
     * @param fileIds
     * @return
     */
    public static List<UrlFileVo> getFileList(String fileIds) {
        if(StringUtils.isEmpty(fileIds)) {
            return null;
        }
        List<UrlFileVo> list = Lists.newArrayList();
        String url = PropertiesUtil.getDomain() + "/image/";
        if (StringUtils.isNotEmpty(fileIds)) {
            String[] fileArray = fileIds.split(",");
            Arrays.stream(fileArray).forEach(fileId -> list.add(new UrlFileVo(fileId, url + fileId)));
        }
        return list;
    }

    /**
     * 获取完整URL 链接
     * @param fileId
     * @return
     */
    public static String getFileUrl(String fileId) {
        String url = null;
        if (StringUtils.isNotEmpty(fileId)) {
            url = PropertiesUtil.getDomain() + "/image/" + fileId;
        }
        return url;
    }
}
