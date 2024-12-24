package org.base.http.service;

import org.base.date.vo.HolidayVo;

import java.util.List;

public interface TableService {
    List<HolidayVo> getHolidays();

    void saveBatch(List<HolidayVo> listVo);
}
