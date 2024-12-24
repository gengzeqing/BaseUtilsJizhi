package org.base.http.vo;

import com.burukeyou.uniapi.http.annotation.param.QueryPar;
import lombok.Data;

import java.io.Serializable;

@Data
public class HolidayVoReq implements Serializable {

    private static final long serialVersionUID = 2351255353676979161L;
    // 	查询日期或日期范围
    @QueryPar
    private String date;
    //查询类型，0批量、1按年、2按月、3范围
    @QueryPar
    private String type;
    //查询模式，为1同时返回中外特殊节日信息
    @QueryPar
    private String mode;

}
