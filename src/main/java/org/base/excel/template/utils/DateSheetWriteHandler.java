package org.base.excel.template.utils;


import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 耿
 */
@Data
@AllArgsConstructor
public class DateSheetWriteHandler implements SheetWriteHandler {

    // 用一个静态映射来存储日期格式
    private static final Map<String, String[]> DATE_FORMAT_MAP = new HashMap<String, String[]>() {{
        put("YYYY-MM", new String[]{"Date(1970, 01)", "Date(9999, 12)", "yyyy-MM"});
        put("YYYY-MM-DD", new String[]{"Date(1970, 01, 01)", "Date(9999, 12, 31)", "yyyy-MM-dd"});
        put("YYYY-MM-DD HH", new String[]{"Date(1970, 01, 01, 00)", "Date(9999, 12, 31, 23)", "yyyy-MM-dd HH"});
        put("YYYY-MM-DD HH:MM", new String[]{"Date(1970, 01, 01, 00, 00)", "Date(9999, 12, 31, 23, 59)", "yyyy-MM-dd HH:mm"});
        put("YYYY-MM-DD HH:MM:SS", new String[]{"Date(1970, 01, 01, 00, 00, 00)", "Date(9999, 12, 31, 23, 59, 59)", "yyyy-MM-dd HH:mm:ss"});
    }};


    private final Map<Integer, ExcelDateResolve> excelDateResolveMap;

    /**
     * Called before create the sheet
     */
    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }


    /**
     * Called after the sheet is created
     */
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        // 获取Sheet和DataValidationHelper对象
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();

        // 遍历 excelDateResolveMap
        excelDateResolveMap.forEach((k, v) -> {
            // 设置下拉列表的行范围： 首行，末行，首列，末列
            CellRangeAddressList rangeList = new CellRangeAddressList(v.getFirstRow(), v.getLastRow(), k, k);

            // 如果日期格式不为空
            if (StringUtils.isNotEmpty(v.getSourceFormat())) {
                // 获取对应的日期格式数据
                String[] data = getDateData(v.getSourceFormat());

                // 检查日期格式是否合法
                if (data != null) {
                    // 创建日期验证约束
                    DataValidationConstraint dvConstraint = helper.createDateConstraint(
                            DVConstraint.ValidationType.DATE,
                            data[0],  // 最小日期
                            data[1],  // 最大日期
                            data[2]   // 日期格式
                    );

                    // 创建数据验证对象
                    DataValidation dataValidation = helper.createValidation(dvConstraint, rangeList);
                    configureDataValidation(dataValidation, v.getSourceFormat());

                    // 将数据验证对象添加到sheet
                    sheet.addValidationData(dataValidation);
                }
            }
        });
    }

    // 获取日期格式的数据
    private String[] getDateData(String key) {
        return DATE_FORMAT_MAP.get(key.toUpperCase());
    }

    // 配置数据验证的提示框和错误框
    private void configureDataValidation(DataValidation dataValidation, String format) {
        // 允许为空
        dataValidation.setEmptyCellAllowed(true);
        // 显示错误框
        dataValidation.setShowErrorBox(true);
        // 错误提示
        dataValidation.createErrorBox("错误提示", "请输入正确的日期格式！");
        // 显示提示框
        dataValidation.setShowPromptBox(true);
        // 提示内容
        dataValidation.createPromptBox("提示", "请输入日期，格式为" + format);
        // 设置错误样式为停止
        dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
    }
}