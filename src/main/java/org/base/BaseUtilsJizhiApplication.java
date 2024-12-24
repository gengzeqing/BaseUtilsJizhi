package org.base;

import com.burukeyou.uniapi.annotation.UniAPIScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@UniAPIScan("org.base.http.service")
@SpringBootApplication
public class BaseUtilsJizhiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseUtilsJizhiApplication.class, args);
    }
}