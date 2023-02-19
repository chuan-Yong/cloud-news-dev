package com.cloud.service.api;

import com.cloud.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 23:27 2021/3/21
 * @Modified by:ycy
 */
public class BaseController {

    @Autowired
    public RedisOperator redisOperator;

    @Value("${website.domain-name}")
    public String domainName;

    public static final String MOBILE_SMSCODE = "mobile:smscode";

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    public static final String REDIS_USER_INFO = "redis_user_info";

    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token";

    public static final Integer REDIS_MAXAGE = 30 * 24 * 60 * 60;

    public static final Integer COOKIE_DELETE = 0;

    public static final Integer COMMON_START_PAGE = 1;

    public static final Integer COMMON_PAGE_SIZE = 10;
    /**
     * 验证BO中错误信息
     */
    public Map<String, Object> getErrors(BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            //获取具体错误的属性
            String fieldName =  fieldError.getField();
            //获取具体的错误信息
            String defaultMessage = fieldError.getDefaultMessage();
            map.put(fieldName,defaultMessage);
        }
        return map;
    }

    /**
     *
     * @param servletRequest
     * @param servletResponse
     * @param cookieName    cookie名
     * @param cookieValue cookie值信息
     * @param maxAge cookie有效时间
     */
    public void setCookie(HttpServletRequest servletRequest,
                          HttpServletResponse servletResponse,
                          String cookieName,
                          String cookieValue,
                          Integer maxAge) {
        try {
            cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
            setCookieValue(servletRequest,servletResponse,cookieName,cookieValue,maxAge);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setCookieValue(HttpServletRequest request,
                               HttpServletResponse response,
                               String cookieName,
                               String cookieValue,
                               Integer maxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domainName);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletRequest request,
                             HttpServletResponse response,
                             String cookieName) {
        try {
            String deleteValue = URLEncoder.encode("", "utf-8");
            setCookieValue(request, response, cookieName, deleteValue, COOKIE_DELETE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
