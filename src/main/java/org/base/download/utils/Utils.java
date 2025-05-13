package org.base.download.utils;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

    public static ServletOutputStream getImageResponse(HttpServletResponse response, String fileName) throws IOException {
        // 统一字符编码防止文件名乱码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // 根据文件扩展名动态设置MIME类型
        String fileExtension = FilenameUtils.getExtension(fileName).toLowerCase();
        response.setContentType("application/octet-stream");
        // 设置下载头（attachment）或预览头（inline）
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString())
                .replaceAll("\\+", "%20"); // 处理空格编码问题
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + encodedFileName + "\";" +
                        "filename*=utf-8''" + encodedFileName);
        // 禁用缓存确保最新文件
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");

        return response.getOutputStream();
    }


}
