package org.base.excel.template;
/**
 * 主任务入口 DemoExport
 *
 *  Excel 文件数据导出 添加数据验证功能
 *  @ExcelDate 时间类型的格式验证注解
 *  @ExcelSelected 下拉框的验证注解 包含下拉框数据初始化，以及填入的信息验证
 *  @ExcelTxt 文本框设置 设置的是整列
 *  ExcelDynamicSelect 动态下拉框接口 可自定义继承实现，自己的业务逻辑
 * <p>
 *  主方法：
 *         String filename = "教师入职信息导入模板.xlsx";
 *         ServletOutputStream response1 = Utils.getResponse(response, filename);
 *         try (ExcelWriter excelWriter = EasyExcel.write(response1).build()) {
 *             WriteSheet writeSheet = EasyExcelUtil.writeSelectedSheet(TeacherExcelExport.class, 0, "教师入职信息数据表");
 *             excelWriter.write(new ArrayList<String>(), writeSheet);
 *             excelWriter.finish();
 *         }
 *
 */