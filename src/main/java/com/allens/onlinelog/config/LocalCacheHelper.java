package com.allens.onlinelog.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * LocalCacheHelper
 *
 * @author allens
 * @since 2024/5/9
 */
public class LocalCacheHelper {

    private static final Cache<String, String> caffeine = Caffeine.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .maximumSize(20)
            .build();

    public static void setCacheObject(String key, String value) {
        caffeine.put(key, value);
    }

    public static String getCacheObject(String key) {
        return caffeine.getIfPresent(key);
    }

    public void invalidate(String key) {
        caffeine.invalidate(key);
    }

}
