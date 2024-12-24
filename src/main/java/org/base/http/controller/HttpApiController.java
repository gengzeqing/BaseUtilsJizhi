package org.base.http.controller;

import com.alibaba.fastjson2.JSON;
import com.burukeyou.uniapi.http.core.response.HttpFileResponse;
import com.burukeyou.uniapi.http.core.response.HttpJsonResponse;
import com.burukeyou.uniapi.http.core.response.HttpResponse;
import com.burukeyou.uniapi.http.core.response.HttpTextResponse;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.base.date.vo.HolidayVo;
import org.base.http.service.JZHttpApiService;
import org.base.http.service.TableService;
import org.base.http.service.WeatherHttpApiService;
import org.base.http.vo.DataVo;
import org.base.http.vo.HolidayVoReq;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author 耿
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class HttpApiController {

    private final WeatherHttpApiService weatherHttpApiService;
    private final JZHttpApiService jZHttpApiService;
    private final TableService tableService;

    /**
     * GET 请求 获取天气信息 无参数
     *
     * @return
     */
    @GetMapping("/api")
    public Map<String, Object> getWeather() {
        Map<String, Object> map = Maps.newHashMap();
        HttpJsonResponse<String> weather = weatherHttpApiService.getWeather();
        int httpCode = weather.getHttpCode();
        String jsonValue = weather.getJsonValue();
        Map<String, String> headers = weather.getHeaders();
        map.put("httpCode", httpCode);
        map.put("jsonValue", jsonValue);
        map.put("headers", headers);
        return map;
    }


    /**
     * GET 请求 获取天气信息 链接中带参数
     *
     * @return
     */
    @GetMapping("/api/getWeatherByCtiyId")
    public Map<String, Object> getWeatherByCtiyId() {
        String cityId = "101010100";
        Map<String, Object> map = Maps.newHashMap();
        HttpJsonResponse<String> weather = weatherHttpApiService.getWeatherByCtiyId(cityId);
        int httpCode = weather.getHttpCode();
        String jsonValue = weather.getJsonValue();
        Map<String, String> headers = weather.getHeaders();
        map.put("httpCode", httpCode);
        map.put("jsonValue", jsonValue);
        map.put("headers", headers);
        return map;
    }



    /**
     * GET 和 POST 请求 智能机器人
     * 一个和多个参数的传递
     *
     * jZHttpApiService.getChat
     * 把参数替换到链接当中
     *
     * jZHttpApiService.getChatUrl
     * 多个参数一个一个传递
     *
     * jZHttpApiService.getChatUrlData
     * 一对象的形式传递参数，
     *
     * @return
     */
    @PostMapping("/api/getChat")
    public Map<String, Object> getChat(String msg) {
        Map<String, Object> map = Maps.newHashMap();
        HttpResponse<String> weather = jZHttpApiService.getChat(msg);

        String bodyResult = weather.getBodyResult();
        int httpCode = weather.getHttpCode();
        Map<String, String> headers = weather.getHeaders();
        map.put("httpCode", httpCode);
        map.put("bodyResult", bodyResult);
        map.put("headers", headers);

        HttpResponse<String> weather1 = jZHttpApiService.getChatUrl(msg,"free","0");
        String bodyResult1 = weather1.getBodyResult();
        map.put("bodyResult1", bodyResult1);


        DataVo dataVo = new DataVo();
        dataVo.setAppid("0");
        dataVo.setKey("free");
        dataVo.setMsg(msg);
        HttpResponse<String> weather2 = jZHttpApiService.getChatUrlData(dataVo);
        String bodyResult2 = weather2.getBodyResult();
        map.put("bodyResult2", bodyResult2);

        return map;
    }


    /**
     * 获取 节假日，和补班日
     * @return
     */
    @PostMapping("/api/getHoliday")
    private String getHoliday() {
        AtomicLong count = new AtomicLong(1L);
        HolidayVoReq holidayVo = new HolidayVoReq();
        holidayVo.setDate("2024-01-01");
        holidayVo.setType("1");

        int httpCode = 0;
        String value = null;

        HttpResponse<String> holiday = jZHttpApiService.getHoliday(holidayVo);
        if (holiday instanceof HttpJsonResponse) {
            HttpJsonResponse<String> response = (HttpJsonResponse<String>) holiday;
            httpCode = response.getHttpCode();
            value = response.getJsonValue();
        } else if (holiday instanceof HttpTextResponse) {
            HttpTextResponse response = (HttpTextResponse) holiday;
            httpCode = response.getHttpCode();
            String textValue = response.getTextValue();
        }else if (holiday instanceof HttpFileResponse) {
            HttpFileResponse<String> response = (HttpFileResponse<String>) holiday;
            httpCode = response.getHttpCode();
        }
        if (httpCode != HttpStatus.OK.value()) {
            log.error("获取节假日失败");
        }

        JSONObject jsonObject = new JSONObject(value);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray list = result.getJSONArray("list");

        List<HolidayVo> listVo = Lists.newArrayList();

        for (int i = 0; i < list.length(); i++) {
            JSONObject holidayObj = list.getJSONObject(i);
            String holidayDate = holidayObj.getString("holiday");
            String name = holidayObj.getString("name");
            String vacation = holidayObj.getString("vacation");
            String remark = holidayObj.optString("remark");
            String wage = holidayObj.getString("wage");
            String tip = holidayObj.getString("tip");
            String rest = holidayObj.getString("rest");

            List<String> vacationList = Arrays.stream(vacation.split("\\|")).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            List<String> remarkList = Arrays.stream(remark.split("\\|")).filter(s -> !s.isEmpty()).collect(Collectors.toList());
            vacationList.forEach(m -> {
                HolidayVo holidavo = new HolidayVo();
                holidavo.setId(count.getAndIncrement());
                holidavo.setHolidayDate(m);
                holidavo.setHoliday("Y");
                holidavo.setName(name+"假期");
                holidavo.setAfter("N");
                holidavo.setWage(0);
                holidavo.setStatus(1);
                listVo.add(holidavo);
            });
            remarkList.forEach(m -> {
                HolidayVo holidavo = new HolidayVo();
                holidavo.setId(count.getAndIncrement());
                holidavo.setHolidayDate(m);
                holidavo.setHoliday("N");
                holidavo.setName(name+"补班");
                holidavo.setAfter("Y");
                holidavo.setWage(0);
                holidavo.setStatus(1);
                listVo.add(holidavo);
            });

            System.out.println("节日: " + name);
            System.out.println("日期: " + holidayDate);
            System.out.println("假期: " + vacationList);
            System.out.println("备注: " + remarkList);
            System.out.println("工资支付日: " + wage);
            System.out.println("提示: " + tip);
            System.out.println("请假建议: " + rest);
            System.out.println("----------------------------");
        }

        // 使用 List.sort() 和自定义的 Comparator 按 holidayDate 排序
        listVo.sort(new Comparator<HolidayVo>() {
            @SneakyThrows
            @Override
            public int compare(HolidayVo h1, HolidayVo h2) {
                // 使用 SimpleDateFormat 将日期字符串解析为 Date 对象
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = sdf.parse(h1.getHolidayDate());
                    Date date2 = sdf.parse(h2.getHolidayDate());
                    return date1.compareTo(date2);  // 从小到大排序
                } catch (Exception e) {
                    return 0;
                }
            }
        });

        tableService.saveBatch(listVo);
        return JSON.toJSONString(listVo);
    }
}
