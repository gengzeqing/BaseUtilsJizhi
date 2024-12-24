package org.base.excel.export_data;


import com.google.common.collect.Lists;
 import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("excel")
public class ExcelController {


    @GetMapping(value = "/exportTest")
    public void export(HttpServletResponse response) {
        List<Object> build = build();
        //调用上述封装好的导出方法
        new MyExcelUtil().exportData(build, "data2", response);
    }

    //构造一对多嵌套数据
    public static List<Object> build() {
        List<Object> exportVos = new ArrayList<>();
        //第一条数据
        List<ExportVo.ProjectGroupExcelVO> projectGroupList = new ArrayList<>();
        ExportVo.ProjectGroupExcelVO projectGroupExcelVO1 = ExportVo.ProjectGroupExcelVO.builder()
                .age(17)
                .name("张三")
                .phone("111111111111")
                .vehicle(Lists.newArrayList(ExportVo.Vehicle.builder().brand("BYD").plateNo("京AM8888").build()
                        , ExportVo.Vehicle.builder().brand("BMW").plateNo("京AM9999").build()))
                .build();

        ExportVo.ProjectGroupExcelVO projectGroupExcelVO2 = ExportVo.ProjectGroupExcelVO.builder()
                .age(18)
                .name("李四")
                .phone("22222222222")
                .vehicle(Lists.newArrayList(ExportVo.Vehicle.builder().brand("NIO").plateNo("沪A99999").build(),
                                ExportVo.Vehicle.builder().brand("xiaopeng").plateNo("沪A88888").build()
                                , ExportVo.Vehicle.builder().brand("Benz").plateNo("沪B7777").build()
                        )
                )
                .build();


        projectGroupList.add(projectGroupExcelVO1);
        projectGroupList.add(projectGroupExcelVO2);
        ExportVo exportVo = ExportVo.builder().groupName("groupOne")
                .groupSlogan("we are family")
                .groupType("aaa")
                .house(new ExportVo.House()
                        .setAddressName("北京四合院")
                        .setRoomList(Lists.newArrayList(new ExportVo.Room().setName("主卧").setSquare(130),
                                new ExportVo.Room().setName("次卧").setSquare(100))))
                .build();
        exportVo.setGroupUsers(projectGroupList);


        //第二条数据
        List<ExportVo.ProjectGroupExcelVO> projectGroupList2 = new ArrayList<>();
        ExportVo.ProjectGroupExcelVO projectGroupExcelVO22 = ExportVo.ProjectGroupExcelVO.builder()
                .age(27)
                .name("王五")
                .phone("444444444444")
                .build();

        ExportVo.ProjectGroupExcelVO projectGroupExcelVO23 = ExportVo.ProjectGroupExcelVO.builder()
                .age(28)
                .name("老六")
                .phone("5555555555555")
                .build();

        projectGroupList2.add(projectGroupExcelVO22);
        projectGroupList2.add(projectGroupExcelVO23);
        ExportVo exportVo2 = ExportVo.builder().groupName("groupTwo")
                .groupSlogan("we are friends")
                .groupType("bbb")
                .house(new ExportVo.House().setAddressName("上海")
                        .setRoomList(Lists.newArrayList(new ExportVo.Room().setName("401"),
                                new ExportVo.Room().setName("401"))))
                .build();
        exportVo2.setGroupUsers(projectGroupList2);
        exportVos.add(exportVo);
        exportVos.add(exportVo2);
        return exportVos;
    }


}
