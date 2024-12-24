package org.base.excel.template.utils;

import org.base.excel.template.annotation.ExcelDynamicSelect;
import org.springframework.stereotype.Component;

/**
 * 岗位类别下拉选数据实现类
 *
 * @author 耿
 */
@Component("stationlateService")
public class StationlateServiceImpl implements ExcelDynamicSelect {

    @Override
    public String[] getSource() {
        return new String[]{"grapefruit", "huckleberry", "jackfruit", "kiwifruit", "lime"
        };
    }
}