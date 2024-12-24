package org.base.download.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author 耿
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class DownloadController {

    @RequestMapping(value = "/download")
    public void template(HttpServletResponse response) throws Exception {

        InputStream fileInputStreamFile1 = getClass().getClassLoader().getResourceAsStream("template/申诉函模版.docx");
        //InputStream fileInputStreamFile2 = getClass().getClassLoader().getResourceAsStream("template/申诉函模版2.docx");

       /*
        * 多个文件下载 *
        {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

            // ZIP压缩输出流
            ZipOutputStream zip = new ZipOutputStream(byteOut);

            // 压缩文件1
            zip.putNextEntry(new ZipEntry("测试下载.docx"));
            IOUtils.copy(fileInputStreamFile1, zip);
            zip.closeEntry();

//        // 压缩文件2
//        zip.putNextEntry(new ZipEntry("申诉函模版2.docx"));
//        IOUtils.copy(fileInputStreamFile2, zip);
//        zip.closeEntry();

            IOUtils.closeQuietly(fileInputStreamFile1);
//        IOUtils.closeQuietly(fileInputStreamFile2);
            IOUtils.closeQuietly(zip);

            byte[] data = byteOut.toByteArray();
            // 清除缓冲区数据
            response.reset();

            ServletOutputStream outputStream = Utils.getResponse(response, "测试下载.zip");


            IOUtils.write(data, outputStream);
            // 关闭流
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(byteOut);

        }*/




        /* 单个文件下载 *
        {
            IOUtils.copy(fileInputStreamFile1, Utils.getResponse(response, "测试.docx"));
            response.flushBuffer();
            IOUtils.closeQuietly(fileInputStreamFile1);
        }
         */
    }

}
