package org.base.http.controller.需要登录认证的rest;

import com.alibaba.fastjson2.JSON;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.compress.utils.Lists;
import org.base.error.ParamException;
import org.base.http.vo.OaFileVo;
import org.base.http.vo.UrlFileVo;
import org.base.utils.PropertiesUtil;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * OA 消息推送工具类
 * @author 耿
 */
@Slf4j
public class OaPushUtils {

    // 不同的环境地址不同
    private static final String ADD_URL = PropertiesUtil.getDomain() + "/api/km-review/kmReviewRestService/addReview";
    private static final String UPDATE_URL = PropertiesUtil.getBdKey() + "/api/km-review/kmReviewRestService/updateReviewInfo";
    private static final String USERNAME = "song.yang";
    private static final String PASSWORD = "sy@123456";

    // 传递附件时文件的类型
    private static final Map<String, String> FILE_MAP = Stream.of(new Object[][]{
            {"JPEG", "image/jpeg"},
            {"PNG", "image/png"},
            {"GIF", "image/gif"},
            {"PDF", "application/pdf"},
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (String) data[1]));

    /**
     * @param docSubject 文档标题
     * @param fdTemplateId 模板ID
     * @param docCreator 发起人
     * @param formValuesJson 业务数据
     * @return 流程Id 后续更新使用
     */
    @SneakyThrows
    public static String send(String docSubject, String fdTemplateId, String docCreator, String formValuesJson, List<UrlFileVo> urlFile) {
        log.info("send推送OA数据 docSubject:{} fdTemplateId:{} docCreator:{}", docSubject, fdTemplateId, docCreator);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                // 文档标题
                .addFormDataPart("docSubject", docSubject)
                // 模板ID
                .addFormDataPart("fdTemplateId", fdTemplateId)
                // 状态：待审
                .addFormDataPart("docStatus", "20")
                // 发起人
                .addFormDataPart("docCreator", docCreator)
                // 业务数据
                .addFormDataPart("formValues", formValuesJson);
        // 附件
        getFileStream(bodyBuilder, fdTemplateId, urlFile);
        return sendMessage(bodyBuilder, 1);
    }


    @SneakyThrows
    private static String sendMessage(MultipartBody.Builder bodyBuilder, int type) {
        // 发送请求
        log.info("发送请求===start==>");
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(chain -> {
                    // 创建基本认证的凭证
                    String credential = Credentials.basic(USERNAME, PASSWORD);
                    // 在请求头中加入 Authorization 头
                    Request request = chain.request().newBuilder().header("authorization", credential).build();
                    return chain.proceed(request);
                })
                // 设置连接超时
                .connectTimeout(360, TimeUnit.SECONDS)
                // 设置读取超时
                .readTimeout(360, TimeUnit.SECONDS)
                // 设置写入超时
                .writeTimeout(360, TimeUnit.SECONDS).build();

        // 流程Id
        String processId = null;
        String url = type == 1 ? ADD_URL : UPDATE_URL;
        Request request = new Request.Builder().url(url).post(bodyBuilder.build()).build();
        log.info("发送请求===end==>");
        // 处理响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                log.error("推送OA失败 response: {}", response);
                throw new ParamException("数据推送失败");
            }
            String responseBody = response.body().string();
            if (!isValidJson(responseBody)) {
                log.info("推送OA成功 response: {}", responseBody);
                if (responseBody.length() == 32) {
                    processId = responseBody;
                } else {
                    log.error(responseBody);
                    throw new ParamException(responseBody);
                }
            } else {
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                // 获取解析后的 JSON 对象的值
                if (!jsonObject.get("success").getAsBoolean()) {
                    String msg = jsonObject.get("msg").getAsString();
                    log.error("推送OA处理失败 msg: {}", msg);
                } else {
                    log.error("OA数据处理失败 response: {}", responseBody);
                }
                throw new ParamException("处理失败");
            }
        }
        log.info("发送请求===end==>" + processId);
        return processId;
    }

    private static void getFileStream(MultipartBody.Builder bodyBuilder, String fdTemplateId, List<UrlFileVo> urlFileVoList) {
        List<OaFileVo> oaFile = getOaFile(urlFileVoList, fdTemplateId);
        if (CollectionUtils.isEmpty(oaFile)) {
            return;
        }
        for (int i = 0; i < oaFile.size(); i++) {
            OaFileVo oaFileVo = oaFile.get(i);
            bodyBuilder.addFormDataPart("attachmentForms[" + i + "].fdKey", oaFileVo.getFileId());
            String urlFileType = getUrlFileType(oaFileVo.getFdKey());
            String mediaType = FILE_MAP.get(urlFileType.toUpperCase());
            byte[] fdAttachment = oaFileVo.getFdAttachment();
            RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType),fdAttachment);
            bodyBuilder.addFormDataPart("attachmentForms[" + i + "].fdFileName", oaFileVo.getFdFileName() + "." + urlFileType);
            bodyBuilder.addFormDataPart("attachmentForms[" + i + "].fdAttachment", oaFileVo.getFdFileName(), requestBody);
        }
    }

    // 判断字符串是否是有效的 JSON 格式
    private static boolean isValidJson(String str) {
        try {
            JsonElement element = JsonParser.parseString(str);
            // 判断是否是 JSON 对象或 JSON 数组
            return element.isJsonObject() || element.isJsonArray();
        } catch (Exception e) {
            // 如果解析失败，说明不是有效的 JSON 格式
            return false;
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

    @SneakyThrows
    private static List<OaFileVo> getOaFile(List<UrlFileVo> fileVoList, String fdTemplateId) {
        if (CollectionUtils.isEmpty(fileVoList)) {
            return null;
        }
        List<OaFileVo> oaFileVoList = Lists.newArrayList();
        for (int i = 0; i < fileVoList.size(); i++) {
            int count = i + 1;
            UrlFileVo urlFileVo = fileVoList.get(i);
            String outputFilePath = PropertiesUtil.getFile_path() + File.separator + urlFileVo.getFileId();
            Path path = Paths.get(outputFilePath);
            byte[] fileBytes = null;
            try {
                fileBytes = Files.readAllBytes(path);
                System.out.println("File size: " + fileBytes.length + " bytes");
            } catch (Exception e) {
                log.error("解析附件出错:{}", path, e);
                throw new ParamException("文件不存在");
            }
            OaFileVo oaFileVo = new OaFileVo();
            oaFileVo.setFileId(fdTemplateId);
            oaFileVo.setFdKey(urlFileVo.getFileId());
            oaFileVo.setFdFileName("附件" + count);
            oaFileVo.setFdAttachment(fileBytes);
            oaFileVoList.add(oaFileVo);
        }
        return oaFileVoList;
    }

}
