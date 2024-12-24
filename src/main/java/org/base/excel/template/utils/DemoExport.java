package org.base.excel.template.utils;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 主任务入口
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class DemoExport {

    @GetMapping("/test")
    public void exportTeacher(HttpServletResponse response) {
        String filename = "教师入职信息导入模板.xlsx";
        ServletOutputStream response1 = Utils.getResponse(response, filename);
        try (ExcelWriter excelWriter = EasyExcel.write(response1).build()) {
            WriteSheet writeSheet = EasyExcelUtil.writeSheet(TeacherExcelExport.class, 0, "教师入职信息数据表");
            excelWriter.write(new ArrayList<String>(), writeSheet);
            excelWriter.finish();
        }
    }
}
