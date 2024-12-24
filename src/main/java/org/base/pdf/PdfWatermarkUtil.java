package org.base.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import org.base.utils.PropertiesUtil;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * PDF 添加水印
 * @author 耿
 */
public class PdfWatermarkUtil {


    /**
     * 获取水印文字总长度
     *
     * @param waterMarkName 水印的文字
     * @param g
     * @return 水印文字总长度
     */
    public static int getWatermarkLength(String waterMarkName, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(
                waterMarkName.toCharArray(), 0, waterMarkName.length());
    }

    private static int interval = 0;

    /**
     * @param waterMarkName 水印的文字
     * @param outputFile    输出地址
     */
    public static boolean waterMarkPdf(InputStream in, String outputFile,
                                       String waterMarkName) {
        PdfStamper stamper = null;
        PdfReader reader = null;
        FileOutputStream out = null;
        try {
            reader = new PdfReader(in);
            Field f = PdfReader.class.getDeclaredField("ownerPasswordUsed");
            f.setAccessible(true);
            f.set(reader, Boolean.TRUE);
            out = new FileOutputStream(outputFile);
            stamper = new PdfStamper(reader, out);
            BaseFont base = null;
            base = BaseFont.createFont(
                    PropertiesUtil.getSimSun_path() + ",0",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Rectangle pageRect = null;
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.2f);
            gs.setStrokeOpacity(0.4f);
            int total = reader.getNumberOfPages() + 1;
            JLabel label = new JLabel();
            FontMetrics metrics;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            int textH = metrics.getHeight();
            int textW = metrics.stringWidth(label.getText());
            PdfContentByte under;
            for (int i = 1; i < total; i++) {
                pageRect = reader.getPageSizeWithRotation(i);
                under = stamper.getOverContent(i);
                under.saveState();
                under.setGState(gs);
                under.beginText();
                under.setFontAndSize(base, 12);
                // 水印文字成30度角倾斜
                for (int height = interval + textH; height < pageRect.getHeight(); height = height + textH * 20) {
                    for (int width = interval + textW; width < pageRect
                            .getWidth() + textW; width = width + textW * 2) {
                        under.showTextAligned(Element.ALIGN_TOP,
                                waterMarkName, width - textW, height - textH,
                                30);
                    }
                }
                under.setColorFill(Color.GRAY);
                // 添加水印文字
                under.endText();
                under.stroke();
            }
            stamper.close();
            reader.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stamper != null) {
                try {
                    stamper.close();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                reader.close();
            }
        }
        return false;
    }


}
