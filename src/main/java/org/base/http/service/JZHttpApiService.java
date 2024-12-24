package org.base.http.service;

import com.burukeyou.uniapi.http.annotation.param.ComposePar;
import com.burukeyou.uniapi.http.annotation.param.PathPar;
import com.burukeyou.uniapi.http.annotation.param.QueryPar;
import com.burukeyou.uniapi.http.annotation.request.GetHttpInterface;
import com.burukeyou.uniapi.http.annotation.request.PostHttpInterface;
import com.burukeyou.uniapi.http.core.response.HttpResponse;
import org.base.http.annotation.JZHttpApi;
import org.base.http.vo.DataVo;
import org.base.http.vo.HolidayVoReq;

/**
 * 调用 http 天气接口
 */
//@JZHttpApi(url = "http://api.qingyunke.com")
@JZHttpApi
public interface JZHttpApiService {

    @GetHttpInterface("/api.php?key=free&appid=0&msg={msg}")
    HttpResponse<String> getChat(@PathPar("msg") String msg);

    @PostHttpInterface("/api.php")
    HttpResponse<String> getChatUrl(@QueryPar("msg") String param,@QueryPar("key") String free,@QueryPar("appid") String appid);

    @PostHttpInterface("/api.php")
    HttpResponse<String> getChatUrlData(@ComposePar DataVo dataVo);

    @PostHttpInterface
    (
            path = "/jiejiari/index",
            headers = {"Content-type:application/x-www-form-urlencoded","Accept:application/json"}

    )
    HttpResponse<String> getHoliday(@ComposePar HolidayVoReq HolidayVo);
}
