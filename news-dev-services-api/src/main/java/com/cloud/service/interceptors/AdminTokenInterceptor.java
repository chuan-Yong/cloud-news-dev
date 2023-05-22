package com.cloud.service.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 18:31 2021/7/31
 * @Modified by:ycy
 */
public class AdminTokenInterceptor extends BaseInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String adminUserId = request.getHeader("adminUserId");
        String adminUserToken = request.getHeader("adminUserToken");
        System.out.println("=====================================================================");
        System.out.println("AdminTokenInterceptor - adminUserId = " + adminUserId);
        System.out.println("AdminTokenInterceptor - adminUserToken = " + adminUserToken);
        System.out.println("=====================================================================");

        boolean run = verifyUserIdToken(adminUserId,adminUserToken,REDIS_ADMIN_TOKEN);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {

    }
}
