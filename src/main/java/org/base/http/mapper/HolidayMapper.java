package org.base.http.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.base.date.vo.HolidayVo;

/**
 * @author wh
 */
@Mapper
public interface HolidayMapper extends BaseMapper<HolidayVo> {
}
