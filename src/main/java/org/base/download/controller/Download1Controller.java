package org.base.download.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.base.download.vo.ImageData;
import org.base.download.vo.RiskFundTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 耿
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class Download1Controller {

    /**
     * 下载图片 成压缩包
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/download1")
    public void template(HttpServletResponse response) throws Exception {

        RiskFundTaskVo fundTaskVo = new RiskFundTaskVo();
        fundTaskVo.setPartnerId(1);
        fundTaskVo.setPartnerName("合作商1");
        fundTaskVo.setId(23);
        fundTaskVo.setImageUrl("https://img13.360buyimg.com/n1/s450x450_jfs/t28570/85/1099414327/106024/8393f1/5c064302N617e6f1c.jpg");

        RiskFundTaskVo fundTaskVo1 = new RiskFundTaskVo();
        fundTaskVo1.setPartnerId(2);
        fundTaskVo1.setPartnerName("合作商2");
        fundTaskVo1.setId(33);
        fundTaskVo1.setImageUrl("https://img13.360buyimg.com/n1/s450x450_jfs/t28570/85/1099414327/106024/8393f1/5c064302N617e6f1c.jpg");
        List<RiskFundTaskVo> records = Lists.newArrayList();
        records.add(fundTaskVo);
        records.add(fundTaskVo1);
        Map<Integer, List<RiskFundTaskVo>> listMap = records.stream().collect(Collectors.groupingBy(RiskFundTaskVo::getPartnerId));
        downloadImages(listMap,response);

    }

    @SneakyThrows
    private void downloadImages(Map<Integer, List<RiskFundTaskVo>> listMap,HttpServletResponse response) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<ImageData>> futures = new ArrayList<>();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(byteArrayOutputStream)) {
            zos.setMethod(ZipOutputStream.DEFLATED);

            // 遍历每个合作伙伴的任务列表
            for (Map.Entry<Integer, List<RiskFundTaskVo>> listEntry : listMap.entrySet()) {
                List<RiskFundTaskVo> taskVos = listEntry.getValue().stream()
                        .sorted(Comparator.comparing(RiskFundTaskVo::getId))
                        .collect(Collectors.toList());
                String partnerName = taskVos.get(0).getPartnerName();

                // 处理每个任务
                for (RiskFundTaskVo taskVo : taskVos) {
                    String processNumber = taskVo.getProcessNumber();
                    List<String> imageUrls = Arrays.stream(taskVo.getImageUrl().split(",")).collect(Collectors.toList());

                    // ✅ 每个任务使用独立的计数器
                    AtomicInteger count = new AtomicInteger(1);

                    // 提交图片下载任务
                    for (String imageUrl : imageUrls) {
                        futures.add(executor.submit(() -> {
                            try {
                                // 下载图片数据
                                URL url = new URL(imageUrl);
                                String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
                                String fileExtension = fileName.contains(".")
                                        ? fileName.substring(fileName.lastIndexOf(".") + 1)
                                        : "dat";

                                try (InputStream is = url.openStream();
                                     ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                                    byte[] dataBlock = new byte[4096];
                                    int bytesRead;
                                    while ((bytesRead = is.read(dataBlock)) != -1) {
                                        buffer.write(dataBlock, 0, bytesRead);
                                    }

                                    // ✅ 使用原子递增确保序号唯一
                                    return new ImageData(
                                            processNumber,
                                            partnerName,
                                            buffer.toByteArray(),
                                            fileExtension,
                                            String.format("%03d", count.getAndIncrement())
                                    );
                                }
                            } catch (Exception e) {
                                log.error("图片下载失败: {}", imageUrl, e);
                                return null;
                            }
                        }));
                    }
                }
            }

            // 处理所有下载结果
            for (Future<ImageData> future : futures) {
                ImageData data = future.get();
                if (data == null) continue;

                String zipEntryName = data.getProcessNumber() + "-" + data.getPartnerName() + "-" + data.getCount() + "."
                        + data.getFileExtension();

                zos.putNextEntry(new ZipEntry(zipEntryName));
                zos.write(data.getData());
                zos.closeEntry();
            }

            // ✅ 确保ZIP完整结束
            zos.finish();

            // 准备响应数据
            byte[] zipData = byteArrayOutputStream.toByteArray();

            // 配置HTTP响应
            response.reset();
            response.setContentType("application/zip");
            // 保证下载到本地文件名不乱码
            response.setCharacterEncoding("UTF-8");
            String fileNameURL = URLEncoder.encode("凭证.zip", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);
            response.setContentLength(zipData.length);

            // 发送响应
            try (OutputStream out = response.getOutputStream()) {
                out.write(zipData);
            }
        } finally {
            // 确保线程池关闭
            executor.shutdown();
            try {
                if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
