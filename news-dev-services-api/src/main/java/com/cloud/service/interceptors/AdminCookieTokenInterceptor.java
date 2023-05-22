package com.cloud.service.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 21:22 2023/5/18
 * @Modified by:ycy
 */
public class AdminCookieTokenInterceptor extends BaseInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求，在访问controller调用之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String adminUserId = getCookie(request, "aid");
        String adminUserToken = getCookie(request, "atoken");

        boolean run = verifyUserIdToken(adminUserId, adminUserToken, REDIS_ADMIN_TOKEN);
        return run;
    }
}
