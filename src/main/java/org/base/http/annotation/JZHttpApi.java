package org.base.http.annotation;

import com.burukeyou.uniapi.http.annotation.HttpApi;
import org.base.http.JZHttpApiProcessor;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author 耿
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@HttpApi(processor = JZHttpApiProcessor.class)
public @interface JZHttpApi {

    /**
     * 渠道方域名地址
     */
    @AliasFor(annotation = HttpApi.class)
    String url() default "https://apis.tianapi.com";

    /**
     * 渠道方分配的appId
     */
    String key() default "253d7883589f34f1418aa56b02828486";
}
