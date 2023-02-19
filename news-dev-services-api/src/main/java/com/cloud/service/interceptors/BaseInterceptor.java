package com.cloud.service.interceptors;

import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.grace.result.exception.GraceException;
import com.cloud.utils.RedisOperator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 18:32 2021/7/31
 * @Modified by:ycy
 */
public class BaseInterceptor {

    @Autowired
    public RedisOperator redis;

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token";

    /**
     * 验证用户token
     * @param id 用户id
     * @param token 用户token
     * @param redisKeyPrefix redisKey前缀
     * @return
     */
    public boolean verifyUserIdToken(String id,
                                     String token,
                                     String redisKeyPrefix) {
        if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(token)) {
            String redisKey = redis.get(redisKeyPrefix + ":" + id);
            if(StringUtils.isBlank(id)) {
                GraceException.display(ResponseStatusEnum.UN_LOGIN);
                return false;
            } else {
                if(!redisKeyPrefix.equalsIgnoreCase(redisKey)) {
                    GraceException.display(ResponseStatusEnum.TICKET_INVALID);
                    return false;
                }
            }
        } else {
            GraceException.display(ResponseStatusEnum.UN_LOGIN);
        }
        return true;
    }
}
