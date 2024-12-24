package org.base.date.controller;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.base.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * @author 耿
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class DateController {


    private final DateUtil dateUtil;

    /**
     * 获取当月最后一个工作日
     *
     * @return
     */
    @GetMapping("/api/getLastWorkingDayOfMonth")
    public HashMap<String, String> getLastWorkingDayOfMonth() {
        String lastWorkingDayOfMonth = dateUtil.getLastWorkingDayOfMonth();
        HashMap<String, String> map = Maps.newHashMap();
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 获取当前月份
        YearMonth currentMonth = YearMonth.from(today);
        // 获取当月的最后一天
        LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();

        map.put("当前日期", today.toString());
        map.put("当月最后一天", lastDayOfMonth.toString());
        map.put("当月最后一个工作日", lastWorkingDayOfMonth);

        return map;
    }


    @GetMapping("/api/daysToReachTargetWorkingDate")
    public HashMap<String, String> daysToReachTargetWorkingDate(String startDate, String targetWorkingDays) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parse = LocalDate.parse(startDate, formatter);

        String lastWorkingDayOfMonth = dateUtil.daysToReachTargetWorkingDate(parse, Integer.parseInt(targetWorkingDays));
        HashMap<String, String> map = Maps.newHashMap();

        String str = startDate + " 开始 距离 " + targetWorkingDays + " 个工作日 是 " + lastWorkingDayOfMonth;

        map.put("当月最后一个工作日", str);

        return map;
    }


}
