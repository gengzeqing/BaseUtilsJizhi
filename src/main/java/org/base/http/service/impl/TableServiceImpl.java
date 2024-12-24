package org.base.http.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.base.date.vo.HolidayVo;
import org.base.http.mapper.HolidayMapper;
import org.base.http.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional(rollbackFor = Exception.class)
public class TableServiceImpl implements TableService {

    private final HolidayMapper holidayMapper;

    public List<HolidayVo> getHolidays() {
        return holidayMapper.selectList(new QueryWrapper<HolidayVo>().eq("status", 1));
    }

    @Override
    public void saveBatch(List<HolidayVo> listVo) {
        listVo.forEach(holidayMapper::insert);
    }

}
