package org.base.date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.base.date.vo.HolidayVo;
import org.base.http.mapper.HolidayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 耿
 */
@Component(value = "dateUtil")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DateUtil {

    private final HolidayMapper holidayMapper;

    /**
     * @param date 时间
     * @return
     * @DateTimeFormat(pattern = "yyyy-MM-dd")
     * private Date ctimeEnd;// 创建日期结束时间
     * <p>
     * 获取传入时间的最大值 2024-12-23 -> 2024-12-23 23:59:59
     */
    public static Long getMaxTime(Date date) {
        if (date == null) {
            return null;
        }
        // 将 Date 转换为 Instant
        Instant instant = date.toInstant();
        // 将 Instant 转换为 LocalDate（根据系统默认时区）
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        // 获取当天的最大时间 (23:59:59.999)
        LocalDateTime maxDateTime = localDate.atTime(LocalTime.MAX);
        // 将 LocalDateTime 转换为 Instant（时间戳）
        Instant instant1 = maxDateTime.atZone(ZoneId.systemDefault()).toInstant();
        // 获取时间戳（毫秒）
        return instant1.toEpochMilli() / 1000;
    }


    /**
     * 获取当月最后一个工作日
     *
     * @return 当月的最后一个工作日
     */
    public String getLastWorkingDayOfMonth() {
        LocalDate currentDate = LocalDate.now();
        // 获取节假日和补班数据
        List<HolidayVo> holidays = holidayMapper.selectList(new QueryWrapper<HolidayVo>().eq("status", 1));

        // 定义日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 存储节假日集合
        Set<LocalDate> holidaySet = new HashSet<>();

        // 存储补班日集合
        Set<LocalDate> workdaySet = new HashSet<>();

        // 将节假日和补班日期分别添加到集合中
        for (HolidayVo holiday : holidays) {
            // 解析节假日日期
            LocalDate holidayDate = LocalDate.parse(holiday.getHolidayDate(), formatter);
            if ("Y".equals(holiday.getHoliday())) {
                // 添加到节假日集合中
                holidaySet.add(holidayDate);
            } else {
                // 添加到补班日集合中
                workdaySet.add(holidayDate);
            }
        }

        // 获取当前月的最后一天
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        // 从月末开始，向前遍历，找到最后一个工作日
        LocalDate date = lastDayOfMonth;

        while (date.getDayOfMonth() > 0) {
            boolean isWorkday = false;

            // 判断是否是工作日（周一至周五），并且不是假期或是补班日
            boolean isWeekday = date.getDayOfWeek().getValue() >= 1 && date.getDayOfWeek().getValue() <= 5;
            if ((isWeekday && !holidaySet.contains(date)) || workdaySet.contains(date)) {
                isWorkday = true;
            }

            if (isWorkday) {
                // 找到最后一个工作日，返回
                return date.format(formatter);
            }

            // 否则，继续往前查找
            date = date.minusDays(1);
        }

        // 如果找不到工作日，返回空字符串或其他适当的值
        return "";
    }


    /**
     *  String date = "2023-07-01";
     *  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     *  LocalDate parse = LocalDate.parse(date, formatter);
     *
     * 计算指定日期 几天 后的工作日
     *
     * @param startDate   开始日期
     * @param targetWorkingDays 目标工作日数
     * @return
     */
    public String daysToReachTargetWorkingDate(LocalDate startDate, long targetWorkingDays) {
        // 获取节假日和补班数据
        List<HolidayVo> holidays = holidayMapper.selectList(new QueryWrapper<HolidayVo>().eq("status", 1));

        // 定义日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 存储节假日集合
        Set<LocalDate> holidaySet = new HashSet<>();

        // 存储补班日集合
        Set<LocalDate> workdaySet = new HashSet<>();


        // 将节假日和补班日期分别添加到集合中
        for (HolidayVo holiday : holidays) {

            // 解析节假日日期
            LocalDate holidayDate = LocalDate.parse(holiday.getHolidayDate(), formatter);
            if ("Y".equals(holiday.getHoliday())) {

                // 添加到节假日集合中
                holidaySet.add(holidayDate);
            } else {
                // 添加到补班日集合中
                workdaySet.add(holidayDate);
            }
        }

        // 设置开始日期为创建时间
        LocalDate date = startDate;


        // 工作日计数器
        long workingDaysCount = 0;

        // 遍历从开始日期开始的每一天，直到达到目标工作日数
        while (workingDaysCount < targetWorkingDays) {
            boolean isCountAdd = false;
            // 判断是否是工作日（周一至周五），并且不是假期或是补班日
            boolean isWorkday = date.getDayOfWeek().getValue() >= 1 && date.getDayOfWeek().getValue() <= 5;
            if ((isWorkday && !holidaySet.contains(date)) || workdaySet.contains(date)) {
                workingDaysCount++; // 计数器加一
                isCountAdd = true;
            }
            //如果是周六日,并且是补班日
            if (isWeekday(date) && workdaySet.contains(date)) {
                workingDaysCount++; // 计数器加一
                isCountAdd = true;
            }
            if (!isCountAdd && date.equals(LocalDate.now())) {
                workingDaysCount++; // 计数器加一
            }
            // 移动到下一天
            date = date.plusDays(1);
        }
        // 确保返回的日期是一个工作日
        while (holidaySet.contains(date) || ((date.getDayOfWeek().getValue() > 5) && !workdaySet.contains(date))) {
            date = date.plusDays(1);
        }
        return date.format(formatter);
    }

    /**
     * 判断给定日期是否为工作日
     *
     * @param date 日期
     * @return 如果是工作日，返回 true；否则，返回 false
     */
    private boolean isWeekday(LocalDate date) {
        return !("SATURDAY".equals(date.getDayOfWeek().toString()) || "SUNDAY".equals(date.getDayOfWeek().toString()));
    }

}
