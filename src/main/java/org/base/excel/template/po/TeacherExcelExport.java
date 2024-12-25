package org.base.excel.template.po;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;
import org.base.excel.template.annotation.ExcelDate;
import org.base.excel.template.annotation.ExcelSelected;
import org.base.excel.template.annotation.ExcelTxt;
import org.base.excel.template.utils.StationlateServiceImpl;

import java.io.Serializable;


/**
 * @author 耿
 */
@Data
@HeadRowHeight(20)
@ContentRowHeight(18)
public class TeacherExcelExport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 职工编号
     */
    @ExcelTxt
    @ExcelProperty(index = 0, value = "职工编号")
    @ColumnWidth(20)
    private String no;
    /**
     * 姓名
     */
    @ExcelSelected(source = {"编号1","编号2","编号3","编号4"})
    @ExcelProperty(index = 1, value = "姓名")
    @ColumnWidth(30)
    private String name;
    /**
     * 身份证
     */
    @ExcelSelected(sourceClass = StationlateServiceImpl.class)
    @ExcelProperty(index = 2, value = "身份证号")
    @ColumnWidth(25)
    private String idCard;

    /**
     * 电话
     */
    @ExcelTxt
    @ExcelProperty(index = 3, value = "联系电话")
    @ColumnWidth(15)
    private String phone;
    /**
     * 岗位类别
     */
    //需要自定义实现  StationlateServiceImpl，下面有具体的实现方式
    //@ExcelSelected(sourceClass = StationlateServiceImpl.class)
    //@ExcelSelected(source = {"无哈哈","稍大","防辐射","挂号费更好"})
    @ExcelProperty(index = 4, value = "岗位类别")
    @ColumnWidth(15)
    private String station;

    /**
     * 备注
     */
    @ExcelProperty(index = 5, value = "备注")
    @ColumnWidth(15)
    private String remarks;

    @ExcelDate(sourceFormat = "yyyy-MM-dd")
    @ExcelProperty(index = 6, value = "导入时间1")
    @ColumnWidth(15)
    private String sourceFormat1;

    //@ExcelDate(sourceFormat = "yyyy-MM-dd")
    @ExcelProperty(index = 7, value = "导入时间2")
    @ColumnWidth(15)
    private String sourceFormat12;


    @ExcelProperty(index = 8, value = "导入时间3")
    @ColumnWidth(15)
    private String sourceFormat13;


    @ExcelProperty(index = 9, value = "导入时间4")
    @ColumnWidth(15)
    private String sourceFormat14;


    @ExcelProperty(index = 10, value = "导入时间5")
    @ColumnWidth(15)
    private String sourceFormat15;


    @ExcelProperty(index = 11, value = "导入时间6")
    @ColumnWidth(15)
    private String sourceFormat16;


}