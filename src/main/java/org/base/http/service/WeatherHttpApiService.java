package org.base.http.service;

import com.burukeyou.uniapi.http.annotation.HttpApi;
import com.burukeyou.uniapi.http.annotation.param.PathPar;
import com.burukeyou.uniapi.http.annotation.request.GetHttpInterface;
import com.burukeyou.uniapi.http.core.response.HttpJsonResponse;

/**
 * 调用 http 天气接口
 */
@HttpApi(url = "http://t.weather.itboy.net")
public interface WeatherHttpApiService {


    @GetHttpInterface("/api/weather/city/101030101")
    HttpJsonResponse<String> getWeather();

    @GetHttpInterface("/api/weather/city/{cityId}")
    HttpJsonResponse<String> getWeatherByCtiyId(@PathPar("cityId") String cityId);
}
