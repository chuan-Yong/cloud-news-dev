package com.cloud.service.interceptors;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 13:24 2023/5/22
 * @Modified by:ycy
 */

import com.cloud.entity.AppUser;
import com.cloud.enums.UserStatus;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.grace.result.exception.GraceException;
import com.cloud.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/* 用户激活状态检查拦截器
* 发文章，修改文章，删除文章，
* 发表评论，查看评论等等
* 这些接口都是需要在用户激活以后，才能进行
* 否则需要提示用户前往[账号设置]去修改信息
*/
public class UserActiveInterceptor extends BaseInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求，访问controller之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        String userJson = redis.get(REDIS_USER_INFO + ":" + userId);
        AppUser user = null;
        if (StringUtils.isNotBlank(userJson)) {
            user = JsonUtils.jsonToPojo(userJson, AppUser.class);
        } else {
            GraceException.display(ResponseStatusEnum.UN_LOGIN);
            return false;
        }

        if (user.getActiveStatus() == null
                || user.getActiveStatus() != UserStatus.ACTIVE.type) {
            GraceException.display(ResponseStatusEnum.USER_INACTIVE_ERROR);
            return false;
        }

        /**
         * false：请求被拦截
         * true：请求通过验证，放行
         */
        return true;
    }


    /**
     * 请求访问到controller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求访问到controller之后，渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
