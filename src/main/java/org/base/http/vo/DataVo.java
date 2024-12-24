package org.base.http.vo;

import com.burukeyou.uniapi.http.annotation.param.QueryPar;
import lombok.Data;

@Data
public class DataVo {

    @QueryPar
    private String appid;
    @QueryPar
    private String key;
    @QueryPar
    private String msg;

}
