package org.base.http;

import com.burukeyou.uniapi.http.core.channel.HttpApiMethodInvocation;
import com.burukeyou.uniapi.http.core.channel.HttpSender;
import com.burukeyou.uniapi.http.core.request.HttpMetadata;
import com.burukeyou.uniapi.http.core.response.HttpResponse;
import com.burukeyou.uniapi.http.extension.HttpApiProcessor;
import lombok.extern.slf4j.Slf4j;
import org.base.http.annotation.JZHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JZHttpApiProcessor implements HttpApiProcessor<JZHttpApi> {


    @Autowired
    private Environment environment;

    /**
     * 实现-postBeforeHttpMetadata： 发送Http请求之前会回调该方法，可对Http请求体的内容进行二次处理
     *
     * @param httpMetadata     原来的请求体
     * @param methodInvocation 被代理的方法
     * @return 新的请求体
     */
    @Override
    public HttpMetadata postBeforeHttpMetadata(HttpMetadata httpMetadata, HttpApiMethodInvocation<JZHttpApi> methodInvocation) {

        log.info("执行 postBeforeHttpMetadata ---------->1");

        // 获取MTuanHttpApi注解
        JZHttpApi proxyApiAnnotation = methodInvocation.getProxyApiAnnotation();

        // 获取MTuanHttpApi注解的appId，由于该appId是环境变量所以我们从environment中解析取出来
        String appIdVar = proxyApiAnnotation.key();
        appIdVar = environment.resolvePlaceholders(appIdVar);

        // 添加到查询参数中
        httpMetadata.putQueryParam("key", appIdVar);

        return httpMetadata;
    }

    /**
     * 实现-postBeforeHttpMetadata： 发送Http请求时，可定义发送请求的行为 或者打印请求和响应日志。
     */
    @Override
    public HttpResponse<?> postSendingHttpRequest(HttpSender httpSender, HttpMetadata httpMetadata, HttpApiMethodInvocation<JZHttpApi> methodInvocation) {

        System.out.println("执行 postSendingHttpRequest ---------->2");


        //  忽略 weatherApi.getToken的方法回调，否则该方法也会回调此方法会递归死循环。 或者该接口指定自定义的HttpApiProcessor重写postSendingHttpRequest
//        Method getTokenMethod = ReflectionUtils.findMethod(JZHttpApi.class, "getToken", String.class, String.class);
//        if (getTokenMethod == null || getTokenMethod.equals(methodInvocation.getMethod())) {
//            return httpSender.sendHttpRequest(httpMetadata);
//        }

        // 把这两个值放到此次的请求cookie中
//        httpMetadata.addCookie(new Cookie("token", token));
//        httpMetadata.addCookie(new Cookie("sessionId", sessionId));

        log.info("开始发送Http请求 请求接口:{} 请求体:{}", httpMetadata.getHttpUrl().toUrl(), httpMetadata.toHttpProtocol());

        // 使用框架内置工具实现发送请求
        HttpResponse<?> rsp = httpSender.sendHttpRequest(httpMetadata);

        log.info("开始发送Http请求 响应结果:{}", rsp.toHttpProtocol());

        return rsp;
    }

    /**
     * 实现-postAfterHttpResponseBodyResult： 反序列化后Http响应体的内容后回调，可对该结果进行二次处理返回
     *
     * @param bodyResult   Http响应体反序列化后的结果
     * @param rsp          原始Http响应对象
     * @param httpMetadata       被代理的方法
     * @param methodInvocation Http请求体
     */
    @Override
    public Object postAfterHttpResponseBodyResult(Object bodyResult, HttpResponse<?> rsp, HttpMetadata httpMetadata, HttpApiMethodInvocation<JZHttpApi> methodInvocation) {

        System.out.println("执行 postAfterHttpResponseBodyResult ---------->3");

        return bodyResult;
    }


}
