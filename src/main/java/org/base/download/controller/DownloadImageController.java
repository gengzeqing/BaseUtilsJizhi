package org.base.download.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 *
 * 通过一个URL 连接下载图片
 * @author 耿
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class DownloadImageController {


    /**
     * 通过一个URL 连接下载图片
     * @param response
     */
    @SneakyThrows
    @RequestMapping(value = "/download2")
    public void templateIo(HttpServletResponse response) {
        String path = "https://img13.360buyimg.com/n1/s450x450_jfs/t28570/85/1099414327/106024/8393f1/5c064302N617e6f1c.jpg";
        String urlFileType = getUrlFileType(path);
        String fileName = "下载文件"+"."+urlFileType;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            // 快速失败
            conn.setConnectTimeout(3000);
            // 10秒读取超时
            conn.setReadTimeout(10000);
            // 自动重定向
            conn.setInstanceFollowRedirects(true);

            // 验证HTTP状态码
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "远程服务器响应异常: " + responseCode);
                return;
            }
            // 设置响应头
            response.setContentType("application/octet-stream");
            // 恢复加号
            String encodedName = URLEncoder.encode(fileName, "UTF-8")
                    .replace("+", "%20").replace("%2B", "+");

            String contentDisposition = "attachment; filename=\"" + encodedName + "\"" + "; filename*=UTF-8''" + encodedName;
            response.setHeader("Content-Disposition", contentDisposition);

            // 高效传输（Java 8优化版）
            try (InputStream rawIn = conn.getInputStream();
                 BufferedInputStream in = new BufferedInputStream(rawIn, 32768);
                 OutputStream rawOut = response.getOutputStream();
                 BufferedOutputStream out = new BufferedOutputStream(rawOut, 32768)) {

                // 32KB缓冲区
                byte[] buffer = new byte[32768];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.flush(); // 确保最后缓冲数据写出
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "下载失败: " + e.getClass().getSimpleName());
        } finally {
            if (conn != null) {
                conn.disconnect(); // 确保释放连接
            }
        }
    }


    /**
     * 大数据量的文件 NIO 零拷贝传输
     * @param response
     */
    @SneakyThrows
    public void templateNio(HttpServletResponse response) {
        String path = "https://img13.360buyimg.com/n1/s450x450_jfs/t28570/85/1099414327/106024/8393f1/5c064302N617e6f1c.jpg";
        String urlFileType = getUrlFileType(path);
        String fileName = "下载文件"+"."+urlFileType;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);

            // 设置响应头
            response.setContentType("application/octet-stream");
            String encodedName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedName + "\"");
            response.setContentLengthLong(conn.getContentLengthLong());

            // 使用NIO零拷贝传输
            try (ReadableByteChannel inChannel = Channels.newChannel(conn.getInputStream());
                 WritableByteChannel outChannel = Channels.newChannel(response.getOutputStream())) {

                // 直接内存缓冲区  32KB
                ByteBuffer buffer = ByteBuffer.allocateDirect(32768);
                while (inChannel.read(buffer) != -1) {
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                }
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "下载失败: " + e.getMessage());
        }
    }

    // 获取 URL 中的文件扩展名
    private static String getUrlFileType(String fileUrl) {
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
