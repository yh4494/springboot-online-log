package com.allens.onlinelog.annotations;

import com.allens.onlinelog.config.AllensOnlineLogConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * AllensOnlineLog
 *
 * @author allens
 * @since 2024/5/8
 */
@Import(AllensOnlineLogConfiguration.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllensOnlineLog {
}
