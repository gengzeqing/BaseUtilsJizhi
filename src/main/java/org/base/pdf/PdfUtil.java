package org.base.pdf;

import cn.hutool.core.util.StrUtil;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.base.utils.PropertiesUtil;
import org.base.utils.StringUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 耿
 */
@Slf4j
public class PdfUtil {

    private static final String PATTERN_DATE_YEAR_MONTH_DAY = "yyyy-MM-dd";
    // PDF 水印名称
    private static final String WATERMARK = "瑞福德汽车金融有限公司";

    /**
     * 创建PDF文件并保存到服务器路径
     *
     * @param data             数据Map
     * @param templateFileName 模板文件名
     * @return 文件完整路径 可以直接访问
     */
    @SuppressWarnings("deprecation")
    public static String createPdfAndGetUrl(Map<String, Object> data, String templateFileName) {
        try (ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
            // 创建FreeMarker配置实例
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setClassLoaderForTemplateLoading(PdfUtil.class.getClassLoader(), "template");
            cfg.setDefaultEncoding("UTF-8");

            // 加载模板
            Template template = cfg.getTemplate(templateFileName);

            // 输出内容到字符串
            StringWriter stringWriter = new StringWriter();
            template.setEncoding("UTF-8");
            template.process(data, stringWriter);

            // 将HTML内容转换为PDF
            String htmlContent = stringWriter.toString();
            log.info("Generated HTML content: {}", htmlContent);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            ITextFontResolver fontResolver = renderer.getFontResolver();

            // 使用类加载器加载字体文件
            try (InputStream fontStream = PdfUtil.class.getClassLoader().getResourceAsStream("fonts/simsun.ttc")) {
                if (fontStream != null) {
                    fontResolver.addFont("fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                } else {
                    throw new RuntimeException("加载字体文件失败");
                }
            }

            renderer.layout();
            renderer.createPDF(pdfOutputStream);
            renderer.finishPDF();

            // 保存PDF到服务器路径
            String fileId = StringUtils.getKey("1.pdf");
            if (StrUtil.isEmpty(fileId)) {
                throw new RuntimeException("文件名不合法");
            } else {
                fileId = System.currentTimeMillis() + fileId;
            }
            String outputFilePath = PropertiesUtil.getFile_path() + File.separator + fileId;
            File destinationFile = new File(outputFilePath);
            try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
                pdfOutputStream.writeTo(fileOutputStream);
            }

            // 构造并返回文件的URL
            String fileUrl = PropertiesUtil.getDomain() + "/image/" + fileId;

            FileInputStream in = new FileInputStream(new File(destinationFile.toURI()));
            PdfWatermarkUtil.waterMarkPdf(in, destinationFile.getPath(), WATERMARK);

            return fileUrl;

        } catch (IOException | TemplateException | DocumentException e) {
            log.error("创建PDF失败", e);
            throw new RuntimeException("创建PDF失败");
        }
    }


    /**
     * 创建PDF文件并保存到服务器路径
     *
     * @param data             数据Map
     * @param templateFileName 模板文件名
     * @return 文件的 fileId
     */
    @SuppressWarnings("deprecation")
    public static String createPdfAndGetFileId(Map<String, Object> data, String templateFileName) {
        try (ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
            // 创建FreeMarker配置实例
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setClassLoaderForTemplateLoading(PdfUtil.class.getClassLoader(), "template");
            cfg.setDefaultEncoding("UTF-8");

            // 加载模板
            Template template = cfg.getTemplate(templateFileName);

            // 输出内容到字符串
            StringWriter stringWriter = new StringWriter();
            template.setEncoding("UTF-8");
            template.process(data, stringWriter);

            // 将HTML内容转换为PDF
            String htmlContent = stringWriter.toString();
            log.info("Generated HTML content: {}", htmlContent);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            ITextFontResolver fontResolver = renderer.getFontResolver();

            // 使用类加载器加载字体文件
            try (InputStream fontStream = PdfUtil.class.getClassLoader().getResourceAsStream("fonts/simsun.ttc")) {
                if (fontStream != null) {
                    fontResolver.addFont("fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                } else {
                    throw new RuntimeException("加载字体文件失败");
                }
            }

            renderer.layout();
            renderer.createPDF(pdfOutputStream);
            renderer.finishPDF();

            // 保存PDF到服务器路径
            String fileId = StringUtils.getKey("1.pdf");
            if (StrUtil.isEmpty(fileId)) {
                throw new RuntimeException("文件名不合法");
            } else {
                fileId = System.currentTimeMillis() + fileId;
            }
            String outputFilePath = PropertiesUtil.getFile_path() + File.separator + fileId;
            File destinationFile = new File(outputFilePath);
            try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
                pdfOutputStream.writeTo(fileOutputStream);
            }

            // 构造并返回文件的URL
            //String fileUrl = PropertiesUtil.getDomain()+"/image/"+fileId;

            FileInputStream in = new FileInputStream(new File(destinationFile.toURI()));
            PdfWatermarkUtil.waterMarkPdf(in, destinationFile.getPath(), WATERMARK);

            return fileId;

        } catch (IOException | TemplateException | DocumentException e) {
            log.error("创建PDF失败", e);
            throw new RuntimeException("创建PDF失败");
        }
    }


    public static void main(String[] args) {
        ComplaintVo vo = new ComplaintVo();
        ComplaintVo complaint = new ComplaintVo();
        vo.setOccurrenceDate("1234");
        vo.setNoticeDefaultType("1");
        vo.setNoticeType("1");
        vo.setProposedDefaultHandling("a");
        vo.setDocumentDate("1234");
        vo.setResolutionOccurrenceDate("aaaa");
        complaint.setPartnerName("测试的");
        complaint.setContractNo("aaaa1233");
        Map<String, Object> data = new HashMap<>();
        data.put("complaint", complaint);
        data.put("vo", vo);
        String templateFileName = "tsgaozhihan.ftl";
        createPdfAndGetUrl(data, templateFileName);
    }
}
