package com.cloud.zuul.filters;

import com.cloud.grace.result.GraceJSONResult;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.utils.IPUtil;
import com.cloud.utils.JsonUtils;
import com.cloud.utils.RedisOperator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 13:50 2023/5/31
 * @Modified by:ycy
 */
@Component
public class BlackIpFilter extends ZuulFilter{

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 连续请求次数
     */
    @Value("${blackIp.continueCounts}")
    private Integer continueCounts;

    /**
     * 时间间隔
     */
    @Value("${blackIp.timeInterval}")
    private Integer timeInterval;

    /**
     * 限制的时间
     */
    @Value("${blackIp.limitTimes}")
    private Integer limitTimes;



    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("执行【ip黑名单】过滤器...");

        System.out.println("continueCounts: " + continueCounts);
        System.out.println("timeInterval: " + timeInterval);
        System.out.println("limitTimes: " + limitTimes);

         //获取上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        //获取当前访问的ip
        String ip = IPUtil.getRequestIp(request);

        /**
         * 需求：
         *  判断ip在10秒内的请求次数是否超过10次
         *  如果超过，则限制这个ip访问15秒，15秒以后再放行
         */
        //ip key
        final String ipRedisKey = "zuul-ip:" + ip;
        //ip limit key
        final String ipRedisLimitKey = "zuul-ip-limit:" + ip;

        //获取当前ip key的剩余时间
        long limitLeftTime = redisOperator.ttl(ipRedisLimitKey);
        //如果限制时间大于o则停止响应
        if (limitLeftTime > 0) {
            stopRequest(context);
            return null;
        }

        //请求次数+1
        long requestCounts = redisOperator.increment(ipRedisKey, 1);
        //设置连续访问失效时间
        if(requestCounts == 1) {
            redisOperator.expire(ipRedisLimitKey,timeInterval);
        }

        // 如果还能取得请求次数，说明用户连续请求的次数落在10秒内
        // 一旦请求次数超过了连续访问的次数，则需要限制这个ip的访问
        if (requestCounts > continueCounts) {
            // 限制ip的访问时间
            redisOperator.set(ipRedisLimitKey, ipRedisLimitKey, limitTimes);
            stopRequest(context);
        }

        return null;

    }

    public void stopRequest(RequestContext context){
        // 停止zuul继续向下路由，禁止请求通信
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(200);

        String result = JsonUtils.objectToJson(GraceJSONResult.errorCustom
                (ResponseStatusEnum.SYSTEM_ERROR_ZUUL));
        context.setResponseBody(result);
        //指定编码格式
        context.getResponse().setCharacterEncoding("utf-8");
        //设置响应格式
        context.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
