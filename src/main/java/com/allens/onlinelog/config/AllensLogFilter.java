package com.allens.onlinelog.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AllensLogFilter
 *
 * @author allens
 * @since 2024/5/8
 */
@Slf4j
public class AllensLogFilter implements Filter {

    private String contextPath;

    public AllensLogFilter(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            if (request instanceof HttpServletRequest) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;

                if (httpServletRequest.getRequestURI().equals(contextPath + "/log/login")) {
                    chain.doFilter(request, response);
                    return;
                }

                //log.info("request uri: {}", httpServletRequest.getRequestURI());
                Cookie tokenId = getCookieByCookieName(AllensLogConstant.ALLENS_ONLINE_LOG, httpServletRequest);
                if (null == tokenId) {
                    httpServletResponse.sendRedirect(contextPath + "/log/login");
                    return;
                }

                if (!"TRUE".equals(LocalCacheHelper.getCacheObject(tokenId.getValue()))) {
                    httpServletResponse.sendRedirect(contextPath + "/log/login");
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("Exception", e);
        }
    }

    private Cookie getCookieByCookieName(String allensOnlineLog, HttpServletRequest request) {
        // 从HttpServletRequest中获取cookie
        if (null != request.getCookies()) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(allensOnlineLog)) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
