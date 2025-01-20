package org.base.excel.export_data;
/**
 * @ExcelController 注解 标记导出的数据是一个集合类型
 *
 * @MyExcelEntity 注解 标记导出的数据是一个实体对象类型
 *
 * @MyExcelProperty 注解 指定导出的表头名称，
 *
 * 可以参考 ExcelController.export() 的导出方法
 * ---------------------------------------------------------------------------------------------------------------------
 *导出结果：多sheet 页面
 *    // 创建 ExcelWriter
 *    1.ExcelWriter writerBuilder = EasyExcel.write(outputStream).build();
 *
 *    // 每个sheet页 创建一个sheet
 *    2.WriteSheet build = EasyExcel.writerSheet("扣除补缴列表").registerWriteHandler(FraudUtils.exportStyle()).head(RiskFundTaskExportVo.class).build();
 *    writerBuilder.write(taskExportVos, build);
 *
 *    WriteSheet build1 = EasyExcel.writerSheet("扣除补缴任务明细").registerWriteHandler(FraudUtils.exportStyle()).head(RiskFundTaskDetailsExportVo.class).build();
 *    writerBuilder.write(detailsExportVos, build1);
 *
 *    // 结束写入操作并关闭资源
 *    3.writerBuilder.finish();
 *
 * ---------------------------------------------------------------------------------------------------------------------
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */