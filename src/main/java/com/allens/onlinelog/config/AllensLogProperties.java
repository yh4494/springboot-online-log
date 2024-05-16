package com.allens.onlinelog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AllensLogProperties
 *
 * @author allens
 * @since 2024/5/9
 */
@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "logging.online")
public class AllensLogProperties {

    private String logFile;

    private String username = "admin";

    private String password = "admin";

    private String title = "在线日志";

    private String enableHttpInfo;

}
