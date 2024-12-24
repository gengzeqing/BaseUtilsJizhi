package org.base.utils;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * 读取配置文件
 * @author 耿
 */
@Data
@Slf4j
public class PropertiesUtil {

    private static final Properties config;
    @Getter
    private static String redisStart;
    @Getter
    private static String file_path;
    @Getter
    private static String domain;
    @Getter
    private static String appDomain;
    @Getter
    private static String qccKey;
    @Getter
    private static String qccSecretKey;
    @Getter
    private static String bdKey;
    @Getter
    private static String bdSecretKey;
    @Getter
    private static String ksKey;
    @Getter
    private static String ksSecret;
    @Getter
    private static String simSun_path;
    @Getter
    private static String oAAlias;
    @Getter
    private static String oADbLinkChannel;


    static {
        config = new Properties();
        try {
            config.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties"));
            redisStart = config.getProperty("redisStart").trim();
            file_path = config.getProperty("file_path").trim();
            domain = config.getProperty("domain").trim();
            appDomain = config.getProperty("appDomain").trim();
            qccKey = config.getProperty("qccKey").trim();
            qccSecretKey = config.getProperty("qccSecretKey").trim();
            bdKey = config.getProperty("bdKey").trim();
            bdSecretKey = config.getProperty("bdSecretKey").trim();
            ksKey = config.getProperty("ksKey").trim();
            ksSecret = config.getProperty("ksSecret").trim();
            simSun_path = config.getProperty("simSun_path").trim();
            oAAlias = config.getProperty("oAAlias").trim();
        } catch (Exception e) {
            log.error("读取配置文件失败", e);
            throw new RuntimeException("读取配置文件失败");
        }
    }
}
