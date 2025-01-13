package org.base.video;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
public class VideoController {

    private static final String BYTE_RANGE_PREFIX = "bytes=";

    private static final String VIDEO_PATH = "D:/ceshi/abc.mp4";  // 视频文件路径

    @GetMapping("/video")
    public ResponseEntity<Resource> streamVideo(@RequestHeader(value = HttpHeaders.RANGE, required = false) String range, HttpServletResponse response) {
        System.out.println("请求数据:"+range);
        if (StringUtils.hasLength(range)) {
            String rangeStart = range.substring(BYTE_RANGE_PREFIX.length());
            if (StringUtils.hasLength(rangeStart)) {
                String[] split = rangeStart.split("-");  // 分割起始和结束位置
                // 如果只有一个数字，说明没有请求截至大小，则设置为1048576
                if (split.length == 1) {
                    range = BYTE_RANGE_PREFIX + split[0] +"-"+new BigInteger(split[0]).add(new BigInteger("1048576"));
                    System.out.println("修改为的范围"+range);
                }else {
                    System.out.println("原范围"+range);
                }
            }
        }

        Path videoFilePath = Paths.get(VIDEO_PATH);
        File videoFile = videoFilePath.toFile();

        // 如果文件不存在，返回 404
        if (!videoFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        long fileSize = videoFile.length();  // 获取文件大小

        // 如果有 RANGE 请求头，进行部分内容返回
        if (range != null && !range.isEmpty()) {
            try {
                List<HttpRange> ranges = HttpRange.parseRanges(range);
                HttpRange rangeToReturn = ranges.get(0);  // 只处理第一个字节范围

                long start = rangeToReturn.getRangeStart(fileSize);
                long end = rangeToReturn.getRangeEnd(fileSize);

                // 检查是否超出文件范围
                if (start >= fileSize || end >= fileSize) {
                    return ResponseEntity.status(416)  // 416 Requested Range Not Satisfiable
                            .header(HttpHeaders.CONTENT_RANGE, "bytes */" + fileSize)
                            .build();
                }

                // 打开文件流
                try (InputStream inputStream = Files.newInputStream(videoFilePath);
                     ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                    inputStream.skip(start);  // 跳过前面的字节

                    byte[] buffer = new byte[8192];  // 设置缓冲区大小
                    int bytesRead;
                    long bytesToRead = end - start + 1;
                    long bytesReadSoFar = 0;

                    while ((bytesRead = inputStream.read(buffer)) != -1 && bytesReadSoFar < bytesToRead) {
                        bytesReadSoFar += bytesRead;
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    byte[] videoPart = outputStream.toByteArray();
                    int sizeInBytes = videoPart.length; // 获取字节数组的大小，单位是字节

// 转换为 KB 和 MB
                    double sizeInKB = sizeInBytes / 1024.0;  // 转换为 KB
                    double sizeInMB = sizeInKB / 1024.0;    // 转换为 MB

// 输出结果
                    System.out.println("Size in bytes: " + sizeInBytes + " bytes");
                    System.out.println("Size in KB: " + String.format("%.2f", sizeInKB) + " KB");
                    System.out.println("Size in MB: " + String.format("%.2f", sizeInMB) + " MB");

                    return ResponseEntity.status(206)  // 206 Partial Content
                            .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                            .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(videoPart.length))
                            .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize)
                            .body(new ByteArrayResource(videoPart));
                }
            } catch (IOException e) {
                // 错误处理：返回一个自定义的错误信息
                String errorMessage = "Error streaming video: " + e.getMessage();
                ByteArrayResource errorResource = new ByteArrayResource(errorMessage.getBytes());
                return ResponseEntity.status(500)  // 500 Internal Server Error
                        .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                        .body(errorResource);
            }
        } else {
            System.out.println("返回所有数据");
            // 如果没有 RANGE 请求头，返回整个视频
            try {
                Resource resource = new UrlResource(videoFilePath.toUri());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                        .body(resource);
            } catch (IOException e) {
                // 错误处理：返回一个自定义的错误信息
                String errorMessage = "Error streaming video: " + e.getMessage();
                ByteArrayResource errorResource = new ByteArrayResource(errorMessage.getBytes());
                return ResponseEntity.status(500)  // 500 Internal Server Error
                        .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                        .body(errorResource);
            }
        }
    }
}
