package org.base.date.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode()
@TableName("TB_HOLIDAYS_COPY")
@Alias("holidays")
public class HolidayVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 假期记录的唯一标识符。
     */
    private Long id;

    /**
     * 假期的日期，格式为 'YYYY-MM-DD'。
     */
    private String holidayDate;

    /**
     * 表示是否为假期（'Y' 表示是，'N' 表示不是）。
     */
    private String holiday;

    /**
     * 假期或工作日的名称。
     */
    private String name;

    /**
     * 假期的工资倍数。
     */
    private Integer wage;


    /**
     * 表示该天是否为补班日（'Y' 表示是，'N' 表示不是）。
     */
    private String after;

    /**
     * 该补班日对应的假期或事件。
     */
    private String target;

    /**
     * 记录的状态（默认为 1）。
     */
    private Integer status;

    public HolidayVo(Long id, String holidayDate, String holiday, String name, Integer wage, String after, String target, Integer status) {
        this.id = id;
        this.holidayDate = holidayDate;
        this.holiday = holiday;
        this.name = name;
        this.wage = wage;
        this.after = after;
        this.target = target;
        this.status = status;
    }

    public HolidayVo() {
    }
}
