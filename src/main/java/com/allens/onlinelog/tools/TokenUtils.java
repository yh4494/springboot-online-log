package com.allens.onlinelog.tools;

import java.util.UUID;

/**
 * TokenUtils
 *
 * @author allens
 * @since 2024/5/9
 */
public class TokenUtils {

    /**
     * 获取token
     *
     * @return token
     */
    public static String getToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
