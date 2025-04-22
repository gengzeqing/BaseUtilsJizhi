package org.base.download.utils;

import lombok.SneakyThrows;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author 耿
 */
public class Utils {


    /**
     * 导出excel 的表头配置
     *
     * @param response
     * @param fileName
     * @return
     */
    @SneakyThrows
    public static ServletOutputStream getResponse(HttpServletResponse response, String fileName) {
        // 保证下载到本地文件名不乱码
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setContentType("application/octet-stream; charset=UTF-8");
        String fileNameURL = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);
        ServletOutputStream outputStream = response.getOutputStream();
        return outputStream;
    }

    // 获取 URL 中的文件扩展名
    public static String getUrlFileType(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return "";
        }
        int dotIndex = fileUrl.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileUrl.length()) {
            return fileUrl.substring(dotIndex + 1);
        }
        return "";
    }


}
