package org.base.file.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.base.FileVo;
import org.base.Result;
import org.base.error.ParamException;
import org.base.role.vo.SystemClock;
import org.base.utils.PropertiesUtil;
import org.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class FileOSSController {


    /**
     * 获取上传的文件信息
     *
     * @param fileId
     * @param response
     * @throws IOException
     */
    @GetMapping("/image/{fileId}")
    public void getImage(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
        File file = null;
        try {
            // 构建文件对象
            String file_path = PropertiesUtil.getFile_path();
            file = new File(file_path + fileId);
            // 将图片写入响应输出流
            Files.copy(file.toPath(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                file = null;
            }
            if (response.getOutputStream() != null) {
                response.getOutputStream().close();
            }
        }
    }


    /**
     * @Description: 上传文件
     * @Date: 2022/9/26 14:06
     * @Author:
     */
    @SneakyThrows
    @PostMapping(value = "/api/file/upload")
    public Result uploadFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ParamException("数据为空");
        }
        if (StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new ParamException("格式错误");
        }
        String fileId = StringUtils.getKey(file.getOriginalFilename());
        if (StringUtils.isEmpty(fileId)) {
            throw new ParamException("格式错误");
        } else {
            fileId = SystemClock.now() + fileId;
        }
        // 上传到阿里云
        String path = PropertiesUtil.getFile_path() + File.separator + fileId;
        // 完整的保存路径
        File destinationFile = new File(path);
        // 保存文件
        file.transferTo(destinationFile);
        FileVo fileVo = new FileVo();
        fileVo.setObjectId(fileId);
        fileVo.setUrl(PropertiesUtil.getDomain() + "/image/" + fileId);
        fileVo.setFileName(file.getOriginalFilename());
        //apiMapper.insertFile(fileVo);
        return Result.success(fileVo);
    }

}