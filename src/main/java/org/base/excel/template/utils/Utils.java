package org.base.excel.template.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author 耿
 */
public class Utils {

    private static final String DATE_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

    private static final Pattern PATTERN = Pattern.compile(DATE_PATTERN);


    /**
     * 从实体类获取导出的名称
     *
     */
    public static List<String> getExcelHeaders(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> headers = new ArrayList<>();

        for (Field field : fields) {
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null) {
                String[] values = excelProperty.value();
                if (values.length > 0) {
                    // 获取注解中的第一个值
                    headers.add(values[0]);
                }
            }
        }
        return headers;
    }

    /**
     * 导出excel 的表头配置
     * @param response
     * @param fileName
     * @return
     */
    @SneakyThrows
    public static ServletOutputStream getResponse(HttpServletResponse response, String fileName) {
        // 保证下载到本地文件名不乱码
        response.setCharacterEncoding("UTF-8");
       // response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileNameUrl = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameUrl + ";" + "filename*=utf-8''" + fileNameUrl);
        return response.getOutputStream();
    }


    public static void styleTextConfig(Workbook workbook ,List<Integer> listNum,Sheet sheet) {
        if (!CollectionUtils.isEmpty(listNum)) {

            // 创建单元格样式
            CellStyle textStyle = workbook.createCellStyle();
            // 设置单元格数据格式为文本
            DataFormat format = workbook.createDataFormat();
            // "@" 表示文本格式
            textStyle.setDataFormat(format.getFormat("@"));

            int rows = 1;
            for (int i = 0; i < 100 ; i++) {
                Row header = sheet.createRow(rows);
                for (Integer integer : listNum) {
                    Cell cell = header.createCell(integer);
                    sheet.setDefaultColumnWidth((short) 20);
                    // 应用文本样式
                    cell.setCellStyle(textStyle);
                }
                ++rows;
            }
        }
    }

}
