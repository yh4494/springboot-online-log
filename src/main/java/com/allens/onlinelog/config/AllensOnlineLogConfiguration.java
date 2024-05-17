package com.allens.onlinelog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AllensOnlineLogConfiguration
 *
 * @author allens
 * @since 2024/5/8
 */
@Configuration
@ComponentScan("com.allens.onlinelog")
@EnableConfigurationProperties(AllensLogProperties.class)
public class AllensOnlineLogConfiguration {

    @Value("${server.servlet.context-path:}")
    private String serverContextPath;

    @Bean
    public FilterRegistrationBean<AllensLogFilter> myFilterRegistration() {
        FilterRegistrationBean<AllensLogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AllensLogFilter(serverContextPath));
        registration.addUrlPatterns("/log/*", "/log"); // 设置过滤器的 URL 匹配模式
        registration.setOrder(-1);
        // 可以设置其他过滤器配置，如顺序、初始化参数等
        return registration;
    }

}
